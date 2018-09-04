package com.zhiwei.credit.action.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiweii Software Company
*/
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.communicate.MailBox;
import com.zhiwei.credit.model.communicate.MailFolder;
import com.zhiwei.credit.service.communicate.MailBoxService;
import com.zhiwei.credit.service.communicate.MailFolderService;
/**
 * 
 * @author csx
 *
 */
public class MailFolderAction extends BaseAction{
	static long FOLDER_ID_RECEIVE = 1l;//收件箱
	static long FOLDER_ID_SEND = 2l;//发件箱
	static long FOLDER_ID_DRAFT = 3l;//草稿箱
	static long FOLDER_ID_DELETE = 4l;//删除箱
	
	static short OTHER_FOLDER_TYPE =(short)10;//其它类型文件夹
	static int FIRST_LEVEL = 1;//第一层
	static long FIRST_LEVEL_PARENTID = 0l;//第一层文件夹的parentId
	@Resource
	private MailFolderService mailFolderService;
	@Resource
	private MailBoxService mailBoxService;
	
	private MailFolder mailFolder;
	
	private Long folderId;

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public MailFolder getMailFolder() {
		return mailFolder;
	}

	public void setMailFolder(MailFolder mailFolder) {
		this.mailFolder = mailFolder;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'我的邮箱',iconCls:'menu-mail_box',expanded:true,children:[");
		Long curUserId=ContextUtil.getCurrentUserId();
		List<MailFolder> mailFolderList=mailFolderService.getAllUserFolderByParentId(curUserId, 0l);//最顶层父节点
		for(MailFolder folder:mailFolderList){
			buff.append("{id:'"+folder.getFolderId()).append("',text:'"+folder.getFolderName()).append("',");
			Long folderId = folder.getFolderId();
			if(folderId == FOLDER_ID_RECEIVE){//收件箱图标
				buff.append("iconCls:'menu-mail_inbox',");
			}else if(folderId == FOLDER_ID_SEND){//发件箱图标
				buff.append("iconCls:'menu-mail_outbox',");
			}else if(folderId == FOLDER_ID_DRAFT){//草稿箱图标
				buff.append("iconCls:'menu-mail_drafts',");
			}else if(folderId == FOLDER_ID_DELETE){//垃圾箱图标
				buff.append("iconCls:'menu-mail_trash',");
			}else {//其他文件夹图标
				buff.append("iconCls:'menu-mail_folder',");
			}
		    buff.append(findChildsFolder(curUserId,folder.getFolderId()));
		}
		if (!mailFolderList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		
		logger.info("tree json:" + buff.toString());
		
		return SUCCESS;
	}
	
	public String findChildsFolder(Long userId,Long parentId){
		StringBuffer sb=new StringBuffer();
		List<MailFolder> folders=mailFolderService.getUserFolderByParentId(userId, parentId);
		if(folders.size()==0){
			sb.append("leaf:true,expanded:true},");
			return sb.toString(); 
		}else {
			sb.append("children:[");
			for(MailFolder folder:folders){				
				sb.append("{id:'"+folder.getFolderId()+"',text:'"+folder.getFolderName()+"',");
				sb.append("iconCls:'menu-mail_folder',");//文件夹图标
				sb.append(findChildsFolder(userId,folder.getFolderId()));
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]},");
			return sb.toString();
		}
	}
	
	/**
	 * 批量删除--未用到
	 * @return
	 */
	public String multiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				mailFolderService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	/**
	 * 删除文件夹时查询出该文件夹及其子文件夹的邮件数
	 * @return
	 */
	public String count(){
		MailFolder tmpFolder=mailFolderService.get(new Long(folderId));
		List<MailFolder> mailFolderList=mailFolderService.getFolderLikePath(tmpFolder.getPath());
		//查询出该目录及其子目录下的邮件数
		Long total = 0l;
		for (MailFolder folder : mailFolderList){
			Long count = mailBoxService.CountByFolderId(folder.getFolderId());
			total += count ;
		}
		
		setJsonString("{success:true,count:"+total+"}");
		return SUCCESS;
	}
	
	/**
	 * 删除文件
	 * @return
	 */
	public String remove(){
		String count=getRequest().getParameter("count");
		if(folderId!=null){
			MailFolder tmpFolder=mailFolderService.get(new Long(folderId));
			List<MailFolder> mailFolderList=mailFolderService.getFolderLikePath(tmpFolder.getPath());
			
			//假如文件夹中的邮件数大于0,则把邮件转到删除箱中
			if(count!=null&&new Long(count)>0){
				MailFolder deleteFolder = mailFolderService.get(4l);//获得删除箱
				for(MailFolder folder : mailFolderList){
					List<MailBox> mailBoxList = mailBoxService.findByFolderId(folder.getFolderId());
					for(MailBox mailBox : mailBoxList){
						mailBox.setMailFolder(deleteFolder);
						mailBoxService.save(mailBox);
					}
				}
			}
			
			//批量删除其下的目录
			for(MailFolder folder:mailFolderList){
				mailFolderService.remove(folder.getFolderId());
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
		MailFolder mailFolder=mailFolderService.get(folderId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(mailFolder));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		MailFolder parentFolder = null;
		Long parentId = mailFolder.getParentId();
		if(parentId == null || parentId== FIRST_LEVEL_PARENTID){
			mailFolder.setParentId(new Long(FIRST_LEVEL_PARENTID));
			mailFolder.setDepLevel(FIRST_LEVEL);
		}else{
			parentFolder = mailFolderService.get(parentId);
			mailFolder.setDepLevel(parentFolder.getDepLevel()+1);
		}
		mailFolder.setFolderType(OTHER_FOLDER_TYPE);
		mailFolder.setUserId(ContextUtil.getCurrentUserId());
		mailFolderService.save(mailFolder);
		
		//保存后获得folderId，根据folderId设置path
		if(mailFolder.getParentId()==FIRST_LEVEL_PARENTID){
			mailFolder.setPath("0."+mailFolder.getFolderId()+".");
		}else{
			mailFolder.setPath(parentFolder.getPath()+mailFolder.getFolderId()+".");
		}
		mailFolderService.save(mailFolder);
		setJsonString("{success:true}"); 
		return SUCCESS;
	}
}
