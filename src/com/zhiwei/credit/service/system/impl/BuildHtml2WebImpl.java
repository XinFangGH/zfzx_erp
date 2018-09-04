package com.zhiwei.credit.service.system.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;



import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;

import com.hurong.credit.model.system.HtmlDataMapVO;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.service.p2p.P2pFriendlinkService;
import com.zhiwei.credit.service.system.BuildHtml2Web;
import com.zhiwei.credit.util.JsonUtils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

public class BuildHtml2WebImpl implements BuildHtml2Web {
    @Resource
	private P2pFriendlinkService p2pFriendlinkService;
	@Override
	public void buildHtml(String format, String url, String service,
			String method, Map<String, Object> data) {
		 WebClient client = WebClient.create(url);
		  client.path(service+"/"+method).accept(format);
		  HtmlDataMapVO dataVo=new HtmlDataMapVO();
		  dataVo.setData(data);
		 // HtmlDataMapVO response = client.post(dataVo, HtmlDataMapVO.class);	
	}
	
	
	// 获取公共数据
	@Override
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		
		commonData.put("erpPath",AppUtil.getSystemUrl());
		commonData.put("attachFiles",Constants.ATTACH_FILES);
		commonData.put("friendLinkList", JsonUtils.getJson(p2pFriendlinkService.getAll(),JsonUtils.TYPE_LIST));
	
		return commonData;
	}

}
