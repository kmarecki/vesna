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
package org.vesna.apps.server.controls;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManegementControlModel {
    private final ListProperty<String> tables = new SimpleListProperty<>(FXCollections.<String>observableArrayList());

    public ObservableList<String> getTables() {
        return tables.get();
    }

    public void setTables(ObservableList<String> value) {
        tables.set(value);
    }

    public ListProperty tablesProperty() {
        return tables;
    }
    
    public DatabaseManegementControlModel() {
        tables.add("Table1");
        tables.add("Table2");
    }
    
    public void initialize() {
        
    }
    
}
