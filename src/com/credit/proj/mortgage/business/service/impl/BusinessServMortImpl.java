package com.credit.proj.mortgage.business.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageBusiness;
import com.credit.proj.mortgage.business.service.BusinessServMort;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class BusinessServMortImpl implements  BusinessServMort{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(BusinessServMortImpl.class);
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
	public void addBusiness(ProcreditMortgageBusiness procreditMortgageBusiness,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageBusiness.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageBusiness);
			procreditMortgage.setDywId(procreditMortgageBusiness.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
//				JsonUtil.jsonFromObject("新增商业用地抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增商业用地抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			//log.error("新增商业用地抵押贷款信息失败!!!"+e.getMessage());
			logger.error("新增商业用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从商业视图查询数据显示在更新和详情页面
	public List seeBusiness(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortBusiness a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			logger.error("查询商业用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBusiness(int mortgageid,ProcreditMortgageBusiness procreditMortgageBusiness,
			ProcreditMortgage procreditMortgage) throws Exception{
		boolean isUpdateOk = false;
		
		ProcreditMortgageBusiness projMorbusness = null;
		if(procreditMortgageBusiness != null){
			projMorbusness = updateMorBusnessInfo(mortgageid,procreditMortgageBusiness);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorbusness);
				if(isUpdateOk){
					//log.info("更新商业用地抵押贷款信息成功!!!");
//					JsonUtil.jsonFromObject("更新商业用地抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					//log.info("更新商业用地抵押贷款信息失败!!!");
					JsonUtil.jsonFromObject("更新商业用地抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				//log.error("更新商业用地抵押贷款信息出错!!!"+e.getMessage());
				logger.error("更新商业用地抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeBusinessForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageBusiness a where a.mortgageid=? ",id);
		} catch (Exception e) {
			logger.error("查询商业用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private ProcreditMortgageBusiness updateMorBusnessInfo(int mortgageid,ProcreditMortgageBusiness business){
		ProcreditMortgageBusiness proMorBusiness = null;
		try{
			List list=this.seeBusinessForUpdate(mortgageid);
			proMorBusiness=(ProcreditMortgageBusiness)list.get(0);
			if(proMorBusiness != null){
				proMorBusiness.setAddress(business.getAddress());//土地地点
				proMorBusiness.setPropertyperson(business.getPropertyperson());//产权人
				proMorBusiness.setCertificatenumber(business.getCertificatenumber());//证件号码
				proMorBusiness.setDescriptionid(business.getDescriptionid());//地段描述
				proMorBusiness.setAcreage(business.getAcreage());//占地面积(M2)
				proMorBusiness.setRegisterinfoid(business.getRegisterinfoid());//登记情况
				proMorBusiness.setAnticipateacreage(business.getAnticipateacreage());//预规划住宅面积(M2)
				proMorBusiness.setMortgagesbalance(business.getMortgagesbalance());//土地抵质押贷款余额(万元)
				proMorBusiness.setLendpriceformonthone(business.getLendpriceformonthone());//同等商业房屋每月出租价格1(元/月/M2)
				proMorBusiness.setGroundexchangeprice(business.getGroundexchangeprice());//同等土地成交单价(元/M2)
				proMorBusiness.setLendpriceformonthtow(business.getLendpriceformonthtow());//同等商业房屋每月出租价格2(元/月/M2)
				proMorBusiness.setExchangepriceone(business.getExchangepriceone());//同等商业房屋成交价格1(元/M2)
				proMorBusiness.setModelrangeprice(business.getModelrangeprice());//模型估价(万元)
				proMorBusiness.setExchangepricetow(business.getExchangepricetow());//同等商业房屋成交价格2(元/M2)
				proMorBusiness.setTenancyrangeprice(business.getTenancyrangeprice());//租赁模型估值(万元)
				proMorBusiness.setVenditionrangeprice(business.getVenditionrangeprice());//销售模型估值(万元)
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新商业用地抵押贷款信息出错:"+e.getMessage());
		}
		return proMorBusiness;
	}

	@Override
	public List seeBusinessByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageBusiness a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnBusiness(
			ProcreditMortgageBusiness procreditMortgageBusiness,
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
			procreditMortgageBusiness.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageBusiness.getId()){
				creditBaseDao.saveDatas(procreditMortgageBusiness);
			}else{
				ProcreditMortgageBusiness orgProcreditMortgageBusiness=(ProcreditMortgageBusiness) creditBaseDao.getById(ProcreditMortgageBusiness.class, procreditMortgageBusiness.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageBusiness, procreditMortgageBusiness);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageBusiness);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
}

	@Override
	public List getBusinessByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortBusiness a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
