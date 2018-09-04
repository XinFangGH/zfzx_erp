package com.zhiwei.credit.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.zhiwei.credit.model.system.AppUser;

public class SessionListener implements HttpSessionListener {
	public static HashMap sessionMap = new HashMap();

	public void sessionCreated(HttpSessionEvent hse) {

		HttpSession session = hse.getSession();

	}

	public void sessionDestroyed(HttpSessionEvent hse) {

		HttpSession session = hse.getSession();

		this.DelSession(session);

	}

	public static synchronized void DelSession(HttpSession session) {

		if (session != null) {

			// 删除单一登录中记录的变量

			if (session.getAttribute("users") != null) {

				AppUser tu = (AppUser) session.getAttribute("users");

				SessionListener.sessionMap.remove(tu.getUserId());

			}

		}

	}
}
