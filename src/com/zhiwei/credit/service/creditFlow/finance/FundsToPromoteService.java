package com.zhiwei.credit.service.creditFlow.finance;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.FundsToPromote;

/**
 * 
 * @author 
 *
 */
public interface FundsToPromoteService extends BaseService<FundsToPromote>{
	public List<FundsToPromote> getListByProjectId(Long projectId);
}


