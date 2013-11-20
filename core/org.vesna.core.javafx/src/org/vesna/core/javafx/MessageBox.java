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
package org.vesna.core.javafx;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Krzysztof Marecki
 */
public class MessageBox {
    
    public enum DialogResult {
        OK,
        Cancel,
        None
    }
    
    public static DialogResult show(String message, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(MessageBox.class.getResource("MessageBox.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((Parent)loader.load()));
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        
        MessageBox dialog = (MessageBox)loader.getController();
        dialog.stage = stage;
        dialog.messageLabel.setText(message);
        
        stage.showAndWait();
        
        return dialog.dialogResult;
    }
    
    private Stage stage;
    private DialogResult dialogResult = DialogResult.None;
    
    @FXML 
    private Label messageLabel;
    
    @FXML
    private void handleActionOK(ActionEvent event) {
        dialogResult = DialogResult.OK;
        stage.close();
    }
    
    @FXML
    private void handleActionCancel(ActionEvent event) {
        dialogResult = DialogResult.Cancel;
        stage.close();
    }
}
