package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;

/**
 * 
 * @author 
 *
 */
public interface ObObligationInvestInfoDao extends BaseDao<ObObligationInvestInfo>{

	public List<ObObligationInvestInfo> getListInvestPeonId(Long investmentPersonId,String flag);
//查询某个债权产品下面的投资项目
	public List<ObObligationInvestInfo> getInfoByobObligationProjectId(Long id,
			String flag);
}