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
public class MetaDataColumn {
    
    private String columnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    private boolean isNullable;

    public boolean isIsNullable() {
        return isNullable;
    }

    public void setIsNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    private boolean isAutoincrement;

    public boolean isIsAutoincrement() {
        return isAutoincrement;
    }

    public void setIsAutoincrement(boolean isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }
    
     public static MetaDataColumn fromResultSet(ResultSet resultSet) throws SQLException {
         MetaDataColumn column = new MetaDataColumn();
         column.setColumnName(resultSet.getString("COLUMN_NAME"));
         column.setIsNullable(resultSet.getString("IS_NULLABLE").equals("YES"));
         column.setIsAutoincrement(resultSet.getString("IS_AUTOINCREMENT").equals("YES"));
         return column;
     }
}
