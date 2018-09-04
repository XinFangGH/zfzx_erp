package com.zhiwei.credit.service.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUserAccountInfo;

/**
 * 
 * @author 
 *
 */
public interface PlFinanceProductUserAccountInfoService extends BaseService<PlFinanceProductUserAccountInfo>{
	
	public List<PlFinanceProductUserAccountInfo> getListByParamet(
			HttpServletRequest request, PagingBean pb);
	
	public boolean  creatYestDayIntent(Date date);
	public void  creatYestDayIntent();
	public Boolean creatBeforeIntentRecord(String date);
	
}


