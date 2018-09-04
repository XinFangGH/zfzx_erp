package com.zhiwei.core.util;

/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
 */

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.Constants;
import com.zhiwei.core.DataInit.DataInit;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jbpm.jpdl.ProcessInit;
import com.zhiwei.core.menu.TopModule;
import com.zhiwei.core.model.OnlineUser;
import com.zhiwei.core.web.filter.MySessionFilter;
import com.zhiwei.core.web.filter.SecurityInterceptorFilter;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.system.AppFunction;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Company;
import com.zhiwei.credit.model.system.FunUrl;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.system.AppFunctionService;
import com.zhiwei.credit.service.system.CompanyService;
import com.zhiwei.credit.service.system.FunUrlService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.util.FlowUtil;

/**
 * 方便取得Spring容器，取得其他服务实例，必须在Spring的配置文件里进行配置 如：<bean id="appUtil"
 * class="com.zhiwei.util.core.AppUtil"/> 也提供整个应用程序的相关配置获取方法
 * @author csx
 */
@SuppressWarnings("unchecked")
public class AppUtil implements ApplicationContextAware {

	private static Log logger = LogFactory.getLog(AppUtil.class);

	/**
	 * 存放应用程序的配置,如邮件服务器等
	 */
	public static Map configMap = new HashMap();
	/**
	 * 存放P2P应用程序的配置
	 */
	public static Map p2pConfigMap = new HashMap();
	/**
	 * 应用程序全局对象
	 */
	public static ServletContext servletContext = null;
	/**
	 * 存放在线用户,SessionId,OnlineUser
	 */
	private static Map<String, OnlineUser> onlineUsers = new LinkedHashMap<String, OnlineUser>();
	/**
	 * 在线用户的ID
	 */
	private static HashSet<Long> onlineUserIds = new HashSet<Long>();

	private static ApplicationContext appContext;

	/**
	 * 桌面菜单
	 */
	private static Map<String, String> deskMenus = null;

	/**
	 * 系统的左边导航菜单文档，当系统启动时， 由系统去解析menu-all.xml，并放置系统，供权限菜单使用
	 */
	private static Map<String, Document> orgMenus = null;
	/**
	 * 去除了Function与url
	 */
	private static Map<String, Document> itemsMenus = null;

	/**
	 * 系统的所有头部菜单配置
	 */
	private static Map<String, TopModule> allTopModels = null;
	/**
	 * 系统的所有菜单功能
	 */
	private static Document menuDocument = null;
	/**
	 * 公共的顶部模块
	 */
	public static Map<String, TopModule> publicTopModules = null;

	/**
	 * 公共菜单IDs TODO（尚未考虑好处理处理items中的isPublic）
	 */
	private static Set<String> publicMenuIds = null;

	public static Map<String, Document> getOrgMenus() {
		return orgMenus;
	}

	public static Map<String, Document> getItemsMenus() {
		return itemsMenus;
	}

	public static Map<String, TopModule> getAllTopModels() {
		return allTopModels;
	}

	public static Document getMenuDocument() {
		return menuDocument;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
	}

	public static void setPublicMenuIds(Set<String> pubIds) {
		publicMenuIds = pubIds;
	}

	public static Map<String, TopModule> getPublicTopModules() {
		return publicTopModules;
	}

	public static void setPublicTopModules(
			Map<String, TopModule> publicTopModules) {
		AppUtil.publicTopModules = publicTopModules;
	}
	
	public static ServletContext getContext(){
		return servletContext;
	}

