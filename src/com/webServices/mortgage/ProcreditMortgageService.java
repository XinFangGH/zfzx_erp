package com.webServices.mortgage;

import com.credit.proj.entity.ProcreditMortgage;
import com.zhiwei.core.service.BaseService;

public interface ProcreditMortgageService extends BaseService<ProcreditMortgage>{
	public void getTransactMortgage();
	public void getUnchainMortgage();
}
