package com.zhiwei.credit.action.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.jbpm.api.TaskService;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jbpm.jpdl.Node;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.bpm.ILog.factory.BpmFactory;
import com.zhiwei.credit.service.flow.FormDefMappingService;
import com.zhiwei.credit.service.flow.FormTemplateService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.service.system.OrganizationService;

import flexjson.JSONSerializer;

/**
 * @description 流程定义
 * @class ProDefinitionAction
 * @author csx
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-28PM
 */
public class ProDefinitionAction extends BaseAction {
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private FormDefMappingService formDefMappingService;
	@Resource
	private FormTemplateService formTemplateService;
	
	@Resource
	private ProcessRunService processRunService; //运行中的流程管理 
	@Resource
	private OrganizationService organizationService;
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;

	private ProDefinition proDefinition;

	private Long defId;

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public ProDefinition getProDefinition() {
		return proDefinition;
	}

	public void setProDefinition(ProDefinition proDefinition) {
		this.proDefinition = proDefinition;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());

		String typeId = getRequest().getParameter("typeId");

		List<ProDefinition> list = null;
		if (StringUtils.isNotEmpty(typeId) && !"0".equals(typeId)) {
			// 有类型ID,说明该用户有该类型的权限,查出该类型下的所有流程定义
			GlobalType proType = globalTypeService.get(new Long(typeId));
			filter.addFilter("Q_proType.path_S_LK", proType.getPath());
			list = proDefinitionService.getAll(filter);
		} else {
			AppUser curUser = ContextUtil.getCurrentUser();
			if (curUser.isSupperManage()) {
				// 假如该用户为超级管理员角色,则该用户可看到所有流程定义
				list = proDefinitionService.getAll(filter);
			} else {
				// 非管理员角色的用户需要通过权限过虑流程
				list = proDefinitionService.getByRights(curUser, proDefinition,
						filter);
			}
		}

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime")
				.exclude("defXml");

		buff.append(serializer.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}
	
	/**
	 * 根据分公司id查询对应流程信息列表
	 */
	public String listByBranchCompanyId() {

		QueryFilter filter=new QueryFilter(getRequest());
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		
		String flowName =  filter.getRequest().getParameter("Q_name_S_LK");//流程名称
		String flowDesc =  filter.getRequest().getParameter("Q_description_S_LK");//流程描述
		
		PagingBean pb=new PagingBean(start, limit);

		String branchCompanyId = getRequest().getParameter("branchCompanyId");

		if(branchCompanyId!=null&&!"".equals(branchCompanyId)){
			String isGroupVersion=AppUtil.getSystemIsGroupVersion();
			if(isGroupVersion!=null&&Boolean.valueOf(isGroupVersion)){
				if("getCompanyIdBySession".equals(branchCompanyId)){
					String companyId = this.getSession().getAttribute("CompanyId").toString();
					if(companyId!=null&&!"".equals(companyId)){
						branchCompanyId = companyId;
					}
				}
			}else{
				branchCompanyId = "0";
			}
			
			List<ProDefinition> list = proDefinitionService.getByBranchCompanyId(new Long(branchCompanyId), pb,flowName,flowDesc);

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");

			JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime").exclude("defXml");

			buff.append(serializer.serialize(list));
			
			buff.append("}");

			jsonString = buff.toString();
		}

		return SUCCESS;
	}
	
