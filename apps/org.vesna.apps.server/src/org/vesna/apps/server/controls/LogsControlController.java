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
package org.vesna.apps.server.controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.vesna.apps.server.ServerAppModel;
import org.vesna.apps.server.MainFormController;
import org.vesna.core.javafx.controls.LogsList;
import org.vesna.core.server.derby.DerbyService;

/**
 *
 * @author Krzysztof Marecki
 */
public class LogsControlController {
    private static final Logger logger = Logger.getLogger(MainFormController.class);
    private ServerAppModel appModel;
    private DerbyService derbyService;
    
    @FXML
    LogsList logsList;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
//        JednmiarRepositoryImpl repository = new JednmiarRepositoryImpl();
//		Jednmiary[] jednmiar = repository.getAllEntities();
//		
//		for(Jednmiary jednmiary : jednmiar) {
//			System.out.printf("Id=%s, Nazwa=%s, Symbol=%s\n", 
//								jednmiary.getJednmiaryId(),
//								jednmiary.getNazwa(),
//								jednmiary.getSymbol());
//		}
        
        derbyService.check();
    }
    
    @FXML 
     private void handleButtonInfo(ActionEvent event) {
        if (derbyService.isRunning()) {
            derbyService.shutdown();
        }
        logger.info("buttonInfo was clicked!");
    }
	
    
    @FXML 
     private void handleButtonWarning(ActionEvent event) {
        logger.log(Level.WARN, "buttonWarning was clicked!");
    }
	
    @FXML 
     private void handleButtonError(ActionEvent event) {
        logger.log(Level.ERROR, "buttonError was clicked!\n Multiline Rulez");
    }
	
     @FXML 
     private void handleButtonCritical(ActionEvent event) {
        logger.log(Level.FATAL, "buttonFatal was clicked!");
    }
    
    public void setModel(ServerAppModel model) {
        appModel = model;
        derbyService = appModel.getServices().get(DerbyService.class);
        
        logsList.getController().setModel();
    }
}
