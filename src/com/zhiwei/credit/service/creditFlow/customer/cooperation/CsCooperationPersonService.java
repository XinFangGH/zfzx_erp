package com.zhiwei.credit.service.creditFlow.customer.cooperation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;

/**
 * 
 * @author 
 *
 */
public interface CsCooperationPersonService extends BaseService<CsCooperationPerson>{
	/**
	 * 根据证件号码查找个人合作机构
	 * @param cardNumber
	 * @return  CsCooperationPerson
	 */
	public CsCooperationPerson queryByCardnumber(String cardNumber);


	public List<CsCooperationPerson> getAllAccountList(Map map, PagingBean pb);

	public List<CsCooperationPerson> accountList(Map map, PagingBean pb);
	

}


