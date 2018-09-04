/**
 * @description 编辑和新增窗口
 * @author YHZ
 * @data 2010-9-21
 */
BoardRooForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
	this.initUIComponent();
	// 调用构造函数
	BoardRooForm.superclass.constructor.call(this, {
		layout : 'fit',
		id : 'BoardRooWin',
		title : '编辑/新增会议室信息',
		iconCls : 'menu-conference_boardRoom',
		width : 400,
		minWidth : 400,
		height : 200,
		minHeight : 200,
		border : false,
		modal : true,
		maximizable : true,
		buttonAlign : 'center',
		items : this.formPanel,
		buttons : [ this.buttons ],
		keys : {
			key : Ext.EventObject.ENTER,
			fn : this.save.createCallback(this.formPanel, this),
			scope : this
		}
	});
	// end of this constructor
},
initUIComponent : function() {
	this.formPanel = new Ext.FormPanel( {
		url : __ctxPath + '/admin/saveBoardRoo.do',
		layout : 'form',
		id : 'BoardRooForm',
		frame : false,
		border : true,
		defaults : {
			width : 400,
			anchor : '98%,98%'
		},
		bodyStyle : 'padding-top:5px;padding-left:5px;',
		formId : 'BoardRooFormId',
		defaultType : 'textfield',
		items : [ {
			xtype : 'hidden',
			name : 'boardRoo.roomId',
			value : this.roomId
		}, {
			name : 'boardRoo.roomName',
			xtype : 'textfield',
			fieldLabel : '会议室名称',
			allowBlank : false,
			blankText : '会议室名称不能为空！',
			maxLength : 128,
			maxLengthText : '会议室名称不能超过128个字符长度！'
		}, {
			name : 'boardRoo.roomDesc',
			xtype : 'textarea',
			fieldLabel : '会议室描述',
			allowBlank : false,
			blankText : '会议室描述不能为空！',
			maxLength : '4000',
			maxLengthText : '会议室描述不能超过4000个字符长度！'
		}, {
			xtype : 'numberfield',
			name : 'boardRoo.containNum',
			fieldLabel : '会议室总容量',
			allowBlank : false,
			blankText : '请输入总容量！',
			maxlength : 5,
			maxLengthText : '会议室总容量不能超过十万人！'
		} ]
	});
	// end of this formPanel

	if (this.roomId != null && this.roomId != '') {
		this.formPanel.loadData( {
			url : __ctxPath + '/admin/getBoardRoo.do?roomId=' + this.roomId,
			root : 'data',
			preName : 'boardRoo',
			success : function(response, options) {
			},
			failure : function() {
				Ext.ux.Toast.msg('操作提示', '对不起，数据加载失败！');
			}
		});
	}
	this.buttons = [ {
		text : '保存',
		iconCls : 'btn-save',
		handler : this.save.createCallback(this.formPanel, this)
	}, {
		text : '取消',
		iconCls : 'btn-cancel',
		handler : this.cancel.createCallback(this)
	} ];
},// end of this initUIComponent

/**
 * 保存
 */
save : function(formPanel, window) {
	if (formPanel.getForm().isValid()) {
		formPanel.getForm().submit( {
			url : __ctxPath + '/admin/saveBoardRoo.do',
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '成功保存信息!');
				Ext.getCmp('BoardRooGrid').getStore().reload( {
					params : {
						start : 0,
						limit : 25
					}
				});
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
},
/**
 * 取消
 */
cancel : function(window) {
	window.close();
}
});