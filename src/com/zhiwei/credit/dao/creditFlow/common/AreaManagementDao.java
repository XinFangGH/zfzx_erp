package com.zhiwei.credit.dao.creditFlow.common;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.common.AreaManagement;

/**
 * 
 * @author 
 *
 */
public interface AreaManagementDao extends BaseDao<AreaManagement>{
	public List<AreaManagement> getListByParentId(Long parentId);
}