package com.sunman.org.restfulwebservices.socialmedia;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResourceController {

	private UserDAOService daoService;

	public UserResourceController(UserDAOService service) {
		this.daoService = service;
	}


	@GetMapping("/users")
	public List<User> getAllUsers() {
		return daoService.getUsers();
	}


	@GetMapping("/users/{id}")
	public User getUser(@PathVariable Integer id) {
		return daoService.getUser(id);
	}

	@GetMapping("/users/predicate/{id}")
	public User getUserUsingPredicate(@PathVariable Integer id) {
		return daoService.getUserUsingPredicate(id);
	}

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User newUser = daoService.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest() //Get the current URI - http://localhost:8080/users
				.path("/{Id}")											//add variable id - http://localhost:8080/users/{id}
				.buildAndExpand(newUser.getId())						//replace runtime value - http://localhost:8080/users/4
				.toUri();												// Generate new URI for just created user
		return ResponseEntity.created(location).build();				
	}

	@DeleteMapping("/users/predicate/{id}")
	public void deleteUserUsingPredicate(@PathVariable Integer id) {
		daoService.deleteUserUsingPredicate(id);
	}


	@GetMapping("/users/hateaoas/{id}")
	public EntityModel<User> getUserByHATEOAS(@PathVariable Integer id) {
		EntityModel<User> entityModel = EntityModel.of(daoService.getUser(id)); // Same as Bean(User) but with extra features to add by HEATOAS
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers()); // Create link to JSON Response
		entityModel.add(link.withRel("all-users")); //Add link to Entity(Bean)
		return entityModel;
	}
}
