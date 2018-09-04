package com.zhiwei.credit.core.creditUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.credit.model.admin.FixedAssets;
import com.zhiwei.credit.model.admin.GoodsApply;
import com.zhiwei.credit.model.admin.InStock;
import com.zhiwei.credit.model.admin.OfficeGoods;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.VInvestmentPerson;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.VBankBankcontactperson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.VEnterprisePerson;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormBidIncomeDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateRedFinanceDetail;
import com.zhiwei.credit.model.creditFlow.financeProject.VFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidSale;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpLoneExternal;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductRate;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUserAccountInfo;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUseraccount;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.TaskProxy;
import com.zhiwei.credit.model.system.AppUser;

@SuppressWarnings("unchecked")
public class ExcelHelper {
	
	private static final Log logger=LogFactory.getLog(ExcelHelper.class);

	/**
	 * 导出数据到excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String export(List list,String []tableHeader,String headerStr) throws Exception {
		/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 * 下面的都可以拷贝不用编写
		 */
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置数据类型
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style2.setBorderLeft((short)1);     //左边框
		style2.setBorderRight((short)1);    //右边框
		style2.setBorderBottom((short)1);//下边框
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		try {
			/**
			 *根据是否取出数据，设置header信息
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				double sumMoney = 0d;
				double sumMoney2 = 0d;
				String countMoney ="";
				String countMoney2 ="";
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					if(headerStr.indexOf("个人")==0){
						switch(k){
							case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							case 1 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							case 2 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 4 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 5 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 6 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						}
					}else if(headerStr.indexOf("企业")==0){
						switch(k){
							case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							case 1 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
							break;
							case 6 : sheet.setColumnWidth(k, 2600);// 设置列的宽度
							break;
							case 7 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 8 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							default : sheet.setColumnWidth(k, 4500);// 设置列的宽度
						}
					}else if(headerStr.indexOf("银行")==0){
						switch(k){
							case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							case 1 : sheet.setColumnWidth(k, 3100);// 设置列的宽度
							break;
							case 2 : sheet.setColumnWidth(k, 1500);// 设置列的宽度
							break;
							case 3 : sheet.setColumnWidth(k, 2700);// 设置列的宽度
							break;
							case 6 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 7 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 8 : sheet.setColumnWidth(k, 4500);// 设置列的宽度
							break;
							case 9 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							default : sheet.setColumnWidth(k, 3600);// 设置列的宽度
						}
					}else if(headerStr.indexOf("还款")==0){
						switch(k){
							case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							case 1 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 2 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 3 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 6 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 7 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 8 : sheet.setColumnWidth(k, 4500);// 设置列的宽度
							break;
							case 9 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 10 : sheet.setColumnWidth(k, 4200);// 设置列的宽度
							break;
							default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						}
					}else if(headerStr.indexOf("权限")==0){
						switch(k){
							case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
							case 1 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 2 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 3 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						}
					}else if(headerStr.indexOf("全部款项催收")==0||headerStr.indexOf("即将到期款项催收")==0||headerStr.indexOf("已逾期款项催收")==0){
					  switch(k){
					  		case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
							break;
						  	case 1 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 2 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 3 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 4 : sheet.setColumnWidth(k,4000);// 设置列的宽度
							break;
							case 5 : sheet.setColumnWidth(k,4000);// 设置列的宽度
							break;
							case 6 : sheet.setColumnWidth(k,4000);// 设置列的宽度
							break;
							case 7 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 8 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
							break;
							case 9 : sheet.setColumnWidth(k, 4500);// 设置列的宽度
							break;
							default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
					  }
					}else if(headerStr.indexOf("融资业绩表")==0){
						switch(k){
						 	case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
								break;
						 	case 1 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
								break;
						 	case 2 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
								break;
						 	case 3 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
								break;
						 	case 4 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
								break;
						 	case 5 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
								break;
						 	case 6 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
								break;
						 	default : sheet.setColumnWidth(k, 6000);
						 }
					}else{
						switch(k){
							case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
								break;
							default : sheet.setColumnWidth(k, 6000);
						}
					}
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 *给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if(headerStr.indexOf("个人")==0){
						if(list.get(0) instanceof InvestPerson){
							InvestPerson investPerson = (InvestPerson)list.get(i);
							if(investPerson.getPerName()!=null){
								cell = row.createCell(1);
								cell.setCellValue(investPerson.getPerName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(investPerson.getPerSex()!=null){
								cell = row.createCell(2);
								cell.setCellValue(investPerson.getPerSex().equals("312")?"男":"女");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(investPerson.getCardType()!=null&&investPerson.getCardNumber()!=null){
								cell = row.createCell(3);
								cell.setCellValue(investPerson.getCardTypeValue()+"/"+investPerson.getCardNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(investPerson.getCustomerLevel()!=null){
								cell = row.createCell(4);
								if(investPerson.getCustomerLevel().equals("861")){
									cell.setCellValue("潜在客户");
								}else if(investPerson.getCustomerLevel().equals("862")){
									cell.setCellValue("正式客户");
								}else if(investPerson.getCustomerLevel().equals("863")){
									cell.setCellValue("大客户");
								}else{
									cell.setCellValue("");									
								}
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(investPerson.getPhoneNumber()!=null){
								cell = row.createCell(5);
								cell.setCellValue(investPerson.getPhoneNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(investPerson.getPerBirthday()!=null){
								cell = row.createCell(6);
								cell.setCellValue(investPerson.getPerBirthday().toString().substring(0,investPerson.getPerBirthday().toString().lastIndexOf(" ")));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(investPerson.getPostAddress()!=null){
								cell = row.createCell(7);
								cell.setCellValue(investPerson.getPostAddress());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
							if(investPerson.getLinkmanName()!=null){
								cell = row.createCell(8);
								cell.setCellValue(investPerson.getLinkmanName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(8);
								cell.setCellStyle(style);
							}
							if(investPerson.getLinkmanPhone()!=null){
								cell = row.createCell(9);
								cell.setCellValue(investPerson.getLinkmanPhone());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}
							if(investPerson.getFiliation()!=null){
								cell = row.createCell(10);
								cell.setCellValue(investPerson.getFiliation());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(10);
								cell.setCellStyle(style);
							}
							if(investPerson.getPerEmail()!=null){
								cell = row.createCell(11);
								cell.setCellValue(investPerson.getPerEmail());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(11);
								cell.setCellStyle(style);
							}
						}else{
							Person vperson = (Person) list.get(i);// 获取对象
							if (vperson.getName() != null) {
								cell = row.createCell(2); // 创建第i+1行第1列
								cell.setCellValue(vperson.getName());// 设置第i+1行第1列的值
								cell.setCellStyle(style); // 设置风格
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							// 由于下面的和上面的基本相同，就不加注释了
							if (vperson.getSexvalue() != null) {
								cell = row.createCell(3);
								cell.setCellValue(vperson.getSexvalue());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if (vperson.getShopName() != null) {
								cell = row.createCell(1);
								cell.setCellValue(vperson.getShopName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							/*if (vperson.getJobvalue() != null) {
								cell = row.createCell(4);
								cell.setCellValue(vperson.getJobvalue());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}*/
						/*	if (vperson.getBirthday() != null) {
								cell = row.createCell(3);
								cell.setCellValue(ExcelHelper.getAge(vperson.getBirthday())+"岁");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}*/
							
						/*	if (vperson.getMarryvalue() != null) {
								cell = row.createCell(5);
								cell.setCellValue(vperson.getMarryvalue());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}*/
							if (vperson.getCardtypevalue()!= null && vperson.getCardnumber()!=null) {
								cell = row.createCell(4);
								cell.setCellValue(vperson.getCardtypevalue()+"/"+vperson.getCardnumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if (vperson.getCellphone()!= null) {
								cell = row.createCell(5);
								cell.setCellValue(vperson.getCellphone());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if (vperson.getTelphone() != null) {
								cell = row.createCell(6);
								cell.setCellValue(vperson.getTelphone());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							/*if (vperson.getBirthday() != null) {
								cell = row.createCell(9);
								cell.setCellValue(vperson.getBirthday().toString().substring(0,vperson.getBirthday().toString().lastIndexOf(" ")));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}*/
						}
					}else if(headerStr.indexOf("企业")==0){
						Enterprise venterprise = (Enterprise) list.get(i);
						//所属门店
						if (venterprise.getShopName() != null) {
							cell = row.createCell(1); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getShopName());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						//企业名称
						if (venterprise.getEnterprisename() != null) {
							cell = row.createCell(2); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getEnterprisename());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
                        
						//证件号码
	                    if(venterprise.getCardnumber() != null){
							cell = row.createCell(3); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getCardnumber());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
	                    }else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
	                    }
						//税务号码
						if (venterprise.getTaxnum() != null) {
							cell = row.createCell(4); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getTaxnum());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}

						//法人
						if (venterprise.getLegalpersonName() != null) {
							cell = row.createCell(5); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getLegalpersonName());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						
						//注册资金
						if (venterprise.getRegistermoney() != null) {
							cell = row.createCell(6); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getRegistermoney());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}


