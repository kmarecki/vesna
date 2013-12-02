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
package org.vesna.core.client.config;

import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import org.vesna.core.app.Core;
import org.vesna.core.client.services.ConnectionsService;
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.client.services.ServiceCallReturn;
import org.vesna.core.config.ConfigurationService;
import org.vesna.core.lang.GsonHelper;

/**
 *
 * @author Krzysztof Marecki
 */
public class ClientConfigurationService extends ConfigurationService {

    @Override
    public String readParameterValue(String parameterName) {
        String value = super.readParameterValue(parameterName); 
        if (value == null) {
            value = readParameterValueFromServer(parameterName);
        }
        return value;
    }
    
    private String readParameterValueFromServer(String parameterName) {
        ConnectionsService connectionService = Core.getService(ConnectionsService.class);
        MasterServiceImpl impl = connectionService.getMasterServiceImpl();
        String nameJson = GsonHelper.toJson(parameterName);
        ServiceCallReturn ret = impl.execServiceMethod(
                "ConfigurationService", "readParameterValue", Arrays.asList(new String[] { nameJson }));
        if(ret.isSuccess()) {
           String value = GsonHelper.fromJson(new TypeToken<String>(){}, ret.getReturnValue());
           return value;
        }
        String msg = String.format("execServiceMethod failed: %s", ret.getErrorMessage());
                throw new RuntimeException(msg);
    }
    
}
