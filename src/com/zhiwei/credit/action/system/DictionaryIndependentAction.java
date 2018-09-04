package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;



import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.DictionaryIndependent;

import com.zhiwei.credit.model.system.GlobalTypeIndependent;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.GlobalTypeIndependentService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class DictionaryIndependentAction extends BaseAction{
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private GlobalTypeIndependentService globalTypeIndependentService;
	private DictionaryIndependent dictionaryIndependent;
	
	private Long dicId;
    
	private String nodeKey;
	
	private String itemName;

	private String dicKey;
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDicKey() {
		return dicKey;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public Long getDicId() {
		return dicId;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public DictionaryIndependent getDictionaryIndependent() {
		return dictionaryIndependent;
	}

	public void setDictionaryIndependent(DictionaryIndependent dictionaryIndependent) {
		this.dictionaryIndependent = dictionaryIndependent;
	}

	/**
	 * 显示列表
	 */
	public String loadIndepItems(){
		
		GlobalTypeIndependent globalTypeIndependent=globalTypeIndependentService.getByNodeKey(nodeKey);
		   if(null!=globalTypeIndependent ){
			   List<DictionaryIndependent> list=dictionaryIndependentService.getListByProTypeId(globalTypeIndependent.getProTypeId());
			   StringBuffer buff = new StringBuffer("[");
				for (DictionaryIndependent dic : list) {
					buff.append("['").append(dic.getDicKey()).append("','")
							.append(dic.getItemValue()).append("'],");

				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				setJsonString(buff.toString());
		   }
		
		return SUCCESS;
	}
	public String mulSave() {
		String data = getRequest().getParameter("data");

		if (StringUtils.isNotEmpty(data)) {
			Gson gson = new Gson();
			DictionaryIndependent[] dics = gson.fromJson(data, DictionaryIndependent[].class);

			for (int i = 0; i < dics.length; i++) {
				DictionaryIndependent dic = dictionaryIndependentService.get(dics[i].getDicId());
		
				try {
					BeanUtil.copyNotNullProperties(dic, dics[i]);

					dictionaryIndependentService.save(dic);
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
				;
			}
		}

		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		String sParentId = getRequest().getParameter("parentId");
		if (StringUtils.isNotEmpty(sParentId) && !"0".equals(sParentId)) {
			GlobalTypeIndependent globalType = globalTypeIndependentService.get(new Long(sParentId));
			filter.addFilter("Q_globalTypeIndependent.path_S_LFK", globalType.getPath());
		}
		List<DictionaryIndependent> dlist = dictionaryIndependentService.getAll(filter);
		List<DictionaryIndependent> list=new ArrayList<DictionaryIndependent>();
		for(DictionaryIndependent dic :dlist){
			if("0".equals(dic.getStatus())){
				list.add(dic);
			}
		}
		Type type = new TypeToken<List<DictionaryIndependent>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		JSONSerializer json = new JSONSerializer();
		buff.append(json.serialize(list));

		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 根据条目查询出字典的value并返回数组
	 * 
	 * @return
	 */
	public String load() {
		List<String> list = dictionaryIndependentService.getAllByItemName(itemName);
		StringBuffer buff = new StringBuffer("[");
		for (String itemName : list) {
			buff.append("'").append(itemName).append("',");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}



	public String typeChange() {
		String dicIds = getRequest().getParameter("dicIds");
		String dicTypeId = getRequest().getParameter("dicTypeId");

		if (StringUtils.isNotEmpty(dicIds) && StringUtils.isNotEmpty(dicTypeId)) {
			GlobalTypeIndependent globalType = globalTypeIndependentService.get(new Long(dicTypeId));

			String[] ids = dicIds.split("[,]");
			if (ids != null) {
				for (String id : ids) {
					DictionaryIndependent dic = dictionaryIndependentService.get(new Long(id));
					dic.setGlobalTypeIndependent(globalType);
					dic.setItemName(globalType.getTypeName());

					dictionaryIndependentService.save(dic);
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				DictionaryIndependent dictionaryIndependent=dictionaryIndependentService.get(new Long(id));
				dictionaryIndependent.setStatus("1");				
				dictionaryIndependentService.save(dictionaryIndependent);
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		DictionaryIndependent dictionaryIndependent = dictionaryIndependentService.get(dicId);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");

		sb.append(gson.toJson(dictionaryIndependent));
		sb.append("}");

		setJsonString(sb.toString());
        
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
        if(dictionaryIndependent.getDicKey()!=null && !"".equals(dictionaryIndependent.getDicKey())){
        	List<DictionaryIndependent> list=null;
        	if(dictionaryIndependent.getDicId()==null){
        	     list=dictionaryIndependentService.getByDicKey(dictionaryIndependent.getDicKey());
        	}else{
        		DictionaryIndependent orgDic = dictionaryIndependentService.get(dictionaryIndependent.getDicId());

					if(!dictionaryIndependent.getDicKey().equals(orgDic.getDicKey())){
						list=dictionaryIndependentService.getByDicKey(dictionaryIndependent.getDicKey());
					}
				
        	}
        	if(null!=list && list.size()>0){
        		setJsonString("{success:true,mag:false}");
        	}else{
        		if (dictionaryIndependent.getDicId() != null) {
        			DictionaryIndependent orgDic = dictionaryIndependentService.get(dictionaryIndependent.getDicId());
        			try {
        				
        				BeanUtil.copyNotNullProperties(orgDic, dictionaryIndependent);
        				dictionaryIndependentService.save(orgDic);
        			} catch (Exception ex) {
        				logger.error(ex.getMessage());
        			}
        		} else {
        			String parentId = getRequest().getParameter("parentId");
        			if (StringUtils.isNotEmpty(parentId)) {
        				GlobalTypeIndependent globalType = globalTypeIndependentService
        						.get(new Long(parentId));
        				dictionaryIndependent.setGlobalTypeIndependent(globalType);
        			}
        			dictionaryIndependentService.save(dictionaryIndependent);
        		}
        		setJsonString("{success:true}");
        	}
        }
		
		return SUCCESS;
	}

	/**
	 * 获得条目
	 * 
	 * @return
	 */
	public String items() {
		List<String> list = dictionaryIndependentService.getAllItems();
		StringBuffer buff = new StringBuffer("[");
		for (String str : list) {
			buff.append("'").append(str).append("',");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
public String loadfundType(){
	String[] strArr=nodeKey.split(",");
	 List<DictionaryIndependent> list=new ArrayList<DictionaryIndependent>();
	for(int j=0;j<=strArr.length-1;j++){
		GlobalTypeIndependent globalTypeIndependent=globalTypeIndependentService.getByNodeKey(strArr[j]);
		if(null!=globalTypeIndependent ){
			List<DictionaryIndependent>  list1=dictionaryIndependentService.getListByProTypeId(globalTypeIndependent.getProTypeId());
			for(DictionaryIndependent l:list1){
				list.add(l);
			}
		}
	}
		
		   
			   StringBuffer buff = new StringBuffer("[");
				for (DictionaryIndependent dic : list) {
					buff.append("['").append(dic.getDicKey()).append("','")
							.append(dic.getItemValue()).append("'],");

				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				setJsonString(buff.toString());
		
		
		return SUCCESS;
	}
}
