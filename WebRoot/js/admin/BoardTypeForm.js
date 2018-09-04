/**
 * @description 新增和编辑窗口
 * @author YHZ
 * @data 2010-9-25
 */
BoardTypeForm = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
	this.initUIComponent();
	BoardTypeForm.superclass.constructor.call(this, {
		layout : 'fit',
		id : 'BoardTypeWindow',
		title : '编辑/新增会议类型',
		iconCls : 'menu-confernece_boardType',
		width : 350,
		minWidth : 350,
		height : 200,
		minHeight : 200,
		border : false,
		modal : true,
		resizable : true,
		maximizable : true,
		items : [ this.formPanel ],
		buttonAlign : 'center',
		keys : {
			key : Ext.EventObject.ENTER,
			fn : this.save.createCallback(this.formPanel, this),
			scope : this
		}
	});
},// end of this constructor

	// 页面组件
	initUIComponent : function() {
		this.formPanel = new Ext.FormPanel( {
			id : 'BoardTypeFormPanel',
			layout : 'form',
			border : true,
			bodyStyle : 'padding-top:5px;padding-left:5px;',
			defaultType : 'textfield',
			defaults : {
				width : 400,
				anchor : '98%,98%'
			},
			url : __ctxPath + '/admin/saveBoardType.do',
			items : [ {
				xtype : 'hidden',
				name : 'boardType.typeId',
				value : this.typeId
			}, {
				name : 'boardType.typeName',
				fieldLabel : '会议类型名称',
				allowBlank : false,
				blankText : '会议类型名称不能为空，请输入！',
				maxLength : 128,
				maxLengthText : '会议类型名称不能超过128个字符长度！'
			}, {
				name : 'boardType.typeDesc',
				xtype : 'textarea',
				fieldLabel : '会议类型描述',
				allowBlank : false,
				blankText : '会议类型描述不能为空，请输入！',
				maxLength : 4000,
				maxLengthText : '会议类型描述不能超过4000个字符串长度！'
			} ]
		});// end of this panel
		if (this.typeId !=null && this.typeId != '' && this.typeId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + '/admin/getBoardType.do?typeId='+ this.typeId,
				root : 'data',
				preName : 'boardType',
				waitMsg : '数据正在加载中，请稍后...',
				success : function(response, options) {
				},
				failure : function() {
					Ext.ux.Toast.msg('操作提示','对不起，数据加载失败！');
				}
			});
		}// end of this 数据加载结束
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
	 * 取消
	 */
	cancel : function(window) {
		window.close();
	},
	/**
	 * 保存
	 */
	save : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit( {
				url : __ctxPath + '/admin/saveBoardType.do',
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				success : function(form, action) {
					Ext.ux.Toast.msg('操作提示', '恭喜您，您提交的数据操作成功！');
					Ext.getCmp('BoardTypeGridPanel').getStore().reload( {
						params : {
							start : 0,
							limit : 25
						}
					});
					window.close();
				},
				failure : function() {
					Ext.ux.Toast.msg('操作提示', '对不起，您提交的数据失败，请重新提交！');
				}
			});
		}
	}
});