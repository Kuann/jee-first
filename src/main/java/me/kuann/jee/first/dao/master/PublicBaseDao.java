package me.kuann.jee.first.dao.master;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import me.kuann.jee.first.dao.BaseDao;
import me.kuann.jee.first.model.BaseEntity;

public abstract class PublicBaseDao<T extends BaseEntity> extends BaseDao<T> {

	@PersistenceContext
    private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
