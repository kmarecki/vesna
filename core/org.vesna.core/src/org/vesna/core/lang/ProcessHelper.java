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
package org.vesna.core.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 *
 * @author Krzysztof Marecki
 */
public class ProcessHelper {
    
    public static Thread StartInSeparateThread(final String[] commands, final Logger logger) {
          Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Process process;
                try {
                    logger.info("Current directory is " + System.getProperty("user.dir"));
                    ProcessBuilder builder = new ProcessBuilder(commands);

                    process = builder.start();
                    InputStream outputStream = process.getInputStream();
                    InputStreamReader outputStreamReader = new InputStreamReader(outputStream);
                    BufferedReader outputBufferedReader = new BufferedReader(outputStreamReader);
                    
                    String line;
                    while((line = outputBufferedReader.readLine()) != null) {
                        logger.info(line);
                    }   

                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                } 
            }
        });
        thread.start();
        return thread;
    }
}
