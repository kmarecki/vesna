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
package org.vesna.core.server.derby;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.vesna.core.lang.ProcessHelper;
import org.vesna.core.server.sql.DatabaseAdapter;

/**
 *
 * @author Krzysztof Marecki
 */
public class DerbyService implements DatabaseAdapter {
    private static final Logger logger = Logger.getLogger(DerbyService.class);
    private String databaseName;
    private Connection connection;
    
    public DerbyService(String databaseName) {
        this.databaseName = databaseName;
    }
    
    private String getDerbyRunPath() {
        String path = String.format("dist%1$slib%1$sderbyrun.jar", File.separator);
        return path;
    }
    private void runStandaloneServer() {
        String[] commands = {"java", "-jar", getDerbyRunPath(), "server", "start"};
        ProcessHelper.StartInSeparateThread(commands, logger);
    }
    
    private Boolean isDatabaseExists() {
        File databaseDirectory =  new File(System.getProperty("user.dir"), databaseName);
        return databaseDirectory.exists();
    }
   
    private String getDatabaseUrl() {
        String url = String.format("jdbc:derby://localhost:1527/%s", databaseName);
        return url;
    }
    
    public Boolean isRunning() {
        Boolean create = !isDatabaseExists();
        String url = getDatabaseUrl();
        if (create) {
            url = url.concat(";create=true");
        }
        try {
            Connection testConnection = DriverManager.getConnection(url);
            testConnection.close();
            if (create) {
                logger.info(String.format("Database %s has been created", databaseName));
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return true;
    }
    
    public void check() {
        Boolean isRunning = isRunning();
        
        if (!isRunning) {
            runStandaloneServer();
            isRunning = isRunning();
        }
        
        if (isRunning) {
            logger.info(String.format("Derby server is running, connection to database %s successful.", databaseName));
        } else {
            logger.error("Derby Server cannot be started");
        }
    }
    
    private void shutdownDatabase() {
        String url = getDatabaseUrl();
         url = url.concat(";shutdown=true");
        try {
            DriverManager.getConnection(url);
            logger.info("Derby server has been shut down");
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private void shutdownServer() {
        String url = "jdbc:derby://localhost:1527/;shutdown=true";
        try {
           DriverManager.getConnection(url);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private void shutdownStandaloneServer() {
        String[] commands = {"java", "-jar", getDerbyRunPath(), "server", "shutdown"};
        ProcessHelper.StartInSeparateThread(commands, logger);
    } 
    
    public void shutdown() {
        shutdownStandaloneServer();
        logger.info("Derby server has been shut down");
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = getDatabaseUrl();
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }
}
