/**
 * @author
 * @createtime
 * @class CsHolidayForm
 * @extends Ext.Window
 * @description CsHoliday表单
 * @company 智维软件
 */
CsHolidayForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CsHolidayForm.superclass.constructor.call(this, {
					id : 'CsHolidayFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 150,
					width : 300,
					maximizable : true,
					title : '详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save.createCallback(this.formPanel, this)
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			url : __ctxPath + '/system/saveCsHoliday.do',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			// id : 'CsHolidayForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
						name : 'csHoliday.id',
						id:'id',
						xtype : 'hidden',
						value : this.id == null ? '' : this.id
					}, {
						xtype : "dickeycombo",
						nodeKey : 'Holday',
						fieldLabel : '节假日类别',
						//emptyText : '请选择类别',
						allowBlank : false,
						editable:false,
						blankText : "节假日类别不能为空，请正确填写!",
						hiddenName : 'csHoliday.dicId',
						id:'dicId',
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											if (combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue(st.getAt(0).data.itemId);
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
							}
						}
					}, {
						fieldLabel : '节假日',
						name : 'csHoliday.dateStr',
						id:'dateStr',
						editable:false,
						xtype : 'datefield',
						format : 'Y-m-d',
						allowBlank : false,//是否必填
						blankText : "节假日不能为空，请正确填写!",
						value : new Date()
					}]
		});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/system/getCsHoliday.do?id='
								+ this.id,
						root : 'data',
						preName : 'csHoliday'
					});
		}

	},// end of the initcomponents

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
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function(formPanel, window) {
		if (formPanel.getForm().isValid()) {
			formPanel.getForm().submit({
				method : 'POST',
				params:{
					"id":Ext.getCmp('id').getValue(),
		            "dateStr":Ext.getCmp('dateStr').getValue(),
		            "dicId":Ext.getCmp('dicId').getValue()
				},
				success : function(form ,action) {
					var res = Ext.util.JSON.decode(action.response.responseText);
					if (res.e) {
						var gridPanel = Ext.getCmp('CsHolidayGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						window.close();
						Ext.ux.Toast.msg('操作信息','信息保存成功！');
					} else {
						Ext.ux.Toast.msg('信息提示', res.result);
					}
				},
				failure : function(form ,action) {
					Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
				}
			})// end of save
		}
	}
});