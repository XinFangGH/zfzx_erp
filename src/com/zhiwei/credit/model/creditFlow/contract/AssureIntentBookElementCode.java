package com.zhiwei.credit.model.creditFlow.contract;

public class AssureIntentBookElementCode implements java.io.Serializable {
	
	public static final String BANK_D = "贷款银行"; 
	private String bank_v = "" ; 
	public static final String BANK_C = "[#bank#]" ;
	
	public static final String SUB_BANK_D = "贷款银行支行"; 
	private String sub_bank_v = "" ; 
	public static final String SUB_BANK_C = "[#subbank#]" ;

	public static final String CREIDT_MAN_D = "借款人"; 
	private String credit_man_v = "" ; 
	public static final String CREIDT_MAN_C = "[#creditman#]" ;

	public static final String CREIDT_KIND_D = "信贷种类"; 
	private String credit_kind_v = "" ; 
	public static final String CREIDT_KIND_C = "[#creditkind#]" ;
	
	public static final String CURRENCY_TYPE_D = "币种"; 
	private String currency_type_v = "" ; 
	public static final String CURRENCY_TYPE_C = "[#currencytype#]" ;
	
	public static final String CREDIT_MONEY_D = "借款金额万元"; 
	private String credit_money_v = "" ;
	public static final String CREDIT_MONEY_C = "[#creditmoney#]" ;
	
	public static final String CREDIT_MONEY_BIG_D = "借款金额万元大写"; 
	private String credit_money_big_v = "" ;
	public static final String CREDIT_MONEY_BIG_C = "[#creditmoneybig#]" ;
	
	public static final String CREDIT_TERM_D = "借款期限月"; 
	private String credit_term_v = "" ;
	public static final String CREDIT_TERM_C = "[#creditterm#]" ;
	
	public static final String INTENTBOOK_EFFECTIVEST_START_TIME_D = "拟担保意向书起始日期"; 
	private String intentbook_effectives_start_time_v = "" ;
	public static final String INTENTBOOK_EFFECTIVEST_START_TIME_C = "[#intentbookeffectivestarttime#]" ;
	
	public static final String INTENTBOOK_EFFECTIVEST_END_TIME_D = "拟担保意向书截止日期"; 
	private String intentbook_effectives_end_time_v = "" ;
	public static final String INTENTBOOK_EFFECTIVEST_END_TIME_C = "[#intentbookeffectiveendtime#]" ;
	
	public static final String CONTRACT_CODE_D = "拟担保意向书编码"; 
	private String contract_code_v = "" ;
	public static final String CONTRACT_CODE_C = "[#contractcode#]" ;
	
	public String getBank_v() {
		return bank_v;
	}
	public void setBank_v(String bankV) {
		bank_v = bankV;
	}
	public String getSub_bank_v() {
		return sub_bank_v;
	}
	public void setSub_bank_v(String subBankV) {
		sub_bank_v = subBankV;
	}
	public String getCredit_man_v() {
		return credit_man_v;
	}
	public void setCredit_man_v(String creditManV) {
		credit_man_v = creditManV;
	}
	public String getCredit_kind_v() {
		return credit_kind_v;
	}
	public void setCredit_kind_v(String creditKindV) {
		credit_kind_v = creditKindV;
	}
	public String getCurrency_type_v() {
		return currency_type_v;
	}
	public void setCurrency_type_v(String currencyTypeV) {
		currency_type_v = currencyTypeV;
	}
	public String getCredit_money_v() {
		return credit_money_v;
	}
	public void setCredit_money_v(String creditMoneyV) {
		credit_money_v = creditMoneyV;
	}
	public String getCredit_term_v() {
		return credit_term_v;
	}
	public void setCredit_term_v(String creditTermV) {
		credit_term_v = creditTermV;
	}
	public String getIntentbook_effectives_start_time_v() {
		return intentbook_effectives_start_time_v;
	}
	public void setIntentbook_effectives_start_time_v(
			String intentbookEffectivesStartTimeV) {
		intentbook_effectives_start_time_v = intentbookEffectivesStartTimeV;
	}
	public String getIntentbook_effectives_end_time_v() {
		return intentbook_effectives_end_time_v;
	}
	public void setIntentbook_effectives_end_time_v(
			String intentbookEffectivesEndTimeV) {
		intentbook_effectives_end_time_v = intentbookEffectivesEndTimeV;
	}
	public String getCredit_money_big_v() {
		return credit_money_big_v;
	}
	public void setCredit_money_big_v(String creditMoneyBigV) {
		credit_money_big_v = creditMoneyBigV;
	}
	public String getContract_code_v() {
		return contract_code_v;
	}
	public void setContract_code_v(String contractCodeV) {
		contract_code_v = contractCodeV;
	}
}
