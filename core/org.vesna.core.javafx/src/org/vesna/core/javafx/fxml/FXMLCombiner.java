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
package org.vesna.core.javafx.fxml;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.vesna.core.logging.LoggerHelper;
import org.vesna.core.util.StreamHelper;
import org.vesna.core.xml.DocumentHelper;
import org.vesna.core.xml.NodeTypeConstraints;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Krzysztof Marecki
 */
public class FXMLCombiner {
    
    private abstract class CombinerMethod {
        
        public abstract String getElementName();
    }
    
    private class IncludeMethod extends CombinerMethod {
        
        private String source;

        public String getSource() {
            return source;
        }

        public IncludeMethod(String source) {
            this.source = source;
        }

        @Override
        public String getElementName() {
            return "include";
        }
    }
    
    private class ReplaceMethod extends CombinerMethod {
        
        private String attribute;

        public String getAttribute() {
            return attribute;
        }
        
        private String value;

        public String getValue() {
            return value;
        }

        
        public ReplaceMethod(String attribute, String value) {
            this.attribute = attribute;
            this.value = value;
        }

        @Override
        public String getElementName() {
            return "replace";
        }
    }
    
    private class RemoveMethod extends CombinerMethod {
        
        private String attribute;

        public String getAttribute() {
            return attribute;
        }
        
        public RemoveMethod(String attribute) {
            this.attribute = attribute;
        }
        
        @Override
        public String getElementName() {
            return "remove";
        }
    }
    
    private class MethodPoint {
        
        private CombinerMethod method;

        public CombinerMethod getMethod() {
            return method;
        }

        public void setMethod(CombinerMethod method) {
            this.method = method;
        }
        
        private Node methodNode;

        public Node getMethodNode() {
            return methodNode;
        }
        
        public MethodPoint(CombinerMethod method, Node methodNode) {
            this.method = method;
            this.methodNode = methodNode;
        }
    }
    
    private static final Logger logger = Logger.getLogger(FXMLCombiner.class);
    private String fxmlTemplate;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document parsedTemplate;
    private List<MethodPoint> methodPoints;
    
    public void loadTemplate(InputStream stream) {
        fxmlTemplate = StreamHelper.readToEnd(stream);
        parseTemplate();
    }
    
    public String getCombinedFXML() {
        try {
            removeMethodNodes();
            String fxml = DocumentHelper.toString(parsedTemplate);
            return fxml;
        } catch (TransformerException ex) {
            LoggerHelper.logException(logger, ex);
        }
        return "";
    }
    
    public void addFXMLVariable(String variableName, InputStream stream) {
        
    }
    
    public void addAttributeVariable(String variableName, String value) {
        
    }
            
    private void parseTemplate() {
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(fxmlTemplate.getBytes());
            parsedTemplate = builder.parse(stream);
            methodPoints = new ArrayList();
            findComments(parsedTemplate.getChildNodes());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
    
    private void findComments(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            switch(node.getNodeType()) {
                case NodeTypeConstraints.ELEMENT_NODE: {
                    Element element = (Element)node;
                    findComments(element.getChildNodes());
                    break;
                }
                case NodeTypeConstraints.COMMENT_NODE: {
                    Comment comment = (Comment)node;
                    MethodPoint point = parseComment(comment);
                    methodPoints.add(point);
                    break;
                }
            }
        }
    }
    
    private MethodPoint parseComment(Comment comment) {
        CombinerMethod method = new ReplaceMethod("", "");
        MethodPoint point = new MethodPoint(method, comment);
        return point;
    }
    
    private void removeMethodNodes() {
        for(MethodPoint point : methodPoints) {
            Node methodNode = point.methodNode;
            methodNode.getParentNode().removeChild(methodNode);
        }
    }
}
