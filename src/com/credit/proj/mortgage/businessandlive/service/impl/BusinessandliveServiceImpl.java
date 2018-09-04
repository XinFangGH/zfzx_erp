package com.credit.proj.mortgage.businessandlive.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageBusinessandlive;
import com.credit.proj.mortgage.businessandlive.service.BusinessandliveService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class BusinessandliveServiceImpl implements  BusinessandliveService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(BusinessandliveServiceImpl.class);
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
	public void addBusinessandlive(ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageBusinessandlive.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageBusinessandlive);
			procreditMortgage.setDywId(procreditMortgageBusinessandlive.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
//				JsonUtil.jsonFromObject("新增商住用地抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				//log.info("新增商住用地抵押贷款信息失败!!!");
				JsonUtil.jsonFromObject("新增商住用地抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			//log.error("新增商住用地抵押贷款信息出错!!!"+e.getMessage());
			logger.error("新增商住用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从商住用地视图查询数据显示在更新和详情页面
	public List seeBusinessandlive(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortBusAndLive a where a.mortgageid=? and a.objectType='mortgage'",id);
		} catch (Exception e) {
			logger.error("查询商住用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBusinessandlive(int mortgageid,ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isUpdateOk = false;
		
		ProcreditMortgageBusinessandlive projMorbus = null;
		if(procreditMortgageBusinessandlive != null){
			projMorbus = updateMorBusAndLiveInfo(mortgageid,procreditMortgageBusinessandlive);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorbus);
				if(isUpdateOk){
					//log.info("更新商住用地抵押贷款信息成功!!!");
//					JsonUtil.jsonFromObject("更新商住用地抵押贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					//log.info("更新商住用地抵押贷款信息失败!!!");
					JsonUtil.jsonFromObject("更新商住用地抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新商住用地抵押贷款信息出错:"+e.getMessage());
				//log.error("更新商住用地抵押贷款信息出错!!!"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeBusinessAndLiveForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageBusinessandlive a where a.mortgageid=? ",id);
		} catch (Exception e) {
			logger.error("查询商住用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private ProcreditMortgageBusinessandlive updateMorBusAndLiveInfo(int mortgageid,ProcreditMortgageBusinessandlive busAndLive){
		ProcreditMortgageBusinessandlive proMorBus = null;
		try{
			List list=this.seeBusinessAndLiveForUpdate(mortgageid);
			proMorBus=(ProcreditMortgageBusinessandlive)list.get(0);
			if(proMorBus != null){
				proMorBus.setAddress(busAndLive.getAddress());//土地地点
				proMorBus.setPropertyperson(busAndLive.getPropertyperson());//产权人
				proMorBus.setCertificatenumber(busAndLive.getCertificatenumber());//证件号码
				proMorBus.setDescriptionid(busAndLive.getDescriptionid());//地段描述
				proMorBus.setAcreage(busAndLive.getAcreage());//占地面积(M2)
				proMorBus.setRegisterinfoid(busAndLive.getRegisterinfoid());//登记情况
				proMorBus.setAnticipateacreage(busAndLive.getAnticipateacreage());//预规划住宅面积(M2)
				proMorBus.setMortgagesbalance(busAndLive.getMortgagesbalance());//土地抵质押贷款余额(万元)
				proMorBus.setExchangepriceground(busAndLive.getExchangepriceground());//同等土地成交单价(元/M2)
				proMorBus.setRentpriceformonth(busAndLive.getRentpriceformonth());//同等商业房屋每月出租价格(元/月/M2)
				proMorBus.setExchangepricebusiness(busAndLive.getExchangepricebusiness());//同等商业房屋成交价格(元/M2)
				proMorBus.setExchangepricehouse(busAndLive.getExchangepricehouse());//同等住宅成交单价(元/M2)
				proMorBus.setTenancyrangeprice(busAndLive.getTenancyrangeprice());//租赁模型估值(万元)
				proMorBus.setModelrangeprice(busAndLive.getModelrangeprice());//模型估价(万元)
				proMorBus.setVenditionrangeprice(busAndLive.getVenditionrangeprice());//销售模型估值(万元)
			}
		}catch(Exception e){
			logger.error("更新商住用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
		return proMorBus;
	}

	@Override
	public List seeBusinessandliveByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageBusinessandlive a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnBusinessandlive(
			ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive,
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
			procreditMortgageBusinessandlive.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageBusinessandlive.getId()){
				creditBaseDao.saveDatas(procreditMortgageBusinessandlive);
			}else{
				ProcreditMortgageBusinessandlive orgProcreditMortgageBusinessandlive=(ProcreditMortgageBusinessandlive) creditBaseDao.getById(ProcreditMortgageBusinessandlive.class, procreditMortgageBusinessandlive.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageBusinessandlive, procreditMortgageBusinessandlive);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageBusinessandlive);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List getBusinessandliveByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortBusAndLive a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
