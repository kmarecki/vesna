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
package org.vesna.core.client.entities;

import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.client.services.MasterServiceImplService;
import org.vesna.core.client.services.ServiceCallReturn;
import org.vesna.core.entities.EntitiesServiceTypesConnector;
import org.vesna.core.entities.EntityType;
import org.vesna.core.lang.GsonHelper;

/**
 *
 * @author Krzysztof Marecki
 */
public class EntitiesServiceTypesConnectorImpl implements EntitiesServiceTypesConnector {

    @Override
    public EntityType getEntityType(String klassName) {
        MasterServiceImplService service = new MasterServiceImplService();
        MasterServiceImpl impl = service.getMasterServiceImplPort();
        String klassNameJson = GsonHelper.toJson(klassName);
        ServiceCallReturn ret = impl.execServiceMethod(
                "EntitiesService", "getEntityType", Arrays.asList(new String[] { klassNameJson }));
        if(ret.isSuccess()) {
           EntityType entityType = GsonHelper.fromJson(new TypeToken<EntityType>(){}, ret.getReturnValue());
           return entityType;
        }
        String msg = String.format("execServiceMethod failed: %s", ret.getErrorMessage());
                throw new RuntimeException(msg);
    }

}
