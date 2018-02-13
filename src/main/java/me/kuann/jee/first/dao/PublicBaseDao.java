package me.kuann.jee.first.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import me.kuann.jee.first.model.BaseEntity;

public abstract class PublicBaseDao<T extends BaseEntity> extends BaseDao<T> {

	@PersistenceContext(unitName = "kuannJeePU")
    private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
