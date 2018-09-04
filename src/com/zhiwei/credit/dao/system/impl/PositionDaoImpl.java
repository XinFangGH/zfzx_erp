package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.PositionDao;
import com.zhiwei.credit.model.system.Position;

@SuppressWarnings("unchecked")
public class PositionDaoImpl extends BaseDaoImpl<Position> implements PositionDao{

	public PositionDaoImpl() {
		super(Position.class);
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Position> getByParent(Long parentId){
		String hql="from Position p where p.posSupId=?";
		return findByHql(hql, new Object[]{parentId});
	}
	/**
	 * 按路径查找所有节
	 * @param path
	 * @return
	 */
	public List<Position> getByPath(String path){
		String hql="from Position p where p.path like ?";
		return findByHql(hql,new Object[]{path+"%"});
	}
	
	@Override
	public List<Position> getUnderLing(Long posId, PagingBean pb) {
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select vo from Position vo where 1=1 ");
		if(posId != null && posId > 0){ // 根据posId查询所有
			params.add(posId);
			hql.append("and vo.posSupId = ? ");
		}
		return findByHql(hql.toString(), params.toArray(), pb);
	}
	
	/**
	 * 根据某岗位取得下属岗位
	 * @param 
	 * posId 当前岗位
	 * @return
	 */
	public List<Position> getRelativeUnderling(Long posId, PagingBean pb) {
		Position pos = get(posId);
		Long posSupId = pos==null?0:pos.getPosSupId();
		
		StringBuffer sb = new StringBuffer("select p from Position p where p.posSupId=? ");
		List<Position> plist=findByHql(sb.toString(), new Object[]{posSupId});
		
		// 根据当前职位ID取得其已关联的同级职位
		Set<Position> mainPosSub = get(posId)==null?null:get(posId).getMainPositionSubs();
		Set<Position> subPosSub = get(posId)==null?null:get(posId).getSubPositionSubs();
		if(mainPosSub!=null&&subPosSub!=null){
			mainPosSub.addAll(subPosSub);
		}
		
		if(mainPosSub==null||mainPosSub.size()==0){plist.clear();}

		// 找其它同级职位之间的对应关系
		String path = "";
		if(plist!=null&&plist.size()>0){
			for(Position p:plist){
				
				mainPosSub = get(p.getPosId())==null?null:get(p.getPosId()).getMainPositionSubs();
				subPosSub = get(p.getPosId())==null?null:get(p.getPosId()).getSubPositionSubs();
				if(mainPosSub!=null&&subPosSub!=null){
					mainPosSub.addAll(subPosSub);
				}
				
				if(mainPosSub!=null&&mainPosSub.size()>0){
					for(Position p2:mainPosSub){
						if(plist.contains(p2)){
							if(path.length()>0)
								path+=",";
							path += p2.getPath();
						}
					}
				}
			}
		}
		
		// 根据路径取得所有下属
		StringBuffer rsb = new StringBuffer("");
		if(path.length()>0){
			rsb.append("select p from Position p ");
			String[] pths = path.split(",");
			
			for(int index=0;index<pths.length;index++){
				if(index==0){
					rsb.append("where (p.path like '"+pths[index]+"%' and p.path <> '"+pths[index]+"') ");
				}else{
					rsb.append("or (p.path like '"+pths[index]+"%' and p.path <> '"+pths[index]+"') ");
				}
			}
			
			Position supPos = get(posSupId);
			String supPosPath = supPos==null?"0.":supPos.getPath();
			rsb.append("and p.path <> '"+supPosPath+"' ");
		}
		
		// 没有取得任何下属时。取回本职位所有下属
		if(path.length()==0){
			Position curPos = get(posId);
			String curPath = curPos==null?"0.":curPos.getPath();
			String hql = "select p from Position p where p.path like '"+curPath+"%' and p.path <> '"+curPath+"' ";
			plist = findByHql(hql, new Object[]{}, pb);
		}
		
		return path.length()==0?plist:findByHql(rsb.toString(), new Object[]{}, pb);
	}

	/**
	 * 取得同级岗位的主岗位
	 * @param posId 岗位id
	 * @return
	 */
	public List<Position> getUnderByParent(Long posId) {
		String hql = "select p from Position p where p.posId in " +
			"(select ps.mainPositionId from PositionSub ps " +
			"where ps.subPositionId = ?) ";
		return findByHql(hql, new Object[]{posId});
	}

	/**
	 * 取得同级岗位
	 * @param 
	 * posId 岗位id
	 * posName 岗位名称
	 * @return
	 */
	public List<Position> getSameLevel(Long posId, PagingBean pb, String posName) {
		Position pos = get(posId);
		Long posSupId = pos==null?0:pos.getPosSupId();
		
		StringBuffer sb = new StringBuffer("select p from Position p where p.posSupId=? and p.posId <> ?");
		Object[] obj = new Object[]{posSupId,posId};
		if(posName!=null&&!"".equals(posName)){
			sb.append(" and p.posName like ? ");
			obj = new Object[]{posSupId,posId,"%"+posName.trim()+"%"};
		}
		List<Position> plist=findByHql(sb.toString(), obj);
		
		// 根据当前职位ID取得其已关联的同级职位
		Set<Position> mainPosSub = get(posId)==null?null:get(posId).getMainPositionSubs();
		Set<Position> subPosSub = get(posId)==null?null:get(posId).getSubPositionSubs();
		if(mainPosSub!=null&&subPosSub!=null){
			mainPosSub.addAll(subPosSub);
		}
		
		if(mainPosSub!=null&&mainPosSub.size()>0){
			for(Position p:mainPosSub){
				if(plist.contains(p)){
					plist.remove(p);
				}
			}
		}
		
		int page = plist.size()/pb.getPageSize();
		return plist.subList(pb.getFirstResult(),pb.getPageSize()*page+plist.size()%pb.getPageSize());
	}

}