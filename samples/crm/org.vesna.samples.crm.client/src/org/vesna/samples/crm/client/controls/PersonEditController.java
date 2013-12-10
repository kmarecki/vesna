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
import javafx.scene.control.TextField;
import org.vesna.apps.client.controls.EntitiesEditController;

/**
 *
 * @author Krzysztof Marecki
 */
public class PersonEditController<TModel extends PersonEditModel> 
    extends EntitiesEditController<TModel> {
   
    @FXML
    TextField firstNameText;
    @FXML
    TextField lastNameText;
    @FXML
    TextField phoneText;
    @FXML
    TextField privatePhoneText;
    @FXML
    TextField emailText;

    @Override
    protected void configureView(TModel model) {
        super.configureView(model);
        
        firstNameText.textProperty().bindBidirectional(model.firstNameProperty());
        lastNameText.textProperty().bindBidirectional(model.lastNameProperty());
        phoneText.textProperty().bindBidirectional(model.phoneProperty());
        privatePhoneText.textProperty().bindBidirectional(model.privatePhoneProperty());
        emailText.textProperty().bindBidirectional(model.emailProperty());
    }

    @Override
    protected void refreshView() {
        super.refreshView(); 
        
        firstNameText.requestFocus();
    }
   
}
