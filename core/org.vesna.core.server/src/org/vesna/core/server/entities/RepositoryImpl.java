/*
 * Copyright 2013 Krzysztof Marecki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vesna.core.server.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.vesna.core.app.Core;
import org.vesna.core.entities.Repository;
import org.vesna.core.lang.ReflectionHelper;
import org.vesna.core.logging.LoggerHelper;
import org.vesna.core.server.hibernate.HibernateService;

/**
 *
 * @author Krzysztof Marecki
 */
public class RepositoryImpl<TEntity> implements Repository<TEntity> {

    protected Logger logger = Logger.getLogger(this.getClass());
            
    @Override
    public TEntity create() {
       TEntity entity = null;
        try {
            entity = (TEntity)getTEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
           LoggerHelper.logException(logger, ex);
        }
       return entity;
    }
    
    @Override
    public TEntity insert(TEntity entity) {
        Session session = getSession();
        session.save(entity);
        session.close();
        return entity;
    }

    @Override
    public TEntity update(TEntity entity) {
        Session session = getSession();
        session.update(entity);
        session.close();
        return entity;
    }

    @Override
    public void delete(TEntity entity) {
        Session session = getSession();
        session.delete(entity);
        session.close();
    }

    @Override
    public List<TEntity> getAll() {
        Session session = getSession();
        Criteria crit = session.createCriteria(getTEntityClass());
        
        List<TEntity> result = Collections.checkedList(crit.list(), getTEntityClass());
        return result;
    }

    @Override
    public TEntity getSingle(Object id) {
        Session session = getSession();
        TEntity entity = (TEntity)session.get(getTEntityClass(), (Serializable)id);
        return entity;
    }
    
    private Class getTEntityClass() {
        Class entityClass = ReflectionHelper.getTemplateTypeParameter(this.getClass());
        return entityClass;
    }
    
    private Session getSession() {
        HibernateService hibernateService = Core.getService(HibernateService.class);
        Session session = hibernateService.getSessionFactory().openSession();
        return session;
    }
}
