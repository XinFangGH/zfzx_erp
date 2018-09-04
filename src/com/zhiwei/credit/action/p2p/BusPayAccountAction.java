package com.zhiwei.credit.action.p2p;
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


import com.zhiwei.credit.model.p2p.BusPayAccount;
import com.zhiwei.credit.service.p2p.BusPayAccountService;
/**
 * 
 * @author 
 *
 */
public class BusPayAccountAction extends BaseAction{
	@Resource
	private BusPayAccountService busPayAccountService;
	private BusPayAccount busPayAccount;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BusPayAccount getBusPayAccount() {
		return busPayAccount;
	}

	public void setBusPayAccount(BusPayAccount busPayAccount) {
		this.busPayAccount = busPayAccount;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BusPayAccount> list= busPayAccountService.getAll(filter);
		
		Type type=new TypeToken<List<BusPayAccount>>(){}.getType();
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
		try{
			String ids=getRequest().getParameter("ids");
			if(ids!=null){
				String[] temp=ids.split(",");
				for(String id:temp){
					busPayAccountService.remove(new Long(id));
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BusPayAccount busPayAccount=busPayAccountService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(busPayAccount));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(busPayAccount.getId()==null){
			busPayAccountService.save(busPayAccount);
		}else{
			BusPayAccount orgBusPayAccount=busPayAccountService.get(busPayAccount.getId());
			try{
				BeanUtil.copyNotNullProperties(orgBusPayAccount, busPayAccount);
				busPayAccountService.save(orgBusPayAccount);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
