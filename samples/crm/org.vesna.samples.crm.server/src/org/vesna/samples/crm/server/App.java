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
package org.vesna.samples.crm.server;

import static javafx.application.Application.launch;
import javafx.scene.Scene;
import org.vesna.apps.server.ServerAppModel;
import org.vesna.core.app.Core;
import org.vesna.core.entities.EntitiesService;
import org.vesna.samples.crm.server.entities.CompanyRepositoryImpl;
import org.vesna.samples.crm.server.entities.EmployeeRepositoryImpl;
import org.vesna.samples.crm.server.entities.PersonRepositoryImpl;


/**
 *
 * @author Krzysztof Marecki
 */
public class App extends org.vesna.apps.server.App  {
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
    protected ServerAppModel createAppModel() {
        CrmAppModel model = new CrmAppModel();
        return model;
    }

    @Override
    protected void configureServices() {
        super.configureServices(); 
        
        EntitiesService entityService = Core.getService(EntitiesService.class);
        entityService.addRepository("Persons", new PersonRepositoryImpl());
        entityService.addRepository("Companies", new CompanyRepositoryImpl());
        entityService.addRepository("Employees", new EmployeeRepositoryImpl());
    }

    @Override
    protected void configureRootScene(Scene scene) {
        scene.getStylesheets().add("resources/css/styles.css");
    }
}
