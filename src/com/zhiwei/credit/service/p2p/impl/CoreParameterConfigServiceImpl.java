package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.CoreParameterConfigDao;
import com.zhiwei.credit.model.p2p.CoreParameterConfig;
import com.zhiwei.credit.service.p2p.CoreParameterConfigService;

/**
 * 
 * @author 
 *
 */
public class CoreParameterConfigServiceImpl extends BaseServiceImpl<CoreParameterConfig> implements CoreParameterConfigService{
	@SuppressWarnings("unused")
	private CoreParameterConfigDao dao;
	
	public CoreParameterConfigServiceImpl(CoreParameterConfigDao dao) {
		super(dao);
		this.dao=dao;
	}

}