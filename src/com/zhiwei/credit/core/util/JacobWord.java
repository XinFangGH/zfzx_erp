package com.zhiwei.credit.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hurong.core.util.AppUtil;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.zhiwei.credit.model.creditFlow.contract.Element;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;

public  class JacobWord {
	   private static JacobWord instance = new JacobWord();
	   
	   private Dispatch doc = null;
	   private Dispatch docSelection = null;
	   private Dispatch wrdDocs = null;
	   private ActiveXComponent wrdCom;
	   
	   //private Dispatch activeWindow = null;
	   //private String fileName;
	  /**
	    * 获取Word操作静态实例对象
	    * 
	    * @return 
	    */
	   public final static synchronized JacobWord getInstance()
	   {
		   /*if (instance == null)
	         instance = new JacobWord();*/
	      return instance;

	   }
	 /**
	    * 打开一个已存在的文档
	    * 
	    * @param docPath
	    */

	   public void openDocument(String docPath)
	   {
	      if (this.doc != null)
	      {
	         this.closeDocument();
	      }
	      doc = Dispatch.call(wrdDocs, "Open", docPath).toDispatch();
	      docSelection = Dispatch.get(wrdCom, "Selection").toDispatch();
	   }
	 /**
	    * 关闭当前word文档
	    * 
	    */
	   public void closeDocument()
	   {
	      if (doc != null)
	      {
	    	Dispatch.call(doc, "Save");
	         Dispatch.call(doc, "Close", new Variant(false));
	         doc = null;
	      }
	   }
	   /**
	    * 关闭全部应用
	    * 
	    */
	   public void close() {
		   if (wrdCom != null) {
			   ComThread.Release();
			   ComThread.RemoveObject(wrdCom);
			   if(wrdCom != null &&wrdCom.m_pDispatch!=0){
				   Dispatch.call(wrdCom, "Quit");
				   wrdCom = null;
			   }
		   }
		   docSelection = null;
		   wrdDocs = null;
	   }
	   /**
	    * 初始化Word对象
	    * 
	    * @return 是否初始化成功
	    */
	   public boolean initWordObj()
	   {
	      boolean retFlag = false;
	      ComThread.InitSTA();// 初始化com的线程，非常重要！！使用结束后要调用 realease方法
	      wrdCom = new ActiveXComponent("Word.Application");
	      try
	      {
	         // 返回wrdCom.Documents的Dispatch
	         wrdDocs = wrdCom.getProperty("Documents").toDispatch();
	         wrdCom.setProperty("Visible", new Variant(false));
	         retFlag = true;
	      }
	      catch (Exception e)
	      {
	         retFlag = false;
	         e.printStackTrace();
	      }
	      return retFlag;
	   }
	   /**
	    * 初始化Word对
	    * 
	    *
	    */
	   public void initWordObj(String url)
	   {
		  // 初始化com的线程，非常重要！！使用结束后要调用 realease方法
	      ComThread.InitSTA();
	      wrdCom = new ActiveXComponent("Word.Application");
	      try
	      {
	         // 返回wrdCom.Documents的Dispatch
	         wrdDocs = wrdCom.getProperty("Documents").toDispatch();
	         wrdCom.setProperty("Visible", new Variant(0));
	         doc = Dispatch.call(wrdDocs, "Open", url).toDispatch();
	         //this.close();
	      }
	      catch (Exception e)
	      {  
	         e.printStackTrace();
	      }
	      ComThread.Release();
	   }
	   /**
	    * 从选定内容或插入点开始查找文本
	    * 
	    * @param toFindText
	    *            要查找的文本
	    * @return boolean true-查找到并选中该文本，false-未查找到文本
	    */
	   public boolean find(String toFindText) {
		   if (toFindText == null || toFindText.equals(""))
			   return false;
		   // 从selection所在位置开始查询
		   Dispatch find = wrdCom.call(docSelection, "Find").toDispatch();
		   // 设置要查找的内容
		   Dispatch.put(find, "Text", toFindText);
		   // 向前查找
		   Dispatch.put(find, "Forward", "True");
		   // 设置格式
		   Dispatch.put(find, "Format", "True");
		   // 大小写匹配
		   Dispatch.put(find, "MatchCase", "True");
		   // 全字匹配
		   Dispatch.put(find, "MatchWholeWord", "True");
		   // 查找并选中
		   return Dispatch.call(find, "Execute").getBoolean();
	   }
	   /**
	    * 全局替换文本
	    * 
	    * @param docPath
	    *            查找文件路径
	    * @param toFindText
	    *            要查找的要素编码数组
	    * @param newText
	    *            要替换的值数组
	    */
	   public synchronized boolean replaceAllText(String docPath, String[] toFindText, String[] newText) {
		   this.initWordObj();
		   this.openDocument(docPath);
		   for(int i = 0 ; i < toFindText.length ; i ++){
			   moveStart(docSelection);
			   while (find(toFindText[i])) {
				   if(toFindText[i].equals("[#clause#]") && "".equals(newText[i])){
					   newText[i] = "" ;
					   Dispatch.put(docSelection, "Text", newText[i]);
					   Dispatch.call(docSelection, "MoveRight");
				   }else if(newText[i].indexOf("∏")>0&&newText[i].indexOf("＃")>0){
					   List<String[]> strArrList = new ArrayList<String[]>();
					   String[] arr = newText[i].split("＃");
					   for(String str:arr){
						   strArrList.add(str.split("∏"));
					   }
					   Dispatch.put(docSelection, "Text", "");
					   insertTable(strArrList);
					   Dispatch.call(docSelection, "MoveRight");
				   }else{
					   if("".equals(newText[i])){
						   newText[i] = " " ;
					   } 
					   if(newText[i].contains("</br>")){
						   String[] strArrTmp = newText[i].split("</br>");
						   for(String str : strArrTmp){
							   Dispatch.put(docSelection, "Text", str);
							   Dispatch.call(docSelection, "MoveRight");
							   Dispatch.call(docSelection, "TypeParagraph");  
						   }
					   }else{
						   Dispatch.put(docSelection, "Text", newText[i]);
					   }
					   Dispatch.call(docSelection, "MoveRight");
				   }				   
			   }
		   }
		   this.closeDocument();
		   this.close() ;
		   try {
			   Runtime.getRuntime().exec("taskkill /f /im WINWORD.exe");
			} catch (IOException e) {
				e.printStackTrace();
			} 
		   return true ;
	   }
	   	   
