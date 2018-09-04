package com.zhiwei.credit.model.pay;

public class ResultBean {
	
	protected String ResultCode;
	protected String CodeMsg;
	protected String MoneymoremoreId;
	

	public String getMoneymoremoreId() {
		return MoneymoremoreId;
	}

	public void setMoneymoremoreId(String moneymoremoreId) {
		MoneymoremoreId = moneymoremoreId;
	}

	public String getCodeMsg() {
		return CodeMsg;
	}

	public void setCodeMsg(String codeMsg) {
		CodeMsg = codeMsg;
	}

	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}

}
