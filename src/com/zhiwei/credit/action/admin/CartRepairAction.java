package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.CartRepair;
import com.zhiwei.credit.service.admin.CartRepairService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class CartRepairAction extends BaseAction{
	@Resource
	private CartRepairService cartRepairService;
	private CartRepair cartRepair;
	
	private Long repairId;

	public Long getRepairId() {
		return repairId;
	}

	public void setRepairId(Long repairId) {
		this.repairId = repairId;
	}

	public CartRepair getCartRepair() {
		return cartRepair;
	}

	public void setCartRepair(CartRepair cartRepair) {
		this.cartRepair = cartRepair;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<CartRepair> list= cartRepairService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer=JsonUtil.getJSONSerializer("repairDate");
		buff.append(serializer.exclude(new String[]{"class"}).serialize(list));
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
				cartRepairService.remove(new Long(id));
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
		CartRepair cartRepair=cartRepairService.get(repairId);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer=JsonUtil.getJSONSerializer("repairDate");
		sb.append(serializer.exclude(new String[]{"class","car.cartRepairs"}).serialize(cartRepair));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		cartRepairService.save(cartRepair);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
