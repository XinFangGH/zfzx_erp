package com.credit.proj.mortgage.machineinfo.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageMachineinfo;
import com.credit.proj.mortgage.machineinfo.service.MachineinfoService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class MachineinfoServiceImpl implements  MachineinfoService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(MachineinfoServiceImpl.class);
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
	public void addMachineinfo(ProcreditMortgageMachineinfo procreditMortgageMachineinfo,ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageMachineinfo.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageMachineinfo);
			procreditMortgage.setDywId(procreditMortgageMachineinfo.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
//				JsonUtil.jsonFromObject("新增机器设备抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by gao
			}else{
				JsonUtil.jsonFromObject("新增机器设备抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增机器设备抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从机器设备视图查询数据显示在更新和详情页面
	public List seeMachineinfo(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortMachineInfo a where a.mortgageid=? and a.objectType='mortgage'",id);	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询机器设备抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMachineinfo(int mortgageid,ProcreditMortgageMachineinfo procreditMortgageMachineinfo,
			ProcreditMortgage procreditMortgage) throws Exception{
		boolean isUpdateOk = false;
		
		ProcreditMortgageMachineinfo projMorMachine = null;
		if(procreditMortgageMachineinfo != null){
			projMorMachine = updateMorMachineInfoExt(mortgageid,procreditMortgageMachineinfo);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorMachine);
				if(isUpdateOk){
//					JsonUtil.jsonFromObject("更新机器设备抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新机器设备抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("更新机器设备抵押贷款信息出错:"+e.getMessage());
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeMachineinfoForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageMachineinfo a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询机器设备抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	private ProcreditMortgageMachineinfo updateMorMachineInfo(int mortgageid,ProcreditMortgageMachineinfo procreditMortgageMachineinfo){
		ProcreditMortgageMachineinfo projMorMachi = null;
		try{
			List list=this.seeMachineinfoForUpdate(mortgageid);
			projMorMachi=(ProcreditMortgageMachineinfo)list.get(0);
			if(projMorMachi != null){
				projMorMachi.setMachinename(procreditMortgageMachineinfo.getMachinename());//设备名称
				projMorMachi.setController(procreditMortgageMachineinfo.getController());//控制权人
				projMorMachi.setMachinetype(procreditMortgageMachineinfo.getMachinetype());//设备型号
				projMorMachi.setNewcarprice(procreditMortgageMachineinfo.getNewcarprice());//新货价格(万元)
				projMorMachi.setHavedusedtime(procreditMortgageMachineinfo.getHavedusedtime());//使用时间(年)
				projMorMachi.setSecondaryvalueone(procreditMortgageMachineinfo.getSecondaryvalueone());//二手价值1(万元)
				projMorMachi.setSecondaryvaluetow(procreditMortgageMachineinfo.getSecondaryvaluetow());//二手价值2(万元)
				projMorMachi.setModelrangeprice(procreditMortgageMachineinfo.getModelrangeprice());//模型估价(万元)
				projMorMachi.setLeavefactorydate(procreditMortgageMachineinfo.getLeavefactorydate());//出厂日期
				projMorMachi.setControllertypeid(procreditMortgageMachineinfo.getControllertypeid());//控制权方式
				projMorMachi.setCommongradeid(procreditMortgageMachineinfo.getCommongradeid());//通用程度
				projMorMachi.setCapabilitystatusid(procreditMortgageMachineinfo.getCapabilitystatusid());//性能状况
				projMorMachi.setCashabilityid(procreditMortgageMachineinfo.getCashabilityid());//变现能力
				projMorMachi.setRegisterinfoid(procreditMortgageMachineinfo.getRegisterinfoid());//登记情况
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新机器设备抵押贷款信息出错:"+e.getMessage());
		}
		return projMorMachi;
	}
	
	private ProcreditMortgageMachineinfo updateMorMachineInfoExt (int mortgageid,ProcreditMortgageMachineinfo procreditMortgageMachineinfo){
		ProcreditMortgageMachineinfo projMorMachi = null;
		try{
			List list=this.seeMachineinfoForUpdate(mortgageid);
			projMorMachi=(ProcreditMortgageMachineinfo)list.get(0);
			if(projMorMachi != null){
				BeanUtil.copyNotNullProperties(projMorMachi,procreditMortgageMachineinfo);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新机器设备抵押贷款信息出错:"+e.getMessage());
		}
		return projMorMachi;
	}

	@Override
	public List getMachineinfoByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageMachineinfo a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnMachineinfo(
			ProcreditMortgageMachineinfo procreditMortgageMachineinfo,
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
			procreditMortgageMachineinfo.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageMachineinfo.getId()){
				creditBaseDao.saveDatas(procreditMortgageMachineinfo);
			}else{
				ProcreditMortgageMachineinfo orgProcreditMortgageMachineinfo=(ProcreditMortgageMachineinfo) creditBaseDao.getById(ProcreditMortgageMachineinfo.class, procreditMortgageMachineinfo.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageMachineinfo, procreditMortgageMachineinfo);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageMachineinfo);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List seeMachineinfoByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortMachineInfo a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
