package me.kuann.jee.first.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.kuann.jee.first.dao.master.CourseDao;
import me.kuann.jee.first.model.Course;

@RunWith(Arquillian.class)
@Cleanup(phase = TestExecutionPhase.BEFORE)
@UsingDataSet(value = { "dataset/course.xml" })
public class CourseServiceIT extends Deployments {
	
	@Inject
	private CourseDao courseDao;
	
	@Test
	public void test_getting_courses() {
		Course courseEntity = Course.builder()
				.courseCode("PA231")
				.courseName("Computer vision")
				.build();
		
		courseDao.persist(courseEntity);
		List<Course> courses = courseDao.getAllData();
		assertThat(courses.size(), is(2));
	}
}
