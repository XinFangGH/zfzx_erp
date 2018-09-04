
Ext.ns('UserJobDetailForm');
/**
 * @description 员工职位显示列表
 */
UserJobDetailForm.show = function(userId,username){
	var win = new Ext.Window({
		title : '员工['+username+']职位信息',
		iconCls : 'menu-job',
		width : 480,
		height : 200,
		minHeight : 200,
		maximizable : true,
		modal : true,
		layout : 'form',
		buttonAlign : 'center',
		autoLoad : {
			url : __ctxPath + '/pages/system/UserJobDetailView.jsp?userId='+userId
		},
		buttons : [ {
			xtype : 'button',
			iconCls : 'close',
			text : '关闭',
			handler : function() {
				win.close();
			}
		} ],
		keys : {
			key : Ext.EventObject.ENTER,
			fn : function(){
				win.close();
			},
			scope : this
		}
	});
	win.show();
};