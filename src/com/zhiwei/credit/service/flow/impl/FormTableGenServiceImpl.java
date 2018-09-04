package com.zhiwei.credit.service.flow.impl;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;

import org.apache.struts2.ServletActionContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;


import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.model.DynaModel;
import com.zhiwei.core.service.impl.BaseServiceImpl;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.XmlUtil;

import com.zhiwei.credit.FlowConstants;
import com.zhiwei.credit.dao.flow.FormFieldDao;
import com.zhiwei.credit.dao.flow.FormTableDao;
import com.zhiwei.credit.model.flow.FormField;
import com.zhiwei.credit.model.flow.FormTable;

import com.zhiwei.credit.service.flow.FormTableGenService;
import com.zhiwei.credit.service.flow.FormTableService;
import com.zhiwei.credit.util.FlowUtil;

import javax.sql.DataSource;

public class FormTableGenServiceImpl extends BaseServiceImpl<FormTable> implements FormTableGenService ,ApplicationContextAware {
	/**
	 * 数据库底层方言
	 */
	private String hibernateDialect=null;

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}


	ApplicationContext context;
	@Override 
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
	
	
	private FormTableDao dao;
	private VelocityEngine velocityEngine;
	@Resource
	private FormFieldDao formFieldDao;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public FormTableGenServiceImpl(FormTableDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	
	
	//mapping 用来保存 新增加的 xml 文件路径
	private static Map<String,String> mapping=new HashMap<String,String>();
	
	//生成实体
	public boolean genBean(FormTable[] formTables){
		mapping.clear();
		try{
			for(FormTable formTable:formTables){
				createModelHbmFile(formTable);
			}
			if(createTable(mapping)){
				//把需要更新的mapping提交至HibernateSessionFactory里进行更新
				this.context.publishEvent(new com.zhiwei.core.spring.SessionFactoryChangeEvent(mapping));
				//同时更新Mapping
				updateDynaModelMap(mapping);
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		

	};
	
	//更新流程动态表单动态实体的结构映射
	public void updateDynaModelMap(Map<String,String> mapping){
		java.util.Collection<String> newMapCollect=mapping.keySet();
			for(String key:newMapCollect){
				FormTableService formTableService=(FormTableService) AppUtil.getBean("formTableService");
				QueryFilter q= new QueryFilter(ServletActionContext.getRequest());
				int i=key.indexOf("_");
				String Q_key=key.substring(i+1,key.length());
				q.addFilter("Q_tableKey_S_EQ", Q_key);
				List<FormTable> formTables=formTableService.getAll(q);
				if(formTables!=null&&formTables.size()>0){
					DynaModel dynaModel=new DynaModel(formTables.get(0));
					FlowUtil.DynaModelMap.put(dynaModel.getEntityName(), dynaModel);
					if(logger.isInfoEnabled()){
						logger.info("	更新实体："+key);
					}
				}
			}
	}
	
	//删除流程动态表单动态实体的结构映射
	public void deleteDynaModelMap(Map<String,String> mapping){
		java.util.Collection<String> entitys=mapping.keySet();
		if(entitys!=null&&entitys.size()>0){
			for(String key:entitys){
				String p = (String) mapping.get(key);
				java.io.File xmlFile= new File(p);
				if(logger.isErrorEnabled()){
					org.dom4j.Document xmlDoc=XmlUtil.load(xmlFile.getPath());
					logger.error(xmlFile.getPath()+":"+XmlUtil.docToString(xmlDoc));
				}
				//删除其对应的映射文件
				if(xmlFile.delete()){
					if(FlowUtil.DynaModelMap.containsKey(key)){
						FlowUtil.DynaModelMap.remove(key);
						logger.info("	删除实体:"+key);
					}
					logger.info("	删除mxl文件"+xmlFile.getPath());

				}else{
					logger.info("	xml文件不存在:"+xmlFile.getPath());
				}
			}
		}
	}
	
	//创建数据表
	private boolean createTable(Map<String,String> mapping){
		
		logger.info("create table start...");
		
		java.util.Collection<String> xmlCollect=mapping.values();
		if(xmlCollect!=null&&xmlCollect.size()>0){
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
			LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
			factoryBean.setDataSource(dataSource);
			Properties prop = new Properties();
			prop.setProperty("connection.useUnicode", "true");
			prop.setProperty("connection.characterEncoding", "utf-8");
			
			prop.setProperty("hibernate.dialect",hibernateDialect);
			
			prop.setProperty("hibernate.show_sql", "true");
			prop.setProperty("hibernate.jdbc.batch_size", "20");
			prop.setProperty("hibernate.jdbc.fetch_size", "20");
			prop.setProperty("hibernate.cache.provider_class",
					"org.hibernate.cache.EhCacheProvider");
			prop.setProperty("hibernate.cache.use_second_level_cache", "true");
			prop.setProperty("hibernate.hbm2ddl.auto", "update");
			factoryBean.setHibernateProperties(prop);
			
		
			org.springframework.core.io.Resource[] mappingLocations = new FileSystemResource[xmlCollect.size()]; 
			int i=0;
			for(String p:xmlCollect){
				mappingLocations[i++]=new FileSystemResource(p);
			}
			factoryBean.setMappingLocations(mappingLocations);
			
			try {
				factoryBean.afterPropertiesSet();
				return true;
			} catch (Exception e) {
				logger.info("	更新xml文件错误:"+e.getMessage());
				deleteDynaModelMap(mapping);
				return false;
			}
		}else {
			logger.info("	没有要更新的xml文件!");
			return false;
		}
	}

	//排序编号
	private static final int ORDER_1=1;
	private static final int ORDER_2=2;
	
	/**
	 * 为新增加或修改的FormTable生成hibernate映射文件
	 * @param formTable
	 * @throws ResourceNotFoundException
	 * @throws ParseErrorException
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createModelHbmFile(FormTable formTable)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		Template template = velocityEngine.getTemplate("codegen/Model.vm");
		/**
		 * 组织数据
		 * */
		// 表名,实体名
		VelocityContext context = new VelocityContext();

		String TableName = formTable.getEntityName();
		String EntityName = TableName;

		context.put("EntityName", EntityName);
		context.put("TableName", TableName);

		java.util.Iterator<FormField> fieldIterator = formTable.getFormFields()
				.iterator();
		List<Map> list = new ArrayList<Map>();
		// 是否有主键
		boolean hadPrimary = false;

		while (fieldIterator.hasNext()) {
			FormField formField = fieldIterator.next();
			if (StringUtils.isNotEmpty(formField.getForeignTable())
					&& StringUtils.isNotEmpty(formField.getForeignKey())) {// 外键

				String foreignTable = FormTable.TABLE_PRE_NAME
						+ formField.getForeignTable();
				String foreignEntity = foreignTable;

				Map m = new HashMap();
				m.put("bag", "");
				m.put("order", ORDER_2);
				m.put("foreignEntity", foreignEntity);
				String foreignKey = formField.getForeignKey();
				m.put("foreignKey", foreignKey);

				list.add(m);

			} else {
				// 是主键
				if (formField.getIsPrimary().intValue() == 1) {
					hadPrimary = true;
					// 查询是否有外键关联于自已
					List<FormField> setList = formFieldDao.getByForeignTableAndKey(formField.getFormTable().getTableKey(), formField.getFieldName());
					if (setList != null && setList.size() > 0) {
						// 经查，有外键关联于自已
						for (FormField f : setList) {
							FormTable ft = f.getFormTable();
							String table_Key = ft.getEntityName();
							String Entity_Name = table_Key;
							Map m = new HashMap();
							m.put("order", ORDER_2);
							m.put("isPrimary", FormField.NOT_PRIMARY_KEY);

							m.put("foreignClass", "");
							m.put("foreignEntity", "");
							m.put("foreignKey", "");
							m.put("bag", "true");

							m.put("bagTable", table_Key);

							String bagName = Entity_Name + "s";
							m.put("bagName", bagName);
							// 关联外键
							String fieldName = formField.getFieldName();
							m.put("bagColumn", fieldName);
							m.put("bagEntity", Entity_Name);
							list.add(m);
						}
					}
				}

				Map m = new HashMap();

				String fieldSize = "";
				if (formField.getFieldSize() != null) {
					fieldSize = formField.getFieldSize().toString();
				}
				m.put("fieldSize", fieldSize);
				m.put("isRequired", formField.getIsRequired());
				m.put("showFormat", formField.getShowFormat());
				m.put("fieldDscp", formField.getFieldDscp());

				m.put("fieldType", FlowConstants.FIELD_TYPE_MAP.get(formField.getFieldType()).getName());
				m.put("isPrimary", formField.getIsPrimary());

				if (formField.getIsPrimary().shortValue() == FormField.PRIMARY_KEY.shortValue()) {
					m.put("order", ORDER_1);
				} else {
					m.put("order", ORDER_2);
				}
				m.put("foreignClass", "");
				m.put("foreignEntity", "");
				m.put("foreignKey", "");
				m.put("bag", "");

				String fieldName = formField.getFieldName();
				m.put("column", fieldName);
				m.put("property", fieldName);
				list.add(m);

			}

		}

		// 自动生成主键的情况
		if (hadPrimary == false) {
			Map m = new HashMap();
			m.put("fieldType", FlowConstants.FIELD_TYPE_MAP.get("Long").getName());
			m.put("isPrimary", FormField.PRIMARY_KEY);
			m.put("order", ORDER_1);
			m.put("foreignClass", "");
			m.put("foreignEntity", "");
			m.put("foreignKey", "");
			m.put("bag", "");
			String fieldName = EntityName + "Id";
			m.put("column", fieldName);
			m.put("property", fieldName);
			list.add(m);
		}
		// 加入runid
		Map m = new HashMap();

		m.put("fieldType", FlowConstants.FIELD_TYPE_MAP.get("Long").getName());
		m.put("isPrimary", FormField.NOT_PRIMARY_KEY);
		m.put("order", ORDER_2);
		m.put("foreignClass", "");
		m.put("foreignEntity", "");
		m.put("foreignKey", "");
		m.put("bag", "");
		m.put("column", "runId");
		m.put("property", "runId");
		list.add(m);
		context.put("fields", list);
		// 排序
		Collections.sort(list, new Comparator<Map>() {
			public int compare(Map arg0, Map arg1) {
				Integer i1 = (Integer) arg0.get("order");
				Integer i2 = (Integer) arg1.get("order");
				return i1.compareTo(i2);
			}
		});

		//合并模板和数据 ,生成字附串
		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		String beanStr = writer.toString();

		writeXmlToFile(EntityName, beanStr);

	}
	
	//写入XML文件
	private void writeXmlToFile(String EntityName,String beanXml) throws IOException{
		/**
		 * 将字附串写入xml文件
		 * */
		String rootPath = AppUtil.getAppAbsolutePath();
		String hbmdir = rootPath + "WEB-INF/classes/com/zhiwei/credit/model/process";
		File fileSrc = new File(hbmdir);
		if (!fileSrc.exists()) {
			if (!fileSrc.mkdirs()) {
				logger.info("创建目录失败:" + fileSrc.getPath());
			}
		}
		String xmlPath=hbmdir + "/" + EntityName + ".hbm.xml";
		//以二进制方式写进文件里
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(xmlPath)), "UTF-8"));
		bw.write(beanXml);
		bw.close();
		
		//记录新增的mxl文件
		mapping.put(EntityName, hbmdir + "/" + EntityName + ".hbm.xml");
	}
	
}
