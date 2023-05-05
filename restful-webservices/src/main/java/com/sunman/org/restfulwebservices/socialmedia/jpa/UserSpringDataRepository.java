package com.sunman.org.restfulwebservices.socialmedia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunman.org.restfulwebservices.socialmedia.User;

public interface UserSpringDataRepository extends JpaRepository<User, Integer> {

}
