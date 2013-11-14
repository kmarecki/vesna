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
import org.vesna.apps.client.controls.EntitiesEditModel;
import static org.vesna.apps.client.controls.EntitiesEditModel.Mode.Add;
import static org.vesna.apps.client.controls.EntitiesEditModel.Mode.Edit;
import org.vesna.core.entities.Repository;
import org.vesna.samples.crm.dto.Person;

/**
 *
 * @author Krzysztof Marecki
 */
public class PersonEditModel extends EntitiesEditModel<Person> {
    private final StringProperty firstName = new SimpleStringProperty();

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String value) {
        firstName.set(value);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    private final StringProperty lastName = new SimpleStringProperty();

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String value) {
        lastName.set(value);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public PersonEditModel(Repository entitiesRepository, Mode mode) {
        super(entitiesRepository, mode);
        switch(mode) {
            case Add : {
                setModelName("New person");
                break;
            } 
            case Edit : {
                setModelName("Edit person");
            }
        }
    }
    
    @Override
    protected void fromEntity(Person entity) {
        setFirstName(entity.getFirstName());
        setLastName(entity.getLastName());
    }

    @Override
    protected void toEntity(Person entity) {
        entity.setFirstName(getFirstName());
        entity.setLastName(entity.getLastName());
    }
    
    

    
    
    
}
