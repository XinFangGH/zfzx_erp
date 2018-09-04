package com.zhiwei.credit.action.p2p;

/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.FileUtil;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.p2p.P2pFriendlink;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.p2p.P2pFriendlinkService;
import com.zhiwei.credit.service.system.FTPService;

import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class P2pFriendlinkAction extends BaseAction {
	@Resource
	private P2pFriendlinkService p2pFriendlinkService;
	private P2pFriendlink p2pFriendlink;
	
	@Resource
	private FileFormService  fileFormService;
	
	@Resource
	private FTPIoadFileService fTPUploadFileimpl;
	
	private Long id;
	// svn:songwj
	public String mark ;// 表字段
	
	// svn:songwj
	public String extendname;// 后缀名
	// svn:songwj
	private File myUpload;
	           
    private Long friendLinkId;//友情链接主键
    
    @Resource
	private FTPService ftpService;


	public  Integer  fileid;

	public Integer getFileid() {
		return fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid = fileid;
	}

	/**
	 * 显示列表
	 * 排序
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
//		List<P2pFriendlink> list = p2pFriendlinkService.getAll(filter);
		
		List<P2pFriendlink> list=p2pFriendlinkService.listOrderBy() ;
		Type type = new TypeToken<List<P2pFriendlink>>() {
		}.getType();
//		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
//				.append(filter.getPagingBean().getTotalItems()).append(
//						",result:");

		StringBuffer buff = new StringBuffer("{success:true,")
		.append("result:");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				p2pFriendlinkService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		if(id != null && !"".equals(id.toString())){
			System.out.println("进入后台的get（）方法------------------");
			P2pFriendlink p2pFriendlink = p2pFriendlinkService.get(id);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			// 将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append(gson.toJson(p2pFriendlink));
			System.out.println("返回的json值-------------------------+"+gson.toJson(p2pFriendlink));
			sb.append("}");
			setJsonString(sb.toString());
		}
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		
		 
		Map<String ,String > map = new  HashMap<String ,String >();
		if(friendLinkId != null && !"".equals(friendLinkId)){//如果主键存在，说明是友情链接进行的操作是修改+
			if(myUpload != null && !"".equals(myUpload)){
			 
				//根据mark获取存放文件信息
			    String  markSelect = "p2p_friendLink."+friendLinkId;//拼接mark值
				List<FileForm> list = fileFormService.getFileList(markSelect);//根据mark值查询出上传文件信息
				FileForm aa = new FileForm();
				if(list.size()>0){//友情链接记录有相关的图片文件
					System.out.println("友情链接有图片附件------------------------------");
					aa = list.get(0);//去查询出来数组的第一组值
					System.out.println("aa.getWebPath()=---------------"+aa.getWebPath());
					//上传文件
					map=fTPUploadFileimpl.ftpUploadFile(myUpload, appointFileSetFolder,"p2p_friendLink","", friendLinkId.toString(), extendname, setname, creatorid);
					System.out.println("map.getWebPath()=---------------"+map.get("webpath"));
					aa.setFilename(map.get("filename"));
					aa.setExtendname(map.get("extendname"));
					aa.setWebPath(map.get("webpath"));
					aa.setFilepath(map.get("filePath"));
					aa.setMark(map.get("mark"));
					fileFormService.merge(aa);//修改查询出来类的值
					fileFormService.DeleFile(Integer.valueOf(map.get("filedId").toString()));
				}else{
					System.out.println("友情链接没有图片附件------------------------------");
					map=fTPUploadFileimpl.ftpUploadFile(myUpload, appointFileSetFolder,"p2p_friendLink","", friendLinkId.toString(), extendname, setname, creatorid);
				}
			}else {//如果没有图片的文件信息，直接保存
				
				System.out.println("只保存记录，不上传图片");
				p2pFriendlink.setCreateDate(new Date());//添加创建时间
				p2pFriendlink.setModifyDate(new Date());//添加修改时间
				p2pFriendlinkService.save(p2pFriendlink);//先保存一条只有主键的数据
			}
		}else  {//如果传进来的friendLinkId为null，或者“”说明是添加操作
			System.out.println("友情链接表中没有页面上添加的记录----------------------");
			if(myUpload != null && !"".equals(myUpload)){//如果包含图片附件信息 说明前台选择了图片文件，需要上传操作
				System.out.println("存在上传图片");
				P2pFriendlink	aa = new P2pFriendlink();
				p2pFriendlink.setCreateDate(new Date());//添加创建时间
				p2pFriendlink.setModifyDate(new Date());//添加修改时间
				aa =p2pFriendlinkService.save(p2pFriendlink);//先保存一条只有主键的数据
				selectId = aa.getId().toString();//获得主键值
				map=fTPUploadFileimpl.ftpUploadFile(myUpload, appointFileSetFolder,tablename,"", selectId.toString(), extendname, setname, creatorid);//图片上传
				System.out.println("map。getfield----------------------"+map.get("filedId"));
			}else{//没有图片文件信息，说明不需要上传操作，直接保存前台传来的数据信息
				p2pFriendlink.setCreateDate(new Date());//添加创建时间
				p2pFriendlink.setModifyDate(new Date());//添加修改时间
				p2pFriendlinkService.save(p2pFriendlink);//先保存一条只有主键的数据
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	
	private File excelsql;
	public File getExcelsql() {
		return excelsql;
	}

	public void setExcelsql(File excelsql) {
		this.excelsql = excelsql;
	}
	
	String dirStr;
	String appointFileSetFolder = "webfile";//文件类别，赋值后会在服务器中
	private String tablename ;//表明,
	

	
	
	private String filename;//要保存的文件名，对应数据库中filename字段,
	private  String filepath  ="";//保存字段,对应数据库中setname字段,
	private  String webPath ="" ;//网上保存路径
	private  String setname ;
	private Integer creatorid ;
	
	

	private String  selectId; //要上传附件记录的主键,
	/**
	 * 友情链接Logo图片上传（FTP） svn：songwj
	 * @return
	 */
	public String ftpUploadFile(){
		System.out.println("*******************执行上传方法*************888");
		saveAndUpload();

		//指定上传文件所在的位置
		String filepath = "";
		filepath = joinServerPath(myUpload,appointFileSetFolder,tablename,selectId,extendname);
		 
		//需要创建文件夹的路径
		dirStr ="";
		dirStr = createDirStr(appointFileSetFolder,tablename,selectId);
		ftpService.FtpUploadFile(myUpload.getAbsolutePath(), filepath, filename,dirStr);//保存方法
		saveCsFile(myUpload,filename,extendname,setname,filepath,creatorid,mark,webPath);
	  return  SUCCESS;
	}
	
	//保存上传文件记录信息,cs_file
	public  String  saveCsFile(File  myUpload,String filename,String extendname ,String setname,String filePath,Integer creatorid,String mark,String webPath){
		FileForm fileinfo = new FileForm();
		fileinfo.setFilename(filename);//保存文件名
		fileinfo.setSetname(setname);//数据库中setname字段
		fileinfo.setFilepath(filepath);//数据库中filepath字段
		fileinfo.setExtendname(extendname);//文件后缀名
		Long sl=myUpload.length();
		fileinfo.setFilesize(sl.intValue());//文件的大小
		fileinfo.setCreatorid(creatorid);//文件创建着
		fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());//文件创建时间
		fileinfo.setContentType("application/octet-stream");
		fileinfo.setMark(mark);
		fileinfo.setWebPath(webPath);
		fileFormService.save(fileinfo);
		return SUCCESS;
	}
	
	/**
	 * 拼接文件存放的路径，创建文件夹使用
	 * @return
	 */
	public  String createDirStr(String appointFileSetFolder,String tablename,String selectId){
		String mark  ="";
		String dirStr ="";
		if(appointFileSetFolder  != null && !appointFileSetFolder.equals("")){
			dirStr += appointFileSetFolder;//拼接表明
			dirStr += "\\";
			
		}
		
		if(tablename  != null && !tablename.equals("") ){
			dirStr += tablename;//拼接表明
			mark+= tablename;
			dirStr += "."+selectId;//拼接主键值
			mark  += "."+selectId;//拼接主键值
		}
		 
		dirStr += "\\";
		dirStr +=  DateUtil.getYearAndMonth();//拼接年份和月份
		return dirStr;
		
	}
	//拼接上传后文件存放的位置
	/**
	 * myUpload：要上传的文件
	 * appointFileSetFolder：存放文件类型
	 * tableName：要进行文件上传的表明
	 * selectId：要上传文件信息的主键值
	 */
	public String joinServerPath( File myUpload,String appointFileSetFolder,String tablename,String selectId,String extendname){
		String fileToServerPath ="";
		System.out.println("*********************进入上传页面方法*****************");
		if(myUpload != null ){
			//存放位置：attachFiles+指定类别文件夹+表明+主键值
			fileToServerPath+="attachFiles/webfile";
			fileToServerPath += "\\";
//			fileToServerPath += appointFileSetFolder;//拼接文件指定文件类型文件夹
//			fileToServerPath += "\\";
			fileToServerPath += createDirStr( appointFileSetFolder,tablename,selectId);
			fileToServerPath += "\\";
			String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());//得到当前时间，精确到毫秒
			filename =nowTime+extendname;//拼接文件名保存数据时使用
			fileToServerPath += nowTime;//拼接保存文件名称
			fileToServerPath +=extendname;//拼接上传文件的后缀名
			filepath  = fileToServerPath;//保存字段，文件上传保存路径
			webPath = fileToServerPath;//网上保存路径
			System.out.println("*********************webPath*****************"+webPath);
		}
		return fileToServerPath;
	}
	
	// 上传并保存友情链接数据信息
	public void saveAndUpload(){
//		String  selectId ="";
		
		if(null!=p2pFriendlink.getId() && !"".equals(p2pFriendlink.getId())){
			selectId = p2pFriendlink.getId().toString();//获得主键值
		}else{
			P2pFriendlink	aa = new P2pFriendlink();
			aa.setCreateDate(new Date());
			aa.setModifyDate(new Date());
			aa =p2pFriendlinkService.save(p2pFriendlink);//先保存一条只有主键的数据
			selectId = aa.getId().toString();//获得主键值
			p2pFriendlink.setId(Long.valueOf(selectId));
			 
			System.out.println("****************p2pFriendlink====="+selectId);
		}
//		return selectId;
	}
	
	public String upLoadImgFile(File imgefile) {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			String uuid = UUID.randomUUID().toString();
			String subuuid = uuid.substring(0, 8) + uuid.substring(9, 13)
					+ uuid.substring(14, 18) + uuid.substring(19, 23)
					+ uuid.substring(24);
			String sourceProductImagePath = "attachFiles/excelupload/" + dateString
					+ "/" + subuuid + ".xls";
			File sourceProductImageFile = new File(ServletActionContext
					.getServletContext().getRealPath(sourceProductImagePath));
			File sourceProductImageParentFile = sourceProductImageFile
					.getParentFile();

			if (!sourceProductImageParentFile.exists()) {
				sourceProductImageParentFile.mkdirs();
			}
			try {

				FileUtils.copyFile(imgefile, sourceProductImageFile);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("记录上传出错" + e.getMessage());
			}
			sourceProductImagePath = ServletActionContext.getServletContext()
					.getRealPath(sourceProductImagePath);
			return sourceProductImagePath;
		}

	/*********************获取图片显示路径*****************************/
	
	public String getwebPath(){
		String tabelAndId = tablename +"."+ friendLinkId.toString();
		String webpaths ="";
	
		webpaths = fTPUploadFileimpl.getWebpath(tabelAndId);
		StringBuffer buff = new StringBuffer("{success:true,webpaths:'");
		buff.append(webpaths);
		buff.append("'}");
		jsonString=buff.toString();
		System.out.println(jsonString);
		return SUCCESS;
	}
	
	/***************友情链接获得上传的图片路径*************************/
	
	//svn：songwj
	public void getwebPaths(){
		
		String markSelect ="";
		System.out.println("mark-----------------"+mark);
		if(mark.indexOf("|")!=-1){
			markSelect = mark.substring(mark.lastIndexOf("|")+1, mark.length());
		}else{
			markSelect = mark;
		}
		List<FileForm> list = fileFormService.getFileList(markSelect);
		System.out.println("list.size()-----------------"+list.size());
		JsonUtil.jsonFromList(list);
	}
	/************************查询友情链接图片路径集合*******************/
	//查询友情链接图片路径集合
		//SVN：songwj
		public String getwebPathList(){
			System.out.println("mark-------------"+mark);
			String tabelAndId = mark;
			List<FileForm> list = null;
			list = fTPUploadFileimpl.getWebpathList(tabelAndId);
			JsonUtil.jsonFromList(list);
			return SUCCESS;
		}
	
	/****************************文件下载*****************************/
	public URL remoteFile = null;
	  
	//songwenjie  远程服务器上的文件下载方法
	public  void downloadFriendLink(){
	 
		FileForm fileEntityFile= fileFormService.getById(fileid);//查询文件附件记录表
		
		String  filePath = AppUtil.getConfigMap().get("fileURL") + fileEntityFile.getWebPath();//拼接文件在远程服务器上的路径
		
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
	           
	        	remoteFile =new URL(AppUtil.getFileUrl()+fileEntityFile.getWebPath());//建立远程连接
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
	public File getMyUpload() {
		return myUpload;
	}

	public void setMyUpload(File myUpload) {
		this.myUpload = myUpload;
	}
	

	public Long getFriendLinkId() {
		return friendLinkId;
	}

	public void setFriendLinkId(Long friendLinkId) {
		this.friendLinkId = friendLinkId;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public P2pFriendlink getP2pFriendlink() {
		return p2pFriendlink;
	}

	public void setP2pFriendlink(P2pFriendlink p2pFriendlink) {
		this.p2pFriendlink = p2pFriendlink;
	}
	
	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}


	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

}
