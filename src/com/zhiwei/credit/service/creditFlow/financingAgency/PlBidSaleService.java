package com.zhiwei.credit.service.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidSale;
import com.hurong.credit.model.user.BpCustMember;

/**
 * 
 * @author 
 *
 */
public interface PlBidSaleService extends BaseService<PlBidSale>{
	 public List<PlBidSale> getCanTransferingList(Long userId, PagingBean pb,Map<String, String> map);
	 public List<PlBidSale> outTransferingList(Long userId, PagingBean pb,Map<String, String> map);
	 public List<PlBidSale> inTransferingList(Long userId, PagingBean pb,Map<String, String> map);
	 public List<PlBidSale> transferingList(Long userId, PagingBean pb,Map<String, String> map);
	 public void schedulingToRightChildren();
	 public PlBidSale getMoreinfoById(Long id);
}


