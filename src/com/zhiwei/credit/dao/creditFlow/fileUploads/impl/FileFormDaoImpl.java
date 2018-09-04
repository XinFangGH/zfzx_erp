package com.zhiwei.credit.dao.creditFlow.fileUploads.impl;



import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.fileUploads.FileFormDao;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;

public class FileFormDaoImpl extends BaseDaoImpl<FileForm> implements
		FileFormDao {
	@Resource
	private CreditBaseDao creditBaseDao;
	public FileFormDaoImpl() {
		super(FileForm.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FileForm> findList(String projectId, String businessType) {
		String hql="from FileForm as f where f.projId=? and f.businessType=? and ((f.remark='typeisglborrowguarantee' and f.mark='gl_borrow_guarantee."+projectId+"') or (f.remark='typeisgldbguarantee' and f.mark='gl_db_guarantee."+projectId+"') or (f.remark='typeisonlyfile' and f.mark='typeisdbsbhjywd."+projectId+"'))";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public FileForm findByMark(String mark) {
		String hql="from FileForm as f where f.mark=?";
		List<FileForm> list= getSession().createQuery(hql).setParameter(0, mark).list();
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateFile(String mark, Integer fileid) {
		String hql="update FileForm set mark=? where fileid=?";
		getSession().createQuery(hql).setParameter(0, mark).setParameter(1, fileid).executeUpdate();
	}
	@Override
	public FileForm getFileByMark(String mark) {
		FileForm fileinfo = new FileForm() ;
		String hql = "from FileForm f where f.mark=? and f.remark is null order by f.fileid asc"	 ;
		try {
			List list = creditBaseDao.queryHql(hql,mark);
			if(list !=null){
				if(list.size()==1){
					fileinfo = (FileForm)list.get(0);
				}else{
					fileinfo = (FileForm)list.get(list.size()-1) ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileinfo ;
	}

	@Override
	public List<FileForm> listByProjectId(String projectId, String businessType) {
		String hql="from FileForm AS ff where ff.projId = ? and ff.businessType = ?";
		return this.findByHql(hql, new Object[]{projectId,businessType});
	}

	//根据cs_file表的主键进行查询路径
	@Override
	public FileForm getById(Integer fileId) {
		String hql="from FileForm as f where f.fileid=?";
		return (FileForm) this.getSession().createQuery(hql).setParameter(0, fileId).uniqueResult();
	}

	@Override
	public List<FileForm> listByMark(String mark) {
		String hql="from FileForm as f where f.mark=?";
		return getSession().createQuery(hql).setParameter(0, mark).list();
	}
	
	//根据mark进行上传文件的查询
	//svn：songwj
	@Override
	public List<FileForm> getFileList(String mark){
		if(mark != null && !mark.equals("")){
			String hql = "from FileForm a where a.mark=?";
			return this.findByHql(hql, new Object[]{mark});
		}
		return null;
	}
	
	@Override
	public List<FileForm> getFileList(String mark,String typeisfile,String projId,String businessType) {
		if(typeisfile == null){
			String hql = "from FileForm a where a.mark=? and a.remark is null";
			try {
				return this.findByHql(hql, new Object[]{mark});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(typeisfile != null &&null==businessType){
			//String hql = "from FileForm a where a.mark=? and a.remark is null";
			String hql = "from FileForm a where a.remark=?";
			try {
				return this.findByHql(hql, new Object[]{mark});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(null!=mark&&null!=projId){
			String hql =  "from FileForm a where a.mark=? and a.projId = ?";
			try {
				return this.findByHql(hql, new Object[]{mark,projId});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(null!=mark){
			String hql = "from FileForm a where a.mark=?";
			try {
				return this.findByHql(hql, new Object[]{mark});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			String hql = "from FileForm a where (a.mark=? or a.remark=?) and a.projId=? and businessType=?";
			try {
				Object []object ={mark,typeisfile,projId,businessType};
				return this.findByHql(hql, object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;

	}
	@Override
	public List<FileForm> ajaxGetFilesList(String typeisfile,String mark){
		String hql="from FileForm AS f where f.remark=? and f.mark=?";
		Object [] object = {typeisfile,mark};
		return this.findByHql(hql, object);
	}
	@Override
	public List<FileForm> getAllFileList(String mark, String typeisfile, String projId,
			String businessType) {
		if(typeisfile == null){
//			String hql = "from FileForm a where a.remark is null";//不明白 by gao
			String hql = "from FileForm a where a.mark like '%"+mark +"%' and a.businessType = '"+ businessType+"' and a.projId = '" + projId+"'";
			try {
				return this.findByHql(hql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(typeisfile != null &&null==businessType){
			//String hql = "from FileForm a where a.mark=? and a.remark is null";
			String hql = "from FileForm a where a.remark=?";
			try {
				return this.findByHql(hql, new Object[]{mark});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			String hql = "from FileForm a where a.remark=? and a.projId=? and businessType=?";
			try {
				Object []object ={mark,typeisfile,projId,businessType};
				return this.findByHql(hql, object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return null;
	}
	@Override
	public List<FileForm> getFilesSize(String mark,String typeisfile){
		List<FileForm> list= null;
		if(typeisfile == null){
			String hql = "from FileForm a where a.mark=?";
			try {
				list = creditBaseDao.queryHql(hql, mark);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			String hql = "from FileForm a where a.mark=? and a.remark=?";
			try {
				String []object ={mark,typeisfile};
				list = creditBaseDao.queryHql(hql, object);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}
	@Override
	public List<FileForm> listFiles(String projId,String typeisfile,String mark,String businessType){
		String hql="from FileForm AS f where f.remark=? and f.mark=? and f.projId=? and f.businessType=?";
		List<FileForm> list = null;
		Object[] object = {typeisfile,mark,projId,businessType};
		try {
			list =this.findByHql(hql, object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<FileForm> getlistbyprojId(String projId,String businessType){
		String hql="from FileForm AS f where projId=? and businessType=? and mark='typeisdbzrjchsmj."+projId+"' or mark='back_gl_bank_guaranteemoney."+projId+"' or mark='gl_actual_to_charge."+projId+"'";
		List<FileForm> list = null;
		Object [] object = {projId,businessType};
		try {
			list =this.findByHql(hql,object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<FileForm> getArchiveListByBusinessType(String projectId,String mark,String businessType){
		Object []params ={businessType,projectId.toString()};
		String hql = "from FileForm AS f where f.businessType=? and f.projId=? and f.remark in("+mark+")";
		return this.findByHql(hql, params);
	}
	@Override
	public Integer getFileListCount(String mark) {
		String hql = "from FileForm a where a.mark='"+mark+"'"; 
		int count=0;
		List<FileForm> list=this.findByHql(hql);
		if(null!=list && list.size()>0){
			count=list.size();
		}
		return count;
	}

	@Override
	public FileForm getFileForm(Integer mark) {
		String hql="from FileForm as f where f.mark=?";
		List<FileForm> list= getSession().createQuery(hql).setParameter(0, mark).list();
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Long findFileCount(String mark) {
		String hql = " select count(*) from FileForm as f where f.mark=? ";
		return (Long) this.getHibernateTemplate().find(hql,mark).get(0);
	}
	
	@Override
	public List<FileForm> getFileListisMobile(Integer start, Integer limit,
			String mark, String typeisfile, String projId, String businessType) {
		if(typeisfile == null){
			String hql = "from FileForm a where a.mark=? ";
			try {
				return this.findByHql(hql, new Object[]{mark}, start, limit);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(typeisfile != null &&null==businessType){
			//String hql = "from FileForm a where a.mark=? and a.remark is null";
			String hql = "from FileForm a where a.remark=?";
			try {
				return this.findByHql(hql, new Object[]{mark});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(null!=mark&&null!=projId){
			String hql =  "from FileForm a where a.mark=? and a.projId = ?";
			try {
				return this.findByHql(hql, new Object[]{mark,projId});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(null!=mark){
			String hql = "from FileForm a where a.mark=?";
			try {
				return this.findByHql(hql, new Object[]{mark});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			String hql = "from FileForm a where (a.mark=? or a.remark=?) and a.projId=? and businessType=?";
			try {
				Object []object ={mark,typeisfile,projId,businessType};
				return this.findByHql(hql, object);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;

	}

	@Override
	public void updateSetName(int i, int sort,String yyzz) {
		String hql="update FileForm set setname=? ,sort=? where fileid=?";
		getSession().createQuery(hql).setParameter(0, yyzz).setParameter(1, sort).setParameter(2,i).executeUpdate();
	}
}
