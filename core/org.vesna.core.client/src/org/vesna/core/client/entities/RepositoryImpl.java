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
package org.vesna.core.client.entities;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.vesna.core.app.Core;
import org.vesna.core.client.services.ConnectionsService;
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.client.services.ServiceCallReturn;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.entities.Repository;
import org.vesna.core.lang.Func1;
import org.vesna.core.lang.GsonHelper;
import org.vesna.core.lang.ReflectionHelper;
import org.vesna.core.logging.LoggerHelper;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class RepositoryImpl<TEntity> implements Repository<TEntity> {
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
    public TEntity insert(final TEntity entity) {
        return execMasterService(new Func1<MasterServiceImpl, TEntity>() {
            @Override
            public TEntity apply(MasterServiceImpl impl) {
                String entityJson = GsonHelper.toJson(entity);
                ServiceCallReturn ret = impl.execRepositoryMethod(
                        getRepositoryName(), "insert", Arrays.asList(new String[]{entityJson}));
                if (ret.isSuccess()) {
                    TEntity dtos = (TEntity) GsonHelper.fromJson(getTEntityTypeToken(), ret.getReturnValue());
                    return dtos;
                }
                String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
                throw new RuntimeException(msg);
            }
        });
    }

    @Override
    public TEntity update(final TEntity entity) {
        return execMasterService(new Func1<MasterServiceImpl, TEntity>() {
            @Override
            public TEntity apply(MasterServiceImpl impl) {
                String entityJson = GsonHelper.toJson(entity);
                ServiceCallReturn ret = impl.execRepositoryMethod(
                        getRepositoryName(), "update", Arrays.asList(new String[]{entityJson}));
                if (ret.isSuccess()) {
                    TEntity dtos = (TEntity) GsonHelper.fromJson(getTEntityTypeToken(), ret.getReturnValue());
                    return dtos;
                }
                String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
                throw new RuntimeException(msg);
            }
        });
    }

    @Override
    public void delete(final TEntity entity) {
        execMasterService(new Func1<MasterServiceImpl, Boolean>() {
            @Override
            public Boolean apply(MasterServiceImpl impl) {
                String entityJson = GsonHelper.toJson(entity);
                ServiceCallReturn ret = impl.execRepositoryMethod(
                        getRepositoryName(), "delete", Arrays.asList(new String[]{entityJson}));
                if (ret.isSuccess()) {
                    return true;
                }
                String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
                throw new RuntimeException(msg);
            }
        });
    }

    @Override
    public List<TEntity> getAll() {
        return execGetList("getAll", null);
    }

    @Override
    public TEntity getSingle(final Object id) {
         return execMasterService(new Func1<MasterServiceImpl, TEntity>() {
            @Override
            public TEntity apply(MasterServiceImpl impl) {
                String idJson = GsonHelper.toJson(id);
                ServiceCallReturn ret = impl.execRepositoryMethod(
                        getRepositoryName(), "getSingle", Arrays.asList(new String[]{idJson}));
                if (ret.isSuccess()) {
                    TEntity dtos = (TEntity) GsonHelper.fromJson(getTEntityTypeToken(), ret.getReturnValue());
                    return dtos;
                }
                String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
                throw new RuntimeException(msg);
            }
        });
    }
    
    protected abstract TypeToken getTEntityTypeToken();
    
    protected abstract TypeToken getListTEntityTypeToken();
    
    protected <TResult> TResult execMasterService(Func1<MasterServiceImpl, TResult> method) {
        ConnectionsService connectionService = Core.getService(ConnectionsService.class);
        MasterServiceImpl impl = connectionService.getMasterServiceImpl();
        TResult result = method.apply(impl);
        return result;
    }
    
    protected List<TEntity> execGetList(final String methodName, final Object[] parameters) {
         return execMasterService(new Func1<MasterServiceImpl, List<TEntity>>() {
            @Override
            public List<TEntity> apply(MasterServiceImpl impl) {
                List<String> jsonParameters = new ArrayList();
                if (parameters != null) {
                    for(Object parameter : parameters) {
                        String jsonParameter = GsonHelper.toJson(parameter);
                        jsonParameters.add(jsonParameter);
                    }
                }
                ServiceCallReturn ret = impl.execRepositoryMethod(
                        getRepositoryName(), methodName, jsonParameters);
                if (ret.isSuccess()) {
                    List<TEntity> dtos = (List<TEntity>) GsonHelper.fromJson(getListTEntityTypeToken(), ret.getReturnValue());
                    return dtos;
                }
                String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
                throw new RuntimeException(msg);
            }
        });
    }
    
    protected Class getTEntityClass() {
        Class entityClass = ReflectionHelper.getTemplateTypeParameter(this.getClass());
        return entityClass;
    }
    
    protected String getRepositoryName() {
        EntitiesService entitiesService = Core.getService(EntitiesService.class);
        String name = entitiesService.getRepositoryName(this);
        return name;
    }
}
