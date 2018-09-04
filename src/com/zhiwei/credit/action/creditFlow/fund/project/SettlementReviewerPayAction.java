package com.zhiwei.credit.action.creditFlow.fund.project;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.credit.proj.settlecenter.entity.OwnerShip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementInfo;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.fund.project.OwnerShipService;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementInfoService;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementReviewerPayService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class SettlementReviewerPayAction extends BaseAction{
	@Resource
	private SettlementReviewerPayService settlementReviewerPayService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private OwnerShipService ownerShipService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private SettlementInfoService settlementInfoService;
	
	private SettlementReviewerPay settlementReviewerPay;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SettlementReviewerPay getSettlementReviewerPay() {
		return settlementReviewerPay;
	}

	public void setSettlementReviewerPay(SettlementReviewerPay settlementReviewerPay) {
		this.settlementReviewerPay = settlementReviewerPay;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SettlementReviewerPay> list= settlementReviewerPayService.getAll(filter);
		for(SettlementReviewerPay pay : list){
			if(pay.getCollectionDepartment()!=null){
				Organization o = organizationService.get(Long.valueOf(pay.getCollectionDepartment()));
				pay.setOrganName(o.getOrgName());
			}
		}
		Type type=new TypeToken<List<SettlementReviewerPay>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
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
				settlementReviewerPayService.remove(new Long(id));
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
		SettlementReviewerPay settlementReviewerPay=settlementReviewerPayService.get(id);
		if(settlementReviewerPay.getCollectionDepartment()!=null){
			Organization org = organizationService.get(Long.valueOf(settlementReviewerPay.getCollectionDepartment()));
			settlementReviewerPay.setOrganName(org.getOrgName());
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(settlementReviewerPay));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(settlementReviewerPay.getId()==null){
			settlementReviewerPayService.save(settlementReviewerPay);
		}else{
			SettlementReviewerPay orgSettlementReviewerPay=settlementReviewerPayService.get(settlementReviewerPay.getId());
			try{
				BeanUtil.copyNotNullProperties(orgSettlementReviewerPay, settlementReviewerPay);
				settlementReviewerPayService.save(orgSettlementReviewerPay);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 生成投资保有量明细信息
	 * @return
	 * @throws ParseException 
	 */
	public String listSettle() throws ParseException{
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");
		if(endDate!=null && !"".equals(endDate)){
			Date date = s.parse(endDate);
			endDate = s.format(date);
		}
		String id = this.getRequest().getParameter("id");
		List<SettlementReviewerPay> list = null;
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		if(endDate==null || "".equals(endDate)){
			buff.append(0).append(",result:");
			
			buff.append(gson.toJson(list));
			buff.append("}");
		}else{
			list = settlementReviewerPayService.listSettle(Long.valueOf(id), startDate, endDate);
			buff.append(list.size()).append(",result:");
			
			buff.append(gson.toJson(list));
			buff.append("}");
		}
		System.out.println(buff.toString());
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 当日投资保有量详细信息
	 * @return
	 * @throws ParseException 
	 */
	public String listSettleInformation() throws ParseException{
		/*SimpleDateFormat s = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z",Locale.ENGLISH);
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");
		if(endDate!=null && !"".equals(endDate)){
			Date date = s.parse(endDate);
			endDate = s1.format(date);
		}
		String id = this.getRequest().getParameter("id");*/
		
		String date = this.getRequest().getParameter("date");
		List<SettlementReviewerPay> list = null;
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		list = settlementReviewerPayService.listInformation(date);
		buff.append(list.size()).append(",result:");
		
		buff.append(gson.toJson(list));
		buff.append("}");
		System.out.println(buff.toString());
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	public String test(){
		settlementReviewerPayService.createSettleInfo();
		return SUCCESS;
	}
	public String testAll(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");
		try {
			settlementReviewerPayService.createSettleInfoAll(sf.parse(startDate),sf.parse(endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String update(){
		String settlementPayToChangeJson = this.getRequest().getParameter("settlementPayToChangeJson");
		if(settlementReviewerPay.getId()!=null){
			SettlementReviewerPay pay = settlementReviewerPayService.get(settlementReviewerPay.getId());
			try {
				BeanUtil.copyNotNullProperties(pay, settlementReviewerPay);
				settlementReviewerPayService.merge(pay);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//保存手续费
		if(settlementPayToChangeJson!=null&&!"".equals(settlementPayToChangeJson)){
			slActualToChargeService.savejson(settlementPayToChangeJson, settlementReviewerPay.getId(), "InvestSettle", new Short("0"), settlementReviewerPay.getCompanyId());
		}
		return SUCCESS;
	}
	public String listByShips(){
		String info = this.getRequest().getParameter("infoId");
		if(info!=null&&!"".equals(info)){
			QueryFilter filter = new QueryFilter();
			filter.addFilter("Q_infoId_L_EQ", Long.valueOf(info));
			List<OwnerShip> list = ownerShipService.getAll(filter);
			for(OwnerShip s:list){
				if(s.getInvestId()!=null&&!"".equals(s.getInvestId())){
					BpCustMember mem = bpCustMemberService.get(s.getInvestId());
					if(mem!=null){
						s.setInvestTrueName(mem.getTruename());
					}
				}
				if(s.getBorrower()!=null&&!"".equals(s.getBorrower())){
					BpCustMember mem = bpCustMemberService.getMemberUserName(s.getBorrower());
					if(mem!=null){
						s.setBorrowerName(mem.getTruename());
					}
				}
			}
			Type type=new TypeToken<List<OwnerShip>>(){}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(list==null?0:list.size()).append(",result:");
			
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			
			jsonString=buff.toString();
		}
		return SUCCESS;
	}
	public void shipsExcel(){
		String info = this.getRequest().getParameter("infoId");
		QueryFilter filter = new QueryFilter();
		filter.addFilter("Q_infoId_L_EQ", Long.valueOf(info));
		List<OwnerShip> list = ownerShipService.getAll(filter);
		for(OwnerShip s:list){
			if(s.getInvestId()!=null&&!"".equals(s.getInvestId())){
				BpCustMember mem = bpCustMemberService.get(s.getInvestId());
				if(mem!=null){
					s.setInvestTrueName(mem.getTruename());
				}
			}
			if(s.getBorrower()!=null&&!"".equals(s.getBorrower())){
				BpCustMember mem = bpCustMemberService.getMemberUserName(s.getBorrower());
				if(mem!=null){
					s.setBorrowerName(mem.getTruename());
				}
			}
		}
		SettlementInfo settleInfo = settlementInfoService.get(Long.valueOf(info));
		String[] tableHeader = null;
		String[] fields = null;
		if(settleInfo!=null&&settleInfo.getOrgId()!=null){
			Organization org = organizationService.get(settleInfo.getOrgId());
			if(org!=null&&org.getSettlementType()!=null&&!"".equals(org.getSettlementType())){
				if(new Short("3").equals(org.getSettlementType())){
					tableHeader = new String[]{"序号","标的名称","日期","保有量金额","借款人账号","借款人姓名"};
					fields = new String[]{"bidName","transferDate","reMainMoney","borrower","borrowerName"};
				}else{
					tableHeader = new String[]{"序号","标的名称","日期","保有量金额","投资人账户","投资人姓名"};
					fields = new String[]{"bidName","transferDate","reMainMoney","investName","investTrueName"};
				}
			}
		}
		String headerStr="每日结算列表";
		try {
			ExportExcel.export(tableHeader, fields, list,headerStr, OwnerShip.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
