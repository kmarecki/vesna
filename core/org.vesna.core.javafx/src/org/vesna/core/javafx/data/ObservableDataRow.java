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
package org.vesna.core.javafx.data;

import javafx.collections.ObservableList;
import org.vesna.core.server.sql.DataRow;

/**
 *
 * @author Krzysztof Marecki
 */
public class ObservableDataRow implements DataRow {
    private ObservableDataTable parent;
    private ObservableList<Object> items;
    
    public ObservableList<Object> getItems() {
        return items;
    }
    
    void setItems(ObservableList<Object> value) {
        items = value;
    }
    
    ObservableDataRow(ObservableDataTable table) {
        this.parent = table;
    }
    
    
    @Override
    public String getString(int columnIndex) {
        return (String)items.get(columnIndex);
    }
    
    @Override
    public String getString(String columnName) {
        int columnIndex = parent.indexOfColumn(columnName);
        return getString(columnIndex);
    }
    
    @Override
    public void setString(int columnIndex, String value) {
        items.set(columnIndex, value);
    }
    
    @Override
    public void setString(String columnName, String value) {
        int columnIndex = parent.indexOfColumn(columnName);
        setString(columnIndex, value);
    }
}
