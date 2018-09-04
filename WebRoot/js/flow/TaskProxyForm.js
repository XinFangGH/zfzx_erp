/**
 * @author
 * @createtime
 * @class TaskProxyForm
 * @extends Ext.Window
 * @description TaskProxy表单
 * @company 智维软件
 */
TaskProxyForm = Ext.extend(Ext.Window, {
	readOnly:false,
	title:'新增代理信息',
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.readOnly) != "undefined") {
			this.readOnly = _cfg.readOnly;
			if(this.readOnly){
				this.title="查看代理信息";
			}else{
				this.title="编辑代理信息";
			}
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		TaskProxyForm.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 200,
			width : 500,
			resizable : false,
			title : this.title,
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				hidden:this.readOnly,
				handler : this.save
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				hidden:this.readOnly,
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				hidden:this.readOnly,
				handler : this.cancel
			}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
				name : 'taskProxy.id',
				xtype : 'hidden',
				value : this.id == null ? '' : this.id
			},{
				name : 'taskProxy.principalId',
				xtype : 'hidden'
			},{
				name : 'taskProxy.agentId',
				xtype : 'hidden'
			}, {
				xtype : 'trigger',
				fieldLabel : '被代理人',
				name : 'principalName',
				allowBlank:false,
				triggerClass : 'x-form-search-trigger',
				editable : false,
				scope : this,
				readOnly:this.readOnly,
				value:this.principalName,
				onTriggerClick : function() {
					var obj = this;
					UserLevelSelector.getView(
						function(id, name) {
							obj.setValue(name);
							obj.getOriginalContainer().getCmpByName('taskProxy.principalId').setValue(id);
					},currentUserId).show();
				}
			}, {
				xtype : 'trigger',
				fieldLabel : '代理人',
				name : 'agentName',
				allowBlank:false,
				readOnly:this.readOnly,
				triggerClass : 'x-form-search-trigger',
				editable : false,
				scope : this,
				value:this.agentName,
				onTriggerClick : function() {
					var obj = this;
					UserLevelSelector.getView(
						function(id, name) {
							obj.setValue(name);
							obj.getOriginalContainer().getCmpByName('taskProxy.agentId').setValue(id);
					},currentUserId).show();
				}
			}, {
				fieldLabel : '代理开始日期',
				name : 'taskProxy.startDate',
				xtype : 'datefield',
				allowBlank:false,
				readOnly:this.readOnly,
				format : 'Y-m-d'
			}, {
				fieldLabel : '代理结束日期',
				name : 'taskProxy.endDate',
				xtype : 'datefield',
				allowBlank:false,
				readOnly:this.readOnly,
				format : 'Y-m-d'
			}, {
				name : 'taskProxy.createId',
				xtype : 'hidden'
			}, {
				name : 'taskProxy.status',
				xtype : 'hidden'
			}]
		});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + '/flow/getTaskProxy.do?id=' + this.id,
				root : 'data',
				preName : 'taskProxy'
			});
		}
	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 * formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 * window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		this.getCmpByName('taskProxy.createId').setValue(currentUserId);
		this.getCmpByName('taskProxy.status').setValue(0);
		var principalId=this.getCmpByName('taskProxy.principalId').getValue();
		var agentId=this.getCmpByName('taskProxy.agentId').getValue();
		if(principalId && agentId && principalId==agentId){
			Ext.ux.Toast.msg('操作信息', '被代理人和代理人不能是同一个人,请重新填写!');
		}else{
			var parentGridPanel=this.parentGridPanel;
			$postForm({
				formPanel : this.formPanel,
				scope : this,
				url : __ctxPath + '/flow/saveTaskProxy.do',
				callback : function(fp, action) {
					parentGridPanel.getStore().reload();
					this.close();
				}
			});
		}
	}// end of save
});