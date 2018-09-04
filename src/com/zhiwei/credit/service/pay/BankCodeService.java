package com.zhiwei.credit.service.pay;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.pay.BankCode;

/**
 * 
 * @author 
 *
 */
public interface BankCodeService extends BaseService<BankCode>{
	
	/**
	 * 获取省市列表
	 * @param ParentCode
	 * @return
	 */
	public Map<String, String> getAreaList(String ParentCode);

	public BankCode getBycityName(String cityname, String thirdPayConfig);
	
}


