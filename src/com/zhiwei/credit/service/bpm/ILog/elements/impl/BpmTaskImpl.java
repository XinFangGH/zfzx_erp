package com.zhiwei.credit.service.bpm.ILog.elements.impl;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.credit.service.bpm.ILog.elements.BpmElementsManager;
import com.zhiwei.credit.service.bpm.ILog.helper.BpmHelper;

/**
 * @description 处理IBM ILOG流程的stateEvent节点操作
 * @class BpmTaskImpl
 * @class BpmHelper
 * @interface BpmManager
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14AM
 * 
 */
public class BpmTaskImpl extends BpmHelper implements BpmElementsManager {

	public BpmTaskImpl(Document document) {
		super(document);
	}

	/**
	 * @description 任务节点，添加属性：type,expression，描述：type=expression
	 * @param element
	 *            Element对象
	 * @return String
	 */
	@Override
	public String getInfo(Element element) {
		StringBuffer sbf = new StringBuffer("<task ");
		sbf.append(super.getAttributes(element));
		Map<String, String> map = getNodeLabels(element, new String[] { "type",
				"expression" });
		String msg = "";
		if (map.size() == 2) {
			if (map.get("type") != null && !map.get("type").equals("none")) {
				msg = " " + map.get("type") + "=\"" + map.get("expression")
						+ "\"";
			}
		}
		sbf.append(msg + " ");
		sbf.append(">\n");
		super.addTransition(element, sbf);
		sbf.append("</task>\n");
		return sbf.toString();
	}
}
