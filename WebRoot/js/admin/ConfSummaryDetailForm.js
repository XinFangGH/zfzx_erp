Ext.ns('ConfSummaryDetailForm');
/**
 * @description 会议纪要详细信息展示
 * @class ConfSummaryDetailForm
 * @author YHZ
 * @date 2010-11-1 AM
 * @company www.zhiwei-jee.org
 */
ConfSummaryDetailForm.show = function(sumId) {
	var win = new Ext.Window( {
		id : 'confSummaryDetailForm',
		name : 'confSummaryDetailForm',
		width : 500,
		heigth : 500,
		modal : true,
		autoScroll : true,
		maximizable : true,
		title : '纪要详细信息',
		iconCls : 'menu-confSummary',
		layout : 'form',
		region : 'center',
		buttonAlign : 'center',
		autoLoad : {
			url : __ctxPath + '/admin/confSummaryDetail.do?sumId=' + sumId
		},
		buttons : [ {
			text : '关闭',
			iconCls : 'close',
			handler : function() {
				win.close();
			}
		} ]
	});
	win.show();
};