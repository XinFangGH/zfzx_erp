package com.credit.proj.mortgage.house.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageHouse;
import com.credit.proj.mortgage.house.service.HouseService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class HouseServiceImpl implements  HouseService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(HouseServiceImpl.class);
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
	public void addHouse(ProcreditMortgageHouse procreditMortgageHouse,ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageHouse.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageHouse);
			procreditMortgage.setDywId(procreditMortgageHouse.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
				//log.info("新增住宅抵押贷款信息成功!!!");
//				JsonUtil.jsonFromObject("新增住宅抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增住宅抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增住宅抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从住宅表查询数据显示在更新页面
	public List seeHouse(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortHouse a where a.mortgageid=? and a.objectType='mortgage'",id);	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询住宅抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateHouse(int mortgageid,ProcreditMortgageHouse procreditMortgageHouse,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isUpdateOk = false;
		ProcreditMortgageHouse projMorHouse = null;
		if(procreditMortgageHouse != null){
			projMorHouse = updateMorHouseInfo(mortgageid,procreditMortgageHouse);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorHouse);
				if(isUpdateOk){
//					JsonUtil.jsonFromObject("更新住宅抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新住宅抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新住宅抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeHouseForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageHouse a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询住宅抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	private ProcreditMortgageHouse updateMorHouseInfo(int mortgageid,ProcreditMortgageHouse procreditMortgageHouse){
		ProcreditMortgageHouse proMorHouse = null;
		try{
			List list=this.seeHouseForUpdate(mortgageid);
			proMorHouse=(ProcreditMortgageHouse)list.get(0);
			if(proMorHouse != null){
				proMorHouse.setHouseaddress(procreditMortgageHouse.getHouseaddress());//房地产地点
				proMorHouse.setCertificatenumber(procreditMortgageHouse.getCertificatenumber());//证件号码
				proMorHouse.setPropertyperson(procreditMortgageHouse.getPropertyperson());//产权人
				proMorHouse.setMutualofperson(procreditMortgageHouse.getMutualofperson());//共有人
				proMorHouse.setBuildacreage(procreditMortgageHouse.getBuildacreage());//建筑面积(M2)
				proMorHouse.setBuildtime(procreditMortgageHouse.getBuildtime());//建成年份
				proMorHouse.setResidualyears(procreditMortgageHouse.getResidualyears());//剩余年限(年)
				proMorHouse.setPropertyrightid(procreditMortgageHouse.getPropertyrightid());//产权性质
				proMorHouse.setConstructiontypeid(procreditMortgageHouse.getConstructiontypeid());//建筑式样
				proMorHouse.setConstructionframeid(procreditMortgageHouse.getConstructionframeid());//建筑结构
				proMorHouse.setHousetypeid(procreditMortgageHouse.getHousetypeid());//户型结构
				proMorHouse.setDescriptionid(procreditMortgageHouse.getDescriptionid());//地段描述
				proMorHouse.setRegisterinfoid(procreditMortgageHouse.getRegisterinfoid());//登记情况
				proMorHouse.setExchangepriceone(procreditMortgageHouse.getExchangepriceone());//同等房产单位交易单价1(元/M2)
				proMorHouse.setExchangepricetow(procreditMortgageHouse.getExchangepricetow());//同等房产单位交易单价2(元/M2)
				proMorHouse.setExchangepricethree(procreditMortgageHouse.getExchangepricethree());//同等房产单位交易单价3(元/M2)
				proMorHouse.setMortgagesbalance(procreditMortgageHouse.getMortgagesbalance());//按揭余额(万元)
				proMorHouse.setExchangefinalprice(procreditMortgageHouse.getExchangefinalprice());//新房交易单价(元/M2)
				proMorHouse.setModelrangeprice(procreditMortgageHouse.getModelrangeprice());//模型估价(万元)
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新住宅抵押贷款信息出错:"+e.getMessage());
		}
		return proMorHouse;
	}

	@Override
	public List seeHouseByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageHouse a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnHouse(ProcreditMortgageHouse procreditMortgageHouse,
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
			procreditMortgageHouse.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageHouse.getId()){
				creditBaseDao.saveDatas(procreditMortgageHouse);
			}else{
				ProcreditMortgageHouse orgProcreditMortgageHouse=(ProcreditMortgageHouse) creditBaseDao.getById(ProcreditMortgageHouse.class, procreditMortgageHouse.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageHouse, procreditMortgageHouse);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageHouse);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List getHouseByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortHouse a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
