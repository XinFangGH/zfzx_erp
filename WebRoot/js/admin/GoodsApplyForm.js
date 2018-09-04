/**
 * @author
 * @createtime
 * @class GoodsApplyForm
 * @extends Ext.Window
 * @description GoodsApplyForm表单
 * @company 智维软件
 */
GoodsApplyForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		GoodsApplyForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'GoodsApplyFormWin',
			title : '申请表详细信息',
			iconCls : 'menu-goods-apply',
			width : 450,
			height : 300,
			minWidth : 449,
			minHeight : 299,
			items : this.formPanel,
			maximizable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.submitRecord,
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		//初始化form表单
		this.formPanel =  new Ext.FormPanel({
				url : __ctxPath + '/admin/saveGoodsApply.do',
				layout : 'form',
				id : 'GoodsApplyForm',
				frame : false,
				bodyStyle : 'padding : 5px;',
				defaults : {
					widht : 450,
					anchor : '96%,96%'
				},
				formId : 'GoodsApplyFormId',
				defaultType : 'textfield',
				items : [{
							name : 'goodsApply.applyId',
							id : 'applyId',
							xtype : 'hidden',
							value : this.applyId == null ? '' : this.applyId
						}, {
							name : 'goodsApply.goodsId',
							id : 'goodsId',
							xtype : 'hidden'
						}, {
							name : 'goodsApply.userId',
							id : 'userId',
							xtype : 'hidden'
						},{
							fieldLabel : '申请号',
							name : 'goodsApply.applyNo',
							id : 'applyNo',
							readOnly:true
						}, {
							width : '100%',
							xtype : 'container',
							layout : 'column',
							fieldLabel : '商品名称',
							style : 'padding-left:0px;margin-bottom:4px;',
							items : [{
								columnWidth : .999,
								xtype : 'textfield',
								name : 'inStock.officeGoods.goodsName',
								id : 'goodsName',
								allowBlank : false,
								readOnly : true
							}, {
								xtype : 'button',
								text : '选择商品',
								iconCls:'btn-select',
								handler : function() {
									GoodsSelector.getView(
										function(id, name) {
											var goodsIdField = Ext.getCmp('goodsId');
											goodsIdField.setValue(id);
											var goodsNameField = Ext.getCmp('goodsName');
											goodsNameField.setValue(name);
										}, true).show();
								}
							}, {
								xtype : 'button',
								text : ' 清除记录',
								iconCls:'reset',
								handler : function() {
									var goodsIdField = Ext.getCmp('goodsId');
									goodsIdField.setValue('');
									var goodsNameField = Ext.getCmp('goodsName');
									goodsNameField.setValue('');
								}
							}]
						}, {
							fieldLabel : '申请日期',
							name : 'goodsApply.applyDate',
							id : 'applyDate',
							xtype : 'datefield',
							format : 'Y-m-d',
							allowBlank:false,
							editable:false
						}, {
							fieldLabel : '申请数量',
							name : 'goodsApply.useCounts',
							allowBlank:false,
							xtype:'numberfield',
							id : 'useCounts'
						},{
							xtype : 'container',
							layout : 'column',
							fieldLabel : '申请人',
							style : 'padding-left:0px;margin-bottom:4px;',
							border : true,
							items : [{
								columnWidth : .999,
								xtype : 'textfield',
								name : 'goodsApply.proposer',
								id : 'proposer',
								allowBlank : false,
								readOnly : true
							}, {
								xtype : 'button',
								text : '选择人员',
								iconCls:'btn-user-sel',
								handler : function() {
									UserSelector.getView(
										function(id, name) {
											var proposerField = Ext.getCmp('proposer');
											proposerField.setValue(name);
											Ext.getCmp('userId').setValue(id);
										}, true).show();
								}
							}, {
								xtype : 'button',
								text : ' 清除记录',
								iconCls:'reset',
								handler : function() {
									var proposerField = Ext.getCmp('proposer');
									proposerField.setValue('');
								}
							}]
						}, {
						fieldLabel : '审批状态 ',
						name : 'goodsApply.approvalStatus',
						id : 'approvalStatus',
						xtype:'hidden'
					}
					, {
						fieldLabel : '备注',
						name : 'goodsApply.notes',
						id : 'notes',
						xtype:'textarea'
					}
				]
			});

	if (this.applyId != null && this.applyId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getGoodsApply.do?applyId=' + this.applyId,
			method:'post',
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var applyDate = action.result.data.applyDate;
				var applyDateField = Ext.getCmp('applyDate');
				applyDateField.setValue(new Date(getDateFromFormat(applyDate, "yyyy-MM-dd")));
				var goodsId=action.result.data.officeGoods.goodsId;
				var goodsName=action.result.data.officeGoods.goodsName;
				var goodsIdField=Ext.getCmp('goodsId');
	            var goodsNameField=Ext.getCmp('goodsName');
	            goodsIdField.setValue(goodsId);
	            goodsNameField.setValue(goodsName);
				
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
		this.buttons = [{
					text : '保存草稿',
					iconCls:'btn-save',
					handler : this.saveRecord
				},{
					text : '提交审核',
					iconCls:'btn-ok',
					handler : this.submitRecord
				}, {
					text : '取消',
					iconCls:'btn-cancel',
					scope:this,
					handler : function() {
						this.close();
					}
				}];//end of the buttons
	},//end of the initUIComponents
	saveRecord : function(){
		Ext.getCmp('approvalStatus').setValue(0);
	    var fp = Ext.getCmp('GoodsApplyForm');
		if (fp.getForm().isValid()) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					Ext.getCmp('GoodsApplyGrid').getStore()
							.reload();
					Ext.getCmp('GoodsApplyFormWin').close();
				},
				failure : function(fp, action) {
					var message=action.result.message;
					Ext.MessageBox.show({
						title : '操作信息',
						msg : message==null&&message==''?'信息保存出错，请联系管理员！':message,
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					Ext.getCmp('GoodsApplyFormWin').close();
				}
			});
		}
		
	},
	submitRecord : function(){
		Ext.getCmp('approvalStatus').setValue(1);
	    var fp = Ext.getCmp('GoodsApplyForm');
		if (fp.getForm().isValid()) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					Ext.getCmp('GoodsApplyGrid').getStore().reload();
					Ext.getCmp('GoodsApplyFormWin').close();
				},
				failure : function(fp, action) {
					var message=action.result.message;
					Ext.MessageBox.show({
						title : '操作信息',
						msg : message==null&&message==''?'信息保存出错，请联系管理员！':message,
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					Ext.getCmp('GoodsApplyFormWin').close();
				}
			});
		}
	}
});
