/**
 * 把自己的任务分配给他人来代处理，并且选择是否发送邮件来通知
 * @param {} taskId
 */
var ChangeTaskView=function(taskId,taskname){
	var formPanel=new Ext.FormPanel({
		layout:'form',
		bodyStyle:'padding:4px 4px 4px 4px',
		url:__ctxPath+'/flow/changeTask.do',
		border:false,
		items:[
			{
				xtype:'hidden',
				name:'taskId',
				value:taskId
			},{
				xtype : 'panel',
				height:32,
				border:false,
				layout : 'column',
				defaults:{
					margins:'0 6 0 0'
				},
				items : [{
							xtype : 'label',
							text : '代办人',
							width : 105
						}, {
							name : 'fullname',
							id : 'fullname',
							xtype:'textfield',
							allowBlank:false,
							width:240
						}, {
							xtype:'hidden',
							name:'userId',
							id:'userId'
						},{
							xtype : 'button',
							text : '选择',
							iconCls : 'btn-select',
							width : 80,
							handler : function() {
								UserSelector.getView(
										function(ids, names) {
										  var fullname = Ext.getCmp('fullname');
										  var userId = Ext.getCmp('userId');
										  fullname.setValue(names);
										  userId.setValue(ids);
										},true).show();//true表示单选
							}
						}]
			},{
				xtype:'textarea',
				name:'msg',
				anchor:'98%,98%',
				fieldLabel:'备注'
			}
		]
	});

	var win=new Ext.Window({
		title:'任务代办--' + taskname,
		height:180,
		iconCls:'btn-changeTask',
		buttonAlign:'center',
		width:500,
		modal:true,
		layout:'fit',
	    items:[formPanel],
		buttons:[
			{
				text:'转交代办人',
				iconCls:'btn-save',
				handler : function() {
						if (formPanel.getForm().isValid()) {
							formPanel.getForm().submit({
								success : function(form, action) {
									Ext.ux.Toast.msg('操作信息提示','任务已经成功转交代办人来处理！');
									ZW.refreshTaskPanelView();
									win.close();
									
								}
							});
						}
					}
			},
			{
				text:'关闭',
				iconCls:'close',
				handler:function(){
					win.close();
				}
			}
		]
	});
	
	win.show();
};