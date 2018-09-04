package com.thirdPayInterface.AllinPay.AllinpayUtil;

import com.aipg.common.InfoReq;

public class Q_AIPG {
	private InfoReq INFO;
	private Q_Body BODY;
	private QUERY_TRANS QTRANSREQ;
	private NOTIFY NOTIFY;
	
	public NOTIFY getNOTIFY() {
		return NOTIFY;
	}

	public void setNOTIFY(NOTIFY nOTIFY) {
		NOTIFY = nOTIFY;
	}

	public QUERY_TRANS getQTRANSREQ() {
		return QTRANSREQ;
	}

	public void setQTRANSREQ(QUERY_TRANS qTRANSREQ) {
		QTRANSREQ = qTRANSREQ;
	}
	public InfoReq getINFO() {
		return INFO;
	}

	public void setINFO(InfoReq info) {
		INFO = info;
	}

	public Q_Body getBODY() {
		return BODY;
	}

	public void setBODY(Q_Body body) {
		BODY = body;
	}
}
