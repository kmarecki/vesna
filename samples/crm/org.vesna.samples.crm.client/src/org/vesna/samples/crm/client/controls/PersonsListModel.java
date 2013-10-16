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
import org.vesna.apps.client.controls.EntitiesListModel;
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.client.services.MasterServiceImplService;
import org.vesna.core.client.services.ServiceCallReturn;
import org.vesna.samples.crm.dto.Person;

/**
 *
 * @author Krzysztof Marecki
 */
public class PersonsListModel extends EntitiesListModel<Person> {
    
    public PersonsListModel() {
    }

    @Override
    public void initialize() {
        super.initialize(); 
        
        MasterServiceImplService service = new MasterServiceImplService();
        MasterServiceImpl impl = service.getMasterServiceImplPort();
        ServiceCallReturn ret = impl.execRepositoryMethod("Persons", "getAll", null);
        if (ret.isSuccess()) {
            String json = ret.getReturnValue();

            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Person>>(){}.getType();
            Collection<Person> dtos = gson.fromJson(json, collectionType);

            for(Person dto : dtos) {
                getEntities().add(dto);
            }
        } else {
            String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
            throw new RuntimeException(msg);
        }
    }
    
    
}