	    private Dispatch select(ActiveXComponent word){   
	        return word.getProperty("Selection").toDispatch();   
	    }  
	    private void moveStart(Dispatch selection){   
	        Dispatch.call(selection, "HomeKey",new Variant(6));   
	    }  
	    public void createNewDocument(String fileUrl) {
    		Dispatch documents = Dispatch.get(wrdCom, "Documents").toDispatch();
    		doc = Dispatch.call(documents, "Add").toDispatch();
	    	Dispatch.call(doc, "SaveAs", fileUrl);
	    }  
	    public void insertText(String textToInsert) {
	    	Dispatch selection = Dispatch.get(wrdCom, "Selection").toDispatch();
	        Dispatch.put(selection, "Text", textToInsert);
	    }
	    
	    /**  
	     * 打印当前word文档  
	     */  
	    public void printFile(String url) { 
	    	this.initWordObj();
	    	this.openDocument(url);
		    if (doc != null) {   
		    	Dispatch.call(doc, "PrintOut");   
		    } 
		    ComThread.Release();
		    this.closeDocument();
		    this.close();
	    } 
	   /**
	    * 全局替换文本
	    * 
	    * @param docPath
	    *            查找文件路径
	    * @param fileName
	    *            文件名称使用 : 文档名称(项目简称)
	    * @param obj
	    *            要替换的对象
	    */
	    public boolean replaceAllTextObj(String docPath,String fileName , Object obj) {
	    	File file = null; 
	    	String newWordUrl = "" , newFolderPath = "";
	    	file = new File(docPath);
	    	if(!file.exists()){
	    		return  false;
	    	}
			if(obj != null){
				if(!this.initWordObj()){
		    		return false ;
		    	}
				this.openDocument(docPath) ;
				try{
					Class temp = obj.getClass(); 
					Field[] fields = temp.getDeclaredFields(); 
					String text = "" ; int n = 0 ;
					for(int j = 0 ; j < fields.length ; j ++){
						String fieldvalue = fields[j].getName().toString().trim() ;
						Field field = temp.getDeclaredField(fieldvalue); 
						field.setAccessible(true); 
						Object object = field.get(obj) ; n ++ ;
						String textFiled[] = field.toString().split(" ");
						if(textFiled[0].equals("private")){
							text += fieldvalue + "@" ;
						}
						if(n > 3){
							String str[] =  text.split("@");
							Element element = new Element();
							element.setDepict(str[0]);
							element.setFieldValue(str[1]);
							element.setValue(str[2]);
							element.setCode(str[3]);
							Dispatch selection = select(wrdCom);
							moveStart(selection);
							while (find(element.getCode())) {
								Dispatch.call(selection, "HomeKey", new Variant(6));
								Dispatch.put(selection, "Text", element.getValue());
								Dispatch.call(docSelection, "MoveRight");
							}
							n = 0 ;
							text = "" ;
							text += object.toString() + "@";
							n ++ ;
						}else{
							text += object.toString() + "@";
						}
					}
				}catch(Exception e){
					this.closeDocument();
					this.close() ;
					e.printStackTrace() ;
					return false;
				}
				this.closeDocument();
				this.close() ;
			}else{
				return false;
			}
		    return true ;
	   }
	   /**
	    * 把word转换成html 
	    * @param filePath
	    *            查找文件路径
	    * @param fileName
	    *            实际文件名称
	    */
	    public boolean ChageFormat(String filePath ,String htmlPath){
	    	String FileFormat = filePath.substring(filePath.lastIndexOf(".")+1);
    	    if(FileFormat.equalsIgnoreCase("docx") || FileFormat.equalsIgnoreCase("doc")){
    	    	  this.initWordObj() ;
    	    	  //打开word文件
    	    	  try{
    	    		  doc = Dispatch.invoke(wrdDocs,"Open", Dispatch.Method, new Object[]{filePath,new Variant(false), new Variant(false)}, new int[1]).toDispatch(); 
        	    	  //作为htm格式保存文件
        	          Dispatch.invoke(doc,"SaveAs",Dispatch.Method, new Object[]{htmlPath,new Variant(8)}, new int[1]);
        	          //Dispatch.call(doc, "Close",new Variant(false));
        	          this.closeDocument();
    	    	  }catch(Exception e){
  					  this.close() ;
    	    		  e.printStackTrace() ;
    	    		  return false ;
    	    	  }
    	    	 
    	    }
    	    //this.close() ;
    	    return true ;
	    }
	    /**
	    * 读取word文档中编码 
	    * @param filePath
	    *            查找文件路径
	    *        fileName 文档的实际名称
	    * @exception 转换html文档错误，读取要素错误
	    */
	    public synchronized String[] readWordCode(String filePath ,String fileName){
	    	String htmlName = "" ,htmlPath = "" ,newFolderPath = "" ,tempString = "";
	    	String[] filed = null ;
	    	File file = null ;
	    	BufferedReader br  = null ;
	    	StringBuffer sbuffer = new StringBuffer();
	    	if(fileName.indexOf(".") == -1){
	    		htmlName = fileName + ".htm" ;
	    	}
	    	htmlName = fileName.substring(0, fileName.lastIndexOf("."))+ ".htm" ;
	    	newFolderPath = filePath.substring(0, filePath.lastIndexOf("\\"))+"\\testfile";
	    	file = new File(newFolderPath);
	    	if (!file.exists()) { 
	    		FileHelper.newFolder(newFolderPath);
	        }
	    	htmlPath = newFolderPath + "\\" +htmlName;
	    	file = new File(htmlPath) ;
	    	if(!file.exists()){
	    		try{
	    			if(ChageFormat(filePath,htmlPath)){
	    				Runtime.getRuntime().exec("taskkill /f /im WINWORD.exe");
			    	}else{
			    		filed[0] ="false";
			    		return filed;
			    	}
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	    	}
	    	try {
	    		br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk")) ;
    			int line = 1 , i = 0;
    			try {
					while ((tempString = br.readLine()) != null){
						sbuffer.append(tempString);
						line ++ ;
					}
					br.close() ;
					tempString = HtmlToText.Html2Text(sbuffer.toString()).replaceAll("&nbsp;", "") ;
					String filedStrAll = "",filedStr = "" ,substr="";
					filedStrAll = tempString.substring(tempString.indexOf("[#"), tempString.lastIndexOf("#]")+2) ;
					StringBuffer sbuf = new StringBuffer(filedStrAll);
					int len = tempString.split("#]").length ;
					filed = new String[len] ;
					for(int j = 0 ; j < len-1 ; j++){
						filedStr = sbuf.substring(sbuf.indexOf("[#")+2, sbuf.indexOf("#]")) ;
						//substr = tempString.substring(tempString.indexOf("[#") ,tempString.indexOf("#]")+2);
						sbuf.delete(0, sbuf.indexOf("#]")+2);
						filed[j] = filedStr ; i = j+1 ;
					}
					if(filed[0] != null){
						filed[i] = "true" ;
					}else{
						filed[i] = "false" ;
					}
					filedStrAll = null ;
					filedStr = null ;
					substr = null;
				} catch (IOException e) {
					e.printStackTrace();
					filed[0] ="false";
		    		return filed;
				}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		filed[0] ="false";
	    		return filed;
	    	}finally{
	    		if(null != br){
	    			try {
						br.close();
						sbuffer = null ;
						tempString = null ;
						file = null ;
						htmlPath = null ;
						htmlName = null ;
					} catch (IOException e) {
						e.printStackTrace();
						filed[0] ="false";
			    		return filed;
					}
	    		}
	    	}
	    	return filed ; 
	    }
	    public static void comThreadClose(){
	    	 ComThread.Release();
	    }
	    
	    public void insertTable(List<String[]> strArrArr) {
	    	int row = strArrArr.size();
	    	int column = strArrArr.get(0).length;
	        Dispatch selection = docSelection;//Dispatch.get(wrdCom, "Selection").toDispatch(); // 输入内容需要的对象  
//	        Dispatch.call(selection, "MoveDown"); // 游标往下一行  
	        // 建立表格  
	        Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();  
	        // int count = Dispatch.get(tables,  
	        // "Count").changeType(Variant.VariantInt).getInt(); // document中的表格数量  
	        // Dispatch table = Dispatch.call(tables, "Item", new Variant(  
	        // 1)).toDispatch();//文档中第一个表格  
	        Dispatch range = Dispatch.get(selection, "Range").toDispatch();// /当前光标位置或者选中的区域  
	        Dispatch newTable = Dispatch.call(tables, "Add", range,  
	                new Variant(row), new Variant(column), new Variant(1))  
	                .toDispatch(); // 设置row,column,表格外框宽度  
	        Dispatch cols = Dispatch.get(newTable, "Columns").toDispatch(); // 此表的所有列，  
	        int colCount = Dispatch.get(cols, "Count").getInt();// 一共有多少列 实际上这个数==column  
	        for (int i = 1; i <= colCount; i++) { // 循环取出每一列  
	            Dispatch col = Dispatch.call(cols, "Item", new Variant(i))  
	                    .toDispatch();  
	            Dispatch cells = Dispatch.get(col, "Cells").toDispatch();// 当前列中单元格  
	            int cellCount = Dispatch.get(cells, "Count").getInt();// 当前列中单元格数 实际上这个数等于row  
	            for (int j = 1; j <=cellCount; j++) {// 每一列中的单元格数  
	                // Dispatch cell = Dispatch.call(cells, "Item", new  
	                // Variant(j)).toDispatch(); //当前单元格  
	                // Dispatch cell = Dispatch.call(newTable, "Cell", new  
	                // Variant(j) , new Variant(i) ).toDispatch(); //取单元格的另一种方法  
	                // Dispatch.call(cell, "Select");//选中当前单元格  
	                // Dispatch.put(selection, "Text",  
	                // "第"+j+"行，第"+i+"列");//往选中的区域中填值，也就是往当前单元格填值  
	                putTxtToCell(newTable, j, i, strArrArr.get(j-1)[i-1]);// 与上面四句的作用相同  
	            }  
	        }  
	    }
	    
	    public void putTxtToCell(Dispatch table, int cellRowIdx, int cellColIdx,  
	            String txt) {  
	        Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),  
	                new Variant(cellColIdx)).toDispatch();  
	        Dispatch.call(cell, "Select");  
	        Dispatch selection = Dispatch.get(wrdCom, "Selection").toDispatch(); // 输入内容需要的对象  
	        Dispatch.put(selection, "Text", txt);  
	    } 
	    
	    
	    /**
	     * 创建表格.
	     * @param numRows 行数
	     * @param numCols 列数
	     * @param autoFormat 默认格式
	     * @return 表格对象
	     */
	    public synchronized Dispatch createTable(
	            final int numRows,
	            final int numCols,
	            final int autoFormat) {
	 
	        int index = 1;
	        while (true) {
	            try {
	                Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
	                Dispatch range = Dispatch.get(docSelection, "Range").toDispatch();
	                Dispatch newTable = Dispatch.call(tables, "Add", range,
	                        new Variant(numRows),
	                        new Variant(numCols),new Variant(1)).toDispatch();
	 
	                Dispatch.call(docSelection, "MoveRight");
	         //     Dispatch.call(newTable, "AutoFormat", new Variant(autoFormat));
	                return newTable;
	            } catch (ComFailException e) {
	                if (index++ >= 10) {
	                    throw e;
	                } else {
	                    continue;
	                }
	            }
	        }
	    }
	    
	    /**
	     * 在指定的单元格里填写数据.
	     * @param table 表格
	     * @param cellRowIdx 行号
	     * @param cellColIdx 列号
	     * @param txt 文字
	     * @param style 样式
	     */
	    public synchronized void putTableCell(
	            final Dispatch table,
	            final int cellRowIdx,
	            final int cellColIdx,
	            final String txt,
	            final String style) {
	 
	        Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),
	                new Variant(cellColIdx)).toDispatch();
	 
	        Dispatch.call(cell, "Select");
	        Dispatch.put(docSelection, "Text", txt);
	    //    Dispatch.put(this.docSelection, "Style", getOutlineStyle(style));
	    }
	    
	    
	    /**
	     * 获取对应名称的Style对象.
	     * @param style Style名称.
	     * @return Style对象
	     */
	    public synchronized Variant getOutlineStyle(
	            final String style) {
	 
	        int index = 1;
	        while (true) {
	            try {
	                return Dispatch.call(
	                        Dispatch.get(this.doc, "Styles").toDispatch(),
	                        "Item", new Variant(style));
	            } catch (ComFailException e) {
	                if (index++ >= 10) {
	                    throw e;
	                } else {
	                    continue;
	                }
	            }
	        }
	    }