	/**
	 * 根据业务流程分类id查询对应流程信息列表
	 */
	public String listByProTypeId() {

		String proTypeId = getRequest().getParameter("typeId");

		if(proTypeId!=null&&!"".equals(proTypeId)){
			
			List<ProDefinition> list = proDefinitionService.getNormalFlowByProTypeId(new Long(proTypeId));

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?null:list.size()).append(",result:");

			JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime").exclude("defXml");

			buff.append(serializer.serialize(list));
			
			buff.append("}");

			jsonString = buff.toString();
		}

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				// 删除流程的定义，就会删除流程的实例，表单的数据及Jbpm的流程相关的内容
				// proDefinitionService.remove(new Long(id));
				jbpmService.doUnDeployProDefinition(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * 对于有任务正在处理的则提示不能删除，需先结束现有任务。
	 * add by lu 2013.05.13
	 * @return
	 */
	public String delByDefIds() {
		String msg = "";
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				// 删除流程的定义，就会删除流程的实例，表单的数据及Jbpm的流程相关的内容
				// proDefinitionService.remove(new Long(id));
				//jbpmService.doUnDeployProDefinition(new Long(id));
				ProDefinition proDefinition = proDefinitionService.get(new Long(id));
				if(proDefinition!=null){
					List<TaskImpl> taskList = flowTaskService.getTaskByExecutionId(proDefinition.getProcessName()+".");
					if(taskList!=null&&taskList.size()!=0){
						
					}else{
						msg = "{success:false,msg:'该流程有任务正在运行,不能进行删除操作!'}";
					}
				}
			}
		}
		setJsonString(msg);
		//jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {

		if (defId != null) {
			proDefinition = proDefinitionService.get(defId);
		} else {
			proDefinition = new ProDefinition();
			String proTypeId = getRequest().getParameter("proTypeId");
			if (StringUtils.isNotEmpty(proTypeId)) {
				GlobalType proType = globalTypeService.get(new Long(proTypeId));
				proDefinition.setProType(proType);
			}
		}

