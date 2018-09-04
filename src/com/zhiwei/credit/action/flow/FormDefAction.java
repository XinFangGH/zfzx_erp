package com.zhiwei.credit.action.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.FileUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.FieldRights;
import com.zhiwei.credit.model.flow.FormDef;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.model.flow.FormTableItem;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.flow.FieldRightsService;
import com.zhiwei.credit.service.flow.FormDefMappingService;
import com.zhiwei.credit.service.flow.FormDefService;
import com.zhiwei.credit.service.flow.FormTableGenService;
import com.zhiwei.credit.service.flow.FormTableService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.system.OrganizationService;

/**
 * 
 * @author
 * 
 */
public class FormDefAction extends BaseAction {
	@Resource
	private FormDefService formDefService;
	@Resource
	private FormTableGenService formTableGenService;
	@Resource
	private FormTableService formTableService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private FormDefMappingService formDefMappingService;
	@Resource
	private FieldRightsService fieldRightsService;
	@Resource
	private OrganizationService organizationService;

	private FormDef formDef;

	private FormTable formTable;

	private FormTable formTable1;

	private Long formDefId;
	private Long defId;// 流程定义id

	public Long getFormDefId() {
		return formDefId;
	}

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public FormDef getFormDef() {
		return formDef;
	}

	public void setFormDef(FormDef formDef) {
		this.formDef = formDef;
	}

	public FormTable getFormTable() {
		return formTable;
	}

	public void setFormTable(FormTable formTable) {
		this.formTable = formTable;
	}

	public FormTable getFormTable1() {
		return formTable1;
	}

	public void setFormTable1(FormTable formTable1) {
		this.formTable1 = formTable1;
	}

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<FormDef> list = formDefService.getAll(filter);

