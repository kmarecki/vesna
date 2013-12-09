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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.vesna.apps.client.controls.EntitiesListController;
import org.vesna.core.javafx.controls.ControlEx;
import org.vesna.samples.crm.dto.Employee;

/**
 *
 * @author Krzysztof Marecki
 */
public class EmployeeListController
    extends EntitiesListController<EmployeeListModel>{
    
    @FXML
    TableColumn firstNameColumn;
    @FXML
    TableColumn lastNameColumn;
    @FXML
    TableColumn titleColumn;
    @FXML
    TableColumn phoneColumn;
    @FXML
    TableColumn emailColumn;
    
    
    @Override
    protected void configureView(EmployeeListModel model) {
        super.configureView(model);

        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("firstName"));
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("lastName"));
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("title"));
        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("phone"));
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("email"));
    }

    @Override
    protected ControlEx createRowEditControl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
