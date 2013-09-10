/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesna.core.javafx.controls;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 *
 * @author Administrator
 */
public class LogsList extends VBox {
    
    public LogsList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogsList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(new LogsListController());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
