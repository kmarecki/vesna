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
package org.vesna.samples.crm.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.vesna.apps.client.MainFormController;
import org.vesna.samples.crm.client.controls.PersonsList;
import org.vesna.samples.crm.client.controls.PersonsListModel;

/**
 *
 * @author Krzysztof Marecki
 */
public class CrmMainFormController extends MainFormController {
        
    @FXML
    protected void handleMenuItemPersons(ActionEvent event) {
        PersonsList list = new PersonsList();
        PersonsListModel model = new PersonsListModel();
        list.getController().setModel(model);
        
        addTabPage(list, "Persons");
    }
}
