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
import org.vesna.apps.client.controls.EntitiesListModel;
import org.vesna.core.entities.Repository;
import org.vesna.samples.crm.dto.Person;

/**
 *
 * @author Krzysztof Marecki
 */
public class PersonBaseEditModel<TEntity extends Person> 
    extends EntitiesEditModel<TEntity> {
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
    
    private final StringProperty phone = new SimpleStringProperty();

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String value) {
        phone.set(value);
    }

    public StringProperty phoneProperty() {
        return phone;
    }
    private final StringProperty privatePhone = new SimpleStringProperty();

    public String getPrivatePhone() {
        return privatePhone.get();
    }

    public void setPrivatePhone(String value) {
        privatePhone.set(value);
    }

    public StringProperty privatePhoneProperty() {
        return privatePhone;
    }
    
    private final StringProperty email = new SimpleStringProperty();

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
    

    public PersonBaseEditModel(EntitiesListModel parentModel, Repository entitiesRepository, EntitiesEditModel.Mode mode) {
        super(parentModel, entitiesRepository, mode);
    }
    
    @Override
    protected void fromEntity(TEntity entity) {
        setFirstName(entity.getFirstName());
        setLastName(entity.getLastName());
        setPhone(entity.getPhone());
        setPrivatePhone(entity.getPrivatePhone());
        setEmail(entity.getEmail());
    }

    @Override
    protected void toEntity(TEntity entity) {
        entity.setFirstName(getFirstName());
        entity.setLastName(getLastName());
        entity.setPhone(getPhone());
        entity.setPrivatePhone(getPrivatePhone());
        entity.setEmail(getEmail());
    }
    
    

    
    
    
}
