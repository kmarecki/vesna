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
import org.hibernate.SessionFactory;
import org.vesna.core.app.Core;
import org.vesna.core.server.hibernate.HibernateService;


/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class HibernateControlController {

     private HibernateControlModel model;
    
     @FXML 
     private void handleButtonUpdateSchema(ActionEvent event) {
         HibernateService hibernateService = Core.getService(HibernateService.class);
         SessionFactory session = hibernateService.getSessionFactory();
     }
    
    public void setModel(final HibernateControlModel model) {
        this.model = model;
    }
}
