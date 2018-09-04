package com.zhiwei.credit.service.creditFlow.finance;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;


public interface SlBankAccountService extends BaseService<SlBankAccount>{
	public  List<SlBankAccount> getallbycompanyId( PagingBean pb);
	public  List<SlBankAccount> getallbycompanyId( PagingBean pb,Map<String,String> map);
	public  List<SlBankAccount> getbyaccount(String accont);
	public  List<SlBankAccount> webgetbyaccount(String accont,Long companyId);
	public  List<SlBankAccount> getall(Map<String,String> map);
	public  int getallsize(Map<String,String> map);
	public  List<SlBankAccount> selectbycompanyId( PagingBean pb,Map<String,String> map);
}


