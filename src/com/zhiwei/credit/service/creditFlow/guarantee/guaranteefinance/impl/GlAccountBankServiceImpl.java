package com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankDao;
import com.zhiwei.credit.dao.system.CsDicAreaDynamDao;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankService;

/**
 * 
 * @author 
 *
 */
public class GlAccountBankServiceImpl extends BaseServiceImpl<GlAccountBank> implements GlAccountBankService{
	@SuppressWarnings("unused")
	private GlAccountBankDao dao;
	@Resource
	private GlAccountBankCautionmoneyDao  GlAccountBankCautionmoneyDao;
	@Resource
	private CsDicAreaDynamDao csDicAreaDynamDao;
	public GlAccountBankServiceImpl(GlAccountBankDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public String getAccountBankTree(String node) {
		String json = "";
		GlAccountBank glAccountBank = null;
		GlAccountBankCautionmoney cautionMoney = null;
		Long accountId ;//授信银行表id
		List list =null ;
		List cautionList = null;
		List LList = new ArrayList();
		BigDecimal creditMoney = new BigDecimal(0);//项目表贷款金额
		BigDecimal authorizationMoney =  new BigDecimal(0);//授信额度
		BigDecimal surplusMoney =  new BigDecimal(0);//剩余授信额度
		BigDecimal usedMoney =  new BigDecimal(0);//已用额度
		String serviceTypeBank = "";//授信银行适用的业务种类
		String textBank = "";//授信银行名称
		
		String serviceTypeAccount = "";//保证金账户适用的业务种类
		BigDecimal bankmarginBank =  new BigDecimal(0);//授信银行对应的项目所使用了的额度
		BigDecimal bankmarginAccount =  new BigDecimal(0);//保证金账户对应的项目所使用了的额度
		
		List bankNumList = null;
		//AccountBankCautionMoney accountBankCautionMoney = null;
	
		
		String bankBranchName = "";//开户支行名称
		String accountName = "";//保证金账户名称
		
		
		//add 3.9 start
		List lessMoneyList = new ArrayList<Double>();
		double accountTotalMoney = 0;//账户总额
		double accountFrozenMoney = 0;//冻结总额
		double accountUnfreezeMoney = 0;//解冻总额
		
		double surplusMoneyAcc = 0;//保证金账户剩余总额
		list = this.getalllist() ;//授信银行主表
		
		if(node == "0" || "0".equals(node)){
		if(list != null || list.size() != 0){
			for(int i=0;i<list.size();i++){//循环银行
				glAccountBank = (GlAccountBank)list.get(i);
				this.evict(glAccountBank);
				if(null !=glAccountBank.getServiceTypeBank() && !glAccountBank.getServiceTypeBank().equals("")){
				glAccountBank.setText(glAccountBank.getText()+"("+glAccountBank.getServiceTypeBank()+")");
				}
				LList.add(glAccountBank);
			}
		}}else{
			cautionList = GlAccountBankCautionmoneyDao.getallbybankId(Long.parseLong(node));//保证金账户表
			if(cautionList == null || cautionList.size() == 0){
				
			}else{
				for(int k=0;k<cautionList.size();k++){
					cautionMoney = (GlAccountBankCautionmoney)cautionList.get(k);
					GlAccountBankCautionmoneyDao.evict(cautionMoney);
					cautionMoney.setText(cautionMoney.getText()+"-"+cautionMoney.getAccountname());
					if(null !=cautionMoney.getServiceTypeAccount() && !cautionMoney.getServiceTypeAccount().equals("")){
						cautionMoney.setText(cautionMoney.getText()+"("+cautionMoney.getServiceTypeAccount()+")");
						}
					LList.add(cautionMoney);
				}
			}
			
			
			
		}
		JSONArray jsonArray=JSONArray.fromObject(LList); 
		json = jsonArray.toString();
		JsonUtil.responseJsonString(json);
		return json;
	}

	@Override
	public List<GlAccountBank> getalllist() {
		return dao.getalllist();
	}
	
	@Override//add by gaoqingrui
	public List<GlAccountBank> getalllistByComId(Long companyId) {
		return dao.getalllistByComId(companyId);
	}
	
	/**
	 *根据分公司id获取分公司对应的银行信息
	 * @author gaoqingrui
	 * @param companyId 分公司Id 
	 */
	@Override
	public String getAccountBankTree(String node, String companyId) {
		String json = "";
		GlAccountBank glAccountBank = null;
		GlAccountBankCautionmoney cautionMoney = null;
		Long accountId ;//授信银行表id
		List list =null ;
		List cautionList = null;
		List LList = new ArrayList();
		BigDecimal creditMoney = new BigDecimal(0);//项目表贷款金额
		BigDecimal authorizationMoney =  new BigDecimal(0);//授信额度
		BigDecimal surplusMoney =  new BigDecimal(0);//剩余授信额度
		BigDecimal usedMoney =  new BigDecimal(0);//已用额度
		String serviceTypeBank = "";//授信银行适用的业务种类
		String textBank = "";//授信银行名称
		
		String serviceTypeAccount = "";//保证金账户适用的业务种类
		BigDecimal bankmarginBank =  new BigDecimal(0);//授信银行对应的项目所使用了的额度
		BigDecimal bankmarginAccount =  new BigDecimal(0);//保证金账户对应的项目所使用了的额度
		
		List bankNumList = null;
		//AccountBankCautionMoney accountBankCautionMoney = null;
	
		
		String bankBranchName = "";//开户支行名称
		String accountName = "";//保证金账户名称
		
		
		//add 3.9 start
		List lessMoneyList = new ArrayList<Double>();
		double accountTotalMoney = 0;//账户总额
		double accountFrozenMoney = 0;//冻结总额
		double accountUnfreezeMoney = 0;//解冻总额
		
		double surplusMoneyAcc = 0;//保证金账户剩余总额
//		list = this.getalllistByComId(Long.valueOf(companyId)) ;//授信银行主表
		list = this.getalllistByComId(Long.valueOf("1")) ;//授信银行主表
		if(node == "0" || "0".equals(node)){
		if(list != null || list.size() != 0){
			for(int i=0;i<list.size();i++){//循环银行
				glAccountBank = (GlAccountBank)list.get(i);
				this.evict(glAccountBank);
				if(null !=glAccountBank.getServiceTypeBank() && !glAccountBank.getServiceTypeBank().equals("")){
				glAccountBank.setText(glAccountBank.getText()+"("+glAccountBank.getServiceTypeBank()+")");
				}
				LList.add(glAccountBank);
			}
		}}else{
			cautionList = GlAccountBankCautionmoneyDao.getallbybankId(Long.parseLong(node));//保证金账户表
			if(cautionList == null || cautionList.size() == 0){
				
			}else{
				for(int k=0;k<cautionList.size();k++){
					cautionMoney = (GlAccountBankCautionmoney)cautionList.get(k);
					GlAccountBankCautionmoneyDao.evict(cautionMoney);
					cautionMoney.setText(cautionMoney.getText()+"-"+cautionMoney.getAccountname());
					if(null !=cautionMoney.getServiceTypeAccount() && !cautionMoney.getServiceTypeAccount().equals("")){
						cautionMoney.setText(cautionMoney.getText()+"("+cautionMoney.getServiceTypeAccount()+")");
						}
					LList.add(cautionMoney);
				}
			}
			
			
			
		}
		JSONArray jsonArray=JSONArray.fromObject(LList); 
		json = jsonArray.toString();
		JsonUtil.responseJsonString(json);
		return json;
	}
	

}