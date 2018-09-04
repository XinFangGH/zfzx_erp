package com.zhiwei.core.spring;

import org.springframework.context.ApplicationEvent;
/**
 * 用于更新SessionFactory的事件
 * @author csx
 *
 */
public class SessionFactoryChangeEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public SessionFactoryChangeEvent(Object paramObject) {
		super(paramObject);
	}
}