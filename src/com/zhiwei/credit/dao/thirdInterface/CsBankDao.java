package com.zhiwei.credit.dao.thirdInterface;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.common.CsBank;


/**
 * 
 * @author 
 *
 */
public interface CsBankDao extends BaseDao<CsBank>{
	public List<CsBank> getListByBankName(String bankName);
}