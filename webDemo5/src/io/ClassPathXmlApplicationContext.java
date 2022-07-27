package io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory{
    private Map<String,Object> beanMap=new HashMap<>();

    public ClassPathXmlApplicationContext(){
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(resourceAsStream);
            //获取所有bean节点
            NodeList beans = document.getElementsByTagName("bean");
            //获取所有的bean节点
            for (int i = 0; i < beans.getLength(); i++) {
                Node item = beans.item(i);
                if(item.getNodeType()==Node.ELEMENT_NODE){
                    Element beanElement=(Element) item;
                    String id = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class BeanClass = Class.forName(className);
                    Object o = BeanClass.getDeclaredConstructor().newInstance();
//                    Method setServletContext = controllerBeanClass.getDeclaredMethod("setServletContext", ServletContext.class);
//                    setServletContext.invoke(o,this.getServletContext());
                    beanMap.put(id,o);
                }
            }
            //组装bean之间的依赖关系
            for (int i = 0; i < beans.getLength(); i++) {
                Node item = beans.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) item;
                    String id = beanElement.getAttribute("id");
                    NodeList childNodes = beanElement.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item1 = childNodes.item(j);
                        if(item1.getNodeType()==Node.ELEMENT_NODE&&"property".equals(item1.getNodeName())){
                            Element property=(Element) item1;
                            String propertyName = property.getAttribute("name");
                            String ref = property.getAttribute("ref");
                            //找到ref对应的实例
                            Object refObj = beanMap.get(ref);
                            //将refObj设置到当前bean对应的实例的property属性上
                            Object beanObj = beanMap.get(id);
                            Field propertyField = beanObj.getClass().getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj,refObj);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
