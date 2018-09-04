/**
 * @author
 * @class SlSupervisionRecord
 * @extends Ext.Panel
 * @description 监管信息
 * @company 智维软件
 * @createtime:
 */
GlobalSupervisionRecord = Ext.extend(Ext.Panel, {
	projectId : null,
	superviseManageId : null,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.superviseManageId) != "undefined") {
			this.superviseManageId = _cfg.superviseManageId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		Ext.applyIf(this, _cfg);
		GlobalSupervisionRecord.superclass.constructor.call(this, {
			border : false,
			columnWidth : 1,
			layout : 'column',
			defaults : {
				anchor : '100%',
				columnWidth : .1
			},
			items : [{
					xtype : "hidden",
					name : "globalSupervisemanage.superviseManageId",
					value : this.superviseManageId
				},{
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .25,
					items : [{
						fieldLabel : '监管人',
						name : 'globalSupervisemanage.superviseManagerName',
						value : currentUserFullName,
						xtype : 'textfield',
						anchor : '100%',
						readOnly : true
					}, {
						xtype : "hidden",
						name : "globalSupervisemanage.superviseManager",
						value : currentUserId
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .25,
					items : [{
						fieldLabel : '监管时间',
						name : 'globalSupervisemanage.superviseManageTime',
						xtype : 'datefield',
						format : 'Y-m-d',
						value : new Date(),
						allowBlank : false,
						anchor : '100%'
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .25,
					items : [{
						xtype : "diccombo",
						fieldLabel : '监管方式',
						hiddenName : 'globalSupervisemanage.superviseManageMode',
						itemName : '监管方式', // xx代表分类名称
						isDisplayItemName : false,
						allowBlank : false,
						editable : false,
						value : null,
						emptyText : "请选择",
						anchor : '100%',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.clearInvalid();
									if (combox.getValue() > 0) {
										combox.setValue(combox.getValue());
									}
								})
							}
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .25,
					items : [{
						xtype : "diccombo",
						fieldLabel : '监管意见',
						hiddenName : 'globalSupervisemanage.superviseManageOpinion',
						itemName : '监管意见', // xx代表分类名称
						isDisplayItemName : false,
						allowBlank : false,
						editable : false,
						value : null,
						emptyText : "请选择",
						anchor : '100%',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.clearInvalid();
									if (combox.getValue() > 0) {
										combox.setValue(combox.getValue());
									}
								})
							}
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : 1,
					items : [{
						fieldLabel : '备注',
						name : 'globalSupervisemanage.superviseManageRemark',
						xtype : 'textarea',
						anchor : '100%'
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : 1,
					style : 'padding-left:104px',
					items : [new uploads({
						title_c : '上传监管报告',
						businessType : this.businessType,
						typeisfile : 'superviseRecordReport',
						tableName : 'global_supervise_report_'+this.businessType+'.'+ this.superviseManageId,
						projectId : this.projectId,//改进过的
						uploadsSize : 1
					})]	
				}]
			}]
		});
		if (this.superviseManageId != null && this.superviseManageId != 'undefined') {
			this.loadData({
						url : __ctxPath
								+ '/supervise/getGlobalSupervisemanage.do?superviseManageId='
								+ this.superviseManageId,
						root : 'data',
						preName : 'globalSupervisemanage',
						scope : this
					});
		}
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
		if (store.getCount() > 0) {
			var fileid = store.getAt(0).get('fileid');
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/fileUploads/DeleRsFileForm.do?fileid='
						+ fileid,
				method : 'POST',
				success : function() {

				},
				failure : function(result, action) {
				}
			});
		}
		Ext.Ajax.request({
			url : __ctxPath
//					+ "/project/cancleSuperviseRecordSlSmallloanProject.do",
					+ "/creditFlow/leaseFinance/project/cancleSuperviseRecordFlLeaseFinanceProject.do",
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
			url : __ctxPath
//					+ "/project/saveSuperviseRecordSlSmallloanProject.do",
					+ "/creditFlow/leaseFinance/project/saveSuperviseRecordFlLeaseFinanceProject.do",
			params : {
				superviseId : this.superviseId,
				businessType:this.businessType
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
