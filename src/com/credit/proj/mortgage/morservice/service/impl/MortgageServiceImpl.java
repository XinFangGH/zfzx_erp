package com.credit.proj.mortgage.morservice.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryFinance;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.morservice.service.pageconfig;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.system.Dictionary;

@SuppressWarnings("all")
public class MortgageServiceImpl implements MortgageService{

	//private final Log log = LogFactory.getLog(getClass());
	private static final Log logger=LogFactory.getLog(MortgageServiceImpl.class);
	private CreditBaseDao creditBaseDao;
	@Resource
	private DictionaryDao dictionaryDao;

	
	public CreditBaseDao getCreditBaseDao() {
		return creditBaseDao;
	}

	public void setCreditBaseDao(CreditBaseDao creditBaseDao) {
		this.creditBaseDao = creditBaseDao;
	}
	

	//节点26根据martgageid获取该条记录--从视图
	public void ajaxMortgageManageSeeNode26(int id) {
		try{
			VProcreditDictionary vProcreditDictionary = (VProcreditDictionary)creditBaseDao.getById(VProcreditDictionary.class, id);
			if(vProcreditDictionary!=null){
				JsonUtil.jsonFromObject(vProcreditDictionary, true) ;
			}else{
				JsonUtil.responseJsonString("{success:false,msg:'该记录已经不存在!'}") ;
			}
		}catch(Exception e){
			logger.error("根据mortgageid查询信息出错:"+e.getMessage());
			e.printStackTrace() ;
		}
	}
	
