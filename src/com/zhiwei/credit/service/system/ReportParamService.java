package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.ReportParam;

public interface ReportParamService extends BaseService<ReportParam>{
	/**
	 * 根据ID来查找参数
	 * @param reportId
	 * @return
	 */
	public List<ReportParam> findByRepTemp(Long reportId);
}


