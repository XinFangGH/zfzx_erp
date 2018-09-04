package com.zhiwei.core.util;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * XML读写工具
 * @author csx
 *
 */
public class XmlUtil {
	
	private static final Log logger=LogFactory.getLog(XmlUtil.class);
	public static final String CONFIG_FILE_NAME = "zhiwei.xml";// 系统配置文件名称
	public static final String WEBCONFIG_FILE_NAME = "webServices.xml";// 系统配置文件名称
	public static final String OBLIGATIONCONFIG_FILE_OB = "obligationMappingConfig.xml";// 债权匹配配置文件
	
	public static Document getSystemConfigXML(){
		
		try{
		   Document document = null;
		   File configFile = null;
		   String configFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()+"zhiwei.xml";
		   configFile = new File(configFilePath);
		   SAXReader saxReader = new SAXReader();
		   document = saxReader.read(configFile);
		   return document;}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Document getWebServicesConfigXML(){
		
		try{
		   Document document = null;
		   File configFile = null;
		   String configFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()+WEBCONFIG_FILE_NAME;
		   configFile = new File(configFilePath);
		   SAXReader saxReader = new SAXReader();
		   document = saxReader.read(configFile);
		   return document;}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把Document对象转成XML String
	 * @param document
	 * @return
	 */
	public static String docToString(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			OutputFormat format = new OutputFormat("  ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			logger.error("docToString error:"+ex.getMessage());
		}
		return s;
	}
	/**
	 * 把XML String转成Document对象
	 * @param s
	 * @return
	 */
	public static Document stringToDocument(String s) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(s);
		} catch (Exception ex) {
			logger.error("stringToDocument error:"+ex.getMessage());
		}
		return doc;
	}
	
	/**
	 * 把Document对象转成XML对象
	 * @param document
	 * @param filename
	 * @return
	 */
	public static boolean docToXmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(
					 new FileOutputStream(new File(filename)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			logger.error("docToXmlFile error:"+ex.getMessage());
		}
		return flag;
	}
	/**
	 * 把String XML转成XML文件
	 * @param str
	 * @param filename
	 * @return
	 */
	public static boolean stringToXmlFile(String str, String filename) {
		boolean flag = true;
		try {
			Document doc = DocumentHelper.parseText(str);
			flag = docToXmlFile(doc, filename);
		} catch (Exception ex) {
			flag = false;
			logger.error("stringToXmlFile error:"+ex.getMessage());
		}
		return flag;
	}
	/**
	 * 加载一个XML文件转成Document对象
	 * @param filename
	 * @return
	 */
	public static Document load(String filename) {
		return load(new File(filename));
	}
	
	public static Document load(File file){
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			document = saxReader.read(file);
		} catch (Exception ex) {
			logger.error("load XML File error:"+ex.getMessage());
		}
		return document;
	}
	
	/**
	 * 加载一个XML文件转成Document对象
	 * @param filename
	 * @return
	 */
	public static Document load(String filename,String encode) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("encode");
			document = saxReader.read(new File(filename));
		} catch (Exception ex) {
			logger.error("load XML File error:"+ex.getMessage());
		}
		return document;
	}
	/**
	 * 通过流加载一个XML文档对象
	 * @param is
	 * @return
	 */
	public static Document load(InputStream is){
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("UTF-8");
			document = saxReader.read(is);
		} catch (Exception ex) {
			logger.error("load XML File error:"+ex.getMessage());
		}
		return document;
	}

	
	/**
	 * 通过流加载一个XML文档对象
	 * @param is
	 * @return
	 */
	public static Document load(InputStream is,String encode){
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding(encode);
			document = saxReader.read(is);
		} catch (Exception ex) {
			logger.error("load XML File error:"+ex.getMessage());
		}
		return document;
	}
	
	public static Document styleDocument(
	        Document document, 
	        String stylesheet
	    ) throws Exception {

	        // load the transformer using JAXP
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer( 
	            new StreamSource( stylesheet ) 
	        );

	        // now lets style the given document
	        DocumentSource source = new DocumentSource( document );
	        DocumentResult result = new DocumentResult();
	        transformer.transform( source, result );

	        // return the transformed document
	        Document transformedDoc = result.getDocument();
	        return transformedDoc;
	    }
public static Document getobligationConfigXML(){
		
		try{
		   Document document = null;
		   File configFile = null;
		   String configFilePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()+OBLIGATIONCONFIG_FILE_OB;
		   configFile = new File(configFilePath);
		   SAXReader saxReader = new SAXReader();
		   document = saxReader.read(configFile);
		   return document;}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
