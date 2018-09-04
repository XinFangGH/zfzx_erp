/**
 * @author
 * @createtime
 * @class CheckBorrowRecordForm
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */
CheckBorrowRecordForm = Ext.extend(Ext.Window, {
	returnStatusName : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CheckBorrowRecordForm.superclass.constructor.call(this, {
			id : 'CheckBorrowRecordFormWin',
			// layout : 'form',
			items : [this.formPanel,this.filePanel],
			modal : true,
			height : 600,
			width : 800,
			maximizable : true,
			title : '借阅编号:'
				+ this.borrowNum,
			buttonAlign : 'center',
			listeners : {
				// 'afterrender' : function(window) {
				// window.maximize();
				// }
				'afterlayout' : function(window) {
					
						var wh = window.getInnerHeight();
						var fh = window.formPanel.getInnerHeight();
						window.filePanel.setHeight(wh - fh+50);
			
					
					
					var returnStatus = window.returnStatus;
					window.returnStatusName = '';
					switch (returnStatus) {
						case 0 :
							window.returnStatusName = '申请';
							break;
						case 1 :
							window.returnStatusName = '通过';
							window.okButton.setVisible(false);
							window.cancelButton.setVisible(false);
							break;
						case -1 :
							window.returnStatusName = '驳回';
							window.okButton.setVisible(false);
							window.cancelButton.setVisible(false);
							break;
						case 2 :
							window.returnStatusName = '归还';
							window.okButton.setVisible(false);
							window.cancelButton.setVisible(false);
							break;

					}
					window.formPanel
							.getForm()
							.findField('CheckBorrowRecordForm.borrowRecord.returnStatusName')
							.setValue(window.returnStatusName);

				}
			},

			buttons : [this.okButton,this.cancelButton ,{
						text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : this.clo
					}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.okButton=new Ext.Button({
						text : '通过',
						iconCls : 'btn-ok',
						scope : this,
						handler : function(){ this.doCheck(1);}
					});
		this.cancelButton=new Ext.Button({
						text : '驳回',
						iconCls : 'btn-cancel',
						scope : this,
						handler : function(){ this.doCheck(-1);}
					});

		
		
		
		
		this.formPanel = new Ext.FormPanel({
			id : 'CheckBorrowRecordForm',
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : true,
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
							xtype : 'displayfield',
							name : 'borrowRecord.borrowNum'

						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅人',
							xtype : 'displayfield',
							name : 'borrowRecord.checkUserName'
							
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅日期',
							xtype : 'displayfield',
							name : 'borrowRecord.borrowDate'
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '应还日期',
							name : 'borrowRecord.returnDate',
							xtype : 'displayfield'

						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅方式',
							name : 'borrowRecord.borrowType',
							xtype : 'displayfield'
							
						}
					}, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅目的',
							name : 'borrowRecord.borrowReason',
							xtype : 'displayfield'
						}
					}

					, {
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '借阅状态',
							id : 'CheckBorrowRecordForm.borrowRecord.returnStatusName',
							xtype : 'displayfield'
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '备注',
							xtype : 'displayfield',
							name : 'borrowRecord.borrowRemark'

						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						items : {
							fieldLabel : '登记人ID',
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
							xtype : 'datefield',
							name : 'borrowRecord.checkDate'
						}
					}, {
						columnWidth : 1,
						layout : 'form',
						labelWidth : 100,
						hidden:true,
						items : {
							fieldLabel : '借阅状态',
							id : 'CheckBorrowRecordForm.borrowRecord.returnStatus',
							name : 'borrowRecord.returnStatus',
							xtype : 'numberfield',
							value : 0
						}
					}

			]
		});

		
		
		this.filePanel=new MyBorrowFilePanel({
			id : 'check'+this.borrowNum,
			title:'借阅清单',
			recordId : this.recordId,
			borrowNum : this.borrowNum,
			showFlag:'check'
		});
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
			

		}
	},



	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	clo : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	doCheck : function(returnStatus) {
		if (this.formPanel.getForm().isValid()) {

			this.formPanel.getForm().findField('CheckBorrowRecordForm.borrowRecord.returnStatus').setValue(returnStatus);

			this.formPanel.getForm().submit({
						clientValidation : true,
						url : __ctxPath + '/arch/checkBorrowRecord.do',
						
						success : function(form, action) {

							Ext.getCmp('CheckBorrowRecordFormWin').close();
							Ext.getCmp('CheckBorrowRecordGrid').getStore().reload();

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

		}

	}// end of save

});