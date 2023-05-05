package com.sunman.org.restfulwebservices.socialmedia;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.sunman.org.restfulwebservices.socialmedia.jpa.PostSpringDataRepository;
import com.sunman.org.restfulwebservices.socialmedia.jpa.UserSpringDataRepository;

import jakarta.validation.Valid;

@RestController
public class UserResourceJpaController {

	private UserSpringDataRepository userRepository;
	
	private PostSpringDataRepository postRepository;

	/*public UserResourceJpaController(UserSpringDataRepository userRepository) {
		this.userRepository = userRepository;
	}*/
	
	public UserResourceJpaController(UserSpringDataRepository userRepository, PostSpringDataRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}


	@GetMapping("/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}


	@GetMapping("/jpa/users/{id}")
	public Optional<User> getUser(@PathVariable Integer id) {
		return userRepository.findById(id);
	}

	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User newUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest() //Get the current URI - http://localhost:8080/users
				.path("/{Id}")											//add variable id - http://localhost:8080/users/{id}
				.buildAndExpand(newUser.getId())						//replace runtime value - http://localhost:8080/users/4
				.toUri();												// Generate new URI for just created user
		return ResponseEntity.created(location).build();				
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}


	@GetMapping("/jpa/users/hateaoas/{id}")
	public EntityModel<User> getUserByHATEOAS(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("User not found with Id : "+id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user.get()); // Same as Bean(User) but with extra features to add by HEATOAS
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers()); // Create link to JSON Response
		entityModel.add(link.withRel("all-users")); //Add link to Entity(Bean)
		return entityModel;
	}
	
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getPostsByUser(@PathVariable Integer id) {
		Optional<User> optUser = userRepository.findById(id);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("User not found with Id : "+id);
		}
		return optUser.get().getPosts();
	}
	
	
	@GetMapping("/jpa/users/{id}/posts/{postId}")
	public Post getPostForUser(@PathVariable Integer id, @PathVariable Integer postId) {
		Optional<User> optUser = userRepository.findById(id);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("User not found with Id : "+id);
		}
		
		List<Post> posts = optUser.get().getPosts();
		for(int i = 0; i < posts.size(); i++) {
			if(posts.get(i).getId().equals(postId)) {
				return posts.get(i);
			}
		}
		return null;
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> addPostByUser(@PathVariable Integer id, @Valid @RequestBody Post post) {
		Optional<User> optUser = userRepository.findById(id);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("User not found with Id : "+id);
		}
		
		post.setUser(optUser.get());
		Post newPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest() //Get the current URI - http://localhost:8080/jpa/users/{id}/posts/
				.path("/{id}")											//add variable id - http://localhost:8080/jpa/users/{id}/posts/{id}
				.buildAndExpand(newPost.getId())						//replace runtime value - http://localhost:8080/jpa/users/1000/posts/4
				.toUri();												// Generate new URI for just created user
		return ResponseEntity.created(location).build();				
	}
}
