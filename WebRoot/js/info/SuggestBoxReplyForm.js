/**
 * @author
 * @createtime
 * @class SuggestBoxReplyForm
 * @extends Ext.Window
 * @description SuggestBox表单
 * @company 智维软件
 */
SuggestBoxReplyForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 显示意见信息
	displayPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SuggestBoxReplyForm.superclass.constructor.call(this, {
					id : 'SuggestBoxReplyFormWin',
					layout : {
						type : 'vbox',
						align : 'stretch'
					},
					items : [this.displayPanel,this.formPanel],
					modal : true,
					height : 550,
					width : 735,
					maximizable : true,
					title : '意见回复',
					iconCls : 'menu-suggestbox',
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.displayPanel = new Ext.Panel({
					flex : 1,
					id : 'CheckEmpProfileFormPanel',
//					height : 430,
					autoScroll : true,
					border : false,
					autoLoad : {
						url : __ctxPath
								+ '/pages/info/displaySuggest.jsp?boxId='
								+ this.boxId
					}
				});
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			flex : 1,
			bodyStyle : 'padding:10px 10px 10px 10px',
			border : false,
			url : __ctxPath + '/info/replySuggestBox.do',
			id : 'SuggestBoxReplyForm',
			defaults : {
				anchor : '95%,95%'
			},
			autoScroll : true,
			defaultType : 'textfield',
			items : [{
						name : 'suggestBox.boxId',
						id : 'boxId',
						xtype : 'hidden',
						value : this.boxId == null ? '' : this.boxId
					}, {
						xtype : 'radiogroup',
						fieldLabel : '是否公开',
						autoHeight : true,
						columns : 2,
						width : 520,
						items : [{
							boxLabel : '公开',
							name : 'suggestBox.isOpen',
							inputValue : 0,
							id : 'isOpen1',
							checked : true
						}, {
							boxLabel : '不公开',
							name : 'suggestBox.isOpen',
							inputValue : 1,
							id : 'isOpen0'
						}]
					},{
						fieldLabel : '回复内容',
						name : 'suggestBox.replyContent',
						id : 'replyContent',
						width : 200,
						xtype : 'htmleditor'
					}
			]
		});
		// 初始化功能按钮
		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					scope : this,
					handler : this.reply.createCallback(this.formPanel, this)
				}, {
					text : '重置',
					iconCls : 'btn-reset',
					scope : this,
					handler : this.reset.createCallback(this.formPanel)
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					scope : this,
					handler : this.cancel.createCallback(this)
				}];
	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function(formPanel) {
		formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(window) {
		window.close();
	},
	/**
	 * 保存记录
	 */
	reply : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
						method : 'POST',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							var gridPanel = Ext.getCmp('SuggestBoxGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							window.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							window.close();
						}
					});
		}
	}// end of save
});