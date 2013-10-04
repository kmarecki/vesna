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
import javafx.scene.control.ComboBox;
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
import org.vesna.core.javafx.data.ObservableDataRow;
import org.vesna.core.javafx.data.ObservableDataTable;
import org.vesna.core.sql.MetaDataColumn;
import org.vesna.core.sql.MetaDataSchema;
import org.vesna.core.sql.MetaDataTable;

/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class DatabaseManagementControlController {
    
    class SchemaListCell extends ListCell<MetaDataSchema> {
        @Override
        public void updateItem(MetaDataSchema item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getTableSchema());
            }
        }
    }
    
    class TableListCell extends ListCell<MetaDataTable> {
        @Override
        public void updateItem(MetaDataTable item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(String.format("%s.%s", item.getTableSchema(), item.getTableName()));
            }
        }
    }
    
    private DatabaseManagementControlModel model;
    
    @FXML
    ComboBox schemasCombo;
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
    private void handleActionInsertRow(ActionEvent event) {
        ObservableDataRow row = (ObservableDataRow)model.getRowsTable().newRow();
        showEditRowForm(RowEditControlModel.Mode.Insert, row);
    }
    
    @FXML
    private void handleActionUpdateRow(ActionEvent event) {
        ObservableDataRow row = model.getSelectedRow();
        showEditRowForm(RowEditControlModel.Mode.Update, row);
    }
    
    @FXML
    private void handleActionDeleteRow(ActionEvent event) {
        ObservableDataRow row = model.getSelectedRow();
        model.deleteSelectedRow();
    }
    
    private void showEditRowForm(RowEditControlModel.Mode mode, ObservableDataRow row) {
        Stage stage = new Stage();
        RowEditControl control = new RowEditControl();
        stage.setScene(new Scene(control));
        stage.setTitle(mode.toString());
        stage.initModality(Modality.APPLICATION_MODAL);
        
        RowEditControlModel controlModel = model.createRowEditModel(row, mode);
        control.getController().setModel(controlModel);
        control.getController().setStage(stage);
        stage.show();
    }
            
    public void setModel(final DatabaseManagementControlModel model) {
        this.model = model;
        
        schemasCombo.itemsProperty().bind(model.schemasProperty());
        schemasCombo.setButtonCell(new SchemaListCell());
        schemasCombo.setCellFactory(new Callback<ListView<MetaDataSchema>, ListCell<MetaDataSchema>>() {
            @Override
            public ListCell<MetaDataSchema> call(ListView<MetaDataSchema> p) {
                ListCell<MetaDataSchema> cell = new SchemaListCell();
                return cell;
            }
        });
        schemasCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MetaDataSchema>() {
            @Override
            public void changed(ObservableValue<? extends MetaDataSchema> ov, 
                                MetaDataSchema oldValue, MetaDataSchema newValue) {
                model.setSelectedSchema(newValue);
            }
        });
        tablesList.itemsProperty().bind(model.tablesProperty());
        tablesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MetaDataTable>() {
            @Override
            public void changed(ObservableValue<? extends MetaDataTable> ov, 
                                MetaDataTable oldValue, MetaDataTable newValue) {
                model.setSelectedTable(newValue); 
                bindRows();
                addColumns();
                setTableNameLabel();
            }	
        });
        tablesList.setCellFactory(new Callback<ListView<MetaDataTable>, ListCell<MetaDataTable>>() {
            @Override
            public ListCell<MetaDataTable> call(ListView<MetaDataTable> p) {
                ListCell<MetaDataTable> cell = new TableListCell();
                return cell;
            }
        });
        rowsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ObservableDataRow>() {
            @Override
            public void changed(ObservableValue<? extends ObservableDataRow> ov, 
                                ObservableDataRow oldValue, ObservableDataRow newValue) {
                model.setSelectedRow(newValue);
            }
        });
        
        model.initialize();
        schemasCombo.getSelectionModel().selectFirst();
    }
    
    private void addColumns() {
        rowsTable.getColumns().clear();
        MetaDataTable metatable = model.getSelectedTable();
        if (metatable != null) {
            for(final MetaDataColumn metacolumn : metatable.getColumns()) {
                final String columnName = metacolumn.getColumnName();
                TableColumn column = new TableColumn(columnName);
                column.setMinWidth(JavaFXUtils.getTextWithMarginWidth(columnName));
                column.setCellValueFactory(new Callback<CellDataFeatures<ObservableDataRow,String>,ObservableValue<String>>(){                   
                        @Override
                        public ObservableValue<String> call(CellDataFeatures<ObservableDataRow, String> param) {                                                                                             
                            return new SimpleStringProperty(param.getValue().getString(columnName));                       
                        }                   
                    });

                rowsTable.getColumns().add(column);
            }
        }
    }
    
    private void bindRows() {
        ObservableDataTable table = model.getRowsTable();
        if (table != null) {
            rowsTable.itemsProperty().bind(table.rowsProperty());
        }
    }
    
    private void setTableNameLabel() {
        MetaDataTable table = model.getSelectedTable();
        tableNameLabel.setText(table != null ? table.getTableName() : "");
    }
    
}
