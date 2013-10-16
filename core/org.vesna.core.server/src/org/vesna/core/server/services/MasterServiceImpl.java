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
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.vesna.core.app.Core;
import org.vesna.core.entities.EntitiesService;
import org.vesna.core.entities.Repository;
import org.vesna.core.logging.LoggerHelper;
import org.vesna.core.services.ServiceCallReturn;

/**
 *
 * @author Krzysztof Marecki
 */
@WebService
public class MasterServiceImpl implements MasterService {
    private static final Logger logger = Logger.getLogger(MasterServiceImpl.class);
    
    class Person {
    
        private int personID;

        public int getPersonID() {
            return personID;
        }

        public void setPersonID(int personID) {
            this.personID = personID;
        }

        private String firstName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        private String lastName;

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
    
    @Override
    public String getServerInfo() {
        return "Vesna Server 2013";
    }
    
    @Override
    public ServiceCallReturn execRepositoryMethod(String repositoryName, String methodName, String[] arguments) {
        try {
            Repository repository = Core.getServices().get(EntitiesService.class).getRepository(repositoryName);
            if (repository == null) {
                return new ServiceCallReturn(
                        false, null, String.format("%s is unknown repository", repositoryName));
            }

            if (!methodName.equals("getAll")) {
                return new ServiceCallReturn(
                        false, null, String.format("%s is unknown method", methodName));
            }

            List entities = repository.getAll();
            Gson gson = new Gson();

            ServiceCallReturn ret = new ServiceCallReturn(true, gson.toJson(entities), null);
            return ret;
        } catch (Throwable ex) {
            LoggerHelper.logException(logger, ex);
            return new ServiceCallReturn(
                    false, null, String.format("MasterService exception: %s", ex.getLocalizedMessage()));
        }
    }
}
