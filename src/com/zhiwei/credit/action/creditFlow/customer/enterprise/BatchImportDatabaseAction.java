package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedEnvelope;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedMemberService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class BatchImportDatabaseAction extends BaseAction  {

	private static final long serialVersionUID = 1L;
	private File fileBatch ;
	private String fileBatchFileName ;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpCustRedMemberService bpCustRedMemberService;
	public final int UPLOAD_SIZE = 1024 * 1024 * 10;
	public void setFileBatch(File fileBatch) {
		this.fileBatch = fileBatch;
	}
	public String getFileBatchFileName() {
		return fileBatchFileName;
	}
	public void setFileBatchFileName(String fileBatchFileName) {
		this.fileBatchFileName = fileBatchFileName;
	}
	public File getFileBatch() {
		return fileBatch;
	}

	public void batchImportE()
	{
		String jsonStr = "" ;
		String path = batchUploads();
		boolean flag =true; //POIdataBaseMysql.readExcelToDB2(path);
		
			try{
				  
				  Workbook book = Workbook.getWorkbook(new File(path)) ;
				  Sheet sheet = book.getSheet(0);
				  int rows = sheet.getRows();
				  List<Enterprise> enterprises=new ArrayList<Enterprise>();
				  for(int i = 1; i <rows; i++) 
				  {        
					       Enterprise enterprise =new Enterprise();
					       
					       Cell [] cell = sheet.getRow(i);
					       for(int j=0; j<cell.length; j++)
					       {
					    	   if(j==1) //企业名称
					    	   {
					    		   enterprise.setEnterprisename(sheet.getCell(j, i).getContents());
					           }
					    	   else if(j==2){
					    		   enterprise.setShortname(sheet.getCell(j, i).getContents());
					    	   }
					    	   else if(j==3){
					    		   enterprise.setOrganizecode(sheet.getCell(j, i).getContents());
					    	   }
					    	   else if(j==4){
					    		   enterprise.setCciaa(sheet.getCell(j, i).getContents());
					    	   }
					    	   else if(j==7){
					    		   enterprise.setRegistermoney(Double.valueOf(sheet.getCell(j, i).getContents()));
					    	   }
					    	   else if(j==8){
					    		   enterprise.setTelephone(sheet.getCell(j, i).getContents());
					    	   }
					    	   else if(j==9){
					    		   enterprise.setCreatedate(DateUtil.parseDate(sheet.getCell(j, i).getContents(), "yyyy-MM-dd"));
					    	   }
					       }
					       enterprises.add(enterprise);
				   }
				  book.close();
				  for(int i=0;i<enterprises.size();i++){
					  this.enterpriseService.save(enterprises.get(i));
				  }
			 }
			catch (Exception e) {
				e.printStackTrace();
			}
		 
		if(flag){
			jsonStr = "{success : true}";
			JsonUtil.responseJsonString(jsonStr,"text/html; charset=utf-8");
		}else
			jsonStr = "{success : false}";
		JsonUtil.responseJsonString(jsonStr,"text/html; charset=utf-8");
	} 
	public void batchImportP()
	{
		String path = batchUploads();
		try{
				  
				  Workbook book = Workbook.getWorkbook(new File(path)) ;
				  Sheet sheet = book.getSheet(0);
				  int rows = sheet.getRows();
				  List<Person> persons=new ArrayList<Person>();
				  for(int i = 1; i <rows; i++) 
				  {        
					       Person person =new Person();
					       Cell [] cell = sheet.getRow(i);
					       for(int j=0; j<cell.length; j++)
					       {
					    	   if(j==1) //企业名称
					    	   {
					    		   person.setName(sheet.getCell(j, i).getContents());
					           }
					    	   else if(j==2){
					    		   if(!"".equals(sheet.getCell(j, i).getContents())){
					    			   person.setSex(Integer.valueOf(sheet.getCell(j, i).getContents()));
					    		   }
					    	   }
					    	   else if(j==3){
					    		  // person.set(sheet.getCell(j, i).getContents());
					    	   }
					    	   else if(j==4){
					    		   //person.setjob;
					    	   }
					    	   else if(j==7){
					    		   person.setCellphone(sheet.getCell(j, i).getContents());
					    	   }
					    	   else if(j==8){
					    		   person.setTelphone(sheet.getCell(j, i).getContents());
					    	   }
					    	   else if(j==9){
					    		   person.setBirthday(DateUtil.parseDate(sheet.getCell(j, i).getContents(), "yyyy-MM-dd"));
					    	   }
					       }
					       persons.add(person);
				   }
				  book.close();
				  for(int i=0;i<persons.size();i++){
					  this.personService.addPersonImport(persons.get(i));
				  }
			 }
			catch (Exception e) {
				e.printStackTrace();
			}
	} 
	public String batchUploads(){
		File file =null ;
		String realPath = ServletActionContext.getServletContext().getRealPath("/credit/customer/enterprise");
		file = new File(realPath+"/"+fileBatchFileName);
		if(FileHelper.fileUpload(fileBatch ,file , new byte[UPLOAD_SIZE])){
			return realPath +"/"+ fileBatchFileName ;
		}else
			return "" ;
	}
	public void batchImportFinance() {
		String jsonStr = "" ;
		String path = batchUploads();
		boolean flag =true; //POIdataBaseMysql.readExcelToDB2(path);
		StringBuffer buff = new StringBuffer("");
		try{
			Workbook book = Workbook.getWorkbook(new File(path)) ;
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows();
			for(int i = 1; i <rows; i++) {
				Cell [] cell = sheet.getRow(i);
				buff.append("'row_"+i+"':{");
				for(int j=2;j<cell.length;j++) {
					if(j != cell.length-1) {
						buff.append("'column_"+j+"':'"+sheet.getCell(j, i).getContents()+"',");
					}else {
						buff.append("'column_"+j+"':'"+sheet.getCell(j, i).getContents()+"'");
					}
				}
				if(i != rows -1){
					buff.append("},");
				}else {
					buff.append("}");
				}
			}
			book.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(flag){
			jsonStr = "{success : true,data:{"+buff.toString()+"}}";
		}else
			jsonStr = "{success : false}";
		JsonUtil.responseJsonString(jsonStr,"text/html; charset=utf-8");
	}
	public String importRedData(){
		String jsonStr = "" ;
		String path = batchUploads();
		 List<BpCustRedMember> valuelist=new ArrayList<BpCustRedMember>();
		Long     recordPersonId= ContextUtil.getCurrentUserId();
			try{
				  
				  Workbook book = Workbook.getWorkbook(new File(path)) ;
				  Sheet sheet = book.getSheet(0);
				  int rows = sheet.getRows();
				 
				  
				  for(int i = 0; i <rows; i++) 
				  {        
					  BpCustRedMember l =new BpCustRedMember();
					       Cell [] cell = sheet.getRow(i);
					       for(int j=0; j<cell.length; j++)
					       {
					    	   System.out.println(i+"=="+j+"---"+sheet.getCell(j, i).getContents());
					    	   
					    	   if(i>0&&j==1){
					    			l.setLoginname(sheet.getCell(j, i).getContents());
					    	   }
					    	 
					    	   if(i>0&&j==2){
					    			l.setRedMoney(new BigDecimal(sheet.getCell(j, i).getContents()));
					    	   }
					    	   
					    	   
					       }
					       if(i>0){
						       BpCustMember	 	bpCustMember=bpCustMemberService.getMemberUserName(l.getLoginname());
							  if(bpCustMember ==null){
								  setJsonString("{failure:true,msg:'"+l.getLoginname()+"'}");
								  return SUCCESS;
							  }
						       l.setEmail(bpCustMember.getEmail());
							   	l.setTelphone(bpCustMember.getTelphone());
							   	l.setThirdPayFlag0(bpCustMember.getThirdPayFlagId());
							   	l.setBpCustMemberId(bpCustMember.getId());
							   	l.setTruename(bpCustMember.getTruename());
							   	valuelist.add(l);
					       }
				  }
				  book.close();
				  
			 }
			catch (Exception e) {
				e.printStackTrace();
			}
		 
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			setJsonString("{success:true,result:"+gson.toJson(valuelist)+",totalCounts:"+valuelist.size()+"}");
			
			return SUCCESS;
	}
}
