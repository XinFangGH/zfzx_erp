package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.admin.InStock;
import com.zhiwei.credit.model.admin.OfficeGoods;
import com.zhiwei.credit.service.admin.InStockService;
import com.zhiwei.credit.service.admin.OfficeGoodsService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class InStockAction extends BaseAction{
	@Resource
	private InStockService inStockService;
	private InStock inStock;
	@Resource
	private OfficeGoodsService officeGoodsService;
	
	private Long buyId;

	public Long getBuyId() {
		return buyId;
	}

	public void setBuyId(Long buyId) {
		this.buyId = buyId;
	}

	public InStock getInStock() {
		return inStock;
	}

	public void setInStock(InStock inStock) {
		this.inStock = inStock;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<InStock> list= inStockService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer=JsonUtil.getJSONSerializer("inDate");
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
				inStockService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	/*
	 * 导出Excel
	 * 
	 * */
	public String toExcel(){
		List list = null;
		String [] tableHeader = {"流水号","入库日期","用品编号","用品名称","供应商","购买人","数量","单价","总额"};
		try {
			list  = inStockService.getAll();
			ExcelHelper.export2(list,tableHeader,"办公用品入库信息("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+")");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		InStock inStock=inStockService.get(buyId);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer=JsonUtil.getJSONSerializer("inDate");
		sb.append(serializer.exclude(new String[]{"class"}).serialize(inStock));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss-SSS"); //自动生成入库单号
		inStock.setStockNo(sdf.format(new Date()));
		Integer inCount= inStock.getInCounts();
		BigDecimal price=inStock.getPrice();
		BigDecimal amount=null;
		if(inCount!=null&&price!=null){
			amount=price.multiply(BigDecimal.valueOf(inCount));//总额的计算
		}
		inStock.setAmount(amount);
		Long goodsId=inStock.getGoodsId();
		OfficeGoods goods=officeGoodsService.get(goodsId);
		if(inStock.getBuyId()==null){
		    goods.setStockCounts(goods.getStockCounts()+inStock.getInCounts());
		}else{
			Integer newInCount=inStock.getInCounts(); //修改之后的入库数量
			Integer oldInCount=inStockService.findInCountByBuyId(inStock.getBuyId());//修改前的入库数量
			if(!oldInCount.equals(newInCount)){
				goods.setStockCounts(goods.getStockCounts()-oldInCount+newInCount);
			}
		}
		inStockService.save(inStock);
		officeGoodsService.save(goods);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
