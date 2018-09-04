package com.zhiwei.credit.action.system;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.system.FileAttachService;

/**
 * @description 文件安全下载
 * @author YHZ
 * @datetime 2010-11-25PM
 * @company www.credit-software.com
 */
@SuppressWarnings("serial")
public class FileDownloadServlet extends HttpServlet {

	private FileAttachService fileAttachService = (FileAttachService) AppUtil
			.getBean("fileAttachService");

	/**
	 * 处理get请求
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String fileId = req.getParameter("fileId");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (StringUtils.isNotEmpty(fileId)) {
			FileAttach fileAttach = fileAttachService.get(new Long(fileId));
			String ext = fileAttach.getExt();

			if (ext.toLowerCase().endsWith("zip")) {
				resp.setContentType("application/x-zip-compressed");
			} else if (ext.toLowerCase().endsWith("rar")) {
				resp.setContentType("application/octet-stream");
			} else if (ext.toLowerCase().endsWith("doc")) {
				resp.setContentType("application/msword");
			} else if (ext.toLowerCase().endsWith("xls")
					|| ext.toLowerCase().endsWith("csv")) {
				resp.setContentType("application/ms-excel ");

			} else if (ext.toLowerCase().endsWith("pdf")) {
				resp.setContentType("application/pdf");
			} else {
				resp.setContentType("application/x-msdownload");
			}

			ServletOutputStream out = null;
			try {

				java.io.FileInputStream fileIn = new java.io.FileInputStream(
						getServletContext().getRealPath("/") + "/attachFiles/"
								+ fileAttach.getFilePath());

				resp.setHeader("Content-Disposition", "attachment;filename="
						+ URLEncoder.encode(fileAttach.getFileName(), "UTF-8"));

				out = resp.getOutputStream();

				byte[] buff = new byte[1024];
				int leng = fileIn.read(buff);
				while (leng > 0) {
					out.write(buff, 0, leng);
					leng = fileIn.read(buff);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 处理post请求
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
