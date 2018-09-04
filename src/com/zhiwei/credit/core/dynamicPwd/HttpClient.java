package com.zhiwei.credit.core.dynamicPwd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpClient {
	private URI base_uri;
	private String encoding = "UTF8";
	
	public HttpClient(URI base_uri){
		this.base_uri = base_uri;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	protected String makeQueryString(Map<String, String> vars_dict) throws UnsupportedEncodingException {
		StringBuffer out = new StringBuffer();
		Iterator<String> i = vars_dict.keySet().iterator();
		boolean first = true;
		while (i.hasNext()) {
			if (first) {
				first = false;
			} else {
				out.append("&");
			}
			String name = i.next();
			String value = vars_dict.get(name);
			out.append(
					URLEncoder.encode(name, getEncoding())
					+ "=" 
					+ URLEncoder.encode(value, getEncoding()));
		}
		return out.toString();
	}
	public YooeResponse call_api(String cmd_name, Map<String, String> vars_dict) throws IOException,Exception  {
		String query_string = makeQueryString(vars_dict);
		URI full_uri = base_uri.resolve(URI.create(cmd_name+"/?"+query_string));
		URL url = full_uri.toURL();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int status_code = conn.getResponseCode();
		if (status_code != 200) {
			String msg = "Error:"+status_code;
			throw new Exception(msg);
		}
		
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		List<String> relines = new LinkedList<String>();
		String in;
		while ((in = buf.readLine()) != null) {
			String line = in.trim();
			if (!line.isEmpty()) relines.add(line);
		}
		
		return new YooeResponse(relines);
	}
}
