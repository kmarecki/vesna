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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import java.util.Map;
import javafx.collections.ObservableList;

/**
 *
 * @author Krzysztof Marecki
 */
public class DataTable {
    Map<String, Integer> columnNames;
    
    private ListProperty<DataRow> rows = new SimpleListProperty<>(FXCollections.<DataRow>observableArrayList());
    
    public ObservableList<DataRow> getRows() {
        return rows;
    }
    
    public ListProperty rowsProperty() {
        return rows;
    }
    
    public static DataTable fromResultSet(ResultSet resultSet) throws SQLException {
        DataTable table = new DataTable();
        
        table.fillColumnNames(resultSet);
        table.fillRows(resultSet);
        
        return table;
    }
    
    int indexOfColumn(String columnName) {
        if (!columnNames.containsKey(columnName)) {
            throw new NullPointerException(String.format("%s column does not exist", columnName));
        }
        return columnNames.get(columnName);
    }
    
    private void fillColumnNames(ResultSet resultSet) throws SQLException {
        columnNames = new HashMap<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for(int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            columnNames.put(columnName, i - 1);
        }
    }
    
    private void fillRows(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        while(resultSet.next()) {
            DataRow row = new DataRow(this);
            ObservableList<Object> items = new SimpleListProperty<>(FXCollections.<Object>observableArrayList());
            for(int i = 1; i <= metaData.getColumnCount(); i++) {
                String value = resultSet.getString(i);
                items.add(value);
            }
            row.setItems(items);
            rows.add(row);
        }
    }
}
