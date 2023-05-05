package com.sunman.org.restfulwebservices.socialmedia;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details")
//@JsonIgnoreProperties("dob","passowrd") // Use to hide multiple field value to be part of JSON response.
public class User {

	@Id
	@GeneratedValue
	private Integer id;

	@Size(min = 2, message = "Name should be minimum 2 characters") // Length constraints
	@JsonProperty("user-name")  //Give different name in JSON response(instead of name - use user-name
	private String name;

	@Past(message = "Date of birth should be past date") //Future value not accepted
	private LocalDate dob;
	
	@JsonIgnore   //Field will be hide in JSON response.
	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)	//One user have many posts, mapped by user field in Post class.
	private List<Post> posts;		// Multiple Post created by one user // FetchType.EGAR- its default, it sends details of User with Post, FetchType.LAZY - It will send only Post details.
	
	public User() {
		super();
	}

	public User(Integer id, String name, LocalDate dob, String password) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dob=" + dob + "]"+ ", password=" + password;
	}
}
