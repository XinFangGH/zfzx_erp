/**
 * @author
 * @createtime
 * @class NewBorrowRecordFormPan
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */
NewBorrowRecordFormPan = Ext.extend(Ext.Panel, {
	returnStatusName : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		NewBorrowRecordFormPan.superclass.constructor.call(this, {
			id : 'NewBorrowRecordFormPan',
			layoutConfig : {
				padding : '5',
				pack : 'center',
				align : 'middle'
			},
			layout : 'form',
			items : [this.formPanel, this.fileListGrid],
			title : '借阅文件详细信息',
			iconCls : "menu-newBorrowRecord",
			buttonAlign : 'center',
			listeners : {

				'afterlayout' : function(window) {
					var wh = window.getInnerHeight();
					var fh = window.formPanel.getHeight();
					window.fileListGrid.setHeight(wh - fh);
					window.returnStatusName = '申请';
					window.formPanel
							.getForm()
							.findField('NewBorrowRecordForm.borrowRecord.returnStatusName')
							.setValue(window.returnStatusName);

					// window.doLayout(true, true);

				}
			},

			buttons : [this.saveButton, this.resetButton, {
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

		this.resetButton = new Ext.Button({
					text : '重置',
					iconCls : 'btn-reset',
					scope : this,
					handler : this.reset
				});

		this.formPanel = new Ext.FormPanel({
			id : 'NewBorrowRecordForm',
			layout : 'column',
			bodyStyle : 'padding:30px',
			border : true,
			region : 'north',
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
							// width : 638,
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
							fieldLabel : '借阅人',
							anchor : '95%',
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
							fieldLabel : '借阅日期',
							anchor : '95%',
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
							fieldLabel : '应还日期',
							allowBlank : false,
							anchor : '95%',
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
							fieldLabel : '借阅方式',
							anchor : '95%',
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
							fieldLabel : '借阅目的',
							allowBlank : false,
							anchor : '95%',
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
						hidden:true,
						items : {
							fieldLabel : '借阅状态',
							anchor : '95%',
							allowBlank : false,
							width : 100,
							id : 'NewBorrowRecordForm.borrowRecord.returnStatusName',
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

							allowBlank : true,
							// width : 638,
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
							id : 'NewBorrowRecordForm.borrowRecord.returnStatus',
							name : 'borrowRecord.returnStatus',
							xtype : 'numberfield',
							value : 0
						}
					}

			]
		});

		this.fileListGrid = new BorrowFileListView({
					region : 'center',
					returnStatus : 0
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
		var centerPanel = Ext.getCmp('centerTabPanel');
							centerPanel.remove(Ext
									.getCmp('NewBorrowRecordFormPan'));
	},
	/**
	 * 保存记录
	 */
	save : function() {
		if (this.formPanel.getForm().isValid()) {

			this.formPanel.getForm()
					.findField('NewBorrowRecordForm.borrowRecord.returnStatus')
					.setValue(0);

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
							var centerPanel = Ext.getCmp('centerTabPanel');
							centerPanel.remove(Ext
									.getCmp('NewBorrowRecordFormPan'));

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