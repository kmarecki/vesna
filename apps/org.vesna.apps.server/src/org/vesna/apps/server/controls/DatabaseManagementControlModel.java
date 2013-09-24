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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.vesna.core.javafx.data.DataTable;
import org.vesna.core.server.derby.DerbyService;
import org.vesna.core.sql.MetaDataTable;

/**
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManagementControlModel {
    
    
    private static final Logger logger = Logger.getLogger(DatabaseManagementControlModel.class);
    private DerbyService derbyService;
    
    private final ListProperty<MetaDataTable> tables = new SimpleListProperty<>(FXCollections.<MetaDataTable>observableArrayList());

    public ObservableList<MetaDataTable> getTables() {
        return tables.get();
    }

    public void setTables(ObservableList<MetaDataTable> value) {
        tables.set(value);
    }

    public ListProperty tablesProperty() {
        return tables;
    }
    
    
    private final ObjectProperty<MetaDataTable> selectedTable = new ObjectPropertyBase<MetaDataTable>() {

        @Override
        public Object getBean() {
          return null;
        }

        @Override
        public String getName() {
           return "";
        }
    };

    public MetaDataTable getSelectedTable() {
        return selectedTable.get();
    }

    public void setSelectedTable(MetaDataTable value) {
        selectedTable.set(value);
    }

    public ObjectProperty<MetaDataTable> selectedTableProperty() {
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
    
    public void deleteSelectedTable() {
        MetaDataTable table = getSelectedTable();
        if (table != null) {
            deleteTable(getSelectedTable());
            initialize();
        }
    }
    
    private void loadTables() {
        try {
            DatabaseMetaData dbmd = getDatabaseMetaData();
            ResultSet resultSet = dbmd.getTables(null, null, null, null);
            tables.clear();
            while (resultSet.next()) {
                MetaDataTable table = MetaDataTable.fromResultSet(resultSet);
                tables.add(table);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private void loadColumns() {
         columns.clear();
         MetaDataTable table = getSelectedTable();
         if (table != null) {
            try {
               DatabaseMetaData dbmd = getDatabaseMetaData();
               ResultSet resultSet = dbmd.getColumns(
                       null, table.getTableSchema(), table.getTableName(), null);
               while (resultSet.next()) {
                   String tableName = resultSet.getString("COLUMN_NAME");
                   columns.add(tableName);
               }
            } catch (SQLException ex) {
               logger.error(ex.getMessage());
            }
        }
    }
    
    private void loadRows() {
        rowsTable = null;
        MetaDataTable table = getSelectedTable();
        if (table != null) {
            try {
               Connection connection = derbyService.getConnection();
               Statement statement = connection.createStatement();
               String sql = String.format("SELECT * FROM %s", table.getFullTableName());
               statement.execute(sql);
               ResultSet resultSet = statement.getResultSet();
               rowsTable = DataTable.fromResultSet(resultSet);
            } catch (SQLException ex) {
               logger.error(ex.getMessage());
            }
        }
    }
    
    private void deleteTable(MetaDataTable table) {
        try {
            Connection connection = derbyService.getConnection();
            Statement statement = connection.createStatement();
            String sql = String.format("DROP TABLE %s", table.getFullTableName());
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
           logger.error(ex.getMessage());
        }
    }
    
    private DatabaseMetaData getDatabaseMetaData() throws SQLException {
        Connection connection = derbyService.getConnection();
        DatabaseMetaData dbmd = connection.getMetaData();
        return dbmd;
    }
}
