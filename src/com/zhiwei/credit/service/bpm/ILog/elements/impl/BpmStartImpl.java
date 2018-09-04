package com.zhiwei.credit.service.bpm.ILog.elements.impl;

import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.credit.service.bpm.ILog.helper.BpmHelper;

import com.zhiwei.credit.service.bpm.ILog.elements.BpmElementsManager;

/**
 * @description 处理StartEvent节点操作
 * @class BpmStartImpl
 * @extends BpmHelper
 * @interface BpmElementsManager
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14AM
 * 
 */
public class BpmStartImpl extends BpmHelper implements BpmElementsManager {
	
	public BpmStartImpl(Document document) {
		super(document);
	}

	/**
	 * @description Start节点
	 * @param element
	 *            Element对象
	 * @return String
	 */
	@Override
	public String getInfo(Element element) {
		StringBuffer sbf = new StringBuffer("<start ");
		sbf.append(super.getAttributes(element));
		sbf.append(">\n");
		super.addTransition(element, sbf);
		sbf.append("</start>\n");
		return sbf.toString();
	}
}
