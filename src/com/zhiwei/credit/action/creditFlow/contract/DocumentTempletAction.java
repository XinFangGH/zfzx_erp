package com.zhiwei.credit.action.creditFlow.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.core.util.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.FinancingElementCode;
import com.zhiwei.credit.model.creditFlow.contract.GuaranteeElementCode;
import com.zhiwei.credit.model.creditFlow.contract.LeaseFinanceElementCode;
import com.zhiwei.credit.model.creditFlow.contract.PawnElementCode;
import com.zhiwei.credit.model.creditFlow.contract.SmallLoanElementCode;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.creditFlow.contract.ConstantTemplateImpl;
import com.zhiwei.credit.service.creditFlow.contract.DocumentTempletService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;

public class DocumentTempletAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log logger=LogFactory.getLog(DocumentTempletAction.class);
	@Resource
	private DocumentTempletService documentTempletService ;
	@Resource
	private FileFormService fileFormService;
	private DocumentTemplet documentTemplet ;
	private int id;
	private int parentid ;
	private int fileId ;
	
	private String query ;
	private String mark ;
	private int templettype;
	private String businessType;
	private String type;
	private int leaf;
	
	
	public int getLeaf() {
		return leaf;
	}
	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public int getTemplettype() {
		return templettype;
	}
	public void setTemplettype(int templettype) {
		this.templettype = templettype;
	}
	
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public DocumentTemplet getDocumentTemplet() {
		return documentTemplet;
	}
	public void setDocumentTemplet(DocumentTemplet documentTemplet) {
		this.documentTemplet = documentTemplet;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	@LogResource(description = "添加或修改模板文档")
	public void add(){
		try {
			documentTempletService.add(documentTemplet) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getByType(){
		HttpServletRequest request = getRequest();
		List list = new ArrayList();
		try {
			int node = Integer.parseInt(request.getParameter("node"));
			//int templettype = Integer.parseInt(request.getParameter("templettype"));
			String businessType = request.getParameter("businessType");
			DocumentTemplet dtem = this.documentTempletService.getByIdTemId(businessType);
			if(dtem == null){
				//去除从数据字典里查询 by chencc
				/*if(node == 0){
					CreditDictionary dic = this.documentTempletService.getByDicId(businessType);
					DocumentTemplet templet = new DocumentTemplet();
					templet.setTemplettype(templettype);
					templet.setText(dic.getValue());
					templet.setParentid(0);
					templet.setLeaf(0);
					templet.setBusinessType(businessType);
					this.documentTempletService.add2(templet);
					dtem = this.documentTempletService.getByIdTemId(businessType);
					list.add(dtem);
					String json = JSONArray.fromObject(list).toString();
					JsonUtil.responseJsonString(json);
				}else
					documentTempletService.getByParentId(node);*/
			}else{
				List<DocumentTemplet> dlist=null;
				if(node == 0){
					dlist=documentTempletService.getByBusinessType(businessType) ;
				}else{
					dlist=documentTempletService.getByParentId(node);
				}
				String json = JSONArray.fromObject(dlist).toString();
				JsonUtil.responseJsonString(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获得合同树形列表出错:"+e.getMessage());
		}
	}
	
	public void getById(){
		try {
			DocumentTemplet d=documentTempletService.getById(id);
			JsonUtil.jsonFromObject(d, true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	public void update(){
		try {
			documentTempletService.update(documentTemplet);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void isHaveChildNode(){
		try {
			documentTempletService.isHave(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	@LogResource(description = "删除文档模板")
	public void deleteRs(){
		try {
			documentTempletService.delete(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	public void getListOtherDocumentTemplet(){
		try {
			PagingBean pb=new PagingBean(start,limit);
			List<DocumentTemplet> list=documentTempletService.listByTemplettype(ConstantTemplateImpl.CONTRACT_TEMPLATE, pb);
			List<DocumentTemplet> list1=documentTempletService.listByTemplettype(ConstantTemplateImpl.CONTRACT_TEMPLATE, null);
			int count=0;
			if(null!=list1 && list1.size()>0){
				count=list1.size();
			}
			JsonUtil.jsonFromList(list,count);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getByOnlyMarkDocumentTemplet(){
		HttpServletRequest request = getRequest() ;
		int node = Integer.parseInt(request.getParameter("node"));
		String onlymark = request.getParameter("onlymark");
		try {
			documentTempletService.getByOnlyMark(onlymark, true, 1 ,node);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void  sqlUpdate(){
		try {
			 documentTempletService.sqlUpdate(id, fileId);
			
			JsonUtil.responseJsonString("{success:true}");
			
		} catch (Exception e) {
			JsonUtil.responseJsonString("{success:false}");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void deleteTempletFile(){
		try {
			String url = super.getRequest().getRealPath("/");
			documentTempletService.deleteFile(id,url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void validateExist(){
		try {
			DocumentTemplet doc=documentTempletService.getById(id);
			JsonUtil.jsonFromObject(doc, true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void validateParam(){
		if(businessType == ""||"".equals(businessType)){
			JsonUtil.responseJsonString("{success:false}");
		}else
			JsonUtil.responseJsonString("{success:true}");
	}
	
	public void ajaxGetReportTemplate(){

		HttpServletResponse response = getResponse();
		DocumentTemplet dt = null;
		try {
			dt = documentTempletService.getByMarkAndBus(mark, businessType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dt != null){
			String onlymark = "cs_document_templet."+dt.getId();
			FileForm fileForm = null;
			fileForm = fileFormService.getFileByMark(onlymark);
			if(fileForm != null){
//				String path = ElementUtil.getUrl(fileForm.getFilepath(),fileForm.getFilename());
				String path = super.getRequest().getRealPath("/")+fileForm.getFilepath();
				ElementUtil.downloadFile(path, response);
			}
		}
		
	}
	
	public void ajaxGetReport(){
		DocumentTemplet dt = null;
		try {
			dt = documentTempletService.getByMarkAndBus(mark, businessType);
			
			if(dt != null){
				JsonUtil.responseJsonString("{success :true,text:\""+dt.getText()+"\"}");
			}else{
				JsonUtil.responseJsonFailure();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.responseJsonFailure();
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
	public void getByOnlymark(){
		DocumentTemplet dt = null;
		try {
			dt = documentTempletService.getByMarkAndBus(mark, businessType);
			if(dt != null && dt.isIsexist()){
			
				JsonUtil.jsonFromObject(dt, true);
			}else{
				JsonUtil.jsonFromObject(dt, false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			JsonUtil.jsonFromObject(dt, false);
		}
		
	}
	
	public void getByOnlymarkForUpload(){
		DocumentTemplet dt = null;
		try {
			dt = documentTempletService.getByMarkAndBus(mark, businessType);
			if(dt != null){
				JsonUtil.jsonFromObject(dt, true);
			}else{
				JsonUtil.jsonFromObject(dt, false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			JsonUtil.jsonFromObject(dt, false);
		}
		
	}
	
	public void getAllByParentId(){
		try {
			List<DocumentTemplet> list=documentTempletService.listByParentId(parentid);
			StringBuffer buff = new StringBuffer("[");
			if(null!=list && list.size()>0){
				
				for (DocumentTemplet dt : list) {
					buff.append("[").append(dt.getId()).append(",'").append(dt.getText()).append("'],");
				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
			}
			buff.append("]");
			JsonUtil.responseJsonString(buff.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public void getAllByOnlymark(){
		try {
			documentTempletService.getAllDocumentTempletByOnlymark(mark);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	public void getTempletByParentId(){
		try {
			String path=this.getRequest().getParameter("path");
			if(parentid == 0){
				PagingBean pb=new PagingBean(start,limit);
				List<DocumentTemplet> list = documentTempletService.getTempletIsDocument(businessType,pb);
				List<DocumentTemplet> list1 = documentTempletService.getTempletIsDocument(businessType,null);
				int totalProperty =0;
				if(null!=list1 && list1.size()>0){
					totalProperty=list1.size();
				}
				for(int i=0;i<list.size();i++){
					DocumentTemplet templet=(DocumentTemplet) list.get(i);
					String str=templet.getHandleFun();
					if(null!=str){
						String str1=str.replaceAll("__ctxPath", path);
						templet.setHandleFun(str1);
					}
				}
				JsonUtil.jsonFromList(list, totalProperty) ;
				return;
			}
			List<DocumentTemplet> list = null;
			int totalProperty = 0;
			if(leaf == 1){
				PagingBean pb=new PagingBean(start,limit);
				list = documentTempletService.getTempletByParentId(parentid,pb);
				List<DocumentTemplet> list1=documentTempletService.getTempletByParentId(parentid,null);
				if(null!=list1 && list1.size()>0){
					totalProperty=list1.size();
				}
				for(int i=0;i<list.size();i++){
					DocumentTemplet templet=(DocumentTemplet) list.get(i);
					String str=templet.getHandleFun();
					if(null!=str){
						String str1=str.replaceAll("__ctxPath", path);
						templet.setHandleFun(str1);
					}
				}
			}else{
				PagingBean pb=new PagingBean(start,limit);
				list = documentTempletService.getTempletByParentId(parentid,pb);
				List<DocumentTemplet> list1=documentTempletService.getTempletByParentId(parentid,null);
				if(null!=list1 && list1.size()>0){
					totalProperty=list1.size();
				}
				for(int i=0;i<list.size();i++){
					DocumentTemplet templet=(DocumentTemplet) list.get(i);
					String str=templet.getHandleFun();
					if(null!=str){
						String str1=str.replaceAll("__ctxPath", path);
						templet.setHandleFun(str1);
					}
				}
			}
			
			JsonUtil.jsonFromList(list, totalProperty) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.jsonFromObject("数据库查询出错，请重试", false) ;
			e.printStackTrace();
		}
	}

	
	public void getTempletIsDocument(){
		try {
			PagingBean pb=new PagingBean(start,limit);
			List<DocumentTemplet> list = documentTempletService.getTempletIsDocument(businessType,pb);
			List<DocumentTemplet> list1 = documentTempletService.getTempletIsDocument(businessType,null);
			int totalProperty = 0;
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
			JsonUtil.jsonFromList(list, totalProperty) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.jsonFromObject("数据库查询出错，请重试", false) ;
			e.printStackTrace();
		}
	}
	
	public void getDownLoadTemplet(){
		try {
			String onlymark[] ={};
			if(!mark.equals("")){
				onlymark = mark.split("@");
			}
			String path=this.getRequest().getParameter("path");
			PagingBean pb=new PagingBean(start,limit);
			List<DocumentTemplet> list = documentTempletService.getDownLoadTemplet(businessType,type,onlymark,pb);
			for(int i=0;i<list.size();i++){
				DocumentTemplet dt=(DocumentTemplet) list.get(i);
				String str=dt.getHandleFun();
				String str1=str.replaceAll("__ctxPath", path);
				dt.setHandleFun(str1);
				documentTempletService.evict(dt);
			}
			List<DocumentTemplet> list1 = documentTempletService.getDownLoadTemplet(businessType,type,onlymark,null);
			int totalProperty =0;
			if(null!=list1 && list1.size()>0){
				totalProperty=list1.size();
			}
			JsonUtil.jsonFromList(list, totalProperty) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonUtil.jsonFromObject("数据库查询出错，请重试", false) ;
			e.printStackTrace();
		}
	}
	
	public void updateById(){
		boolean flag  = false;
		try {
			documentTempletService.updateDocumentTempletById(documentTemplet);
			
			JsonUtil.responseJsonSuccess();
			
		} catch (Exception e) {
			JsonUtil.responseJsonFailure();
			e.printStackTrace();
		}
	}
	
	public void outputExcel(){
		try {
			List list = null ;
			String name = "";
			String []tableHeader ={"序号","要素描述","要素编码"};
			if("SmallLoan".equals(businessType)){
				name = "小额贷款系统要素表";
				SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
				list = ElementUtil.getListElement(smallLoanElementCode) ;
			}else if("Financing".equals(businessType)){
				name = "金融担保系统要素表";
				FinancingElementCode financingElementCode = new FinancingElementCode();
				list = ElementUtil.getListElement(financingElementCode);
			}else if("Guarantee".equals(businessType)){
				name = "资金融资系统要素表";
				GuaranteeElementCode guaranteeElementCode = new GuaranteeElementCode();
				list = ElementUtil.getListElement(guaranteeElementCode);
			}else if("LeaseFinance".equals(businessType)){
				name = "融资租赁系统要素表";
				LeaseFinanceElementCode leaseFinaceElementCode = new LeaseFinanceElementCode();
				list = ElementUtil.getListElement(leaseFinaceElementCode);
			}else if("Pawn".equals(businessType)){
				name = "典当系统要素表";
				PawnElementCode pawnElementCode = new PawnElementCode();
				list = ElementUtil.getListElement(pawnElementCode);
			}
			ExcelHelper.export(list,tableHeader,name+"-"+DateUtil.getNowDateTime().substring(0,DateUtil.getNowDateTime().lastIndexOf(" ")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String listByMark(){
		try{
			List<DocumentTemplet> list=documentTempletService.getListByOnlymark(mark);
			if(null!=list && list.size()>0){
				DocumentTemplet t=list.get(0);
				List<DocumentTemplet> dlist=documentTempletService.listByParentId(t.getId());
				StringBuffer buff = new StringBuffer("[");
				for (DocumentTemplet d : dlist) {
					buff.append("[").append(d.getId()).append(",'")
							.append(d.getText()).append("'],");
				}
				if (dlist.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				setJsonString(buff.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
