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

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.vesna.core.sql.MetaDataColumn;


/**
 * 
 *
 * @author Krzysztof Marecki
 */
public class RowEditControlController  {

  
   private RowEditControlModel model;
   private Stage stage;
   
   @FXML
   private VBox mainVBox;
   @FXML
   private GridPane columnsGrid;
   
   @FXML
   private void handleActionSave(ActionEvent event){
       stage.close();
   }
   
   @FXML
   private void handleActionCancel(ActionEvent event){
       
   }
   
   public void setModel(RowEditControlModel model) {
       this.model = model;
       addTextBox();
   }
   
   public void setStage(Stage stage) {
       this.stage = stage;
   }
   
   private void addTextBox() {
       int rowIndex = 1;
       for (MetaDataColumn column : model.getTable().getColumns()) {
           final String columnName = column.getColumnName();
           
           Label columnLabel = new Label();
           columnLabel.setAlignment(Pos.CENTER_RIGHT);
           columnLabel.setText(columnName);
           GridPane.setConstraints(columnLabel, 1, rowIndex);
           
           TextField columnText = new TextField();
           columnText.setEditable(true);
           columnText.setText(model.getRow().getString(columnName));
           columnText.textProperty().bindBidirectional(new SimpleObjectProperty<String>() {
               @Override
               public String get() {
                   return model.getRow().getString(columnName);
               }
               @Override
               public void set(String value) {
                   model.getRow().setString(columnName, value);
               }
           });
           GridPane.setConstraints(columnText, 2, rowIndex);
           
           columnsGrid.getChildren().addAll(columnLabel, columnText);
           rowIndex++;
       }
   }
}
