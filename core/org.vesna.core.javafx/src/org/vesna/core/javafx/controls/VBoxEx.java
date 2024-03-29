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

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.javafx.BaseModel;
import org.vesna.core.javafx.fxml.FXMLCombiner;
import org.vesna.core.javafx.fxml.FXMLLoaderHelper;


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
    

    public VBoxEx() {
        FXMLLoader fxmlLoader = FXMLLoaderHelper.loadRoot(this, getCombiner());
        controller = fxmlLoader.getController();
    }
    
    protected FXMLCombiner getCombiner() {
        return new FXMLCombiner();
    }
}
