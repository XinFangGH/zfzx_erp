package com.zhiwei.credit.service.bpm.ILog.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @description 对节点操作的辅助方法
 * @class BpmHelper
 * @extends AddTransition
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-4-14AM
 * 
 */
public class BpmHelper extends AddTransition {
	
	/**
	 * start节点下的文本高度,value=12
	 */
	private static int STARTLABELHEIGHT = 12;
	
	/**
	 * @description 必须传入Document对象
	 * 
	 * @param document
	 *            Document对象
	 */
	public BpmHelper(Document document) {
		setGetXMLDocument(document);
	}

	private Document getXMLDocument;

	public void setGetXMLDocument(Document getXMLDocument) {
		this.getXMLDocument = getXMLDocument;
	}

	/**
	 * @@description 根据标签名称查询对应Element对象下该标签的文本信息
	 * @param el
	 *            Element对象
	 * @param qName
	 *            标签名称
	 * @return 该标签的文本信息
	 */
	protected String getNodeLabel(Element element, String qName) {
		String str = "";
		@SuppressWarnings("unchecked")
		Iterator<Element> it = element.elements().iterator();
		while (it.hasNext()) {
			Element el = it.next();
			if (el.getName().equals(qName)) {
				str = el.getText();
				break;
			}
		}
		return str;
	}
	
	/**
	 * @description 根据标签名称查询对应Element对象下该标签的文本信息
	 * @param el
	 *            Element对象
	 * @param qNames
	 *            标签名称组成的数组
	 * @return 该标签的文本信息组成的数组,与标签名称一一对应
	 */
	protected Map<String,String> getNodeLabels(Element element, String [] qNames){
		Map<String,String> map = new HashMap<String,String>();
		@SuppressWarnings("unchecked")
		Iterator<Element> it = element.elements().iterator();
		while (it.hasNext()) {
			Element el = it.next();
			for (int i = 0 ; i < qNames.length ; i++) {
				String qName = qNames[i];
				if (el.getName().equals(qName) && el.getTextTrim() != null ) {
					map.put(qName, el.getText());
				}
			}
		}
		return map;
	}

	/**
	 * @description 获取节点的单个属性值
	 * @param el
	 *            Element对象
	 * @param attributeName
	 *            属性名称
	 * @return 属性值
	 */
	protected String getAttribute(Element el, String attributeName) {
		String str = "";
		Attribute obj = el.attribute(attributeName);
		if (obj != null && obj.getValue() != "")
			str = obj.getValue();
		return str;
	}

	/**
	 * @description 根据SquenceFlow节点某个属性值查询这个属性对应的Element对象列表
	 * @param attributeName
	 *            属性名称
	 * @param attributeValue
	 *            属性值
	 * @return 满足条件的Element对象列表
	 */
	protected List<Element> baseSFAttributeSearch(String attributeName,
			String attributeValue) {
		List<Element> list = new ArrayList<Element>();
		@SuppressWarnings("unchecked")
		Iterator<Element> it = getXMLDocument.getRootElement()
				.elements("SequenceFlow").iterator();
		while (it.hasNext()) {
			Element el = it.next();
			String name = el.attributeValue(attributeName);
			if (name != null && name.equals(attributeValue))
				list.add(el);
		}
		return list;
	}

	/**
	 * @description 获取节点属性[width,height,x,y]
	 * @param el
	 *            Element对象
	 * @return 非直线节点String字符串的格式 eg: g="?,?,?,?"
	 *         name="?";直线节点String字符串的格式eg:g="-52,-20"
	 */
	protected String getAttributes(Element el) {
		String str = "";
		int x = 0;
		int y = 0;
		String width = "48";
		String height = "48";
		// x
		String xs = el.attributeValue("x");
		if (xs != null && !xs.equals(""))
			x = Integer.valueOf(subString(xs));
		// y
		String ys = el.attributeValue("y");
		y = Integer.valueOf(subString(ys));
		if(el.getName().equals("StartEvent") ) // 减除Start标签文本的高度
			y = y - STARTLABELHEIGHT / 2; 
		else if(el.getName().equals("EndEvent"))
			y = y - 2;
		// 只有Task,SubProcess的大小不能设置固定
		if(el.getName().equals("Task")  || el.getName().equals("SubProcess")){
			String widths = el.attributeValue("width");
			if (widths != null && !widths.equals(""))
				width = subString(widths);
			String heights = el.attributeValue("height");
			if (heights != null && !heights.equals(""))
				height = subString(heights);
		}
		
		String name = getNodeLabel(el, "label");
		str += "name=\"" + name + "\"";
		if (el.getName().equalsIgnoreCase("SequenceFlow"))
			str += " g=\"-52,-22\"";
		else
			str += " g=\"" + x + "," + y + "," + width + "," + height + "\"";
		return str;
	}

