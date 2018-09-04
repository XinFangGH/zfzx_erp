package com.credit.proj.mortgage.industry.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageIndustry;
import com.credit.proj.mortgage.industry.service.IndustryService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class IndustryServiceImpl implements  IndustryService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(IndustryServiceImpl.class);
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
	public void addIndustry(ProcreditMortgageIndustry procreditMortgageIndustry,ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageIndustry.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageIndustry);
			procreditMortgage.setDywId(procreditMortgageIndustry.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
//				JsonUtil.jsonFromObject("新增工业用地抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增工业用地抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增工业用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从股权表查询数据显示在更新页面
	public List seeIndustry(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortIndustry a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询工业用地抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateIndustry(int mortgageid,ProcreditMortgageIndustry procreditMortgageIndustry,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isUpdateOk = false;
		ProcreditMortgageIndustry projMorIndustry = null;
		if(procreditMortgageIndustry != null){
			projMorIndustry = updateMorIndustryInfo(mortgageid,procreditMortgageIndustry);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorIndustry);
				if(isUpdateOk){
//					JsonUtil.jsonFromObject("更新工业用地抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新工业用地抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新工业用地抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeIndustryForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageIndustry a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询工业用地抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	private ProcreditMortgageIndustry updateMorIndustryInfo(int mortgageid,ProcreditMortgageIndustry procreditMortgageIndustry){
		ProcreditMortgageIndustry projMortIndus = null;
		
		try{
			List list=this.seeIndustryForUpdate(mortgageid);
			projMortIndus=(ProcreditMortgageIndustry)list.get(0);
			if(projMortIndus != null){
				projMortIndus.setAddress(procreditMortgageIndustry.getAddress());//土地地点
				projMortIndus.setCertificatenumber(procreditMortgageIndustry.getCertificatenumber());//证件号码
				projMortIndus.setPropertyperson(procreditMortgageIndustry.getPropertyperson());//产权人
				projMortIndus.setOccupyacreage(procreditMortgageIndustry.getOccupyacreage());//占地面积(M2)
				projMortIndus.setGroundcharacterid(procreditMortgageIndustry.getGroundcharacterid());//土地性质
				projMortIndus.setDescriptionid(procreditMortgageIndustry.getDescriptionid());//地段描述
				projMortIndus.setRegisterinfoid(procreditMortgageIndustry.getRegisterinfoid());//登记情况
				projMortIndus.setExchangepriceone(procreditMortgageIndustry.getExchangepriceone());//同等土地成交价格1(元/M2)
				projMortIndus.setExchangepricetow(procreditMortgageIndustry.getExchangepricetow());//同等土地成交价格2(元/M2)
				projMortIndus.setBuytime(procreditMortgageIndustry.getBuytime());//购买年份
				projMortIndus.setResidualyears(procreditMortgageIndustry.getResidualyears());//剩余年限(年)
				projMortIndus.setMortgagesbalance(procreditMortgageIndustry.getMortgagesbalance());//土地抵质押贷款余额(万元)
				projMortIndus.setRentpriceformonthone(procreditMortgageIndustry.getRentpriceformonthone());//每月出租价格1(元/月/M2)
				projMortIndus.setRentpriceformonthtow(procreditMortgageIndustry.getRentpriceformonthtow());//每月出租价格2(元/月/M2)
				projMortIndus.setExchangeprice(procreditMortgageIndustry.getExchangeprice());//市场交易价格(元/M2)
				projMortIndus.setTenancyrangeprice(procreditMortgageIndustry.getTenancyrangeprice());//租赁模型估值(万元)
				projMortIndus.setVenditionrangeprice(procreditMortgageIndustry.getVenditionrangeprice());//销售模型估值(万元)
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新工业用地抵押贷款信息出错:"+e.getMessage());
		}
		return projMortIndus;
	}

	@Override
	public List seeIndustryByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageIndustry a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnIndustry(
			ProcreditMortgageIndustry procreditMortgageIndustry,
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
			procreditMortgageIndustry.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageIndustry.getId()){
				creditBaseDao.saveDatas(procreditMortgageIndustry);
			}else{
				ProcreditMortgageIndustry orgProcreditMortgageIndustry=(ProcreditMortgageIndustry) creditBaseDao.getById(ProcreditMortgageIndustry.class, procreditMortgageIndustry.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageIndustry, procreditMortgageIndustry);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageIndustry);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List getIndustryByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortIndustry a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
