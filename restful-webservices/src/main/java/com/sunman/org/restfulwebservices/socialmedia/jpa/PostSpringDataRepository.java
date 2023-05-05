package com.sunman.org.restfulwebservices.socialmedia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunman.org.restfulwebservices.socialmedia.Post;

public interface PostSpringDataRepository extends JpaRepository<Post, Integer> {

}
