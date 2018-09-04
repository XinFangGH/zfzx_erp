package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hurong.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.hurong.credit.util.Common;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.investInfoManager.Investproject;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.*;
import com.zhiwei.credit.util.xmlToWord.DocumentHandler;
import flexjson.JSONSerializer;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
/**
 * 
 * @author 
 *
 */
public class PlManageMoneyPlanBuyinfoAction extends BaseAction{
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource
	private PlMmOrderChildrenorTestService plMmOrderChildrenorTestService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private PlMmOrderInfoService plMmOrderInfoService;
	private PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlManageMoneyPlanBuyinfo getPlManageMoneyPlanBuyinfo() {
		return plManageMoneyPlanBuyinfo;
	}

	public void setPlManageMoneyPlanBuyinfo(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo) {
		this.plManageMoneyPlanBuyinfo = plManageMoneyPlanBuyinfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlManageMoneyPlanBuyinfo> list= plManageMoneyPlanBuyinfoService.listbysearch(pb, map);
		for(PlManageMoneyPlanBuyinfo l:list){
			l.setMmName(l.getPlManageMoneyPlan().getMmName());
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String listbyPlanId(){
		QueryFilter filter=new QueryFilter(getRequest());
		String state=getRequest().getParameter("state");
		if(null!=state&&!"".equals(state)){
			List inList=new ArrayList();
			String[] str=state.split(",");
			for(String s:str){
				inList.add(Short.valueOf(s));
			}
			filter.addFilterIn("Q_state_SN_IN", inList);
		}
		filter.getPagingBean().setPageSize(1000000000);
		List<PlManageMoneyPlanBuyinfo> list= plManageMoneyPlanBuyinfoService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
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
				plManageMoneyPlanBuyinfoService.remove(new Long(id));
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
		PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("plManageMoneyPlanBuyinfo", plManageMoneyPlanBuyinfo);
		PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanBuyinfo.getPlManageMoneyPlan();
		map.put("plManageMoneyPlan", plManageMoneyPlan);
		
		PlMmOrderInfo plMmOrderInfo = plMmOrderInfoService.getByOrderId(id);
		if(plMmOrderInfo!=null){
			map.put("plMmOrderInfo", plMmOrderInfo);
		}
		
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.serialize(map));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String saveProdeuce(){
		BigDecimal[] a=obSystemAccountService.sumTypeTotalMoney(plManageMoneyPlanBuyinfo.getInvestPersonId(),"1");
		if(a[0].compareTo(new BigDecimal("0"))==0){
			setJsonString("{failure:true,moneytoobig:true,msg:'此投资人没有开设账户'}");
			return SUCCESS;
		}
		if(a[3].compareTo(plManageMoneyPlanBuyinfo.getBuyMoney())==-1){
			setJsonString("{failure:true,moneytoobig:true,msg:'购买金额不能大于可用金额'}");
			return SUCCESS;
		}
		if(plManageMoneyPlanBuyinfo.getOrderId()==null){
			
			plManageMoneyPlanBuyinfo.setPersionType(PlManageMoneyPlanBuyinfo.P_TYPE1);
			PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmplanId());
			plManageMoneyPlan.setInvestedMoney(plManageMoneyPlan.getInvestedMoney().add(plManageMoneyPlanBuyinfo.getBuyMoney()));
			plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(plManageMoneyPlan);
			BigDecimal dayRate=plManageMoneyPlan.getYeaRate().divide(new BigDecimal("360"),10,BigDecimal.ROUND_UP);
			plManageMoneyPlanBuyinfo.setCurrentGetedMoney(new BigDecimal("0"));
			plManageMoneyPlanBuyinfo.setPromisDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setPromisMonthRate(plManageMoneyPlan.getYeaRate().divide(new BigDecimal("12"),10,BigDecimal.ROUND_UP));
			plManageMoneyPlanBuyinfo.setPromisYearRate(plManageMoneyPlan.getYeaRate());
			plManageMoneyPlanBuyinfo.setMmName(plManageMoneyPlan.getMmName());
			if(plManageMoneyPlan.getIsOne()==2){
				int investlimit =plManageMoneyPlan.getInvestlimit();
				BigDecimal baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney();
				BigDecimal summoney=new BigDecimal(0);
				for(int i=1;i<=investlimit;i++){
					summoney=summoney.add(baseMoney.multiply(plManageMoneyPlanBuyinfo.getPromisMonthRate()).divide(new BigDecimal(100)));
					baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney().add(summoney);
				 }
				
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(summoney);
			}else{
				
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(plManageMoneyPlanBuyinfo.getBuyMoney().multiply(dayRate.multiply(new BigDecimal(plManageMoneyPlanBuyinfo.getOrderlimit())).divide(new BigDecimal("100"))));
			}
		
			plManageMoneyPlanBuyinfo.setCurrentMatchingMoney(plManageMoneyPlanBuyinfo.getBuyMoney());
			plManageMoneyPlanBuyinfo.setOptimalDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setKeystr("mmproduce");
			plManageMoneyPlanBuyinfo.setFirstProjectIdcount(0);
			plManageMoneyPlanBuyinfo.setFirstProjectIdstr("");
			plManageMoneyPlanBuyinfo.setIsAtuoMatch(0);
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",plManageMoneyPlanBuyinfo.getInvestPersonId());//投资人Id（必填）
			map.put("investPersonType",ObSystemAccount.type1);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			map.put("money",plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额	（必填）			 
			map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			map.put("dealType",ObAccountDealInfo.BANKDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			map.put("recordNumber",Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));//交易流水号	（必填）
			map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] relt =obAccountDealInfoService.operateAcountInfo(map);
			/*String[] relt=obAccountDealInfoService.operateAcountInfo(plManageMoneyPlanBuyinfo.getInvestPersonId().toString(),ObAccountDealInfo.T_INVEST.toString(), plManageMoneyPlanBuyinfo.getBuyMoney().toString() ,ObAccountDealInfo.BANKDEAL.toString(),
			ObSystemAccount.type1.toString(), ObAccountDealInfo.ISAVAILABLE.toString(),ObAccountDealInfo.UNREADTHIRDRECORD.toString(),null);*/
		     if(relt[0].equals(Constants.CODE_SUCCESS)){
		    		plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
					plMmOrderAssignInterestService.createAssignInerestlist(plManageMoneyPlanBuyinfo,plManageMoneyPlan);
				//	plMmOrderChildrenorTestService.matchForecast(plManageMoneyPlanBuyinfo);
		     }else{
		    	 
		 			setJsonString("{failure:true,moneytoobig:true,msg:"+relt[1]+"}");
		 			return SUCCESS;
		 	}
		     

		}else{
			PlManageMoneyPlanBuyinfo orgPlManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(plManageMoneyPlanBuyinfo.getOrderId());
			try{
				BeanUtil.copyNotNullProperties(orgPlManageMoneyPlanBuyinfo, plManageMoneyPlanBuyinfo);
				plManageMoneyPlanBuyinfoService.save(orgPlManageMoneyPlanBuyinfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String buyBidplan(){
		/*String InvestPersonId=this.getRequest().getParameter("investPersonId");
		String mmplanId=this.getRequest().getParameter("mmplanId");
		String buyMoney=this.getRequest().getParameter("buyMoney");
		BigDecimal[] a=obSystemAccountService.sumTypeTotalMoney(Long.valueOf(InvestPersonId),String.valueOf(PlManageMoneyPlanBuyinfo.P_TYPE0));
		if(a[0].compareTo(new BigDecimal("0"))==0){
			setJsonString("{failure:true,moneytoobig:true,msg:'此投资人没有开设账户'}");
			return SUCCESS;
		}
		if(a[3].compareTo(new BigDecimal(buyMoney))==-1){
			setJsonString("{failure:true,moneytoobig:true,msg:'购买金额不能大于可用金额'}");
			return SUCCESS;
		}
			PlManageMoneyPlan plManageMoneyPlan =plManageMoneyPlanService.get(Long.valueOf(mmplanId));
			
			if(new BigDecimal(buyMoney).compareTo(plManageMoneyPlan.getStartMoney())==-1){
				
				setJsonString("{failure:true,moneytoobig:true,msg:'购买金额不能小于起始金额'}");
				return SUCCESS;
			}
			
			BigDecimal amoney=new BigDecimal(buyMoney).subtract(plManageMoneyPlan.getStartMoney());
			int ab=Integer.valueOf(amoney.toString())%Integer.valueOf(plManageMoneyPlan.getRiseMoney().toString());
              if(ab!=0){
				
				setJsonString("{failure:true,moneytoobig:true,msg:'购买金额不符合递增金额'}");
				return SUCCESS;
			}
			plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(plManageMoneyPlan);
			plManageMoneyPlanBuyinfo.setBuyMoney(new BigDecimal(buyMoney));
			plManageMoneyPlanBuyinfo.setBuyDatetime(new Date());
			
			plManageMoneyPlanBuyinfo.setPersionType(PlManageMoneyPlanBuyinfo.P_TYPE0);
			plManageMoneyPlan.setInvestedMoney(plManageMoneyPlan.getInvestedMoney().add(plManageMoneyPlanBuyinfo.getBuyMoney()));
		
			
			if(plManageMoneyPlan.getSumMoney().compareTo(plManageMoneyPlan.getInvestedMoney())==0){
							
				plManageMoneyPlan.setState(plManageMoneyPlan.STATE2);
			}
			plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(plManageMoneyPlan);
			BigDecimal dayRate=plManageMoneyPlan.getYeaRate().divide(new BigDecimal("360"),10,BigDecimal.ROUND_UP);
			plManageMoneyPlanBuyinfo.setCurrentGetedMoney(new BigDecimal("0"));
			plManageMoneyPlanBuyinfo.setPromisDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setPromisMonthRate(plManageMoneyPlan.getYeaRate().divide(new BigDecimal("12"),10,BigDecimal.ROUND_UP));
			plManageMoneyPlanBuyinfo.setPromisYearRate(plManageMoneyPlan.getYeaRate());
			plManageMoneyPlanBuyinfo.setMmName(plManageMoneyPlan.getMmName());
			plManageMoneyPlanBuyinfo.setOrderlimit(plManageMoneyPlan.getInvestlimit()*30);
			if(plManageMoneyPlan.getIsCyclingLend()==1){
				int investlimit =plManageMoneyPlan.getInvestlimit();
				BigDecimal baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney();
				BigDecimal summoney=new BigDecimal(0);
				for(int i=1;i<=investlimit;i++){
					summoney=summoney.add(baseMoney.multiply(plManageMoneyPlanBuyinfo.getPromisMonthRate()).divide(new BigDecimal(100)));
					baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney().add(summoney);
				 }
				
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(summoney);
			}else{
				
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(plManageMoneyPlanBuyinfo.getBuyMoney().multiply(dayRate.multiply(new BigDecimal(plManageMoneyPlanBuyinfo.getOrderlimit())).divide(new BigDecimal("100"))));
			}
		
			plManageMoneyPlanBuyinfo.setCurrentMatchingMoney(plManageMoneyPlanBuyinfo.getBuyMoney());
			plManageMoneyPlanBuyinfo.setOptimalDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setKeystr("mmplan");
			plManageMoneyPlanBuyinfo.setFirstProjectIdcount(0);
			plManageMoneyPlanBuyinfo.setFirstProjectIdstr("");
			plManageMoneyPlanBuyinfo.setIsAtuoMatch(0);
			
			String[] relt=obAccountDealInfoService.operateAcountInfo(plManageMoneyPlanBuyinfo.getInvestPersonId().toString(),ObAccountDealInfo.T_INVEST.toString(), plManageMoneyPlanBuyinfo.getBuyMoney().toString() ,ObAccountDealInfo.BANKDEAL.toString(),
			ObSystemAccount.type1.toString(), ObAccountDealInfo.ISAVAILABLE.toString(),ObAccountDealInfo.UNREADTHIRDRECORD.toString(),null);

*/
		
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 显示列表
	 */
	public String newBidList() {

		QueryFilter filter = new QueryFilter(getRequest());
		//filter.addFilter("Q_state_SN_GT", "0");
		List<PlManageMoneyPlanBuyinfo> list = plManageMoneyPlanBuyinfoService.getAll(filter);
		Type type = new TypeToken<List<PlBidInfo>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{\"success\":true")
				.append(",\"result\":");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public String getorderList(){
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlManageMoneyPlanBuyinfo> list=plManageMoneyPlanBuyinfoService.listbysearch(null, map);
		StringBuffer buff = new StringBuffer("[");
		for(PlManageMoneyPlanBuyinfo bank:list){
			buff.append("[").append(bank.getOrderId()).append(",'")
			.append(bank.getMmName()+"-"+bank.getOrderId()).append("'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	
	/**
	 * 投资人投资管理查询方法
	 * add by linyan
	 * 2014-5-16
	 * @param request
	 * @return
	 */
	public String getPersonInvestProject(){
		List<Investproject>  list =plManageMoneyPlanBuyinfoService.getPersonInvestProject(this.getRequest(),start,limit);
		List<Investproject> listCount =plManageMoneyPlanBuyinfoService.getPersonInvestProject(this.getRequest(),null,null);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(listCount!=null?listCount.size():0).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

    public String changeIsAutoMatcing(){
    	String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoService.get(new Long(id));
				if(null !=order.getIsAtuoMatch()){
			    	order.setIsAtuoMatch(1-order.getIsAtuoMatch());
				}else{
					order.setIsAtuoMatch(0);
				}
				plManageMoneyPlanBuyinfoService.save(order);
			}
		}
    	
		return SUCCESS;
	}

    /**
     * 会员投资信息导出到Excel
     */
    public void getPersonInvestProjectToexcel(){
	    List<Investproject> list = new ArrayList<>();
		if(this.getRequest().getParameter("out").equals("0")){
            List<Investproject>   lists =plManageMoneyPlanBuyinfoService.getPersonInvestProject(this.getRequest(),null, null);
            String idStr = this.getRequest().getParameter("idStr");
            String[] split = StringUtils.split(idStr, ",");
            if (lists!=null && lists.size()>0){

                for (String s : split) {
                    long id = Long.valueOf(s);
                    for (Investproject investproject : lists) {
                        if (id == investproject.getInfoId().longValue()){
                            list.add(investproject);
                        }
                    }
                }
            }
		}
            else if(this.getRequest().getParameter("out").equals("2")){
			list = plManageMoneyPlanBuyinfoService.getPersonInvestProject(this.getRequest(), null, null);
		}

		String[] tableHeader = { "序号", "投资日期","用户名", "投标金额","姓名","投资名称","投资状态","身份证","手机号"};
		String[] fields = {"bidtimeStr","userName","userMoney","trueName","bidName","stateShow","cardcode","telphone"};
		try {
			ExportExcel.export(tableHeader, fields, list,"线上投资管理", Investproject.class);
		//	ExcelHelper.exportNew(list,tableHeader,"线上投资信息");
			//ExcelHelper.export(list, tableHeader, "利息收款台账");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public String getPayIntentPeriod(){
    	try{
    		PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(id);
    		PlManageMoneyPlan plan=plManageMoneyPlanBuyinfo.getPlManageMoneyPlan();
    		int period=plan.getInvestlimit();
    		StringBuffer buff = new StringBuffer("[");
    		for (int i = 0; i < period + 1; i++) {
    			buff.append("['").append((i)).append("','").append((i)).append(
    					"'],");
    		}
    		if (period > 0) {
    			buff.deleteCharAt(buff.length() - 1);
    		}
    		buff.append("]");
    		setJsonString(buff.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
	 
	    /**
	     * 打印对账单
	     * @return
	     */
	    @SuppressWarnings("deprecation")
	    public void printOrders(){
	    	
	    	String orderId = this.getRequest().getParameter("orderId");
			String serverPath = this.getRequest().getRealPath("/");
			String ftlSrc = serverPath+"attachFiles\\projFile\\printCreditorFile\\firstCreditor";
			UUID randomUUID = UUID.randomUUID();
			String uuid = randomUUID.toString().replace("-", "");
			String wordSrc = serverPath+"attachFiles\\projFile\\printCreditorFile\\"+uuid+".doc";
	    	
	    	Map<String,Object> dataMap = new HashMap<String, Object>();
	    	plManageMoneyPlanBuyinfoService.setMap(orderId,dataMap);
	    	
			DocumentHandler dh=new DocumentHandler(ftlSrc);
			//生成word
			dh.createOrderAndDown(dataMap, wordSrc);
			
			FileHelper.downLoadFile(wordSrc,"doc","对账单", this.getResponse());
			FileHelper.deleteFile(wordSrc);
			
	    }
	    
	    /**
	     * 体验标投资人列表查询方法
	     */
	    
	    public String listByMmplanId(){
	    	PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlManageMoneyPlanBuyinfo> list= plManageMoneyPlanBuyinfoService.listByMmplanId(null, map);
			StringBuffer buff = new StringBuffer("{success:true").append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			return SUCCESS;
	    }
	    
	    /**
	     * 体验标派息查询导出Excel方法
	     */
	    public void toExcelPlBuyinfo(){
	    	PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlManageMoneyPlan> list = plManageMoneyPlanService.getAllPlbuyInfo(pb,map);
			String [] tableHeader = {"序号","招标编号","招标名称","起息日期","计划派息日期","派息合计","实际派息日"};
			ExcelHelper.toExcelPlBuyinfo(list,tableHeader,"体验标派息列表");
		}
	 
	    
	    /**
		 * 显示列表
		 */
		public String listInfo(){
			PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlManageMoneyPlanBuyinfo> list= plManageMoneyPlanBuyinfoService.listbyState(pb, map);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			return SUCCESS;
		}
		
		
		 /**
	     * 体验标投资记录导出Excel方法
	     */
	    public void toExcelList(){
	    	PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlManageMoneyPlanBuyinfo> list= plManageMoneyPlanBuyinfoService.listbyState(pb, map);
			String [] tableHeader = {"序号","账号","体验券金额","招标编号","招标名称","投标日期","起息日期","到期日期"};
			ExcelHelper.toExcelListInfo(list,tableHeader,"体验标投资记录");
		}
	    
	    /**
	     * U计划投资记录导出Excel方法
	     */
	    public void toExcelUPlanList(){
	    	PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlManageMoneyPlanBuyinfo> list= plManageMoneyPlanBuyinfoService.listbyState(null, map);
			String [] tableHeader = {"序号","账号","投资金额","计划编号","计划名称","投标日期","起息日期","到期日期","状态"};
			ExcelHelper.toExcelUPlanListInfo(list,tableHeader,"U计划投资记录");
		}
	 
	 
	 
   
}
