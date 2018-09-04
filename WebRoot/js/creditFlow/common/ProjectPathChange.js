/**
 * 流程跳转
 * 
 * @class ProjectPathChange
 * @extends Ext.Window
 */
ProjectPathChange = Ext.extend(Ext.Window, {
	isIntoMiddle : false,
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIComponents();
		ProjectPathChange.superclass.constructor.call(this, {
			title : '流程跳转',
			iconCls : "btn-changeTask",
			modal : true,
			width : 433,
			height : 188,
			layout : 'fit',
			autoScroll : false,
			items : [this.rightPanel],
			buttonAlign : 'center',
			buttons : [{
				text : '确认',
				scope : this,
				iconCls : 'btn-ok',
				handler : this.submit
			}, {
				text : '取消',
				scope : this,
				iconCls : 'btn-cancel',
				scope : this,
				handler : function() {
					this.close();
				}
			}]
		});
	},
	initUIComponents : function() {
		this.taskId = this.record.data.taskId;
		this.currentActivityName = this.record.data.activityName;
		this.projectId = this.record.data.projectId;
		this.businessType = this.record.data.businessType;
		this.rightPanel = new Ext.form.FormPanel({
			border : false,
			region : 'center',
			layout : 'form',
			autoScroll : true,
			bodyStyle : 'padding:15px',
			defaults : {
				anchor : '98%,98%'
			},
			items : [{
				fieldLabel : '跳转节点',
				allowBlank : false,
				xtype : 'combo',
				name : 'destName',
				allowBlank : false,
				editable : false,
				lazyInit : false,
				anchor : '65%',
				triggerAction : 'all',
				listeners : {
					scope : this,
					select : function(combo, record, index) {
						var destName = record.data.destName;
						var destType = record.data.destType;
						var isJumpToTargetTask = record.data.isJumpToTargetTask;
						var taskSequence = parseInt(record.data.taskSequence);
						if (taskSequence >= 2000) {// 项目进入贷、保中阶段
							this.isIntoMiddle = true;
						}
						if (isJumpToTargetTask == 0) {
							this.jumpPrompt.call(this, destName);
							combo.clearValue();
							return;
						}
					}
				},
				store : new Ext.data.ArrayStore({
					autoLoad : true,
					url : __ctxPath
							+ '/flow/freeTransProcessActivity.do?taskId='
							+ this.taskId,
					fields : ['signalName', 'destName', 'destType',
							'isJumpToTargetTask', 'taskSequence']
				}),
				displayField : 'destName',
				valueField : 'signalName'
			}, {
				xtype : "textarea",
				name : "comments",
				allowBlank : false,
				anchor : "100%",
				fieldLabel : "备注"
			}]
		});
	},
	submit : function() {
		var win = this;
		var formPanel = this.rightPanel;

		var destCombo = formPanel.getCmpByName('destName');
		var signalName = destCombo.getValue();
		var destName = '';
		var destStore = destCombo.getStore();
		for (var i = 0; i < destStore.getCount(); i++) {
			var rs = destStore.getAt(i);
			if (signalName == rs.data.signalName) {
				destName = rs.data.destName;
				break;
			}
		}
		if (formPanel.getForm().isValid()) {
			if (this.currentActivityName == "贷中监管"
					|| this.currentActivityName == "项目完成") {
				Ext.ux.Toast.msg('操作信息', '不能进行此操作！');
				return;
			}
			var toActivityName = destName;

			var commentRemarks = "【流程跳转：" + this.currentActivityName + "→"
					+ toActivityName + "】";
			var inputComments = this.getCmpByName('comments').getValue();
			this.getCmpByName('comments').setValue(commentRemarks
					+ inputComments);// 设置最后存入数据库表的备注值
			formPanel.getForm().submit({
				url : __ctxPath + "/flow/nextProcessActivity.do",
				method : 'post',
				waitMsg : '正在提交处理，请稍等',
				params : {
					taskId : this.taskId,
					isJump : 1,// 1表示跳转
					signalName : signalName,
					destName : destName
				},
				scope : this,
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存！');
					// 跳转到贷、保中项目阶段后，使其相应款项生效
					if (this.isIntoMiddle == true) {
						Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/ajaxEffectPaymentCreditProject.do',
							scope : this,
							params : {
								projectId : this.projectId,
								businessType : this.businessType
							},
							success : function(response, options) {
							}
						});
					}
					win.close();
					closeProjectInfoTab(this.projectId, this.idPrefix,
							this.idPrefix_edit);
					ZW.refreshTaskPanelView();
				},
				failure : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
				}
			});
		}
	},
	// 任务跳转提示
	jumpPrompt : function(destName) {
		Ext.ux.Toast.msg('提示信息', '不能跳转到' + destName + '节点!');
	}
});