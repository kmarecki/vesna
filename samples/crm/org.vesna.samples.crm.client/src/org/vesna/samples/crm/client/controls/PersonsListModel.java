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
package org.vesna.samples.crm.client.controls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import org.vesna.apps.client.controls.EntitiesListModel;
import org.vesna.core.app.Core;
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.client.services.MasterServiceImplService;
import org.vesna.core.client.services.ServiceCallReturn;
import org.vesna.core.entities.EntitiesService;
import org.vesna.samples.crm.dto.Person;
import org.vesna.samples.crm.entities.PersonsRepository;

/**
 *
 * @author Krzysztof Marecki
 */
public class PersonsListModel extends EntitiesListModel<Person> {
    
    PersonsRepository repository;
    public PersonsListModel() {
    }

    @Override
    public void initialize() {
        super.initialize(); 
        
        EntitiesService entitiesService = Core.getServices().get(EntitiesService.class);
        repository = (PersonsRepository) entitiesService.getRepository("Persons");
        List<Person> dtos = repository.getAll();
        
        for(Person dto : dtos) {
                getEntities().add(dto);
            }
        
    }
    
    
}
