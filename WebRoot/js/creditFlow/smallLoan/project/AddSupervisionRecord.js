/**
 * @author
 * @class AddSupervisionRecord
 * @extends Ext.Window
 * @description 添加监管记录
 * @company 智维软件
 * @createtime:
 */
AddSupervisionRecord = Ext.extend(Ext.Window, {
	projectId : null,
	superviseId : null,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.superviseId) != "undefined") {
			this.superviseId = _cfg.superviseId;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		AddSupervisionRecord.superclass.constructor.call(this, {
			title : '添加监管记录',
			border : false,
			width : 707,
			height : 290,
			modal : true,
			iconCls : '',
			autoScroll : true,
			layout : 'fit',
			items : [this.formPanel],
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
	},
	initUIComponents : function() {
		var operObj = this;
		var rowEditIndex = -1;
		this.uploadViews = new uploads({
			title_c : '上传监管报告',
			businessType : "SmallLoan",
			typeisfile : 'superviseRecordReport',
			mark : 'sl_supervise_report_SmallLoan.' + this.superviseId,
			projectId : this.projectId,
			uploadsSize : 1
		})
		this.formPanel = new Ext.FormPanel({
			layout : 'column',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			monitorValid : true,
			frame : true,
			plain : true,
			labelAlign : "right",
			// id : 'SlPersonMainForm',
			defaults : {
				anchor : '96%',
				labelWidth : 70,
				columnWidth : 1,
				layout : 'column'
			},
			// defaultType : 'textfield',
			items : [{
				columnWidth : 1,
				labelWidth : 70,
				layout : 'form',
				anchor : '100%',
				items : [{
					fieldLabel : '监管人',
					submitValue : true,
					xtype : 'trigger',
					triggerClass : 'x-form-search-trigger',
					editable : false,
					allowBlank : false,
					scope : this,
					onTriggerClick : function() {
						var obj = this;
							var appuserIdObj = obj.nextSibling();
							var appuserNameObj = obj.nextSibling().nextSibling();
							var userIds = appuserIdObj.getValue();
							if (null == obj.getValue() || "" == obj.getValue()) {
								userIds = "";
							}
							new UserDialog({
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择监管人",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuserIdObj.setValue(uId);
									appuserNameObj.setValue(uname);
								}
							}).show();
					},
					anchor : '98%'
				}, {
					xtype : "hidden",
					name : "slSuperviseIn.superviseUsers"
				},{
					xtype : "hidden",
					name : "slSuperviseIn.supervisionPersonName"
				}]
			}, {
				columnWidth : .5,
				labelWidth : 70,
				layout : 'form',
				items : [{
					xtype : "diccombo",
					fieldLabel : '监管意见',
					hiddenName : 'slSuperviseIn.superviseOpinion',
					itemName : '监管意见', // xx代表分类名称
					isDisplayItemName : false,
					allowBlank : false,
					editable : false,
					emptyText : "请选择",
					anchor : "100%"
				}]
			}, {
				columnWidth : .5,
				labelWidth : 70,
				layout : 'form',
				items : [{
					fieldLabel : '监管时间',
					name : 'slSuperviseIn.superviseDateTime',
					xtype : 'datefield',
					anchor : '96%',
					format : 'Y-m-d',
					value : new Date(),
					allowBlank : false
				}]
			}, {
				columnWidth : 1,
				labelWidth : 70,
				layout : 'form',
				items : [{
					fieldLabel : '备注',
					name : 'slSuperviseIn.remark',
					anchor : '98%',
					xtype : 'textarea'

				}]
			}, {
				columnWidth : 1,
				labelWidth : 70,
				layout : 'form',
				style : "padding-left:76px",
				items : [this.uploadViews]
			}

			]
		});
	},
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
		var store = this.uploadViews.grid_UploadsPanel.getStore();
		if(store.getCount() > 0) {
			var fileid = store.getAt(0).get('fileid');
			alert(fileid);
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/fileUploads/DeleRsFileForm.do?fileid=' + fileid,
				method : 'POST',
				success : function() {
				
				},
				failure : function(result, action) {
				}
			});
		}
		Ext.Ajax.request({
			url : __ctxPath
					+ "/project/cancleSuperviseRecordSlSmallloanProject.do",
			params : {
				superviseId : this.superviseId
			},
			method : 'POST',
			scope : this,
			success : function(response) {
			}
		})
		this.close();
	},
	save : function() {
		var grid = this.grid;
		this.formPanel.getForm().submit({
			url :  __ctxPath
					+ "/project/saveSuperviseRecordSlSmallloanProject.do",
			params : {
				superviseId : this.superviseId
			},
			scope : this,
			method : 'POST',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功添加监管记录!');
				grid.getStore().reload();
				this.close();
			},
			failure : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '操作失败，请联系管理员!');
			}
		});
	}
});
