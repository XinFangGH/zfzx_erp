package com.credit.proj.mortgage.morservice.action;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.credit.proj.entity.CsProcreditMortgageReceivables;
import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageBusiness;
import com.credit.proj.entity.ProcreditMortgageBusinessandlive;
import com.credit.proj.entity.ProcreditMortgageCar;
import com.credit.proj.entity.ProcreditMortgageDroit;
import com.credit.proj.entity.ProcreditMortgageEducation;
import com.credit.proj.entity.ProcreditMortgageEnterprise;
import com.credit.proj.entity.ProcreditMortgageHouse;
import com.credit.proj.entity.ProcreditMortgageHouseground;
import com.credit.proj.entity.ProcreditMortgageIndustry;
import com.credit.proj.entity.ProcreditMortgageMachineinfo;
import com.credit.proj.entity.ProcreditMortgageOfficebuilding;
import com.credit.proj.entity.ProcreditMortgagePerson;
import com.credit.proj.entity.ProcreditMortgageProduct;
import com.credit.proj.entity.ProcreditMortgageStockownership;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageGlobal;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.mortgage.business.service.BusinessServMort;
import com.credit.proj.mortgage.businessandlive.service.BusinessandliveService;
import com.credit.proj.mortgage.company.service.CompanyMService;
import com.credit.proj.mortgage.droit.service.DroitService;
import com.credit.proj.mortgage.dutyperson.service.DutypersonService;
import com.credit.proj.mortgage.education.service.EducationService;
import com.credit.proj.mortgage.house.service.HouseService;
import com.credit.proj.mortgage.houseground.service.HousegroundService;
import com.credit.proj.mortgage.industry.service.IndustryService;
import com.credit.proj.mortgage.machineinfo.service.MachineinfoService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.officebuilding.service.OfficebuildingService;
import com.credit.proj.mortgage.product.service.ProductService;
import com.credit.proj.mortgage.receivables.service.CsProcreditMortgageReceivablesService;
import com.credit.proj.mortgage.stockownership.service.StockownershipService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.OrganizationService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author weipengfei
 * 
 */
@SuppressWarnings("all")
public class MortgageAction extends CreditBaseAction {
	private static final long serialVersionUID = 1L;
	private MortgageService mortgageService;
	private ProcreditMortgage procreditMortgage;
	
	@Resource
	private FileFormService fileFormService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService; //企业贷款
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private VehicleService vehicleService;
	@Resource
	private StockownershipService stockownershipService;
	@Resource
	private CompanyMService companyMService;
	@Resource
	private MachineinfoService machineinfoService;
	
	@Resource
	private DutypersonService dutypersonService;
	@Resource
	private ProductService productService;
	@Resource
	private HouseService houseService;
	@Resource
	private OfficebuildingService officebuildingService;
	@Resource
	private HousegroundService housegroundService;
	@Resource
	private BusinessServMort businessServMort;
	@Resource
	private BusinessandliveService businessandliveService;
	@Resource
	private EducationService educationService;
	@Resource
	private IndustryService industryService;
	@Resource
	private DroitService droitService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private SlCompanyMainService slCompanyMainService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService; //add by gaoqingrui 
	@Resource
	private CsProcreditMortgageReceivablesService csProcreditMortgageReceivablesService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	private int start ;
	private int limit ;
	
	private String mortgageIds;
	private String isPledged;//只需判断是否为空，不需该值作为查询参数。
	
	
	protected String jsonString="{success:true}";
	private String successResultValue="/jsonString.jsp";
	
	//列表查询条件参数
	private String assureofname="";//所有权人
	private int typeid;//反担保物类型
	//private int assuretypeid;//担保类型
	private int statusid;//登记状态
	//private String mortgagename="";//反担保物名称
	private double finalpriceOne;//最终价格1
	private double finalpriceTow;//最终价格2
	
	//2011.03.04
	private String projnum="";//项目编号
	private String enterprisename="";//企业名称
	
	private int beforeOrMiddleType;//1代表的是保前的修改，2代表的是保中的修改
	private String sort;//中文排序的字段名
	private String dir;//ASC,DESC
	
	private Long projectId;
	private String businessType;
	private int mortgageid;
	private String personType;
	private Integer customerEnterpriseName;
	private Integer manufacturer;
	private ProcreditMortgageCar procreditMortgageCar;
	private Enterprise enterprise;
	private Integer targetEnterpriseName;
	private ProcreditMortgageStockownership procreditMortgageStockownership;
	private Person person;
	private Integer legalpersonid;//企业表中的法人id===个人表id
	private Integer cardtype;
	private String type;
	private String companyId;
	private ProcreditMortgagePerson procreditMortgagePerson;
	private ProcreditMortgageEnterprise procreditMortgageEnterprise;
	private ProcreditMortgageMachineinfo procreditMortgageMachineinfo;
	private ProcreditMortgageProduct procreditMortgageProduct;
	private ProcreditMortgageHouse procreditMortgageHouse;
	private ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding;
	private ProcreditMortgageHouseground procreditMortgageHouseground;
	private ProcreditMortgageBusiness procreditMortgageBusiness;
	private ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive;
	private ProcreditMortgageEducation procreditMortgageEducation;
	private ProcreditMortgageIndustry procreditMortgageIndustry;
	private ProcreditMortgageDroit procreditMortgageDroit;
	private CsProcreditMortgageReceivables csProcreditMortgageReceivables;
	private String tag;//"dzy"表示抵质押担保，"bz"表示保证担保
	
