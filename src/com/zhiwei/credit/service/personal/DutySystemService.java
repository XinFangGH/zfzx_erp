package com.zhiwei.credit.service.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.personal.DutySystem;

public interface DutySystemService extends BaseService<DutySystem>{
	/**
	 * 取得缺省的班次
	 * @return
	 */
	public DutySystem getDefaultDutySystem();
}


