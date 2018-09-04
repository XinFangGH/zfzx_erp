package com.webServices.finance.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.axis2.AxisFault;
import org.eclipse.jdt.internal.compiler.lookup.ImportBinding;

import nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.Exception;
import nc.itf.crd.webservice.izyhtwebservice.IZyhtWebServiceStub;
import nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData;
import nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData;
import nc.vo.crd.acc.realpi.realpibvo.RealPiBVO;
import nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO;

import com.webServices.custom.BaseCustomService;
import com.webServices.finance.FundQlideFromWebService;
import com.webServices.services.factory.modelfactory.ZyVo;
import com.webServices.services.factory.modelfactory.base.ZyhtVoFactory;
import com.webServices.services.factory.urlFactory.WebServicesUrl;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.finance.SlBankAccountDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundQlideDao;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;
import com.zhiwei.credit.service.system.OrganizationService;
public class FundQlideFromWebServiceImpl  extends BaseServiceImpl<SlFundQlide> implements FundQlideFromWebService{
	@SuppressWarnings("unchecked")
	@Resource
	private SlFundQlideService slFundQlideService;
	@Resource
	private SlBankAccountService slBankAccountService;
	@Resource
	private SlBankAccountDao slBankAccountDao;
	private SlFundQlideDao dao;
	private List<SlBankAccount>  crtAccountlist;//收方账号
	private List<SlBankAccount>  dbAccountlist;//付方账号
	
