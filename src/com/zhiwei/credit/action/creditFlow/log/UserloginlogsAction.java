package com.zhiwei.credit.action.creditFlow.log;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.log.Userloginlogs;
import com.zhiwei.credit.service.creditFlow.log.UserloginlogsService;

/**
 * 系统日志
 * @author 
 *
 */
public class UserloginlogsAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	UserloginlogsService userloginlogsService; 
	
	String userLoginName;
	boolean isSuccess;
	String loginAddr;
	String macAddr;
	Long companyId;

	String s_keyWord = "";
	int s_isSuccess = 100;
	Date s_beginTime = DateUtil.strToDateLong("2000-01-01 00:00:00.0");
	Date s_endTime = DateUtil.strToDateLong("2111-01-01 00:00:00.0");
	
	
	public void saveloginData(){
		companyId=ContextUtil.getLoginCompanyId();
		String ipAddr = this.getRequest().getRemoteAddr();//用户局域网ip
//		String macAddr = SystemUtil.getMACAddress();//物理地址
		//mac地址 暂时无法获取，所以先用它保存广域网的IP地址
		
		try {
			userloginlogsService.saveSysLog(ipAddr, macAddr, loginAddr, userLoginName, isSuccess,companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void getLoginDataList(){
		companyId=ContextUtil.getLoginCompanyId();
		int amount = 0;
		List<Userloginlogs> list = null;
		
		if(s_beginTime == null){	
			s_beginTime = DateUtil.strToDateLong("2000-01-01 00:00:00.0");
		}
		if(s_endTime == null){
			s_endTime = DateUtil.strToDateLong("2111-01-01 00:00:00.0");
		}
		
		
		try {
			amount = userloginlogsService.getLoginAmount(companyId,s_keyWord, s_isSuccess, s_beginTime, s_endTime);
			list = userloginlogsService.getLoginList(companyId,s_keyWord, s_isSuccess, s_beginTime, s_endTime,start,limit);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JsonUtil.jsonFromList(list,amount,"yyyy-MM-dd HH:mm:ss");
		}
		
		
	}


	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setS_keyWord(String sKeyWord) {
		s_keyWord = sKeyWord;
	}

	public void setS_isSuccess(int sIsSuccess) {
		s_isSuccess = sIsSuccess;
	}

	public void setS_beginTime(Date sBeginTime) {
		s_beginTime = sBeginTime;
	}

	public void setS_endTime(Date sEndTime) {
		s_endTime = sEndTime;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setLoginAddr(String loginAddr) {
		this.loginAddr = loginAddr;
	}
	
}
