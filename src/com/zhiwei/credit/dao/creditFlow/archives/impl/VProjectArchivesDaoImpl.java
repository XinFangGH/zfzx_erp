package com.zhiwei.credit.dao.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.archives.PlProjectArchivesDao;
import com.zhiwei.credit.dao.creditFlow.archives.VProjectArchivesDao;
import com.zhiwei.credit.model.creditFlow.archives.PlProjectArchives;
import com.zhiwei.credit.model.creditFlow.archives.VProjectArchives;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class VProjectArchivesDaoImpl extends BaseDaoImpl<VProjectArchives> implements VProjectArchivesDao{

	public VProjectArchivesDaoImpl() {
		super(VProjectArchives.class);
	}

	@Override
	public List<VProjectArchives> searchproject(Map<String, String> map, String businessType) {
		String hql="from VProjectArchives v where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and v.companyId in ("+strs+")"; //
		}
		
		
		String projectName=map.get("projectName");
		String projectNum=map.get("projectNum");
		String oppositeTypeValue=map.get("oppositeTypeValue");
		String companyId=map.get("companyId");
		String isArchives=map.get("isArchives");
		hql+=" and v.projectStatus =1 ";
	    
		 if(null!=companyId && !companyId.equals("")){
				hql=hql+" and v.companyId="+companyId; 
			}
		
	    if(null!=projectName && !projectName.equals("")){
			hql=hql+" and v.projectName like '%"+projectName+"%'"; 
		}
       if(null!=projectNum && !projectNum.equals("")){
    	   hql=hql+" and v.projectNumber like '%"+projectNum+"%'";
		}
       if(null!=oppositeTypeValue && !oppositeTypeValue.equals("")){
    	   hql=hql+ " and v.oppositeTypeValue= '"+oppositeTypeValue+"'";
       }
       if(null!=businessType && !businessType.equals("")){
    	   hql=hql+ " and v.businessType= '"+businessType+"'";
       }
       if(null!=isArchives && !isArchives.equals("")){
    	   if(isArchives.equals("0")){
    		   hql=hql+" and (v.isArchives=0 or v.isArchives is null)";
    	   }else if(isArchives.equals("1")){
    		   hql=hql+" and v.isArchives=1";
    	   }
       }
		 Query query = getSession().createQuery(hql);
			
		 return  query.list();
	}

	@Override
	public int searchprojectsize(Map<String, String> map, String businessType) {
		String hql="from VProjectArchives v where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and v.companyId in ("+strs+")"; //
		}
		
		
		String projectName=map.get("projectName");
		String projectNum=map.get("projectNum");
		String oppositeTypeValue=map.get("oppositeTypeValue");
		String companyId=map.get("companyId");
		String isArchives=map.get("isArchives");
		hql+=" and v.projectStatus =1 ";
	    
		 if(null!=companyId && !companyId.equals("")){
				hql=hql+" and v.companyId="+companyId; 
			}
		
	    if(null!=projectName && !projectName.equals("")){
			hql=hql+" and v.projectName like '%"+projectName+"%'"; 
		}
       if(null!=projectNum && !projectNum.equals("")){
    	   hql=hql+" and v.projectNumber like '%"+projectNum+"%'";
		}
       if(null!=oppositeTypeValue && !oppositeTypeValue.equals("")){
    	   hql=hql+ " and v.oppositeTypeValue= '"+oppositeTypeValue+"'";
       }
       if(null!=businessType && !businessType.equals("")){
    	   hql=hql+ " and v.businessType= '"+businessType+"'";
       }
       if(null!=isArchives && !isArchives.equals("")){
    	   if(isArchives.equals("0")){
    		   hql=hql+" and (v.isArchives=0 or v.isArchives is null)";
    	   }else if(isArchives.equals("1")){
    		   hql=hql+" and v.isArchives=1";
    	   }
       }
		 Query query = getSession().createQuery(hql);
			
		 return  query.list().size();
	}

	@Override
	public List<VProjectArchives> getbyproject(Long projectId,
			String businessType) {
		String hql = "from PlProjectArchives s where s.projectId = "+projectId+" and s.businessType ='"+businessType+"'";
		return findByHql(hql);
	}

}