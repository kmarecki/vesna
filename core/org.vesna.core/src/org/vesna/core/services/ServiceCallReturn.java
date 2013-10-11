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
package org.vesna.core.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Krzysztof Marecki
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallReturn")
public class ServiceCallReturn {
    @XmlAttribute(name = "success")
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }
    
    @XmlAttribute(name = "returnValue")
    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }
    
    @XmlAttribute(name = "errorMessage")
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ServiceCallReturn(
            Boolean success, String returnValue, String errorMessage) {
        this.success = success;
        this.returnValue = returnValue;
        this.errorMessage =  errorMessage;
    }

}
