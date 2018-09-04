package com.zhiwei.credit.action.creditFlow.finance.plateFormFinanceManage;
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


import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepaymentDao;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormBidIncomeDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepayment;
import com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepaymentService;
/**
 * 
 * @author 
 *
 */
public class SlRiskguaranteemoneyRepaymentAction extends BaseAction{
	@Resource
	private SlRiskguaranteemoneyRepaymentService slRiskguaranteemoneyRepaymentService;
	@Resource
	private SlRiskguaranteemoneyRepaymentDao slRiskguaranteemoneyRepaymentDao;
	private SlRiskguaranteemoneyRepayment slRiskguaranteemoneyRepayment;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SlRiskguaranteemoneyRepayment getSlRiskguaranteemoneyRepayment() {
		return slRiskguaranteemoneyRepayment;
	}

	public void setSlRiskguaranteemoneyRepayment(SlRiskguaranteemoneyRepayment slRiskguaranteemoneyRepayment) {
		this.slRiskguaranteemoneyRepayment = slRiskguaranteemoneyRepayment;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlRiskguaranteemoneyRepayment> list= slRiskguaranteemoneyRepaymentService.getAll(filter);
		
		Type type=new TypeToken<List<SlRiskguaranteemoneyRepayment>>(){}.getType();
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
				slRiskguaranteemoneyRepaymentService.remove(new Long(id));
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
		SlRiskguaranteemoneyRepayment slRiskguaranteemoneyRepayment=slRiskguaranteemoneyRepaymentService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slRiskguaranteemoneyRepayment));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slRiskguaranteemoneyRepayment.getId()==null){
			slRiskguaranteemoneyRepaymentService.save(slRiskguaranteemoneyRepayment);
		}else{
			SlRiskguaranteemoneyRepayment orgSlRiskguaranteemoneyRepayment=slRiskguaranteemoneyRepaymentService.get(slRiskguaranteemoneyRepayment.getId());
			try{
				BeanUtil.copyNotNullProperties(orgSlRiskguaranteemoneyRepayment, slRiskguaranteemoneyRepayment);
				slRiskguaranteemoneyRepaymentService.save(orgSlRiskguaranteemoneyRepayment);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 
	 * @return
	 */
	public String getpunishmentRecord(){
		List<SlRiskguaranteemoneyRepayment> list=slRiskguaranteemoneyRepaymentDao.getpunishmentRecord(this.getRequest(),start,limit);
		List<SlRiskguaranteemoneyRepayment> listcount=slRiskguaranteemoneyRepaymentDao.getpunishmentRecord(this.getRequest(),null,null);
		Type type = new TypeToken<List<SlRiskguaranteemoneyRepayment>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
}
