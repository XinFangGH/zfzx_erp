package com.zhiwei.credit.action.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXml4SwfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.util.MoneyFormat;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.finance.ProfitRateMaintenance;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.ReportParam;
import com.zhiwei.credit.model.system.ReportTemplate;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.finance.ProfitRateMaintenanceService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.FileAttachService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.ReportParamService;
import com.zhiwei.credit.service.system.ReportTemplateService;

/**
 * 
 * @author csx
 * 
 */
@SuppressWarnings("unchecked")
public class ReportTemplateAction extends BaseAction {
	private String uploadPath = AppUtil.getAppAbsolutePath() + "/attachFiles/";
	private Long reportId;
	private String reportKey;
	private ReportTemplate reportTemplate;
	
	@Resource
	private ReportTemplateService reportTemplateService;
	@Resource
	private ReportParamService reportParamService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private  GLGuaranteeloanProjectService gLGuaranteeloanProjectService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private VFundDetailService vFundDetailService;
	@Resource
	private ProfitRateMaintenanceService profitRateMaintenanceService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private PlMmOrderChildrenOrService plMmOrderChildrenOrService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	
	private static DataSource dataSource=null;
	private static Connection conn=null;

	/**
	 * 显示列表
	 */
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<ReportTemplate> list = reportTemplateService.getAll(filter);
		Type type = new TypeToken<List<ReportTemplate>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public String checkKey(){
		/*String Q_reportKey_S_EQ=getRequest().getParameter("Q_reportKey_S_EQ");
		Type type = new TypeToken<List<ReportTemplate>>() {}.getType();*/
		QueryFilter filter = new QueryFilter(getRequest());
//		List<ReportTemplate> list = reportTemplateService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				reportTemplate = reportTemplateService.get(new Long(id));
				List<ReportParam> list = reportParamService.findByRepTemp(new Long(id));
				for (ReportParam rp : list) {
					reportParamService.remove(rp);
				}
				java.io.File file = new java.io.File(uploadPath+ reportTemplate.getReportLocation());
				deleteFile(file);
				if(null!=file.getParentFile().listFiles() && file.getParentFile().listFiles().length==0){
					java.io.File parent=file.getParentFile();
					deleteFile(parent);
				}
				reportTemplateService.remove(new Long(id));
				FileAttach fa=fileAttachService.getByPath(reportTemplate.getReportLocation());
				if(null!=fa){
					fileAttachService.remove(fa);
				}
			}
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		ReportTemplate reportTemplate = reportTemplateService.get(reportId);
		Gson gson = new Gson();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(reportTemplate));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		// 通过后台直接设置报表上传时间和修改时间
		if (reportTemplate.getReportId() == null) {
			reportTemplate.setCreatetime(new Date());
			reportTemplate.setUpdatetime(new Date());
			reportTemplateService.save(reportTemplate);
		} else {
			ReportTemplate old=reportTemplateService.get(reportTemplate.getReportId());
			//删除旧文件ljf
			if(!old.getReportLocation().toString().trim().equals(reportTemplate.getReportLocation().toString().trim())){
//				java.io.File file = new java.io.File(uploadPath+ old.getReportLocation());
//				deleteFile(file.getParentFile());
			}
			try {
				BeanUtil.copyNotNullProperties(old, reportTemplate);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			old.setUpdatetime(new Date());
			reportTemplateService.save(old);
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	
	/**
	 * 删除旧文件
	 * 黎剑发2010.08.05
	 */
	private void deleteFile(File file){ 
		if(file.exists()){ 
		    if(file.isFile()){ 
		    	file.delete(); 
		    }else if(file.isDirectory()){ 
		    	File files[] = file.listFiles(); 
		    	for(int i=0;i<files.length;i++){ 
		    		this.deleteFile(files[i]); 
		    	} 
		    } 
		    file.delete(); 
		}
	} 

	/**
	 * 加载搜索条件框
	 */
	public String load() {
		String strReportId = getRequest().getParameter("reportId");
		if (StringUtils.isNotEmpty(strReportId)) {
			List<ReportParam> list = reportParamService.findByRepTemp(new Long(strReportId));
//			JSONSerializer serializer=new JSONSerializer();
			Gson gson = new Gson();
			StringBuffer sb = new StringBuffer();
			sb.append(gson.toJson(list));
			setJsonString("{success:true,data:" + sb.toString() + "}");
		} else {
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}

	/**
	 * 提交数据
	 */
	/*public String submit() {
		Map map = getRequest().getParameterMap();
		Iterator it = map.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			sb.append("&" + key + "=" + value[0]);
		}
		setJsonString("{success:true,data:'" + sb.toString() + "'}");
		return SUCCESS;
	}*/
	
	public String submit() {
		java.text.SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-mm-dd");
		Map map = getRequest().getParameterMap();
		Iterator it = map.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			String v=value[0];
			if(v==null||v.equals("")){
				v="%";
			}else{
				try {
					dateformat.parse(v.trim());
				} catch (ParseException e) {
					v="%"+v.trim()+"%";
				}
			}
			sb.append("&" + key + "=" + v);
		}
		setJsonString("{success:true,data:'" + sb.toString() + "'}");
		return SUCCESS;
	}
	
	public String submit1() {
//		java.text.SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-mm-dd");
		Map map = getRequest().getParameterMap();
		Iterator it = map.entrySet().iterator();
		Map tm = new TreeMap();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			String v=value[0];
			tm.put(key, v);
		}
		Iterator it1 = tm.entrySet().iterator();
		saveproject(reportKey);
		String isinti=this.getRequest().getParameter("isinti");
		if( isinti !=null){
			dynamicreprot(reportId);
		}
		setJsonString("{success:true,data:'" + searchbyreportkey(it1,reportKey).toString() + "'}");
		return SUCCESS;
	}
	
	public void dynamicreprot(Long reportId1){
		try {
			String roleType=ContextUtil.getRoleTypeSession();
			Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
			String reportPath=reportTemplateService.get(reportId1).getReportLocation().split("jasper")[0]+"jrxml";
			String rootPath=uploadPath;
			File fullPath = new File(rootPath + "/" + reportPath);
			String reportPath1=fullPath.getPath();
			if(reportKey.equals("projectdetail")){  
			     JasperDesign jasperDesign =  JRXmlLoader.load(reportPath1);
				 if(flag==false || roleType.endsWith("business")){  //业务角色business
				    JRDesignBand columnHeaderBand = (JRDesignBand )jasperDesign.getColumnHeader();//获取ColumnHeader拦
				    JRDesignStaticText columnHeaderField1 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header1");
				    columnHeaderField1.setWidth(0);
				          
			        JRDesignStaticText columnHeaderField2 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header2");
			        columnHeaderField2.setX(columnHeaderField1.getX());
			        JRDesignStaticText columnHeaderField3 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header3");
			        columnHeaderField3.setX(columnHeaderField1.getX()+columnHeaderField2.getWidth());
			        JRDesignStaticText columnHeaderField4 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header4");
			        columnHeaderField4.setX(columnHeaderField3.getX()+columnHeaderField3.getWidth());
			        JRDesignStaticText columnHeaderField5 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header5");
			        columnHeaderField5.setX(columnHeaderField4.getX()+columnHeaderField4.getWidth());
			        JRDesignStaticText columnHeaderField6 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header6");
			        columnHeaderField6.setX(columnHeaderField5.getX()+columnHeaderField5.getWidth());
			        JRDesignStaticText columnHeaderField7 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header7");
			        columnHeaderField7.setX(columnHeaderField6.getX()+columnHeaderField6.getWidth());
			        JRDesignStaticText columnHeaderField8 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header8");
			        columnHeaderField8.setX(columnHeaderField7.getX()+columnHeaderField7.getWidth());
			        JRDesignStaticText columnHeaderField9 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header9");
			        columnHeaderField9.setX(columnHeaderField8.getX()+columnHeaderField8.getWidth());
			        JRDesignStaticText columnHeaderField10 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header10");
			        columnHeaderField10.setX(columnHeaderField9.getX()+columnHeaderField9.getWidth());
			        JRDesignStaticText columnHeaderField11 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header11");
			        columnHeaderField11.setX(columnHeaderField10.getX()+columnHeaderField10.getWidth());
				        
				    int widthall=columnHeaderField1.getWidth()+columnHeaderField2.getWidth()+columnHeaderField3.getWidth()
				          +columnHeaderField4.getWidth()+columnHeaderField5.getWidth()+columnHeaderField6.getWidth()
				          +columnHeaderField7.getWidth()+columnHeaderField8.getWidth()+columnHeaderField9.getWidth()
				          +columnHeaderField10.getWidth()+columnHeaderField11.getWidth();
				    JRDesignStaticText columnHeaderreprotcount= (JRDesignStaticText)columnHeaderBand.getElementByKey("headerreportcount");
				          
				    JRDesignBand titleBand = (JRDesignBand )jasperDesign.getTitle();
					JRDesignStaticText title=(JRDesignStaticText)titleBand.getElementByKey("title");
					title.setWidth(widthall+columnHeaderreprotcount.getWidth());
					JRDesignTextField search= (JRDesignTextField )titleBand.getElementByKey("search");
					search.setWidth(widthall+columnHeaderreprotcount.getWidth());
						
					jasperDesign.setPageWidth(widthall+columnHeaderreprotcount.getWidth()+jasperDesign.getLeftMargin()+jasperDesign.getRightMargin());
					jasperDesign.setColumnWidth(widthall);
				          
					JRDesignBand detailBand = (JRDesignBand )jasperDesign.getDetail();//获取Detail拦
					JRDesignTextField detailField1= (JRDesignTextField )detailBand.getElementByKey("detail1");
					detailField1.setWidth(0);
		          
					JRDesignTextField detailField2= (JRDesignTextField )detailBand.getElementByKey("detail2");
					detailField2.setX(detailField1.getX());
					JRDesignTextField detailField3= (JRDesignTextField )detailBand.getElementByKey("detail3");
					detailField3.setX(detailField2.getX()+detailField2.getWidth());
				          
					JRDesignTextField detailField4= (JRDesignTextField )detailBand.getElementByKey("detail4");
					detailField4.setX(detailField3.getX()+detailField3.getWidth());
					JRDesignTextField detailField5= (JRDesignTextField )detailBand.getElementByKey("detail5");
					detailField5.setX(detailField4.getX()+detailField4.getWidth());
					JRDesignTextField detailField6= (JRDesignTextField )detailBand.getElementByKey("detail6");
					detailField6.setX(detailField5.getX()+detailField5.getWidth());
					JRDesignTextField detailField7= (JRDesignTextField )detailBand.getElementByKey("detail7");
					detailField7.setX(detailField6.getX()+detailField6.getWidth());
					JRDesignTextField detailField8= (JRDesignTextField )detailBand.getElementByKey("detail8");
					detailField8.setX(detailField7.getX()+detailField7.getWidth());
					JRDesignTextField detailField9= (JRDesignTextField )detailBand.getElementByKey("detail9");
					detailField9.setX(detailField8.getX()+detailField8.getWidth());
					JRDesignTextField detailField10= (JRDesignTextField )detailBand.getElementByKey("detail10");
					detailField10.setX(detailField9.getX()+detailField9.getWidth());
					JRDesignTextField detailField11= (JRDesignTextField )detailBand.getElementByKey("detail11");
				    detailField11.setX(detailField10.getX()+detailField10.getWidth());
				          
				    JRDesignBand summaryBand = (JRDesignBand )jasperDesign.getSummary();
				    JRDesignStaticText summaryField1 = (JRDesignStaticText)summaryBand.getElementByKey("summary1");
				    summaryField1.setWidth(0);
				    JRDesignStaticText summaryField2 = (JRDesignStaticText)summaryBand.getElementByKey("summary2");
				    summaryField2.setX(summaryField1.getX());
				    JRDesignStaticText summaryField3= (JRDesignStaticText)summaryBand.getElementByKey("summary3");
				    summaryField3.setX(summaryField2.getX()+summaryField2.getWidth());
				    JRDesignStaticText summaryField4= (JRDesignStaticText)summaryBand.getElementByKey("summary4");
				    summaryField4.setX(summaryField3.getX()+summaryField3.getWidth());
				    JRDesignTextField summaryField5= (JRDesignTextField)summaryBand.getElementByKey("summary5");
				    summaryField5.setX(summaryField4.getX()+summaryField4.getWidth());
				    JRDesignStaticText summaryField6= (JRDesignStaticText)summaryBand.getElementByKey("summary6");
				    summaryField6.setX(summaryField5.getX()+summaryField5.getWidth());
				    JRDesignTextField summaryField7= (JRDesignTextField)summaryBand.getElementByKey("summary7");
				    summaryField7.setX(summaryField6.getX()+summaryField6.getWidth());
				    JRDesignStaticText summaryField8= (JRDesignStaticText)summaryBand.getElementByKey("summary8");
				    summaryField8.setX(summaryField7.getX()+summaryField7.getWidth());
				    JRDesignStaticText summaryField9= (JRDesignStaticText)summaryBand.getElementByKey("summary9");
				    summaryField9.setX(summaryField8.getX()+summaryField8.getWidth());
				    JRDesignStaticText summaryField10= (JRDesignStaticText)summaryBand.getElementByKey("summary10");
				    summaryField10.setX(summaryField9.getX()+summaryField9.getWidth());
				    JRDesignStaticText summaryField11= (JRDesignStaticText)summaryBand.getElementByKey("summary11");
				    summaryField11.setX(summaryField10.getX()+summaryField10.getWidth());
				 }
//		         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign); 
//		         JasperCompileManager.compileReportToFile(reportPath1);
		         JasperCompileManager.compileReportToFile(jasperDesign, reportPath1.split("jrxm")[0]+"jasper");
			 }
			 if(reportKey.equals("projectintentdetail") || reportKey.equals("projectchargedetail")){  
				  JasperDesign jasperDesign =  JRXmlLoader.load(reportPath1);
				  if(flag==false || roleType.endsWith("business")){  //业务角色business
					  JRDesignBand columnHeaderBand = (JRDesignBand )jasperDesign.getColumnHeader();//获取ColumnHeader拦
					    
				      JRDesignStaticText columnHeaderField1 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header1");
				      columnHeaderField1.setWidth(0);
				          
			          JRDesignStaticText columnHeaderField2 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header2");
			          columnHeaderField2.setX(columnHeaderField1.getX());
			          JRDesignStaticText columnHeaderField3 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header3");
			          columnHeaderField3.setX(columnHeaderField1.getX()+columnHeaderField2.getWidth());
			          JRDesignStaticText columnHeaderField4 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header4");
			          columnHeaderField4.setX(columnHeaderField3.getX()+columnHeaderField3.getWidth());
			          JRDesignStaticText columnHeaderField5 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header5");
			          columnHeaderField5.setX(columnHeaderField4.getX()+columnHeaderField4.getWidth());
			          JRDesignStaticText columnHeaderField6 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header6");
			          columnHeaderField6.setX(columnHeaderField5.getX()+columnHeaderField5.getWidth());
			          JRDesignStaticText columnHeaderField7 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header7");
			          columnHeaderField7.setX(columnHeaderField6.getX()+columnHeaderField6.getWidth());
			          JRDesignStaticText columnHeaderField8 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header8");
			          columnHeaderField8.setX(columnHeaderField7.getX()+columnHeaderField7.getWidth());
			          JRDesignStaticText columnHeaderField9 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header9");
			          columnHeaderField9.setX(columnHeaderField8.getX()+columnHeaderField8.getWidth());
			          JRDesignStaticText columnHeaderField10 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header10");
			          columnHeaderField10.setX(columnHeaderField9.getX()+columnHeaderField9.getWidth());
			          JRDesignStaticText columnHeaderField11 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header11");
			          columnHeaderField11.setX(columnHeaderField10.getX()+columnHeaderField10.getWidth());
			          JRDesignStaticText columnHeaderField12 = (JRDesignStaticText)columnHeaderBand.getElementByKey("header12");
			          columnHeaderField12.setX(columnHeaderField11.getX()+columnHeaderField11.getWidth());
				          
				        
				      int widthall=columnHeaderField1.getWidth()+columnHeaderField2.getWidth()+columnHeaderField3.getWidth()
				          +columnHeaderField4.getWidth()+columnHeaderField5.getWidth()+columnHeaderField6.getWidth()
				          +columnHeaderField7.getWidth()+columnHeaderField8.getWidth()+columnHeaderField9.getWidth()
				          +columnHeaderField10.getWidth()+columnHeaderField11.getWidth()+columnHeaderField12.getWidth();
				      JRDesignStaticText columnHeaderreprotcount= (JRDesignStaticText)columnHeaderBand.getElementByKey("headerreportcount");
				          
				      JRDesignBand titleBand = (JRDesignBand )jasperDesign.getTitle();
					  JRDesignStaticText title=(JRDesignStaticText)titleBand.getElementByKey("title");
					  title.setWidth(widthall+columnHeaderreprotcount.getWidth());
					  JRDesignTextField search= (JRDesignTextField )titleBand.getElementByKey("search");
					  search.setWidth(widthall+columnHeaderreprotcount.getWidth());
						
					  jasperDesign.setPageWidth(widthall+columnHeaderreprotcount.getWidth()+jasperDesign.getLeftMargin()+jasperDesign.getRightMargin());
					  jasperDesign.setColumnWidth(widthall);
				          
			          JRDesignBand detailBand = (JRDesignBand )jasperDesign.getDetail();//获取Detail拦
			          JRDesignTextField detailField1= (JRDesignTextField )detailBand.getElementByKey("detail1");
			          detailField1.setWidth(0);
			          
			          JRDesignTextField detailField2= (JRDesignTextField )detailBand.getElementByKey("detail2");
			          detailField2.setX(detailField1.getX());
			          JRDesignTextField detailField3= (JRDesignTextField )detailBand.getElementByKey("detail3");
			          detailField3.setX(detailField2.getX()+detailField2.getWidth());
			          
			          JRDesignTextField detailField4= (JRDesignTextField )detailBand.getElementByKey("detail4");
			          detailField4.setX(detailField3.getX()+detailField3.getWidth());
			          JRDesignTextField detailField5= (JRDesignTextField )detailBand.getElementByKey("detail5");
			          detailField5.setX(detailField4.getX()+detailField4.getWidth());
			          JRDesignTextField detailField6= (JRDesignTextField )detailBand.getElementByKey("detail6");
			          detailField6.setX(detailField5.getX()+detailField5.getWidth());
			          JRDesignTextField detailField7= (JRDesignTextField )detailBand.getElementByKey("detail7");
			          detailField7.setX(detailField6.getX()+detailField6.getWidth());
			          JRDesignTextField detailField8= (JRDesignTextField )detailBand.getElementByKey("detail8");
			          detailField8.setX(detailField7.getX()+detailField7.getWidth());
			          JRDesignTextField detailField9= (JRDesignTextField )detailBand.getElementByKey("detail9");
			          detailField9.setX(detailField8.getX()+detailField8.getWidth());
			          JRDesignTextField detailField10= (JRDesignTextField )detailBand.getElementByKey("detail10");
			          detailField10.setX(detailField9.getX()+detailField9.getWidth());
			          JRDesignTextField detailField11= (JRDesignTextField )detailBand.getElementByKey("detail11");
			          detailField11.setX(detailField10.getX()+detailField10.getWidth());
			          JRDesignTextField detailField12= (JRDesignTextField )detailBand.getElementByKey("detail12");
			          detailField12.setX(detailField11.getX()+detailField11.getWidth());
				 }
//				 JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign); 
//				 JasperCompileManager.compileReportToFile(reportPath1);
				 JasperCompileManager.compileReportToFile(jasperDesign, reportPath1.split("jrxm")[0]+"jasper");
			 }
		 } catch (JRException e) {
			e.printStackTrace();
		} 
	}
	
	public void saveproject(String reportKey){
		if(reportKey.equals("guaranteeprojectdetail") || reportKey.equals("guaranteeprojectintentdetail")){
			List<GLGuaranteeloanProject> listg=gLGuaranteeloanProjectService.getAll();
			for(GLGuaranteeloanProject persistent:listg){
				String businessManagername="";
				if(null !=persistent.getAppUserIdOfA() && !persistent.getAppUserIdOfA().equals("")){
					String businessManager= persistent.getAppUserIdOfA();
					String [] appstr=businessManager.split(",");
					Set<AppUser> userSet1 = appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet1){
						businessManagername += s.getFamilyName()+",";
					}
					businessManagername = businessManagername.substring(0, businessManagername.length()-1);
					persistent.setAppUserName(businessManagername);
				}
				gLGuaranteeloanProjectService.save(persistent);
			}
		}	
        if(reportKey.equals("projectdetail")){
			List<SlSmallloanProject> lists=slSmallloanProjectService.getAll();
			for(SlSmallloanProject persistent:lists){
				String businessManagername="";
				if(null !=persistent.getAppUserId() && !persistent.getAppUserId().equals("")){
					String businessManager= persistent.getAppUserId();
					String [] appstr=businessManager.split(",");
					Set<AppUser> userSet1 = appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet1){
						businessManagername += s.getFamilyName()+",";
					}
					businessManagername = businessManagername.substring(0, businessManagername.length()-1);
					persistent.setAppUserName(businessManagername);
				}
				slSmallloanProjectService.save(persistent);
			}
		}	
        
        if(reportKey.equals("financingprojectdetail")){
			List<FlFinancingProject> listf=flFinancingProjectService.getAll();
			for(FlFinancingProject persistent:listf){
				String businessManagername="";
				if(null !=persistent.getAppUserId() && !persistent.getAppUserId().equals("")){
					String businessManager= persistent.getAppUserId();
					String [] appstr=businessManager.split(",");
					Set<AppUser> userSet1 = appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet1){
						businessManagername += s.getFamilyName()+",";
					}
					businessManagername = businessManagername.substring(0, businessManagername.length()-1);
					persistent.setAppUserName(businessManagername);
				}
				flFinancingProjectService.save(persistent);
			}
		}	
	}
	
	public StringBuffer searchbyreportkey(Iterator it,String reportKey){
		StringBuffer sb = new StringBuffer();
		String searchcondition="当前搜索条件： ";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		
		if(null!=strs && !"".equals(strs)){
			String vs=" and a.companyId in ("+strs+")"; //
			sb.append("&sqlcompanyIdcontrol=" + vs);
		}
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value =  (String)entry.getValue();
			String v=value;
			String s=value;
	        if(key.equals("projectName") && !v.equals("")){
	    	  searchcondition=searchcondition+"  项目名称:"+s.trim();
	        }
			if(key.equals("oppositeName") && !v.equals("")){
				 searchcondition=searchcondition+"  客户名称:"+s.trim();    	  
			}
			if(key.equals("projectNumber") && !v.equals("")){
				 searchcondition=searchcondition+"  项目编号:"+s.trim();    	  
			}
			if(key.equals("projectName")){s="%"+s.trim()+"%";};
		    if(key.equals("oppositeName")){s="%"+s.trim()+"%";};
		    if(key.equals("projectNumber")){s="%"+s.trim()+"%";};
		    if(key.equals("orgName")){}
			if(reportKey.equals("projectintentdetail") || reportKey.equals("projectchargedetail")){
				if(key.equals("orgName") && !v.equals("")){
					v=" and a.orgName LIKE \"%"+s.trim()+"%\"";
					sb.append("&sqlorgName=" + v);
					searchcondition=searchcondition+"  所属分公司:"+s.trim();   
				}
				if(key.equals("accrual") && !v.equals("")){
					v=" and a.accrual ="+v.trim();
					sb.append("&sqlaccrual=" + v);
					 searchcondition=searchcondition+"  利率:"+s.trim()+"%";   
				}
				if(key.equals("accrualtype") && !v.equals("")){
					v=" and a.accrualtype = \""+v.trim()+"\"";
					sb.append("&sqlaccrualtype=" + v);
					if(s.equals("sameprincipalandInterest")){s="等额本息";}
					if(s.equals("sameprincipal")){s="等额本金";}
					if(s.equals("singleInterest")){s="单利";}
					
					 searchcondition=searchcondition+"  计息方式:"+s.trim();   
				}
				if(key.equals("projectStatus") && !v.equals("")){
					v=" and a.projectStatus ="+v.trim();
				
					if(s.equals("0")){s="办理中贷款";}
					if(s.equals("1")){s="放款后贷款";}
					if(s.equals("2")){s="已完成贷款";}
					if(s.equals("3")){s="提前终止贷款";}
					if(s.equals("5")){s="展期中贷款";}
					if(s.equals("7")){v="";s="全部贷款业务";}
					if(s.equals("8")){s="违约贷款";}
					sb.append("&sqlprojectstatu=" + v);
					searchcondition=searchcondition+"  项目状态:"+s.trim();   
				}
				if(key.equals("projectMoney") && !v.equals("")){
					v=" and a.projectMoney >="+v.trim();
					sb.append("&sqlprojectmoney=" + v);
					searchcondition=searchcondition+"  本金金额>=:"+s.trim();   
				}
				if(key.equals("projectMoneyl") && !v.equals("")){
					v=" and a.projectMoney <="+v.trim();
					sb.append("&sqlprojectMoneyl=" + v);
					searchcondition=searchcondition+"  本金金额<=:"+s.trim();   
				}
				if(key.equals("accrualMoneyg") && !v.equals("")){
					v=" and a.accrualMoney >= "+v.trim();
					sb.append("&sqlaccrualMoneyg=" + v);
					searchcondition=searchcondition+"  利息总额>=:"+s.trim();   
				}
				if(key.equals("accrualMoneyl") && !v.equals("")){
					v=" and a.accrualMoney  <= "+v.trim();
					sb.append("&sqlaccrualMoneyl=" + v);
					searchcondition=searchcondition+"  利息总额<=:"+s.trim();   
				}
				if(key.equals("annualNetProfit") && !v.equals("")){
					v=" and a.annualNetProfit >="+v.trim();
					sb.append("&sqlannualNetProfit=" + v);
					searchcondition=searchcondition+"  年化利率>=:"+s.trim()+"%";   
				}
				if(key.equals("startDateg") && !v.equals("")){
					v=" and a.startDate >= \""+v.trim()+"\"";
					sb.append("&sqlstartDateg=" + v);
					searchcondition=searchcondition+"  贷款日>=:"+s.trim();   
				}
				if(key.equals("startDatel") && !v.equals("")){
					v=" and a.startDate <=  \""+v.trim()+"\"";
					sb.append("&sqlstartDatel=" + v);
					searchcondition=searchcondition+"  贷款日<=:"+s.trim();   
				}
				if(key.equals("oppositeType") && !v.equals("")){
					v=" and a.oppositeType = \""+v.trim()+"\"";
					sb.append("&sqloppositeType=" + v);
					if(s.equals("company_customer")){s="企业";}
					if(s.equals("person_customer")){s="个人";}
					searchcondition=searchcondition+"  客户类型:"+s.trim();   
				}
			}
			if(reportKey.equals("projectdetail")){
				if(key.equals("orgName") && !v.equals("")){
					v=" and a.orgName LIKE \"%"+s.trim()+"%\"";
					sb.append("&sqlorgName=" + v);
					searchcondition=searchcondition+"  所属分公司:"+s.trim();   
				}
				if(key.equals("projectMoney") && !v.equals("")){
					v=" and a.projectMoney >="+v.trim();
					sb.append("&sqlprojectmoney=" + v);
					 searchcondition=searchcondition+"  贷款金额>=:"+s.trim();   
				}
				if(key.equals("projectMoneyl") && !v.equals("")){
					v=" and a.projectMoney <="+v.trim();
					sb.append("&sqlprojectMoneyl=" + v);
					searchcondition=searchcondition+"  贷款金额<=:"+s.trim();   
				}
				if(key.equals("startDateg") && !v.equals("")){
					v=" and a.startDate >= \""+v.trim()+"\"";
					sb.append("&sqlstartDateg=" + v);
					searchcondition=searchcondition+"  贷款开始时间>=:"+s.trim();   
				}
				if(key.equals("startDatel") && !v.equals("")){
					v=" and a.startDate <=  \""+v.trim()+"\"";
					sb.append("&sqlstartDatel=" + v);
					searchcondition=searchcondition+"  贷款开始时间<=:"+s.trim();   
				}
				if(key.equals("appUserIdOfA") && !v.equals("")){
					v=" and a.appUserName LIKE \"%"+v.trim()+"%\"";
					sb.append("&sqlappUserIdOfA=" + v);
					searchcondition=searchcondition+"  项目经理:"+s.trim();   
				}
				if(key.equals("annualNetProfitg") && !v.equals("")){
					v=" and a.annualNetProfit >="+v.trim();
					sb.append("&annualNetProfitg=" + v);
					searchcondition=searchcondition+"  年化利率>=:"+s.trim();   
				}
				if(key.equals("oppositeTypeValue") && !v.equals("")){
					v=" and a.oppositeTypeValue = \""+v.trim()+"\"";
					sb.append("&sqloppositeTypeValue=" + v);
					 searchcondition=searchcondition+"  客户类型:"+s.trim();   
				}
                if(key.equals("projectStatus") && !v.equals("")){
					if(s.equals("办理中贷款")){
						v=" and a.projectStatus =0 "; s="办理中贷款";
					}
					if(s.equals("放款后贷款")){
						v=" and a.projectStatus =1 ";s="放款后贷款";
					}
					if(s.equals("已完成贷款")){
						v=" and a.projectStatus =2 ";s="已完成贷款";
					}
					if(s.equals("提前终止贷款")){
						v=" and a.projectStatus =3 ";s="提前终止贷款";
					}
					if(s.equals("展期中贷款")){
						v=" and a.projectStatus =5 ";s="展期中贷款";
					}
					if(s.equals("全部贷款业务")){
						v="";s="全部贷款业务";
					}
					if(s.equals("违约贷款")){
						v=" and a.projectStatus =8 ";s="违约贷款";
					}
					sb.append("&sqlprojectStatus=" + v);
					searchcondition=searchcondition+"  项目状态:"+s.trim();   
				}
			}
			if(reportKey.equals("financingprojectdetail")){
				if(key.equals("projectMoney") && !v.equals("")){
					v=" and a.projectMoney >="+v.trim();
					sb.append("&sqlprojectmoney=" + v);
					searchcondition=searchcondition+"  融资金额>=:"+s.trim();   
				}
				if(key.equals("projectMoneyl") && !v.equals("")){
					v=" and a.projectMoney <="+v.trim();
					sb.append("&sqlprojectMoneyl=" + v);
					searchcondition=searchcondition+"  融资金额<=:"+s.trim();   
				}
				if(key.equals("startDateg") && !v.equals("")){
					v=" and a.startDate >= \""+v.trim()+"\"";
					sb.append("&sqlstartDateg=" + v);
					searchcondition=searchcondition+"  融资日期>=:"+s.trim();   
				}
				if(key.equals("startDatel") && !v.equals("")){
					v=" and a.startDate <=  \""+v.trim()+"\"";
					sb.append("&sqlstartDatel=" + v);
					searchcondition=searchcondition+"  融资日期<=:"+s.trim();   
				}
				if(key.equals("appUserIdOfA") && !v.equals("")){
					v=" and a.appUserName LIKE \"%"+v.trim()+"%\"";
					sb.append("&sqlappUserIdOfA=" + v);
					searchcondition=searchcondition+"  业务经理:"+s.trim();   
				}
				if(key.equals("oppositeTypeValue") && !v.equals("")){
					v=" and a.oppositeTypeValue = \""+v.trim()+"\"";
					sb.append("&sqloppositeTypeValue=" + v);
					searchcondition=searchcondition+"  客户类型:"+s.trim();   
				}
                if(key.equals("projectStatus") && !v.equals("")){
					if(s.equals("融资前")){
						v=" and a.projectStatus =0 "; s="融资前";
					}
					if(s.equals("还款中")){
						v=" and a.projectStatus =1 ";s="还款中";
					}
					if(s.equals("已完成")){
						v=" and a.projectStatus =2 ";s="已完成";
					}
					if(s.equals("提前终止")){
						v=" and a.projectStatus =3 ";s="提前终止";
					}
					sb.append("&sqlprojectStatus=" + v);
					searchcondition=searchcondition+"  项目状态:"+s.trim();   
				}
			}
			if(reportKey.equals("guaranteeprojectdetail") || reportKey.equals("guaranteeprojectintentdetail")){
				if(key.equals("projectMoney") && !v.equals("")){
					v=" and a.projectMoney >="+v.trim();
					sb.append("&sqlprojectmoney=" + v);
					searchcondition=searchcondition+"  贷款金额>=:"+s.trim();   
				}
				if(key.equals("projectMoneyl") && !v.equals("")){
					v=" and a.projectMoney <="+v.trim();
					sb.append("&sqlprojectMoneyl=" + v);
					searchcondition=searchcondition+"  贷款金额<=:"+s.trim();   
				}
				if(key.equals("startDateg") && !v.equals("")){
					v=" and a.startDate >= \""+v.trim()+"\"";
					sb.append("&sqlstartDateg=" + v);
					searchcondition=searchcondition+"  担保开始时间>=:"+s.trim();   
				}
				if(key.equals("startDatel") && !v.equals("")){
					v=" and a.startDate <=  \""+v.trim()+"\"";
					sb.append("&sqlstartDatel=" + v);
					searchcondition=searchcondition+"  担保开始时间<=:"+s.trim();   
				}
				if(key.equals("appUserIdOfA") && !v.equals("")){
					v=" and a.appUserName LIKE \"%"+v.trim()+"%\"";
					sb.append("&sqlappUserIdOfA=" + v);
					searchcondition=searchcondition+"  项目经理:"+s.trim();   
				}
				if(key.equals("oppositeTypeValue") && !v.equals("")){
					v=" and a.oppositeTypeValue = \""+v.trim()+"\"";
					sb.append("&sqloppositeTypeValue=" + v);
					searchcondition=searchcondition+"  客户类型:"+s.trim();   
				}
				if(key.equals("acceptDateg") && !v.equals("")){
					v=" and a.acceptDate >= \""+v.trim()+"\"";
					sb.append("&sqlacceptDateg=" + v);
					searchcondition=searchcondition+"  担保起始日期>=:"+s.trim();   
				}
				if(key.equals("acceptDatel") && !v.equals("")){
					v=" and a.acceptDate <=  \""+v.trim()+"\"";
					sb.append("&sqlacceptDatel=" + v);
					searchcondition=searchcondition+"  担保起始日期<=:"+s.trim();   
				}
				if(key.equals("oppositeType") && !v.equals("")){
					v=" and a.oppositeType = \""+v.trim()+"\"";
					sb.append("&sqloppositeType=" + v);
					if(s.equals("company_customer")){s="企业";}
					if(s.equals("person_customer")){s="个人";}
					searchcondition=searchcondition+"  客户类型:"+s.trim();   
				}
				if(key.equals("projectStatus") && !v.equals("")){
					if(s.equals("保前-进行中")){
						v=" and a.projectStatus =0  and a.bmStatus=0 "; s="保前-进行中";}
					if(s.equals("保前-已挂起")){
						v=" and a.projectStatus =0  and a.bmStatus=10 ";s="保前-已挂起";}
					if(s.equals("保前-已终止")){
						v=" and a.projectStatus =0  and a.bmStatus=3 ";s="保前-已终止";}
					if(s.equals("保中-进行中")){
						v=" and a.projectStatus =1  and a.bmStatus=0 ";s="保中-进行中";}
					if(s.equals("保中-违约处理")){
						v=" and a.projectStatus =1  and a.bmStatus=1 ";s="保中-违约处理";}
					if(s.equals("保中-已完成")){
						v=" and a.projectStatus =1  and a.bmStatus=2 ";s="保中-已完成";}
					if(s.equals("保中-已终止")){
						v=" and a.projectStatus =1  and a.bmStatus=3 ";s="保中-已终止";}
					sb.append("&sqlprojectStatus=" + v);
					searchcondition=searchcondition+"  项目状态:"+s.trim();   
				}
			}
			if(reportKey.equals("raypaymentdetail") || reportKey.equals("interestPayment") || reportKey.equals("financing")|| reportKey.equals("analysis")|| reportKey.equals("achievement")){
				 if(key.equals("projectName")){s="%"+s.trim()+"%";}
			     if(key.equals("oppositeName") && !v.equals("")){
				   	  s=" and e.name like \"%"+s.trim()+"%\"";
				   	  sb.append("&sqlcustomerName=" + s);
				 }
			     if(key.equals("projectNumber") && !v.equals("")){
			   	  	 s=" and f.projectNumber like \"%"+s.trim()+"%\"";
			   	  	 sb.append("&sqlprojectNumber=" + s);
			     }
			     if(key.equals("orgName")){}
				 if(key.equals("orgName") && !v.equals("")){
					 v="and org.org_name LIKE \"%"+s.trim()+"%\"";
					 sb.append("&sqlorgName=" + v);
					 searchcondition=searchcondition+"  所属分公司:"+s.trim();   
				 }
				 if(key.equals("accrual") && !v.equals("")){
					 v=" and a.accrual ="+v.trim();
					 sb.append("&sqlaccrual=" + v);
					 searchcondition=searchcondition+"  利率:"+s.trim()+"%";   
				 }
				 if(key.equals("accrualtype") && !v.equals("")){
					 v=" and a.accrualtype = \""+v.trim()+"\"";
					 sb.append("&sqlaccrualtype=" + v);
					 if(s.equals("sameprincipalandInterest")){s="等额本息";}
					 if(s.equals("sameprincipal")){s="等额本金";}
					 if(s.equals("singleInterest")){s="单利";}
					 searchcondition=searchcondition+"  计息方式:"+s.trim();   
				 }
				 if(key.equals("projectStatus") && !v.equals("")){
					 v=" and a.projectStatus ="+v.trim();
					 if(s.equals("0")){s="办理中贷款";}
					 if(s.equals("1")){s="放款后贷款";}
					 if(s.equals("2")){s="已完成贷款";}
					 if(s.equals("3")){s="提前终止贷款";}
					 if(s.equals("5")){s="展期中贷款";}
					 if(s.equals("7")){v="";s="全部贷款业务";}
					 if(s.equals("8")){s="违约贷款";}
					 sb.append("&sqlprojectstatu=" + v);
					 searchcondition=searchcondition+"  项目状态:"+s.trim();   
				 }
				 if(key.equals("appUserIdOfA") && !v.equals("")){
					 v=" and u.fullname LIKE \"%"+v.trim()+"%\"";
					 sb.append("&sqlappUserIdOfA=" + v);
					 searchcondition=searchcondition+"  项目经理:"+s.trim();   
				 }
				 if(key.equals("projectMoney") && !v.equals("")){
					 v=" and f.projectMoney >="+v.trim();
					 sb.append("&sqlprojectmoneyg=" + v);
					 searchcondition=searchcondition+"  融资金额>=:"+s.trim();   
				 }
				 if(key.equals("projectMoneyl") && !v.equals("")){
					 v=" and f.projectMoney <="+v.trim();
					 sb.append("&sqlprojectMoneyl=" + v);
					 searchcondition=searchcondition+"  融资金额<=:"+s.trim();   
				 }
						
				 if(key.equals("notMoneyg") && !v.equals("")){
					 v=" and s.notMoney >="+v.trim();
					 sb.append("&sqlnotMoneyg=" + v);
					 searchcondition=searchcondition+"  未付息金额>=:"+s.trim();   
				 }
				 if(key.equals("notMoneyl") && !v.equals("")){
					 v=" and s.notMoney <="+v.trim();
					 sb.append("&sqlnotMoneyl=" + v);
					 searchcondition=searchcondition+"  未付息金额<=:"+s.trim();   
				 }
				 if(key.equals("accrualMoneyg") && !v.equals("")){
					 v=" and a.accrualMoney >= "+v.trim();
					 sb.append("&sqlaccrualMoneyg=" + v);
					 searchcondition=searchcondition+"  利息总额>=:"+s.trim();   
				 }
				 if(key.equals("accrualMoneyl") && !v.equals("")){
					 v=" and a.accrualMoney  <= "+v.trim();
					 sb.append("&sqlaccrualMoneyl=" + v);
					 searchcondition=searchcondition+"  利息总额<=:"+s.trim();   
				 }
				 if(key.equals("annualNetProfit") && !v.equals("")){
					 v=" and a.annualNetProfit >="+v.trim();
					 sb.append("&sqlannualNetProfit=" + v);
					 searchcondition=searchcondition+"  年化利率>=:"+s.trim()+"%";   
				 }
				 if(key.equals("startDateg") && !v.equals("")){
					 v=" and f.startDate >= \""+v.trim()+"\"";
					 sb.append("&sqlstartDateg=" + v);
					 searchcondition=searchcondition+"  融资日>=:"+s.trim();   
				 }
				 if(key.equals("startDatel") && !v.equals("")){
					 v=" and f.startDate <=  \""+v.trim()+"\"";
					 sb.append("&sqlstartDatel=" + v);
					 searchcondition=searchcondition+"  融资日<=:"+s.trim();   
				 }
				 if(key.equals("intentDateg") && !v.equals("")){
					 v=" and s.intentDate >= \""+v.trim()+"\"";
					 sb.append("&sqlintentDateg=" + v);
					 searchcondition=searchcondition+"  计划到账日>=:"+s.trim();   
				 }
				 if(key.equals("intentDatel") && !v.equals("")){
					 v=" and s.intentDate <=  \""+v.trim()+"\"";
					 sb.append("&sqlintentDatel=" + v);
					 searchcondition=searchcondition+"  计划到账日<=:"+s.trim();   
				 }
				 if(key.equals("intentDate") && !v.equals("")){
					 sb.append("&fixedDate=" + v+"-01");
					 searchcondition=searchcondition+"  付息月份<=:"+s.trim();   
				 }else {
					sb.append("&fixedDate=" + DateUtil.getNowDateTime());
					searchcondition=searchcondition+"  付息月份<=:"+s.trim();   
				 }
						
				 if(key.equals("oppositeType") && !v.equals("")){
					 v=" and a.oppositeType = \""+v.trim()+"\"";
					 sb.append("&sqloppositeType=" + v);
					 if(s.equals("company_customer")){s="企业";}
					 if(s.equals("person_customer")){s="个人";}
					 searchcondition=searchcondition+"  客户类型:"+s.trim();   
				 }
			}
			sb.append("&" + key + "=" + s);
		}
		if(searchcondition.equals("当前搜索条件： ")){
			searchcondition="当前搜索条件：空 ";
		}
		sb.append("&searchcondition=" + searchcondition);
		return sb;
	}
	
	public String submit2() {
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		if(flag){
			String strs=ContextUtil.getBranchIdsStr();
			String companyId=this.getRequest().getParameter("companyId");
			String hql="";
			if(null!=companyId && !"".equals(companyId)){
				strs=companyId;
			}
			if(null!=strs && !"".equals(strs)){
				hql=" and companyId in ("+strs+") ";
			}
			setJsonString("{success:true,data:'&companyIds=" +hql + "'}");
		}else{
			setJsonString("{success:true,data:'&companyIds='}");
		}
	/*	java.text.SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-mm-dd");
		Map map = getRequest().getParameterMap();
		Iterator it = map.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			String v=value[0];
			if(v==null||v.equals("")){
				v="%";
			}else{
				try {
					dateformat.parse(v.trim());
				} catch (ParseException e) {
					v="%"+v.trim()+"%";
				}
			}
			sb.append("&" + key + "=" + v);
		}
		setJsonString("{success:true,data:'" + sb.toString() + "'}");*/
		return SUCCESS;
	}
	
	/**
	 * 
	 * 贷款期限统计表
	 */
	public String submitLoanTerm(){
		String endDate=this.getRequest().getParameter("endDate");
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		setJsonString("{success:true,data:'&endDate=" +endDate + "&searchDate="+df.format(date)+"'}");
		return SUCCESS;
	}
	/**
	 * 
	 * 担保方式分析表
	 */
	public String submitMortgageMode(){
		String endDate=this.getRequest().getParameter("endDate");
		String startDate=this.getRequest().getParameter("startDate");
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		setJsonString("{success:true,data:'&endDate=" +endDate + "&searchDate="+df.format(date)+"&startDate="+startDate+"'}");
		return SUCCESS;
	}
	
	public String submitCommon(){
		String endDate=this.getRequest().getParameter("endDate");
		String startDate=this.getRequest().getParameter("startDate");
		String companyId=this.getRequest().getParameter("companyId");
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		strs="in ("+strs+")";
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		setJsonString("{success:true,data:'&endDate=" +endDate + "&searchDate="+df.format(date)+"&startDate="+startDate+"&companyIdStrs="+strs+"'}");
		return SUCCESS;
	}
	
	public String submitLoanLimitInterval(){
		String companyId=this.getRequest().getParameter("companyId");
		String startDate=this.getRequest().getParameter("startDate");
		String endDate=this.getRequest().getParameter("endDate");
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		if(null!=strs && !"".equals(strs)){
			String[] strArr=strs.split(",");
			if(strArr.length>1){
				companyId="1";
			}else{
				companyId=strs;
			}
		}
		if(null!=companyId && !companyId.equals("")){
			Organization org=organizationService.get(Long.valueOf(companyId));
			if(null!=org){
				String companyName=org.getAcronym();
				List list1=slSmallloanProjectService.getprojectList(Long.valueOf(companyId), startDate, endDate, "1");
				long customerCount1=0;
				BigDecimal money1=new BigDecimal(0);
				if(null!=list1 && list1.size()>0){
					Object[] object=(Object[]) list1.get(0);
					customerCount1=(Long)object[0];
					if(null!=object[1]){
						money1=((BigDecimal) object[1]).divide(new BigDecimal(10000));
					}
				}
				List list2=slSmallloanProjectService.getprojectList(Long.valueOf(companyId), startDate, endDate, "2");
				long customerCount2=0;
				BigDecimal money2=new BigDecimal(0);
				if(null!=list2 && list2.size()>0){
					Object[] object=(Object[]) list2.get(0);
					customerCount2=(Long)object[0];
					if(null!=object[1]){
						money2=((BigDecimal) object[1]).divide(new BigDecimal(10000));
					}
				}
				List list3=slSmallloanProjectService.getprojectList(Long.valueOf(companyId), startDate, endDate, "3");
				long customerCount3=0;
				BigDecimal money3=new BigDecimal(0);
				if(null!=list3 && list3.size()>0){
					Object[] object=(Object[]) list3.get(0);
					customerCount3=(Long)object[0];
					if(null!=object[1]){
						money3=((BigDecimal) object[1]).divide(new BigDecimal(10000));
					}
				}
				List list4=slSmallloanProjectService.getprojectList(Long.valueOf(companyId), startDate, endDate, "4");
				long customerCount4=0;
				BigDecimal money4=new BigDecimal(0);
				if(null!=list4 && list4.size()>0){
					Object[] object=(Object[]) list4.get(0);
					customerCount4=(Long)object[0];
					if(null!=object[1]){
						money4=((BigDecimal) object[1]).divide(new BigDecimal(10000));
					}
				}
				List list5=slSmallloanProjectService.getprojectList(Long.valueOf(companyId), startDate, endDate, "5");
				long customerCount5=0;
				BigDecimal money5=new BigDecimal(0);
				if(null!=list5 && list5.size()>0){
					Object[] object=(Object[]) list5.get(0);
					customerCount5=(Long)object[0];
					if(null!=object[1]){
						money5=((BigDecimal) object[1]).divide(new BigDecimal(10000));
					}
				}
				setJsonString("{success:true,data:'&searchDate="+df.format(date)+"&startDate="+startDate+"&endDate="+endDate+"&companyName="+companyName+
						"&customerCount1="+customerCount1+"&customerCount2="+customerCount2+"&customerCount3="+customerCount3+"&customerCount4="+customerCount4+
						"&customerCount5="+customerCount5+"&money1="+money1+"&money2="+money2+"&money3="+money3+"&money4="+money4+"&money5="+money5+"'}");
			}
		}
		return SUCCESS;
	}
	
	public String submitAverageDailyBalance(){
		try {
			String endDate=this.getRequest().getParameter("endDate");
			if(null!=endDate && !endDate.equals("")){
				Date date=new Date();
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(endDate));
				int year=calendar.get(calendar.YEAR);
				int month=calendar.get(calendar.MONTH)+1;
				String searchMonth="";
				if(month==1 || month==2 || month==3){
					searchMonth="01";
				}else if(month==4 || month==5 || month==6){
					searchMonth="04";
				}else if(month==7 || month==8 || month==9){
					searchMonth="07";
				}else if(month==10 || month==11 || month==12){
					searchMonth="10";
				}
				String monthDate=endDate.substring(0,endDate.lastIndexOf("-"))+"-01";
				String seasonDate=year+"-"+searchMonth+"-01";
				String yearDate=year+"-01-01";
				long monthDays=(df.parse(endDate).getTime()-df.parse(monthDate).getTime())/(1000*24*60*60);
				long seasonDays=(df.parse(endDate).getTime()-df.parse(seasonDate).getTime())/(1000*24*60*60);
				long yearDays=(df.parse(endDate).getTime()-df.parse(yearDate).getTime())/(1000*24*60*60);
				setJsonString("{success:true,data:'&endDate="+endDate+"&searchDate="+df.format(date)+"&monthDays="+monthDays+"&seasonDays="+seasonDays+"&yearDays="+yearDays+"'}");
			}else{
				setJsonString("{success:true,data:''}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String submitTenCustomer(){
		String companyId=this.getRequest().getParameter("companyId");
		String endDate=this.getRequest().getParameter("endDate");
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String companyName="";
		if(null!=companyId && !companyId.equals("")){
			Organization org=organizationService.get(Long.valueOf(companyId));
			if(null!=org){
				companyName=org.getAcronym();
			}
			companyId="="+companyId;
		}
		setJsonString("{success:true,data:'&endDate="+endDate+"&searchDate="+df.format(date)+"&comapnyId="+companyId+"&companyName="+companyName+"'}");
		return SUCCESS;
	}
	
	public String submitCustomerDailyBalance(){
		try {
			String endDate=this.getRequest().getParameter("endDate");
			String companyId=this.getRequest().getParameter("companyId");
			String strs=ContextUtil.getBranchIdsStr();
			if(null!=companyId && !"".equals(companyId)){
				strs=companyId;
			}
			String companyName="";
			String remarks="";
			if(null!=strs && !"".equals(strs)){
				String[] str=strs.split(",");
				if(str.length>1){
					companyName="多公司汇总";
					for(int i=0;i<str.length;i++){
						Organization org=organizationService.get(Long.valueOf(str[i]));
						remarks=remarks+org.getAcronym()+",";
					}
					if(!remarks.equals("")){
						remarks="注："+remarks.substring(0,remarks.length()-1);
					}
				}else if(str.length==1){
					Organization org=organizationService.get(Long.valueOf(str[0]));
					companyName=org.getAcronym();
				}
				
			}
			strs="in ("+strs+")";
			if(null!=endDate && !endDate.equals("")){
				Date date=new Date();
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(endDate));
				int year=calendar.get(calendar.YEAR);
				int month=calendar.get(calendar.MONTH)+1;
				String searchMonth="";
				if(month==1 || month==2 || month==3){
					searchMonth="01";
				}else if(month==4 || month==5 || month==6){
					searchMonth="04";
				}else if(month==7 || month==8 || month==9){
					searchMonth="07";
				}else if(month==10 || month==11 || month==12){
					searchMonth="10";
				}
				String monthDate=endDate.substring(0,endDate.lastIndexOf("-"))+"-01";
				String seasonDate=year+"-"+searchMonth+"-01";
				String yearDate=year+"-01-01";
				long monthDays=(df.parse(endDate).getTime()-df.parse(monthDate).getTime())/(1000*24*60*60);
				long seasonDays=(df.parse(endDate).getTime()-df.parse(seasonDate).getTime())/(1000*24*60*60);
				long yearDays=(df.parse(endDate).getTime()-df.parse(yearDate).getTime())/(1000*24*60*60);
				setJsonString("{success:true,data:'&endDate="+endDate+"&searchDate="+df.format(date)+"&monthDays="+monthDays+"&seasonDays="+seasonDays+"&yearDays="+yearDays+"&companyIdStrs="+strs+"&companyName="+companyName+"&remarks="+remarks+"'}");
			}else{
				setJsonString("{success:true,data:''}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String submitLoanSummary(){
		try{
			String startDate=this.getRequest().getParameter("startDate");
			String endDate=this.getRequest().getParameter("endDate");
			String reportId=this.getRequest().getParameter("reportId");
			String reportPath=reportTemplateService.get(Long.valueOf(reportId)).getReportLocation().split("jasper")[0]+"jrxml";
			String rootPath=uploadPath;
			File file = new File(rootPath + "/" + reportPath);
			String reportPath1=file.getPath();
			JasperDesign jasperDesign =  JRXmlLoader.load(reportPath1);
			List<Organization> list=organizationService.getAllByOrgType();
			int restWidth = jasperDesign.getColumnWidth()-200;
		    int columnNum = list.size();
		    int columnWidth = restWidth/columnNum;
		    JRDesignBand columnHeader = (JRDesignBand)jasperDesign.getColumnHeader();
		    JRDesignBand summary=(JRDesignBand)jasperDesign.getSummary();
		    BigDecimal sumfmoney=new BigDecimal(0);
   	     	BigInteger sumfcount=new BigInteger("0");
   	     	BigDecimal sumsmoney=new BigDecimal(0);
   	     	BigInteger sumscount=new BigInteger("0");
   	     	BigDecimal sumwmoney=new BigDecimal(0);
	     	BigInteger sumwcount=new BigInteger("0");
	     	BigDecimal sumymoney=new BigDecimal(0);
	     	BigInteger sumycount=new BigInteger("0");
	     	JRDesignImage image=(JRDesignImage)((JRDesignBand)jasperDesign.getColumnHeader()).getElementByKey("image");
	     	JRDesignExpression iexpression = new JRDesignExpression();
	     	iexpression.setValueClass(java.lang.String.class);
	     	String str=this.getRequest().getSession().getServletContext().getRealPath("/");
	     	String str1=str.replaceAll("\\\\", "\\\\\\\\");
	     	iexpression.setText("'"+str1+"\\\\images\\\\2.jpg'");
	     	image.setExpression(iexpression);
	     	for(int i=0;i<list.size();i++){
		    	Organization o=list.get(i);
		    	 JRDesignTextField textField = (JRDesignTextField)(((JRDesignBand)jasperDesign.getColumnHeader()).getElementByKey("header")).clone();
	    	     textField.setX(150+columnWidth*i);
	    	     textField.setY(0);
	    	     textField.setWidth(columnWidth);
	    	     textField.setHeight(40);
	    	     textField.setFontSize(15);
	    	     JRDesignExpression expression = new JRDesignExpression();
	    	     expression.setValueClass(java.lang.String.class);
	    	     expression.setText("'"+o.getAcronym()+"'");
	    	     textField.setExpression(expression);
	    	     columnHeader.addElement(textField);
	    	     
	    	     List flist=vFundDetailService.getListByFundType(startDate, endDate, o.getOrgId(), "principalLending");	    	    
	    	     BigDecimal fmoney=new BigDecimal(0);
	    	     BigInteger fcount=new BigInteger("0");
	    	     if(null!=flist && flist.size()>0){
	    	    	 Object[] obj=(Object[]) flist.get(0);
	    	    	 if(null!=obj[0]){
	    	    		 fcount=(BigInteger) obj[0];
	    	    	 }
	    	    	 if(null!=obj[1]){
	    	    		 fmoney=(BigDecimal) (obj[1]);
	    	    	 }
	    	     }
	    	     sumfmoney=sumfmoney.add(fmoney);
	    	     sumfcount=sumfcount.add(fcount);
	    	     JRDesignTextField textField1 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text1")).clone();
	    	     textField1.setX(150+columnWidth*i);
	    	     textField1.setY(0);
	    	     textField1.setWidth(columnWidth);
	    	     textField1.setHeight(25);
	    	     textField1.setFontSize(15);
	    	     JRDesignExpression expression1 = new JRDesignExpression();
	    	     expression1.setValueClass(java.lang.String.class);
	    	     expression1.setText(String.format("%.2f", (fmoney.doubleValue()/10000)));
	    	     textField1.setExpression(expression1);
	    	     summary.addElement(textField1);
	    	     
	    	     JRDesignTextField textField2 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text2")).clone();
	    	     textField2.setX(150+columnWidth*i);
	    	     textField2.setY(25);
	    	     textField2.setWidth(columnWidth);
	    	     textField2.setHeight(25);
	    	     textField2.setFontSize(15);
	    	     JRDesignExpression expression2 = new JRDesignExpression();
	    	     expression2.setValueClass(java.lang.String.class);
	    	     expression2.setText(String.valueOf(fcount));
	    	     textField2.setExpression(expression2);
	    	     summary.addElement(textField2);
	    	     
	    	     BigDecimal smoney=new BigDecimal(0);
	    	     List slist=vFundDetailService.getListByFundType(startDate, endDate, o.getOrgId(), "principalRepayment");
	    	     if(null!=slist && slist.size()>0){
	    	    	 Object[] obj=(Object[]) slist.get(0);
	    	    	 if(null!=obj[1]){
	    	    		 smoney=(BigDecimal) obj[1];
	    	    	 }
	    	     }
	    	     sumsmoney=sumsmoney.add(smoney);
	    	     JRDesignTextField textField3 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text3")).clone();
	    	     textField3.setX(150+columnWidth*i);
	    	     textField3.setY(50);
	    	     textField3.setWidth(columnWidth);
	    	     textField3.setHeight(25);
	    	     textField3.setFontSize(15);
	    	     JRDesignExpression expression3 = new JRDesignExpression();
	    	     expression3.setValueClass(java.lang.String.class);
	    	     expression3.setText(String.format("%.2f", (smoney.doubleValue()/10000)));
	    	     textField3.setExpression(expression3);
	    	     summary.addElement(textField3);
	    	     List shlist=vFundDetailService.getshCount(startDate, endDate, o.getOrgId(), "principalRepayment");	    	    
	    	     BigInteger scount=new BigInteger("0");
	    	     if(null!=shlist && shlist.size()>0){
	    	    	 scount=(BigInteger) shlist.get(0);
	    	    	
	    	     }
	    	     sumscount=sumscount.add(scount);
	    	     JRDesignTextField textField4 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text4")).clone();
	    	     textField4.setX(150+columnWidth*i);
	    	     textField4.setY(75);
	    	     textField4.setWidth(columnWidth);
	    	     textField4.setHeight(25);
	    	     textField4.setFontSize(15);
	    	     JRDesignExpression expression4 = new JRDesignExpression();
	    	     expression4.setValueClass(java.lang.String.class);
	    	     expression4.setText(String.valueOf(scount));
	    	     textField4.setExpression(expression4);
	    	     summary.addElement(textField4);
	    	     List wlist=vFundDetailService.getwjqList(startDate, endDate, o.getOrgId(), "principalRepayment");	    	    
	    	     BigDecimal wmoney=new BigDecimal(0);
	    	     BigInteger wcount=new BigInteger("0");
	    	     if(null!=wlist && wlist.size()>0){
	    	    	 Object[] obj=(Object[]) wlist.get(0);
	    	    	 if(null!=obj[1]){
	    	    		 wcount=(BigInteger) obj[1];
	    	    	 }
	    	    	 if(null!=obj[0]){
	    	    		 wmoney=(BigDecimal) (obj[0]);
	    	    	 }
	    	     }
	    	     sumwmoney=sumwmoney.add(wmoney);
	    	     sumwcount=sumwcount.add(wcount);
	    	     JRDesignTextField textField5 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text5")).clone();
	    	     textField5.setX(150+columnWidth*i);
	    	     textField5.setY(100);
	    	     textField5.setWidth(columnWidth);
	    	     textField5.setHeight(25);
	    	     textField5.setFontSize(15);
	    	     JRDesignExpression expression5 = new JRDesignExpression();
	    	     expression5.setValueClass(java.lang.String.class);
	    	     expression5.setText(String.format("%.2f", (wmoney.doubleValue()/10000)));
	    	     textField5.setExpression(expression5);
	    	     summary.addElement(textField5);
	    	     
	    	     JRDesignTextField textField6 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text6")).clone();
	    	     textField6.setX(150+columnWidth*i);
	    	     textField6.setY(125);
	    	     textField6.setWidth(columnWidth);
	    	     textField6.setHeight(25);
	    	     textField6.setFontSize(15);
	    	     JRDesignExpression expression6 = new JRDesignExpression();
	    	     expression6.setValueClass(java.lang.String.class);
	    	     expression6.setText(String.valueOf(wcount));
	    	     textField6.setExpression(expression6);
	    	     summary.addElement(textField6);
	    	     List ylist=vFundDetailService.getyqList( endDate, o.getOrgId(), "principalRepayment");	    	    
	    	     BigDecimal ymoney=new BigDecimal(0);
	    	     BigInteger ycount=new BigInteger("0");
	    	     if(null!=ylist && ylist.size()>0){
	    	    	 Object[] obj=(Object[]) ylist.get(0);
	    	    	 if(null!=obj[0]){
	    	    		 ycount=(BigInteger) obj[0];
	    	    	 }
	    	    	 if(null!=obj[1]){
	    	    		 ymoney=(BigDecimal) (obj[1]);
	    	    	 }
	    	     }
	    	     sumymoney=sumymoney.add(ymoney);
	    	     sumycount=sumycount.add(ycount);
	    	     JRDesignTextField textField7 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text7")).clone();
		   	     textField7.setX(150+columnWidth*i);
		   	     textField7.setY(150);
		   	     textField7.setWidth(columnWidth);
		   	     textField7.setHeight(25);
		   	     textField7.setFontSize(15);
		   	     JRDesignExpression expression7 = new JRDesignExpression();
		   	     expression7.setValueClass(java.lang.String.class);
		   	     expression7.setText(String.format("%.2f", (ymoney.doubleValue()/10000)));
		   	     textField7.setExpression(expression7);
		   	     summary.addElement(textField7);
		     
		   	     JRDesignTextField textField8 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text8")).clone();
		   	     textField8.setX(150+columnWidth*i);
		   	     textField8.setY(175);
		   	     textField8.setWidth(columnWidth);
		   	     textField8.setHeight(25);
		   	     textField8.setFontSize(15);
		   	     JRDesignExpression expression8 = new JRDesignExpression();
		   	     expression8.setValueClass(java.lang.String.class);
		   	     expression8.setText(String.valueOf(ycount));
		   	     textField8.setExpression(expression8);
		   	     summary.addElement(textField8);
		    }
		    JRDesignTextField textField = (JRDesignTextField)(((JRDesignBand)jasperDesign.getColumnHeader()).getElementByKey("header")).clone();
	   	     textField.setX(150+columnWidth*columnNum);
	   	     textField.setY(0);
	   	     textField.setWidth(80);
	   	     textField.setHeight(40);
	   	     textField.setFontSize(15);
	   	     JRDesignExpression expression = new JRDesignExpression();
	   	     expression.setValueClass(java.lang.String.class);
	   	     expression.setText("'合计'");
	   	     textField.setExpression(expression);
	   	     columnHeader.addElement(textField);
	   	     
	   	     JRDesignTextField textField1 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text1")).clone();
	   	     textField1.setX(150+columnWidth*columnNum);
	   	     textField1.setY(0);
	   	     textField1.setWidth(80);
	   	     textField1.setHeight(25);
	   	     textField1.setFontSize(15);
	   	     JRDesignExpression expression1 = new JRDesignExpression();
	   	     expression1.setValueClass(java.lang.String.class);
	   	     expression1.setText(String.format("%.2f", (sumfmoney.doubleValue()/10000)));
	   	     textField1.setExpression(expression1);
	   	     summary.addElement(textField1);
	   	     
	   	 	JRDesignTextField textField2 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text2")).clone();
	 	     textField2.setX(150+columnWidth*columnNum);
	 	     textField2.setY(25);
	 	     textField2.setWidth(80);
	 	     textField2.setHeight(25);
	 	     textField2.setFontSize(15);
	 	     JRDesignExpression expression2 = new JRDesignExpression();
	 	     expression2.setValueClass(java.lang.String.class);
	 	     expression2.setText(String.valueOf(sumfcount));
	 	     textField2.setExpression(expression2);
	 	     summary.addElement(textField2);
 	     
	 	     JRDesignTextField textField3 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text3")).clone();
	   	     textField3.setX(150+columnWidth*columnNum);
	   	     textField3.setY(50);
	   	     textField3.setWidth(80);
	   	     textField3.setHeight(25);
	   	     textField3.setFontSize(15);
	   	     JRDesignExpression expression3 = new JRDesignExpression();
	   	     expression3.setValueClass(java.lang.String.class);
	   	     expression3.setText(String.format("%.2f", (sumsmoney.doubleValue()/10000)));
	   	     textField3.setExpression(expression3);
	   	     summary.addElement(textField3);
		   	  JRDesignTextField textField4 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text4")).clone();
	 	     textField4.setX(150+columnWidth*columnNum);
	 	     textField4.setY(75);
	 	     textField4.setWidth(80);
	 	     textField4.setHeight(25);
	 	     textField4.setFontSize(15);
	 	     JRDesignExpression expression4 = new JRDesignExpression();
	 	     expression4.setValueClass(java.lang.String.class);
	 	     expression4.setText(String.valueOf(sumscount));
	 	     textField4.setExpression(expression4);
	 	     summary.addElement(textField4);
	 	     
	 	     JRDesignTextField textField5 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text5")).clone();
	   	     textField5.setX(150+columnWidth*columnNum);
	   	     textField5.setY(100);
	   	     textField5.setWidth(80);
	   	     textField5.setHeight(25);
	   	     textField5.setFontSize(15);
	   	     JRDesignExpression expression5 = new JRDesignExpression();
	   	     expression5.setValueClass(java.lang.String.class);
	   	     expression5.setText(String.format("%.2f", (sumwmoney.doubleValue()/10000)));
	   	     textField5.setExpression(expression5);
	   	     summary.addElement(textField5);
   	     
	   	     JRDesignTextField textField6 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text6")).clone();
	   	     textField6.setX(150+columnWidth*columnNum);
	   	     textField6.setY(125);
	   	     textField6.setWidth(80);
	   	     textField6.setHeight(25);
	   	     textField6.setFontSize(15);
	   	     JRDesignExpression expression6 = new JRDesignExpression();
	   	     expression6.setValueClass(java.lang.String.class);
	   	     expression6.setText(String.valueOf(sumwcount));
	   	     textField6.setExpression(expression6);
	   	     summary.addElement(textField6);
	   	     
	   	     JRDesignTextField textField7 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text7")).clone();
	   	     textField7.setX(150+columnWidth*columnNum);
	   	     textField7.setY(150);
	   	     textField7.setWidth(80);
	   	     textField7.setHeight(25);
	   	     textField7.setFontSize(15);
	   	     JRDesignExpression expression7 = new JRDesignExpression();
	   	     expression7.setValueClass(java.lang.String.class);
	   	     expression7.setText(String.format("%.2f", (sumymoney.doubleValue()/10000)));
	   	     textField7.setExpression(expression7);
	   	     summary.addElement(textField7);
	     
	   	     JRDesignTextField textField8 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text8")).clone();
	   	     textField8.setX(150+columnWidth*columnNum);
	   	     textField8.setY(175);
	   	     textField8.setWidth(80);
	   	     textField8.setHeight(25);
	   	     textField8.setFontSize(15);
	   	     JRDesignExpression expression8 = new JRDesignExpression();
	   	     expression8.setValueClass(java.lang.String.class);
	   	     expression8.setText(String.valueOf(sumycount));
	   	     textField8.setExpression(expression8);
	   	     summary.addElement(textField8);
	   	     
		    JRDesignTextField textFieldDel = (JRDesignTextField)(((JRDesignBand)jasperDesign.getColumnHeader()).getElementByKey("header"));
		    columnHeader.removeElement(textFieldDel); 
		    JRDesignTextField textFieldDel1 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text1"));
		    summary.removeElement(textFieldDel1);
		    JRDesignTextField textFieldDel2 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text2"));
		    summary.removeElement(textFieldDel2);
		    JRDesignTextField textFieldDel3 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text3"));
		    summary.removeElement(textFieldDel3);
		    JRDesignTextField textFieldDel4 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text4"));
		    summary.removeElement(textFieldDel4);
		    JRDesignTextField textFieldDel5 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text5"));
		    summary.removeElement(textFieldDel5);
		    JRDesignTextField textFieldDel6 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text6"));
		    summary.removeElement(textFieldDel6);
		    JRDesignTextField textFieldDel7 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text7"));
		    summary.removeElement(textFieldDel7);
		    JRDesignTextField textFieldDel8 = (JRDesignTextField)(((JRDesignBand)jasperDesign.getSummary()).getElementByKey("text8"));
		    summary.removeElement(textFieldDel8);
//		    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        JasperCompileManager.compileReportToFile(jasperDesign, reportPath1.split("jrxml")[0]+"jasper");
		    setJsonString("{success:true,data:'&startDate="+startDate+"&endDate="+endDate+"'}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String submitProfitRate(){
		try{
			String startDate=this.getRequest().getParameter("startDate");
			String endDate=this.getRequest().getParameter("endDate");
			String companyId=this.getRequest().getParameter("companyId");
			String strs=ContextUtil.getBranchIdsStr();
			if(null!=companyId && !"".equals(companyId)){
				strs=companyId;
			}
			DateFormat f=new SimpleDateFormat("yyyy-MM-dd");
			String companyName="";
			String remarks="";
			if(null!=strs && !"".equals(strs)){
				String[] str=strs.split(",");
				if(str.length>1){
					companyName="多公司汇总";
					for(int i=0;i<str.length;i++){
						Organization org=organizationService.get(Long.valueOf(str[i]));
						remarks=remarks+org.getAcronym()+",";
					}
					if(!remarks.equals("")){
						remarks="注："+remarks.substring(0,remarks.length()-1);
					}
				}else if(str.length==1){
					Organization org=organizationService.get(Long.valueOf(str[0]));
					companyName=org.getAcronym();
				}
			}
			List list=profitRateMaintenanceService.getList(startDate, endDate, strs);
			long lowCount=0;
			long standardCount=0;
			long upCount=0;
			BigDecimal lowMoney=new BigDecimal(0);
			BigDecimal standardMoney=new BigDecimal(0);
			BigDecimal upMoney=new BigDecimal(0);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				Date eDate=(Date) obj[1];
				BigDecimal accrual=(BigDecimal)obj[2];
				BigDecimal projectMoney=(BigDecimal)obj[3]; 
				Date sDate=(Date)obj[4];
				Date adjustDate=(Date)obj[5];
				long monthday;
			    Date startDate1 = f.parse(sDate.toString());
	            //开始时间与今天相比较
	            Date endDate1 =f.parse(eDate.toString());

	            Calendar starCal = Calendar.getInstance();
	            starCal.setTime(startDate1);

	            int sYear = starCal.get(Calendar.YEAR);
	            int sMonth = starCal.get(Calendar.MONTH);
	            int sDay = starCal.get(Calendar.DATE);

	            Calendar endCal = Calendar.getInstance();
	            endCal.setTime(endDate1);
	            int eYear = endCal.get(Calendar.YEAR);
	            int eMonth = endCal.get(Calendar.MONTH);
	            int eDay = endCal.get(Calendar.DATE);

	            monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

	            if (sDay < eDay) {
	                monthday = monthday + 1;
	            }
	            List<ProfitRateMaintenance> plist=profitRateMaintenanceService.getRateList(adjustDate);
	            if(null!=plist && plist.size()>0){
		            ProfitRateMaintenance profitRateMaintenance=plist.get(0);
		            if(monthday<=6){
						if(accrual.multiply(new BigDecimal(48)).doubleValue()<profitRateMaintenance.getSixMonthLow().doubleValue()){
							lowCount=lowCount+1;
							lowMoney=lowMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()==profitRateMaintenance.getSixMonthLow().doubleValue()){
							standardCount=standardCount+1;
							standardMoney=standardMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()>profitRateMaintenance.getSixMonthLow().doubleValue()){
							upCount=upCount+1;
							upMoney=upMoney.add(projectMoney);
						}
					}else if(monthday>6 && monthday<=12){
						if(accrual.multiply(new BigDecimal(48)).doubleValue()<profitRateMaintenance.getOneYear().doubleValue()){
							lowCount=lowCount+1;
							lowMoney=lowMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()==profitRateMaintenance.getOneYear().doubleValue()){
							standardCount=standardCount+1;
							standardMoney=standardMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()>profitRateMaintenance.getOneYear().doubleValue()){
							upCount=upCount+1;
							upMoney=upMoney.add(projectMoney);
						}
					}else if(monthday>12 && monthday<=36){
						if(accrual.multiply(new BigDecimal(48)).doubleValue()<profitRateMaintenance.getThreeYear().doubleValue()){
							lowCount=lowCount+1;
							lowMoney=lowMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()==profitRateMaintenance.getThreeYear().doubleValue()){
							standardCount=standardCount+1;
							standardMoney=standardMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()>profitRateMaintenance.getThreeYear().doubleValue()){
							upCount=upCount+1;
							upMoney=upMoney.add(projectMoney);
						}
					}else if(monthday>36 && monthday<=60){
						if(accrual.multiply(new BigDecimal(48)).doubleValue()<profitRateMaintenance.getFiveYear().doubleValue()){
							lowCount=lowCount+1;
							lowMoney=lowMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()==profitRateMaintenance.getFiveYear().doubleValue()){
							standardCount=standardCount+1;
							standardMoney=standardMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()>profitRateMaintenance.getFiveYear().doubleValue()){
							upCount=upCount+1;
							upMoney=upMoney.add(projectMoney);
						}
					}else if(monthday>60){
						if(accrual.multiply(new BigDecimal(48)).doubleValue()<profitRateMaintenance.getFiveYearUp().doubleValue()){
							lowCount=lowCount+1;
							lowMoney=lowMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()==profitRateMaintenance.getFiveYearUp().doubleValue()){
							standardCount=standardCount+1;
							standardMoney=standardMoney.add(projectMoney);
						}else if(accrual.multiply(new BigDecimal(48)).doubleValue()>profitRateMaintenance.getFiveYearUp().doubleValue()){
							upCount=upCount+1;
							upMoney=upMoney.add(projectMoney);
						}
					}
				}	
			}
		setJsonString("{success:true,data:'&startDate="+startDate+"&endDate="+endDate+"&searchDate="+f.format(new Date())+"&companyName="+companyName+"&lowCount="+lowCount+"&standardCount="+standardCount+"&upCount="+upCount+"&lowMoney="+lowMoney+"&standardMoney="+standardMoney+"&upMoney="+upMoney+"&remarks="+remarks+"'}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String submitLoanInfoSummary(){
		String endDate=this.getRequest().getParameter("endDate");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null==endDate || "".equals(endDate)){
			endDate=sd.format(new Date());
		}
		if(null!=reportKey && reportKey.equals("monthLoanSummary")){
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtil.parseDate(endDate,"yyyy-MM"));
			int MaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay);
			String curendDate = sdf.format(c.getTime());
			 
			Date date=DateUtil.addMonthsToDate(DateUtil.parseDate(endDate,"yyyy-MM"), -1);
			c.setTime(date);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay);
			String sendDate= sdf.format(c.getTime());
			setJsonString("{success:true,data:'&endDate=" + sendDate + "&curEndDate="+curendDate+"'}");
		}else if(null!=reportKey && reportKey.equals("seasonLoanSummary")){
			String[] arr=endDate.split("-");
			String season1=arr[1];
			String curendDate="";
			if(season1.equals("01") || season1.equals("02") || season1.equals("03")){
				curendDate=arr[0]+"-03-31";
			}else if(season1.equals("04") || season1.equals("05") || season1.equals("06")){
				curendDate=arr[0]+"-06-30";
			}else if(season1.equals("07") || season1.equals("08") || season1.equals("09")){
				curendDate=arr[0]+"-09-30";
			}else if(season1.equals("10") || season1.equals("11") || season1.equals("12")){
				curendDate=arr[0]+"-12-31";
			}
			Date date=DateUtil.addMonthsToDate(DateUtil.parseDate(endDate,"yyyy-MM"), -3);
			String[] arr1=sd.format(date).split("-");
			String sendDate="";
			String season2=arr1[1];
			if(season2.equals("01") || season2.equals("02") || season2.equals("03")){
				sendDate=arr1[0]+"-03-31";
			}else if(season2.equals("04") || season2.equals("05") || season2.equals("06")){
				sendDate=arr1[0]+"-06-30";
			}else if(season2.equals("07") || season2.equals("08") || season2.equals("09")){
				sendDate=arr1[0]+"-09-30";
			}else if(season2.equals("10") || season2.equals("11") || season2.equals("12")){
				sendDate=arr1[0]+"-12-31";
			}
			setJsonString("{success:true,data:'&endDate=" + sendDate + "&curEndDate="+curendDate+"'}");
		}else if(null!=reportKey && reportKey.equals("yearLoanSummary")){
			String[] arr=endDate.split("-");
			String curendDate=arr[0]+"-12-31";
			Date date=DateUtil.addMonthsToDate(DateUtil.parseDate(endDate,"yyyy-MM"), -12);
			String[] arr1=sd.format(date).split("-");
			String sendDate=arr1[0]+"-12-31";
			setJsonString("{success:true,data:'&endDate=" + sendDate + "&curEndDate="+curendDate+"'}");
		}
		return SUCCESS;
	}
	
	public String submitLoanCommon(){
		String startDate=this.getRequest().getParameter("startDate");
		String endDate=this.getRequest().getParameter("endDate");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		if(null==endDate || "".equals(endDate)){
			endDate=sd.format(new Date());
		}
		
		String sql="";
		String createDate="";
		if(null!=reportKey && (reportKey.equals("personContrant") || reportKey.equals("companyContrant"))){
			if(null==startDate || "".equals(startDate)){
				sql=" and s.loanStartDate<= \""+endDate+"\"";
			}else{
				sql=" and s.loanStartDate<= \""+endDate+"\" and s.loanStartDate>=\""+startDate+"\"";
			}
			createDate=sd.format(new Date());
		}else if(null!=reportKey && reportKey.equals("loanMonth")){
			if(null==startDate || "".equals(startDate)){
				sql=" and s.startDate<= \""+endDate+"\"";
			}else{
				sql=" and s.startDate<= \""+endDate+"\" and s.startDate>=\""+startDate+"\"";
			}
			createDate=new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
		}
		setJsonString("{success:true,data:'&contions="+sql+"&createDate="+createDate+"&startDate="+startDate+"&endDate="+endDate+"'}");
		return SUCCESS;
	}
	
	public String submitIncome(){
		String startDate=this.getRequest().getParameter("startDate");
		String endDate=this.getRequest().getParameter("endDate");
		String customerName=this.getRequest().getParameter("customerName");
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		if(null==endDate || "".equals(endDate)){
			endDate=df.format(new Date());
		}
		if(reportKey.equals("loanIncome")){
			String sqlCondition="";
			if(null==startDate || "".equals(startDate)){
				sqlCondition=" and s.loanStartDate<=\""+endDate+"\" and s.customerName like \"%"+customerName+"%\"";
			}else{
				sqlCondition=" and s.loanStartDate>=\""+startDate+"\" and s.loanStartDate<=\""+endDate+"\" and s.customerName like \"%"+customerName+"%\"";
			}
			setJsonString("{success:true,data:'&sqlCondition=" +sqlCondition + "&createDate="+df.format(new Date())+"&startDate="+startDate+"&endDate="+endDate+" '}");
		}else if(reportKey.equals("newLoanIncome")){
			String sqlCondition="";
			String sqlCondition1="";
			String sqlCondition2="";
			String sqlCondition3="";
			String sqlCondition4="";
			if(null==startDate || "".equals(startDate)){
				sqlCondition=sqlCondition+" and DATE_FORMAT(f.intentDate,\"%Y-%m-%d\")<=\""+endDate+"\"";
				sqlCondition1=sqlCondition1+" and DATE_FORMAT(sf.intentDate,\"%Y-%m-%d\")<=\""+endDate+"\"";
				sqlCondition2=sqlCondition2+" and DATE_FORMAT(v.factDate,\"%Y-%m-%d\")<=\""+endDate+"\"";
				sqlCondition3=sqlCondition3+" and DATE_FORMAT(vf.factDate,\"%Y-%m-%d\")<=\""+endDate+"\"";
				sqlCondition4=sqlCondition4+" where (DATE_FORMAT(s.intentDate,\"%Y-%m-%d\")<=\""+endDate+"\" and s.isValid=0 and s.isCheck=0 and s.isInitialorId is null and s.fundType in (\"loanInterest\",\"consultationMoney\",\"serviceMoney\",\"backInterest\")) or (DATE_FORMAT(vfd.factDate,\"%Y-%m-%d\")<=\""+endDate+"\" and vfd.iscancel is null and vfd.funType in (\"loanInterest\",\"consultationMoney\",\"serviceMoney\",\"backInterest\")) and sp.customerName like \"%"+customerName+"%\"";
			}else{
				sqlCondition=sqlCondition+" and DATE_FORMAT(f.intentDate,\"%Y-%m-%d\")<=\""+endDate+"\" and DATE_FORMAT(f.intentDate,\"%Y-%m-%d\")>=\""+startDate+"\"";
				sqlCondition1=sqlCondition1+" and DATE_FORMAT(sf.intentDate,\"%Y-%m-%d\")<=\""+endDate+"\" and DATE_FORMAT(sf.intentDate,\"%Y-%m-%d\")>=\""+startDate+"\"";
				sqlCondition2=sqlCondition2+" and DATE_FORMAT(v.factDate,\"%Y-%m-%d\")<=\""+endDate+"\" and DATE_FORMAT(v.factDate,\"%Y-%m-%d\")>=\""+startDate+"\"";
				sqlCondition3=sqlCondition3+" and DATE_FORMAT(vf.factDate,\"%Y-%m-%d\")<=\""+endDate+"\" and DATE_FORMAT(vf.factDate,\"%Y-%m-%d\")>=\""+startDate+"\"";
				sqlCondition4=sqlCondition4+" where ((DATE_FORMAT(s.intentDate,\"%Y-%m-%d\")<=\""+endDate+"\" and DATE_FORMAT(s.intentDate,\"%Y-%m-%d\")>=\""+startDate+"\" and s.isValid=0 and s.isCheck=0 and s.isInitialorId is null and s.fundType in (\"loanInterest\",\"consultationMoney\",\"serviceMoney\",\"backInterest\")) or (DATE_FORMAT(vfd.factDate,\"%Y-%m-%d\")<=\""+endDate+"\" and DATE_FORMAT(vfd.factDate,\"%Y-%m-%d\")>=\""+startDate+"\" and vfd.iscancel is null and vfd.funType in (\"loanInterest\",\"consultationMoney\",\"serviceMoney\",\"backInterest\"))) and sp.customerName like \"%"+customerName+"%\"";
			}
			setJsonString("{success:true,data:'&sqlCondition=" +sqlCondition + "&sqlCondition1="+sqlCondition1+"&sqlCondition2="+sqlCondition2+"&sqlCondition3="+sqlCondition3+"&sqlCondition4="+sqlCondition4+"&createDate="+df.format(new Date())+"&startDate="+startDate+"&endDate="+endDate+" '}");
		}
		return SUCCESS;
	}
	
	public String submitLoanCustomerCount(){
		String startDate=this.getRequest().getParameter("startDate");
		String endDate=this.getRequest().getParameter("endDate");
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		if(null==endDate || "".equals(endDate)){
			endDate=df.format(new Date());
		}
		String sqlCondition="";
		String condition="";
		String sqlCondition1="";
		if(null==startDate || "".equals(startDate)){
			sqlCondition=" and s.startDate<=\""+endDate+"\"";
			condition="("+endDate+"之前)";
			sqlCondition1=" and p.startDate<=\""+endDate+"\"";
		}else{
			sqlCondition=" and s.startDate>=\""+startDate+"\" and s.startDate<=\""+endDate+"\"";
			condition="("+startDate+"至"+endDate+")";
			sqlCondition1=" and p.startDate>=\""+startDate+"\" and p.startDate<=\""+endDate+"\"";
		}
		setJsonString("{success:true,data:'&sqlCondition=" +sqlCondition + "&condition="+condition+"&sqlCondition1="+sqlCondition1+"'}");
		return SUCCESS;
	}
//	
	public String submitPawnDetail(){
		String customerName=this.getRequest().getParameter("customerName");
		String phnumber=this.getRequest().getParameter("phnumber");
		String startDate1=this.getRequest().getParameter("startDate1");
		String startDate2=this.getRequest().getParameter("startDate2");
		String companyId=this.getRequest().getParameter("companyId");
		String projectStatus=this.getRequest().getParameter("projectStatus");
		String appUserId=this.getRequest().getParameter("appUserId");
		String intentDate1=this.getRequest().getParameter("intentDate1");
		String intentDate2=this.getRequest().getParameter("intentDate2");
		String appUserName=this.getRequest().getParameter("appUserName");
//		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer contions=new StringBuffer("查询条件:");
		StringBuffer sqlContions=new StringBuffer("");
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			sqlContions.append(" and v.companyId in ("+str+")");
		}
		if(null!=customerName && !"".equals(customerName)){
			sqlContions.append(" and v.customerName like \"%"+customerName+"%\"");
			contions.append("客户名称:"+customerName+",");
		}
		if(null!=phnumber && !phnumber.equals("")){
			sqlContions.append(" and v.phnumber like \"%"+phnumber+"%\"");
			contions.append("当票号:"+phnumber+",");
		}
		if(null!=startDate1 && !"".equals(startDate1) && null!=startDate2 && !"".equals(startDate2)){
			sqlContions.append(" and v.startDate>=\""+startDate1+"\" and v.startDate<=\""+startDate2+"\"");
			contions.append("开始日期:"+startDate1+"至"+startDate2+",");
		}else if(null!=startDate1 && !"".equals(startDate1) && (null==startDate2 || "".equals(startDate2))){
			sqlContions.append(" and v.startDate>=\""+startDate1+"\"");
			contions.append("开始日期：大于等于"+startDate1+",");
		}else if(null!=startDate2 && !"".equals(startDate2) && (null==startDate1 || "".equals(startDate1))){
			sqlContions.append(" and v.startDate<=\""+startDate2+"\"");
			contions.append("开始日期：小于等于"+startDate2+",");
		}
		if(null!=projectStatus && !"".equals(projectStatus)){
			sqlContions.append(" and v.projectStatus="+projectStatus);
			contions.append("项目状态："+(projectStatus.equals("0")?"审批中项目":(projectStatus.equals("1")?"在当项目" :(projectStatus.equals("4")?"续当项目":(projectStatus.equals("5")?"赎当项目":"绝当项目"))))+",");
		}
		if(null!=appUserId && !appUserId.equals("")){
			sqlContions.append(" and v.appUserId like \"%"+appUserId+"%\"");
			contions.append("项目经理："+appUserName+",");
		}
		if(null!=intentDate1 && !"".equals(intentDate1) && null!=intentDate2 && !"".equals(intentDate2)){
			sqlContions.append(" and ((v.continuedIntentDate is null and v.intentDate>=\""+intentDate1+"\" and v.intentDate<=\""+intentDate2+"\") or (v.continuedIntentDate is not null and v.continuedIntentDate>=\""+intentDate1+"\" and v.continuedIntentDate<=\""+intentDate2+"\"))");
			contions.append("到期日期:"+intentDate1+"至"+intentDate2+",");
		}else if(null!=intentDate1 && !"".equals(intentDate1) && (null==intentDate2 || "".equals(intentDate2))){
			sqlContions.append(" and ((v.continuedIntentDate is null and v.intentDate>=\""+intentDate1+"\") or (v.continuedIntentDate is not null and v.continuedIntentDate>=\""+intentDate1+"\"))");
			contions.append("开始日期：大于等于"+intentDate1+",");
		}else if(null!=intentDate2 && !"".equals(intentDate2) && (null==intentDate1 || "".equals(intentDate1))){
			sqlContions.append(" and ((v.continuedIntentDate is null  and v.intentDate<=\""+intentDate2+"\") or (v.continuedIntentDate is not null  and v.continuedIntentDate<=\""+intentDate2+"\"))");
			contions.append("开始日期：小于等于"+intentDate2+",");
		}
		String contionsStr="";
		if(!contions.toString().equals("查询条件:")){
			contionsStr=contions.toString().substring(0,contions.toString().length()-1);
		}
		setJsonString("{success:true,data:'&sqlContions=" +sqlContions.toString() + "&contions="+contionsStr+"'}");
		return SUCCESS;
	}
	
	public String submitMonthPawnPayment(){
		String monthQuery=this.getRequest().getParameter("monthQuery");
		String companyId=this.getRequest().getParameter("companyId");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		if(null!=monthQuery && !"".equals(monthQuery)){
			String startDate=monthQuery+"-01";
			Date date=DateUtil.addMonthsToDate(DateUtil.parseDate(monthQuery+"-01"), 1);
			String sDate=monthQuery.substring(0,monthQuery.indexOf("-"));
			sDate=sDate+"-01-01";
			List<SlFundIntent> list=slFundIntentService.getList("pawnPrincipalRepayment", sd.format(date),companyId);
			Integer finalCount=0;
			BigDecimal finalBalance=new BigDecimal(0);
			for(SlFundIntent s:list){
				BigDecimal principalMoney=s.getIncomeMoney();
				List<VFundDetail> vlist=vFundDetailService.listByFundType("pawnPrincipalRepayment", s.getProjectId(), s.getBusinessType());
				for(VFundDetail v:vlist){
					if(v.getFundIntentId().toString().equals(s.getFundIntentId().toString()) && v.getFactDate().compareTo(date)<0 && v.getIscancel()==null){
						principalMoney=principalMoney.subtract(new BigDecimal(v.getAfterMoney()));
					}
				}
				if(principalMoney.compareTo(new BigDecimal(0))!=0){
					finalCount=finalCount+1;
					finalBalance=finalBalance.add(principalMoney);
				}
			}
			Integer monthCount=0;
			Double monthMoney=new Double(0);
			List dlist=vFundDetailService.getListByFundType("pawnPrincipalLending", companyId, startDate, sd.format(date));
			if(null!=dlist && dlist.size()>0){
				monthCount=dlist.size();
				for(int i=0;i<dlist.size();i++){
					monthMoney=monthMoney+(Double)dlist.get(i);
				}
			}
			Integer yearCount=0;
			Double yearMoney=new Double(0);
			List vflist=vFundDetailService.getListByFundType("pawnPrincipalLending", companyId, sDate, sd.format(date));
			if(null!=vflist && vflist.size()>0){
				yearCount=vflist.size();
				for(int i=0;i<vflist.size();i++){
					yearMoney=yearMoney+(Double)vflist.get(i);
				}
			}
			String conditions="查询月份："+monthQuery;
			setJsonString("{success:true,data:'&finalCount="+finalCount+"&finalBalance="+finalBalance+"&monthCount="+monthCount+"&monthMoney="+monthMoney+"&yesrCount="+yearCount+"&yearMoney="+yearMoney+"&conditions="+conditions+"'}");
		}else{
			setJsonString("{success:true,data:''}");
		}
		return SUCCESS;
	}
	
	public String submitLeaseFinanceDetail(){
		String customerName=this.getRequest().getParameter("customerName");
		String projectNumber=this.getRequest().getParameter("projectNumber");
		String startDate1=this.getRequest().getParameter("startDate1");
		String startDate2=this.getRequest().getParameter("startDate2");
		String companyId=this.getRequest().getParameter("companyId");
		String projectStatus=this.getRequest().getParameter("projectStatus");
		String appUserId=this.getRequest().getParameter("appUserId");
		String intentDate1=this.getRequest().getParameter("intentDate1");
		String intentDate2=this.getRequest().getParameter("intentDate2");
		String appUserName=this.getRequest().getParameter("appUserName");
//		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer contions=new StringBuffer("查询条件:");
		StringBuffer sqlContions=new StringBuffer("");
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			sqlContions.append(" and v.companyId in ("+str+")");
		}
		if(null!=customerName && !"".equals(customerName)){
			sqlContions.append(" and v.customerName like \"%"+customerName+"%\"");
			contions.append("客户名称:"+customerName+",");
		}
		if(null!=projectNumber && !projectNumber.equals("")){
			sqlContions.append(" and v.projectNumber like \"%"+projectNumber+"%\"");
			contions.append("当票号:"+projectNumber+",");
		}
		if(null!=startDate1 && !"".equals(startDate1) && null!=startDate2 && !"".equals(startDate2)){
			sqlContions.append(" and v.startDate>=\""+startDate1+"\" and v.startDate<=\""+startDate2+"\"");
			contions.append("开始日期:"+startDate1+"至"+startDate2+",");
		}else if(null!=startDate1 && !"".equals(startDate1) && (null==startDate2 || "".equals(startDate2))){
			sqlContions.append(" and v.startDate>=\""+startDate1+"\"");
			contions.append("开始日期：大于等于"+startDate1+",");
		}else if(null!=startDate2 && !"".equals(startDate2) && (null==startDate1 || "".equals(startDate1))){
			sqlContions.append(" and v.startDate<=\""+startDate2+"\"");
			contions.append("开始日期：小于等于"+startDate2+",");
		}
		if(null!=projectStatus && !"".equals(projectStatus)){
			sqlContions.append(" and v.projectStatus="+projectStatus);
			contions.append("项目状态："+(projectStatus.equals("0")?"办理中项目" :(projectStatus.equals("1")?"还款中项目":(projectStatus.equals("2")?"已完成项目":(projectStatus.equals("8")?"违约处置项目":"提前终止项目"))))+",");
		}
		if(null!=appUserId && !appUserId.equals("")){
			sqlContions.append(" and v.appUserId like \"%"+appUserId+"%\"");
			contions.append("项目经理："+appUserName+",");
		}
		if(null!=intentDate1 && !"".equals(intentDate1) && null!=intentDate2 && !"".equals(intentDate2)){
			sqlContions.append(" and ((v.superviseEndDate is null and v.intentDate>=\""+intentDate1+"\" and v.intentDate<=\""+intentDate2+"\") or (v.superviseEndDate is not null and v.superviseEndDate>=\""+intentDate1+"\" and v.superviseEndDate<=\""+intentDate2+"\"))");
			contions.append("到期日期:"+intentDate1+"至"+intentDate2+",");
		}else if(null!=intentDate1 && !"".equals(intentDate1) && (null==intentDate2 || "".equals(intentDate2))){
			sqlContions.append(" and ((v.superviseEndDate is null and v.intentDate>=\""+intentDate1+"\") or (v.superviseEndDate is not null and v.superviseEndDate>=\""+intentDate1+"\"))");
			contions.append("开始日期：大于等于"+intentDate1+",");
		}else if(null!=intentDate2 && !"".equals(intentDate2) && (null==intentDate1 || "".equals(intentDate1))){
			sqlContions.append(" and ((v.superviseEndDate is null  and v.intentDate<=\""+intentDate2+"\") or (v.superviseEndDate is not null  and v.superviseEndDate<=\""+intentDate2+"\"))");
			contions.append("开始日期：小于等于"+intentDate2+",");
		}
		String contionsStr="";
		if(!contions.toString().equals("查询条件:")){
			contionsStr=contions.toString().substring(0,contions.toString().length()-1);
		}
		setJsonString("{success:true,data:'&sqlConditions=" +sqlContions.toString() + "&conditions="+contionsStr+"'}");
		return SUCCESS;
	}
	
	public String printcomm(){
		long start = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportKey=this.getRequest().getParameter("reportKey");
		String[] orderIds =this.getRequest().getParameter("orderIds").split(",");
		String matchIds =this.getRequest().getParameter("matchIds");
		ReportTemplate rt=reportTemplateService.getReportTemplateByKey(reportKey);
		String reportPath=rt.getReportLocation();
		String reportId=rt.getId().toString();
		String rootPath=uploadPath;
		File fullPath = new File(rootPath + "/" + reportPath);
		String reportPath1=fullPath.getPath();
		Connection conn=null;
		try {
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
			conn = dataSource.getConnection();	
			List jasperList= new ArrayList();
		    if (orderIds != null) {
			   for (String orderId :orderIds) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderId", orderId);
					List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbysearch(null, map);
					BigDecimal xiaoxiepromisIncomesum=new BigDecimal(0);
					BigDecimal xiaoxiebuyMoney=new BigDecimal(0);
					Date qingdingDate=new Date();
					for(PlMmOrderChildrenOr l:list){
						xiaoxiepromisIncomesum=xiaoxiepromisIncomesum.add(l.getMatchingGetMoney());
						xiaoxiebuyMoney=xiaoxiebuyMoney.add(l.getMatchingMoney());
						qingdingDate=l.getMatchingStartDate();
					}
					Map parameters = new HashMap();
					parameters.put("qingdingDate", qingdingDate);
					parameters.put("xiaoxiepromisIncomesum", xiaoxiepromisIncomesum.toString());
					parameters.put("daxiepromisIncomesum", MoneyFormat.getInstance().format(xiaoxiepromisIncomesum.doubleValue()));
					parameters.put("xiaoxiebuyMoney",xiaoxiebuyMoney.toString());
					parameters.put("daxiebuyMoney", MoneyFormat.getInstance().format(xiaoxiebuyMoney.doubleValue()));
					
					PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoService.get(Long.valueOf(orderId));  
					if(order.getKeystr().equals("mmplan")|| order.getKeystr().equals("UPlan") ){
						BpCustMember bpcustmember=bpCustMemberService.get(order.getInvestPersonId());
						parameters.put("postaddress", bpcustmember.getCardcode());
						parameters.put("cardnumber", bpcustmember.getRelationAddress());
						parameters.put("postCode", bpcustmember.getPostCode());
					}else{
						CsInvestmentperson csInvestmentperson=csInvestmentpersonService.get(order.getInvestPersonId());
						parameters.put("postaddress", csInvestmentperson.getPostaddress()!=null?csInvestmentperson.getPostaddress():"");
						parameters.put("cardnumber", csInvestmentperson.getCardnumber()!=null?csInvestmentperson.getCardnumber():"");
						parameters.put("postCode", csInvestmentperson.getPostcode()!=null?csInvestmentperson.getPostcode():"");
					}
					parameters.put("investPersonName", order.getInvestPersonName());
					parameters.put("endDate",sdf.format(order.getEndinInterestTime()) );
					parameters.put("mmName", order.getMmName());
					parameters.put("sqlcondition", " and childrenOr.matchId in ("+matchIds+")");
					JasperPrint jasperPrint1 = JasperFillManager.fillReport(reportPath1, parameters,conn);
					jasperList.add(jasperPrint1);	
				}
				this.getRequest().getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE, jasperList);
				setJsonString("{success:true,reportId:" +reportId+"}");
			    System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DOCX creation time : " );
		}
		return SUCCESS;
	}
	
	public void printlist() throws IOException{
		JRXml4SwfExporter exporter = new JRXml4SwfExporter();
		try {
			List jasperPrintList = (List)this.getRequest().getSession().getAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,this.getResponse().getOutputStream()); 
			exporter.exportReport();
			this.getResponse().getOutputStream().close();
			this.getResponse().getOutputStream().flush();
			this.getRequest().getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE,null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.getResponse().getOutputStream().close();
		}
	}
	
	public String printcommfirst() throws SQLException{
		long start = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportKey=this.getRequest().getParameter("reportKey");
		String[] orderIds =this.getRequest().getParameter("orderIds").split(",");
		String matchIds =this.getRequest().getParameter("matchIds");
		String firstst =this.getRequest().getParameter("firstst");
		ReportTemplate rt=reportTemplateService.getReportTemplateByKey(reportKey);
		String reportPath=rt.getReportLocation();
		String reportId=rt.getId().toString();
		String rootPath=uploadPath;
		File fullPath = new File(rootPath + "/" + reportPath);
		String reportPath1=fullPath.getPath();
		Connection conn = null;
		try {
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
            conn = dataSource.getConnection();	
          	if(null==matchIds||"".equals(matchIds)){
          		matchIds=firstst;
          	}  
          	List jasperList= new ArrayList();
          	if (orderIds != null) {
          		for (String orderId :orderIds) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderId", orderId);
					List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbyIds(matchIds);
					BigDecimal xiaoxiepromisIncomesum=new BigDecimal(0);
					BigDecimal xiaoxiebuyMoney=new BigDecimal(0);
					
					Date qingdingDate=new Date();
					for(PlMmOrderChildrenOr l:list){
//						xiaoxiepromisIncomesum=xiaoxiepromisIncomesum.add(l.getMatchingGetMoney());
						xiaoxiebuyMoney=xiaoxiebuyMoney.add(l.getMatchingMoney());
						qingdingDate=l.getMatchingStartDate();
					}
					Map parameters = new HashMap();
					parameters.put("qingdingDate", qingdingDate);
					parameters.put("xiaoxiebuyMoney",xiaoxiebuyMoney.toString());
					parameters.put("daxiebuyMoney", MoneyFormat.getInstance().format(xiaoxiebuyMoney.doubleValue()));
					
					PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoService.get(Long.valueOf(orderId));
					xiaoxiepromisIncomesum=order.getPromisIncomeSum();
					parameters.put("xiaoxiepromisIncomesum", xiaoxiepromisIncomesum.toString());
					parameters.put("daxiepromisIncomesum", MoneyFormat.getInstance().format(xiaoxiepromisIncomesum.doubleValue()));
					
					if(order.getKeystr().equals("mmplan")){
						BpCustMember bpcustmember=bpCustMemberService.get(order.getInvestPersonId());
						parameters.put("postaddress", bpcustmember.getCardcode());
						parameters.put("cardnumber", bpcustmember.getRelationAddress());
						parameters.put("postCode", bpcustmember.getPostCode());
					}else{
						CsInvestmentperson csInvestmentperson=csInvestmentpersonService.get(order.getInvestPersonId());
						if(null!=csInvestmentperson){
							parameters.put("postaddress", csInvestmentperson.getPostaddress());
							parameters.put("cardnumber", csInvestmentperson.getCardnumber());
							parameters.put("postCode", csInvestmentperson.getPostcode());
						}
					}
					parameters.put("investPersonName", order.getInvestPersonName());
					parameters.put("endDate",sdf.format(order.getEndinInterestTime()) );
					parameters.put("mmName", order.getMmName());
					parameters.put("sqlcondition", " and childrenOr.matchId in ("+matchIds+")");
					JasperPrint jasperPrint1 = JasperFillManager.fillReport(reportPath1, parameters,conn);
					jasperList.add(jasperPrint1);	
				}
				this.getRequest().getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE, jasperList);
				setJsonString("{success:true,reportId:" +reportId+"}");
			    System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
			 }
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DOCX creation time : " );
		}finally{
			conn.close();
		}
		return SUCCESS;
	}
	
	public String printcommevery() throws SQLException{
		long start = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportKey=this.getRequest().getParameter("reportKey");
		String[] orderIds =this.getRequest().getParameter("orderIds").split(",");
		String matchIds =this.getRequest().getParameter("matchIds");
		String everyst =this.getRequest().getParameter("everyst");
		ReportTemplate rt=reportTemplateService.getReportTemplateByKey(reportKey);
		String reportPath=rt.getReportLocation();
		String reportId=rt.getId().toString();
		String rootPath=uploadPath;
		File fullPath = new File(rootPath + "/" + reportPath);
		String reportPath1=fullPath.getPath();
		Connection conn = null;
		try {
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
			conn = dataSource.getConnection();	
			if(null==matchIds||"".equals(matchIds)){
				matchIds=everyst;
			}  
			List jasperList= new ArrayList();
			if (orderIds != null) {
				for (String orderId :orderIds) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderId", orderId);
					List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbyIds(matchIds);
					BigDecimal xiaoxiepromisIncomesum=new BigDecimal(0);
					BigDecimal xiaoxiebuyMoney=new BigDecimal(0);
					
					Date qingdingDate=new Date();
					for(PlMmOrderChildrenOr l:list){
						xiaoxiepromisIncomesum=xiaoxiepromisIncomesum.add(l.getMatchingGetMoney());
						xiaoxiebuyMoney=xiaoxiebuyMoney.add(l.getMatchingMoney());
						qingdingDate=l.getMatchingStartDate();
					}
					Map parameters = new HashMap();
					parameters.put("qingdingDate", qingdingDate);
					parameters.put("xiaoxiepromisIncomesum", xiaoxiepromisIncomesum.toString());
					parameters.put("daxiepromisIncomesum", MoneyFormat.getInstance().format(xiaoxiepromisIncomesum.doubleValue()));
					parameters.put("xiaoxiebuyMoney",xiaoxiebuyMoney.toString());
					parameters.put("daxiebuyMoney", MoneyFormat.getInstance().format(xiaoxiebuyMoney.doubleValue()));
					
					PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoService.get(Long.valueOf(orderId));
					if(order.getKeystr().equals("mmplan")){
						BpCustMember bpcustmember=bpCustMemberService.get(order.getInvestPersonId());
						parameters.put("postaddress", bpcustmember.getCardcode());
						parameters.put("cardnumber", bpcustmember.getRelationAddress());
						parameters.put("postCode", bpcustmember.getPostCode());
					}else{
						CsInvestmentperson csInvestmentperson=csInvestmentpersonService.get(order.getInvestPersonId());
						if(null!=csInvestmentperson){
							parameters.put("postaddress", csInvestmentperson.getPostaddress());
							parameters.put("cardnumber", csInvestmentperson.getCardnumber());
							parameters.put("postCode", csInvestmentperson.getPostcode());
						}
					}
					parameters.put("investPersonName", order.getInvestPersonName());
					parameters.put("endDate",sdf.format(order.getEndinInterestTime()) );
					parameters.put("mmName", order.getMmName());
					parameters.put("sqlcondition", " and childrenOr.matchId in ("+matchIds+")");
					JasperPrint jasperPrint1 = JasperFillManager.fillReport(reportPath1, parameters,conn);
					jasperList.add(jasperPrint1);	
				}
				this.getRequest().getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE, jasperList);
				setJsonString("{success:true,reportId:" +reportId+"}");
			    System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DOCX creation time : " );
		}finally{
			conn.close();
		}
		return SUCCESS;
	}
	
	public String printcommend() throws SQLException{
		long start = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportKey=this.getRequest().getParameter("reportKey");
		String[] orderIds =this.getRequest().getParameter("orderIds").split(",");
		String matchIds =this.getRequest().getParameter("matchIds");
		ReportTemplate rt=reportTemplateService.getReportTemplateByKey(reportKey);
		String reportPath=rt.getReportLocation();
		String reportId=rt.getId().toString();
		String rootPath=uploadPath;
		File fullPath = new File(rootPath + "/" + reportPath);
		String reportPath1=fullPath.getPath();
		Connection conn = null;
		try {
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
			conn = dataSource.getConnection();	
			List jasperList= new ArrayList();
			if (orderIds != null) {
				for (String orderId :orderIds) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderId", orderId);
					List<PlMmOrderChildrenOr> list= plMmOrderChildrenOrService.listbyIds(matchIds);
					BigDecimal xiaoxiepromisIncomesum=new BigDecimal(0);
					BigDecimal xiaoxiebuyMoney=new BigDecimal(0);
					
					Date qingdingDate=new Date();
					if(null!=list && list.size()>0){
						for(PlMmOrderChildrenOr l:list){
							xiaoxiepromisIncomesum=xiaoxiepromisIncomesum.add(l.getMatchingGetMoney());
							xiaoxiebuyMoney=xiaoxiebuyMoney.add(l.getMatchingMoney());
							qingdingDate=l.getMatchingStartDate();
						}
					}
					Map parameters = new HashMap();
					parameters.put("qingdingDate", qingdingDate);
					parameters.put("xiaoxiepromisIncomesum", xiaoxiepromisIncomesum.toString());
					parameters.put("daxiepromisIncomesum", MoneyFormat.getInstance().format(xiaoxiepromisIncomesum.doubleValue()));
					parameters.put("xiaoxiebuyMoney",xiaoxiebuyMoney.toString());
					parameters.put("daxiebuyMoney", MoneyFormat.getInstance().format(xiaoxiebuyMoney.doubleValue()));
					
					PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoService.get(Long.valueOf(orderId));
					if(order.getKeystr().equals("mmplan")){
						BpCustMember bpcustmember=bpCustMemberService.get(order.getInvestPersonId());
						parameters.put("postaddress", bpcustmember.getCardcode());
						parameters.put("cardnumber", bpcustmember.getRelationAddress());
						parameters.put("postCode", bpcustmember.getPostCode());
					}else{
						CsInvestmentperson csInvestmentperson=csInvestmentpersonService.get(order.getInvestPersonId());
						if(null!=csInvestmentperson){
							parameters.put("postaddress", csInvestmentperson.getPostaddress());
							parameters.put("cardnumber", csInvestmentperson.getCardnumber());
							parameters.put("postCode", csInvestmentperson.getPostcode());
						}
					}
					parameters.put("investPersonName", order.getInvestPersonName());
					parameters.put("endDate",sdf.format(order.getEndinInterestTime()) );
					parameters.put("mmName", order.getMmName());
					if(null!=matchIds && !"".equals(matchIds)){
						parameters.put("sqlcondition", " and childrenOr.matchId in ("+matchIds+")");
					}
					JasperPrint jasperPrint1 = JasperFillManager.fillReport(reportPath1, parameters,conn);
					jasperList.add(jasperPrint1);	
				}
				this.getRequest().getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE, jasperList);
				setJsonString("{success:true,reportId:" +reportId+"}");
				System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
			  }
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DOCX creation time : " );
		}finally{
			conn.close();
		}
		return SUCCESS;
	}
	
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportKey() {
		return reportKey;
	}

	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
}