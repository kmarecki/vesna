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

import org.vesna.apps.client.controls.ServerDiagnosticsControl;
import org.vesna.apps.client.controls.ServerDiagnosticsControlModel;
import org.vesna.core.javafx.BaseController;


/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class MainFormController extends BaseController<ClientAppModel>  {
   
    @FXML
    protected void handleMenuItemExit(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML 
    protected void handleMenuServerDiagnostics(ActionEvent event) {
//        Stage stage = new Stage();
//        ServerDiagnosticsControl control = new  ServerDiagnosticsControl();
//        stage.setScene(new Scene(control));
//        stage.setTitle("Server diagnostics");
//        stage.initModality(Modality.APPLICATION_MODAL);
//        
//        ServerDiagnosticsControlModel controlModel = new ServerDiagnosticsControlModel();
//        control.getController().setModel(controlModel);
//        stage.show();
        ServerDiagnosticsControl control = new  ServerDiagnosticsControl();
        ServerDiagnosticsControlModel controlModel = new ServerDiagnosticsControlModel();
        showStage(control, controlModel, "Server diagnostics");
    }
}
