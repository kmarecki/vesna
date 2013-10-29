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
import org.vesna.core.app.Core;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.javafx.navigation.NavigationService;


/**
 * 
 * @author Krzysztof Marecki
 */
public abstract class EntitiesEditController<TModel extends EntitiesEditModel>
    extends BaseController<TModel> {

    @FXML
    protected void handleActionCancel(ActionEvent event) {
        NavigationService navigationService = Core.getServices().get(NavigationService.class);
        navigationService.closeCurrentScreen();
    }
    
    @FXML
    protected void handleActionApply(ActionEvent event) {  
        NavigationService navigationService = Core.getServices().get(NavigationService.class);
        navigationService.closeCurrentScreen();
    }

    @Override
    protected void configureView(TModel model) {
      
    }
}
