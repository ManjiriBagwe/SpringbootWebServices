package com.sunman.soapwebservices.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sunman.courses.Course;
import com.sunman.courses.CourseDetails;
import com.sunman.courses.DeleteCourseDetailsRequest;
import com.sunman.courses.DeleteCourseDetailsResponse;
import com.sunman.courses.GetAllCourseDetailsRequest;
import com.sunman.courses.GetAllCourseDetailsResponse;
import com.sunman.courses.GetCourseDetailsRequest;
import com.sunman.courses.GetCourseDetailsResponse;
import com.sunman.courses.Status;
import com.sunman.soapwebservices.endpoint.CourseService.StatusEnum;
import com.sunman.soapwebservices.exception.CourseNotFoundException;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	public CourseService service;

	/*
	 * @Endpoint - Tell Spring framework that t, this is a endpoint. This can accept request and send response.
	 * @RequestPayload - Maps XML(GetCourseDetailsRequest) to Java class(GetCourseDetailsRequest.java).
	 * @ResponsePayload - Maps XML(GetCourseDetailsResponse) to Java class(GetCourseDetailsResponse.java).
	 * @PayloadRoot - Tells that if any request comes with given name space and given local part, should execute this method.
	 * */

	@PayloadRoot(namespace = "http://sunman.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse getCourseDetails(@RequestPayload GetCourseDetailsRequest request) throws CourseNotFoundException {

		//Hard coded the values
		/*
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(request.getId());
		courseDetails.setName("Learn SOAP");
		courseDetails.setDescription("Learn with Spring");
		 */

		//Get values from Service class
		Course course = service.findById(request.getId());
		if(course == null) {
			//throw new RuntimeException("Invalid Course Id : "+request.getId());
			throw new CourseNotFoundException("Invalid Course Id : "+request.getId());
		}
		return mapCourseDetails(course);

	}

	@PayloadRoot(namespace = "http://sunman.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse getAllCourseDetails(@RequestPayload GetAllCourseDetailsRequest request) {
		List<Course> courses = service.findAll();
		return mapAllCourseDetails(courses);

	}


	@PayloadRoot(namespace = "http://sunman.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseById(@RequestPayload DeleteCourseDetailsRequest request) {
		StatusEnum status = service.deleteById(request.getId());
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;
	}


	/* Mapping Bean Status to XSD Status. */
	private Status mapStatus(StatusEnum status) {
		if(status == StatusEnum.SUCCESS) {
			return com.sunman.courses.Status.SUCCESS;
		}
		return com.sunman.courses.Status.FAILURE;
	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for(Course course : courses) {
			CourseDetails courseDetails = mapCourse(course);
			response.getCourseDetails().add(courseDetails);
		}
		return response;
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}
}
