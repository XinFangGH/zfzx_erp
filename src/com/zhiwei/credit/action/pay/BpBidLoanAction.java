package com.zhiwei.credit.action.pay;
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


import com.zhiwei.credit.model.pay.BpBidLoan;
import com.zhiwei.credit.service.pay.BpBidLoanService;
/**
 * 
 * @author 
 *
 */
public class BpBidLoanAction extends BaseAction{
	@Resource
	private BpBidLoanService bpBidLoanService;
	private BpBidLoan bpBidLoan;
	
	private Long bidLoanId;

	public Long getBidLoanId() {
		return bidLoanId;
	}

	public void setBidLoanId(Long bidLoanId) {
		this.bidLoanId = bidLoanId;
	}

	public BpBidLoan getBpBidLoan() {
		return bpBidLoan;
	}

	public void setBpBidLoan(BpBidLoan bpBidLoan) {
		this.bpBidLoan = bpBidLoan;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpBidLoan> list= bpBidLoanService.getAll(filter);
		
		Type type=new TypeToken<List<BpBidLoan>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
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
				bpBidLoanService.remove(new Long(id));
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
		BpBidLoan bpBidLoan=bpBidLoanService.get(bidLoanId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpBidLoan));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpBidLoan.getBidLoanId()==null){
			bpBidLoanService.save(bpBidLoan);
		}else{
			BpBidLoan orgBpBidLoan=bpBidLoanService.get(bpBidLoan.getBidLoanId());
			try{
				BeanUtil.copyNotNullProperties(orgBpBidLoan, bpBidLoan);
				bpBidLoanService.save(orgBpBidLoan);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
