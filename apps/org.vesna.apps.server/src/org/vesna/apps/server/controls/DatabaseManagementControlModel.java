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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.vesna.core.javafx.data.DataTable;
import org.vesna.core.server.derby.DerbyService;

/**
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManagementControlModel {
    private static final Logger logger = Logger.getLogger(DatabaseManagementControlModel.class);
    private DerbyService derbyService;
    
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
    
    
    private final StringProperty selectedTable = new SimpleStringProperty();

    public String getSelectedTable() {
        return selectedTable.get();
    }

    public void setSelectedTable(String value) {
        selectedTable.set(value);
    }

    public StringProperty selectedTableProperty() {
        return selectedTable;
    }
    
    private final ListProperty<String> columns = new SimpleListProperty<>(FXCollections.<String>observableArrayList());

    public ObservableList<String> getColumns() {
        return columns.get();
    }

    public void setColumns(ObservableList<String> value) {
        columns.set(value);
    }

    public ListProperty columnsProperty() {
        return columns;
    }
    
    private DataTable rowsTable;
    
    public DataTable getRowsTable() {
        return rowsTable;
    }
    
    public DatabaseManagementControlModel() {
        
    }
    
    public DatabaseManagementControlModel(DerbyService derbyService) {
        this.derbyService = derbyService;
    }
    
    public void initialize() {
        loadTables();
    }
    
    public void loadTableRows() {
        loadColumns();
        loadRows();
    }
    
    private void loadTables() {
        try {
            Connection connection = derbyService.getConnection();
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, null, null, null);
            tables.clear();
            while (resultSet.next()) {
//                String tableName = String.format("%s.%s",
//                        resultSet.getString("TABLE_SCHEM"),
//                        resultSet.getString("TABLE_NAME"));
                String tableName = resultSet.getString("TABLE_NAME");
                tables.add(tableName);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private void loadColumns() {
         try {
            Connection connection = derbyService.getConnection();
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet resultSet = dbmd.getColumns(null, null, getSelectedTable(), null);
            columns.clear();
            while (resultSet.next()) {
                String tableName = resultSet.getString("COLUMN_NAME");
                columns.add(tableName);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private void loadRows() {
        rowsTable = null;
        
        try {
           Connection connection = derbyService.getConnection();
           Statement statement = connection.createStatement();
           String sql = String.format("SELECT * FROM sys.%s", getSelectedTable());
           statement.execute(sql);
           ResultSet resultSet = statement.getResultSet();
           rowsTable = DataTable.fromResultSet(resultSet);
        } catch (SQLException ex) {
           logger.error(ex.getMessage());
        }
        
    }
}
