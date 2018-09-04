package com.zhiwei.credit.core.util;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WordtoPdfUtil {
	static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。
	static final int wdFormatPDF = 17;// PDF 格式
	
	/*private String wordname;
	private String pdfname;
	
	public WordtoPdfUtil(String wordname,String pdfname){
		this.wordname =wordname;
		this.pdfname=pdfname;
	}*/
	
	@SuppressWarnings("deprecation")
	public static void  wordToPdf(String wordname,String pdfname){
	  // wordname = "E:\\file\\yefbv.docx";
		//ComThread.InitSTA();
		int index1 = wordname.lastIndexOf("\\");
		pdfname = wordname.substring(0, index1);
		String arr[] = wordname.split("\\\\");
	   	int index = arr[arr.length-1].lastIndexOf(".");
	   	
	   	String pdfname1 = arr[arr.length-1].substring(0, index);
	   	pdfname =pdfname +"\\"+pdfname1 + ".pdf";
	    System.out.println("启动Word====="  +pdfname);
	    long start = System.currentTimeMillis();
	    ActiveXComponent app = null;
	    Dispatch docs=null;
	    try {
	    	System.runFinalizersOnExit(true);
	    	 app = new ActiveXComponent("Word.Application");
	        app.setProperty("Visible", false);

	         docs = app.getProperty("Documents").toDispatch();
	        System.out.println("打开文档" + wordname);
	        Dispatch doc = Dispatch.call(docs,//
	                "Open", //
	                wordname,// FileName
	                false,// ConfirmConversions
	                true // ReadOnly
	                ).toDispatch();

	        System.out.println("转换文档到PDF" + pdfname);
	        File tofile = new File(pdfname);
	        if (tofile.exists()) {
	            tofile.delete();
	        }
	        Dispatch.call(doc,//
	                "SaveAs", //
	                pdfname, // FileName
	                wdFormatPDF);

	        Dispatch.call(doc, "Close", false);
	        long end = System.currentTimeMillis();
	        
	        System.out.println("转换完成..用时：" + (end - start) + "ms.");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        System.out.println("========Error:文档转换失败：" + e.getMessage());
	    } finally {
	        if (app != null){
	        	app.invoke("Quit", wdDoNotSaveChanges);
	        }
	        if(docs != null){
	        	 ComThread.Release();
	 	        ComThread.RemoveObject(docs);
	        }	
	       
	    }
		
	}
	
/*	public void run() { 
		 // wordname = "E:\\file\\yefbv.docx";
		//ComThread.InitSTA();
		int index1 = wordname.lastIndexOf("\\");
		pdfname = wordname.substring(0, index1);
		String arr[] = wordname.split("\\\\");
	   	int index = arr[arr.length-1].lastIndexOf(".");
	   	
	   	String pdfname1 = arr[arr.length-1].substring(0, index);
	   	pdfname =pdfname +"\\"+pdfname1 + ".pdf";
	    System.out.println("启动Word"  +pdfname);
	    long start = System.currentTimeMillis();
	    ActiveXComponent app = null;
	    Dispatch docs=null;
	    try {
	    	//System.runFinalizersOnExit(true);
	    	 app = new ActiveXComponent("Word.Application");
	        app.setProperty("Visible", false);

	         docs = app.getProperty("Documents").toDispatch();
	        System.out.println("打开文档" + wordname);
	        Dispatch doc = Dispatch.call(docs,//
	                "Open", //
	                wordname,// FileName
	                false,// ConfirmConversions
	                true // ReadOnly
	                ).toDispatch();

	        System.out.println("转换文档到PDF" + pdfname);
	        File tofile = new File(pdfname);
	        if (tofile.exists()) {
	            tofile.delete();
	        }
	        Dispatch.call(doc,//
	                "SaveAs", //
	                pdfname, // FileName
	                wdFormatPDF);

	        Dispatch.call(doc, "Close", false);
	        long end = System.currentTimeMillis();
	        
	        System.out.println("转换完成..用时：" + (end - start) + "ms.");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        System.out.println("========Error:文档转换失败：" + e.getMessage());
	    } finally {
	        if (app != null){
	        	app.invoke("Quit", wdDoNotSaveChanges);
	        }
	        if(docs != null){
	        	 ComThread.Release();
	 	        ComThread.RemoveObject(docs);
	        }	
	       
	    }
		
	}*/
	public static void main(String[] args) {
		wordToPdf("E:\\file\\yefbv.docx","");
		//createPDF("E:\\file\\yefbv.docx","E:\\file\\yefbv.docx");
		
	}
	


	@SuppressWarnings("deprecation")
	public static void createPDF(String path,String newPath){
		File file = null;
		file = new File(path);
		
		String ph = path.substring(0, path.lastIndexOf("."))+".pdf";
		File pdf = new File(ph);
		System.runFinalizersOnExit(true);
		ActiveXComponent objWord = new ActiveXComponent("Word.Application");
		Dispatch wordObject = objWord.getObject();
		
		Dispatch.put(wordObject,"Visible",new Variant(false));
		Dispatch documents = objWord.getProperty("Documents").toDispatch();
		Dispatch doc = Dispatch.invoke(documents, "Open", Dispatch.Method, new Object[]{file.getAbsolutePath(),new Variant(false),new Variant(true)}, new int[1]).toDispatch();

		
		Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { pdf.getAbsolutePath(), new Variant(17) }, new int[1]);    
		Variant ff = new Variant(false);    
		Dispatch.call(doc, "Close", ff);
		
		objWord.invoke("Quit", new Variant(){});
		if(objWord!=null){
			ComThread.Release();
			ComThread.RemoveObject(wordObject);
		}
	}
}
