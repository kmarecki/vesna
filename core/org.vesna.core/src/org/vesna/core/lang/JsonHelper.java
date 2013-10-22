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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author Krzysztof Marecki
 */
public class JsonHelper {
    
    public static Gson gson = new Gson();
    
    public static <T> T fromJson(TypeToken<T> typeToken , String jsonValue) {
        Type valueType = typeToken.getType();
        T value = gson.fromJson(jsonValue, valueType);
        return value;
    }
    
    public static <T> T fromJson(Type valueType , String jsonValue) {
        T value = (T)gson.fromJson(jsonValue, valueType);
        return value;
    }
    
    public static String toJson(Object value) {
        String  jsonValue = gson.toJson(value);
        return jsonValue;
    }
}
