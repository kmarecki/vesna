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
package org.vesna.core.lang;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author Krzysztof Marecki
 */
public class ReflectionHelper {
    
     public static Method findMethod(Class<?> klass, String methodName) throws NoSuchMethodException {
        Method[] methods = klass.getMethods();
        for(Method method : methods) {
            //return only public methods
            if (method.getName().equals(methodName) && method.getModifiers() == 1) {
                return method;
            }
        }
        String msg = String.format("%s.%s", klass.getName(), methodName);
        throw new NoSuchMethodException(msg);
    }
     
    public static Class getTemplateTypeParameter(Class<?> klass) {
        return getTemplateTypeParameter(klass, 0);
    } 
    public static Class getTemplateTypeParameter(Class<?> klass, int index) {
        ParameterizedType superklass = (ParameterizedType)klass.getGenericSuperclass();
        Class typeParameter = (Class)superklass.getActualTypeArguments()[index];
        return typeParameter;
    }
}
