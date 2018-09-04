package com.zhiwei.credit.dao.creditFlow.log;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.log.Userloginlogs;

public interface UserloginlogsDao extends BaseDao<Userloginlogs>{
	/**得到用户“上一次”登录数据 */
	public Userloginlogs getUserBeforeData(String userName);
	
	/**得到用户登录次数 */
	public int getLoginAccount(String userName);
	public int getLoginErrorNum(String loginIp,String userName);
	public int getLoginAmount(Long companyId,String keyWord, int isSuccess, Date beginTime, Date endTime) throws Exception;
	public List<Userloginlogs> getLoginList(Long companyId,String keyWord, int isSuccess, Date beginTime, Date endTime,int start,int limit) throws Exception;
}