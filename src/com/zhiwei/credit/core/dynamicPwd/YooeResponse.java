package com.zhiwei.credit.core.dynamicPwd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class YooeResponse {
	protected static final String RET_CMD_OK = "OK";
	protected static final String RET_CMD_ERR = "ERR";
	
	protected static final String[] RET_CMD = { RET_CMD_OK, RET_CMD_ERR };
	
	private String ret_cmd;
	private HashMap<String, String> vars_dict;
	private List<String> response;

	private void parse_response() throws Exception {
		if (response.isEmpty()) {
			throw new Exception();
		}
		
		Iterator<String> i = response.iterator();

		ret_cmd = i.next();
		if ( !Arrays.asList(RET_CMD).contains(ret_cmd) ) {
			throw new Exception();
		}

		vars_dict = new HashMap<String, String>();
		while (i.hasNext()) {
			String[] reline = i.next().split(":", 2);
			if (reline.length < 2) {
				vars_dict.put(reline[0].trim(), "");
			} else {
				vars_dict.put(reline[0].trim(), reline[1].trim());
			}
		}
	}

	public String getRetCmd() throws Exception {
		if (ret_cmd == null) {
			parse_response();
		}
		return ret_cmd;
	}

	public HashMap<String, String> getVarsDict() throws Exception {
		if (ret_cmd == null) {
			parse_response();
		}
		return vars_dict;
	}

	public YooeResponse(List<String> response) {
		super();
		this.response = response;
	}
}
