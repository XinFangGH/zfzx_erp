package com.thirdPayInterface;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共接口类
 * @author userAdmin
 *
 */
public interface ThirdPayTypeInterface {
	CommonResponse businessHandle(CommonRequst commonRequst);
	CommonResponse businessReturn(Map maps,HttpServletRequest request);
	/**
	 * 获取第三方操作模式
	 * @param businessType
	 * @return
	 */
	Object[] checkThirdType(String businessType,String type);
}
