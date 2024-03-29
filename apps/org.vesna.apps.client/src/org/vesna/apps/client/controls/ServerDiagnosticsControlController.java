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
package org.vesna.apps.client.controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.vesna.core.javafx.BaseController;


/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class ServerDiagnosticsControlController extends BaseController<ServerDiagnosticsControlModel>  {

    @FXML 
    private Label infoLabel;
    
    @FXML 
    private void handleActionGetInfo(ActionEvent event) {
        ServerDiagnosticsControlModel model = getModel();
        model.refreshServerInfo();
    }

    @Override
    protected void configureView(ServerDiagnosticsControlModel model) {
        infoLabel.textProperty().bind(model.serverInfoProperty());
    }

    @Override
    protected void refreshView() {
    }
    
}
