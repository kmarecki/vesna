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
package org.vesna.apps.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.log4j.Logger;

import org.vesna.apps.client.controls.ServerDiagnosticsControl;
import org.vesna.apps.client.controls.ServerDiagnosticsControlModel;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.logging.LoggerHelper;


/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class MainFormController extends BaseController<ClientAppModel>  {
    private static final Logger logger = Logger.getLogger(MainFormController.class);
    
    @FXML
    protected TabPane tabPane;
    
    @FXML
    protected void handleMenuItemExit(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML 
    protected void handleMenuServerDiagnostics(ActionEvent event) {
        ServerDiagnosticsControl control = new  ServerDiagnosticsControl();
        ServerDiagnosticsControlModel controlModel = new ServerDiagnosticsControlModel();
        showStage(control, controlModel, "Server diagnostics");
    }
    
    protected void addTabPage(Node content, String title) {
        try {
            Tab tab = new Tab(title);
            tab.setContent(content);
   
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().selectLast();
        } catch (IllegalArgumentException ex) {
            LoggerHelper.logException(logger, ex);
        }
    }

    @Override
    protected void configureView(ClientAppModel model) {
    }
}
