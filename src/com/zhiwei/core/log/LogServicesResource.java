package com.zhiwei.core.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @company  北京互融时代软件有限公司
 * @description 类的方法描述注解 
 * 供旧项目中 在services层调用
 * @author yzc
 * @create 2013-01-31
 */
@Target(ElementType.METHOD)   
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited  
public @interface LogServicesResource {
	/**
	 * 方法描述
	 * @return
	 */
	public String description() default "方法描述"; 
}