						//联系电话
						if (venterprise.getTelephone() != null) {
							cell = row.createCell(7); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getTelephone());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}


						//企业成立日期
						if (venterprise.getOpendate() != null) {
							cell = row.createCell(8); // 创建第i+1行第1列
							cell.setCellValue(venterprise.getOpendate().toString());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						// 由于下面的和上面的基本相同，就不加注释了
/*						if (venterprise.getShortname() != null) {
							cell = row.createCell(2);
							cell.setCellValue(venterprise.getShortname());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (venterprise.getOrganizecode()!= null) {
							cell = row.createCell(3);
							cell.setCellValue(venterprise.getOrganizecode());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (venterprise.getLegalpersonName() != null) {
							cell = row.createCell(4);
							cell.setCellValue(venterprise.getLegalpersonName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (venterprise.getRegistermoney() != null) {
							cell = row.createCell(5);
							cell.setCellValue(venterprise.getRegistermoney());
							cell.setCellStyle(ExcelHelper.setAlign(workbook, 2));
						}else{
							cell = row.createCell(5);
							cell.setCellValue("0.0");
							cell.setCellStyle(ExcelHelper.setAlign(workbook, 2));
						}
						if (venterprise.getTelephone() != null) {
							cell = row.createCell(6);
							cell.setCellValue(venterprise.getTelephone());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (venterprise.getOpendate() != null) {
							cell = row.createCell(7);
							System.out.println(venterprise.getOpendate().toString()+"++++"+venterprise.getOpendate().toString().lastIndexOf(" "));
							cell.setCellValue(venterprise.getOpendate().toString().substring(0,venterprise.getOpendate().toString().lastIndexOf(" ")==-1?0:venterprise.getOpendate().toString().lastIndexOf(" ")));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
*/					}else if(headerStr.indexOf("银行")==0){
						VBankBankcontactperson vbank = (VBankBankcontactperson) list.get(i);
						if (vbank.getName()!= null) {
							cell = row.createCell(1); // 创建第i+1行第1列
							cell.setCellValue(vbank.getName());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						// 由于下面的和上面的基本相同，就不加注释了
						if (vbank.getSexvalue() != null) {
							cell = row.createCell(2);
							cell.setCellValue(vbank.getSexvalue());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (vbank.getMarriagename() != null) {
							cell = row.createCell(3);
							cell.setCellValue(vbank.getMarriagename());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (vbank.getBlmtelephone() != null) {
							cell = row.createCell(4);
							cell.setCellValue(vbank.getBlmtelephone());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (vbank.getBlmphone() != null) {
							cell = row.createCell(5);
							cell.setCellValue(vbank.getBlmphone());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (vbank.getEmail() != null) {
							cell = row.createCell(6);
							cell.setCellValue(vbank.getEmail());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (vbank.getBankname() != null) {
							cell = row.createCell(7);
							cell.setCellValue(vbank.getBankname());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (vbank.getFenbankvalue() != null) {
							cell = row.createCell(8);
							cell.setCellValue(vbank.getFenbankvalue());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (vbank.getDuty() != null) {
							cell = row.createCell(9);
							cell.setCellValue(vbank.getDuty());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
					}else if(headerStr.indexOf("还款")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						SlFundIntent slIntent = (SlFundIntent) list.get(i);
						if (slIntent.getFundType()!= null) {
							cell = row.createCell(1); // 创建第i+1行第1列
                           if(slIntent.getFundType().equals("principalRepayment")){
                        	   cell.setCellValue("贷款本金偿还");
                           }else if(slIntent.getFundType().equals("consultationMoney")){
                        	   cell.setCellValue("管理咨询费用收取");
                           }else if(slIntent.getFundType().equals("loanInterest")){
                        	   cell.setCellValue("贷款利息收取");
                           }else if(slIntent.getFundType().equals("principalLending")){
                        	   cell.setCellValue("贷款本金放贷");
                           }else if(slIntent.getFundType().equals("serviceMoney")){
                        	   cell.setCellValue("财务服务费用收取");
                           }else if(slIntent.getFundType().equals("backInterest")){
                        	   cell.setCellValue("费用退回");
                           }else if(slIntent.getFundType().equals("riskRate")){
                        	   cell.setCellValue("风险保证金");
                           }else if(slIntent.getFundType().equals("interestPenalty")){
                        	   cell.setCellValue("补偿息");
                           }// 设置第i+1行第1列的值
                           cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						// 由于下面的和上面的基本相同，就不加注释了
						if (slIntent.getIncomeMoney() != null) {
							cell = row.createCell(2);
							cell.setCellValue(slIntent.getIncomeMoney().doubleValue());
							cell.setCellStyle(style2);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (slIntent.getPayMoney() != null) {
							cell = row.createCell(3);
							cell.setCellValue(slIntent.getPayMoney().doubleValue());
							cell.setCellStyle(style2);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (slIntent.getIntentDate() != null) {
							cell = row.createCell(4);
							cell.setCellValue(from.format(slIntent.getIntentDate()));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (slIntent.getFactDate()!= null) {
							cell = row.createCell(5);
							cell.setCellValue(from.format(slIntent.getFactDate()));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (slIntent.getAfterMoney() != null) {
							cell = row.createCell(6);
							cell.setCellValue(slIntent.getAfterMoney().doubleValue());
							cell.setCellStyle(style2);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (slIntent.getNotMoney() != null) {
							cell = row.createCell(7);
							cell.setCellValue(slIntent.getNotMoney().doubleValue());
							cell.setCellStyle(style2);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (slIntent.getFlatMoney()!= null) {
							cell = row.createCell(8);
							cell.setCellValue(slIntent.getFlatMoney().doubleValue());
							cell.setCellStyle(style2);
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (slIntent.getPunishAccrual()!= null) {
							cell = row.createCell(9);
							cell.setCellValue(slIntent.getPunishAccrual().doubleValue()+"%");
							cell.setCellStyle(style2);
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						if (slIntent.getAccrualMoney() != null) {
							cell = row.createCell(10);
							cell.setCellValue(slIntent.getAccrualMoney().doubleValue());
							cell.setCellStyle(style2);
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
					}else if(headerStr.indexOf("全部款项催收")==0||headerStr.indexOf("即将到期款项催收")==0||headerStr.indexOf("已逾期款项催收")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						DecimalFormat  df1 = new DecimalFormat("#,###.##");
						SlFundIntent slIntent = (SlFundIntent) list.get(i);
						if (slIntent.getProjectNumber() != null) {
							cell = row.createCell(1);
							cell.setCellValue(slIntent.getProjectNumber());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						if (slIntent.getOppositeName() != null) {
							cell = row.createCell(2);
							cell.setCellValue(slIntent.getOppositeName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (slIntent.getOpposittelephone() != null) {
							cell = row.createCell(3);
							cell.setCellValue(slIntent.getOpposittelephone());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (slIntent.getFundTypeName()!= null) {
							cell = row.createCell(4); // 创建第i+1行第1列
							cell.setCellValue(slIntent.getFundTypeName());
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (slIntent.getIncomeMoney()!= null) {
							cell = row.createCell(5); // 创建第i+1行第1列
							cell.setCellValue(df1.format(slIntent.getIncomeMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (slIntent.getNotMoney()!= null) {
							cell = row.createCell(6); // 创建第i+1行第1列
							cell.setCellValue(df1.format(slIntent.getNotMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (slIntent.getIntentDate()!= null) {
							cell = row.createCell(7); // 创建第i+1行第1列
							cell.setCellValue(from.format(slIntent.getIntentDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (slIntent.getFactDate()!= null) {
							cell = row.createCell(8); // 创建第i+1行第1列
							cell.setCellValue(from.format(slIntent.getFactDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (slIntent.getLastslFundintentUrgeTime()!= null) {
							cell = row.createCell(9); // 创建第i+1行第1列
							cell.setCellValue(from.format(slIntent.getLastslFundintentUrgeTime()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						}else if(headerStr.indexOf("融资业绩表")==0){
							SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
							VFinancingProject slIntent = (VFinancingProject)list.get(i);
							if(slIntent.getProjectId()!=null){
								cell = row.createCell(1);
								cell.setCellValue(slIntent.getProjectNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(slIntent.getProjectMoney()!=null){
								cell = row.createCell(2);
								String money = slIntent.getProjectMoney().toString();
								sumMoney = sumMoney + Double.parseDouble(money);
								DecimalFormat df=new DecimalFormat("#.00");
								countMoney=df.format(sumMoney);
								cell.setCellValue(money);
								cell.setCellStyle(style2);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(slIntent.getStartDate()!=null){
								cell = row.createCell(3);
								cell.setCellValue(from.format(slIntent.getStartDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(slIntent.getAccrual()!=null){
								cell = row.createCell(4);
								cell.setCellValue(slIntent.getAccrual());
								cell.setCellStyle(style2);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(slIntent.getAccrualTypeValue()!=null){
								cell = row.createCell(5);
								cell.setCellValue(slIntent.getAccrualTypeValue());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(slIntent.getCreator()!=null){
								cell = row.createCell(6);
								cell.setCellValue(slIntent.getCreator());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(slIntent.getIsAheadPay()!=null){
								cell = row.createCell(7);
								cell.setCellValue(slIntent.getIsAheadPay()==0?"否":"是");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("系统账户列表")==0){
							AppUser user=(AppUser)list.get(i);
							if(null!=user.getFullname()){
								cell = row.createCell(1);
								cell.setCellValue(user.getFullname());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=user.getUserNumber()){
								cell = row.createCell(2);
								cell.setCellValue(user.getUserNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=user.getUsername()){
								cell = row.createCell(3);
								cell.setCellValue(user.getUsername().split("@")[0]);
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=user.getP2pAccount()){
								cell = row.createCell(4);
								cell.setCellValue(user.getP2pAccount());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=user.getPlainpassword()){
								cell = row.createCell(5);
								cell.setCellValue(user.getPlainpassword());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=user.getMobile()){
								cell = row.createCell(6);
								cell.setCellValue(user.getMobile());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=user.getEmail()){
								cell = row.createCell(7);
								cell.setCellValue(user.getEmail());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("推荐个数列表")==0){
							AppUser user=(AppUser)list.get(i);
							if(null!=user.getFullname()){
								cell = row.createCell(1);
								cell.setCellValue(user.getFullname());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=user.getPlainpassword()){
								cell = row.createCell(2);
								cell.setCellValue(user.getPlainpassword());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=user.getMobile()){
								cell = row.createCell(3);
								cell.setCellValue(user.getMobile());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=user.getEmail()){
								cell = row.createCell(4);
								cell.setCellValue(user.getEmail());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=user.getRegistrationDate()){
								cell = row.createCell(5);
								cell.setCellValue(user.getRegistrationDate());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=user.getRecommandNum()){
								cell = row.createCell(6);
								cell.setCellValue(user.getRecommandNum());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=user.getSecondRecommandNum()){
								cell = row.createCell(7);
								cell.setCellValue(user.getSecondRecommandNum());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("推荐业绩列表")==0){
							AppUser user=(AppUser)list.get(i);
							if(null!=user.getFullname()){
								cell = row.createCell(1);
								cell.setCellValue(user.getFullname());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=user.getPlainpassword()){
								cell = row.createCell(2);
								cell.setCellValue(user.getPlainpassword());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=user.getMobile()){
								cell = row.createCell(3);
								cell.setCellValue(user.getMobile());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=user.getEmail()){
								cell = row.createCell(4);
								cell.setCellValue(user.getEmail());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=user.getRegistrationDate()){
								cell = row.createCell(5);
								cell.setCellValue(user.getRegistrationDate());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=user.getInfoMoneyone()){
								cell = row.createCell(6);
								cell.setCellValue(user.getInfoMoneyone().add(user.getMmplanMoneyone())+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=user.getInfoMoneytwo()){
								cell = row.createCell(7);
								cell.setCellValue(user.getInfoMoneytwo().add(user.getMmplanMoneytwo())+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("线下一级业绩明细表")==0 || headerStr.indexOf("线下二级业绩明细表")==0){
							AppUser user=(AppUser)list.get(i);
							if(null!=user.getTruename()){
								cell = row.createCell(1);
								cell.setCellValue(user.getTruename());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=user.getCardcode()){
								cell = row.createCell(2);
								cell.setCellValue(user.getCardcode());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=user.getInvestCount()){
								cell = row.createCell(3);
								cell.setCellValue(user.getInvestCount());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=user.getInvestDate()){
								cell = row.createCell(4);
								cell.setCellValue(user.getInvestDate());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=user.getKeystr()){
								cell = row.createCell(5);
								if("mmplan".equals(user.getKeystr())){
									cell.setCellValue("D计划");
								}else if("UPlan".equals(user.getKeystr())){
									cell.setCellValue("U计划");
								}else{
									cell.setCellValue("散标");
								}
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=user.getInvestProjectName()){
								cell = row.createCell(6);
								cell.setCellValue(user.getInvestProjectName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=user.getSumInvestMoney()){
								cell = row.createCell(7);
								cell.setCellValue(user.getSumInvestMoney().doubleValue()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
							if(null!=user.getInvestlimit()){
								cell = row.createCell(8);
								cell.setCellValue(user.getInvestlimit()+"个月");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(8);
								cell.setCellStyle(style);
							}
							if(null!=user.getRecommended()){
								cell = row.createCell(9);
								cell.setCellValue(user.getRecommended());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}
							if(null!=user.getPlainpassword()){
								cell = row.createCell(10);
								cell.setCellValue(user.getPlainpassword());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(10);
								cell.setCellStyle(style);
							}
							if(null!=user.getOneDept()){
								cell = row.createCell(11);
								cell.setCellValue(user.getOneDept());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(11);
								cell.setCellStyle(style);
							}
							if(null!=user.getSecDept()){
								cell = row.createCell(12);
								cell.setCellValue(user.getSecDept());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(12);
								cell.setCellStyle(style);
							}
							if(null!=user.getRoleName()){
								cell = row.createCell(13);
								cell.setCellValue(user.getRoleName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(13);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("平台随息收费台账")==0){
							SlFundIntentPeriod slfund=(SlFundIntentPeriod)list.get(i);
							if(null!=slfund.getBorrowName()){
								cell = row.createCell(1);
								cell.setCellValue(slfund.getBorrowName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getBidPlanName()){
								cell = row.createCell(2);
								cell.setCellValue(slfund.getBidPlanName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getBidPlanProjectNum()){
								cell = row.createCell(3);
								cell.setCellValue(slfund.getBidPlanProjectNum());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getPayintentPeriod()){
								cell = row.createCell(4);
								cell.setCellValue("第"+slfund.getPayintentPeriod()+"期");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getConsultationMoney() && null!=slfund.getConsultationMoney().getIncomeMoney()){
								cell = row.createCell(5);
								cell.setCellValue(slfund.getConsultationMoney().getIncomeMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getServiceMoney() && null!=slfund.getServiceMoney().getIncomeMoney()){
								cell = row.createCell(6);
								cell.setCellValue(slfund.getServiceMoney().getIncomeMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getConsultationMoney().getAfterMoney() && null!=slfund.getServiceMoney().getAfterMoney()){
								cell = row.createCell(7);
								cell.setCellValue(slfund.getConsultationMoney().getAfterMoney().add(slfund.getServiceMoney().getAfterMoney())+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getIntentDate()){
								cell = row.createCell(8);
								cell.setCellValue(sd.format(slfund.getIntentDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(8);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getConsultationMoney().getAfterMoney() && null!=slfund.getServiceMoney().getAfterMoney()){
								cell = row.createCell(9);
								cell.setCellValue(slfund.getConsultationMoney().getAfterMoney().add(slfund.getServiceMoney().getAfterMoney())+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}
							//实际到帐日
							if(null!=slfund.getFactDate()){
								cell = row.createCell(10);
								cell.setCellValue(sd.format(slfund.getFactDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(10);
								cell.setCellStyle(style);
							}
							if(null!=slfund.getRepaySource()){
								cell = row.createCell(11);
								if(1==slfund.getRepaySource()){
									cell.setCellValue("正常代偿");
								}else if(2==slfund.getRepaySource()){
									cell.setCellValue("平台代偿");
								}
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(11);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("平台其他收费台账")==0){
							PlateFormBidIncomeDetail pd=(PlateFormBidIncomeDetail)list.get(i);
							if(null!=pd.getBorrowerName()){
								cell = row.createCell(1);
								cell.setCellValue(pd.getBorrowerName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=pd.getBidPlanName()){
								cell = row.createCell(2);
								cell.setCellValue(pd.getBidPlanName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=pd.getBidPlanNumber()){
								cell = row.createCell(3);
								cell.setCellValue(pd.getBidPlanNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=pd.getFundTypeName()){
								cell = row.createCell(4);
								cell.setCellValue(pd.getFundTypeName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=pd.getFactIncomeMoney()){
								cell = row.createCell(5);
								cell.setCellValue(pd.getFactIncomeMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=pd.getPlanReciveDate()){
								cell = row.createCell(6);
								cell.setCellValue(sd.format(pd.getPlanReciveDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=pd.getFactIncomeMoney()){
								cell = row.createCell(7);
								cell.setCellValue(pd.getFactIncomeMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
							if(null!=pd.getFactReciveDate()){
								cell = row.createCell(8);
								cell.setCellValue(sd.format(pd.getFactReciveDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(8);
								cell.setCellStyle(style);
							}
							if(null!=pd.getNotMoney()){
								cell = row.createCell(9);
								cell.setCellValue(pd.getNotMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("债权交易手续费台账")==0){
							PlBidSale pb=(PlBidSale)list.get(i);
							if(null!=pb.getOutCustName()){
								cell = row.createCell(1);
								cell.setCellValue(pb.getOutCustName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=pb.getInCustName()){
								cell = row.createCell(2);
								cell.setCellValue(pb.getInCustName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=pb.getBidProName()){
								cell = row.createCell(3);
								cell.setCellValue(pb.getBidProName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=pb.getSaleMoney()){
								cell = row.createCell(4);
								cell.setCellValue(pb.getSaleMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=pb.getSaleStartTime()){
								cell = row.createCell(5);
								cell.setCellValue(sd.format(pb.getSaleStartTime()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=pb.getSumMoney()){
								cell = row.createCell(6);
								cell.setCellValue(pb.getSumMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=pb.getPreTransferFee()){
								cell = row.createCell(7);
								String money = pb.getPreTransferFee().toString();
								sumMoney = sumMoney + Double.parseDouble(money);
								DecimalFormat df=new DecimalFormat("#.00");
								countMoney=df.format(sumMoney);
								cell.setCellValue(pb.getPreTransferFee()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
							if(null!=pb.getTransferFee()){
								cell = row.createCell(8);
								String money = pb.getTransferFee().toString();
								sumMoney2 = sumMoney2 + Double.parseDouble(money);
								DecimalFormat df=new DecimalFormat("#.00");
								countMoney2=df.format(sumMoney2);
								cell.setCellValue(pb.getTransferFee()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(8);
								cell.setCellStyle(style);
							}
							if(null!=pb.getSaleSuccessTime()){
								cell = row.createCell(9);
								cell.setCellValue(sd.format(pb.getSaleSuccessTime()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("非业务相关台账")==0){
							ObAccountDealInfo od=(ObAccountDealInfo)list.get(i);
							if(null!=od.getRecordNumber()){
								cell = row.createCell(1);
								cell.setCellValue(od.getRecordNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=od.getThirdPayRecordNumber()){
								cell = row.createCell(2);
								cell.setCellValue(od.getThirdPayRecordNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=od.getAccountNumber()){
								cell = row.createCell(3);
								cell.setCellValue(od.getAccountNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=od.getTransferAccountNumber()){
								cell = row.createCell(4);
								cell.setCellValue(od.getTransferAccountNumber());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=od.getTransferTypeName()){
								cell = row.createCell(5);
								cell.setCellValue(od.getTransferTypeName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=od.getIncomMoney()){
								cell = row.createCell(6);
								String money = od.getIncomMoney().toString();
								sumMoney = sumMoney + Double.parseDouble(money);
								DecimalFormat df=new DecimalFormat("#.00");
								countMoney=df.format(sumMoney);
								cell.setCellValue(od.getIncomMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=od.getPayMoney()){
								cell = row.createCell(7);
								String money = od.getPayMoney().toString();
								sumMoney2 = sumMoney2 + Double.parseDouble(money);
								DecimalFormat df=new DecimalFormat("#.00");
								countMoney=df.format(sumMoney2);
								cell.setCellValue(od.getPayMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
							if(null!=od.getCreateDate()){
								cell = row.createCell(8);
								cell.setCellValue(sd.format(od.getCreateDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(8);
								cell.setCellStyle(style);
							}
							if(null!=od.getTransferDate()){
								cell = row.createCell(9);
								cell.setCellValue(sd.format(od.getTransferDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}
							if(null!=od.getMsg()){
								cell = row.createCell(10);
								cell.setCellValue(sd.format(od.getMsg()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(10);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("红包发放台账")==0){
							PlateRedFinanceDetail pr=(PlateRedFinanceDetail)list.get(i);
							if(null!=pr.getOrderNo()){
								cell = row.createCell(1);
								cell.setCellValue(pr.getOrderNo());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=pr.getRequestNo()){
								cell = row.createCell(2);
								cell.setCellValue(pr.getRequestNo());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=pr.getLoginname()){
								cell = row.createCell(3);
								cell.setCellValue(pr.getLoginname());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=pr.getTruename()){
								cell = row.createCell(4);
								cell.setCellValue(pr.getTruename());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=pr.getRegistrationDate()){
								cell = row.createCell(5);
								cell.setCellValue(sd.format(pr.getRegistrationDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=pr.getRedName()){
								cell = row.createCell(6);
								cell.setCellValue(pr.getRedName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=pr.getRedMoney()){
								cell = row.createCell(7);
								String money = pr.getRedMoney().toString();
								sumMoney = sumMoney + Double.parseDouble(money);
								DecimalFormat df=new DecimalFormat("#.00");
								countMoney=df.format(sumMoney);
								cell.setCellValue(pr.getRedMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
							if(null!=pr.getDredMoney()){
								cell = row.createCell(8);
								String money = pr.getDredMoney().toString();
								sumMoney2 = sumMoney2 + Double.parseDouble(money);
								DecimalFormat df=new DecimalFormat("#.00");
								countMoney2=df.format(sumMoney2);
								cell.setCellValue(pr.getDredMoney()+"元");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(8);
								cell.setCellStyle(style);
							}
							if(null!=pr.getDistributeTime()){
								cell = row.createCell(9);
								cell.setCellValue(sd.format(pr.getDistributeTime()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(9);
								cell.setCellStyle(style);
							}
						}else if(headerStr.indexOf("流程任务代理列表")==0){
							TaskProxy tp=(TaskProxy)list.get(i);
							if(null!=tp.getPrincipalName()){
								cell = row.createCell(1);
								cell.setCellValue(tp.getPrincipalName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(1);
								cell.setCellStyle(style);
							}
							if(null!=tp.getAgentName()){
								cell = row.createCell(2);
								cell.setCellValue(tp.getAgentName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(2);
								cell.setCellStyle(style);
							}
							if(null!=tp.getStartDate()){
								cell = row.createCell(3);
								cell.setCellValue(sd.format(tp.getStartDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(3);
								cell.setCellStyle(style);
							}
							if(null!=tp.getEndDate()){
								cell = row.createCell(4);
								cell.setCellValue(sd.format(tp.getEndDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(4);
								cell.setCellStyle(style);
							}
							if(null!=tp.getCreateName()){
								cell = row.createCell(5);
								cell.setCellValue(tp.getCreateName());
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(5);
								cell.setCellStyle(style);
							}
							if(null!=tp.getCreateDate()){
								cell = row.createCell(6);
								cell.setCellValue(sd.format(tp.getCreateDate()));
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(6);
								cell.setCellStyle(style);
							}
							if(null!=tp.getStatus()){
								cell = row.createCell(7);
								cell.setCellValue(tp.getStatus()==0?"未过期":"已过期");
								cell.setCellStyle(style);
							}else{
								cell = row.createCell(7);
								cell.setCellStyle(style);
							}
						}
					}
				if(headerStr.indexOf("融资业绩表")==0){
					int footRownumber = sheet.getLastRowNum();    
				     HSSFRow footRow = sheet.createRow(footRownumber + 1);    
				     HSSFCell footRowcell = footRow.createCell(0);    
				     footRowcell.setCellValue("总计：");
				     footRowcell.setCellStyle(style);
    				 if (countMoney != null) {
    					 footRowcell = footRow.createCell(2);
    					 footRowcell.setCellValue(countMoney);
    					 footRowcell.setCellStyle(style2);
					 }else{
						footRowcell = footRow.createCell(2);
						footRowcell.setCellStyle(style);
					 }
				}else if(headerStr.indexOf("债权交易手续费台账")==0 || headerStr.indexOf("红包发放台账")==0){
					int footRownumber = sheet.getLastRowNum();    
				    HSSFRow footRow = sheet.createRow(footRownumber + 1);    
				    HSSFCell footRowcell = footRow.createCell(0);    
				    footRowcell.setCellValue("总计：");
				    footRowcell.setCellStyle(style);
   				    if (null!=countMoney) {
	   					footRowcell = footRow.createCell(7);
	   					footRowcell.setCellValue(countMoney);
	   					footRowcell.setCellStyle(style2);
					}else{
						footRowcell = footRow.createCell(7);
						footRowcell.setCellStyle(style);
					}
   				    if (null!=countMoney2) {
	   					footRowcell = footRow.createCell(8);
	   					footRowcell.setCellValue(countMoney2);
	   					footRowcell.setCellStyle(style2);
					}else{
						footRowcell = footRow.createCell(8);
						footRowcell.setCellStyle(style);
					}
				}else if(headerStr.indexOf("非业务相关台账")==0){
					int footRownumber = sheet.getLastRowNum();    
				    HSSFRow footRow = sheet.createRow(footRownumber + 1);    
				    HSSFCell footRowcell = footRow.createCell(0);    
				    footRowcell.setCellValue("总计：");
				    footRowcell.setCellStyle(style);
   				    if (null!=countMoney) {
	   					footRowcell = footRow.createCell(6);
	   					footRowcell.setCellValue(countMoney);
	   					footRowcell.setCellStyle(style2);
					}else{
						footRowcell = footRow.createCell(6);
						footRowcell.setCellStyle(style);
					}
   				    if (null!=countMoney2) {
	   					footRowcell = footRow.createCell(7);
	   					footRowcell.setCellValue(countMoney2);
	   					footRowcell.setCellStyle(style2);
					}else{
						footRowcell = footRow.createCell(7);
						footRowcell.setCellStyle(style);
					}
				}
			}
		} catch (Exception e) {
			logger.error("导出Excel出错:"+e.getMessage());
			e.printStackTrace();
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*
	 * 固定资产导出
	 * 
	 * */
	public static String export1(List list,String []tableHeader,String headerStr) throws Exception {
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
						case 3 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
						case 4 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 5 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 6 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
						break;
						case 15 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					FixedAssets vperson = (FixedAssets) list.get(i);// 获取对象
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						//编号
						if (vperson.getAssetsNo() != null) {
							cell = row.createCell(0); // 创建第i+1行第1列

							cell.setCellValue(vperson.getAssetsNo());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(0);
							cell.setCellStyle(style);
						}
						//资产名字
						// 由于下面的和上面的基本相同，就不加注释了
						if (vperson.getAssetsName() != null) {
							cell = row.createCell(1);
							cell.setCellValue(vperson.getAssetsName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						//资产类别
						if (vperson.getAssetsType().getTypeName() != null) {
							cell = row.createCell(2);
							cell.setCellValue(vperson.getAssetsType().getTypeName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						//规格型号
						if (vperson.getModel() != null) {
							cell = row.createCell(3);
							cell.setCellValue(vperson.getModel());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						//资产值
						if (vperson.getAssetValue() != null) {
							cell = row.createCell(4);
							cell.setCellValue(vperson.getAssetValue().toString());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						//资产当前值
						if (vperson.getAssetCurValue()!= null) {
							cell = row.createCell(5);
							cell.setCellValue(vperson.getAssetCurValue().toString());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						//制造厂商
						if (vperson.getManufacturer()!= null) {
							cell = row.createCell(6);
							cell.setCellValue(vperson.getManufacturer());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						//出厂日期
						if (vperson.getManuDate() != null) {
							
							cell = row.createCell(7);
							cell.setCellValue(sdf.format(vperson.getManuDate()));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						//置办日期
						if (vperson.getBuyDate() != null) {
							cell = row.createCell(8);
							cell.setCellValue(sdf.format(vperson.getBuyDate()));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						//所属部门
						if (vperson.getBeDep() != null) {
							cell = row.createCell(9);
							cell.setCellValue(vperson.getBeDep());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						//保管人
						if (vperson.getCustodian() != null) {
							cell = row.createCell(10);
							cell.setCellValue(vperson.getCustodian());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
						//折旧类型
						if (vperson.getDepreType().getTypeName() != null) {
							cell = row.createCell(11);
							cell.setCellValue(vperson.getDepreType().getTypeName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(11);
							cell.setCellStyle(style);
						}
						//开始折旧日期
						if (vperson.getStartDepre() != null) {
							cell = row.createCell(12);
							cell.setCellValue(sdf.format(vperson.getStartDepre()));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(12);
							cell.setCellStyle(style);
						}
						//预计使用年限
						if (vperson.getIntendTerm() != null) {
							cell = row.createCell(13);
							cell.setCellValue(vperson.getIntendTerm().toString());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(13);
							cell.setCellStyle(style);
						}
						//残值率
						if (vperson.getRemainValRate() != null) {
							cell = row.createCell(14);
							cell.setCellValue(vperson.getRemainValRate().toString());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(14);
							cell.setCellStyle(style);
						}
						//备注
						if (vperson.getNotes() != null) {
							cell = row.createCell(15);
							cell.setCellValue(vperson.getNotes());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(15);
							cell.setCellStyle(style);
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
	/*
	 * 商品入库导出
	 * */
	public static String export2(List list,String []tableHeader,String headerStr) throws Exception {
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 3 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 5 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 6 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					InStock vperson = (InStock) list.get(i);// 获取对象
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						//流水号
						if (vperson.getStockNo() != null) {
							cell = row.createCell(0); // 创建第i+1行第1列

							cell.setCellValue(vperson.getStockNo());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(0);
							cell.setCellStyle(style);
						}
						//入库时间
						// 由于下面的和上面的基本相同，就不加注释了
						if (vperson.getInDate() != null) {
							cell = row.createCell(1);
							cell.setCellValue(sdf.format(vperson.getInDate()));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						//用品编号
						if (vperson.getOfficeGoods().getGoodsNo() != null) {
							cell = row.createCell(2);
							cell.setCellValue(vperson.getOfficeGoods().getGoodsNo());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						//用品名称
						if (vperson.getOfficeGoods().getGoodsName() != null) {
							cell = row.createCell(3);
							cell.setCellValue(vperson.getOfficeGoods().getGoodsName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						//供应商
						if (vperson.getProviderName() != null) {
							cell = row.createCell(4);
							cell.setCellValue(vperson.getProviderName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						//购买人
						if (vperson.getBuyer()!= null) {
							cell = row.createCell(5);
							cell.setCellValue(vperson.getBuyer());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						//数量
						if (vperson.getInCounts()!= null) {
							cell = row.createCell(6);
							cell.setCellValue(vperson.getInCounts());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						//单价
						if (vperson.getPrice() != null) {
							
							cell = row.createCell(7);
							cell.setCellValue(vperson.getPrice().toString());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						//总价
						if (vperson.getAmount() != null) {
							cell = row.createCell(8);
							cell.setCellValue(vperson.getAmount().toString());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
	/*
	 * 商品出库导出
	 * 
	 * */
	public static String export3(List list,String []tableHeader,String headerStr) throws Exception {
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
						break;
						case 3 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 4 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 5 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 6 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					GoodsApply vperson = (GoodsApply) list.get(i);// 获取对象
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Iterator<InStock> insIters = vperson.getOfficeGoods().getInStocks().iterator();
					InStock ins = null;
					if(insIters.hasNext()){
						ins = insIters.next();
					}
						//流水号
						if (vperson.getApplyNo() != null) {
							cell = row.createCell(0); // 创建第i+1行第1列

							cell.setCellValue(vperson.getApplyNo());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(0);
							cell.setCellStyle(style);
						}
						//用品编号
						// 由于下面的和上面的基本相同，就不加注释了
						if (vperson.getOfficeGoods().getGoodsNo() != null) {
							cell = row.createCell(1);
							cell.setCellValue(vperson.getOfficeGoods().getGoodsNo());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						//用品名称
						if (vperson.getOfficeGoods().getGoodsName() != null) {
							cell = row.createCell(2);
							cell.setCellValue(vperson.getOfficeGoods().getGoodsName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						//出库日期
						if (vperson.getApplyDate() != null) {
							cell = row.createCell(3);
							cell.setCellValue(sdf.format(vperson.getApplyDate()));
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						//领用人员
						if (vperson.getProposer() != null) {
							cell = row.createCell(4);
							cell.setCellValue(vperson.getProposer());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						//经手人
						if (vperson.getUserId()!= null) {
							cell = row.createCell(5);
							
							cell.setCellValue(vperson.getUsername());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						//数量
						if (vperson.getUseCounts()!= null) {
							cell = row.createCell(6);
							cell.setCellValue(vperson.getUseCounts());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						//单价
						if (vperson.getOfficeGoods().getInStocks() != null) {
							
							cell = row.createCell(7);
							if(ins!= null && ins.getPrice()!=null){
								cell.setCellValue(ins.getPrice().toString());
							}
							
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						//总价
						if (vperson.getOfficeGoods().getInStocks() != null && vperson.getUseCounts()!= null) {
							cell = row.createCell(8);
							if(ins!= null && ins.getPrice()!=null){
								cell.setCellValue((vperson.getUseCounts()*Double.valueOf(ins.getPrice().toString())));
							}
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
	/*
	 * 导出办公用品档案
	 * */
	public static String export4(List list,String []tableHeader,String headerStr) throws Exception {
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
						break;
						case 3 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 4 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 5 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 6 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					OfficeGoods vperson = (OfficeGoods) list.get(i);// 获取对象
					Iterator<InStock> insIters = vperson.getInStocks().iterator();
					Iterator<GoodsApply> appIters = vperson.getGoodsApplys().iterator();
					InStock ins = null;
					GoodsApply gapp = null;
					
					double avePrice = 0;
					double countPrice = 0;
					int countInstock = 0;
					int countAppl = 0;
					while(insIters.hasNext()){
						ins = insIters.next();
						InStock inst = ins;
						
						countInstock += inst.getInCounts();
						countPrice += Double.valueOf(inst.getAmount().toString());
						avePrice = countPrice/countInstock;
					}
					while(appIters.hasNext()){
						gapp = appIters.next();
						GoodsApply inst = gapp;
						countAppl += inst.getUseCounts();
					}
						//编号
						if (vperson.getGoodsNo() != null) {
							cell = row.createCell(0); // 创建第i+1行第1列
							cell.setCellValue(vperson.getGoodsNo());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(0);
							cell.setCellStyle(style);
						}
						//名称
						// 由于下面的和上面的基本相同，就不加注释了
						if (vperson.getGoodsName() != null) {
							cell = row.createCell(1);
							cell.setCellValue(vperson.getGoodsName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						//供应商
						if (ins != null) {
							cell = row.createCell(2);
							cell.setCellValue(ins.getProviderName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						//购买人
						if (ins != null) {
							cell = row.createCell(3);
							cell.setCellValue(ins.getBuyer());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						//数量
						if (vperson.getStockCounts() != null) {
							cell = row.createCell(4);
							cell.setCellValue(vperson.getStockCounts());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						//平均单价
						if (avePrice!= 0) {
							cell = row.createCell(5);
							cell.setCellValue(avePrice);
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						//库存总值
						if (vperson.getStockCounts()!= null) {
							cell = row.createCell(6);
							cell.setCellValue(vperson.getStockCounts()*avePrice);
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						//库存下限
						if (vperson.getWarnCounts() != null) {
							
							cell = row.createCell(7);
							cell.setCellValue(vperson.getWarnCounts());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						//备注
						if (vperson.getNotes()!= null) {
							cell = row.createCell(8);
							cell.setCellValue(vperson.getNotes());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						//累计进库数量
						if (countInstock!= 0) {
							cell = row.createCell(9);
							cell.setCellValue(countInstock);
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						//累计出库数量
						if (countAppl!= 0) {
							cell = row.createCell(10);
							cell.setCellValue(countAppl);
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
	/**
	 * 导出数据到excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String exportE(List<String[]> list,String []tableHeader,String headerStr) throws Exception {

		/**
		 *如果是从数据库里面取数据，就让studentList=取数据的函数： 就没必要下面的for循环
		 * 我下面的for循环就是手动给studentList赋值
		 */
		/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
		//String[] tableHeader = { "学号", "姓名", "性别", "寝室号", "所在系" };
		/*
		 * 下面的都可以拷贝不用编写
		 */
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		/*style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
*/		HSSFCellStyle styleRed = workbook.createCellStyle(); // 设置类型
		HSSFFont fontRed = workbook.createFont(); // 设置字体
		styleRed.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		fontRed.setColor(HSSFColor.RED.index);
		styleRed.setFont(fontRed);
		
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置数据类型
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	/*	style2.setBorderLeft((short)1);     //左边框
		style2.setBorderRight((short)1);    //右边框
		style2.setBorderBottom((short)1);//下边框
*/		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("权限配置"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					if(headerStr.indexOf("权限")==0){
						if(k==0)
						{
						sheet.setColumnWidth(k, 8000);// 设置列的宽度
						}else if(k==1){
							sheet.setColumnWidth(k, 20000);// 设置列的宽度
						}else {
							sheet.setColumnWidth(k, 4000);// 设置列的宽度
						}
						
					}
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
			    if(headerStr.indexOf("权限")==0){
						//EnterpriseView venterprise = (EnterpriseView) list.get(i);
						String[] str=list.get(i);
						for(int j=0;j<str.length;j++){
						if (str[j] != null) {
							cell = row.createCell(j); // 创建第i+1行第1列

							cell.setCellValue(str[j]);// 设置第i+1行第1列的值
							if(str[j].equals("有")){
								cell.setCellStyle(styleRed);// 设置风格 红色字体
							}else{
							cell.setCellStyle(style); // 设置风格 黑色字体
							}
						}else{
							cell = row.createCell(j);
							cell.setCellStyle(style);
						}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/x-download");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			out.close();
			out=null;  
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
					out=null;  
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	
	private static HSSFCellStyle setAlign(HSSFWorkbook workbook ,int align){
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置类型
		style2.setBorderLeft((short)1);     //左边框
		style2.setBorderRight((short)1);    //右边框
		style2.setBorderBottom((short)1);//下边框
		if(align == 1){
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		}else if(align == 2){
			style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		}else{
			style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}
		return style2;
	}
	private static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                }
            } else {
                age--;
            }
        }
        if(yearNow == yearBirth){
        	age = 1 ;
        }
        if(yearNow==(yearBirth+1)){
        	age=1;
        }
        if(yearNow==(yearBirth+2)){
        	age=2;
        } 
        return age;
    }
	
	/**
	 * 导出企业财务数据到excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String exportFinance(String[][] financeInfoArray,String enterpriseName,String []tableHeader) throws Exception {

		/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
		/*
		 * 下面的都可以拷贝不用编写
		 */
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setBorderLeft((short)1);//左边框
		style1.setBorderRight((short)1);//右边框
		style1.setBorderBottom((short)1);//下边框
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setFillForegroundColor(HSSFColor.YELLOW.index);//添加前景色,内容看的清楚 
		style1.setFillBackgroundColor(HSSFColor.YELLOW.index);
		style1.setFillPattern((short)HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置数据类型
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setBorderLeft((short)1);//左边框
		style2.setBorderRight((short)1);//右边框
		style2.setBorderBottom((short)1);//下边框
		HSSFCellStyle style3 = workbook.createCellStyle(); // 设置类型
		style3.setBorderLeft((short)1);     //左边框
		style3.setBorderRight((short)1);    //右边框
		style3.setBorderBottom((short)1);//下边框
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style3.setFillForegroundColor(HSSFColor.YELLOW.index);//添加前景色,内容看的清楚 
		style3.setFillBackgroundColor(HSSFColor.YELLOW.index);
		style3.setFillPattern((short)HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style4 = workbook.createCellStyle(); // 设置类型
		style4.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style4.setBorderLeft((short)1);     //左边框
		style4.setBorderRight((short)1);    //右边框
		style4.setBorderBottom((short)1);//下边框
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style4.setFillForegroundColor(HSSFColor.YELLOW.index);//添加前景色,内容看的清楚 
		style4.setFillBackgroundColor(HSSFColor.YELLOW.index);
		style4.setFillPattern((short)HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (financeInfoArray.length < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter("企业财报信息");
				row = sheet.createRow(0);
				row.setHeight((short) 600);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					cell.setCellStyle(style1);
					switch(k){
						case 0 : sheet.setColumnWidth(k, 2500);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 6500);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < financeInfoArray.length; i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 350);// 设置行高
					String [] financeInfoArray1 =  financeInfoArray[i];
					for(int j = 0;j < 7;j++) {
						cell = row.createCell(j); // 创建第i+1行第j列
						if(j < financeInfoArray[i].length) {
							cell.setCellValue(financeInfoArray1[j]);// 设置第i+1行第j列的值
						}else {
							cell.setCellValue("");// 设置第i+1行第j列的值
						}
						if(j == 0) {
							cell.setCellStyle(style2); // 设置风格
						}else if(j == 1){
							if((i >=5 && i <= 10) || (i >=19 && i <= 25) || (i >=28 && i <= 31)) {
								cell.setCellStyle(style3); // 设置风格
							}else {
								cell.setCellStyle(style2); // 设置风格
							}
						}else{
							if((i >=5 && i <= 10) || (i >=19 && i <= 25) || (i >=28 && i <= 31)) {
								cell.setCellStyle(style4); // 设置风格
							}else {
								cell.setCellStyle(style); // 设置风格
							}
						}
					}
				}
				Region  range = new Region(2, (short)0, 11, (short)0);//参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号
				sheet.addMergedRegion(range);
				Region range1 = new Region(12, (short)0, 26, (short)0); 
				sheet.addMergedRegion(range1);
				Region range2 = new Region(27, (short)0, 32, (short)0); 
				sheet.addMergedRegion(range2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
			String str = enterpriseName + "_企业财报信息";
	        String headerStr =new String(str.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/x-download");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			out.close();
			out=null;  
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
					out=null;  
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
	/**
	 * 
	 * 导出租赁定价表
	 */
	public static String exportLease(FlLeaseFinanceProject project,String customerName,String[] arr,String []tableHeader,String headerStr) throws Exception {

		/**
		 *如果是从数据库里面取数据，就让studentList=取数据的函数： 就没必要下面的for循环
		 * 我下面的for循环就是手动给studentList赋值
		 */
		/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
		//String[] tableHeader = { "学号", "姓名", "性别", "寝室号", "所在系" };
		/*
		 * 下面的都可以拷贝不用编写
		 */
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置数据类型
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style2.setBorderLeft((short)1);     //左边框
		style2.setBorderRight((short)1);    //右边框
		style2.setBorderBottom((short)1);//下边框
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		DecimalFormat  df1 = new DecimalFormat("#,###.##");
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			
			if (arr.length < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				cell=row.createCell(0);
				 sheet.setColumnWidth(0, 6000);
				cell.setCellValue("承租人");
				cell=row.createCell(1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				sheet.addMergedRegion(new CellRangeAddress(0,0,1,5));
				cell.setCellValue(customerName);
				 sheet.setColumnWidth(1, 6000);
				row = sheet.createRow(1);
				row.setHeight((short) 400);
				cell=row.createCell(0);
				cell.setCellValue("租赁成本");
				 sheet.setColumnWidth(0, 6000);
				cell=row.createCell(1);
				cell.setCellValue(df1.format(project.getProjectMoney()));
				 sheet.setColumnWidth(1, 6000);
				 sheet.addMergedRegion(new CellRangeAddress(1,1,1,2));
				cell=row.createCell(3);
				cell.setCellValue("租金总额");
				 sheet.setColumnWidth(3, 6000);
				cell=row.createCell(4);
				cell.setCellValue(df1.format(project.getAllMoney()));
				 sheet.setColumnWidth(4, 6000);
				 sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
				row = sheet.createRow(2);
				row.setHeight((short) 400);
				cell=row.createCell(0);
				cell.setCellValue("租赁保证金");
				 sheet.setColumnWidth(0, 6000);
				cell=row.createCell(1);
				cell.setCellValue(project.getLeaseDepositRate().setScale(3,BigDecimal.ROUND_HALF_UP).toString()+"%");
				 sheet.setColumnWidth(1, 6000);
				cell=row.createCell(2);
				cell.setCellValue(df1.format(project.getLeaseDepositMoney()));
				 sheet.setColumnWidth(2, 6000);
				cell=row.createCell(3);
				cell.setCellValue("租赁期限");
				 sheet.setColumnWidth(3, 6000);
				String qx="";
				String method="";
				if(project.getPayaccrualType().equals("dayPay")){
					qx=project.getPayintentPeriod()+"天";
					method="日后";
				}else if(project.getPayaccrualType().equals("monthPay")){
					qx=project.getPayintentPeriod()+"个月";
					method="月后";
				}else if(project.getPayaccrualType().equals("seasonPay")){
					qx=project.getPayintentPeriod()*3+"个月";
					method="季后";
				}else if(project.getPayaccrualType().equals("yearPay")){
					qx=project.getPayintentPeriod()+"年";
					method="年后";
				}else if(project.getPayaccrualType().equals("owerPay")){
					qx=project.getPayintentPeriod()*project.getDayOfEveryPeriod()+"天";
					method="日后";
				}
				cell=row.createCell(4);
				cell.setCellValue(qx);
				 sheet.setColumnWidth(4, 6000);
				cell=row.createCell(5);
				cell.setCellValue(method);
				 sheet.setColumnWidth(5, 6000);
				 row = sheet.createRow(3);
				row.setHeight((short) 400);
				cell=row.createCell(0);
				cell.setCellValue("租赁物价留购费");
				 sheet.setColumnWidth(0, 6000);
				cell=row.createCell(1);
				cell.setCellValue(project.getLeaseRetentionFeeRate().setScale(3,BigDecimal.ROUND_HALF_UP).toString()+"%");
				 sheet.setColumnWidth(1, 6000);
				cell=row.createCell(2);
				cell.setCellValue(df1.format(project.getLeaseRetentionFeeMoney()));
				 sheet.setColumnWidth(2, 6000);
				cell=row.createCell(3);
				cell.setCellValue("租赁手续费");
				 sheet.setColumnWidth(3, 6000);
			
				cell=row.createCell(4);
				cell.setCellValue(project.getRentalFeeRate().setScale(3,BigDecimal.ROUND_HALF_UP)+"%");
				sheet.setColumnWidth(4, 6000);
				cell=row.createCell(5);
				cell.setCellValue(df1.format(project.getRentalFeeMoney()));
				 sheet.setColumnWidth(5, 6000);
				 row = sheet.createRow(4);
					row.setHeight((short) 400);
					cell=row.createCell(0);
					cell.setCellValue("项目内涵收益率");
					 sheet.setColumnWidth(0, 6000);
					cell=row.createCell(1);
					cell.setCellValue(project.getConnotationRate().setScale(3,BigDecimal.ROUND_HALF_UP).toString());
					 sheet.setColumnWidth(1, 6000);
					
					 sheet.addMergedRegion(new CellRangeAddress(4,4,1,2));
					cell=row.createCell(3);
					cell.setCellValue("租赁费率");
					 sheet.setColumnWidth(3, 6000);
				
					cell=row.createCell(4);
					cell.setCellValue(project.getRentalRate().setScale(3,BigDecimal.ROUND_HALF_UP)+"%");
					sheet.setColumnWidth(4, 6000);
					cell=row.createCell(5);
					cell.setCellValue(df1.format(project.getRentalMoney()));
					 sheet.setColumnWidth(5, 6000);
		;
				row = sheet.createRow(5);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					
					if(headerStr.indexOf("租赁定价表")==0){
						switch(k){
							case 0 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 1 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 2 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
							break;
							default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						}
					}
				font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
				font.setFontHeight((short) 350); // 设置单元字体高度
				font.setFontHeightInPoints((short)11);   //表头字体大小 
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
				style1.setFont(font);// 设置字体风格
				style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
				style1.setBorderLeft((short)1);     //左边框
				style1.setBorderRight((short)1);    //右边框
				style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
				style1.setBorderBottom((short)1);//下边框
				cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < arr.length; i++) {
					String str=arr[i];
					str=str.substring(1,str.length()-1);
					String[] sarr=str.split(",");
					row = sheet.createRow((short) (i + 6));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					
					cell.setCellValue(sarr[0].split(":")[1]);// 设置第i+1行第0列的值
					cell.setCellStyle(style);// 设置风格
					String str1=sarr[1].split(":")[1];
					if(!str1.equals("null")){
						cell = row.createCell(1);// 创建第i+1行第0列
						cell.setCellValue(df1.format(new BigDecimal(str1.trim())));// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}else{
						cell = row.createCell(1);// 创建第i+1行第0列
						cell.setCellStyle(style);// 设置风格
					}
					String str2=sarr[2].split(":")[1];
					if(!str2.equals("null")){
						cell = row.createCell(2);// 创建第i+1行第0列
						
						cell.setCellValue(df1.format(new BigDecimal(str2.trim())));// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}else{
						cell = row.createCell(2);// 创建第i+1行第0列
						cell.setCellStyle(style);// 设置风格
					}
					String str3=sarr[3].split(":")[1];
					if(!str3.equals("null")){
						cell = row.createCell(3);// 创建第i+1行第0列
						cell.setCellValue(df1.format(new BigDecimal(str3.trim())));// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}else{
						cell = row.createCell(3);// 创建第i+1行第0列
						cell.setCellStyle(style);// 设置风格
					}
					String str4=sarr[4].split(":")[1];
					if(!str4.equals("null")){
						cell = row.createCell(4);// 创建第i+1行第0列
						cell.setCellValue(df1.format(new BigDecimal(str4.trim())));// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}else{
						cell = row.createCell(4);// 创建第i+1行第0列
						cell.setCellStyle(style);// 设置风格
					}
					String str5=sarr[5].split(":")[1];
					if(!str5.equals("null")){
						cell = row.createCell(5);// 创建第i+1行第0列
						
						cell.setCellValue(df1.format(new BigDecimal(str5.trim())));// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}else{
						cell = row.createCell(5);// 创建第i+1行第0列
						cell.setCellStyle(style);// 设置风格
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("导出Excel出错:"+e.getMessage());
			e.printStackTrace();
			
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
	public static String investEnterpriseExcel(List list,String []tableHeader,String headerStr) throws Exception {
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 5 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
						break;
						case 6 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 7 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					InvestEnterprise enterprise = (InvestEnterprise) list.get(i);// 获取对象
						if (enterprise.getEnterprisename()!=null) {
							cell = row.createCell(1); // 创建第i+1行第1列

							cell.setCellValue(enterprise.getEnterprisename());// 设置第i+1行第1列的值
							cell.setCellStyle(style); // 设置风格
						}else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						if (enterprise.getShortname() != null) {
							cell = row.createCell(2);
							cell.setCellValue(enterprise.getShortname());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						
						if (enterprise.getOrganizecode() != null) {
							cell = row.createCell(3);
							cell.setCellValue(enterprise.getOrganizecode());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						
						if (enterprise.getCciaa() != null) {
							cell = row.createCell(4);
							cell.setCellValue(enterprise.getCciaa());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						
						if (enterprise.getMainHangye() != null) {
							cell = row.createCell(5);
							cell.setCellValue(enterprise.getMainHangye());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						
						if (enterprise.getMainperson()!= null) {
							cell = row.createCell(6);
							cell.setCellValue(enterprise.getMainperson());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (enterprise.getTelephone()!= null) {
							cell = row.createCell(7);
							cell.setCellValue(enterprise.getTelephone());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}
	/**
	 * 导出项目信息数据到Excel文件
	 * @author lisl
	 * @return
	 * @throws Exception
	 */
	/*public static String projectInfoExport(List list,String []tableHeader,String headerStr) throws Exception {

		*//**
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 *//*
		*//**
		 * 下面的都可以拷贝不用编写
		 *//*
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			*//**
			 *根据是否取出数据，设置header信息
			 * 
			 *//*
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				
				 * // 给excel填充数据这里需要编写
				 
				for (int i = 0; i < list.size(); i++) {
					
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					VProcessRunData project = (VProcessRunData) list.get(i);// 获取对象
					if (project.getProjectNumber() != null) {
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(project.getProjectNumber());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					// 由于下面的和上面的基本相同，就不加注释了
					if (project.getCustomerName() != null) {
						cell = row.createCell(2);
						cell.setCellValue(project.getCustomerName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (project.getOppositeTypeValue() != null) {
						cell = row.createCell(3);
						cell.setCellValue(project.getOppositeTypeValue());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
		 * 下面的可以不用编写，直接拷贝
		 
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}*/
	/** 投资客户信息导出 */
	public static String exportInvesmentPerson(List list, String[] tableHeader,
			String headerStr) throws Exception {

		/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
		// String[] tableHeader = { "学号", "姓名", "性别", "寝室号", "所在系" };
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short) 1); // 左边框
		style.setBorderRight((short) 1); // 右边框
		style.setBorderBottom((short) 1);// 下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置数据类型
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style2.setBorderLeft((short) 1); // 左边框
		style2.setBorderRight((short) 1); // 右边框
		style2.setBorderBottom((short) 1);// 下边框
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头

		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */

			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列

					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch (k) {
					case 0:
						sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
					case 1:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 2:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 3:
						sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
					case 4:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 5:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 6:
						sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
					case 7:
						sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
					case 8:
						sheet.setColumnWidth(k, 5000);// 设置列的宽度
						break;
					default:
						sheet.setColumnWidth(k, 6000);// 设置列的宽度

					}
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short) 11); // 表头字体大小
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);// 添加前景色,内容看的清楚
					style1.setBorderLeft((short) 1); // 左边框
					style1.setBorderRight((short) 1); // 右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE); // 顶边框
					style1.setBorderBottom((short) 1);// 下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {

					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i + 1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					VInvestmentPerson vperson = (VInvestmentPerson) list.get(i);// 获取对象
					// cell = row.createCell(1);
					// cell.setCellStyle(style);
					// 由于下面的和上面的基本相同，就不加注释了

					if (vperson.getInvestName() != null) {
						cell = row.createCell(1);
						cell.setCellValue(vperson.getInvestName());
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (vperson.getShopName() != null) {
						cell = row.createCell(2);
						cell.setCellValue(vperson.getShopName());
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (vperson.getSex() != null) {
						cell = row.createCell(3);
						if (vperson.getSex() == 312) {
							cell.setCellValue("男");
						}else{
							cell.setCellValue("女");
						}
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (vperson.getCellphone() != null) {
						cell = row.createCell(4);
						cell.setCellValue(vperson.getCellphone());
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (vperson.getCardtype() != null) {
						cell = row.createCell(5);
						if (vperson.getCardtype() == 309) {
							cell.setCellValue("身份证");
						} else if (vperson.getCardtype() == 310) {
							cell.setCellValue("军官证");
						} else if (vperson.getCardtype() == 311) {
							cell.setCellValue("护照");
						} else if (vperson.getCardtype() == 834) {
							cell.setCellValue("临时身份证");
						} else if (vperson.getCardtype() == 835) {
							cell.setCellValue("港澳台通行证");
						} else {
							cell.setCellValue("港其他");
						}
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (vperson.getCardnumber() != null) {
						cell = row.createCell(6);
						cell.setCellValue(vperson.getCardnumber());
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (vperson.getBankNum() != null) {
						cell = row.createCell(7);
						cell.setCellValue(vperson.getBankNum());
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					// }
				}

			}
		} catch (Exception e) {
			logger.error("导出Excel出错:" + e.getMessage());
			e.printStackTrace();

		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
			headerStr = new String(headerStr.getBytes("gb2312"), "ISO8859-1");// headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr + ".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:" + e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;

	}
	/** 投资客户信息导出 */
	public static String exportAssignFundIntent(List list, String[] tableHeader,
			String headerStr) throws Exception {

		/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
		// String[] tableHeader = { "学号", "姓名", "性别", "寝室号", "所在系" };
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short) 1); // 左边框
		style.setBorderRight((short) 1); // 右边框
		style.setBorderBottom((short) 1);// 下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置数据类型
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style2.setBorderLeft((short) 1); // 左边框
		style2.setBorderRight((short) 1); // 右边框
		style2.setBorderBottom((short) 1);// 下边框
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */

			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列

					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch (k) {
					case 0:
						sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
					case 1:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 2:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 3:
						sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
					case 4:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 5:
						sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
					case 6:
						sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
					case 7:
						sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
					
					default:
						sheet.setColumnWidth(k, 6000);// 设置列的宽度

					}
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short) 11); // 表头字体大小
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);// 添加前景色,内容看的清楚
					style1.setBorderLeft((short) 1); // 左边框
					style1.setBorderRight((short) 1); // 右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE); // 顶边框
					style1.setBorderBottom((short) 1);// 下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {

					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i + 1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					SlFundIntent s = (SlFundIntent) list.get(i);// 获取对象
					// cell = row.createCell(1);
					// cell.setCellStyle(style);
					// 由于下面的和上面的基本相同，就不加注释了

					if (s.getInvestPersonName() != null) {
						cell = row.createCell(1);
						cell.setCellValue(s.getInvestPersonName());
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (s.getPayintentPeriod() != null) {
						cell = row.createCell(2);
						cell.setCellValue("第"+s.getPayintentPeriod()+"期");
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (s.getFundTypeName() != null) {
						cell = row.createCell(3);
						
						cell.setCellValue(s.getFundTypeName());
						
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (s.getIncomeMoney() != null) {
						cell = row.createCell(4);
						cell.setCellValue(s.getIncomeMoney().toString());
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (s.getPayMoney() != null) {
						cell = row.createCell(5);
					
						cell.setCellValue(s.getPayMoney().toString());
						
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (s.getIntentDate() != null) {
						cell = row.createCell(6);
						cell.setCellValue(sd.format(s.getIntentDate()));
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (s.getFactDate() != null) {
						cell = row.createCell(7);
						cell.setCellValue(sd.format(s.getFactDate()));
						cell.setCellStyle(style);
					} else {
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					// }
				}

			}
		} catch (Exception e) {
			logger.error("导出Excel出错:" + e.getMessage());
			e.printStackTrace();

		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
			headerStr = new String(headerStr.getBytes("gb2312"), "ISO8859-1");// headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr + ".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:" + e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;

	}
	//导出excel表格
	/*
	 * 1、生成表头
	 * 2、生成body
	 * 3、导出文件
	 * **/
	public static String exportManagePlan(List list,String []tableHeader,String headerStr) throws Exception {
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
						case 3 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
						break;
						case 4 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 5 : sheet.setColumnWidth(k, 4000);// 设置列的宽度
						break;
						case 6 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 4000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					String[] arr= (String[]) list.get(i);// 获取对象
					if (arr[0] != null) {
						cell = row.createCell(0); // 创建第i+1行第1列

						cell.setCellValue(arr[0]);// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(0);
						cell.setCellStyle(style);
					}
					
					if (arr[1] != null) {
						cell = row.createCell(1);
						cell.setCellValue(arr[1]);
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (arr[2] != null) {
						cell = row.createCell(2);
						cell.setCellValue(arr[2]);
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (arr[3] != null) {
						cell = row.createCell(3);
						cell.setCellValue(arr[3]);
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (arr[4] != null) {
						cell = row.createCell(4);
						cell.setCellValue(arr[4]);
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (arr[5]!= null) {
						cell = row.createCell(5);
						cell.setCellValue(arr[5]);
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
						
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}
	
	public static String exportOrderChildren(List list,String []tableHeader,String headerStr) throws Exception {
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置类型
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style2.setBorderLeft((short)1);     //左边框
		style2.setBorderRight((short)1);    //右边框
		style2.setBorderBottom((short)1);//下边框
		
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
						break;
						case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
						break;
						case 2 : sheet.setColumnWidth(k, 13000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlMmOrderChildrenOr orderChildren= (PlMmOrderChildrenOr) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (orderChildren.getInvestPersonName() != null) {
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(orderChildren.getInvestPersonName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					if (orderChildren.getParentOrBidName() != null) {
						cell = row.createCell(2);
						cell.setCellValue(orderChildren.getParentOrBidName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (orderChildren.getMatchingMoney() != null) {
						cell = row.createCell(3);
						cell.setCellValue(orderChildren.getMatchingMoney()+"元");
						cell.setCellStyle(style2);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (orderChildren.getChildrenOrDayRate() != null) {
						cell = row.createCell(4);
						cell.setCellValue(orderChildren.getChildrenOrDayRate()+"%");
						cell.setCellStyle(style2);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (orderChildren.getMatchingStartDate() != null) {
						cell = row.createCell(5);
						cell.setCellValue(sd.format(orderChildren.getMatchingStartDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (orderChildren.getMatchingEndDate()!= null) {
						cell = row.createCell(6);
						cell.setCellValue(sd.format(orderChildren.getMatchingEndDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (orderChildren.getMatchingLimit()!= null) {
						cell = row.createCell(7);
						cell.setCellValue(orderChildren.getMatchingLimit()+"天");
						cell.setCellStyle(style2);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}	
					if (orderChildren.getMatchingGetMoney()!= null) {
						cell = row.createCell(8);
						cell.setCellValue(orderChildren.getMatchingGetMoney()+"元");
						cell.setCellStyle(style2);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}
	
	/**
	 * 
	 * @param workbook
	 * @param type
	 * @return
	 */
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,Short type){
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置数据类型
		if(type!=null){
			style2.setAlignment(type);
			style2.setBorderLeft((short)1);     //左边框
			style2.setBorderRight((short)1);    //右边框
			style2.setBorderBottom((short)1);//下边框
			if(type.equals(HSSFCellStyle.ALIGN_CENTER)){//居中模式不需要设置左右格式
				style2.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
			}
		}
		return style2;
	}
	
	
	/**
	 * 创建一个带样式的excel的sheet
	 * @return
	 */
	public static HSSFWorkbook createbook(String []tableHeader,String headerStr){
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 18000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		return workbook;
	}
	
	/**
	 * 将排版好的excel  sheet写到response中并下载到客户浏览器端
	 * @param headerStr
	 * @param workbook
	 */
	public static void writeResponse(String headerStr, HSSFWorkbook workbook){
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 风控中心-流程任务工作台监控
	 * 导出excel方法
	 * @param list
	 * @param tableHeader
	 * @param headerStr
	 * @return
	 * @throws Exception
	 */
	public static String exportProcesstask(List list,String []tableHeader,String headerStr) throws Exception {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 18000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					TaskInfo taskinfo= (TaskInfo) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (taskinfo.getTaskName() != null) {
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(taskinfo.getTaskName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (taskinfo.getActivityName() != null) {
						cell = row.createCell(2);
						cell.setCellValue(taskinfo.getActivityName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (taskinfo.getAssignee()!= null) {
						cell = row.createCell(3);
						cell.setCellValue(taskinfo.getAssignee());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellValue("暂无执行人");
						cell.setCellStyle(style);
					}
					if (taskinfo.getCreateTime() != null) {
						cell = row.createCell(4);
						cell.setCellValue(sd.format(taskinfo.getCreateTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (taskinfo.getDueDate()!= null) {
						cell = row.createCell(5);
						cell.setCellValue(sd.format(taskinfo.getDueDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
						
						
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
		return null;
	}
	public static void exportAllProcesstask(List<ProcessForm> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 18000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					ProcessForm processForm= (ProcessForm) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (processForm.getStatus() != null) {//任务状态
						cell = row.createCell(1); // 创建第i+1行第1列
						if(processForm.getStatus().equals(Short.valueOf("-1"))){
							cell.setCellValue("驳回");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("0"))){
							cell.setCellValue("等待处理");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("1"))){
							cell.setCellValue("审批通过");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("2"))){
							cell.setCellValue("流程跳转");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("3"))){
							cell.setCellValue("打回重做");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("4"))){
							cell.setCellValue("追回");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("5"))){
							cell.setCellValue("任务换人");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("6"))){
							cell.setCellValue("项目换人");// 设置第i+1行第1列的值
						}else if(processForm.getStatus().equals(Short.valueOf("7"))){
							cell.setCellValue("项目终止");// 设置第i+1行第1列的值
						}
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (processForm.getProjectName() != null) {//项目编号
						cell = row.createCell(2);
						cell.setCellValue(processForm.getProjectName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (processForm.getActivityName()!= null) {//任务名称
						cell = row.createCell(3);
						cell.setCellValue(processForm.getActivityName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (processForm.getCreatorName() != null) {//处理人
						cell = row.createCell(4);
						cell.setCellValue(processForm.getCreatorName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellValue("暂无执行人");
						cell.setCellStyle(style);
					}
					if (processForm.getCreatetime()!= null) {//分配时间
						cell = row.createCell(5);
						cell.setCellValue(sd.format(processForm.getCreatetime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					
					if (processForm.getTaskLimitTime()!= null) {//到期时间
						cell = row.createCell(6);
						cell.setCellValue(sd.format(processForm.getTaskLimitTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					
					if (processForm.getEndtime()!= null) {//实际完成时间
						cell = row.createCell(7);
						cell.setCellValue(sd.format(processForm.getEndtime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
						
					if (processForm.getDurtimes()!= null) {//耗时
						cell = row.createCell(8);
						cell.setCellValue(DateUtil.formatTime(processForm.getDurtimes()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
					
					if (processForm.getMinTime()!= null) {//异常状态
						cell = row.createCell(9);
						cell.setCellValue(processForm.getMinTime().compareTo(0l)>=0?"正常任务":"逾期任务");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(9);
						cell.setCellStyle(style);
					}
							
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	public static void exportCompleteProcesstask(List<ProcessForm> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 18000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					ProcessForm processForm= (ProcessForm) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (processForm.getProjectName() != null) {//项目编号
						cell = row.createCell(1);
						cell.setCellValue(processForm.getProjectName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (processForm.getActivityName()!= null) {//任务名称
						cell = row.createCell(2);
						cell.setCellValue(processForm.getActivityName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (processForm.getCreatorName() != null) {//处理人
						cell = row.createCell(3);
						cell.setCellValue(processForm.getCreatorName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellValue("暂无执行人");
						cell.setCellStyle(style);
					}
					if (processForm.getCreatetime()!= null) {//分配时间
						cell = row.createCell(4);
						cell.setCellValue(sd.format(processForm.getCreatetime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					
					if (processForm.getTaskLimitTime()!= null) {//到期时间
						cell = row.createCell(5);
						cell.setCellValue(sd.format(processForm.getTaskLimitTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					
					if (processForm.getEndtime()!= null) {//实际完成时间
						cell = row.createCell(6);
						cell.setCellValue(sd.format(processForm.getEndtime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
						
					if (processForm.getDurtimes()!= null) {//耗时
						cell = row.createCell(7);
						cell.setCellValue(DateUtil.formatTime(processForm.getDurtimes()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					
					if (processForm.getMinTime()!= null) {//异常状态
						cell = row.createCell(8);
						cell.setCellValue(processForm.getMinTime().compareTo(0l)>=0?"正常任务":"逾期任务");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
							
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	public static void exportAllBlackList(List<VEnterprisePerson> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					VEnterprisePerson vEnterprisePerson= (VEnterprisePerson) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (vEnterprisePerson.getName() != null) {//客户名称
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(vEnterprisePerson.getName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (vEnterprisePerson.getCode()!= null) {//证件号码
						cell = row.createCell(2);
						cell.setCellValue(vEnterprisePerson.getCode());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (vEnterprisePerson.getTel()!= null) {//联系电话
						cell = row.createCell(3);
						cell.setCellValue(vEnterprisePerson.getTel());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (vEnterprisePerson.getBlackReason() != null) {//原因说明
						cell = row.createCell(4);
						cell.setCellValue(vEnterprisePerson.getBlackReason());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	public static void exportAllBpProjectList(List<BpFundProject> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 15000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					BpFundProject bpFundProject= (BpFundProject) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (bpFundProject.getProjectName() != null) {//项目名称
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(bpFundProject.getProjectName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getOwnJointMoney()!= null) {//项目金额
						cell = row.createCell(2);
						cell.setCellValue(bpFundProject.getOwnJointMoney().toString()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getAppUserName()!= null) {//项目经理
						cell = row.createCell(3);
						cell.setCellValue(bpFundProject.getAppUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getStartDate() != null) {//开始时间
						cell = row.createCell(4);
						cell.setCellValue(sd.format(bpFundProject.getStartDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getIntentDate() != null) {//结束时间
						cell = row.createCell(5);
						cell.setCellValue(sd.format(bpFundProject.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getSuperviseManageOpinionValue() != null) {//五级分类
						cell = row.createCell(6);
						cell.setCellValue(bpFundProject.getSuperviseManageOpinionValue());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellValue("正常贷款");
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	public static void exportAllOverdueProjectList(List<BpFundProject> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 15000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					BpFundProject bpFundProject= (BpFundProject) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (bpFundProject.getProjectName() != null) {//项目名称
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(bpFundProject.getProjectName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getOwnJointMoney()!= null) {//项目金额
						cell = row.createCell(2);
						cell.setCellValue(bpFundProject.getOwnJointMoney().toString()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getAppUserName()!= null) {//项目经理
						cell = row.createCell(3);
						cell.setCellValue(bpFundProject.getAppUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getStartDate() != null) {//开始时间
						cell = row.createCell(4);
						cell.setCellValue(sd.format(bpFundProject.getStartDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getIntentDate() != null) {//结束时间
						cell = row.createCell(5);
						cell.setCellValue(sd.format(bpFundProject.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getOverduePrincipalRepayment() != null) {//本金逾期金额
						cell = row.createCell(6);
						cell.setCellValue(bpFundProject.getOverduePrincipalRepayment().toString()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellValue(0.00+"元");
						cell.setCellStyle(style);
					}
					if (bpFundProject.getOverduePrincipalRepaymentDays() != null) {//本金逾期天数
						cell = row.createCell(7);
						cell.setCellValue(bpFundProject.getOverduePrincipalRepaymentDays()+"天");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellValue(0+"天");
						cell.setCellStyle(style);
					}
					if (bpFundProject.getOverdueLoanInterest() != null) {//利息逾期金额
						cell = row.createCell(8);
						cell.setCellValue(bpFundProject.getOverdueLoanInterest().toString()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellValue(0.00+"元");
						cell.setCellStyle(style);
					}
					if (bpFundProject.getOverdueLoanInterestDays() != null) {//利息逾期天数
						cell = row.createCell(9);
						cell.setCellValue(bpFundProject.getOverdueLoanInterestDays()+"天");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(9);
						cell.setCellValue(0+"天");
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	public static void exportAllIndustryProjectList(List<BpFundProject> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 15000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					BpFundProject bpFundProject= (BpFundProject) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (bpFundProject.getHangyeTypeValue() != null) {//行业类型
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(bpFundProject.getHangyeTypeValue());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getProjectName() != null) {//项目名称
						cell = row.createCell(2); // 创建第i+1行第1列
						cell.setCellValue(bpFundProject.getProjectName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getOwnJointMoney()!= null) {//项目金额
						cell = row.createCell(3);
						cell.setCellValue(bpFundProject.getOwnJointMoney().toString()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getAppUserName()!= null) {//项目经理
						cell = row.createCell(4);
						cell.setCellValue(bpFundProject.getAppUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getStartDate() != null) {//开始时间
						cell = row.createCell(5);
						cell.setCellValue(sd.format(bpFundProject.getStartDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getIntentDate() != null) {//结束时间
						cell = row.createCell(6);
						cell.setCellValue(sd.format(bpFundProject.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (bpFundProject.getSuperviseManageOpinionValue() != null) {//五级分类
						cell = row.createCell(7);
						cell.setCellValue(bpFundProject.getSuperviseManageOpinionValue());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellValue("正常贷款");
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
    //
	public static void exportAllBpLoneExternalList(List<BpLoneExternal> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFCellStyle style2 =createCellStyle(workbook,HSSFCellStyle.ALIGN_RIGHT); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			
			if(headerStr.indexOf("外部借贷项目列表")==0){
				switch(k){
					case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
					break;
					case 2 : sheet.setColumnWidth(k, 9000);// 设置列的宽度
					break;
					case 6 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
					break;
					default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				}
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					BpLoneExternal bpLoneExternal= (BpLoneExternal) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					if(headerStr.indexOf("外部借贷项目列表")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						DecimalFormat  df1 = new DecimalFormat("#,###.##");
						if (bpLoneExternal.getBusinessType() != null && "InternetFinance".equals(bpLoneExternal.getBusinessType())) {
							cell = row.createCell(1);
							cell.setCellValue("互联网金融");
							cell.setCellStyle(style);
						} else	if ( "SmallLoan".equals(bpLoneExternal.getBusinessType())) {
							cell = row.createCell(1);
							cell.setCellValue("小额贷款");
							cell.setCellStyle(style);
						} 
						else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getCustomerName() != null) {
							cell = row.createCell(2);
							cell.setCellValue(bpLoneExternal.getCustomerName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getCardnumber() != null) {
							cell = row.createCell(3);
							cell.setCellValue(bpLoneExternal.getCardnumber());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getLoneMoney()!= null) {
							cell = row.createCell(4); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpLoneExternal.getLoneMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getOnLoneMoney()!= null) {
							cell = row.createCell(5); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpLoneExternal.getOnLoneMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getLoneCompany() != null) {
							cell = row.createCell(6);
							cell.setCellValue(bpLoneExternal.getLoneCompany());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getOverdueDays() != null) {
							cell = row.createCell(7);
							cell.setCellValue(bpLoneExternal.getOverdueDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getStartDate()!= null) {
							cell = row.createCell(8); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpLoneExternal.getStartDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getIntentDate()!= null) {
							cell = row.createCell(9); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpLoneExternal.getIntentDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						if (bpLoneExternal.getProjectStatusValue() != null) {
							cell = row.createCell(10);
							cell.setCellValue(bpLoneExternal.getProjectStatusValue());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
   
	public static void exportAllBpBadCreditList(List<BpBadCredit> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFCellStyle style2 =createCellStyle(workbook,HSSFCellStyle.ALIGN_RIGHT); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			
			if(headerStr.indexOf("不良征信登记列表")==0){
				switch(k){
					case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
					break;
					case 3 : sheet.setColumnWidth(k, 9000);// 设置列的宽度
					break;
					case 4 : sheet.setColumnWidth(k, 9000);// 设置列的宽度
					break;
					case 7 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
					break;
					default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				}
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					BpBadCredit bpBadCredit= (BpBadCredit) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					if(headerStr.indexOf("不良征信登记列表")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						DecimalFormat  df1 = new DecimalFormat("#,###.##");
						if (bpBadCredit.getBusinessType() != null && "InternetFinance".equals(bpBadCredit.getBusinessType())) {
							cell = row.createCell(1);
							cell.setCellValue("互联网金融");
							cell.setCellStyle(style);
						} else	if ( "SmallLoan".equals(bpBadCredit.getBusinessType())) {
							cell = row.createCell(1);
							cell.setCellValue("小额贷款");
							cell.setCellStyle(style);
						} 
						if (bpBadCredit.getReportType() != null && 0==bpBadCredit.getReportType()) {
							cell = row.createCell(2);
							cell.setCellValue("内部");
							cell.setCellStyle(style);
						} else	if ( 1==bpBadCredit.getReportType()) {
							cell = row.createCell(2);
							cell.setCellValue("外部");
							cell.setCellStyle(style);
						} 
						else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getProjectName() != null) {
							cell = row.createCell(3);
							cell.setCellValue(bpBadCredit.getProjectName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getCustomerName() != null) {
							cell = row.createCell(4);
							cell.setCellValue(bpBadCredit.getCustomerName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getCardnumber() != null) {
							cell = row.createCell(5);
							cell.setCellValue(bpBadCredit.getCardnumber());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getLoneMoney()!= null) {
							cell = row.createCell(6); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpBadCredit.getLoneMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getLoneCompany() != null) {
							cell = row.createCell(7);
							cell.setCellValue(bpBadCredit.getLoneCompany());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getOverdueDays() != null) {
							cell = row.createCell(8);
							cell.setCellValue(bpBadCredit.getOverdueDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getBadTypeValue() != null) {
							cell = row.createCell(9);
							cell.setCellValue(bpBadCredit.getBadTypeValue());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getCreator() != null) {
							cell = row.createCell(10);
							cell.setCellValue(bpBadCredit.getCreator());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
						if (bpBadCredit.getCreateDate()!= null) {
							cell = row.createCell(11); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpBadCredit.getCreateDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(11);
							cell.setCellStyle(style);
						}
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	public static void exportAllBrowerProjectList(List<BpFundProject> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFCellStyle style2 =createCellStyle(workbook,HSSFCellStyle.ALIGN_RIGHT); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			
			if(headerStr.indexOf("主借款人登记项目列表")==0){
				switch(k){
					case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
					break;
					case 2 : sheet.setColumnWidth(k, 12000);// 设置列的宽度
					break;
					case 10 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
					break;
					default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				}
			}
			if(headerStr.indexOf("保证人登记项目列表")==0){
				switch(k){
					case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
					break;
					case 4 : sheet.setColumnWidth(k, 12000);// 设置列的宽度
					break;
					case 13 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
					break;
					default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				}
			}
			if(headerStr.indexOf("法人借款登记项目列表")==0){
				switch(k){
					case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
					break;
					case 4 : sheet.setColumnWidth(k, 12000);// 设置列的宽度
					break;
					case 12 : sheet.setColumnWidth(k, 3000);// 设置列的宽度
					break;
					default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				}
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					BpFundProject bpFundProject= (BpFundProject) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					if(headerStr.indexOf("主借款人登记项目列表")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						DecimalFormat  df1 = new DecimalFormat("#,###.##");
						if (bpFundProject.getBusinessType() != null && "SmallLoan".equals(bpFundProject.getBusinessType()) && bpFundProject.getFlag()==1 ) {
							cell = row.createCell(1);
							cell.setCellValue("互联网金融");
							cell.setCellStyle(style);
						} else	if ( "SmallLoan".equals(bpFundProject.getBusinessType()) && bpFundProject.getFlag()==0 ) {
							cell = row.createCell(1);
							cell.setCellValue("小额贷款");
							cell.setCellStyle(style);
						} 
						else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getProjectName() != null) {
							cell = row.createCell(2);
							cell.setCellValue(bpFundProject.getProjectName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getJointMoney()!= null) {
							cell = row.createCell(3); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getJointMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getStartDate()!= null) {
							cell = row.createCell(4); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpFundProject.getStartDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getIntentDate()!= null) {
							cell = row.createCell(5); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpFundProject.getIntentDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverduePrincipalRepayment()!= null) {
							cell = row.createCell(6); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getOverduePrincipalRepayment().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverduePrincipalRepaymentDays()!= null) {
							cell = row.createCell(7);
							cell.setCellValue(bpFundProject.getOverduePrincipalRepaymentDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverdueLoanInterest()!= null) {
							cell = row.createCell(8); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getOverdueLoanInterest().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverdueLoanInterestDays()!= null) {
							cell = row.createCell(9);
							cell.setCellValue(bpFundProject.getOverdueLoanInterestDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getProjectStatus()!= null && bpFundProject.getProjectStatus()==0 ) {
							cell = row.createCell(10);
							cell.setCellValue("办理中");
							cell.setCellStyle(style);
						} else if( bpFundProject.getProjectStatus()==1 ) {
							cell = row.createCell(10);
							cell.setCellValue("还款中");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==2 ) {
							cell = row.createCell(10);
							cell.setCellValue("已完成");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==3 ) {
							cell = row.createCell(10);
							cell.setCellValue("已终止");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==4 ) {
							cell = row.createCell(10);
							cell.setCellValue("已展期");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==8 ) {
							cell = row.createCell(10);
							cell.setCellValue("已违约");
							cell.setCellStyle(style);
						}
						else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
					}
					if(headerStr.indexOf("保证人登记项目列表")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						DecimalFormat  df1 = new DecimalFormat("#,###.##");
						if (bpFundProject.getBusinessType() != null && "SmallLoan".equals(bpFundProject.getBusinessType()) && bpFundProject.getFlag()==1 ) {
							cell = row.createCell(1);
							cell.setCellValue("互联网金融");
							cell.setCellStyle(style);
						} else	if ( "SmallLoan".equals(bpFundProject.getBusinessType()) && bpFundProject.getFlag()==0 ) {
							cell = row.createCell(1);
							cell.setCellValue("小额贷款");
							cell.setCellStyle(style);
						} 
						else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getName() != null) {
							cell = row.createCell(2);
							cell.setCellValue(bpFundProject.getName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getCardnumber() != null) {
							cell = row.createCell(3);
							cell.setCellValue(bpFundProject.getCardnumber());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getProjectName() != null) {
							cell = row.createCell(4);
							cell.setCellValue(bpFundProject.getProjectName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getJointMoney()!= null) {
							cell = row.createCell(5); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getJointMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getMortgagepersontypeforvalue() != null) {
							cell = row.createCell(6);
							cell.setCellValue(bpFundProject.getMortgagepersontypeforvalue());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getStartDate()!= null) {
							cell = row.createCell(7); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpFundProject.getStartDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getIntentDate()!= null) {
							cell = row.createCell(8); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpFundProject.getIntentDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverduePrincipalRepayment()!= null) {
							cell = row.createCell(9); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getOverduePrincipalRepayment().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverduePrincipalRepaymentDays()!= null) {
							cell = row.createCell(10);
							cell.setCellValue(bpFundProject.getOverduePrincipalRepaymentDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverdueLoanInterest()!= null) {
							cell = row.createCell(11); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getOverdueLoanInterest().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(11);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverdueLoanInterestDays()!= null) {
							cell = row.createCell(12);
							cell.setCellValue(bpFundProject.getOverdueLoanInterestDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(12);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getProjectStatus()!= null && bpFundProject.getProjectStatus()==0 ) {
							cell = row.createCell(13);
							cell.setCellValue("办理中");
							cell.setCellStyle(style);
						} else if( bpFundProject.getProjectStatus()==1 ) {
							cell = row.createCell(13);
							cell.setCellValue("还款中");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==2 ) {
							cell = row.createCell(13);
							cell.setCellValue("已完成");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==3 ) {
							cell = row.createCell(13);
							cell.setCellValue("已终止");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==4 ) {
							cell = row.createCell(13);
							cell.setCellValue("已展期");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==8 ) {
							cell = row.createCell(13);
							cell.setCellValue("已违约");
							cell.setCellStyle(style);
						}
						else{
							cell = row.createCell(13);
							cell.setCellStyle(style);
						}
					}
					if(headerStr.indexOf("法人借款登记项目列表")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						DecimalFormat  df1 = new DecimalFormat("#,###.##");
						if (bpFundProject.getBusinessType() != null && "SmallLoan".equals(bpFundProject.getBusinessType()) && bpFundProject.getFlag()==1 ) {
							cell = row.createCell(1);
							cell.setCellValue("互联网金融");
							cell.setCellStyle(style);
						} else	if ( "SmallLoan".equals(bpFundProject.getBusinessType()) && bpFundProject.getFlag()==0 ) {
							cell = row.createCell(1);
							cell.setCellValue("小额贷款");
							cell.setCellStyle(style);
						} 
						else{
							cell = row.createCell(1);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getName() != null) {
							cell = row.createCell(2);
							cell.setCellValue(bpFundProject.getName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getCardnumber() != null) {
							cell = row.createCell(3);
							cell.setCellValue(bpFundProject.getCardnumber());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getProjectName() != null) {
							cell = row.createCell(4);
							cell.setCellValue(bpFundProject.getProjectName());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getJointMoney()!= null) {
							cell = row.createCell(5); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getJointMoney().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getStartDate()!= null) {
							cell = row.createCell(6); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpFundProject.getStartDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getIntentDate()!= null) {
							cell = row.createCell(7); // 创建第i+1行第1列
							cell.setCellValue(from.format(bpFundProject.getIntentDate()));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverduePrincipalRepayment()!= null) {
							cell = row.createCell(8); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getOverduePrincipalRepayment().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverduePrincipalRepaymentDays()!= null) {
							cell = row.createCell(9);
							cell.setCellValue(bpFundProject.getOverduePrincipalRepaymentDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverdueLoanInterest()!= null) {
							cell = row.createCell(10); // 创建第i+1行第1列
							cell.setCellValue(df1.format(bpFundProject.getOverdueLoanInterest().doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getOverdueLoanInterestDays()!= null) {
							cell = row.createCell(11);
							cell.setCellValue(bpFundProject.getOverdueLoanInterestDays());
							cell.setCellStyle(style);
						}else{
							cell = row.createCell(11);
							cell.setCellStyle(style);
						}
						if (bpFundProject.getProjectStatus()!= null && bpFundProject.getProjectStatus()==0 ) {
							cell = row.createCell(12);
							cell.setCellValue("办理中");
							cell.setCellStyle(style);
						} else if( bpFundProject.getProjectStatus()==1 ) {
							cell = row.createCell(12);
							cell.setCellValue("还款中");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==2 ) {
							cell = row.createCell(12);
							cell.setCellValue("已完成");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==3 ) {
							cell = row.createCell(12);
							cell.setCellValue("已终止");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==4 ) {
							cell = row.createCell(12);
							cell.setCellValue("已展期");
							cell.setCellStyle(style);
						}
						else if( bpFundProject.getProjectStatus()==8 ) {
							cell = row.createCell(12);
							cell.setCellValue("已违约");
							cell.setCellStyle(style);
						}
						else{
							cell = row.createCell(12);
							cell.setCellStyle(style);
						}
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}

	//导出账户交易台账
	public static void exportAllToExcel(
			List<PlFinanceProductUserAccountInfo> list, String[] tableHeader, String headerStr) {

		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlFinanceProductUserAccountInfo plFinanceProductUserAccountInfo= (PlFinanceProductUserAccountInfo) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plFinanceProductUserAccountInfo.getDealDate() != null) {//交易时间
						cell = row.createCell(1);
						cell.setCellValue(sd.format(plFinanceProductUserAccountInfo.getDealDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getUserloginName()!= null) {//会员账号
						cell = row.createCell(2);
						cell.setCellValue(plFinanceProductUserAccountInfo.getUserloginName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getUserName() != null) {//开会名
						cell = row.createCell(3);
						cell.setCellValue(plFinanceProductUserAccountInfo.getUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getProductName()!= null) {//产品名称
						cell = row.createCell(4);
						cell.setCellValue(plFinanceProductUserAccountInfo.getProductName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getAmont()!= null) {//交易金额
						cell = row.createCell(5);
						cell.setCellValue(plFinanceProductUserAccountInfo.getAmont().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getCurrentMoney()!= null) {//账户金额
						cell = row.createCell(6);
						cell.setCellValue(plFinanceProductUserAccountInfo.getCurrentMoney().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getDealtype()!= null) {//交易类型
						cell = row.createCell(7);
						if("1".equals(plFinanceProductUserAccountInfo.getDealtype())){
							cell.setCellValue("转入");
						}else if("2".equals(plFinanceProductUserAccountInfo.getDealtype())){
							cell.setCellValue("转出");
						}else if("3".equals(plFinanceProductUserAccountInfo.getDealtype())){
							cell.setCellValue("收益");
						}else{
							cell.setCellValue("未知类型");
						}
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
						
					if (plFinanceProductUserAccountInfo.getDealStatus()!= null) {//交易状态
						cell = row.createCell(8);
						if(Short.valueOf("-1").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("交易异常");
						}else if(Short.valueOf("0").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("系统资金");
						}else if(Short.valueOf("1").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("在途资金");
						}else if(Short.valueOf("2").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("交易成功");
						}else{
							cell.setCellValue("未知类型");
						}
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getRequestNo()!= null) {//流水号
						cell = row.createCell(9);
						cell.setCellValue(plFinanceProductUserAccountInfo.getRequestNo());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(9);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getRemark()!= null) {//备注
						cell = row.createCell(10);
						cell.setCellValue(plFinanceProductUserAccountInfo.getRemark());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(10);
						cell.setCellStyle(style);
					}
							
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	
		
	}
	public static void exportExceptionToExcel(
			List<PlFinanceProductUserAccountInfo> list, String[] tableHeader, String headerStr) {
		
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlFinanceProductUserAccountInfo plFinanceProductUserAccountInfo= (PlFinanceProductUserAccountInfo) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plFinanceProductUserAccountInfo.getDealDate() != null) {//交易时间
						cell = row.createCell(1);
						cell.setCellValue(sd.format(plFinanceProductUserAccountInfo.getDealDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getUserloginName()!= null) {//会员账号
						cell = row.createCell(2);
						cell.setCellValue(plFinanceProductUserAccountInfo.getUserloginName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getUserName() != null) {//开会名
						cell = row.createCell(3);
						cell.setCellValue(plFinanceProductUserAccountInfo.getUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getProductName()!= null) {//产品名称
						cell = row.createCell(4);
						cell.setCellValue(plFinanceProductUserAccountInfo.getProductName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getAmont()!= null) {//交易金额
						cell = row.createCell(5);
						cell.setCellValue(plFinanceProductUserAccountInfo.getAmont().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getDealtype()!= null) {//交易类型
						cell = row.createCell(6);
						if("1".equals(plFinanceProductUserAccountInfo.getDealtype())){
							cell.setCellValue("转入");
						}else if("2".equals(plFinanceProductUserAccountInfo.getDealtype())){
							cell.setCellValue("转出");
						}else if("3".equals(plFinanceProductUserAccountInfo.getDealtype())){
							cell.setCellValue("收益");
						}else{
							cell.setCellValue("未知类型");
						}
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
						
					
					if (plFinanceProductUserAccountInfo.getRequestNo()!= null) {//流水号
						cell = row.createCell(7);
						cell.setCellValue(plFinanceProductUserAccountInfo.getRequestNo());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getRemark()!= null) {//备注
						cell = row.createCell(8);
						cell.setCellValue(plFinanceProductUserAccountInfo.getRemark());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
							
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	
		
	}
	public static void exportIntentToExcel(
			List<PlFinanceProductUserAccountInfo> list, String[] tableHeader, String headerStr) {

		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlFinanceProductUserAccountInfo plFinanceProductUserAccountInfo= (PlFinanceProductUserAccountInfo) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plFinanceProductUserAccountInfo.getDealDate() != null) {//交易时间
						cell = row.createCell(1);
						cell.setCellValue(sd.format(plFinanceProductUserAccountInfo.getDealDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getUserloginName()!= null) {//会员账号
						cell = row.createCell(2);
						cell.setCellValue(plFinanceProductUserAccountInfo.getUserloginName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getUserName() != null) {//开会名
						cell = row.createCell(3);
						cell.setCellValue(plFinanceProductUserAccountInfo.getUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getProductName()!= null) {//产品名称
						cell = row.createCell(4);
						cell.setCellValue(plFinanceProductUserAccountInfo.getProductName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getAmont()!= null) {//交易金额
						cell = row.createCell(5);
						cell.setCellValue(plFinanceProductUserAccountInfo.getAmont().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUserAccountInfo.getCurrentMoney()!= null) {//账户金额
						cell = row.createCell(6);
						cell.setCellValue(plFinanceProductUserAccountInfo.getCurrentMoney().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
						
					if (plFinanceProductUserAccountInfo.getDealStatus()!= null) {//交易状态
						cell = row.createCell(7);
						if(Short.valueOf("-1").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("交易异常");
						}else if(Short.valueOf("0").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("系统资金");
						}else if(Short.valueOf("1").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("在途资金");
						}else if(Short.valueOf("2").equals(plFinanceProductUserAccountInfo.getDealStatus())){
							cell.setCellValue("交易成功");
						}else{
							cell.setCellValue("未知类型");
						}
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getRequestNo()!= null) {//流水号
						cell = row.createCell(8);
						cell.setCellValue(plFinanceProductUserAccountInfo.getRequestNo());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUserAccountInfo.getRemark()!= null) {//备注
						cell = row.createCell(9);
						cell.setCellValue(plFinanceProductUserAccountInfo.getRemark());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(9);
						cell.setCellStyle(style);
					}
							
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	
		
	}
	//理财专户列表导出excel
	public static void ExportProductUseraccountToExcel(
			List<PlFinanceProductUseraccount> list, String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlFinanceProductUseraccount plFinanceProductUseraccount= (PlFinanceProductUseraccount) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plFinanceProductUseraccount.getUserloginName() != null) {//会员名称
						cell = row.createCell(1);
						cell.setCellValue(plFinanceProductUseraccount.getUserloginName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					
					if (plFinanceProductUseraccount.getUserName() != null) {//开户名
						cell = row.createCell(2);
						cell.setCellValue(plFinanceProductUseraccount.getUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					
					
					if (plFinanceProductUseraccount.getCurrentMoney()!= null) {//账户金额
						cell = row.createCell(3);
						cell.setCellValue(plFinanceProductUseraccount.getCurrentMoney().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUseraccount.getTotalIntestMoney()!= null) {//累计收益金额
						cell = row.createCell(4);
						cell.setCellValue(plFinanceProductUseraccount.getTotalIntestMoney().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUseraccount.getYesterdayEarn()!= null) {//昨日金额
						cell = row.createCell(5);
						cell.setCellValue(plFinanceProductUseraccount.getYesterdayEarn().doubleValue()+"元");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (plFinanceProductUseraccount.getOpenDate() != null) {//开户时间
						cell = row.createCell(6);
						cell.setCellValue(sd.format(plFinanceProductUseraccount.getOpenDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductUseraccount.getAccountStatus()!= null) {//账户状态
						cell = row.createCell(7);
						if(Short.valueOf("-1").equals(plFinanceProductUseraccount.getAccountStatus())){
							cell.setCellValue("账户锁定");
						}else{
							cell.setCellValue("账户开启");
						}
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
							
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	//平台理财专户产品利率列表
	public static void exportAllProductRate(List<PlFinanceProductRate> list, String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 5000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlFinanceProductRate plFinanceProductRate= (PlFinanceProductRate) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					DecimalFormat   df   =new  DecimalFormat("#.00");  
					
					if (plFinanceProductRate.getProductName() != null) {//产品名称
						cell = row.createCell(1);
						cell.setCellValue(plFinanceProductRate.getProductName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductRate.getIntentDate() != null) {//执行日期
						cell = row.createCell(2);
						cell.setCellValue(sd.format(plFinanceProductRate.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductRate.getYearRate()!= null) {//年化利率
						cell = row.createCell(3);
						cell.setCellValue(df.format(plFinanceProductRate.getYearRate())+"%");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plFinanceProductRate.getSevYearRate()!= null) {//七日平均年化利率
						cell = row.createCell(4);
						cell.setCellValue(df.format(plFinanceProductRate.getSevYearRate())+"%");
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductRate.getModifyDate() != null) {//设置日期
						cell = row.createCell(5);
						cell.setCellValue(sd.format(plFinanceProductRate.getModifyDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductRate.getModifyDate() != null) {//修改时间
						cell = row.createCell(6);
						cell.setCellValue(sd.format(plFinanceProductRate.getModifyDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					
					if (plFinanceProductRate.getCreateUserName()!= null) {//修改人
						cell = row.createCell(7);
						cell.setCellValue(plFinanceProductRate.getCreateUserName());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
							
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}

	
	
	public static void toExcelPlBuyinfo(List<PlManageMoneyPlan> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlManageMoneyPlan plManageMoneyPlan= (PlManageMoneyPlan) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plManageMoneyPlan.getMmNumber() != null) {//招标编号
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getMmNumber());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlan.getMmName() != null) {//招标名称
						cell = row.createCell(2); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getMmName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlan.getStartinInterestTime()!= null) {//起息日期
						cell = row.createCell(3);
						cell.setCellValue(sd.format(plManageMoneyPlan.getStartinInterestTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlan.getIntentDate()!= null) {//计划派息日期
						cell = row.createCell(4);
						cell.setCellValue(sd.format(plManageMoneyPlan.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlan.getSumIncomeMoney() != null) {//派息合计
						cell = row.createCell(5);
						cell.setCellValue(plManageMoneyPlan.getSumIncomeMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlan.getFactDate() != null) {//实际派息日
						cell = row.createCell(6);
						cell.setCellValue(sd.format(plManageMoneyPlan.getFactDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	
	public static void toExcelListInfo(List<PlManageMoneyPlanBuyinfo> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo= (PlManageMoneyPlanBuyinfo) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plManageMoneyPlanBuyinfo.getInvestPersonName() !=null) {//账号
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getInvestPersonName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlanBuyinfo.getCouponsMoney() !=null) {//体验券金额
						cell = row.createCell(2); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getCouponsMoney().toString());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					
					
					if (plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmNumber() != null) {//招标编号
						cell = row.createCell(3); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmNumber());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getMmName() != null) {//招标名称
						cell = row.createCell(4); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getMmName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getBuyDatetime()!= null) {//投标日期
						cell = row.createCell(5);
						cell.setCellValue(sd.format(plManageMoneyPlanBuyinfo.getBuyDatetime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getStartinInterestTime()!= null) {//起息日期
						cell = row.createCell(6);
						cell.setCellValue(sd.format(plManageMoneyPlanBuyinfo.getStartinInterestTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getEndinInterestTime() != null) {//到期日期
						cell = row.createCell(7);
						cell.setCellValue(sd.format(plManageMoneyPlanBuyinfo.getEndinInterestTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	public static void toExcelUPlanListInfo(List<PlManageMoneyPlanBuyinfo> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo= (PlManageMoneyPlanBuyinfo) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plManageMoneyPlanBuyinfo.getInvestPersonName() !=null) {//账号
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getInvestPersonName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlanBuyinfo.getBuyMoney() !=null) {//投资金额
						cell = row.createCell(2); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getBuyMoney().toString());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					
					
					if (plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmNumber() != null) {//招标编号
						cell = row.createCell(3); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmNumber());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getMmName() != null) {//招标名称
						cell = row.createCell(4); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlanBuyinfo.getMmName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getBuyDatetime()!= null) {//投标日期
						cell = row.createCell(5);
						cell.setCellValue(sd.format(plManageMoneyPlanBuyinfo.getBuyDatetime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getStartinInterestTime()!= null) {//起息日期
						cell = row.createCell(6);
						cell.setCellValue(sd.format(plManageMoneyPlanBuyinfo.getStartinInterestTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getEndinInterestTime() != null) {//到期日期
						cell = row.createCell(7);
						cell.setCellValue(sd.format(plManageMoneyPlanBuyinfo.getEndinInterestTime()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					if (plManageMoneyPlanBuyinfo.getState() != null) {//状态
						String stateValue="";
						if(plManageMoneyPlanBuyinfo.getState()==-2){
							stateValue="已流标";
						}else if(plManageMoneyPlanBuyinfo.getState()==1){
							stateValue="购买中";
						}else if(plManageMoneyPlanBuyinfo.getState()==2){
							stateValue="持有中";
						}else if(plManageMoneyPlanBuyinfo.getState()==7){
							stateValue="提前退出申请中";
						}else if(plManageMoneyPlanBuyinfo.getState()==8){
							stateValue="已退出";
						}else if(plManageMoneyPlanBuyinfo.getState()==10){
							stateValue="已完成";
						}else{
							stateValue="其他状态";
						}
						cell = row.createCell(8);
						cell.setCellValue(stateValue);
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	/**
	 * U计划债权列表导出方法
	 * @param list
	 * @param tableHeader
	 * @param headerStr
	 */
	public static void toExcelRightChildren(List<PlMmObligatoryRightChildren> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlMmObligatoryRightChildren plMmObligatoryRightChildren= (PlMmObligatoryRightChildren) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plMmObligatoryRightChildren.getBidProName() !=null) {//标的名称
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(plMmObligatoryRightChildren.getBidProName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					if (plMmObligatoryRightChildren.getUserName() !=null) {//投资人
						cell = row.createCell(2); // 创建第i+1行第1列
						cell.setCellValue(plMmObligatoryRightChildren.getUserName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					
					
					if (plMmObligatoryRightChildren.getParentOrBidName() != null) {//债权名称
						cell = row.createCell(3); // 创建第i+1行第1列
						cell.setCellValue(plMmObligatoryRightChildren.getParentOrBidName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getChildrenorName() != null) {//子债权名称
						cell = row.createCell(4); // 创建第i+1行第1列
						cell.setCellValue(plMmObligatoryRightChildren.getChildrenorName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getStartDate()!= null) {//债权起日
						cell = row.createCell(5);
						cell.setCellValue(sd.format(plMmObligatoryRightChildren.getStartDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getIntentDate()!= null) {//债权止日
						cell = row.createCell(6);
						cell.setCellValue(sd.format(plMmObligatoryRightChildren.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getOrlimit() != null) {//期限
						cell = row.createCell(7);
						cell.setCellValue(plMmObligatoryRightChildren.getOrlimit());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getPrincipalMoney() != null) {//本金
						cell = row.createCell(8);
						cell.setCellValue(plMmObligatoryRightChildren.getPrincipalMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getDayRate() != null) {//日化利率
						cell = row.createCell(9);
						cell.setCellValue(plMmObligatoryRightChildren.getDayRate().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(9);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getAvailableMoney() != null) {//可转让金额
						cell = row.createCell(10);
						cell.setCellValue(plMmObligatoryRightChildren.getAvailableMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(10);
						cell.setCellStyle(style);
					}
					if (plMmObligatoryRightChildren.getSurplusValueMoney() != null) {//债权剩余价值
						cell = row.createCell(11);
						cell.setCellValue(plMmObligatoryRightChildren.getSurplusValueMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(11);
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	
	/**
	 * 逾期散标台账
	 * @param list
	 * @param tableHeader
	 * @param headerStr
	 */
	public static void toOverDueExcel(List<SlFundIntentPeriod> list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					SlFundIntentPeriod slFundIntentPeriod= (SlFundIntentPeriod) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (slFundIntentPeriod.getBorrowName() !=null) {//借款人
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(slFundIntentPeriod.getBorrowName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					if (slFundIntentPeriod.getBidPlanName() !=null) {//招标项目名称
						cell = row.createCell(2); // 创建第i+1行第1列
						cell.setCellValue(slFundIntentPeriod.getBidPlanName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					
					
					if (slFundIntentPeriod.getBidPlanProjectNum() != null) {//招标项目编号
						cell = row.createCell(3); // 创建第i+1行第1列
						cell.setCellValue(slFundIntentPeriod.getBidPlanProjectNum());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					if (slFundIntentPeriod.getPayintentPeriod() != null) {//期数
						cell = row.createCell(4); // 创建第i+1行第1列
						cell.setCellValue("第"+slFundIntentPeriod.getPayintentPeriod()+"期");// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					if (slFundIntentPeriod.getPrincipalRepayment().getIncomeMoney()!= null) {//本金
						cell = row.createCell(5);
						cell.setCellValue(slFundIntentPeriod.getPrincipalRepayment().getIncomeMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(5);
						cell.setCellStyle(style);
					}
					if (slFundIntentPeriod.getIntentDate()!= null) {//利息
						cell = row.createCell(6);
						cell.setCellValue(sd.format(slFundIntentPeriod.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					if (slFundIntentPeriod.getConsultationMoney().getIncomeMoney() != null) {//管理咨询费
						cell = row.createCell(7);
						cell.setCellValue(slFundIntentPeriod.getConsultationMoney().getIncomeMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					if (slFundIntentPeriod.getServiceMoney().getIncomeMoney() != null) {//财务服务费
						cell = row.createCell(8);
						cell.setCellValue(slFundIntentPeriod.getServiceMoney().getIncomeMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
					if (slFundIntentPeriod.getInterestPenalty().getIncomeMoney() != null) {//补偿息
						cell = row.createCell(9);
						cell.setCellValue(slFundIntentPeriod.getInterestPenalty().getIncomeMoney().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(9);
						cell.setCellStyle(style);
					}
					if (slFundIntentPeriod.getPunishDays() != null) {//逾期天数
						cell = row.createCell(10);
						cell.setCellValue(slFundIntentPeriod.getPunishDays().toString());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(10);
						cell.setCellStyle(style);
					}
					//罚息
					cell = row.createCell(11);
					cell.setCellValue(((slFundIntentPeriod.getPrincipalRepayment().getAccrualMoney())).add((slFundIntentPeriod.getLoanInterest().getAccrualMoney())).add((slFundIntentPeriod.getConsultationMoney().getAccrualMoney())).add((slFundIntentPeriod.getServiceMoney().getAccrualMoney())).toString());
					cell.setCellStyle(style);
					
					//合计
					cell = row.createCell(12);
					cell.setCellValue(slFundIntentPeriod.getPrincipalRepayment().getIncomeMoney().add((slFundIntentPeriod.getLoanInterest().getIncomeMoney())).add((slFundIntentPeriod.getConsultationMoney().getIncomeMoney())).add((slFundIntentPeriod.getServiceMoney().getIncomeMoney())).add((slFundIntentPeriod.getInterestPenalty().getIncomeMoney()))
					           .add((slFundIntentPeriod.getPrincipalRepayment().getAccrualMoney())).add((slFundIntentPeriod.getLoanInterest().getAccrualMoney())).add((slFundIntentPeriod.getConsultationMoney().getAccrualMoney())).add((slFundIntentPeriod.getServiceMoney().getAccrualMoney())).toString());
					cell.setCellStyle(style);
					
					if (slFundIntentPeriod.getIntentDate() != null) {//计划还款日
						cell = row.createCell(13);
						cell.setCellValue(sd.format(slFundIntentPeriod.getIntentDate()));
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(13);
						cell.setCellStyle(style);
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	
	/**
	 * 理财计划导出
	 * @param list
	 * @param tableHeader
	 * @param headerStr
	 */
	public static void toExcelPlanList(List<PlManageMoneyPlan> list,
			String[] tableHeader, String headerStr) {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			switch(k){
				case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				break;
				case 1 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
				break;
				case 3 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				case 4 : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				break;
				default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					PlManageMoneyPlan plManageMoneyPlan= (PlManageMoneyPlan) list.get(i);// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					if (plManageMoneyPlan.getMmNumber() !=null) {
						cell = row.createCell(1); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getMmNumber());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getMmName() !=null) {
						cell = row.createCell(2); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getMmName());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getReceiverType() !=null) {
						String receiverTypeValue = "";
						if(plManageMoneyPlan.getReceiverType().equals("zc")){
							receiverTypeValue="注册用户";
						}else if(plManageMoneyPlan.getReceiverType().equals("pt")){
							receiverTypeValue="平台账户";
						}
						cell = row.createCell(3); // 创建第i+1行第1列
						cell.setCellValue(receiverTypeValue);// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(3);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getMoneyReceiver() !=null) {
						cell = row.createCell(4); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getMoneyReceiver());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(4);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getAuthorityStatus() !=null) {
						String authorityStatusValue = "";
						if(plManageMoneyPlan.getAuthorityStatus().toString().equals("1")){
							authorityStatusValue="是";
						}else{
							authorityStatusValue="否";
						}
						cell = row.createCell(5); // 创建第i+1行第1列
						cell.setCellValue(authorityStatusValue);// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(5);
						cell.setCellValue("否");// 设置第i+1行第1列的值
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getSumMoney() !=null) {
						cell = row.createCell(6); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getSumMoney().toString()+"元");// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(6);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getInvestedMoney() !=null) {
						cell = row.createCell(7); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getInvestedMoney().toString()+"元");// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(7);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getYeaRate() !=null) {
						cell = row.createCell(8); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getYeaRate().setScale(2).toString()+"%");// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(8);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getInvestlimit() !=null) {
						cell = row.createCell(9); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getInvestlimit().toString()+"月");// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(9);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getLockingLimit() !=null) {
						cell = row.createCell(10); // 创建第i+1行第1列
						cell.setCellValue(plManageMoneyPlan.getLockingLimit().toString()+"月");// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(10);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getBuyStartTime() !=null) {
						cell = row.createCell(11); // 创建第i+1行第1列
						cell.setCellValue(sd.format(plManageMoneyPlan.getBuyStartTime()));// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(11);
						cell.setCellStyle(style);
					}
					
					if (plManageMoneyPlan.getBuyEndTime() !=null) {
						cell = row.createCell(12); // 创建第i+1行第1列
						cell.setCellValue(sd.format(plManageMoneyPlan.getBuyEndTime()));// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(12);
						cell.setCellStyle(style);
					}
					
				    
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
	public static void exportSlEarlyRepaymentList(List  list,
			String[] tableHeader, String headerStr) {
		//生成带格式的excel表单
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = createCellStyle(workbook,HSSFCellStyle.ALIGN_LEFT);
		HSSFCellStyle style1 = createCellStyle(workbook,HSSFCellStyle.ALIGN_CENTER); // 设置数据类型
		HSSFCellStyle style2 =createCellStyle(workbook,HSSFCellStyle.ALIGN_RIGHT); // 设置数据类型
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		header.setCenter(headerStr);
		row = sheet.createRow(0);
		row.setHeight((short) 400);
		for (int k = 0; k < cellNumber; k++) {
			cell = row.createCell(k);// 创建第0行第k列
			cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
			
			if(headerStr.indexOf("提前还款列表")==0){
				switch(k){
			    	case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
				    break;
					case 1 : sheet.setColumnWidth(k, 9000);// 设置列的宽度
					break;
					case 2 : sheet.setColumnWidth(k, 5000);// 设置列的宽度
					break;
					case 3 : sheet.setColumnWidth(k, 10000);// 设置列的宽度
					break;
					case 7 : sheet.setColumnWidth(k, 8000);// 设置列的宽度
					break;
					default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
				}
			}
			font.setColor(HSSFFont.COLOR_RED); // 设置单元格字体的颜色.
			font.setFontHeight((short) 350); // 设置单元字体高度
			font.setFontHeightInPoints((short)11);   //表头字体大小 
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
			style1.setFont(font);// 设置字体风格
			style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
			cell.setCellStyle(style1);
		}
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				/*
				 * 
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					Object[] obj=(Object[]) list.get(i);;// 获取对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					if(headerStr.indexOf("提前还款列表")==0){
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
						DecimalFormat  df1 = new DecimalFormat("#,###.##");
						if (obj[4] != null) {
							cell = row.createCell(1);
							cell.setCellValue(obj[4].toString());
							cell.setCellStyle(style);
						} else {
							cell = row.createCell(1);
							cell.setCellStyle(style);
						} 
						if (obj[5] != null ) {
							cell = row.createCell(2);
							cell.setCellValue(obj[5].toString());
							cell.setCellStyle(style);
						} 
						else{
							cell = row.createCell(2);
							cell.setCellStyle(style);
						}
						if (null!=obj[7] && !obj[7].toString().equals("") ) {
							cell = row.createCell(3);
							cell.setCellValue(obj[7].toString());
							cell.setCellStyle(style);
						} else if(null!=obj[8] && !obj[8].toString().equals("")){
							cell = row.createCell(3);
							cell.setCellValue(obj[8].toString());
							cell.setCellStyle(style);
						}
						 else if(null!=obj[9] && !obj[9].toString().equals("")){
								cell = row.createCell(3);
								cell.setCellValue(obj[9].toString());
								cell.setCellStyle(style);
							}
						 else if(null!=obj[10] && !obj[10].toString().equals("")){
								cell = row.createCell(3);
								cell.setCellValue(obj[10].toString());
								cell.setCellStyle(style);
							}
						else{
							cell = row.createCell(3);
							cell.setCellStyle(style);
						}
						if (obj[1]!= null) {
							cell = row.createCell(4); // 创建第i+1行第1列
							cell.setCellValue(from.format(obj[1]));
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(4);
							cell.setCellStyle(style);
						}
						if (obj[2]!= null) {
							cell = row.createCell(5); // 创建第i+1行第1列
							cell.setCellValue(df1.format(((BigDecimal)obj[2]).doubleValue())+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(5);
							cell.setCellStyle(style);
						}
						if (obj[11]!= null) {
							cell = row.createCell(6); // 创建第i+1行第1列
							cell.setCellValue(df1.format(Double.valueOf(obj[11].toString()))+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(6);
							cell.setCellStyle(style);
						}
						if (obj[13]!= null) {
							cell = row.createCell(7); // 创建第i+1行第1列
							cell.setCellValue(df1.format(Double.valueOf(obj[13].toString()))+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(7);
							cell.setCellStyle(style);
						}
						if (obj[14]!= null) {
							cell = row.createCell(8); // 创建第i+1行第1列
							cell.setCellValue(df1.format(Double.valueOf(obj[14].toString()))+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(8);
							cell.setCellStyle(style);
						}
						if (obj[6] != null ) {
							cell = row.createCell(9);
							cell.setCellValue(obj[6].toString());
							cell.setCellStyle(style);
						} 
						else{
							cell = row.createCell(9);
							cell.setCellStyle(style);
						}
						if (obj[12]!= null) {
							cell = row.createCell(10); // 创建第i+1行第1列
							cell.setCellValue(df1.format(Double.valueOf(obj[12].toString()))+"元");
							cell.setCellStyle(style2); // 设置风格
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(style);
						}
						BigDecimal allMoney=new BigDecimal(0);
						if(null!=obj[2]){
							allMoney=allMoney.add(new BigDecimal(obj[2].toString()));
						}
						if(null!=obj[11]){
							allMoney=allMoney.add(new BigDecimal(obj[11].toString()));
						}
						if(null!=obj[12]){
							allMoney=allMoney.add(new BigDecimal(obj[12].toString()));
						}
						if(null!=obj[13]){
							allMoney=allMoney.add(new BigDecimal(obj[13].toString()));
						}
						if(null!=obj[14]){
							allMoney=allMoney.add(new BigDecimal(obj[14].toString()));
						}
						cell = row.createCell(11); // 创建第i+1行第1列
						cell.setCellValue(df1.format(allMoney.doubleValue())+"元");
						cell.setCellStyle(style2); // 设置风格
					}
				}
				
			}
			//将excel表单写到response中，在客户端下载
			writeResponse(headerStr,workbook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		
	}
}
