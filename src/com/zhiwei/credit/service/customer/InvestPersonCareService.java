package com.zhiwei.credit.service.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.customer.InvestPersonCare;

/**
 * 
 * @author 
 *
 */
public interface InvestPersonCareService extends BaseService<InvestPersonCare>{
	/**
	 * 通过投资人主键Id获得关怀的人
	 * @param valueOf
	 * @return
	 */
	List<InvestPersonCare> getByperId(Long perId);
	public List<InvestPersonCare> getList(Long perId,Integer isEnterprise);
}


