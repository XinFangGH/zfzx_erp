package com.zhiwei.credit.action.creditFlow.common;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.service.creditFlow.common.GlobalSupervisemanageService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class GlobalSupervisemanageAction extends BaseAction{
	@Resource
	private GlobalSupervisemanageService globalSupervisemanageService;
	private GlobalSupervisemanage globalSupervisemanage;
	private Long projectId;
	
	private Long superviseManageId;
	private String superviseMangeJsonData;
	private String businessType;
	
	

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getSuperviseManageId() {
		return superviseManageId;
	}

	public void setSuperviseManageId(Long superviseManageId) {
		this.superviseManageId = superviseManageId;
	}

	public GlobalSupervisemanage getGlobalSupervisemanage() {
		return globalSupervisemanage;
	}

	public void setGlobalSupervisemanage(GlobalSupervisemanage globalSupervisemanage) {
		this.globalSupervisemanage = globalSupervisemanage;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getSuperviseMangeJsonData() {
		return superviseMangeJsonData;
	}

	public void setSuperviseMangeJsonData(String superviseMangeJsonData) {
		this.superviseMangeJsonData = superviseMangeJsonData;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addFilter("Q_projectId_L_EQ", projectId.toString());
		filter.addFilter("Q_businessType_S_EQ", businessType);
		List<GlobalSupervisemanage> list= globalSupervisemanageService.getListByProjectId(projectId,businessType);
		Type type=new TypeToken<List<GlobalSupervisemanage>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(1).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
		if(ids!=null&&ids.length!=0){
			for(String id:ids){
				if(!id.equals("")){
				globalSupervisemanageService.remove(new Long(id));
				}
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
		GlobalSupervisemanage globalSupervisemanage=globalSupervisemanageService.get(superviseManageId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(globalSupervisemanage));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(null != superviseMangeJsonData && !"".equals(superviseMangeJsonData)) {
			String[] dataStr = superviseMangeJsonData.split("@");
			for (int i = 0; i < dataStr.length; i++) {
				String str = dataStr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					GlobalSupervisemanage globalSupervisemanage = (GlobalSupervisemanage)JSONMapper.toJava(parser.nextValue(), GlobalSupervisemanage.class);
					globalSupervisemanage.setProjectId(projectId);
					globalSupervisemanage.setBusinessType(businessType);
					if(globalSupervisemanage.getSuperviseManageId()==null){
						globalSupervisemanageService.save(globalSupervisemanage);
					}else{
						GlobalSupervisemanage orgGlobalSupervisemanage=globalSupervisemanageService.get(globalSupervisemanage.getSuperviseManageId());
						try{
							BeanUtil.copyNotNullProperties(orgGlobalSupervisemanage, globalSupervisemanage);
							globalSupervisemanageService.save(orgGlobalSupervisemanage);
						}catch(Exception ex){
							logger.error(ex.getMessage());
						}
					}
					//制定监管计划后检测一下，如果是今天直接派发任务，如果不是今日再用定时器派发任务！不用每隔两分钟就定时扫一下
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
					if(sd.format(new Date()).equals(sd.format(globalSupervisemanage.getDesignSuperviseManageTime()))){
						globalSupervisemanageService.startSuperviseFlow(globalSupervisemanage);
					}
					setJsonString("{success:true}");
				}catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex.getMessage());
				}
				
			}
		}
		return SUCCESS;
	}
	
	public String getInfo(){
		GlobalSupervisemanage globalSupervisemanage = globalSupervisemanageService.get(Long.valueOf(getRequest().getParameter("superviseManageId")));
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("superviseManageTime");
		sb.append(serializer.exclude(new String[] { "class", "GlobalSupervisemanage" })
				.serialize(globalSupervisemanage));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
}
