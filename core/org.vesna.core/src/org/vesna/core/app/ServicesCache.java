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

package org.vesna.core.app;

import java.util.HashMap;
import java.util.Map;
import org.vesna.core.lang.Func1;

/**
 *
 * @author Krzysztof Marecki
 */
public class ServicesCache {
    Map<ServiceInfo, Object> services = new HashMap<>();
    
    public void add(ServiceInfo serviceInfo, Object service) {
        services.put(serviceInfo, service);
    }
    
    public <T> T get(final Class<T> clazz) {
         return get(new Func1<ServiceInfo, Boolean>(){
            @Override
            public Boolean apply(ServiceInfo serviceInfo) {
                return serviceInfo.getServiceClass() == clazz;
            }
        });
    }
    
     public <T> T get(final String serviceName) {
        return get(new Func1<ServiceInfo, Boolean>(){
            @Override
            public Boolean apply(ServiceInfo serviceInfo) {
                return serviceInfo.getServiceName().equals(serviceName);
            }
        });
    }
     
    private <T> T get(Func1<ServiceInfo, Boolean> comparator) {
        T service = null;
        for (ServiceInfo serviceInfo : services.keySet()) {
            if (comparator.apply(serviceInfo)) {
                service = (T)services.get(serviceInfo);
            }
        }
        return service;
    }
}
