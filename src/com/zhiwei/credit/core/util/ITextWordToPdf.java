package com.zhiwei.credit.core.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;


public class ITextWordToPdf {

	public static void main(String[] args) {

		//创建一个文档对象

		Rectangle pSize=new Rectangle(144,90);
		
		pSize.setBackgroundColor(BaseColor.GREEN); 
		
		Document doc=new Document(pSize ,5 ,5 ,5,5);

		try {

		//定义输出位置并把文档对象装入输出对象中

		PdfWriter.getInstance(doc, new FileOutputStream("d:/hello.pdf"));

		//打开文档对象

		doc.open();

		// 加入文字“Hello World”

		doc.add(new Paragraph("HelloWorld"));

		// 关闭文档对象，释放资源

		doc.close(); 

		} catch (FileNotFoundException e) {

		e.printStackTrace();

		} catch (DocumentException e) {

		e.printStackTrace();

		}

		}
	

}
