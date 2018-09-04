package com.zhiwei.credit.dao.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;

/**
 * 
 * @author 
 *
 */
public interface SlFundDetailDao extends BaseDao<SlFundDetail>{
	public  List<SlFundDetail> getallbycompanyId();
	
	public List<SlFundDetail> getlistbySlFundIntentId(Long slFundIntentId, String type);
	
	public List<SlFundDetail> getAllRecordList(String projectId, String period);
	
}