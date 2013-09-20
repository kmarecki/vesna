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
package org.vesna.core.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Krzysztof Marecki
 */
public class MetaDataTable {
    
    private String tableSchema;

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
    
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public static MetaDataTable fromResultSet(ResultSet resultSet) throws SQLException {
        MetaDataTable table = new MetaDataTable();
        table.setTableSchema(resultSet.getString("TABLE_SCHEM"));
        table.setTableName(resultSet.getString("TABLE_NAME"));
        return table;
    }
}
