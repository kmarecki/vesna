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

import org.vesna.core.data.DataRow;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.vesna.core.lang.StringHelper;
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
    private SqlGenerator sqlGenerator;
    
    public DatabaseService(DatabaseAdapter database) {
        this.database = database;
        this.sqlGenerator = new SqlGenerator();
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
        
        catalog = StringHelper.toUpperCase(catalog);
        schema = StringHelper.toUpperCase(schema);
        table = StringHelper.toUpperCase(table);
        
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
        
        catalog = StringHelper.toUpperCase(catalog);
        schemaPattern = StringHelper.toUpperCase(schemaPattern);
        
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
      
        catalog = StringHelper.toUpperCase(catalog);
        schemaPattern = StringHelper.toUpperCase(schemaPattern);
        tableNamePattern = StringHelper.toUpperCase(tableNamePattern);
        
        List<MetaDataTable> tables = new ArrayList();
        DatabaseMetaData dbmd = getDatabaseMetaData();
        ResultSet resultSet = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types);
        while (resultSet.next()) {
            MetaDataTable table = MetaDataTable.fromResultSet(resultSet);
            tables.add(table);
        }
        return tables;
    }
    
    public MetaDataTable loadTable(MetaDataTable table) throws SQLException {
        List<MetaDataColumn> columns = getColumns(
                null, table.getTableSchema(), table.getTableName(), null);
        List<MetaDataPrimaryKey> primaryKeys = getPrimaryKeys(
                null, table.getTableSchema(), table.getTableName());
        
        table.addColumns(columns);
        table.addPrimaryKeys(primaryKeys);
        
        return table;
    }
    
    public MetaDataTable getLoadedTable(
            String catalog,
            String schemaPattern,
            String tableNamePattern) throws SQLException {
        MetaDataTable table = getTables(
                catalog, schemaPattern, tableNamePattern, null).get(0);
        loadTable(table);
        return table;
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
    
    private void addStatementParameters(
            SqlGenerator.Query query,
            PreparedStatement statement, 
            MetaDataTable table, 
            DataRow row) throws SQLException {
        for (SqlGenerator.QueryParameter param : query.getParameters()) {
            String value = row.getString(param.getParameterName());
            statement.setString(param.getParameterIndex(), value);
        }
    }
    
    private void executeUpdateQuery(
            SqlGenerator.Query query, 
            MetaDataTable table,
            DataRow row) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(query.getSql());
        addStatementParameters(query, statement, table, row);
        statement.executeUpdate();
    }
    
    public void insertRow(MetaDataTable table, DataRow row) throws SQLException {
        SqlGenerator.Query query = sqlGenerator.generateInsert(table);
        executeUpdateQuery(query, table, row);
    }
    
     
    public void updateRow(MetaDataTable table, DataRow row) throws SQLException {
        SqlGenerator.Query query = sqlGenerator.generateUpdate(table);
        executeUpdateQuery(query, table, row);
    }
    
    public void deleteRow(MetaDataTable table, DataRow row) throws SQLException {
        SqlGenerator.Query query = sqlGenerator.generateDelete(table);
        executeUpdateQuery(query, table, row);
    }
    
    private DatabaseMetaData getDatabaseMetaData() throws SQLException {
        Connection connection = database.getConnection();
        DatabaseMetaData dbmd = connection.getMetaData();
        return dbmd;
    }
}
