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
package org.vesna.samples.crm.test.server;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.vesna.core.app.Core;
import org.vesna.core.app.ServiceInfo;
import org.vesna.core.config.ConfigurationService;

/**
 *
 * @author Krzysztof Marecki
 */
public class ApplicationTest {
    
    @BeforeClass
    public static void setUpClass() {
        ConfigurationService configurationService = new ConfigurationService();
        configurationService.setResourceFile("vesna.server.properties");
        
        Core.addService(new ServiceInfo("ConfigurationService", ConfigurationService.class), configurationService);
    }
    
    @Test
    public void readParameterValue() {
        ConfigurationService configurationService = Core.getService(ConfigurationService.class);
        String parameterValue = configurationService.readParameterValue("vesna.masterservice.url");
        assertEquals("http://localhost:1234/", parameterValue);
    }
}
