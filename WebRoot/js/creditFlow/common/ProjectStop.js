/**
 * 终止项目
 * 
 * @class ProjectStop
 * @extends Ext.Window
 */
ProjectStop = Ext.extend(Ext.Window, {
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIComponents();
		ProjectStop.superclass.constructor.call(this, {
			title : '终止项目',
			border : false,
			modal : true,
			width : 433,
			height : 188,
			layout : 'border',
			iconCls : 'btn-close1',
			items : [this.panel],
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
		this.runId = this.record.data.runId;
		this.projectId = this.record.data.projectId;
		this.businessType = this.record.data.businessType;
		this.panel = new Ext.form.FormPanel({
			region : 'center',
			layout : 'form',
			autoScroll : true,
			bodyStyle : 'padding:15px',
			defaults : {
				anchor : '98%,98%'
			},
			items : [{
				fieldLabel : '终止原因',
				mode : 'local',
				allowBlank : false,
				xtype : 'combo',
				name : 'reason',
				allowBlank : false,
				editable : false,
				anchor : '60%',
				store : new Ext.data.SimpleStore({
					fields : ["value"],
					data : [["项目结款"], ["提前结款"], ["被迫终止"], ["其他原因"]]
				}),
				displayField : 'value',
				triggerAction : "all"
			}, {
				xtype : "textarea",
				name : "comments",
				allowBlank : false,
				anchor : "100%",
				fieldLabel : "操作说明"
			}]
		});
	},
	submit : function() {
		var win = this;
		var formPanel = this.panel;
		var reason = formPanel.getCmpByName('reason').getValue();
		if (formPanel.getForm().isValid()) {
			var commentRemarks = "【" + reason + "】";
			var inputComments = this.getCmpByName('comments').getValue();
			this.getCmpByName('comments').setValue(commentRemarks
					+ inputComments);// 设置最后存入数据库表的备注值
			var comments = this.getCmpByName('comments').getValue();
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/endCreditProject.do',
				params : {
					taskId : this.taskId,
					runId : this.runId,
					projectId : this.projectId,
					businessType : this.businessType,
					comments : commentRemarks+inputComments,
					type:this.type
				},
				scope : this,
				method : 'post',
				success : function(resp, op) {
					var res = Ext.util.JSON.decode(resp.responseText);
					if (res.success) {
						Ext.ux.Toast.msg('信息提示', '成功终止项目！');
						win.close();
						closeProjectInfoTab(this.projectId, this.idPrefix,
								this.idPrefix_edit);
						ZW.refreshTaskPanelView();
					} else {
						Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
					}
				},
				failure : function() {
					Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
				}

			});

		}
	}
})