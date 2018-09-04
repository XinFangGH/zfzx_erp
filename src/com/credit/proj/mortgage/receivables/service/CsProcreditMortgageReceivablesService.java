package com.credit.proj.mortgage.receivables.service;

import java.util.List;

import com.credit.proj.entity.CsProcreditMortgageReceivables;
import com.credit.proj.entity.ProcreditMortgage;

/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/

/**
 * 
 * @author 
 *
 */
public interface CsProcreditMortgageReceivablesService{
	public List<CsProcreditMortgageReceivables> listByMortgageId(int mortgageid, String objectType);
	public void addReceivables(CsProcreditMortgageReceivables csProcreditMortgageReceivables,ProcreditMortgage procreditMortgage);
}


