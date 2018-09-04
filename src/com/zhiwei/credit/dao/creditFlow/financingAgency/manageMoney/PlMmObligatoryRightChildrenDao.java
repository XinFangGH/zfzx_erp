package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;

/**
 * 
 * @author 
 *
 */
public interface PlMmObligatoryRightChildrenDao extends BaseDao<PlMmObligatoryRightChildren>{
	List<PlMmObligatoryRightChildren> listbysearch(PagingBean pb,Map<String, String> map);
	List<PlMmObligatoryRightChildren> listbydifferentBigOr(Map<String, String> map);
	/**
	 * 查询U计划债权列表
	 * @param pb
	 * @param map
	 * @return
	 */
	List<PlMmObligatoryRightChildren> listbyUPLan(PagingBean pb,Map<String, String> map);
	
	List<PlMmObligatoryRightChildren> balanceList(PagingBean pb,Map<String, String> map);
	
	BigDecimal totalMoney(String account,String keystr);
}