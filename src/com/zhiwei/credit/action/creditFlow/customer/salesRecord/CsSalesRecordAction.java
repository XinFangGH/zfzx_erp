package com.zhiwei.credit.action.creditFlow.customer.salesRecord;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.salesRecord.CsSalesRecord;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.RelativeUser;
import com.zhiwei.credit.service.creditFlow.customer.salesRecord.CsSalesRecordService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.RelativeUserService;
/**
 * 
 * @author 
 *
 */
public class CsSalesRecordAction extends BaseAction{
	@Resource
	private CsSalesRecordService csSalesRecordService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private RelativeUserService relativeUserService;
	
	private CsSalesRecord csSalesRecord;
	
	private Boolean isAll;
	private Long saleId;

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public CsSalesRecord getCsSalesRecord() {
		return csSalesRecord;
	}

	public void setCsSalesRecord(CsSalesRecord csSalesRecord) {
		this.csSalesRecord = csSalesRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String []userIds = null;
		String currentUserId = ContextUtil.getCurrentUserId().toString();
		StringBuffer userIdsStr = new StringBuffer();
		isAll = super.getRequest().getParameter("isAll").equals("true")?true:false;
		//授权查询全部客户的代码begin 在这里为companyId赋值
/*		List<AppUser> userList = appUserService.findRelativeUsersByUserId();
		if(!isAll) {//如果用户不拥有查看所有项目信息的权限
			if(userList.size() > 0) {
				for(AppUser appUser : userList) {
					userIdsStr.append(appUser.getUserId());
					userIdsStr.append(",");
				}
				userIds = (userIdsStr.toString() + currentUserId).split(",");//当前登录用户以及其所有下属用户
			}else {
				userIds = new String[]{currentUserId};
			}
		}*/
		
		List<RelativeUser> findSubordinate = relativeUserService.findSubordinate(ContextUtil.getCurrentUserId());
		ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
		List<AppUser> diguiList = new ArrayList<AppUser>();
		diguiList = appUserService.diguiRelativeUser(findSubordinate, appUserList);
		if(!isAll) {//如果用户不拥有查看所有项目信息的权限
			if(diguiList.size() > 0) {
				for(AppUser appUser : diguiList) {
					userIdsStr.append(appUser.getUserId());
					userIdsStr.append(",");
				}
				userIds = (userIdsStr.toString() + currentUserId).split(",");//当前登录用户以及其所有下属用户
			}else {
				userIds = new String[]{currentUserId};
			}
		}
		
		
		
		List<CsSalesRecord> list= csSalesRecordService.getListByRequest(this.getRequest(),start,limit,userIds);
		if(null!=list && list.size()>0){
			for(CsSalesRecord c:list){
				Organization org=organizationService.get(c.getCompanyId());
				c.setCompanyName(org.getOrgName());
			}
		}
		List<CsSalesRecord> listCount =csSalesRecordService.getListByRequest(this.getRequest(),null,null,userIds);
		Type type=new TypeToken<List<CsSalesRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(listCount!=null?listCount.size():0).append(",result:");
		
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
		if(ids!=null){
			for(String id:ids){
				csSalesRecordService.remove(new Long(id));
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
		CsSalesRecord csSalesRecord=csSalesRecordService.get(saleId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csSalesRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csSalesRecord.getSaleId()==null){
			csSalesRecord.setCompanyId(ContextUtil.getLoginCompanyId());
			csSalesRecord.setCreateDate(new Date());
			csSalesRecordService.save(csSalesRecord);
		}else{
			CsSalesRecord orgCsSalesRecord=csSalesRecordService.get(csSalesRecord.getSaleId());
			try{
				BeanUtil.copyNotNullProperties(orgCsSalesRecord, csSalesRecord);
				csSalesRecordService.save(orgCsSalesRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
}
