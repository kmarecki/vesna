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
package org.vesna.apps.server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import org.apache.log4j.Logger;
import org.vesna.apps.server.controls.DatabaseManagementControl;
import org.vesna.apps.server.controls.DatabaseManagementControlModel;
import org.vesna.apps.server.controls.HibernateControl;
import org.vesna.apps.server.controls.HibernateControlModel;
import org.vesna.apps.server.controls.LogsControl;
import org.vesna.core.server.derby.DerbyService;

/**
 *
 * @author Krzysztof Marecki
 */
public class MainFormController {
    private static final Logger logger = Logger.getLogger(MainFormController.class);
    private AppModel appModel;
    
    @FXML
    LogsControl logsControl;
    @FXML
    DatabaseManagementControl databaseControl;
    @FXML
    HibernateControl hibernateControl;
    
    @FXML
    private void handleMenuItemExit(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void handleTabDatabase(Event event) {
         setDatabaseManagementControlModel();
    }
    
    @FXML
    private void handleTabHibernate(Event event) {
        setHibernateControlModel();
    }
    
    public void setModel(AppModel model) {
        appModel = model;
        
        setLogControlModel();
    }
    
    private void setLogControlModel() {
       logsControl.getController().setModel(appModel);
    }
    
    private void setDatabaseManagementControlModel() {
        DerbyService derbyService = appModel.getServices().get(DerbyService.class);
        DatabaseManagementControlModel model = new DatabaseManagementControlModel(derbyService);
        databaseControl.getController().setModel(model);
    }
    
    private void setHibernateControlModel() {
        
        HibernateControlModel model = new HibernateControlModel(appModel.getHibernateMappingsJar());
        hibernateControl.getController().setModel(model);
    }
}
