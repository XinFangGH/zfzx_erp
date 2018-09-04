package com.zhiwei.credit.action.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUserAccountInfo;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUseraccount;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductUseraccountService;
/**
 * 
 * @author 
 *
 */
public class PlFinanceProductUseraccountAction extends BaseAction{
	@Resource
	private PlFinanceProductUseraccountService plFinanceProductUseraccountService;
	private PlFinanceProductUseraccount plFinanceProductUseraccount;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlFinanceProductUseraccount getPlFinanceProductUseraccount() {
		return plFinanceProductUseraccount;
	}

	public void setPlFinanceProductUseraccount(PlFinanceProductUseraccount plFinanceProductUseraccount) {
		this.plFinanceProductUseraccount = plFinanceProductUseraccount;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		PagingBean  pb=new PagingBean(start,limit);
		Map<String,String> request=ContextUtil.createResponseMap(getRequest());
		List<PlFinanceProductUseraccount> list= plFinanceProductUseraccountService.getUserAccountList(request,pb);
		Type type=new TypeToken<List<PlFinanceProductUseraccount>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	public void exportProductUseraccountToExcel(){
		try {
			PagingBean pb = null;
			Map<String,String> request=ContextUtil.createResponseMap(getRequest());
			List<PlFinanceProductUseraccount> list= plFinanceProductUseraccountService.getUserAccountList(request,pb);
			String [] tableHeader = {"序号","会员账号","开户名","账户金额","累计收益","昨日收益","开户日期","交易状态"};
			ExcelHelper.ExportProductUseraccountToExcel(list,tableHeader,"理财专户列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				plFinanceProductUseraccountService.remove(new Long(id));
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
		PlFinanceProductUseraccount plFinanceProductUseraccount=plFinanceProductUseraccountService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plFinanceProductUseraccount));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plFinanceProductUseraccount.getId()==null){
			plFinanceProductUseraccountService.save(plFinanceProductUseraccount);
		}else{
			PlFinanceProductUseraccount orgPlFinanceProductUseraccount=plFinanceProductUseraccountService.get(plFinanceProductUseraccount.getId());
			try{
				BeanUtil.copyNotNullProperties(orgPlFinanceProductUseraccount, plFinanceProductUseraccount);
				plFinanceProductUseraccountService.save(orgPlFinanceProductUseraccount);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	//更新操作账户的状态
	public String updateAccountStatus(){
		PlFinanceProductUseraccount plFinanceProductUseraccount=plFinanceProductUseraccountService.get(id);
		String accountStatus=this.getRequest().getParameter("accountStatus");
		if(accountStatus!=null&&!"".equals(accountStatus)){
			if(accountStatus.equals("1")){
				plFinanceProductUseraccount.setAccountStatus(Short.valueOf("1"));
				plFinanceProductUseraccountService.merge(plFinanceProductUseraccount);
				setJsonString("{success:true,msg:'解锁成功'}");
			}else if(accountStatus.equals("-1")){
				plFinanceProductUseraccount.setAccountStatus(Short.valueOf("-1"));
				plFinanceProductUseraccountService.merge(plFinanceProductUseraccount);
				setJsonString("{success:true,msg:'锁定成功'}");
			}else{
				setJsonString("{success:true,msg:'无效状态操作'}");
			}
		}else{
			setJsonString("{success:true,msg:'无效操作'}");
		}
		return SUCCESS;
	}
}
