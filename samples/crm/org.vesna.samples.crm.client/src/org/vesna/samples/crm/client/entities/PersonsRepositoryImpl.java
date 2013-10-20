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
package org.vesna.samples.crm.client.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.client.services.MasterServiceImplService;
import org.vesna.core.client.services.ServiceCallReturn;
import org.vesna.core.lang.JasonHelper;
import org.vesna.samples.crm.dto.Person;
import org.vesna.samples.crm.entities.PersonsRepository;

/**
 *
 * @author Krzysztof Marecki
 */
public class PersonsRepositoryImpl implements PersonsRepository {

    @Override
    public Person insert(Person entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person update(Person entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Person entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Person> getAll() {
        MasterServiceImplService service = new MasterServiceImplService();
        MasterServiceImpl impl = service.getMasterServiceImplPort();
        ServiceCallReturn ret = impl.execRepositoryMethod("Persons", "getAll", null);
        if (ret.isSuccess()) {
            List<Person> dtos = JasonHelper.fromJason(new TypeToken<List<Person>>(){}, ret.getReturnValue());
            return dtos;
        }
        String msg = String.format("execRepositoryMethod failed: %s", ret.getErrorMessage());
        throw new RuntimeException(msg);
    }

    @Override
    public Person getSingle(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
