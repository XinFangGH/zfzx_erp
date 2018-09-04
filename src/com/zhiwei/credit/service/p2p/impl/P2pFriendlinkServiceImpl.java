package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.util.Assert;

import com.hurong.credit.config.HtmlConfig;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.p2p.P2pFriendlinkDao;
import com.zhiwei.credit.model.p2p.P2pFriendlink;
import com.zhiwei.credit.service.p2p.P2pFriendlinkService;
import com.zhiwei.credit.service.system.BuildHtml2Web;
import com.zhiwei.credit.service.system.ResultWebPmsService;
import com.zhiwei.credit.util.JsonUtils;

/**
 * 
 * @author 
 *
 */
public class P2pFriendlinkServiceImpl extends BaseServiceImpl<P2pFriendlink> implements P2pFriendlinkService{
	@SuppressWarnings("unused")
	private P2pFriendlinkDao dao;
	@Resource
	private ResultWebPmsService resultWebPmsService;
	@Resource
	private BuildHtml2Web buildHtml2WebService;
	
	
	public P2pFriendlink save(P2pFriendlink link){
		P2pFriendlink p2pFriendlink = new P2pFriendlink();
		p2pFriendlink=dao.save(link);
		return p2pFriendlink;
	}
	public P2pFriendlinkServiceImpl(P2pFriendlinkDao dao) {
		super(dao);
		this.dao=dao;
	}

	
	public List<P2pFriendlink>  listOrderBy(){
		return  dao.getOrderby();
	}
	@Override
	public P2pFriendlink save(P2pFriendlink link,String path) {
		dao.save(link);
		System.out.println(path);
//		if(link.getLinkType()!=null&&link.getLinkType()==2){
//			createHtmlLogo(path,false);
//		}else if(link.getLinkType()!=null&&link.getLinkType()==1){
//			createHtmlLogo(path,true);
//		}
		return null;
	}
	public void createHtmlLogo(String path,boolean bool){
		HtmlConfig htmlConfig = null;
		String key = null;
		if(bool){
			key = "linkl";
		}else{
			key = "linkt";
		}
		List<P2pFriendlink> list = dao.getLinks(bool);
		htmlConfig = resultWebPmsService.findHtmlCon(key);
		String templateFilePath = htmlConfig.getTemplateFilePath();
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		Map<String, Object> data = buildHtml2WebService.getCommonData();
		data.put(key, JsonUtils.getJson(list, JsonUtils.TYPE_OBJ));
		data.put("htmlFilePath", htmlFilePath);
		data.put("paths", path);
		data.put("templateFilePath", templateFilePath);
		buildHtml2WebService.buildHtml(Constants.BUILDHTML_FORMAT_JSON,AppUtil.getWebServiceUrlRs(), "htmlService","signSchemeContentBuildHtml", data);
	}

}