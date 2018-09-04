package com.zhiwei.credit.service.sms.util;

import java.io.ByteArrayInputStream;  

 import java.io.ByteArrayOutputStream;  

 import java.io.InputStream;  

 import java.io.OutputStreamWriter;  

 import java.util.Iterator;  

 import java.util.Vector;  

 import javax.xml.parsers.DocumentBuilder;  

 import javax.xml.parsers.DocumentBuilderFactory;  

 import javax.xml.transform.OutputKeys;  

 import javax.xml.transform.Result;  

 import javax.xml.transform.Source;  

 import javax.xml.transform.Transformer;  

 import javax.xml.transform.TransformerConfigurationException;  

 import javax.xml.transform.TransformerException;  

 import javax.xml.transform.TransformerFactory;  

 import javax.xml.transform.dom.DOMSource;  

 import javax.xml.transform.stream.StreamResult;  

 import org.w3c.dom.Document;  

 import org.w3c.dom.Element;  

 import org.w3c.dom.Node;  

 import org.w3c.dom.NodeList;  

 import org.w3c.dom.Text;  

    

 public class DomTest {  

     Vector students_Vector;  

    

     private Vector readXMLFile(String file) throws Exception {  

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  

         DocumentBuilder builder = dbf.newDocumentBuilder();  

         Document doc = builder.parse(file); // ��ȡ��xml�ļ�  

         // ���濪ʼ��ȡ  

         Element root = doc.getDocumentElement(); // ��ȡ��Ԫ��  

         NodeList students = root.getElementsByTagName("ѧ��");  

         students_Vector = new Vector();  

         for (int i = 0; i < students.getLength(); i++) {  

             // һ��ȡ��ÿһ��ѧ��Ԫ��  

             Element ss = (Element) students.item(i);  

    

             // ����һ��ѧ���ʵ��  

             student stu = new student();  

             stu.setSex(ss.getAttribute("�Ա�"));  

    

             NodeList names = ss.getElementsByTagName("����");  

             Element e = (Element) names.item(0);  

             Node t = e.getFirstChild();  

             stu.setName(t.getNodeValue());  

    

             NodeList ages = ss.getElementsByTagName("����");  

             e = (Element) ages.item(0);  

             t = e.getFirstChild();  

             stu.setAge(Integer.parseInt(t.getNodeValue()));  

    

             students_Vector.add(stu);  

         }  

         return students_Vector;  

     }  

    

     /**  

      * ���ܣ�ֱ����ȡXML��ʽ�ַ��е���Ϣ  

      */ 

     private Vector readXMLString(String xmlStr) throws Exception {  

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  

         DocumentBuilder builder = dbf.newDocumentBuilder();  

         InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes());  

         Document doc = builder.parse(inputStream); //  

         // ���濪ʼ��ȡ  

         Element root = doc.getDocumentElement(); // ��ȡ��Ԫ��  

         NodeList students = root.getElementsByTagName("ѧ��");  

         students_Vector = new Vector();  

         for (int i = 0; i < students.getLength(); i++) {  

             // һ��ȡ��ÿһ��ѧ��Ԫ��  

             Element ss = (Element) students.item(i);  

    

             // ����һ��ѧ���ʵ��  

             student stu = new student();  

             stu.setSex(ss.getAttribute("�Ա�"));  

    

             NodeList names = ss.getElementsByTagName("����");  

             Element e = (Element) names.item(0);  

             Node t = e.getFirstChild();  

             stu.setName(t.getNodeValue());  

    

             NodeList ages = ss.getElementsByTagName("����");  

             e = (Element) ages.item(0);  

             t = e.getFirstChild();  

             stu.setAge(Integer.parseInt(t.getNodeValue()));  

    

             students_Vector.add(stu);  

         }  

         return students_Vector;  

     }  
     
     
     public String readXMLString4TagName(String xmlStr,String tagName) throws Exception {  

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  

         DocumentBuilder builder = dbf.newDocumentBuilder();  

         InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes());  

         Document doc = builder.parse(inputStream); //  

         // ���濪ʼ��ȡ  

         Element root = doc.getDocumentElement(); // ��ȡ��Ԫ��  

         NodeList nodes = root.getElementsByTagName(tagName); 
         Element e = (Element) nodes.item(0);  

         Node t = e.getFirstChild();  

         return t.getNodeValue(); 
     }  

    

     // ��Document���� д��XML�ַ�����  

     private String callWriteXmlString(Document doc, String encoding) {  

    

         try {  

             Source source = new DOMSource(doc);  

             ByteArrayOutputStream outStream = new ByteArrayOutputStream();  

             OutputStreamWriter write = new OutputStreamWriter(outStream);  

             Result result = new StreamResult(write);  

    

             Transformer xformer = TransformerFactory.newInstance()  

                     .newTransformer();  

             xformer.setOutputProperty(OutputKeys.ENCODING, encoding);  

    

             xformer.transform(source, result);  

             return outStream.toString();  

    

         } catch (TransformerConfigurationException e) {  

             e.printStackTrace();  

             return null;  

         } catch (TransformerException e) {  

             e.printStackTrace();  

             return null;  

         }  

    

     }  

    

     /**  

      * ���ܣ����XML��ʽ���ַ�  

      */ 

     public String writeXMLString() {  

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  

         DocumentBuilder builder = null;  

         try {  

             builder = dbf.newDocumentBuilder();  

         } catch (Exception e) {  

         }  

         Document doc = builder.newDocument();  

    

         Element root = doc.createElement("ѧ�����");  

         doc.appendChild(root); // ����Ԫ����ӵ��ĵ���  

    

         // ��ȡѧ����Ϣ  

         for (int i = 0; i < students_Vector.size(); i++) {  

             student s = (student) students_Vector.get(i);  

             // ����һ��ѧ��  

             Element stu = doc.createElement("ѧ��");  

             stu.setAttribute("�Ա�", s.getSex());  

             root.appendChild(stu);// �������  

    

             // �����ı�����ڵ�  

             Element name = doc.createElement("����");  

             stu.appendChild(name);  

             Text tname = doc.createTextNode(s.getName());  

             name.appendChild(tname);  

    

             // �����ı�����ڵ�  

             Element age = doc.createElement("����");  

             stu.appendChild(age); // ��age��ӵ�ѧ��ڵ���  

             Text tage = doc.createTextNode(String.valueOf(s.getAge()));  

             age.appendChild(tage); // ���ı��ڵ����age�ڵ���  

         }  

         try {  

             String result = callWriteXmlString(doc, "gb2312");  

             return result;  

         } catch (Exception e) {  

             e.printStackTrace();  

             return null;  

         }  

     }  

    

     /**  

      * ������  

      */ 

     public static void main(String args[]) {  

         String str = "xml\\student.xml";  

         DomTest t = new DomTest();  

         System.out.println("����ԭʼXML�ļ���");  

         try {  

             Vector v = t.readXMLFile(str);  

             Iterator it = v.iterator();  

             while (it.hasNext()) {  

                 student s = (student) it.next();  

                 System.out.println(s.getName() + "\t" + s.getAge() + "\t" 

                         + s.getSex());  

             }  

         } catch (Exception e) {  

             e.printStackTrace();  

    

         }  

         String xmlStr = t.writeXMLString();  

         System.out.println("\n��ɵ�XML�ַ�\n" + xmlStr);  

         try {  

             Vector v = t.readXMLString(xmlStr);  

             Iterator it = v.iterator();  

             System.out.println("\n������ɵ�XML�ַ�");  

             while (it.hasNext()) {  

                 student s = (student) it.next();  

                 System.out.println(s.getName() + "\t" + s.getAge() + "\t" 

                         + s.getSex());  

             }  

         } catch (Exception e) {  
             e.printStackTrace();  
         }  
     }  
 }  
 
 class student {  
     private String sex;  
     private String name;  
     private int age;  

     public int getAge() {  

         return age;  
     }  

    

     public void setAge(int age) {  

         this.age = age;  

     }  

    

     public void setSex(String s) {  

         sex = s;  

     }  

    

     public String getSex() {  

         return sex;  

     }  

    

     public void setName(String n) {  

         name = n;  

     }  

    

     public String getName() {  

         return name;  

     }  

 } 
