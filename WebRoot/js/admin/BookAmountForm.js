var BookAmountForm = function(bookId) {
	this.bookId = bookId;
	var fp = this.setup();
	var window = new Ext.Window( {
		id : 'BookAmountFormWin',
		title : '增加图书数量',
		autoHeight : true,
		width: 300,
		modal : true,
		layout : 'anchor',
		plain : true,
		bodyStyle : 'padding:5px;',
		buttonAlign : 'center',
		items : [ this.setup() ],
		buttons : [ {
			text : '保存',
			iconCls:'btn-save',
			handler : function() {
				var fp = Ext.getCmp('bookAmountForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit( {
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg("操作信息","成功保存信息！");
							window.close();
							Ext.getCmp('amount').setValue(action.result.data.amount);
							Ext.getCmp('leftAmount').setValue(action.result.data.leftAmount);
							Ext.Ajax.request({
								url:__ctxPath+'/admin/getSnBookSn.do?bookId=' + bookId,
								method : 'post',
								success:function(response){
									var result = Ext.util.JSON.decode(response.responseText);
									var booksnPanel = Ext.getCmp('bookSnPanel');
									booksnPanel.body.update('');
									for(var i=0;i<result.length;i++){
										Ext.DomHelper.append(booksnPanel.body,'<div>'+result[i][1]+'&nbsp;<img class="img-delete" src="'+__ctxPath+'/images/system/delete.gif" alt="删除该本图书" onclick="#"/></div>');
									}

								}
							});
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
			iconCls:'btn-cancel',
			handler : function() {
				window.close();
			}
		} ]
	});
	window.show();
};

BookAmountForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel( {
		url : __ctxPath + '/admin/updateAmountBook.do?bookId='+this.bookId,
		layout : 'form',
		id : 'bookAmountForm',
		frame : true,
		defaults : {
			anchor : '95%,95%'
		},
		formId : 'BookAmountFormId',
		defaultType : 'textfield',
		items : [{
			fieldLabel : '增加的图书数量',
			name : 'addAmount',
			id : 'addAmount',
			allowBlank : false,
			blankText : '增加的图书数量不能为空'
		}

		]
	});
	return formPanel;
};
