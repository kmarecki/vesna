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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javafx.scene.Node;
import org.vesna.core.javafx.BaseController;
import org.vesna.core.javafx.controls.ControlEx;

/**
 *
 * @author Krzysztof Marecki
 */
public class NavigationService {
    
    class Screen {
    
        private Node control;

        public Node getControl() {
            return control;
        }
        
        private String title;

        public String getTitle() {
            return title;
        }
        
        public Screen(Node control, String title) {
            this.control = control;
            this.title = title;
        }
    }
    
    class WindowStack {
        private Map<String, Stack<Screen>> screens = new HashMap();
        
        public void addWindow(String windowTag) {
            Stack<Screen> window = new Stack<>();
            screens.put(windowTag, window);
        }
        
        public void closeWindow(String windowTag) {
            screens.remove(windowTag);
        }
        
        public Screen getCurrentScreen(String windowTag) {
            Stack<Screen> window = screens.get(windowTag);
            Screen screen = window.peek();
            return screen;
        }
        
        public void addNextScreen(String windowTag, Screen screen) {
             Stack<Screen> window = screens.get(windowTag);
             window.push(screen);
        }
        
        public Screen navigateToPreviousScreen(String windowTag) {
             Stack<Screen> window = screens.get(windowTag);
             window.pop();
             Screen screen = window.peek();
             return  screen;
        }
        
        public Iterable<Screen> getWindowScreens(String windowTag) {
            List<Screen> result = new ArrayList();
            for(Screen screen : screens.get(windowTag)) {
                result.add(screen);
            }
            return result;
        }
    }
    
    private NavigationAdapter adapter;
    private WindowStack windows = new WindowStack();

    public void setAdapter(NavigationAdapter adapter) {
        this.adapter = adapter;
    }

    public Node getCurrentScreen() {
        String windowTag = adapter.getCurrentWindowTag();
        Screen screen = windows.getCurrentScreen(windowTag);
        Node control = screen.getControl();
        Node currentControl = adapter.getCurrentScreen();
        assert control == currentControl;
        return control;
    }

    public void closeCurrentScreen() {
        String windowTag = adapter.getCurrentWindowTag();
        
        adapter.closeCurrentScreen();
        Screen screen = windows.navigateToPreviousScreen(windowTag);
        if (screen.control instanceof ControlEx) {
            BaseController screenController = ((ControlEx)screen.control).getController();
            screenController.refreshModel();
        }
        adapter.openScreenInCurrentWindow(screen.control, screen.title);
        refreshScreen(screen);
    }
    
    public void closeCurrentWindow() {
        String windowTag = adapter.getCurrentWindowTag();
        
        adapter.closeCurrentWindow();
        windows.closeWindow(windowTag);
    }
    
    public void openScreenInCurrentWindow(Node control, String title) {
        Screen screen = new Screen(control, title);
        String windowTag = adapter.getCurrentWindowTag();
        
        String pathTitle = getFutureScreenPathTitle(windowTag, title);
        adapter.openScreenInCurrentWindow(screen.control, pathTitle);
        windows.addNextScreen(windowTag, screen);
        refreshScreen(screen);
    }
    
    public void openScreenInNewWindow(Node control, String title) {
        Screen screen = new Screen(control, title);
        
        adapter.openScreenInNewWindow(screen.control, title);
        String windowTag = adapter.getCurrentWindowTag();
        windows.addWindow(windowTag);
        windows.addNextScreen(windowTag, screen);
        refreshScreen(screen);
    }
    
    private String getScreenPathTitle(String windowTag) {
        Iterable<Screen> screens = windows.getWindowScreens(windowTag);
        StringBuilder title = new StringBuilder();
        Boolean first = true;
        for (Screen screen : screens) {
            if(!first) {
                title.append("->");
            }
            title.append(screen.title);
            first = false;
        }
        return title.toString();
    }
    
    private String getFutureScreenPathTitle(String windowTag, String title) {
       String pathTitle = String.format("%s->%s", getScreenPathTitle(windowTag), title);
       return pathTitle;
    }
    
    private void refreshScreen(Screen screen) {
        if (screen.control instanceof ControlEx) {
              BaseController screenController = ((ControlEx)screen.control).getController();
              screenController.refresh();
        }
    }
}
