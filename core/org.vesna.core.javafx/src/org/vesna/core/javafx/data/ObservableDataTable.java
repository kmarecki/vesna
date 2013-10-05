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
import org.vesna.core.data.DataRow;
import org.vesna.core.data.DataTable;

/**
 *
 * @author Krzysztof Marecki
 */
public class ObservableDataTable implements DataTable {
    Map<String, Integer> columnNames;
    
    private ListProperty<ObservableDataRow> rows = new SimpleListProperty<>(FXCollections.<ObservableDataRow>observableArrayList());
    
    public ObservableList<ObservableDataRow> getRows() {
        return rows;
    }
    
    public ListProperty rowsProperty() {
        return rows;
    }
    
    @Override
    public DataRow newRow() {
         ObservableDataRow row = new ObservableDataRow(this);
         Object[] arr = new Object[columnNames.size()];
         ObservableList<Object> items = new SimpleListProperty<>(FXCollections.observableArrayList(arr));
         row.setItems(items);
         return row;
    }
    
    public static ObservableDataTable fromResultSet(ResultSet resultSet) throws SQLException {
        ObservableDataTable table = new ObservableDataTable();
        
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
        while(resultSet.next()) {
            ObservableDataRow row = (ObservableDataRow)newRow(); 
            for (Integer columnIndex : columnNames.values()) {
                String value = resultSet.getString(columnIndex + 1);
                row.setString(columnIndex, value);
            }
            rows.add(row);
        }
    }
}
