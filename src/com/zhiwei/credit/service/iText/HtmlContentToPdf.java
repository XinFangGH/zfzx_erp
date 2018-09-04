package com.zhiwei.credit.service.iText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.util.Log;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

/**
 * @description 将html格式的信息通过iText转化为pdf文档，解决中文乱码问题
 * @jar 必须导入jar包：core-renderer-minimal.jar,core-renderer.jar,iText-2.0.8.jar
 * @font 支持中文的字体文件[win7系统中对应:C:/Windows/Fonts/simsun.ttc]，同时添加style样式body
 *       {font-family: SimSun;}
 * @class HtmlContentToPdf
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-6-8PM
 * 
 */
public class HtmlContentToPdf {

	/**
	 * @description 将html对应的文本信息通过iText转化为pdf文档
	 * @param content
	 *            html文本信息，去掉body标签
	 * @param fontPath
	 *            字体文件路径，完整的路径包含文件的后缀名
	 * @param outPdfPath
	 *            pdf文件输出路径，完整的路径包含文件的后缀名
	 * @return 转化成功:true，否则：msg
	 */
	public String contentToPdf(String content, String fontPath,
			String outPdfPath) {
		if (!new File(fontPath).exists()) {
			Log.debug("html内容转化为pdf文档操作：转化字体font文件不存在请添加！");
			return "转化字体font文件不存在请添加！";
		}
		StringBuffer html = new StringBuffer();
		String headerContent = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		headerContent += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		headerContent += "<head>";
		headerContent += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
		headerContent += "<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>";
		headerContent += "</head><body>";
		html.append(headerContent);
		html.append(content);
		html.append("</body></html>");
//		String htmlStr = new HtmlValidate().fiterHtmlTag(html.toString());
		try {
			ITextRenderer renderer = new ITextRenderer();

			// 解决中文乱码问题,目前只发现这个simsun.ttc字体文件支持中文
			ITextFontResolver resolver = renderer.getFontResolver();
			resolver.addFont(fontPath, BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(html.toString());
//			renderer.setDocumentFromString(htmlStr);
			// 解决乱码问题

			// 解决图片的相对路径问题
			// renderer.getSharedContext().setBaseURL("file:/F:/teste/html/");
			// renderer.getSharedContext().setBaseURL("file:/" +
			// application.getRealPath("UserFiles/Image") + "/");
			// end

			OutputStream os = new FileOutputStream(outPdfPath);
			renderer.layout();
			renderer.createPDF(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.debug("pdf[" + outPdfPath + "]文档正在使用，请稍后再操作！");
			return "pdf[" + outPdfPath + "]文档正在使用，请稍后再操作！";
		} catch (IOException e) {
			e.printStackTrace();
			return "IO异常！";
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
			Log.debug("创建PDF文档出现异常！");
			return "创建PDF文档出现异常！";
		}
		if (new File(outPdfPath).exists()) {
			Log.debug("html内容转化为pdf文档操作：true！");
			return "true";
		} else {
			Log.debug("html内容转化为pdf文档操作：false！");
			return "false";
		}
	}
}
