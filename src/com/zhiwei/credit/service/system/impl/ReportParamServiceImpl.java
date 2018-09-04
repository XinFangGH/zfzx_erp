package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.ReportParamDao;
import com.zhiwei.credit.model.system.ReportParam;
import com.zhiwei.credit.service.system.ReportParamService;

public class ReportParamServiceImpl extends BaseServiceImpl<ReportParam> implements ReportParamService{
	private ReportParamDao dao;
	
	public ReportParamServiceImpl(ReportParamDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<ReportParam> findByRepTemp(Long reportId) {
		return dao.findByRepTemp(reportId);
	}

}