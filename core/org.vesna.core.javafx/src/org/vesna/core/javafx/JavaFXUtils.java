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
package org.vesna.core.javafx;

import javafx.scene.text.Text;

/**
 *
 * @author Krzysztof Marecki
 */
public class JavaFXUtils {
    
    public static Text widthText = new Text();
    
    public static double getTextWithMarginWidth(String text) {
        return getTextWidth(String.format("__%s__", text));
    }
    
    public static double getTextWidth(String text) {
        widthText.setText(text);
        widthText.snapshot(null, null);
        return widthText.getLayoutBounds().getWidth();
    }
}
