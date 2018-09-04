package com.zhiwei.credit.service.creditFlow.assuretenet;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com/
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;

public interface SlProcreditAssuretenetService extends BaseService<SlProcreditAssuretenet>{
	
	
	//根据项目ID查询准入原则
	public List<SlProcreditAssuretenet> getByProjId(String projId,String businessType);
	
}


