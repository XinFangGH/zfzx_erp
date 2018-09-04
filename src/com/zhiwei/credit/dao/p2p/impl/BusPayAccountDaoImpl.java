package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.BusPayAccountDao;
import com.zhiwei.credit.model.p2p.BusPayAccount;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BusPayAccountDaoImpl extends BaseDaoImpl<BusPayAccount> implements BusPayAccountDao{

	public BusPayAccountDaoImpl() {
		super(BusPayAccount.class);
	}

}