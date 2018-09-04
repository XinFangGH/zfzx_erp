package com.zhiwei.credit.dao.creditFlow.common;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;

/**
 * 
 * @author 
 *
 */
public interface GlobalSupervisemanageDao extends BaseDao<GlobalSupervisemanage>{
	public List<GlobalSupervisemanage> getListByProjectId(Long projectId,String businessType);
	public List<GlobalSupervisemanage> listByDesignSuperviseManageTime(String designSuperviseManageTime);
}