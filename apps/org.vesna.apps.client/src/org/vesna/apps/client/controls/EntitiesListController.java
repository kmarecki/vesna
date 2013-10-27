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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.javafx.BaseModel;
import org.vesna.core.javafx.controls.ControlEx;


/**
 * 
 * @author Krzysztof Marecki
 */
public abstract class EntitiesListController<TModel extends EntitiesListModel>
    extends BaseController<TModel> {
    
    @FXML
    protected void handleActionAdd(ActionEvent event) {
        ControlEx control = createRowEditControl();
        EntitiesEditModel model = createRowEditModel();
        
        model.getEntity();
        showStage(control, model, "Add");
    }
    
    @FXML
    protected void handleActionEdit(ActionEvent event) {  
        ControlEx control = createRowEditControl();
        BaseModel model = createRowEditModel();
        showStage(control, model, "Edit");
    }
    
    @FXML
    protected void handleActionDelete(ActionEvent event) {
       
    }
    
    protected abstract EntitiesEditModel createRowEditModel();
    
    protected abstract ControlEx createRowEditControl();

}
