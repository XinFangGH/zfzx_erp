package com.zhiwei.core.jbpm.jpdl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.service.flow.JbpmService;

/**
 * 
 * <B><P>Joffice -- http://www.jee-soft.cn</P></B>
 * <B><P>Copyright (C) 2008-2010 GuangZhou HongTian Software Company (广州宏天软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:joffice</P>
 * <P>流程初始化类</P> 
 * @see com.hurong.core.jbpm.jpdl.ProcessInit
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-11-24上午10:40:26
 */
public class ProcessInit {
	private static Log logger=LogFactory.getLog(ProcessInit.class);
	
	public static void initFlows(String absPath){
		try{
			JbpmService jbpmService=(JbpmService)AppUtil.getBean("jbpmService");
			List<ProDefinition> defs=getProDefinitions(absPath);
			for(ProDefinition def:defs){
				logger.debug("初始化系统流程:"+def.getName());
				jbpmService.saveOrUpdateDeploy(def);
			}
		}catch(Exception ex){
			logger.debug("init flows:" + ex.getMessage());
		}
	}
	
	/**
	 * @throws IOException 
	 * 系统启动时初始化流程定义(放在安装处)
	 */
	public static List<ProDefinition> getProDefinitions(String absPath) throws IOException{
			
		String jpdlPath=absPath+"/WEB-INF/classes/jpdl";
		String initFlowFile=jpdlPath+"/initFlow.xml";
		
		List<ProDefinition> proDefinitions=new ArrayList<ProDefinition>();
		Document rootDoc=XmlUtil.load(initFlowFile);
		
		if(rootDoc!=null){
			Element defEl=rootDoc.getRootElement();
			if(defEl!=null){
				Iterator<Element> processIt=defEl.elementIterator();
				while(processIt.hasNext()){
					ProDefinition proDefinition=new ProDefinition();
					Element processEl=processIt.next();
					String name=processEl.attributeValue("name");
					proDefinition.setName(name);
					proDefinition.setCreatetime(new Date());
					
					Element descEl =(Element)processEl.selectSingleNode("./description");
					if(descEl!=null){
						String description=descEl.getText();
						proDefinition.setDescription(description);
						proDefinition.setIsDefault(ProDefinition.IS_DEFAULT);
					}
					Element jpdlLocEl =(Element)processEl.selectSingleNode("./jpdlLocation");
					if(jpdlLocEl!=null){
						String jpdlLocation=jpdlLocEl.getText().trim();
						File jpdlFile=new File(jpdlPath+"/" + jpdlLocation);
						if(jpdlLocation!=null){
							File timeFile=new File(jpdlPath+"/"+jpdlLocation.replace(".jpdl.xml", ".time"));
							boolean isNotExist=false;
							if(!timeFile.exists()){//不存在
								timeFile.createNewFile();
								isNotExist=true;
							}
							
							if(isNotExist ||jpdlFile.lastModified()>timeFile.lastModified()){
								String defXml=XmlUtil.load(jpdlFile).asXML();
								proDefinition.setDefXml(defXml);
								proDefinitions.add(proDefinition);
							}
							timeFile.setLastModified(jpdlFile.lastModified());
						}
					}
				}//end of while
			}
		}
		
		return proDefinitions;
	}

//	public static void main(String[]args){
//		String path=Thread.currentThread().getContextClassLoader().getResource(".").getFile();
//		//System.out.println("path:" + path);
//	}
	
}
