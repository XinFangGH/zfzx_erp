package com.zhiwei.credit.action.customer;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
/**
 * 
 * @author 
 *
 */
public class InvestPersonInfoAction extends BaseAction{
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private PlBidPlanService plBidPlanService;
	
	
	private InvestPersonInfo investPersonInfo;
	
	
	private Long investId;
	
	public Long getInvestId() {
		return investId;
	}

	public void setInvestId(Long investId) {
		this.investId = investId;
	}

	public InvestPersonInfo getInvestPersonInfo() {
		return investPersonInfo;
	}

	public void setInvestPersonInfo(InvestPersonInfo investPersonInfo) {
		this.investPersonInfo = investPersonInfo;
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		
		List<InvestPersonInfo> list= investPersonInfoService.getAll(filter);
		
		Type type=new TypeToken<List<InvestPersonInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 根据项目id获取对应的投资人信息
	 * @return
	 */
	public String listByProjectId(){
		String projectId = getRequest().getParameter("projectId");
		List<InvestPersonInfo> investList = new ArrayList<InvestPersonInfo>();
		if(true){
			BpFundProject bp = bpFundProjectService.getByProjectId(Long.parseLong(projectId), (short)1);
			if(bp!=null){
				String investorIds = bp.getInvestorIds();
				if(investorIds!=null&&!"".equals(investorIds)){
					String[] proArrs = investorIds.split(",");
					if(proArrs.length>0){
						for(int i=0;i<proArrs.length;i++){
							InvestPersonInfo investObj = investPersonInfoService.get(Long.valueOf(proArrs[i]));
							if(null!=investObj){
								//根据订单号查询pl_bid_info表获得投标日期
								if(null!=investObj.getOrderNo()){
									PlBidInfo pd=plBidInfoService.getByOrderNo(investObj.getOrderNo());
									if(null!=pd){
										investObj.setInvestTime(pd.getBidtime());
									}
								}
								investList.add(investObj);
							}
						}
					}
				}
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(investList==null?0:investList.size()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(investList));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	public String listByMoneyPlanId(){
		QueryFilter filter=new QueryFilter(this.getRequest());
		List<InvestPersonInfo> investList = new ArrayList<InvestPersonInfo>();
		String params = getRequest().getParameter("Q_moneyPlanId_L_EQ");
		String params1 = getRequest().getParameter("Q_bidPlanId_L_EQ");
		
		filter.getPagingBean().setPageSize(10000000);
		if(null==params&&null==params1){
			
		}else{
			investList = investPersonInfoService.getAll(filter);
			for(InvestPersonInfo per:investList){
				//根据订单号查询pl_bid_info表获得投标日期
				PlBidInfo info=null;
				if(null!=per.getOrderNo()){
					info=plBidInfoService.getByOrderNo(per.getOrderNo());
					if(null!=info){
						per.setInvestTime(info.getBidtime());
					}
				}
				if(info!=null&&info.getBidtime()!=null){
					per.setRemark(new SimpleDateFormat("yyyy-MM-dd").format(info.getBidtime()));
				}
				if(info!=null&&info.getCouponId()!=null&&!info.getCouponId().equals("")){
					BpCoupons cou = bpCouponsService.get(info.getCouponId());
					if(cou!=null){
						DecimalFormat df = new DecimalFormat("#0.00");
						String couValue = df.format(cou.getCouponValue());
						if(cou.getCouponType().toString().equals("3")){
							per.setCouponsValue(couValue+"%");
							per.setCouponsType("加息券");
							per.setValidMoney(couValue+"%");
						}else{
							per.setCouponsValue(couValue+"元");
							per.setCouponsType("返现券");
							PlBidPlan plan = plBidPlanService.get(info.getBidId());
							per.setValidMoney(df.format(plan.getReturnRatio().multiply(cou.getCouponValue().divide(new BigDecimal(100),2)))+"元");
						}
					}
				}
			}
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(investList==null?0:investList.size()).append(",result:");
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(investList));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String ids = getRequest().getParameter("ids");
		if(ids!=null&&!"".equals(ids)){
			//for(String id:ids){
				investPersonInfoService.remove(new Long(ids));
		//	}
		}
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		InvestPersonInfo investPersonInfo=investPersonInfoService.get(investId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(investPersonInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(investPersonInfo.getInvestId()==null){
			investPersonInfoService.save(investPersonInfo);
		}else{
			InvestPersonInfo orgInvestPersonInfo=investPersonInfoService.get(investPersonInfo.getInvestId());
			try{
				BeanUtil.copyNotNullProperties(orgInvestPersonInfo, investPersonInfo);
				investPersonInfoService.save(orgInvestPersonInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 导出投资人列表
	 */
	public void toExcel(){
		QueryFilter filter=new QueryFilter(this.getRequest());
		filter.getPagingBean().setPageSize(100000);
		List<InvestPersonInfo> investList = new ArrayList();
		String params = getRequest().getParameter("Q_moneyPlanId_L_EQ");
		String params1 = getRequest().getParameter("Q_bidPlanId_L_EQ");
		if(null==params&&null==params1){
		}else{
			investList = investPersonInfoService.getAll(filter);
		}
		
		String[] tableHeader = { "序号", "投资人", "投资金额","投资比例(%)" };
		String[] fields = {"investPersonName","investMoney","investPercent"};
		try {
			
			ExportExcel.export(tableHeader, fields, investList,"项目投资列表", InvestPersonInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
