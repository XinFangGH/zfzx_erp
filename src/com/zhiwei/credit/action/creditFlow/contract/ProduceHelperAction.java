package com.zhiwei.credit.action.creditFlow.contract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.core.util.JacobWord;
import com.zhiwei.credit.core.util.MoneyFormat;
import com.zhiwei.credit.core.util.WordtoPdfUtil;
import com.zhiwei.credit.dao.creditFlow.contract.VProcreditContractDao;
import com.zhiwei.credit.model.admin.ContractElement;
import com.zhiwei.credit.model.creditFlow.contract.AssignmentElementCode;
import com.zhiwei.credit.model.creditFlow.contract.AssureIntentBookElementCode;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.ElementCode;
import com.zhiwei.credit.model.creditFlow.contract.FinancingElementCode;
import com.zhiwei.credit.model.creditFlow.contract.GuaranteeElementCode;
import com.zhiwei.credit.model.creditFlow.contract.LeaseFinanceElementCode;
import com.zhiwei.credit.model.creditFlow.contract.PawnElementCode;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.SmallLoanElementCode;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.creditmanagement.CreditmanagementElementCode;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.UploadLog;
import com.zhiwei.credit.service.creditFlow.contract.DocumentTempletService;
import com.zhiwei.credit.service.creditFlow.contract.ElementHandleService;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.PlBusinessDirProKeepService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.PlPersionDirProKeepService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.system.FileAttachService;
import com.zhiwei.credit.service.system.UploadLogService;

