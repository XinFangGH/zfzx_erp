package com.credit.proj.mortgage.dutyperson.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgagePerson;
import com.credit.proj.mortgage.dutyperson.service.DutypersonService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;

@SuppressWarnings("all")
public class DutypersonServiceImpl implements  DutypersonService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(DutypersonServiceImpl.class);
	private CreditBaseDao creditBaseDao ;
	private MortgageService mortgageService;
	private VehicleService vehicleService;
	@Resource
	private SlPersonMainService slPersonMainService;
	
	
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
	public void addDutyperson(ProcreditMortgagePerson procreditMortgageperson,
			ProcreditMortgage procreditMortgage,Person person,int customerPersonName,int cardtype) throws Exception{
		
		boolean isSubmitOk = false;
		
		try {
			if("Financing".equals(procreditMortgage.getBusinessType())){
				SlPersonMain perMain=slPersonMainService.get(new Long(customerPersonName));
				if(perMain!=null){
					//perMain.setCardtype((short) cardtype);
					perMain.setCardnum(person.getCardnumber());
					perMain.setLinktel(person.getCellphone());
					perMain.setHome(person.getFamilyaddress());
					slPersonMainService.merge(perMain);
				}
			}else{
				//更新个人信息到个人表--电话号码
				Person personUpdate = (Person) creditBaseDao.getById(Person.class,customerPersonName);
				if(personUpdate!=null){
					personUpdate.setCardnumber(person.getCardnumber());
					personUpdate.setCellphone(person.getCellphone());
					personUpdate.setFamilyaddress(person.getFamilyaddress());
					personUpdate.setIspublicservant(procreditMortgageperson.getIsCivilServant());
					//personUpdate.setCardtype(cardtype);
					creditBaseDao.updateDatas(personUpdate);
					//PropertyUtils.copyNotNullProperties(personUpdate,person);
				}
			}
			
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageperson.setMortgageid(procreditMortgage.getId());
			//baseDao.updateDatas(personUpdate);
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgageperson);
			procreditMortgage.setDywId(procreditMortgageperson.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			if(isSubmitOk){
				//log.info("新增个人贷款信息成功!!!");
				JsonUtil.jsonFromObject("新增个人贷款信息成功!!!", isSubmitOk) ;
			}else{
				JsonUtil.jsonFromObject("新增个人贷款信息失败!!!", isSubmitOk) ;
			}
		} catch (Exception e) {
			logger.error("新增个人贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从责任个人视图查询数据显示在更新和详情页面
	public List seeDutyperson(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortDutyPerson a where a.mortgageid=? ",id);	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询个人贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDutyperson(int mortgageid,ProcreditMortgagePerson procreditMortgageperson,
			ProcreditMortgage procreditMortgage,Person person,int customerPersonName,int cardtype) throws Exception{
		
		boolean isUpdateOk = false;
		ProcreditMortgagePerson projMorPerson = null;
		
		if(procreditMortgageperson != null){
			projMorPerson = updateMorHouseInfo(mortgageid,procreditMortgageperson);
			try {
				
				if("Financing".equals(procreditMortgage.getBusinessType())){
					SlPersonMain perMain=slPersonMainService.get(new Long(customerPersonName));
					if(perMain!=null){
						//perMain.setCardtype((short) cardtype);
						perMain.setCardnum(person.getCardnumber());
						perMain.setLinktel(person.getCellphone());
						perMain.setHome(person.getFamilyaddress());
						slPersonMainService.merge(perMain);
					}
				}else{
					//更新个人信息到个人表--电话号码
					Person personEntityUpdate = (Person) creditBaseDao.getById(Person.class,customerPersonName);
					if(personEntityUpdate!=null){
						personEntityUpdate.setCardnumber(person.getCardnumber());
						personEntityUpdate.setCellphone(person.getCellphone());
						personEntityUpdate.setFamilyaddress(person.getFamilyaddress());
						personEntityUpdate.setIspublicservant(procreditMortgageperson.getIsCivilServant());
					//	personEntityUpdate.setCardtype(cardtype);
						creditBaseDao.updateDatas(personEntityUpdate);
						//PropertyUtils.copyNotNullProperties(personEntityUpdate,person);
					}
				}
				
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				//baseDao.updateDatas(personEntityUpdate);
				isUpdateOk = creditBaseDao.updateDatas(projMorPerson);
				if(isUpdateOk){
					//log.info("更新反担保个人抵押贷款信息成功!!!");
					JsonUtil.jsonFromObject("更新个人贷款信息成功!!!", isUpdateOk) ;
				}else{
					JsonUtil.jsonFromObject("更新个人贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新个人贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeDutypersonForUpdate(int id) throws Exception {
		String hql = "from ProcreditMortgagePerson v where v.mortgageid=? ";
		try {
			return creditBaseDao.queryHql(hql, id);
		} catch (Exception e) {
			logger.error("查询个人贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private ProcreditMortgagePerson updateMorHouseInfo(int mortgageid,ProcreditMortgagePerson procreditMortgageperson){
		ProcreditMortgagePerson proMorPer = null;
		try{
			List list=this.seeDutypersonForUpdate(mortgageid);
			proMorPer=(ProcreditMortgagePerson)list.get(0);
			if(proMorPer != null){
				//proMorPer.setName(procreditMortgageperson.getName());//姓名
				//proMorPer.setIdcard(procreditMortgageperson.getIdcard());//身份证号码
				//proMorPer.setPhone(procreditMortgageperson.getPhone());//联系电话
				proMorPer.setAssetvalue(procreditMortgageperson.getAssetvalue());//资产价值(万元)
				proMorPer.setSocietynexusid(procreditMortgageperson.getSocietynexusid());//社会关系
				proMorPer.setModelrangeprice(procreditMortgageperson.getModelrangeprice());//模型估价(万元)
				proMorPer.setIsCivilServant(procreditMortgageperson.getIsCivilServant());
				proMorPer.setAssets(procreditMortgageperson.getAssets());
				proMorPer.setBusiness(procreditMortgageperson.getBusiness());
				proMorPer.setMonthlyIncome(procreditMortgageperson.getMonthlyIncome());
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新个人贷款信息出错:"+e.getMessage());
		}
		return proMorPer;
	}
}
