package com.zhiwei.credit.service.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;

/**
 * 
 * @author 
 *
 */
public interface SlFundDetailService extends BaseService<SlFundDetail>{
	public  List<SlFundDetail> getallbycompanyId();
	
	/**
	 * 查询对账明细
	 * @param slFundIntentId  款项Id
	 * @param type  标识查询资金明细的范围(0查所有既包括罚息、1只查相关款项)
	 * @return
	 */
	public List<SlFundDetail> getlistbySlFundIntentId(Long slFundIntentId, String type);
	
	/**
	 * 综合款项台账对账明细查看方法
	 * @param projectId  项目id
	 * @param period     期数
	 * @return
	 */
	public List<SlFundDetail> getAllRecordList(String projectId, String period);

}


