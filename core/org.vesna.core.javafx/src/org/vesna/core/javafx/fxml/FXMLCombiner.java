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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.vesna.core.logging.LoggerHelper;
import org.vesna.core.net.ClasspathURLHandler;
import org.vesna.core.xml.DocumentHelper;
import org.vesna.core.xml.NodeHelper;
import org.vesna.core.xml.NodeTypeConstraints;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
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
    
    private class CombinerContext {
    
       private DocumentBuilderFactory factory;
       private DocumentBuilder builder;
       private Map<String, Object> variables;
       
       
       public void initializeContext() throws ParserConfigurationException {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            variables = new HashMap();
       }
       
       public void addVariable(String variableName, Object variableValue) {
           variables.put(variableName, variableValue);
       }
       
       public Object resolveVariable(String name) {
            Object value = name;
            if(name.charAt(0) == '$') {
                String variableName = name.substring(1);
                value = variables.get(variableName);
            } 
            return value;
        } 
    }
    
    private abstract class CombinerMethod {
        public abstract void invoke(Node node, Node parentNode)
            throws TransformerException, SAXException, IOException, DOMException, FXMLCombinerException;
        
        protected Node getChildNode(Document document, String source)
            throws TransformerException, SAXException, IOException, DOMException, FXMLCombinerException {
            InputStream templateStream = (InputStream)context.resolveVariable(source);
            
            if (templateStream == null) {
                String msg = String.format("Cannot find %s", source);
                throw new FXMLCombinerException(msg);
            }
            
            CombinedDocument childNodeCombined = new CombinedDocument();
            childNodeCombined.parseTemplate(templateStream);
            childNodeCombined.combine();
            
            String childNodeXml = childNodeCombined.getCombinedFXML();
            InputStream childNodeStream = new ByteArrayInputStream(childNodeXml.getBytes());
            Document childNodeDocument = context.builder.parse(childNodeStream);
            Node childNode = document.importNode(childNodeDocument.getDocumentElement(), true);
            return childNode;
        }
        
        @SuppressWarnings("empty-statement")
        protected Element getNextElement(Node node, Node parentNode) {
            int methodNodeIndex = NodeHelper.indexOfChildNode(parentNode, node);
            Node nextNode = null;
            while(!((nextNode = parentNode.getChildNodes().item(++methodNodeIndex)) instanceof Element));
            
            return (Element)nextNode;
        }
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
        public void invoke(Node node, Node parentNode) 
            throws TransformerException, SAXException, DOMException, IOException, FXMLCombinerException {
            Node childNode = getChildNode(parentNode.getOwnerDocument(), source);
            parentNode.replaceChild(childNode, node);
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
        public void invoke(Node node, Node parentNode) 
            throws TransformerException, SAXException, DOMException, IOException {
            Node nextNode = getNextElement(node, parentNode);
            Node attributeNode = nextNode.getAttributes().getNamedItem(attribute);
            String resolvedValue = (String)context.resolveVariable(value);
            attributeNode.setNodeValue(resolvedValue);
            parentNode.removeChild(node);
        }
    }
    
    private class ReplaceElementMethod extends CombinerMethod {
        private String source;

        public String getSource() {
            return source;
        }
        
        public ReplaceElementMethod(String source) {
            this.source = source;
        }

        @Override
        public void invoke(Node node, Node parentNode) 
            throws TransformerException, SAXException, IOException, DOMException, FXMLCombinerException {
            Node nextNode = getNextElement(node, parentNode);
            Node childNode = getChildNode(parentNode.getOwnerDocument(), source);
            parentNode.replaceChild(childNode, nextNode);
            parentNode.removeChild(node);
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
        public void invoke(Node node, Node parentNode) 
            throws TransformerException, SAXException, DOMException, IOException {
            int methodNodeIndex = NodeHelper.indexOfChildNode(parentNode, node);
            Element nextNode = (Element)parentNode.getChildNodes().item(methodNodeIndex + 1);
            nextNode.removeAttribute(attribute);
            parentNode.removeChild(node);
        }
    }
    
    private class MethodFactory {
    
        public  CombinerMethod fromXml(Document document) {
            Element element = document.getDocumentElement();
            String methodName = element.getNodeName();
            switch(methodName) {
                case "tfx:include" : {
                    return newIncludeMethod(element);
                }
                case "tfx:remove" : {
                    return newRemoveMethod(element);
                }
                case "tfx:replace" : {
                    return newReplaceMethod(element);
                }
                case "tfx:replace_element" : {
                    return newReplaceElementMethod(element);
                }
                default: {
                    String msg = String.format(
                            "%s is not valid combiner method name", methodName);
                    throw new IllegalArgumentException(msg);
                }
            }
        }
        
        private IncludeMethod newIncludeMethod(Element element) {
            String source = element.getAttribute("source");
            IncludeMethod method = new IncludeMethod(source);
            return method;
        }
        
        private RemoveMethod newRemoveMethod(Element element) {
            String attribute = element.getAttribute("attribute");
            RemoveMethod method = new RemoveMethod(attribute);
            return method;
        }
        
        private ReplaceMethod newReplaceMethod(Element element) {
            String attribute = element.getAttribute("attribute");
            String value = element.getAttribute("value");
            ReplaceMethod method = new ReplaceMethod(attribute, value);
            return method;
        }
        
        private ReplaceElementMethod newReplaceElementMethod(Element element) {
            String source = element.getAttribute("source");
            ReplaceElementMethod method = new ReplaceElementMethod(source);
            return method;
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
        
        private Node parentNode;

        public Node getParentNode() {
            return parentNode;
        }
        
        public MethodPoint(
                CombinerMethod method, 
                Node methodNode,
                Node parentNode) {
            this.method = method;
            this.methodNode = methodNode;
            this.parentNode = parentNode;
        }
        
        public void invokeMethod() 
            throws TransformerException, SAXException, IOException, FXMLCombinerException {
            method.invoke(methodNode, parentNode);
        }
    }
    
    private class CombinedDocument {
        
         private Document parsedTemplate;
         
         private List<MethodPoint> methodPoints;
         
         public Iterable<MethodPoint> getMethodPoints() {
             return methodPoints;
         }
         
         public CombinedDocument() {
         }
         
         public void parseTemplate(InputStream templateStream) {
            try {
                parsedTemplate = context.builder.parse(templateStream);
                methodPoints = new ArrayList();
                findComments(parsedTemplate);
            } catch (SAXException | IOException ex) {
                LoggerHelper.logException(logger, ex);
            }
        }
        
        public void combine() 
            throws TransformerException, SAXException, DOMException, IOException, FXMLCombinerException {
            invokeCombinerMethods();
        }
        
        public String getCombinedFXML() 
                throws TransformerConfigurationException, TransformerException {
            
            String fxml = DocumentHelper.toString(parsedTemplate);
            return fxml;
        }

        private void findComments(Node parentNode) 
                throws SAXException, IOException {
            NodeList nodes = parentNode.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                switch(node.getNodeType()) {
                    case NodeTypeConstraints.ELEMENT_NODE: {
                        Element element = (Element)node;
                        findComments(element);
                        break;
                    }
                    case NodeTypeConstraints.COMMENT_NODE: {
                        Comment comment = (Comment)node;
                        MethodPoint point = parseComment(comment, parentNode);
                        methodPoints.add(point);
                        break;
                    }
                }
            }
        }

        private MethodPoint parseComment(Comment comment, Node parentNode) 
            throws SAXException, IOException {
            String methodXml = comment.getNodeValue();
            Document methodDocument = getMethodDocument(methodXml);
            CombinerMethod method = methodFactory.fromXml(methodDocument);
            MethodPoint point = new MethodPoint(method, comment, parentNode);
            return point;
        }
        
        private Document getMethodDocument(String methodXml)
            throws SAXException, IOException {
             try {
                 InputStream stream = new ByteArrayInputStream(methodXml.getBytes());
                 Document document = context.builder.parse(stream);
                 return document;
             } catch (SAXException ex) {
                 String msg =  String.format("%s is not valid xml for FXMLCombiner.", methodXml);
                 logger.error(msg);
                 throw ex;
             }
        }
        
        private void invokeCombinerMethods() 
            throws TransformerException, SAXException, DOMException, IOException, FXMLCombinerException {
            for(MethodPoint point : methodPoints) {
                point.invokeMethod();
            }
        }
    }
    
    private static final Logger logger = Logger.getLogger(FXMLCombiner.class);
    
    private CombinedDocument rootDocument;
    private CombinerContext context;
    private MethodFactory methodFactory;
    
    private URL templateLocation;
     
    public URL getTemplateLocation() {
        return templateLocation;
    }
    
    public void loadTemplate(String classpath) {
        try {
            templateLocation = new URL(null, "classpath:"+classpath, 
                    new ClasspathURLHandler(Thread.currentThread().getContextClassLoader()));
            InputStream stream = templateLocation.openStream();
            loadTemplate(stream);
        } catch (IOException ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
    
    void loadTemplate(InputStream stream)  {
        try {
            context = new CombinerContext();
            context.initializeContext();
            methodFactory = new MethodFactory();
            rootDocument = new CombinedDocument();
            rootDocument.parseTemplate(stream);
        } catch (ParserConfigurationException ex) {
            LoggerHelper.logException(logger, ex);
        }
    }
    
    public String getCombinedFXML() {
        try {
            rootDocument.combine();
            String fxml = rootDocument.getCombinedFXML();
            logger.info(fxml);
            return fxml;
        } catch (Throwable ex) {
            LoggerHelper.logException(logger, ex);
        }
        return "";
    }
    
    public void addFXMLVariable(String variableName, InputStream stream) {
        context.addVariable(variableName, stream);
    }
    
    public void addAttributeVariable(String variableName, String value) {
         context.addVariable(variableName, value);
    }
}
