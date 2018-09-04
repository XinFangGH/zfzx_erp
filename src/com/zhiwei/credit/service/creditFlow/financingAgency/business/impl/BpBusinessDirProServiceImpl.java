package com.zhiwei.credit.service.creditFlow.financingAgency.business.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.BpBusinessDirProDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessDirProService;

/**
 * 
 * @author 
 *
 */
public class BpBusinessDirProServiceImpl extends BaseServiceImpl<BpBusinessDirPro> implements BpBusinessDirProService{
	@SuppressWarnings("unused")
	private BpBusinessDirProDao dao;
	@Resource
	private PlBidPlanDao plBidPlanDao;
	@Resource
	private PlBidInfoService plBidInfoService;
	public BpBusinessDirProServiceImpl(BpBusinessDirProDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	@Override
	public BpBusinessDirPro residueMoneyMeth(BpBusinessDirPro pack) {

		//已发布债权金额合计
		BigDecimal publishOrMoney=new BigDecimal(0);
		//已发布金额/总金额
		double rate=0;
		//发布笔数
		int num=0;
		List<PlBidPlan> list=plBidPlanDao.listByState("(-1,3)", "B_Dir", pack.getBdirProId());
		List<Integer> idList=new ArrayList<Integer>();
		idList.add(1);
		idList.add(2);
		for(PlBidPlan releaseProj:list){
			//只有未发布和招标中的按计划招标金额计算占比，其他状态按实际投资金额计算占比
			if(!"0,1".contains(releaseProj.getState().toString())){
				Map<String, String> map = new HashMap<String, String>();
				map.put("bidId", releaseProj.getBidId().toString());
//				map.put("state", "1,2");
				List<PlBidInfo> infoList = plBidInfoService.getInfo(map,idList);
				for (PlBidInfo p : infoList) {
					publishOrMoney = publishOrMoney.add(p.getUserMoney());
				}
				num++;
			}else{
				publishOrMoney=publishOrMoney.add(releaseProj.getBidMoney());
				num++;
			}
		}
		 rate=publishOrMoney.divide(pack.getBidMoney(),BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)).doubleValue(); //乘以100 算出 是 % 之多少
		 pack.setPublishOrMoney(publishOrMoney);
		 pack.setPublishOrNum(num);
		 pack.setRate(rate);
		 pack.setResidueMoney(pack.getBidMoney().subtract(publishOrMoney));
		return pack;
	
	}

}