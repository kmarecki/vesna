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

package org.vesna.core.server.logging;

import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author Krzysztof Marecki
 */
public class ObservableAppender extends AppenderSkeleton {

	private static ArrayList<LogEntry> entries = new ArrayList<>();
	private static ArrayList<LogEntriesObserver> observers = new ArrayList<>();
	
	public static void addObserver(LogEntriesObserver observer) {
		observers.add(observer);
	}
	
	public static void removeObserver(LogEntriesObserver observer) {
		observers.remove(observer);
	}
	
	public static Iterable<LogEntry> getAllEntries() {
		return entries;
	}
	
	private static void addLogEntry(LogEntry entry) {
		entries.add(entry);
		for(LogEntriesObserver observer : observers) {
			observer.addNewLogEntry(entry);
		}
	}
	
	@Override
	protected void append(LoggingEvent le) {
		System.out.println(le.getMessage());
		
		LogEntry entry = new LogEntry();
		entry.setMessage(le.getMessage().toString());
		entry.setEntryType(getEntryTypeFromLevel(le.getLevel()));
		entry.setDate(new Date(le.getTimeStamp()));
		addLogEntry(entry);
	}
	
	private LogEntryType getEntryTypeFromLevel(Level level) {
		switch(level.toInt()) {
			case Level.INFO_INT : return LogEntryType.Info;
			case Level.WARN_INT : return LogEntryType.Warning;
			case Level.ERROR_INT : return LogEntryType.Error;
			case Level.FATAL_INT : return LogEntryType.Fatal;
			default : return LogEntryType.Unknown;
		}
	}

	@Override
	public void close() {
		
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}
}