		// 用JSONSerializer解决延迟加载的问题
		JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime");
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(serializer.serialize(proDefinition));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}
	
	public String flexGet(){
		if (defId != null) {
			proDefinition = proDefinitionService.get(defId);
		} else {
			proDefinition = new ProDefinition();
			String proTypeId = getRequest().getParameter("proTypeId");
			if (StringUtils.isNotEmpty(proTypeId)) {
				GlobalType proType = globalTypeService.get(new Long(proTypeId));
				proDefinition.setProType(proType);
			}
		}
		StringBuffer msg = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Result>");
		// defId
		msg.append("<defId>" + proDefinition.getDefId() + "</defId>"); 
		// drawDefXml
		msg.append("<drawDefXml>" + proDefinition.getDrawDefXml() + "</drawDefXml>");
	
		if(proDefinition.getProType() != null){
			// proTypeName 
			msg.append("<proTypeName>" + proDefinition.getProType().getTypeName() + "</proTypeName>");
			// proTypeId
			msg.append("<proTypeId>" + proDefinition.getProType().getProTypeId() + "</proTypeId>"); 
		}
		
		// name
		msg.append("<name>" + proDefinition.getName() + "</name>"); 
		
		// processName
		msg.append("<processName>" + proDefinition.getProcessName() + "</processName>");
		
		// status
		msg.append("<status>" + proDefinition.getStatus() + "</status>"); 
		
		// description
		msg.append("<description>" + proDefinition.getDescription() + "</description>");
		
		// newVersion
		msg.append("<newVersion>" + proDefinition.getNewVersion() + "</newVersion>");
		msg.append("</Result>");
		setJsonString(msg.toString());
		return SUCCESS;
	}
	
	/**
	 * flex提交
	 */
	public String flexDefSave(){
//		if(proDefinition.getDefId() != null && !proDefinition.getDefId().equals("")){
//			
//			save();
//		} else {
			logger.info("...eneter defSave......");
			boolean deploy = Boolean.parseBoolean(getRequest().getParameter("deploy"));
			
			//获取当前操作人的分公司id add by lu 2012.11.22 start
			String flowType = getRequest().getParameter("flowType").toString();
			String isGroupVersion=AppUtil.getSystemIsGroupVersion();
			if(isGroupVersion!=null&&Boolean.valueOf(isGroupVersion)){
				if(flowType!=null&&!"".equals(flowType)&&!"isEditFlow".equals(flowType)&&!"original".equals(flowType)){
					if(flowType.equals("normal")){//标准版本
						proDefinition.setBranchCompanyId(new Long(0));
					}else if(flowType.equals("currentCompany")){//当前操作账号所属的分公司
						String companyId = this.getSession().getAttribute("CompanyId").toString();
						if(companyId!=null&&!"".equals(companyId)){
							proDefinition.setBranchCompanyId(new Long(companyId));
						}
					}else{//为对应分公司分配流程
						proDefinition.setBranchCompanyId(new Long(flowType));
					}
				}
			}else{
				proDefinition.setBranchCompanyId(new Long(0));//非集团版,使用BranchCompanyId为0的流程。
			}
			
			//add by lu 2012.11.22 end
			
			//编辑流程的当前版本xml文件或者所有版本的xml文件 add by lu 2012.09.25 start
			boolean isEditCurrentVersion = Boolean.parseBoolean(getRequest().getParameter("isEditCurrentVersion"));
			boolean isEditAllVersions = Boolean.parseBoolean(getRequest().getParameter("isEditAllVersions"));
			//编辑流程的当前版本xml文件或者所有版本的xml文件 add by lu 2012.09.25 end
			
			//转化xml文件格式
			if(proDefinition.getDrawDefXml()!= null && !proDefinition.getDrawDefXml().equals("")){
				String text = convertFlexXmlToJbpmXml(proDefinition.getDrawDefXml(), proDefinition.getProcessName());
				proDefinition.setDefXml(text); 
				//logger.debug("解析的JBPM对应的xml文件：\n" + text);//不输出到日志文件。以免日志文件过大。
			}
	
			// 检查流程名称的唯一性
			if (!proDefinitionService.checkNameByVo(proDefinition)) {   
				// 假如不唯一
				setJsonString("流程名称(系统中使用)已存在,请重新填写.");
				return SUCCESS;
			}
			if (!proDefinitionService.checkProcessNameByVo(proDefinition)) {
				// 假如不唯一
				setJsonString("流程名称(定义中使用)已存在,请重新填写.");
				return SUCCESS;
			}
			if (deploy) {
				save();
			} else {
				//编辑流程的当前版本xml文件或者所有版本的xml文件 add by lu 2012.09.25 start
				if(isEditCurrentVersion||isEditAllVersions){
					saveDefinition();//编辑流程的当前版本xml文件或者所有版本的xml文件 add by lu 2012.09.25 end
				}else{
					Long proTypeId = proDefinition.getProTypeId();
					if (proTypeId != null) {
						GlobalType proType = globalTypeService.get(proTypeId);
						proDefinition.setProType(proType);
					}
					proDefinition.setCreatetime(new Date());
					proDefinitionService.save(proDefinition);
					setJsonString("true");
				}
			}
		//}
		return SUCCESS;
	}
	
	/**
	 * 编辑流程的当前版本xml文件或者所有版本的xml文件。add by lu 2012.09.25
	 * isEditCurrentVersion=true：编辑当前版本
	 * isEditAllVersions=true：编辑该流程下的所有版本
	 */
	public String saveDefinition(){
		
		boolean isEditCurrentVersion = Boolean.parseBoolean(getRequest().getParameter("isEditCurrentVersion"));
		boolean isEditAllVersions = Boolean.parseBoolean(getRequest().getParameter("isEditAllVersions"));
		
		if (proDefinition.getDefId() != null) {// 更新操作
			ProDefinition proDef = proDefinitionService.get(proDefinition.getDefId());
			proDef.setDefXml(proDefinition.getDefXml());
			proDefinitionService.merge(proDef);
			try {
				if(isEditCurrentVersion){
					jbpmService.wirteDefXmlToJbpmLob(proDef, true);//编辑当前版本
				}
				if(isEditAllVersions){
					jbpmService.wirteDefXmlToJbpmLob(proDef, false);//编辑所有版本
				}
			} catch (Exception ex) {
				logger.error("编辑当前流程版本或者所有版本xml文件出错。流程名称为："+proDefinition.getName()+"--->"+ex.getMessage());
			}
		}/* else {
			jbpmService.wirteDefXml(proDefinition.getDeployId(), proDefinition.getDefXml());
		}*/
		setJsonString("{success:true}");
		return SUCCESS;
	}

	public String defSave() {
		return custSave();
	}
	
	private String custSave(){
		logger.info("...eneter defSave......");
		boolean deploy = Boolean.parseBoolean(getRequest().getParameter("deploy"));
		//转化xml文件格式
		if(proDefinition.getDrawDefXml()!= null && !proDefinition.getDrawDefXml().equals("")){
			String text = convertFlexXmlToJbpmXml(proDefinition.getDrawDefXml(), proDefinition.getProcessName());
			proDefinition.setDefXml(text); 
			logger.debug("解析的JBPM对应的xml文件：\n" + text);
		}

		// 检查流程名称的唯一性
		if (!proDefinitionService.checkNameByVo(proDefinition)) {
			// 假如不唯一
			setJsonString("{success:false,msg:'流程名称(系统中使用)已存在,请重新填写.'}");
			return SUCCESS;
		}
		if (!proDefinitionService.checkProcessNameByVo(proDefinition)) {
			// 假如不唯一
			setJsonString("{success:false,msg:'流程名称(定义中使用)已存在,请重新填写.'}");
			return SUCCESS;
		}
		if (deploy) {
			save();
		} else {
			Long proTypeId = proDefinition.getProTypeId();
			if (proTypeId != null) {
				GlobalType proType = globalTypeService.get(proTypeId);
				proDefinition.setProType(proType);
			}
			proDefinition.setCreatetime(new Date());
			proDefinitionService.save(proDefinition);
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}

	/**
	 * @description 转变xml文件的格式
	 * @param str
	 * @param processName JBPM流程Key
	 * @return
	 */
	private String convertFlexXmlToJbpmXml(String xml,String processName) {
		String text = "";
		if (xml != null && !xml.equals("")) {
			org.dom4j.Document document = XmlUtil.stringToDocument(xml);
			Element element = document.getRootElement();
			BpmFactory factory = new BpmFactory(document);
			@SuppressWarnings("unchecked")
			Iterator<Element> it = element.elements().iterator();
			text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <process name=\"" + processName + "\" xmlns=\"http://jbpm.org/4.4/jpdl\">";
			while (it.hasNext()) {
				Element el = it.next();
				String str = factory.getInfo(el, el.getName());
				text += str;
			}
			text += "</process>";
		}
		return text;
	}
	/**
	 * 添加及保存操作
	 */
	public String save() {

		Long proTypeId = proDefinition.getProTypeId();
		if (proTypeId != null) {
			GlobalType proType = globalTypeService.get(proTypeId);
			proDefinition.setProType(proType);
		}

		String deploy = getRequest().getParameter("deploy");
		if (logger.isDebugEnabled()) {
			logger.debug("---deploy---" + deploy);
		}

		if (proDefinition.getDefId() != null) {// 更新操作
			ProDefinition proDef = proDefinitionService.get(proDefinition
					.getDefId());
			try {
				BeanUtil.copyNotNullProperties(proDef, proDefinition);
				if ("true".equals(deploy)) {
					jbpmService.saveOrUpdateDeploy(proDef);
				} else {
					proDefinitionService.save(proDef);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else {// 添加流程
			proDefinition.setCreatetime(new Date());

			if (logger.isDebugEnabled()) {
				logger.debug("---start deploy---");
			}

			if ("true".equals(deploy)) {
				jbpmService.saveOrUpdateDeploy(proDefinition);
			} else {
				proDefinitionService.save(proDefinition);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 通过defId取得某个流程是使用表单模板还是在线的定制模板
	 * @return
	 */
	public String formTemp(){
		StringBuffer sb=new StringBuffer("{success:true");
		Short isUseTemplate=FormDefMapping.NOT_USE_TEMPLATE;
		
		ProDefinition proDefinition=proDefinitionService.get(defId);
		
		if(proDefinition!=null && proDefinition.getDeployId()!=null){
			FormDefMapping fdm=formDefMappingService.getByDeployId(proDefinition.getDeployId());
			if(fdm!=null){
				isUseTemplate=fdm.getUseTemplate();
				sb.append(",mappingId:").append(fdm.getMappingId());
			}
		}
		sb.append(",isUseTemplate:"+isUseTemplate+"}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 保存formMapping
	 * @return
	 */
	public String saveFm(){
		
		ProDefinition pd=proDefinitionService.get(defId);
		String useTemplate=getRequest().getParameter("useTemplate");
		
		if(pd!=null && pd.getDeployId()!=null){
			FormDefMapping mapping=formDefMappingService.getByDeployId(pd.getDeployId());
			Short isUseTemplate=FormDefMapping.NOT_USE_TEMPLATE;
			if("true".equals(useTemplate)){
				isUseTemplate=FormDefMapping.USE_TEMPLATE;
			}
			if(mapping!=null){
				mapping.setUseTemplate(isUseTemplate);
			}else{
				mapping=new FormDefMapping();
				mapping.setUseTemplate(isUseTemplate);
				mapping.setDefId(pd.getDefId());
				mapping.setDeployId(pd.getDeployId());
				mapping.setVersionNo(pd.getNewVersion());
			}
			formDefMappingService.save(mapping);

			if("true".equals(useTemplate)){
				List formTemplateList=formTemplateService.getByMappingId(mapping.getMappingId());
				if(formTemplateList.size()==0){
					//为该流程映射添加所有表单定义
					List<Node> nodes = jbpmService.getFormNodesByDeployId(new Long(pd.getDeployId()));
					List<String>nodeNames=new ArrayList<String>();
					for(Node node:nodes){
						nodeNames.add(node.getName());
					}
					formTemplateService.batchAddDefault(nodeNames, mapping);
				}
			}
			setJsonString("{success:true,mappingId:"+mapping.getMappingId()+"}");
		}

		return SUCCESS;
	}

	
	/**
	 * 取得表单映射
	 * TODO
	 * @return
	 */
	public String getFormMapping(){
		ProDefinition pd=proDefinitionService.get(defId);
		StringBuffer sb=new StringBuffer();
		Short isUseTemplate=FormDefMapping.NOT_USE_TEMPLATE;
		if(pd!=null && pd.getDeployId()!=null){
			FormDefMapping mapping=formDefMappingService.getByDeployId(pd.getDeployId());
			if(mapping!=null){
				
			}
		}
		
		return SUCCESS;
	}

	/**
	 * 修改流程状态
	 */
	public String update() {
		ProDefinition orgProDefinition = proDefinitionService.get(proDefinition
				.getDefId());
		orgProDefinition.setStatus(proDefinition.getStatus());
		proDefinitionService.save(orgProDefinition);

		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 判断该流程是否可以重新设置表单
	 */
	public String checkRun(){
		//1.判断流程是否存为运行中的流程[process_run]
		boolean isRun = processRunService.checkRun(defId); //存在
		String msg ="{success:true}";
		if(isRun)//存在
			msg = "{failure:true,msg:'对不起，该流程正在运行不能设置，请谅解！'}";
		setJsonString(msg);
		return SUCCESS;
	}
	
	
	public String inList(){
//		QueryFilter filter = new QueryFilter(getRequest());
		PagingBean pb =getInitPagingBean();
//		filter.setFilterName("InstanceProcess");
//		filter.addParamValue(ProcessRun.RUN_STATUS_RUNNING);
		
		List<ProDefinition> list=proDefinitionService.findRunningPro(proDefinition, ProcessRun.RUN_STATUS_RUNNING, pb);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(
				",result:[");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for(ProDefinition pd:list){
        	 buff.append("{defId:'").append(pd.getDefId())
        	 .append("',subTotal:").append(processRunService.countRunningProcess(pd.getDefId()))
        	 .append(",typeName:'").append(pd.getProType()==null?"":pd.getProType().getTypeName())
        	 .append("',name:'").append(pd.getName())
        	 .append("',createtime:'").append(sdf.format(pd.getCreatetime()))
        	 .append("',deployId:'").append(pd.getDeployId())
        	 .append("',status:").append(pd.getStatus()).append("},");
        }
        if(list.size()>0){
        	buff.deleteCharAt(buff.length()-1);
        }
		buff.append("]}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 为分公司分配标准流程
	 * add by lu 2012.09.12
	 * @return
	 */
	public String assignFlows(){
		//boolean deploy = true;
		String branchCompanyId = getRequest().getParameter("branchCompanyId");
		String defIds = getRequest().getParameter("defIds");
		String processNameSubCompany = "";
		if(branchCompanyId!=null&&!"".equals(branchCompanyId)&&defIds!=null&&!"".equals(defIds)){
			String[] proArrs = defIds.split(",");
			for(int i=0;i<proArrs.length;i++){
				ProDefinition proDef = new ProDefinition();
				String defId = proArrs[i];
				ProDefinition definition = proDefinitionService.get(new Long(defId));
				Organization organization = organizationService.get(new Long(branchCompanyId));
				if(definition!=null&&organization!=null){
					GlobalType proType = globalTypeService.get(definition.getProTypeId());
					processNameSubCompany = definition.getProcessName()+"_"+organization.getKey();
					//转化xml文件格式
					if(definition.getDrawDefXml()!= null && !definition.getDrawDefXml().equals("")){
						String branchNO = "BranchCompanyAbbreviationIsNull";
						if(organization.getBranchNO()!=null&&!"".equals(organization.getBranchNO())){//分公司编号不为空
							branchNO = organization.getBranchNO();
						}
						String text = convertFlexXmlToJbpmXml(definition.getDrawDefXml(), processNameSubCompany);
						proDef.setProType(proType);
						//proDef.setName(definition.getName()+"_"+organization.getOrgName());
						//proDef.setDescription(definition.getName()+"_"+organization.getOrgName());
						proDef.setName(definition.getName()+"_"+branchNO);
						proDef.setDescription(definition.getName()+"_"+branchNO);
						proDef.setCreatetime(new Date());
						proDef.setBranchCompanyId(new Long(branchCompanyId));
						proDef.setDefXml(text); 
						//proDef.setDeployId(definition.getDeployId());
						proDef.setStatus(ProDefinition.STATUS_ENABLE);
						proDef.setDrawDefXml(definition.getDrawDefXml());
					}

					// 检查流程名称的唯一性
					if (!proDefinitionService.checkNameByVo(proDef)) {   
						// 假如不唯一
						jsonString="{success:false,msg:'流程名称(系统中使用)已存在,请重新填写.'}";
						return SUCCESS;
					}
					if (!proDefinitionService.checkProcessNameByVo(proDef)) {
						// 假如不唯一
						jsonString="{success:false,msg:'流程名称(定义中使用)已存在,请重新填写.'}";
						return SUCCESS;
					}
					String msg = jbpmService.saveNewFlow(proDef,organization.getKey(),definition.getDeployId(),false,null);
					setJsonString(msg);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 标准版本的某个流程发布新版本后，执行更新已经分配了该流程的分公司的对应流程。
	 * @return
	 * add by lu 2013.07.04
	 */
	public String updateBranchCompanyFlowVersion(){
		String orgIds = getRequest().getParameter("orgIds");
		String defId = getRequest().getParameter("defId");
		String msg = "";
		if(orgIds!=null&&!"".equals(orgIds)&&defId!=null&&!"".equals(defId)){
			ProDefinition pro = proDefinitionService.get(new Long(defId));
			if(pro!=null){
				String[] proArrs = orgIds.split(",");
				for(int i=0;i<proArrs.length;i++){
					String orgId = proArrs[i];
					Organization organization = organizationService.get(Long.valueOf(orgId));
					ProDefinition branchPro = proDefinitionService.getByCompanyIdProcessName(Long.valueOf(orgId), pro.getProcessName());
					if(branchPro!=null){
						String text = convertFlexXmlToJbpmXml(pro.getDrawDefXml(), branchPro.getProcessName());
						if(text.equals(branchPro.getDefXml())){
							msg = "{success:false,msg:'已经为<"+organization.getOrgName()+">更新了<"+pro.getName()+">!'}";
						}else{
							branchPro.setDefXml(text);
							branchPro.setDrawDefXml(pro.getDrawDefXml());
							msg = jbpmService.saveNewFlow(branchPro,organization.getKey(),pro.getDeployId(),true,branchPro.getDeployId());
						}
						setJsonString(msg);
					}
				}
			}
		}
		return SUCCESS;
	}
}
