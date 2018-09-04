/**
 * 任务换人
 * 
 * @class ProjectTaskHandler
 * @extends Ext.Window
 */
ProjectTaskHandler = Ext.extend(Ext.Window, {
	//originalUser : "",// 该任务节点最初的处理人
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIs();
		ProjectTaskHandler.superclass.constructor.call(this, {
			title : '指定待办人',
			border : false,
			width : 433,
			height : this.isActivityComboEdit ? 211 : 183,
			layout : 'fit',
			autoScroll : false,
			modal : true,
			iconCls : 'btn-users-sel',
			items : this.formPanel,
			buttonAlign : 'center',
			buttons : [{
				text : '确认',
				iconCls : 'btn-ok',
				scope : this,
				handler : this.submit
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.close
			}]
		});
	},
	// 初始化UI
	initUIs : function() {
		this.taskId = this.record.data.taskId;
		this.projectId = this.record.data.projectId;
		//this.originalUser = this.userName;
		this.formPanel = new Ext.form.FormPanel({
			layout : 'form',
			modal : true,
			bodyStyle : 'padding:15px',
			items : [{
				xtype : 'hidden',
				name : 'userId'
			}, {
				fieldLabel : '任务节点选择',
				allowBlank : false,
				xtype : 'combo',
				name : 'destName',
				allowBlank : false,
				editable : false,
				lazyInit : false,
				anchor : '82%',
				triggerAction : 'all',
				listeners : {
					scope : this,
					select : function(combo, record, index) {/*
						this.taskId = record.data.taskId;
						var activityName = record.data.activityName;
						Ext.Ajax.request({
							url : __ctxPath + '/flow/usersProcessActivity.do',
							scope : this,
							params : {
								isParentFlow : false,
								isGetCurrent : true,
								taskId : this.taskId,
								activityName : activityName
							},
							success : function(response, options) {
								var result = Ext.decode(response.responseText);
								this.originalUser = [result.userNames];
							}
						});
					*/}
				},
				store : new Ext.data.ArrayStore({
					autoLoad : true,
					url : __ctxPath
							+ '/flow/getUnFinishedActivitysProcessActivity.do?taskId='
							+ this.taskId,
					fields : ['activityName', 'taskId']
				}),
				displayField : 'activityName',
				valueField : 'taskId'
			}, {
				xtype : 'compositefield',
				fieldLabel : '指定人员',
				allowBlank : false,
				items : [{
					xtype : 'textfield',
					name : 'userName',
					anchor : "50%",
					disabled:true,
					allowBlank : false

				}, {
					xtype : 'button',
					text : '选择代办人员',
					anchor : "50%",
					scope : this,
					handler : this.selectUser
				}]
			}, {
				xtype : "textarea",
				name : "comments",
				allowBlank : false,
				anchor : "100%",
				fieldLabel : "备注"
			}]
		});
		if (this.isActivityComboEdit == false) {// 不需要选择任务节点时，去除combo组件
			this.formPanel.remove(this.formPanel.get(1));
			this.formPanel.doLayout();
		}
	},
	selectUser : function() {
		var userId = this.getCmpByName('userId');
		var userName = this.getCmpByName('userName');

		UserSelector.getView(function(uIds, uNames) {
			userId.setValue(uIds);
			userName.setValue(uNames);
		}, true).show();
	},
	submit : function() {
		if (this.formPanel.getForm().isValid()) {
			var selectedUser = this.getCmpByName('userName').getValue();
			var inputComments = this.getCmpByName('comments').getValue();
			var commentRemarks = "【待办人调换：" + this.userId + "→" + selectedUser + "】";
			this.getCmpByName('comments').setValue(commentRemarks + inputComments);// 设置最后存入数据库表的备注值
			this.formPanel.getForm().submit({
				scope : this,
				params : {
					taskIds : this.taskId
				},
				url : __ctxPath + '/flow/handlerTask.do',
				method : 'POST',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功更改待办人员!');
					this.close();
					closeProjectInfoTab(this.projectId, this.idPrefix, this.idPrefix_edit);
					ZW.refreshTaskPanelView();
				},
				failure : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '操作失败，请联系管理员!');
				}
			});
		}
	}
});
