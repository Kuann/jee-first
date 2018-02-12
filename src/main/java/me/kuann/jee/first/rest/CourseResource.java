package me.kuann.jee.first.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import me.kuann.jee.first.model.Course;
import me.kuann.jee.first.service.CourseService;

@Path("course")
public class CourseResource {

	@Inject
	private CourseService courseService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAllCourse() {
		return courseService.getAllCourses();
	}
	
}
