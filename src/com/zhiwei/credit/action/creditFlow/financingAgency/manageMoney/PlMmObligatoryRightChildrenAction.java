package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlMmObligatoryRightChildrenAction extends BaseAction{
	@Resource
	private PlMmObligatoryRightChildrenService plMmObligatoryRightChildrenService;
	@Resource
	private PlMmOrderChildrenOrService plMmOrderChildrenOrService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyInfoService ;
	private PlMmObligatoryRightChildren plMmObligatoryRightChildren;
	private PlBidPlan plBidPlan;
	private Long childrenorId;
	private Long orderId;

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

	public PlBidPlan getPlBidPlan() {
		return plBidPlan;
	}

	public void setPlBidPlan(PlBidPlan plBidPlan) {
		this.plBidPlan = plBidPlan;
	}

	public PlMmObligatoryRightChildren getPlMmObligatoryRightChildren() {
		return plMmObligatoryRightChildren;
	}

	public void setPlMmObligatoryRightChildren(PlMmObligatoryRightChildren plMmObligatoryRightChildren) {
		this.plMmObligatoryRightChildren = plMmObligatoryRightChildren;
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
		String orderId=getRequest().getParameter("orderId");
		if(orderId!=null&&!"".equals(orderId)){
			PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyInfo=plManageMoneyPlanBuyInfoService.get(Long.valueOf(orderId));
			if(plManageMoneyPlanBuyInfo!=null){
				if(plManageMoneyPlanBuyInfo.getPlManageMoneyPlan()!=null&&!"".equals(plManageMoneyPlanBuyInfo.getPlManageMoneyPlan())){
					map.put("receiver", plManageMoneyPlanBuyInfo.getPlManageMoneyPlan().getMoneyReceiver());
				}
			}
		}
		List<PlMmObligatoryRightChildren> list= plMmObligatoryRightChildrenService.listbysearch(pb, map);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"startDate","intentDate"});
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
				plMmObligatoryRightChildrenService.remove(new Long(id));
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
		PlMmObligatoryRightChildren plMmObligatoryRightChildren=plMmObligatoryRightChildrenService.get(childrenorId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plMmObligatoryRightChildren));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
