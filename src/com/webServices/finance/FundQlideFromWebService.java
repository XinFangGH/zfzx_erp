package com.webServices.finance;


import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
public interface FundQlideFromWebService extends BaseService<SlFundQlide>{
  public String getqlideTiming();
	  
  public String[] getqlideManual(String dateStart,String dateEnd,Long companyId);  
	  
	  
  
}
