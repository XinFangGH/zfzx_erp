/**
 * @author
 * @createtime
 * @class ProductForm
 * @extends Ext.Window
 * @description ProductForm表单
 * @company 智维软件
 */
ProductForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ProductForm.superclass.constructor.call(this, {
					layout : 'fit',
					id : 'ProductFormWin',
					title : '产品详细信息',
					iconCls : 'menu-product',
					width : 500,
					height : 370,
					minWidth:499,
					minHeight:369,
					items:this.formPanel,
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					buttonAlign : 'center',
					buttons : this.buttons
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		//初始化form表单
		this.formPanel = new Ext.FormPanel({
				url : __ctxPath + '/customer/saveProduct.do',
				layout : 'form',
				id : 'ProductForm',
				bodyStyle : 'padding:5px;',
				frame : false,
				defaults : {
					width : 400,
					anchor : '98%,98%'
				},
				formId : 'ProductFormId',
				defaultType : 'textfield',
				items : [{
							name : 'product.productId',
							id : 'productId',
							xtype : 'hidden',
							value : this.productId == null
									? ''
									: this.productId
						}, {
							//fieldLabel : '供应商',
							xtype:'hidden',
							name : 'product.providerId',
							id : 'providerId',
							allowBlank:false,
							blankText:'供应商不能为空!'
						},{
							fieldLabel : '产品名称',
							name : 'product.productName',
							id : 'productName',
							allowBlank:false,
							blankText:'录入时间不能为空!'
						}, {
							fieldLabel : '产品型号',
							name : 'product.productModel',
							id : 'productModel',
							allowBlank:false,
							blankText:'录入时间不能为空!'
						},{
							xtype : 'compositefield',
							fieldLabel : '供应商*',
							items : [{
								//fieldLabel : '供应商',
								xtype:'textfield',
								name : 'providerName',
								id : 'providerName',
								width:230,
								allowBlank:false,
								blankText:'供应商不能为空!'
							},{
								xtype:'button',
								//id:'customerSelectButton',
								text:'选择供应商',
								iconCls:'menu-provider',
								handler:function(){
									ProviderSelector.getView(function(providerId,providerName){
										Ext.getCmp('providerId').setValue(providerId);
										Ext.getCmp('providerName').setValue(providerName);
									},true).show();

								}
							}]
						},{
							fieldLabel : '录入时间',
							name : 'product.createtime',
							id : 'createtime',
							format:'Y-m-d',
							xtype:'datefield',
							allowBlank:false,
							blankText:'录入时间不能为空!'
						}, {
							fieldLabel : '产品单位',
							name : 'product.unit',
							id : 'unit'
						}, {
							xtype : 'compositefield',
							fieldLabel : '成本价*',
							items : [{
								xtype:'panel',
								width : 30,
								border : false,
								html:'<img src="'+__ctxPath+'/images/flag/customer/rmb.png" />：'
							},{
								//fieldLabel : '成本价',
								xtype: 'numberfield',
								width: 308,
								name : 'product.costPrice',
								id : 'costPrice'
							}]
						},{
							xtype : 'compositefield',
							fieldLabel : '出售价*',
							items : [{
								xtype:'panel',
								width : 30,
								border : false,
								html:'<img src="'+__ctxPath+'/images/flag/customer/rmb.png" />：'
							},{
								//fieldLabel : '出售价',
								xtype: 'numberfield',
								width: 308,
								name : 'product.salesPrice',
								id : 'salesPrice'
							}]
						}, {
							fieldLabel : '产品描述',
							name : 'product.productDesc',
							id : 'productDesc',
							xtype:'textarea',
							height:100
						}

				]
			});

	if (this.productId != null && this.productId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/customer/getProduct.do?productId='
					+ this.productId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var result = action.result.data;
				var createtime = getDateFromFormat(result.createtime,
						'yyyy-MM-dd HH:mm:ss');
				Ext.getCmp('createtime').setValue(new Date(createtime));
				Ext.getCmp('providerId').setValue(result.provider.providerId);
				Ext.getCmp('providerName').setValue(result.provider.providerName)
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
		
		this.buttons = [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('ProductForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('ProductGrid').getStore()
											.reload();
									Ext.getCmp('ProductFormWin').close();
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
						Ext.getCmp('ProductFormWin').close();
					}
				}];//end of the buttons
	}//end of the initUIComponents
});
