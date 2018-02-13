package me.kuann.jee.first.service.tenant;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import me.kuann.jee.first.dao.master.CourseDao;
import me.kuann.jee.first.dao.tenant.StudentDao;
import me.kuann.jee.first.model.Course;
import me.kuann.jee.first.model.Student;

@Stateless
public class StudentService {

	@Inject
	private CourseDao courseDao;
	
	@Inject
	private StudentDao studentDao;
	
	public List<Student> getAllStudents() {
		List<Course> courses = courseDao.findAll();
		List<Student> students = studentDao.findAll();
		
		for (Student student: students) {
			Course course = courses.stream().filter(c -> c.getCourseCode().equals(student.getCourseCode())).findFirst().orElse(null);
			student.setCourse(course);
		}
		
		return students;
	}
	
}
