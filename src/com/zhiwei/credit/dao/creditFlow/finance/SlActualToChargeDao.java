package com.zhiwei.credit.dao.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;

/**
 * 
 * @author 
 *
 */
public interface SlActualToChargeDao extends BaseDao<SlActualToCharge>{
	public  List<SlActualToCharge> getallbycompanyId();
	public void initActualCharges(Long projectId,String projectNumber,String projectName,List<SlPlansToCharge> plans,String businessType,Short flag);
	/**
	 * 根据产品id来初始化费用清单
	 * @param projectId
	 * @param projectNumber
	 * @param projectName
	 * @param businessType
	 * @param flag
	 * @param productId
	 */
	public void initActualChargesProduct(Long projectId,String projectNumber,String projectName,String businessType,Short flag,Long productId);
	public List<SlActualToCharge> listbyproject(Long projectId);
	public List<SlActualToCharge> listbyproject(Long projectId,String businessType);
	public int searchsize(Map<String,String> map,String businessType);
	public List<SlActualToCharge> search(Map<String,String> map,String businessType);
	public int updateOverdue(SlActualToCharge s);
	public int updateFlatMoney(SlActualToCharge s);
	public List<SlActualToCharge> getlistbyslSuperviseRecordId(Long slSuperviseRecordId,String businessType,Long projectId);
	public List<SlActualToCharge> listbyproject(Long projectId,String businessType,String chargeKey);
	public List<SlActualToCharge> getlistbyslEarlyRepaymentRecordId(Long slEarlyRepaymentRecordId,String businessType,Long projectId);
	public List<SlActualToCharge> getlistbyslAlteraccrualRecordId(Long slAlteraccrualRecordId,String businessType,Long projectId);
	public List<SlActualToCharge> getAllbyProjectId(Long projectId,String businessType);
	/**
	 * 根据标的id和项目的id 获取到手续费的支出和收入金额
	 * @param bidId
	 * @param projectId2
	 * @param type
	 * @return
	 */
	public BigDecimal getSumMoney(String bidId, String projectId2, String type,String businessType);

	public List<SlActualToCharge> listByBidPlanId(Long bidPlanId);
	public List<SlActualToCharge> listbyProjectIdAndBidPlanId(Long projectId,
			String bidPlanId);
}