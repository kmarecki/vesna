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

import com.google.gson.Gson;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.vesna.core.app.Core;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.entities.Repository;
import org.vesna.core.lang.GsonHelper;
import org.vesna.core.lang.ReflectionHelper;
import org.vesna.core.logging.LoggerHelper;
import org.vesna.core.server.entities.RepositoryImpl;
import org.vesna.core.services.ServiceCallReturn;

/**
 *
 * @author Krzysztof Marecki
 */
@WebService
public class MasterServiceImpl implements MasterService {
    private static final Logger logger = Logger.getLogger(MasterServiceImpl.class);
    
    @Override
    public String getServerInfo() {
        return "Vesna Server 2013";
    }
    
    @Override
    public ServiceCallReturn execRepositoryMethod(String repositoryName, String methodName, String[] arguments) {
        try {
            RepositoryImpl repository = (RepositoryImpl)Core.getService(EntitiesService.class).getRepository(repositoryName);
            if (repository == null) {
                return new ServiceCallReturn(
                        false, null, String.format("%s is unknown repository.", repositoryName));
            }

            Method method;
            try {
                method = ReflectionHelper.findMethod(repository.getClass(), methodName);
            } catch(NoSuchMethodException | SecurityException ex) {
                LoggerHelper.logException(logger, ex);
                    return new ServiceCallReturn(
                            false, null, String.format("%s is unknown method in %s repository.",
                            methodName, repositoryName));
            }
            Object[] parameters = toMethodParameters(method, arguments);
            Object result = method.invoke(repository, parameters);
            result = repository.transformForJson(result);
            Gson gson = new Gson();
            ServiceCallReturn ret = new ServiceCallReturn(true, gson.toJson(result), null);
            return ret;
        } catch (Throwable ex) {
            LoggerHelper.logException(logger, ex);
            return new ServiceCallReturn(
                    false, null, String.format("MasterService exception: %s", ex.getMessage()));
        }
    }
    
    @Override
    public ServiceCallReturn execServiceMethod(String serviceName, String methodName, String[] arguments) {
        try {
            Object service = Core.getService(serviceName);
            if (service == null) {
                return new ServiceCallReturn(
                        false, null, String.format("%s is unknown service.", serviceName));
            }
            Method method;
            try {
                method = ReflectionHelper.findMethod(service.getClass(), methodName);
            } catch(NoSuchMethodException | SecurityException ex) {
                LoggerHelper.logException(logger, ex);
                    return new ServiceCallReturn(
                            false, null, String.format("%s is unknown method in %s service.", 
                            methodName, serviceName));
            }
            Object[] parameters = toMethodParameters(method, arguments);
            Object result = method.invoke(service, parameters);
            Gson gson = new Gson();
            ServiceCallReturn ret = new ServiceCallReturn(true, gson.toJson(result), null);
            return ret;
        } catch (Throwable ex) {
            LoggerHelper.logException(logger, ex);
            return new ServiceCallReturn(
                    false, null, String.format("MasterService exception: %s", ex.getMessage()));
        }
    }
    
    private Object[] toMethodParameters(Method method, String[] arguments) {
        List<Object> parameters = new ArrayList();
        Type[] parameterTypes = method.getGenericParameterTypes();
        for(int i = 0; i < parameterTypes.length; i++) {
            Object parameter = GsonHelper.fromJson(parameterTypes[i], arguments[i]);
            parameters.add(parameter);
        }
        return parameters.toArray();
    }
    
}
