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

//import bt.core.server.services.MasterServiceImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.ws.Endpoint;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author krzys
 */
public class App extends Application {
    private static final Logger logger = Logger.getLogger(App.class);
	
    
//    private Endpoint endpoint;
	
	
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        
        Scene scene = new Scene(root);
		scene.getStylesheets().add("resources/css/styles.css");
        
        stage.setTitle("Vesna Server 2013");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
		
//		publishMasterService();
    }
	
	@Override 
	public void stop() {
//		if (endpoint != null && endpoint.isPublished()) {
//			endpoint.stop();
//		}
	}
	
	private void publishMasterService() {
//		endpoint = Endpoint.publish("http://0.0.0.0:1234/", new MasterServiceImpl());
//		logger.log(Priority.INFO, "Master service has been successfully published");
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