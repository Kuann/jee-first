/*
 * Copyright(c)2016 by AXON IVY AG, CH-6000 Lucerne. http://www.axonivy.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * AXON IVY AG. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with AXON IVY AG.
 */
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
