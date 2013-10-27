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
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.client.services.MasterServiceImplService;
import org.vesna.core.client.services.ServiceCallReturn;
import org.vesna.core.entities.Repository;
import org.vesna.core.lang.GsonHelper;
import org.vesna.core.lang.ReflectionHelper;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class RepositoryImpl<TEntity> implements Repository<TEntity> {

    @Override
    public TEntity insert(TEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TEntity update(TEntity entity) {
        MasterServiceImplService service = new MasterServiceImplService();
        MasterServiceImpl impl = service.getMasterServiceImplPort();
        String entityJson = GsonHelper.toJson(entity);
        ServiceCallReturn ret = impl.execRepositoryMethod("Persons", "update", Arrays.asList(new String[] { entityJson }));
        if (ret.isSuccess()) {
            TEntity dtos = (TEntity)GsonHelper.fromJson(getTEntityTypeToken(), ret.getReturnValue());
            return dtos;
        }
        String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
        throw new RuntimeException(msg);
    }

    @Override
    public void delete(TEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TEntity> getAll() {
        MasterServiceImplService service = new MasterServiceImplService();
        MasterServiceImpl impl = service.getMasterServiceImplPort();
        ServiceCallReturn ret = impl.execRepositoryMethod("Persons", "getAll", null);
        if (ret.isSuccess()) {
            List<TEntity> dtos = (List<TEntity>)GsonHelper.fromJson(getListTEntityTypeToken(), ret.getReturnValue());
            return dtos;
        }
        String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
        throw new RuntimeException(msg);
    }

    @Override
    public TEntity getSingle(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected abstract TypeToken getTEntityTypeToken();
    
    protected abstract TypeToken getListTEntityTypeToken();
}
