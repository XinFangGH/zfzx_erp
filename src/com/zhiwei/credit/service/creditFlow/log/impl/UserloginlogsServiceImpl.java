package com.zhiwei.credit.service.creditFlow.log.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.core.commons.CreditException;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.dao.creditFlow.log.UserloginlogsDao;
import com.zhiwei.credit.model.creditFlow.log.Userloginlogs;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.log.UserloginlogsService;
import com.zhiwei.credit.service.system.AppUserService;

public class UserloginlogsServiceImpl extends BaseServiceImpl<Userloginlogs> implements UserloginlogsService{


	private UserloginlogsDao dao;
	@Resource
	private AppUserService appUserService;
	public UserloginlogsServiceImpl(UserloginlogsDao dao) {
		super(dao);
		this.dao=dao;
	}
	public void saveSysLog(String ipAddr, String macAddr, String loginAddr,String userLoginName, boolean isSuccess,Long companyId){
		
		Userloginlogs userLoginLog = new Userloginlogs();
		
		String userName = "匿名";
		
		try {
			
			if(isSuccess == true){//登录成功显示名字    没成功 用户名显示匿名 
				AppUser appUser = null;
				appUser = appUserService.findByUserName(userLoginName);
				if(appUser != null){
					userName = appUser.getFullname();
				}
				
			}
			
			userLoginLog.setIsSuccess(isSuccess);
			userLoginLog.setLoginIp(ipAddr);
			userLoginLog.setLoginMac(macAddr);
			userLoginLog.setLoginTime(DateUtil.getNowDateTimeTs());
			userLoginLog.setUserLoginName(userLoginName);
			userLoginLog.setUserName(userName);
			userLoginLog.setLoginAddr(loginAddr);
			userLoginLog.setCompanyId(companyId);
			dao.save(userLoginLog);
		} catch (Exception e) {
			throw new CreditException(e);
		}
	}
	@Override
	public int getLoginAccount(String userName) {
		
		return dao.getLoginAccount(userName);
	}
	@Override
	public int getLoginAmount(Long companyId, String keyWord, int isSuccess,
			Date beginTime, Date endTime) throws Exception {
		
		return dao.getLoginAmount(companyId, keyWord, isSuccess, beginTime, endTime);
	}
	@Override
	public List<Userloginlogs> getLoginList(Long companyId, String keyWord,
			int isSuccess, Date beginTime, Date endTime, int start, int limit)
			throws Exception {
		
		return dao.getLoginList(companyId, keyWord, isSuccess, beginTime, endTime, start, limit);
	}
	@Override
	public Userloginlogs getUserBeforeData(String userName) {
		
		return dao.getUserBeforeData(userName);
	}
	@Override
	public int getLoginErrorNum(String loginIp, String userName) {
		// TODO Auto-generated method stub
		return dao.getLoginErrorNum(loginIp, userName);
	}
	

}