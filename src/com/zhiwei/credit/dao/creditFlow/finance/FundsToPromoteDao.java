package com.zhiwei.credit.dao.creditFlow.finance;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.finance.FundsToPromote;

/**
 * 
 * @author 
 *
 */
public interface FundsToPromoteDao extends BaseDao<FundsToPromote>{
	public List<FundsToPromote> getListByProjectId(Long projectId);
}