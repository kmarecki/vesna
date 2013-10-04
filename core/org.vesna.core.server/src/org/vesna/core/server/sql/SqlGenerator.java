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
package org.vesna.core.server.sql;

import java.util.ArrayList;
import java.util.List;
import org.vesna.core.sql.MetaDataColumn;
import org.vesna.core.sql.MetaDataPrimaryKey;
import org.vesna.core.sql.MetaDataTable;

/**
 *
 * @author Krzysztof Marecki
 */
public class SqlGenerator {
    
    public class QueryParameter {
        
        private int parameterIndex;
         
        public int getParameterIndex() {
            return parameterIndex;
        }

        private String parameterName;

        public String getParameterName() {
            return parameterName;
        }
       

        public QueryParameter(int index, String name) {
            this.parameterIndex = index;
            this.parameterName = name;
        }        

    }
    
    public class Query {
    
        private String sql;

        public String getSql() {
            return sql;
        }

        private Iterable<QueryParameter> parameters;

        public Iterable<QueryParameter> getParameters() {
            return parameters;
        }

        public Query(String sql, Iterable<QueryParameter> parameters) {
            this.sql = sql;
            this.parameters = parameters;
        }
    }
    
    public Query generateInsert(MetaDataTable table) {
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<QueryParameter> parameters = new ArrayList<>();
        
        sql.append(String.format("INSERT INTO %s (", table.getFullTableName()));
        int parameterIndex = 1;
        String separator = "";
        for (MetaDataColumn column : table.getColumns()) {
            if (!column.isIsAutoincrement()) {
                sql.append(separator);
                sql.append(String.format("%s", column.getColumnName()));
                values.append(separator);
                values.append("?");
                parameters.add(new QueryParameter(parameterIndex++, column.getColumnName()));
                separator = ", ";
            }
        }
        sql.append(String.format(") VALUES(%s)", values.toString()));
        
        Query query = new Query(sql.toString(), parameters);
        return query;
    }
    
    public Query generateUpdate(MetaDataTable table) {
        StringBuilder sql = new StringBuilder();
        List<QueryParameter> parameters = new ArrayList<>();
        int parameterIndex = 1;
        
        sql.append(String.format("UPDATE %s SET ", table.getFullTableName()));
        String separator = "";
        for (MetaDataColumn column : table.getColumns()) {
            if (!column.isIsAutoincrement()) {
                sql.append(separator);
                sql.append(String.format("%s = ? ", column.getColumnName()));
                parameters.add(new QueryParameter(parameterIndex++, column.getColumnName()));
                separator = ", ";
            }
        }
        sql.append("WHERE ");
        generatePrimaryKey(table, sql, parameters, parameterIndex);
        
        Query query = new Query(sql.toString(), parameters);
        return query;
    }

    public Query generateDelete(MetaDataTable table) {
        StringBuilder sql = new StringBuilder();
        List<QueryParameter> parameters = new ArrayList<>();
        int parameterIndex = 1;
        
        sql.append(String.format("DELETE FROM %s ", table.getFullTableName()));
        sql.append("WHERE ");
        
        generatePrimaryKey(table, sql, parameters, parameterIndex);
        
        Query query = new Query(sql.toString(), parameters);
        return query;
    }
    
    private void generatePrimaryKey(
            
            MetaDataTable table, 
            StringBuilder sql, 
            List<QueryParameter> parameters,
            int parameterIndex) {
        
        String whereSeparator = "";
        for (MetaDataPrimaryKey key : table.getPrimaryKeys()) {
            sql.append(whereSeparator);
            sql.append(String.format("%s = ? ", key.getColumnName()));
            parameters.add(new QueryParameter(parameterIndex++, key.getColumnName()));
            whereSeparator = "AND ";
        }
    }
}
