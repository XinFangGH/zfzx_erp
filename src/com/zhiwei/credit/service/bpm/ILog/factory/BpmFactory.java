package com.zhiwei.credit.service.bpm.ILog.factory;

import org.dom4j.Element;

import com.zhiwei.credit.service.bpm.ILog.elements.impl.BpmEndImpl;
import com.zhiwei.credit.service.bpm.ILog.elements.impl.BpmGatewayImpl;
import com.zhiwei.credit.service.bpm.ILog.elements.BpmElementsManager;
import com.zhiwei.credit.service.bpm.ILog.elements.impl.BpmStartImpl;
import com.zhiwei.credit.service.bpm.ILog.elements.impl.BpmTaskImpl;
import com.zhiwei.credit.service.bpm.ILog.elements.impl.BpmSubProcessImpl;

/**
 * @description 使用工厂模式，调用BpmHelper子类操作
 * @class BpmFactory
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14AM
 * 
 */
public class BpmFactory {

	private org.dom4j.Document getDocument;
	
	/**
	 * 节点类型，构造函数中赋值
	 */
	private String[] nodeType;

	/**
	 * @description 必须传入Document对象
	 * @param document Document对象
	 */
	public BpmFactory(org.dom4j.Document document) {
		nodeType = new String[] { "StartEvent", "Task", "Gateway", "EndEvent",
				"SequenceFlow", "SubProcess" };
		getDocument = document;
	}

	/**
	 * @description 获取节点信息
	 * @param element
	 *            Element对象
	 * @param nodeName
	 *            对应IBM流程节点的nodeName
	 * @return 显示节点的文本信息 eg:<node></node>
	 */
	public String getInfo(Element element, String nodeName) {
		String str = ""; // 返回的字符串
		BpmElementsManager bpmManager = null; // BpmHelper对象
		if (nodeName.equalsIgnoreCase(nodeType[0])) { // 开始节点
			bpmManager = new BpmStartImpl(getDocument);
		} else if (nodeName.equalsIgnoreCase(nodeType[1])) { // 任务节点
			bpmManager = new BpmTaskImpl(getDocument);
		} else if (nodeName.equalsIgnoreCase(nodeType[2])) { // 网关节点
			bpmManager = new BpmGatewayImpl(getDocument);
		} else if (nodeName.equalsIgnoreCase(nodeType[3])) { // 结束节点
			bpmManager = new BpmEndImpl(getDocument);
		}  else if (nodeName.equalsIgnoreCase(nodeType[5])) // 子流程
			bpmManager = new BpmSubProcessImpl(getDocument);
		if (bpmManager != null)
			str = bpmManager.getInfo(element);
		return str;
	}
}
