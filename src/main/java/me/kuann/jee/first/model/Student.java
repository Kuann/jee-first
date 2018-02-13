package me.kuann.jee.first.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "student_code", nullable = false)
	private String studentCode;
	
	@Column(name = "student_name", nullable = false)
	private String studentName;
	
	@Column(name = "course_code", nullable = false)
	private String courseCode;
	
	private transient Course course;
}
