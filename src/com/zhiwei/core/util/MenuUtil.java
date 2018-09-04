package com.zhiwei.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zhiwei.core.menu.TopModule;

/**
 * 系统的菜单XML转化工具类
 * @author IBM
 *
 */
public class MenuUtil {
	
	private static Log logger=LogFactory.getLog(MenuUtil.class);

	
	/**
	 * 取得一级菜单的Document
	 * @return
	 */
	public static Map<String,String> getOneMenus(){
		Map<String,String> menusMap=new LinkedHashMap<String,String>();
		String menuDir=AppUtil.getMenuAbDir();
		//@DHZCB 菜单调整
//		Document doc = XmlUtil.load( menuDir + "/menu-all.xml");
		Document doc = XmlUtil.load( menuDir +"/"+AppUtil.getConfigMap().get("initXML"));
		
		Element root=doc.getRootElement();
		Iterator<Element> it=root.elementIterator();
		while(it.hasNext()){
			Element el=it.next();
			String name=el.attributeValue("name");
			menusMap.put("Mod_"+name,name);
		}
		return menusMap;
	}
	
	/**
	 * 取得所有菜单是否具有自己的个人桌面
	 * @return
	 */
	public static Map<String,String> getAllDeskMenus(){
		Map<String,String> menusMap=new LinkedHashMap<String,String>();
		String menuDir=AppUtil.getMenuAbDir();
		Document allDoc = XmlUtil.load( menuDir +"/"+AppUtil.getConfigMap().get("initXML"));
		Element root=allDoc.getRootElement();
		Iterator<Element> it=root.elementIterator();
		while(it.hasNext()){
			 Element el=it.next();
			 String name=el.attributeValue("name");
			 String isHaveDesktop=el.attributeValue("isHaveDesktop");
			 String businessName=el.attributeValue("businessName");
			 if(null!=isHaveDesktop && !"".equals(isHaveDesktop) && "true".equals(isHaveDesktop)){
				 Iterator<Element> elet= el.elementIterator();
				 while(elet.hasNext()){
					 Element elt=elet.next();
					 String name1=elt.attributeValue("name");
					 String isUsed=elt.attributeValue("isUsed");
					 String deskName=elt.attributeValue("deskName");
					 if(null!=isUsed && !"".equals(isUsed) && "true".equals(isUsed)){//判断当前桌面是否能用。
						 menusMap.put(name1,deskName);
					 }
					 
				 }
			 }
		 }
		 return menusMap;
	}
	
	/**
	 * 取得所有菜单的Document
	 * @return
	 */
	public static Map<String,Document> getAllOrgMenus(){
		Map<String,Document> menusMap=new LinkedHashMap<String, Document>();
		
		String menuDir=AppUtil.getMenuAbDir();
		
		//@DHZCB 菜单调整
//		Document allDoc = XmlUtil.load( menuDir + "/menu-all.xml");
		Document allDoc = XmlUtil.load( menuDir +"/"+AppUtil.getConfigMap().get("initXML"));
		 
		Element root=allDoc.getRootElement();
		 
		Iterator<Element> it=root.elementIterator();
		 
		while(it.hasNext()){
			 Element el=it.next();
			 
			 String name=el.attributeValue("name");
			 String file=el.attributeValue("file");
			 if(logger.isDebugEnabled()){
				 logger.debug("name:" + name + " file=" + file);
			 }
			 if(name!=null && file!=null){
				logger.info("load the menu config:" + menuDir + "/" + file);
				Document subDoc=XmlUtil.load(menuDir + "/" + file);
				if(subDoc!=null){
					menusMap.put(name, subDoc);
				}
			 }
		 }
		 
		 return menusMap;
	}
	
	public static Map<String,Document> convertByXsl(Map<String,Document> orgMenus,String xslStyle){
		Map<String,Document> covMenus=new LinkedHashMap<String,Document>();
		Iterator<String> keys= orgMenus.keySet().iterator();
		while(keys.hasNext()){
			String key=keys.next();
			Document doc=orgMenus.get(key);
			try{
				Document covDoc=XmlUtil.styleDocument(doc, xslStyle);
				covMenus.put(key, covDoc);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		return covMenus;
	}
	
	public static Map<String,Document> getAllItemsMenus(Map<String,Document> orgMenus){
		String xslStyle=AppUtil.getMenuXslDir() + "/menu-items.xsl";
		return convertByXsl(orgMenus,xslStyle);
	}
	
	public static Map<String,Document> getAllGrantedMenus(Map<String,Document> orgMenus){
		String xslStyle=AppUtil.getMenuXslDir()+"/menu-grant.xsl";
		return convertByXsl(orgMenus,xslStyle);
	}
	
	//Map<String,Document>covMenus
	public static Document mergeOneDoc(Map<String,Document>covMenus){
		Document allDoc=DocumentHelper.createDocument();
		
		Element root=allDoc.addElement("Modules");
		Iterator<String> keys= covMenus.keySet().iterator();
		while(keys.hasNext()){
			String key=keys.next();
			Document doc=covMenus.get(key);
			Element docRoot=doc.getRootElement();
			if(docRoot!=null){				
				root.add(docRoot.createCopy());
			}
		}
		
		if(logger.isDebugEnabled()){
			//logger.debug("menu:" + allDoc.asXML());
		}
		return allDoc;
	}
	
	/**
	 * 取到某个文档中的所有top菜单的导航
	 * @param modDoc
	 * @return
	 */
	public static Map<String,TopModule> getTopModules(Document modDoc){
		
		Map<String,TopModule> topMap=new LinkedHashMap<String,TopModule>();
		Element modulesEl=modDoc.getRootElement();
		Iterator<Element> menusIt=modulesEl.elementIterator();
		int sn=1;
		while(menusIt.hasNext()){
			Element modEl=menusIt.next();
			String id=modEl.attributeValue("id");
			String text=modEl.attributeValue("text");
			String iconCls=modEl.attributeValue("iconCls");
			String isPublic=modEl.attributeValue("isPublic");
			
			TopModule topModule=new TopModule(id,text,iconCls,isPublic,sn++);
			topMap.put(id, topModule);
		}
		
		return topMap;
	}
	/**
	 * 取得公共的头部菜单
	 * @param allModules
	 * @return
	 */
	public static Map<String,TopModule> getPublicTopModules(Map<String,TopModule> allModules){
		Map<String,TopModule> pubMod=new HashMap<String,TopModule>();

		Iterator<Map.Entry<String, TopModule>> it=allModules.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, TopModule> entry=it.next();
			if(entry.getValue().isPublic()){
				pubMod.put(entry.getKey(), entry.getValue());
			}
		}
		return pubMod;
	}
	
	
	
	
	public  static void  main(){
		Map<String,String> menusMap=MenuUtil.getAllDeskMenus();//新增，初始化桌面map
		System.out.println(menusMap.size());
	}
}


