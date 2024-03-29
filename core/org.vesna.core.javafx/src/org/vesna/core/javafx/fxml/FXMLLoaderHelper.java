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
package org.vesna.core.javafx.fxml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Krzysztof Marecki
 */
public class FXMLLoaderHelper {
    
    public static FXMLLoader loadRoot(Object root, FXMLCombiner combiner) {
        String fxml = combiner.getCombinedFXML();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(root);
        fxmlLoader.setClassLoader(root.getClass().getClassLoader());
        fxmlLoader.setLocation(combiner.getTemplateLocation());
         
        try {
            fxmlLoader.load(new ByteArrayInputStream(fxml.getBytes()));
            return fxmlLoader;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
