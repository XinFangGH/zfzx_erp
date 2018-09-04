package com.zhiwei.credit.service.flow.impl;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.dom4j.Element;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.FormDefMappingDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.bpm.ILog.factory.BpmFactory;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.system.GlobalTypeService;

public class ProDefinitionServiceImpl extends BaseServiceImpl<ProDefinition>
		implements ProDefinitionService {

	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private FormDefMappingDao formDefMappingDao;

	private ProDefinitionDao dao;

	public ProDefinitionServiceImpl(ProDefinitionDao dao) {
		super(dao);
		this.dao = dao;
	}

	public ProDefinition getByDeployId(String deployId) {
		return dao.getByDeployId(deployId);
	}

	public ProDefinition getByName(String name) {
		return dao.getByName(name);
	}

	@Override
	public List<ProDefinition> getByRights(AppUser curUser,
			ProDefinition proDefinition, QueryFilter filter) {
		return dao.getByRights(curUser, proDefinition, filter);
	}

	@Override
	public boolean checkNameByVo(ProDefinition proDefinition) {
		return dao.checkNameByVo(proDefinition);
	}

	@Override
	public boolean checkProcessNameByVo(ProDefinition proDefinition) {
		return dao.checkProcessNameByVo(proDefinition);
	}

	@Override
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,
			Short runstate, PagingBean pb) {
		return dao.findRunningPro(proDefinition, runstate, pb);
	}

	/**
	 * 保存流程信息
	 */
	@Override
	public String defSave(ProDefinition proDefinition, Boolean deploy) {
		logger.info("...eneter defSave......");
		String msg = "";
		// 转化xml文件格式
		if (proDefinition.getDrawDefXml() != null && !proDefinition.getDrawDefXml().equals("")) {
			String text = change(proDefinition.getDrawDefXml(), proDefinition.getProcessName());
			proDefinition.setDefXml(text);
			logger.debug("解析的JBPM对应的xml文件：\n" + text);
		}
		//流程类型
		Long proTypeId = proDefinition.getProTypeId();
		if (proTypeId != null) {
			GlobalType proType = globalTypeService.get(proTypeId);
			proDefinition.setProType(proType);
		}

		// 检查流程名称的唯一性
		if (!checkNameByVo(proDefinition)) {
			// 假如不唯一
			msg = "流程名称(系统中使用)已存在,请重新填写.";
		}
		if (!checkProcessNameByVo(proDefinition)) {
			// 假如不唯一
			msg = "流程名称(定义中使用)已存在,请重新填写.";
		}
		if(deploy){
			save(proDefinition,deploy.toString());
			msg = "true";
			logger.debug("flex流程设计【保存并发布】操作返回结果：" + msg);
		} else {			
			proDefinition.setCreatetime(new Date());
			save(proDefinition);
			msg = "true";
			logger.debug("flex流程设计【保存】操作返回结果：" + msg);
		}
		return msg;
	}

	/**
	 * @description 转变xml文件的格式
	 * @param str
	 * @param processName
	 *            JBPM流程Key
	 * @return
	 */
	private String change(String xml, String processName) {
		String text = "";
		if (xml != null && !xml.equals("")) {
			org.dom4j.Document document = XmlUtil.stringToDocument(xml);
			Element element = document.getRootElement();
			BpmFactory factory = new BpmFactory(document);
			@SuppressWarnings("unchecked")
			Iterator<Element> it = element.elements().iterator();
			text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <process name=\""
					+ processName + "\" xmlns=\"http://jbpm.org/4.4/jpdl\">";
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
	private void save(ProDefinition proDefinition, String deploy) {
		if (logger.isDebugEnabled()) {
			logger.debug("---deploy---" + deploy);
		}
		if (proDefinition.getDefId() != null) {// 更新操作
			ProDefinition proDef = get(proDefinition.getDefId());
			try {
				BeanUtil.copyNotNullProperties(proDef, proDefinition);
				if ("true".equals(deploy))
					jbpmService.saveOrUpdateDeploy(proDef);
				else 
					save(proDef);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else {// 添加流程
			proDefinition.setCreatetime(new Date());
			
			if (logger.isDebugEnabled()) {
				logger.debug("---start deploy---");
			}
			
			if ("true".equals(deploy))
				jbpmService.saveOrUpdateDeploy(proDefinition);
			else 
				save(proDefinition);
		}
	}
	
	/**
	 * @description 复制标准流程的节点文件(vm)到新创建的分公司目录版本下。
	 * @param ProDefinition
	 * @param oldDeployId
	 * add by lu 2012.09.25
	 */
	public String copyNormalVMToBranchCompany(ProDefinition proDefinition,String oldDeployId){
		String msg = "";
		//String blocName = "";//集团(公司)名称
		String branchCompanyOrBlocName = "";//分公司名称(修改为分公司缩写2012.11.23)
		String operationType = "";//业务品种类型
		String rootPath = AppUtil.getAppAbsolutePath();
		ProDefinition oldProDefinition = dao.getByDeployId(oldDeployId);
		Organization orgBranchCompany = organizationDao.get(proDefinition.getBranchCompanyId());
		if(orgBranchCompany!=null&&!"0".equals(orgBranchCompany.toString())&&!"1".equals(orgBranchCompany.toString())){
			branchCompanyOrBlocName = orgBranchCompany.getVmName();//vmName和orgName一致,而且不可编辑。orgName可以修改。
			//branchCompanyName = orgBranchCompany.getOrgName();
			//branchCompanyName = orgBranchCompany.getAcronym();
		}else{
			branchCompanyOrBlocName = "总公司";
			/*List<Organization> organizationList = organizationDao.getByParent(new Long(0), new Long(1));
			if(organizationList!=null&&organizationList.size()!=0){
				blocName = organizationList.get(0).getOrgName();
			}*/
		}
		
		if(oldProDefinition!=null){
			String nodeKey = oldProDefinition.getProType().getNodeKey();
			if(nodeKey!=null&&!"".equals(nodeKey)&&nodeKey.indexOf("_")!=-1){
				String[] proArrs = nodeKey.split("_");
				if(proArrs.length>1){//Guarantee_definitionType_test_CompanyBusiness无论多少级,获取的都是最后一个属性值。而且必定是一级目录以上。
					operationType = proArrs[proArrs.length-1];
				}
			}
		}
		
		//新流程的流程文件路径，CompanyBusiness为流程分类数中Guarantee_test_test1_CompanyBusiness最后的属性值。
		//  如：WEB-INF\FlowForm\XX分公司\CompanyBusiness\小额贷款常规流程_XX分公司\3
		//或者：WEB-INF\FlowForm\总公司\CompanyBusiness\小额贷款常规流程_XX分公司\3
		String wholePath = rootPath + "WEB-INF/FlowForm/" + branchCompanyOrBlocName + "/" + operationType + "/" + proDefinition.getName() + "/" + proDefinition.getNewVersion();
		File wholePathFile = new File(wholePath);
		
		//标准流程的流程文件路径
		String normalFlowsPath = rootPath + "WEB-INF/FlowForm/标准流程/" + oldProDefinition.getName() + "/" + oldProDefinition.getNewVersion();
		File normalFlowsPathFile = new File(normalFlowsPath);
		if (!wholePathFile.exists()) {
			if (wholePathFile.mkdirs()) {
				if (!normalFlowsPathFile.exists()) {
					msg = "{success:true,msg:'已为分公司分配流程和设置基本信息。标准流程的文件路径："+normalFlowsPath+"文件夹不存在,请手动复制标准流程的VM文件到分公司对应目录下!'}";
					logger.debug("ProDefinitionServiceImpl-->"+msg);
				}else{
					msg = this.copyVMFiles(normalFlowsPathFile, wholePathFile,wholePath,branchCompanyOrBlocName,proDefinition);
				}
			}
		}else{
			this.copyVMFiles(normalFlowsPathFile, wholePathFile,wholePath,branchCompanyOrBlocName,proDefinition);
			msg = "{success:true,msg:'已为分公司分配流程和设置基本信息。"+wholePath+"路径已经存在,"+oldProDefinition.getName()+"/"+oldProDefinition.getNewVersion()+"的流程文件已经覆盖已存在的文件!'}";
			logger.debug("ProDefinitionServiceImpl-->"+msg);
		}
		dao.save(proDefinition);
		FormDefMapping formDefMapping = new FormDefMapping();
		formDefMapping.setDefId(proDefinition.getDefId());
		formDefMapping.setVersionNo(proDefinition.getNewVersion());
		formDefMapping.setDeployId(proDefinition.getDeployId());
		formDefMapping.setUseTemplate(FormDefMapping.USE_TEMPLATE);
		formDefMapping.setProDefinition(proDefinition);
		formDefMappingDao.save(formDefMapping);
		return msg;
	}
	
	private String copyVMFiles(File normalFlowsPathFile,File wholePathFile,String wholePath,String branchCompanyName,ProDefinition proDefinition){
		String msg = "";
		try {
			FileUtils.copyDirectory(normalFlowsPathFile, wholePathFile);
			String vmFileName = wholePathFile+"/开始.vm";
	        File myFile=new File(vmFileName);
	        InputStreamReader isr = new InputStreamReader(new FileInputStream(myFile), "UTF-8");
	        if(!myFile.exists()){ 
	            logger.error("Can't Find " + vmFileName);
	        }else{
	        	 BufferedReader readerFile = new BufferedReader(isr);
	        	 String str;
		            str = readerFile.readLine();
		            if(str!=null&&!"".equals(str)&&str.contains("/")){
		            	String[] proArrs = str.split("/");
		        		String vmName = "";
		        		for(int i=0;i<proArrs.length;i++){
		        			if(proArrs[i].contains(".vm")){
		        				vmName = proArrs[i].toString();
		        			}
		        		}
		        		FileOutputStream wirteVMFile = new FileOutputStream(wholePath+"/开始.vm");
		        		wirteVMFile.write(new String("#parse('/"+proDefinition.getName()+"/"+proDefinition.getNewVersion()+"/"+vmName).getBytes());
		    			wirteVMFile.close();
		            }
	        }
			msg = "{success:true,msg:'操作完成!'}";
		} catch (IOException e) {
			e.printStackTrace();
			msg = "{success:false,msg:'复制标准流程的文件到分公司"+branchCompanyName+"下出错!'}";
			logger.error(msg+e.getMessage());
		}
		return msg;
	}
	
	/**
	 * @description 根据分公司id查询对应流程信息列表
	 * @param branchCompanyId
	 */
	public List<ProDefinition> getByBranchCompanyId(Long branchCompanyId,PagingBean pb,String flowName,String flowDesc){
		return dao.getByBranchCompanyId(branchCompanyId,pb,flowName,flowDesc);
	}

	@Override
	public ProDefinition getProdefinitionByProcessName(String name) {
		return this.dao.getByProcessName(name);
	}

	@Override
	public List<ProDefinition> getByProTypeId(Long proTypeId,boolean isGroupVersion,Long branchCompanyId) {
		
		return this.dao.getByProTypeId(proTypeId,isGroupVersion,branchCompanyId);
	}

	@Override
	public ProDefinition getByKey(String key) {
		return this.dao.getByKey(key);
	}
	
	/**
	 * @description 根据流程分类id查询对应标准流程信息列表
	 * @param proTypeId
	 * add by lu 2012.11.13
	 */
	@Override
	public List<ProDefinition> getNormalFlowByProTypeId(Long proTypeId){
		return dao.getNormalFlowByProTypeId(proTypeId);
	}
	
	/**
	 * @description 根据流程的key查询已经分配了该流程的分公司
	 * @param processName
	 * add by lu 2013.07.04
	 */
	@Override
	public List<ProDefinition> listByProcessName(String processName){
		return dao.listByProcessName(processName);
	}
	
	/**
	 * 根据companyId和processName查询对应的流程定义
	 * @param companyId
	 * @param processName
	 * add by lu 2012.12.27
	 */
	public ProDefinition getByCompanyIdProcessName(Long companyId,String processName){
		return dao.getByCompanyIdProcessName(companyId, processName);
	}
}