	public String ajaxGetMortgageAllDataByProjectId(){
		Boolean isReadOnly = "".equals(super.getRequest().getParameter("isReadOnly"))||"false".equals(super.getRequest().getParameter("isReadOnly"))?false:true;
		List list = new ArrayList();
		String htType=this.getRequest().getParameter("htType");
		list = mortgageService.ajaxGetMortgageAllDataByProjectId(projectId,businessType,htType);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		
		if(list==null||list.size()==0){
			//do nothing...
		}else{
			if("SmallLoan".equals(businessType)){//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditDictionary vp = (VProcreditDictionary) list.get(i);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getCategoryId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
					    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						/*pm = mortgageService.seeMortgage(ProcreditMortgage.class, vp.getId());
						if(pm.getCategoryId() != null){
					    	VProcreditContractCategory vpcc= contractDraftService.getVProcreditContractCategory(pm.getCategoryId());
					    	if(vp!= null && vpcc != null){
					    		vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getContractId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    	}
					    	
					    }*/
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			}else if("Guarantee".equals(businessType)){
				for(int j=0;j<list.size();j++){
					VProcreditDictionaryGuarantee vp = (VProcreditDictionaryGuarantee) list.get(j);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int q=0;q<clist.size();q++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(q);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getCategoryId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
				    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if("Financing".equals(businessType)){//先判断再循环
				for(int i=0;i<list.size();i++){/*
					VProcreditMortgageFinance vp = (VProcreditMortgageFinance) list.get(i);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId().intValue());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId().intValue(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getCategoryId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
					    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				*/}
			}
		}
		
		//Gson gson=new Gson();
		//Gson gson=new GsonBuilder().create();
		//buff.append(gson.toJson(list, type));
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public void queryMortgage(){
		try {
			mortgageService.queryMortgage(assureofname, typeid, projnum, statusid, enterprisename, finalpriceOne, finalpriceTow, start, limit,sort,dir);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//节点26 修改页面显示数据的查询方法
	public void ajaxMortgageManageSeeNode26(){
		
		int mortgageid = 0;
		if(getRequest().getParameter("id")!=null){
			mortgageid=Integer.parseInt(getRequest().getParameter("id"));
			mortgageService.ajaxMortgageManageSeeNode26(mortgageid);
		}else{
			JsonUtil.responseJsonString("{success:false,msg:'出现错误,未获取到id'}");
		}
	}
	
	//节点26 修改的方法
	public void ajaxUpdateMortgageManageNode26(){
		int mortgageid = 0;
		if(getRequest().getParameter("id")!=null){
			mortgageid=Integer.parseInt(getRequest().getParameter("id"));
		}
		mortgageService.ajaxUpdateMortgageManageNode26(procreditMortgage,mortgageid,beforeOrMiddleType);
	}
	
	//为删除主表,次表添加事务的删除方法
	public void deleteAllObjectDatas(){
		/*int rid=0;
		int typeid=0;
		
		if(getRequest().getParameter("id")!=null){
			rid=Integer.parseInt(getRequest().getParameter("id"));
		}
		if(getRequest().getParameter("typeid")!=null){
			typeid=Integer.parseInt(getRequest().getParameter("typeid"));
		}*/
		
		try{
			mortgageService.deleteAllObjectDatas(ProcreditMortgage.class, mortgageIds);
			//mortgageService.deleteAllObjectDatas(ProcreditMortgage.class, rid, typeid);
		}catch(Exception e){
			e.printStackTrace();
		}
		//return SUCCESS;
	}
	
	//异步更新记录
	public void ajaxArchiveConfirm() {
		try{	
			mortgageService.archiveConfirm(procreditMortgage.getId(), procreditMortgage);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateMortgage(){
		//抵质押物状态
		if(null!=procreditMortgage.getIsTransact() && procreditMortgage.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("SmallLoan")){
			procreditMortgage.setMortgageStatus("smallybl");
		}else if(null!=procreditMortgage.getIsTransact() && procreditMortgage.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("Guarantee")){
			procreditMortgage.setMortgageStatus("bzybl");
		}else if(null!=procreditMortgage.getIsTransact() && procreditMortgage.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("Financing")){
			procreditMortgage.setMortgageStatus("financingybl");
		}
		String transactPerson="";
		if(null!=procreditMortgage.getTransactPersonId() && !"".equals(procreditMortgage.getTransactPersonId()))
		{
		
			String [] appstr=procreditMortgage.getTransactPersonId().split(",");
			Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				transactPerson += s.getFamilyName()+",";
			}
			transactPerson = transactPerson.substring(0, transactPerson.length()-1);
		}
		String unchainPerson="";
		if(null!=procreditMortgage.getUnchainPersonId() && !"".equals(procreditMortgage.getUnchainPersonId()) )
		{
		
			String [] appstr=procreditMortgage.getUnchainPersonId().split(",");
			Set<AppUser> userSet1 = this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet1){
				unchainPerson += s.getFamilyName()+",";
			}
			unchainPerson = unchainPerson.substring(0, unchainPerson.length()-1);
		}
		procreditMortgage.setTransactPerson(transactPerson);
		procreditMortgage.setUnchainPerson(unchainPerson);
		ProcreditMortgage procreditMortgage1=mortgageService.updateProcreditMortgage(mortgageid, procreditMortgage);
	
		if(null!=procreditMortgage1.getIsTransact() && procreditMortgage1.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("SmallLoan") && null!=procreditMortgage.getIsunchain() && procreditMortgage.getIsunchain()==true){
			procreditMortgage1.setMortgageStatus("smallyjc");
		}else if(null!=procreditMortgage1.getIsTransact() && procreditMortgage1.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("Guarantee") && null!=procreditMortgage.getIsunchain() && procreditMortgage.getIsunchain()==true){
			procreditMortgage1.setMortgageStatus("guaranteeyjc");
		}
		ArrayList<ProcreditMortgage> list=new ArrayList<ProcreditMortgage>();
		list.add(procreditMortgage1);
		try {
			mortgageService.updateMortgages(list);
			/*if(null!=procreditMortgage1.getBusinessType() && procreditMortgage1.getBusinessType().equals("Financing")){
				SlMortgageFinancing slMortgageFinancing=slMortgageFinancingService.getMortgageByMortId(mortgageid);
				slMortgageFinancing.setIsTransact(procreditMortgage1.getIsTransact());
				slMortgageFinancing.setTransactDate(procreditMortgage1.getTransactDate());
				slMortgageFinancing.setIsunchain(procreditMortgage1.getIsunchain());
				slMortgageFinancing.setUnchainremark(procreditMortgage1.getUnchainremark());
				SlMortgageFinancing slm = slMortgageFinancingService.update(slMortgageFinancing);
				if(slMortgageFinancing.getIsunchain()!=null){
					mortgageService.updateIsPledged(slm.getMortId().intValue(), slMortgageFinancing.getIsunchain()==true?(short)0:(short)1);
				}
			}*/
			jsonString="{success:true}";
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public String updateMortgage1(){
		//抵质押物状态
		if(null!=procreditMortgage.getIsTransact() && procreditMortgage.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("SmallLoan")){
			procreditMortgage.setMortgageStatus("smallybl");
		}else if(null!=procreditMortgage.getIsTransact() && procreditMortgage.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("Guarantee")){
			procreditMortgage.setMortgageStatus("bzybl");
		}else if(null!=procreditMortgage.getIsTransact() && procreditMortgage.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("Financing")){
			procreditMortgage.setMortgageStatus("financingybl");
		}
		String transactPerson="";
		if(null!=procreditMortgage.getTransactPersonId() && !"".equals(procreditMortgage.getTransactPersonId()))
		{
		
			String [] appstr=procreditMortgage.getTransactPersonId().split(",");
			Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				transactPerson += s.getFamilyName()+",";
			}
			transactPerson = transactPerson.substring(0, transactPerson.length()-1);
		}
		String unchainPerson="";
		if(null!=procreditMortgage.getUnchainPersonId() && !"".equals(procreditMortgage.getUnchainPersonId()) )
		{
		
			String [] appstr=procreditMortgage.getUnchainPersonId().split(",");
			Set<AppUser> userSet1 = this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet1){
				unchainPerson += s.getFamilyName()+",";
			}
			unchainPerson = unchainPerson.substring(0, unchainPerson.length()-1);
		}
		procreditMortgage.setTransactPerson(transactPerson);
		procreditMortgage.setUnchainPerson(unchainPerson);
		ProcreditMortgage procreditMortgage1=mortgageService.updateProcreditMortgage(mortgageid, procreditMortgage);
	
		if(null!=procreditMortgage1.getIsTransact() && procreditMortgage1.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("SmallLoan") && null!=procreditMortgage.getIsunchain() && procreditMortgage.getIsunchain()==true){
			procreditMortgage1.setMortgageStatus("smallyjc");
		}else if(null!=procreditMortgage1.getIsTransact() && procreditMortgage1.getIsTransact()==true && null!=procreditMortgage.getBusinessType() && procreditMortgage.getBusinessType().equals("Guarantee") && null!=procreditMortgage.getIsunchain() && procreditMortgage.getIsunchain()==true){
			procreditMortgage1.setMortgageStatus("guaranteeyjc");
		}
		ArrayList<ProcreditMortgage> list=new ArrayList<ProcreditMortgage>();
		list.add(procreditMortgage1);
		try {
			mortgageService.updateMortgages(list);
			/*if(null!=procreditMortgage1.getBusinessType() && procreditMortgage1.getBusinessType().equals("Financing")){
				SlMortgageFinancing slMortgageFinancing=slMortgageFinancingService.getMortgageByMortId(mortgageid);
				slMortgageFinancing.setIsTransact(procreditMortgage1.getIsTransact());
				slMortgageFinancing.setTransactDate(procreditMortgage1.getTransactDate());
				slMortgageFinancing.setIsunchain(procreditMortgage1.getIsunchain());
				slMortgageFinancing.setUnchainremark(procreditMortgage1.getUnchainremark());
				SlMortgageFinancing slm = slMortgageFinancingService.update(slMortgageFinancing);
				if(slMortgageFinancing.getIsunchain()!=null){
					mortgageService.updateIsPledged(slm.getMortId().intValue(), slMortgageFinancing.getIsunchain()==true?(short)0:(short)1);
				}
			}*/
			jsonString="{success:true}";
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getMortgageFinance(){

		QueryFilter filter=new QueryFilter(getRequest());
		//获取查询面板的查询条件值
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		String mortgageType = filter.getRequest().getParameter("Q_mortgageType_SN_EQ");
		String mortgageName = filter.getRequest().getParameter("Q_mortgageName_S_LK");
		String finalpriceOne = filter.getRequest().getParameter("Q_finalEstimatePrice_BD_GE");
		String finalpriceTow = filter.getRequest().getParameter("Q_finalEstimatePrice_BD_LE");
		
		List list = new ArrayList();
		//PagingBean pb=new PagingBean(start, limit);
		
		list = mortgageService.getMortgageFinance(isPledged, mortgageType, mortgageName, finalpriceOne, finalpriceTow, start, limit);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		
		/*if(list==null||list.size()==0){
			//do nothing...
		}else{
			
		}*/
		
		//Gson gson=new Gson();
		//Gson gson=new GsonBuilder().create();
		//buff.append(gson.toJson(list, type));
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	
	}
	
	//融资抵质押物假删除
	public void deleteMortgageFalse(){
		mortgageService.deleteMortgageFalse(mortgageIds);
	}
	
	public String getMortgageList(){
		QueryFilter filter=new QueryFilter(getRequest());
		//获取查询面板的查询条件值
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		String businessType = filter.getRequest().getParameter("businessType");
		String projectName="";
		if(null!=filter.getRequest().getParameter("projectName")){
			projectName=filter.getRequest().getParameter("projectName");
		}
		String projectNum="";
		if(null!=filter.getRequest().getParameter("projectNum")){
			projectNum = filter.getRequest().getParameter("projectNum");
		}
		String mortgageStatus=filter.getRequest().getParameter("mortgageStatus");
		String mortgageName="";
		if(null!=filter.getRequest().getParameter("mortgageName")){
		   mortgageName = filter.getRequest().getParameter("mortgageName");
		}
		String mortgageType=filter.getRequest().getParameter("mortgageType");
		String danbaoType = filter.getRequest().getParameter("danbaoType");
		String minMoney=filter.getRequest().getParameter("minMoney");
		String maxMoney = filter.getRequest().getParameter("maxMoney");
        List list = mortgageService.getMortgageList(businessType,mortgageName,projectName,projectNum,mortgageType,danbaoType,minMoney,maxMoney,start,limit,0,mortgageStatus,companyId);

        List totalList = mortgageService.getMortgageList(businessType,mortgageName,projectName,projectNum,mortgageType,danbaoType,minMoney,maxMoney,start,limit,1,mortgageStatus,companyId);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(totalList==null?0:totalList.get(0)).append(",result:");
		if(list!=null){

			if("SmallLoan".equals(businessType)){//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditDictionary vp = (VProcreditDictionary) list.get(i);
					if(null!=vp.getProjid()){
						try {
							SlSmallloanProject sl = (SlSmallloanProject) creditBaseDao.getById(SlSmallloanProject.class, vp.getProjid());
							if(null!=sl && null!=sl.getCompanyId()){
						    	Organization organization=organizationService.get(sl.getCompanyId());
								if(null!=organization){
									vp.setCompanyName(organization.getOrgName());
								}
						    }
						} catch (Exception e) {
							
							e.printStackTrace();
						}	
					}
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							VProcreditContract vpcc = (VProcreditContract)clist.get(0);
							
						}
						/*if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
							}
						}*/
						BpFundProject fundProject=bpFundProjectService.getByProjectId(vp.getProjid(), Short.valueOf("0"));
						if(fundProject!=null){
							vp.setFundProjectId(fundProject.getId());
						}
						vp.setContractCount(contractCount);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			}else if("Guarantee".equals(businessType)){
				for(int j=0;j<list.size();j++){
					VProcreditDictionaryGuarantee vp = (VProcreditDictionaryGuarantee) list.get(j);
					if(null!=vp.getProjid()){
						try {
							GLGuaranteeloanProject gl = (GLGuaranteeloanProject) creditBaseDao.getById(GLGuaranteeloanProject.class, vp.getProjid());
							if(null!=gl && null!=gl.getCompanyId()){
						    	Organization organization=organizationService.get(gl.getCompanyId());
								if(null!=organization){
									vp.setCompanyName(organization.getOrgName());
								}
						    }
						} catch (Exception e) {
							
							e.printStackTrace();
						}	
					}
//					if(vp.getLoanDate()!=null){
//						long days=0;
//						if(null!=vp.getIsTransact() && vp.getIsTransact()==true && null!=vp.getTransactDate()){
//							Date loanDate=vp.getLoanDate();
//							Date transactDate=vp.getTransactDate();
//							days=(transactDate.getTime()-loanDate.getTime())/(1000*60*60*24);
//							vp.setDays(days);
//						}else if(null==vp.getIsTransact() || vp.getIsTransact()==false){
//							Date date=new Date();
//							Date loanDate=vp.getLoanDate();
//							days=(date.getTime()-loanDate.getTime())/(1000*60*60*24);
//							vp.setDays(days);
//						}
//						
//						
//					}
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int q=0;q<clist.size();q++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(q);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
							}
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if("Financing".equals(businessType)){/*//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditMortgageFinance vp = (VProcreditMortgageFinance) list.get(i);
					if(null!=vp.getProjid()){
						try {
							FlFinancingProject fl = (FlFinancingProject) creditBaseDao.getById(FlFinancingProject.class, vp.getProjid());
							if(null!=fl && null!=fl.getCompanyId()){
						    	Organization organization=organizationService.get(fl.getCompanyId());
								if(null!=organization){
									vp.setCompanyName(organization.getOrgName());
								}
						    }
						} catch (Exception e) {
							
							e.printStackTrace();
						}	
					}
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId().intValue());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId().intValue(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		
							}
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			*/}else if("LeaseFinance".equals(businessType)){//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditMortgageLeaseFinance vp = (VProcreditMortgageLeaseFinance) list.get(i);
					if(null!=vp.getProjid()){
						try {
							FlLeaseFinanceProject fl = (FlLeaseFinanceProject) creditBaseDao.getById(FlLeaseFinanceProject.class, vp.getProjid());
							if(null!=fl && null!=fl.getCompanyId()){
						    	Organization organization=organizationService.get(fl.getCompanyId());
								if(null!=organization){
									vp.setCompanyName(organization.getOrgName());
								}
						    }
						} catch (Exception e) {
							
							e.printStackTrace();
						}	
					}
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId().intValue());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId().intValue(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		
							}
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			}
		
		JSONSerializer json=new JSONSerializer();
		if(null!=businessType && businessType.equals("Guarantee")){
		    json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate","loanDate"});
		}else{
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate"});
		}
		buff.append(json.serialize(list));
		}else{
			buff.append("[]");
		}
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
    public String getMortgageInfo(){
    	try {
			procreditMortgage=mortgageService.seeMortgage(ProcreditMortgage.class, mortgageid);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(procreditMortgage));
		sb.append("}");
		jsonString=sb.toString();
    	return SUCCESS;
    }
    public String getMorByPersonType(){
    	List<ProcreditMortgage> list=mortgageService.getList(Integer.parseInt(personType), Integer.parseInt(assureofname));
    	for(ProcreditMortgage p:list){
    		if(p.getAssuretypeid()!=null){
    			Dictionary dic=dictionaryService.get(p.getAssuretypeid().longValue());
    			p.setLessDatumRecord(dic.getItemValue());
    		}
    		if(p.getBusinessType().equals("SmallLoan")){
    			SlSmallloanProject sp=slSmallloanProjectService.get(p.getProjid());
    			p.setRemarks(sp.getProjectName());
    		}
    		if(p.getBusinessType().equals("Guarantee")){
    			GLGuaranteeloanProject gp=glGuaranteeloanProjectService.get(p.getProjid());
    			p.setRemarks(gp.getProjectName());
    		}
    		if(p.getBusinessType().equals("Financing")){
    			FlFinancingProject fp=flFinancingProjectService.get(p.getProjid());
    			p.setRemarks(fp.getProjectName());
    		}
    		if(p.getBusinessType().equals("LeaseFinance")){
    			FlLeaseFinanceProject fp=flLeaseFinanceProjectService.get(p.getProjid());
    			p.setRemarks(fp.getProjectName());
    		}
    		Date date=new Date();
    		if(p.getUnchaindate()!=null){
    			if(date.getTime()>p.getUnchaindate().getTime()){
    				p.setNeedDatumList("已结束");
    			}else{
    				p.setNeedDatumList("进行中");
    			}
    		}else{
    			p.setNeedDatumList("");
    		}
    	}
    	
    	StringBuffer buff = new StringBuffer("{success:true,result:");
    	JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
    	return SUCCESS;
    }
    
    public String getMortgageOfDY(){
		Boolean isReadOnly = "".equals(super.getRequest().getParameter("isReadOnly"))||"false".equals(super.getRequest().getParameter("isReadOnly"))?false:true;
    	List list=mortgageService.getMortgageOfDZ(projectId, businessType);
    	StringBuffer buff = new StringBuffer("{success:true,result:");
    	if(null!=list && list.size()>0){

			if("SmallLoan".equals(businessType)){//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditDictionary vp = (VProcreditDictionary) list.get(i);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
					    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vp.getId(),null,null,null);//获取办理文件
						List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vp.getId(),null,null,null);//获取 解除文件
						vp.setHavingTransactFile(null!=list1&&list1.size()>0);
						vp.setHavingUnchainFile(null!=list2&&list2.size()>0);
						/*pm = mortgageService.seeMortgage(ProcreditMortgage.class, vp.getId());
						if(pm.getCategoryId() != null){
					    	VProcreditContractCategory vpcc= contractDraftService.getVProcreditContractCategory(pm.getCategoryId());
					    	if(vp!= null && vpcc != null){
					    		vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getContractId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    	}
					    	
					    }*/
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			}else if("Guarantee".equals(businessType)){
				for(int j=0;j<list.size();j++){
					VProcreditDictionaryGuarantee vp = (VProcreditDictionaryGuarantee) list.get(j);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int q=0;q<clist.size();q++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(q);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
				    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vp.getId(),null,null,null);//获取办理文件
						List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vp.getId(),null,null,null);//获取 解除文件
						vp.setHavingTransactFile(null!=list1&&list1.size()>0);
						vp.setHavingUnchainFile(null!=list2&&list2.size()>0);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if("Financing".equals(businessType)){/*//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditMortgageFinance vp = (VProcreditMortgageFinance) list.get(i);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId().intValue());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId().intValue(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
					    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vp.getId(),null,null,null);//获取办理文件
						List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vp.getId(),null,null,null);//获取 解除文件
						vp.setHavingTransactFile(null!=list1&&list1.size()>0);
						vp.setHavingUnchainFile(null!=list2&&list2.size()>0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			*/}else if("LeaseFinance".equals(businessType)){
				for(int j=0;j<list.size();j++){
					VProcreditMortgageLeaseFinance vp = (VProcreditMortgageLeaseFinance) list.get(j);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "thirdContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int q=0;q<clist.size();q++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(q);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
				    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vp.getId(),null,null,null);//获取办理文件
						List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vp.getId(),null,null,null);//获取 解除文件
						vp.setHavingTransactFile(null!=list1&&list1.size()>0);
						vp.setHavingUnchainFile(null!=list2&&list2.size()>0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
    	}
    	JSONSerializer json=new JSONSerializer();
    	json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate","recieveDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
    	return SUCCESS;
    }
    public void addMortgageOfDY(){
    	
    	try {
    		procreditMortgage.setAssureofname(customerEnterpriseName);
        	
        	procreditMortgage.setContractid(0);
        	DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        	if(null==procreditMortgage.getCreateTime()){
        		procreditMortgage.setCreateTime(df.parse(df.format(new Date())));
        	}
	    	if(null!=procreditMortgage){
		    	if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==1){
		    		if(manufacturer==null||"".equals(manufacturer)||"null".equals(manufacturer)){
						procreditMortgageCar.setManufacturer(0);
					}else{
						procreditMortgageCar.setManufacturer(manufacturer);
					}
		    		if(null==procreditMortgage.getId()){
		    			vehicleService.addVehicle(procreditMortgageCar, procreditMortgage);
		    		}else{
		    			vehicleService.updateVehicle(procreditMortgage.getId(), procreditMortgageCar, procreditMortgage);
		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==2){
		    		enterprise.setId(targetEnterpriseName);
					procreditMortgageStockownership.setCorporationname(targetEnterpriseName);
					procreditMortgageStockownership.setBusinessType(procreditMortgage.getBusinessType());
					if(null==procreditMortgage.getId()){
						stockownershipService.addStockownership(procreditMortgageStockownership,procreditMortgage,enterprise);
					}else{
						stockownershipService.updateStockownership(procreditMortgage.getId(),procreditMortgageStockownership,procreditMortgage,enterprise);
					}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==5){
		    		if(null==procreditMortgage.getId()){
		    			machineinfoService.addMachineinfo(procreditMortgageMachineinfo,procreditMortgage);
		    		}else{
		    			machineinfoService.updateMachineinfo(procreditMortgage.getId(),procreditMortgageMachineinfo,procreditMortgage);

		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==6){
		    		if(null==procreditMortgage.getId()){
		    			productService.addProduct(procreditMortgageProduct,procreditMortgage);
		    		}else{
		    			productService.updateProduct(procreditMortgage.getId(),procreditMortgageProduct,procreditMortgage);

		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && (procreditMortgage.getMortgagenametypeid()==7 || procreditMortgage.getMortgagenametypeid()==15 || procreditMortgage.getMortgagenametypeid()==16 || procreditMortgage.getMortgagenametypeid()==17)){
		    		if(null==procreditMortgage.getId()){
		    			houseService.addHouse(procreditMortgageHouse,procreditMortgage);
		    		}else{
		    			houseService.updateHouse(procreditMortgage.getId(),procreditMortgageHouse,procreditMortgage);
		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==8){
		    		if(null==procreditMortgage.getId()){
		    			officebuildingService.addOfficebuilding(procreditMortgageOfficebuilding,procreditMortgage);
		    		}else{
		    			officebuildingService.updateOfficebuilding(procreditMortgage.getId(),procreditMortgageOfficebuilding,procreditMortgage);

		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==9){
		    		if(null==procreditMortgage.getId()){
		    			housegroundService.addHouseground(procreditMortgageHouseground,procreditMortgage);
		    		}else{
		    			housegroundService.updateHouseground(procreditMortgage.getId(),procreditMortgageHouseground,procreditMortgage);

		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==10){
		    		if(null==procreditMortgage.getId()){
		    			businessServMort.addBusiness(procreditMortgageBusiness,procreditMortgage);
		    		}else{
		    			businessServMort.updateBusiness(procreditMortgage.getId(),procreditMortgageBusiness,procreditMortgage);

		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==11){
		    		if(null==procreditMortgage.getId()){
		    			businessandliveService.addBusinessandlive(procreditMortgageBusinessandlive,procreditMortgage);
		    		}else{
		    			businessandliveService.updateBusinessandlive(procreditMortgage.getId(),procreditMortgageBusinessandlive,procreditMortgage);

		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==12){
		    		if(null==procreditMortgage.getId()){
		    			educationService.addEducation(procreditMortgageEducation,procreditMortgage);
		    		}else{
		    			educationService.updateEducation(procreditMortgage.getId(),procreditMortgageEducation,procreditMortgage);
		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==13){
		    		if(null==procreditMortgage.getId()){
		    			industryService.addIndustry(procreditMortgageIndustry,procreditMortgage);
		    		}else{
		    			industryService.updateIndustry(procreditMortgage.getId(),procreditMortgageIndustry,procreditMortgage);
		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==14){
		    		if(null==procreditMortgage.getId()){
		    			droitService.addDroit(procreditMortgageDroit,procreditMortgage);
		    		}else{
		    			droitService.updateDroit(procreditMortgage.getId(),procreditMortgageDroit,procreditMortgage);
		    		}
		    	}else if(null!=procreditMortgage.getMortgagenametypeid() && procreditMortgage.getMortgagenametypeid()==18){
		    		csProcreditMortgageReceivablesService.addReceivables(csProcreditMortgageReceivables, procreditMortgage);
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    public String getMortgageByType(){
    	try {
    		String objectType=this.getRequest().getParameter("objectType");
			procreditMortgage=(ProcreditMortgage) creditBaseDao.getById(ProcreditMortgage.class, mortgageid);
		
			String assureofname="";
			if(procreditMortgage.getPersonType()==602){
				if(procreditMortgage.getBusinessType().equals("Financing")){
					SlCompanyMain com=slCompanyMainService.get(procreditMortgage.getAssureofname().longValue());
				    if(null!=com){
				    	assureofname=com.getCorName();
				    }
				}else{
					enterprise=(Enterprise) creditBaseDao.getById(Enterprise.class, procreditMortgage.getAssureofname());
					if(null!=enterprise){
						assureofname=enterprise.getEnterprisename();
					}
				}
			}else if(procreditMortgage.getPersonType()==603){
				if(procreditMortgage.getBusinessType().equals("Financing")){
					SlPersonMain spm=slPersonMainService.get(procreditMortgage.getAssureofname().longValue());
				    if(null!=spm){
				    	assureofname=spm.getName();
				    }
				}else{
					person=(Person) creditBaseDao.getById(Person.class, procreditMortgage.getAssureofname());
					if(null!=person){
						assureofname=person.getName();
					}
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("procreditMortgage", procreditMortgage);
			map.put("assureofname", assureofname);
			//VCarDic vCarDic=null;
			if(typeid==1 ){
				//List list=vehicleService.seeVehicleForUpdate(mortgageid);
				List list=vehicleService.getVehicleByObjectType(mortgageid, objectType);
				if(null!=list && list.size()>0){
					procreditMortgageCar=(ProcreditMortgageCar)list.get(0);
					/*if(null!=procreditMortgageCar){
						if(null!=procreditMortgageCar.getManufacturer()){
							vCarDic=(VCarDic) creditBaseDao.getById(VCarDic.class, procreditMortgageCar.getManufacturer());
						}
					}*/
				}
				map.put("procreditMortgageCar", procreditMortgageCar);
				//map.put("vCarDic", vCarDic);
	    	}else if(typeid==2){
	    		//List list=stockownershipService.seeStockownershipForUpdate(mortgageid);
	    		List list=stockownershipService.getStockownershipByObjectType(mortgageid, objectType);
	    		EnterpriseView enterpriseView=null;
	    		if(null!=list && list.size()>0){
	    			procreditMortgageStockownership=(ProcreditMortgageStockownership) list.get(0);
	    			if(null!=procreditMortgageStockownership){
	    				if(null!=procreditMortgageStockownership.getCorporationname()){
	    					enterpriseView=(EnterpriseView) creditBaseDao.getById(EnterpriseView.class, procreditMortgageStockownership.getCorporationname());
	    				}
	    			}
	    		}
	    		map.put("procreditMortgageStockownership", procreditMortgageStockownership);
	    		map.put("enterprise", enterpriseView);
	    	}else if(typeid==5){
	    		//List list=machineinfoService.seeMachineinfoForUpdate(mortgageid);
	    		List list=machineinfoService.getMachineinfoByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageMachineinfo=(ProcreditMortgageMachineinfo) list.get(0);
	    		}
	    		map.put("procreditMortgageMachineinfo", procreditMortgageMachineinfo);
	    	}else if(typeid==6){
	    		//List list=productService.seeProductForUpdate(mortgageid);
	    		List list=productService.getProductByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageProduct=(ProcreditMortgageProduct) list.get(0);
	    		}
	    		map.put("procreditMortgageProduct", procreditMortgageProduct);
	    	}else if(typeid==7 || typeid==15 || typeid==16 || typeid==17){
	    		//List list=houseService.seeHouseForUpdate(mortgageid);
	    		List list=houseService.seeHouseByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageHouse=(ProcreditMortgageHouse) list.get(0);
	    		}
	    		map.put("procreditMortgageHouse", procreditMortgageHouse);
	    	}else if(typeid==8){
	    		//List list=officebuildingService.seeOfficebuildingForUpdate(mortgageid);
	    		List list=officebuildingService.seeOfficebuildingByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageOfficebuilding=(ProcreditMortgageOfficebuilding) list.get(0);
	    		}
	    		map.put("procreditMortgageOfficebuilding", procreditMortgageOfficebuilding);
	    	}else if(typeid==9){
	    		//List list=housegroundService.seeHousegroundForUpdate(mortgageid);
	    		List list=housegroundService.seeHousegroundByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageHouseground=(ProcreditMortgageHouseground) list.get(0);
	    		}
	    		map.put("procreditMortgageHouseground", procreditMortgageHouseground);
	    	}else if(typeid==10){
	    		//List list=businessServMort.seeBusinessForUpdate(mortgageid);
	    		List list=businessServMort.seeBusinessByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageBusiness=(ProcreditMortgageBusiness) list.get(0);
	    		}
	    		map.put("procreditMortgageBusiness", procreditMortgageBusiness);
	    	}else if(typeid==11){
	    		//List list=businessandliveService.seeBusinessAndLiveForUpdate(mortgageid);
	    		List list=businessandliveService.seeBusinessandliveByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageBusinessandlive=(ProcreditMortgageBusinessandlive) list.get(0);
	    		}
	    		map.put("procreditMortgageBusinessandlive", procreditMortgageBusinessandlive);
	    	}else if(typeid==12){
	    		//List list=educationService.seeEducationForUpdate(mortgageid);
	    		List list=educationService.seeEducationByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageEducation=(ProcreditMortgageEducation) list.get(0);
	    		}
	    		map.put("procreditMortgageEducation", procreditMortgageEducation);
	    	}else if(typeid==13){
	    		//List list=industryService.seeIndustryForUpdate(mortgageid);
	    		List list=industryService.seeIndustryByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageIndustry=(ProcreditMortgageIndustry) list.get(0);
	    		}
	    		map.put("procreditMortgageIndustry", procreditMortgageIndustry);
	    	}else if(typeid==14){
	    		//List list=droitService.seeDroitForUpdate(mortgageid);
	    		List list=droitService.seeDroitByObjectType(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageDroit=(ProcreditMortgageDroit) list.get(0);
	    		}
	    		map.put("procreditMortgageDroit", procreditMortgageDroit);
	    	}else if(typeid==18){
	    		//List list=droitService.seeDroitForUpdate(mortgageid);
	    		List<CsProcreditMortgageReceivables> list=csProcreditMortgageReceivablesService.listByMortgageId(mortgageid, objectType);
	    		if(null!=list && list.size()>0){
	    			csProcreditMortgageReceivables=(CsProcreditMortgageReceivables) list.get(0);
	    		}
	    		map.put("csProcreditMortgageReceivables", csProcreditMortgageReceivables);
	    	}
	    	if(procreditMortgage.getPersonType()==602){
	    		List list=companyMService.seeCompanyForUpdate(mortgageid);
	    		String tel="";
	    		if(procreditMortgage.getBusinessType().equals("Financing")){
		    		SlCompanyMain sl=null;
		    		if(null!=procreditMortgage && null!=procreditMortgage.getAssureofname()){
		    			sl=slCompanyMainService.get(procreditMortgage.getAssureofname().longValue());
		    			if(null!=sl && null!=sl.getPersonMainId()){
		    				SlPersonMain sp=slPersonMainService.get(sl.getPersonMainId());
		    				if(null!=sp){
		    					tel=sp.getLinktel();
		    				}
		    			}
		    		}
		    		map.put("enterpriseView", sl);
	    		}else{
	    			EnterpriseView enterpriseView=null;
		    		if(null!=procreditMortgage && null!=procreditMortgage.getAssureofname()){
		    			enterpriseView=(EnterpriseView) creditBaseDao.getById(EnterpriseView.class, procreditMortgage.getAssureofname());
		    			if(null!=enterpriseView && null!=enterpriseView.getLegalpersonid()){
		    				person=(Person) creditBaseDao.getById(Person.class, enterpriseView.getLegalpersonid());
		    				if(null!=person){
		    					tel=person.getCellphone();
		    				}
		    			}
		    		}
		    		map.put("enterpriseView", enterpriseView);
	    		}
	    		if(null!=list && list.size()>0){
	    			procreditMortgageEnterprise=(ProcreditMortgageEnterprise) list.get(0);
	    		}
	    		map.put("procreditMortgageEnterprise", procreditMortgageEnterprise);
	    		
	    		map.put("tel", tel);
	    	}else if(procreditMortgage.getPersonType()==603){
	    		if(procreditMortgage.getBusinessType().equals("Financing")){
	    			if(null!=procreditMortgage && null!=procreditMortgage.getAssureofname()){
	    				SlPersonMain sp=slPersonMainService.get(procreditMortgage.getAssureofname().longValue());
	    				if(null!=sp && null!=sp.getCardtype()){
	    					Dictionary dic=dictionaryService.get(sp.getCardtype().longValue());
	    					if(null!=dic){
	    						map.put("cardtypevalue", dic.getItemValue());
	    					}
	    				}
	    				map.put("person", sp);
	    			}
	    		}else{
		    		VPersonDic vp=null;
		    		if(null!=procreditMortgage && null!=procreditMortgage.getAssureofname()){
		    			vp=(VPersonDic) creditBaseDao.getById(VPersonDic.class, procreditMortgage.getAssureofname());
		    		}
		    		map.put("person", vp);
	    		}
	    		List list=dutypersonService.seeDutypersonForUpdate(mortgageid);
	    		if(null!=list && list.size()>0){
	    			procreditMortgagePerson=(ProcreditMortgagePerson) list.get(0);
	    		}
	    		map.put("procreditMortgagePerson", procreditMortgagePerson);
	    		
	    	}
			JsonUtil.jsonFromObject(map, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return SUCCESS;
    }
    public String getMortgageOfBZ(){
		Boolean isReadOnly = "".equals(super.getRequest().getParameter("isReadOnly"))||"false".equals(super.getRequest().getParameter("isReadOnly"))?false:true;
    	List list=mortgageService.getMortgageOfBZ(projectId, businessType);
    	StringBuffer buff = new StringBuffer("{success:true,result:");
    	if(null!=list && list.size()>0){

			if("SmallLoan".equals(businessType)){//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditDictionary vp = (VProcreditDictionary) list.get(i);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "baozContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
					    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						/*pm = mortgageService.seeMortgage(ProcreditMortgage.class, vp.getId());
						if(pm.getCategoryId() != null){
					    	VProcreditContractCategory vpcc= contractDraftService.getVProcreditContractCategory(pm.getCategoryId());
					    	if(vp!= null && vpcc != null){
					    		vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getContractId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    	}
					    	
					    }*/
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			}else if("Guarantee".equals(businessType)){
				for(int j=0;j<list.size();j++){
					VProcreditDictionaryGuarantee vp = (VProcreditDictionaryGuarantee) list.get(j);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "baozContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int q=0;q<clist.size();q++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(q);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
				    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if("Financing".equals(businessType)){/*//先判断再循环
				for(int i=0;i<list.size();i++){
					VProcreditMortgageFinance vp = (VProcreditMortgageFinance) list.get(i);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId().intValue());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "baozContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId().intValue(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int j=0;j<clist.size();j++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(j);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getCategoryId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
					    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
			*/}else if("LeaseFinance".equals(businessType)){
				for(int j=0;j<list.size();j++){
					VProcreditMortgageLeaseFinance vp = (VProcreditMortgageLeaseFinance) list.get(j);
					int fileCounts = fileFormService.getFileListCount("cs_procredit_mortgage."+vp.getId());
					vp.setFileCounts(fileCounts);
					//查询合同分类视图信息 chencc
					ProcreditMortgage pm = null;
					try {
						String contractType = "baozContract";
						List clist = vProcreditContractService.getVProcreditContractCategoryList(vp.getId(),businessType,contractType);
						int contractCount = clist.size();
						if(clist != null && clist.size()>0){
							StringBuffer cbuffer = new StringBuffer();
							for(int q=0;q<clist.size();q++){
								String contractContent = "";
								VProcreditContract vpcc = (VProcreditContract)clist.get(q);
								vp.setContractCategoryTypeText(vpcc.getContractCategoryTypeText());
					    		vp.setContractCategoryText(vpcc.getContractCategoryText());
					    		vp.setContractId(vpcc.getId());
					    		vp.setIsLegalCheck(vpcc.getIsLegalCheck());
					    		vp.setCategoryId(vpcc.getId());
					    		vp.setTemptId(vpcc.getTemptId());
					    		vp.setIssign(vpcc.getIssign());
					    		vp.setSignDate(vpcc.getSignDate());
					    		vp.setFileCount(vpcc.getFileCount());
					    		if(isReadOnly == false){
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",60,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"编辑合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"reMakeContract(\'"+businessType+"\',\'"+projectId+"\',\'"+vp.getMortgagename()+"\',\'"+vp.getId()+"\',\'"+vpcc.getId()+"\',\'"+vp.getCategoryId()+"\',\'"+vpcc.getTemptId()+"\')\">编辑合同</font></a></td>"+
									"<td width=\"70\"  align=right><font color=blue><a title=\"删除合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"deleteContract("+vpcc.getId()+")\">删除合同</font></a></td>"+
									"</tr>";
					    		}else{
					    			contractContent ="<tr onMouseOver=\"bgColor=\'#FFFFFF\';\" onMouseOut=\"bgColor=\'\';\"><td width=\"120\" align=right><b>合同类型:</b></td><td width=\"150\"  style=\"word-break:break-all\">"+vpcc.getContractCategoryTypeText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同名称:</b></td><td width=\"230\" style=\"word-break:break-all\">"+vpcc.getContractCategoryText()+"</td>"+
									"<td width=\"70\"  align=right><b>合同附件:</b></td><td><a title=\"下载合同附件\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadFiles("+vpcc.getId()+",0,\'"+projectId+"\',\'"+businessType+"\')\" ><font color=blue>"+vpcc.getFileCount()+"</font></a></td>"+
									"<td width=\"70\"  align=right><a title=\"下载合同\" style =\"TEXT-DECORATION:underline;cursor:pointer\" onclick=\"downloadContract("+vpcc.getId()+")\" ><font color=blue>下载合同</font></a></td>"+
									"</tr>";
					    		}
				    		cbuffer.append(contractContent);
							}
							vp.setContractContent(cbuffer.toString());
						}else{
							vp.setContractContent("");
						}
						vp.setContractCount(contractCount);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
    	}
    	JSONSerializer json=new JSONSerializer();
    	json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate","recieveDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
    	return SUCCESS;
    }
    public void addMortgageOfBZ(){
    	  
    	
    	try {
    		procreditMortgage.setAssureofname(customerEnterpriseName);
        	
        	procreditMortgage.setContractid(0);
        	DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        	procreditMortgage.setCreateTime(df.parse(df.format(new Date())));
	    	if(null!=procreditMortgage){
		    	if(null!=procreditMortgage.getPersonType() && procreditMortgage.getPersonType()==602){

		    		if(null==procreditMortgage.getId()){
		    			companyMService.addCompany(procreditMortgageEnterprise,procreditMortgage,enterprise,person,customerEnterpriseName,legalpersonid);
		    		}else{
		    			enterprise.setId(customerEnterpriseName);//把选择的企业的id放到实体
		    			enterprise.setLegalpersonid(legalpersonid);//更新法人id
		    			companyMService.updateCompany(procreditMortgage.getId(),procreditMortgageEnterprise,procreditMortgage,enterprise,person,legalpersonid);
		    		}
		    	}else if(null!=procreditMortgage.getPersonType() && procreditMortgage.getPersonType()==603){
		    		if(null==procreditMortgage.getId()){
		    			dutypersonService.addDutyperson(procreditMortgagePerson,procreditMortgage,person,customerEnterpriseName,cardtype);
		    		}else{
		    			dutypersonService.updateDutyperson(procreditMortgage.getId(),procreditMortgagePerson,procreditMortgage,person,customerEnterpriseName,cardtype);
		    		}
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public String getMortgageFinanceList(){
    	List list=mortgageService.getMortgageFinanceList(isPledged, type, start, limit);
    	long count=mortgageService.getMortgageFinanceCount(isPledged, type);
    	StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(count).append(",result:");
	
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"enregisterdate","unchaindate","transactDate","signDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
    	return SUCCESS;
    }
    public void getAssuremoney(){
    	SlSmallloanProject project=slSmallloanProjectService.get(projectId);
    	if(null!=project){
    		JsonUtil.responseJsonString("{success:true,projectMoney:"+project.getProjectMoney()+"}");
    	}
    }
    
    public String getMortageStore() {
    	List list = null;
    	if(!businessType.equals("Assignment")){
	    	if(tag.equals("dzy")) {
	    		list = mortgageService.getMortgageOfDZ(projectId, businessType);
	    	}else if(tag.equals("bz")) {
	    		list = mortgageService.getMortgageOfBZ(projectId, businessType);
	    	}
    	}
    	List<VProcreditDictionary> vdList = null;
    	List<VProcreditDictionaryGuarantee> vdgList = null;
    	//List<VProcreditMortgageFinance> vmfList = null;
    	List<VProcreditMortgageLeaseFinance> vmflList = null;
    	List<VProcreditMortgageGlobal> vpmgList = null;
    	
    	StringBuffer buff = new StringBuffer("[");
		if(null!=list && list.size()>0){
			if("SmallLoan".equals(businessType)){
	    		vdList = list;
	    		for (VProcreditDictionary vd : vdList) {
					buff.append("[").append(vd.getId()).append(",'").append(tag.equals("dzy")?vd.getMortgagepersontypeforvalue():vd.getMortgagename()).append("'],");
				}
			}else if("Guarantee".equals(businessType)){
				vdgList = list;
				for (VProcreditDictionaryGuarantee vdg : vdgList) {
					buff.append("[").append(vdg.getId()).append(",'").append(tag.equals("dzy")?vdg.getMortgagepersontypeforvalue():vdg.getMortgagename()).append("'],");
				}
			}else if("Financing".equals(businessType)){
				/*vmfList = list;
				for (VProcreditMortgageFinance vmf : vmfList) {
					buff.append("[").append(vmf.getId()).append(",'").append(tag.equals("dzy")?vmf.getMortgagepersontypeforvalue():vmf.getMortgagename()).append("'],");
				}*/
			}else if("LeaseFinance".equals(businessType)){
				vmflList = list;
				for (VProcreditMortgageLeaseFinance vmf : vmflList) {
					buff.append("[").append(vmf.getId()).append(",'").append(tag.equals("dzy")?vmf.getMortgagepersontypeforvalue():vmf.getMortgagename()).append("'],");
				}
			}else if("Pawn".equals(businessType)){
				vmflList = list;
				for (VProcreditMortgageGlobal vmf : vpmgList) {
					buff.append("[").append(vmf.getId()).append(",'").append(tag.equals("dzy")?vmf.getMortgagepersontypeforvalue():vmf.getMortgagename()).append("'],");
				}
			}
			if (list.size() > 0) {
				buff.deleteCharAt(buff.length() - 1);
			}
		}
		buff.append("]");
		jsonString=buff.toString();
    	return SUCCESS;
    }
    
    public String deleteByMortgageIds(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				mortgageService.deleteByMortgageIds(Long.valueOf(id));
			}
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
    
	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Integer getTargetEnterpriseName() {
		return targetEnterpriseName;
	}

	public void setTargetEnterpriseName(Integer targetEnterpriseName) {
		this.targetEnterpriseName = targetEnterpriseName;
	}

	public ProcreditMortgageStockownership getProcreditMortgageStockownership() {
		return procreditMortgageStockownership;
	}

	public void setProcreditMortgageStockownership(
			ProcreditMortgageStockownership procreditMortgageStockownership) {
		this.procreditMortgageStockownership = procreditMortgageStockownership;
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public MortgageService getMortgageService() {
		return mortgageService;
	}
	public void setMortgageService(MortgageService mortgageService) {
		this.mortgageService = mortgageService;
	}
	
	
	public ProcreditMortgage getProcreditMortgage() {
		return procreditMortgage;
	}
	public void setProcreditMortgage(ProcreditMortgage procreditMortgage) {
		this.procreditMortgage = procreditMortgage;
	}
	
	
	public String getAssureofname() {
		return assureofname;
	}
	public void setAssureofname(String assureofname) {
		this.assureofname = assureofname;
	}
	
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public double getFinalpriceOne() {
		return finalpriceOne;
	}
	public void setFinalpriceOne(double finalpriceOne) {
		this.finalpriceOne = finalpriceOne;
	}
	public double getFinalpriceTow() {
		return finalpriceTow;
	}
	public void setFinalpriceTow(double finalpriceTow) {
		this.finalpriceTow = finalpriceTow;
	}
	public int getStatusid() {
		return statusid;
	}
	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}
	public String getProjnum() {
		return projnum;
	}
	public void setProjnum(String projnum) {
		this.projnum = projnum;
	}
	public String getEnterprisename() {
		return enterprisename;
	}
	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public int getBeforeOrMiddleType() {
		return beforeOrMiddleType;
	}

	public void setBeforeOrMiddleType(int beforeOrMiddleType) {
		this.beforeOrMiddleType = beforeOrMiddleType;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getSuccessResultValue() {
		return successResultValue;
	}

	public void setSuccessResultValue(String successResultValue) {
		this.successResultValue = successResultValue;
	}

	public String getMortgageIds() {
		return mortgageIds;
	}

	public void setMortgageIds(String mortgageIds) {
		this.mortgageIds = mortgageIds;
	}

	public int getMortgageid() {
		return mortgageid;
	}

	public void setMortgageid(int mortgageid) {
		this.mortgageid = mortgageid;
	}

	public String getIsPledged() {
		return isPledged;
	}

	public void setIsPledged(String isPledged) {
		this.isPledged = isPledged;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public Integer getCustomerEnterpriseName() {
		return customerEnterpriseName;
	}

	public void setCustomerEnterpriseName(Integer customerEnterpriseName) {
		this.customerEnterpriseName = customerEnterpriseName;
	}

	public Integer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Integer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ProcreditMortgageCar getProcreditMortgageCar() {
		return procreditMortgageCar;
	}

	public void setProcreditMortgageCar(ProcreditMortgageCar procreditMortgageCar) {
		this.procreditMortgageCar = procreditMortgageCar;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getLegalpersonid() {
		return legalpersonid;
	}

	public void setLegalpersonid(Integer legalpersonid) {
		this.legalpersonid = legalpersonid;
	}

	public ProcreditMortgageEnterprise getProcreditMortgageEnterprise() {
		return procreditMortgageEnterprise;
	}

	public void setProcreditMortgageEnterprise(
			ProcreditMortgageEnterprise procreditMortgageEnterprise) {
		this.procreditMortgageEnterprise = procreditMortgageEnterprise;
	}
	public Integer getCardtype() {
		return cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}

	public ProcreditMortgagePerson getProcreditMortgagePerson() {
		return procreditMortgagePerson;
	}

	public void setProcreditMortgagePerson(
			ProcreditMortgagePerson procreditMortgagePerson) {
		this.procreditMortgagePerson = procreditMortgagePerson;
	}

	public ProcreditMortgageMachineinfo getProcreditMortgageMachineinfo() {
		return procreditMortgageMachineinfo;
	}

	public void setProcreditMortgageMachineinfo(
			ProcreditMortgageMachineinfo procreditMortgageMachineinfo) {
		this.procreditMortgageMachineinfo = procreditMortgageMachineinfo;
	}
	public ProcreditMortgageProduct getProcreditMortgageProduct() {
		return procreditMortgageProduct;
	}

	public void setProcreditMortgageProduct(
			ProcreditMortgageProduct procreditMortgageProduct) {
		this.procreditMortgageProduct = procreditMortgageProduct;
	}

	public ProcreditMortgageHouse getProcreditMortgageHouse() {
		return procreditMortgageHouse;
	}

	public void setProcreditMortgageHouse(
			ProcreditMortgageHouse procreditMortgageHouse) {
		this.procreditMortgageHouse = procreditMortgageHouse;
	}
	public ProcreditMortgageOfficebuilding getProcreditMortgageOfficebuilding() {
		return procreditMortgageOfficebuilding;
	}

	public void setProcreditMortgageOfficebuilding(
			ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding) {
		this.procreditMortgageOfficebuilding = procreditMortgageOfficebuilding;
	}

	public ProcreditMortgageHouseground getProcreditMortgageHouseground() {
		return procreditMortgageHouseground;
	}

	public void setProcreditMortgageHouseground(
			ProcreditMortgageHouseground procreditMortgageHouseground) {
		this.procreditMortgageHouseground = procreditMortgageHouseground;
	}

	public ProcreditMortgageBusiness getProcreditMortgageBusiness() {
		return procreditMortgageBusiness;
	}

	public void setProcreditMortgageBusiness(
			ProcreditMortgageBusiness procreditMortgageBusiness) {
		this.procreditMortgageBusiness = procreditMortgageBusiness;
	}

	public ProcreditMortgageBusinessandlive getProcreditMortgageBusinessandlive() {
		return procreditMortgageBusinessandlive;
	}

	public void setProcreditMortgageBusinessandlive(
			ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive) {
		this.procreditMortgageBusinessandlive = procreditMortgageBusinessandlive;
	}

	public ProcreditMortgageEducation getProcreditMortgageEducation() {
		return procreditMortgageEducation;
	}

	public void setProcreditMortgageEducation(
			ProcreditMortgageEducation procreditMortgageEducation) {
		this.procreditMortgageEducation = procreditMortgageEducation;
	}

	public ProcreditMortgageIndustry getProcreditMortgageIndustry() {
		return procreditMortgageIndustry;
	}

	public void setProcreditMortgageIndustry(
			ProcreditMortgageIndustry procreditMortgageIndustry) {
		this.procreditMortgageIndustry = procreditMortgageIndustry;
	}
	public ProcreditMortgageDroit getProcreditMortgageDroit() {
		return procreditMortgageDroit;
	}

	public void setProcreditMortgageDroit(
			ProcreditMortgageDroit procreditMortgageDroit) {
		this.procreditMortgageDroit = procreditMortgageDroit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public CsProcreditMortgageReceivables getCsProcreditMortgageReceivables() {
		return csProcreditMortgageReceivables;
	}

	public void setCsProcreditMortgageReceivables(
			CsProcreditMortgageReceivables csProcreditMortgageReceivables) {
		this.csProcreditMortgageReceivables = csProcreditMortgageReceivables;
	}
	
}
