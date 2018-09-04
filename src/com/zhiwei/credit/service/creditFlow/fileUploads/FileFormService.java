package com.zhiwei.credit.service.creditFlow.fileUploads;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;

public interface FileFormService extends BaseService<FileForm> {
    public List<FileForm> findList(String projectId,String businessType);
    public FileForm findByMark(String mark);
    public void updateFile(String mark,Integer fileid);
    public FileForm getFileByMark(String mark);
    public List<FileForm> listByProjectId(String projectId, String businessType);
    public FileForm getById(Integer fileId);
    public List<FileForm> listByMark(String mark);
    public FileForm DeleFile(Integer id);
    
    public List<FileForm> getFileList(String mark,String typeisfile,String projId,String businessType);
    
    //svnL:songwj
    //查询已上传的文件
    public List<FileForm> getFileList(String mark);
    
    
    public List<FileForm> ajaxGetFilesList(String typeisfile,String mark);
    public List<FileForm> getAllFileList(String mark, String typeisfile, String projId,
			String businessType);
    public List<FileForm> getFilesSize(String mark,String typeisfile);
    public List<FileForm> listFiles(String projId,String typeisfile,String mark,String businessType);
    public List<FileForm> getlistbyprojId(String projId,String businessType);
	public void archiveConfirm(Boolean isArchiveConfirm, String archiveConfirmRemark,Date archiveConfirmDate,Boolean isSubmit,Date submitDate,String submitRemarks, Integer  fileId);
	public List<FileForm> getArchiveListByBusinessType(String projectId,String mark,String businessType);
	public Integer getFileListCount(String mark);
	public FileForm getFileForm(Integer mark);
	public Long findFileCount(String mark);
	public List<FileForm> getFileListisMobile(int start,int limit,String mark,String typeisfile,String projId,String businessType);
}
