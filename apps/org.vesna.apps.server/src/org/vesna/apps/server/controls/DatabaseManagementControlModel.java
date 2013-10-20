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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.vesna.core.javafx.data.ObservableDataRow;
import org.vesna.core.javafx.data.ObservableDataTable;
import org.vesna.core.logging.LoggerHelper;
import org.vesna.core.server.sql.DatabaseService;
import org.vesna.core.sql.MetaDataColumn;
import org.vesna.core.sql.MetaDataPrimaryKey;
import org.vesna.core.sql.MetaDataSchema;
import org.vesna.core.sql.MetaDataTable;

/**
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManagementControlModel {
    
    private static final Logger logger = Logger.getLogger(DatabaseManagementControlModel.class);
    
    private DatabaseService databaseService;
    
    public DatabaseService getDatabaseService() {
        return databaseService;
    }
    
    private final ListProperty<MetaDataSchema> schemas = new SimpleListProperty<>(FXCollections.<MetaDataSchema>observableArrayList());

    public ObservableList<MetaDataSchema> getSchemas() {
        return schemas.get();
    }

    public void setSchemas(ObservableList<MetaDataSchema> value) {
        schemas.set(value);
    }

    public ListProperty schemasProperty() {
        return schemas;
    }
    
    private final ObjectProperty<MetaDataSchema> selectedSchema = new SimpleObjectProperty();

    public MetaDataSchema getSelectedSchema() {
        return selectedSchema.get();
    }

    public void setSelectedSchema(MetaDataSchema value) {
        selectedSchema.set(value);
        loadTables();
    }

    public ObjectProperty<MetaDataSchema> selectedSchemaProperty() {
        return selectedSchema;
    }
    
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
    
    
    private final ObjectProperty<MetaDataTable> selectedTable = new SimpleObjectProperty();

    public MetaDataTable getSelectedTable() {
        return selectedTable.get();
    }

    public void setSelectedTable(MetaDataTable value) {
        selectedTable.set(value);
        loadTableRows();
    }

    public ObjectProperty<MetaDataTable> selectedTableProperty() {
        return selectedTable;
    }
    
    
    private final ObjectProperty<ObservableDataRow> selectedRow = new SimpleObjectProperty();

    public ObservableDataRow getSelectedRow() {
        return selectedRow.get();
    }

    public void setSelectedRow(ObservableDataRow value) {
        selectedRow.set(value);
    }

    public ObjectProperty selectedRowProperty() {
        return selectedRow;
    }
    
    
    
    private final ListProperty<String> columns = new SimpleListProperty<>(FXCollections.<String>observableArrayList());
    
    private ObjectProperty<ObservableDataTable> rowsTable = new SimpleObjectProperty();
    
    public ObservableDataTable getRowsTable() {
        return rowsTable.get();
    }
  
    public void setRowsTable(ObservableDataTable value) {
        rowsTable.set(value);
    }
    
    public ObjectProperty rowsTableProperty() {
        return rowsTable;
    }
   
    public DatabaseManagementControlModel(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    
    public void initialize() {
        loadSchemas();
    }
    
    public RowEditControlModel createRowEditModel(
            ObservableDataRow row, 
            RowEditControlModel.Mode mode) {
        MetaDataTable table = getSelectedTable();
        RowEditControlModel model = new RowEditControlModel(
            databaseService, table, row, mode, new Runnable() {
                @Override
                public void run() {
                    loadTableRows();
                }
            });
        return model;
    }
    
    public void deleteSelectedTable() {
        MetaDataTable table = getSelectedTable();
        if (table != null) {
            deleteTable(table);
            initialize();
        }
    }
    
    public void deleteSelectedRow() {
        MetaDataTable table = getSelectedTable();
        ObservableDataRow row = getSelectedRow();
        if (table != null) {
            deleteRow(table, row);
        }
        loadTableRows();
    }
    
    private void loadSchemas() {
        try {
            schemas.clear();
            List<MetaDataSchema> list = databaseService.getSchemas(
                    null, null);
            for (MetaDataSchema schema : list) {
                schemas.add(schema);
            }
        } catch (Throwable ex) {
             LoggerHelper.logException(logger, ex);
        }
    }
    
    private void loadTables() {
        try {
            tables.clear();
            MetaDataSchema schema = getSelectedSchema();
            if (schema != null) {
                List<MetaDataTable> list = databaseService.getTables(
                        null, schema.getTableSchema(), null, null);
                for (MetaDataTable table : list) {
                    tables.add(table);
                }
            }
        } catch (SQLException ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
    
     private void loadTableRows() {
        loadColumns();
        loadRows();
    }
    
    private void loadColumns() {
        columns.clear();
        MetaDataTable table = getSelectedTable();
        if (table != null) {
            try {
                if (table.getColumns().isEmpty()) {
                    List<MetaDataColumn> list = databaseService.getColumns(
                            null, table.getTableSchema(), table.getTableName(), null);
                    table.addColumns(list);
                }  
                if (table.getPrimaryKeys().isEmpty()) {
                    List<MetaDataPrimaryKey> list = databaseService.getPrimaryKeys(
                            null, table.getTableSchema(), table.getTableName());
                    table.addPrimaryKeys(list);
                }
            } catch (SQLException ex) {
                LoggerHelper.logException(logger, ex);
            }
        }
    }
    
    private void loadRows() {
        MetaDataTable table = getSelectedTable();
        if (table != null) {
            try {
               ResultSet resultSet = databaseService.selectAll(table);
               setRowsTable(ObservableDataTable.fromResultSet(resultSet));
            } catch (SQLException ex) {
                LoggerHelper.logException(logger, ex);
            }
        }
    }
    
    private void deleteTable(MetaDataTable table) {
        try {
            databaseService.deleteTable(table);
        } catch (SQLException ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
    
    private void deleteRow(MetaDataTable table, ObservableDataRow row) {
        try {
            databaseService.deleteRow(table, row);
        } catch (SQLException ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
}
