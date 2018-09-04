package com.webServices.services.zwSerDao.impl;

import javax.annotation.Resource;

import com.webServices.services.md.msgmd.ZwBankErroMd;
import com.webServices.services.zwSerDao.BankInfoErro;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlLoanAccountErrorLog;
import com.zhiwei.credit.service.creditFlow.finance.SlLoanAccountErrorLogService;
import com.zhiwei.credit.service.flow.ProcessService;

public class BankInfoErroImpl implements BankInfoErro {
	@Resource
	private SlLoanAccountErrorLogService slLoanAccountErrorLogService;
	private SlLoanAccountErrorLog slLoanAccountErrorLog;
	private Long accountErrorId;

	@Override
	public boolean add(ZwBankErroMd erroMd) {
		boolean ret = false;
		slLoanAccountErrorLog=new SlLoanAccountErrorLog();
		ProcessService processService = (ProcessService) AppUtil.getBean("processService");
		SlLoanAccountErrorLogService slLoanAccountErrorLogService=(SlLoanAccountErrorLogService)AppUtil.getBean("slLoanAccountErrorLogService");
		slLoanAccountErrorLogService.equals("111");
		if (erroMd.getDuebillno() != null
				&& !erroMd.getDuebillno().equals("")) {
			slLoanAccountErrorLog.setCorpno(erroMd.getCorpno());
			slLoanAccountErrorLog.setDuebillno(erroMd.getDuebillno());
			slLoanAccountErrorLog.setCustname(erroMd.getCustname()
					.substring(1));//截取掉第一个字符作为我们的客户ID
			if (erroMd.getCusttype().equals("A01")) {
				slLoanAccountErrorLog.setCusttype("company_customer");
			} else if (erroMd.getCusttype().equals("A02")) {
				slLoanAccountErrorLog.setCusttype("person_customer");
			}

			if (erroMd.getLoantype().equals("B03")) {
				slLoanAccountErrorLog.setLoantype("MicroLoanBusiness");
			} else if (erroMd.getLoantype().equals("B02")) {
				slLoanAccountErrorLog.setLoantype("SmallLoanBusiness");
			} else if (erroMd.getLoantype().equals("B01")) {
				slLoanAccountErrorLog.setLoantype("");
			}
			slLoanAccountErrorLog.setCustbankname(erroMd.getCustbankname());
			slLoanAccountErrorLog.setBankname(erroMd.getBankname());
			if(erroMd.getAccounttype().equals("F01")){
				slLoanAccountErrorLog.setAccounttype("0");
			}else{
				slLoanAccountErrorLog.setAccounttype("1");
			}
			slLoanAccountErrorLog.setAccountno(erroMd.getAccountno());
			if (slLoanAccountErrorLog.getAccountErrorId() == null) {
				slLoanAccountErrorLogService.save(slLoanAccountErrorLog);
			} else {
				SlLoanAccountErrorLog orgSlLAErr = slLoanAccountErrorLogService
						.get(slLoanAccountErrorLog.getAccountErrorId());
				try {
					BeanUtil.copyNotNullProperties(slLoanAccountErrorLog,
							orgSlLAErr);
					slLoanAccountErrorLogService.save(orgSlLAErr);
				} catch (Exception e) {
					e.getStackTrace();
					// logger.error(e.getMessage());
				}
			}
			// FundIntentPrincipalLendingAction.reciveBankInfoErro(erroMd.getDuebillno(),
			// "N");
			ret = true;
		}
	
		return ret;

	}

	public Long getAccountErrorId() {
		return accountErrorId;
	}

	public void setAccountErrorId(Long accountErrorId) {
		this.accountErrorId = accountErrorId;
	}

	public SlLoanAccountErrorLog getSlLoanAccountErrorLog() {
		return slLoanAccountErrorLog;
	}

	public void setSlLoanAccountErrorLog(
			SlLoanAccountErrorLog slLoanAccountErrorLog) {
		this.slLoanAccountErrorLog = slLoanAccountErrorLog;
	}

}
