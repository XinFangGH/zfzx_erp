package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.RunDataDao;
import com.zhiwei.credit.model.flow.RunData;

public class RunDataDaoImpl extends BaseDaoImpl<RunData> implements RunDataDao{

	public RunDataDaoImpl() {
		super(RunData.class);
	}
	
	public RunData getByRunIdFieldName(Long runId,String fieldName){
		String hql="from RunData rd where rd.processRun.runId=? and rd.fieldName=?";
		return (RunData)findUnique(hql,new Object[]{runId,fieldName});
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.RunDataDao#getByRunId(java.lang.Long)
	 */
	public List<RunData> getByRunId(Long runId){
	    String hql="from RunData rd where rd.processRun.runId=?";
	    return findByHql(hql, new Object[]{runId});
	}

}