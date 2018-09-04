package com.zhiwei.credit.service.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamCustom;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddownstream;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamCustom;

/**
 * 
 * @author 
 *
 */
public interface BpCustEntUpanddownstreamService extends BaseService<BpCustEntUpanddownstream>{
	public List<BpCustEntUpanddownstream> getByentpriseid(Integer entpriseid);
	public List<BpCustEntUpstreamCustom> getByupAndDownCustomId(Integer upAndDownCustomId);
	public List<BpCustEntDownstreamCustom> getByupAndDownCustomId1(Integer upAndDownCustomId);
	public BpCustEntUpanddownstream getByeid(Integer upAndDownCustomId);
	public BpCustEntUpstreamCustom getByeupid(Integer upCustomId);
	public BpCustEntDownstreamCustom getByedownid(Integer downCustomId);
	
}


