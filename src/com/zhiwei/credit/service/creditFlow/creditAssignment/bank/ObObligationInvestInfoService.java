package com.zhiwei.credit.service.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;

/**
 * 
 * @author 
 *
 */
public interface ObObligationInvestInfoService extends BaseService<ObObligationInvestInfo>{
	//依据投资人id  查出投资人
	public List<ObObligationInvestInfo> getListInvestPeonId(Long investmentPersonId,String flag);
//查出系统默认的系统账户信息
	public List<ObObligationInvestInfo> getInfoByobObligationProjectId(Long id,
			String flag);
	//给投资人款项对账以及给平台账户添加账户支出明细以及修改投资人投资状态
	public void checkSlFundQulid(ObObligationInvestInfo ob);
	//修改系统默认债权信息以及项目状态（投资人添加债权和撤销债权时以及产品添加债权产品时用的公共方法）
	public void changeStatus(String obligationId, int i);
}


