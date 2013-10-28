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
package org.vesna.apps.client.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.vesna.core.javafx.BaseModel;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class EntitiesEditModel<TEntity> extends BaseModel {
    private final ObjectProperty<TEntity> entity = new SimpleObjectProperty();

    public TEntity getEntity() {
        return entity.get();
    }

    public void setEntity(TEntity value) {
        entity.set(value);
    }

    public ObjectProperty entityProperty() {
        return entity;
    }

    @Override
    public void initialize() {
        super.initialize(); 
        
        fromEntity(getEntity());
    }
    
    protected abstract void fromEntity(TEntity entity);
    
    protected abstract void toEntity(TEntity entity);
}
