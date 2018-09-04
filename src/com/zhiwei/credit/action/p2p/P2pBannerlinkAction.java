package com.zhiwei.credit.action.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.io.File;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.p2p.P2pBannerlink;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.p2p.P2pBannerlinkService;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class P2pBannerlinkAction extends BaseAction{
	@Resource
	private P2pBannerlinkService p2pBannerlinkService;
	private P2pBannerlink p2pBannerlink;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public P2pBannerlink getP2pBannerlink() {
		return p2pBannerlink;
	}

	public void setP2pBannerlink(P2pBannerlink p2pBannerlink) {
		this.p2pBannerlink = p2pBannerlink;
	}
	//songwj
	@Resource
	private FTPIoadFileService fTPIoadFileService;
    //svn:song
    private  File  myUpload;
	
	// svn:songwj
	public String mark ;// 表字段
	// svn:songwj
	public String extendname;// 后缀名
	// svn:songwj
	public String tablename;// 后缀名
	
	public String setname;
	 
	public int creatorid;
	
	@Resource
	private FileFormService fileFormService;
	
	public String getSetname() {
		return setname;
	}

	public void setSetname(String setname) {
		this.setname = setname;
	}

	public int getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(int creatorid) {
		this.creatorid = creatorid;
	}
	public String getwebPathList(){
		System.out.println("mark-------------"+mark);
		String tabelAndId = mark;
		List<FileForm> list = null;
		list = fTPIoadFileService.getWebpathList(tabelAndId);
		com.zhiwei.credit.core.creditUtils.JsonUtil.jsonFromList(list);
		return SUCCESS;
	}
	public String upLoadFiles(){
		String tablenameFirst = tablename;//获得第一个表的名称
		String tablenameTwo = "";
		String appointFileSetFolder ="p2p_article";//指定文件存放的路径
		String selectId =Common.getRandomNum(32).toString();
		
		Map<String ,String > map = new  HashMap<String ,String >();
		map=fTPIoadFileService.ftpUploadFile(myUpload,appointFileSetFolder, tablenameFirst,tablenameTwo, selectId, extendname, setname, creatorid);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create() ;
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,webPath:'"+map.get("webpath")+"'}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<P2pBannerlink> list= p2pBannerlinkService.getAll(filter);
		for(P2pBannerlink li : list){
			if(li.getIsShow() != null&& li.getIsShow()==(short)1){
				li.setShow("是");
			}else if(li.getIsShow() != null&& li.getIsShow()==0){
				li.setShow("否");
			}
		}
		Type type=new TypeToken<List<P2pBannerlink>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		System.out.println(buff.toString());
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				p2pBannerlinkService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		P2pBannerlink p2pBannerlink=p2pBannerlinkService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(p2pBannerlink));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(p2pBannerlink.getId()==null){
			p2pBannerlink.setCreateDate(new Date());
			p2pBannerlinkService.save(p2pBannerlink);
		}else{
			P2pBannerlink orgP2pBannerlink=p2pBannerlinkService.get(p2pBannerlink.getId());
			try{
				BeanUtil.copyNotNullProperties(orgP2pBannerlink, p2pBannerlink);
				orgP2pBannerlink.setModifyDate(new Date());
				p2pBannerlinkService.save(orgP2pBannerlink);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}

	public File getMyUpload() {
		return myUpload;
	}

	public void setMyUpload(File myUpload) {
		this.myUpload = myUpload;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getExtendname() {
		return extendname;
	}

	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
}
