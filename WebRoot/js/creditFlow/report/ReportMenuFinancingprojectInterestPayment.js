//报表菜单id:ReportTemplateMenu
ReportMenuFinancingprojectInterestPayment = function(_cfg) {

	Ext.applyIf(this, _cfg);
    if(typeof(_cfg.projectMoney)!="undefined")
	{
	      this.projectMoney=_cfg.projectMoney;
	}
	if(typeof(_cfg.projectMoneyl)!="undefined")
	{
	      this.projectMoneyl=_cfg.projectMoneyl;
	}
	if(typeof(_cfg.projectNumber)!="undefined")
	{
	      this.projectNumber=_cfg.projectNumber;
	}
	if(typeof(_cfg.oppositeTypeValue)!="undefined")
	{
	      this.oppositeTypeValue=_cfg.oppositeTypeValue;
	}
		if(typeof(_cfg.customerName)!="undefined")
	{
	      this.customerName=_cfg.customerName;
	}
	if(typeof(_cfg.businessManagerid)!="undefined")
	{
	      this.businessManagerid=_cfg.businessManagerid;
	}
		if(typeof(_cfg.businessManager)!="undefined")
	{
	      this.businessManager=_cfg.businessManager;
	}
	if(typeof(_cfg.startDateg)!="undefined")
	{
	      this.startDateg=_cfg.startDateg;
	}
		if(typeof(_cfg.startDatel)!="undefined")
	{
	      this.startDatel=_cfg.startDatel;
	}
	if(typeof(_cfg.projectStatus)!="undefined")
	{
	      this.projectStatus=_cfg.projectStatus;
	}
		if(typeof(_cfg.reportKey)!="undefined")
	{
	      this.reportKey=_cfg.reportKey;
	}
	this.reportId = getReportIdByKey(this.reportKey);
	return ReportPreviewFinancingprojectInterestPayment(this.reportId, this.title,this.reportKey,this.projectMoney,this.projectMoneyl,
												
												this.projectNumber,
												this.oppositeTypeValue,
												this.customerName,
												this.businessManagerid ,
												this.businessManager ,
												this.startDateg,
												this.startDatel,
												this.projectStatus);
}
//根据报表key 取得报表ID
var getReportIdByKey = function(reportKey) {

	var reportId = '';
	Ext.Ajax.request( {
		url : '',//__ctxPath + '/creditFlow/finance/listSlCapitalFlowtemp.do?state=0',
		method : 'POST',
		async : false,
		success : function(response, opts) {
			var obj = Ext.decode(response.responseText);
			reportId = obj.result[0].reportId;
		},

		failure : function(response, opts) {

		},
		params : {
			Q_reportKey_S_EQ : reportKey
		}
	});
	return reportId;

}