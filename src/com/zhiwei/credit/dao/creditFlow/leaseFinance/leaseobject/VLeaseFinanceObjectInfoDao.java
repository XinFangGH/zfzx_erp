package com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfo;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

/**
 * 
 * @author 
 *
 */
public interface VLeaseFinanceObjectInfoDao extends BaseDao<VLeaseFinanceObjectInfo>{
	public List<VLeaseFinanceObjectInfo> getListByProjectId(Long projectId);
}