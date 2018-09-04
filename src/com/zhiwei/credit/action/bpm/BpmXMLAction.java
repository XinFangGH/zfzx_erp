package com.zhiwei.credit.action.bpm;

import java.util.Iterator;

import org.dom4j.Element;

import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.service.bpm.ILog.factory.BpmFactory;

/**
 * @description 转化xml文件的格式
 * @class BpmXMLAction
 * @extends BaseAction
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14Am
 * 
 */
public class BpmXMLAction extends BaseAction {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public String change() {
		String xml = getRequest().getParameter("xmlString");
		String text = "";
		if (xml != null && !xml.equals("")) {
			org.dom4j.Document document = XmlUtil.stringToDocument(xml);
			Element element = document.getRootElement();
			// Element element = getXMLDocument(xmlPath).getRootElement();
			BpmFactory factory = new BpmFactory(document);
			@SuppressWarnings("unchecked")
			Iterator<Element> it = element.elements().iterator();
			text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <process name=\"test\" xmlns=\"http://jbpm.org/4.4/jpdl\">";
			while (it.hasNext()) {
				Element el = it.next();
				String str = factory.getInfo(el, el.getName());
				text += str;
			}
			text += "</process>";
		} else {
		}
		setJsonString("{success:true,jbpmXML:'" + text + "'}");
		return SUCCESS;
	}


}
