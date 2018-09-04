package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.customer.CusLinkman;
import com.zhiwei.credit.service.customer.CusLinkmanService;
import com.zhiwei.credit.service.customer.CustomerService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class CusLinkmanAction extends BaseAction{
	private Short isPrimary = (short)1;//isPrimary 表示为主要联系人
	@Resource
	private CusLinkmanService cusLinkmanService;
	@Resource
	private CustomerService customerService;
	
	private CusLinkman cusLinkman;
	
	private Long linkmanId;
	public Long getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(Long linkmanId) {
		this.linkmanId = linkmanId;
	}

	public CusLinkman getCusLinkman() {
		return cusLinkman;
	}

	public void setCusLinkman(CusLinkman cusLinkman) {
		this.cusLinkman = cusLinkman;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<CusLinkman> list= cusLinkmanService.getAll(filter);
		
		//Type type=new TypeToken<List<CusLinkman>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		//Gson gson=new Gson();
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[]{"class"}).serialize(list));
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
				cusLinkmanService.remove(new Long(id));
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
		CusLinkman cusLinkman=cusLinkmanService.get(linkmanId);
		
		//Gson gson=new Gson();
		JSONSerializer json = new JSONSerializer();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.exclude(new String[] {"class","custoemr.class"}).serialize(cusLinkman));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		boolean pass = false;
		StringBuffer buff = new StringBuffer("{");
		if(cusLinkman.getCustomerId()!=null){
			if(cusLinkman.getIsPrimary()!= 1){
				pass = true;
			}else{
				if(cusLinkmanService.checkMainCusLinkman(cusLinkman.getCustomerId(),cusLinkman.getLinkmanId())){
					pass = true;
				}else buff.append("msg:'该客户的主要联系人已存在,请保存为普通联系人!',");
			}
		}else buff.append("msg:'所属客户不能为空.',");
		
		if(pass){
			cusLinkman.setCustomer(customerService.get(cusLinkman.getCustomerId()));
			cusLinkmanService.save(cusLinkman);
			buff.append("success:true}");
		}else{
			buff.append("failure:true}");
		}
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String find(){
			QueryFilter filter=new QueryFilter(getRequest());
			filter.addSorted("isPrimary", "desc");
			List<CusLinkman> list= cusLinkmanService.getAll(filter);
			
			StringBuffer buff = new StringBuffer("[");
			for(CusLinkman cusLinkman: list){
				buff.append("['"+cusLinkman.getLinkmanId()+"','"+cusLinkman.getFullname()+"'],");
			}
			if(list.size()!=0){
				buff.deleteCharAt(buff.length()-1);
			}
			buff.append("]");
			setJsonString(buff.toString());
			return SUCCESS;
	}
}
