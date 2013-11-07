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

/**
 *
 * @author Krzysztof Marecki
 */
public class DummyService {
    
    public class HelloWorldArgument {
        
        private String userName;

        public String getUserName() {
            return userName;
        }

        public HelloWorldArgument(String userName) {
            this.userName = userName;
        }
    }
    
    public class HelloWorldResult {
        
        private String message;

        public String getMessage() {
            return message;
        }
        
        private Boolean success;

        public Boolean getSuccess() {
            return success;
        }
        
        public HelloWorldResult(Boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
    
    public HelloWorldResult helloWorld(HelloWorldArgument argument) {
        String message = String.format("Hello %s", argument.getUserName());
        HelloWorldResult result = new HelloWorldResult(true, message);
        return result;
    }
}
