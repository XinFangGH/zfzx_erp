package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishInterest;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;
import com.zhiwei.credit.service.creditFlow.finance.SlPunishDetailService;
import com.zhiwei.credit.service.creditFlow.finance.SlPunishInterestService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class SlPunishInterestAction extends BaseAction{
	@Resource
	private SlPunishInterestService slPunishInterestService;
	@Resource
	private SlFundQlideService slFundQlideService;
	@Resource
	private SlPunishDetailService slPunishDetailService;
	@Resource
	private SlBankAccountService slBankAccountService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private SlFundIntentService slFundIntentService;
	private SlPunishInterest slPunishInterest;
	private Long fundQlideId;
	private SlFundQlide slFundQlide;
	private Long punishInterestId;
	private String qlideId;;

	public Long getPunishInterestId() {
		return punishInterestId;
	}

	public void setPunishInterestId(Long punishInterestId) {
		this.punishInterestId = punishInterestId;
	}

	public SlPunishInterest getSlPunishInterest() {
		return slPunishInterest;
	}

	public void setSlPunishInterest(SlPunishInterest slPunishInterest) {
		this.slPunishInterest = slPunishInterest;
	}

	public Long getFundQlideId() {
		return fundQlideId;
	}

	public void setFundQlideId(Long fundQlideId) {
		this.fundQlideId = fundQlideId;
	}

	public SlFundQlide getSlFundQlide() {
		return slFundQlide;
	}

	public void setSlFundQlide(SlFundQlide slFundQlide) {
		this.slFundQlide = slFundQlide;
	}

	public String getQlideId() {
		return qlideId;
	}

	public void setQlideId(String qlideId) {
		this.qlideId = qlideId;
	}
	
	//TODO ---------------罚息对账方法开始----------------------
	
	/**
	 * 罚息（逾期）单笔对账(一笔款项对应一笔流水)
	 * @return
	 */
	public String editQlideCheck() {
		SlFundQlide oldSlFundQlide = slFundQlideService.get(fundQlideId);//流水记录
		slPunishInterest =slPunishInterestService.get(punishInterestId);//罚息记录
		
		BigDecimal inIntentMoney=slFundQlide.getNotMoney();//流水未对账金额
		
		//1.新增罚息(逾期)资金明细
		SlPunishDetail slPunishDetail = new SlPunishDetail();
		slPunishDetail.setOperTime(new Date());
		slPunishDetail.setPunishInterestId(slPunishInterest.getPunishInterestId());
		slPunishDetail.setFundQlideId(oldSlFundQlide.getFundQlideId());
		slPunishDetail.setAfterMoney(inIntentMoney);
		slPunishDetail.setFactDate(oldSlFundQlide.getFactDate());
		AppUser user=ContextUtil.getCurrentUser();
		slPunishDetail.setCheckuser(user.getFullname());
		slPunishDetail.setCompanyId(slPunishInterest.getCompanyId());	
	
		//2.修改罚息记录
		slPunishInterest.setAfterMoney(slPunishInterest.getAfterMoney().add(inIntentMoney));//修改罚息已对账金额
		slPunishInterest.setNotMoney(slPunishInterest.getNotMoney().subtract(inIntentMoney));//修改罚息未对账金额
		slPunishInterest.setFactDate(oldSlFundQlide.getFactDate());
		
		//3.修改流水
		oldSlFundQlide.setAfterMoney(oldSlFundQlide.getAfterMoney().add(inIntentMoney));
		oldSlFundQlide.setNotMoney(oldSlFundQlide.getNotMoney().subtract(inIntentMoney));
		oldSlFundQlide.setDialMoney(oldSlFundQlide.getIncomeMoney());//流水收入金额   
		
		slPunishDetailService.save(slPunishDetail);
		slFundQlideService.merge(oldSlFundQlide);
		slPunishInterestService.merge(slPunishInterest);
		
		//4.修改款项
		SlFundIntent slFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());
		slFundIntent.setFaxiAfterMoney(slPunishInterest.getAfterMoney());//修改罚息已对账
		slFundIntentService.merge(slFundIntent);
		
		return SUCCESS;
	}
	
	/**
	 * 罚息(逾期)批量对账(一笔款项对应多笔流水)
	 * @return
	 */
	public String check() {
		String[] ids = getRequest().getParameterValues("qlideId");//流水id集合
		slPunishInterest =slPunishInterestService.get(punishInterestId);
		SlFundIntent slFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());
		if(null==slPunishInterest.getFlatMoney()){
			slPunishInterest.setFlatMoney(new BigDecimal(0));
		}
		BigDecimal payinmoney=slPunishInterest.getIncomeMoney().subtract(slPunishInterest.getFlatMoney());//罚息应收金额
		if (payinmoney.compareTo(slPunishInterest.getAfterMoney()) == 0) {//已对账
			return SUCCESS;
		}
		int count = 0;//（如果其中任何一条流水的金额+罚息已对账金额）》=罚息应收金额，则只需要循环一次
		if (ids != null) {
			for (String id : ids ) {
				if (!id.equals("") && count < 1) {
					payinmoney=slPunishInterest.getIncomeMoney().subtract(slPunishInterest.getFlatMoney()).subtract(slPunishInterest.getAfterMoney());//罚息应收金额
					SlPunishDetail slPunishDetail = new SlPunishDetail();//创建一条罚息明细
					slPunishDetail.setOperTime(new Date());
					SlFundQlide slFundQlide = slFundQlideService.get(new Long(id));
					//判断流水是支出还是收入
					if(slFundQlide.getPayMoney()!=null){
	                	slFundQlide.setDialMoney(slFundQlide.getPayMoney());
	                }else{
	                	slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	                } 
					BigDecimal chargeAfterMoney = slPunishInterest.getAfterMoney();//罚息已对账金额
					BigDecimal qlideNotMoney = slFundQlide.getNotMoney();//流水未对账金额
					BigDecimal detailAfterMoney = qlideNotMoney;
					chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);//罚息已对账金额     +  流水未对账金额
					//如果（罚息已对账金额     +  流水未对账金额）》=罚息应收金额
					int flag=chargeAfterMoney.compareTo(payinmoney);
					if (flag == 1 || flag == 0) {
						count++;
						detailAfterMoney = slPunishInterest.getNotMoney();
						slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().add(detailAfterMoney));
						slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(detailAfterMoney));
						slPunishInterest.setAfterMoney(slPunishInterest.getAfterMoney().add(detailAfterMoney));
						slPunishInterest.setNotMoney(slPunishInterest.getNotMoney().subtract(detailAfterMoney));
					} else {
						slPunishInterest.setAfterMoney(chargeAfterMoney);
						slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().add(slFundQlide.getDialMoney()));
						slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(slFundQlide.getDialMoney()));
						BigDecimal chargeNotMoney = payinmoney.subtract(chargeAfterMoney);
						slPunishInterest.setNotMoney(chargeNotMoney);
					}
					slPunishInterest.setFactDate(slFundQlide.getFactDate());
					slPunishDetail.setPunishInterestId(slPunishInterest.getPunishInterestId());
					slPunishDetail.setFundQlideId(slFundQlide.getFundQlideId());
					slPunishDetail.setAfterMoney(detailAfterMoney);
					slPunishDetail.setFactDate(slFundQlide.getFactDate());
					AppUser user=ContextUtil.getCurrentUser();
					slPunishDetail.setCheckuser(user.getFullname());
					slPunishDetail.setCompanyId(slPunishInterest.getCompanyId());	
					
					slPunishDetailService.save(slPunishDetail);
					slFundQlideService.merge(slFundQlide);
					slPunishInterestService.merge(slPunishInterest);
					
					slFundIntent.setFaxiAfterMoney(slPunishInterest.getAfterMoney());//修改款项罚息已对账金额
					slFundIntentService.merge(slFundIntent);
				}
			}
		}
		return SUCCESS;
	
	}
	
	/**
	 * 多笔罚息对账
	 * @return
	 */
	public String manyIntentCheck(){
		slFundQlide = slFundQlideService.get(fundQlideId);
        if(slFundQlide.getPayMoney()!=null){
        	slFundQlide.setDialMoney(slFundQlide.getPayMoney());
        }else{
	       	slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	    }     
		//流水初始化
		String[] ids = getRequest().getParameterValues("qlideId");
		if (ids != null) {
			for (String id : ids ) {
				if (!id.equals("")) {
					slPunishInterest = slPunishInterestService.get(new Long(id));
					BigDecimal lin = new BigDecimal(0.00);
					BigDecimal payinmoney=new BigDecimal(0.00);
					if (slPunishInterest.getIncomeMoney().compareTo(lin) == 0) {
						payinmoney=slPunishInterest.getPayMoney();
					} else {
						payinmoney= slPunishInterest.getIncomeMoney();
					}
					//款项初始化
					List<SlPunishDetail> setdetails=slPunishDetailService.getlistbyActualChargeId(slPunishInterest.getPunishInterestId());
					List<SlPunishDetail> sortlist = new ArrayList<SlPunishDetail>();
					for(SlPunishDetail d:setdetails){
						sortlist.add(d);
					}
					//详情初始化
					SlPunishDetail slPunishDetail = new SlPunishDetail();
					long day = CompareDate.compare_date(slPunishInterest.getIntentDate(), slFundQlide.getFactDate());
					if (day == -1 || day == 0) {
						slPunishDetail.setOperTime(new Date());
					} else {
						slPunishDetail.setOperTime(new Date());
					}
					BigDecimal chargeAfterMoney = slPunishInterest.getAfterMoney();
					BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
					BigDecimal detailAfterMoney = qlideNotMoney;
					chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
			
					detailAfterMoney = slPunishInterest.getNotMoney();
					slFundQlide.setAfterMoney(slPunishInterest.getNotMoney().add(slFundQlide.getAfterMoney()));
					slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(slPunishInterest.getNotMoney()));
					slPunishInterest.setAfterMoney(payinmoney);
					slPunishInterest.setNotMoney(new BigDecimal(0.00));
					slPunishInterest.setFactDate(slFundQlide.getFactDate());
					slPunishDetail.setPunishInterestId(slPunishInterest.getPunishInterestId());
					slPunishDetail.setFundQlideId(slFundQlide.getFundQlideId());
					slPunishDetail.setAfterMoney(detailAfterMoney);
					slPunishDetail.setFactDate(slFundQlide.getFactDate());
					AppUser user=ContextUtil.getCurrentUser();
					slPunishDetail.setCheckuser(user.getFullname());
					slPunishDetail.setCompanyId(slPunishInterest.getCompanyId());	
					slPunishDetailService.save(slPunishDetail);
					slFundQlideService.save(slFundQlide);
					slPunishInterestService.save(slPunishInterest);
					SlFundIntent slFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());
					slFundIntent.setFaxiAfterMoney(slPunishInterest.getAfterMoney());//修改罚息已对账
					slFundIntentService.merge(slFundIntent);
				}
			}
		}
		return SUCCESS;
	
	}
	
	//TODO ---------------罚息对账方法结束----------------------

	/**
	 * 显示列表
	 */
	public String list(){
		Long fundIntentId=Long.valueOf(this.getRequest().getParameter("fundIntentId"));
		List<SlPunishInterest> list= slPunishInterestService.listbyisInitialorId(fundIntentId);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate","factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();

		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				slPunishInterestService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlPunishInterest slPunishInterest=slPunishInterestService.get(punishInterestId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slPunishInterest));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slPunishInterest.getPunishInterestId()==null){
			slPunishInterestService.save(slPunishInterest);
		}else{
			SlPunishInterest orgSlPunishInterest=slPunishInterestService.get(slPunishInterest.getPunishInterestId());
			try{
				BeanUtil.copyNotNullProperties(orgSlPunishInterest, slPunishInterest);
				slPunishInterestService.save(orgSlPunishInterest);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String cashCheck() {
		BigDecimal cashMoney=slFundQlide.getNotMoney();
		Date factDate=slFundQlide.getFactDate();
		String transactionType=slFundQlide.getTransactionType();
		SlFundQlide slFundQlide = slFundQlideService.getcashQlide("cash").get(0);
		slFundQlide.setFactDate(factDate);
		 if(slFundQlide.getPayMoney()!=null){
        	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
         }else{
        	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
        	 
         }       //现金流水的初始化
		 
		 slPunishInterest =slPunishInterestService.get(punishInterestId);
		BigDecimal lin = new BigDecimal(0.00);
		BigDecimal payinmoney=new BigDecimal(0.00);
		if (slPunishInterest.getIncomeMoney().compareTo(lin) == 0) {
			payinmoney=slPunishInterest.getPayMoney();
		} else {
			payinmoney= slPunishInterest.getIncomeMoney();
		}
		 //费用的初始化
		
		SlPunishDetail slPunishDetail = new SlPunishDetail();
				 if(slFundQlide.getPayMoney()!=null){
                	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
                 }else{
                	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
                	 
                 } 
				long day = CompareDate.compare_date(slPunishInterest.getIntentDate()
						, slFundQlide.getFactDate());
				if (day == -1 || day == 0) {
					if(null==slPunishInterest.getFactDate())
					
						slPunishDetail.setOperTime(new Date());
				} else {
					slPunishDetail.setOperTime(new Date());
				}
			
				BigDecimal chargeAfterMoney = slPunishInterest.getAfterMoney();
				BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
				BigDecimal detailAfterMoney = qlideNotMoney;
				chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
			
				slPunishInterest.setAfterMoney(slPunishInterest.getAfterMoney().add(cashMoney));
				slPunishInterest.setNotMoney(slPunishInterest.getNotMoney().subtract(cashMoney));

				slPunishInterest.setFactDate(slFundQlide.getFactDate());
				slPunishDetail.setPunishInterestId(slPunishInterest.getPunishInterestId());
				slPunishDetail.setFundQlideId(slFundQlide.getFundQlideId());
				slPunishDetail.setAfterMoney(cashMoney);
				slPunishDetail.setFactDate(slFundQlide.getFactDate());
				slPunishDetail.setTransactionType(transactionType);
				AppUser user=ContextUtil.getCurrentUser();
				slPunishDetail.setCheckuser(user.getFullname());
				slPunishDetail.setCompanyId(slPunishInterest.getCompanyId());	
				slPunishDetailService.save(slPunishDetail);
		       //slFundQlideService.save(slFundQlide);
				slPunishInterestService.save(slPunishInterest);
				SlFundIntent slFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());
				slFundIntent.setFaxiAfterMoney(slPunishInterest.getAfterMoney());
				slFundIntentService.save(slFundIntent);
				
		return SUCCESS;
	}
	
	public String savecashqlideAndcheck(){
		
		if(slFundQlide.getFundQlideId()==null){
			if(null !=slFundQlide.getPayMoney()){
				slFundQlide.setNotMoney(slFundQlide.getPayMoney());
			}else{
				slFundQlide.setNotMoney(slFundQlide.getIncomeMoney());
				
			}
			slFundQlide.setAfterMoney(new BigDecimal(0.00));
			slFundQlide.setOperTime(new Date());
			slFundQlide.setMyAccount("cahsqlideAccount");
			slFundQlide.setIsCash("cash");
			
			
			BigDecimal cashMoney=slFundQlide.getNotMoney();
			Date factDate=slFundQlide.getFactDate();
			String transactionType=slFundQlide.getTransactionType();
			 if(slFundQlide.getPayMoney()!=null){
	        	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
	         }else{
	        	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	        	 
	         }       //现金流水的初始化
			 
			 slPunishInterest =slPunishInterestService.get(punishInterestId);
			BigDecimal lin = new BigDecimal(0.00);
			BigDecimal payinmoney=new BigDecimal(0.00);
			if (slPunishInterest.getIncomeMoney().compareTo(lin) == 0) {
				payinmoney=slPunishInterest.getPayMoney();
			} else {
				payinmoney= slPunishInterest.getIncomeMoney();
			}
			 //费用的初始化
			
			SlPunishDetail slPunishDetail = new SlPunishDetail();
					 if(slFundQlide.getPayMoney()!=null){
	                	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
	                 }else{
	                	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	                	 
	                 } 
					long day = CompareDate.compare_date(slPunishInterest.getIntentDate()
							, slFundQlide.getFactDate());
					if (day == -1 || day == 0) {
						
						slPunishDetail.setOperTime(new Date());
					} else {
						slPunishDetail.setOperTime(new Date());
					}
				
					BigDecimal chargeAfterMoney = slPunishInterest.getAfterMoney();
					BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
					BigDecimal detailAfterMoney = qlideNotMoney;
					chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
				
					slPunishInterest.setAfterMoney(slPunishInterest.getAfterMoney().add(cashMoney));
					slPunishInterest.setNotMoney(slPunishInterest.getNotMoney().subtract(cashMoney));
                 
					slPunishInterest.setFactDate(slFundQlide.getFactDate());
					slFundQlide.setAfterMoney(cashMoney);
					slFundQlide.setNotMoney(lin);
					slFundQlideService.save(slFundQlide);
					slPunishDetail.setPunishInterestId(slPunishInterest.getPunishInterestId());
					slPunishDetail.setFundQlideId(slFundQlide.getFundQlideId());
					slPunishDetail.setAfterMoney(cashMoney);
					slPunishDetail.setFactDate(slFundQlide.getFactDate());
					slPunishDetail.setTransactionType(transactionType);
					AppUser user=ContextUtil.getCurrentUser();
					slPunishDetail.setCheckuser(user.getFullname());
					slPunishDetail.setCompanyId(slPunishInterest.getCompanyId());	
					slPunishDetailService.save(slPunishDetail);
			       //slFundQlideService.save(slFundQlide);
					slPunishInterestService.save(slPunishInterest);
					SlFundIntent slFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());
					slFundIntent.setFaxiAfterMoney(slPunishInterest.getAfterMoney());
					slFundIntentService.save(slFundIntent);
					
		        	return SUCCESS;
			
		}
		
		
		
		return SUCCESS;
	}
	 public String cancelAccount(){
			String[] ids = getRequest().getParameterValues("qlideId");
			slPunishInterest =slPunishInterestService.get(punishInterestId);
			BigDecimal lin = new BigDecimal(0.00);
			BigDecimal payinmoney=new BigDecimal(0.00);
			if (ids != null) {
				for (String id : ids ) {
					if (!id.equals("") && !id.equals("0")){
						if(null ==slPunishInterestService.get(new Long(id))){return SUCCESS;}
						SlFundQlide slFundQlide =slFundQlideService.get(slPunishDetailService.get(new Long(id)).getFundQlideId());
						List<SlPunishDetail> slChargeDetails =slPunishDetailService.getlistbyActualChargeId(punishInterestId);
						SlPunishDetail slPunishDetail=new SlPunishDetail();
						List<SlPunishDetail> sortlist = new ArrayList<SlPunishDetail>();
						for(SlPunishDetail d:slChargeDetails){
							if(d.getFundQlideId().equals(slFundQlide.getFundQlideId()))
							{	
								slPunishDetail=d;
							}
							   sortlist.add(d);
						   }
						sortlist.remove(slPunishDetail);
						MyComparechargedetail myComparechargedetail=new MyComparechargedetail();
						Collections.sort(sortlist,myComparechargedetail);
						if(sortlist.size()==0){
							slPunishInterest.setFactDate(null);
						}else{
								 Date maxDate=slFundQlideService.get(sortlist.get(sortlist.size()-1).getFundQlideId()).getFactDate();
								
								 slPunishInterest.setFactDate(maxDate);
						}
						slPunishInterest.setAfterMoney(slPunishInterest.getAfterMoney().subtract(slPunishDetail.getAfterMoney()));
						slPunishInterest.setNotMoney(slPunishInterest.getNotMoney().add(slPunishDetail.getAfterMoney()));
						
						slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().subtract(slPunishDetail.getAfterMoney()));
						slFundQlide.setNotMoney(slFundQlide.getNotMoney().add(slPunishDetail.getAfterMoney()));
						
						slPunishDetail.setIscancel(Short.valueOf("1"));
						AppUser user=ContextUtil.getCurrentUser();
						slPunishDetail.setCancelremark(user.getFullname()+"于"+DateUtil.getNowDateTime()+"撤销此对账记录");
						slPunishDetailService.save(slPunishDetail);
						slFundQlideService.save(slFundQlide);
						slPunishInterestService.save(slPunishInterest);
						SlFundIntent slFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());
						slFundIntent.setFaxiAfterMoney(slPunishInterest.getAfterMoney());
						slFundIntentService.save(slFundIntent);
					
					}
				}
		}
		 return SUCCESS; 
	 }
	 public String MoneyDetail() {
		slPunishInterest = slPunishInterestService.get(punishInterestId);
		List<SlPunishDetail> slChargeDetails = new ArrayList<SlPunishDetail>();
		slChargeDetails = slPunishDetailService.getlistbyActualChargeId(punishInterestId);
		List<SlPunishDetail> list = new ArrayList<SlPunishDetail>();
		for (SlPunishDetail s : slChargeDetails) {
			SlFundQlide slFundQlide=slFundQlideService.get(s.getFundQlideId());
			if(null==slFundQlide.getIsCash()){
				s.setQlideafterMoney(slFundQlide.getAfterMoney());
				List<SlBankAccount>	 sllist=slBankAccountService.getbyaccount(slFundQlide.getMyAccount());
				if(sllist !=null){
					CsBank cb=csBankService.get(sllist.get(0).getOpenBankId());
					s.setQlidemyAccount(cb.getBankname()+"-"+sllist.get(0).getName()+"-"+sllist.get(0).getAccount());
				}
				s.setQlidenotMoney(slFundQlide.getNotMoney());
				s.setQlidetransactionType(slFundQlide.getTransactionType());
				s.setQlidepayMoney(slFundQlide.getPayMoney());
				s.setQlideincomeMoney(slFundQlide.getIncomeMoney());
				s.setQlidecurrency(slFundQlide.getCurrency());
			}else{
				if(slFundQlide.getMyAccount().equals("cahsqlideAccount")){
					s.setQlidemyAccount("现金账户");
					s.setQlideafterMoney(slFundQlide.getAfterMoney());
					s.setQlidenotMoney(slFundQlide.getNotMoney());
					s.setQlidetransactionType(slFundQlide.getTransactionType());
					s.setQlidepayMoney(slFundQlide.getPayMoney());
					s.setQlideincomeMoney(slFundQlide.getIncomeMoney());
					s.setQlidecurrency(slFundQlide.getCurrency());
					
				}else{
					s.setQlidetransactionType(s.getTransactionType());
				}
			}
			list.add(s);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate","factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	 }
	 
	 public String editAfterMoney() {
			SlPunishInterest s = slPunishInterestService
					.get(slPunishInterest.getPunishInterestId());

			SlFundDetail slFundDetail = new SlFundDetail();
			SlFundQlide slFundQlide = slFundQlideService.getcashQlide("ping")
					.get(0);

			s.setFlatMoney(s.getFlatMoney().add(slPunishInterest.getFlatMoney()));
			s.setNotMoney(slPunishInterest.getNotMoney());
			slPunishInterestService.save(s);

			//saveprojectreleadfinance(s.getProjectId(), s.getBusinessType());

			setJsonString("{success:true}");
			return SUCCESS;
		}
}
