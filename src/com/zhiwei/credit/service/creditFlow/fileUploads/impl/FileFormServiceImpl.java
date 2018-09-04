package com.zhiwei.credit.service.creditFlow.fileUploads.impl;



import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.contract.ProcreditContractDao;
import com.zhiwei.credit.dao.creditFlow.fileUploads.FileFormDao;
import com.zhiwei.credit.dao.system.FileAttachDao;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;

public class FileFormServiceImpl extends BaseServiceImpl<FileForm> implements
		FileFormService {
    private FileFormDao dao;
    @Resource
    private ProcreditContractDao procreditContractDao;
    @Resource
    private FileAttachDao fileAttachDao;
	public FileFormServiceImpl(FileFormDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<FileForm> findList(String projectId, String businessType) {
		
		return dao.findList(projectId, businessType);
	}
	@Override
	public FileForm findByMark(String mark) {
		// TODO Auto-generated method stub
		return dao.findByMark(mark);
	}
	@Override
	public void updateFile(String mark, Integer fileid) {
		dao.updateFile(mark, fileid);
	}
	@Override
	public FileForm getFileByMark(String mark) {
		
		return dao.getFileByMark(mark);
	}
	@Override
	public List<FileForm> listByProjectId(String projectId, String businessType) {
		
		return dao.listByProjectId(projectId, businessType);
	}
	@Override
	public FileForm getById(Integer fileId) {
		
		return dao.getById(fileId);
	}
	@Override
	public List<FileForm> listByMark(String mark) {
		
		return dao.listByMark(mark);
	}
	@Override
	public FileForm DeleFile(Integer id) {
		try {
			FileForm fileinfo = dao.getById(id);//根据cs_file主键值查询文件记录信息
			if(null!=fileinfo){
				if(fileinfo.getMark().indexOf("cs_procredit_contract")==0){//更新合同表中fileCount
					int contractId = Integer.parseInt(fileinfo.getMark().split("\\.")[1]);//获取合同主键
					ProcreditContract procreditContract =procreditContractDao.getById( contractId);//根据主键进行查询
					if(null==procreditContract.getFileCount()){
						procreditContract.setFileCount("0");
					}
					procreditContract.setFileCount((Integer.parseInt(procreditContract.getFileCount())-1)+"");//修改FileCount字段值
					procreditContractDao.merge(procreditContract);//更新数据库
				}
				dao.remove(fileinfo);
			}
			//删除FileAttach表
			fileAttachDao.removeByFileId(id);//删除服务器上的文件
			return fileinfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<FileForm> getFileList(String mark, String typeisfile,
			String projId, String businessType) {
		
		return dao.getFileList(mark, typeisfile, projId, businessType);
	}
	
	@Override
	//查询已上传文件svn：songwj
	public List<FileForm> getFileList(String mark) {
		return dao.getFileList(mark);
	}
	
	
	@Override
	public List<FileForm> ajaxGetFilesList(String typeisfile, String mark) {
		
		return dao.ajaxGetFilesList(typeisfile, mark);
	}
	@Override
	public List<FileForm> getAllFileList(String mark, String typeisfile,
			String projId, String businessType) {
		
		return dao.getAllFileList(mark, typeisfile, projId, businessType);
	}
	@Override
	public List<FileForm> getFilesSize(String mark, String typeisfile) {
		
		return dao.getFilesSize(mark, typeisfile);
	}
	@Override
	public List<FileForm> listFiles(String projId, String typeisfile,
			String mark, String businessType) {
		
		return dao.listFiles(projId, typeisfile, mark, businessType);
	}
	@Override
	public List<FileForm> getlistbyprojId(String projId, String businessType) {
		
		return dao.getlistbyprojId(projId, businessType);
	}
	@Override
	public void archiveConfirm(Boolean isArchiveConfirm, String archiveConfirmRemark,Date archiveConfirmDate,Boolean isSubmit,Date submitDate,String submitRemarks, Integer  fileId) {
		FileForm fileForm = this.getById(fileId);
		if(null != isArchiveConfirm) {
			fileForm.setIsArchiveConfirm(isArchiveConfirm);
		}
		if(null != archiveConfirmRemark) {
			fileForm.setArchiveConfirmRemark(archiveConfirmRemark);
		}
		if(null!=archiveConfirmDate){
			fileForm.setArchiveConfirmDate(archiveConfirmDate);
		}
		if(null!=isSubmit){
			fileForm.setIsSubmit(isSubmit);
		}
		if(null!=submitDate){
			fileForm.setSubmitDate(submitDate);
		}
		if(null!=submitRemarks){
			fileForm.setSubmitRemarks(submitRemarks);
		}
		try {
			dao.merge(fileForm);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@Override
	public List<FileForm> getArchiveListByBusinessType(String projectId,
			String mark, String businessType) {
		
		return dao.getArchiveListByBusinessType(projectId, mark, businessType);
	}
	@Override
	public Integer getFileListCount(String mark) {
		
		return dao.getFileListCount(mark);
	}
	@Override
	public FileForm getFileForm(Integer mark) {
		return dao.getFileForm(mark);
	}
	@Override
	public Long findFileCount(String mark) {
		return dao.findFileCount(mark);
	}
	@Override
	public List<FileForm> getFileListisMobile(int start, int limit,
			String mark, String typeisfile, String projId, String businessType) {
		// TODO Auto-generated method stub
		return dao.getFileListisMobile(start, limit, mark, typeisfile, projId, businessType);
	}
}
