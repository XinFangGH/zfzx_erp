package com.zhiwei.credit.service.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveRelation;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;

/**
 * 
 * @author 
 *
 */
public interface BpCustProspectiveRelationService extends BaseService<BpCustProspectiveRelation>{

	public  Integer saveRelationData(String relationData, BpCustProsperctive customer);

	public List<BpCustProspectiveRelation> listByPerId(String perId);
	
}


