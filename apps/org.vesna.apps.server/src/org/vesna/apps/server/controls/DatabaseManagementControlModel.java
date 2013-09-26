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
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.vesna.core.javafx.data.DataRow;
import org.vesna.core.javafx.data.DataTable;
import org.vesna.core.server.sql.DatabaseService;
import org.vesna.core.sql.MetaDataColumn;
import org.vesna.core.sql.MetaDataSchema;
import org.vesna.core.sql.MetaDataTable;

/**
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManagementControlModel {
    
    private static final Logger logger = Logger.getLogger(DatabaseManagementControlModel.class);
    private DatabaseService databaseService;
    
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
    
    
    private final ObjectProperty<DataRow> selectedRow = new SimpleObjectProperty();

    public DataRow getSelectedRow() {
        return selectedRow.get();
    }

    public void setSelectedRow(DataRow value) {
        selectedRow.set(value);
    }

    public ObjectProperty selectedRowProperty() {
        return selectedRow;
    }
    
    
    
    private final ListProperty<String> columns = new SimpleListProperty<>(FXCollections.<String>observableArrayList());
    
    private DataTable rowsTable;
    
    public DataTable getRowsTable() {
        return rowsTable;
    }
    
   
    public DatabaseManagementControlModel(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    
    public void initialize() {
        loadSchemas();
    }
    
    public void deleteSelectedTable() {
        MetaDataTable table = getSelectedTable();
        if (table != null) {
            deleteTable(getSelectedTable());
            initialize();
        }
    }
    
    private void loadSchemas() {
        try {
            schemas.clear();
            List<MetaDataSchema> list = databaseService.getSchemas(
                    null, null);
            for (MetaDataSchema schema : list) {
                schemas.add(schema);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
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
            logger.error(ex.getMessage());
        }
    }
    
     private void loadTableRows() {
        loadColumns();
        loadRows();
    }
    
    private void loadColumns() {
         columns.clear();
         MetaDataTable table = getSelectedTable();
         if (table != null && table.getColumns().isEmpty()) {
            try {
               List<MetaDataColumn> list = databaseService.getColumns(
                       null, table.getTableSchema(), table.getTableName(), null);
               table.addColumns(list);
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
               ResultSet resultSet = databaseService.selectAll(table);
               rowsTable = DataTable.fromResultSet(resultSet);
            } catch (SQLException ex) {
               logger.error(ex.getMessage());
            }
        }
    }
    
    private void deleteTable(MetaDataTable table) {
        try {
            databaseService.deleteTable(table);
        } catch (SQLException ex) {
           logger.error(ex.getMessage());
        }
    }
}
