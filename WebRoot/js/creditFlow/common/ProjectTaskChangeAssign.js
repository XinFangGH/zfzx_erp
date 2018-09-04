/**
 * 项目换人
 * @class ProjectTaskChangeAssign
 * @extends Ext.Window
 */
ProjectTaskChangeAssign=Ext.extend(Ext.Window,{
	//originalUser : "默认值",//该任务节点最初的处理人
	constructor:function(conf){
		if(typeof(conf.taskId)!="undefined"){
		   this.taskId=conf.taskId;
		}
		if(typeof(conf.runId)!="undefined"){
		   this.runId=conf.runId;
		}
		if(typeof(conf.startUserId)!="undefined"){
		   this.startUserId=conf.startUserId;
		}
		if(typeof(conf.startUserName)!="undefined"){
		   this.startUserName=conf.startUserName;
		}
		Ext.applyIf(this,conf);
		this.initUIs();
		ProjectTaskChangeAssign.superclass.constructor.call(this,{
			title : '指定项目人员',
			border : false,
			width : 433,
			height : 230,
			layout : 'fit',
			autoScroll : false,
			modal : true,
			iconCls : 'btn-users-sel',
			items :this.formPanel,
			buttonAlign : 'center',
			buttons : [
				{
					text:'确认',
					iconCls:'btn-ok',
					scope:this,
					handler:this.submit
				},{
					text:'取消',
					iconCls:'btn-cancel',
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
			bodyStyle:'padding:15px',
			items:[
				{
					xtype : 'hidden',
					name : 'userId'
				}, {
					xtype : 'compositefield',
					fieldLabel : '指定项目人员',
					allowBlank : false,
					items : [{
							xtype : 'textfield',
							name : 'userName',
							anchor : "50%",
							allowBlank : false
						},{
							xtype:'button',
							text:'选择项目人员',
							anchor : "50%",
							scope:this,
							handler:this.selectUser
						}
					]
				}, {
					xtype : "textarea",
					name : "comments",
					allowBlank : false,
					anchor : "100%",
					fieldLabel : "备注"
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
	submit:function(){
		//if(this.formPanel.getForm().isValid()){
		var selectedUser = this.getCmpByName('userName').getValue();
		var commentRemarks = "【项目人员调换："+this.startUserName+"→"+selectedUser+"】";
		var inputComments = this.getCmpByName('comments').getValue();
		this.getCmpByName('comments').setValue(commentRemarks + inputComments);//设置最后存入数据库表的备注值
		this.formPanel.getForm().submit({
			scope:this,
			params:{
				taskId : this.taskId,
				runId : this.runId,
				startUserId : this.startUserId/*,
				comments : inputComments*/
			},
			url:__ctxPath+'/creditFlow/projectAssignCreditProject.do',
			method:'POST',
			success:function(fp,action){
				Ext.ux.Toast.msg('操作信息','成功更改项目人员!');
				this.close();
				/*closeProjectInfoTab(this.projectId, this.idPrefix, this.idPrefix_edit);
				ZW.refreshTaskPanelView();*/
			},
			failure:function(fp,action){
				Ext.ux.Toast.msg('操作信息','操作失败，请联系管理员!');
			}
		});
		//}
	}
});

