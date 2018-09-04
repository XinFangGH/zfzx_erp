package com.zhiwei.core.command;
/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 * 排序查询条件
 * @author csx
 *
 */
public class SortCommandImpl implements CriteriaCommand{

	public Criteria execute(Criteria criteria) {
		String[]propertys=sortName.split("[.]");
    	if(propertys!=null&&propertys.length>1){
    		for(int i=0;i<propertys.length-1;i++){
    			//防止别名重复
    			if(!filter.getAliasSet().contains(propertys[i])){
    				criteria.createAlias(propertys[i],propertys[i]);
    				filter.getAliasSet().add(propertys[i]);
    			}
    		}
    	}
		if(SORT_DESC.equalsIgnoreCase(ascDesc)){
			criteria.addOrder(Order.desc(sortName));
		}else if(SORT_ASC.equalsIgnoreCase(ascDesc)){
			criteria.addOrder(Order.asc(sortName));
		}
		return criteria;
	}
	
	public SortCommandImpl(String sortName,String ascDesc,QueryFilter filter){
		this.sortName=sortName;
		this.ascDesc=ascDesc;
		this.filter=filter;
	}
	
	private String sortName;
	
	private String ascDesc;
	
	private QueryFilter filter;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getAscDesc() {
		return ascDesc;
	}

	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}
	
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.sortName) 
				.append(this.ascDesc).toHashCode() ;
	}
	
	public String getPartHql(){
		return sortName + " " + ascDesc;
	}
}
