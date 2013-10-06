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
package org.vesna.core.javafx;

import java.text.Format;
import javafx.scene.control.TableCell;

/**
 *
 * @author Krzysztof Marecki
 */
public class FormattedTableCell<S, T> extends TableCell<S, T> {
    
    private Format format;

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
    
    @Override
    public void updateItem(T item, boolean empty) {
        if (item == getItem()) {
            return;
        }
        super.updateItem(item, empty);

        String text = (item != null) ?
                      (format != null ? format.format(item) : item.toString()) :
                       null;
        super.setText(text);
        super.setGraphic(null);
    }
}
