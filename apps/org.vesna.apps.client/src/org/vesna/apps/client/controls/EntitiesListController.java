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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.javafx.controls.ControlEx;


/**
 * 
 * @author Krzysztof Marecki
 */
public abstract class EntitiesListController<TModel extends EntitiesListModel>
    extends BaseController<TModel> {
    
    @FXML
    TableView entitiesTable;
    
    @FXML
    protected void handleActionAdd(ActionEvent event) {
        TModel model = getModel();
        ControlEx control = createRowEditControl();
        EntitiesEditModel editModel = model.createNewEntityEditModel();
    }
    
    @FXML
    protected void handleActionEdit(ActionEvent event) { 
        TModel model = getModel();
        ControlEx control = createRowEditControl();
        EntitiesEditModel editModel = model.createSelectedEntityEditModel();
        showScreenInCurrentWindow(control, editModel, "Edit");
    }
    
    @FXML
    protected void handleActionDelete(ActionEvent event) {
       
    }

    @Override
    protected void configureView(final TModel model) {
        entitiesTable.itemsProperty().bind(model.entitiesProperty());
        entitiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(
                ObservableValue ov, 
                Object oldValue, 
                Object newValue) {
                model.setSelectedEntity(newValue);
            }	
        });
    }
    
    protected abstract ControlEx createRowEditControl();
}
