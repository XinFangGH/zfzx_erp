package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.ReportTemplateDao;
import com.zhiwei.credit.model.system.ReportTemplate;
import com.zhiwei.credit.service.system.ReportTemplateService;

public class ReportTemplateServiceImpl extends BaseServiceImpl<ReportTemplate> implements ReportTemplateService{
	private ReportTemplateDao dao;
	
	public ReportTemplateServiceImpl(ReportTemplateDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public ReportTemplate getReportTemplateByKey(String key) {
		// TODO Auto-generated method stub
		return dao.getReportTemplateByKey(key);
	}

}