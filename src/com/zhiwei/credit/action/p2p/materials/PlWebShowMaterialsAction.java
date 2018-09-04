package com.zhiwei.credit.action.p2p.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.ConvertFileType;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.core.creditUtils.FileUtil;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.p2p.WebFinanceApplyUploads;
import com.zhiwei.credit.model.p2p.materials.PlWebShowMaterials;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.materials.OurProcreditMaterialsEnterpriseService;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.p2p.WebFinanceApplyUploadsService;
import com.zhiwei.credit.service.p2p.materials.PlWebShowMaterialsService;
import com.zhiwei.credit.util.TreeBeanUtil;
/**
 * @author 
 *
 */
public class PlWebShowMaterialsAction extends BaseAction{
	
	@Resource
	private FileFormService fileFormService;
	@Resource
	private PlWebShowMaterialsService plWebShowMaterialsService;
	@Resource
	private OurProcreditMaterialsEnterpriseService ourProcreditMaterialsEnterpriseService;
	@Resource
	private SlProcreditMaterialsService slProcreditMaterialsService;
	private PlWebShowMaterials plWebShowMaterials;
	
	@Resource
	private FTPIoadFileService fTPUploadFileimpl;
	@Resource
	private WebFinanceApplyUploadsService webFinanceApplyUploadsService;
	
	private Long webMaterialsId;
	private Long proMaterialsId;
	private String projId;//项目ID
	private String proMaterialIds;//Id拼接字符串
	private String businessType;
	private String operationType;
	
	public String extendname;
	public String mark;
	public String tablename;
	
	public String setname;
	public Integer creatorid;
	
	//svn:songwj
	public Integer fileid;  
	//材料表主键值
	//svn:songwj
	public String webId;
	
	
	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	//删除服务器上的文件
	//表记录的主键
	//svn:songwj
	public void removeImg(){
		 //主要分四步：1 删除服务器文件 2 删除cs_file表中的数据  3 跟新材料表中的数据  4 刷新前台数据
		FileForm fileEntityFile= fileFormService.getById(fileid);
		if(fileEntityFile != null){
			AppUser user = ContextUtil.getCurrentUser();
			boolean deletePath = false;
			if(user.getId().equals("1")){//超级管理员
				deletePath = true;
			}else{//其他用户删除附件验证是否是本人上传的附件
				if(user.getId().equals(fileEntityFile.getCreatorid())){
					deletePath = true;
				}
			}
			if(deletePath){
				fileFormService.DeleFile(fileid);//删除cs_file表中的图片记录
				updateDataNum1(Long.valueOf(webId));//更新 贷款材料信息表中的数据
				list();
			}
		}
		 jsonString="{success:true}";
	}
	
