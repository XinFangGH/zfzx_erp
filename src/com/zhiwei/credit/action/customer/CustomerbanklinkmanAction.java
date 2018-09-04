package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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


import com.zhiwei.credit.model.customer.Customerbanklinkman;
import com.zhiwei.credit.service.customer.CustomerbanklinkmanService;
/**
 * 
 * @author 
 *
 */
public class CustomerbanklinkmanAction extends BaseAction{
	@Resource
	private CustomerbanklinkmanService customerbanklinkmanService;
	private Customerbanklinkman customerbanklinkman;
	
	private Long id;
	private Long enterpriseId;
	
	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customerbanklinkman getCustomerbanklinkman() {
		return customerbanklinkman;
	}

	public void setCustomerbanklinkman(Customerbanklinkman customerbanklinkman) {
		this.customerbanklinkman = customerbanklinkman;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		if(null!=enterpriseId){
			List<Customerbanklinkman> list= customerbanklinkmanService.getListByEntId(enterpriseId);
			StringBuffer buff = new StringBuffer("{success:true,result:");
			
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list));
			buff.append("}");
			
			jsonString=buff.toString();
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
				customerbanklinkmanService.remove(new Long(id));
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
		Customerbanklinkman customerbanklinkman=customerbanklinkmanService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(customerbanklinkman));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(customerbanklinkman.getId()==null){
			customerbanklinkmanService.save(customerbanklinkman);
		}else{
			Customerbanklinkman orgCustomerbanklinkman=customerbanklinkmanService.get(customerbanklinkman.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCustomerbanklinkman, customerbanklinkman);
				customerbanklinkmanService.save(orgCustomerbanklinkman);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
