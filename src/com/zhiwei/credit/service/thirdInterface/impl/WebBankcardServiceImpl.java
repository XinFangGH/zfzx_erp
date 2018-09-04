package com.zhiwei.credit.service.thirdInterface.impl;


import java.util.List;

import com.hurong.credit.model.user.BpCustMember;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.thirdInterface.WebBankcardDao;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.service.thirdInterface.WebBankcardService;

/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/


/**
 * 
 * @author 
 *
 */
public class WebBankcardServiceImpl extends BaseServiceImpl<WebBankcard> implements WebBankcardService{

	@SuppressWarnings("unused")
	private WebBankcardDao dao;
	
	public WebBankcardServiceImpl(WebBankcardDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public WebBankcard getByCardNum(String openAcctId) {
		return dao.getByCardNum(openAcctId);
	}

	@Override
	public WebBankcard checkCardExit(WebBankcard webBankcard, BpCustMember mem) {
		// TODO Auto-generated method stub
		return dao.checkCardExit(webBankcard,mem);
	}

	@Override
	public List<WebBankcard> getBycusterId(Long id) {
		// TODO Auto-generated method stub
		return dao.getBycusterId(id);
	}

	@Override
	public WebBankcard getByRequestNo(String requestNo) {
		// TODO Auto-generated method stub
		return dao.getByRequestNo(requestNo);
	}



}