package com.zhiwei.credit.action.creditFlow.fileUploads;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.ConvertFileType;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DownloadURLFile;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.FileCompressionUtil;
import com.zhiwei.credit.core.creditUtils.FileUtil;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financeProject.VFinancingProject;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.VGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnContinuedManagment;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.contract.DocumentTempletService;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financeProject.VFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.VGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.VLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnContinuedManagmentService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.FTPService;
import com.zhiwei.credit.service.system.FileAttachService;
import com.zhiwei.credit.service.system.OrganizationService;

public class FileFormAction extends BaseAction{
	@Resource
	private FileFormService fileFormService;
	@Resource
	private VGuaranteeloanProjectService vGuaranteeloanProjectService;
	@Resource
	private VLeaseFinanceProjectService vLeaseFinanceProjectService;
	@Resource
	private VFinancingProjectService vFinancingProjectService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private MortgageService mortgageService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlCompanyMainService companyMainService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	@Resource
	private GLGuaranteeloanProjectService gLGuaranteeloanProjectService;
	@Resource
	private PawnContinuedManagmentService pawnContinuedManagmentService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private ProcreditContractService procreditContractService;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private DocumentTempletService documentTempletService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	
	@Resource
	private FTPService ftpService;
	
	@Resource
	private FTPIoadFileService fTPUploadFileimpl;
	@Resource
	private BpFundProjectService bpFundProjectService;
	private String projectId;
	private String businessType;
	private String projectNum;
	private String projectName;
    private String companyId;
    
    private static final String uploadPath="attachFiles/uploads";
	private static final String uploadPathReport="attachFiles";
	public static final String ROOT = "attachFiles\\uploads\\";

	private File myUpload;

	private String myUploadContentType;

	private String myUploadFileName;

	private String path;

	private String name;

	private boolean success;

	private String fullpath;
	
	public String returnhtml;

	/*-----数据库表字段----*/
	private Integer fileid;
	private String setname;
	private String filename ;
	private String filepath;
	private String extendname;
	private Integer filesize;
	private Integer creatorid;
	private java.util.Date createtime;
	private String remark;
	private String basepath;
	private String mark;//mark=模块名+"."+ID  唯一标识！
	
	private String sid;
	
	private String typeisfile;//是否只是一个文件，目前只调查报告用到
	private String projId;//项目id，目前只调查报告用到
	private int contractId;
	
	private int templettype;
	private FileForm fileinfo;
	private FileAttach fileAttach;
	private Boolean isGrantedShowAllProjects ;
	
	
	public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public FileForm getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(FileForm fileinfo) {
		this.fileinfo = fileinfo;
	}

	public void setFileAttach(FileAttach fileAttach) {
		this.fileAttach = fileAttach;
	}

	public File getMyUpload() {
		return myUpload;
	}

	public void setMyUpload(File myUpload) {
		this.myUpload = myUpload;
	}

	public String getMyUploadContentType() {
		return myUploadContentType;
	}

	public void setMyUploadContentType(String myUploadContentType) {
		this.myUploadContentType = myUploadContentType;
	}

	public String getMyUploadFileName() {
		return myUploadFileName;
	}

	public void setMyUploadFileName(String myUploadFileName) {
		this.myUploadFileName = myUploadFileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFullpath() {
		return fullpath;
	}

	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}

	public String getReturnhtml() {
		return returnhtml;
	}

	public void setReturnhtml(String returnhtml) {
		this.returnhtml = returnhtml;
	}

	public Integer getFileid() {
		return fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid = fileid;
	}

	public String getSetname() {
		return setname;
	}

	public void setSetname(String setname) {
		this.setname = setname;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getExtendname() {
		return extendname;
	}

	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}

