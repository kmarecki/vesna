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

import org.vesna.core.javafx.controls.LogsListController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.vesna.apps.server.controls.LogsControl;
import org.vesna.core.server.derby.DerbyServer;

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
    private void handleMenuItemExit(ActionEvent event) {
        Platform.exit();
    }
    
    public void setModel(AppModel model) {
        appModel = model;
        
        logsControl.getController().setModel(model);
    }
}
