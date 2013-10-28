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
package org.vesna.core.javafx.navigation;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.scene.control.Control;

/**
 *
 * @author Krzysztof Marecki
 */
public class NavigationService {
    
    class WindowStack {
        private Map<String, Stack<Control>> columns = new HashMap();
    }
    
    private NavigationAdapter adapter;
    private WindowStack windows = new WindowStack();

    public void setAdapter(NavigationAdapter adapter) {
        this.adapter = adapter;
    }
    
    private Control currentScreen;

    public Control getCurrentScreen() {
        String windowTag = adapter.getCurrentWindowTag();
        Stack<Control> screens = windows.columns.get(windowTag);
        Control screen = screens.peek();
        return adapter.getCurrentScreen();
    }

    
    public void closeCurrentScreen() {
        String windowTag = adapter.getCurrentWindowTag();
        Stack<Control> screens = windows.columns.get(windowTag);
        
    }
    
    public void closeCurrentWindow() {
        
    }
    
    public void openScreenInCurrentWindow(Control screen) {
        
    }
    
    public void openScreenInNewWindow(Control screen) {
        
    }

}