	public Integer getFilesize() {
		return filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public Integer getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Integer creatorid) {
		this.creatorid = creatorid;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBasepath() {
		return basepath;
	}

	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getTypeisfile() {
		return typeisfile;
	}

	public void setTypeisfile(String typeisfile) {
		this.typeisfile = typeisfile;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public int getTemplettype() {
		return templettype;
	}

	public void setTemplettype(int templettype) {
		this.templettype = templettype;
	}

	public static String getUploadpath() {
		return uploadPath;
	}

	public static String getUploadpathreport() {
		return uploadPathReport;
	}

	public static String getRoot() {
		return ROOT;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String list(){
		List<VProcreditContract> vlist=vProcreditContractService.findList(projectId, businessType);
		List<FileForm> flist=fileFormService.findList(projectId, businessType);
		for(VProcreditContract vp:vlist){
			FileForm file=new FileForm();
			file.setFileid(vp.getId());
			file.setFilename(vp.getContractCategoryText());
			file.setFilesize(0);
			file.setExtendname("--");
			file.setCreatetime(vp.getDraftDate());
			file.setSetname(vp.getContractCategoryTypeText());
			file.setFileid(vp.getId());
			file.setWebPath(vp.getUrl());
			file.setIsArchiveConfirm(vp.getIsRecord());
			file.setArchiveConfirmRemark(vp.getRecordRemark());
			flist.add(file);
		}
		StringBuffer buff = new StringBuffer("{success:true,result:");	
		Gson gson=new Gson();
		buff.append(gson.toJson(flist));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 合同管理
	 * @return
	 */
	public String contractList(){
		String userIdsStr="";
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isGrantedShowAllProjects && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
		if(null!=businessType && businessType.equals("SmallLoan")){
		   List<BpFundProject> slist=bpFundProjectService.fundProjectList(userIdsStr, start, limit, this.getRequest());
		   long totalCount=bpFundProjectService.fundProjectCount(userIdsStr, this.getRequest());
		   for(BpFundProject vs:slist){
			   int totalSize = 0;
			   if(null!=vs.getCompanyId()){
				   Organization organization=organizationService.get(vs.getCompanyId());
					if(null!=organization){
						vs.setCompanyName(organization.getOrgName());
					}
			   }
			   List<VProcreditContract> conlist=vProcreditContractService.getList(vs.getId().toString(), businessType, "('loanContract','thirdContract','baozContract','otherFiles')");
			   if(null!=conlist && conlist.size()>0){
				   totalSize= conlist.size();//合同个数
			   }
			 
			  /* List<VProcreditContractCategory> morlist=vProcreditContractCategoryService.getList(vs.getProjectId().toString(), businessType, "thirdContract");
			   if(null!=morlist && morlist.size()>0){
				   totalSize += morlist.size();//担保合同个数
			   }
			   List<VProcreditContractCategory> baozlist=vProcreditContractCategoryService.getList(vs.getProjectId().toString(), businessType, "baozContract");
			   if(null!=baozlist && baozlist.size()>0){
				   totalSize += baozlist.size();//担保合同个数
			   }
			   List<VProcreditContractCategory> filelist=vProcreditContractCategoryService.getList(vs.getProjectId().toString(), businessType, "otherFiles");
			   if(null!=filelist && filelist.size()>0){
				   totalSize +=filelist.size();//担保合同个数
			   }*/
			   vs.setContractCount(totalSize);
			  
			  List<SlSuperviseRecord> list=slSuperviseRecordService.getListByProjectId(vs.getId(),vs.getBusinessType());//◎
			  if(null!=list && list.size()>0){
				  SlSuperviseRecord s=list.get(0);
				  if(null!=s){
					  vs.setRepaymentDate(s.getEndDate());
				  }
			  }else{
				  vs.setRepaymentDate(vs.getIntentDate());
			  }
		   }
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(totalCount).append(",result:");	
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(slist));
			buff.append("}");
			jsonString=buff.toString();
		}else if(null!=businessType && businessType.equals("Guarantee")){
			List<VGuaranteeloanProject> glist=vGuaranteeloanProjectService.getGuaranteeList(projectNum, projectName, start, limit,companyId);
			long totalCount=vGuaranteeloanProjectService.getGuaranteeNum(projectNum, projectName,companyId);
			for(VGuaranteeloanProject vg:glist){
				 int totalSize = 0;
				 if(null!=vg.getCompanyId()){
				   Organization organization=organizationService.get(vg.getCompanyId());
					if(null!=organization){
						vg.setCompanyName(organization.getOrgName());
					}
			   }
			/*	List<VProcreditContractCategory> conlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, null);
				   if(null!=conlist && conlist.size()>0){
					   vg.setContractCount(conlist.size());
				   }else{
					   vg.setContractCount(0);
				   }
				   List<VProcreditContractCategory> morlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, "thirdContract");
				   if(null!=morlist && morlist.size()>0){
					   vg.setMorContractCount(morlist.size());
				   }else{
					   vg.setMorContractCount(0);
				   }*/
				 
				 	/*updade by gengjj  20130717*/
				   
				   List<VProcreditContract> conlist=vProcreditContractService.getList(vg.getProjectId().toString(), businessType, "('guaranteeContract','thirdContract','baozContract')");
				   if(null!=conlist && conlist.size()>0){
					   totalSize = conlist.size();//合同个数
				   }
				 
				  /* List<VProcreditContractCategory> morlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, "thirdContract");
				   if(null!=morlist && morlist.size()>0){
					   totalSize += morlist.size();//担保合同个数
				   }
				   List<VProcreditContractCategory> baozlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, "baozContract");
				   if(null!=baozlist && baozlist.size()>0){
					   totalSize += baozlist.size();//担保合同个数
				   }*/
				   vg.setContractCount(totalSize);
				   /*updade by gengjj  20130717*/ 
				   String businessManager=vg.getAppUserIdOfA();
				   String businessManagerValue="";
				   if(null!=businessManager){
					   String bus[]=businessManager.split(",");
					   for(int i=0;i<bus.length;i++){
						   AppUser user=appUserService.get(Long.parseLong(bus[i]));
						   businessManagerValue=businessManagerValue+user.getFullname()+",";
					   }
					   businessManagerValue=businessManagerValue.substring(0,businessManagerValue.length()-1);
					   vg.setBusinessManagerValue(businessManagerValue);
				   }
				  GLGuaranteeloanProject project=gLGuaranteeloanProjectService.get(vg.getProjectId());
				  vg.setLoanStartDate(project.getAcceptDate());
				  vg.setRepaymentDate(project.getIntentDate());
			}
			Type type=new TypeToken<List<VGuaranteeloanProject>>(){}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(totalCount).append(",result:");	
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(glist, type));
			buff.append("}");
			jsonString=buff.toString();
		}else if(null!=businessType && businessType.equals("Financing")){
			List<VFinancingProject> flist=vFinancingProjectService.getFinancingList(projectNum, projectName, start, limit,companyId);
		    long totalCount=vFinancingProjectService.getFinancingNum(projectNum, projectName,companyId);
		    for(VFinancingProject vf:flist){
		    	 	if(null!=vf.getCompanyId()){
					   Organization organization=organizationService.get(vf.getCompanyId());
						if(null!=organization){
							vf.setCompanyName(organization.getOrgName());
						}
				   }
		    	   List<VProcreditContract> conlist=vProcreditContractService.getList(vf.getProjectId().toString(), businessType, null);
				   if(null!=conlist && conlist.size()>0){
					   vf.setContractCount(conlist.size());
				   }else{
					   vf.setContractCount(0);
				   }
				   List<VProcreditContract> morlist=vProcreditContractService.getList(vf.getProjectId().toString(), businessType, "thirdContract");
				   if(null!=morlist && morlist.size()>0){
					   vf.setMorContractCount(morlist.size());
				   }else{
					   vf.setMorContractCount(0);
				   }
				   String businessManager=vf.getBusinessManager();
				   String businessManagerValue="";
				   if(null!=businessManager){
					   String bus[]=businessManager.split(",");
					   for(int i=0;i<bus.length;i++){
						   AppUser user=appUserService.get(Long.parseLong(bus[i]));
						   businessManagerValue=businessManagerValue+user.getFullname()+",";
					   }
					   businessManagerValue=businessManagerValue.substring(0,businessManagerValue.length()-1);
					   vf.setBusinessManagerValue(businessManagerValue);
				   }
				   
		    }
		    Type type=new TypeToken<List<VFinancingProject>>(){}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(totalCount).append(",result:");	
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(flist, type));
			buff.append("}");
			
			jsonString=buff.toString();
		}else if(null!=businessType && businessType.equals("LeaseFinance")){//★★
			List<VLeaseFinanceProject> fllist=vLeaseFinanceProjectService.getLeaseFinanceList(projectNum, projectName, start, limit,companyId);
			long totalCount=vLeaseFinanceProjectService.getLeaseFinanceNum(projectNum, projectName,companyId);
			for(VLeaseFinanceProject vg:fllist){
				String mineName = "";
				 int totalSize = 0;
				 if("true".equals(AppUtil.getSystemIsGroupVersion())){
					 if(null != vg.getMineId()){
						 mineName = this.organizationService.get(vg.getMineId()).getOrgName();
					 }
				}else{
					if(null != vg.getMineId()){
						SlCompanyMain com=this.companyMainService.get(vg.getMineId());
						if(null!=com){
							mineName = this.companyMainService.get(vg.getMineId()).getCorName();
						}
					 }
				}
				/* if(null!=vg.getCompanyId()){
				   Organization organization=organizationService.get(vg.getCompanyId());
					if(null!=organization){
						vg.setCompanyName(organization.getOrgName());
					}
			   }*/
				   vg.setCompanyName(mineName);//因为前台显示的是CompanyName,按理说集团办应该显示MineName的，但是要改动很多前台js，so   先不改
				   List<VProcreditContract> conlist=vProcreditContractService.getList(vg.getProjectId().toString(), businessType, "('leaseFinanceContract','thirdContract','baozContract','leaseFinanceContractExt')");
				   if(null!=conlist && conlist.size()>0){
					   totalSize = conlist.size();//合同个数
				   }
				 
				 /*  List<VProcreditContractCategory> morlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, "thirdContract");
				   if(null!=morlist && morlist.size()>0){
					   totalSize += morlist.size();//担保合同个数
				   }
				   List<VProcreditContractCategory> baozlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, "baozContract");
				   if(null!=baozlist && baozlist.size()>0){
					   totalSize += baozlist.size();//担保合同个数
				   }*/
				   vg.setContractCount(totalSize);
				   /*updade by gengjj  20130717*/ 
				   String businessManager=vg.getAppUserId();
				   String businessManagerValue="";
				   if(null!=businessManager){
					   String bus[]=businessManager.split(",");
					   for(int i=0;i<bus.length;i++){
						   AppUser user=appUserService.get(Long.parseLong(bus[i]));
						   businessManagerValue=businessManagerValue+user.getFullname()+",";
					   }
					   businessManagerValue=businessManagerValue.substring(0,businessManagerValue.length()-1);
					   vg.setBusinessManagerValue(businessManagerValue);
				   }
				   List<SlSuperviseRecord> list=slSuperviseRecordService.getListByProjectId(vg.getProjectId(),vg.getBusinessType());//◎
					  if(null!=list && list.size()>0){
						  SlSuperviseRecord s=list.get(0);
						  if(null!=s){
							  vg.setRepaymentDate(s.getEndDate());
						  }
					  }else{
						  vg.setRepaymentDate(vg.getExpectedRepaymentDate());
					  }
			}
			Type type=new TypeToken<List<VLeaseFinanceProject>>(){}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(totalCount).append(",result:");	
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(fllist, type));
			buff.append("}");
			jsonString=buff.toString();
		}else if(null!=businessType && businessType.equals("Pawn")){//★★
			List<VPawnProject> fllist=plPawnProjectService.getPawnList(projectNum, projectName, start, limit,companyId);
			long totalCount=plPawnProjectService.getPawnNum(projectNum, projectName,companyId);
			for(VPawnProject vg:fllist){
				String mineName = "";
				 int totalSize = 0;
				 if("true".equals(AppUtil.getSystemIsGroupVersion())){
					 if(null != vg.getMineId()){
						 mineName = this.organizationService.get(vg.getMineId()).getOrgName();
					 }
				}else{
					if(null != vg.getMineId()){
						SlCompanyMain com=this.companyMainService.get(vg.getMineId());
						if(null!=com){
							mineName = com.getCorName();
						}
					 }
				}
				/* if(null!=vg.getCompanyId()){
				   Organization organization=organizationService.get(vg.getCompanyId());
					if(null!=organization){
						vg.setCompanyName(organization.getOrgName());
					}
			   }*/
				 vg.setCompanyName(mineName);//因为前台显示的是CompanyName,按理说集团办应该显示MineName的，但是要改动很多前台js，so   先不改
				   List<VProcreditContract> conlist=vProcreditContractService.getList(vg.getProjectId().toString(), businessType, "('pawnContract','dwContract')");
				   if(null!=conlist && conlist.size()>0){
					   totalSize = conlist.size();//合同个数
				   }
				 
				  /* List<VProcreditContractCategory> morlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, "thirdContract");
				   if(null!=morlist && morlist.size()>0){
					   totalSize += morlist.size();//担保合同个数
				   }
				   List<VProcreditContractCategory> baozlist=vProcreditContractCategoryService.getList(vg.getProjectId().toString(), businessType, "baozContract");
				   if(null!=baozlist && baozlist.size()>0){
					   totalSize += baozlist.size();//担保合同个数
				   }*/
				   vg.setContractCount(totalSize);
				   /*updade by gengjj  20130717*/ 
				   String businessManager=vg.getAppUserId();
				   String businessManagerValue="";
				   if(null!=businessManager){
					   String bus[]=businessManager.split(",");
					   for(int i=0;i<bus.length;i++){
						   AppUser user=appUserService.get(Long.parseLong(bus[i]));
						   businessManagerValue=businessManagerValue+user.getFullname()+",";
					   }
					   businessManagerValue=businessManagerValue.substring(0,businessManagerValue.length()-1);
					   vg.setBusinessManagerValue(businessManagerValue);
				   }
				   vg.setLoanStartDate(vg.getStartDate());
				   List<PawnContinuedManagment> plist=pawnContinuedManagmentService.getListByProjectId(vg.getProjectId(), vg.getBusinessType(), null);
					if(null!=plist && plist.size()>0){
						PawnContinuedManagment con=plist.get(plist.size()-1);
						vg.setRepaymentDate(con.getIntentDate());
					}else{
						vg.setRepaymentDate(vg.getIntentDate());
					}
				  
			}
			Type type=new TypeToken<List<VPawnProject>>(){}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(totalCount).append(",result:");	
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(fllist, type));
			buff.append("}");
			jsonString=buff.toString();
		}else{
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':0,result:[]}");	
			jsonString=buff.toString();
		}

