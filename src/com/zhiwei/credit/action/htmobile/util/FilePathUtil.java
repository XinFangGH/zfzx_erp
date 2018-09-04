package com.zhiwei.credit.action.htmobile.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.util.AppUtil;

/*import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.StringUtil;*/

/**
 * 系统的文件路径帮助类
 * 
 * @author zxh
 * 
 */
public class FilePathUtil {

	/**
	 * 附件路径
	 */
	public static String FILE_PATH_ROOT = AppUtil.getAppAbsolutePath()
			+ "/attachFiles/";

	/**
	 * 获取classes路径。
	 * 
	 * @return 返回如下的路径E:\work\joffice\src\main\webapp\WEB-INF\classes\。
	 */
	public static String getClassesPath() {
		return StringUtil.trimSufffix(AppUtil.getAppAbsolutePath(),
				File.separator)
				+ "\\WEB-INF\\classes\\".replace("\\", File.separator);

	}

	/**
	 * 返回表单模版物理的路径。
	 * 
	 * @return
	 */
	public static String getDesignTemplatePath() {
		return FilePathUtil.getClassesPath() + "velocity" + File.separator
				+ "design" + File.separator;
	}

	/**
	 * 返回hbm映射文件的路径
	 * 
	 * @return
	 */
	public static String getHbmPath() {
		StringBuffer hbmPath = new StringBuffer(FilePathUtil.getClassesPath());
		hbmPath.append("com").append(File.separator).append("htsoft")
				.append(File.separator).append("oa").append(File.separator)
				.append("model").append(File.separator).append("process");
		return hbmPath.toString();

	}

	/**
	 * 返回Model VM文件的路径
	 * 
	 * @return
	 */
	public static String getModelVMPath() {
		String vmPath = "codegen" + File.separator + "Model.vm";
		return vmPath;
	}

	/**
	 * 获取表单模板的的程序真实路径
	 * 
	 * @return
	 */
	public static String formTemplatePath() {
		return AppUtil.getAppAbsolutePath()
				+ "\\WEB-INF\\FlowForm".replace("\\", File.separator);
	}

	/**
	 * 拼接表单模板
	 * 
	 * @param basePath
	 *            表单模板
	 * @param versionNo
	 *            版本
	 * @param tempName
	 *            表单名称
	 * @return 如果为空，则返回通用模板
	 */
	public static String formTemplatePath(String basePath, Integer versionNo,
			String tempName) {
		String formPath = "\\通用\\表单.vm".replace("\\", File.separator);
		if (StringUtils.isNotEmpty(basePath) && versionNo.intValue() > 0
				&& StringUtils.isNotEmpty(tempName)) {
			formPath = File.separator + basePath + File.separator + versionNo
					+ File.separator + tempName + ".vm";
		}
		return formPath;
	}
}
