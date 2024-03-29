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

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.vesna.core.app.Core;
import org.vesna.core.javafx.controls.ControlEx;
import org.vesna.core.javafx.navigation.NavigationService;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class BaseController<TModel extends BaseModel> {
   
    private TModel model;

    protected TModel getModel() {
        return model;
    }

    public void setModelAndInitialize(TModel model) {
        this.model = model;
        configureView(this.model);
        this.model.initialize();
        this.model.refresh();
    }
    
    public void refreshModel() {
        if (this.model != null) {
            model.refresh();
        }
    }
    
    public final void refresh() {
        refreshView();
    }
     
    protected abstract void configureView(TModel model);
    
    protected abstract void refreshView();
    
    protected <TNewModel extends BaseModel> Stage showStage(
            ControlEx control,
            TNewModel model,
            String title) {
        Stage stage = new Stage();
        stage.setScene(new Scene((Parent)control));
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        control.getController().setModelAndInitialize(model);
        stage.show();
        
        return stage;
    }
    
    protected <TNewModel extends BaseModel> void showScreenInCurrentWindow(
            ControlEx control,
            TNewModel model) {
        showScreenInCurrentWindow(control, model, null);
    }
    
    protected <TNewModel extends BaseModel> void showScreenInCurrentWindow(
            ControlEx control,
            TNewModel model,
            String title) {
        NavigationService navigationServices = Core.getService(NavigationService.class);
        control.getController().setModelAndInitialize(model);
        if (title == null) {
            title = model.getModelName();
        }
        navigationServices.openScreenInCurrentWindow((Node)control, title);
    }
    
    protected <TNewModel extends BaseModel> void showScreenInNewWindow(
            ControlEx control,
            TNewModel model) {
        showScreenInNewWindow(control, model, model.getModelName());
    }
    
    protected <TNewModel extends BaseModel> void showScreenInNewWindow(
            ControlEx control,
            TNewModel model,
            String title) {
        NavigationService navigationServices = Core.getService(NavigationService.class);
        control.getController().setModelAndInitialize(model);
        navigationServices.openScreenInNewWindow((Node)control, title);
    }
}
