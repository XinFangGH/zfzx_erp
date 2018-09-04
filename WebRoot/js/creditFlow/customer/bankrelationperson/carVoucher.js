function carVoucher(){
	var window_add = new Ext.Window({
		title : '新增担保人',
		layout : 'fit',
		width:700,
		height : 480,
		closable : true,
		constrainHeader : true ,
		collapsible : true,
		resizable : true,
		plain : true,
		border : false,
		autoScroll : true ,
		modal : true,
		bodyStyle:'overflowX:hidden',
		buttonAlign : 'right',
		minHeight : 480,
		minWidth : 700,
		items :[]
	}).show();
}