	/**
	 * @description 根据开始位置和结束位置，找到该直线，并获取对应的Label文本信息
	 * @param element
	 *            顶级Element对象,即:Document.getRoolElement()对象
	 * @param startPort
	 *            开始位置
	 * @param endPort
	 *            结束位置
	 * @return 直线的Label文本信息
	 */
	protected String getSequenceFlowLabel(String startPort, String endPort) {
		Element element = null;
		@SuppressWarnings("unchecked")
		Iterator<Element> it = getXMLDocument.getRootElement()
				.elements("SequenceFlow").iterator();
		while (it.hasNext()) {
			Element el = it.next();
			String sp = el.attributeValue("startPort");
			String ep = el.attributeValue("endPort");
			if (sp.equals(startPort) && ep != null && ep.equals(endPort)) {
				element = el;
				break;
			}
		}
		if (element != null)
			return getNodeLabel(element, "label");
		else
			return "";
	}

	/**
	 * @description 获取节点中连线的开始节点信息Port编号列表
	 * @param element
	 *            Element对象
	 * @return List<String>Port编号列表
	 */
	protected List<String> getChildInfo(Element element) {
		List<String> list = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<Element> subList = element.elements("ports");
		if (subList != null && subList.size() > 0) {
			Element el = subList.get(0);
			@SuppressWarnings("unchecked")
			Iterator<Element> it = el.elements("Port").iterator();
			while (it.hasNext()) {
				Element e = it.next(); // Element对象
				String x = e.attributeValue("x");
				String y = e.attributeValue("y");
				if (x != null || y != null)
					list.add(e.attributeValue("id"));
				// else 没有连线
			}
		}
		return list;
	}

	/**
	 * @description 根据startPort对应的id查询另一个属性为endPort的数值
	 * @param element
	 *            Element对象
	 * @param startPortId
	 *            startPort属性对应的id
	 * @return endPort属性值
	 */
	protected String baseStartPortGetEndPortId(Element element,
			String startPortId) {
		List<Element> list = baseSFAttributeSearch("startPort", startPortId);
		if (list != null && list.size() > 0) {
			Element el = list.get(0); // 获取SequenceFlow对象列表
			String endPortId = getAttribute(el, "endPort");
			return endPortId;
		}
		return null;
	}

