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
package org.vesna.apps.server.controls;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.vesna.apps.server.AppModel;


/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManagementControlController {
    
    private DatabaseManegementControlModel model;
    
    @FXML
    ListView tablesList;
    @FXML
    TableView rowsTable;
    
    public void setModel(AppModel appModel) {
        model = new DatabaseManegementControlModel();
        
        tablesList.itemsProperty().bind(model.tablesProperty());
    }
    
}
