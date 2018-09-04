package com.zhiwei.credit.dao.pay;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.pay.BankCode;

/**
 * 
 * @author 
 *
 */
public interface BankCodeDao extends BaseDao<BankCode>{
	
	/**
	 * 获取省市列表
	 * @param ParentCode
	 * @return
	 */
	public List<BankCode> getAreaList(String ParentCode);
	/**
	 * 获得第三方开户行
	 * @param cityname
	 * @param thirdPayConfig
	 * @return
	 */
	public BankCode getBycityName(String cityname, String thirdPayConfig);
	
}