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
package org.vesna.apps.client.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.vesna.core.app.Core;
import org.vesna.core.client.services.ConnectionsService;
import org.vesna.core.client.services.MasterServiceImpl;
import org.vesna.core.javafx.BaseModelImpl;

/**
 *
 * @author Krzysztof Marecki
 */
public class ServerDiagnosticsControlModel extends BaseModelImpl {
    private final StringProperty serverInfo = new SimpleStringProperty();

    public String getServerInfo() {
        return serverInfo.get();
    }

    public void setServerInfo(String value) {
        serverInfo.set(value);
    }

    public StringProperty serverInfoProperty() {
        return serverInfo;
    }
    
    public void refreshServerInfo() {
        ConnectionsService connectionService = Core.getService(ConnectionsService.class);
        MasterServiceImpl impl = connectionService.getMasterServiceImpl();
        String info = impl.getServerInfo();
        setServerInfo(info);
    }
}
