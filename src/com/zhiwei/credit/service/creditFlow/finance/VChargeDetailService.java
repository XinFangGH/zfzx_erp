package com.zhiwei.credit.service.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.VChargeDetail;

/**
 * 
 * @author 
 *
 */
public interface VChargeDetailService extends BaseService<VChargeDetail>{
	public int searchsize(Map<String,String> map);
	public List<VChargeDetail> search(Map<String,String> map);
}


