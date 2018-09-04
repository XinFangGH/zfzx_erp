package com.zhiwei.credit.service.creditFlow.customer.common.impl;

import java.util.Iterator;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;


public class EnterpriseBankServiceImpl extends BaseServiceImpl<EnterpriseBank> implements EnterpriseBankService{
	private EnterpriseBankDao dao;
	public EnterpriseBankServiceImpl(EnterpriseBankDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public List<EnterpriseBank> getBankList(Integer customerId,Short isEnterprise,Short iscredit,Short isInvest){
		return dao.getBankList(customerId, isEnterprise, iscredit, isInvest);
	}

	@Override
	public EnterpriseBank getById(Integer id) {
		
		return dao.getById(id);
	}

	@Override
	public List<EnterpriseBank> getList(Integer customerId, Short isEnterprise,
			Short isInvest, Integer start, Integer limit) {
		
		return dao.getList(customerId, isEnterprise, isInvest, start, limit);
	}
	@Override
	public void add(EnterpriseBank bank){
		try{
			int eid = bank.getEnterpriseid();
			EnterpriseBank enterpriseBankIter = null ;
			List<EnterpriseBank> list = dao.getBankList(eid, bank.getIsEnterprise(), Short.valueOf("0"), bank.getIsInvest());
			if(null != list && !"".equals(list)){
				if(bank.getIscredit()==0){
					for(Iterator<EnterpriseBank> iter = list.iterator() ; iter.hasNext();){
						enterpriseBankIter = iter.next() ;
					
						if(enterpriseBankIter.getIscredit()==0 && (null==bank.getId() || (null!=bank.getId() && !bank.getId().toString().equals(enterpriseBankIter.getId().toString())))){
							enterpriseBankIter.setIscredit(Short.valueOf("1"));
							dao.save(enterpriseBankIter);
						}
					}
				}
			}
			
			dao.save(bank) ;
		
		}catch(Exception e) {
			
			e.printStackTrace() ;
		}
	}
	@Override
	public void update(EnterpriseBank bank) {
		try{
			dao.save(bank) ;
			
			int BankId =bank.getId();
			int eid = bank.getEnterpriseid();
			EnterpriseBank enterpriseBankIter = null ;
			List<EnterpriseBank> list = dao.getBankList(eid, bank.getIsEnterprise(), Short.valueOf("0"), bank.getIsInvest());
			if(null != list && !"".equals(list)){
				if( bank.getIscredit()==0){
					for(Iterator<EnterpriseBank> iter = list.iterator() ; iter.hasNext();){
						enterpriseBankIter = iter.next() ;
					
						if(enterpriseBankIter.getIscredit()==0 && enterpriseBankIter.getId()!=BankId){
							enterpriseBankIter.setIscredit(Short.valueOf("1"));
							dao.save(enterpriseBankIter);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}

	@Override
	public EnterpriseBank queryIscredit(Integer customerId, Short isEnterprise,
			Short isInvest) {
		
		return dao.queryIscredit(customerId, isEnterprise, isInvest);
	}
	@Override
	public List<EnterpriseBank> queryAlreadyAccount(Integer id,String accountnum){
		return dao.queryAlreadyAccount(id, accountnum);
	}
	
	@Override
	public void querySomeList(PageBean<EnterpriseBank> pageBean){
		dao.querySomeList(pageBean);
	}
}