	//删除服务器上文件时，减少文件的数量
	//svn：songsj
	public String updateDataNum1(Long webMaterialsId){      
		if(null!=webMaterialsId){
			PlWebShowMaterials materials=this.plWebShowMaterialsService.get(webMaterialsId);
			int data= materials.getDatumNums();
			data --;
		    materials.setDatumNums(data);
		    this.plWebShowMaterialsService.merge(materials);
		}
		    jsonString="{success:true}";
		    setJsonString(jsonString);
			return SUCCESS;
	}
	
	
	//贷款材料下载
	//svn:songwj
	public void DownLoad(){
		FileForm fileEntityFile= fileFormService.getById(fileid);
		
		if(fileEntityFile!=null){
			String fullfilepath=AppUtil.getP2pUrl()+"/"+fileEntityFile.getWebPath();
			String filename = fileEntityFile.getFilename();
			try {
				fTPUploadFileimpl.ftpDownFile(fullfilepath, "D:/ftpFiles/",filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void DownloadFileStream(String filepath,String fileName,boolean isInline ) throws Exception{
		HttpServletResponse response=getResponse();
		File filed = null ;
		InputStream fis = null ;
		OutputStream toClient = null ;
		try {
			filed = new File(filepath);
			
			String dname=new String(filed.getName().getBytes(),"ISO8859-1");
			
			String dext=FileUtil.getExtentionUpper(dname);
			response.reset();
			String inlineType=isInline ? "inline" : "attachment"; //是否内联附件
			//response.setHeader("Content-Disposition", inlineType + ";filename=\"" + fileName + "\"");
			//修改下载时候出现乱码的情况。update by lu 2011.06.29
			response.setHeader("Content-Disposition", inlineType + ";filename=\"" + new String(fileName.getBytes("gb2312"),"ISO8859-1") + "\"");
			fis= new BufferedInputStream(new FileInputStream(filepath));
			byte [] buffer = new byte [fis.available()];
			fis.read(buffer);
			toClient  = new BufferedOutputStream(response.getOutputStream());
			String filter = fileName.substring(fileName.lastIndexOf("."));
			String contentType = ConvertFileType.returnConvertFileType(filter);
			if("".equals(contentType)){
				contentType="application/octet-stream";
			}
        	response.setContentType(contentType);
        	toClient.write(buffer);
		} catch (FileNotFoundException e) {
			 e.printStackTrace();
		}finally{
			if(null!=fis){
				fis.close();
			}
			if(null!=toClient){
				toClient.close();
			}
			
		}
	}
	
	
	/**
	 * 显示列表
	 */
	public String list(){
		
		List<PlWebShowMaterials> returnList=new ArrayList<PlWebShowMaterials>();
		
		List<OurProcreditMaterialsEnterprise> listtemp=ourProcreditMaterialsEnterpriseService.getListByParentId(0,null);
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		//for(OurProcreditMaterialsEnterprise areatemp:listtemp){
			       
			    // List<SlProcreditMaterials> listtemp1 = slProcreditMaterialsService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(),businessTypeKey, show);
			     List<PlWebShowMaterials> listtemp1 = plWebShowMaterialsService.getByProjIdAndShow(projId, businessTypeKey);
			     if(null!=listtemp1 && listtemp1.size()>0)
			     {   
			    	 for(PlWebShowMaterials  mt:listtemp1){
			    		 if(mt.getMaterialsId()!=null){
			    			 OurProcreditMaterialsEnterprise areatemp=ourProcreditMaterialsEnterpriseService.get(mt.getMaterialsId());
				    		 if(null!=areatemp){
				    			 OurProcreditMaterialsEnterprise o=ourProcreditMaterialsEnterpriseService.get(areatemp.getParentId().longValue());
				    			 mt.setParentName(o.getMaterialsName());
				    		 }
			    		 }else{
			    			 mt.setParentName("其他");
			    		 }
			    		
			    		 returnList.add(mt);
			    	 }
			     }
		//}
		Gson gson=new Gson();
		buff.append(gson.toJson(returnList));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	//贷款材料上传
	//svn：songwj
	public String upLoadFiles(){
		if(mark.indexOf("pl_Web_Show_Materials|sl_smallloan_project")!=-1){
			AppUser user = ContextUtil.getCurrentUser();
			if(user!=null){
				creatorid = Integer.valueOf(user.getId());
				String selectId = mark.substring(mark.indexOf(".")+1, mark.length());
				String tablenameFirst = mark.substring(0, mark.indexOf("|"));//获得第一个表的名称
				String tablenameTwo = mark.substring(mark.indexOf("|")+1, mark.indexOf("."));
				String appointFileSetFolder ="webfile";//指定文件存放的路径
				@SuppressWarnings("unused") 
				Map<String,String> map = new  HashMap<String ,String >();
				map=fTPUploadFileimpl.ftpUploadFile(myUpload,appointFileSetFolder, tablenameFirst,tablenameTwo, selectId, extendname, setname, creatorid);
				updateDataNum();
				list();	
			}
		}
		return SUCCESS;
	}
	
	public  String listTree1(){
		
		try{
			String businessType=this.getRequest().getParameter("businessType");
			String operationType=this.getRequest().getParameter("operationType");
			 List<TreeBeanUtil> blist= new ArrayList<TreeBeanUtil>();
		
			 List<SlProcreditMaterials> list=slProcreditMaterialsService.listByMaterialsIdGroupById(projId, businessType,operationType);
			 for(SlProcreditMaterials m:list){
				 	 OurProcreditMaterialsEnterprise setting=ourProcreditMaterialsEnterpriseService.get(Long.valueOf(m.getParentId()));
					 TreeBeanUtil treeBean=new TreeBeanUtil();
					 treeBean.setId(setting.getMaterialsId());
					 treeBean.setText(setting.getMaterialsName());
					 treeBean.setChecked(false);
					 treeBean.setCls("folder");
					 treeBean.setLeaf(false);
					 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
					 List<SlProcreditMaterials> mlist=slProcreditMaterialsService.listByMaterialsIdAndOperationTypeKey(projId, businessType,operationType, Long.valueOf(m.getParentId()));
					 for(SlProcreditMaterials s:mlist){
						 List<PlWebShowMaterials> pl=plWebShowMaterialsService.getByProMaterialsId(projId,s,businessType,operationType);
						 if(pl==null||pl.size()==0){
							 TreeBeanUtil c= new TreeBeanUtil();
							 c.setId(s.getProMaterialsId());
							 c.setText(s.getMaterialsName());
							 c.setChecked(false);
							 c.setCls("file");
							 c.setLeaf(true);
							 set.add(c);
							 
							 
							
						 }
					 }
					 treeBean.setChildren(set);
					 blist.add(treeBean); 
				 }
			
			 Gson gson=new Gson();
			jsonString=gson.toJson(blist);
		 }catch(Exception e){
			 logger.error("PlArchivesMaterialsAction:"+e.getMessage());
			 e.printStackTrace();
		 }
		 return SUCCESS;
	}
	
	public  String listExitTree(){
		try{
			String businessType=this.getRequest().getParameter("businessType");
			String operationType=this.getRequest().getParameter("operationType");
			 List<TreeBeanUtil> blist= new ArrayList<TreeBeanUtil>();
			 List<PlWebShowMaterials> list=plWebShowMaterialsService.listByMaterialsIdGroupById(projId, businessType,operationType);
			 for(PlWebShowMaterials m:list){
				 if(m.getParentId()!=null){
					 if(m.getParentId().compareTo(Integer.valueOf("0"))==0){
						 TreeBeanUtil treeBean=new TreeBeanUtil();
						 treeBean.setId(Long.valueOf("0"));
						 treeBean.setText("新增");
						 treeBean.setChecked(false);
						 treeBean.setCls("folder");
						 treeBean.setLeaf(false);
						 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
						 List<PlWebShowMaterials> mlist=plWebShowMaterialsService.listByMaterialsIdAndOperationTypeKey(projId, businessType,operationType, Long.valueOf(m.getParentId()));
						 for(PlWebShowMaterials s:mlist){
								 TreeBeanUtil c= new TreeBeanUtil();
								 c.setId(s.getWebMaterialsId());
								 c.setText(s.getMaterialsName());
								 c.setChecked(false);
								 c.setCls("file");
								 c.setLeaf(true);
								 set.add(c);
							 
						 }
						 treeBean.setChildren(set);
						 blist.add(treeBean); 
					 }else{
						 OurProcreditMaterialsEnterprise setting=ourProcreditMaterialsEnterpriseService.get(Long.valueOf(m.getParentId()));
						 TreeBeanUtil treeBean=new TreeBeanUtil();
						 treeBean.setId(setting.getMaterialsId());
						 treeBean.setText(setting.getMaterialsName());
						 treeBean.setChecked(false);
						 treeBean.setCls("folder");
						 treeBean.setLeaf(false);
						 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
						 List<PlWebShowMaterials> mlist=plWebShowMaterialsService.listByMaterialsIdAndOperationTypeKey(projId, businessType,operationType, Long.valueOf(m.getParentId()));
						 for(PlWebShowMaterials s:mlist){
								 TreeBeanUtil c= new TreeBeanUtil();
								 c.setId(s.getWebMaterialsId());
								 c.setText(s.getMaterialsName());
								 c.setChecked(false);
								 c.setCls("file");
								 c.setLeaf(true);
								 set.add(c);
							 
						 }
						 treeBean.setChildren(set);
						 blist.add(treeBean); 
					 }
					
				 }
				 	
			}
			
			 Gson gson=new Gson();
			jsonString=gson.toJson(blist);
		 }catch(Exception e){
			 logger.error("PlArchivesMaterialsAction:"+e.getMessage());
			 e.printStackTrace();
		 }
		 return SUCCESS;
	}
	public String getProjId() {
		return projId;
	}

	public String updateMaterials(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					SlProcreditMaterials o = slProcreditMaterialsService.get(Long.valueOf(proArrs[i]));
					PlWebShowMaterials add =new PlWebShowMaterials();
					add.setProjId(projId);
					add.setMaterialsId(o.getMaterialsId());
					add.setMaterialsName(o.getMaterialsName());
					add.setDatumNums(0);
					add.setXxnums(0);
					add.setParentId(o.getParentId());
					add.setBusinessTypeKey(businessType);
					add.setOperationTypeKey(operationType);
					add.setProMaterialsId(o.getProMaterialsId());
					add.setDatumNumsOfLine(o.getDatumNumsOfLine());
					add.setCreateTime(new Date());//添加创建时间
					/*add.setMaterialsType(o.getIsPublic());*/
					plWebShowMaterialsService.save(add);
					
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 */
	public String addOnlineMaterials(){
		try{
			String materialIds = this.getRequest().getParameter("materialIds");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					WebFinanceApplyUploads w=webFinanceApplyUploadsService.get(Long.valueOf(proArrs[i]));
					PlWebShowMaterials add =new PlWebShowMaterials();
					add.setProjId(projId);
					add.setMaterialsId(w.getId());
					add.setMaterialsName(w.getMaterialstype());
					add.setDatumNums(0);
					add.setXxnums(0);
					add.setParentId(Integer.valueOf(w.getId().toString()));
					add.setBusinessTypeKey(businessType);
					add.setOperationTypeKey(operationType);
					add.setProMaterialsId(w.getId());
					add.setCreateTime(new Date());//添加创建时间
					/*add.setMaterialsType(o.getIsPublic());*/
					add.setIsOnline(Short.valueOf("1"));
					plWebShowMaterialsService.save(add);
					
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String deleteMaterials(){
		 try{
			 String materialIds = this.getRequest().getParameter("materialsIds");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					plWebShowMaterialsService.remove(Long.valueOf(proArrs[i]));
				}
			}
			jsonString="{success:true}";
		 }catch(Exception e){
			 jsonString="{success:false}";
			 logger.error("BpMortgageMaterials:"+e.getMessage());
			 e.printStackTrace();
		 }
		 return SUCCESS;
	 }
	

	//上传后更新图片数量的总数的方法
	//svn：songsj
	public String updateDataNum(){      
		if(null!=webMaterialsId){
			PlWebShowMaterials materials=this.plWebShowMaterialsService.get(webMaterialsId);
			int data= materials.getDatumNums();
			data ++;
		    materials.setDatumNums(data);
		     
		    this.plWebShowMaterialsService.merge(materials);
		}
		    jsonString="{success:true}";
		    setJsonString(jsonString);
			return SUCCESS;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}

	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				plWebShowMaterialsService.remove(new Long(id));
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
		PlWebShowMaterials plWebShowMaterials=plWebShowMaterialsService.get(webMaterialsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plWebShowMaterials));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plWebShowMaterials.getWebMaterialsId()==null){
			plWebShowMaterials.setCreateTime(new  Date());
			plWebShowMaterials.setDatumNums(0);
			plWebShowMaterialsService.save(plWebShowMaterials);
		}else{
			PlWebShowMaterials orgPlWebShowMaterials=plWebShowMaterialsService.get(plWebShowMaterials.getWebMaterialsId());
			try{
				plWebShowMaterials.setCreateTime(new Date());
				BeanUtil.copyNotNullProperties(orgPlWebShowMaterials, plWebShowMaterials);
				plWebShowMaterialsService.save(orgPlWebShowMaterials);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**得到下载列表	jiang*/
	@SuppressWarnings("unchecked")
	public void getFileListExt(){
		String markSelect ="";
		if(mark.indexOf("|")!=-1){
			markSelect = mark.substring(mark.lastIndexOf("|")+1, mark.length());
		}else{
			markSelect = mark;
		}
		List<FileForm> list = fileFormService.getFileList(markSelect);
		
		JsonUtil.jsonFromList(list);
	}
	
	
	/***********************贷款材料展示清附件下载*************************/
	
	//svn:songwj
    public URL remoteFlie = null;
    
	public  void downloadMaterials(){
		
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
	           
	            remoteFlie =new URL(AppUtil.getFileUrl()+fileEntityFile.getWebPath());//建立远程连接
	            httpConn = (HttpURLConnection)remoteFlie.openConnection();  //打开连接
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
	
	//流的转换
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

	
	public String saveNewMaterials(){
		setJsonString("{success:true}");
		return SUCCESS;
	}

	public void setProMaterialsId(Long proMaterialsId) {
		this.proMaterialsId = proMaterialsId;
	}

	public Long getProMaterialsId() {
		return proMaterialsId;
	}

	public void setProMaterialIds(String proMaterialIds) {
		this.proMaterialIds = proMaterialIds;
	}

	public String getProMaterialIds() {
		return proMaterialIds;
	}
	
	public Integer getFileid() {
		return fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid = fileid;
	}

	public String getExtendname() {
		return extendname;
	}

	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getSetname() {
		return setname;
	}

	public void setSetname(String setname) {
		this.setname = setname;
	}

	public Integer getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Integer creatorid) {
		this.creatorid = creatorid;
	}

	public File getMyUpload() {
		return myUpload;
	}

	public void setMyUpload(File myUpload) {
		this.myUpload = myUpload;
	}

	public String getMyUploadFileName() {
		return myUploadFileName;
	}

	public void setMyUploadFileName(String myUploadFileName) {
		this.myUploadFileName = myUploadFileName;
	}

	public File getExcelsql() {
		return excelsql;
	}

	public void setExcelsql(File excelsql) {
		this.excelsql = excelsql;
	}

	//svn:songwj
	private File myUpload;
	 
	private String myUploadFileName;
	
	private File excelsql;
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public Long getWebMaterialsId() {
		return webMaterialsId;
	}

	public void setWebMaterialsId(Long webMaterialsId) {
		this.webMaterialsId = webMaterialsId;
	}

	public PlWebShowMaterials getPlWebShowMaterials() {
		return plWebShowMaterials;
	}

	public void setPlWebShowMaterials(PlWebShowMaterials plWebShowMaterials) {
		this.plWebShowMaterials = plWebShowMaterials;
	}

}
