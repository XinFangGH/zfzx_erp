package com.zhiwei.credit.action.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.p2p.loan.P2pLoanApplyStep;
import com.zhiwei.credit.model.p2p.loan.P2pLoanProduct;
import com.zhiwei.credit.service.p2p.loan.P2pLoanApplyStepService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanProductService;
/**
 * 
 * @author 
 *
 */
public class P2pLoanProductAction extends BaseAction{
	@Resource
	private P2pLoanProductService p2pLoanProductService;
	@Resource
	private P2pLoanApplyStepService p2pLoanApplyStepService;
	private P2pLoanProduct p2pLoanProduct;
	
	private Long productId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public P2pLoanProduct getP2pLoanProduct() {
		return p2pLoanProduct;
	}

	public void setP2pLoanProduct(P2pLoanProduct p2pLoanProduct) {
		this.p2pLoanProduct = p2pLoanProduct;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		String Q_productName = this.getRequest().getParameter("Q_productName");
		String Q_productState = this.getRequest().getParameter("Q_productState");
		String Q_userScope = this.getRequest().getParameter("Q_userScope");
		String Q_operationType = this.getRequest().getParameter("Q_operationType");
		if(Q_productName!=null&&!Q_productName.equals("")){
			filter.addFilter("Q_productName_S_LK", Q_productName);
		}
		if(Q_productState!=null&&!Q_productState.equals("")){
			filter.addFilter("Q_productState_L_EQ", Q_productState);
		}
		if(Q_userScope!=null&&!Q_userScope.equals("")){
			filter.addFilter("Q_userScope_S_LK", Q_userScope);
		}
		if(Q_operationType!=null&&!Q_operationType.equals("")){
			filter.addFilter("Q_operationType_S_EQ", Q_operationType);
		}
		String state=this.getRequest().getParameter("productState");
		if(null!=state && !"".equals(state) && !"undefined".equals(state) && !"ALL".equals(state)){
			filter.addFilter("Q_productState_L_EQ",state);
		}
		List<P2pLoanProduct> list= p2pLoanProductService.getAll(filter);
		
		Type type=new TypeToken<List<P2pLoanProduct>>(){}.getType();
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
		String ids=getRequest().getParameter("ids");
		if(ids!=null){
			String id[] = ids.split(",");
			for(int i=0;i<id.length;i++){
				p2pLoanProductService.remove(new Long(id[i]));
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
		P2pLoanProduct p2pLoanProduct=p2pLoanProductService.get(productId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(p2pLoanProduct));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(p2pLoanProduct.getProductId()==null){
			p2pLoanProduct.setProductState(Long.valueOf("1"));
			p2pLoanProductService.save(p2pLoanProduct);
		}else{
			String isSave=this.getRequest().getParameter("isSave");
			P2pLoanProduct orgP2pLoanProduct=p2pLoanProductService.get(p2pLoanProduct.getProductId());
			try{
				BeanUtil.copyNotNullProperties(orgP2pLoanProduct, p2pLoanProduct);
				if("1".equals(isSave)){
					orgP2pLoanProduct.setProductState(Long.valueOf("2"));
				}
				p2pLoanProductService.save(orgP2pLoanProduct);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 修改产品状态
	 */
	public String updateProduct(){
		String productId = this.getRequest().getParameter("productId");
		String productState = this.getRequest().getParameter("productState");
		if(productState!=null&&!productState.equals("")&&productId!=null&&!productId.equals("")){
			P2pLoanProduct product=p2pLoanProductService.get(Long.valueOf(productId));
			if(product!=null){
				//发布项目，判断是否配置了流程
				if(productState.equals("2")){
					QueryFilter filter = new QueryFilter(this.getRequest());
					filter.addFilter("Q_productId_L_EQ", productId);
					List<P2pLoanApplyStep> step = p2pLoanApplyStepService.getAll(filter);
					if(step.size()>0){
						product.setProductState(Long.valueOf(productState));
						p2pLoanProductService.save(product);
						setJsonString("{success:true,msg:'操作成功'}");
					}else{
						setJsonString("{success:true,msg:'该产品未配置流程不能发布!'}");
					}
				}else{
					product.setProductState(Long.valueOf(productState));
					p2pLoanProductService.save(product);
					setJsonString("{success:true,msg:'操作成功'}");
				}
				
			}
		}
		return SUCCESS;
		
	}
	
	public void excelProduct(){
		QueryFilter filter=new QueryFilter(getRequest());
		String Q_productName = this.getRequest().getParameter("Q_productName");
		String Q_productState = this.getRequest().getParameter("Q_productState");
		String Q_userScope = this.getRequest().getParameter("Q_userScope");
		String Q_operationType = this.getRequest().getParameter("Q_operationType");
		String productState = this.getRequest().getParameter("productState");//产品状态
		if(null!=productState && !"".equals(productState)){
			filter.addFilter("Q_productState_L_EQ",productState);
		}
		if(Q_productName!=null&&!Q_productName.equals("")){
			filter.addFilter("Q_productName_S_LK", Q_productName);
		}
		if(Q_productState!=null&&!Q_productState.equals("")){
			filter.addFilter("Q_productState_L_EQ", Q_productState);
		}
		if(Q_userScope!=null&&!Q_userScope.equals("")){
			filter.addFilter("Q_userScope_S_LK", Q_userScope);
		}
		if(Q_operationType!=null&&!Q_operationType.equals("")){
			filter.addFilter("Q_operationType_S_EQ", Q_operationType);
		}
		filter.addFilter("Q_productState_L_GT", "0");
		List<P2pLoanProduct> list= p2pLoanProductService.getAll(filter);
		for(P2pLoanProduct pro:list){
			if(pro.getOperationType().equals("person")){
				pro.setOperationTypeValue("个人贷");
			}else if(pro.getOperationType().equals("enterprise")){
				pro.setOperationTypeValue("企业贷");
			}
			if(pro.getProductState()==1){
				pro.setProductStateValue("填写中");
			}else if(pro.getProductState()==2){
				pro.setProductStateValue("发布中");
			}else if(pro.getProductState()==3){
				pro.setProductStateValue("已关闭");
			}
		}
		String[] tableHeader = {"序号", "产品名称", "业务品种", "使用范围","状态"};
		String[] fields = {"productName","operationTypeValue","userScope","productStateValue"};
		try {
			ExportExcel.export(tableHeader, fields, list,"贷款产品管理", P2pLoanProduct.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
