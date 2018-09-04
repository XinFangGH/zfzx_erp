package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.creditFlow.finance.SlDataList;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.finance.SlDataListService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class SlDataListAction extends BaseAction{
	@Resource
	private SlDataListService slDataListService;
	@Resource
	private AppUserService appUserService;
	private SlDataList slDataList;
	
	private Long dataId;

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public SlDataList getSlDataList() {
		return slDataList;
	}

	public void setSlDataList(SlDataList slDataList) {
		this.slDataList = slDataList;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String tp=this.getRequest().getParameter("type");
		String companyId=this.getRequest().getParameter("companyId");
		String startDate=this.getRequest().getParameter("startDate");
		String endDate=this.getRequest().getParameter("endDate");
		String sendPersonId=this.getRequest().getParameter("sendPersonId");
		PagingBean pb=new PagingBean(start,limit);
		List<SlDataList> list= slDataListService.getListByType(tp, companyId, startDate, endDate, sendPersonId, pb);		
		for(SlDataList data:list){
			if(data.getSendPersonId()!=null){
				AppUser user=appUserService.get(data.getSendPersonId());
				if(null!=user){
					data.setSendPersonName(user.getFullname());
				}
			}
			
		}
		Type type=new TypeToken<List<SlDataList>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");

		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	@LogResource(description = "生成同步数据")
	public String getMaxDate(){
		String dateStr="";
		String type=this.getRequest().getParameter("type");
		Date date=slDataListService.getMaxDate(type);
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar d = Calendar.getInstance();
		if(null !=date){
			d.setTime(date);
			d.set(Calendar.DATE, d.get(Calendar.DATE) + 1);
			
			if(date!=null){
				dateStr=df.format(d.getTime());
			}
		}else{
			date=new Date();
			d.setTime(date);
			d.set(Calendar.DATE, d.get(Calendar.DATE) + 1);
			if(date!=null){
				dateStr=df.format(d.getTime());
			}
			
		}
		jsonString="{success:true,date:'"+dateStr+"'}";
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	@LogResource(description = "撤回同步数据")
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				slDataListService.remove(new Long(id));
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
		SlDataList slDataList=slDataListService.get(dataId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slDataList));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slDataList.getDataId()==null){
			slDataListService.save(slDataList);
		}else{
			SlDataList orgSlDataList=slDataListService.get(slDataList.getDataId());
			try{
				BeanUtil.copyNotNullProperties(orgSlDataList, slDataList);
				slDataListService.save(orgSlDataList);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
