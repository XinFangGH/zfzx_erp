package com.zhiwei.credit.service.creditFlow.log;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.log.Userloginlogs;

public interface UserloginlogsService extends BaseService<Userloginlogs>{
	/**得到用户“上一次”登录数据 */
	public Userloginlogs getUserBeforeData(String userName);
	
	/**得到用户登录次数 */
	public int getLoginAccount(String userName);
	/**
	 * 得到同一个ip 15分钟内登录失败的次数
	 * @param loginIp
	 * @return
	 */
	public int getLoginErrorNum(String loginIp,String userName);
	public int getLoginAmount(Long companyId,String keyWord, int isSuccess, Date beginTime, Date endTime) throws Exception;
	public List<Userloginlogs> getLoginList(Long companyId,String keyWord, int isSuccess, Date beginTime, Date endTime,int start,int limit) throws Exception;
	public void saveSysLog(String ipAddr, String macAddr, String loginAddr,String userLoginName, boolean isSuccess,Long companyId);
}