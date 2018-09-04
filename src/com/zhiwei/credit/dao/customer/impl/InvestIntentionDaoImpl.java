package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.InvestIntentionDao;
import com.zhiwei.credit.model.customer.InvestIntention;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class InvestIntentionDaoImpl extends BaseDaoImpl<InvestIntention> implements InvestIntentionDao{

	public InvestIntentionDaoImpl() {
		super(InvestIntention.class);
	}

}