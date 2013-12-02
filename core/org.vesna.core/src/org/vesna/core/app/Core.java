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

/**
 *
 * @author Krzysztof Marecki
 */
public class Core {
    
    private static ServicesCache services = new ServicesCache();
    
    public static void addService(Object service) {
        Class serviceClass = service.getClass();
        String serviceName = serviceClass.getName();
        ServiceInfo serviceInfo = new ServiceInfo(serviceName, serviceClass);
        addService(serviceInfo, service);
    }
    
    public static void addService(ServiceInfo serviceInfo, Object service) {
        services.add(serviceInfo, service);
    }
    
    public static <T> T getService(Class<T> serviceClass) {
        T service = services.get(serviceClass);
        if (service == null) {
            String msg = String.format("Cannot find service for %s type.", serviceClass.getName());
            throw new RuntimeException(msg);
        }
        return service;
    }
    
    public static <T> T getService(String serviceName) {
       T service = services.get(serviceName);
       if (service == null) {
            String msg = String.format("Cannot find service %s.", serviceName);
            throw new RuntimeException(msg);
        }
       return service;
    }
}
