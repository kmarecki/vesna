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

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 *
 * @author Krzysztof Marecki
 */
public class HibernateControl extends VBox {
    
    private HibernateControlController controller;

    public HibernateControlController getController() {
        return controller;
    }

    public HibernateControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HibernateControl.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
            controller = fxmlLoader.getController();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
