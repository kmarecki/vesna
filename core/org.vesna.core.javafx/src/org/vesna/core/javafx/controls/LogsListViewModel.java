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
package org.vesna.core.javafx.controls;

import org.vesna.core.server.logging.LogEntriesObserver;
import org.vesna.core.server.logging.LogEntry;
import org.vesna.core.server.logging.LogEntryType;
import org.vesna.core.server.logging.ObservableAppender;
import java.util.Collections;
import java.util.Comparator;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Krzysztof Marecki
 */
public final class LogsListViewModel implements LogEntriesObserver {
	
	public LogsListViewModel() {
		logTypes.add("All");
		logTypes.add(LogEntryType.Info.toString());
		logTypes.add(LogEntryType.Warning.toString());
		logTypes.add(LogEntryType.Error.toString());
		logTypes.add(LogEntryType.Fatal.toString());
		
		setSelectedLogType(getLogTypes().get(0));
	}
	
	private final ListProperty<String> logTypes = new SimpleListProperty<>(FXCollections.<String>observableArrayList());

	public ObservableList<String> getLogTypes() {
		return logTypes.get();
	}

	public void setLogTypes(ObservableList<String> value) {
		logTypes.set(value);
	}

	public ListProperty logTypesProperty() {
		return logTypes;
	}
	private final StringProperty selectedLogType = new SimpleStringProperty();

	public String getSelectedLogType() {
		return selectedLogType.get();
	}

	public void setSelectedLogType(String value) {
		selectedLogType.set(value);
		filterLogEntries();
	}

	public StringProperty selectedLogTypeProperty() {
		return selectedLogType;
	}
	
	
	private final ListProperty<LogEntry> logEntries = new SimpleListProperty<>(FXCollections.<LogEntry>observableArrayList());

	public ObservableList getLogEntries() {
		return logEntries.get();
	}

	public void setLogEntries(ObservableList value) {
		logEntries.set(value);
	}

	public ListProperty logEntriesProperty() {
		return logEntries;
	}
	private final IntegerProperty warningsCount = new SimpleIntegerProperty();

	public int getWarningsCount() {
		return warningsCount.get();
	}

	public void setWarningsCount(int value) {
		warningsCount.set(value);
	}

	public IntegerProperty warningsCountProperty() {
		return warningsCount;
	}
	private final IntegerProperty errorsCount = new SimpleIntegerProperty();

	public int getErrorsCount() {
		return errorsCount.get();
	}

	public void setErrorsCount(int value) {
		errorsCount.set(value);
	}

	public IntegerProperty errorsCountProperty() {
		return errorsCount;
	}
	private final IntegerProperty fatalsCount = new SimpleIntegerProperty();

	public int getFatalsCount() {
		return fatalsCount.get();
	}

	public void setFatalsCount(int value) {
		fatalsCount.set(value);
	}

	public IntegerProperty fatalsCountProperty() {
		return fatalsCount;
	}
	
	

	public void initialize() {
		ObservableAppender.addObserver(this);
	}
	
	@Override
	public void addNewLogEntry(final LogEntry entry) {
                Platform.runLater(new Runnable() {
                    @Override 
                    public void run() {
                        addEntry(entry);
                        sortLogEntries();
                     }
                });
        }
	
	private void addEntry(LogEntry entry) {
		if (getSelectedLogType().equals(entry.getEntryType().toString()) || 
			getSelectedLogType().equals(getLogTypes().get(0))) {
				logEntries.add(entry);
				switch(entry.getEntryType()) {
					case Warning : {
						setWarningsCount(getWarningsCount() + 1);
						break;
					}
					case Error : {
						setErrorsCount(getErrorsCount() + 1);
						break;
					}
					case Fatal : {
						setFatalsCount(getFatalsCount() + 1);
					}
				}
			}
	}
	
	private void filterLogEntries() {
		logEntries.clear();
		setWarningsCount(0);
		setErrorsCount(0);
		setFatalsCount(0);
		
		for (LogEntry entry : ObservableAppender.getAllEntries()) {
			addEntry(entry);
		}
		
		sortLogEntries();
	}

	private void sortLogEntries() {
		Collections.sort(logEntries, new Comparator<LogEntry>(){
			@Override
			public int compare(LogEntry o1, LogEntry o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		});
	}	
}

