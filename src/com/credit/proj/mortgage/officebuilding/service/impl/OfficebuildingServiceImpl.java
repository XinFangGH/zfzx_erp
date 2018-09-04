package com.credit.proj.mortgage.officebuilding.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageOfficebuilding;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.officebuilding.service.OfficebuildingService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class OfficebuildingServiceImpl implements  OfficebuildingService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(OfficebuildingServiceImpl.class);
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
	public void addOfficebuilding(ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageOfficebuilding.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageOfficebuilding);
			procreditMortgage.setDywId(procreditMortgageOfficebuilding.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
				//log.info("新增商铺写字楼抵押贷款信息成功!!!");
//				JsonUtil.jsonFromObject("新增商铺写字楼抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增商铺写字楼抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增商铺写字楼抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从商品写字楼表查询数据显示在更新和详情页面
	public List seeOfficebuilding(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortOfficeBuilding a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询商铺写字楼抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateOfficebuilding(int mortgageid,ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isUpdateOk = false;
		ProcreditMortgageOfficebuilding projMorOffice = null;
		if(procreditMortgageOfficebuilding != null){
			projMorOffice = updateMorOfficeInfo(mortgageid,procreditMortgageOfficebuilding);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorOffice);
				if(isUpdateOk){
					//log.info("更新反担保商铺写字楼抵押贷款信息成功!!!");
//					JsonUtil.jsonFromObject("更新商铺写字楼抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新商铺写字楼抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新商铺写字楼抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//查看详情--从视图查询数据显示在详情页面
	public List seeOfficebuildingForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageOfficebuilding a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询商铺写字楼抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	private ProcreditMortgageOfficebuilding updateMorOfficeInfo(int mortgageid,ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding){
		ProcreditMortgageOfficebuilding procMorOfficebuilding = null;
		try{
			List list=this.seeOfficebuildingForUpdate(mortgageid);
			procMorOfficebuilding=(ProcreditMortgageOfficebuilding)list.get(0);
			if(procMorOfficebuilding != null){
				procMorOfficebuilding.setHouseaddress(procreditMortgageOfficebuilding.getHouseaddress());//房地产地点
				procMorOfficebuilding.setCertificatenumber(procreditMortgageOfficebuilding.getCertificatenumber());//证件号码
				procMorOfficebuilding.setPropertyperson(procreditMortgageOfficebuilding.getPropertyperson());//产权人
				procMorOfficebuilding.setMutualofperson(procreditMortgageOfficebuilding.getMutualofperson());//共有人
				procMorOfficebuilding.setFloors(procreditMortgageOfficebuilding.getFloors());//层高
				procMorOfficebuilding.setBuildacreage(procreditMortgageOfficebuilding.getBuildacreage());//建筑面积(M2)
				procMorOfficebuilding.setMortgagesbalance(procreditMortgageOfficebuilding.getMortgagesbalance());//按揭余额(万元)
				procMorOfficebuilding.setPropertyrightid(procreditMortgageOfficebuilding.getPropertyrightid());//产权性质
				procMorOfficebuilding.setConstructiontypeid(procreditMortgageOfficebuilding.getConstructiontypeid());//建筑式样
				procMorOfficebuilding.setConstructionframeid(procreditMortgageOfficebuilding.getConstructionframeid());//建筑结构
				procMorOfficebuilding.setHousetypeid(procreditMortgageOfficebuilding.getHousetypeid());//户型结构
				procMorOfficebuilding.setDescriptionid(procreditMortgageOfficebuilding.getDescriptionid());//地段描述
				procMorOfficebuilding.setRegisterinfoid(procreditMortgageOfficebuilding.getRegisterinfoid());//登记情况
				procMorOfficebuilding.setRentone(procreditMortgageOfficebuilding.getRentone());//同等房产租金1(元/月/M2)
				procMorOfficebuilding.setRenttow(procreditMortgageOfficebuilding.getRenttow());//同等房产租金2(元/月/M2)
				procMorOfficebuilding.setExchangepriceone(procreditMortgageOfficebuilding.getExchangepriceone());//同等房产单位交易单价1(元/M2)
				
				procMorOfficebuilding.setExchangepricetow(procreditMortgageOfficebuilding.getExchangepricetow());//同等房产单位交易单价2(元/M2)
				procMorOfficebuilding.setExchangepricethree(procreditMortgageOfficebuilding.getExchangepricethree());//同等房产单位交易单价3(元/M2)
				procMorOfficebuilding.setBuildtime(procreditMortgageOfficebuilding.getBuildtime());//建成年份
				procMorOfficebuilding.setResidualyears(procreditMortgageOfficebuilding.getResidualyears());//剩余年限(年)
				procMorOfficebuilding.setExchangefinalprice(procreditMortgageOfficebuilding.getExchangefinalprice());//新房交易单价(元/M2)
				procMorOfficebuilding.setLeaseholdrangeprice(procreditMortgageOfficebuilding.getLeaseholdrangeprice());//租赁模型估算(万元)
				procMorOfficebuilding.setModelrangeprice(procreditMortgageOfficebuilding.getModelrangeprice());//模型估价(万元)
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新商铺写字楼抵押贷款信息出错:"+e.getMessage());
		}
		return procMorOfficebuilding;
	}

	@Override
	public List seeOfficebuildingByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageOfficebuilding a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnOfficebuilding(
			ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding,
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
				procreditMortgageOfficebuilding.setMortgageid(pawnItemsList.getPawnItemId().intValue());
				if(null==procreditMortgageOfficebuilding.getId()){
					creditBaseDao.saveDatas(procreditMortgageOfficebuilding);
				}else{
					ProcreditMortgageOfficebuilding orgProcreditMortgageOfficebuilding=(ProcreditMortgageOfficebuilding) creditBaseDao.getById(ProcreditMortgageOfficebuilding.class, procreditMortgageOfficebuilding.getId());
					BeanUtil.copyNotNullProperties(orgProcreditMortgageOfficebuilding, procreditMortgageOfficebuilding);
					creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageOfficebuilding);
				}
				JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
			}catch(Exception e){
				e.printStackTrace();
			}
	}

	@Override
	public List getOfficebuildingByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortOfficeBuilding a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
