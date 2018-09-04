package com.zhiwei.credit.action.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.p2p.WebFinanceApplyUploads;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.WebFinanceApplyUploadsService;
/**
 * 
 * @author 
 *
 */
public class WebFinanceApplyUploadsAction extends BaseAction{
	@Resource
	private WebFinanceApplyUploadsService webFinanceApplyUploadsService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	private WebFinanceApplyUploads webFinanceApplyUploads=new WebFinanceApplyUploads();
	private String mark;//mark=模块名+"."+ID  唯一标识
	
	private String typeisfile;//是否只是一个文件，目前只调查报告用到
	//private String userID;//
	//private String materialstype;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WebFinanceApplyUploads getWebFinanceApplyUploads() {
		return webFinanceApplyUploads;
	}

	public void setWebFinanceApplyUploads(WebFinanceApplyUploads webFinanceApplyUploads) {
		this.webFinanceApplyUploads = webFinanceApplyUploads;
	}
	
	/****************************文件下载*****************************/
	
	@Resource
	private FileFormService  fileFormService;
	
	
	public URL remoteFile = null;
	  
	public int  fileid;//查询图片的主键值
	
	public String picURL;//从前台传入的图片的路径
	

	public String  fileid1;//查询图片的主键值
	
	//songwenjie  远程服务器上的文件下载方法6
	public  void downloadFriendLink(){
		
		String  filePath = AppUtil.getP2pUrl()+ picURL;//拼接文件在远程服务器上的路径
		System.out.println("filePath ====="+filePath);
	  
		HttpServletResponse response=getResponse();
		try {
	            File file = new File(filePath);//已远程服务器上的文件存放路径创建一个文件
	            String  fileName = file.getName();//获取文件名称
	            fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");//把文件名按UTF-8取出并按ISO8859-1编码，
	            															//保证弹出窗口中的文件名中文不乱码，中文不要太多，
	            															//最多支持17个中文，因为header有150个字节限制。
	            response.setContentType("application/octet-stream");//告诉浏览器输出内容为流
	            response.reset();//清除缓冲中的数据
	            response.addHeader("Content-Disposition", "attachment;filename="+fileName);//Content-Disposition中指定的类型是文件的扩展名，
	           
	            //HTTP远程下载songwj
	            HttpURLConnection httpConn = null;  
	           
	        	remoteFile =new URL(filePath);//建立远程连接
	            httpConn = (HttpURLConnection)remoteFile.openConnection();  //打开连接
	            httpConn.setRequestMethod("GET");    
	            httpConn.setConnectTimeout(1000 * 1000);//设置下载连接时间
	            
	            InputStream inStream = httpConn.getInputStream();//通过输入流获取图片数据    
	            byte data[];
				try {
					data = readInputStream(inStream);
					inStream.read(data);  //读数据     
					inStream.close();     
					OutputStream os = response.getOutputStream();    
					os.write(data);    
					os.flush();    
				    os.close();  
				}catch (Exception e) {
					e.printStackTrace();
				} 
		       } catch (Exception e) {
					e.printStackTrace();
		       } 
	}
	
	//主要用于流的转换
	public static byte[] readInputStream(InputStream inStream) throws Exception{    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[2048];    
        int len = 0;    
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }    
        inStream.close();    
        return outStream.toByteArray();    
	}    
	
	/*****************************获得展示图片的路径*********************************/
	
	//查询图片路径方法
	public List<WebFinanceApplyUploads> getPicPath(){
		List<WebFinanceApplyUploads> list2 =null;
		if(mark != null && !"".equals(mark)){
			String[] arr = mark.split("\\.");	
			String userIdStr = mark.substring(0, mark.indexOf("."));
			String materialstypeStr = mark.substring( mark.indexOf(".")+1, mark.length());
			int userId  = Integer.valueOf(userIdStr);
			String materialstype =materialstypeStr;
			list2 =	webFinanceApplyUploadsService.getlistByUserIDAndType(userId ,materialstype);
		}
		List<FileForm> list4 =  new ArrayList<FileForm>();
		if(list2.size()>0){
			for(int i=0;i<list2.size();i++){
				WebFinanceApplyUploads apply = list2.get(i);
					FileForm filef =  new FileForm();
					filef.setFileid(Integer.valueOf(list2.get(i).getId().toString()));
					filef.setFilename(apply.getMaterialstype());
					filef.setCreatetime(list2.get(i).getLastuploadtime());
					filef.setWebPath(apply.getFiles());
					list4.add(filef);
			}
		}
		JsonUtil.jsonFromList(list4);
		return null;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		long userid=Integer.parseInt(this.getRequest().getParameter("userId"));
		if(userid!=0){
			filter.addFilter("Q_userID_L_EQ", userid+"");
		}
		List<WebFinanceApplyUploads> list= webFinanceApplyUploadsService.getAll(filter);
		List<WebFinanceApplyUploads> currlist=new ArrayList<WebFinanceApplyUploads>();
		int num=0;
		for(WebFinanceApplyUploads upload:list){
			if(upload!=null){
			BpCustMember member=bpCustMemberService.get(upload.getUserID());
			String fileNum=upload.getFiles();
			if(fileNum!=null){
			String[] str=fileNum.split("\\|");
				if(str[0].equals(""))
			{
				upload.setPictureNum("0");
			}else if(fileNum!="" && str[0].equals("")){
				upload.setPictureNum("1");
			}else {
				upload.setPictureNum(str.length+"");
			}
			upload.setLoginName(member.getLoginname());
			currlist.add(upload);
			num++;
			System.out.println("i======================="+num);
		    	}
			}
		}
		Type type=new TypeToken<List<WebFinanceApplyUploads>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH-mm-ss").create();
		buff.append(gson.toJson(currlist, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 所有用户上传资料的列表
	 * @return
	 */
	public String upLoadList(){
		String state=this.getRequest().getParameter("state");
		
		List<WebFinanceApplyUploads> listPerson=webFinanceApplyUploadsService.upLoadList(start,limit,state,this.getRequest());
		List<WebFinanceApplyUploads> listPersonCount=webFinanceApplyUploadsService.upLoadList(null,null,state,this.getRequest());
//		List<WebFinanceApplyUploads> currlist=new ArrayList<WebFinanceApplyUploads>();
	/*	for(WebFinanceApplyUploads upload:listPerson){
			String fileNum=upload.getFiles();
			String[] str=fileNum.split("\\|");
			if(str[0].equals(""))
			{
				upload.setPictureNum("0");
			}else if(fileNum!="" && str[0].equals("")){
				upload.setPictureNum("1");
			}else{
				upload.setPictureNum(str.length+"");
			}
			currlist.add(upload);
		}*/
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH-mm-ss").create();
		Type type=new TypeToken<List<WebFinanceApplyUploads>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(listPersonCount!=null?listPersonCount.size():0).append(",result:");
		buff.append(gson.toJson(listPerson, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 查询线上用户上传但没有维护到该项目的文件列表
	 * @return
	 */
	public String upLoadOnLineList(){
		String state=this.getRequest().getParameter("state");
		
		List<WebFinanceApplyUploads> listPerson=webFinanceApplyUploadsService.upLoadOnLineList(start,limit,state,this.getRequest());
		List<WebFinanceApplyUploads> listPersonCount=webFinanceApplyUploadsService.upLoadOnLineList(null,null,state,this.getRequest());
//		List<WebFinanceApplyUploads> currlist=new ArrayList<WebFinanceApplyUploads>();
	/*	for(WebFinanceApplyUploads upload:listPerson){
			String fileNum=upload.getFiles();
			String[] str=fileNum.split("\\|");
			if(str[0].equals(""))
			{
				upload.setPictureNum("0");
			}else if(fileNum!="" && str[0].equals("")){
				upload.setPictureNum("1");
			}else{
				upload.setPictureNum(str.length+"");
			}
			currlist.add(upload);
		}*/
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH-mm-ss").create();
		Type type=new TypeToken<List<WebFinanceApplyUploads>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(listPersonCount!=null?listPersonCount.size():0).append(",result:");
		buff.append(gson.toJson(listPerson, type));
		buff.append("}");
		jsonString=buff.toString();
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
				webFinanceApplyUploadsService.remove(new Long(id));
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
		WebFinanceApplyUploads webFinanceApplyUploads=webFinanceApplyUploadsService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(webFinanceApplyUploads));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		
		long id=Long.parseLong(this.getRequest().getParameter("id"));
		int state=Integer.parseInt(this.getRequest().getParameter("state"));
		webFinanceApplyUploads.setId(id);
		if(webFinanceApplyUploads.getId()==null){
			webFinanceApplyUploadsService.save(webFinanceApplyUploads);
		}else{
			WebFinanceApplyUploads orgWebFinanceApplyUploads=webFinanceApplyUploadsService.get(webFinanceApplyUploads.getId());
			orgWebFinanceApplyUploads.setStatus(state);
			try{
				BeanUtil.copyNotNullProperties(orgWebFinanceApplyUploads, webFinanceApplyUploads);
				webFinanceApplyUploadsService.save(orgWebFinanceApplyUploads);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 审核通过，驳回
	 * @return
	 */
	public String updateState(){
		long id=Long.parseLong(this.getRequest().getParameter("id"));
		int state=Integer.parseInt(this.getRequest().getParameter("state"));
		String rejectReason = this.getRequest().getParameter("webFinanceApplyUploads.rejectReason");
		WebFinanceApplyUploads uploadList=webFinanceApplyUploadsService.get(id);
		List<WebFinanceApplyUploads> apply = webFinanceApplyUploadsService.getlistByUserIDAndType(uploadList.getUserID().intValue(), uploadList.getMaterialstype());
		for(WebFinanceApplyUploads web:apply){
			web.setStatus(state);
			web.setRejectReason(rejectReason);
			webFinanceApplyUploadsService.save(web);
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/*public void getPic(){
		
		String userID=this.getRequest().getParameter("userID");
		List<WebFinanceApplyUploads> list1 = new ArrayList<WebFinanceApplyUploads>();
		QueryFilter filter=new QueryFilter(this.getRequest());
		filter.addFilter("userID", userID);
		List<WebFinanceApplyUploads> list = webFinanceApplyUploadsService.getAll(filter);
		System.out.println(list.size());
		if(null!=list && list.size()>0)
		{
			for(WebFinanceApplyUploads upload:list){
				String fileNum=upload.getFiles();
				int id=Integer.parseInt(upload.getId()+"");
				String[] str=fileNum.split("|");
				webFinanceApplyUploads.setPictureNum(str.length+"");
				list.add(id, webFinanceApplyUploads);
			}
		}
		
		JsonUtil.jsonFromList(list1);
	}*/
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	public int getFileid() {
		return fileid;
	}

	public void setFileid(int fileid) {
		this.fileid = fileid;
	}
}
