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
package org.vesna.core.server.services;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vesna.core.app.Core;
import org.vesna.core.app.ServiceInfo;
import org.vesna.core.lang.GsonHelper;
import org.vesna.core.services.ServiceCallReturn;

/**
 *
 * @author Krzysztof Marecki
 */
public class MasterServiceTest {
    
    public MasterServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DummyService dummyService = new DummyService();
        DummyService dummyService2 = new DummyService();
        MasterService masterService = new MasterServiceImpl();
        
        Core.addService(dummyService);
        Core.addService(new ServiceInfo("DummyService", DummyService.class), dummyService2);
        Core.addService(masterService);
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
    public void execServiceMethodWithServiceClassName() {
       execServiceMethod("org.vesna.core.server.services.DummyService", "John Doe");
    }
    
    @Test
    public void execServiceMethodWithServiceName() {
       execServiceMethod("DummyService", "Jane Roe");
    }
    
    private void execServiceMethod(String serviceName, String userName) {
        MasterService masterService = Core.getService(MasterServiceImpl.class);
        DummyService dummyService = Core.getService(DummyService.class);
        DummyService.HelloWorldArgument argument = dummyService.new HelloWorldArgument(userName);
        String argumentJson = GsonHelper.toJson(argument);
        ServiceCallReturn result = masterService.execServiceMethod(
                serviceName, "helloWorld", new String[] { argumentJson });
        assertTrue(result.getErrorMessage(), result.getSuccess());
        DummyService.HelloWorldResult helloWorldResult = GsonHelper.fromJson(
                DummyService.HelloWorldResult.class, result.getReturnValue());
        assertTrue(helloWorldResult.getSuccess());
        assertEquals(String.format("Hello %s", userName), helloWorldResult.getMessage());
    }
}