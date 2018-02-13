package me.kuann.jee.first.dao.tenant;

import javax.ejb.Stateless;

import me.kuann.jee.first.model.Student;

@Stateless
public class StudentDao extends TenantBaseDao<Student> {
}
