package com.zhiwei.credit.service.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;

/**
 * 
 * @author 
 *
 */
public interface VFundDetailService extends BaseService<VFundDetail>{
	public int searchsize(Map<String,String> map);
	public List<VFundDetail> search(Map<String,String> map);
	public List<VFundDetail> wslistbyinterest(String businessType,Long projectId,String factDate);
	public List<VFundDetail> wslistbyPrincipalRepay(String businessType,Long projectId,String factDate);
	public List<VFundDetail> wslistbyCharge(String businessType,Long projectId,String factDate);
	public List getListByFundType(String startDate,String endDate,Long companyId,String fundType);
	public List getshCount(String startDate,String endDate,Long companyId,String fundType);
	public List getwjqList(String startDate,String endDate,Long companyId,String fundType);
	public List getyqList(String endDate,Long companyId,String fundType);
	public List<VFundDetail> listByFundType(String fundType,Long projectId,String businessType);
	public List getListByFundType(String fundType,String companyId,String startDate,String intentDate);
}


