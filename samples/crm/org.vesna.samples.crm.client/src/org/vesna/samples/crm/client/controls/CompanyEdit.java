/*
 * Copyright 2013 Krzysztof Marecki
 *
 * Licensed under te Apache License, Version 2.0 (the "License");
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
package org.vesna.samples.crm.client.controls;

import org.vesna.apps.client.controls.EntitiesEdit;
import org.vesna.core.javafx.fxml.FXMLCombiner;

/**
 *
 * @author Krzysztof Marecki
 */
public class CompanyEdit
    extends EntitiesEdit<CompanyEditModel, CompanyEditController> {
    
    
    @Override
    protected FXMLCombiner getCombiner() {
        FXMLCombiner combiner = super.getCombiner();
        combiner.addAttributeVariable("CONTROLLER", CompanyEditController.class.getName());
        combiner.addFXMLVariable("ENTITIES_ROW_VIEW", this.getClass().getResourceAsStream("CompanyEditView.fxml"));
        return combiner;
    }
}
