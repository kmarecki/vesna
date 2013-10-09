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
package org.vesna.core.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Krzysztof Marecki
 */
public class NodeHelper {
    public static int indexOfChildNode(Node parentNode, Node childNode) {
        NodeList nodes = parentNode.getChildNodes();
        int index = -1;
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i) == childNode) {
                index = i;
                break;
            }
        }
        return index;
    }
}
