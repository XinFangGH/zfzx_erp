package com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountRecordDao;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;

/**
 * 
 * @author 
 *
 */
public class GlAccountBankCautionmoneyServiceImpl extends BaseServiceImpl<GlAccountBankCautionmoney> implements GlAccountBankCautionmoneyService{
	@SuppressWarnings("unused")
	private GlAccountBankCautionmoneyDao dao;
	@Resource
	private  GlAccountBankCautionmoneyDao glAccountBankCautionmoneyDao;
	@Resource
	private  GlAccountBankDao glAccountBankDao;
	@Resource
	private  GlAccountRecordDao glAccountRecordDao;
	public GlAccountBankCautionmoneyServiceImpl(GlAccountBankCautionmoneyDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<GlAccountBankCautionmoney> getbyparentId(Long parentId) {
		
		return dao.getallbybankId(parentId);
	}

	@Override
	public List<GlAccountBankCautionmoney> getalllist() {
		// TODO Auto-generated method stub
		return dao.getalllist();
	}

	@Override
	public void saveguaranteemoneyAccount(Long glAccountBankId,Long cautionAccountId){
		GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyDao.get(cautionAccountId);
		List<GlAccountRecord> listRecord =glAccountRecordDao.getallbycautionAccountId(cautionAccountId,0,99999999);
		BigDecimal incomemoney=new BigDecimal(0);
		BigDecimal paymoney =new BigDecimal(0);
		BigDecimal frozenMoney=new BigDecimal(0);
		BigDecimal unFrozenMoney =new BigDecimal(0);
		for(GlAccountRecord l:listRecord){
			if(l.getCapitalType()==1){ //存入
				incomemoney=incomemoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==2){ //支出
				paymoney=paymoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==3){ //冻结
				frozenMoney=frozenMoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==4){ //解冻
				unFrozenMoney=unFrozenMoney.add(l.getOprateMoney());
			}
		}
		BigDecimal sum =glAccountBankCautionmoney.getRawauthorizationMoney().add(incomemoney).subtract(paymoney);
		glAccountBankCautionmoney.setAuthorizationMoney(sum);
		BigDecimal sumsurplusMoney=glAccountBankCautionmoney.getRawsurplusMoney().add(incomemoney).subtract(paymoney).add(unFrozenMoney).subtract(frozenMoney);
		glAccountBankCautionmoney.setSurplusMoney(sumsurplusMoney);
		glAccountBankCautionmoneyDao.save(glAccountBankCautionmoney);
		
		GlAccountBank glAccountBank=glAccountBankDao.get(glAccountBankId);
		List<GlAccountBankCautionmoney> list=glAccountBankCautionmoneyDao.getallbybankId(glAccountBankId);
		BigDecimal authorizationMoney=new BigDecimal(0);
		BigDecimal surplusMoney =new BigDecimal(0);
		for(GlAccountBankCautionmoney l:list){
			authorizationMoney=authorizationMoney.add(l.getAuthorizationMoney());
			surplusMoney=surplusMoney.add(l.getSurplusMoney());
		}
		glAccountBank.setAuthorizationMoney(authorizationMoney);
		glAccountBank.setSurplusMoney(surplusMoney);
		glAccountBankDao.save(glAccountBank);
	}
}