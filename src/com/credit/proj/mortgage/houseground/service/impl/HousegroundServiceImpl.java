package com.credit.proj.mortgage.houseground.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageHouseground;
import com.credit.proj.mortgage.houseground.service.HousegroundService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class HousegroundServiceImpl implements  HousegroundService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(HousegroundServiceImpl.class);
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
	public void addHouseground(ProcreditMortgageHouseground procreditMortgageHouseground,ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageHouseground.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageHouseground);
			procreditMortgage.setDywId(procreditMortgageHouseground.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
//				JsonUtil.jsonFromObject("新增住宅用地抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增住宅用地抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增住宅用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从住宅用地表查询数据显示在更新页面
	public List seeHouseground(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortHouseGround a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询住宅用地抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateHouseground(int mortgageid,ProcreditMortgageHouseground procreditMortgageHouseground,
			ProcreditMortgage procreditMortgage) throws Exception{
		boolean isUpdateOk = false;
		
		ProcreditMortgageHouseground projMorHouseg = null;
		if(procreditMortgageHouseground != null){
			projMorHouseg = updateMorHouseGroundInfo(mortgageid,procreditMortgageHouseground);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorHouseg);
				if(isUpdateOk){
//					JsonUtil.jsonFromObject("更新住宅用地抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新住宅用地抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新住宅用地抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeHousegroundForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageHouseground a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询住宅用地抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	private ProcreditMortgageHouseground updateMorHouseGroundInfo(int mortgageid,ProcreditMortgageHouseground procreditMortgageHouseground){
		ProcreditMortgageHouseground projMorthouseground = null;
		
		try{
			List list=this.seeHousegroundForUpdate(mortgageid);
			projMorthouseground=(ProcreditMortgageHouseground)list.get(0);
			if(projMorthouseground != null){
				projMorthouseground.setAddress(procreditMortgageHouseground.getAddress());//土地地点
				projMorthouseground.setCertificatenumber(procreditMortgageHouseground.getCertificatenumber());//证件号码
				projMorthouseground.setDescriptionid(procreditMortgageHouseground.getDescriptionid());//地段描述
				projMorthouseground.setAnticipateacreage(procreditMortgageHouseground.getAnticipateacreage());//预规划住宅面积(M2)
				projMorthouseground.setMortgagesbalance(procreditMortgageHouseground.getMortgagesbalance());//土地抵质押贷款余额(万元)
				projMorthouseground.setExchangeprice(procreditMortgageHouseground.getExchangeprice());//同等土地成交单价1(元/M2)
				projMorthouseground.setExchangepriceone(procreditMortgageHouseground.getExchangepriceone());//同等土地成交单价2(元/M2)
				
				projMorthouseground.setExchangepricetow(procreditMortgageHouseground.getExchangepricetow());//同等土地成交价格3(元/M2)
				projMorthouseground.setRegisterinfoid(procreditMortgageHouseground.getRegisterinfoid());//登记情况
				projMorthouseground.setPropertyperson(procreditMortgageHouseground.getPropertyperson());//产权人
				projMorthouseground.setAcreage(procreditMortgageHouseground.getAcreage());//占地面积(M2)
				projMorthouseground.setModelrangepriceone(procreditMortgageHouseground.getModelrangepriceone());//模型估价1(万元)
				projMorthouseground.setModelrangepricetow(procreditMortgageHouseground.getModelrangepricetow());//模型估价2(万元)
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新住宅用地抵押贷款信息出错:"+e.getMessage());
		}
		return projMorthouseground;
	}

	@Override
	public List seeHousegroundByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageHouseground a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnHouseground(
			ProcreditMortgageHouseground procreditMortgageHouseground,
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
			procreditMortgageHouseground.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageHouseground.getId()){
				creditBaseDao.saveDatas(procreditMortgageHouseground);
			}else{
				ProcreditMortgageHouseground orgProcreditMortgageHouseground=(ProcreditMortgageHouseground) creditBaseDao.getById(ProcreditMortgageHouseground.class, procreditMortgageHouseground.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageHouseground, procreditMortgageHouseground);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageHouseground);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
}

	@Override
	public List getHousegroundByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortHouseGround a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
