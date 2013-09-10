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
package org.vesna.core.javafx.controls;

import java.io.IOException;
import org.vesna.core.server.logging.LogEntry;
import org.vesna.core.server.logging.LogEntryType;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import static org.vesna.core.server.logging.LogEntryType.Error;
import static org.vesna.core.server.logging.LogEntryType.Fatal;
import static org.vesna.core.server.logging.LogEntryType.Info;
import static org.vesna.core.server.logging.LogEntryType.Warning;

/**
 *
 * @author Krzysztof Marecki
 */
public class LogsListController implements Initializable {

	private LogsListViewModel model;
	
	@FXML
	private ComboBox logtypeComboBox;
	
	@FXML
	private TableView logsTableView;
	@FXML
	private TableColumn typeColumn;
	@FXML
	private TableColumn dateColumn;
	@FXML
	private TableColumn messageColumn;
	
	@FXML
	private Label labelWarningsCount;
	@FXML
	private Label labelErrorsCount;
	@FXML
	private Label labelFatalsCount;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		model = new LogsListViewModel();
		model.initialize();
		
		logtypeComboBox.itemsProperty().bind(model.logTypesProperty());
		logtypeComboBox.getSelectionModel().select(model.getSelectedLogType());
		logtypeComboBox.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> ov, 
										String oldValue, String newValue) {
						model.setSelectedLogType(newValue);
					}	
		});
		
		typeColumn.setCellValueFactory(
				new PropertyValueFactory<LogEntry, LogEntryType>("entryType")
		);
		typeColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(TableColumn param) {
				return new TableCell<LogEntry, LogEntryType>() {
					@Override
					public void updateItem(LogEntryType type, boolean empty) {
						super.updateItem(type, empty);
						TableRow currentRow = getTableRow();
						if (currentRow != null && type != null) {
							setText(type.toString());
							switch(type) {
								case Info: {
									currentRow.setStyle("-fx-control-inner-background: palegreen;");
									break;
								}
								case Warning: {
									currentRow.setStyle("-fx-control-inner-background: yellow;");
									break;
								}
								case Error: {
									currentRow.setStyle("-fx-control-inner-background: coral;");
									break;
								}
								case Fatal: {
									currentRow.setStyle("-fx-control-inner-background: red;");
									break;
								}
							}
						}
					}
				};
			}
		});
		
		dateColumn.setCellValueFactory(
				new PropertyValueFactory<LogEntry, Date>("date")
		);
		messageColumn.setCellValueFactory(
				new PropertyValueFactory<LogEntry, String>("message")
		);
		messageColumn.prefWidthProperty().bind(logsTableView.widthProperty().subtract(252));
		logsTableView.itemsProperty().bind(model.logEntriesProperty());
		
		labelWarningsCount.textProperty().bind(model.warningsCountProperty().asString());
		labelErrorsCount.textProperty().bind(model.errorsCountProperty().asString());
		labelFatalsCount.textProperty().bind(model.fatalsCountProperty().asString());
	}
}