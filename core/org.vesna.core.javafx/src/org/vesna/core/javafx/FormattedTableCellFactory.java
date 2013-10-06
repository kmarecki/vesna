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
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 *
 * @author Krzysztof Marecki
 */
public class FormattedTableCellFactory<S, T> 
    implements Callback<TableColumn<S, T>, TableCell<S, T>> {
     
    private Pos alignment = Pos.CENTER_LEFT;

    public Pos getAlignment() {
        return alignment;
    }

    public void setAlignment(Pos alignment) {
        this.alignment = alignment;
    }

    private TextAlignment textAlignment = TextAlignment.LEFT;

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment alignment) {
        this.textAlignment = alignment;
    }
    
    private Format format;

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
    
    public FormattedTableCellFactory() { }
    
    public FormattedTableCellFactory(
            Pos alignment, 
            TextAlignment textAlignment, 
            Format format) {
        this.alignment = alignment;
        this.textAlignment = textAlignment;
        this.format = format;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        FormattedTableCell<S, T> cell = newCell();
        cell.setAlignment(alignment);
        cell.setTextAlignment(textAlignment);
        cell.setFormat(format);
        return cell;
    }
    
    public FormattedTableCell newCell() {
        return new FormattedTableCell();
    }
}
