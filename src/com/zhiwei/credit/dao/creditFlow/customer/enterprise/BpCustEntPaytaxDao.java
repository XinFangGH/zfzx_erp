package com.zhiwei.credit.dao.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntPaytax;

/**
 * 
 * @author 
 *
 */
public interface BpCustEntPaytaxDao extends BaseDao<BpCustEntPaytax>{
	public BpCustEntPaytax getbyId(Integer lawsuitId);
}