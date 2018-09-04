package com.zhiwei.credit.action.communicate;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.em.SmsSDKClientManager;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.communicate.SmsMobile;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.communicate.SmsMobileService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class SmsMobileAction extends BaseAction{
	@Resource
	private SmsMobileService smsMobileService;
	@Resource
	private AppUserService appUserService;
	private SmsMobile smsMobile;
	
	private Long smsId;

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public SmsMobile getSmsMobile() {
		return smsMobile;
	}

	public void setSmsMobile(SmsMobile smsMobile) {
		this.smsMobile = smsMobile;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SmsMobile> list= smsMobileService.getAll(filter);
		
		Type type=new TypeToken<List<SmsMobile>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_FULL).create();
		buff.append(gson.toJson(list));
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
				smsMobileService.remove(new Long(id));
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
		SmsMobile smsMobile=smsMobileService.get(smsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(smsMobile));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		StringBuffer msg = new StringBuffer("");
		String recipientIds = getRequest().getParameter("recipientIds");
		if(StringUtils.isNotEmpty(recipientIds)){
			//发送站内手机短信
			String[] ids = recipientIds.split(",");
			for(String userId : ids){
				AppUser appUser = appUserService.get(new Long(userId));
				if(appUser.getMobile() == null){
					msg.append("用户<font color=\"red\">").append(appUser.getUsername()).append("</font>");
				}else{
					SmsMobile smsInner = new SmsMobile();
					smsInner.setPhoneNumber(appUser.getMobile());
					smsInner.setRecipients(appUser.getFullname());
					smsInner.setSendTime(new Date());
					smsInner.setSmsContent(smsMobile.getSmsContent());
					smsInner.setUserId(ContextUtil.getCurrentUserId());
					smsInner.setUserName(ContextUtil.getCurrentUser().getFullname());
					smsInner.setStatus(SmsMobile.STATUS_NOT_SENDED);
					
					String sendContent= smsInner.getSmsContent();
					//发送短信
					String[] phones=smsInner.getPhoneNumber().split(",");
					if(phones!=null){
						for(String phone:phones){
							String[] sendphone=new String[]{phone};
								Integer flg=SmsSDKClientManager.SendSMS(sendphone, sendContent);
								smsInner.setStatus(Short.valueOf(flg.toString()));
						}
					}
					
					smsMobileService.save(smsInner);
				}
			}
			if(msg.length()>0){
				msg.append("未设置手机号码.");
			}
		}else{
			//发送站外手机短信
			String mobileNums = smsMobile.getPhoneNumber();
			if(StringUtils.isNotEmpty(mobileNums)){
				String[] numbers = mobileNums.split(",");
				for(String num : numbers){
					SmsMobile smsOutside = new SmsMobile();
					smsOutside.setPhoneNumber(num);
					smsOutside.setRecipients(num);
					smsOutside.setSendTime(new Date());
					smsOutside.setSmsContent(smsMobile.getSmsContent());
					smsOutside.setUserId(ContextUtil.getCurrentUserId());
					smsOutside.setUserName(ContextUtil.getCurrentUser().getFullname());
					smsOutside.setStatus(SmsMobile.STATUS_NOT_SENDED);
					String outsendContent= smsOutside.getSmsContent();
					String[] phones=smsOutside.getPhoneNumber().split(",");
					if(phones!=null){
						for(String phone:phones){
							String[] sendphone=new String[]{phone};
								Integer flg=SmsSDKClientManager.SendSMS(sendphone, outsendContent);
								smsOutside.setStatus(Short.valueOf(flg.toString()));
						}
					}
					
					smsMobileService.save(smsOutside);
				}
			}
			
		}
		setJsonString("{success:true,msg:'"+msg.toString()+"'}");
		return SUCCESS;
	}
	/**
	 * 测试设备连接
	 * @return
	 */
	public String send(){
		smsMobile.setSendTime(new Date());
		smsMobileService.save(smsMobile);
		smsMobileService.sendOneSms(smsMobile);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 显示短信余量
	 * @return
	 */
	public String getSms(){
		//短信剩余金额
		double balance=SmsSDKClientManager.getBalance();
		//短信剩余条数
		int smsNum=(int)(balance*10/(SmsSDKClientManager.Balance*10));
		StringBuffer sb = new StringBuffer("{success:true,balance:"+balance+",smsNum:"+smsNum);
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
}
