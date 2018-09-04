/**
 * @author
 * @createtime
 * @class RollFileForm
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */
RollFileForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RollFileForm.superclass.constructor.call(this, {
					id : 'RollFileFormWin',
					layout : 'form',
					items : [this.formPanel, this.fileGrid],
					modal : true,
					height : 600,
					width : 800,
					maximizable : true,
					title : '卷内文件详细信息',
					buttonAlign : 'center',
					// listeners : {
					// 'afterrender' : function(window) {
					// window.maximize();
					// }},

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
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			id : 'RollFileForm',
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			// id : 'RollFileForm',
			defaults : {
				border : false,
				anchor : '98%,98%'
			},
			// defaultType : 'textfield',
			items : [{
						name : 'rollFile.rollFileId',
						xtype : 'hidden',
						value : this.rollFileId == null ? '' : this.rollFileId
					},

					{
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '文件题名',
							width : 100,
							xtype : 'textfield',
							name : 'rollFile.fileName',
							allowBlank : false,
							maxLength : 128
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '文件编号',
							width : 100,
							xtype : 'textfield',
							id : 'RollFileForm.rollFile.fileNo',
							name : 'rollFile.fileNo',
							allowBlank : false,
							maxLength : 64
						}
					},

					{
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '文件分类',
							width : 100,

							name : 'rollFile.globalType.typeName',
							xtype : 'combo',
							mode : 'local',
							editable : false,
							triggerAction : 'all',
							value : this.typeName == null ? '' : this.typeName,
							store : new Ext.data.JsonStore({
										url : __ctxPath
												+ "/system/subGlobalType.do",
										baseParams : {
											parentId : 0,
											catKey : 'AR_RLF'
										},
										autoLoad : true,
										autoShow : true,
										// reader configs
										root : 'result',
										idProperty : 'proTypeId',
										fields : ['proTypeId', 'typeName']
									}),
							valueField : 'proTypeId',
							displayField : 'typeName',
							listeners : {
								scope : this,
								'select' : function(combo, record, index) {
									Ext.getCmp('RollFileForm').getForm()
											.findField('rollFile.proTypeId')
											.setValue(record.get('proTypeId'));
								}
							}

						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '全宗号',
							width : 100,
							name : 'rollFile.archRoll.archFond.afNo',
							id : 'rollFile.afNo',
							xtype : 'combo',
							mode : 'local',
							editable : true,
							lazyInit : false,
							forceSelection : false,
							triggerAction : 'all',
							store : new Ext.data.JsonStore({
										url : __ctxPath
												+ "/arch/listArchFond.do",
										autoLoad : true,
										autoShow : true,
										// reader configs
										root : 'result',
										fields : ['archFondId', 'afNo']
									}),
							valueField : 'archFondId',
							displayField : 'afNo',
							listeners : {
								scope : this,
								'select' : function(combo, record, index) {

									Ext.getCmp('rollFile.archRoll.rollNo')
											.getStore().load({
												params : {

													'Q_archFond.archFondId_L_EQ' : record.data.archFondId
												}
											});
									Ext.getCmp('rollFile.archRoll.rollNo')
											.reset();
								}
							}
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '案卷号',
							width : 100,
							id : 'rollFile.archRoll.rollNo',
							name : 'rollFile.archRoll.rollNo',
							allowBlank : true,
							xtype : 'combo',
							mode : 'local',
							editable : false,
							triggerAction : 'all',
							store : new Ext.data.JsonStore({
										url : __ctxPath
												+ "/arch/listArchRoll.do",
										autoLoad : true,
										autoShow : true,
										// reader configs
										root : 'result',
										idProperty : 'rollId',
										fields : ['rollId', 'rollNo', 'afNo']
									}),
							valueField : 'rollId',
							displayField : 'rollNo',
							listeners : {
								scope : this,
								'select' : function(combo, record, index) {
									Ext.getCmp('RollFileForm').getForm()
											.findField('rollFile.rollId')
											.setValue(record.get('rollId'));

									// Ext.getCmp('RollFileForm')
									// .getForm()
									// .findField('rollFile.afNo')
									// .setValue(record
									// .get('afNo'));
								}
							}
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '目录号',
							width : 100,
							xtype : 'textfield',
							name : 'rollFile.catNo',
							maxLength : 64
						}
					},

					{
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '顺序号',
							width : 100,
							name : 'rollFile.seqNo',
							xtype : 'numberfield'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '页号',
							width : 100,
							name : 'rollFile.pageNo',
							xtype : 'numberfield'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '页数',
							width : 100,
							name : 'rollFile.pageNums',
							xtype : 'numberfield'
						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '密级',
							width : 100,
							name : 'rollFile.secretLevel',
							editable : true,
							lazyInit : false,
							forceSelection : false,
							xtype : 'diccombo',
							itemName : '文件密级'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '保管期限',
							width : 100,
							name : 'rollFile.timeLimit',
							editable : true,
							lazyInit : false,
							forceSelection : false,
							xtype : 'diccombo',
							itemName : '文件保管期限'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '开放形式',
							width : 100,

							name : 'rollFile.openStyle',
							editable : true,
							lazyInit : false,
							forceSelection : false,
							xtype : 'diccombo',
							itemName : '文件开放形式'
						}
					}

					, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '主题词',
							width : 604,
							height : 40,
							name : 'rollFile.keyWords',
							xtype : 'textarea',
							maxLength : 512
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '附注',
							width : 604,
							height : 40,
							name : 'rollFile.notes',
							xtype : 'textarea',
							maxLength : 4000
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '内容',
							width : 604,
							name : 'rollFile.content',
							xtype : 'textarea',
							maxLength : 65535
						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '文件时间',
							width : 100,
							name : 'rollFile.fileTime',
							xtype : 'datefield',
							format : 'Y-m-d',
							value : new Date()
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '责任者',
							width : 100,
							xtype : 'textfield',
							name : 'rollFile.dutyPerson',
							maxLength : 32
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '归档状态',
							width : 100,
							hiddenName : 'rollFile.archStatus',
							xtype : 'combo',
							mode : 'local',
							editable : false,
							value : '0',
							triggerAction : 'all',
							store : [['0', '未归档'], ['1', '已归档']]
						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '录入人',
							width : 100,
							xtype : 'textfield',
							readOnly : true,
							value : curUserInfo.fullname,
							name : 'rollFile.creatorName',
							maxLength : 128
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '录入时间',
							width : 100,
							name : 'rollFile.createTime',
							readOnly : true,
							xtype : 'datefield',
							format : 'Y-m-d',
							value : new Date()
						}
					}

					, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '文件分类ID',
							width : 100,
							id : 'rollFile.proTypeId',
							value : this.proTypeId == null
									? ''
									: this.proTypeId,
							name : 'rollFile.proTypeId',
							xtype : 'hidden'
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '案卷ID',
							width : 100,
							name : 'rollFile.rollId',
							xtype : 'hidden'
						}
					}

			]
		});

		this.fileGrid = new RollFileListView();
		this.fileGrid.getStore().on('beforeload', function(store) {

					if (this.rollFileId) {
						store.baseParams = {

							'Q_rollFile.rollFileId_L_EQ' : this.rollFileId
						}
					} else {
						return false;
					}

				}, this);
		this.load();

	},// end of the initcomponents
	load : function() {
		// 加载表单对应的数据

		if (this.rollFileId != null && this.rollFileId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/arch/getRollFile.do?rollFileId='
								+ this.rollFileId,
						root : 'data',
						preName : 'rollFile'
					});
			this.fileGrid.getStore().load();

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
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function(type) {
		if (this.rollFileId == null || this.rollFileId == 'undefined') {
			var saveFlag = true;
			Ext.Ajax.request({
						url : __ctxPath + "/arch/listRollFile.do",
						method : 'POST',
						async : false,
						success : function(response, opts) {
							var obj = Ext.decode(response.responseText);

							if (obj.result.length > 0) {
								saveFlag = false;
							}
						},
						failure : function(response, opts) {

							
						},
						params : {
							Q_fileNo_S_EQ : this.formPanel.getForm()
									.findField('RollFileForm.rollFile.fileNo')
									.getValue()
						}
					});

			if (!saveFlag) {
				Ext.ux.Toast.msg("操作信息", "文件编号重覆，不能保存！");
				return;
			}
		}

		if (this.formPanel.getForm().isValid()) {
			var params = [];
			var store = this.fileGrid.getStore();
			var cnt = store.getCount();
			for (i = 0; i < cnt; i++) {

				var record = store.getAt(i);

				var fileAttach = record.data['fileAttach']
				if (fileAttach.createtime) {
					Ext.apply(fileAttach, {
								createtime : new Date(fileAttach.createtime)
										.format('Y-m-d')
							});
				}
				params.push(record.data);

			}

			this.formPanel.getForm().submit({
				clientValidation : true,
				url : __ctxPath + '/arch/saveRollFile.do',
				params : {
					params : Ext.encode(params)
				},
				success : function(form, action) {

					Ext.getCmp('RollFileFormWin').rollFileId = action.result.rollFileId;
					Ext.getCmp('RollFileFormWin').load();
						
					Ext.ux.Toast.msg("操作信息", "卷内文件信息保存成功！");
					Ext.getCmp('RollFileGrid').getStore().reload();
					if(type!='sn'){
						Ext.getCmp('RollFileFormWin').close();
					}

				},
				failure : function(form, action) {

					Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
					

				}
			}, this);

		}
		
		

	}// end of save

});