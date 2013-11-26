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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class BaseApp extends Application {
    
    private BaseAppModel model;
    
    @Override
    public void start(Stage stage) throws Exception {
        model = createAppModel();
        configureServices();
    }
    
    protected abstract BaseAppModel createAppModel();
    
    protected BaseAppModel getAppModel() {
        return model;
    }
    
    protected abstract void configureServices();
    
    protected abstract void configureRootScene(Scene scene);
    
}
