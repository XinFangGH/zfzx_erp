package com.zhiwei.credit.dao.pay.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.pay.BpMoneyManagerDao;
import com.zhiwei.credit.model.pay.BpBidLoan;
import com.zhiwei.credit.model.pay.BpMoneyManager;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpMoneyManagerDaoImpl extends BaseDaoImpl<BpMoneyManager> implements BpMoneyManagerDao{

	public BpMoneyManagerDaoImpl() {
		super(BpMoneyManager.class);
	}

	@Override
	public BpMoneyManager getByOrderAndType(String orderNo, String loanNo,
			String type) {
		String hql = "from BpMoneyManager bpm where bpm.LoanNo=? and bpm.OrderNo=? and bpm.type=?";
		return (BpMoneyManager)findUnique(hql, new Object[]{orderNo,loanNo,type});
	}

}