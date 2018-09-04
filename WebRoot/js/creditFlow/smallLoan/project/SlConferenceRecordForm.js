/**
 * @author
 * @createtime
 * @class SlConferenceRecordForm
 * @extends Ext.Window
 * @description SlConferenceRecord表单
 * @company 智维软件
 */
SlConferenceRecordForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SlConferenceRecordForm.superclass.constructor.call(this, {
			id : 'SlConferenceRecordFormWin',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 400,
			width : 500,
			maximizable : true,
			title : '[SlConferenceRecord]详细信息',
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
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
			// id : 'SlConferenceRecordForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
				name : 'slConferenceRecord.conforenceId',
				xtype : 'hidden',
				value : this.conforenceId == null ? '' : this.conforenceId
			}, {
				fieldLabel : 'projectId',
				name : 'slConferenceRecord.projectId',
				xtype : 'numberfield'
			}, {
				fieldLabel : '会议时间',
				name : 'slConferenceRecord.conforenceTime',
				xtype : 'datefield',
				xtype : 'datetimefield',
				format : 'Y-m-d H:i:s',
				value : new Date()
			}, {
				fieldLabel : '会议地点',
				name : 'slConferenceRecord.conforencePlace',
				maxLength : 200
			}, {

				id : 'recordPersonname',
				name : 'slConferenceRecord.recordPerson',
				fieldLabel : '记录人员',
				xtype : 'trigger',
				triggerClass : 'x-form-search-trigger',
				// emptyText : '点击选择人员----------------',
				editable : false,
				onTriggerClick : function() {
					new UserDialog({
						scope : this,
						single : false,
						callback : function(uId, uname) {
							// alert(uId);
							// aert(uname);
							Ext.getCmp('recordPersonname').setValue(uname);;
						}
					}).show();

				}
			}, {
				id : 'jionPersonname',
				name : 'slConferenceRecord.jionPerson',
				fieldLabel : '参与人员',
				xtype : 'trigger',
				triggerClass : 'x-form-search-trigger',
				// emptyText : '点击选择人员----------------',
				editable : false,
				onTriggerClick : function() {
					new UserDialog({
						scope : this,
						single : false,
						callback : function(uId, uname) {
							// alert(uId);
							// aert(uname);
							Ext.getCmp('jionPersonname').setValue(uname);;
						}
					}).show();

				}

			}, {
				fieldLabel : '决议方式',
				name : 'slConferenceRecord.decisionType',
				xtype : 'numberfield'
			}, {
				fieldLabel : '会议结果',
				name : 'slConferenceRecord.conferenceResult',
				maxLength : 200
			}]
		});
		// 加载表单对应的数据
		if (this.conforenceId != null && this.conforenceId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/project/getSlConferenceRecord.do?conforenceId='
						+ this.conforenceId,
				root : 'data',
				preName : 'slConferenceRecord'
			});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + '/project/saveSlConferenceRecord.do',
			callback : function(fp, action) {
				var gridPanel = Ext.getCmp('SlConferenceRecordGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});