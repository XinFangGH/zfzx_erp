package com.zhiwei.credit.dao.creditFlow.financingAgency.business;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;

/**
 * 
 * @author 
 *
 */
public interface PlBusinessDirProKeepDao extends BaseDao<PlBusinessDirProKeep>{
	public PlBusinessDirProKeep getByType(String type,Long id);
} 