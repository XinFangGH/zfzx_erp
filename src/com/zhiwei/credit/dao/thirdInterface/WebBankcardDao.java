package com.zhiwei.credit.dao.thirdInterface;

import java.util.List;

import com.hurong.credit.model.user.BpCustMember;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;

/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/


/**
 * 
 * @author 
 *
 */
public interface WebBankcardDao extends BaseDao<WebBankcard>{
	WebBankcard getByCardNum(String openAcctId);

	public WebBankcard checkCardExit(WebBankcard webBankcard, BpCustMember mem);

	public List<WebBankcard> getBycusterId(Long id);

	public WebBankcard getByRequestNo(String requestNo);
	
}