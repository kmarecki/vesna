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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.vesna.core.javafx.JavaFXUtils;
import org.vesna.core.javafx.data.DataRow;
import org.vesna.core.javafx.data.DataTable;
import org.vesna.core.sql.MetaDataColumn;
import org.vesna.core.sql.MetaDataTable;

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
    
    @FXML
    MenuItem deleteTableMenuItem;
    
    @FXML
    private void handleActionDeleteTable(ActionEvent event) {
        model.deleteSelectedTable();
    }
    
    @FXML
    private void handleValidateDeleteTable(Event event) {
        Boolean disabled = (model.getSelectedTable() == null);
        deleteTableMenuItem.setDisable(disabled);
    }
    
    @FXML
    private void handleActionAddRow(ActionEvent event) {
        
    }
    
    @FXML
    private void handleActionEditRow(ActionEvent event) {
        Stage stage = new Stage();
        RowEditControl control = new RowEditControl();
        stage.setScene(new Scene(control));
        stage.setTitle("Edit row");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    @FXML
    private void handleActionDeleteRow(ActionEvent event) {
        
    }
            
    public void setModel(final DatabaseManagementControlModel model) {
        this.model = model;
        
        tablesList.itemsProperty().bind(model.tablesProperty());
        tablesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MetaDataTable>() {
					@Override
					public void changed(ObservableValue<? extends MetaDataTable> ov, 
                                                            MetaDataTable oldValue, MetaDataTable newValue) {
                                            model.setSelectedTable(newValue);
                                            model.loadTableRows();
                                            bindRows();
                                            addColumns();
                                            setTableNameLabel();
					}	
		});
         tablesList.setCellFactory(new Callback<ListView<MetaDataTable>, ListCell<MetaDataTable>>() {
            @Override
            public ListCell<MetaDataTable> call(ListView<MetaDataTable> p) {
                ListCell<MetaDataTable> cell = new ListCell<MetaDataTable>() {
                    @Override
                    public void updateItem(MetaDataTable item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(String.format("%s.%s", item.getTableSchema(), item.getTableName()));
                        }
                    };
                };
                return cell;
            }
        });
        
        model.initialize();
    }
    
    private void addColumns() {
        rowsTable.getColumns().clear();
        MetaDataTable metatable = model.getSelectedTable();
        if (metatable != null) {
            for(final MetaDataColumn metacolumn : metatable.getColumns()) {
                final String columnName = metacolumn.getColumnName();
                TableColumn column = new TableColumn(columnName);
                column.setMinWidth(JavaFXUtils.getTextWithMarginWidth(columnName));
                column.setCellValueFactory(new Callback<CellDataFeatures<DataRow,String>,ObservableValue<String>>(){                   
                        @Override
                        public ObservableValue<String> call(CellDataFeatures<DataRow, String> param) {                                                                                             
                            return new SimpleStringProperty(param.getValue().getString(columnName));                       
                        }                   
                    });

                rowsTable.getColumns().add(column);
            }
        }
    }
    
    private void bindRows() {
        DataTable table = model.getRowsTable();
        if (table != null) {
            rowsTable.itemsProperty().bind(table.rowsProperty());
        }
    }
    
    private void setTableNameLabel() {
        MetaDataTable table = model.getSelectedTable();
        tableNameLabel.setText(table != null ? table.getTableName() : "");
    }
    
}
