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

import java.util.Locale;

/**
 *
 * @author Krzysztof Marecki
 */
public class StringHelper {
    
    public static Boolean isNullOrEmpty(String string) {
        return (string == null || string.isEmpty());
    }
    
    public static String toLowerCase(String string) {
        return string != null ? string.toLowerCase() : null;
    }
    
     public static String toLowerCase(String string, Locale locale) {
        return string != null ? string.toLowerCase(locale) : null;
    }
    
    public static String toUpperCase(String string) {
        return string != null ? string.toUpperCase() : null;
    }
    
     public static String toUpperCase(String string, Locale locale) {
        return string != null ? string.toUpperCase(locale) : null;
    }
}
