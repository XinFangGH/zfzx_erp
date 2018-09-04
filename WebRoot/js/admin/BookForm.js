var BookForm = function(bookId) {
	this.bookId = bookId;
	var fp = this.setup();
	var window = new Ext.Window({
		id : 'BookFormWin',
		title : '图书详细信息',
		iconCls:'menu-book-manage',
		width : 500,
		height:320,
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [this.setup()],
		buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('BookForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
										method : 'post',
										waitMsg : '正在提交数据...',
										success : function(fp, action) {
											Ext.ux.Toast.msg("操作信息", "成功保存信息！");
											Ext.getCmp('BookGrid').getStore()
													.reload();
											window.close();
										},
										failure : function(fp, action) {
											Ext.MessageBox.show({
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
				}]
	});
	window.show();
};

BookForm.prototype.setup = function() {
	// 获取图书类别下位选框
	var _url = __ctxPath + '/admin/treeBookType.do?method=1';
	var bookTypeSelector = new TreeSelector('bookTypeSelect', _url, '图书类别',
			'typeId');
	var formPanel = new Ext.FormPanel({
		url : __ctxPath + '/admin/saveBook.do',
		layout : 'form',
		id : 'BookForm',
		border:false,
		defaults : {
			anchor : '96%,96%'
		},
		bodyStyle : 'padding:5px;',
		defaultType : 'textfield',
		items : [{
					name : 'book.bookId',
					id : 'bookId',
					xtype : 'hidden',
					value : this.bookId == null ? '' : this.bookId
				}, {
					name : 'book.typeId',
					id : 'typeId',
					xtype : 'hidden'
				}, {
					name : 'book.leftAmount',
					id : 'leftAmount',
					xtype : 'hidden'
				}, {
					xtype : 'label'
				}, bookTypeSelector, {
					fieldLabel : '书名',
					name : 'book.bookName',
					id : 'bookName',
					allowBlank : false,// 不允许为空
					blankText : '书名不能为空'
				}, {
					fieldLabel : '作者',
					name : 'book.author',
					id : 'author',
					allowBlank : false,// 不允许为空
					blankText : '作者不能为空'
				}, {
					fieldLabel : 'ISBN号',
					name : 'book.isbn',
					id : 'isbn',
					allowBlank : false,// 不允许为空
					blankText : 'ISBN号不能为空'
				}, {
					fieldLabel : '出版社',
					name : 'book.publisher',
					id : 'publisher'
				}, {
					fieldLabel : '图书价格',
					name : 'book.price',
					id : 'price',
					xtype : 'numberfield',// 价格只能输入数字，可以有小数点
					nanText : '只能输入数字',
					allowBlank : false,// 不允许为空
					blankText : '价格不能为空'
				}, {
					fieldLabel : '存放地点',
					name : 'book.location',
					id : 'location',
					allowBlank : false,// 不允许为空
					blankText : '存放地点不能为空'
				}, {
					xtype : 'container',
					layout : 'hbox',
					layoutConfigs:{
						align:'middle'
					},
					defaults:{margins:'0 2 0 0'},
					id : 'amoutContainer',
					defaultType : 'textfield',
					height : 26,
					items : [{
						xtype : 'label',
						text : '数量:',
						width : 105
					}, {
						name : 'book.amount',
						id : 'amount',
						xtype : 'numberfield',// 数量只能输入数字
						allowDecimals : false,// 只允许输入整数
						nanText : '只能输入数字',
						allowBlank : false,// 不允许为空
						blankText : '数量不能为空',
						minValue : 1,
						minText : '图书数量必须大于0'
					}, {
						xtype : 'button',
						id : 'bookAmoutButton',
						text : '增加数量',
						iconCls : 'btn-select',
						width : 80,
						handler : function() {
							var bookId = Ext.getCmp('bookId').getValue();
							new BookAmountForm(bookId);
						}
					}]
				}, {
					xtype : 'container',
					layout : 'hbox',
					layoutConfigs:{
						align:'middle'
					},
					defaults:{margins:'0 2 0 0'},
					defaultType : 'textfield',
					height : 26,
					items : [{
						xtype : 'label',
						text : '所属部门:',
						width : 105
					}, {
						name : 'book.department',
						id : 'department',
						allowBlank : false,// 不允许为空
						blankText : '所属部门不能为空'
					}, {
						xtype : 'button',
						text : '选择',
						iconCls : 'btn-select',
						width : 80,
						// 部门选择器
						handler : function() {
							DepSelector.getView(function(ids, names) {
										var department = Ext
												.getCmp('department');
										department.setValue(names);
									}, true).show();// true表示单选，因为一本书只能属于一个部门
						}
					}]

				}, {
					xtype : 'container',
					id : 'bookSnContainer',
					layout : 'form',
					items : [{
								id : 'bookSnPanel',
								fieldLabel : '图书标签',
								xtype : 'panel',
								frame : false,
								height : 80,
								autoScroll : true,
								html : ''
							}]
				}]
	});

	if (this.bookId != null && this.bookId != 'undefined') {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getBook.do?bookId=' + this.bookId,
			// method : 'post',
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var typeId = Ext.getCmp('typeId');
				// 从服务端action取得图书类别的值
				var typeName = action.result.data.bookType.typeName;
				var bookTypeSelect = Ext.getCmp('bookTypeSelect');
				bookTypeSelect.setValue(typeName);
				var bookId = action.result.data.bookId;
				var leftAmount = action.result.data.leftAmount;
				var leftAmountField = Ext.getCmp('leftAmount');
				leftAmountField.setValue(leftAmount);
				Ext.Ajax.request({
					url : __ctxPath + '/admin/getSnBookSn.do?bookId=' + bookId,
					method : 'post',
					success : function(response) {
						var result = Ext.util.JSON
								.decode(response.responseText);
						var booksnPanel = Ext.getCmp('bookSnPanel');
						for (var i = 0; i < result.length; i++) {
							Ext.DomHelper
									.append(
											booksnPanel.body,
											'<div>'
													+ result[i][1]
													+ '&nbsp;<img class="img-delete" src="'
													+ __ctxPath
													+ '/images/system/delete.gif" alt="删除该本图书" onclick="removeBookSn(this,'
													+ result[i][0] + ','
													+ bookId + ')"/></div>');
						}
					}
				});
			},
			failure : function(form, action) {
			}
		});
	}
	return formPanel;
};
function removeBookSn(obj, bookSnId, bookId) {
	Ext.Msg.confirm('信息确认', '把借阅归还记录一起删除，您确认要删除该书吗？', function(btn) {
		if (btn == 'yes') {
			var el = Ext.get(obj.parentNode);
			el.remove();
			Ext.Ajax.request({
				url : __ctxPath + '/admin/multiDelBookSn.do',
				params : {
					ids : bookSnId
				},
				method : 'post',
				success : function() {
					Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
					// 删除图书标签成功后，将书的数量和未借出数量也同时修改
					Ext.Ajax.request({
						url : __ctxPath
								+ '/admin/updateAmountAndLeftAmountBook.do?bookId='
								+ bookId,
						method : 'post',
						success : function(response) {
							var result = Ext.util.JSON
									.decode(response.responseText);
							// 根据bookId修改书相应的数量和剩余数量
							Ext.getCmp('amount').setValue(result.data.amount);
							Ext.getCmp('leftAmount')
									.setValue(result.data.leftAmount);
						}
					});
				}
			});
		}
	});
};
