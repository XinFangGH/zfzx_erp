package com.zhiwei.credit.service.creditFlow.multiLevelDic.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.credit.proj.entity.Content;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.multiLevelDic.AreaDicDao;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;

public class AreaDicServiceImpl extends BaseServiceImpl<AreaDic> implements AreaDicService{

	private AreaDicDao dao;
	
	public AreaDicServiceImpl(AreaDicDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public AreaDic getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public void addDic(AreaDic area) {
		try {
			int parentid=area.getParentId();
			dao.save(area);
			AreaDic dic=dao.getById(parentid);
			dic.setLeaf(false);
			dao.merge(dic);
			
			JsonUtil.jsonFromObject(null, true) ;
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null, false) ;
		}
	}
	public void addDicType(AreaDic area){
		List<AreaDic> list = this.listByLabelOrText(area.getLable(), area.getText());
		
		if(list == null || list.size() == 0){
			try {
				dao.save(area);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JsonUtil.jsonFromObject(null, true);
		}
		JsonUtil.jsonFromObject(null, false);
	}

	@Override
	public List<AreaDic> listByLabelOrText(String lable, String text) {
		
		return dao.listByLabelOrText(lable, text);
	}

	@Override
	public List<AreaDic> listByParentId(Integer parentId) {
		
		return dao.listByParentId(parentId);
	}
	public void updateDic(int id,String text,int parentId,int orderid,String remarks,int sourcepid,String remarks1){	
		List<AreaDic> list = null;
		try {
			list = this.listByParIdAndText(parentId, text, id);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null, false) ;
		}
		if(list == null || list.size() == 0){
			try {
				
				List<AreaDic> listt= this.listByParentId(parentId);
				String c="0";
				if(null!=listt && listt.size()>0){
					c=String.valueOf(listt.size());
				}
				if(c.equals("0")){
					AreaDic dic=dao.getById(parentId);
					dic.setLeaf(false);
					dao.merge(dic);
				}
				AreaDic area=dao.getById(id);
				area.setText(text);
				area.setOrderid(orderid);
				area.setRemarks(remarks);
				area.setParentId(parentId);
				area.setRemarks1(remarks1);
				dao.merge(area);
				
				String hqls="select count(id) from AreaDic where parentid="+sourcepid+" and isOld=0";
				List<AreaDic> lists= this.listByParentId(sourcepid);
				String s="0";
				if(null!=lists && lists.size()>0){
					s=String.valueOf(lists.size());
				}
				if(s.equals("0")){
					AreaDic dic=dao.getById(sourcepid);
					dic.setLeaf(true);
					dao.merge(dic);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.jsonFromObject(null, false) ;
			}
			JsonUtil.jsonFromObject(null, true) ;
		}
		JsonUtil.jsonFromObject(null, false) ;
	}

	@Override
	public List<AreaDic> listByParIdAndText(Integer parentId, String text,
			Integer id) {
		
		return dao.listByParIdAndText(parentId, text, id);
	};
	@Override
	public void getRecordCountByParentID(int parentid){
		List<AreaDic> totalList = null;
		try{
			totalList = dao.listByParentId(parentid);
			int recordnum=0;
			if(null!=totalList && totalList.size()>0){
				recordnum=totalList.size();
			}
			JsonUtil.responseJsonString("{recordnum:"+recordnum+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void updateDicType(int id,String text,String lable,String remarks,int orderid) {
		List<AreaDic> list = null;
		try {
			list =this.listByIdAndLabel(id, text, lable);
		} catch (Exception e1) {
			e1.printStackTrace();
			JsonUtil.jsonFromObject(null, false) ;
		}
		if(list == null || list.size() == 0){
			try {
				AreaDic dic=dao.getById(id);
				dic.setText(text);
				dic.setLable(lable);
				dic.setRemarks(remarks);
				dic.setOrderid(orderid);
				dao.merge(dic);
			} catch (Exception e) {
				e.printStackTrace();
				JsonUtil.jsonFromObject(null, false) ;
			}
			JsonUtil.jsonFromObject(null, true) ;
		}
		JsonUtil.jsonFromObject(null, false) ;
	}

	@Override
	public List<AreaDic> listByIdAndLabel(Integer id, String text, String label) {
		
		return dao.listByIdAndLabel(id, text, label);
	}
	@Override
	public void deleteDicType(int id) {
		try {
			List<AreaDic> listc=dao.listByParentId(id);
			String c="0";
			if(null!=listc && listc.size()>0){
				c=String.valueOf(listc.size());
			}
			if(c.equals("0")){
				AreaDic areaDic=dao.getById(id);
				areaDic.setIsOld(1);
				dao.merge(areaDic);
				int parentid=areaDic.getParentId();
				List<AreaDic> listp=dao.listByParentId(parentid);
				String p="0";
				if(null!=listp && listp.size()>0){
					p=String.valueOf(listp.size());
				}
				if(p.equals("0")){
					AreaDic area=dao.getById(parentid);
					area.setLeaf(true);
					dao.merge(area);
				}
				JsonUtil.jsonFromObject("1",true);
			}
			else{
				JsonUtil.jsonFromObject("0",false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteById(int id) {
		try {
			AreaDic dic=dao.getById(id);
			dic.setIsOld(1);
			dao.merge(dic);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null, false);
		}
		JsonUtil.jsonFromObject(null, true);
	}
	public void returnDicType(String idString) {
		try{
			String[] idArray = idString.split(",");
			for(int i=0; i<idArray.length; i++){
				Integer id = Integer.parseInt(idArray[i]);
				AreaDic dic=dao.getById(id);
				dic.setIsOld(0);
				dao.merge(dic);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void getDicTypeTree(String lable,int node) {
		List<AreaDic> list = null;
		String json = "";
		if(node == -1){
			list = listByLabel(lable);
		}else{
			list = this.listByParentId(node);
		}
		json = JSONArray.fromObject(list).toString();
		try {
			JsonUtil.responseJsonString(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<AreaDic> listByLabel(String label) {
		
		return dao.listByLabel(label);
	}

	@Override
	public List<AreaDic> findLikeText(String likeText) {
		
		return dao.findLikeText(likeText);
	}
	public void selectPersonAddress(int parentId){
		List<AreaDic> list = null ;
		try {
			list =dao.listByParentId(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.jsonFromList(list) ;
	}

	@Override
	public List<AreaDic> listByLabelAndIsOld(String label) {
		
		return dao.listByLabelAndIsOld(label);
	}

	@Override
	public List<AreaDic> getByParentId(Integer parentId) {
		
		return dao.getByParentId(parentId);
	}
	public List<AreaDic> getDictionaryTree(String lable) {
		return dao.listByLabelAndIsOld(lable);
	}
	
	public List<AreaDic> queryDictionaryTree(int parentId) {
		try {
			List<AreaDic> list =dao.listByParentId(parentId);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<AreaDic> queryIsLeaf(int id) {
		try {
			List<AreaDic> list =dao.getByParentId(id);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public void getDictionaryTreeWindow(String lable, String node) {
		String json = "";
		// 根节点
		if (node.equals("-1")) {
			// taskNode实体bean，是ext规定的类
			List<Content> contentList = new ArrayList<Content>();
			List<AreaDic> li = getDictionaryTree(lable);
			if(li.size() > 0){
				AreaDic dic = (AreaDic)li.get(0);
				List<AreaDic> list = queryDictionaryTree(dic.getId());
				for (AreaDic areaDic : list) {
					Content c = new Content();
					c.setId(areaDic.getId());
					c.setText(areaDic.getText());
					c.setLeaf(false);
					c.setRemarks(areaDic.getRemarks());
					contentList.add(c);
				}
				JSONArray jsonObject = JSONArray.fromObject(contentList);
				json = jsonObject.toString();
			}
			// 叶子节点
		} else {
			List<Content> contentList = new ArrayList<Content>();
			List<AreaDic> list = queryDictionaryTree(Integer.parseInt(node));
			if(list != null && list.size()>0){
				
				for (AreaDic areaDic : list) {
					Content c = new Content();
					c.setId(areaDic.getId());
					c.setText(areaDic.getText());
					c.setRemarks(areaDic.getRemarks());
					List list_leaf = queryIsLeaf(areaDic.getId());
					if(null ==list_leaf || list_leaf.size() == 0) {
						c.setLeaf(true);
						
					} else {
						c.setLeaf(false);
					}
					contentList.add(c);
				}
			}
			
			JSONArray jsonObject = JSONArray.fromObject(contentList);
			json = jsonObject.toString();
		}
		try {
			JsonUtil.responseJsonString(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void getDictionarNotLastNodeyTreeWindow(String lable, String node) {
	

		String json = "";
		// 根节点
		if (node.equals("-1")) {
			// taskNode实体bean，是ext规定的类
			List<Content> contentList = new ArrayList<Content>();
			List<AreaDic> li = getDictionaryTree(lable);
			if(li.size() > 0){
				AreaDic dic = (AreaDic)li.get(0);
				List<AreaDic> list = queryDictionaryTree(dic.getId());
				for (AreaDic areaDic : list) {
					Content c = new Content();
					c.setId(areaDic.getId());
					c.setText(areaDic.getText());
					c.setLeaf(false);
					c.setRemarks(areaDic.getRemarks());
					contentList.add(c);
				}
				JSONArray jsonObject = JSONArray.fromObject(contentList);
				json = jsonObject.toString();
			}
			// 叶子节点
		} else {
			List<Content> contentList = new ArrayList<Content>();
			List<AreaDic> list = queryDictionaryTree(Integer.parseInt(node));
			if(list != null && list.size()>0){
				
				for (AreaDic areaDic : list) {
					Content c = new Content();
					c.setId(areaDic.getId());
					c.setText(areaDic.getText());
					c.setRemarks(areaDic.getRemarks());
					List list_leaf = queryIsLeaf(areaDic.getId());
					if(null ==list_leaf || list_leaf.size() == 0) {
						c.setLeaf(true);
						
					} else {
						c.setLeaf(false);
						contentList.add(c);
					}
				}
			}
			
			JSONArray jsonObject = JSONArray.fromObject(contentList);
			json = jsonObject.toString();
		}
		try {
			JsonUtil.responseJsonString(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	@Override
	public List getNode(int id) {
		List<AreaDic> listArea = new ArrayList<AreaDic>();
		String hql = "from AreaDic where id=?";
		try {
			AreaDic dic=dao.getById(id);
			if(null!=dic){
				listArea.add(dic);
			}
			return listArea;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArea;
	}
}