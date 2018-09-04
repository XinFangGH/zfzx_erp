Ext.ns('FormDefDetailForm');
/**
 * @description 查看表单详细信息
 * @class FormDefDetailForm
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-29PM
 */
FormDefDetailForm.show = function(formDefId){
	var win = new Ext.Window({
		title : '表单详细信息展示',
		iconCls : 'menu-form',
		modal : true,
		width : 600,
		height : 380,
		layout : 'form',
		region : 'center',
		autoScroll : true,
		maximizable : true,
		autoLoad : {
			url : __ctxPath + '/pages/flow/formDefDetail.jsp?formDefId='+formDefId
		},
		buttonAlign : 'center',
		buttons : [{
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function(){
				win.close();
			}
		}]
	});
	win.show();
};