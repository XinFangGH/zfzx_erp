package com.zhiwei.credit.action.creditFlow.finance;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;

public class ExeclToMysqlAction  extends BaseAction{
	@Resource
	private SlFundQlideService slFundQlideService;
	@Resource
	private SlBankAccountService slBankAccountService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	
	private String excelsqlName;
	private File excelsql;
	private SlFundQlide slFundQlide;
	
	
	
	public String getExcData(){
		String flag=this.getRequest().getParameter("uploadtype");
		if(flag.equals("qlideupload")){
			String b;
			int i=0;
			if(null !=slFundQlideService.getAll()){
				i=slFundQlideService.getAll().size();
			}
			if(null==excelsql){
				b="请选择上传文件";
				setJsonString(b);
				return "false";
			}
			String sourceProductImagePath=upLoadImgFile(excelsql);
			List<SlBankAccount> accountList=slBankAccountService.getAll();
			b =QlideExeclMysql.readExcelToDB2(sourceProductImagePath,accountList,enterpriseBankService);
			setJsonString(b );
			if(b.substring(0,4).equals("上传成功")){
				uploadafter(i); 
				accountFinalMoney();
				return "upLoadSuccess";
			}else{
				return "upLoadFalse";
			}
		}	
		if(flag.equals("bankaccountupload")){
			String b;
			String sourceProductImagePath=upLoadImgFile(excelsql);
			List<SlBankAccount> accountList=slBankAccountService.getAll();
			b =BankAccountExeclMysql.readExcelToDB2(sourceProductImagePath,accountList);
			setJsonString(b );
			if(b.substring(0,4).equals("上传成功")){
				return "upLoadSuccess";
			}else{
				return "upLoadFalse";
			}
		}
		return null;
	}
	
	public String upLoadImgFile(File imgefile){
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = UUID.randomUUID().toString();
		String subuuid= uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
		String sourceProductImagePath = "attachFiles/excelupload/" + dateString + "/"  +subuuid+ ".xls";
		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourceProductImagePath));
		File sourceProductImageParentFile = sourceProductImageFile.getParentFile();
		
		if (!sourceProductImageParentFile.exists()) {
			sourceProductImageParentFile.mkdirs();
		}
		try {
			
			FileUtils.copyFile(imgefile, sourceProductImageFile);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("记录上传出错"+e.getMessage());
		}
		sourceProductImagePath =ServletActionContext.getServletContext().getRealPath(sourceProductImagePath);
		return sourceProductImagePath;
	}
	
	public void accountFinalMoney(){
		
		
		List<SlBankAccount> accountList=slBankAccountService.getAll();
		List<SlFundQlide> qlidelist= slFundQlideService.getAll();
		List<SlFundQlide> sortlist= new ArrayList<SlFundQlide>();
		for(SlBankAccount a:accountList){
			a.setFinalMoney(a.getRawMoney());
			for(SlFundQlide q:qlidelist){
				
				if(q.getMyAccount().equals(a.getAccount())){
					if(q.getIncomeMoney()!=null)
					{ a.setFinalMoney(a.getFinalMoney().add(q.getIncomeMoney()));
					}
					if(q.getPayMoney()!=null){
						a.setFinalMoney(a.getFinalMoney().subtract(q.getPayMoney()));
					}
					sortlist.add(q);
					
				}
				
			}
			
			
			 if(null !=sortlist &&sortlist.size() !=0){
				 MyCompareqlide comp=new MyCompareqlide();
				 Collections.sort(sortlist,comp);
				 a.setFinalDate(sortlist.get(sortlist.size()-1).getFactDate());
			 }
			 sortlist.clear();
			slBankAccountService.save(a);
			
		}
		
	}
	public void uploadafter(int i){
		
		int all=0;
		
		List<SlFundQlide> list=slFundQlideService.getAll();
		all=slFundQlideService.getAll().size();
		for(int k=i;k<all;k++){
			slFundQlide=list.get(k);
			int pl=0; 	
		  
		    List<SlBankAccount> slBankAccountlist=	slBankAccountService.getAll();
		    for(SlBankAccount s:slBankAccountlist){
		    	
			   	if(null !=s.getAccount()&&null !=slFundQlide.getOpAccount()&&slFundQlide.getOpAccount().equals(s.getAccount())){
			    		pl++;
			    		break;
			    	}
			    	
			    }
		}
		
	}
	
	/*
	 *  下载线下投资客户录入模版
	 */
	public void downloanExcel(){
		String serverPath = this.getRequest().getRealPath("/");
		FileHelper.downLoadFile(serverPath+"attachFiles/projFile/fundFlowTemplate/资金流水模版.xls","xls","资金流水模版", this.getResponse());
	}
	
	public String getExcelsqlName() {
		return excelsqlName;
	}
	public void setExcelsqlName(String excelsqlName) {
		this.excelsqlName = excelsqlName;
	}
	public File getExcelsql() {
		return excelsql;
	}
	public void setExcelsql(File excelsql) {
		this.excelsql = excelsql;
	}
	public SlFundQlide getSlFundQlide() {
		return slFundQlide;
	}
	public void setSlFundQlide(SlFundQlide slFundQlide) {
		this.slFundQlide = slFundQlide;
	}
	
  }     