	/**
	 * @description 根据startPort查询出对应的<ports>节点的上级节点的<label>值
	 * @param startPort
	 *            startPort对应的id
	 * @return 对应的<ports>节点的上级节点的<label>值
	 */
	protected String baseStartPortSearchParentLabel(String startPort) {
		String str = "";
		@SuppressWarnings("unchecked")
		Iterator<Element> it = getXMLDocument.getRootElement().elements()
				.iterator();
		while (it.hasNext()) {
			Element element = it.next();
			Element ports = element.element("ports");
			if (ports != null) {
				@SuppressWarnings("unchecked")
				Iterator<Element> subIt = ports.elementIterator();
				while (subIt.hasNext()) {
					String id = subIt.next().attributeValue("id");
					if (id != null && id.equals(startPort))
						str = getNodeLabel(element, "label");
				}
			}
		}
		return str;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////似有方法///////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * @descrption 获取小数点前面的数据
	 * @param str
	 *            被截取的对象
	 * @return 截取后的数据对象
	 */
	private String subString(String str) {
		if (str != null && !str.equals("")) {
			int index = str.indexOf(".");
			if (index > 0)
				str = str.substring(0, index);
			return str;
		}
		return "";
	}

	/**
	 * @description 连线操作
	 * @param element
	 *            Element对象
	 * @param StringBuffer返回连线的坐标组成的字符串
	 */
	protected void addTransition(Element element, StringBuffer sbf) {
		List<String> list = getChildInfo(element); // 获取Port对象id的列表
		java.util.Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			// 一
			String startPortId = it.next();// 获取startPort

			// 二
			String endPortId = baseStartPortGetEndPortId(element, startPortId); // 获取endPort

			// 三
			String label = getSequenceFlowLabel(startPortId, endPortId); // 获取连线对应的label文本数值

			// 四
			String toName = baseStartPortSearchParentLabel(endPortId); // 获取连接的下一个流程子节点的文本信息

			// 五 获取连线开始结束的element对象的x,y,width,height属性值
			int[] startEle = getNodeAttributeValues(element);
			Element endElement = baseStartPortSearchParent(endPortId); // 结束Element对象
			int[] endEle = getNodeAttributeValues(endElement);

			// 六.获取x=?,y=?
			String endStr = getXOrYString(endElement, endPortId);
			String startStr = getXOrYString(element, startPortId);
			String pointString = "";
			// 七. 节点连线坐标
			if (endStr != null && !endStr.equals(""))
				pointString = super.getPointString(startStr, endStr, startEle, endEle, element.getName(), endElement.getName());
			if (toName != null && !toName.equals("")) {
				sbf.append("\t<transition g=\"");
				if(pointString != null && !pointString.equals(""))
					sbf.append(pointString + ":");
				sbf.append("-50,-22\" name=\"" + label + "\" to=\"" + toName + "\" />\n");
			}
		}
	}

	/**
	 * @description 获取节点的x,y,width,height属性值
	 */
	private int[] getNodeAttributeValues(Element element) {
		int[] values = { 70, 70, 52, 48 };
		if (element != null) {
			String xs = element.attributeValue("x");
			if (xs != null && !xs.equals(""))
				values[0] = Integer.valueOf(subString(xs));
			String ys = element.attributeValue("y");
			if (ys != null && !ys.equals(""))
				values[1] = Integer.valueOf(subString(ys));
			String ws = element.attributeValue("width");
			if (ws != null && !ws.equals(""))
				values[2] = Integer.valueOf(subString(ws));
			String hs = element.attributeValue("height");
			if (hs != null && !hs.equals(""))
				values[3] = Integer.valueOf(subString(hs));
		}
		return values;
	}

	/**
	 * @description 获取结束节点的element对象
	 */
	private Element baseStartPortSearchParent(String startPort) {
		Element element = null;
		@SuppressWarnings("unchecked")
		Iterator<Element> it = getXMLDocument.getRootElement().elements()
				.iterator();
		while (it.hasNext()) {
			Element el = it.next();
			Element ports = el.element("ports");
			if (ports != null) {
				@SuppressWarnings("unchecked")
				Iterator<Element> subIt = ports.elementIterator();
				while (subIt.hasNext()) {
					String id = subIt.next().attributeValue("id");
					if (id != null && id.equals(startPort))
						element = el;
				}
			}
		}
		return element;
	}

	/**
	 * @descrption 根据<port>标签中对应的id获取该对象的x,y
	 * @param element
	 *            Element对象
	 * @param portId
	 *            <port>节点中的Id
	 * @return x=?,y=?
	 */
	private String getXOrYString(Element element, String portId) {
		String str = "";
		if (element != null) {
			@SuppressWarnings("unchecked")
			Iterator<Element> it = element.element("ports").elements()
					.iterator();
			while (it.hasNext()) {
				Element e = it.next();
				String id = e.attributeValue("id");
				String x = e.attributeValue("x");
				String y = e.attributeValue("y");
				if (id != null && id.equals(portId) && x != null)
					return "x=" + x;
				else if (id != null && id.equals(portId) && y != null)
					return "y=" + y;
			}
		}
		return str;
	}
}
