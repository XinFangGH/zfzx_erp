package com.credit.proj.mortgage.receivables.service.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.annotation.Resource;

import com.credit.proj.entity.CsProcreditMortgageReceivables;
import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.receivables.service.CsProcreditMortgageReceivablesService;
import com.hurong.core.util.BeanUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;

/**
 * 
 * @author 
 *
 */
public class CsProcreditMortgageReceivablesServiceImpl  implements CsProcreditMortgageReceivablesService{
	private CreditBaseDao creditBaseDao;
	
	private MortgageService mortgageService;
	public CreditBaseDao getCreditBaseDao() {
		return creditBaseDao;
	}

	public void setCreditBaseDao(CreditBaseDao creditBaseDao) {
		this.creditBaseDao = creditBaseDao;
	}

	public MortgageService getMortgageService() {
		return mortgageService;
	}

	public void setMortgageService(MortgageService mortgageService) {
		this.mortgageService = mortgageService;
	}

	@Override
	public List<CsProcreditMortgageReceivables> listByMortgageId(
			int mortgageid, String objectType) {
		String hql="from CsProcreditMortgageReceivables as r where r.mortgageId=? and r.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addReceivables(
			CsProcreditMortgageReceivables csProcreditMortgageReceivables,
			ProcreditMortgage procreditMortgage) {
		try{
			if(null==procreditMortgage.getId()){
				creditBaseDao.saveDatas(procreditMortgage);
			}else{
				ProcreditMortgage mor=mortgageService.seeMortgage(ProcreditMortgage.class, procreditMortgage.getId());
				BeanUtil.copyNotNullProperties(mor, procreditMortgage);
				creditBaseDao.saveOrUpdateDatas(mor);
			}
			csProcreditMortgageReceivables.setMortgageId(procreditMortgage.getId());
			if(null==csProcreditMortgageReceivables.getReceivablesId()){
				creditBaseDao.saveDatas(csProcreditMortgageReceivables);
			}else{
				CsProcreditMortgageReceivables receivables=(CsProcreditMortgageReceivables) creditBaseDao.getById(CsProcreditMortgageReceivables.class, csProcreditMortgageReceivables.getReceivablesId());
				BeanUtil.copyNotNullProperties(receivables, csProcreditMortgageReceivables);
				creditBaseDao.saveOrUpdateDatas(receivables);
			}
			JsonUtil.responseJsonSuccess();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	

}