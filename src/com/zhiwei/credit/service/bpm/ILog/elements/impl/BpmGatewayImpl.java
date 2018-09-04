package com.zhiwei.credit.service.bpm.ILog.elements.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.credit.service.bpm.ILog.elements.BpmElementsManager;
import com.zhiwei.credit.service.bpm.ILog.helper.BpmHelper;

/**
 * @description 将IBM ILOG流程的Gateway中的元素转化为对应的JBPM中的【fork同步,join合并,decision决策 】节点
 * @class BpmGatewayImpl
 * @extends BpmHelper
 * @interface BpmElementsManager
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14AM
 * 
 */
public class BpmGatewayImpl extends BpmHelper implements BpmElementsManager {

	public BpmGatewayImpl(Document document) {
		super(document);
	}

	/**
	 * @descrption 网关事件[join,fork,decision]， 新增属性：desicion:expr,handler[子节点]
	 * @param element
	 *            Element对象
	 * @return String
	 */
	@Override
	public String getInfo(Element element) {
		StringBuffer sbf = new StringBuffer("<");
		String trigger = super.getNodeLabel(element, "gatewayType");
		String tg = "decision"; // 分支
		if (trigger != null) {
			if (trigger.equals("AND")) { // 合并
				tg = "join";
			} else if (trigger.equals("OR")) { // 同步
				tg = "fork";
			}
		}
		sbf.append(tg);
		sbf.append(" " + super.getAttributes(element));
		String handlerString = attributeBaseTrigger(element, sbf, tg);
		sbf.append(">\n");
		super.addTransition(element, sbf);
		if (tg != null && tg == "decision" && !handlerString.equals("")) // 是否为决策节点
			sbf.append("\t<handler class=\"" + handlerString + "\"/>\n");
		sbf.append("</" + tg + ">\n"); // 问题
		return sbf.toString();
	}

	private String attributeBaseTrigger(Element element, StringBuffer sbf,
			String tg) {
		String msg = "";
		Map<String, String> map = new HashMap<String, String>();
		// 决策，或者分支
		map = super.getNodeLabels(element, new String[] { "expr", "handler" });
		for (Entry<String, String> et : map.entrySet()) {
			if (et.getKey().equals("expr")) {
				sbf.append(" expr=\"" + et.getValue() + "\" ");
			} else if (et.getKey().equals("handler")) {
				msg = et.getValue();
			}
		}
		return msg;
	}
}
