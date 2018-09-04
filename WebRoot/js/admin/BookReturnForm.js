var BookReturnForm = function(recordId) {
	this.recordId = recordId;
	var fp = this.setup();
	var window = new Ext.Window( {
		id : 'BookReturnFormWin',
		title : '图书归还详细记录',
		iconCls:'menu-book-return',
		width : 520,
		height : 240,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [ this.setup() ],
		buttons : [ {
			text : '归还图书',
			iconCls : 'btn-save',
			handler : function() {
				var fp = Ext.getCmp('BookReturnForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit( {
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg("操作信息","成功保存信息！");
							var BookBorrowGrid=Ext.getCmp('BookBorrowGrid');
							if(BookBorrowGrid!=null){
								BookBorrowGrid.getStore().reload();
							}
							var BookReturnGrid=Ext.getCmp('BookReturnGrid');
							if(BookReturnGrid!=null){
							    BookReturnGrid.getStore().reload();
							}
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show( {
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
							window.close();
						}
					});
				}
			}
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				window.close();
			}
		} ]
	});
	window.show();
};

BookReturnForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel( {
		url : __ctxPath + '/admin/saveReturnBookBorRet.do',
		layout : 'form',
		id : 'BookReturnForm',
		bodyStyle : 'padding:5px;',
		border:false,
		defaultType : 'textfield',
		items : [ {
			name : 'bookBorRet.recordId',
			id : 'recordId',
			xtype : 'hidden',
			value : this.recordId == null ? '' : this.recordId
		}, {
			name : 'bookBorRet.bookSnId',
			id : 'bookSnId',
			xtype : 'hidden'
		}, {
					xtype : 'container',
					height:32,
					layout : 'hbox',
					layoutConfigs:{
						align:'middle'
					},
					defaults:{
						margins:'0 2 0 2'
					},
					items : [
							{
								xtype : 'label',
								text : '借出图书名称:',
								width : 100
							},
							{
								xtype : 'textfield',
								name : 'bookBorRet.bookName',
								id : 'bookName',
								allowBlank : false,
								blankText : '借出图书名称不能为空',
								readOnly : true,
								width : 200
							},
							{
								xtype : 'button',
								id:'ReturnFormButtonSel',
								text : '选择图书',
								iconCls : 'menu-book-manage',
								handler : function() {
									BookSelector.getView(
											function(id, name) {
												var bookNameField = Ext.getCmp('bookName');
												bookNameField.setValue(name);
												var store = Ext.getCmp('borrowIsbn').getStore();
												store.reload( {
													params : {bookId : id}
												});
											}, true).show();
								}
							}, {
								xtype : 'button',
								text : ' 清除记录',
								iconCls:'reset',
								id:'ReturnFormButtonCle',
								handler : function() {
								var bookNameField = Ext.getCmp('bookName');
								bookNameField.setValue('');
							}
							} ]
				},
				{
					xtype : 'container',
					height:32,
					layout : 'hbox',
					layoutConfigs:{
						align:'middle'
					},
					defaults:{
						margins:'0 2 0 2'
					},
					items : [ {
						xtype : 'label',
						text : '借出图书的ISBN:',
						width : 100
					}, {
						name : 'bookBorRet.borrowIsbn',
						id : 'borrowIsbn',
						allowBlank : false,
						blankText : '借出图书的ISBN不能为空',
						width : 200,
						maxHeight : 200,
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						emptyText : '请选择图书系列',
						store : new Ext.data.SimpleStore( {
							//归还时只显示已借出的图书标签（只有借出去的图书才能选择归还）
							url : __ctxPath + '/admin/getReturnSnBookSn.do',
							fields : [ 'bookSnId', 'bookSn' ]
						}),
						displayField : 'bookSn',
						valueField : 'bookSnId',
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp('bookSnId').setValue(combo.value);
								var bookSnId = combo.value;
								//选择图书和图书sn后，自动把借出时间和应还时间填上
								Ext.Ajax.request({
									url : __ctxPath + '/admin/getBorRetTimeBookBorRet.do?bookSnId='+bookSnId,
									success : function(response) {
										var result = Ext.util.JSON.decode(response.responseText).data;
										var borrowTime = result.borrowTime;
										var borrowTimeField = Ext.getCmp('borrowTime');
										var returnTime = result.returnTime;
										var returnTimeField = Ext.getCmp('returnTime');
										var recordId = result.recordId;
										var recordIdField = Ext.getCmp('recordId');
										var fullname=result.fullname;
										var fullnameField=Ext.getCmp('fullname');
										recordIdField.setValue(recordId);
										borrowTimeField.setValue(borrowTime);
										returnTimeField.setValue(returnTime);
										fullnameField.setValue(fullname);
									}
								})
							},
							focus : function(combo, record, index) {
								var bookName = Ext.getCmp('bookName').getValue();
								if(bookName==''){
									Ext.ux.Toast.msg("提示信息","请先选择图书！");
								}
							}
						}
					} ]
				},{
				   	  fieldLabel : '借阅人',
				      xtype:'textfield',
				      id:'fullname',
				      name:'bookBorRet.fullname',
				      readOnly:true,
				      allowBlank:false,
				      width:200
				   },{
					fieldLabel : '借出时间',
					name : 'bookBorRet.borrowTime',
					id : 'borrowTime',
					readOnly : true,
					width:200,
					allowBlank : false,
					blankText : '借出时间不能为空'
				}, {
					fieldLabel : '应还时间',
					name : 'bookBorRet.returnTime',
					id : 'returnTime',
					readOnly : true,
					width:200,
					allowBlank : false,
					blankText : '应还时间不能为空'
				}]
	});

	if (this.recordId != null && this.recordId != 'undefined') {
		formPanel.getForm().load(
				{
					deferredRender : false,
					url : __ctxPath + '/admin/getBookBorRet.do?recordId=' + this.recordId,
					method : 'post',
					waitMsg : '正在载入数据...',
					success : function(form, action) {
						// 对借出日期、应还日期格式化后再进行输出
						var borrowTime = action.result.data.borrowTime;
						var borrowTimeField = Ext.getCmp('borrowTime');
						var returnTime = action.result.data.returnTime;
						var returnTimeField = Ext.getCmp('returnTime');
						borrowTimeField.setValue(borrowTime);
						returnTimeField.setValue(returnTime);
					},
					failure : function(form, action) {
				}
				});
	}
	return formPanel;
};
