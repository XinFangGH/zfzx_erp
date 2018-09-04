package com.zhiwei.credit.service.creditFlow.creditAssignment.project;



import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.VObligationInvestInfo;

public interface VObligationInvestInfoService extends BaseService<VObligationInvestInfo>{
//查出所有债权信息，或者是不包含系统默认的金帆平台债权账户
	public List<VObligationInvestInfo> getAllList(String obligationState,
			String obligationName, String investName, String payintentPeriod,
			String investEndDate, Integer start, Integer limit);
//债权到期提醒查询方法
	public List<VObligationInvestInfo> getInvestList(String obligationState,
			String obligationName, String investName, String investStartDate,
			String investEndDate, Integer start, Integer limit);
	//根据投资人的id查出来投资人的债权情况
	public List<VObligationInvestInfo> getlistInvestPersonByPersonId(
			String investPersonId, String obligationState, Integer start,
			Integer limit);
	public List<VObligationInvestInfo> getlistInvestPersonObligation(
			String investPersonId, String investObligationStatus,
			String investStartDate, String investEndDate);

}