//	    public static void main(String[] args) {
//	    	JacobWord.getInstance().openDocument("E:\\1 借款协议.doc");
//	    	JacobWord.getInstance().replaceAllTextObj("E:\\gaoTest.doc",null);
//	    	String d = new String(); d.trim();
//	    	
//	    }
	    
	    public  boolean replaceAlltable(String docPath, List<BpFundIntent> bpfundlist) {
	    	
	    	 this.initWordObj();
			   this.openDocument(docPath);
			   moveStart(docSelection);

			   if(find("[#投资人还款计划#]")){
				   drawBpFundIntentTable(bpfundlist);
			   }


	    	
			   this.closeDocument();
			   this.close() ;
			   try {
				   Runtime.getRuntime().exec("taskkill /f /im WINWORD.exe");
			   } catch (IOException e) {
				   e.printStackTrace();
			   } 
			   return true;
	    	
	    	
	    }   
	  
		   public  void drawBpFundIntentTable(List<BpFundIntent> esList){
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			   int index = 5;
			   if(esList!=null){
				   index = esList.size()+1;
			   }
			   Dispatch createTable = createTable(index, 3, 0);
			   putTableCell(createTable, 1, 1, "还款日", null);
			   putTableCell(createTable, 1, 2, "还款金额（单位：人民币）",  null);
			   putTableCell(createTable, 1, 3, "还款类型", null);
			   
			   if(esList!=null&&esList.size()>0){
				   for(int i = 0 ; i<esList.size() ; i ++){
					   BpFundIntent es = esList.get(i);
					   putTableCell(createTable, i+2, 1,sdf.format( es.getIntentDate()).toString() , null);
					   putTableCell(createTable, i+2, 2,es.getIncomeMoney().toString(),  null);
					   putTableCell(createTable, i+2, 3, es.getFundType(),null);
				   }
			   }
			   
			   Dispatch columns = Dispatch.get(createTable, "Columns").toDispatch(); 
			   int[] ColWidth = {120,160,135};
			   for(int i=0;i<3;i++){
				   Dispatch column = Dispatch.call(columns, "Item",new Variant(i+1)).toDispatch(); 
				   Dispatch.put(column, "Width", new Variant(ColWidth[i]));
			   }
		/*	   int index = 5;
			   if(esList!=null&&esList.size()>4){
				   index = esList.size()+1;
			   }
			   Dispatch createTable = createTable(index, 6, 0);
			   putTableCell(createTable, 1, 2, "股东名称", null);
			   putTableCell(createTable, 1, 3, "出资额",  null);
			   putTableCell(createTable, 1, 4, "出资比例", null);
			   putTableCell(createTable, 1, 5, "出资方式", null);
			   putTableCell(createTable, 1, 6, "是否到位", null);
			   
			   if(esList!=null&&esList.size()>0){
				   for(int i = 0 ; i<esList.size() ; i ++){
					   BpFundIntent es = esList.get(i);
					   putTableCell(createTable, i+2, 1, i+1+"" , null);
					   putTableCell(createTable, i+2, 2, es.getAccountNumber() , null);
					   putTableCell(createTable, i+2, 3, es.getAccountNumber(),  null);
					   putTableCell(createTable, i+2, 4, es.getAccountNumber(),  null);
					   putTableCell(createTable, i+2, 5, es.getAccountNumber(),  null);
					   putTableCell(createTable, i+2, 6, "", null);
				   }
			   }
			   
			   Dispatch columns = Dispatch.get(createTable, "Columns").toDispatch(); 
			   int[] ColWidth = {40,130,85,85,70,70};
			   for(int i=0;i<6;i++){
				   Dispatch column = Dispatch.call(columns, "Item",new Variant(i+1)).toDispatch(); 
				   Dispatch.put(column, "Width", new Variant(ColWidth[i]));
			   }*/
		   }
		   public synchronized void drawInvestPersonTable(List<InvestPersonInfo> plist){
			   int index=0;
			   if(null!=plist && plist.size()>0){
				   index= plist.size()+2;
			   }
			   Dispatch createTable = createTable(index, 5, 0);
			   putTableCell(createTable, 1, 1, "序号", null);
			   putTableCell(createTable, 1, 2, "资金来源", null);
			   putTableCell(createTable, 1, 3, "投资方",  null);
			   putTableCell(createTable, 1, 4, "投资金额", null);
			   putTableCell(createTable, 1, 5, "应收本息", null);
			   
			   if(plist!=null && plist.size()>0){
				   BigDecimal allMoney=new BigDecimal(0);
				   BigDecimal allInvestMoney=new BigDecimal(0);
				   for(int i = 0 ; i<plist.size() ; i ++){
					   InvestPersonInfo  p = plist.get(i);
					  
					   allMoney=allMoney.add(p.getPrincipalAndInterest());
					   allInvestMoney=allInvestMoney.add(p.getInvestMoney());
					   putTableCell(createTable, i+2, 1, i+1+"" , null);
					   putTableCell(createTable, i+2, 2,"个人" , null);
					   putTableCell(createTable, i+2, 3, p.getInvestPersonName(),  null);
					   putTableCell(createTable, i+2, 4, p.getInvestMoney()+"元", null);
					   putTableCell(createTable, i+2, 5, p.getPrincipalAndInterest()+"元", null);
					   
				   }
				   putTableCell(createTable, plist.size()+2, 1, plist.size()+1+"" , null);
				   putTableCell(createTable, plist.size()+2, 2,"总计" , null);
				   putTableCell(createTable, plist.size()+2, 3, "",  null);
				   putTableCell(createTable, plist.size()+2, 4, allInvestMoney+"元", null);
				   putTableCell(createTable, plist.size()+2, 5, allMoney+"元", null);
			   }
			   
			   Dispatch columns = Dispatch.get(createTable, "Columns").toDispatch(); 
			   int[] ColWidth = {40,100,100,100,100};
			   for(int i=0;i<5;i++){
				   Dispatch column = Dispatch.call(columns, "Item",new Variant(i+1)).toDispatch(); 
				   Dispatch.put(column, "Width", new Variant(ColWidth[i]));
			   }
		 
		   }
		   public synchronized void drawJkrFundIntentTable(List<BpFundIntent> jkrFundList){
			   SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			   int index=0;
			   if(null!=jkrFundList && jkrFundList.size()>0){
				   index= jkrFundList.size()+1;
			   }
			 
			   Dispatch createTable = createTable(index, 6, 0);
			   putTableCell(createTable, 1, 1, "序号", null);
			   putTableCell(createTable, 1, 2, "期数", null);
			   putTableCell(createTable, 1, 3, "资金类型",  null);
			   putTableCell(createTable, 1, 4, "计划收入金额", null);
			   putTableCell(createTable, 1, 5, "计划支出金额", null);
			   putTableCell(createTable, 1, 6, "计划到账日期", null);
			
			   if(jkrFundList!=null && jkrFundList.size()>0){
				   for(int i = 0 ; i<jkrFundList.size() ; i ++){
					   BpFundIntent  f = jkrFundList.get(i);
					   putTableCell(createTable, i+2, 1, i+1+"" , null);
					   putTableCell(createTable, i+2, 2,"第"+f.getPayintentPeriod()+"期" , null);
					   putTableCell(createTable, i+2, 3, f.getFundTypeName(),  null);
					   putTableCell(createTable, i+2, 4, f.getIncomeMoney()+"元", null);
					   putTableCell(createTable, i+2, 5, f.getPayMoney()+"元", null);
					   putTableCell(createTable, i+2, 6,(null!=f.getIntentDate()?sd.format(f.getIntentDate()):""), null);
					 
				   }
			   }
			   
			   Dispatch columns = Dispatch.get(createTable, "Columns").toDispatch(); 
			   int[] ColWidth = {40,60,100,80,80,80};
			   for(int i=0;i<6;i++){
				   Dispatch column = Dispatch.call(columns, "Item",new Variant(i+1)).toDispatch(); 
				   Dispatch.put(column, "Width", new Variant(ColWidth[i]));
			   }
		 
		   }
		   public synchronized boolean replaceAllText(String docPath, String[] toFindText, String[] newText,List<BpFundIntent> bpfundlist,List<InvestPersonInfo> plist,List<BpFundIntent> jkrFundList) {
			   this.initWordObj();
			   this.openDocument(docPath);
			   
			   moveStart(docSelection);
			   if(null!=jkrFundList && jkrFundList.size()>0){
				   if(find("[#借款人款项计划表#]")){
					   drawJkrFundIntentTable(jkrFundList);
				   }
			   }
			   moveStart(docSelection);
			   if(null!=bpfundlist && bpfundlist.size()>0){
				   if(find("[#投资人还款计划#]")){
					   drawBpFundIntentTable(bpfundlist);
				   }
			   }
			   moveStart(docSelection);
			   if(null!=plist && plist.size()>0){
				   if(find("[#投资人列表#]")){
					   drawInvestPersonTable(plist);
				   }
			   }
			   
			   for(int i = 0 ; i < toFindText.length ; i ++){
				   if(null==newText[i]){
					   newText[i]="";
					   
				   }
				   moveStart(docSelection);
				   while (find(toFindText[i])) {
					   if(toFindText[i].equals("[#clause#]") && "".equals(newText[i])){
						   newText[i] = "" ;
						   Dispatch.put(docSelection, "Text", newText[i]);
						   Dispatch.call(docSelection, "MoveRight");
					   }else if(newText[i].indexOf("∏")>0&&newText[i].indexOf("＃")>0){
						   List<String[]> strArrList = new ArrayList<String[]>();
						   String[] arr = newText[i].split("＃");
						   for(String str:arr){
							   strArrList.add(str.split("∏"));
						   }
						   Dispatch.put(docSelection, "Text", "");
						   insertTable(strArrList);
						   Dispatch.call(docSelection, "MoveRight");
					   }else{
						   if("".equals(newText[i])){
							   newText[i] = " " ;
						   } 
						   if(newText[i].contains("</br>")){
							   String[] strArrTmp = newText[i].split("</br>");
							   for(String str : strArrTmp){
								   Dispatch.put(docSelection, "Text", str);
								   Dispatch.call(docSelection, "MoveRight");
								   Dispatch.call(docSelection, "TypeParagraph");  
							   }
						   }else{
							   Dispatch.put(docSelection, "Text", newText[i]);
						   }
						   Dispatch.call(docSelection, "MoveRight");
					   }				   
				   }
			   }
			  
			   this.closeDocument();
			   this.close() ;
			   try {
				   Runtime.getRuntime().exec("taskkill /f /im WINWORD.exe");
				} catch (IOException e) {
					e.printStackTrace();
				} 
			   return true ;
		   }
		  
}