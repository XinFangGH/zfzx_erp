package com.zhiwei.credit.service.bpm.ILog.elements.impl;

import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.credit.service.bpm.ILog.elements.BpmElementsManager;
import com.zhiwei.credit.service.bpm.ILog.helper.BpmHelper;

/**
 * @description 处理IBM ILOG流程EndEvent节点操作
 * @class BpmEndImpl
 * @extend BpmHelper
 * @interface BpmElementsManager
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14AM
 * 
 */
public class BpmEndImpl extends BpmHelper implements BpmElementsManager {

	public BpmEndImpl(Document document) {
		super(document);
	}

	/**
	 * @description End结束节点[end,end-error,end-cancel]
	 * @param element
	 *            Element对象
	 * @return String
	 */
	@Override
	public String getInfo(Element element) {
		StringBuffer sbf = new StringBuffer("<"); // 返回的字符串
		String trigger = super.getNodeLabel(element, "trigger");
		if (trigger != null) {
			if (trigger.equalsIgnoreCase("Error"))
				sbf.append("end-error");
			else if (trigger.equals("Cancel"))
				sbf.append("end-cancel");
			else
				sbf.append("end");
		} else
			sbf.append("end");
		sbf.append(" " + super.getAttributes(element));
		sbf.append(" />\n");
		return sbf.toString();
	}
}
