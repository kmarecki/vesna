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
package org.vesna.core.javafx.controls;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.javafx.BaseModel;
import org.vesna.core.lang.StringHelper;
import org.vesna.core.net.ClasspathURLHandler;

/**
 *
 * @author Krzysztof Marecki
 */
public class VBoxEx<TModel extends BaseModel, 
                    TController extends BaseController<TModel>>  
             extends VBox implements ControlEx<TModel, TController> {
     private TController controller;

    @Override
    public TController getController() {
        return controller;
    }
    

    public VBoxEx(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(this);
        if (!StringHelper.isNullOrEmpty(fxml)) {
            URL url;
            try {
                url = new URL(null, "classpath:"+fxml, new ClasspathURLHandler(Thread.currentThread().getContextClassLoader()));
                fxmlLoader.setLocation(url);
            } catch (MalformedURLException ex) {
               
            }
        }
        try {
            fxmlLoader.load();
            controller = fxmlLoader.getController();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
