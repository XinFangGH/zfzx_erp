package com.zhiwei.credit.dao.creditFlow.financingAgency.persion;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;

/**
 * 
 * @author 
 *
 */
public interface BpPersionDirProDao extends BaseDao<BpPersionDirPro>{

	BpPersionDirPro getByBpFundProjectId(Long id);
	
	BpPersionDirPro getByMoneyPlanId(Long id);

	BpPersionDirPro getByProjectId1(Long projId, String businessType);
	
}