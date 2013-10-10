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

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.vesna.core.javafx.BaseModel;

/**
 *
 * @author Krzysztof Marecki
 */
public class EntitiesListModel<TEntity> extends BaseModel {
    private final ListProperty<TEntity> entities = new SimpleListProperty<>(FXCollections.<TEntity>observableArrayList());

    public ObservableList getEntities() {
        return entities.get();
    }

    public void setLogEntries(ObservableList value) {
        entities.set(value);
    }

    public ListProperty entitiesProperty() {
        return entities;
    }
}