	/**
	 * 应用程序启动时调用
	 * 
	 * @param servletContext
	 */
	public static void init(ServletContext in_servletContext) {
    	servletContext=in_servletContext;
    	//TODO 读取来自每个项目的config.properties文件的配置,并且放入configMap内,应用程序共同使用
		String proj_filePath=servletContext.getRealPath("/WEB-INF/template/"+getProjStr()+"/");
    	String proj_configFilePath=proj_filePath+"/config.properties";
    	Properties Proj_props=new Properties();
    	try{
    		FileInputStream fis=new FileInputStream(proj_configFilePath);
    		Reader r = new InputStreamReader(fis, "UTF-8"); 
    		Proj_props.load(r);
    		Iterator it= Proj_props.keySet().iterator();
    		while(it.hasNext()){
    			String key=(String)it.next();
    			configMap.put(key, Proj_props.getProperty(key));
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	
    	//TODO 读取来自公共的config.properties文件的配置,并且放入configMap内,应用程序共同使用
    	String filePath=servletContext.getRealPath("/WEB-INF/classes/conf/");
    	String configFilePath=filePath+"/config.properties";
    	Properties props=new Properties();
    	try{
    		FileInputStream fis=new FileInputStream(configFilePath);
    		Reader r = new InputStreamReader(fis, "UTF-8"); 
    		props.load(r);
    		Iterator it= props.keySet().iterator();
    		while(it.hasNext()){
    			String key=(String)it.next();
    			configMap.put(key, props.get(key));
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	
    	//TODO 从系统配置中读取所有的信息
    	reloadSysConfig();
    	
    	CompanyService companyService=(CompanyService)getBean("companyService");
    	List<Company> cList=companyService.findCompany();
    	if(cList.size()>0){
    		Company company=cList.get(0);
    		configMap.put(Constants.COMPANY_LOGO,company.getLogo());
    		configMap.put(Constants.COMPANY_NAME,company.getCompanyName());
    	}
    	//初始化安装
    	
    	if(isSetupMode()){
    		logger.info("开始初始化系统的缺省流程...");
	    	ProcessInit.initFlows(getAppAbsolutePath());
	    	logger.info("结束初始化系统的缺省流程...");
	    	//初始化系统流程
	    	//安装完成后，修改config.properites的文件的setupMode为false;
	    	logger.info("初始化数据~	开始...");
    		DataInit.init(getAppAbsolutePath());
    		logger.info("初始化数据~	结束...");
    		logger.info("更改安装模式为false");
	    	PropertiesUtil.writeKey(configFilePath, "setupMode", "false");
    	}
    	
    	//加载菜单转换器
		reloadMenu();
    	
    	//存放流程动态表单动态实体的结构映射
    	logger.info("加载流程动态表单动态实体的结构映射到静态变量... ");
    	FlowUtil.initDynModel();
	}
	
	public static void commonPut(String filePath){
		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(filePath);
			Reader r = new InputStreamReader(fis, "UTF-8");
			props.load(r);
			Iterator it = props.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				configMap.put(key, props.getProperty(key));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
	
	/**
     * request 转map
     * @param request
     * @return
     */
    public static Map<String,String> getParameterRequsetMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map<String,String> returnMap = new HashMap<String,String>();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            if(!name.equals("attest")){//除网站认证信息
            	Object valueObj = entry.getValue();
            	if(null == valueObj){
            		value = "";
            	}else if(valueObj instanceof String[]){
            		String[] values = (String[])valueObj;
            		for(int i=0;i<values.length;i++){
            			value = values[i] + ",";
            		}
            		value = value.substring(0, value.length()-1);
            	}else{
            		value = valueObj.toString();
            	}
            	returnMap.put(name, value);
            }
        }
        return returnMap;
    }
	
	/**
	 * 取得Bean
	 * 
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId) {
		return appContext.getBean(beanId);
	}

	/**
	 * 返回在线用户
	 * 
	 * @return
	 */
	public static Map<String, OnlineUser> getOnlineUsers() {
		return onlineUsers;
	}

	/**
	 * 移除在线用户
	 * 
	 * @param sessionId
	 */
	public static void removeOnlineUser(String sessionId) {
		OnlineUser user = onlineUsers.get(sessionId);
		if (user != null) {
			onlineUserIds.remove(user.getUserId());
		}
		onlineUsers.remove(sessionId);
	}

	public static void addOnlineUser(String sessionId, AppUser user) {
		if (!onlineUsers.containsKey(sessionId)) {
			OnlineUser onlineUser = new OnlineUser();
			onlineUser.setFullname(user.getFullname());
			onlineUser.setSessionId(sessionId);
			onlineUser.setUsername(user.getUsername());
			onlineUser.setUserId(user.getUserId());
			if (!user.getUserId().equals(AppUser.SUPER_USER)) {
				String path = user.getDepartment() == null ? "" : user
						.getDepartment().getPath();
				onlineUser.setDepPath("." + path);
			}
			Set<AppRole> roles = user.getRoles();
			StringBuffer roleIds = new StringBuffer(",");
			for (AppRole role : roles) {
				roleIds.append(role.getRoleId() + ",");
			}
			onlineUser.setRoleIds(roleIds.toString());
			onlineUser.setTitle(user.getTitle());
			onlineUsers.put(sessionId, onlineUser);
			onlineUserIds.add(user.getUserId());
		}
	}

	/**
	 * 取得应用程序的绝对路径
	 * 
	 * @return
	 */
	public static String getAppAbsolutePath() {
		return servletContext.getRealPath("/");
	}

	/**
	 * 取得配置菜单的xml目录的绝对路径
	 * 
	 * @return
	 */
	public static String getMenuAbDir() {
		return getAppAbsolutePath() + "/js/menu/xml/";
	}

	/**
	 * 
	 * @return
	 */
	public static String getMenuXslDir() {
		return getAppAbsolutePath() + "/js/menu/";
	}

	/**
	 * 取得流程表单模板的目录的绝对路径
	 * 
	 * @return
	 */
	public static String getFlowFormAbsolutePath() {
		String path = (String) configMap.get("app.flowFormPath");
		if (path == null) {
			path = "/WEB-INF/FlowForm/";
		}
		return getAppAbsolutePath() + path;
	}

	public static String getMobileFlowFlowAbsPath() {
		return getAppAbsolutePath() + "/mobile/flow/FlowForm/";
	}

	/**
	 * 重新加载安全权限匹配的数据源
	 */
	public static void reloadSecurityDataSource() {
		SecurityInterceptorFilter securityInterceptorFilter = (SecurityInterceptorFilter) AppUtil
				.getBean("securityInterceptorFilter");
		securityInterceptorFilter.loadDataSource();
	}

	/**
	 * 重新加载菜单配置
	 */
	public static void reloadMenu() {
		orgMenus = MenuUtil.getAllOrgMenus();
		deskMenus = MenuUtil.getAllDeskMenus();// 新增，初始化桌面map
		itemsMenus = MenuUtil.getAllItemsMenus(orgMenus);
		menuDocument = MenuUtil.mergeOneDoc(orgMenus);
		allTopModels = MenuUtil.getTopModules(menuDocument);
		publicTopModules = MenuUtil.getPublicTopModules(allTopModels);
	}

	/**
	 * 同步系统菜单
	 */
	public static void synMenu() {
		AppFunctionService appFunctionService = (AppFunctionService) getBean("appFunctionService");
		FunUrlService funUrlService = (FunUrlService) getBean("funUrlService");
		// 同步menu.xml中的功能菜单配置至app_function表
		Iterator<String> menuKeys = orgMenus.keySet().iterator();
		while (menuKeys.hasNext()) {
			Document doc = orgMenus.get(menuKeys.next());
			List<?> funNodeList = doc.getRootElement().selectNodes(
					"/Menus/Items//Item/Function");
			for (int i = 0; i < funNodeList.size(); i++) {
				Element funNode = (Element) funNodeList.get(i);
				String key = funNode.attributeValue("id");
				String name = funNode.attributeValue("text");
				AppFunction appFunction = appFunctionService.getByKey(key);
				if (appFunction == null) {
					appFunction = new AppFunction(key, name);
				} else {
					appFunction.setFunName(name);
				}
				List<?> urlNodes = funNode.selectNodes("./url");
				appFunctionService.save(appFunction);
				for (int k = 0; k < urlNodes.size(); k++) {
					Node urlNode = (Node) urlNodes.get(k);
					String path = urlNode.getText();
					FunUrl fu = funUrlService.getByPathFunId(path, appFunction
							.getFunctionId());
					if (fu == null) {
						fu = new FunUrl();
						fu.setUrlPath(path);
						fu.setAppFunction(appFunction);
						funUrlService.save(fu);
					}
				}
			}
		}
	}

	/**
	 * 取得用于授权的文档，即转化后，去掉url的元素
	 * 
	 * @return
	 */
	public static Document getGrantMenuDocument() {
		String xslStyle = servletContext.getRealPath("/js/menu")
				+ "/menu-grant.xsl";
		Document finalDoc = null;
		try {
			finalDoc = XmlUtil.styleDocument(menuDocument, xslStyle);
		} catch (Exception ex) {
			logger.error("menu-grant.xsl transform has error:"
					+ ex.getMessage());
		}
		return finalDoc;
	}

	/*
	 * 是否同步菜单
	 */
	public static boolean getIsSynMenu() {
		String synMenu = (String) configMap.get("isSynMenu");
		if ("true".equals(synMenu)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取系统配置MAP
	 */
	public static Map<?, ?> getSysConfig() {
		return configMap;
	}
	
	/**
	 * 获取p2p系统配置MAP
	 */
	public static Map<?, ?> getP2PSysConfig() {
		return p2pConfigMap;
	}

	public static void reloadSysConfig() {
		SysConfigService sysConfigService=(SysConfigService)getBean("sysConfigService");
    	List<SysConfig> list=sysConfigService.getAll();
    	for(SysConfig conf:list){
    		configMap.put(conf.getConfigKey(),conf.getDataValue());
    	}
	}

	public static String getCompanyLogo() {
		String defaultLogoPath = Constants.DEFAULT_LOGO;
		String path = (String) configMap.get(Constants.COMPANY_LOGO);
		if (StringUtils.isNotEmpty(path)) {
			defaultLogoPath = "/attachFiles/" + path;
		}
		return defaultLogoPath;
	}

	public static String getCompanyName() {
		String defaultName = Constants.DEFAULT_COMPANYNAME;
		String companyName = (String) configMap.get(Constants.COMPANY_NAME);
		if (StringUtils.isNotEmpty(companyName)) {
			defaultName = companyName;
		}
		return defaultName;
	}

	public static HashSet<Long> getOnlineUserIds() {
		return onlineUserIds;
	}

	/***
	 * 字符串解析为对象
	 * 格式为：fieldName：fieldValue,fieldName：fieldValue@fieldName：fieldValue
	 * ,fieldName：fieldValue
	 * 
	 * @throws MapperException
	 * @throws RecognitionException
	 * @throws TokenStreamException
	 * **/
	@SuppressWarnings("all")
	public static List parserList(String stringJson, Class className)
			throws TokenStreamException, RecognitionException, MapperException {
		String[] json = stringJson.split("@");
		List list = new ArrayList();
		for (int k = 0; k < json.length; k++) {
			String str = json[k];
			JSONParser parser = new JSONParser(new StringReader(str));
			Object object = null;
			object = JSONMapper.toJava(parser.nextValue(), className);
			list.add(object);
		}
		return list;
	}

	/**
	 * 查询系统logo路径
	 * 
	 * @param type
	 * @return
	 */
	public static void getIcoPath() {
		FileFormService fileFormService = (FileFormService) getBean("fileFormService");
		List<FileForm> file_list = fileFormService.listByMark("system");
		for (int i = 0; i < file_list.size(); i++) {
			FileForm f = file_list.get(i);
			configMap.put(f.getRemark(), f.getFilepath());
		}
	}

	/**
	 * Ftp上传 配置 ip
	 * 
	 * @return
	 */
	public static String getFtpIp() {
		try {
			return configMap.get("ftpIp").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Ftp上传 配置 账号
	 * 
	 * @return
	 */
	public static String getFtpUsName() {
		try {
			return configMap.get("ftpUsName").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Ftp上传 配置 密码
	 * 
	 * @return
	 */
	public static String getFtpPss() {
		try {
			return configMap.get("ftpPss").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Ftp上传 配置 端口
	 * 
	 * @return
	 */
	public static String getFtpPort() {
		try {
			return configMap.get("ftpPort").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 是否用短信端口
	 */
	public static boolean getSmsPort() {
		String smsPort = (String) configMap.get("smsPort");
		if ("true".equals(smsPort)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为安装模式
	 * 
	 * @return
	 */
	public static boolean isSetupMode() {
		String isSetupMode = (String) configMap.get("setupMode");
		if ("true".equals(isSetupMode)) {
			return true;
		}
		return false;
	}

	public static String getSystemName() {
		try {
			String systemName = configMap.get("systemName").toString();
			return systemName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getFlowType() {
		try {
			String flowType = configMap.get("flowType").toString();
			return flowType;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 获取宽限天数
	 */
	public static Integer getGraceDayNum() {
		try {
			return Integer.valueOf(configMap.get("graceDayNum").toString());
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

	public static String getSystemUrl() {
		try {
			return configMap.get("systemUrl").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getP2pUrl() {
		try {
			return configMap.get("p2pUrl").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getFileUrl() {
		try {
			return configMap.get("fileURL").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getImageUrlPrefix() {
		try {
			return configMap.get("imageUrlPrefix").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getImagePathFormat() {
		try {
			return configMap.get("imagePathFormat").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 算头不算尾0 算头又算尾1
	 * 
	 * @return
	 */
	public static String getInterest() {
		try {
			return configMap.get("interest").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getIsActualDay() {
		try {
			return configMap.get("isActualDay").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Ftp上传 配置 压缩根目录
	 * 
	 * @return
	 */
	public static String getZipOutPutPath() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node zipOutPutPath = document
					.selectSingleNode("/zhiwei/systemConfig/zipOutPutPath");
			return zipOutPutPath.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Ftp上传 配置 压缩项目名称
	 * 
	 * @return
	 */
	public static String getZipProjectName() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node zipProjectName = document
					.selectSingleNode("/zhiwei/systemConfig/zipProjectName");
			return zipProjectName.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得债权匹配业务配置中在保金额利率
	 * 
	 * @return
	 */
	public static String getshopRate() {
		try {
			Document document = XmlUtil.getobligationConfigXML();
			Node compressionFileName = document
					.selectSingleNode("/obligationMappingConfig/obligationConfig/shopRate");
			return compressionFileName.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得第三方支付类型
	 * 
	 * @return
	 */
	public static String getThirdPayType() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node thirdPayType = document
					.selectSingleNode("/zhiwei/systemConfig/thirdPayType");
			return thirdPayType.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * 获得项目名称
	 * 
	 * @return
	 */
	public static String getProStr() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node projStr = document
					.selectSingleNode("/zhiwei/systemConfig/projStr");
			return projStr.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	public static String getToRightChildrenUser() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node interest = document
					.selectSingleNode("/zhiwei/systemConfig/toRightChildrenUser");
			return interest.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 项目 路径
	 */
	public static String getProjStr() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node projStrNode = document
					.selectSingleNode("/zhiwei/systemConfig/projStr");
			return projStrNode.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "zhiweiConfig";
		}
	}

	/*
	 * 生成环境和测试环境 配置文件路径
	 */
	public static String getKeyPath() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node keyPathNode = document
					.selectSingleNode("/zhiwei/systemConfig/keyPath");
			return keyPathNode.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "zhiweiConfig";
		}
	}

	/**
	 * 汇付key 密码
	 * 
	 * @return
	 */
	public static String getSignpass() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node signpassNode = document
					.selectSingleNode("/zhiwei/systemConfig/signpass");
			return signpassNode.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getEnterFlowType() {
		try {
			return configMap.get("flowEnterType").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 获取webServices url
	 */
	public static String getWebServicesUrl() {
		try {
			Document document = XmlUtil.getWebServicesConfigXML();
			Node systemNameNode = document
					.selectSingleNode("/zhiwei/webServicesConfig/url");
			return systemNameNode.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 * 获取webServices 是否对接财务系统
	 */
	public static boolean getWebServicesIsOpen() {
		boolean isOpen = false;
		try {
			Document document = XmlUtil.getWebServicesConfigXML();
			Node systemNameNode = document
					.selectSingleNode("/zhiwei/webServicesConfig/isOpen");
			if (systemNameNode.getText().toString().equals("false")) {
				isOpen = false;
			}
			if (systemNameNode.getText().toString().equals("true")) {
				isOpen = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOpen;
	}

	/*
	 * 获取webServices 是否对接财务系统
	 */
	public static boolean getWebServicesCustomerIsOpen() {
		boolean customerIsOpen = false;
		try {
			Document document = XmlUtil.getWebServicesConfigXML();
			Node systemNameNode = document
					.selectSingleNode("/zhiwei/webServicesConfig/customerIsOpen");
			if (systemNameNode.getText().toString().equals("false")) {
				customerIsOpen = false;
			}
			if (systemNameNode.getText().toString().equals("true")) {
				customerIsOpen = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerIsOpen;
	}

	public static String getSystemIsGroupVersion() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node systemNameNode = document
					.selectSingleNode("/zhiwei/systemConfig/isGroupVersion");
			// add by liny 用来解决非集团版和集团版切换时间session中company存值得问题
			HttpSession session = (HttpSession) MySessionFilter.session.get();
			Boolean flag = Boolean.valueOf(systemNameNode.getText());
			if (!flag) {
				if (null!=session) {
					session.setAttribute("company", null);
				}
			}
			return systemNameNode.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getSystemIsOAVersion() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node systemNameNode = document
					.selectSingleNode("/zhiwei/systemConfig/isOA");
			return systemNameNode.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean getCodeByNode(String nodestr) {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node node = document.selectSingleNode("/zhiwei/systemConfig/"
					+ nodestr);
			if (null != node) {
				return Boolean.valueOf(node.getText());
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getSystemVersion() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node systemVersion = document
					.selectSingleNode("/zhiwei/systemConfig/systemVersion");
			return systemVersion.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getWebServiceUrlRs() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node webServiceUrlNode = document
					.selectSingleNode("/zhiwei/systemConfig/webServiceUrlRs");
			if (null == webServiceUrlNode) {
				return "";
			} else {
				return webServiceUrlNode.getText();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getWebServiceUrlWs() {
		try {
			Document document = XmlUtil.getSystemConfigXML();
			Node webServiceUrlNode = document
					.selectSingleNode("/zhiwei/systemConfig/webServiceUrlWs");
			if (null == webServiceUrlNode) {
				return "";
			} else {
				return webServiceUrlNode.getText();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 罚息的收取方式第一种：按照剩余金额计算罚息,0表示 第二种如果剩余金额不为0则按全部金额计算 1表示
	 * 
	 * @return
	 */
	public static String getDefaultInterest() {
		try {
			return configMap.get("defaultInterest").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static Map<String, String> getDeskMenus() {
		return deskMenus;
	}

	public static void setDeskMenus(Map<String, String> deskMenus) {
		AppUtil.deskMenus = deskMenus;
	}

	public static Map getConfigMap() {
		return configMap;
	}

	public static void setConfigMap(Map<?, ?> configMap) {
		AppUtil.configMap = configMap;
	}
}