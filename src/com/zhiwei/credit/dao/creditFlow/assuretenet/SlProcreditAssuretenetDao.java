package com.zhiwei.credit.dao.creditFlow.assuretenet;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com/
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;

/**
 * 贷款准入原则
 * @author zhangyl
 *
 */
public interface SlProcreditAssuretenetDao extends BaseDao<SlProcreditAssuretenet>{
	//根据项目ID查询准入原则
	public List<SlProcreditAssuretenet> getByProjId(String projId,String businessType);

	public List<SlProcreditAssuretenet> checkIsExit(String string,
			String businessTypeKey, Long assuretenetId);
	
}