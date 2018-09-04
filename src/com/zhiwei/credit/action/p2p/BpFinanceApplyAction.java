package com.zhiwei.credit.action.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.sql.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.StringUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.p2p.BpFinanceApply;
import com.zhiwei.credit.service.p2p.BpFinanceApplyService;
/**
 * 
 * @author 
 *
 */
public class BpFinanceApplyAction extends BaseAction{
	@Resource
	private BpFinanceApplyService bpFinanceApplyService;
	private BpFinanceApply bpFinanceApply;
	
	private Long loanId;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public BpFinanceApply getBpFinanceApply() {
		return bpFinanceApply;
	}

	public void setBpFinanceApply(BpFinanceApply bpFinanceApply) {
		this.bpFinanceApply = bpFinanceApply;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String ck=getRequest().getParameter("ck");
		String state=this.getRequest().getParameter("state");
		QueryFilter filter=new QueryFilter(getRequest());
		if(ck!=null&&ck!=""){
			if(ck.equals("0")){//非注册个人申请审核
				filter.addFilter("Q_type_S_EQ", "0");
			}else if(ck.equals("1")){//非注册企业申请审核
				filter.addFilter("Q_type_S_EQ", "1");
			}
		}
		filter.addFilter("Q_state_SN_EQ", state);
		List<BpFinanceApply> list= bpFinanceApplyService.getAll(filter);
		
		Type type=new TypeToken<List<BpFinanceApply>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH-mm-ss").create();
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
				bpFinanceApplyService.remove(new Long(id));
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
		BpFinanceApply bpFinanceApply=bpFinanceApplyService.get(loanId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpFinanceApply));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpFinanceApply==null){
			bpFinanceApply=new BpFinanceApply();
		}
		String ret;
		String state="0";
		//个人贷需要的参数
		String productId=this.getRequest().getParameter("productId");
		String linkPersion=this.getRequest().getParameter("linkPersion");
		String phone=this.getRequest().getParameter("phone");
		String loanMoney=this.getRequest().getParameter("loanMoney");
		String isOnline=this.getRequest().getParameter("isOnline");
		String loanTimeLen=this.getRequest().getParameter("loanTimeLen");
		String area=this.getRequest().getParameter("area");
		String remark=this.getRequest().getParameter("remark");
		String productName=this.getRequest().getParameter("productName");
		String businessName=this.getRequest().getParameter("businessName");
		String type=this.getRequest().getParameter("type");
		
		//企业贷需要的参数
		String companyNo = this.getRequest().getParameter("companyNo");
		String cardnumber = this.getRequest().getParameter("cardnumber");
		String legalEmail = this.getRequest().getParameter("legalEmail");
		String registeredcapital = this.getRequest().getParameter("registeredcapital");
		String createdate = this.getRequest().getParameter("createdate");
		String startTime = this.getRequest().getParameter("startTime");
		String expectAccrual = this.getRequest().getParameter("expectAccrual");
		String payIntersetWay = this.getRequest().getParameter("payIntersetWay");
		String loanUse = this.getRequest().getParameter("loanUse");
		String rebatesUse = this.getRequest().getParameter("rebatesUse");
		
		if(type!=null&&"1".equals(type)){
			//企业贷
			try{
				bpFinanceApply.setCompanyNo(StringUtil.stringURLDecoderByUTF8(companyNo));
				bpFinanceApply.setCardnumber(StringUtil.stringURLDecoderByUTF8(cardnumber));
				bpFinanceApply.setLegalEmail(StringUtil.stringURLDecoderByUTF8(legalEmail));
				bpFinanceApply.setRegisteredcapital(StringUtil.stringURLDecoderByUTF8(registeredcapital));
				bpFinanceApply.setCreatedate(Date.valueOf(createdate));
				bpFinanceApply.setStartTime(Date.valueOf(startTime));
				bpFinanceApply.setExpectAccrual(StringUtil.stringURLDecoderByUTF8(expectAccrual));
				bpFinanceApply.setPayIntersetWay(Short.valueOf(payIntersetWay));
				bpFinanceApply.setLoanUse(StringUtil.stringURLDecoderByUTF8(loanUse));
				bpFinanceApply.setRebatesUse(StringUtil.stringURLDecoderByUTF8(rebatesUse));
				bpFinanceApply.setCreateTime(new java.util.Date());
				bpFinanceApply.setProductId(Long.valueOf(productId));
				bpFinanceApply.setLinkPersion(StringUtil.stringURLDecoderByUTF8(linkPersion));
				bpFinanceApply.setPhone(phone);
				bpFinanceApply.setLoanMoney(new BigDecimal(loanMoney));
				bpFinanceApply.setIsOnline(Short.valueOf(isOnline));
				bpFinanceApply.setLoanTimeLen(loanTimeLen);
				bpFinanceApply.setArea(StringUtil.stringURLDecoderByUTF8(area));
				bpFinanceApply.setProductName(StringUtil.stringURLDecoderByUTF8(productName));
				bpFinanceApply.setState(Short.valueOf(state));
				
				
				if(businessName==null)
				{
					businessName="";
				}
				bpFinanceApply.setBusinessName(StringUtil.stringURLDecoderByUTF8(businessName));
				bpFinanceApply.setType(type);
				bpFinanceApplyService.save(bpFinanceApply);
			
				ret=Constants.SUCCESSFLAG;
				
				}catch(Exception e){
					e.printStackTrace();
					ret=Constants.FAILDFLAG;
				}
		}else{
			//个人贷
			try{
				
				bpFinanceApply.setProductId(Long.valueOf(productId));
				bpFinanceApply.setLinkPersion(StringUtil.stringURLDecoderByUTF8(linkPersion));
				bpFinanceApply.setPhone(phone);
				bpFinanceApply.setLoanMoney(new BigDecimal(loanMoney));
				bpFinanceApply.setIsOnline(Short.valueOf(isOnline));
				bpFinanceApply.setLoanTimeLen(loanTimeLen);
				bpFinanceApply.setCreateTime(new java.util.Date());
				bpFinanceApply.setArea(StringUtil.stringURLDecoderByUTF8(area));
				bpFinanceApply.setRemark(StringUtil.stringURLDecoderByUTF8(remark));
				bpFinanceApply.setProductName(StringUtil.stringURLDecoderByUTF8(productName));
				if(businessName==null)
				{
					businessName="";
				}
				bpFinanceApply.setBusinessName(StringUtil.stringURLDecoderByUTF8(businessName));
				bpFinanceApply.setType(type);
				bpFinanceApply.setState(Short.valueOf(state));
				bpFinanceApplyService.save(bpFinanceApply);
				ret=Constants.SUCCESSFLAG;
				}catch(Exception e){
					e.printStackTrace();
					ret=Constants.FAILDFLAG;
				}
		}
		setJsonString(ret);
		return SUCCESS;
		
	}
	/**
	 * 稳安贷信息审核
	 * @return
	 */
	public String update(){
		String ret;
		short state=Short.parseShort(this.getRequest().getParameter("state"));
		long loadid=Long.parseLong(this.getRequest().getParameter("loanId"));
		bpFinanceApply=bpFinanceApplyService.get(loanId);
		bpFinanceApply.setState(state);
		bpFinanceApplyService.save(bpFinanceApply);
		ret=Constants.SUCCESSFLAG;
		setJsonString(ret);
		return SUCCESS;
	}
}
