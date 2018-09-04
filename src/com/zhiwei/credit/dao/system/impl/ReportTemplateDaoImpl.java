package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.ReportTemplateDao;
import com.zhiwei.credit.model.system.ReportTemplate;

public class ReportTemplateDaoImpl extends BaseDaoImpl<ReportTemplate> implements ReportTemplateDao{

	public ReportTemplateDaoImpl() {
		super(ReportTemplate.class);
	}

	@Override
	public ReportTemplate getReportTemplateByKey(String key) {
		String hql="FROM ReportTemplate sl where sl.reportKey='"+key+"'";
		List<ReportTemplate> list =findByHql(hql);
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}