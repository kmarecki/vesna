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

import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.vesna.core.app.Core;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.entities.Repository;
import org.vesna.core.javafx.BaseModelImpl;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class EntitiesListModel<TEntity> extends BaseModelImpl {
    private final ListProperty<TEntity> entities = new SimpleListProperty<>(FXCollections.<TEntity>observableArrayList());

    public ObservableList getEntities() {
        return entities.get();
    }

    public void setEntities(ObservableList value) {
        entities.set(value);
    }

    public ListProperty entitiesProperty() {
        return entities;
    }
    
    private final ObjectProperty<TEntity> selectedEntity = new SimpleObjectProperty();

    public TEntity getSelectedEntity() {
        return selectedEntity.get();
    }

    public void setSelectedEntity(TEntity value) {
        selectedEntity.set(value);
    }

    public ObjectProperty selectedEntityProperty() {
        return selectedEntity;
    }
    
    protected Repository<TEntity> entitiesRepository;

    public Repository<TEntity> getEntitiesRepository() {
        return entitiesRepository;
    }


    @Override
    public void initialize() {
        loadEntities();
    }

    @Override
    public void refresh() {
        loadEntities();
    }
    
    public EntitiesEditModel createNewEntityEditModel() {
        EntitiesEditModel model = createRowEditModel(EntitiesEditModel.Mode.Add);
        TEntity entity = entitiesRepository.create();
        model.setEntity(entity);
        return model;
    }
     
    public EntitiesEditModel createSelectedEntityEditModel() {
        EntitiesEditModel model = createRowEditModel(EntitiesEditModel.Mode.Edit);
        TEntity entity = getSelectedEntity();
        model.setEntity(entity);
        return model;
    }
    
    public void deleteSelectedEntity() {
        TEntity entity = getSelectedEntity();
        entitiesRepository.delete(entity);
    }
     
    
    protected abstract EntitiesEditModel createRowEditModel(EntitiesEditModel.Mode mode);
    
    protected abstract String getRepositoryName();
    
    private void loadEntities() {
        String repositoryName = getRepositoryName();
        EntitiesService entitiesService = Core.getService(EntitiesService.class);
        entitiesRepository = entitiesService.getRepository(repositoryName);
        List<TEntity> dtos = entitiesRepository.getAll();
        
        getEntities().clear();
        for(TEntity dto : dtos) {
                getEntities().add(dto);
        }
    }

}
