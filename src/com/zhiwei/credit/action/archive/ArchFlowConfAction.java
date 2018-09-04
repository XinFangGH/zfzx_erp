package com.zhiwei.credit.action.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jbpm.api.ProcessDefinition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jbpm.jpdl.Node;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.archive.ArchFlowConf;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.service.archive.ArchFlowConfService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
/**
 * 
 * @author 
 *
 */
public class ArchFlowConfAction extends BaseAction{
	@Resource
	private ArchFlowConfService archFlowConfService;
	@Resource 
	private JbpmService jbpmService;
	@Resource
	private ProDefinitionService proDefinitionService;
	private ArchFlowConf archFlowConf;
	
	private Long configId;

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public ArchFlowConf getArchFlowConf() {
		return archFlowConf;
	}

	public void setArchFlowConf(ArchFlowConf archFlowConf) {
		this.archFlowConf = archFlowConf;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ArchFlowConf> list= archFlowConfService.getAll(filter);
		
		Type type=new TypeToken<List<ArchFlowConf>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				archFlowConfService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
//		ArchFlowConf archFlowConf=archFlowConfService.get(configId);
//		
//		Gson gson=new Gson();
//		//将数据转成JSON格式
//		StringBuffer sb = new StringBuffer("{success:true,data:");
//		sb.append(gson.toJson(archFlowConf));
//		sb.append("}");
//		setJsonString(sb.toString());
		ArchFlowConf sendFlowConf=archFlowConfService.getByFlowType(ArchFlowConf.ARCH_SEND_TYPE);
		ArchFlowConf recFlowConf=archFlowConfService.getByFlowType(ArchFlowConf.ARCH_REC_TYPE);
		StringBuffer sb=new StringBuffer("{success:true,data:");
		if(sendFlowConf!=null){
			sb.append("{'sendProcessId':'"+sendFlowConf.getDefId()+"','sendProcessName':'"+sendFlowConf.getProcessName()+"'");		
		}else{
			sb.append("{'sendProcessId':'','sendProcessName':''");
		}
		if(recFlowConf!=null){
			sb.append(",'recProcessId':'"+recFlowConf.getDefId()+"','recProcessName':'"+recFlowConf.getProcessName()+"'}}");
		}else{
			sb.append(",'recProcessId':'','recProcessName':''}}");
		}
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String sendId=getRequest().getParameter("sendProcessId");
		String sendName=getRequest().getParameter("sendProcessName");
		String recId=getRequest().getParameter("recProcessId");
		String recName=getRequest().getParameter("recProcessName");
		if(StringUtils.isNotEmpty(sendId)&&StringUtils.isNotEmpty(sendName)){
			archFlowConf=archFlowConfService.getByFlowType(ArchFlowConf.ARCH_SEND_TYPE);
			if(archFlowConf==null){
				archFlowConf=new ArchFlowConf();
				archFlowConf.setArchType(ArchFlowConf.ARCH_SEND_TYPE);
			}
			archFlowConf.setDefId(new Long(sendId));
			archFlowConf.setProcessName(sendName);
			archFlowConfService.save(archFlowConf);
		}
		if(StringUtils.isNotEmpty(recId)&&StringUtils.isNotEmpty(recName)){
			archFlowConf=archFlowConfService.getByFlowType(ArchFlowConf.ARCH_REC_TYPE);
			if(archFlowConf==null){
				archFlowConf=new ArchFlowConf();
				archFlowConf.setArchType(ArchFlowConf.ARCH_REC_TYPE);
			}
			archFlowConf.setDefId(new Long(recId));
			archFlowConf.setProcessName(recName);
			archFlowConfService.save(archFlowConf);
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 获取流程
	 */
	public String getFlow(){
		String type=getRequest().getParameter("flowType");
		StringBuffer sb=new StringBuffer();
		if(type.equals(ArchFlowConf.ARCH_SEND_TYPE.toString())){
			archFlowConf=archFlowConfService.getByFlowType(ArchFlowConf.ARCH_SEND_TYPE);
		}
		else{
			archFlowConf=archFlowConfService.getByFlowType(ArchFlowConf.ARCH_REC_TYPE);
		}
		if(archFlowConf!=null){
			sb.append("{success:true,defId:").append(archFlowConf.getDefId()).append("}");
		}else{
			sb.append("{success:false,'message':'你还没设定流程'}");
		}
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 为公文模块设置流程时动态加载工菜单
	 */
	public String setting(){
		//流程定义ID
		String defId = getRequest().getParameter("defId");
		//公文收发类型
		String settingType = getRequest().getParameter("settingType");
		Short archType = null;
		if(settingType.equals("send")){
			archType = ArchFlowConf.ARCH_SEND_TYPE;
		}else{
			archType = ArchFlowConf.ARCH_REC_TYPE;
		}
		if(StringUtils.isNotEmpty(defId)){
			ProDefinition proDefintion = proDefinitionService.get(new Long(defId));
			ProcessDefinition processDefinition = jbpmService.getProcessDefinitionByDefId(new Long(defId));
			List<Node> nodes = jbpmService.getTaskNodesByDefId(new Long(defId),false,true);

			//查询设置是否已经存在
			
			if(archType.shortValue() == ArchFlowConf.ARCH_SEND_TYPE.shortValue()){
				archFlowConf = archFlowConfService.getByFlowType(ArchFlowConf.ARCH_SEND_TYPE);
			}else{
				archFlowConf = archFlowConfService.getByFlowType(ArchFlowConf.ARCH_REC_TYPE);
			}
			
			//取得公文模块的MENU.XML
			String archMenu = AppUtil.getAppAbsolutePath()+"/js/menu/xml/menu-archives.xml";
			Document doc=XmlUtil.load(archMenu);
			//取得Menus元素
			Element menus = doc.getRootElement();
			//取得第一个Items元素
			Element firstItems = menus.element("Items");
			
			
			if(archFlowConf == null){//假如公文未设置,则新增记录
				archFlowConf = new ArchFlowConf();
			}else{//否则修改记录并修改菜单
				ProcessDefinition oldProDef = jbpmService.getProcessDefinitionByDefId(archFlowConf.getDefId());
				List<Element> list = firstItems.elements();
				for(Element el : list){
					if(el.attribute("id").getData().equals(oldProDef.getKey())){
						el.getParent().remove(el);
					}
				}
			}
			
			//新增或者修改设置信息
			archFlowConf.setArchType(archType);
			archFlowConf.setDefId(new Long(defId));
			archFlowConf.setProcessName(proDefintion.getName());
			archFlowConfService.save(archFlowConf);
			
			
			//动态增加
			Element dynamicItems = firstItems.addElement("Items"); 
			dynamicItems.addAttribute("id",processDefinition.getKey());
			dynamicItems.addAttribute("text", proDefintion.getName());
			
			//增加启动菜单
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
			String idNo = date.format(new Date());
			
			Element start = dynamicItems.addElement("Item");
			start.addAttribute("id",  processDefinition.getKey()+idNo);
			start.addAttribute("text","流程启动");
			start.addAttribute("defId",defId);
			start.addAttribute("flowName",proDefintion.getName());
			
			//假如是收文则增加公文签收待办结点
			if(archType.shortValue() == ArchFlowConf.ARCH_REC_TYPE.shortValue()){
				StringBuffer params = new StringBuffer("{defId:");
				params.append(defId)
					  .append(",flowName:'")
					  .append(proDefintion.getName())
					  .append("'}");
				Element sign = dynamicItems.addElement("Item");
				sign.addAttribute("id", "ArchivesSignView");
				sign.addAttribute("text","公文签收待办");
				sign.addAttribute("iconCls","menu-archive-sign");
				sign.addAttribute("params", params.toString());
			}
			
			//根据结点增加菜单
			int count = 1;
			for(Node node : nodes){
				Element dynamicItem = dynamicItems.addElement("Item");
				dynamicItem.addAttribute("id",processDefinition.getKey()+"Node"+count);
				dynamicItem.addAttribute("text", node.getName());
				dynamicItem.addAttribute("flowNode", "true");
				count++;
			}
			
			XmlUtil.docToXmlFile(doc, archMenu);
		}
		
		return SUCCESS;
	}
}