	private List<SlBankAccount>  crtAccountlist2;//收方账号
	private List<SlBankAccount>  dbAccountlist2;//付方账号
	private ZyhtVoFactory zyvo;
	private SimpleDateFormat format ;
	private List<SlFundQlide>  qlidelist;
	private IZyhtWebServiceStub stub=null ;
	private  EbankDzdPlusVO[] ebankDzdPluslist=null;
	private EbankDzdPlusVO ep;
	@Resource
	private OrganizationService organizationService;
	public FundQlideFromWebServiceImpl(SlFundQlideDao dao) {
		super(dao);
		this.dao =dao;
	}
	@Override 
	public String[] getqlideManual(String startT, String endT,
			Long companyId) { //手动同步数据
		String[] str=new String[2];
		int bankListLengh=0;
		int importCount=0;//导入多少条
		int notImportCount=0;//有多少条未导入
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		if(isOpen){
		try {
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
			str[0]="N";
			str[1]="webServices 获取有误！";
		}
		BankDzdDataDocument  rpData = BankDzdDataDocument.Factory.newInstance();
		BankDzdData bankDzdData = BankDzdData.Factory.newInstance();
		zyvo=new ZyVo();
		
		 bankDzdData.setZyhtVO(zyvo.createVo(companyId.toString(), startT, endT));
		 rpData.setBankDzdData(bankDzdData);
		 if(isOpen){
		try {
			ebankDzdPluslist = stub.bankDzdData(rpData).getBankDzdDataResponse().getReturnArray();
			bankListLengh=ebankDzdPluslist.length;
		} catch (RemoteException e) {
			e.printStackTrace();
			str[0]="N";
			str[1]="webServices 获取数据有误";
		}
		 }
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		qlidelist= slFundQlideService.getAll();  
		if(null != ebankDzdPluslist&&ebankDzdPluslist.length !=0){
		 for(int i=0;i<ebankDzdPluslist.length;i++){
			  ep= ebankDzdPluslist[i];
			int k=0;
			 for(SlFundQlide q:qlidelist){
				if(q.getWebId() !=null&&q.getWebId().toString().equals(ep.getPkEbankDzd())){
					k++;
					notImportCount++;
					break;
				}
				
			}
			 if(k==0){//说明没有导入过这条流水
				 //curacc=Crtacc curacc账号是 收入    curacc=Dbtacc curacc账号是 支出
				 crtAccountlist=slBankAccountService.webgetbyaccount(ep.getCuracc(),companyId);// 获取当前账号 是否在系统中存在
				 if(null !=crtAccountlist &&crtAccountlist.size()!=0){
					 SlFundQlide fundQlide=new SlFundQlide();
					 if(ep.getCuracc().equals(ep.getCrtacc())){ //判断当前账号 是否和 银行流水 收方账号 相等 如果相等说明 该账户为 收入
						 
						 fundQlide.setMyAccount(ep.getCuracc());//我方账号
						 fundQlide.setIncomeMoney(BigDecimal.valueOf(ep.getTrsamt()));
					try {
						
						if(ep.getTransDate()!=null&&ep.getTransTime()!=null){
							fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+ep.getTransTime()));
						}else if(ep.getTransDate()!=null&&ep.getTransTime()==null){
							fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+"00:00:00"));
						}else{
							fundQlide.setFactDate(new Date());
						}
					} catch (ParseException e) {
						e.getStackTrace();
					}
						
					if(ep.getDbtdwmc()!=null){
						fundQlide.setOpOpenName(ep.getDbtdwmc());//对方名称
					}
					  
					fundQlide.setOpAccount(ep.getDbtacc());//对放账号
						 
					fundQlide.setPayMoney(new BigDecimal("0"));
					fundQlide.setCompanyId(companyId);
					fundQlide.setNotMoney(fundQlide.getIncomeMoney());
					fundQlide.setAfterMoney(new BigDecimal(0.00));
					fundQlide.setOperTime(new Date());
					fundQlide.setBankTransactionType("normalTransactionType");
	
					fundQlide.setWebgetTime(new Date());
					fundQlide.setWebId(ep.getPkEbankDzd());
					fundQlide.setIsProject("是");
						 AppUser userInfo = ContextUtil.getCurrentUser();
					//	 crtQlide.setWebuserId(userInfo.getUserId());
						 dao.save(fundQlide);
						 
					 }else if(ep.getCuracc().equals(ep.getDbtacc())){//判断当前账号 是否和 银行流水 付方账号 相等 如果相等说明 该账户为 支出
						 fundQlide.setMyAccount(ep.getCuracc());//我方账号
						 //System.out.print(ep.getTransDate()+" "+ep.getTransTime());
						 fundQlide.setIncomeMoney(new BigDecimal(0.00));
						try {
							if(ep.getTransDate()!=null&&ep.getTransTime()!=null){
							fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+ep.getTransTime()));
						}else if(ep.getTransDate()!=null&&ep.getTransTime()==null){
							fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+"00:00:00"));
						}else{
							fundQlide.setFactDate(new Date());
						}
							} catch (ParseException e) {
							e.printStackTrace();
						}
						
						fundQlide.setOpAccount(ep.getCrtacc());//对方账号
						
						if(ep.getCrtdwmc()!=null){
							fundQlide.setOpOpenName(ep.getCrtdwmc());//我方名称
						}
						 
						fundQlide.setPayMoney(BigDecimal.valueOf(ep.getTrsamt()));
						fundQlide.setCompanyId(companyId);
						fundQlide.setNotMoney(fundQlide.getPayMoney());
						fundQlide.setAfterMoney(new BigDecimal(0.00));
						fundQlide.setOperTime(new Date());
						fundQlide.setBankTransactionType("normalTransactionType");
	
						fundQlide.setWebgetTime(new Date());
						fundQlide.setWebId(ep.getPkEbankDzd());
						fundQlide.setIsProject("是");
						 AppUser userInfo = ContextUtil.getCurrentUser();
				//		 dbQlide.setWebuserId(userInfo.getUserId());
						 dao.save(fundQlide);
						 
					 }
					 importCount++;
				 }
					
			 }
		 }
		}
		str[0]="Y";
		str[1]="共获取："+bankListLengh+"条记录,其中系统中已存在"+notImportCount+"条记录，共导入："+importCount+"条记录。";
		} catch (Exception e) {
		e.printStackTrace();
	} 
		}else{
			str[0]="N";
			str[1]="未打开webservice地址请检查";
		}
	ep=null;
	qlidelist=null;
	ebankDzdPluslist=null;
	crtAccountlist=null;
	dbAccountlist=null;
	importCount=0;
	notImportCount=0;
	bankListLengh=0;
		return str;
	}
	@Override
	public String getqlideTiming() { //自动同步数据
		try {
			String startT="";//查询开始日期
			String endT="";//查询结束日期
		List<Organization> olist=organizationService.getAllByOrgType();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Date d=new Date();
		
		for(Organization o:olist){
			List<SlFundQlide> fqList=slFundQlideService.getLastByMaxDate(o.getOrgId());//获取最后插入的日期的记录
			if(fqList!=null&&fqList.size()!=0){
				startT=sd.format(fqList.get(0).getWebgetTime());
				endT=sd.format(d);
			}
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		BankDzdDataDocument  rpData = BankDzdDataDocument.Factory.newInstance();
		BankDzdData bankDzdData = BankDzdData.Factory.newInstance();
		zyvo=new ZyVo();
		
		 bankDzdData.setZyhtVO(zyvo.createVo(o.getOrgId().toString(), startT, endT));
		 rpData.setBankDzdData(bankDzdData);
		 if(isOpen){
		try {
			ebankDzdPluslist = stub.bankDzdData(rpData).getBankDzdDataResponse().getReturnArray();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		 }
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		qlidelist= slFundQlideService.getAll();  
		if(null != ebankDzdPluslist&&ebankDzdPluslist.length !=0){
		 for(int i=0;i<ebankDzdPluslist.length;i++){
			  ep= ebankDzdPluslist[i];
			int k=0;
			 for(SlFundQlide q:qlidelist){
				if(q.getWebId() !=null&&q.getWebId().toString().equals(ep.getPkEbankDzd())){
					k++;
					break;
				}
				
			}
			 if(k==0){//说明没有导入过这条流水
				 //curacc=Crtacc curacc账号是 收入    curacc=Dbtacc curacc账号是 支出
				 crtAccountlist=slBankAccountService.webgetbyaccount(ep.getCuracc(),o.getOrgId());// 获取当前账号 是否在系统中存在
				 if(null !=crtAccountlist &&crtAccountlist.size()!=0){
					 SlFundQlide fundQlide=new SlFundQlide();
					 if(ep.getCuracc().equals(ep.getCrtacc())){ //判断当前账号 是否和 银行流水 收方账号 相等 如果相等说明 该账户为 收入
						 
						 fundQlide.setMyAccount(ep.getCuracc());//我方账号
						 fundQlide.setIncomeMoney(BigDecimal.valueOf(ep.getTrsamt()));
					try {
						if(ep.getTransDate()!=null&&ep.getTransTime()!=null){
							fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+ep.getTransTime()));
						}else if(ep.getTransDate()!=null&&ep.getTransTime()==null){
							fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+"00:00:00"));
						}else{
							fundQlide.setFactDate(new Date());
						}
					} catch (ParseException e) {
						e.getStackTrace();
					}
					if(ep.getDbtdwmc()!=null){
						fundQlide.setOpOpenName(ep.getDbtdwmc());//对方名称
					}	
					fundQlide.setOpAccount(ep.getDbtacc());//对放账号
						 
					fundQlide.setPayMoney(new BigDecimal("0"));
					fundQlide.setCompanyId(o.getOrgId());
					fundQlide.setNotMoney(fundQlide.getIncomeMoney());
					fundQlide.setAfterMoney(new BigDecimal(0.00));
					fundQlide.setOperTime(new Date());
					fundQlide.setBankTransactionType("normalTransactionType");
	
					fundQlide.setWebgetTime(new Date());
					fundQlide.setWebId(ep.getPkEbankDzd());
					fundQlide.setIsProject("是");
						 AppUser userInfo = ContextUtil.getCurrentUser();
					//	 crtQlide.setWebuserId(userInfo.getUserId());
						 dao.save(fundQlide);
						 
					 }else if(ep.getCuracc().equals(ep.getDbtacc())){//判断当前账号 是否和 银行流水 付方账号 相等 如果相等说明 该账户为 支出
						 fundQlide.setMyAccount(ep.getCuracc());//我方账号
						 //System.out.print(ep.getTransDate()+" "+ep.getTransTime());
						 fundQlide.setIncomeMoney(new BigDecimal(0.00));
						try {
							if(ep.getTransDate()!=null&&ep.getTransTime()!=null){
								fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+ep.getTransTime()));
							}else if(ep.getTransDate()!=null&&ep.getTransTime()==null){
								fundQlide.setFactDate(format.parse(ep.getTransDate()+" "+"00:00:00"));
							}else{
								fundQlide.setFactDate(new Date());
							}
							
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						fundQlide.setOpAccount(ep.getCrtacc());//对方账号
						if(ep.getCrtdwmc()!=null){
							fundQlide.setOpOpenName(ep.getCrtdwmc());//我方名称
						}
						fundQlide.setPayMoney(BigDecimal.valueOf(ep.getTrsamt()));
						fundQlide.setCompanyId(o.getOrgId());
						fundQlide.setNotMoney(fundQlide.getPayMoney());
						fundQlide.setAfterMoney(new BigDecimal(0.00));
						fundQlide.setOperTime(new Date());
						fundQlide.setBankTransactionType("normalTransactionType");
	
						fundQlide.setWebgetTime(new Date());
						fundQlide.setWebId(ep.getPkEbankDzd());
						fundQlide.setIsProject("是");
						 AppUser userInfo = ContextUtil.getCurrentUser();
				//		 dbQlide.setWebuserId(userInfo.getUserId());
						 dao.save(fundQlide);
						 
					 }
					 
				 }
					
			 }
		 }
		}
		 startT="";//查询开始日期
		endT="";//查询结束日期
		}} catch (Exception e) {
		e.printStackTrace();
	} 
	ep=null;
	qlidelist=null;
	ebankDzdPluslist=null;
	crtAccountlist=null;
	dbAccountlist=null;
		return null;
	}
	
}
