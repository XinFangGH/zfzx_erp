package com.zhiwei.credit.service.creditFlow.contract.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.dao.creditFlow.contract.DocumentTempletDao;
import com.zhiwei.credit.dao.creditFlow.fileUploads.FileFormDao;
import com.zhiwei.credit.dao.system.FileAttachDao;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.service.creditFlow.contract.DocumentTempletService;

public class DocumentTempletServiceImpl extends
		BaseServiceImpl<DocumentTemplet> implements
		DocumentTempletService {
    private DocumentTempletDao dao;
    @Resource
    private FileFormDao fileFormDao;
    @Resource
    private FileAttachDao fileAttachDao;
    private static final String uploadPath="attachFiles/uploads"; //@HT_version3.0
	public DocumentTempletServiceImpl(DocumentTempletDao dao) {
		super(dao);
		this.dao=dao;
		
	}
	@Override
	public DocumentTemplet getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public DocumentTemplet getByIdTemId(String businessType) {
		
		return dao.getByIdTemId(businessType);
	}
	@Override
	public List<DocumentTemplet> getByBusinessType(String businessType) {
		
		return dao.getByBusinessType(businessType);
	}
	@Override
	public List<DocumentTemplet> getByParentId(int parentid) {
		
		return dao.getByParentId(parentid);
	}
	@Override
	public void add(DocumentTemplet templet){
		try{
		if(templet.getIsChild()!=null){
			List<DocumentTemplet> list=dao.getListByText(templet.getBusinessType(),templet.getText());
			if(null!=list && list.size()>0){
				JsonUtil.responseJsonString("{success:true,mark : false,msg:'添加类型重复'}");
				return ;
			}
			List<DocumentTemplet> list1=dao.getListByOnlymark(templet.getOnlymark());
			if(null!=list1 && list1.size()>0){
				JsonUtil.responseJsonString("{success:true,mark : false ,msg:'添加唯一标识重复'}");
				return ;
			}
		}
		dao.save(templet);
		
			
			if(templet.getIsChild()!=null){
			if((templet.getIsChild()== 2 && templet.getParentid() != 0)||(templet.getIsChild()== 10 && templet.getParentid() == 0)){
				List<DocumentTemplet> dlist=dao.getByText(templet.getText());
				if(null!=dlist && dlist.size()>0){
					templet =dlist.get(0);
				}
//				templet.setHandleFun("<img title='上传模板文件' src='__ctxPath/images/upload-start.gif' onclick='uploadTemplate("+templet.getId()+")'/>");
				templet.setHandleFun(String.valueOf(templet.getId()));

				dao.merge(templet);
			}
			}
			JsonUtil.responseJsonString("{success:true,mark : true,msg:'操作成功'}");
		}catch(Exception e){
			JsonUtil.responseJsonString("{success:false,msg:'操作失败'}");
			e.printStackTrace();
		}
			//log.info("save DocumentTemplet error");
			
		
	}
	@Override
	public List<DocumentTemplet> getListByOnlymark(String onlymark) {
		
		return dao.getListByOnlymark(onlymark);
	}
	@Override
	public List<DocumentTemplet> getListByText(String businessType, String text) {
		
		return dao.getListByText(businessType, text);
	}
	@Override
	public List<DocumentTemplet> getByText(String text) {
		
		return dao.getByText(text);
	}
	public void update(DocumentTemplet templet){
		
		try{
			List<DocumentTemplet> list=dao.getByTextAndId(templet.getText(), templet.getId());
			if(null!=list && list.size()>0){
				JsonUtil.responseJsonString("{success:true,mark : false,msg:'添加类型重复'}");
				return ;
			}
			List<DocumentTemplet> list1=dao.getByMarkAndId(templet.getOnlymark(), templet.getId());
			if(null!=list1 && list1.size()>0){
				JsonUtil.responseJsonString("{success:true,mark : false ,msg:'添加唯一标示重复'}");
				return ;
			}
			if(templet.getParentid() == 0){
				dao.merge(templet);
				
			}else{
				DocumentTemplet tem =dao.getById(templet.getId());
				Object[] obj = {tem.getParentid(),tem.getId()} ;
				if(tem.getLeaf()==0 && templet.getLeaf()!=0 && tem.getOnlymark() == null){
					dao.updateTemplet(tem.getParentid(), tem.getId());
					
					templet.setHandleFun("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img title='上传模板文件' src='__ctxPath/images/upload-start.png' onclick='uploadTemplate("+templet.getId()+")' />");
				}else if(tem.isIsexist() && tem.getLeaf()!=0 && templet.getLeaf()==0){
					JsonUtil.responseJsonString("{success:false,mark : false,msg:'修改目录类型必须先删除附件,再修改'}");
					return ;
				}else if(tem.getLeaf()!=0 && templet.getLeaf()==0 && tem.getOnlymark() == null){
					templet.setTextoo("");
				}else if(tem.getLeaf()!=0  && templet.getLeaf()==0 && tem.getOnlymark() != null){
					templet.setTextoo("");
				}
				dao.merge(templet);
			}
		
			JsonUtil.responseJsonString("{success:true,mark : true,msg:'修改成功'}");
		}catch(Exception e){
			JsonUtil.responseJsonString("{success:false,msg:'修改失败'}");
			e.printStackTrace();
		}
			
		
	}
	@Override
	public List<DocumentTemplet> getByMarkAndId(String mark, Integer id) {
		
		return dao.getByMarkAndId(mark, id);
	}
	@Override
	public List<DocumentTemplet> getByTextAndId(String text, Integer id) {
	
		return dao.getByTextAndId(text, id);
	}
	@Override
	public void updateTemplet(Integer parentId, Integer parentId1) {
		dao.updateTemplet(parentId, parentId1);
		
	}
	@Override
	public List<DocumentTemplet> listByParentId(Integer parentid) {
		
		return dao.listByParentId(parentid);
	}
	public void isHave(int id){
		try{
			List<DocumentTemplet> list = this.listByParentId(id);
			if(list == null){
				delete(id);
			}else{
				JsonUtil.responseJsonString("{success:true,mark:false,msg:'删除该 数据会连该数据下的子节点也也一起删除，您时候确认删除'}");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void delete(int id) {
		try{
			DocumentTemplet documentTemplet =dao.getById(id);
			List<DocumentTemplet> delList = new ArrayList<DocumentTemplet>();
			if(null != documentTemplet){
				delList.add(documentTemplet);
				List<DocumentTemplet> list = this.listByParentId(id);
				if(null != list){
					for(Iterator iter = list.iterator();iter.hasNext();){
						documentTemplet = (DocumentTemplet)iter.next();
						delList.add(documentTemplet);
						List<DocumentTemplet> listo =this.listByParentId(documentTemplet.getId());
						if(listo != null){
							for(Iterator itera = listo.iterator();itera.hasNext();){
								documentTemplet = (DocumentTemplet)itera.next();
								delList.add(documentTemplet);
								List<DocumentTemplet> listot =this.listByParentId(documentTemplet.getId()); 
								if(listot != null){
									for(Iterator iterat = listot.iterator();iterat.hasNext();){
										documentTemplet = (DocumentTemplet)iterat.next();
										delList.add(documentTemplet);
									}
								}
							}
						}
					}
				}else{
					delList.add(documentTemplet);
				}
			}
			for(DocumentTemplet d:delList){
				if(null!=d){
					dao.remove(d);
				}
			}
			
			JsonUtil.responseJsonString("{success:true,mark:true,msg:'删除成功'}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public List<DocumentTemplet> listByTemplettype(Integer templettype,
			PagingBean pb) {
		
		return dao.listByTemplettype(templettype, pb);
	}
	@Override
	public List<DocumentTemplet> listByMarkAndType(String mark,
			Integer templettype) {
		
		return dao.listByMarkAndType(mark, templettype);
	}
	@Override
	public List<DocumentTemplet> listByParentAndType(Integer parentId,
			Integer templettype) {
		
		return dao.listByParentAndType(parentId, templettype);
	}
	@Override
	public List<DocumentTemplet> listByexistAndType(Boolean isexist,
			Integer templettype, Integer parentId) {
		
		return dao.listByexistAndType(isexist, templettype, parentId);
	}
	public void getByOnlyMark(String onlymark ,boolean isexist ,int templettype,int node) {
		try{
			String hql = "from DocumentTemplet AS dt where dt.onlymark=? and dt.templettype=?" ;
			List<DocumentTemplet> list = null ; 
			List<DocumentTemplet> arrList = new ArrayList<DocumentTemplet>();
			if(node == 0){
				list = dao.listByMarkAndType(onlymark, templettype);
			}else if(node != 0){
				Object obj[] = {node ,templettype,isexist};
				list =dao.listByexistAndType(isexist, templettype, node);
				if(list.size() <= 0 ){
					list =dao.listByParentAndType(node, templettype);
				}
			}
			if(null == list){
				//log.info("getByOnlyMark DocumentTemplet error");
				JsonUtil.responseJsonString("{success:false,msg:'操作失败'}");
			}else{
				//log.info("getByOnlyMark DocumentTemplet succeed");
				for(int i = 0 ; i < list.size() ; i ++){
					DocumentTemplet tem = (DocumentTemplet)list.get(i) ;
					if(tem.getLeaf()!=0){
						arrList.add(tem);
					}else{
						DocumentTemplet temf = dao.getById(tem.getId());
						arrList.add(temf);
					}
				}
				JsonUtil.responseJsonString(JSONArray.fromObject(arrList).toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void sqlUpdate(int id , int fildId){
		try{
			DocumentTemplet templet = dao.getById(id);
//			templet.setHandleFun("<img title='删除模板文件' src='__ctxPath/images/reset.gif' onclick='deleteTempletFile("+templet.getId()+")'/>&nbsp;&nbsp;&nbsp;<img title='查看模板需要的要素' src='__ctxPath/images/properties.gif' onclick='seeTempletElement("+id+")'/>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img title='查看模板文本内容' src='__ctxPath/images/contacts_shared.png' onclick='seeTemplet("+id+")'/>");

			templet.setFileid(fildId);
			templet.setIsexist(true);
			dao.merge(templet);
		}catch(Exception e){
			e.printStackTrace();
		}
	};
	public void deleteFile(int id,String url){
		try{
			DocumentTemplet tem = dao.getById(id);
			FileForm form =fileFormDao.getById(tem.getFileid());
		    fileAttachDao.removeByFileId(tem.getFileid());
			if(form !=null){
				fileFormDao.remove(form);
				String[] s = form.getFilepath().split(uploadPath);
				String t = s[1].toString().substring(1);
				String k = s[1].toString().substring(1, t.indexOf("/")+1); //@HT_version3.0 将\\改成/
				String delPath = s[0]+ uploadPath+"/" + k ; //@HT_version3.0 将\\改成/
				File f = new File(url+delPath);
				FileHelper.deleteDir(f);
			}
	

//			tem.setHandleFun("<img title='上传模板文件' src='__ctxPath/images/upload-start.gif' onclick='uploadTemplate("+tem.getId()+")'/>");
			tem.setHandleFun(String.valueOf(tem.getId()));

			tem.setFileid(0);
			tem.setIsexist(false);
			dao.merge(tem);
	
			JsonUtil.responseJsonString("{success:true,msg:'删除文件成功'}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public DocumentTemplet getByMarkAndBus(String onlymark, String businessType) {
		
		return dao.getByMarkAndBus(onlymark, businessType);
	}
	public void getAllDocumentTempletByOnlymark(String mark){
		try{
			List<DocumentTemplet> list =dao.getListByOnlymark(mark);
			if(list != null && list.size()>0){
				DocumentTemplet dtt = list.get(0);
				List<DocumentTemplet> list2 =dao.listByParentId(dtt.getId());
				StringBuffer buff = new StringBuffer("[");
				if(null!=list2 && list2.size()>0){
					
					for (DocumentTemplet dt : list2) {
						buff.append("[").append(dt.getId()).append(",'").append(dt.getText()).append("'],");
					}
					if (list2.size() > 0) {
						buff.deleteCharAt(buff.length() - 1);
					}
				}
				buff.append("]");
				JsonUtil.responseJsonString(buff.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public List<DocumentTemplet> getTempletIsDocument(String businessType,
			PagingBean pb) {
		
		return dao.getTempletIsDocument(businessType, pb);
	}
	@Override
	public List<DocumentTemplet> getTempletByParentId(int parentid, PagingBean pb) {
		
		return dao.getTempletByParentId(parentid, pb);
	}
	@Override
	public List<DocumentTemplet> getDownLoadTemplet(String businessType,
			String type, String[] str, PagingBean pb) {
		
		return dao.getDownLoadTemplet(businessType, type, str, pb);
	}
	public void updateDocumentTempletById(DocumentTemplet documentTemplet){
		try{
			DocumentTemplet dt = null;
			dt =dao.getById(documentTemplet.getId());
			if(dt != null){
				if(documentTemplet.getText()!= null){
					dt.setText(documentTemplet.getText());
				}
				if(documentTemplet.getRemarks()!= null){
					dt.setRemarks(documentTemplet.getRemarks());
				}
				if(documentTemplet.getOrderNum()!= 0){
					dt.setOrderNum(documentTemplet.getOrderNum());
				}
				dao.merge(dt);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public DocumentTemplet findDocumentTemplet(String mark) {
		
		return dao.findDocumentTemplet(mark);
	}
	@Override
	public DocumentTemplet getTempletObj(int id) {
		
		return dao.getTempletObj(id);
	}
}