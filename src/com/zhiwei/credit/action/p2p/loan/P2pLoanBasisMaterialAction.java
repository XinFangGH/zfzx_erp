package com.zhiwei.credit.action.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.p2p.loan.P2pLoanBasisMaterial;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanBasisMaterialService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class P2pLoanBasisMaterialAction extends BaseAction{
	@Resource
	private P2pLoanBasisMaterialService p2pLoanBasisMaterialService;
	@Resource
	private FTPIoadFileService fTPUploadFileimpl;
	@Resource
	private FileFormService fileFormService;
	private P2pLoanBasisMaterial p2pLoanBasisMaterial;
	
	private Long materialId;
	private Long productId;
	private String operationType;
	
	public String extendname;
	public String mark;
	public String tablename;
	
	public String setname;
	public Integer creatorid;
	
	private File fileUpload;
   
	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
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

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public P2pLoanBasisMaterial getP2pLoanBasisMaterial() {
		return p2pLoanBasisMaterial;
	}

	public void setP2pLoanBasisMaterial(P2pLoanBasisMaterial p2pLoanBasisMaterial) {
		this.p2pLoanBasisMaterial = p2pLoanBasisMaterial;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		String Q_materialState_L_EQ = this.getRequest().getParameter("Q_materialState");
		String Q_operationType_S_EQ = this.getRequest().getParameter("Q_aoperationType");
		if(Q_materialState_L_EQ!=null&&!Q_materialState_L_EQ.equals("")){
			filter.addFilter("Q_materialState_L_EQ", Q_materialState_L_EQ);
		}
		if(Q_operationType_S_EQ!=null&&!Q_operationType_S_EQ.equals("")){
			filter.addFilter("Q_operationType_S_EQ", Q_operationType_S_EQ);
		}
		filter.addFilter("Q_materialState_L_GT", "0");
		List<P2pLoanBasisMaterial> list= p2pLoanBasisMaterialService.getAll(filter);
		
		Type type=new TypeToken<List<P2pLoanBasisMaterial>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 显示列表（添加基础材料）
	 */
	public String listByProduct(){
		List<P2pLoanBasisMaterial> list=  new ArrayList<P2pLoanBasisMaterial>();
		if(null!=productId && !"".equals(productId)){
			list=p2pLoanBasisMaterialService.findBasisList(productId,operationType);
		}
		Type type=new TypeToken<List<P2pLoanBasisMaterial>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(null!=list?list.size():0).append(",result:");
    	Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
	//	System.out.println("-->"+jsonString);
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String ids=getRequest().getParameter("ids");
		if(ids!=null){
			String id[] = ids.split(",");
			for(int i=0;i<id.length;i++){
				P2pLoanBasisMaterial p2pLoanBasisMaterial = p2pLoanBasisMaterialService.get(new Long(id[i]));
				p2pLoanBasisMaterial.setMaterialState(Long.valueOf("0"));//假删除
				p2pLoanBasisMaterialService.save(p2pLoanBasisMaterial);
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
		P2pLoanBasisMaterial p2pLoanBasisMaterial=p2pLoanBasisMaterialService.get(materialId);
		
		FileForm fileForm1_shilitu1 = fileFormService.getFileByMark("p2p_loan_basismaterial.shilitu1."+p2pLoanBasisMaterial.getMaterialId());
		FileForm fileForm2_shilitu2 = fileFormService.getFileByMark("p2p_loan_basismaterial.shilitu2."+p2pLoanBasisMaterial.getMaterialId());
		if(fileForm1_shilitu1!= null){
			p2pLoanBasisMaterial.setShilitu1Id(fileForm1_shilitu1.getFileid());
			p2pLoanBasisMaterial.setShilitu1URL(fileForm1_shilitu1.getWebPath());
			p2pLoanBasisMaterial.setShilitu1ExtendName(fileForm1_shilitu1.getExtendname());
		}
		if(fileForm2_shilitu2 != null){
			p2pLoanBasisMaterial.setShilitu2Id(fileForm2_shilitu2.getFileid());
			p2pLoanBasisMaterial.setShilitu2URL(fileForm2_shilitu2.getWebPath());
			p2pLoanBasisMaterial.setShilitu2ExtendName(fileForm2_shilitu2.getExtendname());
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(p2pLoanBasisMaterial));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(p2pLoanBasisMaterial.getMaterialId()==null){
			p2pLoanBasisMaterialService.save(p2pLoanBasisMaterial);
			Integer shilitu1Id=p2pLoanBasisMaterial.getShilitu1Id();
			Integer shilitu2Id=p2pLoanBasisMaterial.getShilitu2Id();
			if(shilitu1Id != null && !shilitu1Id.equals("")){ //示例图1
				fileFormService.updateFile("p2p_loan_basismaterial.shilitu1."+p2pLoanBasisMaterial.getMaterialId(), shilitu1Id);
			}
			if(shilitu2Id != null && !shilitu2Id.equals("")){ //示例图2
				fileFormService.updateFile("p2p_loan_basismaterial.shilitu2."+p2pLoanBasisMaterial.getMaterialId(), shilitu2Id);
			}
		}else{
			P2pLoanBasisMaterial orgP2pLoanBasisMaterial=p2pLoanBasisMaterialService.get(p2pLoanBasisMaterial.getMaterialId());
			try{
				BeanUtil.copyNotNullProperties(orgP2pLoanBasisMaterial, p2pLoanBasisMaterial);
				p2pLoanBasisMaterialService.save(orgP2pLoanBasisMaterial);
			/*	Integer shilitu1Id=p2pLoanBasisMaterial.getShilitu1Id();
				Integer shilitu2Id=p2pLoanBasisMaterial.getShilitu2Id();
				if(shilitu1Id != null && !shilitu1Id.equals("")){ //示例图1
					fileFormService.updateFile("p2p_loan_basismaterial."+orgP2pLoanBasisMaterial.getMaterialId(), shilitu1Id);
				}
				if(shilitu2Id != null && !shilitu2Id.equals("")){ //示例图2
					fileFormService.updateFile("p2p_loan_basismaterial."+orgP2pLoanBasisMaterial.getMaterialId(), shilitu2Id);
				}*/
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 上传示例图片
	 */
	public void uploadPhoto(){
	 	HttpServletRequest request = getRequest();
		String mark = getRequest().getParameter("mark");
		//得到当前时间，精确到毫秒
		//String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String selectId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String tablenameFirst = mark;//获得第一个表的名称
		String appointFileSetFolder ="webfile";//指定文件存放的路径
		Map<String,String> map = new  HashMap<String ,String >();
		map=fTPUploadFileimpl.ftpUploadFile(fileUpload,appointFileSetFolder, tablenameFirst,null, selectId, extendname, setname, creatorid);
		String jsonStr = "{success : true,fileid :"+map.get("filedId")+",webPath :'"+map.get("webpath")+"'}";
		JsonUtil.responseJsonString(jsonStr,"text/html; charset=utf-8");
		
	}
}
