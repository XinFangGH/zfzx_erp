package com.zhiwei.core.command;
/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.command.FieldCommandImpl;
import com.zhiwei.core.util.ParamUtil;
import com.zhiwei.core.web.paging.PagingBean;


/**
 * 用于前台的组合条件进行数据的过滤
 * @author csx
 *
 */
public class QueryFilter{
	
	/**
	 * 是否导出标记 
	 */
	private boolean isExport=false;
	
	/**
	 * 降序排序
	 */
	public static final String ORDER_DESC="desc";
	/**
	 * 升序排序
	 */
	public static final String ORDER_ASC="asc";
	
	
	public final static Log logger=LogFactory.getLog(QueryFilter.class);
	
	private HttpServletRequest request=null;
	
	/**
	 * DAO层的querys Map中filter的key,表示用哪一个作为查询的条件
	 */
	private String filterName=null;

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	private List<Object> paramValues=new ArrayList();
	
	private List<CriteriaCommand> commands = new ArrayList<CriteriaCommand>();
	
	private Set<String> aliasSet=new HashSet<String>();
	
	private PagingBean pagingBean=null;

    public PagingBean getPagingBean() {
		return pagingBean;
	}
    
    public QueryFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
     * 从请求对象获取查询参数,并进行构造  
     * <p>参数名格式必须为: Q_firstName_S_EQ
     * 其中Q_表示该参数为查询的参数，firstName查询的字段名称，
     * S代表该参数的类型为字符串类型,该位置的其他值有：
     * D=日期，BD=BigDecimal，FT=float,N=Integer,SN=Short,S=字符串
     * EQ代表等于。
     * 该位置的其他值有：<br/>
     * LT，GT，EQ，LE，GE,LK<br/>
     * 要别代表<,>,=,<=,>=,like的条件查询
     * <p>
     * @param request
     */
    public QueryFilter(HttpServletRequest request){
    	this.request=request;
    	Enumeration paramEnu= request.getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    		if(paramName.startsWith("Q_")){
    			String paramValue=(String)request.getParameter(paramName);
    			if(paramName.contains("NULL")){
    				addFilter(paramName,paramValue);
    			}else{
    				//因为页面request中传的参数可能为空或者"",这种情况应该不予查询
        			if(paramValue!=null&&!"".equals(paramValue)&&!"null".equals(paramValue)){
        				addFilter(paramName,paramValue);
        			}
    			}
    			
    			
    		}
    	}
    	//取得分页的信息
    	Integer start=0;
    	Integer limit=PagingBean.DEFAULT_PAGE_SIZE;
    	
    	String s_start=request.getParameter("start");
    	String s_limit=request.getParameter("limit");
    	if(StringUtils.isNotEmpty(s_start)){
    		start=new Integer(s_start);
    	}
    	if(StringUtils.isNotEmpty(s_limit)){
    		limit=new Integer(s_limit);
    	}
    	
    	String sort=request.getParameter("sort");
    	String dir=request.getParameter("dir");
    	
    	if(StringUtils.isNotEmpty(sort)&&StringUtils.isNotEmpty(dir)){
    		addSorted(sort, dir);
    	}
    	
    	if("true".equals(request.getParameter("isExport"))){
    		isExport=true;
    		request.setAttribute("colId", request.getParameter("colId"));
			request.setAttribute("colName", request.getParameter("colName"));
			request.setAttribute("exportType", request.getParameter("exportType"));
    	}
		request.setAttribute("isExport", isExport);
    	
    	this.pagingBean=new PagingBean(start, limit);
    }
    
    /**
     * 添加过滤的查询条件
     * @param paramName 过滤的查询参数名称
     * <p>过滤的查询参数名称格式必须为: Q_firstName_S_EQ
     * 其中Q_表示该参数为查询的参数，firstName查询的字段名称，
     * S代表该参数的类型为字符串类型,该位置的其他值有：
     * D=日期，BD=BigDecimal，FT=float,N=Integer,SN=Short,L=Long,
     * S=字符串
     * DL=日期类型，并且把其时分秒设置为0
     * DG=日期类型，并且把其时分秒设置为最大，即为23:59:59
     * EQ代表等于
     * 该位置的其他值有：<br/>
     * LT，GT，EQ，LE，GE,LK,EMP,NOTEMP,NULL,NOTNULL<br/>
     * 要别代表<,>,=,<=,>=,like,empty,not empty,null,not null的条件查询
     * <p>
     * @param paramName
     * @param paramValue
     */
    public void addFilter(String paramName,Object paramValue){    	
    	String []fieldInfo=paramName.split("[_]");
    	Object value=null;
		if(fieldInfo!=null&&fieldInfo.length==4){
			value=ParamUtil.convertObject(fieldInfo[2], paramValue);
			if(value!=null){
    			FieldCommandImpl fieldCommand=new FieldCommandImpl(fieldInfo[1],value,fieldInfo[3],this);
    			commands.add(fieldCommand);
			}
		}else if(fieldInfo!=null&&fieldInfo.length==3){
			FieldCommandImpl fieldCommand=new FieldCommandImpl(fieldInfo[1],value,fieldInfo[2],this);
			commands.add(fieldCommand);
		}else{
			logger.error("Query param name ["+paramName+"] is not right format." );
		}
		
    }
    
    /**
     * 添加过滤的查询条件
     * @param paramName 过滤的查询参数名称
     * <p>过滤的查询参数名称格式必须为: Q_firstName_S_EQ
     * 其中Q_表示该参数为查询的参数，firstName查询的字段名称，
     * S代表该参数的类型为字符串类型,该位置的其他值有：
     * D=日期，BD=BigDecimal，FT=float,N=Integer,SN=Short,L=Long,
     * S=字符串
     * DL=日期类型，并且把其时分秒设置为0
     * DG=日期类型，并且把其时分秒设置为最大，即为23:59:59
     * EQ代表等于
     * 该位置的其他值有：<br/>
     * LT，GT，EQ，LE，GE,LK,EMP,NOTEMP,NULL,NOTNULL,IN<br/>
     * 要别代表<,>,=,<=,>=,like,empty,not empty,null,not null,in()的条件查询
     * <p>
     * @param paramName
     * @param paramValue
     */
    public void addFilterIn(String paramName,List paramValue){    
    	String []fieldInfo=paramName.split("[_]");
    	List value=null;
		if(fieldInfo!=null&&fieldInfo.length==4){
			value=ParamUtil.convertObject(fieldInfo[2], paramValue);
			if(value!=null){
    			FieldCommandImpl fieldCommand=new FieldCommandImpl(fieldInfo[1],value,fieldInfo[3],this);
    			commands.add(fieldCommand);
			}
		}else{
			logger.error("Query param name ["+paramName+"] is not right format." );
		}
		
    }
    
    public void addParamValue(Object value){
    	paramValues.add(value);
    }
    
    public List getParamValueList(){
    	return paramValues;
    }
       
    public void addSorted(String orderBy,String ascDesc){
    	commands.add(new SortCommandImpl(orderBy,ascDesc,this));
    }
    
    public void addExample(Object object){
    	commands.add(new ExampleCommandImpl(object));
    }

	public List<CriteriaCommand> getCommands() {
		return commands;
	}

	public Set<String> getAliasSet() {
		return aliasSet;
	}
	
	public boolean isExport() {
		return isExport;
	}

	public void setExport(boolean isExport) {
		this.isExport = isExport;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

}
