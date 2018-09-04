package com.zhiwei.credit.dao.thirdInterface.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.thirdInterface.WebBankCodeDao;
import com.zhiwei.credit.model.thirdInterface.WebBankCode;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class WebBankCodeDaoImpl extends BaseDaoImpl<WebBankCode> implements WebBankCodeDao{

	public WebBankCodeDaoImpl() {
		super(WebBankCode.class);
	}

}