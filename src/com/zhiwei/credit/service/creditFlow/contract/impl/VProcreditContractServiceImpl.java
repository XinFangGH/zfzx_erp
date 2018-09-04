package com.zhiwei.credit.service.creditFlow.contract.impl;



import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.contract.ProcreditContractDao;
import com.zhiwei.credit.dao.creditFlow.contract.VProcreditContractDao;
import com.zhiwei.credit.dao.creditFlow.fileUploads.FileFormDao;
import com.zhiwei.credit.dao.system.FileAttachDao;
import com.zhiwei.credit.model.admin.ContractElement;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;

public class VProcreditContractServiceImpl extends
		BaseServiceImpl<VProcreditContract> implements
		VProcreditContractService {
    private VProcreditContractDao dao;
    @Resource
    private CreditBaseDao creditBaseDao;
    @Resource
    private FileFormDao fileFormDao;
    @Resource
    private FileAttachDao fileAttachDao;
    @Resource
    private ProcreditContractDao procreditContractDao;
	public VProcreditContractServiceImpl(VProcreditContractDao dao) {
		super(dao);
		this.dao=dao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<VProcreditContract> findList(String projectId,String businessType) {
		
		return dao.findList(projectId, businessType);
	}

	@Override
	public List<VProcreditContract> getList(String projectId,
			String businessType, String htType) {
		
		return dao.getList(projectId, businessType, htType);
	}

	@Override
	public List<VProcreditContract> getMortgageContract(
			Integer mortgageId) {
		
		return dao.getMortgageContract(mortgageId);
	}

	@Override
	public List<VProcreditContract> getContractList(
			String businessType, String projectName, String projectNum,
			String contractNum,String searchRemark, String companyId, PagingBean pb) {
		
		return dao.getContractList(businessType, projectName, projectNum, contractNum,searchRemark, companyId, pb);
	}

	@Override
	public List<ProcreditContract> getCategoryList(String projectId, String businessType, String htType,
			Long slSuperviseRecordId) {
		
		return dao.getCategoryList(projectId, businessType, htType, slSuperviseRecordId);
	}

	@Override
	public List<ProcreditContract> getContractList(Integer categoryId) {
		
		return dao.getContractList(categoryId);
	}

	@Override
	public List<VProcreditContract> getList(String projectId,
			String businessType, String htType, Integer categoryId) {
		return dao.getList(projectId, businessType, htType,categoryId);
	}

	@Override
	public List<VProcreditContract> listByHtType(String businessType,
			String projId, String htType) {
		
		return dao.listByHtType(businessType, projId, htType);
	}

	@Override
	public List<VProcreditContract> getThirdContractCategoryTree(
			String businessType, String projId, String htType, String mortgageId) {
		
		return dao.getThirdContractCategoryTree(businessType, projId, htType, mortgageId);
	}

	@Override
	public List<VProcreditContract> getListByHtType(
			String businessType, String projId, String htType) {
		
		return dao.getListByHtType(businessType, projId, htType);
	}

	@Override
	public List<VProcreditContract> getSlauseContractCategoryTree(
			String businessType, String projId, String htType, boolean isApply,
			boolean isUpdate, String clauseId) {
		
		return dao.getSlauseContractCategoryTree(businessType, projId, htType, isApply, isUpdate, clauseId);
	}
	@Override
	public void seeContract(String contractId) {
		VProcreditContract vprocreditContract= null;
		int cid = 0;
		if (null != contractId && !"".equals(contractId)) {
			cid = Integer.parseInt(contractId);
			try {
				vprocreditContract = (VProcreditContract) creditBaseDao.getById(VProcreditContract.class, cid);
				ProcreditMortgage pm = (ProcreditMortgage) creditBaseDao.getById(ProcreditMortgage.class,
								vprocreditContract.getMortgageId());
				if (vprocreditContract.getHtType().equals("baozContract")) {
					vprocreditContract.setMortgagename(pm.getMortgagename());
				} else if (vprocreditContract.getHtType().equals("thirdContract")) {
					vprocreditContract.setMortgageTypeValue(pm.getMortgagepersontypeforvalue());
				} else if (vprocreditContract.getHtType().equals("dwContract")) {
					PawnItemsList pawn = (PawnItemsList) creditBaseDao.getById(PawnItemsList.class, vprocreditContract.getDwId());
					vprocreditContract.setPawnItemName(pawn.getPawnItemName());
				}

			} catch (Exception e) {
				logger.error("查看合同出错:" + e.getMessage());
				e.printStackTrace();
				JsonUtil.jsonFromObject(null, false);
			}
		}
		JsonUtil.jsonFromObject(vprocreditContract, true);
	}
	public void deleteContractRecordByMortgageId(String url, String id,
			String businessType, String contractType, String projId) {

		// ProcreditContractCategory procreditContractCategory=null;

		try {
			if (id != null && !"".equals(id)) {
				List<VProcreditContract> listz = this.getThirdContractCategoryTree(businessType, projId, contractType, id);

				if (listz != null && listz.size() > 0) {
					for (int q = 0; q < listz.size(); q++) {
						VProcreditContract vprocreditContract= (VProcreditContract) listz
								.get(q);
						// procreditContractCategory=(ProcreditContractCategory)creditBaseDao.getById(ProcreditContractCategory.class,
						// Integer.parseInt(id));
						if (vprocreditContract.getId() != null) {
							if (vprocreditContract.getId()!= 0) {
								// 删除csfile表
								String mark = "cs_procredit_contract."
										+ vprocreditContract
												.getId();
								
								Collection<FileForm> clist = (Collection<FileForm>) fileFormDao.findByMark(mark);
								if (clist != null && clist.size() > 0) {
									List<FileForm> list = (List<FileForm>) clist;
									for (int i = 0; i < list.size(); i++) {
										FileForm f = list.get(i);
										// 删除附件文件
										File ff = new File(url
												+ f.getFilepath());
										try {
											boolean b = ff.isFile();
											boolean c = ff.exists();
											if (b == true && c == true) {
												ff.delete();
											}
										} catch (Exception e) {
										} finally {
											ff = null;
										}
										fileAttachDao.removeByFileId(f.getFileid());
										fileFormDao.remove(f);
									}
								}
								// 删除合同文件
								ProcreditContract pc = null;
								pc =procreditContractDao.getById(vprocreditContract.getId()) ;
								if (pc != null) {
									File fileio = new File(url + pc.getUrl());
									try {
										boolean b = fileio.isFile();
										boolean c = fileio.exists();
										if (b == true && c == true) {
											fileio.delete();
										}
									} catch (Exception e) {
									} finally {
										fileio = null;
										System.gc();
									}
								}
								// 删除合同表
								procreditContractDao.remove(pc); 
								
							}
						}
						// 删除合同分类表
						/*ProcreditContractCategory p=procreditContractCategoryDao.getById(vprocreditContractCategory.getId());
						if(null!=p){
							procreditContractCategoryDao.remove(p);
						}*/
						
						
						JsonUtil.jsonFromObjectSuccess(true);
					}
				}
			} else {
				JsonUtil.jsonFromObjectFailure(true);
			}

		} catch (Exception e) {
			logger.error("删除合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil.jsonFromObjectFailure(true);

		}
	}
	public void deleteContractRecord(String url, String id) {
		// ProcreditContractCategory procreditContractCategory=null;

		try {
			if (id != null) {
				// procreditContractCategory=(ProcreditContractCategory)creditBaseDao.getById(ProcreditContractCategory.class,
				// Integer.parseInt(id));
				VProcreditContract vprocreditContract =dao.getById(Integer.parseInt(id));
				if (vprocreditContract.getId() != null) {
					if (vprocreditContract.getId() != 0) {
						// 删除csfile表
						String mark = "cs_procredit_contract."+ vprocreditContract.getId();
						
						Collection<FileForm> clist = (Collection<FileForm>) fileFormDao.findByMark(mark);
						if (clist != null && clist.size() > 0) {
							List<FileForm> list = (List<FileForm>) clist;
							for (int i = 0; i < list.size(); i++) {
								FileForm f = list.get(i);
								// 删除附件文件
								File ff = new File(url + f.getFilepath());
								try {
									boolean b = ff.isFile();
									boolean c = ff.exists();
									if (b == true && c == true) {
										ff.delete();
									}
								} catch (Exception e) {
									logger.error("删除附件出错:" + e.getMessage());
								} finally {
									ff = null;
								}
								fileAttachDao.removeByFileId(f.getFileid());
								fileFormDao.remove(f);
							}
						}
						// 删除合同文件
						ProcreditContract pc = null;
						pc = procreditContractDao.getById(vprocreditContract.getId());
						
						if (pc != null) {
							File fileio = new File(url + pc.getUrl());
							try {
								boolean b = fileio.isFile();
								boolean c = fileio.exists();
								if (b == true && c == true) {
									fileio.delete();
								}
							} catch (Exception e) {
								logger.error("删除合同文件出错:" + e.getMessage());
							} finally {
								fileio = null;
								System.gc();
							}
						}
						// 删除合同表
						procreditContractDao.remove(pc);
						
						// 删除新增模板元素表
					
						List<ContractElement> list = this.getListByCategoryId(vprocreditContract.getId());
						if (null != list) {
							for (int i = 0; i < list.size(); i++) {
								creditBaseDao.deleteDatas(list.get(i));
							}
						}
					}
				}

				// 删除合同分类表
				/*ProcreditContractCategory p=procreditContractCategoryDao.getById(Integer.parseInt(id));
				if(null!=p){
					procreditContractCategoryDao.remove(p);
				}*/
				
				JsonUtil.jsonFromObjectSuccess(true);
			} else {
				JsonUtil.jsonFromObjectFailure(true);
			}

		} catch (Exception e) {
			logger.error("删除合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil.jsonFromObjectFailure(true);

		}
	}

	public void deleteContractRecordFor(String url, String projId,
			String businessType, String htType) {
		try {
			if (projId != null && !"".equals(projId)) {
				List<VProcreditContract> listz = null;
				
				listz = dao.getListByHtType(businessType, projId, htType);
				if (listz != null && listz.size() > 0) {
					for (int q = 0; q < listz.size(); q++) {
						VProcreditContract vprocreditContract = (VProcreditContract) listz.get(q);
						// procreditContractCategory=(ProcreditContractCategory)creditBaseDao.getById(ProcreditContractCategory.class,
						// Integer.parseInt(id));
						if (vprocreditContract.getId() != null) {
							if (vprocreditContract.getId() != 0) {
								// 删除csfile表
								String mark = "cs_procredit_contract."
										+ vprocreditContract
												.getId();
								Collection<FileForm> clist = (Collection<FileForm>) fileFormDao.findByMark(mark);
								if (clist != null && clist.size() > 0) {
									List<FileForm> list = (List<FileForm>) clist;
									for (int i = 0; i < list.size(); i++) {
										FileForm f = list.get(i);
										// 删除附件文件
										File ff = new File(url
												+ f.getFilepath());
										try {
											boolean b = ff.isFile();
											boolean c = ff.exists();
											if (b == true && c == true) {
												ff.delete();
											}
										} catch (Exception e) {
										} finally {
											ff = null;
										}
										fileAttachDao.removeByFileId(f.getFileid());
										fileFormDao.remove(f);
									}
								}
								// 删除合同文件
								ProcreditContract pc = null;
								pc = procreditContractDao.getById(vprocreditContract.getId());
								if (pc != null) {
									File fileio = new File(url + pc.getUrl());
									try {
										boolean b = fileio.isFile();
										boolean c = fileio.exists();
										if (b == true && c == true) {
											fileio.delete();
										}
									} catch (Exception e) {
									} finally {
										fileio = null;
										System.gc();
									}
									// 删除合同表
									procreditContractDao.remove(pc);
								}
								
						}
						}
						// 删除合同分类表
						/*ProcreditContractCategory p=procreditContractCategoryDao.getById(vprocreditContractCategory.getId());
						if(null!=p){
							procreditContractCategoryDao.remove(p);
						}*/
						JsonUtil.jsonFromObjectSuccess(true);
					}
				}
				
			} else {
				JsonUtil.jsonFromObjectFailure(true);
			}
			
		} catch (Exception e) {
			logger.error("删除合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil.jsonFromObjectFailure(true);

		}
	}

	@Override
	public List<ContractElement> getListByCategoryId(Integer categoryId) {
		
		return dao.getListByCategoryId(categoryId);
	}
	public void deleteContractByProject(String projId,
			String businessType, String htType, boolean isApply) {
		try {
			List<VProcreditContract> list_ =dao.getByIsApply(projId, businessType, htType, isApply);
			if (list_ != null && list_.size() > 0) {
				for (int j = 0; j < list_.size(); j++) {
					VProcreditContract vp_ = (VProcreditContract) list_
							.get(j);
					int id = vp_.getId();
					if (id != 0) {
						// procreditContractCategory=(ProcreditContractCategory)creditBaseDao.getById(ProcreditContractCategory.class,
						// Integer.parseInt(id));
						VProcreditContract vprocreditContract = (VProcreditContract) creditBaseDao
								.getById(VProcreditContract.class, id);
						if (vprocreditContract.getId() != null) {
							if (vprocreditContract.getId() != 0) {
								// 删除csfile表
								String mark = "cs_procredit_contract."
										+ vprocreditContract
												.getId();
								Collection<FileForm> clist = (Collection<FileForm>)fileFormDao.findByMark(mark);
								if (clist != null && clist.size() > 0) {
									List<FileForm> list = (List<FileForm>) clist;
									for (int i = 0; i < list.size(); i++) {
										FileForm f = list.get(i);
										fileAttachDao.removeByFileId(f.getFileid());
										fileFormDao.remove(f);
									}
								}
								// 删除合同表
								ProcreditContract pc=procreditContractDao.getById(vprocreditContract.getId());
								if(null!=pc){
									procreditContractDao.remove(pc);
								}
							}
						}

						// 删除合同分类表
						/*ProcreditContractCategory p=procreditContractCategoryDao.getById(id);
						if(null!=p){
							procreditContractCategoryDao.remove(p);
						}*/
					

					}
				}
			}
			JsonUtil.jsonFromObjectSuccess(true);

		} catch (Exception e) {
			logger.error("删除合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil.jsonFromObjectFailure(true);

		}
	}

	@Override
	public List<VProcreditContract> getByIsApply(String projId,
			String businessType, String htType, boolean isApply) {
		
		return dao.getByIsApply(projId, businessType, htType, isApply);
	}
	public void getAllThirdContractByProjectId(String projId,
			String businessType, String htType) {
		List<VProcreditContract> list = null;
		int totalProperty = 0;
		
		try {
			list = dao.getListByHtType(businessType, projId, htType);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					VProcreditContract vpcc = (VProcreditContract) list
							.get(i);
					if (businessType.equals("SmallLoan")) {
						Object object = creditBaseDao.getById(
								VProcreditDictionary.class, vpcc
										.getMortgageId());
						;
						if (object != null) {
							VProcreditDictionary vpd = (VProcreditDictionary) object;
							vpcc.setMortgagename(vpd.getMortgagename());
							vpcc.setMortgageTypeValue(vpd
									.getMortgagepersontypeforvalue());
						}
					} else if (businessType.equals("Guarantee")) {
						Object object = creditBaseDao.getById(
								VProcreditDictionaryGuarantee.class, vpcc
										.getMortgageId());
						if (object != null) {
							VProcreditDictionaryGuarantee vpd = (VProcreditDictionaryGuarantee) object;
							vpcc.setMortgagename(vpd.getMortgagename());
							vpcc.setMortgageTypeValue(vpd
									.getMortgagepersontypeforvalue());
						}

					} else if (businessType.equals("Financing")) {/*
						Object ot = creditBaseDao.getById(
								VProcreditMortgageFinance.class, vpcc
										.getMortgageId().longValue());
						if (ot != null) {
							VProcreditMortgageFinance vmf = (VProcreditMortgageFinance) ot;
							if (vmf != null) {
								Object object = creditBaseDao.getById(
										VProcreditDictionaryFinance.class, vmf
												.getMortgageId());
								if (object != null) {
									VProcreditDictionaryFinance vpd = (VProcreditDictionaryFinance) object;
									vpcc.setMortgagename(vpd.getMortgagename());
									vpcc.setMortgageTypeValue(vpd
											.getMortgagepersontypeforvalue());
								}
							}
						}

					*/}

				}

			}
			totalProperty = list.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获得全部签署的合同或全部编辑的合同出错:" + e.getMessage());
			e.printStackTrace();
		}
		JsonUtil.jsonFromList(list, totalProperty);
	}

	@Override
	public List<VProcreditContract> getVProcreditContractCategoryList(
			int mortgageId, String businessType, String contractType) {
		
		return dao.getVProcreditContractCategoryList(mortgageId, businessType, contractType);
	}

	@Override
	public VProcreditContract getById(Integer id) {
		
		return dao.getById(id);
	}
	public Boolean deleteAllContractByProjectIdAndBusinessType(String url,
			Long projectId, String businessType) {
		String projId = projectId.toString();
		try {
			
			List<VProcreditContract> list_ = dao.listByProjId(projId, businessType);
			if (list_ != null && list_.size() > 0) {
				for (int j = 0; j < list_.size(); j++) {
					VProcreditContract vprocreditContract = (VProcreditContract) list_
							.get(j);
					int id = vprocreditContract.getId();
					if (id != 0) {
						if (vprocreditContract.getId() != null) {
							if (vprocreditContract.getId() != 0) {
								// 删除csfile表
								String mark = "cs_procredit_contract."+ vprocreditContract.getId();
							
								Collection<FileForm> clist = (Collection<FileForm>) fileFormDao.findByMark(mark);
								if (clist != null && clist.size() > 0) {
									List<FileForm> list = (List<FileForm>) clist;
									for (int i = 0; i < list.size(); i++) {
										FileForm f = list.get(i);
										// 删除附件文件
										File ff = new File(url
												+ f.getFilepath());
										try {
											boolean b = ff.isFile();
											boolean c = ff.exists();
											if (b == true && c == true) {
												ff.delete();
											}
										} catch (Exception e) {
											logger.error("删除附件出错:"
													+ e.getMessage());

										} finally {
											ff = null;
										}
										fileAttachDao.removeByFileId(f.getFileid());
										fileFormDao.remove(f);
									}
									
								}
								// 删除合同文件
								ProcreditContract pc = null;
								pc = procreditContractDao.getById(vprocreditContract.getId());
								if (pc != null) {
									File fileio = new File(url + pc.getUrl());
									try {
										boolean b = fileio.isFile();
										boolean c = fileio.exists();
										if (b == true && c == true) {
											fileio.delete();
										}
									} catch (Exception e) {
										logger.error("删除合同文件出错:"
												+ e.getMessage());
									} finally {
										fileio = null;
										System.gc();
									}
								}
								// 删除合同附件
								
								List<FileAttach> listfile =fileAttachDao.listByContractId(vprocreditContract.getId());
								if (listfile != null && listfile.size() > 0) {
									for (int z = 0; z < listfile.size(); z++) {
										FileAttach fah = (FileAttach) listfile
												.get(z);
										File fileio = new File(url
												+ fah.getFilePath());
										try {
											boolean b = fileio.isFile();
											boolean c = fileio.exists();
											if (b == true && c == true) {
												fileio.delete();
											}
										} catch (Exception e) {
											logger.error("删除合同附件文件出错:"
													+ e.getMessage());
										} finally {
											fileio = null;
											System.gc();
										}
										// 删除FileAttach表
										fileAttachDao.remove(fah);
										
									}
								}

								// 删除合同表
								procreditContractDao.remove(pc);
								
							}
						}

						// 删除合同分类表
						/*ProcreditContractCategory p=procreditContractCategoryDao.getById(id);
						if(null!=p){
							procreditContractCategoryDao.remove(p);
						}*/
					}
				}
			}

		} catch (Exception e) {
			logger.error("删除项目时，删除合同出错:" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<VProcreditContract> listByProjId(String projectId,
			String businessType) {
		
		return dao.listByProjId(projectId, businessType);
	}
	public Boolean deleteAllReportByProjectIdAndBusinessType(String url,
			Long projectId, String businessType) {
		String projId = projectId.toString();
		Collection<FileForm> clist;
		try {
			clist = (Collection<FileForm>) fileFormDao.listByProjectId(projId, businessType);
			if (clist != null && clist.size() > 0) {
				List<FileForm> list = (List<FileForm>) clist;
				for (int i = 0; i < list.size(); i++) {
					FileForm f = list.get(i);
					// 删除附件文件
					File ff = new File(url + f.getFilepath());
					try {
						boolean b = ff.isFile();
						boolean c = ff.exists();
						if (b == true && c == true) {
							ff.delete();
						}
					} catch (Exception e) {
						logger.error("删除附件出错:" + e.getMessage());

					} finally {
						ff = null;
					}
					fileAttachDao.removeByFileId(f.getFileid());
					fileFormDao.remove(f);
				}
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("删除项目时，删除调查报告/风险综合分析报告出错:" + e1.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<VProcreditContract> listByTemplateId(String projId,
			Integer templateId) {
		
		return dao.listByTemplateId(projId, templateId);
	}

	@Override
	public List<VProcreditContract> listByHtType(String businessType,
			String projId, String htType, String type) {
		// TODO Auto-generated method stub
		return dao.listByHtType(businessType, projId, htType, type);
	}
}
