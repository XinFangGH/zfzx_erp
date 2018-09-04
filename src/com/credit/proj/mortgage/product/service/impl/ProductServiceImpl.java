package com.credit.proj.mortgage.product.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageProduct;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.product.service.ProductService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class ProductServiceImpl implements  ProductService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(ProductServiceImpl.class);
	private CreditBaseDao creditBaseDao ;
	private MortgageService mortgageService;
	private VehicleService vehicleService;
	@Resource
	private PawnItemsListDao pawnItemsListDao;
	@Resource
	private SlProcreditMaterialsService slProcreditMaterialsService;
	
	public CreditBaseDao getCreditBaseDao() {
		return creditBaseDao;
	}

	public void setCreditBaseDao(CreditBaseDao creditBaseDao) {
		this.creditBaseDao = creditBaseDao;
	}

	public MortgageService getMortgageService() {
		return mortgageService;
	}

	public void setMortgageService(MortgageService mortgageService) {
		this.mortgageService = mortgageService;
	}

	public VehicleService getVehicleService() {
		return vehicleService;
	}

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	//添加记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void addProduct(ProcreditMortgageProduct procreditMortgageProduct,ProcreditMortgage procreditMortgage) throws Exception{
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageProduct.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageProduct);
			procreditMortgage.setDywId(procreditMortgageProduct.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
				//log.info("新增反担保存货商品抵押贷款信息成功!!!");
//				JsonUtil.jsonFromObject("新增存货商品抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增存货商品抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增存货商品抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	//查看详情--从商品视图查询数据显示在查看详情页面
	public List seeProduct(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortProduct a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询存货商品抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProduct(int mortgageid,ProcreditMortgageProduct procreditMortgageProduct,ProcreditMortgage procreditMortgage) throws Exception{
		boolean isUpdateOk = false;
		
		ProcreditMortgageProduct projMorProduct = null;
		if(procreditMortgageProduct != null){
			projMorProduct = updateMorProductInfo(mortgageid,procreditMortgageProduct);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorProduct);
				if(isUpdateOk){
					//log.info("更新反担保存货商品抵押贷款信息成功!!!");
//					JsonUtil.jsonFromObject("更新存货商品抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新存货商品抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新存货商品抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	//根据mortgageid查询实体并且更新
	public List seeProductForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageProduct a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询存货商品抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	private ProcreditMortgageProduct updateMorProductInfo(int mortgageid,ProcreditMortgageProduct procreditMortgageProduct){
		ProcreditMortgageProduct projMortProd = null;
		
		try{
			List list=this.seeProductForUpdate(mortgageid);
			projMortProd=(ProcreditMortgageProduct)list.get(0);
			if(projMortProd != null){
				projMortProd.setName(procreditMortgageProduct.getName());//商品名称
				projMortProd.setController(procreditMortgageProduct.getController());//控制权人
				projMortProd.setDepositary(procreditMortgageProduct.getDepositary());//存放地点
				projMortProd.setPriceone(procreditMortgageProduct.getPriceone());//市场单价1(元)
				projMortProd.setPricetow(procreditMortgageProduct.getPricetow());//市场单价2(元)
				projMortProd.setModelrangeprice(procreditMortgageProduct.getModelrangeprice());//模型估价(万元)
				projMortProd.setBrand(procreditMortgageProduct.getBrand());//品牌
				projMortProd.setType(procreditMortgageProduct.getType());//型号
				projMortProd.setAmount(procreditMortgageProduct.getAmount());//数量
				projMortProd.setControllertypeid(procreditMortgageProduct.getControllertypeid());//控制权方式
				projMortProd.setCommongradeid(procreditMortgageProduct.getCommongradeid());//通用程度
				projMortProd.setCashabilityid(procreditMortgageProduct.getCashabilityid());//变现能力
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新存货商品抵押贷款信息出错:"+e.getMessage());
		}
		return projMortProd;
	}

	@Override
	public List getProductByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageProduct a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnProduct(
			ProcreditMortgageProduct procreditMortgageProduct,
			PawnItemsList pawnItemsList) {
		try{
			if(null==pawnItemsList.getPawnItemId()){
				pawnItemsList.setPawnItemStatus("underway");
				pawnItemsListDao.save(pawnItemsList);
			}else{
				PawnItemsList orgPawnItems=pawnItemsListDao.get(pawnItemsList.getPawnItemId());
				BeanUtil.copyNotNullProperties(orgPawnItems, pawnItemsList);
				pawnItemsListDao.merge(orgPawnItems);
			}
			procreditMortgageProduct.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageProduct.getId()){
				creditBaseDao.saveDatas(procreditMortgageProduct);
			}else{
				ProcreditMortgageProduct orgProcreditMortgageProduct=(ProcreditMortgageProduct) creditBaseDao.getById(ProcreditMortgageProduct.class, procreditMortgageProduct.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageProduct, procreditMortgageProduct);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageProduct);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List seeProductByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortProduct a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
