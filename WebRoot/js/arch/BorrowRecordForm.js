/**
 * @author
 * @createtime
 * @class BorrowRecordForm
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */
BorrowRecordForm = Ext.extend(Ext.Window, {
	returnStatusName : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BorrowRecordForm.superclass.constructor.call(this, {
			id : 'BorrowRecordFormWin',
			// layout : 'form',
			items : [this.formPanel, this.fileListGrid],
			modal : true,
			height : 600,
			width : 800,
			maximizable : true,
			title : '借阅文件详细信息',
			buttonAlign : 'center',
			listeners : {
				// 'afterrender' : function(window) {
				// window.maximize();
				// }
				'afterrender' : function(window) {
					var wh = window.getInnerHeight();
					var fh = window.formPanel.getHeight();
					window.fileListGrid.setHeight(wh - fh);

					var returnStatus = window.returnStatus * 1

					window.returnStatusName = '';
					switch (returnStatus) {
						case 0 :
							window.returnStatusName = '申请';
							window.reSaveButton.setVisible(false);
							window.returnButton.setVisible(false);
							Ext.getCmp('BorrowRecordForm.borrowRecord.returnStatusName.Container')
							.hide();
							break;
						case 1 :
							window.returnStatusName = '通过';
							window.saveButton.setVisible(false);
							window.reSaveButton.setVisible(false);
							window.resetButton.setVisible(false);
							break;
						case -1 :
							window.returnStatusName = '驳回';
							window.saveButton.setVisible(false);
							window.returnButton.setVisible(false);
							break;
						case 2 :
							window.returnStatusName = '归还';
							window.saveButton.setVisible(false);
							window.returnButton.setVisible(false);
							window.resetButton.setVisible(false);
							break;

					}
					window.formPanel
							.getForm()
							.findField('BorrowRecordForm.borrowRecord.returnStatusName')
							.setValue(window.returnStatusName);

					//window.doLayout(true, true);

				}
			},

			buttons : [this.saveButton, this.reSaveButton, this.returnButton,
					this.resetButton, {
						text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : this.cancel
					}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.saveButton = new Ext.Button({
					text : '申请',
					iconCls : 'btn-save',
					scope : this,
					handler : function() {
						this.save(0);
					}
				});
		this.reSaveButton = new Ext.Button({
					text : '重新申请',
					iconCls : 'btn-save',
					scope : this,
					handler : function() {
						this.save(0);
					}
				});
		this.returnButton = new Ext.Button({
					text : '归还',
					iconCls : 'btn-collapse',
					scope : this,
					handler : function() {
						this.save(2);
					}
				});
		this.resetButton = new Ext.Button({
					text : '重置',
					iconCls : 'btn-reset',
					scope : this,
					handler : this.reset
				});

		this.formPanel = new Ext.FormPanel({
			id : 'BorrowRecordForm',
			layout : 'column',
			bodyStyle : 'padding:30px',
			border : false,
			autoScroll : true,
			defaults : {
				border : false,
				anchor : '98%,98%'
			},
			items : [{
						name : 'borrowRecord.recordId',
						xtype : 'hidden',
						value : this.recordId == null ? '' : this.recordId
					},

					{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅编号',
							allowBlank : false,
							//width : 604,
							anchor : '97%',
							xtype : 'textfield',
							readOnly : true,
							value : new Date().getFullYear()+''+(new Date().getMonth()+1)+''+new Date().getDate()+'-'+new Date().getHours()+new Date().getMinutes(),
							name : 'borrowRecord.borrowNum'

						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅人',anchor : '95%',
							readOnly : true,
							value : curUserInfo.fullname,
							width : 100,
							xtype : 'textfield',
							name : 'borrowRecord.checkUserName'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅日期',anchor : '95%',
							readOnly : (this.returnStatus == 1)
									? true
									: false,
							allowBlank : false,
							width : 100,
							xtype : 'datefield',
							format : 'Y-m-d',
							value : new Date(),
							name : 'borrowRecord.borrowDate'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '应还日期',anchor : '95%',
							allowBlank : false,
							readOnly : (this.returnStatus == 1)
									? true
									: false,
							width : 100,
							name : 'borrowRecord.returnDate',
							xtype : 'datefield',
							format : 'Y-m-d'

						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅方式',anchor : '95%',
							readOnly : (this.returnStatus == 1)
									? true
									: false,
							width : 100,
							name : 'borrowRecord.borrowType',
							editable : true,
							lazyInit : false,
							forceSelection : false,
							xtype : 'diccombo',
							itemName : '借阅方式'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅目的',anchor : '95%',
							allowBlank : false,
							readOnly : (this.returnStatus == 1)
									? true
									: false,
							width : 100,
							name : 'borrowRecord.borrowReason',
							editable : true,
							lazyInit : false,
							forceSelection : false,
							xtype : 'diccombo',
							itemName : '借阅目的'
						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						id : 'BorrowRecordForm.borrowRecord.returnStatusName.Container',
						items : {
							fieldLabel : '借阅状态',anchor : '95%',
							allowBlank : false,
							width : 100,
							id : 'BorrowRecordForm.borrowRecord.returnStatusName',
							// name : 'borrowRecord.returnStatus',
							readOnly : true,
							xtype : 'textfield'
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '备注',
							readOnly : (this.returnStatus == 1)
									? true
									: false,
							allowBlank : true,
							//width : 604,
							anchor : '97%',
							xtype : 'textarea',
							name : 'borrowRecord.borrowRemark'

						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '登记人ID',
							value : curUserInfo.userId,
							width : 100,
							xtype : 'hidden',
							name : 'borrowRecord.checkUserId'
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						hidden : true,
						items : {
							fieldLabel : '登记日期',
							width : 100,
							xtype : 'datefield',
							format : 'Y-m-d',
							value : new Date(),
							name : 'borrowRecord.checkDate'
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						hidden : true,
						items : {
							fieldLabel : '借阅状态',
							allowBlank : false,
							width : 100,
							id : 'BorrowRecordForm.borrowRecord.returnStatus',
							name : 'borrowRecord.returnStatus',
							xtype : 'numberfield',
							value : this.returnStatus
						}
					}

			]
		});

		this.fileListGrid = new BorrowFileListView({
			returnStatus:this.returnStatus			
		});
		this.fileListGrid.store.on('beforeload', function(store) {

					if (this.recordId) {
						store.baseParams = {

							'Q_borrowRecord.recordId_L_EQ' : this.recordId,
							start : 0,
							limit : 100000
						}
					} else {
						return false;
					}

				}, this);

		this.load();

	},// end of the initcomponents
	load : function() {
		// 加载表单对应的数据

		if (this.recordId != null && this.recordId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/arch/getBorrowRecord.do?recordId='
								+ this.recordId,
						root : 'data',
						preName : 'borrowRecord'
					});
			this.fileListGrid.store.load();

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
	save : function(returnStatus) {
		if (this.formPanel.getForm().isValid()) {

			this.formPanel.getForm()
					.findField('BorrowRecordForm.borrowRecord.returnStatus')
					.setValue(returnStatus);

			var params = [];
			var store = this.fileListGrid.pageingStore;

			var cnt = store.getTotalCount();

			for (i = 0; i < cnt; i++) {

				var record = store.allData.items[i];

				params.push(record.data);

			}
			if(params.length>0){
				this.formPanel.getForm().submit({
							clientValidation : true,
							url : __ctxPath + '/arch/saveBorrowRecord.do',
							params : {
								params : Ext.encode(params)
							},
							success : function(form, action) {
								Ext.ux.Toast.msg("操作信息", "添加申请成功!");
								Ext.getCmp('BorrowRecordFormWin').close();
								Ext.getCmp('MyBorrowRecordGrid').getStore().reload();
	
							},
							failure : function(form, action) {
	
								Ext.MessageBox.show({
											title : '操作信息',
											msg : '信息保存出错，请联系管理员！',
											buttons : Ext.MessageBox.OK,
											icon : Ext.MessageBox.ERROR
										});
							}
						});
			}else{
				Ext.ux.Toast.msg("操作信息", "借阅清单不能为空!");
			}		
					
					

		}

	}// end of save

});