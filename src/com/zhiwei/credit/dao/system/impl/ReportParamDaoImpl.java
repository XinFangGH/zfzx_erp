package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.ReportParamDao;
import com.zhiwei.credit.model.system.ReportParam;

public class ReportParamDaoImpl extends BaseDaoImpl<ReportParam> implements ReportParamDao{

	public ReportParamDaoImpl() {
		super(ReportParam.class);
	}

	@Override
	public List<ReportParam> findByRepTemp(Long reportId) {
		String hql="from ReportParam vo where vo.reportTemplate.reportId=? order by vo.sn asc";
		Object []objs={reportId};
		return findByHql(hql, objs);
	}

}