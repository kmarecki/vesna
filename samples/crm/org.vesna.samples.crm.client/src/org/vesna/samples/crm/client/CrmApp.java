/*
 * Copyright 2013 Krzysztof Marecki
 *
 * Licensed under te Apache License, Version 2.0 (the "License");
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
package org.vesna.samples.crm.client;

import static javafx.application.Application.launch;
import org.vesna.apps.client.MainFormController;
import org.vesna.core.app.Core;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.javafx.BaseAppModel;
import org.vesna.core.javafx.fxml.FXMLCombiner;
import org.vesna.samples.crm.client.entities.PersonsRepositoryImpl;

/**
 *
 * @author Krzysztof Marecki
 */
public class CrmApp extends org.vesna.apps.client.App {
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    protected BaseAppModel createAppModel() {
        CrmAppModel model = new CrmAppModel();
        return model;
    }

    @Override
    protected MainFormController newMainFormController() {
        return new CrmMainFormController();
    }
    
    @Override
    protected void addCombinerVariables(FXMLCombiner combiner) {
        combiner.addAttributeVariable("CONTROLLER", CrmMainFormController.class.getName());
        combiner.addFXMLVariable("MAIN_FORM_MENU", CrmApp.class.getResourceAsStream("MainFormMenu.fxml"));
    }
    
    @Override
    protected void configureServices() {
        super.configureServices(); 
        
        EntitiesService entityService = Core.getServices().get(EntitiesService.class);
        entityService.addRepository("Persons", new PersonsRepositoryImpl());
    }
}
