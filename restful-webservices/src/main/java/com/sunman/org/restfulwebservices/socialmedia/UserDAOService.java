package com.sunman.org.restfulwebservices.socialmedia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {

	private static List<User> users = new ArrayList<User>();
	
	private static int userCount = 0;

	static {
		//users.add(new User(1,"Manjiri",LocalDate.now().minusYears(40)));
		users.add(new User(++userCount,"Manjiri",LocalDate.now().minusYears(40), "abc"));
		users.add(new User(++userCount,"Shashank",LocalDate.now().minusYears(46), "xyz"));
		users.add(new User(++userCount,"Vedant",LocalDate.now().minusYears(10), "pqr"));
	}

	public List<User> getUsers() {
		return users;
	}
	
	public User getUser(Integer id) {
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getId().equals(id)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public User getUserUsingPredicate(Integer id) {
		Predicate<User> predicate = users -> users.getId().equals(id);
		//return users.stream().filter(predicate).findFirst().get(); //get() : return value if exists/throw NosuchElemetException
		//return users.stream().filter(predicate).findFirst().orElse(null); //orElse() : return value or other
		User user = users.stream().filter(predicate).findFirst().orElse(null);
		if(user == null) {
			throw new UserNotFoundException("User with id "+id+" not Found");
		}
		return user;
	}

	public User addUser(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}

	public void deleteUserUsingPredicate(Integer id) {
		Predicate<User> predicate = users -> users.getId().equals(id);
		users.removeIf(predicate);
	}
	
	
}
