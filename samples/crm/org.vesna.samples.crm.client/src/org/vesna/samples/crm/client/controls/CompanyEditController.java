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

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.vesna.apps.client.controls.EntitiesEditController;
import org.vesna.core.app.Core;
import org.vesna.core.javafx.navigation.NavigationService;
import org.vesna.core.lang.Func;
import org.vesna.samples.crm.dto.Company;

/**
 *
 * @author Krzysztof Marecki
 */
public class CompanyEditController
    extends EntitiesEditController<CompanyEditModel> {
    
    @FXML
    TextField shortNameText;
    @FXML
    TextField longNameText;
    @FXML
    TextField homepageUrlText;
    @FXML
    TextArea commentsText;
    @FXML
    EmployeeList employeesList;

    @Override
    protected void configureView(final CompanyEditModel model) {
        super.configureView(model); 
        
        shortNameText.textProperty().bindBidirectional(model.shortNameProperty());
        longNameText.textProperty().bindBidirectional(model.longNameProperty());
        homepageUrlText.textProperty().bindBidirectional(model.homepageUrlProperty());
        commentsText.textProperty().bindBidirectional(model.commentsProperty());
        
        EmployeeListModel employeesModel = new EmployeeListModel();
        employeesModel.setParentCompany(model.getEntity());
        employeesModel.refreshParentCompany(new Func<Company>() {
            @Override
            public Company apply() {
                model.saveEntity();
                model.refresh();
                NavigationService navigationService = Core.getService(NavigationService.class);
                navigationService.updateCurrentScreenTitle(model.getModelName());
                Company company = model.getEntity();
                return company;
            }
        });
        employeesList.getController().setModelAndInitialize(employeesModel);
    }

    @Override
    protected void refreshView() {
        super.refreshView(); 
        
        shortNameText.requestFocus();
    }

    @Override
    public void refreshModel() {
        super.refreshModel(); 
        
        employeesList.getController().refreshModel();
    }
    
    
    
}
