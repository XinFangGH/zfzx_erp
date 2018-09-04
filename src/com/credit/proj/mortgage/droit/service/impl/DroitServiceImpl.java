package com.credit.proj.mortgage.droit.service.impl;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageDroit;
import com.credit.proj.mortgage.droit.service.DroitService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class DroitServiceImpl implements  DroitService{

	//private final Log log = LogFactory.getLog(getClass());

	private static final Log logger=LogFactory.getLog(DroitServiceImpl.class);
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
	public void addDroit(ProcreditMortgageDroit procreditMortgageDroit,ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageDroit.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageDroit);
			procreditMortgage.setDywId(procreditMortgageDroit.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
				//log.info("新增无形权利抵押贷款信息成功!!!");
//				JsonUtil.jsonFromObject("新增无形权利抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增无形权利抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增无形权利抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从无形权利视图查询数据显示在更新和详情页面
	public List seeDroit(int id) throws IOException{
		try {
			return creditBaseDao.queryHql("from VProjMortDroit a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询无形权利抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDroit(int mortgageid,ProcreditMortgageDroit procreditMortgageDroit,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isUpdateOk = false;
		
		ProcreditMortgageDroit projMorDroit = null;
		if(procreditMortgageDroit != null){
			projMorDroit = updateMorDroitInfo(mortgageid,procreditMortgageDroit);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorDroit);
				if(isUpdateOk){
//					JsonUtil.jsonFromObject("更新无形权利抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新无形权利贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新无形权利贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeDroitForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageDroit a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询无形权利贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	private ProcreditMortgageDroit updateMorDroitInfo(int mortgageid,ProcreditMortgageDroit procreditMortgageDroit){
		ProcreditMortgageDroit proMorDroit = null;
		try{
			List list=this.seeDroitForUpdate(mortgageid);
			proMorDroit=(ProcreditMortgageDroit)list.get(0);
			if(proMorDroit != null){
				proMorDroit.setDroitname(procreditMortgageDroit.getDroitname());//权利名称
				proMorDroit.setDroitpercent(procreditMortgageDroit.getDroitpercent());//享有权利比重(%)
				proMorDroit.setDealdroittime(procreditMortgageDroit.getDealdroittime());//已经经营权利时间(年)
				proMorDroit.setNegotiabilityid(procreditMortgageDroit.getNegotiabilityid());//流通性
				proMorDroit.setResidualdroittime(procreditMortgageDroit.getResidualdroittime());//享有权利剩余时间(年)
				proMorDroit.setDealstatusid(procreditMortgageDroit.getDealstatusid());//经营状况
				proMorDroit.setDroitlucre(procreditMortgageDroit.getDroitlucre());//最近一年权利净收益(万元)
				proMorDroit.setDroitmassid(procreditMortgageDroit.getDroitmassid());//权利质量
				proMorDroit.setModelrangeprice(procreditMortgageDroit.getModelrangeprice());//模型估价(万元)
				proMorDroit.setRegisterinfoid(procreditMortgageDroit.getRegisterinfoid());//登记情况
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新无形权利贷款信息出错:"+e.getMessage());
		}
		return proMorDroit;
	}

	@Override
	public List seeDroitByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageDroit a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnDroit(ProcreditMortgageDroit procreditMortgageDroit,
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
			procreditMortgageDroit.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageDroit.getId()){
				creditBaseDao.saveDatas(procreditMortgageDroit);
			}else{
				ProcreditMortgageDroit orgProcreditMortgageDroit=(ProcreditMortgageDroit) creditBaseDao.getById(ProcreditMortgageDroit.class, procreditMortgageDroit.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageDroit, procreditMortgageDroit);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageDroit);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List getDroitByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortDroit a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
