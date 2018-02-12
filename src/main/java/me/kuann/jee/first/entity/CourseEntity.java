package me.kuann.jee.first.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course")
public class CourseEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "course_code", nullable = false)
	private String courseCode;
	
	@Column(name = "course_name", nullable = false)
	private String courseName;

}
