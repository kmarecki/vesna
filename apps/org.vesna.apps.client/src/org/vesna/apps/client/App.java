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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.vesna.core.javafx.BaseApp;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class App extends BaseApp {
    private static final Logger logger = Logger.getLogger(App.class);
    
    @Override
    public void start(Stage stage) throws Exception {
        ClientAppModel model = (ClientAppModel) createAppModel();

         FXMLLoader fxmlLoader = new FXMLLoader();
         fxmlLoader.setLocation(App.class.getResource("MainForm.fxml"));
         Parent root = (Parent)fxmlLoader.load();
         Scene scene = new Scene(root);
//         scene.getStylesheets().add("resources/css/styles.css");
         MainFormController controller = (MainFormController)fxmlLoader.getController();
         controller.setModel(model);

         stage.setTitle(model.getApplicationTitle());
         stage.setScene(scene);
         stage.sizeToScene();
         stage.show();
    }
}
