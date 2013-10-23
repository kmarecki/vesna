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
package org.vesna.samples.crm.server.entities;

import org.vesna.core.server.entities.RepositoryImpl;
import org.vesna.samples.crm.dto.Person;
import org.vesna.samples.crm.entities.PersonsRepository;


/**
 *
 * @author Krzysztof Marecki
 */
public class PersonsRepositoryImpl extends RepositoryImpl<Person> implements PersonsRepository {

    @Override
    public Person insert(Person entity) {
        return super.insert(entity); 
    }

    @Override
    public Person update(Person entity) {
        return super.update(entity);
    }

    @Override
    public void delete(Person entity) {
        super.delete(entity); 
    }

    public Person getSingle(int id) {
        return super.getSingle(id); 
    }
}
