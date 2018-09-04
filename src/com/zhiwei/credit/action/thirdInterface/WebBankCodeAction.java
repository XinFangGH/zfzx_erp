package com.zhiwei.credit.action.thirdInterface;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
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

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.thirdInterface.WebBankCode;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.system.FTPService;
import com.zhiwei.credit.service.thirdInterface.WebBankCodeService;
/**
 * 
 * @author 
 *
 */
public class WebBankCodeAction extends BaseAction{
	@Resource
	private WebBankCodeService webBankCodeService;
	@Resource
	private FTPService ftpService;
	private WebBankCode webBankCode;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WebBankCode getWebBankCode() {
		return webBankCode;
	}

	public void setWebBankCode(WebBankCode webBankCode) {
		this.webBankCode = webBankCode;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("orderNum", QueryFilter.ORDER_ASC);
		List<WebBankCode> list= webBankCodeService.getAll(filter);
		
		Type type=new TypeToken<List<WebBankCode>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		System.out.println("buff.toString()----"+buff.toString());
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
				webBankCodeService.remove(new Long(id));
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
		WebBankCode webBankCode=webBankCodeService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(webBankCode));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 向ftp上传文件
	 * @return
	 */
	private String upload(){
		String rootPath = this.getRequest().getParameter("rootPath");
		System.out.println("新上传rootPath="+rootPath);
		//得到上传文件名
		String fileName = rootPath.substring(rootPath.lastIndexOf("/")+1,rootPath.length());
		
		//得到上传文件的后缀名
		//String extendname = FileUtil.getExtention(fileName);
		//得到当前时间，精确到毫秒
		//String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//fileName = nowTime + extendname;
		String webPath = rootPath.substring(0,rootPath.lastIndexOf("/")+1)+fileName;
		//项目根目录
		String root = AppUtil.getAppAbsolutePath().replaceAll("\\\\", "/");
		//调用ftp的方法
		ftpService.ftpUploadFile(root+"/attachFiles/"+webPath, fileName);
		return webPath;
	}
	
	
	/************************添加第三方银行时 对银行信息的查询*************************/
	/**
	 * 获得下拉列表项数据信息
	 */
	public String getComboData(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("orderNum", QueryFilter.ORDER_ASC);
		List<WebBankCode> list= webBankCodeService.getAll(filter);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getBankLogo() != null && !"".equals(list.get(i).getBankLogo())){
					list.get(i).setImgURL(AppUtil.getConfigMap().get("fileURL") + list.get(i).getBankLogo());
				}
			}
		}
		
		StringBuffer buff = new StringBuffer("[");
		// if (null != payintentPeriod && !"".equals(payintentPeriod)) {
		for (int i = 0; i < list.size(); i++) {
//			buff.append("['id':").append(list.get(i).getId()).append(",'bankName':'").append(list.get(i).getBankName()).append(
//					"'],");
			
			buff.append("['").append(list.get(i).getId()).append("','").append(list.get(i).getBankName()).append(
					"'],");
		}
		if (buff.length() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		// }
		buff.append("]");
		setJsonString(buff.toString());
		
		System.out.println("buff.toString()-----------"+buff.toString());
		
		return SUCCESS;
	}
	
	
	/************************银行信息保存*************************/
	// svn:songwj
	public String extendname;// 后缀名
	// svn:songwj
	private File myUpload;
	public String selectId;//svn:songwj
	
	@Resource
	private FileFormService  fileFormService;//svn:songwj
	@Resource
	private FTPIoadFileService fTPUploadFileimpl;//svn:songwj
	public String appointFileSetFolder="bankLogo";//svn:songwj
	private  String setname ;
	private Integer creatorid ;
	
	/**
	 * 添加及保存操作
	 */
	public String save(){
		 
		System.out.println("selectId----------------"+selectId);
		System.out.println("selectId----------------"+selectId);
		Map<String ,String > map = new  HashMap<String ,String >();
		if(selectId != null && !"".equals(selectId)){//如果主键存在，说明是友情链接进行的操作是修改+
			if(myUpload != null && !"".equals(myUpload)){//如果包含图片附件信息 说明前台选择了图片文件，需要上传操作
				map=fTPUploadFileimpl.ftpUploadFile(myUpload, appointFileSetFolder,"cs_bank","", selectId, extendname, setname, creatorid);
				webBankCode.setBankLogo(map.get("webpath"));
				webBankCodeService.merge(webBankCode);//先保存一条只有主键的数据
			}else{//没有图片文件信息，说明不需要上传操作，直接保存前台传来的数据信息
				//通过查询图片路径
				WebBankCode aa = null;
				aa  = webBankCodeService.get(webBankCode.getId());
				webBankCode.setBankLogo(aa.getBankLogo());
				webBankCodeService.merge(webBankCode);
			}
		}else  {//如果传进来的friendLinkId为null，或者“”说明是添加操作
			if(myUpload != null && !"".equals(myUpload)){
				WebBankCode aa	= webBankCodeService.save(webBankCode);
				selectId = aa.getId().toString();
				//上传文件
				map=fTPUploadFileimpl.ftpUploadFile(myUpload, appointFileSetFolder,"cs_bank","", selectId, extendname, setname, creatorid);
				aa.setBankLogo(map.get("webpath"));
				webBankCodeService.merge(aa);
			}else {//如果没有图片的文件信息，直接保存
				webBankCodeService.save(webBankCode);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
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

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
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


}
