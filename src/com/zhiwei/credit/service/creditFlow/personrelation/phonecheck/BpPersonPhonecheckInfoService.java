package com.zhiwei.credit.service.creditFlow.personrelation.phonecheck;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfo;

/**
 * 
 * @author 
 *
 */
public interface BpPersonPhonecheckInfoService extends BaseService<BpPersonPhonecheckInfo>{

	BpPersonPhonecheckInfo getPhoneCheck(String projectId,int id);
	
}


