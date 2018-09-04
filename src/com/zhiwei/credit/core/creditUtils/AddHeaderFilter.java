package com.zhiwei.credit.core.creditUtils;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("all")
public class AddHeaderFilter implements Filter {
	private  Map headers = new HashMap();

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			doFilter1((HttpServletRequest) req, (HttpServletResponse) res,
					chain);
		} else {
			chain.doFilter(req, res);
		}
	}

	public void doFilter1(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		for (Iterator it = headers.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			response.addHeader((String) entry.getKey(), (String) entry
					.getValue());
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		String aa = "aa";
		String bb = aa;
		String headersStr = config.getInitParameter("headers");
		String[] headersl = headersStr.split(",");
		for (int i = 0; i < headersl.length; i++) {
			String[] temp = headersl[i].split("=");
			this.headers.put(temp[0].trim(), temp[1].trim());
		}
	}

}