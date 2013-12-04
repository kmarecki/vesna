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
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.vesna.core.app.Core;
import org.vesna.core.app.ServiceInfo;
import org.vesna.core.data.DataRow;
import org.vesna.core.data.HashDataRow;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.entities.EntityException;
import org.vesna.core.entities.EntityHelper;
import org.vesna.core.entities.EntityType;
import org.vesna.core.lang.GsonHelper;
import org.vesna.core.server.derby.DerbyService;
import org.vesna.core.server.hibernate.HibernateService;
import org.vesna.core.server.services.MasterServiceImpl;
import org.vesna.core.server.sql.DatabaseService;
import org.vesna.core.services.ServiceCallReturn;
import org.vesna.core.sql.MetaDataTable;
import org.vesna.samples.crm.dto.Employee;
import org.vesna.samples.crm.dto.Person;
import org.vesna.samples.crm.server.entities.EmployeeRepositoryImpl;
import org.vesna.samples.crm.server.entities.PersonRepositoryImpl;

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
    public static void setUpClass() throws SQLException {
        derbyService = new DerbyService("crm_test");
        derbyService.check();
        databaseService = new DatabaseService(derbyService);
        hibernateService = new HibernateService();
        hibernateService.setConfigurationResource("hibernate.test.cfg.xml");
        hibernateService.setMappingsJar("dist//lib//org.vesna.samples.crm.dto.jar");
        masterService = new MasterServiceImpl();
        entitiesService = new EntitiesService();
        entitiesService.setTypesConnector(hibernateService);
        entitiesService.addRepository("Persons", new PersonRepositoryImpl());
        entitiesService.addRepository("Employees", new EmployeeRepositoryImpl());

        Core.addService(derbyService);
        Core.addService(databaseService);
        Core.addService(new ServiceInfo("EntitiesService", EntitiesService.class), entitiesService);
        Core.addService(hibernateService);
        
        createSchema();
        insertRows();
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
        ServiceCallReturn result = execRepositoryMethod("Persons", "getAll", null);
        List<Person> persons = GsonHelper.fromJson(new TypeToken<List<Person>>(){}, result.getReturnValue());
        assertTrue(persons.size() > 0);
    }
    
    @Test
    public void repositoryInsert() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("XXX");
        String personJasn = GsonHelper.toJson(person);
        ServiceCallReturn result = execRepositoryMethod(
                "Persons", "insert", new String[]{ personJasn });
        person = GsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertTrue(person.getPersonID() > 0);
        String idJson = GsonHelper.toJson(person.getPersonID());
        result = execRepositoryMethod(
                "Persons", "getSingle", new String[] { idJson });
        person = GsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertEquals("John", person.getFirstName());
        assertEquals("XXX", person.getLastName());
    }
    
    @Test
    public void repositoryUpdate() {
        String idJson = GsonHelper.toJson(1);
        ServiceCallReturn result =execRepositoryMethod(
                "Persons", "getSingle", new String[] { idJson });
        Person person = GsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertEquals(1, person.getPersonID());
        assertEquals("Tom", person.getFirstName());
        person.setFirstName("Alex");
        String personJson = GsonHelper.toJson(person);
        result = execRepositoryMethod(
                "Persons", "update", new String[] { personJson });
        person = GsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertEquals(1, person.getPersonID());
        assertEquals("Alex", person.getFirstName());
        result = execRepositoryMethod(
                "Persons", "getSingle", new String[] { idJson });
        person = GsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertEquals(1, person.getPersonID());
        assertEquals("Alex", person.getFirstName());
    }
    
    @Test
    public void repositoryDelete() {
        String idJson = GsonHelper.toJson(2);
        ServiceCallReturn result = execRepositoryMethod(
                "Persons", "getSingle", new String[] { idJson });
        Person person = GsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertTrue(person != null);
        String personJson = GsonHelper.toJson(person);
        execRepositoryMethod(
                "Persons", "delete", new String[] { personJson });
        result = execRepositoryMethod(
                "Persons", "getSingle", new String[] { idJson });
        person = GsonHelper.fromJson(new TypeToken<Person>(){}, result.getReturnValue());
        assertTrue(person == null);   
    }
    
    @Test
    public void repositoryComplexEntityGetAll() {
        ServiceCallReturn result = execRepositoryMethod(
                "Employees", "getAll", new String[] {});
        List<Employee> employees = 
                GsonHelper.fromJson(new TypeToken<List<Employee>>(){}, result.getReturnValue());
        assertTrue(employees.size() > 0);
    }
    
    @Test
    public void entityTypeGet() throws EntityException {
        int personID = 100;
        Person person = new Person();
        person.setPersonID(personID);
        String className = person.getClass().getName();
        String classJson = GsonHelper.toJson(className);
        ServiceCallReturn result = execServiceMethod(
                "EntitiesService", "getEntityType", new String[] { classJson });
        EntityType entityType = GsonHelper.fromJson(new TypeToken<EntityType>(){}, result.getReturnValue());
        assertEquals(className, entityType.getEntityName());
        assertEquals(personID, EntityHelper.getId(entityType, person));
    }
    
    private static void createSchema() {
        hibernateService.getSessionFactory();
    }

    private static void insertRows() throws SQLException {
        MetaDataTable personsTable = databaseService.getLoadedTable(
                null, "app", "persons");

        DataRow person1 = new HashDataRow();
        person1.setString("first_name", "Tom");
        person1.setString("last_name", "Johnson");        
        databaseService.insertRow(personsTable, person1);
        
        DataRow person2 = new HashDataRow();
        person2.setString("first_name", "Julia");
        person2.setString("last_name", "Smith");        
        databaseService.insertRow(personsTable, person2);
        
        DataRow person3 = new HashDataRow();
        person3.setString("first_name", "Jack");
        person3.setString("last_name", "Black");        
        databaseService.insertRow(personsTable, person3);
        
        MetaDataTable companiesTable = databaseService.getLoadedTable(
                null, "app", "companies");
        DataRow company1 = new HashDataRow();
        company1.setString("short_name", "Super Company");
        company1.setString("long_name", "Super Company Inc.");
        databaseService.insertRow(companiesTable, company1);
        
        MetaDataTable employeesTable = databaseService.getLoadedTable(
                null, "app", "employees");
        DataRow employee1 = new HashDataRow();
        employee1.setString("person_id", "3");        
        employee1.setString("company_id", "1");
        employee1.setString("title", "Boss");
        databaseService.insertRow(employeesTable, employee1);
        
    }
    
    private ServiceCallReturn execRepositoryMethod(
            String repositoryName, String methodName, String[] arguments) {
        ServiceCallReturn result = masterService.execRepositoryMethod(
                repositoryName, methodName, arguments);
        assertTrue(result.getErrorMessage(), result.getSuccess());
        return result;
    }
    
    private ServiceCallReturn execServiceMethod(
            String serviceName, String methodName, String[] arguments) {
        ServiceCallReturn result = masterService.execServiceMethod(
                serviceName, methodName, arguments);
        assertTrue(result.getErrorMessage(), result.getSuccess());
        return result;
    }
}