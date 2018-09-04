package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfo;

/**
 * 
 * @author 
 *
 */
public interface VLeaseFinanceObjectInfoService extends BaseService<VLeaseFinanceObjectInfo>{
	public List<VLeaseFinanceObjectInfo> getListByProjectId(Long projectId);
	public List<VLeaseFinanceObjectInfo> getVLeaseFinanceObjectInfoList(int start,int limit,String projectName,String projectNumber,String standardSize,String owner,String companyId,boolean isManaged,boolean isHandled,boolean isBuyBack);
}


