package com.zhiwei.credit.action.menu;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.web.action.BaseAction;

/**
 * @description 图标管理操作
 * @class IconAction
 * @extend BaseAction
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-2-16PM
 * 
 */
public class IconAction extends BaseAction {

	/**
	 * icon.xml文件地址
	 */
	private static String xmlPath = AppUtil.getAppAbsolutePath().replace("\\",
			"/")
			+ "/css/icon.xml";

	private String id;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	public String list(){
		List<Element> list = null;
		Element element = getXMLDocument().getRootElement(); // Element对象
		if(id != null && !id.trim().equals("") && !id.trim().equals("0")){ // 根据id查询
			List<Element> elist = element.selectNodes("//Items[@id='" + id + "']");
			if(elist != null && elist.size() > 0)
				list = elist.get(0).elements("icon");
		} else
			list = element.selectNodes("//icon");
		StringBuffer dt = new StringBuffer(",icon:[");
		for(int i = 0 ;i < list.size() ; i++){
			Element ae = list.get(i);
			String text = ae.attributeValue("text");
			String url = ae.attributeValue("url");;
			dt.append("{").append("'text':'" + text + "','url':'" + url + "'").append("}");
			if (i != list.size() - 1)
				dt.append(",");
		}
		dt.append("]");
		String msg = "{success:true" + dt.toString() + "}";
		setJsonString(msg);
		return SUCCESS;
	}
	
	/*
	 * 加载tree节点操作
	 */
	@SuppressWarnings("unchecked")
	public String loadTree() {
		StringBuffer sbf = new StringBuffer("");
		sbf.append("[{id:'0',text:'" + AppUtil.getCompanyName()
				+ "',expanded:true,children:[");
		Document document = getXMLDocument();
		if (document != null) {
			Element root = document.getRootElement();
			List<Element> list = root.elements();
			Iterator<Element> it = list.iterator();
			int iCount = 0;
			while (it.hasNext()) {
				Element el = it.next();
				String ntn = el.getName();
				boolean bo = (ntn != null && "items".equalsIgnoreCase(ntn)) ? true
						: false;
				if (!bo)
					break;
				sbf.append(getElementValue(el));
				iCount++;
			}
			if (list != null && list.size() == iCount)
				sbf.deleteCharAt(sbf.length() - 1);
		}
		sbf.append("]}]");
		setJsonString(sbf.toString());
		return SUCCESS;
	}

	/*
	 * 查询子节点
	 */
	@SuppressWarnings("unchecked")
	private String findChidNode(Element el) {
		List<Element> list = el.elements();
		StringBuffer bf = new StringBuffer("");
		if (list == null || list.size() == 0)
			bf.append("leaf:true},");
		else {
			int iCount = 0;
			StringBuffer subStr = new StringBuffer("children:[");
			for (Element e : list) {
				String ntn = e.getName();
				boolean bo = (ntn != null && "items".equalsIgnoreCase(ntn)) ? true
						: false;
				if (!bo)
					break;
				subStr.append(getElementValue(e));
				iCount++;
			}
			if (list.size() == iCount && iCount != 0) {
				subStr.deleteCharAt(subStr.length() - 1);
				subStr.append("]},");
				bf.append(subStr);
			} else
				bf.append("leaf:true},");
		}
		return bf.toString();
	}

	/*
	 * 从Element对象中获取[id,text,iconCls]
	 */
	private StringBuffer getElementValue(Element e) {
		StringBuffer subStr = new StringBuffer("");
		String id = e.attribute("id").getValue();
		String text = e.attribute("text") != null ? e.attribute("text")
				.getValue() : "";
		String iconCls = e.attribute("iconCls") != null ? e.attribute("iconCls")
				.getValue() : "";
		subStr.append("{id:'" + id + "',text:'" + text).append("',iconCls:'" + iconCls)
				.append("',");
		subStr.append(findChidNode(e));
		return subStr;
	}

	/**
	 * 获取Document对象
	 */
	private Document getXMLDocument() {
		Document document = null;
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(new File(xmlPath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
}
