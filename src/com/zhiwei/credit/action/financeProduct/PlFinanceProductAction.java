package com.zhiwei.credit.action.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.financeProduct.PlFinanceProduct;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.financeProduct.PlFinanceProductService;
/**
 * 
 * @author 
 *
 */
public class PlFinanceProductAction extends BaseAction{
	@Resource
	private PlFinanceProductService plFinanceProductService;
	private PlFinanceProduct plFinanceProduct;
	
	private Long id;
	private Integer typeId;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlFinanceProduct getPlFinanceProduct() {
		return plFinanceProduct;
	}

	public void setPlFinanceProduct(PlFinanceProduct plFinanceProduct) {
		this.plFinanceProduct = plFinanceProduct;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlFinanceProduct> list= plFinanceProductService.getAll(filter);
		
		Type type=new TypeToken<List<PlFinanceProduct>>(){}.getType();
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
				plFinanceProductService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 获取理财专户产品信息
	 * @return
	 */
	public String get(){
		
		List<PlFinanceProduct> list= plFinanceProductService.getAll();
		PlFinanceProduct plFinanceProduct=null;
		if(list!=null){
			if(typeId!=null){
				if(list.size()>=typeId&&typeId>0){
					plFinanceProduct=list.get(typeId-1);
				}
			}else{
				plFinanceProduct=list.get(0);
			}
		}
		//PlFinanceProduct plFinanceProduct=plFinanceProductService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plFinanceProduct));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plFinanceProduct.getId()==null){
			plFinanceProductService.save(plFinanceProduct);
		}else{
			PlFinanceProduct orgPlFinanceProduct=plFinanceProductService.get(plFinanceProduct.getId());
			try{
				BeanUtil.copyNotNullProperties(orgPlFinanceProduct, plFinanceProduct);
				plFinanceProductService.save(orgPlFinanceProduct);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String getList(){
		List<PlFinanceProduct> list= plFinanceProductService.getAll();
		if(null!=list && list.size()>0){
			StringBuffer buff = new StringBuffer("[");
			for (PlFinanceProduct glType : list) {
				buff.append("[").append(glType.getId()).append(",'")
						.append(glType.getProductName()).append("'],");
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}
}
