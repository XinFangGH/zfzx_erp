package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.ReportTemplate;

/**
 * 
 * @author 
 *
 */
public interface ReportTemplateDao extends BaseDao<ReportTemplate>{
	
	 public  ReportTemplate getReportTemplateByKey(String key);
	
}