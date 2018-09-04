var BookBorrowForm = function(recordId,bookId) {
	this.recordId = recordId;
	this.bookId = bookId;
	var fp = this.setup();
	var window = new Ext.Window( {
		id : 'BookBorrowFormWin',
		title : '图书借出详细记录',
		iconCls:'menu-book-borrow',
		width : 520,
		height : 220,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [ this.setup() ],
		buttons : [ {
			text : '借出图书',
			iconCls : 'btn-save',
			handler : function() {
				var fp = Ext.getCmp('BookBorrowForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit( {
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg("操作信息","成功保存信息！");
							var BookBorrowGrid=Ext.getCmp('BookBorrowGrid');
							if(BookBorrowGrid==null){
								Ext.getCmp('BookGrid').getStore().reload();
							}else{
								Ext.getCmp('BookBorrowGrid').getStore().reload();
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

BookBorrowForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel( {
		url : __ctxPath + '/admin/saveBorrowBookBorRet.do',
		layout : 'form',
		bodyStyle : 'padding:5px;',
		id : 'BookBorrowForm',
		border:false,
		defaultType : 'textfield',
		items : [ {
			name : 'bookBorRet.recordId',
			id : 'recordId',
			xtype : 'hidden',
			value : this.recordId == null ? '' : this.recordId
		},{
			name : 'bookId',
			id : 'bookId',
			xtype : 'hidden',
			value : this.bookId == null ? '' : this.bookId
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
						//readOnly : true,
						width : 200,
						maxHeight : 200,
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						emptyText : '请选择图书系列',
						store : new Ext.data.SimpleStore( {
							//借阅时只显示在库的图书标签（只有在库的图书才能选择被借阅）
							url : __ctxPath + '/admin/getBorrowSnBookSn.do',
							fields : [ 'bookSnId', 'bookSn' ]
						}),
						displayField : 'bookSn',
						valueField : 'bookSnId',
						listeners : {
							select : function(combo, record, index) {
								Ext.getCmp('bookSnId').setValue(combo.value);
							},
							focus : function(combo, record, index) {
								var bookName = Ext.getCmp('bookName').getValue();
								if(bookName==''){
									Ext.ux.Toast.msg('提示信息','请先选择图书');
								}
							}
						}
					} ]
				},{
					xtype : 'container',
					height:32,
					layout : 'hbox',
					layoutConfigs:{
						align:'middle'
					},
					defaults:{
						margins:'0 2 0 2'
					},
				   items:[{
				      xtype:'label',
				      text:'借阅人:',
				      width:100
				   },{
				    name:'bookBorRet.fullname',
				    id:'fullname',
				    xtype:'textfield',
				    readOnly:true,
				    allowBlank:false,
				    width:200
				 },{
				    xtype:'button',
				    text:'选择人员',
				    iconCls:'btn-user-sel',
				    handler:function(){
				       UserSelector.getView(
				          function(id,name){
				             Ext.getCmp('fullname').setValue(name);
				          },true
				       ).show();
				    }
				 },{
				    xtype:'button',
				    text:'清除记录',
				    iconCls:'reset',
				    handler:function(){
				      Ext.getCmp('fullname').setValue('');
				    }
				 }
				 ]},{
					fieldLabel : '应还时间',
					name : 'bookBorRet.returnTime',
					id : 'returnTime',
					xtype : 'datefield',
					format : 'Y-m-d',
					editable : false,
					allowBlank : false,
					width:200,
					blankText : '应还时间不能为空'
				 }]
	});

	if (this.recordId != null && this.recordId != 'undefined') {
		formPanel.getForm().load(
				{
					deferredRender : false,
					url : __ctxPath + '/admin/getBookBorRet.do?recordId='+ this.recordId,
					//method : 'post',
					waitMsg : '正在载入数据...',
					success : function(form, action) {
						// 对应还日期格式化后再进行输出
						var returnTime = action.result.data.returnTime;
						var returnTimeField = Ext.getCmp('returnTime');
						returnTimeField.setValue(new Date(getDateFromFormat(returnTime, "yyyy-MM-dd HH:mm:ss")));
					},
					failure : function(form, action) {
				}
				});
	}
	return formPanel;

};
