package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionOrProService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.util.ExcelUtils;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlMmOrderChildrenOrAction extends BaseAction{
	@Resource
	private PlMmOrderChildrenOrService plMmOrderChildrenOrService;
	@Resource
	private PlMmObligatoryRightChildrenService plMmObligatoryRightChildrenService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	
	@Resource
	private PlBidPlanService  plBidPlanService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private BpPersionOrProService bpPersionOrProService;
	@Resource
	private BpBusinessOrProService bpBusinessOrProService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private PersonService personService;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	private PlMmOrderChildrenOr plMmOrderChildrenOr;
	
	private Date seachDate;
	private Long matchId;
	private Long childrenorId;
	private Long orderId;
	private BigDecimal matchingmoney;
	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public BigDecimal getMatchingmoney() {
		return matchingmoney;
	}

	public void setMatchingmoney(BigDecimal matchingmoney) {
		this.matchingmoney = matchingmoney;
	}

	public Date getSeachDate() {
		return seachDate;
	}

	public void setSeachDate(Date seachDate) {
		this.seachDate = seachDate;
	}

	public PlMmOrderChildrenOr getPlMmOrderChildrenOr() {
		return plMmOrderChildrenOr;
	}

	public void setPlMmOrderChildrenOr(PlMmOrderChildrenOr plMmOrderChildrenOr) {
		this.plMmOrderChildrenOr = plMmOrderChildrenOr;
	}

	public Long getChildrenorId() {
		return childrenorId;
	}

	public void setChildrenorId(Long childrenorId) {
		this.childrenorId = childrenorId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbysearch(pb, map);
		
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");

		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();

	
		return SUCCESS;
	}
	/**
	 * 导出Excel
	 */
	
	public String outToExcel(){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				if(paramName.equals("investPersonName")){
					paramValue=java.net.URLDecoder.decode(paramValue,"UTF-8");
				}
				map.put(paramName, paramValue);
			}
			List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbysearch(null, map);
			String [] tableHeader = {"序号","投资人名称","债权名称","匹配金额","债权日化利率","匹配开始日","匹配结束日","匹配期限","此匹配获得的收益"};
			ExcelHelper.exportOrderChildren(list,tableHeader,"投资客户债权清单");
		}catch(Exception e){
			e.printStackTrace();
		}
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
				plMmOrderChildrenOrService.remove(new Long(id));
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
		PlMmOrderChildrenOr plMmOrderChildrenOr=plMmOrderChildrenOrService.get(matchId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plMmOrderChildrenOr));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plMmOrderChildrenOr.getMatchId()==null){
			plMmOrderChildrenOrService.save(plMmOrderChildrenOr);
		}else{
			PlMmOrderChildrenOr orgPlMmOrderChildrenOr=plMmOrderChildrenOrService.get(plMmOrderChildrenOr.getMatchId());
			try{
				BeanUtil.copyNotNullProperties(orgPlMmOrderChildrenOr, plMmOrderChildrenOr);
				plMmOrderChildrenOrService.save(orgPlMmOrderChildrenOr);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 手动匹配
	 * @return
	 */
	public String matching(){
		if(null ==seachDate){
			seachDate=new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoService.get(orderId);
		if(null!=order.getEarlierOutDate() && !"".equals(order.getEarlierOutDate())){
			setJsonString("{success:true,earlierOutDate:true}");
			return SUCCESS;
		}
		PlMmObligatoryRightChildren	orchildren=plMmObligatoryRightChildrenService.get(childrenorId);
		int matchingLimit=DateUtil.getDaysBetweenDate(sdf.format(order.getEndinInterestTime()),sdf.format(orchildren.getStartDate()));
		int matchingLimit1=DateUtil.getDaysBetweenDate(sdf.format(orchildren.getIntentDate()),sdf.format(order.getStartinInterestTime()));
		
		if(matchingLimit>0 ||matchingLimit1>0){
			setJsonString("{success:true,matchingdayisunused:true}");
			return SUCCESS;
		}
		AppUser appuser=ContextUtil.getCurrentUser();
		String relt=plMmOrderChildrenOrService.matchingsave(order,orchildren,seachDate,matchingmoney,appuser);
		if(relt.equals("matchingdayisunused")){
			setJsonString("{success:true,matchingdayisunused:true}");
			return SUCCESS;
		}
		plMmOrderChildrenOrService.matchingreleaseall(new Date());
		Map<String, String> map = new HashMap<String, String>();
		int count=0;
		if(null!=orderId && !"".equals(orderId)){
			map.put("orderId", orderId.toString());
			List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbysearch(null, map);
			if(null!=list && list.size()>0){
				count=list.size();
			}
		}
		jsonString="{success:true,count:"+count+"}";
		return SUCCESS;
	}
	
	/**
	 * 全部自动匹配
	 * @return
	 */
	public String automatching(){
		if(null ==seachDate){
			seachDate=new Date();
		}
		//计划类型
		String keystr=getRequest().getParameter("keystr");
		plMmOrderChildrenOrService.schedulerMatching(seachDate,keystr);
/*		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			seachDate=sdf.parse("2014-03-31");
			while(seachDate.compareTo(new Date())==-1){
				plMmOrderChildrenOrService.schedulerMatching(seachDate);
				seachDate= DateUtil.addDaysToDate(seachDate, 1);
				 System.out.print("====="+sdf.format(seachDate));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return SUCCESS;
	}
	
	/**
	 * 初始化匹配
	 * @return
	 */
	public String halfAutomatching(){
		if(null ==seachDate){
			seachDate=new Date();
		}
		String[]ids=getRequest().getParameterValues("ids");
		String childType=getRequest().getParameter("childType");
		plMmOrderChildrenOrService.halfAutomatching(seachDate,ids,childType);
		Map<String, String> map = new HashMap<String, String>();
		int count=0;
		if(null!=ids && ids.length>0){
			map.put("orderId", ids[0]);
			List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbysearch(null, map);
			if(null!=list && list.size()>0){
				count=list.size();
			}
		}
		jsonString="{success:true,count:"+count+"}";
		return SUCCESS;
	}
	
	public String generateExcelUrl(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbysearch(pb, map);
			List<String[]> strList = new ArrayList<String[]>();
			String[] headOne = new String[] { "借款人姓名", "借款人身份证号码","转让金额",  "债权转让日期","起始还款日期",
					"预计债权收益率(年)"};
			for (PlMmOrderChildrenOr fe : list) {
				String personIDNO = "";//借款人身份证号码
				String personName = "";//借款人姓名
				PlMmObligatoryRightChildren plMmObligatoryRightChildren=	plMmObligatoryRightChildrenService.get(fe.getChildrenorId());
				PlBidPlan plBidPlan=plBidPlanService.get(plMmObligatoryRightChildren.getParentOrBidId());
				if(plBidPlan.getProType().equals("P_Or")){
	//				bp_persion_or_pro
					BpPersionOrPro bpPersionOrPro = bpPersionOrProService.get(plBidPlan.getPOrProId());
					if("SmallLoan".equals(bpPersionOrPro.getBusinessType())){
						SlSmallloanProject proj = slSmallloanProjectService.get(bpPersionOrPro.getProId());
						if("person_customer".equals(proj.getOppositeType())){
							Person p = personService.getById(proj.getOppositeID().intValue());
							personIDNO = p.getCardnumber();
							personName = p.getName();
						}
					}
				}else if(plBidPlan.getProType().equals("B_Or")){
	//				bp_business_or_pro
					BpBusinessOrPro bpBusinessOrPro = bpBusinessOrProService.get(plBidPlan.getPOrProId());
					if("SmallLoan".equals(bpBusinessOrPro.getBusinessType())){
						SlSmallloanProject proj = slSmallloanProjectService.get(bpBusinessOrPro.getProId());
						if("person_customer".equals(proj.getOppositeType())){
							Person p = personService.getById(proj.getOppositeID().intValue());
							personIDNO = p.getCardnumber();
							personName = p.getName();
						}
					}
				}
				PlManageMoneyPlan plManageMoneyPlan = plManageMoneyPlanService.get(fe.getMmplanId());
				PlMmOrderAssignInterest PlMmOrderAssignInterest = plMmOrderAssignInterestService.getFisrtByOrderId(fe.getOrderId());
				strList.add(new String[] { personName,personIDNO,fe.getMatchingMoney()==null?"": (fe.getMatchingMoney().toString()+"元"),
						fe.getMatchingStartDate()==null?"":sdf.format(fe.getMatchingStartDate()),
								PlMmOrderAssignInterest.getIntentDate()==null?"":sdf.format(PlMmOrderAssignInterest.getIntentDate()),
						plManageMoneyPlan.getYeaRate()==null?"":(plManageMoneyPlan.getYeaRate().stripTrailingZeros()+"%")
						});
	
			}
			ExcelHelper.exportManagePlan(strList,headOne,"债权列表");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		/*String[] rt=ExcelUtils.createExcel("债券列表",orderId.toString(),"计提","债券列表",headOne,strList);
		String url = rt[1];
		StringBuffer sb = new StringBuffer();
		sb.append("{success:true,\"url\":");
		url=(null==url?"":url.replace("\\", "/"));
		sb.append("\""+url);
		sb.append("\""+"}");
		setJsonString(sb.toString());*/
		return SUCCESS;
	}
	
}
