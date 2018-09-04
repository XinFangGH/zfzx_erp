package com.zhiwei.credit.action.creditFlow.customer.person;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;
import com.zhiwei.credit.service.creditFlow.customer.person.BpMoneyBorrowDemandService;
/**
 * 
 * @author 
 *
 */
public class BpMoneyBorrowDemandAction extends BaseAction{
	@Resource
	private BpMoneyBorrowDemandService bpMoneyBorrowDemandService;
	private BpMoneyBorrowDemand bpMoneyBorrowDemand;
	
	private Long borrowid;

	public Long getBorrowid() {
		return borrowid;
	}

	public void setBorrowid(Long borrowid) {
		this.borrowid = borrowid;
	}

	public BpMoneyBorrowDemand getBpMoneyBorrowDemand() {
		return bpMoneyBorrowDemand;
	}

	public void setBpMoneyBorrowDemand(BpMoneyBorrowDemand bpMoneyBorrowDemand) {
		this.bpMoneyBorrowDemand = bpMoneyBorrowDemand;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpMoneyBorrowDemand> list= bpMoneyBorrowDemandService.getAll(filter);
		
		Type type=new TypeToken<List<BpMoneyBorrowDemand>>(){}.getType();
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
				bpMoneyBorrowDemandService.remove(new Long(id));
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
		BpMoneyBorrowDemand bpMoneyBorrowDemand=bpMoneyBorrowDemandService.get(borrowid);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpMoneyBorrowDemand));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpMoneyBorrowDemand.getBorrowid()==null){
			bpMoneyBorrowDemandService.save(bpMoneyBorrowDemand);
		}else{
			BpMoneyBorrowDemand orgBpMoneyBorrowDemand=bpMoneyBorrowDemandService.get(bpMoneyBorrowDemand.getBorrowid().longValue());
			try{
				BeanUtil.copyNotNullProperties(orgBpMoneyBorrowDemand, bpMoneyBorrowDemand);
				bpMoneyBorrowDemandService.save(orgBpMoneyBorrowDemand);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
