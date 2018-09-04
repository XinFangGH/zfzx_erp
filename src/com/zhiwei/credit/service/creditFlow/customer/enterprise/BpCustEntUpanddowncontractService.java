package com.zhiwei.credit.service.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddowncontract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamContract;

/**
 * 
 * @author 
 *
 */
public interface BpCustEntUpanddowncontractService extends BaseService<BpCustEntUpanddowncontract>{
	public List<BpCustEntUpanddowncontract> getByentpriseid(Integer entpriseid);
	public List<BpCustEntUpstreamContract> getByupAndDownContractId(Integer upAndDownContractId);
	public List<BpCustEntDownstreamContract> getByupAndDownContractId1(Integer upAndDownContractId);
	public BpCustEntUpanddowncontract getByeid(Integer upAndDownContractId);
	public BpCustEntUpstreamContract getByeupid(Integer upContractId);
	public BpCustEntDownstreamContract getByedownid(Integer downContractId);
}


