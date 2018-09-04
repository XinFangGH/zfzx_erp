package com.thirdPayInteface.Fuiou.FuiouResponse;



import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="ap")
@XmlAccessorType(XmlAccessType.FIELD)
public class FuiouReponse {
	/**
	 * 包含的plain对象
	 */
	private Plain plain;
	/**
	 * 签名数据
	 */
	@XmlElement
	private String signature;
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Plain getPlain() {
		return plain;
	}

	public void setPlain(Plain plain) {
		this.plain = plain;
	}
}