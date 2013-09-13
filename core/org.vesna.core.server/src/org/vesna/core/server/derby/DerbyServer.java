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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Krzysztof Marecki
 */
public class DerbyServer {
    private static final Logger logger = Logger.getLogger(DerbyServer.class);
    private String databaseName;
    
    public DerbyServer(String databaseName) {
        this.databaseName = databaseName;
    }
    
    public void runStandaloneServer() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                String[] commands = {"java", "-jar", "dist\\lib\\derbyrun.jar", "server", "start"};
                Process process;
                try {
                    logger.info("Current directory is " + System.getProperty("user.dir"));
                    ProcessBuilder builder = new ProcessBuilder(commands);
             
                    process = builder.start();
                    InputStream outputStream = process.getInputStream();
                    InputStreamReader outputStreamReader = new InputStreamReader(outputStream);
                    BufferedReader outputBufferedReader = new BufferedReader(outputStreamReader);

                    while(true) {
                        String line;
                        while((line = outputBufferedReader.readLine()) != null) {
                            logger.info(line);
                        }
                        Thread.sleep(500);
                    }

                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                } catch (InterruptedException ex) {
                    
                }
            }
        });
        thread.start();
    }
    
    public Boolean isRunningAndExists() {
        String url = String.format("jdbc:derby:directory%s;create=true", databaseName);
        try {
            Connection connection = DriverManager.getConnection(url);
            connection.close();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return true;
    }
}