	//节点26 修改的方法
	public void ajaxUpdateMortgageManageNode26(ProcreditMortgage procreditMortgage,int mortgageid,int beforeOrMiddleType){
		boolean isUpdateNode26InfoOk = false;
		try{
			ProcreditMortgage projMortgage = (ProcreditMortgage)creditBaseDao.getById(ProcreditMortgage.class, mortgageid);
			if(beforeOrMiddleType == 1){
				//保前节点的修改
				projMortgage.setBargainNum(procreditMortgage.getBargainNum());//反担保合同编号
				projMortgage.setPlanCompleteDate(procreditMortgage.getPlanCompleteDate());//计划登记完成日期
				projMortgage.setPledgenumber(procreditMortgage.getPledgenumber());//抵质押登记号
				projMortgage.setEnregisterdate(procreditMortgage.getEnregisterdate());//登记日期
				projMortgage.setIsAuditingPass(procreditMortgage.getIsAuditingPass());//反担保手续是否审验通过
				//projMortgage.setStatusid(procreditMortgage.getStatusid());//登记状态
				projMortgage.setEnregisterperson(procreditMortgage.getEnregisterperson());//登记人
				//projMortgage.setRemarks(procreditMortgage.getRemarks());//备注lessDatumRecord
				
				projMortgage.setReplenishTimeLimit(procreditMortgage.getReplenishTimeLimit());
				projMortgage.setReplenishDate(procreditMortgage.getReplenishDate());
				projMortgage.setBusinessPromise(procreditMortgage.getBusinessPromise());
				projMortgage.setIsReplenish(procreditMortgage.getIsReplenish());
				projMortgage.setNeedDatumList(procreditMortgage.getNeedDatumList());
				projMortgage.setReceiveDatumList(procreditMortgage.getReceiveDatumList());
				projMortgage.setLessDatumRecord(procreditMortgage.getLessDatumRecord());
			}else{
				//保中节点的修改
				projMortgage.setUnchainofperson(procreditMortgage.getUnchainofperson());//解除人
				projMortgage.setUnchaindate(procreditMortgage.getUnchaindate());//解除日期
			}
			
			projMortgage.setStatusid(procreditMortgage.getStatusid());//登记状态
			projMortgage.setRemarks(procreditMortgage.getRemarks());//备注
			//PropertyUtils.copyNotNullProperties(projMortgage, procreditMortgage) ;
			isUpdateNode26InfoOk = creditBaseDao.updateDatas(projMortgage);
			if(isUpdateNode26InfoOk){
				JsonUtil.responseJsonString("{success:true,msg:'编辑<反担保措施>信息成功!!!'}");
			}else{
				JsonUtil.responseJsonString("{success:false,msg:'编辑<反担保措施>信息失败!!!'}");
			}
		}catch(Exception e){
			logger.error("更新反担保措施信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	//异步添加记录
	@Transactional(propagation = Propagation.REQUIRED)
	public void addMortgage(ProcreditMortgage procreditMortgage) throws Exception {
		boolean isSubmitOk = false;
		String needDatumList = "";
		String dicKey = "";
		int typeId = procreditMortgage.getMortgagenametypeid();
		
		dicKey = pageconfig.mortgageDicKey(typeId);
		List<Dictionary> list= dictionaryDao.getByDicKey(dicKey);
		if(list!=null&&list.size()!=0){
			needDatumList = list.get(0).getDescp();
		}
		
		/*if("Financing".equals(procreditMortgage.getBusinessType())){
			procreditMortgage.setIsPledged((short)0);
		}*/
		//boolean saveContractOk = false;
		//String hql="from SlSmallloanProject sl where sl.projectId=?";
		//List listP = new ArrayList();
		//SlSmallloanProject enterProject = null;
		
		//ProcreditContract procreditContract = new ProcreditContract();
		
		try {
			//procreditMortgage.setContractid(0);
			procreditMortgage.setNeedDatumList(needDatumList);
			procreditMortgage.setIsPledged((short)0);//是否已抵押。0:否；1：是  (目前融资抵质押物用到)
			procreditMortgage.setIsDel((short)0);//是否已删除的标志。0:否；1：是 (目前融资抵质押物用到)
			isSubmitOk = creditBaseDao.saveDatas(procreditMortgage);
			if(isSubmitOk){
				//JsonUtil.jsonFromObject("新增反担保信息成功!!!", isSubmitOk) ;//add by zhangchuyan★★★
				/*if(processType==1){
					saveContractOk = this.saveContractCategory(procreditMortgage);
				}else{
					//保存到合同表
					listP = creditBaseDao.queryHql(hql, new Long(procreditMortgage.getProjid()));
					if(listP==null||listP.size()==0){
						enterProject=null;
					}else{
						enterProject = (SlSmallloanProject)listP.get(0);
					}
					String hqlCon = "from VProcreditContract AS c where c.projId=? and c.mortgageId !=0";
					List list = creditBaseDao.queryHql(hqlCon, procreditMortgage.getProjid()) ;
					int len = 0 ;
					if(null == list){
						len = 4 ;
					}else
						len = list.size() + 4;
					procreditContract.setContractNumber(enterProject.getProjectNumber() +"-"+ len);
					
					procreditContract.setProjId(procreditMortgage.getProjid());
					procreditContract.setMortgageId(procreditMortgage.getId());
					procreditContract.setContractName(procreditMortgage.getMortgagename()+"-反担保合同");
					procreditContract.setContractType(2);
					procreditContract.setTemplateId(0);
					saveContractOk = creditBaseDao.saveDatas(procreditContract);
				}*/
			}else{
				//JsonUtil.jsonFromObject("新增反担保信息失败!!!", isSubmitOk) ;//★★★
			}
			/*if(saveContractOk){
				//log.info("新增反担保信息成功!!!");
				JsonUtil.jsonFromObject("新增反担保信息成功!!!", isSubmitOk) ;
			}else{
				log.info("新增反担保信息失败!!!");
				JsonUtil.jsonFromObject("新增反担保信息失败!!!", isSubmitOk) ;
			}*/

		} catch (Exception e) {
			logger.error("新增反担保信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private boolean saveContractCategory(ProcreditMortgage procreditMortgage){
		
		boolean saveOk = false;
		
		String hql = "from ProcreditContract pc where pc.projId=?";
		List list = null;
		
		String getOrderNumhql = "from ProcreditContract pc where pc.projId=? and pc.mortgageId!=0";
		List orderList = null;
		ProcreditContract pc = null;
		int orderNum = 0;
		
		AreaDic area = null;
		AreaDic areaDic = null;
		String contractCategoryTypeText = "";//合同分类名称
		String contractCategoryText = "";//具体合同类型名称
		
		try {
			list = creditBaseDao.queryHql(hql,procreditMortgage.getProjid());
			if(list==null||list.size()==0){
				saveOk = true;
			}else{
				orderList = creditBaseDao.queryHql(getOrderNumhql, procreditMortgage.getProjid());
				if(orderList!=null || orderList.size() != 0){
					pc = (ProcreditContract)orderList.get(0);
					orderNum = pc.getOrderNum();
				}else{
					orderNum = this.returnOrderNum();
				}
				area = (AreaDic)creditBaseDao.getById(AreaDic.class, procreditMortgage.getAreaId());
				if(area!=null){
					contractCategoryText = area.getText();
					areaDic = (AreaDic)creditBaseDao.getById(AreaDic.class, area.getParentId());
					if(areaDic!=null){
						contractCategoryTypeText = areaDic.getText();
					}
				}
				ProcreditContract pcc = new ProcreditContract();
				pcc.setParentId(procreditMortgage.getAreaId());
				pcc.setNumber(procreditMortgage.getMortgagenametypeid());
				pcc.setProjId(procreditMortgage.getProjid().toString());
				pcc.setMortgageId(procreditMortgage.getId());
				pcc.setIsOld(0);
				pcc.setOrderNum(orderNum);
				pcc.setRemark(procreditMortgage.getRemarks());
				pcc.setLocalParentId(0);
				pcc.setContractCategoryText(contractCategoryText);
				pcc.setContractCategoryTypeText(contractCategoryTypeText);
				
				saveOk = creditBaseDao.saveDatas(pcc);
				if(saveOk){
					saveOk = true;
				}else{
					saveOk = false;
				}
			}
		} catch (Exception e) {
			logger.error("保存反担保信息到合同分类表出错:"+e.getMessage());
			e.printStackTrace();
		}
		
		return saveOk;
	}
	
	private int returnOrderNum(){
		int orderNum = 0;
		
		String hql = "from AreaDic a where a.lable=?" ;
		List list = null;
		AreaDic area = null;
		int number = 0;
		
		try {
			list = creditBaseDao.queryHql(hql, "contractCategory");
			if(list!=null || list.size()!=0){
				for(int i=0;i<list.size();i++){
					area = (AreaDic)list.get(i);
					number = area.getNumber();
					if(number==210){
						orderNum = area.getOrderid();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("returnOrderNum出错:"+e.getMessage());
		}
		
		return orderNum;
	}

	//查询记录列表
	/*public void queryMortgage(int start ,int limit) throws Exception {
		List totalList = creditBaseDao.queryHql("select count(a) from VProcreditDictionary a") ;
		int totalProperty = ((Long)totalList.get(0)).intValue() ;//记录总数
		List list=creditBaseDao.queryHql("from VProcreditDictionary a order by a.id desc", start, limit);
		JsonUtil.jsonFromList(list,totalProperty);
	}*/
	
	//查看详情-- 从视图(小额)显示在详情页面
	public VProcreditDictionary seeMortgage(int id) throws Exception {
		VProcreditDictionary vp = (VProcreditDictionary)creditBaseDao.getById(VProcreditDictionary.class, id);
		return vp ;
	}
	
	//查看详情-- 从视图(担保)显示在详情页面
	public VProcreditDictionaryGuarantee seeMortgageGuarantee(int id) throws Exception {
		VProcreditDictionaryGuarantee vp = (VProcreditDictionaryGuarantee)creditBaseDao.getById(VProcreditDictionaryGuarantee.class, id);
		return vp ;
	}
	
	//查看详情-- 从视图(融资)显示在详情页面
	public VProcreditDictionaryFinance seeMortgageFinance(int id) throws Exception{
		VProcreditDictionaryFinance vf = (VProcreditDictionaryFinance) creditBaseDao.getById(VProcreditDictionaryFinance.class, id);
		return vf;
	}
	//查看详情-- 从视图(融资租赁)显示在详情页面    add by gaoqingrui
	public VProcreditMortgageLeaseFinance seeMortgageLeaseFinance(int id) throws Exception{
		VProcreditMortgageLeaseFinance vf = (VProcreditMortgageLeaseFinance) creditBaseDao.getById(VProcreditMortgageLeaseFinance.class, id);
		return vf;
	}
	

	
	//异步更新记录
	public void updateMortgage(int mortgageid,ProcreditMortgage procreditMortgage) throws IOException{
		boolean isUpdateOk = false;
		ProcreditMortgage projMortgage = null;
		if(procreditMortgage != null){
			projMortgage = updateMortgageInfo(mortgageid,procreditMortgage);
			try {
				isUpdateOk = creditBaseDao.updateDatas(projMortgage);
				if(isUpdateOk){
					//JsonUtil.jsonFromObject("更新反担保信息成功!!!", isUpdateOk) ;
				}else{
					//JsonUtil.jsonFromObject("更新反担保信息失败!!!", isUpdateOk) ;
				}

			} catch (Exception e) {
				logger.error("更新反担保信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	public void updateMortgageById(int mortgageid,ProcreditMortgage procreditMortgage) throws IOException{
		ProcreditMortgage projMortgage = null;
		if(procreditMortgage != null){
			projMortgage = updateMortgageInfo(mortgageid,procreditMortgage);
			try {
				creditBaseDao.updateDatas(projMortgage);
			} catch (Exception e) {
				logger.error("更新反担保信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	//抵质押物归档确认
	public void archiveConfirm(int mortgageid,ProcreditMortgage procreditMortgage) throws IOException{
		boolean isUpdateOk = false;
		ProcreditMortgage projMortgage = null;
		if(procreditMortgage != null){
			projMortgage = updateProcreditMortgage(mortgageid,procreditMortgage);
			try {
				isUpdateOk = creditBaseDao.updateDatas(projMortgage);
				if(isUpdateOk){
					//log.info("更新反担保信息成功!!!");
					JsonUtil.jsonFromObject("更新反担保信息成功!!!", isUpdateOk) ;
				}else{
					JsonUtil.jsonFromObject("更新反担保信息失败!!!", isUpdateOk) ;
				}

			} catch (Exception e) {
				logger.error("更新反担保信息出错:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	//更新或新增多条记录
	public boolean updateMortgages(ArrayList entities) throws Exception {
		boolean b = false ;
		try{
			creditBaseDao.saveOrUpdateAll(entities) ;
			b = true ;
		}catch(DataAccessException e){
			b = false ;
			logger.error("更新或新增多条反担保信息出错:"+e.getMessage());
		}
		return b ;
	}
	
	//查询记录从反担保主表
	public ProcreditMortgage seeMortgage(Class entityClass,Serializable id) throws Exception {
		ProcreditMortgage procreditMortgage = (ProcreditMortgage)creditBaseDao.getById(entityClass, id) ;
		return procreditMortgage ;
	}
	
	//删除主表记录
	public boolean deleteMortgage(Class entityClass,int id) throws Exception {
		boolean isDeleteTrue = false;
		try{
			isDeleteTrue = creditBaseDao.deleteDatas(entityClass, id);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("删除反担保主表信息出错:"+e.getMessage());
		}
		return isDeleteTrue;
	}
	
	
	//删除合同表对应的反担保记录
	public boolean deleteContractMortgage(int id) throws Exception {
		boolean isDeleteContractTrue = false;
		
		ProcreditContract pc = null;
		List listPc = null;
		String hqlPc = "from ProcreditContract pc where pc.mortgageId = "+id;
		
		try{
			listPc = creditBaseDao.queryHql(hqlPc);
			if(listPc == null || listPc.size() == 0){
				isDeleteContractTrue = true;
			}else{
				pc = (ProcreditContract)listPc.get(0);
				isDeleteContractTrue = creditBaseDao.deleteDatas(pc);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("删除合同表对应的反担保记录信息出错:"+e.getMessage());
		}
		return isDeleteContractTrue;
	}

	//根据主表id删除对应表的记录
	public boolean deleteObjectDatas(int typeid,int id,String objectType) throws Exception {
		boolean isDeleteObjectDatasOk = false;
		String tableObjectName = "";
		String hql = " as t where t.mortgageid="+id;//+" and t.objectType='"+objectType+"'";
		try{
			
			switch(typeid){
			case 1:
				tableObjectName = "ProcreditMortgageCar";
				break;
			case 2:
				tableObjectName = "ProcreditMortgageStockownership";
				break;
			case 3:
				tableObjectName = "ProcreditMortgageEnterprise";
				break;
			case 4:
				tableObjectName = "ProcreditMortgagePerson";
				break;
			case 5:
				tableObjectName = "ProcreditMortgageMachineinfo";
				break;
			case 6:
				tableObjectName = "ProcreditMortgageProduct";
				break;
			case 7:
				tableObjectName = "ProcreditMortgageHouse";
				break;
			case 8:
				tableObjectName = "ProcreditMortgageOfficebuilding";
				break;
			case 9:
				tableObjectName = "ProcreditMortgageHouseground";
				break;
			case 10:
				tableObjectName = "ProcreditMortgageBusiness";
				break;
			case 11:
				tableObjectName = "ProcreditMortgageBusinessandlive";
				break;
			case 12:
				tableObjectName = "ProcreditMortgageEducation";
				break;
			case 13:
				tableObjectName = "ProcreditMortgageIndustry";
				break;
			case 14:
				tableObjectName = "ProcreditMortgageDroit";
				break;
			case 15:
				tableObjectName = "ProcreditMortgageHouse";
				break;
			case 16:
				tableObjectName = "ProcreditMortgageHouse";
				break;
			case 17:
				tableObjectName = "ProcreditMortgageHouse";
				break;
			default:
				tableObjectName = "";
			}
			if(tableObjectName=="")
				return false;
			String deleteHql = "delete from "+tableObjectName+hql;
			
			isDeleteObjectDatasOk = creditBaseDao.excuteSQL(deleteHql);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("删除反担保附表记录信息出错:"+e.getMessage());
		}
		return isDeleteObjectDatasOk;
	}
	
	//删除合同分类表的反担保信息
	private boolean deleteMortgageDataFromContractCategory(int id){
		
		boolean deleteOk = false;
		String hql = "from ProcreditContract p where p.mortgageId=?";
		//String deleteHql = "delete "+hql;
		List list = null;
		ProcreditContract pc = null;
		
		try {
			list = creditBaseDao.queryHql(hql,id);
			if(list==null||list.size()==0){
				deleteOk = true;
			}else{
				pc = (ProcreditContract)list.get(0);
				deleteOk = creditBaseDao.deleteDatas(pc);
				if(deleteOk){
					deleteOk = true;
				}else{
					deleteOk = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除合同分类表的反担保信息出错:"+e.getMessage());
		}
		
		return deleteOk;
		
	}
	

	//为删除主表,次表添加事务的删除方法
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAllObjectDatas(Class entityClass,String mortgageIds) throws Exception {
		boolean isDeleteAll = false;
		try{
			if(null!=mortgageIds&&!"".equals(mortgageIds)){
				String[] mortIds = mortgageIds.split(",");
				for(int i = 0;i<mortIds.length;i++){
					int morttgageId = Integer.parseInt(mortIds[i]);
					ProcreditMortgage pm = new ProcreditMortgage();
					int typeId = ((ProcreditMortgage) creditBaseDao.getById(entityClass, morttgageId)).getMortgagenametypeid();
					isDeleteAll = ((this.deleteMortgage(entityClass, morttgageId)) && (this.deleteObjectDatas(typeId, morttgageId,"mortgage")));
				}
			}
			//接口类定义的删除方法，所以是public
			//isDeleteAll = ((this.deleteMortgage(entityClass, id)) && (this.deleteObjectDatas(typeid, id)));// && (this.deleteContractMortgage(id)) && (this.deleteMortgageDataFromContractCategory(id)));
			if(isDeleteAll){
				JsonUtil.jsonFromObject("删除成功!", true);
			}else{
				JsonUtil.jsonFromObject("删除失败!", false);
			}
		}catch(Exception e){
			logger.error("删除反担保主表及附表信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private ProcreditMortgage updateMortgageInfo(int mortgageid,ProcreditMortgage procreditMortgage){
		ProcreditMortgage projMort = null;
		try{
			projMort = (ProcreditMortgage)creditBaseDao.getById(ProcreditMortgage.class, mortgageid);
			//projMort.setAssuretypeid(procreditMortgage.getAssuretypeid());//担保类型id
			projMort.setAssureofname(procreditMortgage.getAssureofname());//反担保人(企业)名称
			projMort.setMortgagename(procreditMortgage.getMortgagename());//反担保物名称
			//projMort.setMortgagepersontypeid(procreditMortgage.getMortgagepersontypeid());////反担保人类型
			projMort.setAssuremoney(procreditMortgage.getAssuremoney());//可担保额度(万元)
			projMort.setAssuremodeid(procreditMortgage.getAssuremodeid());//保证方式
			projMort.setFinalprice(procreditMortgage.getFinalprice());//最终估价
			projMort.setMortgageRemarks(procreditMortgage.getMortgageRemarks());//备注
			projMort.setPersonType(procreditMortgage.getPersonType());//所有人类型
			projMort.setAssuretypeid(procreditMortgage.getAssuretypeid());//担保类型
			projMort.setRelation(procreditMortgage.getRelation());//所有权人和借款人的关系
			projMort.setValuationMechanism(procreditMortgage.getValuationMechanism());//评估机构
			projMort.setValuationTime(procreditMortgage.getValuationTime());//评估时间
			projMort.setFinalCertificationPrice(procreditMortgage.getFinalCertificationPrice());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新反担保主表信息出错:"+e.getMessage());
		}
		return projMort;
	}
	
	public ProcreditMortgage updateProcreditMortgage(int mortgageid,ProcreditMortgage procreditMortgage) {
		ProcreditMortgage projMort = null;
		try{
			projMort = (ProcreditMortgage)creditBaseDao.getById(ProcreditMortgage.class, mortgageid);
			if(procreditMortgage.getIsArchiveConfirm() != null) {
				projMort.setIsArchiveConfirm(procreditMortgage.getIsArchiveConfirm());//是否归档
			}
			if(procreditMortgage.getRemarks() != null) {
				projMort.setRemarks(procreditMortgage.getRemarks());//归档备注
			}
			if(procreditMortgage.getIsTransact()!=null){
				projMort.setIsTransact(procreditMortgage.getIsTransact());//是否办理
			}
			if(procreditMortgage.getTransactDate() !=null){
				projMort.setTransactDate(procreditMortgage.getTransactDate());//办理时间
			}
			if(procreditMortgage.getPledgenumber() !=null){
				projMort.setPledgenumber(procreditMortgage.getPledgenumber());//反担保物登记号
			}
			if(procreditMortgage.getEnregisterDepartment()!=null){
				projMort.setEnregisterDepartment(procreditMortgage.getEnregisterDepartment());//登记机关
			}
			if(procreditMortgage.getUnchaindate() !=null){
				projMort.setUnchaindate(procreditMortgage.getUnchaindate());//释放时间
			}
			if(procreditMortgage.getUnchainremark() !=null){
				projMort.setUnchainremark(procreditMortgage.getUnchainremark());//释放备注
			}if(procreditMortgage.getIsunchain() !=null){
				projMort.setIsunchain(procreditMortgage.getIsunchain());//是否释放
			}
			if(procreditMortgage.getMortgageStatus() !=null){
				projMort.setMortgageStatus(procreditMortgage.getMortgageStatus());//抵质押物状态
			}
			if(procreditMortgage.getTransactRemarks() !=null){
				projMort.setTransactRemarks(procreditMortgage.getTransactRemarks());//办理备注
			}
			if(procreditMortgage.getTransactPerson() !=null){
				projMort.setTransactPerson(procreditMortgage.getTransactPerson());//办理经办人
			}
			if(procreditMortgage.getUnchainPerson() !=null){
				projMort.setUnchainPerson(procreditMortgage.getUnchainPerson());//解除经办人 
			}
			if(procreditMortgage.getTransactPersonId() !=null){
				projMort.setTransactPersonId(procreditMortgage.getTransactPersonId());//办理经办人
			}
			if(procreditMortgage.getUnchainPersonId() !=null){
				projMort.setUnchainPersonId(procreditMortgage.getUnchainPersonId());//解除经办人 
			}
			if(procreditMortgage.getIsHandle() !=null){
				projMort.setIsHandle(procreditMortgage.getIsHandle());//是否处理
			}
			if(procreditMortgage.getIsRecieve()!=null){
				projMort.setIsRecieve(procreditMortgage.getIsRecieve());//是否收到
			}
			if(procreditMortgage.getRecieveDate()!=null){
				projMort.setRecieveDate(procreditMortgage.getRecieveDate());//收到时间
			}
			if(procreditMortgage.getRecieveRemarks()!=null){
				projMort.setRecieveRemarks(procreditMortgage.getRecieveRemarks());//收到备注
			}
			if(null!=procreditMortgage.getIsTransact() && procreditMortgage.getIsTransact()==true && procreditMortgage.getTractCreateTime()==null){
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				projMort.setTractCreateTime(df.parse(df.format(new Date())));
			}
			if(null!=procreditMortgage.getIsunchain()&& procreditMortgage.getIsunchain()==true && procreditMortgage.getUnchainCreateTime()==null){
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				projMort.setUnchainCreateTime(df.parse(df.format(new Date())));
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新反担保主表信息出错:"+e.getMessage());
		}
		return projMort;
	}
	
	//根据查询条件或查询全部记录
	public void queryMortgage(String assureofname,int typeid, String projnum, int statusid,
			String enterprisename,double finalpriceOne,double finalpriceTow,int start, int limit,
			String sort,String dir) throws Exception {
		
		StringBuffer strHql = new StringBuffer("from VProcreditDictionary vp where 1=1");
		if(assureofname !=null && !"".equals(assureofname)){
			strHql.append(" and vp.assureofnameEnterOrPerson like '%").append(assureofname).append("%'");
		}
		
		if(typeid != 0){
			strHql.append(" and typeid = ").append(typeid);
		}
		
		if(projnum !=null && !"".equals(projnum)){
			strHql.append(" and vp.projnum = '").append(projnum).append("'");
		}
		
		if(statusid != 0){
			strHql.append(" and vp.statusid = ").append(statusid);
		}
		
		if(enterprisename !=null && !"".equals(enterprisename)){
			strHql.append(" and vp.enterprisename like '%").append(enterprisename).append("%'");
		}
		
		if(finalpriceOne != 0){
			strHql.append(" and vp.finalprice >= ").append(finalpriceOne);
		}
		
		if(finalpriceTow != 0){
			strHql.append(" and vp.finalprice <= ").append(finalpriceTow);
		}
		
		if(sort!=null && dir!=null){
			strHql.append(" order by CONVERT(vp.").append(sort).append(" , 'GBK') ").append(dir);//+sort+" , 'GBK') "+dir");
		}else{
			strHql.append(" order by vp.id desc");
		}
		
		String hql="select count(*) "+strHql.toString();
		
		List list = null;
		List totalList = null;
		try{
			list = creditBaseDao.queryHql(strHql.toString(), start, limit);
			totalList = creditBaseDao.queryHql(hql.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("根据查询条件或查询全部记录信息出错:"+e.getMessage());
		}
		
		int result=Integer.parseInt(totalList.get(0).toString());
		JsonUtil.jsonFromList(list,result);
	}
	
	//反担保列表根据项目id查询主表数据
	public List ajaxGetMortgageAllDataByProjectId(Long projectId,String businessType,String htType){
		
		/*VProcreditDictionary vProcreditDictionary = null;
		VProcreditDictionaryGuarantee vpdg = null;
		VProcreditDictionaryFinance vpdf = null;*/
		
		int totalProperty = 0;//记录总数
		List list = new ArrayList();
		Object params[] = new Object[]{projectId} ;
		String objectName = "";
		
		if("SmallLoan".equals(businessType)){
			objectName = "VProcreditDictionary";
		}else if("Guarantee".equals(businessType)){
			objectName = "VProcreditDictionaryGuarantee";
		}else if("Financing".equals(businessType)){
			//objectName = "VProcreditDictionaryFinance";
			objectName = "VProcreditMortgageFinance";
		}
		
		String totalHql = "select count(a) from "+objectName+" a where a.projid=? and a.businessType='"+businessType+"'";
		String hql = "from "+objectName+" a where a.projid=? and a.businessType='"+businessType+"'";
		if(null!=htType && htType.equals("baozContract")){
			totalHql=totalHql+" and a.assuretypeid=606";
			hql=hql+" and a.assuretypeid=606";
		}
		if(null!=htType && htType.equals("thirdContract")){
			totalHql=totalHql+" and (a.assuretypeid=604 or a.assuretypeid=605)";
			hql=hql+" and (a.assuretypeid=604 or a.assuretypeid=605)";
		}
		hql+=" order by a.id desc";
		
		try {
			List totalList = creditBaseDao.queryHql(totalHql,params);
			totalProperty = ((Long) totalList.get(0)).intValue();
			list = creditBaseDao.queryHql(hql,params);
			if(list == null || list.size() == 0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据不同业务品种查询对应反担保信息出错:"+e.getMessage());
		}
		return list;
	}
	
	//根据业务种类和项目id获取抵质押物主表信息
	public List getByBusinessTypeProjectId(String businessType,Long projectId){
		List list = new ArrayList();
		String hql = "from ProcreditMortgage p where p.projid="+projectId+" and p.businessType='"+businessType+"'";
		try {
			list = creditBaseDao.queryHql(hql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据业务种类和项目id获取抵质押物主表信息:"+e.getMessage());
		}
		if(list==null||list.size()==0){
			return null;
		}else{
			return list;
		}
	}
	
	//融资项目添加抵质押物更新抵质押物表的isPledged状态。
	public void updateIsPledged(int mortgageId,short isPledged){
		try {
			ProcreditMortgage procreditMortgage = (ProcreditMortgage)creditBaseDao.getById(ProcreditMortgage.class, mortgageId) ;
			if(procreditMortgage!=null){
				procreditMortgage.setIsPledged(isPledged);
				creditBaseDao.updateDatas(procreditMortgage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("融资项目获取抵质押物信息出错:"+e.getMessage());
		}
	}
	
	//获取融资抵质押物信息-左侧菜单(我方抵质押物：是否已抵押的都显示isPledged=0,1)、融资项目信息节点(抵质押物参照：未抵押isPledged=0)
	public List getMortgageFinance(String isPledged,String mortgageType,String mortgageName,String finalpriceOne,String finalpriceTow,int start,int limit){
		
		StringBuffer strHql = new StringBuffer("from VProcreditDictionaryFinance vp where 1=1");
		
		if(isPledged !=null && !"".equals(isPledged)){
			strHql.append(" and vp.isPledged=0");
		}
		
		if(mortgageType != null && !"".equals(mortgageType)){
			
			strHql.append(" and vp.typeid = ").append(Integer.parseInt(mortgageType));
		}
		
		if(mortgageName !=null && !"".equals(mortgageName)){
			strHql.append(" and vp.mortgagename like '%").append(mortgageName).append("%'");
		}
		
		if(finalpriceOne != null && !"".equals(finalpriceOne)){
			strHql.append(" and vp.finalprice >= ").append(Double.valueOf(finalpriceOne));
		}
		
		if(finalpriceTow != null && !"".equals(finalpriceTow)){
			strHql.append(" and vp.finalprice <= ").append(Double.valueOf(finalpriceTow));
		}
		
		strHql.append(" and vp.isDel=0");
		
		strHql.append(" order by vp.id desc");
		
		String hql="select count(*) "+strHql.toString();
		
		List list = null;
		List totalList = null;
		try{
			list = creditBaseDao.queryHql(strHql.toString(), start, limit);
			totalList = creditBaseDao.queryHql(hql.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询融资抵质押物信息出错:"+e.getMessage());
		}
		
		return list;
	}
	
	//融资抵质押物假删除
	public void deleteMortgageFalse(String mortgageIds){
		try {
			if(mortgageIds!=null&&!"".equals(mortgageIds)){
				String[] proArrs = mortgageIds.split(",");
				for(int i=0;i<proArrs.length;i++){
					String mortgageId = proArrs[i];
					ProcreditMortgage pm = this.seeMortgage(ProcreditMortgage.class, Integer.parseInt(mortgageId));
					if(pm!=null){
						pm.setIsDel((short)1);
						creditBaseDao.updateDatas(pm);
					}
				}
			}
		} catch (Exception e) {
			logger.error("删除融资抵质押物信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List getMortgageList(String businessType,String mortgagename,String projectName,String projectNum,String mortgageType,String danbaoType,String minMoney,String maxMoney,int start,int limit,int status,String mortgageStatus,String companyId) {
		String objectName="";
		String proName="";
		if(null!=businessType && businessType.equals("SmallLoan")){
			objectName="VProcreditDictionary";
			proName="SlSmallloanProject";
		}else if(null!=businessType && businessType.equals("Guarantee")){
			objectName="VProcreditDictionaryGuarantee";
			proName="GLGuaranteeloanProject";
		}else if(null!=businessType && businessType.equals("Financing")){
			objectName="VProcreditMortgageFinance";
			proName="FlFinancingProject";
		}else if(null!=businessType && businessType.equals("LeaseFinance")){
			objectName="VProcreditMortgageLeaseFinance";
			proName="FlLeaseFinanceProject";
		}
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		List list=new ArrayList();
		List tocalList=null;
		try {
			if(null!=businessType){
				String hql="from "+objectName+" v where (v.assuretypeid=604 or v.assuretypeid=605)";
				if(null!=strs && !"".equals(strs)){
					hql=hql+" and v.projid in (select p.id from "+proName+" as p where p.companyId in ("+strs+"))";
				}
				hql=hql+" and (v.mortgagename like '%"+mortgagename+"%' or v.mortgagename is null) and v.projname like '%"+projectName+"%' and v.projnum like '%"+projectNum+"%'";
				
				if(null!=mortgageType && !"".equals(mortgageType)){
					hql=hql+" and v.typeid='"+mortgageType+"'";
				}
				if(null!=danbaoType && !"".equals(danbaoType)){
					hql=hql+" and v.assuretypeid="+Integer.parseInt(danbaoType);
				}
				if(null!=minMoney && !"".equals(minMoney) && null!=maxMoney && !"".equals(maxMoney)){
					hql=hql+" and v.finalprice between "+Double.parseDouble(minMoney)+" and "+Double.parseDouble(maxMoney); 
				}
				if(null!=mortgageStatus && !"".equals(mortgageStatus)){
					hql=hql+" and v.mortgageStatus='"+mortgageStatus+"'";
				}
				hql=hql+" order by v.id desc";
			    list = creditBaseDao.queryHql(hql, start, limit);
			    String counthql="select count(*) "+hql.toString();
			    tocalList=creditBaseDao.queryHql(counthql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(status==0){                             
		return list;
		}else{
			return tocalList;
		}
	}

	@Override
	public List<ProcreditMortgage> getList(int personType, int assureofname) {
		String hql="from ProcreditMortgage p where p.personType=? and p.assureofname=?";
		List<ProcreditMortgage> list=null;
		try {
			list=creditBaseDao.queryHql(hql, new Object[]{personType,assureofname});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List getMortgageOfDZ(Long projectId, String businessType) {

		
		/*VProcreditDictionary vProcreditDictionary = null;
		VProcreditDictionaryGuarantee vpdg = null;
		VProcreditDictionaryFinance vpdf = null;*/
		
		int totalProperty = 0;//记录总数
		List list = new ArrayList();
		Object params[] = new Object[]{projectId} ;
		String objectName = "";
		
		if("SmallLoan".equals(businessType) || "ExhibitionBusiness".equals(businessType)){
			objectName = "VProcreditDictionary";
		}else if("Guarantee".equals(businessType)){
			objectName = "VProcreditDictionaryGuarantee";
		}else if("Financing".equals(businessType)){
			objectName = "VProcreditMortgageFinance";
		}else if("LeaseFinance".equals(businessType)){
			objectName = "VProcreditMortgageLeaseFinance";
		}else if("Pawn".equals(businessType)){
			objectName = "VProcreditMortgageGlobal";//add by gao,为啥非要分类别呢？之前的所有的表的businessType字段就没意义了呀，所以现在采用不分类别，根据businessType筛选。
		}else if("matchingfundsBusiness".equals(businessType)){
			objectName = "VProcreditDictionary";
		}
		
		String hql = "from "+objectName+" a where a.projid=? and a.businessType='"+businessType+"' and (a.assuretypeid=604 or a.assuretypeid=605) order by a.id desc";
		try {
			list = creditBaseDao.queryHql(hql,params);
			if(list == null || list.size() == 0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据不同业务品种查询对应反担保信息出错:"+e.getMessage());
		}
		return list;
	
	}

	@Override
	public List getMortgageOfBZ(Long projectId, String businessType) {
		List list = new ArrayList();
		Object params[] = new Object[]{projectId} ;
		String objectName = "";
		
		if("SmallLoan".equals(businessType) || "ExhibitionBusiness".equals(businessType)){
			objectName = "VProcreditDictionary";
		}else if("Guarantee".equals(businessType)){
			objectName = "VProcreditDictionaryGuarantee";
		}else if("Financing".equals(businessType)){
			//objectName = "VProcreditDictionaryFinance";
			objectName = "VProcreditMortgageFinance";
		}else if("LeaseFinance".equals(businessType)){
			//objectName = "VProcreditDictionaryFinance";
			objectName = "VProcreditMortgageLeaseFinance";
		}else if("Pawn".equals(businessType)){
			//objectName = "VProcreditDictionaryFinance";
			objectName = "VProcreditMortgageGlobal";
		}else if("matchingfundsBusiness".equals(businessType)){
			objectName = "VProcreditDictionary";
		}
		
		String hql = "from "+objectName+" a where a.projid=? and a.businessType='"+businessType+"' and a.assuretypeid=606 order by a.id desc";
		
		try {
			list = creditBaseDao.queryHql(hql,params);
			if(list == null || list.size() == 0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据不同业务品种查询对应反担保信息出错:"+e.getMessage());
		}
		return list;
	}

	@Override
	public List getMortgageFinanceList(String isPledged, String type,int start,int limit) {
		StringBuffer strHql = new StringBuffer("from VProcreditDictionaryFinance vp where 1=1");
		strHql.append(" and vp.isDel=0");
		
		if(isPledged !=null && !"".equals(isPledged)){
			strHql.append(" and vp.isPledged=0");
		}
		if(null!=type && "DY".equals(type)){
			strHql.append(" and (vp.assuretypeid=604 or vp.assuretypeid=605)");

		}else if(null!=type && "BZ".equals(type)){
			strHql.append(" and vp.assuretypeid=606");
		}
		List list=null;
		try {
			list = creditBaseDao.queryHql(strHql.toString(), start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Long getMortgageFinanceCount(String isPledged, String type) {
	StringBuffer strHql = new StringBuffer("select count(*) from VProcreditDictionaryFinance vp where 1=1");
		
		if(isPledged !=null && !"".equals(isPledged)){
			strHql.append(" and vp.isPledged=0");
		}
		if(null!=type && "DY".equals(type)){
			strHql.append(" and (vp.assuretypeid=604 or vp.assuretypeid=605)");

		}else if(null!=type && "BZ".equals(type)){
			strHql.append(" and vp.assuretypeid=606");
		}
		long count=0;
		try {
			List list = creditBaseDao.queryHql(strHql.toString());
			if(null!=list && list.size()>0){
				count=(Long) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<ProcreditMortgage> getTransactMortgage(Date date,Long companyId) {
		String hql="from ProcreditMortgage as m where m.tractCreateTime=? and m.isTransact=true and (m.assuretypeid=604 or m.assuretypeid=605) and m.projid in (select s.projectId from SlSmallloanProject as s where s.companyId=?)";
		List<ProcreditMortgage> list=null;
		try {
			list=creditBaseDao.queryHql(hql, new Object[]{date,companyId});
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ProcreditMortgage> getUnchainMortgage(Date date,Long companyId) {
		String hql="from ProcreditMortgage as m where m.unchainCreateTime=? and m.isunchain=true and (m.assuretypeid=604 or m.assuretypeid=605) and m.projid in (select s.projectId from SlSmallloanProject as s where s.companyId=?)";
		List<ProcreditMortgage> list=null;
		try{
			list=creditBaseDao.queryHql(hql, new Object[]{date,companyId});
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deleteByMortgageIds(Long id) {
		String deleteHql="delete from ProcreditMortgage p where p.id="+id;
		try {
			creditBaseDao.excuteSQL(deleteHql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	//@HT_version3.0
	public List<ProcreditMortgage> getByProjectId(Long projectId) {
		String hql ="from ProcreditMortgage sl where sl.projid="+projectId;
		return creditBaseDao.findByHql(hql).list();
	}
}
