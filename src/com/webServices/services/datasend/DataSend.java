package com.webServices.services.datasend;

import java.util.List;

import com.webServices.finance.FundIntentPrincipalLendingAction;
import com.webServices.services.md.msgmd.ZwBankErroMd;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlLoanAccountErrorLog;
import com.zhiwei.credit.service.creditFlow.finance.SlLoanAccountErrorLogService;

public class DataSend {
	private SlLoanAccountErrorLog slLoanAccountErrorLog;

	private SlLoanAccountErrorLogService slLoanAccountErrorLogService;

	public DataSend() {
		slLoanAccountErrorLog = new SlLoanAccountErrorLog();
		slLoanAccountErrorLogService = (SlLoanAccountErrorLogService) AppUtil
				.getBean("slLoanAccountErrorLogService");
	}

	private void printErro(List<String[]> l) {
		String[] str;
		for (int i = 0; i < l.size(); i++) {
			str = l.get(i);
			for (int j = 0; j < str.length; j++) {
			}
		}
	}

	public boolean addErroMd(ZwBankErroMd[] erroMd) {
		String[] ret = new String[4];
		boolean ret1=false;
		if (erroMd.length > 0) {
			for (int i = 0; i < erroMd.length; i++) {

				if (erroMd[i].getDuebillno() != null
						&& !erroMd[i].getDuebillno().equals("")) {
					if(erroMd[i].getCorpno()!=null){
						slLoanAccountErrorLog.setCorpno(erroMd[i].getCorpno());
					}else{
							System.out.print("机构编号为空");
							ret[1]="机构编号为空";
					}
					slLoanAccountErrorLog
							.setDuebillno(erroMd[i].getDuebillno());
					if (erroMd[i].getCustname() != null) {
						slLoanAccountErrorLog.setCustname(erroMd[i]
								.getCustname().substring(1));
					}
					if (erroMd[i].getCusttype().equals("A01")) {
						slLoanAccountErrorLog.setCusttype("company_customer");
					} else if (erroMd[i].getCusttype().equals("A02")) {
						slLoanAccountErrorLog.setCusttype("person_customer");
					}else {
						System.out.print("客户类别为空");
						ret[2]="客户类别为空";
					}

					if (erroMd[i].getLoantype().equals("B03")) {
						slLoanAccountErrorLog.setLoantype("MicroLoanBusiness");
					} else if (erroMd[i].getLoantype().equals("B02")) {
						slLoanAccountErrorLog.setLoantype("SmallLoanBusiness");
					} else if (erroMd[i].getLoantype().equals("B01")) {
						slLoanAccountErrorLog.setLoantype("");
					}else{
						System.out.print("贷款业务品种为空");
						ret[3]="贷款业务品种为空";
					}
					

					if (erroMd[i].getCustbankname() != null) {
						slLoanAccountErrorLog.setCustbankname(erroMd[i]
								.getCustbankname());
					}
					if (erroMd[i].getBankname() != null) {
						slLoanAccountErrorLog.setBankname(erroMd[i]
								.getBankname());
					}
					if (erroMd[i].getAccounttype() != null) {
						if (erroMd[i].getAccounttype().equals("F01")) {
							slLoanAccountErrorLog.setAccounttype("0");
						} else {
							slLoanAccountErrorLog.setAccounttype("1");
						}
					}
					if (erroMd[i].getAccountno() != null) {
						slLoanAccountErrorLog.setAccountno(erroMd[i]
								.getAccountno());
					}
					if (FundIntentPrincipalLendingAction.reciveBankInfoErro(
							erroMd[i].getDuebillno(), "N")) {
						if (slLoanAccountErrorLog.getAccountErrorId() == null) {
							slLoanAccountErrorLogService
									.save(slLoanAccountErrorLog);
							ret1=true;
						} else {
							SlLoanAccountErrorLog orgSlLAErr = slLoanAccountErrorLogService
									.get(slLoanAccountErrorLog
											.getAccountErrorId());
							try {
								BeanUtil.copyNotNullProperties(
										slLoanAccountErrorLog, orgSlLAErr);
								slLoanAccountErrorLogService.save(orgSlLAErr);
								ret1=true;
								//ret[0] = "错误信息已接受";
							} catch (Exception e) {
								e.getStackTrace();
								ret[0] = "错误信息已存在";
								ret1=false;
							}
						}
					}
					
				} else {
					System.out.print("贷款编号不能为空");
					ret[0]="机构编号为空";
					ret1=false;
				}
			}
		}
		System.out.print(ret[0]+"------"+ret[1]);
		return ret1;
	}

}
