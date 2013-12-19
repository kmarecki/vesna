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
package org.vesna.apps.client;

import java.io.ByteArrayInputStream;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.vesna.core.app.Core;
import org.vesna.core.app.ServiceInfo;
import org.vesna.core.client.config.ClientConfigurationService;
import org.vesna.core.client.entities.EntitiesServiceTypesConnectorImpl;
import org.vesna.core.client.services.ConnectionsService;
import org.vesna.core.config.ConfigurationService;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.javafx.BaseApp;
import org.vesna.core.javafx.fxml.FXMLCombiner;
import org.vesna.core.javafx.navigation.NavigationService;
import org.vesna.core.net.ClasspathURLHandler;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class App extends BaseApp {
    private static final Logger logger = Logger.getLogger(App.class);
    
    @Override
    public void start(Stage stage) throws Exception {
         super.start(stage);
         ClientAppModel model = (ClientAppModel) getAppModel();
        
         FXMLCombiner combiner = new FXMLCombiner();
         combiner.loadTemplate("org/vesna/apps/client/MainForm.templ.fxml");
         addCombinerVariables(combiner);
         String fxml = combiner.getCombinedFXML();
         
         FXMLLoader loader = new FXMLLoader();
         URL url = new URL(null, "classpath:org/vesna/apps/client", new ClasspathURLHandler(ClassLoader.getSystemClassLoader()));
         loader.setLocation(url);
        
         Parent root = (Parent)loader.load(new ByteArrayInputStream(fxml.getBytes()));
         Scene scene = new Scene(root);
         configureRootScene(scene);
         MainFormController controller = loader.getController();
         controller.setModelAndInitialize(model);

         stage.setTitle(model.getApplicationTitle());
         stage.setScene(scene);
         stage.sizeToScene();
         stage.show();
    }
    
    @Override
    protected void configureServices() {
        ConnectionsService connectionsService = new ConnectionsService();
        EntitiesService entitiesService = new EntitiesService();
        entitiesService.setTypesConnector(new EntitiesServiceTypesConnectorImpl());
        NavigationService navigationService = new NavigationService();
        ClientConfigurationService configurationService = new ClientConfigurationService();
        configurationService.setResourceFile("vesna.client.properties");
        

        Core.addService(new ServiceInfo("ConnectionsService", ConnectionsService.class), connectionsService);
        Core.addService(new ServiceInfo("EntitiesService", EntitiesService.class), entitiesService);
        Core.addService(new ServiceInfo("NavigationService", NavigationService.class), navigationService);
        Core.addService(new ServiceInfo("ConfigurationService", ConfigurationService.class), configurationService);
    }
    
    protected MainFormController newMainFormController() {
        return new MainFormController();
    }
    
    protected void addCombinerVariables(FXMLCombiner combiner) {
        combiner.addAttributeVariable("CONTROLLER", MainFormController.class.getName());
        combiner.addFXMLVariable("MAIN_FORM_MENU", App.class.getResourceAsStream("MainFormMenu.fxml"));
    }
    
}