public class ProduceHelperAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(ProduceHelperAction.class);
	@Resource
	private ElementHandleService elementHandleService;
	@Resource
	private ProcreditContractService procreditContractService;
	@Resource
	private PersonService personService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private GLGuaranteeloanProjectService gLGuaranteeloanProjectService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private VProcreditContractDao vProcreditContractDao;
	@Resource
	private PlBusinessDirProKeepService plBusinessDirProKeepService;
	@Resource
	private PlPersionDirProKeepService plPersionDirProKeepService;
	@Resource
	private FTPIoadFileService fTPUploadFileimpl;
	@Resource
	private BpFundProjectService bpFundProjectService;
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	private String strFieldValue = "";
	private ElementCode elementCode;
	private AssureIntentBookElementCode assureIntentBookElementCode;
	private String projId;
	private int conId = 0;
	private String businessType;
	private String contractType;
	// 上传文件参数
	private File fileUpload;
	private String fileUploadFileName;
	private String fileUploadContentType;
	public final int UPLOAD_SIZE = 1024 * 1024 * 10;
	private String path;
	private String startTime = "";
	private String endTime = "";
	private String mark;
	private String sbClause = "";

	private String htType;// 合同类型，用来区分是否第三方保证合同
	private String htlxdName;
	private String htmcdName;
	private Long thirdRalationId;
	private int temptId;
	private boolean isApply;
	private String extendname;
	private Long leaseObjectInfoId;// 融资租赁合同 租赁标的物id project 与 租赁标的物 为一对多关系 add
									// by gao
	private Long dwId;// 当物合同 当物id project 与 租赁标的物 为一对多关系 add by gao

	public Long getLeaseObjectInfoId() {
		return leaseObjectInfoId;
	}

	public void setLeaseObjectInfoId(Long leaseObjectInfoId) {
		this.leaseObjectInfoId = leaseObjectInfoId;
	}

	private ProcreditContract procreditContract;
	@Resource
	private DocumentTempletService documentTempletService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	
	public boolean isApply() {
		return isApply;
	}

	public void setApply(boolean isApply) {
		this.isApply = isApply;
	}

	public Long getThirdRalationId() {
		return thirdRalationId;
	}

	public void setThirdRalationId(Long thirdRalationId) {
		this.thirdRalationId = thirdRalationId;
	}

	public String getHtType() {
		return htType;
	}

	public void setHtType(String htType) {
		this.htType = htType;
	}

	public String getHtlxdName() {
		return htlxdName;
	}

	public void setHtlxdName(String htlxdName) {
		this.htlxdName = htlxdName;
	}

	public String getHtmcdName() {
		return htmcdName;
	}

	public void setHtmcdName(String htmcdName) {
		this.htmcdName = htmcdName;
	}

	public ProcreditContract getProcreditContract() {
		return procreditContract;
	}

	public void setProcreditContract(ProcreditContract procreditContract) {
		this.procreditContract = procreditContract;
	}

	public ElementCode getElementCode() {
		return elementCode;
	}

	public void setElementCode(ElementCode elementCode) {
		this.elementCode = elementCode;
	}

	public int getTemptId() {
		return temptId;
	}

	public void setTemptId(int temptId) {
		this.temptId = temptId;
	}

	public int getConId() {
		return conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	private int documentId;

	public String getStrFieldValue() {
		return strFieldValue;
	}

	public void setStrFieldValue(String strFieldValue) {
		this.strFieldValue = strFieldValue;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public void produceDocument() {

	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSbClause() {
		return sbClause;
	}

	public void setSbClause(String sbClause) {
		this.sbClause = sbClause;
	}

	public Long getDwId() {
		return dwId;
	}

	public void setDwId(Long dwId) {
		this.dwId = dwId;
	}

	public String getExtendname() {
		return extendname;
	}

	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}

	/**
	 * <p>
	 * 选择模板后查看模板下所有的要素和要素所对应的值
	 * </p>
	 * 
	 * */
	public void findElement() {
		try {
			String contractId = getRequest().getParameter("conId"); // add by
			String bidPlanId = getRequest().getParameter("bidPlanId");
			DocumentTemplet templet = documentTempletService
					.getById(documentId);
			if (null == templet) {
				JsonUtil
						.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
				return;
			} else {
				if (!templet.isIsexist()) {
					JsonUtil
							.responseJsonString("{success:true,exsit:false,msg :'请先上传模板'}");
					return;
				} else {
					FileForm fileForm = fileFormService.getById(templet
							.getFileid());
					if (null != fileForm) {
						// 合同编号
						// String rnum = elementHandleService.getNumber(projId,
						// documentId+"");
						// rnum =Integer.parseInt(rnum)-1+"";
						String code[] = JacobWord.getInstance().readWordCode(
								super.getRequest().getRealPath("/")
										+ fileForm.getFilepath(),
								fileForm.getFilename());
						List list = new ArrayList();
						if (code.length > 1) {
							if (businessType.equals("SmallLoan")) {
								SmallLoanElementCode elementCode = null;
								if (projId == null) {
									elementCode = new SmallLoanElementCode();
								} else {
									// change by zcb
									// elementCode =
									// elementHandleService.getElementBySystem(projId,conId,documentId,contractType,rnum,null)
									// ;
									elementCode = elementHandleService
											.getElementBySystem(projId, Integer
													.parseInt(contractId),
													documentId, contractType,
													bidPlanId, null);
									
								}
								list = ElementUtil.findEleCodeValueArray(
										elementCode, code, contractId);
							} else if (businessType.equals("Financing")) {
								FinancingElementCode elementCode = null;
								if (projId == null) {
									elementCode = new FinancingElementCode();
								} else {
									// elementCode =
									// elementHandleService.getFinancingElementBySystem(projId,
									// conId, documentId, contractType, rnum);
									elementCode = elementHandleService
											.getFinancingElementBySystem(
													projId,
													Integer
															.parseInt(contractId),
													documentId, contractType,
													null);
								}
								list = ElementUtil.findEleCodeValueArray(
										elementCode, code, contractId);
							} else if (businessType.equals("Guarantee")) {
								GuaranteeElementCode elementCode = null;
								if (projId == null) {
									elementCode = new GuaranteeElementCode();
								} else {
									// elementCode =
									// elementHandleService.getGuaranteeElementBySystem(projId,
									// conId, documentId, contractType, rnum);
									elementCode = elementHandleService
											.getGuaranteeElementBySystem(
													projId,
													Integer
															.parseInt(contractId),
													documentId, contractType,
													null);
								}
								list = ElementUtil.findEleCodeValueArray(
										elementCode, code, contractId);
							} else if (businessType.equals("LeaseFinance")) {
								LeaseFinanceElementCode elementCode = null;
								if (projId == null) {
									elementCode = new LeaseFinanceElementCode();
								} else {
									// elementCode =
									// elementHandleService.getLeaseFinanceElementBySystem(projId,
									// conId, documentId, contractType,
									// rnum,leaseObjectInfoId);
									int cateId = (null != contractId) ? Integer
											.parseInt(contractId) : null;
									elementCode = elementHandleService
											.getLeaseFinanceElementBySystem(
													projId, cateId, documentId,
													contractType, null,
													leaseObjectInfoId);
								}
								list = ElementUtil.findEleCodeValueArray(
										elementCode, code, contractId);
							} else if (businessType.equals("Pawn")) {
								PawnElementCode elementCode = null;
								if (projId == null) {
									elementCode = new PawnElementCode();
								} else {
									int cateId = 0;
									if (null != contractId) {
										cateId = Integer.parseInt(contractId);
									}
									elementCode = elementHandleService
											.getPawnElementBySystem(projId,
													cateId, documentId,
													contractType, null, dwId);
								}
								list = ElementUtil.findEleCodeValueArray(
										elementCode, code, contractId);
							}

							int total = 0;
							if (list != null) {
								total = list.size();
							}
							JsonUtil.jsonFromList(list, total);
						}
					}
				}
			}
		} catch (Exception e) {
			JsonUtil.responseJsonString("{success:false}");
			logger.error("选择模板后查看模板下所有的要素和要素所对应的值出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 新的增加合同保存方法
	 */
	public void makeContractMain() {
		DocumentTemplet templet = null;
		HttpServletRequest requrst = getRequest();
		String projId = requrst.getParameter("projId");
		String bidPlanId=requrst.getParameter("bidPlanId");
		int tempId = Integer.parseInt(requrst.getParameter("templateId"));
		String ttempId = requrst.getParameter("templateId");
		String contractId = requrst.getParameter("contractId");
		String businessType = requrst.getParameter("businessType");
		String clauseId = requrst.getParameter("clauseId");
		String remark = requrst.getParameter("searchRemark"); // 该字段用来存放合同编号
		String leaseObjectId = requrst.getParameter("leaseObjectInfo"); // 该字段用来存放租赁物标的Id
		String dwId = requrst.getParameter("dwId"); // 该字段用来存放租赁物标的Id
		
		try {
			// 查询对应的模板文件是否上传
			templet = documentTempletService.getById(tempId);
			if (ttempId != null && templet != null) {
				DocumentTemplet dtemplet = documentTempletService.getById(tempId);
				if(null==dtemplet){
					JsonUtil.responseJsonFailure();
					return;
				}
				/*if (dtemplet != null) {
					if (dtemplet.getIsexist() == true && dtemplet.getFileid() != 0) {
						FileForm fileF = fileFormService.getById(dtemplet.getFileid());
						if (fileF != null) {
							File file = new File(serverPath+ fileF.getFilepath());
							if (!file.exists()) {
								JsonUtil.responseJsonFailure();
								return;
							}
						} else {
							JsonUtil.responseJsonFailure();
							return;
						}
					} else {
						JsonUtil.responseJsonFailure();
						return;
					}
				} else {
					JsonUtil.responseJsonFailure();
					return;
				}*/
			} else {
				JsonUtil.responseJsonFailure();
				return;
			}
			
			// 向ProcreditContractCategory表中增加或修改数据
			String bType = businessType;
			ProcreditContract p = null;
			if (null == contractId || "".equals(contractId)) {
				p = new ProcreditContract();
				p.setContractCategoryTypeText(htlxdName);
				p.setContractCategoryText(htmcdName);
			} else {
				p = procreditContractService.getById(Integer.valueOf(contractId));
				String contractCategoryTypeText = requrst.getParameter("contractCategoryTypeText");
				String contractCategoryText = requrst.getParameter("contractCategoryText");
				p.setContractCategoryTypeText(contractCategoryTypeText);
				p.setContractCategoryText(contractCategoryText);
			}
			if(null!=bidPlanId && !bidPlanId.equals("")){
				p.setBidPlanId(Long.valueOf(bidPlanId));
			}
			p.setHtType(htType);
			if (!"".equals(clauseId) && clauseId != null) {
				p.setClauseId(Long.parseLong(clauseId));
			}
			p.setTemptId(tempId);
			p.setTemplateId(tempId);
			if ("thirdContract".equals(htType) || "thirdRalationContract".equals(htType) ||
				"ourThirdContract".equals(htType) || "baozContract".equals(htType)) {
				p.setMortgageId(thirdRalationId.intValue());// 反担保ID
			} else {
				p.setMortgageId(0);
				thirdRalationId = Long.parseLong("0");
			}
			if ("leaseFinanceContract".equals(htType)) {
				p.setLeaseObjectId(Long.valueOf(leaseObjectId));
				thirdRalationId = Long.parseLong("0");
			}
			if ("dwContract".equals(htType)) {
				p.setDwId(Long.valueOf(dwId));
				thirdRalationId = Long.parseLong("0");
			}
			p.setIsApply(isApply);// 展期合同有用
			p.setContractNumber(remark);
			// 如果合同是增加操作则执行以下
			if (null == contractId || "".equals(contractId)) {
				contractId = procreditContractService.add(p, projId, bType)+ "";
			} else {// 否则为修改操作
				if ("".equals(htlxdName) && !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
					Object[] obj = { htmcdName, tempId,Integer.parseInt(contractId) };
					elementHandleService.updateCon("update ProcreditContract set contractCategoryText=?, temptId=? where id =?",obj);
				} else if (!"".equals(htlxdName) && "".equals(htmcdName)) {// 正规操作情况下没有这种可能
					Object[] obj = { htlxdName, Integer.parseInt(contractId) };
					elementHandleService.updateCon("update ProcreditContract set contractCategoryTypeText=? where id =?",obj);
				} else if (!"".equals(htlxdName) && !"".equals(htmcdName)) {
					Object[] obj = { htlxdName, htmcdName, tempId,Integer.parseInt(contractId) };
					elementHandleService.updateCon("update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",obj);
				}
			}

			// 向实体添加数据，返回给客户端
			p.setId(Integer.parseInt(contractId));
			if(p.getFileCount()==null || "".equals(p.getFileCount())){
				p.setFileCount("0");
			}
			procreditContractService.merge(p);
			JsonUtil.jsonFromObject(p, true);
		} catch (Exception e) {
			JsonUtil.responseJsonFailure();
			logger.error("添加合同报错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 以word格式下载模板文件
	 * </p>
	 */
	public void seeTemplateOfHTML() {
		String path = "", fileName;
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		int documentId = Integer.parseInt(request.getParameter("tempId"));
		try {
			DocumentTemplet templet = documentTempletService
					.getById(documentId);
			if (null == templet) {
				// JsonUtil.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
				return;
			} else {
				FileForm fileForm = fileFormService
						.getById(templet.getFileid());
				if (null != fileForm) {
					path = super.getRequest().getRealPath("/")
							+ fileForm.getFilepath();
					fileName = fileForm.getFilename().trim();
					File file = new File(path);
					if (file.exists()) {
						String newPath = path.substring(0, path
								.lastIndexOf("\\") + 1)
								+ fileName;
						// FileHelper.copyFile(path, newPath);//删除bychencc
						// 因为下载文件为空
						ElementUtil.downloadFile(newPath, response);
						// FileHelper.deleteFile(newPath);//删除bychencc 因为下载文件为空
					} else
						JsonUtil
								.responseJsonString("{success:true,exsit:false,msg :'加载错误，可能未上传模板'}");
				} else {
					// JsonUtil.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
					return;
				}
			}
		} catch (Exception e) {
			logger.error("以word格式下载模板文件出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 打开文件,以html文件打开
	 * </p>
	 */
	public void seeDocumentOfHTML() {
		String path = "";
		HttpServletRequest request = getRequest();
		String mark = request.getParameter("mark");
		try {
			FileForm fileForm = fileFormService.getFileByMark(mark);
			if (null != fileForm) {
				if (".docx".equals(fileForm.getExtendname())
						|| ".doc".equals(fileForm.getExtendname())) {
					path = ElementUtil.openFileByHTML(fileForm.getFilepath());
					String rootPath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getContextPath();
					String f[] = path.split(request.getContextPath().substring(
							1));
					path = rootPath + f[1].replace("\\", "/");
					List list = new ArrayList();
					list.add(path);
					list.add(fileForm.getFilename());
					JsonUtil.jsonFromList(list);
				} else {
					HttpServletResponse response = getResponse();
					String filename = fileForm.getFilename();
					File file = new File(fileForm.getFilepath());
					response.setContentType("application/x-msdownload");
					response.setContentLength((int) file.length());
					response.setHeader("Content-Disposition",
							"attachment;filename="
									+ new String(filename.getBytes("gbk"),
											"iso-8859-1"));
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream buff = new BufferedInputStream(fis);
					byte[] b = new byte[1024];
					long k = 0;
					OutputStream myout = response.getOutputStream();
					while (k < file.length()) {
						int j = buff.read(b, 0, 1024);
						k += j;
						myout.write(b, 0, j);
					}
					myout.flush();
				}
			} else {
				JsonUtil
						.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
				return;
			}
		} catch (Exception e) {
			logger.error("打开文件,以html文件打开出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 查看模板所包含的要素
	 * </p>
	 */
	public void findElementTwo() {
		try {
			DocumentTemplet templet = documentTempletService
					.getById(documentId);
			if (null == templet) {
				JsonUtil
						.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
				return;
			} else {
				if (!templet.isIsexist()) {
					JsonUtil
							.responseJsonString("{success:true,exsit:false,msg :'请先上传模板'}");
					return;
				} else {
					FileForm fileForm = fileFormService.getById(templet
							.getFileid());
					templet = documentTempletService.getById(templet
							.getParentid());
					if (null != fileForm) {
						File file = new File(super.getRequest()
								.getRealPath("/")
								+ fileForm.getFilepath());
						if (file.exists()) {
							String code[] = JacobWord.getInstance()
									.readWordCode(
											super.getRequest().getRealPath("/")
													+ fileForm.getFilepath(),
											fileForm.getFilename());
							List list = new ArrayList();
							if (code.length > 1) {
								/*
								 * if(templet.getTemplettype() == 2 &&
								 * "carAssureLetter"
								 * .equals(templet.getOnlymark())){ //
								 * GuaranteeLetterElement guaranteeLetterElement
								 * = new GuaranteeLetterElement(); // list =
								 * ElementUtil
								 * .findEleCodeValueArray(guaranteeLetterElement
								 * ,code); }else{ ElementCode elementCode = new
								 * ElementCode() ; list =
								 * ElementUtil.findEleCodeValueArray
								 * (elementCode,code); }
								 */
								if (businessType.equals("SmallLoan")) {
									SmallLoanElementCode elementCode = new SmallLoanElementCode();
									list = ElementUtil.findEleCodeValueArray(
											elementCode, code);
								} else if (businessType.equals("Financing")) {
									FinancingElementCode elementCode = new FinancingElementCode();
									list = ElementUtil.findEleCodeValueArray(
											elementCode, code);
								} else if (businessType.equals("Guarantee")) {
									GuaranteeElementCode elementCode = new GuaranteeElementCode();
									list = ElementUtil.findEleCodeValueArray(
											elementCode, code);
								} else if (businessType.equals("LeaseFinance")) {
									LeaseFinanceElementCode elementCode = new LeaseFinanceElementCode();
									list = ElementUtil.findEleCodeValueArray(
											elementCode, code);
								} else if (businessType.equals("Pawn")) {
									PawnElementCode elementCode = new PawnElementCode();
									list = ElementUtil.findEleCodeValueArray(
											elementCode, code);
								}
								int total = 0;
								if (list != null) {
									total = list.size();
								}
								JsonUtil.jsonFromList(list, total);
							}
						} else
							JsonUtil
									.responseJsonString("{success:true,exsit:false,msg :'请先上传模板'}");
					} else {
						JsonUtil
								.responseJsonString("{success:true,exsit:false,msg :'请先上传模板'}");
						return;
					}
				}
			}
		} catch (Exception e) {
			JsonUtil.responseJsonString("{success:false}");
			logger.error("查看模板所包含的要素:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/** 检查担保意向书是否存在 */
	public void checkIsAssureIntentBookTemplateExist() {
		String jsonString = "{success:false,exsit:false,msg :'对应的模板文件未找到,请先上传模板'}";
		try {
			String mark = getRequest().getParameter("mark");
			DocumentTemplet templet = documentTempletService
					.findDocumentTemplet(mark);

			templet = documentTempletService.getTempletObj(templet.getId());
			if (null == templet) {
				return;
			} else {
				if (!templet.isIsexist()) {
					return;
				} else {
					FileForm fileForm = fileFormService.getById(templet
							.getFileid());
					if (null != fileForm) {
						String path = ElementUtil.replaceAllEleme(
								assureIntentBookElementCode, super.getRequest()
										.getRealPath("/")
										+ fileForm.getFilepath(), fileForm
										.getFilename());
						if ("false".equals(path)) {
							return;
						} else {
							jsonString = "{success:true}";
						}
					} else {
						return;
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查担保意向书是否存在:" + e.getMessage());
			e.printStackTrace();
		} finally {
			JsonUtil.responseJsonString(jsonString);
		}
	}

	public void createAndDowloadDocument() {

		try {
			String mark_ = getRequest().getParameter("mark");
			String businessType_ = getRequest().getParameter("businessType");
			mark_ = new String(mark_.getBytes("iso-8859-1"), "utf-8");
			DocumentTemplet templet_ = documentTempletService
					.findDocumentTemplet(mark_);
			String htlxdName = templet_.getText();
			templet_ = documentTempletService.getTempletObj(templet_.getId());

			String htmcdName = "";
			if ("LoanSettlement".equals(htType)) {
				htmcdName = "贷款结清文档";
			} else {
				htmcdName = "未知";
			}

			if (null == templet_) {
				JsonUtil
						.responseJsonString("{success:false,exsit:false,msg :'请先上传模板'}");
				return;
			} else {
				if (!templet_.isIsexist()) {
					JsonUtil
							.responseJsonString("{success:false,exsit:false,msg :'请先上传模板'}");
					return;
				} else {
					FileForm fileForm_ = fileFormService.getById(templet_
							.getFileid());
					if (null != fileForm_) {
						String path_ = ElementUtil.replaceAllEleme(
								assureIntentBookElementCode, super.getRequest()
										.getRealPath("/")
										+ fileForm_.getFilepath(), fileForm_
										.getFilename());
						if ("false".equals(path_)) {
							JsonUtil
									.responseJsonString("{success:false,exsit:false,msg :'对应的模板文件未找到,请先上传模板'}");
							return;
						}

						String serverPath = super.getRequest().getRealPath("/");//
						DocumentTemplet templet = null;
						ProcreditContract contract = null;
						String path = "", fileName = "", shortName = "";
						int bytesum = 0, byteread = 0;
						HttpServletResponse response = getResponse();
						HttpServletRequest requrst = getRequest();
						String projId = requrst.getParameter("projId");
						int tempId = templet_.getId();
						String ttempId = templet_.getId() + "";
						String htid = requrst.getParameter("contractId");
						String cconId = (htid != "" ? htid : "0");
						int conId = Integer.parseInt(cconId);
						String contractId = requrst.getParameter("contractId");
						String cType = templet_.getTemplettype() + "";
						String businessType = businessType_;
						String projectNumber = "";

						try {
							// 查询对应的模板文件是否上传
							boolean isFileExists = false;
							templet = documentTempletService.getById(tempId);
							if (ttempId != null && templet != null) {
								DocumentTemplet dtemplet = documentTempletService
										.getById(tempId);
								if (dtemplet != null) {
									if (dtemplet.getIsexist() == true
											&& dtemplet.getFileid() != 0) {
										FileForm fileF = fileFormService
												.getById(dtemplet.getFileid());
										if (fileF != null) {
											File file = new File(serverPath
													+ fileF.getFilepath());
											if (!file.exists()) {
												JsonUtil.responseJsonFailure();
												return;
											} else {
												isFileExists = true;
											}
										} else {
											JsonUtil.responseJsonFailure();
											return;
										}
									} else {
										JsonUtil.responseJsonFailure();
										return;
									}
								} else {
									JsonUtil.responseJsonFailure();
									return;
								}
							} else {
								JsonUtil.responseJsonFailure();
								return;
							}
							// 如果模板文件已经上传
							if (isFileExists == true) {
								// 向ProcreditContractCategory表中增加或修改数据
								// Long bType = Long.parseLong(businessType+"");
								String bType = businessType;
								ProcreditContract p = null;
								if (null == contractId || contractId.equals("")
										|| contractId == "0"
										|| "0".equals(contractId)) {
									p = new ProcreditContract();
								} else {
									p = procreditContractService
											.getById(Integer
													.valueOf(contractId));
								}

								p.setContractCategoryTypeText(htlxdName);
								// p.setContractCategoryText(htmcdName);
								p.setHtType(htType);
								p.setTemptId(tempId);
								if (htType == "thirdContract"
										|| "thirdContract".equals(htType)
										|| htType == "thirdRalationContract"
										|| "thirdRalationContract"
												.equals(htType)
										|| "ourThirdContract".equals(htType)
										|| htType == "ourThirdContract") {
									p.setMortgageId(thirdRalationId.intValue());// 反担保ID
								} else {
									p.setMortgageId(0);
									thirdRalationId = Long.parseLong("0");
								}
								p.setIsApply(isApply);// 展期合同有用
								// 如果合同是增加操作则执行以下
								if (null == contractId || contractId.equals("")
										|| contractId == "0"
										|| "0".equals(contractId)) {
									contractId = procreditContractService.add(
											p, projId, bType)
											+ "";
								} else {// 否则为修改操作
									if ("".equals(htlxdName)
											&& !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
										Object[] obj = { htmcdName, tempId,
												Integer.parseInt(contractId) };
										elementHandleService
												.updateCon(
														"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
														obj);
									} else if (!"".equals(htlxdName)
											&& "".equals(htmcdName)) {// 正规操作情况下没有这种可能
										Object[] obj = { htlxdName,
												Integer.parseInt(contractId) };
										elementHandleService
												.updateCon(
														"update ProcreditContract set contractCategoryTypeText=? where id =?",
														obj);
									} else if (!"".equals(htlxdName)
											&& !"".equals(htmcdName)) {
										Object[] obj = { htlxdName, htmcdName,
												tempId,
												Integer.parseInt(contractId) };
										elementHandleService
												.updateCon(
														"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
														obj);
									}

								}
								
								// 合同编号
								// String rnum =
								// elementHandleService.getNumber(projId,
								// ttempId);
								// 向合同表中增加或修改数据数据（由相应的合同id是否存在）
								String searchRemark = requrst
										.getParameter("searchRemark");
								conId = procreditContractService.makeUpload(
										cconId, ttempId, cType, contractId,
										projId, thirdRalationId, businessType,
										null, searchRemark);// 向合同表中添加数据
								// templet =
								// elementHandleService.seeElement(tempId);
								// try {
								// tem =
								// elementHandleService.seeElement(templet.getParentid());
								// smallloanProject =
								// elementHandleService.findByEnterProjectId(projId);
								// enterprise =
								// (Enterprise)elementHandleService.findByEnterpriseId(Integer.parseInt(smallloanProject.getOppositeID().toString()));
								// } catch (Exception e1) {
								// e1.printStackTrace();
								// }

								if (templet == null) {
									JsonUtil
											.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
									return;
								} else {
									SlSmallloanProject sloanProject = null;
									GLGuaranteeloanProject gloanProject = null;
									FlFinancingProject floanProject = null;
									// tem =
									// elementHandleService.seeElement(templet.getParentid());
									ProcreditContract pcontract = procreditContractService
											.getById(conId);
									// 获得企业简称或个人的名字，由项目的客户类型决定
									if (businessType == "SmallLoan"
											|| "SmallLoan".equals(businessType)) {// 小贷流程
										sloanProject = slSmallloanProjectService
												.get(Long.valueOf(pcontract
														.getProjId()));
										projectNumber = sloanProject
												.getProjectNumber();
										if ((sloanProject.getOppositeType())
												.equals("company_customer")) {// 客户类型为企业
											Enterprise enterp = enterpriseService
													.getById(Integer
															.parseInt(sloanProject
																	.getOppositeID()
																	.toString()));
											shortName = enterp.getShortname()
													.trim();
										} else if ((sloanProject
												.getOppositeType())
												.equals("person_customer")) {// 客户类型为个人
											Person person = personService
													.getById(Integer
															.parseInt(sloanProject
																	.getOppositeID()
																	.toString()));
											shortName = person.getName().trim();
										}
									} else if (businessType == "Guarantee"
											|| "Guarantee".equals(businessType)) {
										gloanProject = gLGuaranteeloanProjectService
												.get(Long.valueOf(pcontract
														.getProjId()));
										projectNumber = gloanProject
												.getProjectNumber();
										if ((gloanProject.getOppositeType())
												.equals("company_customer")) {// 客户类型为企业
											Enterprise enterp = enterpriseService
													.getById(Integer
															.parseInt(gloanProject
																	.getOppositeID()
																	.toString()));
											shortName = enterp.getShortname()
													.trim();
										} else if ((gloanProject
												.getOppositeType())
												.equals("person_customer")) {// 客户类型为个人
											Person person = personService
													.getById(Integer
															.parseInt(gloanProject
																	.getOppositeID()
																	.toString()));
											shortName = person.getName().trim();
										}
									} else if (businessType == "Financing"
											|| "Financing".equals(businessType)) {// 融资流程
										floanProject = flFinancingProjectService
												.get(Long.valueOf(pcontract
														.getProjId()));
										projectNumber = floanProject
												.getProjectNumber();
										if ((floanProject.getOppositeType())
												.equals("company_customer")) {
											Enterprise enterp = enterpriseService
													.getById(floanProject
															.getOppositeID()
															.intValue());
											shortName = enterp.getShortname()
													.trim();
										} else if ((floanProject
												.getOppositeType())
												.equals("person_customer")) {
											Person person = personService
													.getById(floanProject
															.getOppositeID()
															.intValue());
											shortName = person.getName().trim();
										}

									}
									// 生成合同和合同文件的名字
									if (conId != 0) {
										fileName = pcontract.getContractName();
										if (null == fileName) {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText()/*
																		 * +"-"+rnum
																		 */;
										} else {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText()/*
																		 * +"-"+rnum
																		 */;
										}
									} else {
										fileName = shortName + "-"
												+ projectNumber + "-"
												+ templet.getText()/* +"-"+rnum */;
									}
									FileForm fileForm = fileFormService
											.getById(templet.getFileid());
									p.setContractCategoryText(fileForm
											.getFilename());
									// path =
									// ElementUtil.getUrl(fileForm.getFilepath(),fileName+".doc");
									path = ElementUtil.getUrl(serverPath
											+ fileForm.getFilepath(), fileName
											+ ".doc");

									/*
									 * String fileCraName = "";
									 * if(businessType==
									 * "SmallLoan"||"SmallLoan".
									 * equals(businessType)){ fileCraName =
									 * shortName
									 * +"_"+sloanProject.getProjectNumber();
									 * }else
									 * if(businessType=="Guarantee"||"Guarantee"
									 * .equals(businessType)){ fileCraName =
									 * shortName
									 * +"_"+gloanProject.getProjectNumber();
									 * }else if(businessType == "Financing" ||
									 * "Financing".equals(businessType)){
									 * fileCraName =
									 * shortName+"_"+floanProject.getProjectName
									 * (); }
									 */
									String fileCraName = shortName + "_"
											+ projectNumber;
									// path =
									// "/projFile/contfolder/"+fileCraName;
									// String copyDir =
									// super.getRequest().getRealPath("/")+"attachFiles\\projFile\\contfolder\\"+fileCraName+"\\"+templet.getParentid()+"\\";
									String copyDir = serverPath
											+ "attachFiles\\projFile\\contfolder\\"
											+ fileCraName + "\\"
											+ templet.getParentid() + "\\";
									File cFile = new File(copyDir);
									if (!cFile.exists()) {
										// FileHelper.newFolder(copyDir);
										cFile.mkdirs();
									}

									String newPath = (copyDir + fileName + ".doc")
											.trim();
									String filePath = ("projFile/contfolder/"
											+ fileCraName + "/"
											+ templet.getParentid() + "/"
											+ fileName + ".doc").trim();
									ProcreditContract pc = procreditContractService
											.getById(conId);
									pc.setIsUpload(true);
									pc.setUrl("attachFiles/" + filePath);
									procreditContractService.merge(pc);

									List<FileAttach> listfileAttach = fileAttachService
											.listByContractId(conId);
									if (listfileAttach != null) {
										for (FileAttach f : listfileAttach) {
											f.setFilePath(filePath);
											fileAttachService.merge(f);
										}
									} else {
										// 智维附件表操作start...为的是可以在线编辑
										FileAttach fileAttach = new FileAttach();
										fileAttach.setFileName(fileName);
										fileAttach.setFilePath(filePath);
										fileAttach.setCreatetime(new Date());
										fileAttach.setExt("doc");
										fileAttach
												.setFileType("attachFiles/uploads");
										// fileAttach.setNote(filesize.toString());
										fileAttach.setCreatorId(Long
												.parseLong(ContextUtil
														.getCurrentUser()
														.getId()));
										fileAttach
												.setCreator(ContextUtil
														.getCurrentUser()
														.getFullname());
										// fileAttach.setTotalBytes(Long.parseLong(filesize.toString())*1024);
										fileAttach.setDelFlag(0);
										fileAttach.setCsContractId(conId);
										fileAttachService.save(fileAttach);
										// 智维附件表操作end...
									}
									// 写模板
									if (businessType == "SmallLoan"
											|| "SmallLoan".equals(businessType)) {// 小贷
										// ElementCode elementCode = new
										// ElementCode();
										SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
										// ElementCode elementCodeValue =
										// elementHandleService.getElementBySystem(JbpmUtil.piKeyToProjectId(projId),conId,tempId,cType)
										// ;
										// ElementCode elementCodeValue =
										// elementHandleService.getElementBySystem(projId,conId,tempId,cType)
										// ;
										SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
												.getElementBySystem(projId,
														conId, tempId, htType,
														null, null);
										File file = new File(path);
										if (file.exists()) {
											JacobWord
													.getInstance()
													.replaceAllText(
															path,
															ElementUtil
																	.findEleCodeArray(smallLoanElementCode),
															ElementUtil
																	.findEleCodeValueArray(smallLoanElementCodeValue));
											FileHelper.copyFile(path, newPath);
											// ElementUtil.downloadFile(path,
											// response);
											// ElementUtil.downloadFile(newPath,
											// response);
											FileHelper.deleteFile(path);

										} else
											return;
									} else if (businessType == "Guarantee"
											|| "Guarantee".equals(businessType)) {
										GuaranteeElementCode guaranteeElementCode = new GuaranteeElementCode();
										GuaranteeElementCode guaranteeElementCodeValue = elementHandleService
												.getGuaranteeElementBySystem(
														projId, conId, tempId,
														htType, null);
										File file = new File(path);
										if (file.exists()) {
											JacobWord
													.getInstance()
													.replaceAllText(
															path,
															ElementUtil
																	.findEleCodeArray(guaranteeElementCode),
															ElementUtil
																	.findEleCodeValueArray(guaranteeElementCodeValue));
											FileHelper.copyFile(path, newPath);
											FileHelper.deleteFile(path);

										} else
											return;
									} else if (businessType == "Financing"
											|| "Financing".equals(businessType)) {// 融资
										FinancingElementCode financingElementCode = new FinancingElementCode();
										FinancingElementCode financingElementCodeValue = elementHandleService
												.getFinancingElementBySystem(
														projId, conId, tempId,
														htType, null);
										File file = new File(path);
										if (file.exists()) {
											JacobWord
													.getInstance()
													.replaceAllText(
															path,
															ElementUtil
																	.findEleCodeArray(financingElementCode),
															ElementUtil
																	.findEleCodeValueArray(financingElementCodeValue));
											FileHelper.copyFile(path, newPath);
											FileHelper.deleteFile(path);

										} else
											return;
									}

								}
								// 向实体添加数据，返回给客户端
								p.setId(Integer.parseInt(contractId));
								JsonUtil.jsonFromObject(p, true);
							} else {
								JsonUtil.responseJsonFailure();
							}
						} catch (Exception e) {
							JsonUtil.responseJsonFailure();
							logger.error("生成担保意向书/担保承诺函/股东会决议，利用模板和要素的值报错:"
									+ e.getMessage());
							e.printStackTrace();
						}

					} else {
						JsonUtil
								.responseJsonString("{success:false,exsit:false,msg :'对应的模板文件未找到,生成模板前,请先上传模板'}");
						return;
					}
				}
			}
		} catch (Exception e) {
			logger.error("生成担保意向书/担保承诺函/股东会决议，利用模板和要素的值报错:" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * <p>
	 * 生成担保意向书，利用模板和要素的值
	 * </p>
	 */
	public void createAssureIntentBook() {
		try {
			String mark_ = getRequest().getParameter("mark");
			String businessType_ = getRequest().getParameter("businessType");
			mark_ = new String(mark_.getBytes("iso-8859-1"), "utf-8");
			DocumentTemplet templet_ = documentTempletService
					.findDocumentTemplet(mark_);
			String htlxdName = templet_.getText();
			templet_ = documentTempletService.getTempletObj(templet_.getId());

			String htmcdName = "";
			if ("simulateEnterpriseBook".equals(htType)) {
				htmcdName = "拟担保意向书";
			} else if ("assureCommitmentLetter".equals(htType)) {
				htmcdName = "对外担保承诺函";
			} else if ("partnerMeetingResolution".equals(htType)) {
				htmcdName = "股东会决议";
			} else if ("approvalForm".equals(htType)) {
				htmcdName = "股东会决议";
			} else if ("LoanOverdueUrgeNotice".equals(htType)) {
				htmcdName = "贷款逾期通知书";
			} else if ("LoanExpirationNotice".equals(htType)) {
				htmcdName = "贷款到期通知书";
			} else if ("superviseRecordNote".equals(htType)) {
				htmcdName = "展期终审意见通知书";
			} else if ("superviseRecordVerification".equals(htType)) {
				htmcdName = "展期审批表";
			} else if ("LoanNotice".equals(htType)) {
				htmcdName = "放款通知书文档";
			} else if ("slSmallloadReviewTable".equals(htType)) {
				htmcdName = "贷款审查审批表";
			} else if ("slAlteraccrualRecordVerification".equals(htType)) {
				htmcdName = "利率变更审批表";
			} else if ("slEarlyrepaymentRecordVerification".equals(htType)) {
				htmcdName = "提前还款审批表";
			} else if ("LeaseEarlyrepaymentRecordVerification".equals(htType)) {
				htmcdName = "融资租赁提前还款审批表";
			} else if ("LeaseAlteraccrualRecordVerification".equals(htType)) {
				htmcdName = "融资租赁利率变更审批表";
			} else {
				htmcdName = "未知";
			}
			/*
			 * GLGuaranteeloanProject guaranteeProject = null; Enterprise
			 * enterprise = null; int enterpriseId = 0; String pathUrl = "";
			 * String shortName = ""; String fileName = "";
			 */

			if (null == templet_) {
				JsonUtil
						.responseJsonString("{success:false,exsit:false,msg :'请先上传模板'}");
				return;
			} else {
				if (!templet_.isIsexist()) {
					JsonUtil
							.responseJsonString("{success:false,exsit:false,msg :'请先上传模板'}");
					return;
				} else {
					FileForm fileForm_ = fileFormService.getById(templet_
							.getFileid());
					String serverPath = super.getRequest().getRealPath("/");//
					if (null != fileForm_) {
						String path_ = ElementUtil.replaceAllEleme(
								assureIntentBookElementCode, super.getRequest()
										.getRealPath("/")
										+ fileForm_.getFilepath(), fileForm_
										.getFilename());
						if ("false".equals(path_)) {
							JsonUtil
									.responseJsonString("{success:false,exsit:false,msg :'对应的模板文件未找到,请先上传模板'}");
							return;
						}

						// String serverPath =
						// super.getRequest().getRealPath("/");//
						DocumentTemplet templet = null;
						ProcreditContract contract = null;
						String path = "", fileName = "", shortName = "";
						int bytesum = 0, byteread = 0;
						HttpServletResponse response = getResponse();
						HttpServletRequest requrst = getRequest();
						String projId = requrst.getParameter("projId");
						int tempId = templet_.getId();
						String ttempId = templet_.getId() + "";
						String htid = requrst.getParameter("contractId");
						String cconId = (htid != "" ? htid : "0");
						int conId = Integer.parseInt(cconId);
						String contractId = requrst.getParameter("contractId");
						String cType = templet_.getTemplettype() + "";
						String businessType = businessType_;
						String projectNumber = "";

						try {
							// 查询对应的模板文件是否上传
							boolean isFileExists = false;
							templet = documentTempletService.getById(tempId);
							if (ttempId != null && templet != null) {
								DocumentTemplet dtemplet = documentTempletService
										.getById(tempId);
								if (dtemplet != null) {
									if (dtemplet.getIsexist() == true
											&& dtemplet.getFileid() != 0) {
										FileForm fileF = fileFormService
												.getById(dtemplet.getFileid());
										if (fileF != null) {
											File file = new File(serverPath
													+ fileF.getFilepath());
											if (!file.exists()) {
												JsonUtil.responseJsonFailure();
												return;
											} else {
												isFileExists = true;
											}
										} else {
											JsonUtil.responseJsonFailure();
											return;
										}
									} else {
										JsonUtil.responseJsonFailure();
										return;
									}
								} else {
									JsonUtil.responseJsonFailure();
									return;
								}
							} else {
								JsonUtil.responseJsonFailure();
								return;
							}
							// 如果模板文件已经上传
							if (isFileExists == true) {
								// 向ProcreditContractCategory表中增加或修改数据
								// Long bType = Long.parseLong(businessType+"");
								String bType = businessType;
								ProcreditContract p = null;
								if (null == contractId || contractId.equals("")
										|| contractId == "0"
										|| "0".equals(contractId)) {
									p = new ProcreditContract();
								} else {
									p = procreditContractService
											.getById(Integer
													.valueOf(contractId));
								}
								p.setContractCategoryTypeText(htlxdName);
								// p.setContractCategoryText(htmcdName);
								p.setHtType(htType);
								p.setTemptId(tempId);
								p.setTemplateId(tempId);
								if (htType == "thirdContract"
										|| "thirdContract".equals(htType)
										|| htType == "thirdRalationContract"
										|| "thirdRalationContract"
												.equals(htType)
										|| "ourThirdContract".equals(htType)
										|| htType == "ourThirdContract") {
									p.setMortgageId(thirdRalationId.intValue());// 反担保ID
								} else {
									p.setMortgageId(0);
									thirdRalationId = Long.parseLong("0");
								}
								p.setIsApply(isApply);// 展期合同有用
								// 如果合同是增加操作则执行以下
								if (null == contractId || contractId.equals("")
										|| contractId == "0"
										|| "0".equals(contractId)) {
									contractId = procreditContractService.add(
											p, projId, bType)
											+ "";
								} else {// 否则为修改操作
									if ("".equals(htlxdName)
											&& !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
										Object[] obj = { htmcdName, tempId,
												Integer.parseInt(contractId) };
										elementHandleService
												.updateCon(
														"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
														obj);
									} else if (!"".equals(htlxdName)
											&& "".equals(htmcdName)) {// 正规操作情况下没有这种可能
										Object[] obj = { htlxdName,
												Integer.parseInt(contractId) };
										elementHandleService
												.updateCon(
														"update ProcreditContract set contractCategoryTypeText=? where id =?",
														obj);
									} else if (!"".equals(htlxdName)
											&& !"".equals(htmcdName)) {
										Object[] obj = { htlxdName, htmcdName,
												tempId,
												Integer.parseInt(contractId) };
										elementHandleService
												.updateCon(
														"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
														obj);
									}

								}

								String searchRemark = this.getRequest()
										.getParameter("searchRemark");
								conId = procreditContractService.makeUpload(
										contractId, ttempId, cType, contractId,
										projId, thirdRalationId, businessType,
										null, searchRemark);// 向合同表中添加数据

								if (templet == null) {
									JsonUtil
											.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
									return;
								} else {
									SlSmallloanProject sloanProject = null;
									GLGuaranteeloanProject gloanProject = null;
									FlFinancingProject floanProject = null;
									// tem =
									// elementHandleService.seeElement(templet.getParentid());
									ProcreditContract pcontract = procreditContractService
											.getById(conId);
									// 获得企业简称或个人的名字，由项目的客户类型决定
									if (businessType == "SmallLoan"
											|| "SmallLoan".equals(businessType)) {// 小贷流程
										sloanProject = slSmallloanProjectService
												.get(Long.valueOf(pcontract
														.getProjId()));
										projectNumber = sloanProject
												.getProjectNumber();
										if ((sloanProject.getOppositeType())
												.equals("company_customer")) {// 客户类型为企业
											Enterprise enterp = enterpriseService
													.getById(Integer
															.parseInt(sloanProject
																	.getOppositeID()
																	.toString()));
											shortName = enterp.getShortname()
													.trim();
										} else if ((sloanProject
												.getOppositeType())
												.equals("person_customer")) {// 客户类型为个人
											Person person = personService
													.getById(Integer
															.parseInt(sloanProject
																	.getOppositeID()
																	.toString()));
											shortName = person.getName().trim();
										}
									} else if (businessType == "Guarantee"
											|| "Guarantee".equals(businessType)) {
										gloanProject = gLGuaranteeloanProjectService
												.get(Long.valueOf(pcontract
														.getProjId()));
										projectNumber = gloanProject
												.getProjectNumber();
										if ((gloanProject.getOppositeType())
												.equals("company_customer")) {// 客户类型为企业
											Enterprise enterp = enterpriseService
													.getById(Integer
															.parseInt(gloanProject
																	.getOppositeID()
																	.toString()));
											shortName = enterp.getShortname()
													.trim();
										} else if ((gloanProject
												.getOppositeType())
												.equals("person_customer")) {// 客户类型为个人
											Person person = personService
													.getById(Integer
															.parseInt(gloanProject
																	.getOppositeID()
																	.toString()));
											shortName = person.getName().trim();
										}
									} else if (businessType == "Financing"
											|| "Financing".equals(businessType)) {// 融资流程
										floanProject = flFinancingProjectService
												.get(Long.valueOf(pcontract
														.getProjId()));
										projectNumber = floanProject
												.getProjectNumber();
										if ((floanProject.getOppositeType())
												.equals("company_customer")) {
											Enterprise enterp = enterpriseService
													.getById(floanProject
															.getOppositeID()
															.intValue());
											shortName = enterp.getShortname()
													.trim();
										} else if ((floanProject
												.getOppositeType())
												.equals("person_customer")) {
											Person person = personService
													.getById(floanProject
															.getOppositeID()
															.intValue());
											shortName = person.getName().trim();
										}

									}
									// 生成合同和合同文件的名字
									if (conId != 0) {
										fileName = pcontract.getContractName();
										if (null == fileName) {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText()/*
																		 * +"-"+rnum
																		 */;
										} else {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText()/*
																		 * +"-"+rnum
																		 */;
										}
									} else {
										fileName = shortName + "-"
												+ projectNumber + "-"
												+ templet.getText()/* +"-"+rnum */;
									}
									FileForm fileForm = fileFormService
											.getById(templet.getFileid());
									p.setContractCategoryText(fileForm
											.getFilename());
									// path =
									// ElementUtil.getUrl(fileForm.getFilepath(),fileName+".doc");
									path = ElementUtil.getUrl(serverPath
											+ fileForm.getFilepath(), fileName
											+ ".doc");

									/*
									 * String fileCraName = "";
									 * if(businessType==
									 * "SmallLoan"||"SmallLoan".
									 * equals(businessType)){ fileCraName =
									 * shortName
									 * +"_"+sloanProject.getProjectNumber();
									 * }else
									 * if(businessType=="Guarantee"||"Guarantee"
									 * .equals(businessType)){ fileCraName =
									 * shortName
									 * +"_"+gloanProject.getProjectNumber();
									 * }else if(businessType == "Financing" ||
									 * "Financing".equals(businessType)){
									 * fileCraName =
									 * shortName+"_"+floanProject.getProjectName
									 * (); }
									 */
									String fileCraName = shortName + "_"
											+ projectNumber;
									// path =
									// "/projFile/contfolder/"+fileCraName;
									// String copyDir =
									// super.getRequest().getRealPath("/")+"attachFiles\\projFile\\contfolder\\"+fileCraName+"\\"+templet.getParentid()+"\\";
									String copyDir = serverPath
											+ "attachFiles\\projFile\\contfolder\\"
											+ fileCraName + "\\"
											+ templet.getParentid() + "\\";
									File cFile = new File(copyDir);
									if (!cFile.exists()) {
										// FileHelper.newFolder(copyDir);
										cFile.mkdirs();
									}

									String newPath = (copyDir + fileName + ".doc")
											.trim();
									String filePath = ("projFile/contfolder/"
											+ fileCraName + "/"
											+ templet.getParentid() + "/"
											+ fileName + ".doc").trim();
									Object[] obj = { true,
											"attachFiles/" + filePath, conId };
									elementHandleService
											.updateCon(
													"update ProcreditContract set isUpload=?, url=? where id =?",
													obj);
									List<FileAttach> listfileAttach = fileAttachService
											.listByContractId(conId);
									if (listfileAttach != null) {
										for (FileAttach fc : listfileAttach) {
											fc.setFilePath(filePath);
											fileAttachService.merge(fc);
										}
									} else {
										// 智维附件表操作start...为的是可以在线编辑
										FileAttach fileAttach = new FileAttach();
										fileAttach.setFileName(fileName);
										fileAttach.setFilePath(filePath);
										fileAttach.setCreatetime(new Date());
										fileAttach.setExt("doc");
										fileAttach
												.setFileType("attachFiles/uploads");
										// fileAttach.setNote(filesize.toString());
										fileAttach.setCreatorId(Long
												.parseLong(ContextUtil
														.getCurrentUser()
														.getId()));
										fileAttach
												.setCreator(ContextUtil
														.getCurrentUser()
														.getFullname());
										// fileAttach.setTotalBytes(Long.parseLong(filesize.toString())*1024);
										fileAttach.setDelFlag(0);
										fileAttach.setCsContractId(conId);
										fileAttachService.save(fileAttach);
										// 智维附件表操作end...
									}
									if (businessType == "SmallLoan"
											|| "SmallLoan".equals(businessType)) {// 小贷
										// ElementCode elementCode = new
										// ElementCode();
										SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
										// ElementCode elementCodeValue =
										// elementHandleService.getElementBySystem(JbpmUtil.piKeyToProjectId(projId),conId,tempId,cType)
										// ;
										// ElementCode elementCodeValue =
										// elementHandleService.getElementBySystem(projId,conId,tempId,cType)
										// ;
										SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
												.getElementBySystem(projId,
														conId, tempId, htType,
														null, null);
										File file = new File(path);
										if (file.exists()) {
											JacobWord
													.getInstance()
													.replaceAllText(
															path,
															ElementUtil
																	.findEleCodeArray(smallLoanElementCode),
															ElementUtil
																	.findEleCodeValueArray(smallLoanElementCodeValue));
											FileHelper.copyFile(path, newPath);
											// ElementUtil.downloadFile(path,
											// response);
											// ElementUtil.downloadFile(newPath,
											// response);
											FileHelper.deleteFile(path);

										} else
											return;
									} else if (businessType == "Guarantee"
											|| "Guarantee".equals(businessType)) {
										GuaranteeElementCode guaranteeElementCode = new GuaranteeElementCode();
										GuaranteeElementCode guaranteeElementCodeValue = elementHandleService
												.getGuaranteeElementBySystem(
														projId, conId, tempId,
														htType, null);
										File file = new File(path);
										if (file.exists()) {
											JacobWord
													.getInstance()
													.replaceAllText(
															path,
															ElementUtil
																	.findEleCodeArray(guaranteeElementCode),
															ElementUtil
																	.findEleCodeValueArray(guaranteeElementCodeValue));
											FileHelper.copyFile(path, newPath);
											FileHelper.deleteFile(path);

										} else
											return;
									} else if (businessType == "Financing"
											|| "Financing".equals(businessType)) {// 融资
										FinancingElementCode financingElementCode = new FinancingElementCode();
										FinancingElementCode financingElementCodeValue = elementHandleService
												.getFinancingElementBySystem(
														projId, conId, tempId,
														htType, null);
										File file = new File(path);
										if (file.exists()) {
											JacobWord
													.getInstance()
													.replaceAllText(
															path,
															ElementUtil
																	.findEleCodeArray(financingElementCode),
															ElementUtil
																	.findEleCodeValueArray(financingElementCodeValue));
											FileHelper.copyFile(path, newPath);
											FileHelper.deleteFile(path);

										} else
											return;
									} else if (businessType == "ExhibitionBusiness"
											|| "ExhibitionBusiness"
													.equals(businessType)) {
										SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
										SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
												.getExhibitionElementBySystem(
														projId, conId, tempId,
														htType, null);
										File file = new File(path);
										if (file.exists()) {
											// 这个地方是否得判断一下是否有要素???????

											JacobWord
													.getInstance()
													.replaceAllText(
															path,
															ElementUtil
																	.findEleCodeArray(smallLoanElementCode),
															ElementUtil
																	.findEleCodeValueArray(smallLoanElementCodeValue));
											FileHelper.copyFile(path, newPath);
											FileHelper.deleteFile(path);
										} else
											return;
									}

								}
								// 向实体添加数据，返回给客户端
								p.setId(Integer.parseInt(contractId));
								JsonUtil.jsonFromObject(p, true);
							} else {
								JsonUtil.responseJsonFailure();
							}
						} catch (Exception e) {
							JsonUtil.responseJsonFailure();
							logger.error("生成担保意向书/担保承诺函/股东会决议，利用模板和要素的值报错:"
									+ e.getMessage());
							e.printStackTrace();
						}

						/*
						 * //获取企业简称开始 if(businessType ==
						 * "Guarantee"||"Guarantee".equals(businessType)){//企业贷
						 * guaranteeProject
						 * =elementHandleService.findByGlGuaranteeloanId
						 * (projId);
						 * if((guaranteeProject.getOppositeType()).equals
						 * ("company_customer")){//客户类型为企业 Enterprise enterp =
						 * elementHandleService
						 * .findByEnterpriseId(Integer.parseInt
						 * (guaranteeProject.getOppositeID().toString()));
						 * shortName = enterp.getShortname().trim(); }else
						 * if((guaranteeProject
						 * .getOppositeType()).equals("person_customer"
						 * )){//客户类型为个人 Person person =
						 * elementHandleService.findByPersonId
						 * (Integer.parseInt(guaranteeProject
						 * .getOppositeID().toString())); shortName =
						 * person.getName().trim(); } }else{ } smallProject =
						 * elementHandleService.findByEnterProjectId(projId);
						 * if(smallProject != null){ enterpriseId =
						 * Integer.parseInt
						 * (smallProject.getOppositeID().toString()); enterprise
						 * =
						 * elementHandleService.findByEnterpriseId(enterpriseId
						 * ); if(enterprise != null &&
						 * smallProject.getOppositeID() != null) { Person person
						 * =personService.queryPersonEntity(Integer.parseInt(
						 * smallProject.getOppositeID().toString())) ; shortName
						 * = person.getName(); } if(enterprise != null &&
						 * smallProject.getOppositeID()==null ){ shortName =
						 * enterprise.getShortname(); } } fileName =
						 * (shortName+"-"+templet.getText()+".doc").trim();
						 * pathUrl =
						 * ElementUtil.getUrl(fileForm.getFilepath(),fileName);
						 * ElementCode elementCode = new ElementCode(); //拟担保意向书
						 * if
						 * ("simulateEnterpriseBook".equals(mark)||"personNiDanBao"
						 * .equals(mark)){ AssureIntentBookElementCode
						 * elementCodeValue =
						 * elementHandleService.getAssureBookElement
						 * (projId,startTime,endTime) ; //SmallLoanElementCode
						 * elementCodeValue =
						 * elementHandleService.getElementBySystem(projId, 0, 0,
						 * "","") ;
						 * JacobWord.getInstance().replaceAllText(pathUrl,
						 * ElementUtil.findEleCodeArray(elementCode),
						 * ElementUtil.findEleCodeValueArray(elementCodeValue));
						 * }else
						 * if("acceptanceLetter".equals(mark)||"personChengNuoHan"
						 * .equals(mark)){ //生成客户承诺函保存一条记录到合同表，如果存在则不添加; boolean
						 * isExitsOrSaveOk =
						 * elementHandleService.saveAcceptanceLetter(projId,
						 * templet,5,templetName); if(isExitsOrSaveOk){
						 * SmallLoanElementCode elementCodeValue =
						 * elementHandleService.getElementBySystem(projId, 0, 0,
						 * "","") ;
						 * JacobWord.getInstance().replaceAllText(pathUrl,
						 * ElementUtil.findEleCodeArray(elementCode),
						 * ElementUtil.findEleCodeValueArray(elementCodeValue));
						 * } }elseif("assureCommitmentLetter".equals(mark)||
						 * "personDuiWaiDanBao".equals(mark)){
						 * //生成对外担保承诺函保存一条记录到合同表，如果存在则不添加; boolean
						 * isExitsOrSaveOk =
						 * elementHandleService.saveAcceptanceLetter(projId,
						 * templet,6,templetName); if(isExitsOrSaveOk){
						 * SmallLoanElementCode elementCodeValue =
						 * elementHandleService.getElementBySystem(projId, 0, 0,
						 * "","") ;
						 * JacobWord.getInstance().replaceAllText(pathUrl,
						 * ElementUtil.findEleCodeArray(elementCode),
						 * ElementUtil.findEleCodeValueArray(elementCodeValue));
						 * } } //JacobWord.getInstance().replaceAllText(path,
						 * ElementUtil.findEleCodeArray(elementCode),
						 * ElementUtil.findEleCodeValueArray(elementCodeValue));
						 * ElementUtil.downloadFile(pathUrl, response);
						 * FileHelper.deleteFile(pathUrl);
						 */
					} else {
						JsonUtil
								.responseJsonString("{success:false,exsit:false,msg :'对应的模板文件未找到,生成模板前,请先上传模板'}");
						return;
					}
				}
			}
		} catch (Exception e) {
			logger.error("生成担保意向书/担保承诺函/股东会决议，利用模板和要素的值报错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/** 所有要素 */
	public void findElementList() {
		List list = null;
		/*
		 * if(businessType == 1672){ ElementCode elementCode = new
		 * ElementCode(); list = ElementUtil.getListElement(elementCode) ; }else
		 * if(businessType == 1673){
		 * 
		 * }
		 */
		// else if(businessType == 1674){
		// GuaranteeLetterElement guaranteeLetterElement = new
		// GuaranteeLetterElement();
		// list = ElementUtil.getListElement(guaranteeLetterElement) ;
		// }
		if ("SmallLoan".equals(businessType)) {
			SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
			list = ElementUtil.getListElement(smallLoanElementCode);
			/*
			 * CreditmanagementElementCode creditmanagementElementCode = new
			 * CreditmanagementElementCode(); list =
			 * ElementUtil.getCreditManageListElement
			 * (creditmanagementElementCode) ;
			 */
		} else if ("Financing".equals(businessType)) {
			FinancingElementCode financingElementCode = new FinancingElementCode();
			list = ElementUtil.getListElement(financingElementCode);
		} else if ("Guarantee".equals(businessType)) {
			GuaranteeElementCode guaranteeElementCode = new GuaranteeElementCode();
			list = ElementUtil.getListElement(guaranteeElementCode);
		} else if ("LeaseFinance".equals(businessType)) {
			LeaseFinanceElementCode leaseFinanceElementCode = new LeaseFinanceElementCode();
			list = ElementUtil.getListElement(leaseFinanceElementCode);
		} else if ("Pawn".equals(businessType)) {
			PawnElementCode pawnElementCode = new PawnElementCode();
			list = ElementUtil.getListElement(pawnElementCode);
		}
		int total = 0;
		if (list != null) {
			total = list.size();
		}
		JsonUtil.jsonFromList(list, total);
	}

	/** 生成合同 ccc */
	public void makeContract() {
		String serverPath = super.getRequest().getRealPath("/");//
		DocumentTemplet templet = null;
		ProcreditContract contract = null;
		String path = "", fileName = "", shortName = "";
		int bytesum = 0, byteread = 0;
		HttpServletResponse response = getResponse();
		HttpServletRequest requrst = getRequest();
		String projId = requrst.getParameter("projId");
		int tempId = Integer.parseInt(requrst.getParameter("templateId"));
		// int conId =
		// Integer.parseInt(requrst.getParameter("contractId")!=null||requrst.getParameter("contractId")!=""||!"".equals(requrst.getParameter("contractId"))?requrst.getParameter("contractId"):"0");
		String ttempId = requrst.getParameter("templateId");
		String htid = requrst.getParameter("contractId");
		String cconId = (htid != "" ? htid : "0");
		int conId = Integer.parseInt(cconId);
		String contractId = requrst.getParameter("contractId");
		String cType = requrst.getParameter("contractType");
		// int businessType
		// =Integer.parseInt(requrst.getParameter("businessType"));
		String businessType = requrst.getParameter("businessType");
		String clauseId = requrst.getParameter("clauseId");
		String searchRemark = requrst.getParameter("searchRemark");
		// DocumentTemplet tem = null;
		SlSmallloanProject smallloanProject = null;
		Enterprise enterprise = null;
		String projectNumber = "";

		try {
			// 查询对应的模板文件是否上传
			templet = documentTempletService.getById(tempId);
			if (ttempId != null && templet != null) {
				DocumentTemplet dtemplet = documentTempletService
						.getById(tempId);
				if (dtemplet != null) {
					if (dtemplet.getIsexist() == true
							&& dtemplet.getFileid() != 0) {
						FileForm fileF = fileFormService.getById(dtemplet
								.getFileid());
						if (fileF != null) {
							File file = new File(serverPath
									+ fileF.getFilepath());
							if (!file.exists()) {
								JsonUtil.responseJsonFailure();
								return;
							}
						} else {
							JsonUtil.responseJsonFailure();
							return;
						}
					} else {
						JsonUtil.responseJsonFailure();
						return;
					}
				} else {
					JsonUtil.responseJsonFailure();
					return;
				}
			} else {
				JsonUtil.responseJsonFailure();
				return;
			}
			// 如果模板文件已经上传
			// 向ProcreditContractCategory表中增加或修改数据
			// Long bType = Long.parseLong(businessType+"");
			String bType = businessType;
			ProcreditContract p = null;
			if (null == contractId || "".equals(contractId)) {
				p = new ProcreditContract();
			} else {
				p = procreditContractService.getById(Integer
						.valueOf(contractId));
			}
			p.setContractCategoryTypeText(htlxdName);
			p.setContractCategoryText(htmcdName);
			p.setHtType(htType);
			if (!"".equals(clauseId) && clauseId != null) {
				p.setClauseId(Long.parseLong(clauseId));
			}
			p.setTemptId(tempId);
			if (htType == "thirdContract" || "thirdContract".equals(htType)
					|| htType == "thirdRalationContract"
					|| "thirdRalationContract".equals(htType)
					|| "ourThirdContract".equals(htType)
					|| htType == "ourThirdContract"
					|| "baozContract".equals(htType)
					|| htType == "baozContract" || htType == "dwContract"
					|| "dwContract".equals(htType)) {
				p.setMortgageId(thirdRalationId.intValue());// 反担保ID
			} else {
				p.setMortgageId(0);
				thirdRalationId = Long.parseLong("0");
			}
			p.setIsApply(isApply);// 展期合同有用
			// 如果合同是增加操作则执行以下
			if (null == contractId || "".equals(contractId)) {
				contractId = procreditContractService.add(p, projId, bType)
						+ "";

			} else {// 否则为修改操作
				if ("".equals(htlxdName) && !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
					Object[] obj = { htmcdName, tempId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && "".equals(htmcdName)) {// 正规操作情况下没有这种可能
					Object[] obj = { htlxdName, Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && !"".equals(htmcdName)) {
					Object[] obj = { htlxdName, htmcdName, tempId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
									obj);
				}

			}

			conId = procreditContractService.makeUpload(cconId, ttempId, cType,
					contractId, projId, thirdRalationId, businessType, null,
					searchRemark);// 向合同表中添加数据

			if (templet == null) {
				JsonUtil
						.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
				return;
			} else {
				SlSmallloanProject sloanProject = null;
				GLGuaranteeloanProject gloanProject = null;
				FlFinancingProject floanProject = null;
				PlPawnProject pawnProject = null;
				FlLeaseFinanceProject flLeaseFinance = null;
				// tem = elementHandleService.seeElement(templet.getParentid());
				ProcreditContract pcontract = procreditContractService
						.getById(conId);
				// 获得企业简称或个人的名字，由项目的客户类型决定
				if (businessType == "SmallLoan"
						|| "SmallLoan".equals(businessType)) {// 小贷流程
					sloanProject = slSmallloanProjectService.get(Long
							.valueOf(pcontract.getProjId()));
					projectNumber = sloanProject.getProjectNumber();
					if ((sloanProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((sloanProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "Guarantee"
						|| "Guarantee".equals(businessType)) {
					gloanProject = gLGuaranteeloanProjectService.get(Long
							.valueOf(pcontract.getProjId()));
					projectNumber = gloanProject.getProjectNumber();
					if ((gloanProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(gloanProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((gloanProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(gloanProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "Financing"
						|| "Financing".equals(businessType)) {// 融资流程
					floanProject = flFinancingProjectService.get(Long
							.valueOf(pcontract.getProjId()));
					projectNumber = floanProject.getProjectNumber();
					if ((floanProject.getOppositeType())
							.equals("company_customer")) {
						Enterprise enterp = enterpriseService
								.getById(floanProject.getOppositeID()
										.intValue());
						shortName = enterp.getShortname().trim();
					} else if ((floanProject.getOppositeType())
							.equals("person_customer")) {
						Person person = personService.getById(floanProject
								.getOppositeID().intValue());
						shortName = person.getName().trim();
					}
				} else if (businessType == "ExhibitionBusiness"
						|| "ExhibitionBusiness".equals(businessType)) {// 展期流程
					SlSuperviseRecord slSuperviseRecord = (SlSuperviseRecord) creditBaseDao
							.getById(SlSuperviseRecord.class, Long
									.valueOf(pcontract.getProjId()));
					sloanProject = slSmallloanProjectService
							.get(slSuperviseRecord.getProjectId());
					projectNumber = sloanProject.getProjectNumber();
					if ((sloanProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((sloanProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "Pawn"
						|| "Pawn".equals(businessType)) {// 小贷流程
					pawnProject = (PlPawnProject) creditBaseDao.getById(
							PlPawnProject.class, Long.valueOf(pcontract
									.getProjId()));
					projectNumber = pawnProject.getProjectNumber();
					if ((pawnProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(pawnProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((pawnProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(pawnProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "LeaseFinance"
						|| "LeaseFinance".equals(businessType)) {// 租赁流程
					flLeaseFinance = (FlLeaseFinanceProject) creditBaseDao
							.getById(FlLeaseFinanceProject.class, Long
									.valueOf(pcontract.getProjId()));
					projectNumber = flLeaseFinance.getProjectNumber();
					if ((flLeaseFinance.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(flLeaseFinance.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((flLeaseFinance.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(flLeaseFinance.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				}
				// 生成合同和合同文件的名字
				if (conId != 0) {
					fileName = pcontract.getContractName();
					if (null == fileName) {
						fileName = shortName + "-" + projectNumber + "-"
								+ templet.getText()/* +"-"+rnum */;
					} else {
						fileName = shortName + "-" + projectNumber + "-"
								+ templet.getText()/* +"-"+rnum */;
					}
				} else {
					fileName = shortName + "-" + projectNumber + "-"
							+ templet.getText()/* +"-"+rnum */;
				}
				FileForm fileForm = fileFormService
						.getById(templet.getFileid());
				// path =
				// ElementUtil.getUrl(fileForm.getFilepath(),fileName+".doc");
				path = ElementUtil.getUrl(serverPath + fileForm.getFilepath(),
						fileName + ".doc");

				/*
				 * String fileCraName = "";
				 * if(businessType=="SmallLoan"||"SmallLoan"
				 * .equals(businessType)){ fileCraName =
				 * shortName+"_"+sloanProject.getProjectNumber(); }else
				 * if(businessType
				 * =="Guarantee"||"Guarantee".equals(businessType)){ fileCraName
				 * = shortName+"_"+gloanProject.getProjectNumber(); }else
				 * if(businessType == "Financing" ||
				 * "Financing".equals(businessType)){ fileCraName =
				 * shortName+"_"+floanProject.getProjectName(); }
				 */
				String fileCraName = shortName + "_" + projectNumber;
				// path = "/projFile/contfolder/"+fileCraName;
				// String copyDir =
				// super.getRequest().getRealPath("/")+"attachFiles\\projFile\\contfolder\\"+fileCraName+"\\"+templet.getParentid()+"\\";
				String copyDir = serverPath
						+ "attachFiles\\projFile\\contfolder\\" + fileCraName
						+ "\\" + templet.getParentid() + "\\";
				File cFile = new File(copyDir);
				if (!cFile.exists()) {
					// FileHelper.newFolder(copyDir);
					cFile.mkdirs();
				}

				String newPath = (copyDir + fileName + ".doc").trim();
				String filePath = ("projFile/contfolder/" + fileCraName + "/"
						+ templet.getParentid() + "/" + fileName + ".doc")
						.trim();
				Object[] obj = { true, "attachFiles/" + filePath, conId };
				ProcreditContract pc = procreditContractService.getById(conId);
				pc.setIsUpload(true);
				pc.setUrl("attachFiles/" + filePath);
				procreditContractService.merge(pc);
				List<FileAttach> listfileAttach = fileAttachService
						.listByContractId(conId);
				if (listfileAttach != null) {
					for (FileAttach f : listfileAttach) {
						f.setFilePath(filePath);
						fileAttachService.merge(f);
					}
				} else {
					// 智维附件表操作start...为的是可以在线编辑
					FileAttach fileAttach = new FileAttach();
					fileAttach.setFileName(fileName);
					fileAttach.setFilePath(filePath);
					fileAttach.setCreatetime(new Date());
					fileAttach.setExt("doc");
					fileAttach.setFileType("attachFiles/uploads");
					fileAttach.setCreatorId(Long.parseLong(ContextUtil
							.getCurrentUser().getId()));
					fileAttach.setCreator(ContextUtil.getCurrentUser()
							.getFullname());
					fileAttach.setDelFlag(0);
					fileAttach.setCsContractId(conId);
					fileAttachService.save(fileAttach);
					// 智维附件表操作end...
				}
				// 写模板
				if (businessType == "SmallLoan"
						|| "SmallLoan".equals(businessType)) {// 小贷
					// ElementCode elementCode = new ElementCode();
					SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
					SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
							.getElementBySystem(projId, conId, tempId, htType,
									null, null);
					File file = new File(path);
					if (file.exists()) {
						// 这个地方是否得判断一下是否有要素???????

						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(smallLoanElementCode),
										ElementUtil
												.findEleCodeValueArray(smallLoanElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if (businessType == "Guarantee"
						|| "Guarantee".equals(businessType)) {
					GuaranteeElementCode guaranteeElementCode = new GuaranteeElementCode();
					GuaranteeElementCode guaranteeElementCodeValue = elementHandleService
							.getGuaranteeElementBySystem(projId, conId, tempId,
									htType, null);
					File file = new File(path);
					if (file.exists()) {
						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(guaranteeElementCode),
										ElementUtil
												.findEleCodeValueArray(guaranteeElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if (businessType == "Financing"
						|| "Financing".equals(businessType)) {// 融资
					FinancingElementCode financingElementCode = new FinancingElementCode();
					FinancingElementCode financingElementCodeValue = elementHandleService
							.getFinancingElementBySystem(projId, conId, tempId,
									htType, null);
					File file = new File(path);
					if (file.exists()) {
						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(financingElementCode),
										ElementUtil
												.findEleCodeValueArray(financingElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);

					} else
						return;
				} else if (businessType == "ExhibitionBusiness"
						|| "ExhibitionBusiness".equals(businessType)) {
					SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
					SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
							.getExhibitionElementBySystem(projId, conId,
									tempId, htType, null);
					File file = new File(path);
					if (file.exists()) {
						// 这个地方是否得判断一下是否有要素???????

						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(smallLoanElementCode),
										ElementUtil
												.findEleCodeValueArray(smallLoanElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if (businessType == "Pawn"
						|| "Pawn".equals(businessType)) {// 典当
					PawnElementCode pawnElementCode = new PawnElementCode();
					PawnElementCode pawnElementCodeValue = elementHandleService
							.getPawnElementBySystem(projId, conId, tempId,
									htType, null, dwId);
					File file = new File(path);
					if (file.exists()) {
						// 这个地方是否得判断一下是否有要素???????

						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(pawnElementCode),
										ElementUtil
												.findEleCodeValueArray(pawnElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if (businessType == "LeaseFinance"
						|| "LeaseFinance".equals(businessType)) {// 融资租赁
					LeaseFinanceElementCode leaseFinanceElementCode = new LeaseFinanceElementCode();
					LeaseFinanceElementCode leaseFinanceElementCodeValue = elementHandleService
							.getLeaseFinanceElementBySystem(projId, conId,
									tempId, htType, null, leaseObjectInfoId);
					File file = new File(path);
					if (file.exists()) {
						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(leaseFinanceElementCode),
										ElementUtil
												.findEleCodeValueArray(leaseFinanceElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				}

			}
			// 向实体添加数据，返回给客户端
			p.setId(Integer.parseInt(contractId));
			JsonUtil.jsonFromObject(p, true);
		} catch (Exception e) {
			JsonUtil.responseJsonFailure();
			logger.error("生成合同报错:" + e.getMessage());
			e.printStackTrace();
		}

	}

	/** 下载合同 小贷 */
	public void downloadContract() {
		ProcreditContract contract = null;
		HttpServletResponse response = getResponse();
		HttpServletRequest requrst = getRequest();
		int conId = Integer.parseInt(requrst.getParameter("conId"));
		String path = null;
		try {
			contract = (ProcreditContract) procreditContractService
					.getById(conId);
			if (contract != null) {
				path = contract.getUrl();
				ElementUtil.downloadFile(path, response);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("下载合同报错:" + e.getMessage());
			e.printStackTrace();
		}

	}

	/** 下载合同 */
	/*
	 * public void downloadContract(){ DocumentTemplet templet = null ;
	 * ProcreditContract contract = null; String path = "", fileName =""; int
	 * bytesum = 0 ,byteread = 0; HttpServletResponse response = getResponse();
	 * HttpServletRequest requrst = getRequest(); String projId =
	 * requrst.getParameter("piKey"); int tempId =
	 * Integer.parseInt(requrst.getParameter("tempId")); int conId =
	 * Integer.parseInt(requrst.getParameter("conId")); String cType =
	 * requrst.getParameter("cType"); DocumentTemplet tem = null;
	 * SlSmallloanProject smallloanProject =null; Enterprise enterprise =null;
	 * try { templet = elementHandleService.seeElement(tempId); try { tem =
	 * elementHandleService.seeElement(templet.getParentid()); smallloanProject
	 * = elementHandleService.findByEnterProjectId(projId); enterprise =
	 * (Enterprise
	 * )elementHandleService.findByEnterpriseId(Integer.parseInt(smallloanProject
	 * .getOppositeID().toString())); } catch (Exception e1) {
	 * e1.printStackTrace(); } if(conId != 0){ contract =
	 * elementHandleService.findByContractId(conId); fileName =
	 * contract.getContractName(); if(null == fileName){ fileName =
	 * enterprise.getShortname()+"—"+tem.getText(); } }else{ fileName =
	 * enterprise.getShortname()+"—"+tem.getText(); } if(templet == null){
	 * JsonUtil.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
	 * return ; }else{ FileForm fileForm =
	 * fileService.getFileEntity(templet.getFileid()); path =
	 * ElementUtil.getUrl(fileForm.getFilepath(),fileName+".doc");
	 * 
	 * ElementCode elementCode = new ElementCode(); // ElementCode
	 * elementCodeValue =
	 * elementHandleService.getElementBySystem(JbpmUtil.piKeyToProjectId
	 * (projId),conId,tempId,cType) ; ElementCode elementCodeValue =
	 * elementHandleService.getElementBySystem(projId,conId,tempId,cType) ; File
	 * file = new File(path); if(file.exists()){
	 * JacobWord.getInstance().replaceAllText(path,
	 * ElementUtil.findEleCodeArray(elementCode),
	 * ElementUtil.findEleCodeValueArray(elementCodeValue)); //
	 * ElementUtil.downloadFile(path, response); ElementUtil.downloadFile(path,
	 * response); FileHelper.deleteFile(path); }else return ; } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */
	/** 获得服务器的路径 */
	private String serversPath(String path) {
		String s = path, res = "";
		String[] a = new String[7];
		for (int i = 0; i < 7; i++) {
			int j = s.indexOf("\\", 0);
			String b = s.substring(0, j);
			s = s.substring(j + 1);
			a[i] = b;
			res = res + a[i] + "\\";
			if (a[i].trim().equals("webapps")) {
				res = res + s.substring(0, s.indexOf("\\", 0));
				break;
			}
		}
		return res.trim();
	}

	public void uploadEnterprisePhoto() {
		HttpServletRequest request = getRequest();
		String mark = getRequest().getParameter("mark");
		String realPath = super.getRequest().getRealPath("/");
		File file = new File(realPath
				+ "attachFiles\\uploads\\cs_enterprise_tx");
		if (!file.exists()) {
			file.mkdirs();
		}
		String guid = UUID.randomUUID().toString();
		String webPath = "attachFiles/uploads/cs_enterprise_tx" + "/" + guid
				+ extendname;
		String filepath = "attachFiles\\uploads\\cs_enterprise_tx" + "\\"
				+ guid + extendname;
		file = new File(realPath + "attachFiles\\uploads\\cs_enterprise_tx"
				+ "\\" + guid + extendname);
		boolean flag = FileHelper.fileUpload(fileUpload, file,
				new byte[UPLOAD_SIZE]);
		FileForm fileinfo = new FileForm();
		if (flag) {
			fileinfo.setMark(mark);
			fileinfo.setContentType("");
			fileinfo.setSetname(fileUploadFileName);
			fileinfo.setFilename("");
			fileinfo.setFilepath(filepath);
			fileinfo.setExtendname(extendname);
			fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());
			Long sl = fileUpload.length();
			fileinfo.setFilesize(sl.intValue());
			fileinfo.setCreatorid(1);
			// fileinfo.setRemark("");
			fileinfo.setWebPath(webPath);
			fileFormService.save(fileinfo);
		}
		String jsonStr = "{success : true,fileid :" + fileinfo.getFileid()
				+ ",webPath :'" + fileinfo.getWebPath() + "'}";
		JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");

	}

	public void uploadPhoto() {
		HttpServletRequest request = getRequest();
		String mark = getRequest().getParameter("mark");
		String realPath = super.getRequest().getRealPath("/");
		File file = new File(realPath + "attachFiles\\uploads\\cs_person_tx");
		if (!file.exists()) {
			file.mkdirs();
		}
		String guid = UUID.randomUUID().toString();
		String webPath = "attachFiles/uploads/cs_person_tx" + "/" + guid
				+ extendname;
		String filepath = "attachFiles\\uploads\\cs_person_tx" + "\\" + guid
				+ extendname;
		file = new File(realPath + "attachFiles\\uploads\\cs_person_tx" + "\\"
				+ guid + extendname);
		boolean flag = FileHelper.fileUpload(fileUpload, file,
				new byte[UPLOAD_SIZE]);
		FileForm fileinfo = new FileForm();
		if (flag) {
			fileinfo.setMark(mark);
			fileinfo.setContentType("");
			fileinfo.setSetname(fileUploadFileName);
			fileinfo.setFilename(fileUploadFileName);
			fileinfo.setFilepath(filepath);
			fileinfo.setExtendname(extendname);
			fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());
			Long sl = fileUpload.length();
			fileinfo.setFilesize(sl.intValue());
			fileinfo.setCreatorid(1);
			// fileinfo.setRemark("");
			fileinfo.setWebPath(webPath);
			fileFormService.save(fileinfo);
		}
		String jsonStr = "{success : true,fileid :" + fileinfo.getFileid()
				+ ",webPath :'" + fileinfo.getWebPath() + "'}";
		JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");

	}

	/**
	 * 上传合同
	 */
	public void uploadContract() {
		HttpServletRequest request = getRequest();
		String realPath = "";
		String path = "";
		String filePath = "";
		String jsonStr = "";
		String shortName = "";
		String fileName = "";
		String webPath = "";
		String ttempId = request.getParameter("templateId");
		int templateId = Integer.parseInt(request.getParameter("templateId"));
		// int businessType =
		// Integer.parseInt(request.getParameter("businessType"));
		String businessType = request.getParameter("businessType");
		String projId = request.getParameter("projId");
		String htid = request.getParameter("conId");
		/* String cconId = (htid==""||"".equals(htid)?"0":htid); */

		String contractId = request.getParameter("contractId");
		int conId = Integer.parseInt(contractId);
		String htlxdName = request.getParameter("htlxdName");
		String htmcdName = request.getParameter("htmcdName");
		String htType = request.getParameter("htType");
		String thirdRalationId = request.getParameter("thirdRalationId");
		Long thirdRId = thirdRalationId.equals("") ? 0 : Long
				.parseLong(thirdRalationId);
		String isApply = request.getParameter("isApply");
		String clauseId = request.getParameter("clauseId");
		// DocumentTemplet tem =null;
		DocumentTemplet templet = null;
		File file = null;
		boolean flag = false;
		String extendName = request.getParameter("extendName");
		try {
			ProcreditContract p = null;
			if (null == contractId || contractId == "0"
					|| "0".equals(contractId) || "".equals(contractId)) {
				p = new ProcreditContract();
			} else {
				p = procreditContractService.getById(Integer
						.valueOf(contractId));
			}
			p.setContractCategoryTypeText(htlxdName);
			p.setContractCategoryText(htmcdName);
			if (!"".equals(htType)) {
				p.setHtType(htType);
			}
			if (!"".equals(clauseId) && clauseId != null) {
				p.setClauseId(Long.parseLong(clauseId));
			}
			p.setTemptId(templateId);
			p.setMortgageId(thirdRalationId.equals("")
					|| "0".equals(thirdRalationId) ? 0 : Integer
					.parseInt(thirdRalationId));
			p.setIsApply(isApply.equals("true") ? true : false);
			if (contractId == "0" || "0".equals(contractId)
					|| "".equals(contractId) || null == contractId) {
				contractId = procreditContractService.add(p, projId,
						businessType)
						+ "";
			} else {// 否则为修改操作
				if ("".equals(htlxdName) && !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
					Object[] obj = { htmcdName, templateId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && "".equals(htmcdName)) {// 正规操作情况下没有这种可能
					Object[] obj = { htlxdName, Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && !"".equals(htmcdName)) {
					Object[] obj = { htlxdName, htmcdName, templateId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
									obj);
				}

			}

			String searchRemark = this.getRequest()
					.getParameter("searchRemark");
			conId = procreditContractService.makeUpload(contractId, ttempId,
					"", contractId, projId, thirdRId, businessType, null,
					searchRemark);// 向合同表中添加数据

			ProcreditContract contract = procreditContractService
					.getById(conId);
			templet = documentTempletService.getById(templateId);
			// tem = elementHandleService.seeElement(templet.getParentid());
			BpFundProject smallloanProject = null;
			GLGuaranteeloanProject gloanProject = null;
			FlFinancingProject floanProject = null;
			FlLeaseFinanceProject flLeaseFinanceProject = null;
			PlPawnProject plPawnProject = null;
			if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {
				smallloanProject = bpFundProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((smallloanProject.getOppositeType())
						.equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(Integer
							.parseInt(smallloanProject.getOppositeID()
									.toString()));
					shortName = enterp.getShortname().trim();
				} else if ((smallloanProject.getOppositeType())
						.equals("person_customer")) {
					Person person = personService.getById(Integer
							.parseInt(smallloanProject.getOppositeID()
									.toString()));
					shortName = person.getName().trim();
				}
			} else if (businessType == "Guarantee"
					|| "Guarantee".equals(businessType)) {
				gloanProject = gLGuaranteeloanProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((gloanProject.getOppositeType()).equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(Integer
							.parseInt(gloanProject.getOppositeID().toString()));
					shortName = enterp.getShortname().trim();
				} else if ((gloanProject.getOppositeType())
						.equals("person_customer")) {
					Person person = personService.getById(Integer
							.parseInt(gloanProject.getOppositeID().toString()));
					shortName = person.getName().trim();
				}
			} else if (businessType == "Financing"
					|| "Financing".equals(businessType)) {
				floanProject = flFinancingProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((floanProject.getOppositeType()).equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(floanProject
							.getOppositeID().intValue());
					shortName = enterp.getShortname().trim();
				} else if ((floanProject.getOppositeType()
						.equals("person_customer"))) {
					Person person = personService.getById(floanProject
							.getOppositeID().intValue());
					shortName = person.getName().trim();
				}
			} else if (businessType == "LeaseFinance"
					|| "LeaseFinance".equals(businessType)) {// add by
																// gaoqingrui
				flLeaseFinanceProject = flLeaseFinanceProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((flLeaseFinanceProject.getOppositeType())
						.equals("company_customer")) {
					Enterprise enterp = enterpriseService
							.getById(flLeaseFinanceProject.getOppositeID()
									.intValue());
					shortName = enterp.getShortname().trim();
				} else if ((flLeaseFinanceProject.getOppositeType()
						.equals("person_customer"))) {
					Person person = personService.getById(flLeaseFinanceProject
							.getOppositeID().intValue());
					shortName = person.getName().trim();
				}
			} else if ("Pawn".equals(businessType)) {// add by gaoqingrui
				plPawnProject = elementHandleService
						.findByPawnProjectId(contract.getProjId());
				if ((plPawnProject.getOppositeType())
						.equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(plPawnProject
							.getOppositeID().intValue());
					shortName = enterp.getShortname().trim();
				} else if ((plPawnProject.getOppositeType()
						.equals("person_customer"))) {
					Person person = personService.getById(plPawnProject
							.getOppositeID().intValue());
					shortName = person.getName().trim();
				}
			}

			fileName = contract.getContractName();
			String fileCraName = "";
			if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {
				fileCraName = shortName + "_"
						+ smallloanProject.getProjectNumber();
			} else if (businessType == "Guarantee"
					|| "Guarantee".equals(businessType)) {
				fileCraName = shortName + "_" + gloanProject.getProjectNumber();
			} else if (businessType == "Financing"
					|| "Financing".equals(businessType)) {
				fileCraName = shortName + "_" + floanProject.getProjectNumber();
			} else if ("LeaseFinance".equals(businessType)) {
				fileCraName = shortName + "_"
						+ flLeaseFinanceProject.getProjectNumber();
			} else if ("Pawn".equals(businessType)) {
				fileCraName = shortName + "_"
						+ plPawnProject.getProjectNumber();
			}
			// Enterprise enter =
			// elementHandleService.findByEnterpriseId(Integer.parseInt(smallloanProject.getOppositeID().toString()));
			// String fileCraName =
			// enter.getShortname().trim()+"_"+smallloanProject.getProjectNumber();
			// path = "/projFile/contfolder/"+fileCraName;
			path = "/attachFiles/projFile/contfolder/" + fileCraName + "/"
					+ templet.getParentid() + "/";
			webPath = "attachFiles\\projFile\\contfolder\\" + fileCraName
					+ "\\" + templet.getParentid();
			filePath = "projFile/contfolder/" + fileCraName + "/"
					+ templet.getParentid() + "/" + fileName + extendName;
			contractId = contract.getId().toString();
			realPath = ServletActionContext.getServletContext().getRealPath(
					path);
			file = new File(realPath);
			if (!file.exists()) {
				// FileHelper.newFolder(realPath);
				file.mkdirs();
			}
			file = new File(realPath + "/" + fileName + extendName);
			flag = FileHelper.fileUpload(fileUpload, file,
					new byte[UPLOAD_SIZE]);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传合同报错:" + e.getMessage());
			jsonStr = "{success : false}";
		}
		if (flag) {
			try {
				// Object[] obj = {true ,realPath+"\\"+fileName+".doc" ,conId} ;
				Object[] obj = { true, webPath + "\\" + fileName + extendName,
						conId };
				ProcreditContract pc = procreditContractService.getById(conId);
				pc.setIsUpload(true);
				pc.setUrl(webPath + "\\" + fileName + extendName);
				procreditContractService.merge(pc);
				List<FileAttach> listfileAttach = fileAttachService
						.listByContractId(conId);
				if (listfileAttach != null) {
					String extkzm = fileUploadFileName.split("\\.").length > 0 ? fileUploadFileName
							.split("\\.")[1]
							: extendName.substring(1, extendName.length());
					for (FileAttach f : listfileAttach) {
						f.setFilePath(filePath);
						f.setExt(extkzm);
						fileAttachService.merge(f);
					}
				} else {
					// 智维附件表操作start...
					FileAttach fileAttach = new FileAttach();
					fileAttach.setFileName(fileName);
					fileAttach.setFilePath(filePath);
					fileAttach.setCreatetime(new Date());
					String extkzm = fileUploadFileName.split("\\.").length > 0 ? fileUploadFileName
							.split("\\.")[1]
							: extendName.substring(1, extendName.length());
					fileAttach.setExt(extkzm);
					fileAttach.setFileType("attachFiles/uploads");
					// fileAttach.setNote(filesize.toString());
					fileAttach.setCreatorId(Long.parseLong(ContextUtil
							.getCurrentUser().getId()));
					fileAttach.setCreator(ContextUtil.getCurrentUser()
							.getFullname());
					// fileAttach.setTotalBytes(Long.parseLong(filesize.toString())*1024);
					fileAttach.setDelFlag(0);
					fileAttach.setCsContractId(Integer.valueOf(contractId));
					fileAttachService.save(fileAttach);
					// 智维附件表操作end...
				}

			} catch (Exception e) {
				logger.error("上传合同报错:" + e.getMessage());
				e.printStackTrace();
				jsonStr = "{success : false}";
			}
			jsonStr = "{\"success\" : true,\"categoryId\" :\"" + contractId
					+ "\"}";
		} else {
			jsonStr = "{success : false}";
		}
		JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");
	}

	/** 上传合同(拟担保意向书，股东会决议，对外担保承诺函) */
	public void uploadContractOfOther() {
		HttpServletRequest request = getRequest();
		String realPath = "";
		String path = "";
		String filePath = "";
		String jsonStr = "";
		String shortName = "";
		String fileName = "";
		String webPath = "";
		String ttempId = request.getParameter("templateId");
		int templateId = Integer.parseInt(request.getParameter("templateId"));
		// int businessType =
		// Integer.parseInt(request.getParameter("businessType"));
		String businessType = request.getParameter("businessType");
		String projId = request.getParameter("projId");
		/*
		 * String htid = request.getParameter("conId"); String cconId =
		 * (htid==""||"".equals(htid)?"0":htid); int conId =
		 * Integer.parseInt(cconId);
		 */
		String contractId = request.getParameter("contractId");
		String htlxdName = request.getParameter("htlxdName");
		String htmcdName = request.getParameter("htmcdName");
		String htType = request.getParameter("htType");
		String thirdRalationId = request.getParameter("thirdRalationId");
		Long thirdRId = thirdRalationId.equals("") ? 0 : Long
				.parseLong(thirdRalationId);
		String isApply = request.getParameter("isApply");
		String clauseId = request.getParameter("clauseId");
		// DocumentTemplet tem =null;
		DocumentTemplet templet = null;
		File file = null;
		boolean flag = false;
		String extendName = htmcdName.substring(htmcdName.lastIndexOf("."),
				htmcdName.length());
		try {
			ProcreditContract p = null;
			if (null == contractId || contractId == "0"
					|| "0".equals(contractId) || "".equals(contractId)) {
				p = new ProcreditContract();
			} else {
				p = procreditContractService.getById(Integer
						.valueOf(contractId));
			}
			p.setContractCategoryTypeText(htlxdName);
			p.setContractCategoryText(htmcdName);
			if (!"".equals(htType)) {
				p.setHtType(htType);
			}
			if (!"".equals(clauseId) && clauseId != null) {
				p.setClauseId(Long.parseLong(clauseId));
			}
			p.setTemptId(templateId);
			p.setMortgageId(thirdRalationId.equals("")
					|| "0".equals(thirdRalationId) ? 0 : Integer
					.parseInt(thirdRalationId));
			p.setIsApply(isApply.equals("true") ? true : false);
			if (null == contractId || contractId == "0"
					|| "0".equals(contractId) || "".equals(contractId)) {
				contractId = procreditContractService.add(p, projId,
						businessType)
						+ "";
			} else {// 否则为修改操作
				if ("".equals(htlxdName) && !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
					Object[] obj = { htmcdName, templateId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && "".equals(htmcdName)) {// 正规操作情况下没有这种可能
					Object[] obj = { htlxdName, Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && !"".equals(htmcdName)) {
					Object[] obj = { htlxdName, htmcdName, templateId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
									obj);
				}

			}

			String searchRemark = this.getRequest()
					.getParameter("searchRemark");
			conId = procreditContractService.makeUpload(contractId, ttempId,
					"", contractId, projId, thirdRId, businessType, null,
					searchRemark);// 向合同表中添加数据

			ProcreditContract contract = procreditContractService
					.getById(conId);
			templet = documentTempletService.getById(templateId);
			// tem = elementHandleService.seeElement(templet.getParentid());
			SlSmallloanProject smallloanProject = null;
			GLGuaranteeloanProject gloanProject = null;
			FlFinancingProject floanProject = null;
			if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {
				smallloanProject = slSmallloanProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((smallloanProject.getOppositeType())
						.equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(Integer
							.parseInt(smallloanProject.getOppositeID()
									.toString()));
					shortName = enterp.getShortname().trim();
				} else if ((smallloanProject.getOppositeType())
						.equals("person_customer")) {
					Person person = personService.getById(Integer
							.parseInt(smallloanProject.getOppositeID()
									.toString()));
					shortName = person.getName().trim();
				}
			} else if (businessType == "Guarantee"
					|| "Guarantee".equals(businessType)) {
				gloanProject = gLGuaranteeloanProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((gloanProject.getOppositeType()).equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(Integer
							.parseInt(gloanProject.getOppositeID().toString()));
					shortName = enterp.getShortname().trim();
				} else if ((gloanProject.getOppositeType())
						.equals("person_customer")) {
					Person person = personService.getById(Integer
							.parseInt(gloanProject.getOppositeID().toString()));
					shortName = person.getName().trim();
				}
			} else if (businessType == "Financing"
					|| "Financing".equals(businessType)) {
				floanProject = flFinancingProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((floanProject.getOppositeType()).equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(floanProject
							.getOppositeID().intValue());
					shortName = enterp.getShortname().trim();
				} else if ((floanProject.getOppositeType()
						.equals("person_customer"))) {
					Person person = personService.getById(floanProject
							.getOppositeID().intValue());
					shortName = person.getName().trim();
				}
			}

			fileName = contract.getContractName();
			String fileCraName = "";
			if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {
				fileCraName = shortName + "_"
						+ smallloanProject.getProjectNumber();
			} else if (businessType == "Guarantee"
					|| "Guarantee".equals(businessType)) {
				fileCraName = shortName + "_" + gloanProject.getProjectNumber();
			} else if (businessType == "Financing"
					|| "Financing".equals(businessType)) {
				fileCraName = shortName + "_" + floanProject.getProjectNumber();
			}
			// Enterprise enter =
			// elementHandleService.findByEnterpriseId(Integer.parseInt(smallloanProject.getOppositeID().toString()));
			// String fileCraName =
			// enter.getShortname().trim()+"_"+smallloanProject.getProjectNumber();
			// path = "/projFile/contfolder/"+fileCraName;
			path = "/attachFiles/projFile/contfolder/" + fileCraName + "/"
					+ templet.getParentid() + "/";
			webPath = "attachFiles\\projFile\\contfolder\\" + fileCraName
					+ "\\" + templet.getParentid();
			filePath = "projFile/contfolder/" + fileCraName + "/"
					+ templet.getParentid() + "/" + fileName + extendName;
			contractId = contract.getId().toString();
			realPath = ServletActionContext.getServletContext().getRealPath(
					path);
			file = new File(realPath);
			if (!file.exists()) {
				// FileHelper.newFolder(realPath);
				file.mkdirs();
			}
			file = new File(realPath + "/" + fileName + extendName);
			flag = FileHelper.fileUpload(fileUpload, file,
					new byte[UPLOAD_SIZE]);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传合同报错:" + e.getMessage());
			jsonStr = "{success : false}";
		}
		if (flag) {
			try {
				// Object[] obj = {true ,realPath+"\\"+fileName+".doc" ,conId} ;
				ProcreditContract pc = procreditContractService.getById(conId);
				pc.setIsUpload(true);
				pc.setUrl(webPath + "\\" + fileName + extendName);
				procreditContractService.merge(pc);
				List<FileAttach> listfileAttach = fileAttachService
						.listByContractId(conId);
				if (listfileAttach != null) {
					String extkzm = fileUploadFileName.split("\\.").length > 0 ? fileUploadFileName
							.split("\\.")[1]
							: extendName.substring(1, extendName.length());
					for (FileAttach f : listfileAttach) {
						f.setFilePath(filePath);
						f.setExt(extkzm);
						fileAttachService.merge(f);
					}
				} else {
					// 智维附件表操作start...
					FileAttach fileAttach = new FileAttach();
					fileAttach.setFileName(fileName);
					fileAttach.setFilePath(filePath);
					fileAttach.setCreatetime(new Date());
					String extkzm = fileUploadFileName.split("\\.").length > 0 ? fileUploadFileName
							.split("\\.")[1]
							: extendName.substring(1, extendName.length());
					fileAttach.setExt(extkzm);
					fileAttach.setFileType("attachFiles/uploads");
					// fileAttach.setNote(filesize.toString());
					fileAttach.setCreatorId(Long.parseLong(ContextUtil
							.getCurrentUser().getId()));
					fileAttach.setCreator(ContextUtil.getCurrentUser()
							.getFullname());
					// fileAttach.setTotalBytes(Long.parseLong(filesize.toString())*1024);
					fileAttach.setDelFlag(0);
					fileAttach.setCsContractId(Integer.valueOf(contractId));
					fileAttachService.save(fileAttach);
					// 智维附件表操作end...
				}

			} catch (Exception e) {
				logger.error("上传合同报错:" + e.getMessage());
				e.printStackTrace();
				jsonStr = "{success : false}";
			}
			jsonStr = "{\"success\" : true,\"categoryId\" :\"" + contractId
					+ "\"}";
		} else {
			jsonStr = "{success : false}";
		}
		JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");
	}

	public void uploadLoanSettlementDocument() {
		String jsonStr = "";
		try {
			String mark_ = getRequest().getParameter("mark");
			String businessType_ = getRequest().getParameter("businessType");
			mark_ = new String(mark_.getBytes("iso-8859-1"), "utf-8");
			DocumentTemplet templet_ = documentTempletService
					.findDocumentTemplet(mark_);
			templet_ = documentTempletService.getTempletObj(templet_.getId());
			Integer templateId = templet_.getId();
			HttpServletRequest request = getRequest();
			String htlxdName = request.getParameter("htlxdName");
			String htmcdName = request.getParameter("htmcdName");
			String htType = request.getParameter("htType");
			String contractId = request.getParameter("contractId");
			String htid = request.getParameter("conId");
			String cconId = (htid == "" || "".equals(htid) ? "0" : htid);
			int conId = Integer.parseInt(cconId);
			String thirdRalationId = request.getParameter("thirdRalationId");
			Long thirdRId = thirdRalationId.equals("") ? 0 : Long
					.parseLong(thirdRalationId);
			String extendName = htmcdName.substring(htmcdName.lastIndexOf("."),
					htmcdName.length());
			ProcreditContract p = null;
			if (null == contractId || contractId == "0"
					|| "0".equals(contractId) || "".equals(contractId)) {
				p = new ProcreditContract();
			} else {
				p = procreditContractService.getById(Integer
						.valueOf(contractId));
			}
			p.setContractCategoryTypeText(htlxdName);
			p.setContractCategoryText(htmcdName);
			p.setTemptId(templateId);

			if (!"".equals(htType)) {
				p.setHtType(htType);
			}
			if (null == contractId || contractId == "0"
					|| "0".equals(contractId) || "".equals(contractId)) {
				contractId = procreditContractService.add(p, projId,
						businessType)
						+ "";
			} else {// 否则为修改操作
				if ("".equals(htlxdName) && !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
					Object[] obj = { htmcdName, templateId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && "".equals(htmcdName)) {// 正规操作情况下没有这种可能
					Object[] obj = { htlxdName, Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=? where id =?",
									obj);
				} else if (!"".equals(htlxdName) && !"".equals(htmcdName)) {
					Object[] obj = { htlxdName, htmcdName, templateId,
							Integer.parseInt(contractId) };
					elementHandleService
							.updateCon(
									"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
									obj);
				}

			}
			// 合同编号
			// String rnum = elementHandleService.getNumber(projId,
			// templateId.toString());
			// 向合同表中增加或修改数据数据（由相应的合同id是否存在）
			String searchRemark = this.getRequest()
					.getParameter("searchRemark");
			conId = procreditContractService.makeUpload(cconId, templateId
					.toString(), "", contractId, projId, thirdRId,
					businessType, null, searchRemark);// 向合同表中添加数据

			ProcreditContract contract = procreditContractService
					.getById(conId);
			DocumentTemplet templet = documentTempletService
					.getById(templateId);
			// tem = elementHandleService.seeElement(templet.getParentid());
			SlSmallloanProject smallloanProject = null;
			GLGuaranteeloanProject gloanProject = null;
			FlFinancingProject floanProject = null;
			FlLeaseFinanceProject flLeaseFinanceProject = null;
			PlPawnProject plPawnProject = null;
			String shortName = "";
			if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {
				smallloanProject = slSmallloanProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((smallloanProject.getOppositeType())
						.equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(Integer
							.parseInt(smallloanProject.getOppositeID()
									.toString()));
					shortName = enterp.getShortname().trim();
				} else if ((smallloanProject.getOppositeType())
						.equals("person_customer")) {
					Person person = personService.getById(Integer
							.parseInt(smallloanProject.getOppositeID()
									.toString()));
					shortName = person.getName().trim();
				}
			} else if (businessType == "Guarantee"
					|| "Guarantee".equals(businessType)) {
				gloanProject = gLGuaranteeloanProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((gloanProject.getOppositeType()).equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(Integer
							.parseInt(gloanProject.getOppositeID().toString()));
					shortName = enterp.getShortname().trim();
				} else if ((gloanProject.getOppositeType())
						.equals("person_customer")) {
					Person person = personService.getById(Integer
							.parseInt(gloanProject.getOppositeID().toString()));
					shortName = person.getName().trim();
				}
			} else if (businessType == "Financing"
					|| "Financing".equals(businessType)) {
				floanProject = flFinancingProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((floanProject.getOppositeType()).equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(floanProject
							.getOppositeID().intValue());
					shortName = enterp.getShortname().trim();
				} else if ((floanProject.getOppositeType()
						.equals("person_customer"))) {
					Person person = personService.getById(floanProject
							.getOppositeID().intValue());
					shortName = person.getName().trim();
				}
			} else if ("LeaseFinance".equals(businessType)) {
				flLeaseFinanceProject = flLeaseFinanceProjectService.get(Long
						.valueOf(contract.getProjId()));
				if ((flLeaseFinanceProject.getOppositeType())
						.equals("company_customer")) {
					Enterprise enterp = enterpriseService
							.getById(flLeaseFinanceProject.getOppositeID()
									.intValue());
					shortName = enterp.getShortname().trim();
				} else if ((flLeaseFinanceProject.getOppositeType()
						.equals("person_customer"))) {
					Person person = personService.getById(flLeaseFinanceProject
							.getOppositeID().intValue());
					shortName = person.getName().trim();
				}
			} else if ("Pawn".equals(businessType)) {
				plPawnProject = elementHandleService
						.findByPawnProjectId(contract.getProjId());
				if ((plPawnProject.getOppositeType())
						.equals("company_customer")) {
					Enterprise enterp = enterpriseService.getById(plPawnProject
							.getOppositeID().intValue());
					shortName = enterp.getShortname().trim();
				} else if ((plPawnProject.getOppositeType()
						.equals("person_customer"))) {
					Person person = personService.getById(plPawnProject
							.getOppositeID().intValue());
					shortName = person.getName().trim();
				}
			}

			String fileName = contract.getContractName();
			String fileCraName = "";
			if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {
				fileCraName = shortName + "_"
						+ smallloanProject.getProjectNumber();
			} else if (businessType == "Guarantee"
					|| "Guarantee".equals(businessType)) {
				fileCraName = shortName + "_" + gloanProject.getProjectNumber();
			} else if (businessType == "Financing"
					|| "Financing".equals(businessType)) {
				fileCraName = shortName + "_" + floanProject.getProjectNumber();
			} else if ("LeaseFinance".equals(businessType)) {
				fileCraName = shortName + "_"
						+ flLeaseFinanceProject.getProjectNumber();
			} else if ("Pawn".equals(businessType)) {
				fileCraName = shortName + "_"
						+ plPawnProject.getProjectNumber();
			}
			// Enterprise enter =
			// elementHandleService.findByEnterpriseId(Integer.parseInt(smallloanProject.getOppositeID().toString()));
			// String fileCraName =
			// enter.getShortname().trim()+"_"+smallloanProject.getProjectNumber();
			// path = "/projFile/contfolder/"+fileCraName;
			path = "/attachFiles/projFile/contfolder/" + fileCraName + "/"
					+ templet.getParentid() + "/";
			String webPath = "attachFiles\\projFile\\contfolder\\"
					+ fileCraName + "\\" + templet.getParentid();
			String filePath = "projFile/contfolder/" + fileCraName + "/"
					+ templet.getParentid() + "/" + fileName + extendName;
			Integer contId = contract.getId();
			String realPath = ServletActionContext.getServletContext()
					.getRealPath(path);
			File file = new File(realPath);
			if (!file.exists()) {
				// FileHelper.newFolder(realPath);
				file.mkdirs();
			}
			file = new File(realPath + "/" + fileName + extendName);
			Boolean flag = FileHelper.fileUpload(fileUpload, file,
					new byte[UPLOAD_SIZE]);

			if (flag) {
				try {
					// Object[] obj = {true ,realPath+"\\"+fileName+".doc"
					// ,conId} ;
					ProcreditContract pc = procreditContractService
							.getById(conId);
					pc.setIsUpload(true);
					pc.setUrl(webPath + "\\" + fileName + extendName);
					procreditContractService.merge(pc);
					List<FileAttach> listfileAttach = fileAttachService
							.listByContractId(conId);
					if (listfileAttach != null) {
						String extkzm = fileUploadFileName.split("\\.").length > 0 ? fileUploadFileName
								.split("\\.")[1]
								: extendName.substring(1, extendName.length());
						for (FileAttach f : listfileAttach) {
							f.setFilePath(filePath);
							f.setExt(extkzm);
							fileAttachService.merge(f);
						}
					} else {
						// 智维附件表操作start...
						FileAttach fileAttach = new FileAttach();
						fileAttach.setFileName(fileName);
						fileAttach.setFilePath(filePath);
						fileAttach.setCreatetime(new Date());
						String extkzm = fileUploadFileName.split("\\.").length > 0 ? fileUploadFileName
								.split("\\.")[1]
								: extendName.substring(1, extendName.length());
						fileAttach.setExt(extkzm);
						fileAttach.setFileType("attachFiles/uploads");
						// fileAttach.setNote(filesize.toString());
						fileAttach.setCreatorId(Long.parseLong(ContextUtil
								.getCurrentUser().getId()));
						fileAttach.setCreator(ContextUtil.getCurrentUser()
								.getFullname());
						// fileAttach.setTotalBytes(Long.parseLong(filesize.toString())*1024);
						fileAttach.setDelFlag(0);
						fileAttach.setCsContractId(contId);
						fileAttachService.save(fileAttach);
						// 智维附件表操作end...
					}

				} catch (Exception e) {
					logger.error("上传合同报错:" + e.getMessage());
					e.printStackTrace();
					jsonStr = "{success : false}";
				}
				jsonStr = "{\"success\" : true,\"categoryId\" :\"" + contractId
						+ "\"}";
			} else {
				jsonStr = "{success : false}";
			}
			JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传合同报错:" + e.getMessage());
			jsonStr = "{success : false}";
		}

	}

	/** 删除合同 */
	public void deleteContract() {
		String jsonStr = "";
		try {
			ProcreditContract contract = procreditContractService
					.getById(conId);
			String path = super.getRequest().getRealPath("/")
					+ contract.getUrl();
			boolean flag = FileHelper.deleteFile(path);
			if (flag) {
				Object[] obj = { false, "", conId };
				elementHandleService
						.updateCon(
								"update ProcreditContract set isUpload=?, url=? where id =?",
								obj);
				jsonStr = "{success : true}";
			} else {
				jsonStr = "{success : false}";
			}
		} catch (Exception e) {
			logger.error("删除合同报错:" + e.getMessage());
			e.printStackTrace();
			jsonStr = "{success : false}";
		}
		JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");
	}

	/** 删除增加文件上传的合同 */
	public void deleteContractFile() {
		String jsonStr = "";
		try {
			FileForm fileForm = fileFormService.findByMark(mark);
			String path = super.getRequest().getRealPath("/")
					+ fileForm.getFilepath();
			boolean flag = FileHelper.deleteFile(path);
			if (flag) {
				fileFormService.remove(fileForm);
				ProcreditContract contract = procreditContractService
						.getById(conId);
				if (null != contract) {
					procreditContractService.remove(contract);
				}
				jsonStr = "{success : true}";
			} else {
				jsonStr = "{success : false}";
			}
		} catch (Exception e) {
			logger.error("删除合同附件出错:" + e.getMessage());
			e.printStackTrace();
			jsonStr = "{success : false}";
		}
		JsonUtil.responseJsonString(jsonStr, "text/html; charset=utf-8");
	}

	/** 查看上传合同 */
	public void lookUploadContract() {
		String htmlPath = "";
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		try {
			int conId = Integer.parseInt(request.getParameter("conId"));
			ProcreditContract contract = procreditContractService
					.getById(conId);
			ElementUtil.downloadFile(super.getRequest().getRealPath("/")
					+ contract.getUrl(), response);
		} catch (Exception e) {
			logger.error("查看上传合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil
					.responseJsonString("{success:false,exsit:false,msg :'系统错误'}");
		}
	}

	// 贷款逾期催收通知书
	public void download() {

		String htmlPath = "";
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		try {
			int categoryId = Integer.parseInt(request
					.getParameter("categoryId"));
			VProcreditContract contractCategory = vProcreditContractService
					.getById(categoryId);
			ElementUtil.downloadFile(super.getRequest().getRealPath("/")
					+ contractCategory.getUrl(), response);
		} catch (Exception e) {
			logger.error("查看上传合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil
					.responseJsonString("{success:false,exsit:false,msg :'系统错误'}");
		}

	}

	/** 查看增加的文件上传的上传合同 add by chencc */
	public void lookUploadContractFile() {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		try {
			String onlymark = request.getParameter("onlymark");
			FileForm fileForm = fileFormService.findByMark(onlymark);
			ElementUtil.downloadFile(super.getRequest().getRealPath("/")
					+ fileForm.getFilepath(), response);
		} catch (Exception e) {
			logger.error("查看增加的文件上传的上传合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil
					.responseJsonString("{success:false,exsit:false,msg :'系统错误'}");
		}
	}

	/** 删除垃圾文件 */
	public void cleanFile() {
		FileHelper.deleteFile(path + ".htm");
		File file = new File(path + ".files");
		FileHelper.deleteDir(file);
	}

	/** 打印word合同 */
	public void PrintOutContract() {
		try {
			ProcreditContract contract = procreditContractService
					.getById(conId);
			JacobWord.getInstance().printFile(
					super.getRequest().getRealPath("/") + contract.getUrl());
		} catch (Exception e) {
			logger.error("打印出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void setAssureIntentBookElementCode(
			AssureIntentBookElementCode assureIntentBookElementCode) {
		this.assureIntentBookElementCode = assureIntentBookElementCode;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public void validationFileIsExist() {
		try {
			DocumentTemplet templet = documentTempletService
					.getById(documentId);
			if (null == templet) {
				JsonUtil
						.responseJsonString("{success:true,exsit:false,msg :'合同模板没有上传，请先上传模板'}");
				return;
			} else {
				FileForm fileForm = fileFormService
						.getById(templet.getFileid());
				if (null != fileForm) {
					String path = super.getRequest().getRealPath("/")
							+ fileForm.getFilepath();
					File file = new File(path);
					if (file.exists()) {
						JsonUtil
								.responseJsonString("{success:true,exsit:true}");
					} else
						JsonUtil
								.responseJsonString("{success:true,exsit:false,msg :'合同模板没有上传，请先上传模板'}");
				} else {
					// 未找到下载的文档 by chencc
					JsonUtil
							.responseJsonString("{success:true,exsit:false,msg :'合同模板没有上传，请先上传模板'}");
				}
			}
		} catch (Exception e) {
			logger.error("验证模板是否存在出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	// 上传客户承诺函需要的合同id
	public void ajaxGetContractIdForUpLoadFile() {

		try {
			DocumentTemplet templet = documentTempletService
					.findDocumentTemplet(mark);
			templet = documentTempletService.getTempletObj(templet.getId());
			elementHandleService
					.ajaxGetContractIdForUpLoadFile(projId, templet);
		} catch (Exception e) {
			logger.error("上传客户承诺函需要的合同出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateProcreditContractById() {
		elementHandleService.updateProcreditContractById(procreditContract);
	}

	// 下载在线编辑软件
	public void downloadNtko() {
		HttpServletResponse response = getResponse();
		String ntkoPath = super.getRequest().getRealPath("/")
				+ "soft\\NtkoControlSetup.msi";
		ElementUtil.downloadFile(ntkoPath, response);
	}

	public void makeIntentUrge() {// ●№○还款催收
		String serverPath = super.getRequest().getRealPath("/");//
		DocumentTemplet templet = null;
		ProcreditContract contract = null;
		String path = "", fileName = "", shortName = "";
		int bytesum = 0, byteread = 0;
		HttpServletResponse response = getResponse();
		HttpServletRequest requrst = getRequest();
		String fundIntentId = requrst.getParameter("fundIntentId");
		int tempId = Integer.parseInt(requrst.getParameter("templateId"));
		// int conId =
		// Integer.parseInt(requrst.getParameter("contractId")!=null||requrst.getParameter("contractId")!=""||!"".equals(requrst.getParameter("contractId"))?requrst.getParameter("contractId"):"0");
		String ttempId = requrst.getParameter("templateId");
		String htid = requrst.getParameter("contractId");
		String cconId = (htid != "" ? htid : "0");
		int conId = Integer.parseInt(cconId);
		String categoryId = requrst.getParameter("categoryId");
		String cType = requrst.getParameter("contractType");
		// int businessType
		// =Integer.parseInt(requrst.getParameter("businessType"));
		String businessType = requrst.getParameter("businessType");
		String clauseId = requrst.getParameter("clauseId");
		// DocumentTemplet tem = null;
		SlSmallloanProject smallloanProject = null;
		Enterprise enterprise = null;
		String projectNumber = "";

		try {
			// 查询对应的模板文件是否上传
			templet = documentTempletService.getById(tempId);
			if (ttempId != null && templet != null) {
				DocumentTemplet dtemplet = documentTempletService
						.getById(tempId);
				if (dtemplet != null) {
					if (dtemplet.getIsexist() == true
							&& dtemplet.getFileid() != 0) {
						FileForm fileF = fileFormService.getById(dtemplet
								.getFileid());
						if (fileF != null) {
							File file = new File(serverPath
									+ fileF.getFilepath());
							if (!file.exists()) {
								JsonUtil.responseJsonFailure();
								return;
							}
						} else {
							JsonUtil.responseJsonFailure();
							return;
						}
					} else {
						JsonUtil.responseJsonFailure();
						return;
					}
				} else {
					JsonUtil.responseJsonFailure();
					return;
				}
			} else {
				JsonUtil.responseJsonFailure();
				return;
			}
			// 如果模板文件已经上传

			if (templet == null) {
				JsonUtil
						.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
				return;
			} else {
				SlSmallloanProject sloanProject = null;
				GLGuaranteeloanProject gloanProject = null;
				FlFinancingProject floanProject = null;
				// tem = elementHandleService.seeElement(templet.getParentid());
				ProcreditContract pcontract = procreditContractService
						.getById(conId);
				// 获得企业简称或个人的名字，由项目的客户类型决定
				if (businessType == "SmallLoan"
						|| "SmallLoan".equals(businessType)) {// 小贷流程
					sloanProject = slSmallloanProjectService.get(Long
							.valueOf(pcontract.getProjId()));
					projectNumber = sloanProject.getProjectNumber();
					if ((sloanProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((sloanProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				}

			}
			// 生成合同和合同文件的名字
			if (conId != 0) {
				if (null == fileName) {
					fileName = shortName + "-" + projectNumber + "-";
				} else {
					fileName = shortName + "-" + projectNumber + "-";
				}
			} else {
				fileName = shortName + "-" + projectNumber + "-";
			}
			FileForm fileForm = fileFormService.getById(templet.getFileid());
			// path =
			// ElementUtil.getUrl(fileForm.getFilepath(),fileName+".doc");
			path = ElementUtil.getUrl(serverPath + fileForm.getFilepath(),
					fileName + ".doc");

			/*
			 * String fileCraName = "";
			 * if(businessType=="SmallLoan"||"SmallLoan".equals(businessType)){
			 * fileCraName = shortName+"_"+sloanProject.getProjectNumber();
			 * }else
			 * if(businessType=="Guarantee"||"Guarantee".equals(businessType)){
			 * fileCraName = shortName+"_"+gloanProject.getProjectNumber();
			 * }else if(businessType == "Financing" ||
			 * "Financing".equals(businessType)){ fileCraName =
			 * shortName+"_"+floanProject.getProjectName(); }
			 */
			String fileCraName = shortName + "_" + projectNumber;
			// path = "/projFile/contfolder/"+fileCraName;
			// String copyDir =
			// super.getRequest().getRealPath("/")+"attachFiles\\projFile\\contfolder\\"+fileCraName+"\\"+templet.getParentid()+"\\";
			String copyDir = serverPath + "attachFiles\\projFile\\contfolder\\"
					+ fileCraName + "\\" + templet.getParentid() + "\\";
			File cFile = new File(copyDir);
			if (!cFile.exists()) {
				// FileHelper.newFolder(copyDir);
				cFile.mkdirs();
			}

			String newPath = (copyDir + fileName + ".doc").trim();

			// 写模板
			if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {// 小贷
				// ElementCode elementCode = new ElementCode();
				SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
				SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
						.getInterntElementBySystem(fundIntentId, "1");
				File file = new File(path);
				if (file.exists()) {
					// 这个地方是否得判断一下是否有要素???????

					JacobWord
							.getInstance()
							.replaceAllText(
									path,
									ElementUtil
											.findEleCodeArray(smallLoanElementCode),
									ElementUtil
											.findEleCodeValueArray(smallLoanElementCodeValue));
					FileHelper.copyFile(path, newPath);
					FileHelper.deleteFile(path);
				} else
					return;
			}

			// 向实体添加数据，返回给客户端
			// p.setId(Integer.parseInt(categoryId));
			// JsonUtil.jsonFromObject(p, true);
		} catch (Exception e) {
			JsonUtil.responseJsonFailure();
			logger.error("生成合同报错:" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 新增合同生成方法 -- add by zcb 2013-10-08
	 */
	@SuppressWarnings("static-access")
	public void createContract() {
		String serverPath = super.getRequest().getRealPath("/");
		DocumentTemplet templet = null;
		String path = "", fileName = "", shortName = "";
		int bytesum = 0, byteread = 0;
		HttpServletResponse response = getResponse();
		HttpServletRequest requrst = getRequest();
		String projId = requrst.getParameter("projId");
		int tempId = Integer.parseInt(requrst.getParameter("templateId"));
		String ttempId = requrst.getParameter("templateId");
		String htid = requrst.getParameter("contractId");
		String cconId = (htid != "" ? htid : "0");
		int conId = Integer.parseInt(cconId);
		String contractId = requrst.getParameter("contractId");
		String cType = requrst.getParameter("contractType");
		String businessType = requrst.getParameter("businessType");
		String clauseId = requrst.getParameter("clauseId");
		String bidPlanId = requrst.getParameter("bidPlanId");
		SlSmallloanProject smallloanProject = null;
		Enterprise enterprise = null;
		String projectNumber = "";
		String vDatas = requrst.getParameter("vSysDatas");// 自定义要素

		try {
			// 查询对应的模板文件是否上传
			templet = documentTempletService.getById(tempId);
			if (ttempId != null && templet != null) {
				DocumentTemplet dtemplet = documentTempletService
						.getById(tempId);
				if (dtemplet != null) {
					if (dtemplet.getIsexist() == true
							&& dtemplet.getFileid() != 0) {
						FileForm fileF = fileFormService.getById(dtemplet
								.getFileid());
						if (fileF != null) {
							File file = new File(serverPath
									+ fileF.getFilepath());
							if (!file.exists()) {
								JsonUtil.responseJsonFailure();
								return;
							}
						} else {
							JsonUtil.responseJsonFailure();
							return;
						}
					} else {
						JsonUtil.responseJsonFailure();
						return;
					}
				} else {
					JsonUtil.responseJsonFailure();
					return;
				}
			} else {
				JsonUtil.responseJsonFailure();
				return;
			}
			// 如果模板文件已经上传
			// 向ProcreditContractCategory表中增加或修改数据
			String bType = businessType;
			ProcreditContract p = procreditContractService.getById(Integer
					.parseInt(contractId));

			String searchRemark = this.getRequest()
					.getParameter("searchRemark");
			String contractNumber = this.getRequest().getParameter(
					"contractNumber");
			if (!"".equals(vDatas)) {
				String[] data = vDatas.split("!");
				CreditBaseDao creditBaseDao = (CreditBaseDao) AppUtil
						.getBean("creditBaseDao");
				if (null == p.getContractName()) {
					for (int i = 0; i < data.length; i++) {
						// 判断合同是否已存在
						String[] str = data[i].split("@");
						ContractElement cElement = new ContractElement();
						cElement.setElementName(str[1]);
						cElement.setElementValue(str[2]);
						cElement.setContractId(Integer.parseInt(contractId));
						cElement.setAddDate(new Date());
						creditBaseDao.saveDatas(cElement);
					}
				} else {
					List<ContractElement> list2 = vProcreditContractService
							.getListByCategoryId(Integer.parseInt(contractId));
					if (null != list2) {
						for (int k = 0; k < list2.size(); k++) {
							ContractElement cElement = (ContractElement) list2
									.get(k);
							String[] str = data[k].split("@");
							cElement.setElementName(str[1]);
							cElement.setElementValue(str[2]);
							cElement
									.setContractId(Integer.parseInt(contractId));
							cElement.setAddDate(new Date());
							creditBaseDao.updateDatas(cElement);
						}
					} else {
						for (int i = 0; i < data.length; i++) {
							// 判断合同是否已存在
							String[] str = data[i].split("@");
							ContractElement cElement = new ContractElement();
							cElement.setElementName(str[1]);
							cElement.setElementValue(str[2]);
							cElement
									.setContractId(Integer.parseInt(contractId));
							cElement.setAddDate(new Date());
							creditBaseDao.saveDatas(cElement);
						}
					}
				}
			}
			conId = procreditContractService.makeUpload(cconId, ttempId, cType,
					null, projId, thirdRalationId, businessType, null,
					searchRemark, contractNumber);// 向合同表中添加数据
			if (templet == null) {
				JsonUtil
						.responseJsonString("{success:true,exsit:false,msg :'系统错误'}");
				return;
			} else {
				BpFundProject sloanProject = null;
				SlSmallloanProject sproject=null;
				GLGuaranteeloanProject gloanProject = null;
				FlFinancingProject floanProject = null;
				PlPawnProject pawnProject = null;
				FlLeaseFinanceProject flLeaseFinance = null;
				ProcreditContract pcontract = procreditContractService
						.getById(conId);
				// 获得企业简称或个人的名字，由项目的客户类型决定
				if (businessType == "SmallLoan" || "SmallLoan".equals(businessType)) {// 小贷流程
					sloanProject = bpFundProjectService.get(Long.valueOf(pcontract.getProjId()));
					sproject=slSmallloanProjectService.get(sloanProject.getProjectId());
					projectNumber = sloanProject.getProjectNumber();
					if ((sloanProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(sloanProject.getOppositeID().toString()));
						shortName = enterp.getShortname().trim();
					} else if ((sloanProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "Guarantee"
						|| "Guarantee".equals(businessType)) {
					gloanProject = gLGuaranteeloanProjectService.get(Long
							.valueOf(pcontract.getProjId()));
					projectNumber = gloanProject.getProjectNumber();
					if ((gloanProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(gloanProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((gloanProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(gloanProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "Financing"
						|| "Financing".equals(businessType)) {// 融资流程
					floanProject = flFinancingProjectService.get(Long
							.valueOf(pcontract.getProjId()));
					projectNumber = floanProject.getProjectNumber();
					if ((floanProject.getOppositeType())
							.equals("company_customer")) {
						Enterprise enterp = enterpriseService
								.getById(floanProject.getOppositeID()
										.intValue());
						shortName = enterp.getShortname().trim();
					} else if ((floanProject.getOppositeType())
							.equals("person_customer")) {
						Person person = personService.getById(floanProject
								.getOppositeID().intValue());
						shortName = person.getName().trim();
					}
				} else if (businessType == "ExhibitionBusiness"
						|| "ExhibitionBusiness".equals(businessType)) {// 展期流程
					SlSuperviseRecord slSuperviseRecord = (SlSuperviseRecord) creditBaseDao
							.getById(SlSuperviseRecord.class, Long
									.valueOf(pcontract.getProjId()));
					sloanProject = bpFundProjectService
							.get(slSuperviseRecord.getProjectId());
					projectNumber = sloanProject.getProjectNumber();
					if ((sloanProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((sloanProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(sloanProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "Pawn"
						|| "Pawn".equals(businessType)) {// 小贷流程
					pawnProject = (PlPawnProject) creditBaseDao.getById(
							PlPawnProject.class, Long.valueOf(pcontract
									.getProjId()));
					projectNumber = pawnProject.getProjectNumber();
					if ((pawnProject.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(pawnProject.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((pawnProject.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(pawnProject.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				} else if (businessType == "LeaseFinance"
						|| "LeaseFinance".equals(businessType)) {// 租赁流程
					flLeaseFinance = (FlLeaseFinanceProject) creditBaseDao
							.getById(FlLeaseFinanceProject.class, Long
									.valueOf(pcontract.getProjId()));
					projectNumber = flLeaseFinance.getProjectNumber();
					if ((flLeaseFinance.getOppositeType())
							.equals("company_customer")) {// 客户类型为企业
						Enterprise enterp = enterpriseService.getById(Integer
								.parseInt(flLeaseFinance.getOppositeID()
										.toString()));
						shortName = enterp.getShortname().trim();
					} else if ((flLeaseFinance.getOppositeType())
							.equals("person_customer")) {// 客户类型为个人
						Person person = personService.getById(Integer
								.parseInt(flLeaseFinance.getOppositeID()
										.toString()));
						shortName = person.getName().trim();
					}
				}
				// 生成合同和合同文件的名字
				if (conId != 0) {
					fileName = pcontract.getContractName();
					if (null == fileName) {
						fileName = shortName + "-" + projectNumber + "-"
								+ templet.getText()/* +"-"+rnum */;
					} else {
						fileName = shortName + "-" + projectNumber + "-"
								+ templet.getText()/* +"-"+rnum */;
					}
				} else {
					fileName = shortName + "-" + projectNumber + "-"
							+ templet.getText() /*+"-"+rnum */;
				}
				
				FileForm fileForm = fileFormService.getById(templet.getFileid());
			
				String fileCraName = shortName + "_" + projectNumber;
				if(null!=bidPlanId&&!"".equals(bidPlanId)&&!"undefined".equals(bidPlanId)){
					PlBidPlan plan=plBidPlanService.get(Long.valueOf(bidPlanId));
					BpCustRelation re=bpCustRelationService.getByLoanTypeAndId(sloanProject.getOppositeType().equals("person_customer")?"p_loan":"b_loan", sloanProject.getOppositeID());
					if(null!=re){
						BpCustMember member=bpCustMemberService.get(re.getP2pCustId());
						fileName=plan.getBidProNumber()+"_"+member.getLoginname()+"_"+pcontract.getContractNumber();
						fileCraName=plan.getBidProNumber()+"_"+member.getLoginname();
					}
				}
				path = ElementUtil.getUrl(serverPath + fileForm.getFilepath(),fileName + ".doc");
				String copyDir = serverPath
						+ "attachFiles\\projFile\\contfolder\\" + fileCraName
						+ "\\" + templet.getParentid() + "\\";
				
				PlBidPlan	plBidPlan=new PlBidPlan();
				String commonUrlpath="";
				if(null!=bidPlanId&&!"".equals(bidPlanId)&&!"undefined".equals(bidPlanId)){
					plBidPlan=plBidPlanService.get(Long.valueOf(bidPlanId));
					commonUrlpath=	"attachFiles\\projFile\\contfolder\\" + fileCraName+"\\"+plBidPlan.getBidProNumber()+"_"+plBidPlan.getBidId()
					+ "\\" + templet.getParentid() + "\\";
                       fileCraName=fileCraName+"\\"+plBidPlan.getBidProNumber()+"_"+plBidPlan.getBidId();
					copyDir = serverPath+ commonUrlpath;
				}
	
				
				File cFile = new File(copyDir);
				if (!cFile.exists()) {
					cFile.mkdirs();
				}
				String newPath = (copyDir + fileName + ".doc").trim();
				String filePath = ("projFile/contfolder/" + fileCraName + "/"
						+ templet.getParentid() + "/" + fileName + ".doc")
						.trim();
				Object[] obj = { true, "attachFiles/" + filePath, conId };
				elementHandleService
						.updateCon(
								"update ProcreditContract set isUpload=?, url=? where id =?",
								obj);
				ProcreditContract pc = procreditContractService.getById(conId);
				pc.setIsUpload(true);
				pc.setUrl("attachFiles/" + filePath);
				pc.setFileCount("0");
				procreditContractService.merge(pc);
				List<FileAttach> listfileAttach = fileAttachService
						.listByContractId(conId);
				if (listfileAttach != null) {
					for (FileAttach f : listfileAttach) {
						f.setFilePath(filePath);
						fileAttachService.merge(f);
					}
				} else {
					// 智维附件表操作start...为的是可以在线编辑
					FileAttach fileAttach = new FileAttach();
					fileAttach.setFileName(fileName);
					fileAttach.setFilePath(filePath);
					fileAttach.setCreatetime(new Date());
					fileAttach.setExt("doc");
					fileAttach.setFileType("attachFiles/uploads");
					fileAttach.setCreatorId(Long.parseLong(ContextUtil
							.getCurrentUser().getId()));
					fileAttach.setCreator(ContextUtil.getCurrentUser()
							.getFullname());
					fileAttach.setDelFlag(0);
					fileAttach.setCsContractId(conId);
					fileAttachService.save(fileAttach);
					// 智维附件表操作end...
				}
				String sysType = configMap.get("flowType").toString();
				if (businessType == "SmallLoan"
						|| "SmallLoan".equals(businessType)) {// 小贷
					// ElementCode elementCode = new ElementCode();//
					//获取到bidPlanId
					SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
					SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
								.getElementBySystem(projId, Integer
										.parseInt(contractId), tempId, htType,
										bidPlanId, null);
						/*String sysType = configMap.get("flowType").toString();
						System.out.println(sysType);*/
						if(sysType!=null){
							if(!sysType.equals("dltcCreditFlow")){
								List<VProcreditContract> vpcclist= vProcreditContractDao.getList(projId, "SmallLoan", "('loanContract')");
								if(vpcclist!=null&&vpcclist.size()!=0){
									VProcreditContract contract = vpcclist.get(0);
									smallLoanElementCodeValue.setJkhtbh_v(contract.getContractNumber());
								}
							}
						}
					
						File file = new File(path);
						if (file.exists()) {
							// 这个地方是否得判断一下是否有要素???????
							if(null!=bidPlanId&&!"".equals(bidPlanId)&&!"undefined".equals(bidPlanId)){
								List<InvestPersonInfo> plist=investPersonInfoService.getByBidPlanId(Long.valueOf(bidPlanId));
								String personName="";
								
							//借款人还款计划表
								List<BpFundIntent> list1=new ArrayList<BpFundIntent>();
								if(plist!=null && plist.size()!=0){
								
									int i=1;
									for(InvestPersonInfo person:plist){
										//投资人注册号
										personName=personName+person.getInvestPersonName()+",";
										List<BpFundIntent> filist = new ArrayList<BpFundIntent>();
										
										
										filist=bpFundIntentService.listByOrderNo(Long.valueOf(bidPlanId), person.getOrderNo());
										//投资人应收本息
										BigDecimal money=new BigDecimal(0);
										for(BpFundIntent fi:filist){
											if(null!=fi.getIncomeMoney()){
												   money=money.add(fi.getIncomeMoney());
											   }
										}
										 person.setPrincipalAndInterest(money);
										 //借款人的款项计划表
									 /*  if(i==1){
										   
										   list1.addAll(filist);
										   
									   }else{
										   
										   for(int j=0;j<list1.size();j++){
											   
											   bpFundIntentService.evict(list1.get(j));
											   
											   if(list1.get(j).getIncomeMoney().compareTo(new BigDecimal("0"))==1){
												   list1.get(j).setIncomeMoney( list1.get(j).getIncomeMoney().add(filist.get(j).getIncomeMoney()));
												   list1.get(j).setNotMoney( list1.get(j).getIncomeMoney());
											   }else{
												   list1.get(j).setPayMoney( list1.get(j).getPayMoney().add(filist.get(j).getPayMoney()));
												   list1.get(j).setNotMoney( list1.get(j).getPayMoney());
											   }
											   list1.get(j).setFundTypeName(dictionaryIndependentService.getByDicKey(list1.get(j).getFundType()).get(0).getItemValue());
										   }
										  
										   
									   }
										
										
										i++;*/
										
										
								
									}
								}
								if(!personName.equals("")){
									personName=personName.substring(0,personName.length()-1);
								}
								smallLoanElementCodeValue.setMgbtzrzch_v(personName);
								JacobWord.getInstance().replaceAllText(path,ElementUtil.findEleCodeArray(smallLoanElementCode),ElementUtil.findEleCodeValueArray(smallLoanElementCodeValue),null,plist,list1);
								FileHelper.copyFile(path, newPath);
								FileHelper.deleteFile(path);
								
								ProcreditContract contract=procreditContractService.getById(conId);
								WordtoPdfUtil pdfUtil = new WordtoPdfUtil();
								ServletContext context=ServletActionContext.getServletContext();
								pdfUtil.wordToPdf(context.getRealPath("/")+contract.getUrl(), "");
								contract.setUrl(contract.getUrl().split(".doc")[0]+".pdf");
								contract.setBidPlanId(Long.valueOf(bidPlanId));
								
								procreditContractService.save(contract);
							//	ptp
							}else{
								JacobWord.getInstance().replaceAllText(path,ElementUtil.findEleCodeArray(smallLoanElementCode),ElementUtil.findEleCodeValueArray(smallLoanElementCodeValue));
								FileHelper.copyFile(path, newPath);
								FileHelper.deleteFile(path);
								
								ProcreditContract contract=procreditContractService.getById(conId);
								WordtoPdfUtil pdfUtil = new WordtoPdfUtil();
								ServletContext context=ServletActionContext.getServletContext();
								pdfUtil.wordToPdf(context.getRealPath("/")+contract.getUrl(), "");
								contract.setUrl(contract.getUrl().split(".doc")[0]+".pdf");
								
								procreditContractService.save(contract);
							}
							
						} else
							return;
					
				} else if (businessType == "Guarantee"
						|| "Guarantee".equals(businessType)) {
					GuaranteeElementCode guaranteeElementCode = new GuaranteeElementCode();
					GuaranteeElementCode guaranteeElementCodeValue = elementHandleService
							.getGuaranteeElementBySystem(projId, Integer
									.parseInt(contractId), tempId, htType, null);
					File file = new File(path);
					if (file.exists()) {
						JacobWord.getInstance().replaceAllText(
								path,
								ElementUtil.findAllEleCodeArray(
										guaranteeElementCode, contractId),
								ElementUtil.findAllEleCodeValueArray(
										guaranteeElementCodeValue, contractId));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if (businessType == "Financing"
						|| "Financing".equals(businessType)) {// 融资
					FinancingElementCode financingElementCode = new FinancingElementCode();
					FinancingElementCode financingElementCodeValue = elementHandleService
							.getFinancingElementBySystem(projId, Integer
									.parseInt(contractId), tempId, htType, null);
					File file = new File(path);
					if (file.exists()) {
						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(financingElementCode),
										ElementUtil
												.findEleCodeValueArray(financingElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);

					} else
						return;
				} else if (businessType == "ExhibitionBusiness"
						|| "ExhibitionBusiness".equals(businessType)) {
					SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
					SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
							.getExhibitionElementBySystem(projId, Integer
									.parseInt(contractId), tempId, htType, null);
					File file = new File(path);
					if (file.exists()) {
						// 这个地方是否得判断一下是否有要素???????

						JacobWord
								.getInstance()
								.replaceAllText(
										path,
										ElementUtil
												.findEleCodeArray(smallLoanElementCode),
										ElementUtil
												.findEleCodeValueArray(smallLoanElementCodeValue));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if ("Pawn".equals(businessType)) {// 典当
					PawnElementCode pawnElementCode = new PawnElementCode();
					PawnElementCode pawnElementCodeValue = elementHandleService
							.getPawnElementBySystem(projId, Integer
									.parseInt(contractId), tempId, htType,
									null, dwId);
					File file = new File(path);
					if (file.exists()) {
						JacobWord.getInstance().replaceAllText(
								path,
								ElementUtil.findAllEleCodeArray(
										pawnElementCode, contractId),
								ElementUtil.findAllEleCodeValueArray(
										pawnElementCodeValue, contractId));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if ("LeaseFinance".equals(businessType)) {// 融资租赁
					LeaseFinanceElementCode leaseFinanceElementCode = new LeaseFinanceElementCode();
					LeaseFinanceElementCode leaseFinanceElementCodeValue = elementHandleService
							.getLeaseFinanceElementBySystem(projId, Integer
									.parseInt(contractId), tempId, htType,
									null, leaseObjectInfoId);
					File file = new File(path);
					if (file.exists()) {
						JacobWord.getInstance().replaceAllText(
								path,
								ElementUtil.findAllEleCodeArray(
										leaseFinanceElementCode, contractId),
								ElementUtil.findAllEleCodeValueArray(
										leaseFinanceElementCodeValue,
										contractId));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				} else if ("Assignment".equals(businessType)) {// 债权转让
					AssignmentElementCode assignmentElementCode = new AssignmentElementCode();
					AssignmentElementCode assignmentElementCodeValue = elementHandleService
							.getAssignmentElementBySystem();
					File file = new File(path);
					if (file.exists()) {
						JacobWord.getInstance()
								.replaceAllText(
										path,
										ElementUtil.findAllEleCodeArray(
												assignmentElementCode,
												contractId),
										ElementUtil.findAllEleCodeValueArray(
												assignmentElementCodeValue,
												contractId));
						FileHelper.copyFile(path, newPath);
						FileHelper.deleteFile(path);
					} else
						return;
				}
			}
			// 向实体添加数据，返回给客户端
			JsonUtil.jsonFromObject(p, true);
		} catch (Exception e) {
			JsonUtil.responseJsonFailure();
			logger.error("生成合同报错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
		 * 
		 * 
		 * */
	public void findCreditElement() {
		try {
			String depict = this.getRequest().getParameter("depict");
			String operationTypeKey = this.getRequest().getParameter(
					"operationTypeKey");
			List list = null;
			CreditmanagementElementCode creditmanagementElementCode = new CreditmanagementElementCode();
			list = ElementUtil
					.getCreditManageListElement(creditmanagementElementCode);
			/*
			 * if(list!=null){ for(int i=0;i<list.size();i++){ Object temp1
			 * =list.get(i);
			 * 
			 * } } if(depict!=null&&!"".equals(depict)){
			 * 
			 * }else{ alist=list; }
			 */
			int total = 0;
			if (list != null) {
				total = list.size();
			}
			JsonUtil.jsonFromList(list, total);
		} catch (Exception e) {
			JsonUtil.responseJsonString("{success:false}");
			logger.error("选择模板后查看模板下所有的要素和要素所对应的值出错:" + e.getMessage());
			e.printStackTrace();
		}
	}
	private SmallLoanElementCode updateCode(SlSmallloanProject slSmallloanProject,BpFundProject sloanProject,InvestPersonInfo investInfo,SmallLoanElementCode codeValue){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		//担保人----- 姓名、住址
		if(slSmallloanProject.getAvailableId()==null){
			
		}else{
			InvestEnterprise enterprise = investEnterpriseService.get(slSmallloanProject.getAvailableId());
			if(enterprise!=null){
				codeValue.setDbrmc_v(enterprise.getEnterprisename());
				codeValue.setZsd_v(enterprise.getArea());
			}
		}
		
		BpCustRelation relation = null;
		//借款人-----申请号、注册号、
		if(sloanProject.getOppositeType().equals("person_customer")){
			//Person person = personService.getById(sloanProject.getOppositeID().intValue());
			List<BpCustRelation> li = bpCustRelationService.getByCustIdAndCustType(sloanProject.getOppositeID(), "p_loan");
			if(li!=null&&li.size()!=0){
				relation = li.get(0);
			}
		}else if(sloanProject.getOppositeType().equals("company_customer")){
			//Enterprise enter = enterpriseService.getById(sloanProject.getOppositeID().intValue());
			List<BpCustRelation> li = bpCustRelationService.getByCustIdAndCustType(sloanProject.getOppositeID(), "b_loan");
			if(li!=null&&li.size()!=0){
				relation = li.get(0);
			}
		}
		if(null!=relation){
			BpCustMember member = bpCustMemberService.get(relation.getP2pCustId());//
			codeValue.setJksqh_v("借-"+member.getId()+"-"+sloanProject.getOppositeID());//申请号
			codeValue.setJkrzch_v(member.getLoginname());//注册号
		}
		//投资人-----申请号、注册号、姓名
		codeValue.setInvest_v(investInfo.getInvestPersonName());
		codeValue.setTzsqh_v("投-"+investInfo.getInvestId()+"-"+investInfo.getInvestPersonId());
		codeValue.setTzrzch_v(investInfo.getInvestPersonName());
		//放款信息---贷款金额、贷款用途、大小写
		BigDecimal projectMoney =investInfo.getInvestMoney();
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		String dw = "元整";
		codeValue.setDkjexx_v(myFormatter.format(projectMoney).toString()+"元");
		codeValue.setDkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		if(sloanProject.getAccrualtype().equals("sameprincipalsameInterest")){//等本等息
			//SlFundIntent slFundIntent = slFundIntentService.get
			//获取利息
			List<BpFundIntent> interst = bpFundIntentService.getBpBidPlanId(investInfo.getBidPlanId(), "loanInterest");
			List<BpFundIntent> principle = bpFundIntentService.getBpBidPlanId(investInfo.getBidPlanId(), "principalRepayment");
			Double inter = 0d;
			Double prin = 0d;
			if(interst!=null&&interst.size()!=0){
				BpFundIntent intent = interst.get(0);
				if(intent!=null){
					inter = intent.getIncomeMoney().doubleValue();
					codeValue.setTbmc_v(intent.getProjectName());
				}
			}if(principle!=null&&principle.size()!=0){
				BpFundIntent intent = principle.get(0);
				if(intent!=null){
					prin = intent.getIncomeMoney().doubleValue();
					codeValue.setTbmc_v(intent.getProjectName());
				}
			}
			codeValue.setQhbj_v(myFormatter.format(prin).toString()+"元");
			codeValue.setQhlx_v(myFormatter.format(inter).toString()+"元");
			codeValue.setQhze_v(myFormatter.format(prin+inter).toString()+"元");
			//合同编号---发表时间-投资人编号-借款人编号
			PlBidPlan plBidPlan = plBidPlanService.get(investInfo.getBidPlanId());
			
			if(plBidPlan!=null){
				codeValue.setJkhtbh_v(sdf.format(plBidPlan.getCreatetime())+"-"+investInfo.getInvestPersonId()+"-"+sloanProject.getOppositeID());
			}else{
				codeValue.setJkhtbh_v(sdf.format(plBidPlan.getPublishSingeTime())+"- -"+sloanProject.getOppositeID());
			}
			
			
			
		}
		
		
		PlBidPlan	plBidPlan=plBidPlanService.get(investInfo.getBidPlanId());
		QueryFilter filter = new QueryFilter(this.getRequest());
		if(plBidPlan.getProType().endsWith("B_Or")){
			filter.addFilter("Q_bpBusinessOrPro.borProId_L_EQ", plBidPlan.getBorProId().toString());
			List<PlBusinessDirProKeep> list = plBusinessDirProKeepService.getAll(filter);
			if(null!=list&&list.size()!=0){
				if(list.get(0).getProGtOrzId() !=null){
					InvestEnterprise	investEnterprise=investEnterpriseService.get(list.get(0).getProGtOrzId());
				if(null !=investEnterprise){
					codeValue.setDbjgmc_v(investEnterprise.getEnterprisename());
					codeValue.setDbjgdz_v(investEnterprise.getArea());
				}
				}
			}
		}else if(plBidPlan.getProType().endsWith("P_Or")){

			filter.addFilter("Q_bpPersionOrPro.porProId_L_EQ", plBidPlan.getPOrProId().toString());
			List<PlPersionDirProKeep> list = plPersionDirProKeepService.getAll(filter);
			if(null!=list&&list.size()!=0){
				if(list.get(0).getProGtOrzId() !=null){
					InvestEnterprise	investEnterprise=investEnterpriseService.get(list.get(0).getProGtOrzId());
				if(null !=investEnterprise){
					codeValue.setDbjgmc_v(investEnterprise.getEnterprisename());
					codeValue.setDbjgdz_v(investEnterprise.getArea());
				}
				}
			}
		
			
			
		}else if(plBidPlan.getProType().endsWith("B_Dir")){
			filter.addFilter("Q_bpBusinessDirPro.bdirProId_L_EQ", plBidPlan.getBdirProId().toString());
			List<PlBusinessDirProKeep> list = plBusinessDirProKeepService.getAll(filter);
			if(null!=list&&list.size()!=0){
				if(list.get(0).getProGtOrzId() !=null){
					InvestEnterprise	investEnterprise=investEnterpriseService.get(list.get(0).getProGtOrzId());
				if(null !=investEnterprise){
					codeValue.setDbjgmc_v(investEnterprise.getEnterprisename());
					codeValue.setDbjgdz_v(investEnterprise.getArea());
				}
				}
			}
		}else if(plBidPlan.getProType().endsWith("P_Dir")){

		
			filter.addFilter("Q_bpPersionDirPro.pdirProId_L_EQ", plBidPlan.getPDirProId().toString());
			List<PlPersionDirProKeep> list = plPersionDirProKeepService.getAll(filter);
			if(null!=list&&list.size()!=0){
				if(list.get(0).getProGtOrzId() !=null){
					InvestEnterprise	investEnterprise=investEnterpriseService.get(list.get(0).getProGtOrzId());
				if(null !=investEnterprise){
					codeValue.setDbjgmc_v(investEnterprise.getEnterprisename());
					codeValue.setDbjgdz_v(investEnterprise.getArea());
				}
				
				}
			}
		
			
			
		}
		
		PlBidInfo plBidInfo=plBidInfoService.getByOrderNo(investInfo.getOrderNo());
		codeValue.setTbrq_v(sd.format(plBidInfo.getBidtime()));

	    projectMoney = investInfo.getInvestMoney();
		codeValue.setTzrtbjexx_v(myFormatter.format(projectMoney).toString()+"元");
		codeValue.setTzrtbjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		BpCustMember investPerson=bpCustMemberService.get(investInfo.getInvestPersonId());
		codeValue.setTzrxm_v(investPerson.getTruename());
		codeValue.setTzryhm_v(investInfo.getInvestPersonName());
		codeValue.setTzrzjhm_v(investPerson.getCardcode());
		codeValue.setZbxmbh_v(investInfo.getInvestId().toString());

		List<BpCustRelation> bpCustRelationlist=bpCustRelationService.getByCustIdAndCustType(sloanProject.getOppositeID(),sloanProject.getOppositeType().equals("person_customer")?"p_loan":"b_loan");
		if(bpCustRelationlist!=null&bpCustRelationlist.size()>0){
			BpCustMember bpCustMember=bpCustMemberService.get(bpCustRelationlist.get(0).getP2pCustId());
			if(null!=bpCustMember){
				codeValue.setJkryhm_v(bpCustMember.getLoginname());
			}
			
		}
		
		codeValue.setDknhll_v(sloanProject.getYearAccrualRate().setScale(2).toString() + "%");
		    if(null!=plBidPlan.getStartIntentDate()){
		    	codeValue.setPpdkqsr_v(sd.format(plBidPlan.getStartIntentDate()).toString());
		    }
		    if(null!=plBidPlan.getStartIntentDate()){
		    	codeValue.setPpdkdqr_v(sd.format(plBidPlan.getEndIntentDate()).toString());
		    }
			
		
	//	codeValue.setDkqsrq_v(sloanProject.getStartInterestDate().toString());
		return codeValue;
	}

	public List<BpFundIntent>  updateCodelist(InvestPersonInfo investInfo,SmallLoanElementCode codeValue){
		
		
		
		List<BpFundIntent> list1=bpFundIntentService.getByPlanIdA(investInfo.getBidPlanId(),null,investInfo.getOrderNo(),"'loanInterest','principalRepayment'");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String datesring="";
		for(BpFundIntent bf:list1){
			bpFundIntentService.evict(bf);
			if(bf.getFundType().equals("loanInterest")){
				
				bf.setFundType("利息");
				sdf.format(bf.getIntentDate());
				datesring=sdf.format(bf.getIntentDate()).split("-")[2];
				if(datesring.startsWith("0")){
					datesring=datesring.substring(1);
					
				}
				if(datesring.equals("31")){
					
					datesring="30";
				}
				
			}else if(bf.getFundType().equals("principalRepayment")){
				bf.setFundType("本金");
				
			}
		
		}
		
		codeValue.setMyhkr_v(datesring);
		return list1;
	} 
	
	//投资人合同上传
	public String uploadToFtp(){
		HttpServletRequest requrst = getRequest();
		String bidPlanId = requrst.getParameter("bidPlanId");
		PlBidPlan	plBidPlan =	plBidPlanService.get(Long.valueOf(bidPlanId));
		List<InvestPersonInfo> list = investPersonInfoService.getByBidPlanId(Long.parseLong(bidPlanId));
		int errorNum =0;//上传失败的合同数
		int rigthNum = 0;//上传成功的合同数据
		for(InvestPersonInfo a:list){
			ServletContext context=ServletActionContext.getServletContext();
			
			File  file = new File(context.getRealPath("/")+a.getContractUrls());
			String srcPath = file.getAbsolutePath();
			String flag ="";
			flag = fTPUploadFileimpl.upLoadFile(srcPath, a.getContractUrls());
			
			if(flag.equals("true")){
				rigthNum ++;
			}else if(flag.equals("false")){
				errorNum++;
			}
			Integer.valueOf(ContextUtil.getCurrentUserId().toString());
		}
		
		//设置上传操作信息
		UploadLog   uploadLog = new UploadLog();
		uploadLog.setBidId(plBidPlan.getBidId().toString());
		uploadLog.setBidName(plBidPlan.getBidProName());
		uploadLog.setCreateDate(new Date());
		int sum = rigthNum+errorNum;
		uploadLog.setTotalCount(sum);
		uploadLog.setSuccessCount(rigthNum);
		uploadLog.setFailureCount(errorNum);
		String  msgStr = "投资人合同：共"+sum+"份，成功上传"+rigthNum+"份，失败"+errorNum+"份";
		uploadLog.setMsg(msgStr);
		uploadLogService.save(uploadLog);//保存上传合同信息
		return SUCCESS;
	}
		
	public String ids;

	@Resource
	public  UploadLogService  uploadLogService;
	
	/**
	 *  借款合同ftp上传操作
	 * @return
	 */
	public String jiekuanUploadToFtp(){
		HttpServletRequest requrst = getRequest();
		String bidPlanId = requrst.getParameter("bidPlanId");
		System.out.println("ids ==="+ids);
		if(ids != null && !"".equals(ids)){
			String[] arr = ids.split(",");
		 
			int errorNum =0;//上传失败的合同数
			int rigthNum = 0;//上传成功的合同数据
			PlBidPlan plBidPlan = plBidPlanService.get(Long.valueOf(bidPlanId));
			for(int i=0;i<arr.length;i++){
				List<ProcreditContract> aa = procreditContractService.findById(Integer.valueOf(arr[i]));
 
				ServletContext context=ServletActionContext.getServletContext();
				File  file = new File(context.getRealPath("/")+aa.get(0).getUrl());
				String srcPath = file.getAbsolutePath();
				
				String flag ="";
				flag =	fTPUploadFileimpl.upLoadFile(srcPath, aa.get(0).getUrl());
				if(flag.equals("true")){
					rigthNum ++;
				}else if(flag.equals("false")){
					errorNum++;
				}
			}
			//设置上传操作信息
			UploadLog   uploadLog = new UploadLog();
			uploadLog.setBidId(plBidPlan.getBidId().toString());
			uploadLog.setBidName(plBidPlan.getBidProName());
			uploadLog.setCreateDate(new Date());
			int sum = rigthNum+errorNum;
			uploadLog.setTotalCount(sum);
			uploadLog.setSuccessCount(rigthNum);
			uploadLog.setFailureCount(errorNum);
			String  msgStr = "贷款人合同：共"+sum+"份，成功上传"+rigthNum+"份，失败"+errorNum+"份";
			uploadLog.setMsg(msgStr);
			uploadLogService.save(uploadLog);//保存上传合同信息
		}
		return SUCCESS;
	}
	
	public void createP2pContrqcts(){  
	
	
		String serverPath = super.getRequest().getRealPath("/");
		HttpServletRequest requrst = getRequest();
	
		
		String bidPlanId = requrst.getParameter("bidPlanId");
		String fundProjectId = requrst.getParameter("fundProjectId");
		String documenttempletId=requrst.getParameter("documenttempletId");
		DocumentTemplet dtemplet = documentTempletService.getById(Integer.valueOf(documenttempletId));/*documentTempletService.getListByOnlymark("gerenjiekuanhetong").get(0)*/;
		FileForm fileForm = fileFormService.getById(dtemplet.getFileid());
	
		String templetPath=fileForm.getFilepath();
		PlBidPlan	plBidPlan=new PlBidPlan();
		plBidPlan=plBidPlanService.get(Long.valueOf(bidPlanId));
		String commonUrlpath="";
		String copyDir="";
	    

		BpFundProject bpFundProject =bpFundProjectService.get(Long.parseLong(fundProjectId)) ; //项目信息实体
		SlSmallloanProject sproject=slSmallloanProjectService.get(Long.valueOf(projId));
		SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
		SmallLoanElementCode smallLoanElementCodeValue=new SmallLoanElementCode();
		try {
			smallLoanElementCodeValue = elementHandleService
						.getElementBySystem(fundProjectId, null, null, "loanContract",
								null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String fileCraName=sproject.getProjectNumber()+"\\"+plBidPlan.getBidProNumber()+"_"+plBidPlan.getBidId()+ "\\ptpContract\\";
		plBidPlan=plBidPlanService.get(Long.valueOf(bidPlanId));
		commonUrlpath=	"attachFiles\\projFile\\contfolder\\" + fileCraName;
		copyDir = serverPath+ commonUrlpath;
		
		List<InvestPersonInfo> list = investPersonInfoService.getByBidPlanId(Long.parseLong(bidPlanId));
		path = ElementUtil.getUrl(serverPath + templetPath,
				"ptpContractx.doc");
		File file=new File(path);
		if (file.exists()) {
		 if(null!=list&&list.size()!=0){
			int i=1;
			List<ProcreditContract> coutractlist=new ArrayList<ProcreditContract>(); 
			for(InvestPersonInfo investInfo:list){
				path = ElementUtil.getUrl(serverPath + templetPath,
				"ptpContractx.doc");
				String fileName="P2P_"+plBidPlan.getBidProNumber()+"_"+investInfo.getInvestPersonName()+"-"+i;
				if(investInfo!=null){
					List<BpFundIntent> list1=updateCodelist(investInfo,smallLoanElementCodeValue);
					SmallLoanElementCode codeValue = updateCode(sproject,bpFundProject,investInfo,smallLoanElementCodeValue);//根据不通过的投资人修改codeValue中的值
				    JacobWord.getInstance().replaceAllText(path,ElementUtil.findEleCodeArray(smallLoanElementCode),ElementUtil.findEleCodeValueArray(codeValue),list1,null,null);
			
					//newPath 文件名,需要修改的个个投资人
					JacobWord.getInstance().comThreadClose();
					
					File cFile = new File(copyDir);
					if (!cFile.exists()) {
						cFile.mkdirs();
					}
					String newPath = (copyDir + fileName+ ".doc").trim();
					
					FileHelper.copyFile(path, newPath);
					FileHelper.deleteFile(path);
					
					//生成之后要想数据库中添加合同信息
					//每一个投资人生成一份合同，首先要删除contractId
					ProcreditContract loancontract = new ProcreditContract();
                     coutractlist.add(loancontract);
					
					String endpathdoc=commonUrlpath+ fileName+".doc";
					String endpathdpdf=commonUrlpath + fileName+ ".pdf";
					loancontract.setUrl(endpathdoc);
					if(null !=investInfo.getContractUrls()){
						if(!investInfo.getContractUrls().contains(endpathdpdf)){
					    //  investInfo.setContractUrls(investInfo.getContractUrls()+","+endpathdpdf);
							investInfo.setContractUrls(endpathdpdf);
						}
					}else{
						
					   investInfo.setContractUrls(endpathdpdf);
					}
					investPersonInfoService.save(investInfo);
					i++;
				//	JacobWord.getInstance().close();
					 JacobWord.getInstance().comThreadClose();
				}
		
			//	break;
			}	
				
			 for(ProcreditContract a:coutractlist){
					
					WordtoPdfUtil pdfUtil = new WordtoPdfUtil();
					ServletContext context=ServletActionContext.getServletContext();
					pdfUtil.wordToPdf(context.getRealPath("/")+a.getUrl(), "");
                   }
			
		  }
		   
	
		}
		
		// 向实体添加数据，返回给客户端
		JsonUtil.jsonFromObject(null, true);
	}
	public void	downContracts(){

		String htmlPath = "";
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		try {
			Long investId = Long.valueOf(request.getParameter("investId"));
			InvestPersonInfo investPersonInfo = investPersonInfoService.get(investId);
			if(null!=investPersonInfo.getContractUrls()){
			String[] ids=investPersonInfo.getContractUrls().split(",");
			/*FileUtil.mkDirectory(ids[0].substring(0,ids[0].lastIndexOf("/")));
			CompressUtil.zip(ids[0].substring(0,ids[0].lastIndexOf("/")), ids[0].substring(0,ids[0].lastIndexOf("/"))+".zip");//压缩
*/				for(int i=0;i<ids.length;i++){
					ElementUtil.downloadFile(super.getRequest().getRealPath("/")
							+ ids[i], response);
				}
			}
			
		
		} catch (Exception e) {
			logger.error("查看上传合同出错:" + e.getMessage());
			e.printStackTrace();
			JsonUtil
					.responseJsonString("{success:false,exsit:false,msg :'系统错误'}");
		}
	}
	
	

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}