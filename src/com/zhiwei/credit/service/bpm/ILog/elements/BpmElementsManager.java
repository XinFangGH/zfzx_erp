package com.zhiwei.credit.service.bpm.ILog.elements;

import org.dom4j.Element;
/**
 * @description 对IBM ILOG流程节点的处理,获取对应节点的属性，信息，并返回
 * @class BpmElementsManager
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14AM
 * 
 */
public interface BpmElementsManager {
	/**
	 * @description 获取节点对应的标签，和相关属性值，并返回转化后新的节点信息
	 * @param element
	 *            Element对象
	 * @return 转化后新的节点信息[标签，相关属性]
	 */
	String getInfo(Element element);
}
