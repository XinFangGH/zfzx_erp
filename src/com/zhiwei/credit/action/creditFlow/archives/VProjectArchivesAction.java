package com.zhiwei.credit.action.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.archives.PlArchives;
import com.zhiwei.credit.model.creditFlow.archives.VProjectArchives;
import com.zhiwei.credit.service.creditFlow.archives.PlArchivesService;
import com.zhiwei.credit.service.creditFlow.archives.PlProjectArchivesService;
import com.zhiwei.credit.service.creditFlow.archives.VProjectArchivesService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class VProjectArchivesAction extends BaseAction{
	@Resource
	private VProjectArchivesService vProjectArchivesService;
	@Resource
	private PlArchivesService plArchivesService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private PlProjectArchivesService plProjectArchivesService;
	private VProjectArchives vProjectArchives;
	private String businessType;
	private Long projtoarchiveId;
	private Long projectId;

	public Long getProjtoarchiveId() {
		return projtoarchiveId;
	}

	public void setProjtoarchiveId(Long projtoarchiveId) {
		this.projtoarchiveId = projtoarchiveId;
	}



	public PlArchivesService getPlArchivesService() {
		return plArchivesService;
	}

	public void setPlArchivesService(PlArchivesService plArchivesService) {
		this.plArchivesService = plArchivesService;
	}

	public VProjectArchives getvProjectArchives() {
		return vProjectArchives;
	}

	public void setvProjectArchives(VProjectArchives vProjectArchives) {
		this.vProjectArchives = vProjectArchives;
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
		List<VProjectArchives> list= vProjectArchivesService.getAll(filter);
		
		Type type=new TypeToken<List<VProjectArchives>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
public String projectlist(){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		List list=plProjectArchivesService.projectList(businessType, this.getRequest(),start,limit);
		Long count=plProjectArchivesService.projectCount(businessType, this.getRequest());
		StringBuffer buff=new StringBuffer("{success:true,'totalCounts':"+count+",result:[");
		if(null!=businessType && businessType.equals("SmallLoan")){
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				buff.append("{\"fundProjectId\":");
				buff.append(obj[0]);
				buff.append(",\"projectId\":");
				buff.append(obj[1]);
				buff.append(",\"projectName\":'");
				buff.append(obj[2]);
				buff.append("',\"projectNumber\":'");
				buff.append(obj[3]);
				buff.append("',\"projectMoney\":");
				buff.append(obj[4]);
				buff.append(",\"appUserName\":'");
				buff.append(obj[5]);
				buff.append("',\"createDate\":'");
				if(null!=obj[6]){
					buff.append(DateUtil.parseDate(obj[6].toString(), "yyyy-MM-dd"));
				}
				buff.append("',\"isArchives\":");
				buff.append(obj[7]);
				buff.append(",\"oppositeType\":'");
				buff.append(obj[8]);
				buff.append("',\"projtoarchiveId\":");
				buff.append(obj[9]);
				buff.append(",\"businessType\":'");
				buff.append(obj[10]);
				buff.append("'},");
			}
		}else{
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				buff.append("{\"projectId\":");
				buff.append(obj[0]);
				buff.append(",\"projectName\":'");
				buff.append(obj[1]);
				buff.append("',\"projectNumber\":'");
				buff.append(obj[2]);
				buff.append("',\"projectMoney\":");
				buff.append(obj[3]);
				buff.append(",\"appUserName\":'");
				buff.append(obj[4]);
				buff.append("',\"createDate\":'");
				if(null!=obj[5]){
					buff.append(DateUtil.parseDate(obj[5].toString(), "yyyy-MM-dd"));
				}
				buff.append("',\"isArchives\":");
				buff.append(obj[6]);
				buff.append(",\"oppositeType\":'");
				buff.append(obj[7]);
				buff.append("',\"projtoarchiveId\":");
				buff.append(obj[8]);
				buff.append(",\"businessType\":'");
				buff.append(obj[9]);
				buff.append("'},");
			}
		}
		if(null!=list && list.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]}");
		jsonString = buff.toString();
	/*	QueryFilter filter=new QueryFilter(getRequest());
		Map<String,String> map = new HashMap<String,String>();
		int size=0;
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
    	businessType=(String)getRequest().getParameter("businessType");//这段话有意义嘛？ noted  by gao
    																   //这段话是有意义的，当前台传递了两个businessType 的时候，如果采用struts的set get方法获取到的businessType =  "SmallLoan,SmallLoan"
		List<VProjectArchives> list=vProjectArchivesService.searchproject(map, businessType);
		if(null !=list){
			size=vProjectArchivesService.searchprojectsize(map, businessType);
			for(VProjectArchives l:list){
				 if(null!=l.getCompanyId()){
						Organization organization=organizationService.get(l.getCompanyId());
						if(null!=organization){
							l.setOrgName(organization.getOrgName());
						}
					
					}
				
				String businessManagername="";
				if(null !=l.getBusinessManager() && !l.getBusinessManager().equals("")){
					String businessManager= l.getBusinessManager();
					String [] appstr=businessManager.split(",");
					Set<AppUser> userSet1 = this.appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet1){
						businessManagername += s.getFamilyName()+",";
					}
					businessManagername = businessManagername.substring(0, businessManagername.length()-1);
					l.setBusinessManagername(businessManagername);
				}
				
			}
			
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("startDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"startDate"});
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
		buff.append(json.serialize(list));
		buff.append("}");*/
		
		
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
				vProjectArchivesService.remove(new Long(id));
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
		 vProjectArchives=vProjectArchivesService.get(projtoarchiveId);
		PlArchives plArchives= plArchivesService.get(vProjectArchives.getPlarchivesId());
	//	vProjectArchives.setCheckername(plArchivesService.get(plArchives.getParentid()).getName()+"-"+plArchives.getCode());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(vProjectArchives));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	public String getbyprojectId(){
		List<VProjectArchives> list=vProjectArchivesService.getbyproject(projectId, businessType);
		if(null !=list &&list.size()>0){
			vProjectArchives=vProjectArchivesService.getbyproject(projectId, businessType).get(0);
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(vProjectArchives));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */

}
