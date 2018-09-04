package com.credit.proj.mortgage.stockownership.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageStockownership;
import com.credit.proj.entity.VProjMortStockFinance;
import com.credit.proj.entity.VProjMortStockOwnerShip;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.stockownership.service.StockownershipService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.creditUtils.PropertyUtils;
import com.zhiwei.credit.dao.creditFlow.ourmain.SlCompanyMainDao;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;

@SuppressWarnings("all")
public class StockownershipServiceImpl implements  StockownershipService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(StockownershipServiceImpl.class);
	private CreditBaseDao creditBaseDao ;
	private MortgageService mortgageService;
	private VehicleService vehicleService;
	
	@Resource
	private SlCompanyMainDao slCompanyMainDao;
	@Resource
	private AreaDicService areaDicService;
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
	public void addStockownership(ProcreditMortgageStockownership procreditMortgageStockownership,
			ProcreditMortgage procreditMortgage,Enterprise enterprise) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			if("Financing".equals(procreditMortgage.getBusinessType())){
				SlCompanyMain slCompanyMain = slCompanyMainDao.get(new Long(procreditMortgageStockownership.getCorporationname()));
				if(slCompanyMain!=null){
					slCompanyMain.setBusinessCode(enterprise.getCciaa());
					slCompanyMain.setRegisterMoney(enterprise.getRegistermoney());
					slCompanyMain.setHangyeType(enterprise.getHangyeType());
					slCompanyMain.setRegisterStartDate(enterprise.getRegisterstartdate());
					slCompanyMain.setPersonMainId(new Long(enterprise.getLegalpersonid()));
					slCompanyMainDao.merge(slCompanyMain);
				}
			}else{
				//更新企业信息到企业表
				Enterprise enterEntity = (Enterprise) creditBaseDao.getById(Enterprise.class, enterprise.getId());
				if(enterEntity!=null){
					PropertyUtils.copyNotNullProperties(enterEntity,enterprise);
					creditBaseDao.updateDatas(enterEntity);
				}else{
					JsonUtil.responseJsonString("success:true,msg:'对应企业信息不存在! '") ;
				}
			}
			
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageStockownership.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageStockownership);
			procreditMortgage.setDywId(procreditMortgageStockownership.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
				//log.info("新增股权抵押贷款信息成功!!!");
//				JsonUtil.jsonFromObject("新增股权抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增股权抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增股权抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	//查看详情--从股权视图查询数据显示在查看详情页面
	public List seeStockownership(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortStockOwnerShip a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询股权抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	public VProjMortStockOwnerShip seeStockownershipFromView(int mortgageid){
		VProjMortStockOwnerShip vStockOwnerShip = null;
		List stockList = null;
		String queryHql = "from VProjMortStockOwnerShip vp where vp.mortgageid = "+mortgageid+" and vp.objectType='mortgage'";
		
		/*//企业信息
		int enterpriseId = 0;//企业表id，需要查询行业类别的值
		EnterpriseView enterpriseView = new EnterpriseView();
		String enterHql = "";
		List enterList = new ArrayList();
		String hangyeTypeValue = "";*/
		
		/*//行业类别查询信息
		IsicRev4TextChinese isicRev = null ;
		String hqlTypeValue = "from IsicRev4TextChinese AS ir where ir.code=?" ;
		String code = "";*/
		
		try{
			stockList = creditBaseDao.queryHql(queryHql);
			if(stockList != null && stockList.size() != 0){
				vStockOwnerShip = (VProjMortStockOwnerShip)stockList.get(0);
				/*enterpriseId = vStockOwnerShip.getCorporationname();
				enterHql = "from EnterpriseView ev where ev.id = "+enterpriseId;
				enterList = creditBaseDao.queryHql(enterHql);
				if(enterList != null && enterList.size() != 0){
					enterpriseView = (EnterpriseView)enterList.get(0);
					hangyeTypeValue = enterpriseView.getHangyetypevalue();
					vStockOwnerShip.setHangyeTypeValue(hangyeTypeValue);
					code = enterpriseView.getTradetype();
					if(null != code || ""  != code){
						String aray[] = code.split(",");
						int lenght = aray.length ;
						for(int k = 0 ; k < lenght ; k ++){
							if("" != aray[k] && k == 0 ){
								isicRev = (IsicRev4TextChinese)creditBaseDao.queryHql(hqlTypeValue, aray[k]).get(0);
								vStockOwnerShip.setTest1(isicRev.getDescription());
							}else if("" != aray[k] && k == 1){
								isicRev = (IsicRev4TextChinese)creditBaseDao.queryHql(hqlTypeValue, aray[k]).get(0);
								vStockOwnerShip.setTest2(isicRev.getDescription());
							}else if("" != aray[k] && k == 2){
								isicRev = (IsicRev4TextChinese)creditBaseDao.queryHql(hqlTypeValue, aray[k]).get(0);
								vStockOwnerShip.setTest3(isicRev.getDescription());
							}else if("" != aray[k] && k == 3){
								isicRev = (IsicRev4TextChinese)creditBaseDao.queryHql(hqlTypeValue, aray[k]).get(0);
								vStockOwnerShip.setTest4(isicRev.getDescription());
							}
						}
					}
				}*/
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取股权抵押贷款信息出错:"+e.getMessage());
		}
		return vStockOwnerShip;
	}
	
	public VProjMortStockFinance getStockownershipFinance(int mortgageid){
		VProjMortStockFinance vStockOwnerShip = null;
		List stockList = null;
		String queryHql = "from VProjMortStockFinance vp where vp.mortgageid = "+mortgageid;
		try{
			stockList = creditBaseDao.queryHql(queryHql);
			if(stockList != null && stockList.size() != 0){
				vStockOwnerShip = (VProjMortStockFinance)stockList.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取股权(融资)抵押贷款信息出错:"+e.getMessage());
		}
		return vStockOwnerShip;
	}
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateStockownership(int mortgageid,ProcreditMortgageStockownership procreditMortgageStockownership,
			ProcreditMortgage procreditMortgage,Enterprise enterprise) throws Exception{
		boolean isUpdateOk = false;
		
		ProcreditMortgageStockownership projMorSOS = null;
		if(procreditMortgageStockownership != null){
			projMorSOS = updateMorStockownershipInfoExt(mortgageid,procreditMortgageStockownership);
			try {
				if("Financing".equals(procreditMortgage.getBusinessType())){
					SlCompanyMain slCompanyMain = slCompanyMainDao.get(new Long(procreditMortgageStockownership.getCorporationname()));
					if(slCompanyMain!=null){
						slCompanyMain.setBusinessCode(enterprise.getCciaa());
						slCompanyMain.setRegisterMoney(enterprise.getRegistermoney());
						slCompanyMain.setHangyeType(enterprise.getHangyeType());
						slCompanyMain.setRegisterStartDate(enterprise.getRegisterstartdate());
						slCompanyMain.setPersonMainId(new Long(enterprise.getLegalpersonid()));
						slCompanyMainDao.merge(slCompanyMain);
					}
				}else{
					//更新企业信息到企业表
					Enterprise enterEntityUpdate = (Enterprise) creditBaseDao.getById(Enterprise.class, enterprise.getId());
					if(enterEntityUpdate!=null){
						PropertyUtils.copyNotNullProperties(enterEntityUpdate,enterprise);
						creditBaseDao.updateDatas(enterEntityUpdate);
					}else{
						JsonUtil.responseJsonString("success:true,msg:'对应企业信息不存在! '") ;
					}
				}
				
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorSOS);
				if(isUpdateOk){
					//log.info("更新反担保股权抵押贷款信息成功!!!");
//					JsonUtil.jsonFromObject("更新股权抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新股权抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新股权抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}

	//根据mortgageid查询实体并且更新
	public List seeStockownershipForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageStockownership a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询股权抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	private ProcreditMortgageStockownership updateMorStockownershipInfo(int mortgageid,ProcreditMortgageStockownership procreditMortgageStockownership){
		ProcreditMortgageStockownership projMortStock = null;
		
		try{
			List list=this.seeStockownershipForUpdate(mortgageid);
			projMortStock=(ProcreditMortgageStockownership)list.get(0);
			if(projMortStock != null){
				projMortStock.setCorporationname(procreditMortgageStockownership.getCorporationname());//目标公司名称
				//projMortStock.setLicencenumber(procreditMortgageStockownership.getLicencenumber());//营业执照号码
				//projMortStock.setEnrolcapital(procreditMortgageStockownership.getEnrolcapital());//注册资本(万元)
				projMortStock.setStockownershippercent(procreditMortgageStockownership.getStockownershippercent());//股权(%)
				projMortStock.setNetassets(procreditMortgageStockownership.getNetassets());//净资产(万元)
				projMortStock.setModelrangeprice(procreditMortgageStockownership.getModelrangeprice());//模型估价(万元)
				projMortStock.setStockownership(procreditMortgageStockownership.getStockownership());//控制权人
				//projMortStock.setEnroltiame(procreditMortgageStockownership.getEnroltiame());//注册时间(年)
				projMortStock.setManagementtime(procreditMortgageStockownership.getManagementtime());//经营时间(年)
				projMortStock.setManagementstatusid(procreditMortgageStockownership.getManagementstatusid());//经营状况
				//projMortStock.setProducttraitid(procreditMortgageStockownership.getProducttraitid());//经营产品特性
				projMortStock.setRegisterinfoid(procreditMortgageStockownership.getRegisterinfoid());//登记情况
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新股权抵押贷款信息出错:"+e.getMessage());
		}
		return projMortStock;
	}
	//保存更新车辆信息，add by gao 不明白为啥之前一个方法，不设置其他值
	private ProcreditMortgageStockownership updateMorStockownershipInfoExt(int mortgageid,ProcreditMortgageStockownership procreditMortgageStockownership){
		ProcreditMortgageStockownership projMortStock = null;
		
		try{
			List list=this.seeStockownershipForUpdate(mortgageid);
			projMortStock=(ProcreditMortgageStockownership)list.get(0);
			if(projMortStock != null){
				BeanUtil.copyNotNullProperties(projMortStock, procreditMortgageStockownership);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新股权抵押贷款信息出错:"+e.getMessage());
		}
		return projMortStock;
	}

	@Override
	public List getStockownershipByObjectType(int mortgageid, String objectType) {
		
		try {
			String hql="from ProcreditMortgageStockownership a where a.mortgageid=? and a.objectType=?";
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnStockownership(
			ProcreditMortgageStockownership procreditMortgageStockownership,
			PawnItemsList pawnItemsList, Enterprise enterprise) {
		try{
			//更新企业信息到企业表
			Enterprise enterEntity = (Enterprise) creditBaseDao.getById(Enterprise.class, enterprise.getId());
			if(enterEntity!=null){
				PropertyUtils.copyNotNullProperties(enterEntity,enterprise);
				creditBaseDao.updateDatas(enterEntity);
			}else{
				JsonUtil.responseJsonString("success:true,msg:'对应企业信息不存在! '") ;
			}
			if(null==pawnItemsList.getPawnItemId()){
				pawnItemsList.setPawnItemStatus("underway");
				pawnItemsListDao.save(pawnItemsList);
			}else{
				PawnItemsList orgPawnItems=pawnItemsListDao.get(pawnItemsList.getPawnItemId());
				BeanUtil.copyNotNullProperties(orgPawnItems, pawnItemsList);
				pawnItemsListDao.merge(orgPawnItems);
			}
			procreditMortgageStockownership.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageStockownership.getId()){
				creditBaseDao.saveDatas(procreditMortgageStockownership);
			}else{
				ProcreditMortgageStockownership orgProcreditMortgageStockownership=(ProcreditMortgageStockownership) creditBaseDao.getById(ProcreditMortgageStockownership.class, procreditMortgageStockownership.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageStockownership, procreditMortgageStockownership);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageStockownership);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public List seeStockownershipByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortStockOwnerShip a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