		return SUCCESS;
	}
	/**
	 * 获得某个项目的担保合同列表
	 * @return
	 */
	public String getMorContractList(){
		String htType=this.getRequest().getParameter("htType");
		 List<VProcreditContract> morlist=vProcreditContractService.getList(projectId, businessType, htType);
		 for(VProcreditContract vc:morlist){
			 int mortgageId=vc.getMortgageId();
			 try {
				ProcreditMortgage pm=mortgageService.seeMortgage(ProcreditMortgage.class, mortgageId);
				vc.setMortgagename(pm.getMortgagename());//担保物名称
				vc.setMortgageTypeValue(pm.getMortgagepersontypeforvalue());//担保物类型
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		 }
		StringBuffer buff = new StringBuffer("{success:true,result:");	
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(morlist));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public String queryContract(){
		String contractNum=this.getRequest().getParameter("contractNum");
		String searchRemark=this.getRequest().getParameter("searchRemark");
		PagingBean pb=new PagingBean(start,limit); 
		List<VProcreditContract> list=vProcreditContractService.getContractList(businessType, projectName, projectNum, contractNum,searchRemark, companyId, pb);
		for(VProcreditContract v:list){
			if(null!=businessType && businessType.equals("SmallLoan")){
				BpFundProject project=bpFundProjectService.get(Long.valueOf(v.getProjId()));
				if(null!=project){
					v.setProjectName(project.getProjectName());
					v.setProjectNumber(project.getProjectNumber());
					if(null!=project.getCompanyId()){
						Organization organization=organizationService.get(project.getCompanyId());
						if(null!=organization){
							v.setCompanyName(organization.getOrgName());
						}
					}
				}
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");	
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	
	/**
	 * ftp文件下载
     *svn:songwj
	 * @param srcStr  要下载的文件信息
	 * @param disStr  下载文件的存放位置
	 */
	public  void ftpDownFile(String srcStr,String disStr){
		
		ftpService.ftpDownFile(srcStr, disStr);
	}
	
	//Ftp删除文件方法
	//svn:songwj
	//deleteFilePath:删除文件的路径
	public  void ftpRemoveFile(String deleteFilePath){
		ftpService.removeFile(deleteFilePath);
	}
	
	
	/**
	 * 上传文件
	 * @HT_version3.0 主要调整了上传后的文件路径,将反斜杠(\)统一改成斜杠(/)
	 * @return
	 */
	public void uploadFiles() throws UnsupportedEncodingException {
		AppUser user = ContextUtil.getCurrentUser();
		if(user!=null){
			creatorid = Integer.valueOf(user.getId());
			String flag=this.getRequest().getParameter("flag");//用于区分是合同模板制作的上传(默认空值)还是流程中的合同上传(0)
			String selectId = mark.substring(mark.indexOf(".")+1, mark.length());
			filename = getMyUploadFileName();
			//判断文件上传的类型
			 filename = StringUtil.checkExtName(filename);
			 boolean extType = StringUtil.checkExtType(filename);
			if(extType){
				if(mark.indexOf("pl_Web_Show_Materials|sl_smallloan_project")!=-1){
					filename = getMyUploadFileName();
					extendname = FileUtil.getExtention(filename);
					
					Map<String ,String > map = new  HashMap<String ,String >();
					map=fTPIoadFileService.ftpUploadFile(myUpload,"uploads", "pl_Web_Show_Materials","sl_smallloan_project", selectId, extendname, setname, creatorid);
					jsonString = "{success : true, id_file : '"+map.get(fileid)+"' }";
				}else{
					StringBuffer rootPath = new StringBuffer("");
					rootPath.append(ROOT);
					rootPath.append(mark);
					rootPath.append("\\");
					rootPath.append(DateUtil.getYearAndMonth());
					rootPath.append("\\");
					
					String tempSrc=getSession().getServletContext().getRealPath("/")+rootPath.toString();
					tempSrc=tempSrc.replaceAll("\\\\","/");
					
					FileUtil.mkDirectory(tempSrc);//在服务器上创建路径
					filename = getMyUploadFileName();
					extendname = FileUtil.getExtention(filename);
					
					
					//得到当前时间，精确到毫秒
					String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
					filename = nowTime + extendname;
					filepath = rootPath.toString() + filename;
					filepath = filepath.replaceAll("\\\\","/");
					String webPath = uploadPath+"/"+mark+"/"+DateUtil.getYearAndMonth()+"/"+filename;
					setSuccess(FileUtil.upload(filename,tempSrc,getMyUpload()));
					File file =getMyUpload() ;
					if(FileUtil.isImageFile(file)){
						//项目根目录
						/*String root = AppUtil.getAppAbsolutePath().replaceAll("\\\\", "/"); 
						String webImagePath =root+webPath;  //项目的图片保存的完成路径
						//调用ftp的方法
					    //ftpService.ftpUploadFile(webImagePath, filename);
						//通过配置文件回去压缩文件的高宽
						int minWidth = Integer.parseInt(AppUtil.getMinCompressImageHeight()); 
						int minHeight = Integer.parseInt(AppUtil.getMinCompressImageWidth());
						int  width = Integer.parseInt(AppUtil.getCompressImageHeight());
						int  height = Integer.parseInt(AppUtil.getCompressImageWidth());
						String minCompressionFileName = AppUtil.getMinComperssionFileName();
						String compressionFileName = AppUtil.getCompressionFileName();*/
						AddFile(webPath,"","");// 向数据库中添加数据
					}else{
						AddFile(webPath,null,null);// 向数据库中添加数据
					}
					
					if(null!=flag && "0".equals(flag)){//流程中的合同上传
						//修改合同记录
						ProcreditContract p=procreditContractService.getById(Integer.valueOf(selectId));
						if(null!=p){
							p.setContractName(p.getContractCategoryText());
							p.setUrl(webPath);
							procreditContractService.merge(p);
						}
					}
					
					//返回的json必须设置id_file,因为需要修改cs_document_templet的fileid字段
					jsonString = "{success : true, id_file : '"+fileinfo.getFileid()+"' }";	
				}
			}else{
				jsonString = "上传文件类型错误,请重新选择文件";	
			}
			JsonUtil.responseJsonString(jsonString);
		}
	}
	
	/**
	 * 上传文件
	 * 
	 * @return
	 * 
	 */
	public void uploadReportJS() throws UnsupportedEncodingException {
		String rootPath = "";
		
		//获得项目编号
		String projectNumber = "";
		if("SmallLoan".equals(businessType)){
			
			SlSmallloanProject sslp = slSmallloanProjectService.get(Long.parseLong(projId));
			if(sslp!=null){
				projectNumber = sslp.getProjectNumber();
			}else{
				projectNumber = "test";
			}
			
		}else if("Guarantee".equals(businessType)){
			GLGuaranteeloanProject gglp = gLGuaranteeloanProjectService.get(Long.parseLong(projId));
			projectNumber = gglp.getProjectNumber();
		}else if("Financing".equals(businessType)){
			FlFinancingProject ffp = flFinancingProjectService.get(Long.parseLong(projId));
			projectNumber = ffp.getProjectNumber();
		}else if("ExhibitionBusiness".equals(businessType)){
			SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.parseLong(projId));
			SlSmallloanProject s = slSmallloanProjectService.get(slSuperviseRecord.getProjectId());
			projectNumber = s.getProjectNumber();
		}else if("FlExhibitionBusiness".equals(businessType)){
			SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.parseLong(projId));
			FlLeaseFinanceProject flfp = flLeaseFinanceProjectService.get(slSuperviseRecord.getProjectId());
			projectNumber = flfp.getProjectNumber();
		}else if("LeaseFinance".equals(businessType)){
			FlLeaseFinanceProject flfp = flLeaseFinanceProjectService.get(Long.parseLong(projId));
			projectNumber = flfp.getProjectNumber();
		}else if("plmmorder".equals(businessType)){
			projectNumber = "test";
		}else{
			projectNumber = "test";
		}
		rootPath +="attachFiles";
		rootPath += "\\";
		rootPath +="projFile";
		rootPath += "\\";
		rootPath += "reportfolder";
		rootPath += "\\";
		rootPath += projectNumber;
		rootPath += "\\";
		rootPath +=mark;
		rootPath +="\\";
		/*rootPath += ROOT;
		rootPath += mark;
		rootPath += "\\";
		rootPath += DateUtil.getYearAndMonth();
		rootPath += "\\";*/
		FileUtil.mkDirectory(getSession().getServletContext().getRealPath("/")+rootPath);
		
		filename = getMyUploadFileName();
		String truename = filename.split("\\.")[0];
		extendname = FileUtil.getExtention(filename);
		//String SaveName = CreateUUID.getUUID() + extendname;
		String SaveName = truename+"_"+DateUtil.getYearAndMonth()+extendname;//update by chencc

		filepath = rootPath + SaveName;
		
//		String webPath = uploadPath+"/"+mark+"/"+DateUtil.getYearAndMonth()+"/"+truename+"_"+DateUtil.getYearAndMonth()+extendname;
		String webPath = uploadPathReport+"/projFile/reportfolder/"+projectNumber+"/"+mark+"/"+truename+"_"+DateUtil.getYearAndMonth()+extendname;
		setSuccess(FileUtil.upload(SaveName, getSession().getServletContext().getRealPath("/")+rootPath, getMyUpload()));
		
		AddFileJS(webPath,SaveName);// 向数据库中添加数据
		
		String jsonString = "{success : true, id_file : '"+fileinfo.getFileid()+"' }";
		
		JsonUtil.responseJsonString(jsonString,"text/html; charset=utf-8");
		
//		return SUCCESS;
	}
	
	public void reUploadReportJS() throws UnsupportedEncodingException {
		
		//删除原有文件
		FileForm finfo = fileFormService.DeleFile(fileid);
		if(finfo != null){
			
			filepath=finfo.getFilepath();
			deleteFile();//删除附件文件
			
			String rootPath = "";
			//获得项目编号
			String projectNumber = "";
			if(businessType=="SmallLoan"||"SmallLoan".equals(businessType)){
				SlSmallloanProject sslp = slSmallloanProjectService.get(Long.parseLong(projId));
				projectNumber = sslp.getProjectNumber();
			}else if(businessType=="Guarantee"||"Guarantee".equals(businessType)){
				GLGuaranteeloanProject gglp = gLGuaranteeloanProjectService.get(Long.parseLong(projId));
				projectNumber = gglp.getProjectNumber();
			}else if(businessType=="Financing"||"Financing".equals(businessType)){
				FlFinancingProject ffp = flFinancingProjectService.get(Long.parseLong(projId));
				projectNumber = ffp.getProjectNumber();
			}else if(businessType=="LeaseFinance"||"LeaseFinance".equals(businessType)){
				FlLeaseFinanceProject flfp = flLeaseFinanceProjectService.get(Long.parseLong(projId));
				projectNumber = flfp.getProjectNumber();
			}else{
				projectNumber = "test";
			}
			rootPath +="attachFiles";
			rootPath += "\\";
			rootPath +="projFile";
			rootPath += "\\";
			rootPath += "reportfolder";
			rootPath += "\\";
			rootPath += projectNumber;
			rootPath += "\\";
			rootPath +=mark;
			rootPath +="\\";
			FileUtil.mkDirectory(getSession().getServletContext().getRealPath("/")+rootPath);
			
			filename = getMyUploadFileName();
			String truename = filename.split("\\.")[0];
			extendname = FileUtil.getExtention(filename);
			//String SaveName = CreateUUID.getUUID() + extendname;
			String SaveName = truename+"_"+DateUtil.getYearAndMonth()+extendname;//update by chencc

			filepath = rootPath + SaveName;
//			String webPath = uploadPath+"/"+mark+"/"+DateUtil.getYearAndMonth()+"/"+truename+"_"+DateUtil.getYearAndMonth()+extendname;
			String webPath = uploadPathReport+"/projFile/reportfolder/"+projectNumber+"/"+mark+"/"+truename+"_"+DateUtil.getYearAndMonth()+extendname;
			setSuccess(FileUtil.upload(SaveName, getSession().getServletContext().getRealPath("/")+rootPath, getMyUpload()));
			
			AddFileJS(webPath,SaveName);// 向数据库中添加数据
			
			String jsonString = "{success : true, id_file : '"+fileinfo.getFileid()+"' }";
			
			JsonUtil.responseJsonString(jsonString,"text/html; charset=utf-8");
		}else{
			JsonUtil.responseJsonString("{success : false}","text/html; charset=utf-8");
		}
		
		
		
//		return SUCCESS;
	}
	/**
	 * 上传文件
	 * 
	 * @return
	 * 
	 */
	public void uploadFilesContract() throws UnsupportedEncodingException {
		String rootPath = "";
		//获得项目编号
		String projectNumber = "";
		projId=this.getRequest().getParameter("projId");
		if(businessType=="SmallLoan"||"SmallLoan".equals(businessType)){
			SlSmallloanProject sslp = slSmallloanProjectService.get(Long.parseLong(projId));
			if(null!=sslp){
				projectNumber = sslp.getProjectNumber();
			}else{
				BpFundProject p=bpFundProjectService.get(Long.parseLong(projId));
				projectNumber=p.getProjectNumber();
			}
		}else if(businessType=="Guarantee"||"Guarantee".equals(businessType)){
			GLGuaranteeloanProject gglp = gLGuaranteeloanProjectService.get(Long.parseLong(projId));
			projectNumber = gglp.getProjectNumber();
		}else if(businessType=="Financing"||"Financing".equals(businessType)){
			FlFinancingProject ffp = flFinancingProjectService.get(Long.parseLong(projId));
			projectNumber = ffp.getProjectNumber();
		}else if(businessType=="LeaseFinance"||"LeaseFinance".equals(businessType)){
			FlLeaseFinanceProject ffp = flLeaseFinanceProjectService.get(Long.parseLong(projId));
			projectNumber = ffp.getProjectNumber();
		}else{
			projectNumber = "test";
		}
		rootPath +="attachFiles/projFile/contfile/"+projectNumber+"-"+contractId+"/";
		FileUtil.mkDirectory(getSession().getServletContext().getRealPath("/")+rootPath);
		
		filename = getMyUploadFileName();
		extendname = FileUtil.getExtention(filename);
		String SaveName = filename;

		filepath = rootPath + SaveName;
		String webPath = uploadPathReport+"/projFile/contfile/"+projectNumber+"-"+contractId+"/"+SaveName;
		
		setSuccess(FileUtil.upload(SaveName, getSession().getServletContext().getRealPath("/")+rootPath, getMyUpload()));
		
		AddFile(webPath,null,null);// 向数据库中添加数据
		
		/**********************更新合同表附件个数*********************/
		procreditContractService.updateContractByIdSql(contractId);
		
		String jsonString = "{success : true, id_file : '"+fileinfo.getFileid()+"' }";
		
		JsonUtil.responseJsonString(jsonString,"text/html; charset=utf-8");
	}
	
	//隐藏删除按钮的下载附件
	public void AjaxGet(){
		List list = fileFormService.getFileList(mark,typeisfile,projId,businessType);
		if(list != null){
			FileForm fileForm =(FileForm)list.get(0);
			JsonUtil.jsonFromObject(fileForm, true);
		}else{
			JsonUtil.responseJsonFailure();
		}
	}
	
	public void DownLoad(){
		FileForm fileEntityFile= fileFormService.getById(fileid);
		
		if(fileEntityFile!=null){
			String fullfilepath=getSession().getServletContext().getRealPath("/")+fileEntityFile.getWebPath();
	        try {
				DownloadFileStream(fullfilepath,fileEntityFile.getFilename() ,true);
				//return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	 private URL remoteFile = null;  
	 private File storeFile = null;  
	//下载文件
	public void DownLoadnew(){
		
		 
		FileForm fileEntityFile= fileFormService.getById(fileid);
		
		try {
			remoteFile =   new URL(AppUtil.getP2pUrl()+"/"+fileEntityFile.getWebPath());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		storeFile = new  File("d:/"+fileEntityFile.getFilename());
		  
//				getAbstractPath
		if(fileEntityFile!=null){
			String fullfilepath=AppUtil.getP2pUrl()+"/"+fileEntityFile.getWebPath();
	        try {
	        	DownloadURLFile.start(0,remoteFile,storeFile);
				//DownloadFileStream(fullfilepath,fileEntityFile.getFilename() ,true);
				//return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	@Resource
	private FTPIoadFileService fTPIoadFileService;
	
	public void DownLoadFilebymark(){
		
		
		HttpServletResponse response = getResponse();
			FileForm fileForm = null;
			fileForm =  (FileForm)fileFormService.ajaxGetFilesList(typeisfile, mark).get(0);
			if(fileForm != null){
//				String path = ElementUtil.getUrl(fileForm.getFilepath(),fileForm.getFilename());
				String path = super.getRequest().getRealPath("/")+fileForm.getFilepath();
				ElementUtil.downloadFile(path, response);
		}
	}
	
	public void DownloadFileStream(String filepath,String fileName,boolean isInline ) throws Exception{
		HttpServletResponse response=getResponse();
		File filed = null ;
		InputStream fis = null ;
		OutputStream toClient = null ;
		try {
			filed = new File(filepath);
			
			String dname=new String(filed.getName().getBytes(),"ISO8859-1");
			
			String dext=FileUtil.getExtentionUpper(dname);
			response.reset();
			String inlineType=isInline ? "inline" : "attachment"; //是否内联附件
			//response.setHeader("Content-Disposition", inlineType + ";filename=\"" + fileName + "\"");
			//修改下载时候出现乱码的情况。update by lu 2011.06.29
			response.setHeader("Content-Disposition", inlineType + ";filename=\"" + new String(fileName.getBytes("gb2312"),"ISO8859-1") + "\"");
			fis= new BufferedInputStream(new FileInputStream(filepath));
			byte [] buffer = new byte [fis.available()];
			fis.read(buffer);
			toClient  = new BufferedOutputStream(response.getOutputStream());
			String filter = fileName.substring(fileName.lastIndexOf("."));
			String contentType = ConvertFileType.returnConvertFileType(filter);
			if("".equals(contentType)){
				contentType="application/octet-stream";
			}
        	response.setContentType(contentType);
        	toClient.write(buffer);
		} catch (FileNotFoundException e) {
			 e.printStackTrace();
		}finally{
			if(null!=fis){
				fis.close();
			}
			if(null!=toClient){
				toClient.close();
			}
			
		}
	}
	/**得到所有的下载列表	jiang*/
	public void getAllFileListExt(){
		List<FileForm> list1 = new ArrayList<FileForm>();
		List<FileForm> list = fileFormService.getAllFileList(mark, typeisfile, projId, businessType);
		if(null!=list && list.size()>0)
		{
			for(FileForm f:list){
				  FileAttach fa=this.fileAttachService.getFileAttachByCsFileId(f.getFileid());
				  if(null!=fa)
				  {
				      f.setFileAttachID(fa.getId().intValue());
				  }
				  list1.add(f);
			}
		}
		JsonUtil.jsonFromList(list1);
	  //System.out.println(list1.get(0).getMinCompressionFilePath());	
	}

	/**得到下载列表	jiang*/
	@SuppressWarnings("unchecked")
	public void getFileListExt(){
	 
		List<FileForm> list1 = new ArrayList<FileForm>();
		List<FileForm> list = fileFormService.getFileList(mark,typeisfile,projId,businessType);
	 
		if(null!=list && list.size()>0)
		{
			for(FileForm f:list){
				  FileAttach fa=this.fileAttachService.getFileAttachByCsFileId(f.getFileid());
				  if(null!=fa)
				  {
				      f.setFileAttachID(fa.getId().intValue());
				  }
				  list1.add(f);
			}
		}
		 JsonUtil.jsonFromList(list);
		
	}
	
	/******************查询某项记录已上传的附件*********************/
	
	/**得到已上传文件列表		jiang*/
	//修改人svn：songwj
	@SuppressWarnings("unchecked")
	public void getUploadedFileList(){
		try {
			
			List list = fileFormService.getFileList(mark,typeisfile,projId,businessType);
			JsonUtil.jsonFromList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获得fileAttach
	public void getFileAttach(){
		List list = fileFormService.getFileList(mark,typeisfile,projId,businessType);
		if(list!=null&& list.size()>0){
			FileForm file = (FileForm)list.get(list.size()-1);
			FileAttach fileAttach = fileAttachService.getFileAttachByCsFileId(file.getFileid());
			String jsonString = "{success:true,fileId:"+fileAttach.getFileId()+",ext:'"+fileAttach.getExt()+"',filePath:'attachFiles/"+fileAttach.getFilePath()+"'}";
			JsonUtil.responseJsonString(jsonString);
		}else{
			JsonUtil.responseJsonString("{success:false}");
		}
	}
	
	public void getfileAttachBycsfileid(){
		FileAttach file = null;
		file = fileAttachService.getFileAttachByCsFileId(fileid);
		if(file != null){
			JsonUtil.jsonFromObject(file, true);
		}else{
			JsonUtil.responseJsonFailure();
		}
	}
	//获得fileAttach,合同相关
	public void getFileAttachContract(){
		FileAttach fileAttach = null;
		List<FileAttach> list =fileAttachService.listByContractId(contractId);
		if(null!=list && list.size()>0){
			fileAttach=list.get(list.size()-1);
		}
		if(fileAttach!=null){
			String jsonString = "{success:true,fileId:"+fileAttach.getFileId()+",ext:'"+fileAttach.getExt()+"',filePath:'attachFiles/"+fileAttach.getFilePath()+"'}";
			JsonUtil.responseJsonString(jsonString);
		}else{
			JsonUtil.responseJsonString("{success:false}");
		}
		
	}
	//获得上传文件个数chencc 
	public void getFilesSize(){
		List<FileForm> list=fileFormService.getFilesSize(mark,typeisfile);
		JsonUtil.jsonFromList(list);
	}
	
	//删除附件 add by chencc
	public void delUploadedFileList(){
		List listFile = fileFormService.getFileList(mark,typeisfile,projId,businessType);
		if(listFile!=null){
			FileForm fileForm=new FileForm();
			String file_Path="";
			int file_id=0;
			for(int i=0;i<listFile.size();i++){
				fileForm=(FileForm)listFile.get(i);
				file_Path=getSession().getServletContext().getRealPath("/")+fileForm.getFilepath();
				java.io.File fileio = new File(file_Path);
				try {
					//boolean a=fileio.isDirectory();
					boolean b=fileio.isFile();
					boolean c=fileio.exists();
					if (b==true && c==true) {
						fileio.delete();
					}
				} catch (Exception e) {
				} finally {
					fileio = null;
					System.gc();
				}
				file_id=fileForm.getFileid();
				fileFormService.DeleFile(file_id);
				
			}
		}
		
	}
	
	/**@deprecated */
	public String getFileList(){
		
		//String basepath = getSession().getServletContext().getRealPath("/");
		//basepath+="images\\";
		
		List<FileForm> list=fileFormService.getFileList(mark,typeisfile,projId,businessType);
		returnhtml="<div id='filelist'>";
		if(list!=null){
			for (Object object : list) {
				int fid=((FileForm)object).getFileid();
				String nodeid="div_nd"+ fid;
				String fname=((FileForm)object).getFilename();
				String f=(((FileForm)object).getFilesize()).toString();
				String fsize="";
				if(f.length()>3){
					f=f.substring(0, f.length()-3)+"."+f.substring(f.length()-3);
					fsize="("+f+"KB)";
				}else {
					fsize="("+f+"bytes)";
				}
				returnhtml+="<DIV id='";
				returnhtml+=nodeid;
				returnhtml+="'><IMG src='";
				returnhtml+=basepath;
				returnhtml+="images/attachment.png' />	&nbsp;";
				returnhtml+=fname;
				returnhtml+=fsize;
				returnhtml+="&nbsp;<A href=\"javascript:deleteAttach('";
				returnhtml+=nodeid;
				returnhtml+="',";
				returnhtml+=fid;
				returnhtml+=",";
				returnhtml+="'p_";
				returnhtml+=nodeid;
				returnhtml+="')\"";
				returnhtml+=">删除</A>";
				returnhtml+="</DIV>";
				//var result = "<div id='filelist'><DIV id='div_nd1'><IMG src='images/attachment.png '/>&nbsp;myeclipse操作技巧.txt&nbsp;<A href='javascript:deleteAttach(div_nd1,1)'>删除</A></DIV></div>";
			}
		}
		returnhtml+="</div>";
		setReturnhtml(returnhtml);
		return SUCCESS;
	}
	
	//删除服务器上的文件的方法
	public void DeleRs() {
		AppUser user = ContextUtil.getCurrentUser();
		if(user!=null){
			FileForm fileinfo = fileFormService.getById(fileid);
			boolean deletePath = false;
			if(user.getId().equals("1")){//超级管理员
				deletePath = true;
			}else{//其他用户删除附件验证是否是本人上传的附件
				if(user.getId().equals(fileinfo.getCreatorid())){
					deletePath = true;
				}
			}
			if(deletePath){
				String flag=this.getRequest().getParameter("flag");//如果是流程中的合同上传功能,则flag=0
				fileinfo = fileFormService.DeleFile(fileid);//删除一个文件，去主表更新一下图片总量
				fileAttachService.removeByFileId(fileid);
				if (null!=fileinfo) {
					if(null!=flag && "0".equals(flag)){//修改合同主表
						String id=fileinfo.getMark().split("\\.")[1];
						ProcreditContract p=procreditContractService.getById(Integer.valueOf(id));
						if(null!=p){
							p.setContractName(null);
							p.setUrl(null);
							procreditContractService.merge(p);
						}
					}
					filepath=getSession().getServletContext().getRealPath("/")+fileinfo.getFilepath();
					if (deleteFile()) {
						JsonUtil.responseJsonSuccess();
					}
					JsonUtil.responseJsonString("{error}");
				}
				JsonUtil.responseJsonString("{error}");
			}
		}
	}
	
	//删除服务器上的文件
	//表记录的主键
	public void removeImg(){
		 //主要分四步：1 删除服务器文件 2 删除cs_file表中的数据  
		FileForm fileEntityFile= fileFormService.getById(fileid);
		if(fileEntityFile != null){
			fileFormService.DeleFile(fileid);//删除cs_file表中的图片记录
			list();
		}
		 jsonString="{success:true}";
	}
	public void DeleMoreRs() {
		String flag=this.getRequest().getParameter("flag");//如果是流程中的合同上传功能,则flag=0
		String[] ids=this.getRequest().getParameterValues("ids");
		for(String id:ids){
			fileinfo = fileFormService.DeleFile(Integer.valueOf(id));
			fileAttachService.removeByFileId(Integer.valueOf(id));
			if (null!=fileinfo) {
				if(null!=flag && "0".equals(flag)){//修改合同主表
					String cId=fileinfo.getMark().split("\\.")[1];
					ProcreditContract p=procreditContractService.getById(Integer.valueOf(cId));
					if(null!=p){
						p.setContractName(null);
						p.setUrl(null);
						procreditContractService.merge(p);
					}
				}
				filepath=getSession().getServletContext().getRealPath("/")+fileinfo.getFilepath();
				if (deleteFile()) {
					JsonUtil.responseJsonSuccess();
				}
				JsonUtil.responseJsonString("{error}");
			}
			JsonUtil.responseJsonString("{error}");
		}
		
	}

	/**
	 *  删除文件 input 输入 output 输出
	 * */
	public boolean deleteFile() {
		//boolean flag = false;
		java.io.File fileio = new File(filepath);
		boolean d=false;
		try {
			//boolean a=fileio.isDirectory();
			boolean b=fileio.isFile();
			boolean c=fileio.exists();
		
			if (b==true && c==true) {
				 d=	fileio.delete();
				return true;
			}
		} catch (Exception e) {
			return false;
		} finally {
			fileio = null;
			System.gc();
			if(d){
				return true;
			}
		}
		return false;
	}

	/**
	 * 多文件删除
	 * 
	 * @return
	 */
	public String deleteFiles() {
		/*
		 * String rootPath = getSession().getServletContext().getRealPath("/");
		 * rootPath += ROOT; File file = new File(rootPath); if(!file.exists()){
		 * file.mkdirs(); } file = null; boolean flag = false; try { for (String
		 * path : paths) { file = new File(rootPath + path); flag =
		 * MyUtils.delFiles(file); if (!flag) { break; } } } catch
		 * (RuntimeException e) { flag = false; e.printStackTrace(); } finally {
		 * file = null; } setSuccess(flag);
		 */
		return SUCCESS;
	}

	public void AddFile(String webPath,String minCompressionFilePath,String compressionFilePath) {
		fileinfo = new FileForm();
		fileAttach = new FileAttach();
		fileinfo.setMark(mark);
		fileinfo.setContentType(myUploadContentType);
		fileinfo.setSetname(setname);
		fileinfo.setFilename(filename);
		fileinfo.setFilepath(filepath);
		fileinfo.setExtendname(extendname);
		fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());
		Long sl=myUpload.length();
		filesize=sl.intValue();
		fileinfo.setFilesize(filesize);
		fileinfo.setCreatorid(creatorid);
		fileinfo.setRemark(remark);
		fileinfo.setWebPath(webPath);
		fileinfo.setMinCompressionFilePath(minCompressionFilePath);
		fileinfo.setCompressionFilePath(compressionFilePath);
		fileinfo.setProjId(projId);
		fileinfo.setBusinessType(businessType);
		fileFormService.save(fileinfo);
		setFileid(fileinfo.getFileid());
		//智维附件表操作start...
		int f_Fileid=fileinfo.getFileid();
		fileAttach.setCsFileId(f_Fileid);
		fileAttach.setFileName(filename);
		fileAttach.setFilePath(webPath.split("attachFiles/")[1]);
		fileAttach.setCreatetime(new Date());
		String extkzm = extendname.split("\\.").length>0?extendname.split("\\.")[1]:"doc";
		fileAttach.setExt(extkzm);
		fileAttach.setFileType("attachFiles/uploads");
		fileAttach.setNote(filesize.toString());
		fileAttach.setCreatorId(Long.parseLong(ContextUtil.getCurrentUser().getId()));
		fileAttach.setCreator(ContextUtil.getCurrentUser().getFullname());
		fileAttach.setTotalBytes(Long.parseLong(filesize.toString())*1024);
		fileAttach.setDelFlag(0);
		fileAttachService.save(fileAttach);
		//智维附件表操作end...
	}
	
	public void AddFileJS(String webPath,String SaveName) {
		String tag0=super.getRequest().getParameter("setname");
		String tag="";
		try {
			tag = URLDecoder.decode(tag0,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			tag ="";
			//e.printStackTrace();
		}

		fileinfo = new FileForm();
		fileAttach = new FileAttach();
		fileinfo.setMark(mark);
		fileinfo.setContentType(myUploadContentType);
		fileinfo.setSetname(tag);
		fileinfo.setFilename(SaveName);
		fileinfo.setFilepath(filepath);
		fileinfo.setExtendname(extendname);
		fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());
		Long sl=myUpload.length();
		filesize=sl.intValue();
		fileinfo.setFilesize(filesize);
		fileinfo.setCreatorid(creatorid);
		fileinfo.setRemark(typeisfile);
		fileinfo.setProjId(projId);
		fileinfo.setBusinessType(businessType);
		fileinfo.setWebPath(webPath);
		fileFormService.save(fileinfo);
		setFileid(fileinfo.getFileid());
		//智维附件表操作start...
		int f_Fileid=fileinfo.getFileid();
		fileAttach.setCsFileId(f_Fileid);
		fileAttach.setFileName(SaveName);
		fileAttach.setFilePath(webPath.split("attachFiles/")[1]);
		fileAttach.setCreatetime(new Date());
		String extkzm = extendname.split("\\.").length>0?extendname.split("\\.")[1]:"doc";
		fileAttach.setExt(extkzm);
		fileAttach.setFileType("attachFiles/uploads");
		fileAttach.setNote(filesize.toString());
		fileAttach.setCreatorId(Long.parseLong(ContextUtil.getCurrentUser().getId()));
		fileAttach.setCreator(ContextUtil.getCurrentUser().getFullname());
		fileAttach.setTotalBytes(Long.parseLong(filesize.toString())*1024);
		fileAttach.setDelFlag(0);
		fileAttachService.save(fileAttach);
		//智维附件表操作end...
	}

	
	public void getSessionid(){
//		setSid(getSession().getId());
//		return SUCCESS;
		sid= getSession().getId();
		JsonUtil.responseJsonString("{\"sid\":\""+sid+"\",\"success\":true}");
	}
	
	//获得调查报告列表
	public void listFiles(){
		List<FileForm> list = null;
		int totalProperty = 0;
		String onlymark="";
		try {
			DocumentTemplet dt =null;
			dt =documentTempletService.getByMarkAndBus(mark, businessType);
			if(dt !=null){
				if(businessType.equals("CompanyInfo")){
					onlymark = "cs_document_companyInfotemplet."+projId;
				}else if(businessType.equals("SlMsgInfo")){
					onlymark = "cs_document_msgInfotemplet."+projId;
				}else{
				 onlymark = "cs_document_templet."+dt.getId();
				}
				list = fileFormService.listFiles(projId,typeisfile,onlymark,businessType);
				if(list != null && list.size()>0){
					totalProperty = list.size();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonUtil.jsonFromList(list, totalProperty);
		
	}
	
	//公共获得上传文件列表
	public void ajaxGetFilesList(){
		List<FileForm> list = null;
		int totalProperty = 0;
		try {
			if(mark != null){
				list = fileFormService.ajaxGetFilesList(typeisfile,mark);
				if(list != null && list.size()>0){
					totalProperty = list.size();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonUtil.jsonFromList(list, totalProperty);
		
	}
	public void getlistbyprojId(){
		List<FileForm> list = null;
		int totalProperty = 0;
		try {
			
				list = fileFormService.getlistbyprojId(projId, businessType);
				if(list != null && list.size()>0){
					totalProperty = list.size();
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonUtil.jsonFromList(list, totalProperty);
		
	}
	//归档确认
	public void ajaxArchiveConfirm() {
		fileFormService.archiveConfirm(fileinfo.getIsArchiveConfirm(), fileinfo.getArchiveConfirmRemark(),fileinfo.getArchiveConfirmDate(),fileinfo.getIsSubmit(),fileinfo.getSubmitDate(),fileinfo.getSubmitRemarks(), fileinfo.getFileid());
	}
	
	/**
	 * @description 获取款项凭证附件(金融担保-项目保前归档节点)
	 * @param projectId
	 * @param remark
	 * @param businessType
	 * @return 
	 */
	public void getArchiveListByBusinessType(){
		
		List<FileForm> list = null;
		int totalProperty = 0;
		
		list = fileFormService.getArchiveListByBusinessType(projId, remark, businessType);
		if(list!=null&&list.size()!=0){
			totalProperty = list.size();
		}
		
		JsonUtil.jsonFromList(list, totalProperty);
	}
	
	
	/**
	 * 
	 * 根据高度和宽度等比例缩放压缩图片
	 * @return
	 * 
	 */
	public String[] compressionFile(String filePath, String fileName, int width, int height,boolean percentage){
		return FileCompressionUtil.reduceImg(filePath, fileName, width, height, percentage);	
	}
	
	/**
	 *合同制作得到已上传附件列表
	 */
	public void getHTUploaded(){
		try {
			List<FileForm> list = fileFormService.getFileList(mark,typeisfile,projId,businessType);
			Gson gson=new Gson();
			StringBuffer buff=new StringBuffer("{success:true,result:");
			buff.append(gson.toJson(list));
			buff.append("}");
			JsonUtil.responseJsonString(buff.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getFileCount(){
		String mark=this.getRequest().getParameter("mark");
		Long count = fileFormService.findFileCount(mark);
		setJsonString(count.toString());
		return SUCCESS;
	}
	/**得到已上传文件列表		jiang*/
	//isMobile  by gaomimi
	@SuppressWarnings("unchecked")
	public String getUploadedFileListisMobile(){
			Integer start=Integer.parseInt(this.getRequest().getParameter("start"));
			Integer limit=Integer.parseInt(this.getRequest().getParameter("limit"));
			List list = fileFormService.getFileListisMobile(start,limit,mark,typeisfile,projId,businessType);
			List sizelist = fileFormService.getFileList(mark,typeisfile,projId,businessType);
			StringBuffer buff = new StringBuffer("{\"success\":true,\"totalCounts\":");
			buff.append(sizelist==null?0:sizelist.size()).append(",\"topics\":");
			Gson gson=new Gson();
			buff.append(gson.toJson(list));
			buff.append("}");
			jsonString=buff.toString();
			return SUCCESS;
	}

}
