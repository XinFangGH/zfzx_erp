package com.zhiwei.credit.action.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.info.AppTips;
import com.zhiwei.credit.service.info.AppTipsService;
/**
 * 
 * @author 
 *
 */
public class AppTipsAction extends BaseAction{
	@Resource
	private AppTipsService appTipsService;
	private AppTips appTips;
	
	private Long tipsId;

	public Long getTipsId() {
		return tipsId;
	}

	public void setTipsId(Long tipsId) {
		this.tipsId = tipsId;
	}

	public AppTips getAppTips() {
		return appTips;
	}

	public void setAppTips(AppTips appTips) {
		this.appTips = appTips;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addFilter("Q_appUser.userId_L_EQ",ContextUtil.getCurrentUserId().toString());
		List<AppTips> list= appTipsService.getAll(filter);
		
		Type type=new TypeToken<List<AppTips>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
		if(getRequest().getParameter("ids").equals("all")){
			QueryFilter filter=new QueryFilter(getRequest());
			filter.addFilter("Q_appUser.userId_L_EQ",ContextUtil.getCurrentUserId().toString());
			List<AppTips> list= appTipsService.getAll(filter);
			for(AppTips tips:list){
				appTipsService.remove(tips);
			}
		}else{
			String[]ids=getRequest().getParameterValues("ids");
			String[] names=getRequest().getParameterValues("names");
			if(ids!=null&&names!=null){
				for(String name:names){
					AppTips appTips=appTipsService.findByName(name);
					if(appTips!=null){
						appTipsService.remove(appTips);
					}
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
		AppTips appTips=appTipsService.get(tipsId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(appTips));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String data=getRequest().getParameter("data");
		if(StringUtils.isNotEmpty(data)){
			Gson gson=new Gson();
			AppTips[] tips=gson.fromJson(data, AppTips[].class);
			for(AppTips tip:tips){
					if(tip.getTipsId()==-1){
						tip.setTipsId(null);
						tip.setCreateTime(new Date());
//						SimpleDateFormat date = new SimpleDateFormat("yyMMddHHmmssSSS");
//						String customerNo = date.format(new Date());
//						tip.setTipsName("tips"+customerNo);
						tip.setDislevel(1);
						tip.setAppUser(ContextUtil.getCurrentUser());
						appTipsService.save(tip);
					}else if(tip.getTipsId()==-2){
						AppTips orgTip=appTipsService.findByName(tip.getTipsName());
						if(orgTip!=null){
							tip.setTipsId(null);
							try {
								BeanUtil.copyNotNullProperties(orgTip, tip);
							}catch (Exception e) {
								e.printStackTrace();
							}
							orgTip.setDislevel(1);
							orgTip.setAppUser(ContextUtil.getCurrentUser());
							appTipsService.save(orgTip);
						}
					}
					
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	
}
