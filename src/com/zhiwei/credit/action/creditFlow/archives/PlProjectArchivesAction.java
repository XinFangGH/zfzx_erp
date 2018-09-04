package com.zhiwei.credit.action.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.archives.PlProjectArchives;
import com.zhiwei.credit.service.creditFlow.archives.PlArchivesService;
import com.zhiwei.credit.service.creditFlow.archives.PlProjectArchivesService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlProjectArchivesAction extends BaseAction{
	@Resource
	private PlProjectArchivesService plProjectArchivesService;
	@Resource
	private PlArchivesService plArchivesService;
	private PlProjectArchives plProjectArchives;
	private String businessType;
	private Long projtoarchiveId;
	private Long projectId;

	public Long getProjtoarchiveId() {
		return projtoarchiveId;
	}

	public void setProjtoarchiveId(Long projtoarchiveId) {
		this.projtoarchiveId = projtoarchiveId;
	}

	public PlProjectArchives getPlProjectArchives() {
		return plProjectArchives;
	}

	public void setPlProjectArchives(PlProjectArchives plProjectArchives) {
		this.plProjectArchives = plProjectArchives;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlProjectArchives> list= plProjectArchivesService.getAll(filter);
		
		Type type=new TypeToken<List<PlProjectArchives>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
public String projectlist(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		Map<String,String> map = new HashMap<String,String>();
		int size=0;
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
    	businessType=(String)getRequest().getParameter("businessType");
		List list=plProjectArchivesService.searchproject(map, businessType);
		if(null !=list){
			size=plProjectArchivesService.searchprojectsize(map, businessType);
			
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("startDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"startDate"});
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
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
				plProjectArchivesService.remove(new Long(id));
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
		PlProjectArchives plProjectArchives=plProjectArchivesService.get(projtoarchiveId);
	//	PlArchives plArchives= plArchivesService.get(plProjectArchives.getPlarchivesId());
	//	plProjectArchives.setCheckername(plArchivesService.get(plArchives.getParentid()).getName()+"-"+plArchives.getCode());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plProjectArchives));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	public String getbyprojectId(){
		List<PlProjectArchives> list=plProjectArchivesService.getbyproject(projectId, businessType);
		if(null !=list &&list.size()>0){
		 plProjectArchives=plProjectArchivesService.getbyproject(projectId, businessType).get(0);
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plProjectArchives));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plProjectArchives.getProjtoarchiveId()==null){
			String isArchives1=this.getRequest().getParameter("isArchives");
			/*//add by gao这段话是有意义的，当前台传递了两个businessType 的时候，如果采用struts的set get方法获取到的businessType =  "SmallLoan,SmallLoan"
			businessType=(String)getRequest().getParameter("businessType");//方式1*/
			if(null!=businessType){
				businessType = businessType.split(",")[0];//方式2
			}
			plProjectArchives.setBusinessType(businessType);
			plProjectArchives.setProjectId(projectId);
			plProjectArchivesService.save(plProjectArchives);
		}else{
			plProjectArchives.setBusinessType(businessType);
			plProjectArchives.setProjectId(projectId);
			PlProjectArchives orgPlProjectArchives=plProjectArchivesService.get(plProjectArchives.getProjtoarchiveId());
			try{
				BeanUtil.copyNotNullProperties(orgPlProjectArchives, plProjectArchives);
				plProjectArchivesService.save(orgPlProjectArchives);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
