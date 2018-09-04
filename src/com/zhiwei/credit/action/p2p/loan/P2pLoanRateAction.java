package com.zhiwei.credit.action.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.p2p.loan.P2pLoanRate;
import com.zhiwei.credit.service.p2p.loan.P2pLoanRateService;
/**
 * 
 * @author 
 *
 */
public class P2pLoanRateAction extends BaseAction{
	@Resource
	private P2pLoanRateService p2pLoanRateService;
	private P2pLoanRate p2pLoanRate;
	private Long productId;
	
	private Long rateId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public P2pLoanRate getP2pLoanRate() {
		return p2pLoanRate;
	}

	public void setP2pLoanRate(P2pLoanRate p2pLoanRate) {
		this.p2pLoanRate = p2pLoanRate;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalProperty':");
		List<P2pLoanRate> list= p2pLoanRateService.p2pLoanRateList(pb, productId);
		Long lcount=p2pLoanRateService.p2pLoanRateListCount(productId);
		buff=buff.append(lcount).append(",topics:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		 buff.append(gson.toJson(list));
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
				p2pLoanRateService.remove(new Long(id));
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
		P2pLoanRate p2pLoanRate=p2pLoanRateService.get(rateId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(p2pLoanRate));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(p2pLoanRate.getRateId()==null){
			p2pLoanRateService.save(p2pLoanRate);
		}else{
			P2pLoanRate orgP2pLoanRate=p2pLoanRateService.get(p2pLoanRate.getRateId());
			try{
				BeanUtil.copyNotNullProperties(orgP2pLoanRate, p2pLoanRate);
				p2pLoanRateService.save(orgP2pLoanRate);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
