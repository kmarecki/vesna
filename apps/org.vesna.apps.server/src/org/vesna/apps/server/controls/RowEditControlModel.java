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

import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.vesna.core.lang.ThrowableHelper;
import org.vesna.core.javafx.data.ObservableDataRow;
import org.vesna.core.server.sql.DatabaseService;
import org.vesna.core.sql.MetaDataTable;

/**
 *
 * @author Krzysztof Marecki
 */
public class RowEditControlModel {
    
    private static final Logger logger = Logger.getLogger(RowEditControlModel.class);
    
    public enum Mode {
        Insert,
        Update
    }
    
    private DatabaseService databaseService;
    
    private MetaDataTable table;
    
    public MetaDataTable getTable() {
        return table;
    }
    
    private ObservableDataRow row;
    
    public ObservableDataRow getRow() {
        return row;
    }
    
    private Mode mode;
    
    public Mode getMode() {
        return mode;
    }
    
    public RowEditControlModel(
            DatabaseService databaseService, 
            MetaDataTable table, 
            ObservableDataRow row, 
            Mode mode) {
        
        this.databaseService = databaseService;
        this.table = table;
        this.row = row;
        this.mode = mode;
    }
    
    public Boolean save() {
        try {
        switch (mode) {
            case Insert : {
                databaseService.insertRow(table, row);
            }
            case Update : {
                databaseService.updateRow(table, row);
                break;
            }
        }
        return true;
        } catch (SQLException ex) {
            logger.error(ThrowableHelper.getErrorMessage(ex));
        }
        return false;
    }
}
