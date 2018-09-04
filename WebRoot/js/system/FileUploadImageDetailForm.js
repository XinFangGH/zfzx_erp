Ext.ns('FileUploadImageDetailForm');
/**
 * @description 图片详细信息展示
 * @class FileUploadImageDetailForm
 * @author YHZ
 * @date 2010-11-23 AM
 * @company www.hzhiwei-jee.cn
 */
FileUploadImageDetailForm.show = function(fileId) {
	var win = new Ext.Window( {
		id : 'FileUploadImageDetailForm',
		name : 'FileUploadImageDetailForm',
		width : 600,
		heigth : 500,
		modal : true,
		autoScroll : true,
		maximizable : true,
		title : '图片详细信息',
		iconCls : 'menu-file',
		layout : 'form',
		region : 'center',
		buttonAlign : 'center',
		autoLoad : {
			url : __ctxPath + '/system/fileAttachDetail.do?fileId=' + fileId
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