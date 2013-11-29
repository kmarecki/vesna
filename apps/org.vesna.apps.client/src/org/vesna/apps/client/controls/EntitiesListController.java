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
import javafx.scene.layout.VBox;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.javafx.MessageBox;
import org.vesna.core.javafx.controls.ControlEx;
import org.vesna.core.logging.LoggerHelper;


/**
 * 
 * @author Krzysztof Marecki
 */
public abstract class EntitiesListController<TModel extends EntitiesListModel>
    extends BaseController<TModel> {
    
    protected static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EntitiesListController.class);
    
    @FXML
    protected TableView entitiesTable;
    @FXML
    protected VBox buttonsVBox;
    
    @FXML
    protected void handleActionAdd(ActionEvent event) {
        try {
            TModel model = getModel();
            ControlEx control = createRowEditControl();
            EntitiesEditModel editModel = model.createNewEntityEditModel();
            showScreenInCurrentWindow(control, editModel);
        } catch(Throwable ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
    
    @FXML
    protected void handleActionEdit(ActionEvent event) { 
        try {
            TModel model = getModel();
            ControlEx control = createRowEditControl();
            EntitiesEditModel editModel = model.createSelectedEntityEditModel();
            showScreenInCurrentWindow(control, editModel);
        } catch(Throwable ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
    
    @FXML
    protected void handleActionDelete(ActionEvent event) {
        try {
            TModel model = getModel();
            if (MessageBox.show("Do you want to delete selected object?", "Warning") == MessageBox.DialogResult.OK) {
                model.deleteSelectedEntity();
                model.refresh();
                refresh();
            }
        } catch(Throwable ex) {
            LoggerHelper.logException(logger, ex);
        }
    }

    @Override
    protected void configureView(final TModel model) {
        buttonsVBox.getStyleClass().add("vbox");
        entitiesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        entitiesTable.itemsProperty().bind(model.entitiesProperty());
        entitiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                model.setSelectedEntity(newValue);
            }	
        });
        model.selectedEntityProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                entitiesTable.getSelectionModel().select(model.getSelectedEntity());
            }
        });
    }

    @Override
    public void refreshView() {
        entitiesTable.requestFocus();
    }
    
    protected abstract ControlEx createRowEditControl();
}