/*		if(plBidPlan.getProType().endsWith("B_Or"));
		if(plBidPlan.getProType().endsWith("P_Or"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BpBusinessOrPro bpBusinessOrPro=bpBusinessOrProService.get(Long.valueOf("1"));//
		List<SlFundIntent> list = slFundIntentService.getByProjectAsc(bpBusinessOrPro.getProId(),"SmallLoan");
		SlSmallloanProject slSmallloanProject= slSmallloanProjectService.get(bpBusinessOrPro.getProId());
	//	BpFundProject bpFundProject =bpFundProjectService.get(bpBusinessOrPro.getMoneyPlanId());
		Date transferDate=plBidPlan.getBidEndTime();
		Date intentDate=null;
		int days=0;
		int i=0;
		for(SlFundIntent l:list){
			i++;
			 intentDate=l.getIntentDate();
			  days=DateUtil.getDaysBetweenDate(sdf.format(transferDate), sdf.format(intentDate));
			 if(days>0&&i==1){
				 break;
				}
			if(days>0){
				
				break;
			}
			if(days==0){
				
				break;
			}
		}
		SlFundIntent s=list.get(i-1);
		PlMmObligatoryRightChildren plrcone=new PlMmObligatoryRightChildren();
		plrcone.setPrincipalMoney(s.getIncomeMoney().divide(new BigDecimal(30),100,BigDecimal.ROUND_UP).multiply(new BigDecimal(days)));
		plrcone.setIntentDate(s.getIntentDate());
		plrcone.setStartDate(transferDate);
		plrcone.setOrlimit(days);
		plrcone.setDayRate(slSmallloanProject.getDayAccrualRate());
		plrcone.setAvailableMoney(s.getIncomeMoney());
//		plrc.setParentOrBidId(aValue);
	//	plrc.setParentOrBidName(l.get);
		plrcone.setChildrenorName(plrcone.getParentOrBidName()+s.getPayintentPeriod());
		plrcone.setSurplusValueMoney(s.getIncomeMoney().multiply(new BigDecimal(plrcone.getOrlimit())));
		
		plMmObligatoryRightChildrenService.save(plrcone);
		for(int j=i;j<list.size();j++){
			SlFundIntent l=list.get(j);
			PlMmObligatoryRightChildren plrc=new PlMmObligatoryRightChildren();
			plrc.setPrincipalMoney(l.getIncomeMoney());
			plrc.setIntentDate(l.getIntentDate());
			plrc.setStartDate(slSmallloanProject.getStartDate());
		    days=DateUtil.getDaysBetweenDate(sdf.format(slSmallloanProject.getStartDate()), sdf.format(l.getIntentDate()));
			plrc.setOrlimit(days);
			plrc.setDayRate(slSmallloanProject.getDayAccrualRate());
			plrc.setAvailableMoney(l.getIncomeMoney());
	//		plrc.setParentOrBidId(aValue);
		//	plrc.setParentOrBidName(l.get);
			plrc.setChildrenorName(plrc.getParentOrBidName()+l.getPayintentPeriod());
			plrc.setSurplusValueMoney(l.getIncomeMoney().multiply(new BigDecimal(plrc.getOrlimit())));
			
			plMmObligatoryRightChildrenService.save(plrc);
		}
		
		
		plBidPlan.setCreatetime(new Date());
		plBidPlan.setUpdatetime(new Date());
		plBidPlanService.save(plBidPlan);*/
		
		plMmObligatoryRightChildrenService.createObligatoryRightChildren(plBidPlan);
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * U计划债权列表
	 */
	public String listUPlan(){
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlMmObligatoryRightChildren> list= plMmObligatoryRightChildrenService.listbyUPLan(pb, map);
		List<PlMmObligatoryRightChildren> listCount= plMmObligatoryRightChildrenService.listbyUPLan(null, map);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(listCount !=null?listCount.size() : 0).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"startDate","intentDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	
	 /**
     * U计划债权列表导出Excel方法
     */
    public void toExcelRightChildren(){
    	PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlMmObligatoryRightChildren> list= plMmObligatoryRightChildrenService.listbyUPLan(null, map);
		String [] tableHeader = {"序号","标的名称","投资人","债权名称","子债权名称","债权起日","债权止日","期限","本金","日化利率","可转让金额","债权剩余价值"};
		ExcelHelper.toExcelRightChildren(list,tableHeader,"U计划债权库列表");
	}
    
	//更改债权到期日期
	public String changeEndTime (){
		String childrenorId=this.getRequest().getParameter("plMmObligatoryRightChildren.childrenorId");
		//更改后的到期日
		String changeTime=this.getRequest().getParameter("plMmObligatoryRightChildren.intentDate");
		if(childrenorId!=null&&!childrenorId.equals("")){
			PlMmObligatoryRightChildren plMmObligatoryRightChildren=plMmObligatoryRightChildrenService.get(Long.valueOf(childrenorId));
			if(plMmObligatoryRightChildren!=null){
				if(changeTime!=null&&!changeTime.equals("")){
					plMmObligatoryRightChildren.setIntentDate(DateUtil.parseDate(changeTime));
				}
				plMmObligatoryRightChildrenService.merge(plMmObligatoryRightChildren);
				//当修改的债权到期日小于原来的债权到期日时（属于提前还款），需修改债权到期日与匹配记录，当大于时（属于展期，只修改债权到期日即可）
				//以下方法为修改匹配列表
				if(DateUtil.stringToTsDate(changeTime).compareTo(plMmObligatoryRightChildren.getIntentDate())<1){
					plMmOrderChildrenOrService.updateChildrenor(plMmObligatoryRightChildren);
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 查询 原始债权人 指定时间的债权总额
	 * @return
	 */
	public String balanceList(){
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlMmObligatoryRightChildren> list= plMmObligatoryRightChildrenService.balanceList(pb, map);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"startDate","intentDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

}