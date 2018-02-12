package me.kuann.jee.first.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

import me.kuann.jee.first.exception.LocalizedException;
import me.kuann.jee.first.model.BaseEntity;

public abstract class BaseDao<T extends BaseEntity> {

	protected static final String PERSISTENCE_LOADGRAPH_HINT = "javax.persistence.loadgraph";
	private Class<T> persistentClass;

	@Resource
	private SessionContext sessionContext;

	protected abstract EntityManager getEntityManager();
	
	public T persist(T entity) {
		getEntityManager().persist(entity);
		return entity;
	}
	
	public List<T> persist(Collection<T> entities) {
		if (CollectionUtils.isEmpty(entities)) {
			return Collections.emptyList();
		}
		List<T> result = new ArrayList<>(entities.size());
		for (T entity : entities) {
			result.add(persist(entity));
		}
		return result;
	}

	public T merge(T entity) {
		return getEntityManager().merge(entity);
	}
	
	public List<T> merge(Collection<T> entitiesToBeUpdated) {
		if (CollectionUtils.isEmpty(entitiesToBeUpdated)) {
			return Collections.emptyList();
		}
		
		List<T> result = new ArrayList<>(entitiesToBeUpdated.size());
		for (T entity : entitiesToBeUpdated) {
			result.add(merge(entity));
		}
		return result;
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public void remove(Long id) {
		getEntityManager().remove(findAndRefresh(id));
	}
	
	public void remove(List<T> entitiesToBeDeleted) {
		if (CollectionUtils.isEmpty(entitiesToBeDeleted)) {
			return ;
		}
		
		for (T entity : entitiesToBeDeleted) {
			remove(entity);
		}
	}

	public String getPrincipalName() {
		String principalName = AuditingEntityListener.DEFAULT_PRINCIPAL;
		if (sessionContext != null) {
			principalName = sessionContext.getCallerPrincipal().getName();
		}
		return principalName;
	}

	/**
	 * @return the persistentClass
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getPersistentClass() {
		if (persistentClass == null) {
			persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
		return persistentClass;
	}

	/**
	 * Get all data with list column which need to order
	 *
	 * @param orderColumns
	 * @return List T list data of entity
	 */
	@SuppressWarnings("unchecked")
	private List<T> getAllDataWithOrderColumns(List<String> orderColumns) {

		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        Class<T> genericClass = getPersistentClass();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(genericClass);
        Root<T> root = criteriaQuery.from(genericClass);
        criteriaQuery.select(root);
        
        if (CollectionUtils.isNotEmpty(orderColumns)) {
            List<Order> orders = new ArrayList<>();
            orderColumns.stream().forEach(s -> orders.add(criteriaBuilder.asc(root.get(s))));
            criteriaQuery.orderBy(orders);
        }
        Query query = getEntityManager().createQuery(criteriaQuery);
        return query.getResultList();
	}

	/**
	 * Get all data without ordering
	 *
	 * @return List list data of entity
	 */
	public List<T> getAllData() {
		return getAllDataWithOrderColumns(Collections.emptyList());
	}

	/**
	 * Get all data with list column which need to order
	 *
	 * @param orderColumns
	 * @return List T list data of entity
	 */
	public List<T> getAllData(List<String> orderColumns) {
		return getAllDataWithOrderColumns(orderColumns);
	}

	/**
	 * get all data by list column name and list value follow (column name [0]
	 * will have value [0]) order by list order column
	 *
	 * @param columnNames
	 * @param values
	 * @param orderColumns
	 * @return List T
	 */
	public List<T> getAllDataByColumns(List<String> columnNames, List<Object> values, List<String> orderColumns) {
		if (CollectionUtils.isEmpty(columnNames)) {
			return getAllDataWithOrderColumns(orderColumns);
		}
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        Class<T> genericClass = getPersistentClass();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(genericClass);
        Root<T> root = criteriaQuery.from(genericClass);
        criteriaQuery.select(root);
        
        List<Predicate> where = new ArrayList<>(columnNames.size());
        
        for (int i = 0; i < columnNames.size(); i++) {
			where.add(criteriaBuilder.equal(root.get(columnNames.get(i)), values.get(i)));
		}
    	criteriaQuery.where(where.toArray(new Predicate[where.size()]));
        
        if (CollectionUtils.isNotEmpty(orderColumns)) {
            List<Order> orders = new ArrayList<>();
            orderColumns.stream().forEach(s -> orders.add(criteriaBuilder.asc(root.get(s))));
            criteriaQuery.orderBy(orders);
        }
        
        return getEntityManager().createQuery(criteriaQuery).getResultList();
	}

	public T findAndRefresh(Long id) {
		if(id == null) {
			return null;
		}
		T persistent = getEntityManager().find(getPersistentClass(), id);
		if (persistent == null) {
			return null;
		}

		// get fresh data of entity from db
		getEntityManager().refresh(persistent);

		return persistent;
	}
	
	public T find(Long id) {
		if(id == null) {
			return null;
		}
		return getEntityManager().find(getPersistentClass(), id);
	}

	public List<T> findByListIdsWithProperyName(List<Long> ids, String propertyName) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> cq = builder.createQuery(getPersistentClass());
		Root<T> fromEntity = cq.from(getPersistentClass());
		cq.select(fromEntity);
		List<Predicate> where = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(ids)) {
			where.add(fromEntity.get(propertyName).in(ids));
		}
		if (CollectionUtils.isNotEmpty(where)) {
			cq.where(where.toArray(new Predicate[where.size()]));
		}
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	public List<T> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(getPersistentClass());
        Root<T> fromEntity = cq.from(getPersistentClass());
        cq.select(fromEntity);
        return getEntityManager().createQuery(cq).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findByName(String name, String acceptLanguage) {
		if (name == null) {
			return Collections.emptyList();
		}
		
		Locale currentLocal = StringUtils.isNotBlank(acceptLanguage) ? LocaleUtils.toLocale(acceptLanguage) : Locale.ENGLISH;
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery cq = builder.createQuery(getPersistentClass());
		Root<T> c = cq.from(getPersistentClass());
		cq.select(c).where(builder.like(builder.trim(c.get("name")), "%" + name.toLowerCase(currentLocal).trim() + "%"));
		return getEntityManager().createQuery(cq).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findRange(int beginIndex, int endIndex) {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(getPersistentClass()));
		Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(endIndex - beginIndex);
		q.setFirstResult(beginIndex);
		return q.getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int count() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		Root<T> rt = cq.from(getPersistentClass());
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	/**
	 * check isExisted entity in DB, if greater than 1, DB is wrong if equal 1,
	 * entity is existed and otherwise
	 */
	public boolean isExisted(Long id) {
		if (id == null) {
			throw new LocalizedException("Id can not be null!");
		}
		int numberOfEntities = countById(id);
		if (numberOfEntities > 1) {
			throw new LocalizedException("Number of entities in Db with id:" + id + " = " + numberOfEntities);
		}
		return numberOfEntities == 1 ? true : false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int countById(Long id) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		Root<T> rt = cq.from(getPersistentClass());
		cq.where(builder.equal(rt.get("id"), id));
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	/**
	 * Clearing the entity manager empties its associated cache.
	 * The state held by the cache doesn't reflect what is in the
	 * database because of the queries, so you want to clear the cache to avoid
	 * the inconsistency
	 */
    public void clear() {
    	getEntityManager().clear();
    }
    
    public void flush() {
    	getEntityManager().flush();
    }
    
    @SuppressWarnings("rawtypes")
	protected EntityGraph getEntityGraph(String entityGraphName) {
		return getEntityManager().getEntityGraph(entityGraphName);
	}
    
    public void removeAndFlush(T entity) {
        remove(entity);
        getEntityManager().flush();
    }
    
    public void removeAllAndFlush() {
        Class<T> entityClass = getPersistentClass();
        String strQuery = "DELETE FROM " + entityClass.getSimpleName();
        Query query = getEntityManager().createQuery(strQuery);
        query.executeUpdate();
    }
    
}