package me.kuann.jee.first.rest;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import me.kuann.jee.authorization.AccessibleWithoutRole;
import me.kuann.jee.first.lang.ClientLocal;
import me.kuann.jee.first.model.Course;
import me.kuann.jee.first.model.Student;
import me.kuann.jee.first.service.master.CourseService;
import me.kuann.jee.first.service.tenant.StudentService;

@Path("")
public class RestResource {

	@Inject
	private CourseService courseService;
	
	@Inject
	private StudentService studentService;
	
	@Inject
	@ClientLocal
	private Locale local;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("course")
	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("student")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("lang")
	@AccessibleWithoutRole
	public Locale getLocal() {
		return local;
	}
	
}