		Type type = new TypeToken<List<FormDef>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

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
				formDefService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		FormDef formDef = formDefService.get(formDefId);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(formDef));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 生成实体
	 */

	public String gen() {
		FormDef formDef = formDefService.get(formDefId);
		Set<FormTable> tables = formDef.getFormTables();
		if (tables.size() > 0) {
			FormTable[] formTables = new FormTable[tables.size()];
			Iterator<FormTable> it = tables.iterator();
			int i=0;
			while (it.hasNext()) {
				formTables[i++]=it.next();
			}
			if(formTableGenService.genBean(formTables)==true)
				setJsonString("{success:true}");
			else
				setJsonString("{failure:true}");
			
			formDef.setIsGen(FormDef.HAS_GEN);
			formDefService.save(formDef);
		}
		
		return SUCCESS;
	}
	/*
	 * 生成全部实体
	 */
	public String genAll() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_formDef.status_SN_EQ", FormDef.HAS_Pub.toString());
		List<FormTable> tables = formTableService.getAll(filter);
		if (tables.size() > 0) {
			FormTable[] formTables = new FormTable[tables.size()];
			Iterator<FormTable> it = tables.iterator();
			int i=0;
			while (it.hasNext()) {
				formTables[i++]=it.next();
			}
			if(formTableGenService.genBean(formTables))
				setJsonString("{success:true}");
			else
				setJsonString("{failure:true}");
			
			
		}
		
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */

	public String save() {

		Map<FormTable, String> datas = new HashMap<FormTable, String>();
		String tablesReq=getRequest().getParameter("objs");
		if(StringUtils.isNotEmpty(tablesReq)){
			Gson gson=new Gson();
			FormTableItem[] tables=gson.fromJson(tablesReq,FormTableItem[].class);
			for(FormTableItem formTableItem:tables){
                FormTable formTable=new FormTable();
                formTable.setTableId(formTableItem.getTableId());
                formTable.setTableKey(formTableItem.getTableKey());
                formTable.setTableName(formTableItem.getTableName());
                formTable.setIsMain(formTableItem.getIsMain());
                datas.put(formTable, formTableItem.getFields());
                /**
                 * 检测是否已经存在相同的表名
                 */
                List<FormTable> formTables=formTableService.findByTableKey(formTableItem.getTableKey());
                if(formTables.size()>2||(formTables.size()==1&&formTableItem.getTableId()==null)){
                	setJsonString("{success:false,msg:'"+formTableItem.getTableKey()+"'}");
                	return SUCCESS;
                }
			}
		}
//		String data1 = getRequest().getParameter("field1");
//		String data2 = getRequest().getParameter("field2");
//		datas.put(formTable, data1);
//		if (StringUtils.isNotEmpty(data2)) {
//			datas.put(formTable1, data2);
//		} else {
//			if (formTable1.getTableId() != null) {
//				formTableService.remove(formTable1.getTableId());
//			}
//		}
		
		formDef = formDefService.saveFormDef(formDef, datas);

		setJsonString("{success:true}");
		return SUCCESS;

	}

	/**
	 * 添加流程表单数据
	 */
	public String add() {
		String msg = "{success:true}";
		// 1.判断该流程中是否已经存在设置的表单数据，需要加上版本号
		ProDefinition pd=proDefinitionService.get(defId);
		//当流程定义更改时，需要重新定义其对应的表单
		FormDefMapping fm = formDefMappingService.getByDeployId(pd.getDeployId());
		FormDef fd = formDefService.get(formDefId);
		if (fm != null && fm.getFormDefId() == formDefId) { // 数据添加重复,该表单信息已经添加了
			msg = "{failure:true,msg:'对不起，该表单信息已经添加，请选择其他表单信息！'}";
		} else if (fm == null) { // 原来没有设置表单，直接添加数据
			// 2.根据defId从pro_definition表中获取newVersion[版本号]，deployId[工作流id]
			ProDefinition pdf = proDefinitionService.get(defId);
			// 3.向form_def_mapping表中添加数据
			FormDefMapping fdm = new FormDefMapping();
			fdm.setDeployId(pdf.getDeployId()); // deployId
			fdm.setDefId(defId); // defId
			fdm.setFormDef(fd); // formDefId
			fdm.setVersionNo(pdf.getNewVersion());// 版本号
			// 4.保存
			formDefMappingService.save(fdm);
		} else { // 原来已经设置表单信息，只更换FormDefMapping表中的formDefId
			/**
			 * 更改表单时，需要把之前表单的映射下的权限 如果存在，则需要删除
			 */
			List<FieldRights> rights=fieldRightsService.getByMappingId(fm.getMappingId());
			if(rights.size()>0){
				for(FieldRights right:rights){
					fieldRightsService.remove(right);
				}
			}
			fm.setFormDef(fd); // 修改
			
			formDefMappingService.save(fm);
		}
		setJsonString(msg);
		return SUCCESS;
	}
	
	public String check(){
		boolean isHas=formDefMappingService.formDefHadMapping(formDefId);
		if(isHas){
			setJsonString("{success:true,msg:'该表单已经同相应的流程关联了！'}");
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
	/**
	 * 保存表单VM与XML映射文件
	 * @return
	 */
	public String saveVmXml(){
		
		String defId=getRequest().getParameter("defId");
		String activityName=getRequest().getParameter("activityName");
		
		String vmSources=getRequest().getParameter("vmSources");
		
		//创建流程vm文件以集团(公司名称为主文件夹，如：WEB-INF/FlowForm/集团(公司)名称/流程名称/版本号/开始.vm)
		//获取组织结构的集团(公司)的名称
		String orgName = "";
		List<Organization> orgList = organizationService.getByParent(new Long(0), new Long(1));//行政维度的集团(公司)
		if(orgList!=null&&orgList.size()!=0){
			Organization org = orgList.get(0);
			orgName = org.getOrgName();
		}
		
		ProDefinition proDefinition=proDefinitionService.get(new Long(defId));
		String filePath=AppUtil.getAppAbsolutePath() + "/WEB-INF/FlowForm/"+orgName+"/" + proDefinition.getName() + "/" + proDefinition.getNewVersion() + activityName;
		//
		String vmFilePath=filePath+".vm";
		
		FileUtil.writeFile(vmFilePath,vmSources);
		
		setJsonString("{success:true}");
		
		return SUCCESS;
	}
	
	/**
	 * 取得表单VM与XML的源代码
	 * @return
	 */
	public String getVmXml(){
		String defId=getRequest().getParameter("defId");
		String activityName=getRequest().getParameter("activityName");
		
		//创建流程vm文件以集团(公司名称为主文件夹，如：WEB-INF/FlowForm/集团(公司)名称/流程名称/版本号/开始.vm)
		//获取组织结构的集团(公司)的名称
		String orgName = "";
		List<Organization> orgList = organizationService.getByParent(new Long(0), new Long(1));//行政维度的集团(公司)
		if(orgList!=null&&orgList.size()!=0){
			Organization org = orgList.get(0);
			orgName = org.getOrgName();
		}
		
		ProDefinition proDefinition=proDefinitionService.get(new Long(defId));
		String filePath=AppUtil.getAppAbsolutePath() + "/WEB-INF/FlowForm/"+orgName+"/" + proDefinition.getName() + "/" + proDefinition.getNewVersion()+ "/" + activityName;
		String vmFilePath=filePath+".vm";
		String vmSources=FileUtil.readFile(vmFilePath);

		Gson gson=new Gson();
		
		setJsonString("{success:true,vmSources:" + gson.toJson(vmSources) +"}");
		
		return SUCCESS;
	}
}
