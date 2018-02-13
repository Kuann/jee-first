package me.kuann.jee.first.dao;

import javax.ejb.Stateless;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import me.kuann.jee.first.model.BaseEntity;

@Stateless
public class AuditingEntityListener {

	public static final String DEFAULT_PRINCIPAL = "kuann";

	@PrePersist
	public void onPrePersist(BaseEntity entity) {
		entity.setCreateBy(getPrincipalName());
	}

	@PreUpdate
	public void onPreUpdate(BaseEntity entity) {
		entity.setUpdateBy(getPrincipalName());
	}

	public String getPrincipalName() {
		return DEFAULT_PRINCIPAL;
	}
}
