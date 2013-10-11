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
    public ServiceCallReturn execRepositoryMethod(String repository, String methodName, String[] arguments) {
        if (!repository.equals("Persons")) {
            return new ServiceCallReturn(false, null, String.format("%s is unknown repository", repository));
        }
        if (!methodName.equals("getAll")) {
            return new ServiceCallReturn(false, null, String.format("%s is unknown method", methodName));
        }
        
       logger.info(String.format("execRepositoryMethod called: %s, %s", repository, methodName));
       Person p1 = new Person();
       p1.setPersonID(1);
       p1.setFirstName("John");
       p1.setLastName("Smith");
       Person p2 = new Person();
       p2.setPersonID(2);
       p2.setFirstName("Alice");
       p2.setLastName("Key");
       
       List<Person> persons = new ArrayList();
       persons.add(p1);
       persons.add(p2);
       Gson gson = new Gson();
       
       ServiceCallReturn ret = new ServiceCallReturn(true, gson.toJson(persons), null);
       return ret;
    }
}
