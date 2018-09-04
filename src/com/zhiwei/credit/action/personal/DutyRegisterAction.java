package com.zhiwei.credit.action.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;

import com.zhiwei.credit.model.personal.Duty;
import com.zhiwei.credit.model.personal.DutyRegister;
import com.zhiwei.credit.model.personal.DutySection;
import com.zhiwei.credit.model.personal.DutySystem;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.personal.DutyRegisterService;
import com.zhiwei.credit.service.personal.DutySectionService;
import com.zhiwei.credit.service.personal.DutyService;
import com.zhiwei.credit.service.personal.DutySystemService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class DutyRegisterAction extends BaseAction{
	@Resource
	private DutyRegisterService dutyRegisterService;
	@Resource
	private DutyService dutyService;
	@Resource
	private DutySystemService dutySystemService;
	@Resource
	private DutySectionService dutySectionService;
	@Resource
	private AppUserService appUserService;
	
	private DutyRegister dutyRegister;
	
	private Long registerId;

	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public DutyRegister getDutyRegister() {
		return dutyRegister;
	}

	public void setDutyRegister(DutyRegister dutyRegister) {
		this.dutyRegister = dutyRegister;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<DutyRegister> list= dutyRegisterService.getAll(filter);
		
		Type type=new TypeToken<List<DutyRegister>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_FULL)
		.excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 显示个人考勤信息列表
	 * @return
	 */
	public String person(){
		QueryFilter filter=new QueryFilter(getRequest());
		
		filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		filter.addSorted("registerDate", QueryFilter.ORDER_DESC);
		
		List<DutyRegister> list= dutyRegisterService.getAll(filter);
		
		Type type=new TypeToken<List<DutyRegister>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_FULL)
		.excludeFieldsWithoutExposeAnnotation().create();
		
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 显示用户的班次，供用户进行签到及签退
	 * @return
	 */
	public String today(){
		StringBuffer sb=new StringBuffer();
		
		DutySystem dutySystem=null;
		//取到当前用户的班次
		Duty duty=dutyService.getCurUserDuty(ContextUtil.getCurrentUserId());
		
		if(duty!=null){
			dutySystem=duty.getDutySystem();
		}else{
			dutySystem=dutySystemService.getDefaultDutySystem();
		}
				
		if(dutySystem==null){
			setJsonString("{success:true,exception:'尚未为用户设置排班，请联系管理员!'}");
		}else{
			
			AppUser curUser=ContextUtil.getCurrentUser();
			
			String dutySetting=dutySystem.getSystemSetting();
			//分割为7天
			String[] day7Sections=dutySetting.split("[|]");
			
			Calendar curCal=Calendar.getInstance();
			
			//取到当前为几天
			int curDay=curCal.get(Calendar.DAY_OF_WEEK);
			//当天的班制
			String[] curDaySections=day7Sections[curDay-1].split("[,]");
		
			sb.append("{success:true,result:[");
			for(int i=0;i<curDaySections.length;i++){
				if("-".equals(curDaySections[i])){//-代表休息
					continue;
				}
				DutySection dutySection=dutySectionService.get(new Long(curDaySections[i]));
				//取到当前用户签到的记录
				//取到当前用户签退的记录
				DutyRegister signInReg=dutyRegisterService.getTodayUserRegister(curUser.getUserId(), DutyRegister.SIGN_IN, dutySection.getSectionId());
				DutyRegister signOffReg=dutyRegisterService.getTodayUserRegister(curUser.getUserId(), DutyRegister.SIGN_OFF, dutySection.getSectionId());
				if(i>0)sb.append(",");
				sb.append("{sectionId:'").append(dutySection.getSectionId())
				.append("',systemName:'").append(dutySection.getSectionName())
				.append("',startSignin:'").append(dutySection.getStartSignin1())
				.append("',dutyStartTime:'").append(dutySection.getDutyStartTime1())
				.append("',endSignin:'").append(dutySection.getEndSignin1())
				.append("',earlyOffTime:'").append(dutySection.getEarlyOffTime1())
				.append("',dutyEndTime:'").append(dutySection.getDutyEndTime1())
				.append("',signOutTime:'").append(dutySection.getSignOutTime1()).append("'");
				
				//添加是否允许签到及签退
				
				if(signInReg!=null){//已经签到
					sb.append(",signInTime:'").append(signInReg.getRegisterTime())
					.append("',signInFlag:'").append(signInReg.getRegFlag())
					.append("',allowSignIn:'0'");
				}else{
					sb.append(",signInTime:'',signInFlag:''");
					//判断当前时间是否在签到开始和签到结束时间之间，若是，表示当前是可以进行签到
					Calendar startSignInCal=Calendar.getInstance();
					startSignInCal.setTime(dutySection.getStartSignin());
					DateUtil.copyYearMonthDay(startSignInCal, curCal);
					
					Calendar endSignInCal=Calendar.getInstance();
					endSignInCal.setTime(dutySection.getEndSignin());
					DateUtil.copyYearMonthDay(endSignInCal, curCal);
					
					int startCmpResult=curCal.compareTo(startSignInCal);
					int endCmpResult=curCal.compareTo(endSignInCal);
					if(startCmpResult>=0 && endCmpResult<=0){
						sb.append(",allowSignIn:'1'");
					}else if(startCmpResult<0){//尚未到签到时间
						sb.append(",allowSignIn:'-1'");
					}else{//已过签到时间
						sb.append(",allowSignIn:'0'");
					}
					
				}
				
				if(signOffReg!=null){//已经签退
					sb.append(",signOffTime:'").append(signOffReg.getRegisterTime())
					.append("',signOffFlag:'").append(signOffReg.getRegFlag())
					.append("',allowSignOff:'0'");
				}else{
					sb.append(",signOffTime:'',signOffFlag:''");
					
					//判断当前时间是否在签退开始和签退结束时间之间，若是，表示当前是可以进行签退
					Calendar startSignOffCal=Calendar.getInstance();
					startSignOffCal.setTime(dutySection.getEarlyOffTime());
					DateUtil.copyYearMonthDay(startSignOffCal, curCal);
					
					Calendar endSignOffCal=Calendar.getInstance();
					endSignOffCal.setTime(dutySection.getSignOutTime());
					DateUtil.copyYearMonthDay(endSignOffCal, curCal);
					
					int startCmpResult=curCal.compareTo(startSignOffCal);
					int endCmpResult=curCal.compareTo(endSignOffCal);
					
					if(startCmpResult>=0 && endCmpResult <=0){
						sb.append(",allowSignOff:'1'");
					}else if(startCmpResult<0){//尚未到签退时间
						sb.append(",allowSignOff:'-1'");
					}else{//已过签退时间
						sb.append(",allowSignOff:'0'");
					}
				}
				sb.append("}");
			}
			sb.append("]}");
			
			setJsonString(sb.toString());
		}
		dutySystemService.evict(dutySystem);
		
		return SUCCESS;
	}
	
	/**
	 * 签到
	 */
	public String signIn(){
		String sectionId=getRequest().getParameter("sectionId");
		dutyRegisterService.signInOff(new Long(sectionId),DutyRegister.SIGN_IN,ContextUtil.getCurrentUser(),new Date());
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	/**
	 * 签退
	 */
	public String signOff(){
		String sectionId=getRequest().getParameter("sectionId");
		dutyRegisterService.signInOff(new Long(sectionId),DutyRegister.SIGN_OFF,ContextUtil.getCurrentUser(),new Date());
		jsonString="{success:true}";
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
				dutyRegisterService.remove(new Long(id));
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
		DutyRegister dutyRegister=dutyRegisterService.get(registerId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(dutyRegister));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 补签
	 */
	public String save(){		
		HttpServletRequest request=getRequest();
		
		String userIds=request.getParameter("userIds");
		Long sectionId=new Long(request.getParameter("sectionId"));
		Short inOffFlag=new Short(request.getParameter("inOffFlag"));
		
		Date registerDate=DateUtil.parseDate(request.getParameter("registerDate"));
		
		String[]uIds=userIds.split("[,]");
		for(int i=0;i<uIds.length;i++){
			AppUser appUser=appUserService.get(new Long(uIds[i]));
			dutyRegisterService.signInOff(sectionId,inOffFlag,appUser,registerDate);
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
}
