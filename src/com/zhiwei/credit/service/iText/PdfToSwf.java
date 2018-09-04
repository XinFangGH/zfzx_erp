package com.zhiwei.credit.service.iText;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jfree.util.Log;

/**
 * @description 通过SWFTools工具命令行将pdf文档转化为swf文件[flex]
 * @class PdfToSwf
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-6-8PM
 */
public class PdfToSwf {

	/**
	 * @description 将pdf文档转化为swf文件
	 * @param exeFilePath
	 *            exe可执行文件的路径
	 * @param inputFilePath
	 *            要转化的pdf文档的路径
	 * @param outputFilePath
	 *            pdf转化为swf文件保存的路径
	 * @return 成功:true
	 */
	public String start(String exeFilePath, String inputFilePath,
			String outputFilePath) {
		if (!new File(exeFilePath).exists()) { // exe可执行文件
			Log.debug("请安装SWFTools工具或者重新设置SWFTools工具的安装目录！");
			return "请安装SWFTools工具或者重新设置SWFTools工具的安装目录！";
		}
		if (!new File(inputFilePath).exists()) { // 判断是否存在pdf文档
			Log.debug("要转换的pdf文档不存在,请添加！");
			return "要转换的pdf文档不存在,请添加！";
		}
		try {
			String cmd = exeFilePath + " " + inputFilePath + " -O "
					+ outputFilePath + " -T 9";
			String text = null;// exe,bat文件名OR DOS命令
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			while ((text = in.readLine()) != null) {
				text += text; // 输出测试
			}

			BufferedReader errorOut = new BufferedReader(new InputStreamReader(
					proc.getErrorStream()));
			String errorMsg = "";
			while ((errorMsg = errorOut.readLine()) != null) {
				errorMsg += errorMsg;
			}
		} catch (IOException ioError) {
			ioError.printStackTrace();
			System.exit(0);
			Log.debug("异常信息！");
			return "异常信息！";
		}
		if (new File(outputFilePath).exists()) { // 判断pdf文档是否转化成功
			Log.debug("pdf转化为swf文件操作：true！");
			return "true";
		} else {
			Log.debug("pdf转化为swf文件操作：false！");
			return "false";
		}
	}
}
