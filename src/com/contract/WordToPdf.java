package com.contract;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;


/**
 * 将word转换成pdf
 * 注意:word的中文字体尽量用宋体
 * @author zhangcb
 */
public class WordToPdf {
 
    protected static final Logger logger = LoggerFactory.getLogger(WordToPdf.class);
     
    /**
     * 将word文档转换成pdf
     * @param filepath word文件路径
     * @param outpath  pdf输出路径
     * @throws Exception
     */
    public static boolean wordConverterToPdf(String filepath,String outpath){
    	InputStream source=null;
    	OutputStream target=null;
    	boolean flag=true;
		try {
			source = new FileInputStream(filepath);
		    target = new FileOutputStream(outpath);
		     
		    PdfOptions options = PdfOptions.create();
		        
			XWPFDocument doc = new XWPFDocument(source);
//			System.out.println("开始转换!!!");
			PdfConverter.getInstance().convert(doc,target,options);
//			System.out.println("转换成功!!!");
		} catch (IOException e) {
			flag=false;
//			System.out.println("转换失败!!!");
			logger.error("word转pdf出错：" + e.getMessage());
		} finally{
			try {
				if(null!=source){
					source.close();
				}
				if(null!=target){
					target.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
    }

    /**
	 *jpg转pdf
	 *
	 * @auther: XinFang
	 * @date: 2018/8/24 10:43
	 */
    public static boolean jpg2pdf(List<String> list, String outPath){
		Logger logger = LoggerFactory.getLogger(WordToPdf.class);
		boolean flag = true;
		try {
		PDDocument pdDocument = new PDDocument();
			for (String s : list) {
				BufferedImage image = ImageIO.read(new URL(s));
				PDPage pdPage = new PDPage(new PDRectangle(595, 842));
				pdDocument.addPage(pdPage);
				PDImageXObject pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
				PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
				contentStream.drawImage(pdImageXObject, 0, 0, 595, 842);
				contentStream.close();
			}
		pdDocument.save(outPath);
		pdDocument.close();
		} catch (IOException e) {
			flag = false;
			logger.error("jpg图片转pdf错误:{}",e);
			e.printStackTrace();
		}
		return flag;
	}

}