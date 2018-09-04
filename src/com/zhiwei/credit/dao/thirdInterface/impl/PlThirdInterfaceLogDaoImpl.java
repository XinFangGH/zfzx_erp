package com.zhiwei.credit.dao.thirdInterface.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.thirdInterface.PlThirdInterfaceLogDao;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlThirdInterfaceLogDaoImpl extends BaseDaoImpl<PlThirdInterfaceLog> implements PlThirdInterfaceLogDao{

	public PlThirdInterfaceLogDaoImpl() {
		super(PlThirdInterfaceLog.class);
	}

	@Override
	public PlThirdInterfaceLog getByOrdId(String orderNo) {
		String hql="from PlThirdInterfaceLog as log where log.remark1 =?";
		Object[] params = {orderNo};
		return (PlThirdInterfaceLog)findUnique(hql, params);
	}
	
	@Override
	public PlThirdInterfaceLog getByLoanOrdId(String orderNo) {
		String hql="from PlThirdInterfaceLog as log where log.remark2 =?";
		Object[] params = {orderNo};
		return (PlThirdInterfaceLog)findUnique(hql, params);
	}
}