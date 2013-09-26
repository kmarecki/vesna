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
public class MetaDataSchema {
    
    private String tableSchema;

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
    
    private String tableCatalog;

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }
    
    public static MetaDataSchema fromResultSet(ResultSet resultSet) throws SQLException {
        MetaDataSchema schema = new MetaDataSchema();
        schema.setTableSchema(resultSet.getString("TABLE_SCHEM"));
        schema.setTableCatalog(resultSet.getString("TABLE_CATALOG"));
        return schema;
    }


}
