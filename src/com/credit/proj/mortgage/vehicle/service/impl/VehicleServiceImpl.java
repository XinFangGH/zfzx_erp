package com.credit.proj.mortgage.vehicle.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageCar;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.commons.CreditException;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class VehicleServiceImpl implements  VehicleService{
	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(VehicleServiceImpl.class);
	private CreditBaseDao creditBaseDao ;
	private MortgageService mortgageService;
	@Resource
	private PawnItemsListDao pawnItemsListDao;
	@Resource
	private SlProcreditMaterialsService slProcreditMaterialsService;
	public MortgageService getMortgageService() {
		return mortgageService;
	}
	public void setMortgageService(MortgageService mortgageService) {
		this.mortgageService = mortgageService;
	}
	
	
	public CreditBaseDao getCreditBaseDao() {
		return creditBaseDao;
	}
	public void setCreditBaseDao(CreditBaseDao creditBaseDao) {
		this.creditBaseDao = creditBaseDao;
	}
	//制造商查询
	public void ajaxQueryCarFactoryForCombo(String query,int start ,int limit) {
		if(query==null){
			query="" ;
		}else{
			query.replaceAll(" ", "") ;
		}
		String hql = "from VCarDic as v where v.carFirmName like '%" + query + "%'" ;
		try{
			List list = creditBaseDao.queryHql(hql, start, limit);
			JsonUtil.jsonFromList(list) ;
		}catch(Exception e){
			JsonUtil.responseJsonFailure() ;
			logger.error("查询汽车制造商信息出错:"+e.getMessage());
			e.printStackTrace() ;
		}
	}
	
	//添加记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void addVehicle(ProcreditMortgageCar procreditMortgageCar,ProcreditMortgage procreditMortgage) throws Exception{
		boolean isSubmitOk = false;
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageCar.setMortgageid(procreditMortgage.getId());
			isSubmitOk =creditBaseDao.saveDatas(procreditMortgageCar);
			procreditMortgage.setDywId(procreditMortgageCar.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
				//log.info("新增反担保车辆抵押贷款信息成功!!!");
				//JsonUtil.responseJsonString("{success:true,mortgageId:"+procreditMortgage.getId()+",objectId:"+procreditMortgageCar.getId()+"}");
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增车辆抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增车辆抵押贷款信息出错:"+e.getMessage());
			//e.printStackTrace();
			throw new CreditException(e);
		}
	}
	
	//查看详情--从视图
	public List seeVehicle(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortCar a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询车辆抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	//根据mortgageid查询实体并且更新
	public List seeVehicleForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageCar a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询车辆抵押贷款信息出错:"+e.getMessage());
		}
		return null;

	}
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateVehicle(int mortgageid,ProcreditMortgageCar procreditMortgageCar,ProcreditMortgage procreditMortgage) throws Exception{
		boolean isUpdateOk = false;
		ProcreditMortgageCar projMorCar = null;
		if(procreditMortgageCar != null){
			projMorCar = updateMorCarInfoExt(mortgageid,procreditMortgageCar);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorCar);
				if(isUpdateOk){
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新车辆抵押贷款信息失败!!!", isUpdateOk) ;
				}

			} catch (Exception e) {
				logger.error("更新车辆抵押贷款信息出错:"+e.getMessage());
				//e.printStackTrace();
				throw new CreditException(e);
			}
		}
	}
	
	private ProcreditMortgageCar updateMorCarInfo(int mortgageid,ProcreditMortgageCar procreditMortgageCar){
		ProcreditMortgageCar projMortCar = null;
		try{
			List list=this.seeVehicleForUpdate(mortgageid);
			projMortCar=(ProcreditMortgageCar)list.get(0);
			if(projMortCar != null){
				projMortCar.setManufacturer(procreditMortgageCar.getManufacturer());//制造商
				//projMortCar.setController(procreditMortgageCar.getController());//控制权人
				//projMortCar.setCarenregisternumber(procreditMortgageCar.getCarenregisternumber());//机动车辆登记号
				//projMortCar.setCartype(procreditMortgageCar.getCartype());//型号
				//projMortCar.setEnginecapacity(procreditMortgageCar.getEnginecapacity());//排量
				projMortCar.setTotalkilometres(procreditMortgageCar.getTotalkilometres());//里程数(公里)
				//projMortCar.setControlmodeid(procreditMortgageCar.getControlmodeid());//控制权方式
				projMortCar.setEnregisterinfoid(procreditMortgageCar.getEnregisterinfoid());//登记情况
				projMortCar.setLeavefactorydate(procreditMortgageCar.getLeavefactorydate());//出厂日期
				projMortCar.setCarprice(procreditMortgageCar.getCarprice());//新车价格(万元)
				projMortCar.setHaveusedtime(procreditMortgageCar.getHaveusedtime());//使用时间(年)
				projMortCar.setAccidenttimes(procreditMortgageCar.getAccidenttimes());//事故次数
				projMortCar.setExchangepriceone(procreditMortgageCar.getExchangepriceone());//市场交易价格1(万元)
				projMortCar.setExchangepricetow(procreditMortgageCar.getExchangepricetow());//市场交易价格2(万元)
				projMortCar.setCarinfoid(procreditMortgageCar.getCarinfoid());//车况
				projMortCar.setModelrangeprice(procreditMortgageCar.getModelrangeprice());//模型估价(万元)
				
				projMortCar.setEngineNo(procreditMortgageCar.getEngineNo());//发动机号
				projMortCar.setVin(procreditMortgageCar.getVin());//车架号
				projMortCar.setCarColor(procreditMortgageCar.getCarColor());//车辆颜色
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新车辆抵押贷款信息出错:"+e.getMessage());
		}
		return projMortCar;
	}
	//保存更新车辆信息，add by gao 不明白为啥之前一个方法，不设置其他值
	private ProcreditMortgageCar updateMorCarInfoExt(int mortgageid,ProcreditMortgageCar procreditMortgageCar){
		ProcreditMortgageCar projMortCar = null;
		try{
			List list=this.seeVehicleForUpdate(mortgageid);
			projMortCar=(ProcreditMortgageCar)list.get(0);
			if(projMortCar != null){
				BeanUtil.copyNotNullProperties(projMortCar, procreditMortgageCar);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新车辆抵押贷款信息出错:"+e.getMessage());
		}
		return projMortCar;
	}
	
	//根据制造商id查询相应信息
	/*public void ajaxGetProcessCarData(int manufacturer) {	
		VCarDic vCarDic = null;//制造商实体
		
		try {
			vCarDic = this.getProcessCarDataById(manufacturer);
			if(vCarDic != null){
				JsonUtil.jsonFromObject(vCarDic, true) ;
			}else{
				JsonUtil.responseJsonString("{success:false,msg:'没有查询到车辆制造商信息!!!'}") ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.responseJsonString("{success:false,msg:'没有查询到车辆制造商信息!!!'}") ;
		}
	}
	
	public VCarDic getProcessCarDataById(int manufacturer){
		VCarDic vCarDic = null;//制造商实体
		
		try {
			vCarDic = (VCarDic)creditBaseDao.getById(VCarDic.class, manufacturer);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询汽车制造商信息出错:"+e.getMessage());
		}
		return vCarDic;
	}*/
	@Override
	public List getVehicleByObjectType(int mortgageid, String objectType) {
		try {
			String hql="from ProcreditMortgageCar a where a.mortgageid=? and a.objectType=?";
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void addPawnVehicle(ProcreditMortgageCar procreditMortgageCar,
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
			procreditMortgageCar.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageCar.getId()){
				creditBaseDao.saveDatas(procreditMortgageCar);
			}else{
				ProcreditMortgageCar orgProcreditMortgageCar=(ProcreditMortgageCar) creditBaseDao.getById(ProcreditMortgageCar.class, procreditMortgageCar.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageCar, procreditMortgageCar);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageCar);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public List seeVehicleByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortCar a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	//车贷获取担保材料信息
	/*public boolean getCarAssureDatumData(String carProjId, int isMarry,
			int liveMode, List assurePersonList) {

		
		//符合基本材料和是否已婚信息的list数据
		boolean isOk = false;
		List allList = new ArrayList();
		
		//存放符合条件的parentsId
		StringBuffer parentIds = new StringBuffer();
		String liveModeHql = "";//居住地方式hql
		
		
		//保证人
		List assureList = new ArrayList();
		String noAssurePersonHql = "";
		
		parentIds.append("2023");//必须的基础条件
		
		if(isMarry == 1){
			parentIds.append(",2024");//已婚
		}else{
			parentIds.append("");
		}
		
		if(liveMode == 1){
			liveModeHql = " or (a.parentId in(2025) and a.remarks in('自置','住房照片证据'))";
		}else if(liveMode == 2){
			liveModeHql = " or (a.parentId in(2025) and a.remarks in('按揭','住房照片证据'))";
		}else if(liveMode == 3){
			liveModeHql = " or (a.parentId in(2025) and a.remarks in('与父母同住','住房照片证据'))";
		}else if( liveMode ==4){
			liveModeHql = " or (a.parentId in(2025) and a.remarks in('租住','住房照片证据'))";
		}else if(liveMode == 5){
			liveModeHql = " or (a.parentId in(2025) and a.remarks in('集体宿舍','住房照片证据'))";
		}else{
			liveModeHql = "";
		}
		
		//判断是否有保证人或有多少个保证人信息
		if(assurePersonList != null && assurePersonList.size() != 0){
			String getAssureListFirstElement = assurePersonList.get(0).toString();//获取list中第一个元素的值
			
			if(getAssureListFirstElement.equals("false")){
				noAssurePersonHql = "";
			}else{
				for(int i=0;i<assurePersonList.size()-1;i++){//-1表示去掉第一个下标为true的长度
					String queryAssureHql = " from AreaDic a where a.parentId = 2027";
					try {
						assureList = creditBaseDao.queryHql(queryAssureHql);
						if(assureList != null && assureList.size() != 0){
							boolean saveDatas = saveMaterialDatas(carProjId,assureList);
							if(saveDatas){
								isOk = true;
							}
						}
					} catch (Exception e) {
						//log.error("读取或保存保证人材料信息失败!!!"+e.getMessage());
						throw new CreditException(e);
					}
				}
			}
		}
		
		String baseHql = " from AreaDic a where a.parentId in("+parentIds+")" + liveModeHql + noAssurePersonHql;
		try{
			allList = creditBaseDao.queryHql(baseHql);
			if(allList != null && allList.size() != 0){
				boolean saveDatas = saveMaterialDatas(carProjId,allList);
				if(saveDatas){
					isOk = true;
				}
			}
		}catch(Exception e){
			//log.error("读取或保存车贷基本材料信息失败!!!"+e.getMessage());
			throw new CreditException(e);
		}
		return isOk;
	
	}
	
	private boolean saveMaterialDatas(String carProjId,List list){
		boolean saveOk = false;
		for (int j = 0; j < list.size(); j++) {
			AreaDic areaData = new AreaDic();
			ProcreditMaterials procMaterials = new ProcreditMaterials();
			areaData = (AreaDic) list.get(j);
			int id = areaData.getId();
			procMaterials.setProjId(carProjId);
			procMaterials.setMaterialsId(id);
			procMaterials.setIsSelect(1);
			try {
				saveOk = creditBaseDao.saveDatas(procMaterials);
				if(saveOk){
					saveOk = true;
				}else{
					saveOk = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return saveOk;
	}*/
}
