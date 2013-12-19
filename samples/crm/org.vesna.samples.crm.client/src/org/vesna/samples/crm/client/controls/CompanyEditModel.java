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
import org.vesna.apps.client.controls.EntitiesListModel;
import org.vesna.core.entities.Repository;
import org.vesna.samples.crm.dto.Company;

/**
 *
 * @author Krzysztof Marecki
 */
public class CompanyEditModel extends EntitiesEditModel<Company> {
    private final StringProperty shortName = new SimpleStringProperty();

    public String getShortName() {
        return shortName.get();
    }

    public void setShortName(String value) {
        shortName.set(value);
    }

    public StringProperty shortNameProperty() {
        return shortName;
    }
    private final StringProperty longName = new SimpleStringProperty();

    public String getLongName() {
        return longName.get();
    }

    public void setLongName(String value) {
        longName.set(value);
    }

    public StringProperty longNameProperty() {
        return longName;
    }
    private final StringProperty homepageUrl = new SimpleStringProperty();

    public String getHomepageUrl() {
        return homepageUrl.get();
    }

    public void setHomepageUrl(String value) {
        homepageUrl.set(value);
    }

    public StringProperty homepageUrlProperty() {
        return homepageUrl;
    }
    private final StringProperty comments = new SimpleStringProperty();

    public String getComments() {
        return comments.get();
    }

    public void setComments(String value) {
        comments.set(value);
    }

    public StringProperty commentsProperty() {
        return comments;
    }
    
    

    public CompanyEditModel(EntitiesListModel parentModel, Repository entitiesRepository, Mode mode) {
        super(parentModel, entitiesRepository, mode);
    }
    
    @Override
    protected void fromEntity(Company entity) {
        setShortName(entity.getShortName());
        setLongName(entity.getLongName());
        setHomepageUrl(entity.getHomepageUrl());
        setComments(entity.getComments());
    }

    @Override
    protected void toEntity(Company entity) {
       entity.setShortName(getShortName());
       entity.setLongName(getLongName());
       entity.setHomepageUrl(getHomepageUrl());
       entity.setComments(getComments());
    }
    
}
