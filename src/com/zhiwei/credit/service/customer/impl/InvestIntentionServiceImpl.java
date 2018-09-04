package com.zhiwei.credit.service.customer.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.InvestIntentionDao;
import com.zhiwei.credit.model.customer.InvestIntention;
import com.zhiwei.credit.service.customer.InvestIntentionService;

/**
 * 
 * @author 
 *
 */
public class InvestIntentionServiceImpl extends BaseServiceImpl<InvestIntention> implements InvestIntentionService{
	@SuppressWarnings("unused")
	private InvestIntentionDao dao;
	
	public InvestIntentionServiceImpl(InvestIntentionDao dao) {
		super(dao);
		this.dao=dao;
	}

}