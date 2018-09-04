package com.zhiwei.credit.dao.creditFlow.log.impl;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.log.UserloginlogsDao;
import com.zhiwei.credit.model.creditFlow.log.Userloginlogs;

@SuppressWarnings("unchecked")
public class UserloginlogsDaoImpl extends BaseDaoImpl<Userloginlogs> implements UserloginlogsDao{

	public UserloginlogsDaoImpl() {
		super(Userloginlogs.class);
	}

	@Override
	public int getLoginAccount(String userName) {
		
		String hql = "select count(*) from Userloginlogs u where u.userLoginName=?";
		
		List list = null;
		
		try {
			list =this.findByHql(hql, new Object[]{userName});
			
			if(list != null && list.size()>0){
				return ((Long)list.get(0)).intValue();
			}
			
		} catch (Exception e) {
		}
		
		return 0;
	}

	@Override
	public int getLoginErrorNum(String loginIp, String userName) {
		String hql = "select COUNT(*) from sys_userloginlogs where loginTime>DATE_SUB(now(),INTERVAL 15 MINUTE) " +
				" and isSuccess=0 and loginIP=?";
		return Integer.valueOf(this.getSession().createSQLQuery(hql).setParameter(0, loginIp).uniqueResult().toString());
	}

	@Override
public Userloginlogs getUserBeforeData(String userName) {
		
		String hql = "from Userloginlogs e where e.userLoginName=? order by e.loginTime desc";
		
		List<Userloginlogs> list = null;
		
		try {
			list = this.findByHql(hql,new Object[]{userName},0,2);
			
			if(list != null && list.size() > 1){
				return list.get(1);
			}
			
		} catch (Exception e) {
		}
		return null;
	}
	/**得到总数*/
	public int getLoginAmount(Long companyId,String keyWord, int isSuccess, Date beginTime, Date endTime) throws Exception{
		
		List list = null;
		
		String myKeyWord = "%"+keyWord+"%";

		Object[] params = {};
		String hql = "";
		
		if(isSuccess == 100){//如果 没有选择成功条件的话，默认是100

			Object[] params1 = {companyId,myKeyWord,myKeyWord,beginTime,endTime};
			
			params = params1;
			
			hql = "select count(*) from Userloginlogs u where u.companyId=?  and u.type is null   and (u.userLoginName like ?  or u.userName like ?) " +
			"and (u.loginTime between ? and ?) ";
			
			
		}else{
			Object[] params2 = {companyId,myKeyWord,myKeyWord,(isSuccess == 1?true:false),beginTime,endTime};
			
			params = params2;
			
			hql = "select count(*) from Userloginlogs u where u.companyId=?  and u.type is null  and (u.userLoginName like ?  or u.userName like ?) " +
			"and (u.isSuccess = ?) and (u.loginTime between ? and ?) ";
		}
		
		try {
			list = this.findByHql(hql, params);
			
			if(list != null){
				return ((Long)list.get(0)).intValue();
			}else{
				return 0;
			}
			
		} catch (Exception e) {
			return 0;
		}
		
		
	}
	
	public List<Userloginlogs> getLoginList(Long companyId,String keyWord, int isSuccess, Date beginTime, Date endTime,int start,int limit) throws Exception{
		
		List<Userloginlogs> list = null;
		
		String myKeyWord = "%"+keyWord+"%";

		Object[] params = {};
		String hql = "";
		
		if(isSuccess == 100){//如果 没有选择成功条件的话，默认是100
			
			Object[] params1 = {myKeyWord,myKeyWord,beginTime,endTime,companyId};
			
			params = params1;
			
			hql = "from Userloginlogs u where (u.userLoginName like ?  or u.userName like ?) and (u.loginTime between ? and ?) and u.companyId=?  and u.type is null  order by u.loginTime desc";
			
		}else{
			Object[] params2 = {myKeyWord,myKeyWord,(isSuccess == 1?true:false),beginTime,endTime,companyId};
			
			params = params2;
			
			hql = "from Userloginlogs u where (u.userLoginName like ?  or u.userName like ?) and (u.isSuccess = ?) and (u.loginTime between ? and ?) and u.companyId=? and u.type is null order by u.loginTime desc";
		}
		
		try {
			list =this.findByHql(hql, params, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}