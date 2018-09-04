package com.credit.proj.mortgage.product.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageProduct;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

public interface ProductService {

	//异步添加记录
	public void addProduct(ProcreditMortgageProduct procreditMortgageProduct,ProcreditMortgage procreditMortgage) throws Exception;
	
	//从视图查询数据显示在详情页面
	public List seeProductForUpdate(int id) throws Exception;
	
	//查看详情从存货商品表
	@SuppressWarnings("unchecked")
	public List seeProduct(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateProduct(int mortgageid,ProcreditMortgageProduct procreditMortgageProduct,ProcreditMortgage procreditMortgage) throws Exception;
	
	public List getProductByObjectType(int mortgageid,String objectType);
	
	public void addPawnProduct(ProcreditMortgageProduct procreditMortgageProduct,PawnItemsList pawnItemsList);
	
	public List seeProductByObjectType(int mortgageid,String objectType);
}
