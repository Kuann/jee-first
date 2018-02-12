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
@Table(name = "teacher")
public class Teacher extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "teacher_code", nullable = false)
	private String teacherCode;
	
	@Column(name = "teacher_name", nullable = false)
	private String teacherName;
}
