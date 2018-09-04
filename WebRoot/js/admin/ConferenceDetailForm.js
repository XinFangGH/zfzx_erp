Ext.ns('ConferenceDetailForm');
/**
 * @createtime 2010-10-9 PM
 * @class ConferenceDetailForm
 * @extends Ext.Window
 * @description 会议详细信息展示
 * @company 智维软件
 * @author YHZ
 */

ConferenceDetailForm.show = function(confId) {
	var window = new Ext.Window( {
		title : '会议详细信息',
		iconCls : 'menu-conference',
		modal : true,
		width : 750,
		height : 580,
		layout : 'form',
		region : 'center',
		autoScroll : true,
		maximizable : true,
		buttonAlign : 'center',
		autoLoad : {
			url : __ctxPath + '/admin/conferenceDetail.do?confId=' + confId
		},
		buttons : [ {
			xtype : 'button',
			iconCls : 'close',
			text : '关闭',
			handler : function() {
				window.close();
			}
		} ]
	});
	//判断查看权限
	Ext.Ajax.request( {
		url : __ctxPath + '/admin/allowViewConfPrivilege.do',
		params : {
			confId : confId
		},
		method : 'post',
		waitMsg : '数据正在提交，请稍后...',
		success : function(response, options) {
			var res = Ext.util.JSON.decode(response.responseText);
			if (res.success)
				window.show();
			else
				Ext.MessageBox.show( {
					title : '操作信息',
					msg : res.msg,
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
		}
	});

};