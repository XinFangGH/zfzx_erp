package com.credit.proj.mortgage.education.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageEducation;
import com.credit.proj.mortgage.education.service.EducationService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;

@SuppressWarnings("all")
public class EducationServiceImpl implements  EducationService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(EducationServiceImpl.class);
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
	public void addEducation(ProcreditMortgageEducation procreditMortgageEducation,ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageEducation.setMortgageid(procreditMortgage.getId());
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageEducation);
			procreditMortgage.setDywId(procreditMortgageEducation.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
//				JsonUtil.jsonFromObject("新增教育用地抵押贷款信息成功!!!", isSubmitOk) ;
				JsonUtil.jsonFromObject(procreditMortgage.getId(), isSubmitOk) ;//add by zhangchuyan
			}else{
				JsonUtil.jsonFromObject("新增教育用地抵押贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增教育用地抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从教育用地视图查询数据显示在更新和详情页面
	public List seeEducation(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortEducation a where a.mortgageid=? and a.objectType='mortgage'",id);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEducation(int mortgageid,ProcreditMortgageEducation procreditMortgageEducation,
			ProcreditMortgage procreditMortgage) throws Exception{
		
		boolean isUpdateOk = false;
		ProcreditMortgageEducation procMorEdu = null;
		if(procreditMortgageEducation != null){
			procMorEdu = updateMorEducationInfo(mortgageid,procreditMortgageEducation);
			try {
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(procMorEdu);
				if(isUpdateOk){
//					JsonUtil.jsonFromObject("更新教育用地贷款信息成功!!!", isUpdateOk) ;
					JsonUtil.jsonFromObject(procreditMortgage.getId(), isUpdateOk) ;//add by gaoqingrui
				}else{
					JsonUtil.jsonFromObject("更新教育用地贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新教育用地贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeEducationForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageEducation a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询教育用地贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	private ProcreditMortgageEducation updateMorEducationInfo(int mortgageid,ProcreditMortgageEducation procreditMortgageEducation){
		ProcreditMortgageEducation proMorEducation = null;
		try{
			List list=this.seeEducationForUpdate(mortgageid);
			proMorEducation=(ProcreditMortgageEducation)list.get(0);
			if(proMorEducation != null){
				proMorEducation.setAddress(procreditMortgageEducation.getAddress());//土地地点
				proMorEducation.setPropertyperson(procreditMortgageEducation.getPropertyperson());//产权人
				proMorEducation.setCertificatenumber(procreditMortgageEducation.getCertificatenumber());//证件号码
				proMorEducation.setBuytime(procreditMortgageEducation.getBuytime());//购买年份
				proMorEducation.setResidualyears(procreditMortgageEducation.getResidualyears());//剩余年限(年)
				proMorEducation.setAcreage(procreditMortgageEducation.getAcreage());//占地面积(M2)
				proMorEducation.setBuilacreage(procreditMortgageEducation.getBuilacreage());//建筑物面积(M2)
				proMorEducation.setGroundcharacterid(procreditMortgageEducation.getGroundcharacterid());//土地性质
				proMorEducation.setBuildtenancyrangeprice(procreditMortgageEducation.getBuildtenancyrangeprice());//建筑物租赁模型估值(万元)
				proMorEducation.setGroundtenancyrangeprice(procreditMortgageEducation.getGroundtenancyrangeprice());//土地租赁模型估值(万元)
				proMorEducation.setDescriptionid(procreditMortgageEducation.getDescriptionid());//地段描述
				proMorEducation.setRegisterinfoid(procreditMortgageEducation.getRegisterinfoid());//登记情况
				proMorEducation.setGroundrentpriceformonth(procreditMortgageEducation.getGroundrentpriceformonth());//同等土地每月出租价格(元/月/M2)
				proMorEducation.setBuildrentpriceformonth(procreditMortgageEducation.getBuildrentpriceformonth());//同等建筑物每月出租价格(元/月/M2)
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新教育用地贷款信息出错:"+e.getMessage());
		}
		return proMorEducation;
	}

	@Override
	public List seeEducationByObjectType(int mortgageid, String objectType) {
		String hql="from ProcreditMortgageEducation a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql, new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addPawnEducation(
			ProcreditMortgageEducation procreditMortgageEducation,
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
			procreditMortgageEducation.setMortgageid(pawnItemsList.getPawnItemId().intValue());
			if(null==procreditMortgageEducation.getId()){
				creditBaseDao.saveDatas(procreditMortgageEducation);
			}else{
				ProcreditMortgageEducation orgProcreditMortgageEducation=(ProcreditMortgageEducation) creditBaseDao.getById(ProcreditMortgageEducation.class, procreditMortgageEducation.getId());
				BeanUtil.copyNotNullProperties(orgProcreditMortgageEducation, procreditMortgageEducation);
				creditBaseDao.saveOrUpdateDatas(orgProcreditMortgageEducation);
			}
			JsonUtil.responseJsonString("{success:true,pawnItemId:"+pawnItemsList.getPawnItemId()+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List getEducationByObjectType(int mortgageid, String objectType) {
		String hql="from VProjMortEducation a where a.mortgageid=? and a.objectType=?";
		try {
			return creditBaseDao.queryHql(hql,new Object[]{mortgageid,objectType});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
