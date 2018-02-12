package me.kuann.jee.first.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import me.kuann.jee.first.dao.CourseDao;
import me.kuann.jee.first.model.Course;

@Stateless
public class CourseService {

	@Inject
	private CourseDao courseDao;
	
	public List<Course> getAllCourses() {
		return courseDao.findAll();
	}

}
