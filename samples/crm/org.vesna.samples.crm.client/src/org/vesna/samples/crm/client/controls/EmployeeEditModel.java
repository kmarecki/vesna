/*
 * Copyright 2013 Krzysztof Marecki
 *
 * Licensed under te Apache License, Version 2.0 (the "License");
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
package org.vesna.samples.crm.client.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.vesna.apps.client.controls.EntitiesListModel;
import org.vesna.core.entities.Repository;
import org.vesna.samples.crm.dto.Employee;

/**
 *
 * @author Krzysztof Marecki
 */
public class EmployeeEditModel 
    extends PersonBaseEditModel<Employee> {
    
    private final StringProperty titile = new SimpleStringProperty();

    public String getTitile() {
        return titile.get();
    }

    public void setTitile(String value) {
        titile.set(value);
    }

    public StringProperty titileProperty() {
        return titile;
    }

    
    public EmployeeEditModel(EntitiesListModel parentModel, Repository entitiesRepository, Mode mode) {
        super(parentModel, entitiesRepository, mode);
    }

    @Override
    protected void fromEntity(Employee entity) {
        super.fromEntity(entity); 
        setTitile(entity.getTitle());
    }

    
    @Override
    protected void toEntity(Employee entity) {
        super.toEntity(entity); 
        entity.setTitle(getTitile());
    }
    
}
