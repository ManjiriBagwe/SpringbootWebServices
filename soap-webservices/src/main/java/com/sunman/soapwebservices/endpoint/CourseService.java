package com.sunman.soapwebservices.endpoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sunman.courses.Course;

@Component
public class CourseService {

	public enum StatusEnum {SUCCESS, FAILURE};
	
	private static List<Course> courses = new ArrayList<Course>();

	static {
		courses.add(new Course(1, "1dcjk", "1D dnkd"));
		courses.add(new Course(2, "2 ddv", "2D vav"));
		courses.add(new Course(3, "3dsvfgf", "3D reyehr"));
		courses.add(new Course(4, "4hyth", "4D fdghhyeh"));
	}

	public Course findById(int id) {
		for(Course course : courses) {
			if(course.getId() == id) {
				return course;
			}
		}
		return null;
	}

	public List<Course> findAll() {
		return courses;
	}

	public StatusEnum deleteById(int id) {

		Iterator<Course> itr = courses.iterator();
		while (itr.hasNext()) {
			Course course = (Course) itr.next();
			if(course.getId() == id) {
				itr.remove();
				return StatusEnum.SUCCESS;
			}
		}
		return StatusEnum.FAILURE;
	}

}
