package com.zhiwei.credit.service.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.BpCustLoginlog;

/**
 * 
 * @author 
 *
 */
public interface BpCustLoginlogService extends BaseService<BpCustLoginlog>{

	public List<BpCustLoginlog> getAllList(HttpServletRequest request, Integer start,
			Integer limit);
	
}


