/**
 * 
 * @class TaskHandlerWindow
 * @extends Ext.Window
 */
TaskHandlerWindow=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUIs();
		TaskHandlerWindow.superclass.constructor.call(this,{
			title:'为任务设置限期时间',
			height:120,
			width:400,
			layout:'fit',
			modal:true,
			items:this.formPanel,
			buttonAlign:'center',
			buttons:[
				{
					text:'保存',
					iconCls:'btn-save',
					scope:this,
					handler:this.save
				},{
					text:'关闭',
					iconCls:'close',
					scope:this,
					handler:this.close
				}
			]
		});
	},
	//初始化UI
	initUIs:function(){
		this.formPanel=new Ext.form.FormPanel({
			layout:'form',
			modal:true,
			border:false,
			bodyStyle:'padding:4px',
			items:[
				{
					xtype:'hidden',
					name:'userId'
				},
				{
					xtype:'compositefield',
					fieldLabel:'指定人员',
					allowBlank:false,
					items:[
						{
							xtype:'textfield',
							name:'userName',
							allowBlank:false,
							disabled : true,
							anchor:'98%,98%'
						},{
							xtype:'button',
							text:'选择待办人员',
							scope:this,
							handler:this.selectUser
						}
					]
				}
			]
		});
	},
	selectUser:function(){
		var userId=this.getCmpByName('userId');
		var userName=this.getCmpByName('userName');
		
		UserSelector.getView(function(uIds,uNames){
			userId.setValue(uIds);
			userName.setValue(uNames);
		},true).show();
	},
	save:function(){
		var taskGrid=this.taskGrid?this.taskGrid:Ext.getCmp('allTaskGrid');
		var rs = taskGrid.getSelectionModel().getSelections();
		var taskIds='';
		for(var i=0;i<rs.length;i++){
			if(i>0)taskIds+=',';
			taskIds+=rs[i].data.taskId;
		}
		
		if(this.formPanel.getForm().isValid()){
			this.formPanel.getForm().submit({
				scope:this,
				params:{
					taskIds:taskIds,
					comments:'【待办人调换：'+rs[0].data.assignee+'->'+this.formPanel.getCmpByName('userName').getValue()+'】'
				},
				url:__ctxPath+'/flow/handlerTask.do',
				method:'POST',
				success:function(fp,action){
					Ext.ux.Toast.msg('操作信息','成功更改待办人员!');
					taskGrid.getStore().reload();
					this.close();
				},
				failure:function(fp,action){
					Ext.ux.Toast.msg('操作信息','操作失败，请联系管理员!');
				}
			});
		}
	}
});

