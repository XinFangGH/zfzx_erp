package com.zhiwei.credit.action.menu;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hibernate.Hibernate;

import com.google.gson.Gson;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.AppUserService;


/**
 * @description 显示系统左边的功能菜单
 * @class MenuAction
 * @author csx,YHZ
 * @company www.credit-software.com
 * @createtime 2011-1-27AM
 * 
 */
public class MenuAction extends BaseAction {
    @Resource
	private AppUserService appUserService;;
	/**
	 * 根据权限获取当前用户的菜单
	 * @return
	 */
	private Document getCurDocument(){
		Document doc=getModuleDocument();
		AppUser user=appUserService.get(ContextUtil.getCurrentUser().getUserId());
		Hibernate.initialize(user.getRoles());
		user.init();
		Set<String> rights=user.getRights();
		if(rights.contains(AppRole.SUPER_RIGHTS)){//具有超级管理权限
			return doc;
		}

		Document newDoc=DocumentHelper.createDocument();
		Element root = newDoc.addElement( "Menus" );
		//@DHZCB  增加了非空判断
		if(null!=doc){
			createSubMenus(rights,doc.getRootElement(),root);
		}
		
		if(logger.isDebugEnabled()){
			//logger.debug("XML:" + newDoc.asXML());
		}
		
		return newDoc;
		
	}
	private Document getModuleDocument(){
		String topMenuId=getRequest().getParameter("topMenuId");
		if(StringUtils.isEmpty(topMenuId)){
			topMenuId="credit";
		}

		Document doc=AppUtil.getItemsMenus().get(topMenuId.toLowerCase());
		
		return doc;
		
	}
	
	private void createSubMenus(Set<String>rights,Element curNodes,Element newCurNodes){
		List els = curNodes.elements();
		if(els.size()==0) return ;
		
		for (int i = 0; i < els.size(); i++) {
			Element el = (Element) els.get(i);
			String id = el.attributeValue("id");
			if(id!=null){
				if(rights.contains(id)){
					Element newNodes=newCurNodes.addElement(el.getName());
					Iterator<Attribute> it=el.attributeIterator();
					
					while(it.hasNext()){
						Attribute at=it.next();
						newNodes.addAttribute(at.getName(),at.getValue());
					}
					createSubMenus(rights,el,newNodes);
				}
			}			
		}
	}
	

	/**
	 * 返回当前用户左树的菜单
	 * @return
	 */
	public String panelTree(){
		
		String isReload=getRequest().getParameter("isReload");
		//reload the menu
		if("true".equals(isReload)){
			
			AppUtil.reloadMenu();
		}
		
		Gson gson=new Gson();
		
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		StringBuffer sb = new StringBuffer("[");
	    if(flag){  //如果是集团版本
			
			Object obj=this.getSession().getAttribute("RoleType");
			if(null==obj){  //如果还没选择
				
			}
			else{
				Document doc=getCurDocument();
				if (doc != null) {
					Element root = doc.getRootElement();
					List els = root.elements();
		
					for (int i = 0; i < els.size(); i++) {
						Element el = (Element) els.get(i);
						
						Attribute id = el.attribute("id");
						Attribute text = el.attribute("text");
						Attribute iconCls = el.attribute("iconCls");
						if(null!=id && id.getValue().equals("GroupControl")){
							String isGroupVersion=AppUtil.getSystemIsGroupVersion();
							if(null!=isGroupVersion && Boolean.valueOf(isGroupVersion)){
								sb.append("{id:'").append(id == null ? "" : id.getValue()).append("',");
								sb.append("text:'").append(text == null ? "" : text.getValue()).append("',");
								sb.append("iconCls:'").append(iconCls == null ? "" : iconCls.getValue()).append("',");
								sb.append("subXml:").append(gson.toJson(getModelXml(doc,id.getValue())))
								.append("},");
							}
						}
						else{
							sb.append("{id:'").append(id == null ? "" : id.getValue()).append("',");
							sb.append("text:'").append(text == null ? "" : text.getValue()).append("',");
							sb.append("iconCls:'").append(iconCls == null ? "" : iconCls.getValue()).append("',");
							sb.append("subXml:").append(gson.toJson(getModelXml(doc,id.getValue())))
							.append("},");
						}
					}
					if(els.size()>0){
						sb.deleteCharAt(sb.length()-1);
					}
				}
			}
		}
	    else{
	    	Document doc=getCurDocument();
			if (doc != null) {
				Element root = doc.getRootElement();
				List els = root.elements();
	
				for (int i = 0; i < els.size(); i++) {
					Element el = (Element) els.get(i);
					
					Attribute id = el.attribute("id");
					Attribute text = el.attribute("text");
					Attribute iconCls = el.attribute("iconCls");
					if(null!=id && id.getValue().equals("GroupControl")){
						String isGroupVersion=AppUtil.getSystemIsGroupVersion();
						if(null!=isGroupVersion && Boolean.valueOf(isGroupVersion)){
							sb.append("{id:'").append(id == null ? "" : id.getValue()).append("',");
							sb.append("text:'").append(text == null ? "" : text.getValue()).append("',");
							sb.append("iconCls:'").append(iconCls == null ? "" : iconCls.getValue()).append("',");
							sb.append("subXml:").append(gson.toJson(getModelXml(doc,id.getValue())))
							.append("},");
						}
					}
					else{
						sb.append("{id:'").append(id == null ? "" : id.getValue()).append("',");
						sb.append("text:'").append(text == null ? "" : text.getValue()).append("',");
						sb.append("iconCls:'").append(iconCls == null ? "" : iconCls.getValue()).append("',");
						sb.append("subXml:").append(gson.toJson(getModelXml(doc,id.getValue())))
						.append("},");
					}
				}
				if(els.size()>0){
					sb.deleteCharAt(sb.length()-1);
				}
			}
	    }
		sb.append("]");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 返回某个模板的XML菜单
	 * @param doc
	 * @param modelId
	 * @return
	 */
	protected String getModelXml(Document doc,String modelId){
		StringBuffer sb=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r");
		
		Element el=doc.getRootElement();
		
		List nodes=el.selectNodes("/Menus/Items[@id='"+modelId+"']/*");
		
		sb.append("<Menus>\r");
		for(int i=0;i<nodes.size();i++){
			Node node=(Node)nodes.get(i);
			sb.append(node.asXML());
		}
		sb.append("\r</Menus>\r");
		
		return sb.toString();
	}

}
