package com.credit.proj.mortgage.company.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageEnterprise;
import com.credit.proj.mortgage.company.service.CompanyMService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.creditUtils.PropertyUtils;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;

@SuppressWarnings("all")
public class CompanyMServiceImpl implements  CompanyMService{
	
	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(CompanyMServiceImpl.class);

	private CreditBaseDao creditBaseDao ;
	private MortgageService mortgageService;
	private VehicleService vehicleService;
	
	@Resource
	private SlCompanyMainService slCompanyMainService;
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
	public void addCompany(ProcreditMortgageEnterprise procreditMortgageEnterprise,
			ProcreditMortgage procreditMortgage,Enterprise enterprise,Person person,int customerEnterpriseName,int legalpersonid) throws Exception{
		
		boolean isSubmitOk = false;
		boolean saveEnterpriseOk = false;
		boolean savePersonOk = false;
		boolean saveProcreditOk = false;
		
		try {
			if("Financing".equals(procreditMortgage.getBusinessType())){
				SlCompanyMain comMain=slCompanyMainService.get(new Long(customerEnterpriseName));
				SlPersonMain perMain=slPersonMainService.get(new Long(legalpersonid));
				if(comMain!=null){
					comMain.setSjjyAddress(enterprise.getArea());
					comMain.setBusinessCode(enterprise.getCciaa());
					comMain.setTel(enterprise.getTelephone());
					if(perMain!=null){
						comMain.setPersonMainId(new Long(legalpersonid));
						comMain.setLawName(perMain.getName());
						perMain.setLinktel(person.getCellphone());
					}
					slCompanyMainService.merge(comMain);
					slPersonMainService.merge(perMain);
					saveEnterpriseOk = true;
					savePersonOk = true;
				}
			}else{
				//更新企业信息到企业表
				Enterprise enterEntity = (Enterprise) creditBaseDao.getById(Enterprise.class, customerEnterpriseName);
				if(enterEntity!=null){
					enterEntity.setArea(enterprise.getArea());
					enterEntity.setCciaa(enterprise.getCciaa());
					enterEntity.setTelephone(enterprise.getTelephone());
					enterEntity.setLegalpersonid(legalpersonid);
					saveEnterpriseOk = creditBaseDao.updateDatas(enterEntity);
					//PropertyUtils.copyNotNullProperties(enterEntity,enterprise);
				}else{
					saveEnterpriseOk = true ;
				}
				
				//更新个人信息到个人表--电话号码
				Person personEntity = (Person) creditBaseDao.getById(Person.class,legalpersonid);
				if(personEntity!=null){
					personEntity.setCellphone(person.getCellphone());
					savePersonOk = creditBaseDao.updateDatas(personEntity);
					//PropertyUtils.copyNotNullProperties(personEntity,person);
				}else{
					savePersonOk = true;
				}
			}
			
			mortgageService.addMortgage(procreditMortgage);
			procreditMortgageEnterprise.setMortgageid(procreditMortgage.getId());
			saveProcreditOk = creditBaseDao.saveDatas(procreditMortgageEnterprise);
			procreditMortgage.setDywId(procreditMortgageEnterprise.getId());
			mortgageService.updateMortgage(procreditMortgage.getId(), procreditMortgage);
			//baseDao.updateDatas(enterEntity);
			//baseDao.updateDatas(personEntity);
			isSubmitOk = saveEnterpriseOk && savePersonOk && saveProcreditOk;
			if(isSubmitOk){
				//log.info("新增责任公司抵押贷款信息成功!!!");
				JsonUtil.jsonFromObject("新增责任公司抵押贷款信息成功!!!", isSubmitOk) ;
			}else{
				//log.info("新增责任公司抵押贷款信息失败!!!");
				JsonUtil.jsonFromObject("新增责任公司抵押贷款信息失败!!!", isSubmitOk) ;
			}

		} catch (Exception e) {
			logger.error("新增责任公司抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//查看详情--从责任公司视图查询数据显示在更新和详情页面
	public List seeCompany(int id) throws Exception{
		try {
			return creditBaseDao.queryHql("from VProjMortCompany a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询责任公司抵押贷款信息出错:"+e.getMessage());
		}
		return null;
		
	}
	
	
	//更新记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCompany(int mortgageid,ProcreditMortgageEnterprise procreditMortgageEnterprise,
			ProcreditMortgage procreditMortgage,Enterprise enterprise,Person person,int legalpersonid) throws Exception{
		
		boolean isUpdateOk = false;
		
		ProcreditMortgageEnterprise projMorEnter = null;
		if(procreditMortgageEnterprise != null){
			projMorEnter = updateMorEnterpriseInfo(mortgageid,procreditMortgageEnterprise);
			try {
				if("Financing".equals(procreditMortgage.getBusinessType())){
					SlCompanyMain comMain=slCompanyMainService.get(new Long(procreditMortgage.getAssureofname()));
					SlPersonMain perMain=slPersonMainService.get(new Long(legalpersonid));
					if(comMain!=null){
						comMain.setSjjyAddress(enterprise.getArea());
						comMain.setBusinessCode(enterprise.getCciaa());
						comMain.setTel(enterprise.getTelephone());
						if(perMain!=null){
							comMain.setPersonMainId(new Long(legalpersonid));
							comMain.setLawName(perMain.getName());
							perMain.setLinktel(person.getCellphone());
						}
						slCompanyMainService.merge(comMain);
						slPersonMainService.merge(perMain);
					}
				}else{
					//更新企业信息到企业表
					Enterprise enterEntityUpdate = (Enterprise) creditBaseDao.getById(Enterprise.class, enterprise.getId());
					if(enterEntityUpdate!=null){
						PropertyUtils.copyNotNullProperties(enterEntityUpdate,enterprise);
					}else{
						JsonUtil.responseJsonString("success:true,msg:'对应企业信息不存在! '") ;
					}
					
					//更新个人信息到个人表--电话号码
					Person personEntityUpdate = (Person) creditBaseDao.getById(Person.class, legalpersonid);
					if(personEntityUpdate!=null){
						PropertyUtils.copyNotNullProperties(personEntityUpdate,person);
					}else{
						JsonUtil.responseJsonString("success:true,msg:'对应个人信息不存在! '") ;
					}
				}
				
				mortgageService.updateMortgage(mortgageid, procreditMortgage);
				isUpdateOk = creditBaseDao.updateDatas(projMorEnter);
				if(isUpdateOk){
					//log.info("更新反担保责任公司抵押贷款信息成功!!!");
					JsonUtil.jsonFromObject("更新责任公司抵押贷款信息成功!!!", isUpdateOk) ;
				}else{
					JsonUtil.jsonFromObject("更新责任公司抵押贷款信息失败!!!", isUpdateOk) ;
				}
			} catch (Exception e) {
				logger.error("更新责任公司抵押贷款信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	//根据mortgageid查询实体并且更新
	public List seeCompanyForUpdate(int id) throws Exception {
		try {
			return creditBaseDao.queryHql("from ProcreditMortgageEnterprise a where a.mortgageid=? ",id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询责任公司抵押贷款信息出错:"+e.getMessage());
		}
		return null;
	}
	
	//根据反担保表中企业id查询企业实体获取法人代表人id等信息
	public EnterpriseView getEnterpriseObj(int id){
		EnterpriseView ev = null;
		try {
			ev = (EnterpriseView) creditBaseDao.getById(EnterpriseView.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询企业信息出错:"+e.getMessage());
		}
		return ev;
	}
	
	private ProcreditMortgageEnterprise updateMorEnterpriseInfo(int mortgageid,ProcreditMortgageEnterprise enter){
		ProcreditMortgageEnterprise proMorEnter = null;
		try{
			List list=this.seeCompanyForUpdate(mortgageid);
			proMorEnter=(ProcreditMortgageEnterprise)list.get(0);
			if(proMorEnter != null){
				//proMorEnter.setEnterprisename(enter.getEnterprisename());//公司名称
				//proMorEnter.setLicensenumber(enter.getLicensenumber());//营业执照号码
				//proMorEnter.setRegisteraddress(enter.getRegisteraddress());//注册地址
				proMorEnter.setNetassets(enter.getNetassets());//净资产(万元)
				//proMorEnter.setCorporate(enter.getCorporate());//法人代表
				proMorEnter.setSocietynexusid(enter.getSocietynexusid());//社会关系
				//proMorEnter.setCorporatetel(enter.getCorporatetel());//法人代表电话
				proMorEnter.setModelrangeprice(enter.getModelrangeprice());//模型估价(万元)
				proMorEnter.setBusiness(enter.getBusiness());
				proMorEnter.setAssets(enter.getAssets());
				proMorEnter.setMonthlyIncome(enter.getMonthlyIncome());
			}
		}catch(Exception e){
			logger.error("更新责任公司抵押贷款信息出错:"+e.getMessage());
			e.printStackTrace();
		}
		return proMorEnter;
	}
}
