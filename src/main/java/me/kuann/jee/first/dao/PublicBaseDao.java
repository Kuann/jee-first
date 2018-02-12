package me.kuann.jee.first.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import me.kuann.jee.first.entity.BaseEntity;

public abstract class PublicBaseDao<T extends BaseEntity> extends BaseDao<T> {

	@PersistenceContext(unitName = "kuannJeePU")
    private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	protected List<T> executeQueryToGetList(CriteriaQuery<T> criteriaQuery) {
		return getEntityManager().createQuery(criteriaQuery).getResultList();
	}
}