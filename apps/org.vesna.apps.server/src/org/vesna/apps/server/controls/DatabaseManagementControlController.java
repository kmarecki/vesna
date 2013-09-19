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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.vesna.core.javafx.data.DataRow;
import org.vesna.core.javafx.data.DataTable;

/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManagementControlController {
    
    private DatabaseManagementControlModel model;
    
    @FXML
    ListView tablesList;
    @FXML
    TableView rowsTable;
    @FXML 
    Label tableNameLabel;
    
    public void setModel(final DatabaseManagementControlModel model) {
        this.model = model;
        
        tablesList.itemsProperty().bind(model.tablesProperty());
        tablesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> ov, 
                                                            String oldValue, String newValue) {
                                            model.setSelectedTable(newValue);
                                            model.loadTableRows();
                                            addColumns();
                                            bindRows();
					}	
		});
        tableNameLabel.textProperty().bindBidirectional(model.selectedTableProperty());
        
        model.initialize();
    }
    
    private void addColumns() {
        rowsTable.getColumns().clear();
        for(final String columnName : model.getColumns()) {
            TableColumn column = new TableColumn(columnName);
            column.setCellValueFactory(new Callback<CellDataFeatures<DataRow,String>,ObservableValue<String>>(){                   
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<DataRow, String> param) {                                                                                             
                        return new SimpleStringProperty(param.getValue().getString(columnName));                       
                    }                   
                });

            rowsTable.getColumns().add(column);
        }
    }
    
    private void bindRows() {
        DataTable table = model.getRowsTable();
        if (table != null) {
            rowsTable.itemsProperty().bind(table.rowsProperty());
        }
    }
    
}
