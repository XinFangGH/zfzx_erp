package com.zhiwei.credit.service.system;

import java.util.Map;

import org.apache.cxf.jaxrs.client.WebClient;


public interface BuildHtml2Web {
	/**
	 * ����p2p������ɾ�̬ҳ
	 * @param format ��ɸ�ʽ json ���� xml
	 * @param url  webServices ��ַ
	 * @param service  �������
	 * @param method   ����
	 * @param data     map ���
	 * @return
	 */
	public  void buildHtml(String format,String url,String service,String method,Map<String, Object> data);
	public Map<String, Object> getCommonData() ;
}
