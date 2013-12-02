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
package org.vesna.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.vesna.core.logging.LoggerHelper;

/**
 *
 * @author Krzysztof Marecki
 */
public class ConfigurationService {
    private static final Logger logger = Logger.getLogger(ConfigurationService.class);
    
    private String resourceFile;
    
    private Properties properties;
    
    protected Properties getProperties() {
        if (properties == null) {
            properties = loadProperties();
        }
        return properties;
    }

    public String getResourceFile() {
        return resourceFile;
    }

    public void setResourceFile(String resourceFile) {
        this.resourceFile = resourceFile;
    }

    public String readParameterValue(String parameterName) {
        String value = getProperties().getProperty(parameterName);
        return value;
    }
    
    private Properties loadProperties() {
        try {
            assert resourceFile != null : "resourceFile is null";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(resourceFile);
            Properties p = new Properties();
            p.load(stream);
            return p;
        } catch (IOException ex) {
            LoggerHelper.logException(logger, ex);
            throw new RuntimeException("Cannot load properties from resource file.");
        }
    }
}
