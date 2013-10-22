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

import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.vesna.core.app.Core;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.lang.JsonHelper;
import org.vesna.core.server.derby.DerbyService;
import org.vesna.core.server.hibernate.HibernateService;
import org.vesna.core.server.services.MasterServiceImpl;
import org.vesna.core.server.sql.DatabaseService;
import org.vesna.core.services.ServiceCallReturn;
import org.vesna.samples.crm.dto.Person;
import org.vesna.samples.crm.server.entities.PersonsRepositoryImpl;

/**
 *
 * @author Krzysztof Marecki
 */
public class MasterServiceTest {
    
    private static DerbyService derbyService;
    private static DatabaseService databaseService;
    private static EntitiesService entitiesService;
    private static HibernateService hibernateService;
    private static MasterServiceImpl masterService;
        
    public MasterServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        derbyService = new DerbyService("crm_test");
        derbyService.check();
        databaseService = new DatabaseService(derbyService);
        entitiesService = new EntitiesService();
        entitiesService.addRepository("Persons", new PersonsRepositoryImpl());
        hibernateService = new HibernateService();
        hibernateService.setConfigurationResource("hibernate.test.cfg.xml");
        hibernateService.setMappingsJar("dist//lib//org.vesna.samples.crm.dto.jar");
        masterService = new MasterServiceImpl();

        Core.getServices().add(derbyService);
        Core.getServices().add(databaseService);
        Core.getServices().add(entitiesService);
        Core.getServices().add(hibernateService);
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    

    @Test
    public void repositoryGetAll() {
        ServiceCallReturn result = masterService.execRepositoryMethod("Persons", "getAll", null);
        assertTrue(result.getSuccess());
        List<Person> persons = JsonHelper.fromJson(new TypeToken<List<Person>>(){}, result.getReturnValue());
        assertTrue(persons.size() == 0);
    }
    
    @Test
    public void repositoryiInsert() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("XXX");
        String personJason = JsonHelper.toJson(person);
        ServiceCallReturn result = masterService.execRepositoryMethod(
                "Persons", "insert", new String[]{ personJason });
        assertTrue(result.getErrorMessage(), result.getSuccess());
        person = JsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertTrue(person.getPersonID() > 0);
    }
    
    @Test
    public void repositoryUpdate() {
        
    }
    
    @Test
    public void repositoryDelete() {
        
    }

    
}