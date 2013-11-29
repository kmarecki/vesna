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
package org.vesna.core.entities;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 *
 * @author Krzysztof Marecki
 */
public class EntityHelper {
    
    public static Object getId(EntityType entityType, Object entity) 
            throws EntityException {
        if (entity == null) {
            return null;
        }
        try {
        Class entityKlass = entity.getClass();
        PropertyDescriptor pkDescriptor = new PropertyDescriptor(
                entityType.getPrimaryKeyPropertyName(), entityKlass);
        Method getMethod = pkDescriptor.getReadMethod();
        Object ret = getMethod.invoke(entity, (Object[]) null);
        return ret;
        } catch (Throwable ex) {
            String message = String.format("Cannot invoke bean getter for %s.%s", 
                    entityType.getEntityName(), entityType.getPrimaryKeyPropertyName());
            throw new EntityException(message, ex);
        }
    }
}
