package com.zhiwei.core.log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.SystemLog;
import com.zhiwei.credit.service.system.SystemLogService;

@Aspect
// 该注解标示该类为切面类
public class LogAspectJava {

	@Resource
	private SystemLogService systemLogService;
	private Log logger = LogFactory.getLog(LogAspectJava.class);// aa

	// 标注该方法体为后置通知，当目标方法执行成功后执行该方法体
	@AfterReturning("within(com.credit.proj..*) && @annotation(rl)")
	public void addLogSuccess(JoinPoint jp, LogServicesResource rl) {
	
		Object[] parames = jp.getArgs();// 获取目标方法体参数
		
		String className = jp.getTarget().getClass().toString();// 获取目标类名
		className = className.substring(className.indexOf("com"));
		String signature = jp.getSignature().getName();// 获取目标方法签名
	
		String methodName = jp.getSignature().getName();//获取方法名
		String p=parseParames(parames,methodName);// 解析目标方法体的参数;
		//String modelName = getModelName(className); // 根据类名获取所属的模块

		 String ip = ContextUtil.getIpAddr(ServletActionContext.getRequest());//用户IP

		// 取到当前的操作用户
		AppUser appUser = ContextUtil.getCurrentUser();

		if (appUser != null) {
			try {
				SystemLog sysLog = new SystemLog();

				sysLog.setClassName(className);
				sysLog.setMethodName(methodName);
				sysLog.setParams(p);
				sysLog.setModelName("");//所属模块暂时不加
				sysLog.setIp(ip);
				sysLog.setErr("");
				sysLog.setFlag("1");

				sysLog.setCreatetime(new Date());
				sysLog.setUserId(appUser.getUserId());
				sysLog.setUsername(appUser.getFullname());
				sysLog.setExeOperation(rl.description());
				sysLog.setOrgid(ContextUtil.getLoginCompanyId());

				systemLogService.save(sysLog);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}

	}

	// 标注该方法体为异常通知，当目标方法出现异常时，执行该方法体 异常参数要与 抛出的异常一致 否则不会执行
	@AfterThrowing(pointcut = "within(com.credit.proj..*) && @annotation(rl)", throwing ="ex")
	public void addLog(JoinPoint jp, LogServicesResource rl, NullPointerException ex) {
		Object[] parames = jp.getArgs();
		String className = jp.getTarget().getClass().toString();
		className = className.substring(className.indexOf("com"));
		String signature = jp.getSignature().toString();
		String methodName = jp.getSignature().getName();
		String p=parseParames(parames,methodName);// 解析目标方法体的参数;
		//String modelName = getModelName(className);

		String ip = ContextUtil.getIpAddr(ServletActionContext.getRequest());
		// 取到当前的操作用户
		AppUser appUser = ContextUtil.getCurrentUser();
		if (appUser != null) {
			try {
				SystemLog sysLog = new SystemLog();

				sysLog.setClassName(className);
				sysLog.setMethodName(methodName);
				sysLog.setParams(p);
				sysLog.setModelName("");//所属模块 暂时不加
				sysLog.setIp(ip);
				sysLog.setErr(ex.toString());
				sysLog.setFlag("0");

				sysLog.setCreatetime(new Date());
				sysLog.setUserId(appUser.getUserId());
				sysLog.setUsername(appUser.getFullname());
				sysLog.setExeOperation(rl.description());
				sysLog.setOrgid(ContextUtil.getLoginCompanyId());

				systemLogService.save(sysLog);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 根据包名查询检索其所属的模块
	 * 
	 * @param packageName
	 *            包名
	 * @return 模块名称
	 */
	private String getModelName(String packageName) {
		String modelName = "";
		SAXReader reader = new SAXReader();
		try {
			// 读取project.xml模块信息描述xml文档
			File proj = ResourceUtils.getFile("classpath:project.xml");
			Document doc = reader.read(proj);
			// 获取文档根节点
			Element root = doc.getRootElement();
			// 查询模块名称
			modelName = searchModelName(root, packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelName;
	}

	/**
	 * 采用递归方式根据包名逐级检索所属模块
	 * 
	 * @param element
	 *            元素节点
	 * @param packageName
	 *            包名
	 * @return 模块名称
	 */
	private String searchModelName(Element element, String packageName) {
		String modelName = searchModelNodes(element, packageName);
		// 若将包名解析到最后的根目录后仍未检索到模块名称，则返回空
		if (packageName.lastIndexOf(".") == -1) {
			return modelName;
		}
		// 逐级检索模块名称
		if (modelName.equals("")) {
			packageName = packageName
					.substring(0, packageName.lastIndexOf("."));
			modelName = searchModelName(element, packageName);
		}
		return modelName;
	}

	/**
	 * 根据xml文档逐个节点检索模块名称
	 * 
	 * @param element
	 *            节点元素
	 * @param packageName
	 *            包名
	 * @return 模块名称
	 */
	@SuppressWarnings("unchecked")
	private String searchModelNodes(Element element, String packageName) {

		String modelName = "";
		Element modules = element.element("modules");
		Iterator it = modules.elementIterator();
		if (!it.hasNext()) {
			return modelName;
		}
		while (it.hasNext()) {
			Element model = (Element) it.next();
			String pack = model.attributeValue("packageName");
			String name = model.elementText("moduleCHPath");
			if (packageName.equals(pack)) {
				modelName = name;
				return modelName;
			}
			if (modelName != null && !modelName.equals("")) {
				break;
			}
			modelName = searchModelNodes(model, packageName);
		}

		return modelName;
	}

	/**
	 * 解析方法参数
	 * 
	 * @param parames
	 *            方法参数
	 * @return 解析后的方法参数
	 */
	private String parseParames(Object[] parames) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parames.length; i++) {
			if (parames[i] != null && !parames[i].equals("")) {
				if (parames[i] instanceof Object[]
						|| parames[i] instanceof Collection) {
					JSONArray json = JSONArray.fromObject(parames[i]);
					if (i == parames.length - 1) {
						sb.append(json.toString());
					} else {
						sb.append(json.toString() + ",");
					}
				} else {
					JSONObject json = JSONObject.fromObject(parames[i]);
					if (i == parames.length - 1) {
						sb.append(json.toString());
					} else {
						sb.append(json.toString() + ",");
					}
				}
			}
		}
		String params = sb.toString();
		params = params.replaceAll("(\"\\w+\":\"\",)", "");
		params = params.replaceAll("(,\"\\w+\":\"\")", "");
		return params;
	}
	
	/** 
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 
     * 将参数值拼接为操作内容 
     */  
    public String parseParames(Object[] args, String mName){  
  
        if (args == null) {  
            return null;  
        }  
          
        StringBuffer rs = new StringBuffer();  
        rs.append(mName);  
        String className = null;  
        int index = 1;  
        // 遍历参数对象  
        for (Object info : args) {  
              
            //获取对象类型  
            className = info.getClass().getName();  
            className = className.substring(className.lastIndexOf(".") + 1);  
            rs.append("[参数" + index + "，类型：" + className + "，值：");  
              
            // 获取对象的所有方法  
            Method[] methods = info.getClass().getDeclaredMethods();  
              
            // 遍历方法，判断get方法  
            for (Method method : methods) {  
                  
                String methodName = method.getName();  
                // 判断是不是get方法  
                if (methodName.indexOf("get") == -1) {// 不是get方法  
                    continue;// 不处理  
                }  
                  
                Object rsValue = null;  
                try {  
                      
                    // 调用get方法，获取返回值  
                    rsValue = method.invoke(info);  
                      
                    if (rsValue == null) {//没有返回值  
                        continue;  
                    }  
                      
                } catch (Exception e) {  
                    continue;  
                }  
                  
                //将值加入内容中  
                rs.append("(" + methodName + " : " + rsValue + ")");  
            }  
              
            rs.append("]");  
              
            index++;  
        }  
          
        return rs.toString();  
    }  
      

}
