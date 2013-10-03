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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.vesna.core.sql.MetaDataColumn;
import org.vesna.core.sql.MetaDataPrimaryKey;
import org.vesna.core.sql.MetaDataSchema;
import org.vesna.core.sql.MetaDataTable;

/**
 *
 * @author Krzysztof Marecki
 */
public class DatabaseService {
    
    private final DatabaseAdapter database;
    
    public DatabaseService(DatabaseAdapter database) {
        this.database = database;
    }
    
     public List<MetaDataColumn> getColumns(
            String catalog,
            String schemaPattern,
            String tableNamePattern,
            String columnNamePattern
            ) throws SQLException {
        
        List<MetaDataColumn> columns = new ArrayList();
        DatabaseMetaData dbmd = getDatabaseMetaData();
        ResultSet resultSet = dbmd.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
        while (resultSet.next()) {
            MetaDataColumn column = MetaDataColumn.fromResultSet(resultSet);
            columns.add(column);
        }
        return columns;
    }
    
    public List<MetaDataPrimaryKey> getPrimaryKeys(
            String catalog,
            String schema,
            String table
            ) throws SQLException {
        
        List<MetaDataPrimaryKey> primaryKeys = new ArrayList();
        DatabaseMetaData dbmd = getDatabaseMetaData();
        ResultSet resultSet = dbmd.getPrimaryKeys(catalog, schema, table);
        while (resultSet.next()) {
            MetaDataPrimaryKey primaryKey = MetaDataPrimaryKey.fromResultSet(resultSet);
            primaryKeys.add(primaryKey);
        }
        return primaryKeys;
    }
    
    public List<MetaDataSchema> getSchemas(
            String catalog,
            String schemaPattern) throws SQLException {
        
        List<MetaDataSchema> schemas = new ArrayList();
        DatabaseMetaData dbmd = getDatabaseMetaData();
        ResultSet resultSet = dbmd.getSchemas(catalog, schemaPattern);
        while (resultSet.next()) {
            MetaDataSchema schema = MetaDataSchema.fromResultSet(resultSet);
            schemas.add(schema);
        }
        return schemas;
    }
    
    public List<MetaDataTable> getTables(
            String catalog,
            String schemaPattern,
            String tableNamePattern,
            String[] types) throws SQLException {
        
        List<MetaDataTable> tables = new ArrayList();
        DatabaseMetaData dbmd = getDatabaseMetaData();
        ResultSet resultSet = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types);
        while (resultSet.next()) {
            MetaDataTable table = MetaDataTable.fromResultSet(resultSet);
            tables.add(table);
        }
        return tables;
    }
    
    public void deleteTable(MetaDataTable table) throws SQLException {
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sql = String.format("DROP TABLE %s", table.getFullTableName());
        statement.executeUpdate(sql);
    }
     
    public ResultSet selectAll(MetaDataTable table) throws SQLException {
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sql = String.format("SELECT * FROM %s", table.getFullTableName());
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();
        return resultSet;
    }
    
    private String constructSQLInsert(MetaDataTable table) {
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        
        sql.append(String.format("INSERT INTO %s (", table.getFullTableName()));
        Boolean firstColumn = true;
        for (MetaDataColumn column : table.getColumns()) {
            if (!column.isIsAutoincrement()) {
                if (!firstColumn) {
                    sql.append(", ");
                    values.append(", ");
                }
                sql.append(String.format("%s", column.getColumnName()));
                values.append("?");
                firstColumn = false;
            }
        }
        sql.append(String.format(") VALUES(%s)", values.toString()));
        
        return sql.toString();
    }
    
    private void addStatementParameters(PreparedStatement statement, MetaDataTable table, DataRow row) throws SQLException {
        int parameterIndex = 1;
        for (MetaDataColumn column : table.getColumns()) {
            if (!column.isIsAutoincrement()) {
                String value = row.getString(column.getColumnName());
                statement.setString(parameterIndex, value);
                parameterIndex++;
            }
        }
    }
    
    public void insertRow(MetaDataTable table, DataRow row) throws SQLException {
        Connection connection = database.getConnection();
        String sql = constructSQLInsert(table);
        PreparedStatement statement = connection.prepareStatement(sql);
        addStatementParameters(statement, table, row);
        statement.executeUpdate();
    }
    
    public void updateRow(MetaDataTable table, DataRow row) {
        
    }
    
    public void deleteRow(MetaDataTable table) {
        
    }
    
    private DatabaseMetaData getDatabaseMetaData() throws SQLException {
        Connection connection = database.getConnection();
        DatabaseMetaData dbmd = connection.getMetaData();
        return dbmd;
    }
}
