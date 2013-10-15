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
package org.vesna.core.server.hibernate;

import java.io.File;
import org.apache.log4j.Logger;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Krzysztof Marecki
 */
public class HibernateHelper {

        private static final Logger logger = Logger.getLogger(HibernateHelper.class);
        
	private static SessionFactory sessionFactory;
	
        private static String mappingsJar;

        public static String getMappingsJar() {
            return mappingsJar;
        }

        public static void setMappingsJar(String jarPath) {
           mappingsJar = jarPath;
        }
	
	public static SessionFactory getSessionFactory() {
                if (sessionFactory == null) {
                    try {
			// Create the SessionFactory from standard (hibernate.cfg.xml) 
			// config file.
                        AnnotationConfiguration configuration = new AnnotationConfiguration();
                        if (mappingsJar != null) {
                            File jar = new File(System.getProperty("user.dir"), mappingsJar);
                            if (!jar.exists()) {
                                logger.error(String.format("%s mappings jar does not exists", jar.getPath()));
                            } else {
                                configuration.addJar(jar);
                            }
                        }
			sessionFactory = configuration.configure().buildSessionFactory();
                    } catch (Throwable ex) {
                            logger.error(ex.getMessage());
                    }
                }
		return sessionFactory;
	}
}
