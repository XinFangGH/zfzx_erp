package com.zhiwei.credit.action.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.hrm.HireIssue;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.hrm.HireIssueService;
/**
 * 
 * @author 
 *
 */
public class HireIssueAction extends BaseAction{
	@Resource
	private HireIssueService hireIssueService;
	private HireIssue hireIssue;
	
	private Long hireId;

	public Long getHireId() {
		return hireId;
	}

	public void setHireId(Long hireId) {
		this.hireId = hireId;
	}

	public HireIssue getHireIssue() {
		return hireIssue;
	}

	public void setHireIssue(HireIssue hireIssue) {
		this.hireIssue = hireIssue;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<HireIssue> list= hireIssueService.getAll(filter);
		
		Type type=new TypeToken<List<HireIssue>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
				hireIssueService.remove(new Long(id));
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
		HireIssue hireIssue=hireIssueService.get(hireId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(gson.toJson(hireIssue));
		sb.append("]}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		AppUser user=ContextUtil.getCurrentUser();
		if(hireIssue.getHireId()==null){//根据主键来判断是否修改
			hireIssue.setRegFullname(user.getFullname());
			hireIssue.setRegDate(new Date());
		}else{
			hireIssue.setModifyFullname(user.getFullname());
			hireIssue.setModifyDate(new Date());
		}
		hireIssue.setStatus(HireIssue.NOTYETPASS_CHECK);
		hireIssueService.save(hireIssue);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 获取显示对象
	 * 
	 */
	public String load(){
		String strHireId=getRequest().getParameter("hireId");
		if(StringUtils.isNotEmpty(strHireId)){
			hireIssue=hireIssueService.get(new Long(strHireId));
		}
		return "load";
	}
	
	/**
	 * 审核招聘信息
	 * 
	 */
	public String check(){
		String status=getRequest().getParameter("status");
		String strHireId=getRequest().getParameter("hireId");
		String checkOpinion=getRequest().getParameter("checkOpinion");
		if(StringUtils.isNotEmpty(strHireId)){
			AppUser appUser=ContextUtil.getCurrentUser();
			hireIssue=hireIssueService.get(new Long(strHireId));
			hireIssue.setCheckFullname(appUser.getFullname());
			hireIssue.setCheckDate(new Date());
			hireIssue.setCheckOpinion(checkOpinion);
			if(StringUtils.isNotEmpty(status)){
				hireIssue.setStatus(Short.valueOf(status));
				hireIssueService.save(hireIssue);
				setJsonString("{success:true}");
			}else{
				setJsonString("{success:false}");
			}
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
}
