package com.sunman.org.restfulwebservices.socialmedia.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sunman.org.restfulwebservices.socialmedia.UserNotFoundException;

@ControllerAdvice //It links with exception Handler internally
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class) //This will handle a  exception
	public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@ExceptionHandler(UserNotFoundException.class) //This will handle a  exception
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		//ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false)); //Display very long message along with custom message.
		//ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(), request.getDescription(false)); // This sends only 1 error message if though there are many.
		//ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Total Errors : "+ex.getErrorCount()+", First Error : "+ ex.getFieldError().getDefaultMessage(), request.getDescription(false)); // This show total no of errors and sends only 1 error message if though there are many.
		
		StringBuilder message = new StringBuilder();
		List<ObjectError> errorList = ex.getAllErrors();
		for (int i = 0; i < errorList.size(); i++) {
			ObjectError error = errorList.get(i);
			message.append(error.getDefaultMessage());
			message.append(",");
		}
		message.deleteCharAt(message.length() -1);
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), message.toString(), request.getDescription(false)); // This display all errors
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
