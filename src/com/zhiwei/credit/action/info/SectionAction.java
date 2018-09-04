package com.zhiwei.credit.action.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.info.Section;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.service.info.SectionService;
import com.zhiwei.credit.service.system.SysConfigService;
/**
 * 
 * @author 
 *
 */
public class SectionAction extends BaseAction{
	@Resource
	private SectionService sectionService;
	@Resource
	private SysConfigService sysConfigService ;
	private Section section;
	
	private Long sectionId;

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("rowNumber", "asc");
		List<Section> list= sectionService.getAll(filter);
		
		Type type=new TypeToken<List<Section>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		SysConfig sectionColumn = sysConfigService.findByKey("sectionColumn");
		buff.append(",columnType:");
		if(sectionColumn!=null){
			buff.append(sectionColumn.getDataValue());
		}else{
			buff.append("2");//默认为两列
		}
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
				sectionService.remove(new Long(id));
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
		Section section=sectionService.get(sectionId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(section));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		StringBuffer result = new StringBuffer("{success:true");
		if(section.getSectionId()==null){
			AppUser curUser = ContextUtil.getCurrentUser();
			section.setCreatetime(new Date());
			section.setUserId(curUser.getUserId());
			section.setUsername(curUser.getFullname());
			
			section.setColNumber(Section.COLUMN_ONE);//默认在第一列出现
			section.setRowNumber(sectionService.getLastColumn() + 1); //默认在最后一行出现
			sectionService.save(section);
			
			Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
			result.append(",data:").append(gson.toJson(section)).append("}");
		}else{
			result.append("}");
			Section orgSection=sectionService.get(section.getSectionId());
			try{
				BeanUtil.copyNotNullProperties(orgSection, section);
				sectionService.save(orgSection);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		
		setJsonString(result.toString());
		return SUCCESS;
		
	}
	
	/**
	 * 禁用栏目
	 * @return
	 */
	public String disable(){
		String sectionId = getRequest().getParameter("sectionId");
		if(StringUtils.isNotEmpty(sectionId)){
			section = sectionService.get(new Long(sectionId));
		}
		if(section != null){
			section.setStatus(Section.STATUS_DISABLE);
			sectionService.save(section);
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 激活栏目
	 * @return
	 */
	public String enable(){
		String secIds = getRequest().getParameter("secIds");
		if(StringUtils.isNotEmpty(secIds)){
			String[] ids = secIds.split(",");
			for(String sectionId : ids){
				section = sectionService.get(new Long(sectionId));
				if(section != null){
					section.setStatus(Section.STATUS_ENABLE);
					sectionService.save(section);
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 */
	public String position(){
		String items=getRequest().getParameter("sections");
		Gson gson = new Gson();
		Section[] sections = gson.fromJson(items,
				Section[].class);
		AppUser user=ContextUtil.getCurrentUser();
		
		for(Section sec:sections){
			Section orgSection=sectionService.get(sec.getSectionId());
			orgSection.setColNumber(sec.getColNumber());
			orgSection.setRowNumber(sec.getRowNumber());
			orgSection.setStatus(Section.STATUS_ENABLE);
			sectionService.save(orgSection);
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 */
	public String column(){
		String columnType = getRequest().getParameter("columnType");
		SysConfig sectionColumn = sysConfigService.findByKey("sectionColumn");
		if(sectionColumn ==null){
			sectionColumn = new SysConfig();
			sectionColumn.setConfigDesc("栏目列数配置");
			sectionColumn.setConfigKey("sectionColumn");
			sectionColumn.setConfigName("栏目列数");
			sectionColumn.setDataType(SysConfig.SYS_DATA_TYPE_INTEGER);
			sectionColumn.setDataValue(columnType);
			sectionColumn.setTypeKey("sectionColumn");
			sectionColumn.setTypeName("栏目列数配置");
			sysConfigService.save(sectionColumn);
		}else{
			sectionColumn.setDataValue(columnType);
			sysConfigService.save(sectionColumn);
		}
		
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
