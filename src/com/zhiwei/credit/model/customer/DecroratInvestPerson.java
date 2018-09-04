package com.zhiwei.credit.model.customer;

public class DecroratInvestPerson extends InvestPersonInfo implements
		DecorationInvest {

	private InvestPersonInfo investInfo;
	
	private InvestEnterprise investEnterprise;
	
	public DecroratInvestPerson(InvestEnterprise investEnterprise) {
		super();
		this.investEnterprise = investEnterprise;
	}



	@Override
	public InvestPersonInfo getInstantce() {
		investInfo = new InvestPersonInfo();
/*		this.investPersonId=person.getInvestPersonId();
 * ["小贷", "0"],["典当", "1"]
	       this.investPersonName=person.getInvestPersonName();
	       this.fundResource=person.getFundResource().toString();
	       this.projectMoney=person.getInvestMoney();  //贷款金额（单个投资人）
	       this.bidPlanId=person.getBidPlanId();*/
		
		investInfo.setInvestPersonId(investEnterprise.getId());
		investInfo.setInvestPersonName(investEnterprise.getEnterprisename());
		if(investEnterprise.getBusinessType().equals("SmallLoan")){
			investInfo.setFundResource((short)0);
		}else if(investEnterprise.getBusinessType().equals("Pawn")){
			investInfo.setFundResource((short)1);
		}
		return investInfo;
	}

}
