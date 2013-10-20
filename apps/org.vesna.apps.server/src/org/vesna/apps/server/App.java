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
package org.vesna.apps.server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.ws.Endpoint;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.vesna.core.app.Core;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.javafx.BaseApp;
import org.vesna.core.server.derby.DerbyService;
import org.vesna.core.server.hibernate.HibernateService;
import org.vesna.core.server.services.MasterServiceImpl;
import org.vesna.core.server.sql.DatabaseService;
import org.vesna.core.util.StreamHelper;

/**
 *
 * @author Krzysztof Marecki
 */
public abstract class App extends BaseApp {
    private static final Logger logger = Logger.getLogger(App.class);
    
    private Endpoint masterEndpoint;
	
    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        ServerAppModel model = (ServerAppModel) getAppModel();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("MainForm.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(root);
		scene.getStylesheets().add("resources/css/styles.css");
        MainFormController controller = (MainFormController)fxmlLoader.getController();
        controller.setModel(model);
                
        stage.setTitle(model.getApplicationTitle());
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
		
        publishMasterService();
    }
	
    @Override 
    public void stop() {
            if (masterEndpoint != null && masterEndpoint.isPublished()) {
                    masterEndpoint.stop();
            }
    }

    private void publishMasterService() {
            masterEndpoint = Endpoint.publish("http://localhost:1234/", new MasterServiceImpl());
            logger.log(Priority.INFO, "Master service has been successfully published");
    }

    @Override
    protected void configureServices() {
        ServerAppModel model = (ServerAppModel) getAppModel();
        DerbyService derbyService = new DerbyService(model.getDatabaseName());
        DatabaseService databaseService = new DatabaseService(derbyService);
        EntitiesService entitiesService = new EntitiesService();
        HibernateService hibernateService = new HibernateService();
        Core.getServices().add(hibernateService);

        Core.getServices().add(derbyService);
        Core.getServices().add(databaseService);
        Core.getServices().add(entitiesService);
    }

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
}
