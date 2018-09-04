var BookTypeForm = function(typeId) {
	this.typeId = typeId;
	var fp = this.setup();
	var window = new Ext.Window( {
		id : 'BookTypeFormWin',
		title : '图书类别详细信息',
		iconCls:'menu-book-type',
		height:100,
		width: 300,
		modal : true,
		layout : 'fit',
		border:false,
		buttonAlign : 'center',
		items : [ this.setup() ],
		buttons : [ {
			text : '保存',
			iconCls:'btn-save',
			handler : function() {
				var fp = Ext.getCmp('bookTypeForm');
				if (fp.getForm().isValid()) {
					fp.getForm().submit( {
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg("操作信息","成功保存信息！");
							if(Ext.getCmp('BookTypeGrid')!=null){
								Ext.getCmp('BookTypeGrid').getStore().reload();
							}
							if(Ext.getCmp('leftBookManagePanel')!=null){
								Ext.getCmp('leftBookManagePanel').root.reload();
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
			iconCls:'btn-cancel',
			handler : function() {
				window.close();
			}
		} ]
	});
	window.show();
};

BookTypeForm.prototype.setup = function() {

	var formPanel = new Ext.FormPanel( {
		url : __ctxPath + '/admin/saveBookType.do',
		layout : 'form',
		id : 'bookTypeForm',
		bodyStyle : 'padding:5px;',
		frame : false,
		defaults : {
			anchor : '95%,95%'
		},
		formId : 'BookTypeFormId',
		defaultType : 'textfield',
		items : [ {
			name : 'bookType.typeId',
			id : 'typeId',
			xtype : 'hidden',
			value : this.typeId == null ? '' : this.typeId
		}, {
			fieldLabel : '图书类别名称',
			name : 'bookType.typeName',
			id : 'typeName',
			allowBlank : false,
			blankText : '图书类别不能为空'
		}
		]
	});

	if (this.typeId != null && this.typeId != 'undefined') {
		formPanel.getForm().load( {
			deferredRender : false,
			url : __ctxPath + '/admin/getBookType.do?typeId=' + this.typeId,
			method : 'post',
			waitMsg : '正在载入数据...',
			success : function(form, action) {
		},
		failure : function(form, action) {
		}
		});
	}
	return formPanel;
};
