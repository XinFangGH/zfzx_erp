package com.zhiwei.core.jbpm.jpdl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.zhiwei.core.util.XmlUtil;

/**
 * 流程设计器
 * @author csx
 */
public class JpdlConverter {
	
	private static Log logger=LogFactory.getLog(JpdlConverter.class);
	
	public static void main(String[]args){
		String path="D:/dev/workspace/joffice/web/file.xml";
//		
		String drawXml=XmlUtil.load(path).asXML();
		
		String convertXml=JpdlGen(drawXml, "test");
		System.out.println("lastXML:" + convertXml);
		
//		for(int i=0;i<10;i++){
//			Long uuid = Math.abs(UUID.randomUUID().getLeastSignificantBits());
//			System.out.println("uid:" + uuid.toString());
//		}
		
	}
	
	
	/**
	 * 把流程设计器产生的代码转成Jbpm的流程定义
	 * @param drawXml
	 * @param processName
	 */
	public static String JpdlGen(String drawXml,String processName){
		
		if(logger.isDebugEnabled()){
			logger.debug("drawXml:" + drawXml);
		}
		
		Document jpdlDoc = DocumentHelper.createDocument();
		
		jpdlDoc.setXMLEncoding(System.getProperty("file.encoding"));
		
	    Element processEl = jpdlDoc.addElement( "process" );
		
	    processEl.addAttribute("name", processName);
	    
	    Document drawDoc=XmlUtil.stringToDocument(drawXml);
		Element orgRootEl=drawDoc.getRootElement();
	    
	    //存储连接的Set
		Set transitionSet=new HashSet();
		
		Map<String,Element> nodeMap=parseDrawXml(transitionSet,orgRootEl);
		
		Iterator it=nodeMap.values().iterator();
		
		while(it.hasNext()){
			System.out.println(((Element)it.next()).asXML());
		}
		
		Map<String,Element> newNodeMap=new LinkedHashMap();
		Iterator<String> ids=nodeMap.keySet().iterator();
		
		while(ids.hasNext()){
			String id=ids.next();
			Element pNode=nodeMap.get(id);
			Element curNewNode=processEl.addElement(pNode.getQualifiedName());
			String x=pNode.attributeValue("x");
			String y=pNode.attributeValue("y");
			
			String width=pNode.attributeValue("w");
			Integer intWidth=new Integer(width)+10;
			
			String height=pNode.attributeValue("h");
			Integer intHeight=new Integer(height)+10;
			
			curNewNode.addAttribute("name", pNode.attributeValue("name"));
			curNewNode.addAttribute("g", x + "," + y + "," + intWidth + "," + intHeight );
			
			newNodeMap.put(id, curNewNode);
		}

		Iterator<Element> tranIt=transitionSet.iterator();
		
		while(tranIt.hasNext()){
			Element tranEl=tranIt.next();
			String g=tranEl.attributeValue("g");
			String name=tranEl.attributeValue("name");
			
			//取到Start节点
			Element startNode=(Element)tranEl.selectSingleNode("./startConnector/rConnector/Owner/*");
			Element endNode=(Element)tranEl.selectSingleNode("./endConnector/rConnector/Owner/*");
			
			if(startNode!=null && endNode!=null){
				String startRef=startNode.attributeValue("ref")!=null? startNode.attributeValue("ref"):startNode.attributeValue("id");
				String endRef=endNode.attributeValue("ref")!=null? endNode.attributeValue("ref"):endNode.attributeValue("id");
				
				Element newStartNode;
				Element newEndNode;
				
				if(startRef!=null && endRef!=null){
					 newStartNode=newNodeMap.get(startRef);
					 newEndNode=newNodeMap.get(endRef);
				}else{
					String startId=startNode.attributeValue("id");
					String endId=startNode.attributeValue("id");
					newStartNode=newNodeMap.get(startId);
					newEndNode=newNodeMap.get(endId);
				}

				Element transitionEl=newStartNode.addElement("transition");
				transitionEl.addAttribute("name", name);
				transitionEl.addAttribute("to", newEndNode.attributeValue("name"));
				transitionEl.addAttribute("g",g);
				//加上条件表达式
				if("decision".equals(newStartNode.getQualifiedName())){
					Element conditionEl=(Element)orgRootEl.selectSingleNode("/drawing/figures//decision/conditions/condition[@to='"+name+"']");
					if(conditionEl!=null){
						Element newConditionEl= transitionEl.addElement("condition");
						newConditionEl.addAttribute("expr", conditionEl.attributeValue("expr"));
					}
				}
			}
		}

		if(logger.isDebugEnabled()){
			logger.debug("after convter jbpm xml:" + processEl.asXML());
		}
		
		return jpdlDoc.asXML();
		
	}
	
	private static Map<String, Element> parseDrawXml(Set transitionSet,Element rootEl){
		
		Map<String, Element> map=new LinkedHashMap<String, Element>();

		List<Element> figures=rootEl.selectNodes("/drawing/figures/*");
		
		for(Element el:figures){
			String id=el.attributeValue("id");
			String ref=el.attributeValue("ref");
			
			if("transition".equals(el.getQualifiedName())){//transition
				
				transitionSet.add(el);
				
				List taskNodes=el.selectNodes("./*/rConnector/Owner/task");
				
				for(int i=0;i<taskNodes.size();i++){
					Element taskEl=(Element)taskNodes.get(i);
					String  tId=taskEl.attributeValue("id");
					
					if(tId!=null){
						map.put(tId,taskEl);
					}
				}
				
			}else{//node
				if(id!=null){
					map.put(id,el);
				}else if(ref!=null){
					Node figureNode=rootEl.selectSingleNode("/drawing/figures//*[@id='" + ref + "']");
					map.put(ref, (Element)figureNode);
				}
			}
		}
		
		return map;
	}
}
