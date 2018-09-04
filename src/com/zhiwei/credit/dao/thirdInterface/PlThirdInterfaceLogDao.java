package com.zhiwei.credit.dao.thirdInterface;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;

/**
 * 
 * @author 
 *
 */
public interface PlThirdInterfaceLogDao extends BaseDao<PlThirdInterfaceLog>{

	PlThirdInterfaceLog getByOrdId(String orderNo);

	PlThirdInterfaceLog getByLoanOrdId(String orderNo);
	
}