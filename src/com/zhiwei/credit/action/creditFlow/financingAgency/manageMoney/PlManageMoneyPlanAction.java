package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hurong.credit.config.HtmlConfig;
import com.hurong.credit.util.Common;
import com.messageAlert.service.SmsSendService;
import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.Transfer;
import com.zhiwei.credit.service.activity.BpActivityManageService;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyTypeService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderInfoService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.system.BuildHtml2Web;
import com.zhiwei.credit.service.system.ResultWebPmsService;
import com.zhiwei.credit.service.thirdInterface.FuiouService;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.util.JsonUtils;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlManageMoneyPlanAction extends BaseAction{
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private PlManageMoneyTypeService plManageMoneyTypeService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private BuildHtml2Web buildHtml2WebService;
	@Resource
	private ResultWebPmsService resultWebPmsService;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource
	private PlMmOrderChildrenorTestService plMmOrderChildrenorTestService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private FuiouService fuiouService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private PlMmOrderInfoService plMmOrderInfoService;
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private BpActivityManageService bpActivityManageService;
	@Resource 
	private   CreditProjectService creditProjectService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private PlMmObligatoryRightChildrenService plMmObligatoryRightChildrenService;
	@Resource
	private OpraterBussinessDataService opraterBussinessDataService;
	@Resource
	private SmsSendService smsSendService;
	
	SmsSendUtil smsSendUtil=new SmsSendUtil();
	
	public String content;//传到jsp的参数
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	private PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo;
	private PlManageMoneyPlan plManageMoneyPlan;
	
	private Long mmplanId;

	public Long getMmplanId() {
		return mmplanId;
	}

	public void setMmplanId(Long mmplanId) {
		this.mmplanId = mmplanId;
	}

	public PlManageMoneyPlan getPlManageMoneyPlan() {
		return plManageMoneyPlan;
	}

	public void setPlManageMoneyPlan(PlManageMoneyPlan plManageMoneyPlan) {
		this.plManageMoneyPlan = plManageMoneyPlan;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//理财计划过期处理
		QueryFilter filter1 = new QueryFilter();
		filter1.addFilter("Q_keystr_S_EQ", "mmplan");
		filter1.addFilter("Q_state_N_EQ", "1");
		List<PlManageMoneyPlan> updtepList = plManageMoneyPlanService.getAll(filter1);
		for(PlManageMoneyPlan plan:updtepList ){
			if(plan.getBuyEndTime().compareTo(new Date())==-1){
				plan.setState(4);
				plManageMoneyPlanService.save(plan);
				
			}
		}
		//体验标过期处理
		QueryFilter filter2 = new QueryFilter();
		filter2.addFilter("Q_keystr_S_EQ", "experience");
		filter2.addFilter("Q_state_N_EQ", "1");
		List<PlManageMoneyPlan> experienceList = plManageMoneyPlanService.getAll(filter2);
		for(PlManageMoneyPlan plan:experienceList ){
			if(plan.getBuyEndTime().compareTo(new Date())==-1){
				plan.setState(4);
				plManageMoneyPlanService.save(plan);
				
			}
		}
		
		//U计划过期处理
		QueryFilter filter3 = new QueryFilter();
		filter3.addFilter("Q_keystr_S_EQ", "UPlan");
		filter3.addFilter("Q_state_N_EQ", "1");
		List<PlManageMoneyPlan> uPlanList = plManageMoneyPlanService.getAll(filter3);
		for(PlManageMoneyPlan plan:uPlanList ){
			if(plan.getBuyEndTime().compareTo(new Date())==-1){
				plan.setState(4);
				plManageMoneyPlanService.save(plan);
				
			}
		}
		
		String type=this.getRequest().getParameter("type");
		QueryFilter filter=new QueryFilter(getRequest());
		
		//预售查询处理start
		
		String keystr=this.getRequest().getParameter("Q_keystr_S_EQ");
		String saleState=this.getRequest().getParameter("Q_state_N_EQ");
		String isPresale=this.getRequest().getParameter("isPresale");
		if(null !=isPresale && isPresale.equals("dys") && null !=saleState && saleState.equals("1")){//待预售
			filter.addFilter("Q_preSaleTime_D_GT",sd.format(new Date()));
	    }else if(null !=isPresale && isPresale.equals("ysz") && null !=saleState && saleState.equals("1")){//预售中
			filter.addFilter("Q_preSaleTime_D_LT",sd.format(new Date()));
			filter.addFilter("Q_buyStartTime_D_GT",sd.format(new Date()));
		}else if(null !=isPresale && isPresale.equals("zbz") && null !=saleState && saleState.equals("1")){//招标中
			filter.addFilter("Q_buyStartTime_D_LT",sd.format(new Date()));
		}else{
			//其他状态
		}
		//预售查询处理end
		filter.addSorted("buyStartTime", "DESC");
		List<PlManageMoneyPlan> list= plManageMoneyPlanService.getAll(filter);
		List<PlManageMoneyPlan> plmmlist=new ArrayList<PlManageMoneyPlan>();
		//理财产品可购买与查询所有区分
		if(null !=type && !"".equals(type)){
			if(Integer.valueOf(type)==0){
				plmmlist=list;
			}else{
				for(PlManageMoneyPlan plmm : list){
					int days1=DateUtil.getDaysBetweenDate(plmm.getBuyStartTime(),new Date());
					int days2=DateUtil.getDaysBetweenDate(plmm.getBuyEndTime(), new Date());
					if(days1>=0 && days2<=0){
						plmmlist.add(plmm);
					}
				}
			}
		}else{
			plmmlist=list;
		}
		
		
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),new String[]{"buyStartTime","buyEndTime","createtime"});
		buff.append(json.serialize(plmmlist));
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
				plManageMoneyPlanService.remove(new Long(id));
				/*plManageMoneyPlan=plManageMoneyPlanService.get(new Long(id));
				PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(plManageMoneyPlan.getMmplanId());
				if(plManageMoneyPlanBuyinfo==null){
					plManageMoneyPlanService.remove(plManageMoneyPlan);
					jsonString="{success:true,isexist:false}";
				}else{
					
					jsonString="{success:true,isexist:true}";
				}*/
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
		PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
		Map<String, Object> map = new HashMap<String, Object>();
		plManageMoneyPlanService.equals(plManageMoneyPlan);
		plManageMoneyPlan.setPlManageMoneyPlanBuyinfos(null);
		map.put("plManageMoneyPlan", plManageMoneyPlan);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(map));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 理财产品购买流程查询方法
	 */
	public String getTurnoverInfo(){
		String projectId = this.getRequest().getParameter("projectId"); // 项目ID
		String taskId = this.getRequest().getParameter("slTaskId"); // 任务ID
		Map<String, Object> map = new HashMap<String, Object>();
		//理财产品信息
		PlMmOrderInfo plMmOrderInfo=plMmOrderInfoService.get(Long.valueOf(projectId));
		if(null!=plMmOrderInfo){
			PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(Long.valueOf(plMmOrderInfo.getOrderId()));
			if(null!=plManageMoneyPlanBuyinfo){
				map.put("plManageMoneyPlanBuyinfo",plManageMoneyPlanBuyinfo);
			}
		}
		if (null != taskId && !"".equals(taskId)) {
			ProcessForm pform = processFormService.getByTaskId(taskId);
			if (pform == null) {
				pform = creditProjectService.getCommentsByTaskId(taskId);
			}
			if (pform != null && pform.getComments() != null
					&& !"".equals(pform.getComments())) {
				map.put("comments", pform.getComments());
			}
		}
		doJson(map);
		return SUCCESS;
	}
	
	private void doJson(Map<String, Object> map) {
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString = buff.toString();
	}
	/**
	 * 建标。更新标
	 * @param plManageMoneyPlan
	 */
	public String createBid(PlManageMoneyPlan plManageMoneyPlan){
		String msg="";
		BpCustMember bp = bpCustMemberService.getMemberUserName(plManageMoneyPlan.getMoneyReceiver());
		if(bp!=null){
			//建立标的信息
			CommonResponse response = createBidInfo(bp,plManageMoneyPlan);
			if (response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				plManageMoneyPlan.setUmPayBidStatus(Short.valueOf("-1"));
				plManageMoneyPlanService.save(plManageMoneyPlan);
				//更新标的信息
				CommonResponse resp = updateBidInfo(plManageMoneyPlan);
				if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
					plManageMoneyPlan.setUmPayBidStatus(Short.valueOf("0"));
					plManageMoneyPlanService.save(plManageMoneyPlan);
				}
			}else if (response.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)) {
				plManageMoneyPlanService.remove(plManageMoneyPlan);
			}
			msg=response.getResponseMsg();
		}
		return msg;
	}
	/**
	 * 建立标的信息
	 * @param bp
	 * @return
	 */
	public CommonResponse createBidInfo(BpCustMember bp,PlManageMoneyPlan plManageMoneyPlan){
		Calendar sCalendar = Calendar.getInstance();
		sCalendar.add(Calendar.MONTH, Integer.valueOf(plManageMoneyPlan.getInvestlimit()));
		//收款人
		BpCustMember member = bpCustMemberService.getMemberUserName(plManageMoneyPlan.getMoneyReceiver());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String orderNum =ContextUtil.createRuestNumber();//生成第三需要的流水号
		CommonRequst commonRequst=new CommonRequst();
		commonRequst.setRequsetNo(orderNum);
		commonRequst.setBidId(plManageMoneyPlan.getMmplanId().toString());//标的id
		commonRequst.setBidName(plManageMoneyPlan.getMmName());//标的名称
		commonRequst.setBidMoney(plManageMoneyPlan.getSumMoney().setScale(2));//标的金额
		commonRequst.setThirdPayConfigId(bp.getThirdPayFlagId());//借款人
		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
		 
		commonRequst.setYearRate(plManageMoneyPlan.getYeaRate().setScale(2).toString());//年化利率
		commonRequst.setRetInterest(plManageMoneyPlan.getSumMoney().setScale(2).toString());//应还款总利息
		commonRequst.setLastRetDate(format.format(sCalendar.getTime()));//最后还款日期  
		commonRequst.setBidStartDate(sdf.format(plManageMoneyPlan.getBuyStartTime()));//投标开始时间
		commonRequst.setBidEndDate(sdf.format(plManageMoneyPlan.getBuyEndTime()));//投标结束时间
		commonRequst.setLoanPeriod(plManageMoneyPlan.getInvestlimit()+"个月");//借款期限
		commonRequst.setRetDate(format.format(plManageMoneyPlan.getBuyEndTime()));//应还款日期 
		commonRequst.setBorrType(bp.getCustomerType()==0?"01":"02");//借款人类型
		commonRequst.setBorrCustId(member.getThirdPayFlagId());//借款人
		commonRequst.setBorrName(member.getTruename());//借款人姓名/企业名称
		commonRequst.setBorrCertId(member.getCardcode());//借款人身份证号
		commonRequst.setBorrMobiPhone(member.getTelphone());//借款人手机号
		commonRequst.setBussinessType(ThirdPayConstants.BT_CREATEBID);
		commonRequst.setTransferName(ThirdPayConstants.TN_CREATEBID);
		CommonResponse response = ThirdPayInterfaceUtil.thirdCommon(commonRequst);
		return response;
	}
	/**
	 * 更新标的信息
	 * @param bp
	 * @param plManageMoneyPlan
	 * @return
	 */
	public CommonResponse updateBidInfo(PlManageMoneyPlan plManageMoneyPlan){
		String requestNO =ContextUtil.createRuestNumber();//生成第三需要的流水号
		CommonRequst cr = new CommonRequst();
		cr.setRequsetNo(requestNO);
		cr.setBidType(CommonRequst.HRY_PLANBUY);
		cr.setBidId(plManageMoneyPlan.getMmplanId().toString());//标的id
		cr.setBidIdStatus("0");//更改标的状态为开标
		cr.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
		cr.setTransferName(ThirdPayConstants.TN_UPDATEBID);
		CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(cr);
		return resp;
	}
	
	/**
	 * 添加及保存操作
	 * 保存成功后，需要修改购买信息的状态
	 */
	public String save(){
		
		String moneyReceiver= this.getRequest().getParameter("plManageMoneyPlan.moneyReceiver");
		String keystr=this.getRequest().getParameter("keystr");
		String msg="保存失败";
		if(keystr.equals("mmplan")){//保存理财计划
			if(moneyReceiver!=null&&!moneyReceiver.equals("")){
				plManageMoneyPlan.setKeystr(keystr);
				String typeName=plManageMoneyTypeService.get(plManageMoneyPlan.getManageMoneyTypeId()).getName();
				plManageMoneyPlan.setMmName(typeName+"第"+plManageMoneyPlan.getMmNumber()+"期");
				if(plManageMoneyPlan.getMmplanId()==null){
					plManageMoneyPlan.setState(0);
					plManageMoneyPlan.setCreatetime(new Date());
					plManageMoneyPlan.setInvestedMoney(new BigDecimal("0"));
					//如果收款类型为平台账户，派息授权状态默认为是
					if(plManageMoneyPlan.getReceiverType().equals("pt")){
						plManageMoneyPlan.setAuthorityStatus(Short.valueOf("1"));
						plManageMoneyPlanService.save(plManageMoneyPlan);
					}else{
						plManageMoneyPlanService.save(plManageMoneyPlan);
						//建标，更新标
						msg=createBid(plManageMoneyPlan);
					}
				}else{
					PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlan.getMmplanId());
					if(orgPlManageMoneyPlan.getState()>0){
						setJsonString("{success:false,msg:'该招标计划的状态已经改变,不能进行编辑'}");
						return SUCCESS;
					}
					try{
						BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
						orgPlManageMoneyPlan.setUpdatetime(new Date());
						plManageMoneyPlanService.save(orgPlManageMoneyPlan);
						msg="保存成功";
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}
				jsonString = ("{success:true,id:"+plManageMoneyPlan.getMmplanId()+",msg:\""+msg+"\"}");
				return SUCCESS;
			}else{
				setJsonString("{success:true,id:0}");
				return SUCCESS;
			}
		}else if(keystr.equals("mmproduce")){//保存理财产品
			plManageMoneyPlan.setKeystr(keystr);
			String typeName=plManageMoneyTypeService.get(plManageMoneyPlan.getManageMoneyTypeId()).getName();
			plManageMoneyPlan.setMmName(typeName+"第"+plManageMoneyPlan.getMmNumber()+"期");
			if(plManageMoneyPlan.getMmplanId()==null){
				plManageMoneyPlan.setState(0);
				plManageMoneyPlan.setCreatetime(new Date());
				plManageMoneyPlan.setInvestedMoney(new BigDecimal("0"));
				plManageMoneyPlanService.save(plManageMoneyPlan);
				msg="保存成功";
			}else{
				PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlan.getMmplanId());
				try{
					BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
					orgPlManageMoneyPlan.setUpdatetime(new Date());
					plManageMoneyPlanService.save(orgPlManageMoneyPlan);
					msg="保存成功";
					
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
			jsonString = ("{success:true,msg:\""+msg+"\"}");
			return SUCCESS;
		}else{//保存U计划
			if(moneyReceiver!=null&&!moneyReceiver.equals("")){
				plManageMoneyPlan.setKeystr(keystr);
				String typeName=plManageMoneyTypeService.get(plManageMoneyPlan.getManageMoneyTypeId()).getName();
				plManageMoneyPlan.setMmName(typeName+"第"+plManageMoneyPlan.getMmNumber()+"期");
				if(plManageMoneyPlan.getMmplanId()==null){
					plManageMoneyPlan.setState(0);
					plManageMoneyPlan.setCreatetime(new Date());
					plManageMoneyPlan.setInvestedMoney(new BigDecimal("0"));
					plManageMoneyPlanService.save(plManageMoneyPlan);
					//建标，更新标
					msg=createBid(plManageMoneyPlan);
				}else{
					PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlan.getMmplanId());
					if(orgPlManageMoneyPlan.getState()>0){
						setJsonString("{success:true}");
						return SUCCESS;
					}
					try{
						BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
						orgPlManageMoneyPlan.setUpdatetime(new Date());
						plManageMoneyPlanService.save(orgPlManageMoneyPlan);
						msg="保存成功";
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}
				jsonString = ("{success:true,id:"+plManageMoneyPlan.getMmplanId()+",msg:\""+msg+"\"}");
			} else {
				setJsonString("{success:true,id:0}");
			}
			return SUCCESS;
		}
		
		
		
	}
	/**
	 * 理财计划起息方法
	 * @return
	 */
	public String savePlan(){
		String keystr=this.getRequest().getParameter("keystr");
		plManageMoneyPlan.setKeystr(keystr);
		String typeName=plManageMoneyTypeService.get(plManageMoneyPlan.getManageMoneyTypeId()).getName();
		plManageMoneyPlan.setMmName(typeName+"第"+plManageMoneyPlan.getMmNumber()+"期");
		if(plManageMoneyPlan.getMmplanId()==null){
			plManageMoneyPlan.setState(0);
			plManageMoneyPlan.setCreatetime(new Date());
			plManageMoneyPlan.setInvestedMoney(new BigDecimal("0"));
			plManageMoneyPlanService.save(plManageMoneyPlan);
		}else{
			PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlan.getMmplanId());
			
			try{
				List<PlManageMoneyPlanBuyinfo> plList=plManageMoneyPlanBuyinfoService.getListByPlanId(orgPlManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
				int sucessLoanCount=0;
				if(plList!=null&&plList.size()>0){
					for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo :plList){
						plManageMoneyPlanBuyinfo.setStartinInterestTime(plManageMoneyPlan.getStartinInterestTime());
						plManageMoneyPlanBuyinfo.setEndinInterestTime(plManageMoneyPlan.getEndinInterestTime());
						String  payMehor=configMap.get("thirdPayType").toString();
						if(payMehor.equals("1")){
							plManageMoneyPlanBuyinfo.setState((short)2);
							Map<String,Object> map=new HashMap<String,Object>();
							map.put("investPersonId",plManageMoneyPlanBuyinfo.getInvestPersonId());//投资人Id
							map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
							map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
							map.put("money", plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额
							map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
							map.put("DealInfoId",null);//交易记录id，没有默认为null
							map.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber());//交易流水号
							map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
							String[] ret =obAccountDealInfoService.updateAcountInfo(map);
							plManageMoneyPlanBuyinfo.setContractNo(DateUtil.dateToStr(new Date(), "yyyyMMdd")+"_"+plManageMoneyPlanBuyinfo.getOrderId());
							plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
							plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
							plMmOrderAssignInterestService.createAssignInerestlist(plManageMoneyPlanBuyinfo,orgPlManageMoneyPlan);
							plMmOrderChildrenorTestService.matchForecast(plManageMoneyPlanBuyinfo);
							//String[] ret=obAccountDealInfoService.updateAcountInfo(plManageMoneyPlanBuyinfo.getInvestPersonId(), ObAccountDealInfo.T_INVEST.toString(), plManageMoneyPlanBuyinfo.getBuyMoney().toString(), plManageMoneyPlanBuyinfo.getPersionType().toString(), plManageMoneyPlanBuyinfo.getDealInfoNumber(), null, "2");
						}else{
							if(configMap.get("thirdPayConfig").toString().equals(Constants.FUIOU)){
								BpCustMember customer=bpCustMemberService.get(plManageMoneyPlanBuyinfo.getInvestPersonId());
								String amount=plManageMoneyPlanBuyinfo.getBuyMoney().multiply(new BigDecimal(100)).setScale(0).toString();
								String	orderNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
								String[] set=fuiouService.traAcc(orderNum, customer.getThirdPayFlag0(), configMap.get("thirdPay_fuiou_platloginname").toString(),amount, plManageMoneyPlanBuyinfo.getPreAuthorizationNum(), "");
								if(set[0].equals(Constants.CODE_SUCCESS)){
									Map<String,Object> mapf=new HashMap<String,Object>();
									mapf.put("investPersonId",plManageMoneyPlanBuyinfo.getInvestPersonId());//投资人Id
									mapf.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
									mapf.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
									mapf.put("money", plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额
									mapf.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
									mapf.put("DealInfoId",null);//交易记录id，没有默认为null
									mapf.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber());//交易流水号
									mapf.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
									String[] ret =obAccountDealInfoService.updateAcountInfo(mapf);
									sucessLoanCount++;
									// String[] ret=obAccountDealInfoService.updateAcountInfo(plManageMoneyPlanBuyinfo.getInvestPersonId(), ObAccountDealInfo.T_INVEST.toString(), plManageMoneyPlanBuyinfo.getBuyMoney().toString(), ObSystemAccount.type0.toString(), plManageMoneyPlanBuyinfo.getDealInfoNumber(), null,"2" );
										if(ret!=null){
											if(ret[0].equals(Constants.CODE_SUCCESS)){
												plManageMoneyPlanBuyinfo.setState(Short.valueOf("2"));
												plManageMoneyPlanBuyinfo.setContractNo(DateUtil.dateToStr(new Date(), "yyyyMMdd")+"_"+plManageMoneyPlanBuyinfo.getOrderId());
												plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
											}
										}
								 }
							
							}else if(configMap.get("thirdPayConfig").toString().equals(Constants.YEEPAY)){
								//gaomimiinferface起息即放款
								String loginName=orgPlManageMoneyPlan.getMoneyReceiver();
								BpCustMember moneyReciver=bpCustMemberService.getMemberUserName(loginName);
								BpCustMember customer=bpCustMemberService.get(plManageMoneyPlanBuyinfo.getInvestPersonId());
								String amount=plManageMoneyPlanBuyinfo.getBuyMoney().toString();
								String	orderNum=plManageMoneyPlanBuyinfo.getDealInfoNumber();
								List<Transfer> li=new ArrayList<Transfer>();
								Transfer t1=new Transfer();
								t1.setRequestNo(plManageMoneyPlanBuyinfo.getDealInfoNumber());
								t1.setSourcePlatformUserNo(customer.getThirdPayFlagId());
								t1.setSourceUserType("MEMBER");
								t1.setTargetPlatformUserNo(moneyReciver.getThirdPayFlagId());
								t1.setTargetUserType("MEMBER");
								t1.setTransferAmount(plManageMoneyPlanBuyinfo.getBuyMoney().toString());
								li.add(t1);
								Map<String,Object> mapLoan=new HashMap<String,Object>();
								mapLoan.put("basePath",this.getBasePath() );
								mapLoan.put("requestNo",orderNum );
								mapLoan.put("PlatformFee","0");
								mapLoan.put("bidPlanId",orgPlManageMoneyPlan.getMmplanId());
								mapLoan.put("bidPlanType", plManageMoneyPlanBuyinfo.getKeystr());
								mapLoan.put("transfer", li);
								System.out.println("transfer==="+li);
								String[] retValue=yeePayService.loan(mapLoan);
								/*String [] retValue=new String[2];
								retValue[0]=com.zhiwei.core.Constants.CODE_SUCCESS;*/
								if(retValue[0].equals(com.zhiwei.core.Constants.CODE_SUCCESS)){
									
									//判断是否有投标活动设置
//									addbpActivityManage(plManageMoneyPlanBuyinfo);
									
									plManageMoneyPlanBuyinfo.setState((short)2);
									Map<String,Object> map=new HashMap<String,Object>();
									map.put("investPersonId",plManageMoneyPlanBuyinfo.getInvestPersonId());//投资人Id
									map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
									map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
									map.put("money", plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额
									map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
									map.put("DealInfoId",null);//交易记录id，没有默认为null
									map.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber());//交易流水号
									map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
									String[] ret =obAccountDealInfoService.updateAcountInfo(map);
									
									plManageMoneyPlanBuyinfo.setContractNo(DateUtil.dateToStr(new Date(), "yyyyMMdd")+"_"+plManageMoneyPlanBuyinfo.getOrderId());
									plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
									
									plMmOrderAssignInterestService.createAssignInerestlist(plManageMoneyPlanBuyinfo,orgPlManageMoneyPlan);
									
									//plMmOrderChildrenorTestService.matchForecast(plManageMoneyPlanBuyinfo);
									Map<String,Object> mapL=new HashMap<String,Object>();
									mapL.put("investPersonId",moneyReciver.getId());//投资人Id
									mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
									mapL.put("transferType",ObAccountDealInfo.T_INMONEY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
									mapL.put("money", plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额
									mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
									mapL.put("DealInfoId",null);//交易记录id，没有默认为null
									mapL.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber()+"-L");//交易流水号
									mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
									String[] retL =obAccountDealInfoService.operateAcountInfo(mapL);
									sucessLoanCount++;
									//派发奖励
//									checkCoupons(plManageMoneyPlanBuyinfo);
								}
							}
						}
					}
				}
				List<PlManageMoneyPlanBuyinfo> plList2=plManageMoneyPlanBuyinfoService.getListByPlanId(orgPlManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
				if(plList2!=null&&plList2.size()>0){
					BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
					orgPlManageMoneyPlan.setUpdatetime(new Date());
					plManageMoneyPlanService.save(orgPlManageMoneyPlan);
					setJsonString("{success:true,msg:'起息放款部分完成，需放款的投资人个数为："+(plList!=null?plList.size():'0')+";成功放款投资人个数为："+sucessLoanCount+"'}");
				}else{
					orgPlManageMoneyPlan.setState(PlManageMoneyPlan.STATE7);
					BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
					orgPlManageMoneyPlan.setUpdatetime(new Date());
					plManageMoneyPlanService.save(orgPlManageMoneyPlan);
					setJsonString("{success:true,msg:'起息放款全部完成，需放款的投资人个数为："+(plList!=null?plList.size():'0')+";成功放款投资人个数为："+sucessLoanCount+"'}");
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.getMessage());
			}
		}
		
		return SUCCESS;
	}
	/**
	 * 关闭理财计划
	 * @return
	 */
	public String bidClose(){
		String[] set=new String[2];
		List<PlManageMoneyPlanBuyinfo> plList=plManageMoneyPlanBuyinfoService.getListByPlanId(mmplanId);
		if(plList!=null&&plList.size()>0){
			for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo :plList){
				String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				//投资账户
				BpCustMember customer=bpCustMemberService.get(plManageMoneyPlanBuyinfo.getInvestPersonId());
				//收款账户
				BpCustMember moneyReciver=bpCustMemberService.getMemberUserName(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMoneyReceiver());
				String  payMehor=configMap.get("thirdPayType").toString();
				if(plManageMoneyPlanBuyinfo.getState().equals(Short.valueOf("1"))){
					if(payMehor.equals("1")){
						Map<String,Object> mapf=new HashMap<String,Object>();
						mapf.put("investPersonId",plManageMoneyPlanBuyinfo.getInvestPersonId());//投资人Id
						mapf.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
						mapf.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
						mapf.put("money", plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额
						mapf.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
						mapf.put("DealInfoId",null);//交易记录id，没有默认为null
						mapf.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber());//交易流水号
						mapf.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_3);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
						set =obAccountDealInfoService.updateAcountInfo(mapf);
						//String[] ret=obAccountDealInfoService.updateAcountInfo(plManageMoneyPlanBuyinfo.getInvestPersonId(), ObAccountDealInfo.T_INVEST.toString(), plManageMoneyPlanBuyinfo.getBuyMoney().toString(), plManageMoneyPlanBuyinfo.getPersionType().toString(), plManageMoneyPlanBuyinfo.getDealInfoNumber(), null, "3");
					}else{//有第三方托管的第三方支付
						//收款账户为注册用户流标
						if(null !=plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getReceiverType() && 
								plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getReceiverType().equals("zc")){
							CommonRequst cq=new CommonRequst();
							cq.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识
							cq.setLoaner_thirdPayflagId(moneyReciver.getThirdPayFlagId());
							cq.setRequsetNo(orderNum);//请求流水号
							cq.setQueryRequsetNo(plManageMoneyPlanBuyinfo.getDealInfoNumber());//之前投标的请求流水号 
			    			cq.setBussinessType(ThirdPayConstants.BT_MMCANCELUSER);//业务类型
			    			cq.setTransferName(ThirdPayConstants.TN_MMCANCELUSER);//业务名称
			    			cq.setLoanNoList(plManageMoneyPlanBuyinfo.getPreAuthorizationNum());//第三方流水号
			    			cq.setBidId(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmplanId().toString());
			    			cq.setAmount(plManageMoneyPlanBuyinfo.getBuyMoney().add(plManageMoneyPlanBuyinfo.getJoinMoney()));
			    			if(customer.getCustomerType()!=null&&customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
			    				cq.setAccountType(1);
			    			}else{//借款人是个人
			    				cq.setAccountType(0);
			    			}
			    			CommonResponse cr=ThirdPayInterfaceUtil.thirdCommon(cq);
							if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
								set[0]=Constants.CODE_SUCCESS;
								set[1]=cr.getResponseMsg();
							}else{
								set[0]=Constants.CODE_FAILED;
								set[1]=cr.getResponseMsg();
							}
						//收款账户为平台账户流标	
						}else{
							CommonResponse cr=new CommonResponse();
							CommonRequst cq=new CommonRequst();
							cq.setConfimStatus(false);//false表示退回 true表示通过
							cq.setBussinessType(ThirdPayConstants.BT_MMCANCELPLATF);//业务类型
							cq.setTransferName(ThirdPayConstants.TN_MMCANCELPLATF);//业务名称
							cq.setRequsetNo(orderNum);
							cq.setQueryRequsetNo(plManageMoneyPlanBuyinfo.getDealInfoNumber());//请求之前的流水号
							cq.setLoanNoList(plManageMoneyPlanBuyinfo.getPreAuthorizationNum());//还款流水号
							cr=ThirdPayInterfaceUtil.thirdCommon(cq);
							if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
								set[0]=Constants.CODE_SUCCESS;
								set[1]=cr.getResponseMsg();
							}else{
								set[0]=Constants.CODE_FAILED;
								set[1]=cr.getResponseMsg();
							}
							
						}
					}
					
					if(set[0].equals(Constants.CODE_SUCCESS)){
						
						plManageMoneyPlanBuyinfo.setState(Short.valueOf("-2"));
						plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
						
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("investPersonId",customer.getId());//投资人Id
						map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
						map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
						map.put("money",plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额
						map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
						map.put("DealInfoId",null);//交易记录id，没有默认为null
						map.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber());//交易流水号
						map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_8);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
						String[] ret =obAccountDealInfoService.updateAcountInfo(map);
						
						Map<String,Object> mapj=new HashMap<String,Object>();
						mapj.put("investPersonId",customer.getId());//投资人Id
						mapj.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
						mapj.put("transferType",ObAccountDealInfo.T_JOIN);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
						mapj.put("money",plManageMoneyPlanBuyinfo.getJoinMoney());//交易金额
						mapj.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
						mapj.put("DealInfoId",null);//交易记录id，没有默认为null
						mapj.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber()+"TJ");//交易流水号
						mapj.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_8);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
						String[] retj =obAccountDealInfoService.updateAcountInfo(mapj);
						
						//判断是否使用了优惠券
						if(plManageMoneyPlanBuyinfo.getCouponId()!=null&&!plManageMoneyPlanBuyinfo.getCouponId().equals("")){
							BpCoupons coupon = bpCouponsService.get(plManageMoneyPlanBuyinfo.getCouponId());
							coupon.setCouponMoney(new BigDecimal(0));
							coupon.setCouponStatus(Short.valueOf("5"));
							bpCouponsService.save(coupon);
						}
						
						try {
							//流标成功发送短信
							Map<String, String> mapSms = new HashMap<String, String>();
							mapSms.put("telephone", customer.getTelphone());
							mapSms.put("${name}", customer.getTruename());
							mapSms.put("${code}", plManageMoneyPlanBuyinfo.getBuyMoney().toString());
							mapSms.put("${projName}", plManageMoneyPlanBuyinfo.getMmName());
							mapSms.put("key", "sms_flowStandard");
							smsSendService.smsSending(mapSms);
							//smsSendUtil.sms_flowStandard(customer.getTelphone(),customer.getTruename(),plManageMoneyPlanBuyinfo.getBuyMoney().toString(), plManageMoneyPlanBuyinfo.getMmName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else{
					
				}
				
			}
		}
		List<PlManageMoneyPlanBuyinfo> plListStatus=plManageMoneyPlanBuyinfoService.getListByPlanId(mmplanId,Short.valueOf("1"));
		if(plListStatus!=null&&plListStatus.size()>0){
			setJsonString("{success:true,msg:'只是部分退还了投资客户资金，仍有"+plListStatus.size()+"个人没有退还，故不能关闭理财计划'}");
		}else{
			plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
			plManageMoneyPlan.setState(PlManageMoneyPlan.STATE4);
			plManageMoneyPlanService.save(plManageMoneyPlan);
			setJsonString("{success:true,msg:'关闭成功'}");
		}
		return SUCCESS;
	}

	/**
	 * 预览发标信息 isPublish 是否发布 true 发布 false 不发布
	 */
	public String previewPublish() {
		plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
		Boolean isPublish = Boolean.valueOf(this.getRequest().getParameter("isPublish"));
		if(plManageMoneyPlan.getUmPayBidStatus()!=null&&plManageMoneyPlan.getUmPayBidStatus().equals(Short.valueOf("-1"))){
			CommonRequst cr = new CommonRequst();
			cr.setBidType(CommonRequst.HRY_PLANBUY);
			cr.setBidId(plManageMoneyPlan.getMmplanId().toString());
			cr.setBidIdStatus("0");//更改标的状态为开标
			cr.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
			cr.setTransferName(ThirdPayConstants.TN_UPDATEBID);
			CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(cr);
			if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				plManageMoneyPlan.setUmPayBidStatus(Short.valueOf("0"));
				plManageMoneyPlanService.save(plManageMoneyPlan);
				setJsonString("{success:false,msg:'更新标的成功,请再次发布'}");
			}else{
				setJsonString("{success:false,msg:'"+resp.getResponseMsg()+"'}");
			}
		}else if(plManageMoneyPlan.getUmPayBidStatus()!=null&&plManageMoneyPlan.getUmPayBidStatus().equals(Short.valueOf("0"))){
			CommonRequst cr = new CommonRequst();
			cr.setBidType(CommonRequst.HRY_PLANBUY);
			cr.setBidId(plManageMoneyPlan.getMmplanId().toString());
			cr.setBidIdStatus("1");//更改标的状态为投标中
			cr.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
			cr.setTransferName(ThirdPayConstants.TN_UPDATEBID);
			CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(cr);
			if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				plManageMoneyPlan.setUmPayBidStatus(Short.valueOf("1"));
				plManageMoneyPlanService.save(plManageMoneyPlan);
				String htmlFilePath = buildHtml(isPublish);
				// 是否发布
				if (isPublish) {
					plManageMoneyPlan.setState(PlManageMoneyPlan.STATE1);
					plManageMoneyPlanService.save(plManageMoneyPlan);
				}
				setJsonString("{success:true,htmlPath:'" + htmlFilePath + "'}");
			}else{
				setJsonString("{success:true,htmlPath:'" + resp.getResponseMsg() + "'}");
			}
		}else{
			String htmlFilePath = buildHtml(isPublish);
			// 是否发布
			if (isPublish) {
				plManageMoneyPlan.setState(PlManageMoneyPlan.STATE1);
				plManageMoneyPlanService.save(plManageMoneyPlan);
			}
			setJsonString("{success:true,htmlPath:'" + htmlFilePath + "'}");
		}
		return SUCCESS;
	}
	public String buildHtml(boolean isPublish) {
		Map<String, Object> data = buildHtml2WebService.getCommonData();
		String htmlFilePath = "";
		HtmlConfig htmlConfig = null;
		boolean isFirst = false;// 是否已经存在页面
		try {
			plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
		//	plBidPlan = plBidPlanService.get(bidId);
			// 获取动态信息 如 投标进度 投标人数 投标剩余金额
		//	plBidPlan = plBidPlanService.bidDynamic(plBidPlan);
			plManageMoneyPlan=plManageMoneyPlanService.bidDynamic(plManageMoneyPlan);
				htmlConfig = resultWebPmsService.findHtmlCon("mmplanContent");
		//	findPlanProjInfo(data, plBidPlan);
			if (plManageMoneyPlan.getHtmlPath() != null
					&& !plManageMoneyPlan.getHtmlPath().equals("")) {
				htmlFilePath = plManageMoneyPlan.getHtmlPath();
				isFirst = true;
			} else {
				htmlFilePath = htmlConfig.getHtmlFilePath();
			}
			String templateFilePath = htmlConfig.getTemplateFilePath();
			data.put("htmlFilePath", htmlFilePath);
			data.put("templateFilePath", templateFilePath);
			data.put("plan", JsonUtils.getJson(plManageMoneyPlan, JsonUtils.TYPE_OBJ));
			buildHtml2WebService.buildHtml(Constants.BUILDHTML_FORMAT_JSON,
					AppUtil.getWebServiceUrlRs(), "htmlService",
					"signSchemeContentBuildHtml", data);
			if (!isFirst) {
				plManageMoneyPlan.setHtmlPath(htmlFilePath);
				plManageMoneyPlanService.save(plManageMoneyPlan);
			}
			// 是否发布
			if (isPublish) {
				plManageMoneyPlan.setState(PlManageMoneyPlan.STATE1);
		
				plManageMoneyPlanService.save(plManageMoneyPlan);
			}
		} catch (Exception e) {
		}
		return htmlFilePath;
	}
	
	public String streamBid(){
		plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
		plManageMoneyPlan.setState(PlManageMoneyPlan.STATE3);
		List<PlManageMoneyPlanBuyinfo> plList=plManageMoneyPlanBuyinfoService.getListByPlanId(mmplanId);
		if(plList!=null&&plList.size()>0){
			for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo :plList){
				String[] set=null;
				String  payMehor=configMap.get("thirdPayType").toString();
				if(payMehor.equals("1")){
					set=new String[2];
					set[0]=Constants.CODE_SUCCESS;
					set[1]="操作成功";
				}else{//有第三方托管的第三方支付
					if(configMap.get("thirdPayConfig").toString().equals(Constants.FUIOU)){
						BpCustMember customer=bpCustMemberService.get(plManageMoneyPlanBuyinfo.getInvestPersonId());
						String amount=plManageMoneyPlanBuyinfo.getBuyMoney().multiply(new BigDecimal(100)).setScale(0).toString();
						String	orderNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
						set=fuiouService.preAuthRev(orderNum, customer.getThirdPayFlag0(), configMap.get("thirdPay_fuiou_platloginname").toString(), plManageMoneyPlanBuyinfo.getPreAuthorizationNum(), "", this.getRequest());
					}
				}
				if(set!=null&&set[0].equals(Constants.CODE_SUCCESS)){
					plManageMoneyPlanBuyinfo.setState(Short.valueOf("3"));
					plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
					Map<String,Object> mapf=new HashMap<String,Object>();
					mapf.put("investPersonId",plManageMoneyPlanBuyinfo.getInvestPersonId());//投资人Id
					mapf.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
					mapf.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
					mapf.put("money", plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额
					mapf.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
					mapf.put("DealInfoId",null);//交易记录id，没有默认为null
					mapf.put("recordNumber",plManageMoneyPlanBuyinfo.getDealInfoNumber());//交易流水号
					mapf.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_3);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
					String[] ret =obAccountDealInfoService.updateAcountInfo(mapf);
				}
			}
		}
		plManageMoneyPlanService.save(plManageMoneyPlan);
		return SUCCESS;
		
	}
	/**
	 * 理财计划授权平台派息
	 * @return
	 */
	public String autoAuthority(){
		try{
			plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
			String loginName=plManageMoneyPlan.getMoneyReceiver();
			BpCustMember moneyReciver=bpCustMemberService.getMemberUserName(loginName);
			String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
			//针对双乾接口，授权是对账户授权，故不用按标的授权
			CommonResponse cr = new CommonResponse();
			BpCustMember bm = bpCustMemberService.getMemberUserName(plManageMoneyPlan.getMoneyReceiver());
			if(bm.getRefund()!=null && bm.getRefund().equals("1")){
					cr.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			}else{
				CommonRequst cq=new CommonRequst();
				cq.setThirdPayConfigId(moneyReciver.getThirdPayFlagId());//用户支付账号
				cq.setRequsetNo(requestNo);//请求流水号
				cq.setBidId(plManageMoneyPlan.getMmplanId().toString());//标id
				cq.setBussinessType(ThirdPayConstants.BT_MMAUTH);//业务类型
				cq.setTransferName(ThirdPayConstants.TN_MMAUTH);//业务用途
				if(bm.getSecondAudit() != null && bm.getSecondAudit().equals("1")){
					cq.setAuthorizeTypeOpen("2");//开启授权类型
				}else{
					cq.setAuthorizeTypeOpen("2,3");//开启授权类型
				}
				cr=ThirdPayInterfaceUtil.thirdCommon(cq);
			}
			if(CommonResponse.RESPONSECODE_APPLAY.equals(cr.getResponsecode())){
				plManageMoneyPlan.setRequestNo(requestNo);
				plManageMoneyPlanService.save(plManageMoneyPlan);
			}else if(CommonResponse.RESPONSECODE_SUCCESS.equals(cr.getResponsecode())){
				plManageMoneyPlan.setRequestNo(requestNo);
				plManageMoneyPlan.setAuthorityStatus(Short.valueOf("1"));
				plManageMoneyPlanService.save(plManageMoneyPlan);
				setJsonString("{success:true,htmlPath:'授权平台派息成功',flag:0}");
			}else{
				setJsonString("{success:true,htmlPath:'" + cr.getResponseMsg() + "',flag:0}");
				return SUCCESS;
			}
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:true,htmlPath:'系统报错',flag:0}");
			return SUCCESS;
		}
		return SUCCESS;
	}
	public String CheckNumDouble(){
		Long manageMoneyTypeId=Long.valueOf(this.getRequest().getParameter("manageMoneyTypeId"));
		String mmNumber=this.getRequest().getParameter("mmNumber");
		long count=plManageMoneyPlanService.getCountBytypeIdAndNum(manageMoneyTypeId,mmNumber);
		if(count==0){
			setJsonString("{success:true,flag:0}");
		}else{
			setJsonString("{success:true,flag:1}");
		}
		
		return SUCCESS;
	}
	public String updateState(){
		PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
		Integer state=Integer.valueOf(this.getRequest().getParameter("state"));
		if(state==10){
		Set<PlManageMoneyPlanBuyinfo>	list=plManageMoneyPlan.getPlManageMoneyPlanBuyinfos();
		Iterator<PlManageMoneyPlanBuyinfo> iterator=  list.iterator();
		while(iterator.hasNext()){
			PlManageMoneyPlanBuyinfo temp =(PlManageMoneyPlanBuyinfo)iterator.next();
			System.out.println(temp.getState());
			if(!temp.getState().equals(Short.valueOf("10")) && !temp.getState().equals(Short.valueOf("8"))){
				
				setJsonString("{success:true,flag:0}");
				return SUCCESS;
			}
				
			}
		}
		plManageMoneyPlan.setState(state);
		plManageMoneyPlanService.save(plManageMoneyPlan);
		setJsonString("{success:true,flag:1}");
		return SUCCESS;
	}
	public String isCheckMmNumber(){
		String mmNumber=this.getRequest().getParameter("mmNumber");
		long count=plManageMoneyPlanService.isCheckMmNumber(mmNumber);
		if(count==0){
			setJsonString("{success:true,flag:0}");
		}else{
			setJsonString("{success:true,flag:1}");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 添加保存体验标
	 * 
	 */
	public String saveExperience(){
		String keystr=this.getRequest().getParameter("keystr");
		plManageMoneyPlan.setKeystr(keystr);
		if(plManageMoneyPlan.getMmplanId()==null){
			plManageMoneyPlan.setState(0);
			plManageMoneyPlan.setCreatetime(new Date());
	//		plManageMoneyPlan.setStartinInterestTime(plManageMoneyPlan.getBuyStartTime());
	//		plManageMoneyPlan.setEndinInterestTime(DateUtil.addMonthsToDate(plManageMoneyPlan.getBuyStartTime(), plManageMoneyPlan.getInvestlimit()));
			plManageMoneyPlan.setInvestedMoney(new BigDecimal("0"));
			plManageMoneyPlanService.save(plManageMoneyPlan);
		}else{
			PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlan.getMmplanId());
			try{
				BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
				orgPlManageMoneyPlan.setUpdatetime(new Date());
				plManageMoneyPlanService.save(orgPlManageMoneyPlan);
				
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true,id:"+plManageMoneyPlan.getMmplanId()+"}");
		return SUCCESS;
	}
	/**
	 * 理财计划生成投资人奖励明细台账
	 * @return
	 */
	public String createCouponsIntent(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		//理财计划Id
		String mmplanId=this.getRequest().getParameter("mmplanId");
		//起息日
		String startinInterestTime=this.getRequest().getParameter("startinInterestTime");
		//止息日
		String endinInterestTime=this.getRequest().getParameter("endinInterestTime");
		PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(Long.valueOf(mmplanId));
		if(null !=plManageMoneyPlan && !"".equals(plManageMoneyPlan)){
			try {
				plManageMoneyPlan.setStartinInterestTime(format.parse(startinInterestTime));
				plManageMoneyPlan.setEndinInterestTime(format.parse(endinInterestTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			plManageMoneyPlanService.save(plManageMoneyPlan);
		}
		//删除已经生成的奖励台账
		plMmOrderAssignInterestService.deleteCoupons(Long.valueOf(mmplanId),"");
		//成功投标记录
		List<PlManageMoneyPlanBuyinfo> infoList=plManageMoneyPlanBuyinfoService.getListByPlanId(plManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
		
		if(infoList!=null && infoList.size()>0){
			//生成款项台账
			for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo : infoList){
				plManageMoneyPlanBuyinfo.setStartinInterestTime(plManageMoneyPlan.getStartinInterestTime());
				plManageMoneyPlanBuyinfo.setEndinInterestTime(plManageMoneyPlan.getEndinInterestTime());
				plManageMoneyPlanBuyinfo.setIsCreateReward(2);
				//生成投资人的
				//plMmOrderAssignInterestService.createAssignInerestlist(plManageMoneyPlanBuyinfo,plManageMoneyPlan);//
				
				//生成奖励
				plMmOrderAssignInterestService.createAssignCouponsInerestlist(plManageMoneyPlanBuyinfo,plManageMoneyPlan);
			}
		}
		return SUCCESS;
	}
	/**
	 * 检查是否有投标活动
	 * @param bidInfo1
	 */
	/*private void addbpActivityManage(PlManageMoneyPlanBuyinfo bidInfo1){
		BpCustMember mem=null;
		//判断是否是第一次投标
    	QueryFilter fit = new QueryFilter(this.getRequest());
    	fit.addFilter("Q_userId_L_EQ", bidInfo1.getInvestPersonId().toString());
    	fit.addFilter("Q_state_SN_EQ", "2");
    	List<PlBidInfo> infoList = plBidInfoService.getAll(fit);
    	//判断是否是第一次购买理财计划
    	QueryFilter filter = new QueryFilter(this.getRequest());
    	filter.addFilter("Q_investPersonId_L_EQ", bidInfo1.getInvestPersonId().toString());
    	filter.addFilter("Q_state_SN_EQ", "2");
    	List<PlManageMoneyPlanBuyinfo> buyList = plManageMoneyPlanBuyinfoService.getAll(filter);
    	 //判断是否是推荐的用户
		 BpCustMember custMem=bpCustMemberService.get(bidInfo1.getInvestPersonId());
		 if(custMem.getRecommandPerson()!=null&&!custMem.getRecommandPerson().equals("")){
			 mem = bpCustMemberService.isRecommandPerson(custMem.getRecommandPerson());
		 }
		 if(infoList.size()>0||buyList.size()>0){
			 bpActivityManageService.autoDistributeEngine("3", bidInfo1.getInvestPersonId().toString(),bidInfo1.getBuyMoney());
		 }else{
			//投标
			 bpActivityManageService.autoDistributeEngine("3", bidInfo1.getInvestPersonId().toString(),bidInfo1.getBuyMoney());
			 //首投
			 bpActivityManageService.autoDistributeEngine("8", bidInfo1.getInvestPersonId().toString(),bidInfo1.getBuyMoney());
			 if(mem!=null){
				//邀请好友第一次投标
				bpActivityManageService.autoDistributeEngine("5", mem.getId().toString(),bidInfo1.getBuyMoney());
			 }
		 }
		 if(mem!=null){
			 //累计推荐投资
			 bpActivityManageService.autoDistributeEngine("9", bidInfo1.getInvestPersonId().toString(),null);
		 }
		 //累计投资
		 bpActivityManageService.autoDistributeEngine("6", bidInfo1.getInvestPersonId().toString(),null);
	}*/
	/**
	 * 优惠券派发奖励
	 * @param plList
	 */
	/*public void checkCoupons(PlManageMoneyPlanBuyinfo bidInfo1){
			PlManageMoneyPlan bidplan = bidInfo1.getPlManageMoneyPlan();
			//判断此标是否可用优惠券
			if(bidplan.getCoupon()!=null&&bidplan.getCoupon().compareTo(1)==0){
				//判断返利方式是否是 立还
				if(bidplan.getRebateWay().compareTo(1)==0){
					List<PlMmOrderAssignInterest> bpfundInterestList=null;//利息
					List<PlMmOrderAssignInterest> bpfundPrincipalList=null;//本金
					String transferType="";//资金类型
					//判断是否使用了优惠券，派发金额
					if(bidInfo1.getCouponId()!=null&&!bidInfo1.getCouponId().equals("")){
						//判断 返利类型
						if(bidplan.getRebateType().compareTo(1)==0){//返现 principalCoupons
							transferType=ObAccountDealInfo.T_BIDRETURN_RETURNRATIO;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo1.getOrderId(), bidInfo1.getInvestPersonId(), bidplan.getMmplanId(), "'principalCoupons'",null);
						}else if(bidplan.getRebateType().compareTo(2)==0){//返息 couponInterest
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE27;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo1.getOrderId(), bidInfo1.getInvestPersonId(), bidplan.getMmplanId(), "'couponInterest'",null);
						}else if(bidplan.getRebateType().compareTo(3)==0){//返息现  principalCoupons couponInterest
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE29;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo1.getOrderId(), bidInfo1.getInvestPersonId(), bidplan.getMmplanId(), "'couponInterest'",null);
							bpfundPrincipalList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo1.getOrderId(), bidInfo1.getInvestPersonId(), bidplan.getMmplanId(), "'principalCoupons'",null);
						}else if(bidplan.getRebateType().compareTo(4)==0){//加息 couponInterest
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE30;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo1.getOrderId(), bidInfo1.getInvestPersonId(), bidplan.getMmplanId(), "'subjoinInterest'",null);
						}
					}
					if(bpfundInterestList!=null){//返利息
						couponIntent(bpfundInterestList,bidInfo1,bidplan,transferType);
					}
					if(bpfundPrincipalList!=null){//返本金
						couponIntent(bpfundPrincipalList,bidInfo1,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE28);
					}
				}
				
				//判断是否使用了优惠券，更新优惠券
				if(bidInfo1.getCouponId()!=null&&!bidInfo1.getCouponId().equals("")){
						BpCoupons coupon = bpCouponsService.get(bidInfo1.getCouponId());
						coupon.setCouponStatus(Short.valueOf("10"));
						coupon.setUseProjectName(bidplan.getMmName());
						coupon.setUseProjectNumber(bidplan.getMmNumber());
						coupon.setUseProjectType(bidplan.getKeystr());
						coupon.setUseTime(new Date());
						bpCouponsService.save(coupon);
				}
				
			}else{
				//判断此标是否设置普通加息
				//普通加息日化利率，判断是否是立还
				if(bidplan.getAddRate()!=null&&!bidplan.getAddRate().equals("")&&bidplan.getAddRate().compareTo(new BigDecimal(0))!=0){
					if(bidplan.getRebateWay().compareTo(1)==0){
						List<PlMmOrderAssignInterest> bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo1.getOrderId(), bidInfo1.getInvestPersonId(), bidplan.getMmplanId(), "'commoninterest'",null);
						couponIntent(bpfundInterestList,bidInfo1,bidplan,ObAccountDealInfo.T_BIDRETURN_ADDRATE);
					}
					
				}
			}
			
			//判断此标是否设置了募集期利率
			if(bidplan.getRaiseRate()!=null&&!bidplan.getRaiseRate().equals("")){
				List<PlMmOrderAssignInterest> raiseinterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo1.getOrderId(), bidInfo1.getInvestPersonId(), bidplan.getMmplanId(), "'raiseinterest'",null);
				couponIntent(raiseinterestList,bidInfo1,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE31);
			}
	}*/
	/**
	 * 派发优惠券奖励
	 * @param bp
	 * @param info
	 */
	/*private void couponIntent(List<PlMmOrderAssignInterest> bp,PlManageMoneyPlanBuyinfo bidInfo1,PlManageMoneyPlan bidplan,String transferType){
		for(PlMmOrderAssignInterest bpfund:bp){
			if(bpfund.getFactDate()==null||bpfund.getFactDate().equals("")){
				BpCustMember mem=bpCustMemberService.get(bpfund.getInvestPersonId());
				String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst commonRequest = new CommonRequst();
				commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方标识
				commonRequest.setRequsetNo(requestNo);//请求流水号
				commonRequest.setAmount(bpfund.getIncomeMoney());//交易金额
				commonRequest.setCustMemberId(mem.getId().toString());
				commonRequest.setCustMemberType("0");
				commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);//业务类型
				commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);//业务名称
				commonRequest.setBidId(bpfund.getAssignInterestId().toString());
				commonRequest.setBidType(PlBidPlan.HRY_PLANRED);
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
				System.out.println("返现result="+commonResponse.getResponseMsg());
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					//添加资金明细
					Map<String,Object> map3=new HashMap<String,Object>();
					map3.put("investPersonId",mem.getId());//投资人Id（必填）
					map3.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
					map3.put("transferType",transferType);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
					map3.put("money",bpfund.getIncomeMoney());//交易金额	（必填）			 
					map3.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
					map3.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
					map3.put("recordNumber",requestNo);//交易流水号	（必填）
					map3.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
					obAccountDealInfoService.operateAcountInfo(map3);
					
					//更新款项
					bpfund.setAfterMoney(bpfund.getIncomeMoney());
					bpfund.setFactDate(new Date());
					plMmOrderAssignInterestService.save(bpfund);
				}
			}
			
		}
	}*/
	/*
	 * 生成体验标款项台账
	 */
	public String createAssigninterest(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		//体验标Id
		String mmplanId=this.getRequest().getParameter("mmplanId");
		//起息日
		String startinInterestTime=this.getRequest().getParameter("startinInterestTime");
		//止息日
		String endinInterestTime=this.getRequest().getParameter("endinInterestTime");
		//体验标详情
		PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(Long.valueOf(mmplanId));
		if(null !=plManageMoneyPlan && !"".equals(plManageMoneyPlan)){
			try {
				plManageMoneyPlan.setStartinInterestTime(format.parse(startinInterestTime));
				plManageMoneyPlan.setEndinInterestTime(format.parse(endinInterestTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			plManageMoneyPlanService.save(plManageMoneyPlan);
		}
		//成功投标记录
		List<PlManageMoneyPlanBuyinfo> infoList=plManageMoneyPlanBuyinfoService.getListByPlanId(plManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
		if(infoList!=null && infoList.size()>0){
			//删除未对账的款项台账
			List<PlMmOrderAssignInterest> assignInterestList=plMmOrderAssignInterestService.listByMmPlanId(plManageMoneyPlan.getMmplanId(), plManageMoneyPlan.getKeystr());
			if(assignInterestList!=null && assignInterestList.size()>0){
				for(PlMmOrderAssignInterest assignInterest:assignInterestList){
					plMmOrderAssignInterestService.remove(assignInterest);
				}
			}
			//生成款项台账
			for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo : infoList){
				plManageMoneyPlanBuyinfo.setStartinInterestTime(plManageMoneyPlan.getStartinInterestTime());
				plManageMoneyPlanBuyinfo.setEndinInterestTime(plManageMoneyPlan.getEndinInterestTime());
				plMmOrderAssignInterestService.createExperienceAssignInerestlist(plManageMoneyPlanBuyinfo,plManageMoneyPlan);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 体验标起息方法
	 * @return
	 */
	public String startExperience(){
		String keystr=this.getRequest().getParameter("keystr");
		plManageMoneyPlan.setKeystr(keystr);
		if(plManageMoneyPlan.getMmplanId()==null){
			plManageMoneyPlan.setState(0);
			plManageMoneyPlan.setCreatetime(new Date());
			plManageMoneyPlan.setInvestedMoney(new BigDecimal("0"));
			plManageMoneyPlanService.save(plManageMoneyPlan);
		}else{
			PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlan.getMmplanId());
			try{
				List<PlManageMoneyPlanBuyinfo> plList=plManageMoneyPlanBuyinfoService.getListByPlanId(orgPlManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
				int sucessLoanCount=0;
				if(plList!=null&&plList.size()>0){
					for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo :plList){
						plManageMoneyPlanBuyinfo.setStartinInterestTime(plManageMoneyPlan.getStartinInterestTime());
						plManageMoneyPlanBuyinfo.setEndinInterestTime(plManageMoneyPlan.getEndinInterestTime());
						plManageMoneyPlanBuyinfo.setState((short)2);
						plManageMoneyPlanBuyinfo.setContractNo(DateUtil.dateToStr(new Date(), "yyyyMMdd")+"_"+plManageMoneyPlanBuyinfo.getOrderId());
						plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
						
						//修改优惠券状态
						BpCoupons bpCoupons=bpCouponsService.get(plManageMoneyPlanBuyinfo.getCouponId());
						bpCoupons.setCouponStatus(BpCoupons.COUPONSTATUS10);
						bpCouponsService.save(bpCoupons);
					}
					//修改款项台账状态，生效
					List<PlMmOrderAssignInterest> assignInterestList=plMmOrderAssignInterestService.listByMmPlanId(plManageMoneyPlan.getMmplanId(), plManageMoneyPlan.getKeystr());
					if(assignInterestList!=null && assignInterestList.size()>0){
						for(PlMmOrderAssignInterest assignInterest:assignInterestList){
							assignInterest.setIsCheck(Short.valueOf("0"));
							assignInterest.setIsValid(Short.valueOf("0"));
							plMmOrderAssignInterestService.save(assignInterest);
						}
					}
				}
				List<PlManageMoneyPlanBuyinfo> plList2=plManageMoneyPlanBuyinfoService.getListByPlanId(orgPlManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
				if(plList2!=null&&plList2.size()>0){
					BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
					orgPlManageMoneyPlan.setUpdatetime(new Date());
					plManageMoneyPlanService.save(orgPlManageMoneyPlan);
					setJsonString("{success:true,msg:'起息放款部分完成，需放款的投资人个数为："+(plList!=null?plList.size():'0')+";成功放款投资人个数为："+sucessLoanCount+"'}");
				}else{
					orgPlManageMoneyPlan.setState(PlManageMoneyPlan.STATE7);
					BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
					orgPlManageMoneyPlan.setUpdatetime(new Date());
					plManageMoneyPlanService.save(orgPlManageMoneyPlan);
					setJsonString("{success:true,msg:'起息放款全部完成，需放款的投资人个数为："+(plList!=null?plList.size():'0')+";成功放款投资人个数为："+sucessLoanCount+"'}");
				}
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.getMessage());
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 显示体验标列表
	 */
	public String listExperience(){
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlManageMoneyPlan> list= plManageMoneyPlanService.getAllPlbuyInfo(pb,map);
		List<PlManageMoneyPlan> listCount= plManageMoneyPlanService.getAllPlbuyInfo(null,map);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(listCount!=null?listCount.size():0).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"buyStartTime","buyEndTime","createtime","startinInterestTime",
			"intentDate","factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println(buff.toString());
		return SUCCESS;
	}
	
	/**
	 * 体验标发布
	 */
	public String previewPublishExperience() {
		Boolean isPublish = Boolean.valueOf(this.getRequest().getParameter("isPublish"));
		String mmplanId=this.getRequest().getParameter("mmplanId");
		QueryFilter filter=new QueryFilter();
		filter.addFilter("Q_keystr_S_EQ", "experience");
		filter.addFilter("Q_state_N_EQ", 1);
		List<PlManageMoneyPlan> list= plManageMoneyPlanService.getAll(filter);
		if(null !=list && list.size()>0){
			setJsonString("{success:false,msg:'有 招标中 的标的，不能发布新的标的'}");
		}else{
			// 是否发布
			if (isPublish) {
				PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(Long.valueOf(mmplanId));
				plManageMoneyPlan.setState(PlManageMoneyPlan.STATE1);
				plManageMoneyPlanService.save(plManageMoneyPlan);
			}
			setJsonString("{success:true,msg:'发布成功！'}");
		}
		return SUCCESS;
	}
	
	
	public String bidCloseExperience(){
		PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(mmplanId);
		Set<PlManageMoneyPlanBuyinfo>	list=plManageMoneyPlan.getPlManageMoneyPlanBuyinfos();
		if(null !=list && list.size()>0){
			setJsonString("{success:true,msg:'已有投资人投标，不能关闭！'}");
		}else{
			plManageMoneyPlan.setState(PlManageMoneyPlan.STATE4);
			plManageMoneyPlanService.save(plManageMoneyPlan);
			setJsonString("{success:true,msg:'关闭成功！'}");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除U计划
	 * @return
	 */
	public String multiDelUPlan(){
		
		String mmplanId=getRequest().getParameter("mmplanId");
		if(mmplanId!=null){
			PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(Long.valueOf(mmplanId));
			if(plManageMoneyPlan.getState() == 0){
				plManageMoneyPlanService.remove(new Long(mmplanId));
				jsonString="{success:true,msg:'删除成功！'}";
			}else{
				jsonString="{success:true,msg:'已经发布，不能删除！'}";
			}
		}else{
			jsonString="{success:true,msg:'删除失败！'}";
		}
		return SUCCESS;
	}
	
	/**
	 * 理财计划起息方法
	 * @return
	 */
	public String startUPlan(){
		PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlan.getMmplanId());
		try{
			BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
			List<PlManageMoneyPlanBuyinfo> plList=plManageMoneyPlanBuyinfoService.getListByPlanId(orgPlManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
			int sucessLoanCount=0;
			if(plList!=null&&plList.size()>0){
				if(null !=orgPlManageMoneyPlan.getReceiverType() 
						&& orgPlManageMoneyPlan.getReceiverType().equals("pt")){//平台账户收款
					this.ptzhLoan(orgPlManageMoneyPlan, plList);
				}else{//注册账户收款
					this.zczhLoan(orgPlManageMoneyPlan,plList);
				}
			}
			List<PlManageMoneyPlanBuyinfo> plList2=plManageMoneyPlanBuyinfoService.getListByPlanId(orgPlManageMoneyPlan.getMmplanId(),Short.valueOf("1"));
			if(plList2!=null&&plList2.size()>0){
				BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
				orgPlManageMoneyPlan.setUpdatetime(new Date());
				plManageMoneyPlanService.save(orgPlManageMoneyPlan);
				setJsonString("{success:true,msg:'起息放款部分完成，需放款的投资人个数为："+(plList!=null?plList.size():'0')+";成功放款投资人个数为："+(plList!=null?plList.size():'0')+"'}");
			}else{
				orgPlManageMoneyPlan.setState(PlManageMoneyPlan.STATE7);
				BeanUtil.copyNotNullProperties(orgPlManageMoneyPlan, plManageMoneyPlan);
				orgPlManageMoneyPlan.setUpdatetime(new Date());
				plManageMoneyPlanService.save(orgPlManageMoneyPlan);
				setJsonString("{success:true,msg:'起息放款全部完成，需放款的投资人个数为："+(plList!=null?plList.size():'0')+";成功放款投资人个数为："+(plList!=null?plList.size():'0')+"'}");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	
	return SUCCESS;
	}
	/**
	 * 注册账户起息方法
	 * @param orgPlManageMoneyPlan
	 * @param plManageMoneyPlanBuyinfo
	 * @param sucessLoanCount
	 * @return
	 */
	public void zczhLoan(PlManageMoneyPlan orgPlManageMoneyPlan,List<PlManageMoneyPlanBuyinfo> plist){
		//放款总记录条数
		int sumindex = plist.size();
		//操作的记录条数
		int successSum = 0;
		//已操作放款的金额
		BigDecimal loanMoney = new BigDecimal(0);
		//放款总金额
		BigDecimal sumMoney = new BigDecimal(0);
		//资金收入方向
		String loanType="";
		Object[] thirdType = new Object[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String loginName=orgPlManageMoneyPlan.getMoneyReceiver();
		BpCustMember moneyReciver=bpCustMemberService.getMemberUserName(loginName);
		CommonResponse commonResponse = null;
		String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
		List<CommonRequestInvestRecord> li=new ArrayList<CommonRequestInvestRecord>();
		BigDecimal fee = new BigDecimal(0);
		String loanNo = "";
		BigDecimal totalMoney = new BigDecimal(0);
		//判断是单个起息还是批量起息
		thirdType = ThirdPayInterfaceUtil.checkThirdType(ThirdPayConstants.BT_MMLOANUSER,null);
		Date date = new Date();
		CommonRequst common=new CommonRequst();
		common.setRequsetNo(orderNum);//流水号
		common.setThirdPayConfigId(moneyReciver.getThirdPayFlagId());//借款人第三方标识
		common.setBidId(orgPlManageMoneyPlan.getMmplanId().toString());//标id
		common.setLoanNoList(loanNo);//还款流水号
		common.setBussinessType(ThirdPayConstants.BT_MMLOANUSER);//业务类型
		common.setTransferName(ThirdPayConstants.TN_MMLOANUSER);//业务用途
		common.setTransactionTime(date);
		if(moneyReciver.getCustomerType()!=null&&moneyReciver.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
			common.setAccountType(1);
		}else{//借款人是个人
			common.setAccountType(0);
		}
		for(PlManageMoneyPlanBuyinfo plinfo : plist){
			plinfo.setStartinInterestTime(orgPlManageMoneyPlan.getStartinInterestTime());
			plinfo.setEndinInterestTime(orgPlManageMoneyPlan.getEndinInterestTime());
			sumMoney = sumMoney.add(plinfo.getBuyMoney());
			if(thirdType[0].toString().equals("one")){//单个起息
				BpCustMember customer = bpCustMemberService.get(plinfo.getInvestPersonId());
				String	orderoneNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				common.setRequsetNo(orderoneNum);//流水号
				common.setAmount(new BigDecimal(plinfo.getBuyMoney().add(plinfo.getJoinMoney()).toString()));
				common.setFee(plinfo.getJoinMoney());
				common.setSubOrdId(plinfo.getDealInfoNumber());//投标时的流水号
				common.setConfimStatus(false);//审核状态
				common.setSubOrdDate(sdf.format(plinfo.getBuyDatetime()));//投标时间 
				common.setLoaner_thirdPayflagId(moneyReciver.getThirdPayFlagId());
				common.setThirdPayConfigId(customer.getThirdPayFlagId());
				common.setFreezeTrxId(plinfo.getPreAuthorizationNum());//解冻标识
				commonResponse=ThirdPayInterfaceUtil.thirdCommon(common);
				List<PlManageMoneyPlanBuyinfo> buyinfo = new ArrayList<PlManageMoneyPlanBuyinfo>();
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					loanMoney = loanMoney.add(plinfo.getBuyMoney());
					buyinfo.add(plinfo);
				}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)){
					loanMoney = loanMoney.subtract(plinfo.getBuyMoney());
				}
				successSum = successSum+1;
				//已全部起息完成
				if(successSum==sumindex){
					loanType="finish";
				}else{
					loanType="unfinished";
				}
				opraterBussinessDataService.plmmBussiness(buyinfo,orgPlManageMoneyPlan,moneyReciver,commonResponse,loanType,loanMoney);
				
			}else{//批量起息
				BpCustMember customer=bpCustMemberService.get(plinfo.getInvestPersonId());
				CommonRequestInvestRecord t1=new CommonRequestInvestRecord();
				t1.setRequestNo(plinfo.getDealInfoNumber());
				t1.setInvest_thirdPayConfigId(customer.getThirdPayFlagId());
				t1.setInvestCusterType("MEMBER");
				t1.setLoaner_thirdPayConfigId(moneyReciver.getThirdPayFlagId());
				t1.setLoanerCusterType("MEMBER");
				t1.setAmount(new BigDecimal(plinfo.getBuyMoney().add(plinfo.getJoinMoney()).toString()));
				t1.setFee(plinfo.getJoinMoney());
				t1.setPreAuthorizationNum(plinfo.getPreAuthorizationNum());
				li.add(t1);
				fee=fee.add(plinfo.getJoinMoney());
				if("".equals(loanNo)){
					loanNo = plinfo.getPreAuthorizationNum();
				}else{	
					loanNo = loanNo + "," + plinfo.getPreAuthorizationNum();
				}
				totalMoney=totalMoney.add(plinfo.getBuyMoney());
			}
		}
		if(thirdType[0].toString().equals("one")){//单个起息
			
		}else{
			common.setAmount(totalMoney);//联动放款需要总金额
			common.setLoanList(li);//还款集合
			commonResponse=ThirdPayInterfaceUtil.thirdCommon(common);
			if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
				String	orderNum2=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst cr = new CommonRequst();
				cr.setRequsetNo(orderNum2);//流水号
				cr.setBidId(orgPlManageMoneyPlan.getMmplanId().toString());//标id
				cr.setBidType(CommonRequst.HRY_PLANBUY);
				cr.setThirdPayConfigId(moneyReciver.getThirdPayFlagId());
				cr.setBussinessType(ThirdPayConstants.BT_PLATEFORMRECHAGE);//业务类型
				cr.setTransferName(ThirdPayConstants.TN_PLATEFORMRECHAGE);//业务用途
				cr.setAmount(fee);
				if(moneyReciver.getCustomerType()!=null&&moneyReciver.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
					cr.setAccountType(1);
				}else{//借款人是个人
					cr.setAccountType(0);
				}
				CommonResponse cResponse=ThirdPayInterfaceUtil.thirdCommon(cr);
				//起息后业务处理
				if(commonResponse.getSuccessLoanNo()!=null&&!commonResponse.getSuccessLoanNo().equals("")){
					List<PlManageMoneyPlanBuyinfo> buyInfo = plManageMoneyPlanBuyinfoService.getPreAuthorizationNum(commonResponse.getSuccessLoanNo());
					opraterBussinessDataService.plmmBussiness(buyInfo,orgPlManageMoneyPlan,moneyReciver,cResponse,"finish",sumMoney);
				}else{
					opraterBussinessDataService.plmmBussiness(plist,orgPlManageMoneyPlan,moneyReciver,cResponse,"finish",sumMoney);
				}
				//修改标的状态
				CommonRequst cr2 = new CommonRequst();
				cr2.setBidType(CommonRequst.HRY_PLANBUY);
				cr2.setBidId(orgPlManageMoneyPlan.getMmplanId().toString());
				cr2.setBidIdStatus("2");//更改标的状态为还款中
				cr2.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
				cr2.setTransferName(ThirdPayConstants.TN_UPDATEBID);
				CommonResponse commonResponse2 = ThirdPayInterfaceUtil.thirdCommon(cr2);
				if (commonResponse2.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
					orgPlManageMoneyPlan.setUmPayBidStatus(Short.valueOf("2"));
					plManageMoneyPlanService.merge(orgPlManageMoneyPlan);
				}
			}
		}
	}
	/**
	 * 平台账户起息方法
	 * @param orgPlManageMoneyPlan
	 * @param plManageMoneyPlanBuyinfo
	 * @param sucessLoanCount
	 * @return
	 */
	public void ptzhLoan(PlManageMoneyPlan orgPlManageMoneyPlan,List<PlManageMoneyPlanBuyinfo> plist){
		List<CommonRequestInvestRecord> li=new ArrayList<CommonRequestInvestRecord>();
		for(PlManageMoneyPlanBuyinfo plinfo : plist){
			String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
			plinfo.setStartinInterestTime(plManageMoneyPlan.getStartinInterestTime());
			plinfo.setEndinInterestTime(plManageMoneyPlan.getEndinInterestTime());
			BpCustMember customer=bpCustMemberService.get(plinfo.getInvestPersonId());
			CommonRequst commonRequest = new CommonRequst();
			commonRequest.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识
			commonRequest.setRequsetNo(orderNum);//请求流水号
			commonRequest.setAmount(plinfo.getBuyMoney().add(plinfo.getJoinMoney()));//交易金额
			commonRequest.setCustMemberType("0");
			commonRequest.setLoanNoList(plinfo.getPreAuthorizationNum());//还款流水号
			commonRequest.setBidId(plinfo.getOrderId().toString());
			commonRequest.setBussinessType(ThirdPayConstants.BT_MMLOANPLATF);//业务类型
			commonRequest.setTransferName(ThirdPayConstants.TN_MMLOANPLATF);//业务用途
			CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
			
			if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				
				//判断是否有投标活动设置
				bpActivityManageService.addbpActivityManage(plinfo.getInvestPersonId(), plinfo.getBuyMoney(),plinfo.getBuyDatetime());
				
				plinfo.setState((short)2);
				if(plinfo.getKeystr().equals("UPlan")){//U计划默认为托管模式
					plinfo.setIsAtuoMatch(1);//默认托管模式，可进行自动债权匹配
				}else{
					plinfo.setIsAtuoMatch(0);//默认为非托管模式，不可进行自动债权匹配
				}
				//增加投资人流水
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("investPersonId",plinfo.getInvestPersonId());//投资人Id
				map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				map.put("money", plinfo.getBuyMoney());//交易金额
				map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				map.put("DealInfoId",null);//交易记录id，没有默认为null
				map.put("recordNumber",plinfo.getDealInfoNumber());//交易流水号
				map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
				String[] ret =obAccountDealInfoService.updateAcountInfo(map);
				
				//更新加入费用资金流水记录
				Map<String,Object> mapUPlan=new HashMap<String,Object>();
				mapUPlan.put("investPersonId",plinfo.getInvestPersonId());//投资人Id
				mapUPlan.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				mapUPlan.put("transferType",ObAccountDealInfo.T_JOIN);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				mapUPlan.put("money", plinfo.getJoinMoney());//加入费用
				mapUPlan.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				mapUPlan.put("DealInfoId",null);//交易记录id，没有默认为null
				mapUPlan.put("recordNumber",plinfo.getDealInfoNumber()+"TJ");//交易流水号
				mapUPlan.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
				obAccountDealInfoService.updateAcountInfo(mapUPlan);
				
				plinfo.setContractNo(DateUtil.dateToStr(new Date(), "yyyyMMdd")+"_"+plinfo.getOrderId());
				plManageMoneyPlanBuyinfoService.save(plinfo);
				//生成款项台账
				plMmOrderAssignInterestService.createUPlanAssignInerestlist(plinfo,orgPlManageMoneyPlan);
				
				//派发奖励
				plManageMoneyPlanService.checkCoupons(plinfo);
			}
		}
	}
	/**
	 * 理财计划派息是验证是否授权平台
	 */
	public String isAuthority(){
		Boolean flag=true;
		String [] mids=this.getRequest().getParameterValues("mids");
		if(null!=mids && !"".equals(mids)){
			for(String mid :mids){
				PlManageMoneyPlan orgPlManageMoneyPlan=plManageMoneyPlanService.get(Long.valueOf(mid));	
				if(null!=orgPlManageMoneyPlan){
					if(null==orgPlManageMoneyPlan.getAuthorityStatus() || orgPlManageMoneyPlan.getAuthorityStatus()!=1){
						flag=false;
						break;
					}
				}
			}
		}
		StringBuffer buff = new StringBuffer("{success:true");
		buff.append(",flag:'"+flag+"'");
		buff.append("}");
		jsonString=buff.toString();
		return  SUCCESS;
	}
	
	
	/**
	 * 显示列表
	 */
	public String listByKeystr(){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//D计划过期处理
		QueryFilter filter1 = new QueryFilter();
		filter1.addFilter("Q_keystr_S_EQ", "mmplan");
		filter1.addFilter("Q_state_N_EQ", "1");
		List<PlManageMoneyPlan> updtepList = plManageMoneyPlanService.getAll(filter1);
		for(PlManageMoneyPlan plan:updtepList ){
			if(plan.getBuyEndTime().compareTo(new Date())==-1){
				plan.setState(4);
				plManageMoneyPlanService.save(plan);
				
			}
		}
		
		//U计划过期处理
		QueryFilter filter3 = new QueryFilter();
		filter3.addFilter("Q_keystr_S_EQ", "UPlan");
		filter3.addFilter("Q_state_N_EQ", "1");
		List<PlManageMoneyPlan> uPlanList = plManageMoneyPlanService.getAll(filter3);
		for(PlManageMoneyPlan plan:uPlanList ){
			if(plan.getBuyEndTime().compareTo(new Date())==-1){
				plan.setState(4);
				plManageMoneyPlanService.save(plan);
				
			}
		}
		
		QueryFilter filter=new QueryFilter(getRequest());
		String buttonType=this.getRequest().getParameter("buttonType");
		if(buttonType.equals("1")){//计划招标发布
			filter.addFilterIn("Q_state_N_IN", Arrays.asList(0));
	    }else if(buttonType.equals("2")){//理财计划关闭
			filter.addFilterIn("Q_state_N_IN", Arrays.asList(0,1,2,4));
		}else if(buttonType.equals("3")){//手动起息办理
			filter.addFilterIn("Q_state_N_IN", Arrays.asList(2,4));
		}else if(buttonType.equals("4")){//派息还款授权(只查询收款账户是注册用户)
			filter.addFilterIn("Q_state_N_IN", Arrays.asList(1,2,4,7));
			filter.addFilter("Q_receiverType_S_EQ", "zc");
			filter.addFilter("Q_investedMoney_BD_GT", 0);
		}else if(buttonType.equals("7")){//理财结清管理
			filter.addFilterIn("Q_state_N_IN", Arrays.asList(7));
		}else if(buttonType.equals("8")){//全部理财计划查询
			filter.addFilterIn("Q_state_N_IN", Arrays.asList(1,2,4,-2,-1,7,10));
		}
		filter.addSorted("buyStartTime", "DESC");
		List<PlManageMoneyPlan> list= plManageMoneyPlanService.getAll(filter);
		List<PlManageMoneyPlan> plmmlist=new ArrayList<PlManageMoneyPlan>();
		plmmlist=list;
		
		StringBuffer buff = new StringBuffer("{\"success\":true,\"paramers\":\"1111\",\"totalCounts\":").append(filter.getPagingBean().getTotalItems()).append(",\"result\":");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),new String[]{"buyStartTime","buyEndTime","createtime"});
		buff.append(json.serialize(plmmlist));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 判断授权页面打开方式
	 * @return
	 */
	public String isPage(){
		Object[] thirdType = ThirdPayInterfaceUtil.checkThirdType(ThirdPayConstants.BT_MMAUTH,"bid");
		StringBuffer buff = new StringBuffer("{\"success\":true,\"isPage\":\""+thirdType[0]+"\"}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	 /**
     * 导出Excel方法
     */
    public void toExcelPlanList(){
    	SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	QueryFilter filter=new QueryFilter(getRequest());
    
		String keystr=this.getRequest().getParameter("keystr");
		String state=this.getRequest().getParameter("state");
		String isPresale=this.getRequest().getParameter("isPresale");
		String mmNumber=this.getRequest().getParameter("mmNumber");
		String mmName=this.getRequest().getParameter("mmName");
		//预售查询处理start
		if(null !=isPresale && isPresale.equals("dys") && null !=state && state.equals("1")){//待预售
			filter.addFilter("Q_preSaleTime_D_GT",sd.format(new Date()));
	    }else if(null !=isPresale && isPresale.equals("ysz") && null !=state && state.equals("1")){//预售中
			filter.addFilter("Q_preSaleTime_D_LT",sd.format(new Date()));
			filter.addFilter("Q_buyStartTime_D_GT",sd.format(new Date()));
		}else if(null !=isPresale && isPresale.equals("zbz") && null !=state && state.equals("1")){//招标中
			filter.addFilter("Q_buyStartTime_D_LT",sd.format(new Date()));
		}else{
			//其他状态
		}
		//预售查询处理end
		if(null !=keystr && !"".equals(keystr)){
			filter.addFilter("Q_keystr_S_EQ",keystr);
		}
		if(null !=state && !"".equals(state)){
			filter.addFilter("Q_state_N_EQ",state);
		}
		if(null != mmNumber && !"".equals(mmNumber)){
			filter.addFilter("Q_mmNumber_S_LK",mmNumber);
		}
		if(null != mmName && !"".equals(mmName)){
			filter.addFilter("Q_mmName_S_LK",mmName);
		}
		filter.addSorted("buyStartTime", "DESC");
		List<PlManageMoneyPlan> list= plManageMoneyPlanService.getAll(filter);
    	
		String [] tableHeader = {"序号","D计划编号","D计划名称","收款类型","收款专户","派息授权状态","计划金额","已购买总金额","预期年化收益率","投资期限","锁定期限","购买开放时间","购买截止时间"};
		ExcelHelper.toExcelPlanList(list,tableHeader,"理财计划台账");
	}
	
    /**
     * 根据原始债权人账号查询处于招标中总金额
     * @return
     */
    public String findBidMoneyByAccount(){
    	String account=this.getRequest().getParameter("account");//原始债权人账号
    	String keystr=this.getRequest().getParameter("keystr");//D计划、U计划类型
    	BigDecimal money=plManageMoneyPlanService.findBidMoneyByAccount(account,keystr);
    	if(null==money){
    		money=new BigDecimal(0);
    	}
    	jsonString ="{success:true,'money':"+money+"}";
    	return SUCCESS;
    }
    
    /**
     * 根据原始债权人账号计算剩余债权金额=债权总金额-招标及预售金额
     * @return
     */
    public String findBalanceMoney(){
    	String account=this.getRequest().getParameter("account");//原始债权人账号
    	String keystr=this.getRequest().getParameter("keystr");//D计划、U计划类型
    	//查询原始债权人当前时间内的债权总额
    	BigDecimal totalMoney=plMmObligatoryRightChildrenService.totalMoney(account,keystr);
    	if(null==totalMoney){
    		totalMoney=new BigDecimal(0);
    	}
    	//查询原始债权人所有理财计划的招标总金额
    	BigDecimal bidMoney=plManageMoneyPlanService.findBidMoneyByAccount(account,keystr);
    	if(null==bidMoney){
    		bidMoney=new BigDecimal(0);
    	}
    	//查询原始债权人所有理财计划的投资记录，计算出已匹配金额
    	BigDecimal matchingMoney=plManageMoneyPlanService.findMatchingMoneyByAccount(account,keystr);
    	if(null==matchingMoney){
    		matchingMoney=new BigDecimal(0);
    	}
    	BigDecimal money = totalMoney.subtract(bidMoney.subtract(matchingMoney));
    	if(money.compareTo(new BigDecimal(0)) < 0){
    		money = new BigDecimal(0);
    	}
    	jsonString ="{success:true,'money':"+money+"}";
    	return SUCCESS;
    }
}
