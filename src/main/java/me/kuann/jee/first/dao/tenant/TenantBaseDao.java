package me.kuann.jee.first.dao.tenant;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import me.kuann.jee.first.dao.BaseDao;
import me.kuann.jee.first.model.BaseEntity;
import me.kuann.jee.multitenancy.CurrentSchema;

public abstract class TenantBaseDao<T extends BaseEntity> extends BaseDao<T> {

	@Inject
	@CurrentSchema
    private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
