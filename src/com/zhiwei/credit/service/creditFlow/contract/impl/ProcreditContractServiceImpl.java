package com.zhiwei.credit.service.creditFlow.contract.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.contract.DocumentTempletDao;
import com.zhiwei.credit.dao.creditFlow.contract.ProcreditContractDao;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;

public class ProcreditContractServiceImpl extends BaseServiceImpl<ProcreditContract> implements ProcreditContractService {
    private ProcreditContractDao dao;
    @Resource
    private CreditBaseDao creditBaseDao;
    @Resource
    private DocumentTempletDao documentTempletDao;
	public ProcreditContractServiceImpl(ProcreditContractDao dao) {
		super(dao);
		this.dao=dao;
		// TODO Auto-generated constructor stub
	}
	@Override
	public ProcreditContract getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public void batchQSContract(String categoryIds) {
		if (!"".equals(categoryIds)) {
			String[] idArray = categoryIds.split(",");
			if (idArray.length > 0) {
				for (int i = 0; i < idArray.length; i++) {
					if (!"".equals(idArray[i]) && idArray[i] != null
							&& StringUtil.isNumeric(idArray[i])) {
						try {
							Object[] obj = { true, Integer.parseInt(idArray[i]) };
							ProcreditContract p=dao.getById(Integer.parseInt(idArray[i]));
							p.setIssign(true);
							p.setSignDate(new Date());
							dao.save(p);
							
							JsonUtil.responseJsonSuccess();
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							JsonUtil.responseJsonFailure();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							logger.error("批量签署合同出错:" + e.getMessage());
							e.printStackTrace();
							JsonUtil.responseJsonFailure();
						}
					}
				}
			}
		}
	}
	// 合同上传 add by zcb
	public int makeUpload(String contractId, String templateId,
			String contractType, String categoryId, String projId,
			Long thirdRalationId, String businessType, String rnum,
			String searchRemark, String contractNumber) throws Exception {
		int returnContractId = 0;
		DocumentTemplet template = null;
		ProcreditContract contract =dao.getById(Integer.valueOf(contractId));
		template =documentTempletDao.getById(Integer.parseInt(templateId));
		// template =
		// (DocumentTemplet)creditBaseDao.getById(DocumentTemplet.class,
		// template.getParentid());
		contract.setTemplateId(Integer.parseInt(templateId));
		/*if (contractNumber != null && !contractNumber.equals("")) {
			contract.setContractNumber(contractNumber);
		}*/
		if (searchRemark != null && !searchRemark.equals("")) {
			contract.setSearchRemark(searchRemark);
		}
		Long projId_Long = Long.parseLong(projId);

		BpFundProject smallProject = null;
		GLGuaranteeloanProject gloanProject = null;
		FlFinancingProject floanProject = null;
		SlSuperviseRecord slSuperviseRecord = null;
		if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {// 业务品种，小贷
			smallProject = (BpFundProject) creditBaseDao.getById(BpFundProject.class, projId_Long);
			String projectNumber = smallProject.getProjectNumber();// 项目编号
			/*if (null != categoryId && !"".equals(categoryId)) {
				ProcreditContractCategory pc = (ProcreditContractCategory) creditBaseDao
						.getById(ProcreditContractCategory.class, Integer
								.parseInt(categoryId));
				if (null != contract.getHtType() && contract.getHtType().equals("loanContract")) {
					contract.setContractNumber("LA-"+ projectNumber.replaceAll("_", "-"));
				} else if (null != contract.getHtType() && contract.getHtType().equals("baozContract")) {
					int count = Integer.parseInt(rnum);
					if (count < 10) {
						contract.setContractNumber("SA-"+ projectNumber.replaceAll("_", "-") + "-0"+ count);
					} else {
						contract.setContractNumber("SA-"+ projectNumber.replaceAll("_", "-") + "-"+ count);
					}

				} else if (null != contract.getHtType() && contract.getHtType().equals("thirdContract")) {
					if (null != thirdRalationId) {
						ProcreditMortgage mortgage = (ProcreditMortgage) creditBaseDao.getById(ProcreditMortgage.class,thirdRalationId.intValue());
						if (mortgage.getAssuretypeid() == 604) {
							int count = Integer.parseInt(rnum);
							if (count < 10) {
								contract.setContractNumber("MA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-0" + count);
							} else {
								contract.setContractNumber("MA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-" + count);
							}
						} else if (mortgage.getAssuretypeid() == 605) {
							int count = Integer.parseInt(rnum);
							if (count < 10) {
								contract.setContractNumber("PA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-0" + count);
							} else {
								contract.setContractNumber("PA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-" + count);
							}
						}
					}
				} else {
					contract.setContractNumber(projectNumber + "-" + rnum);
				}
			}*/
			// contract.setContractNumber(projectNumber+"-"+rnum);//借款合同编号}
			// contract.setContractNumber(smallProject.getProjectNumber());
			if ((smallProject.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(smallProject
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								/*+ "-" + rnum*/);
			} else if ((smallProject.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(smallProject.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText()/* + "-" + rnum*/);
			}
		} else if (businessType == "Guarantee"
				|| "Guarantee".equals(businessType)) {// 业务品种，企业贷
			gloanProject = (GLGuaranteeloanProject) creditBaseDao.getById(
					GLGuaranteeloanProject.class, projId_Long);
			String projectNumber = gloanProject.getProjectNumber();// 项目编号
			//contract.setContractNumber(projectNumber + "-" + rnum);
			if ((gloanProject.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(gloanProject
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								/*+ "-" + rnum*/);
			} else if ((gloanProject.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(gloanProject.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText()/* + "-" + rnum*/);
			}
		} else if (businessType == "Financing"
				|| "Financing".equals(businessType)) {
			floanProject = (FlFinancingProject) creditBaseDao.getById(
					FlFinancingProject.class, projId_Long);
			String projectNumber = floanProject.getProjectNumber();// 项目编号
			//contract.setContractNumber(projectNumber + "-" + rnum);// 借款合同编号}
			if ((floanProject.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(floanProject
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								/*+ "-" + rnum*/);
			} else if ((floanProject.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(floanProject.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() /*+ "-" + rnum*/);
			}
		} else if (businessType == "ExhibitionBusiness"
				|| "ExhibitionBusiness".equals(businessType)) {// 贷后 监管 展期，小贷 微贷
			slSuperviseRecord = (SlSuperviseRecord) creditBaseDao.getById(
					SlSuperviseRecord.class, projId_Long);
			SlSmallloanProject smallProjecttemp = (SlSmallloanProject) creditBaseDao
					.getById(SlSmallloanProject.class, slSuperviseRecord
							.getProjectId());
			String projectNumber = smallProjecttemp.getProjectNumber();// 项目编号

			//contract.setContractNumber(projectNumber + "-" + rnum);// 借款合同编号}
			// contract.setContractNumber(smallProject.getProjectNumber());
			if ((smallProjecttemp.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(smallProjecttemp
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								/*+ "-" + rnum*/);
			} else if ((smallProjecttemp.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(smallProjecttemp.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText()/* + "-" + rnum*/);
			}
		} else if (businessType == "Pawn" || "Pawn".equals(businessType)) {
			PlPawnProject project = (PlPawnProject) creditBaseDao.getById(
					PlPawnProject.class, projId_Long);
			String projectNumber = project.getProjectNumber();
			//contract.setContractNumber(projectNumber + "-" + rnum);
			if ((project.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(project
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								/*+ "-" + rnum*/);
			} else if ((project.getOppositeType()).equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(project.getOppositeID().toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() /*+ "-" + rnum*/);
			}
		} else if (businessType == "LeaseFinance"
				|| "LeaseFinance".equals(businessType)) {
			FlLeaseFinanceProject project = (FlLeaseFinanceProject) creditBaseDao
					.getById(FlLeaseFinanceProject.class, projId_Long);
			String projectNumber = project.getProjectNumber();
			//contract.setContractNumber(projectNumber + "-" + rnum);
			if ((project.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(project
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								/*+ "-" + rnum*/);
			} else if ((project.getOppositeType()).equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(project.getOppositeID().toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText()/* + "-" + rnum*/);
			}
		}

		//contract.setProjId(projId);
		// contract.setContractType(Integer.parseInt(contractType));
	//	contract.setCategoryId(Integer.parseInt(categoryId));
		contract.setIsDraftp(true);
		// contract.setMortgageId(0);
		if (null != thirdRalationId) {
			contract.setMortgageId(thirdRalationId.intValue());
		}
		contract.setDraftpId(1);
		// contract.setDraftpDate(new Timestamp(new Date().getTime()));
		contract.setDraftpDate(new Date());

		if (contractId != null) {
			if (Integer.parseInt(contractId) != 0) {
				ProcreditContract pc = (ProcreditContract) creditBaseDao
						.getById(ProcreditContract.class, Integer
								.parseInt(contractId));
				contract.setUrl(pc.getUrl());
				contract.setFileCount(pc.getFileCount());
				contract.setIsLegalCheck(pc.getIsLegalCheck());
				contract.setLegalCheckDate(pc.getLegalCheckDate());
				contract.setLegalCheckpId(pc.getLegalCheckpId());
				contract.setIssign(pc.getIssign());
				contract.setSignDate(pc.getSignDate());
				contract.setIssignId(pc.getIssignId());
				contract.setId(Integer.parseInt(contractId));

				creditBaseDao.clearSession();
				dao.merge(contract);
				returnContractId = Integer.parseInt(contractId);
			} else {
				contract.setFileCount("0");
				dao.save(contract);
				returnContractId = contract.getId();
			}
		} else {
			contract.setFileCount("0");
			dao.save(contract);
			returnContractId = contract.getId();
		}

		return returnContractId;
	}
	public int makeUpload(String contractId, String templateId,
			String contractType, String categoryId, String projId,
			Long thirdRalationId, String businessType, String rnum,
			String searchRemark) throws Exception {
		int returnContractId = 0;
		DocumentTemplet template = null;
		ProcreditContract contract = dao.getById(Integer.valueOf(contractId));
		template = (DocumentTemplet) creditBaseDao.getById(DocumentTemplet.class, Integer.parseInt(templateId));
		// template =
		// (DocumentTemplet)creditBaseDao.getById(DocumentTemplet.class,
		// template.getParentid());
		contract.setTemplateId(Integer.parseInt(templateId));
		if (searchRemark != null && !searchRemark.equals("")) {
			contract.setSearchRemark(searchRemark);
		}
		Long projId_Long = Long.parseLong(projId);

		BpFundProject smallProject = null;
		GLGuaranteeloanProject gloanProject = null;
		FlFinancingProject floanProject = null;
		SlSuperviseRecord slSuperviseRecord = null;
		if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {// 业务品种，小贷
			smallProject = (BpFundProject) creditBaseDao.getById(
					BpFundProject.class, projId_Long);
			String projectNumber = smallProject.getProjectNumber();// 项目编号
	/*		if (null != categoryId && !"".equals(categoryId)) {
				ProcreditContractCategory pc = (ProcreditContractCategory) creditBaseDao
						.getById(ProcreditContractCategory.class, Integer
								.parseInt(categoryId));
				if (null != pc.getHtType()
						&& pc.getHtType().equals("loanContract")) {
					contract.setContractNumber("LA-"
							+ projectNumber.replaceAll("_", "-"));
				} else if (null != pc.getHtType()
						&& pc.getHtType().equals("baozContract")) {
					int count = Integer.parseInt(rnum);
					if (count < 10) {
						contract.setContractNumber("SA-"
								+ projectNumber.replaceAll("_", "-") + "-0"
								+ count);
					} else {
						contract.setContractNumber("SA-"
								+ projectNumber.replaceAll("_", "-") + "-"
								+ count);
					}

				} else if (null != pc.getHtType()
						&& pc.getHtType().equals("thirdContract")) {
					if (null != thirdRalationId) {
						ProcreditMortgage mortgage = (ProcreditMortgage) creditBaseDao
								.getById(ProcreditMortgage.class,
										thirdRalationId.intValue());
						if (mortgage.getAssuretypeid() == 604) {
							int count = Integer.parseInt(rnum);
							if (count < 10) {
								contract.setContractNumber("MA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-0" + count);
							} else {
								contract.setContractNumber("MA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-" + count);
							}
						} else if (mortgage.getAssuretypeid() == 605) {
							int count = Integer.parseInt(rnum);
							if (count < 10) {
								contract.setContractNumber("PA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-0" + count);
							} else {
								contract.setContractNumber("PA-"
										+ projectNumber.replaceAll("_", "-")
										+ "-" + count);
							}
						}
					}
				} else {
					contract.setContractNumber(projectNumber + "-" + rnum);
				}
			}*/
			// contract.setContractNumber(projectNumber+"-"+rnum);//借款合同编号}
			// contract.setContractNumber(smallProject.getProjectNumber());
			if ((smallProject.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(smallProject
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								+ "-" + rnum);
			} else if ((smallProject.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(smallProject.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() + "-" + rnum);
			}
		} else if (businessType == "Guarantee"
				|| "Guarantee".equals(businessType)) {// 业务品种，企业贷
			gloanProject = (GLGuaranteeloanProject) creditBaseDao.getById(
					GLGuaranteeloanProject.class, projId_Long);
			String projectNumber = gloanProject.getProjectNumber();// 项目编号
			contract.setContractNumber(projectNumber + "-" + rnum);
			if ((gloanProject.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(gloanProject
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								+ "-" + rnum);
			} else if ((gloanProject.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(gloanProject.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() + "-" + rnum);
			}
		} else if (businessType == "Financing"
				|| "Financing".equals(businessType)) {
			floanProject = (FlFinancingProject) creditBaseDao.getById(
					FlFinancingProject.class, projId_Long);
			String projectNumber = floanProject.getProjectNumber();// 项目编号
			contract.setContractNumber(projectNumber + "-" + rnum);// 借款合同编号}
			if ((floanProject.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(floanProject
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								+ "-" + rnum);
			} else if ((floanProject.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(floanProject.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() + "-" + rnum);
			}
		} else if (businessType == "ExhibitionBusiness"
				|| "ExhibitionBusiness".equals(businessType)) {// 贷后 监管 展期，小贷 微贷
			slSuperviseRecord = (SlSuperviseRecord) creditBaseDao.getById(
					SlSuperviseRecord.class, projId_Long);
			SlSmallloanProject smallProjecttemp = (SlSmallloanProject) creditBaseDao
					.getById(SlSmallloanProject.class, slSuperviseRecord
							.getProjectId());
			String projectNumber = smallProjecttemp.getProjectNumber();// 项目编号

			contract.setContractNumber(projectNumber + "-" + rnum);// 借款合同编号}
			// contract.setContractNumber(smallProject.getProjectNumber());
			if ((smallProjecttemp.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(smallProjecttemp
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								+ "-" + rnum);
			} else if ((smallProjecttemp.getOppositeType())
					.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(smallProjecttemp.getOppositeID()
								.toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() + "-" + rnum);
			}
		} else if (businessType == "Pawn" || "Pawn".equals(businessType)) {
			PlPawnProject project = (PlPawnProject) creditBaseDao.getById(
					PlPawnProject.class, projId_Long);
			String projectNumber = project.getProjectNumber();
			contract.setContractNumber(projectNumber + "-" + rnum);
			if ((project.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(project
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								+ "-" + rnum);
			} else if ((project.getOppositeType()).equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(project.getOppositeID().toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() + "-" + rnum);
			}
		} else if (businessType == "LeaseFinance"
				|| "LeaseFinance".equals(businessType)) {
			FlLeaseFinanceProject project = (FlLeaseFinanceProject) creditBaseDao
					.getById(FlLeaseFinanceProject.class, projId_Long);
			String projectNumber = project.getProjectNumber();
			contract.setContractNumber(projectNumber + "-" + rnum);
			if ((project.getOppositeType()).equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(
						Enterprise.class, Integer.parseInt(project
								.getOppositeID().toString()));
				contract
						.setContractName(enter.getShortname() + "-"
								+ projectNumber + "-" + template.getText()
								+ "-" + rnum);
			} else if ((project.getOppositeType()).equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,
						Integer.parseInt(project.getOppositeID().toString()));
				contract.setContractName(person.getName() + "-" + projectNumber
						+ "-" + template.getText() + "-" + rnum);
			}
		}

		contract.setProjId(projId);
		// contract.setContractType(Integer.parseInt(contractType));
		//contract.setCategoryId(Integer.parseInt(categoryId));
		contract.setIsDraftp(true);
		// contract.setMortgageId(0);
		contract.setMortgageId(thirdRalationId.intValue());
		contract.setDraftpId(1);
		// contract.setDraftpDate(new Timestamp(new Date().getTime()));
		contract.setDraftpDate(new Date());

		if (contractId != null) {
			if (Integer.parseInt(contractId) != 0) {
				ProcreditContract pc =dao.getById(Integer.parseInt(contractId));
				contract.setUrl(pc.getUrl());
				contract.setFileCount(pc.getFileCount());
				contract.setIsLegalCheck(pc.getIsLegalCheck());
				contract.setLegalCheckDate(pc.getLegalCheckDate());
				contract.setLegalCheckpId(pc.getLegalCheckpId());
				contract.setIssign(pc.getIssign());
				contract.setSignDate(pc.getSignDate());
				contract.setIssignId(pc.getIssignId());
				contract.setId(Integer.parseInt(contractId));

				creditBaseDao.clearSession();
				creditBaseDao.updateDatas(contract);
				returnContractId = Integer.parseInt(contractId);
			} else {
				contract.setFileCount("0");
				creditBaseDao.saveDatas(contract);
				returnContractId = contract.getId();
			}
		} else {
			contract.setFileCount("0");
			creditBaseDao.saveDatas(contract);
			returnContractId = contract.getId();
		}

		return returnContractId;
	}
	public void updateContractByIdSql(int contractId) {
		try {
			if (contractId != 0) {
				// String hql =
				// "update cs_procredit_contract AS cpc set cpc.fileCount = cpc.fileCount+1 where cpc.id = '"+contractId+"'";
				ProcreditContract procreditContract = null;
				procreditContract =dao.getById(contractId);
				if (procreditContract != null) {
					if(null==procreditContract.getFileCount()){
						procreditContract.setFileCount("0");
					}
					int fileCount = (Integer.parseInt(procreditContract.getFileCount()) + 1);
					procreditContract.setFileCount(fileCount + "");
					dao.merge(procreditContract);
				}
			} else {
			}

		} catch (Exception e) {
			logger.error("更新出错:" + e.getMessage());
			e.printStackTrace();
		}
	}
	@Override
	public List<ProcreditContract> listByTemplateId(String projId,
			Integer temptId) {
		
		return dao.listByTemplateId(projId, temptId);
	}
	@Override
	public List<ProcreditContract> getProcreditContractsList(String projectId) {
		
		return dao.getProcreditContractsList(projectId);
	}
	@Override
	public Integer add(ProcreditContract procreditContract, String projId,
			String businessType) {
		try {

			procreditContract.setParentId(0);
			procreditContract.setIsOld(0);
			procreditContract.setProjId(projId);
			procreditContract.setBusinessType(businessType);
			dao.evict(procreditContract);
			dao.save(procreditContract);

		} catch (Exception e) {
			logger.error("增加合同出错:" + e.getMessage());
			e.printStackTrace();
		}
		return procreditContract.getId();
	}
	@Override
	public List<ProcreditContract>  getListByPIdAndBT(Long projectId, String businessType) {
		 return this.dao.getListByPIdAndBT(projectId,businessType);
		
	}
	@Override
	public Integer getContractCountByNOLk(String contractNo) {
		return this.dao.getContractCountByNOLk(contractNo);
	}
	@Override
	public List<ProcreditContract> listByBidPlanId(Long bidPlanId, String htType) {
		return dao.listByBidPlanId(bidPlanId, htType);
	}
	
	public   List<ProcreditContract>  findById(Integer  id){
		return dao.listById(id);
	}
	@Override
	public Long countByBidId(Long bidPlanId) {
		
		return dao.countByBidId(bidPlanId);
	}
	@Override
	public List<ProcreditContract> getByPlanId(Long bidPlanId) {
		
		return dao.getByPlanId(bidPlanId);
	}
	@Override
	public int newMakeUpload(Map<String, Object> map) {
		int returnContractId = 0;
		DocumentTemplet template = documentTempletDao.getById(Integer.parseInt(map.get("tempId").toString()));
		ProcreditContract contract =dao.getById(Integer.valueOf(map.get("contractId").toString()));
		Long projId= Long.parseLong(map.get("projId").toString());
		BpFundProject smallProject = null;
		GLGuaranteeloanProject gloanProject = null;
		FlFinancingProject floanProject = null;
		SlSuperviseRecord slSuperviseRecord = null;
		String businessType=map.get("businessType").toString();
		String projectNumber="";
		String contractId=map.get("contractId").toString();
		String thirdRalationId=map.get("thirdRalationId").toString();
		
		contract.setTemplateId(Integer.parseInt(map.get("tempId").toString()));
		contract.setIsDraftp(true);
		contract.setDraftpId(1);
		contract.setDraftpDate(new Date());
		if (null!=map.get("tempId") && !"".equals(map.get("tempId"))) {
			contract.setSearchRemark(map.get("tempId").toString());
		}
		if (null != thirdRalationId) {
			contract.setMortgageId(Integer.valueOf(thirdRalationId));
		}
		try{
			if ("SmallLoan".equals(businessType)) {
				smallProject = (BpFundProject) creditBaseDao.getById(BpFundProject.class,projId);
				projectNumber = smallProject.getProjectNumber();// 项目编号
				changeContractName(projectNumber,smallProject.getOppositeType(),Integer.parseInt(smallProject.getOppositeID().toString()),contract,template);
			} else if ("Guarantee".equals(businessType)){
				gloanProject = (GLGuaranteeloanProject) creditBaseDao.getById(GLGuaranteeloanProject.class,projId);
				projectNumber = gloanProject.getProjectNumber();// 项目编号
				changeContractName(projectNumber,gloanProject.getOppositeType(),Integer.parseInt(gloanProject.getOppositeID().toString()),contract,template);
			} else if ( "Financing".equals(businessType)) {
				floanProject = (FlFinancingProject) creditBaseDao.getById(FlFinancingProject.class,projId);
				projectNumber = floanProject.getProjectNumber();// 项目编号
				changeContractName(projectNumber,floanProject.getOppositeType(),Integer.parseInt(floanProject.getOppositeID().toString()),contract,template);
			} else if ("ExhibitionBusiness".equals(businessType)) {// 贷后 监管 展期，小贷 微贷
				slSuperviseRecord = (SlSuperviseRecord) creditBaseDao.getById(SlSuperviseRecord.class,projId);
				SlSmallloanProject smallProjecttemp = (SlSmallloanProject) creditBaseDao.getById(SlSmallloanProject.class, slSuperviseRecord.getProjectId());
				projectNumber = smallProjecttemp.getProjectNumber();// 项目编号
				changeContractName(projectNumber,smallProjecttemp.getOppositeType(),Integer.parseInt(smallProjecttemp.getOppositeID().toString()),contract,template);
			} else if ("Pawn".equals(businessType)) {
				PlPawnProject project = (PlPawnProject) creditBaseDao.getById(PlPawnProject.class,projId);
				projectNumber = project.getProjectNumber();
				changeContractName(projectNumber,project.getOppositeType(),Integer.parseInt(project.getOppositeID().toString()),contract,template);
			} else if ("LeaseFinance".equals(businessType)) {
				FlLeaseFinanceProject project = (FlLeaseFinanceProject) creditBaseDao.getById(FlLeaseFinanceProject.class,projId);
				projectNumber = project.getProjectNumber();
				changeContractName(projectNumber,project.getOppositeType(),Integer.parseInt(project.getOppositeID().toString()),contract,template);
			}
			if (null!=contractId) {
				if (Integer.parseInt(contractId) != 0) {
					ProcreditContract pc = (ProcreditContract) creditBaseDao.getById(ProcreditContract.class,Integer.parseInt(contractId));
					contract.setUrl(pc.getUrl());
					contract.setFileCount(pc.getFileCount());
					contract.setIsLegalCheck(pc.getIsLegalCheck());
					contract.setLegalCheckDate(pc.getLegalCheckDate());
					contract.setLegalCheckpId(pc.getLegalCheckpId());
					contract.setIssign(pc.getIssign());
					contract.setSignDate(pc.getSignDate());
					contract.setIssignId(pc.getIssignId());
					contract.setId(Integer.parseInt(contractId));
					creditBaseDao.clearSession();
					dao.merge(contract);
					returnContractId = Integer.parseInt(contractId);
				} else {
					contract.setFileCount("0");
					dao.save(contract);
					returnContractId = contract.getId();
				}
			} else {
				contract.setFileCount("0");
				dao.save(contract);
				returnContractId = contract.getId();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnContractId;
	}
	
	/**
	 * 公用代码提取,修改ContractName
	 * @param projectNumber项目编号
	 * @param oppositeType 客户类别
	 * @param oppositeID   客户id
	 * @param contract     合同对象
	 * @param template     模板对象
	 * @HTZCB
	 */
	public void changeContractName(String projectNumber,String oppositeType,Integer oppositeID,ProcreditContract contract,DocumentTemplet template){
		try{
			if (oppositeType.equals("company_customer")) {
				Enterprise enter = (Enterprise) creditBaseDao.getById(Enterprise.class,oppositeID);
				contract.setContractName(enter.getShortname() + "-"+ projectNumber + "-" + template.getText());
			} else if (oppositeType.equals("person_customer")) {
				Person person = (Person) creditBaseDao.getById(Person.class,oppositeID);
				contract.setContractName(person.getName() + "-" + projectNumber+ "-" + template.getText());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public List<ProcreditContract> findListByBidPlanId(Long bidPlanId,String htType) {
		return dao.findListByBidPlanId(bidPlanId,htType);
	}
	@Override
	public void newMakeUpload(Map<String, Object> map,ProcreditContract contract) {
		DocumentTemplet template = documentTempletDao.getById(Integer.parseInt(map.get("tempId").toString()));
		String businessType=map.get("businessType").toString();
		Long projId= Long.parseLong(map.get("projId").toString());
		BpFundProject smallProject = null;
		GLGuaranteeloanProject gloanProject = null;
		FlFinancingProject floanProject = null;
		SlSuperviseRecord slSuperviseRecord = null;
		String projectNumber="";
		
		contract.setTemplateId(Integer.parseInt(map.get("tempId").toString()));
		contract.setIsDraftp(true);
		contract.setDraftpId(1);
		contract.setDraftpDate(new Date());
//		contract.setProjId(map.get("projId").toString());
		if (null!=map.get("tempId") && !"".equals(map.get("tempId"))) {
			contract.setSearchRemark(map.get("tempId").toString());
		}
		if (null != map.get("thirdRalationId")) {
			contract.setMortgageId(Integer.valueOf(map.get("thirdRalationId").toString()));
		}
		try{
			if ("SmallLoan".equals(businessType)) {
				smallProject = (BpFundProject) creditBaseDao.getById(BpFundProject.class,projId);
				projectNumber = smallProject.getProjectNumber();// 项目编号
				changeContractName(projectNumber,smallProject.getOppositeType(),Integer.parseInt(smallProject.getOppositeID().toString()),contract,template);
			} else if ("Guarantee".equals(businessType)){
				gloanProject = (GLGuaranteeloanProject) creditBaseDao.getById(GLGuaranteeloanProject.class,projId);
				projectNumber = gloanProject.getProjectNumber();// 项目编号
				changeContractName(projectNumber,gloanProject.getOppositeType(),Integer.parseInt(gloanProject.getOppositeID().toString()),contract,template);
			} else if ( "Financing".equals(businessType)) {
				floanProject = (FlFinancingProject) creditBaseDao.getById(FlFinancingProject.class,projId);
				projectNumber = floanProject.getProjectNumber();// 项目编号
				changeContractName(projectNumber,floanProject.getOppositeType(),Integer.parseInt(floanProject.getOppositeID().toString()),contract,template);
			} else if ("ExhibitionBusiness".equals(businessType)) {// 贷后 监管 展期，小贷 微贷
				slSuperviseRecord = (SlSuperviseRecord) creditBaseDao.getById(SlSuperviseRecord.class,projId);
				SlSmallloanProject smallProjecttemp = (SlSmallloanProject) creditBaseDao.getById(SlSmallloanProject.class, slSuperviseRecord.getProjectId());
				projectNumber = smallProjecttemp.getProjectNumber();// 项目编号
				changeContractName(projectNumber,smallProjecttemp.getOppositeType(),Integer.parseInt(smallProjecttemp.getOppositeID().toString()),contract,template);
			} else if ("Pawn".equals(businessType)) {
				PlPawnProject project = (PlPawnProject) creditBaseDao.getById(PlPawnProject.class,projId);
				projectNumber = project.getProjectNumber();
				changeContractName(projectNumber,project.getOppositeType(),Integer.parseInt(project.getOppositeID().toString()),contract,template);
			} else if ("LeaseFinance".equals(businessType)) {
				FlLeaseFinanceProject project = (FlLeaseFinanceProject) creditBaseDao.getById(FlLeaseFinanceProject.class,projId);
				projectNumber = project.getProjectNumber();
				changeContractName(projectNumber,project.getOppositeType(),Integer.parseInt(project.getOppositeID().toString()),contract,template);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}