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
@Table(name = "course", schema = "public")
public class Course extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "course_code", nullable = false)
	private String courseCode;
	
	@Column(name = "course_name", nullable = false)
	private String courseName;

}
