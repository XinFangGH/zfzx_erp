package com.zhiwei.core.web.servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

/**
 * 使用jasperReport做报表时的工具支持类.有两个用途,生成jasperPrint对象,和设置导出时的session
 */
public class MyJsperServelet extends ImageServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", -1);
		response.setHeader("Pragma", "no-cache");
		super.service(request, response);
	}

}