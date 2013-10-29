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

import java.util.UUID;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author Krzysztof Marecki
 */
public class TabPaneNavigationAdapter implements NavigationAdapter {
    
    private TabPane tabPane;

    public TabPaneNavigationAdapter(TabPane tabPane) {
        this.tabPane = tabPane;
    }
    
    @Override
    public Control getCurrentScreen() {
        Tab tab = getCurrentTab();
        Control control = (Control)tab.getContent();
        return control;
    }

    @Override
    public void closeCurrentScreen() {
        Tab tab = getCurrentTab();
        tab.setContent(getEmptyContent());
        tab.setText("");
    }

    @Override
    public void closeCurrentWindow() {
        Tab tab = getCurrentTab();
        tabPane.getTabs().remove(tab);
    }

    @Override
    public void openScreenInCurrentWindow(Node screen, String title) {
         Tab tab = getCurrentTab();
         tab.setContent(screen);
         tab.setText(title);
    }

    @Override
    public void openScreenInNewWindow(Node screen, String title) {
        String windowTag = UUID.randomUUID().toString();
        Tab tab = new Tab();
        tab.setContent(screen);
        tab.setText(title);
        tab.setUserData(windowTag);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().selectLast();
    }

    @Override
    public String getCurrentWindowTag() {
        Tab tab = getCurrentTab();
        String windowTag = tab.getUserData().toString();
        return windowTag;
    }
    
    private Tab getCurrentTab() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        return tab;
    }
    
    private Control getEmptyContent() {
        Label empty = new Label();
        return empty;
    }
}
