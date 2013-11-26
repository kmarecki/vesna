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
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.log4j.Logger;
import org.vesna.apps.client.App;
import org.vesna.core.entities.Repository;
import org.vesna.core.javafx.BaseModelImpl;
import org.vesna.core.logging.LoggerHelper;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class EntitiesEditModel<TEntity> extends BaseModelImpl {
   protected static final Logger logger = Logger.getLogger(EntitiesEditModel.class);
    
    public enum Mode {
        Add,
        Copy,
        Edit,
        View
    }
    
    private Repository entitiesRepository;
    
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
    private final ReadOnlyStringWrapper applyButtonText = new ReadOnlyStringWrapper();

    public String getApplyButtonText() {
        return applyButtonText.get();
    }

    public ReadOnlyStringProperty applyButtonTextProperty() {
        return applyButtonText.getReadOnlyProperty();
    }
    
    
    
    private Mode mode;
    
    public Mode getMode() {
        return mode;
    }
    
    public EntitiesEditModel(Repository entitiesRepository, Mode mode) {
        this.entitiesRepository = entitiesRepository;
        this.mode = mode;
    }
    
    @Override
    public void initialize() {
        super.initialize(); 
        
        initializeFromMode();
        fromEntity(getEntity());
    }
    
    public Boolean saveEntity() {
        try {
        toEntity(getEntity());
        switch(mode) {
            case Add : {
                entitiesRepository.insert(getEntity());
                break;
            }
            case Edit : {
                entitiesRepository.update(getEntity());
                break;
            }
        }
        } catch (Throwable ex) {
            LoggerHelper.logException(logger, ex);
            return false;
        }
        
        return true;
    }
    
    protected abstract void fromEntity(TEntity entity);
    
    protected abstract void toEntity(TEntity entity);
    
    protected void initializeFromMode() {
         switch(mode) {
            case Add : {
                applyButtonText.set("Add");
                break;
            }
            case Edit : {
                applyButtonText.set("Edit");
                break;
            }
        }
    }
}
