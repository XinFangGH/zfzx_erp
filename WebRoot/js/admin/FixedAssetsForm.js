/**
 * @author
 * @createtime
 * @class FixedAssetsForm
 * @extends Ext.Window
 * @description FixedAssetsForm表单
 * @company 智维软件
 */
FixedAssetsForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		FixedAssetsForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'FixedAssetsFormWin',
			title : '固定资产详细信息(*为必填)',
			width : 520,
			iconCls : 'menu-assets',
			height : 450,
			minWidth : 519,
			minHeight : 449,
			items : this.formPanel,
			maximizable : true,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel =  new Ext.FormPanel({
			url : __ctxPath + '/admin/saveFixedAssets.do',
			layout : 'form',
			id : 'FixedAssetsForm',
			frame : false,
			bodyStyle : 'padding:5px;',
			formId : 'FixedAssetsFormId',
			defaultType : 'textfield',
			items : [{
					name : 'fixedAssets.assetsId',
					id : 'assetsId',
					xtype : 'hidden',
					value : this.assetsId == null ? '' : this.assetsId
				}, {
					name : 'fixedAssets.depreRate',
					id : 'depreRate',
					xtype : 'hidden'
				},{
				    name :'calDepreMethod',
				    id: 'calDepreMethod',
				    xtype : 'hidden'
				
				} ,{
					fieldLabel : '资产名称*',
					name : 'fixedAssets.assetsName',
					id : 'assetsName',
					anchor : '99%',
					allowBlank : false
				}, {
					fieldLabel : '资产编号',
					xtype : 'textfield',
					name : 'fixedAssets.assetsNo',
					id : 'assetsNo',
					readOnly : true,
					anchor : '99%'
				}, {
					fieldLabel : '资产类别*',
					hiddenName : 'fixedAssets.assetsType.assetsTypeId',
					id : 'assetsTypeId',
					xtype : 'combo',
					mode : 'local',
					anchor : '99%',
					allowBlank : false,
					editable : false,
					valueField : 'id',
					displayField : 'name',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						url : __ctxPath + '/admin/comboxAssetsType.do',
						fields : ['id', 'name']
					})
				}, {
					fieldLabel : '置办日期*',
					name : 'fixedAssets.buyDate',
					id : 'buyDate',
					format : 'Y-m-d',
					xtype : 'datefield',
					allowBlank : false,
					editable : false,
					anchor : '99%'
				}, {
					xtype : 'container',
					layout : 'column',
					fieldLabel : '所属部门*',
					layoutConfigs : {
						align : 'middle'
					},
					defaults : {margins : '0 2 0 0'},
					items : [{
						columnWidth : .99,
						xtype : 'textfield',
						name : 'fixedAssets.beDep',
						id : 'beDep',
						allowBlank : false,
						readOnly : true
					}, {
						xtype : 'button',
						iconCls : 'btn-dep-sel',
						text : '选择部门',
						handler : function() {
							DepSelector.getView(function(id, name) {
								Ext.getCmp('beDep').setValue(name);
							}, true).show();
						}
					}, {
						xtype : 'button',
						text : '清除记录',
					    iconCls : 'reset',
						handler : function() {
							Ext.getCmp('beDep').setValue('');
						}
					}]
				}, {
					xtype : 'container',
					layout : 'column',
					fieldLabel : '保管人',
					height : 28,
					layoutConfigs : {
						align : 'middle'
					},
					defaults : {margins : '0 2 0 0'},
					items : [{
						columnWidth : .99,
						xtype : 'textfield',
						name : 'fixedAssets.custodian',
						id : 'custodian',
						readOnly : true
					}, {
						xtype : 'button',
						iconCls : 'btn-user-sel',
						text : '选择人员',
						handler : function() {
							UserSelector.getView(function(id, name) {
								Ext.getCmp('custodian').setValue(name);
							}, true).show();
						}
					}, {
						xtype : 'button',
						text : '清除记录',
						iconCls : 'reset',
						handler : function() {
							Ext.getCmp('custodian').setValue('');
						}
					}]
				}, {
					xtype : 'tabpanel',
					height : 220,
					plain : true,
					activeTab : 0,
					items : [{
						layout : 'form',
						id : 'deprePanel',
						title : '折旧信息',
						frame : false,
						bodyStyle : 'padding:4px 4px 4px 4px',
						height : 190,
						defaults : {
							anchor : '98%,98%'
						},
						defaultType : 'textfield',
						items : [{
							fieldLabel : '折旧类型*',
							hiddenName : 'fixedAssets.depreType.depreTypeId',
							id : 'depreTypeId',
							xtype : 'combo',
							mode : 'local',
							allowBlank : false,
							editable : false,
							valueField : 'id',
							displayField : 'name',
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								url : __ctxPath + '/admin/comboxDepreType.do',
								fields : ['id', 'name', 'method']
							}),
							listeners : {
								select : function(combo, record, index) {
									var method = record.data.method;
									if (method == '2') {
										Ext.getCmp('calDepreMethod').setValue(method);
										Ext.getCmp('WorkGrossPanel').show();
										Ext.getCmp('intendTermContainer').hide();
                                        Ext.getCmp('intendTerm').setValue('');
									} else {
										Ext.getCmp('calDepreMethod').setValue(method);
										Ext.getCmp('intendTermContainer').show();
										Ext.getCmp('WorkGrossPanel').hide();
										Ext.getCmp('intendWorkGross').setValue('');
										Ext.getCmp('workGrossUnit').setValue('');
										Ext.getCmp('defPerWorkGross').setValue('');
									}
								}
							}
						}, {
							fieldLabel : '开始折旧日期',
							name : 'fixedAssets.startDepre',
							id : 'startDepre',
							format : 'Y-m-d',
							xtype : 'datefield',
							editable : false
						}, {
							xtype : 'container',
							layout : 'column',
							fieldLabel : '预计使用年限*',
							height : 28,
							layoutConfigs : {
								align : 'middle'
							},
							defaults : {margins : '0 2 0 0'},
							id : 'intendTermContainer',
							items : [{
								columnWidth : .99,
								xtype : 'numberfield',
								name : 'fixedAssets.intendTerm',
								allowNegative : false,
								allowDecimals : false,
								id : 'intendTerm'
							}, {
								xtype : 'label',
								text : '年',
								width : 10
							}]
						}, {
							layout : 'form',
							xtype : 'container',
							id : 'WorkGrossPanel',
							items : [{
								xtype : 'container',
								layout : 'hbox',
								height : 28,
								layoutConfigs:{
									align:'middle'
								},
								defaults:{margins:'0 2 0 0'},
								items : [{
											xtype : 'label',
											text : '预使用总工作量*:',
											width : 103
										}, {
											xtype : 'numberfield',
											name : 'fixedAssets.intendWorkGross',
											allowNegative:false,
											id : 'intendWorkGross'
										}, {
											xtype : 'label',
											text : '单位*:'
										}, {
											xtype : 'textfield',
											name : 'fixedAssets.workGrossUnit',
											id : 'workGrossUnit',
											width : 30
										}]

							}, {
								fieldLabel : '默认周期工作量',
								xtype : 'numberfield',
								allowNegative:false,
								name : 'fixedAssets.defPerWorkGross',
								id : 'defPerWorkGross'
							}]
						}, {
							fieldLabel : '残值率(%)*',
							xtype : 'numberfield',
							name : 'fixedAssets.remainValRate',
							allowNegative : false,
							decimalPrecision : 2,
							id : 'remainValRate',
							allowBlank : false
						}, {
							fieldLabel : '资产值*',
							name : 'fixedAssets.assetValue',
							id : 'assetValue',
							allowBlank : false,
							allowNegative : false,
							decimalPrecision : 2,
							xtype : 'numberfield'
						}, {
							fieldLabel : '资产当前值*',
							name : 'fixedAssets.assetCurValue',
							id : 'assetCurValue',
							allowBlank : false,
							allowNegative:false,
							decimalPrecision:2,
							xtype:'numberfield'
						}]

					}, {
						layout : 'form',
						title : '扩展信息',
						width : 300,
						id : 'assetsFormPanel',
						height : 190,
						bodyStyle:'padding:4px 4px 4px 4px',
						defaults : {
							anchor : '98%,98%'
						},
						defaultType : 'textfield',
						items : [{
									fieldLabel : '规格型号',
									name : 'fixedAssets.model',
									id : 'model'
								}, {
									fieldLabel : '制造厂商',
									name : 'fixedAssets.manufacturer',
									id : 'manufacturer'
								}, {
									fieldLabel : '出厂日期',
									name : 'fixedAssets.manuDate',
									format : 'Y-m-d',
									id : 'manuDate',
									xtype : 'datefield'
								}, {
									fieldLabel : '备注',
									name : 'fixedAssets.notes',
									id : 'notes',
									height : 100,
									xtype : 'textarea'
								}]
					}]

				}

		]
	});//end of the formPanel

	if (this.assetsId != null && this.assetsId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getFixedAssets.do?assetsId='
					+ this.assetsId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var method = action.result.data.depreType.calMethod;
				if (method == '2') {//2为按工作量折算
					Ext.getCmp('calDepreMethod').setValue(method);
					Ext.getCmp('WorkGrossPanel').show();
					Ext.getCmp('intendTermContainer').hide();
				} else {
					Ext.getCmp('calDepreMethod').setValue(method);
					Ext.getCmp('WorkGrossPanel').hide();
					Ext.getCmp('intendTermContainer').show();
				}
				Ext.getCmp('buyDate').setValue(new Date(getDateFromFormat(
						action.result.data.buyDate, "yyyy-MM-dd HH:mm:ss")));
				Ext.getCmp('startDepre').setValue(new Date(getDateFromFormat(
						action.result.data.startDepre, "yyyy-MM-dd HH:mm:ss")));
				var manuDate = action.result.data.manuDate;
				if (manuDate != null) {
					Ext.getCmp('manuDate').setValue(new Date(getDateFromFormat(
							manuDate, "yyyy-MM-dd HH:mm:ss")));
				}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	};//end of load formPanel
		this.buttons = [{
			text : '保存',
			iconCls : 'btn-save',
			handler : this.save,
			scope : this
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				Ext.getCmp('FixedAssetsFormWin').close();
			}
		}];//end of the buttons 
	},//end of the initUIComponents
	
	/**
	 * 保存操作
	 */
	save : function() {
		var fp = Ext.getCmp('FixedAssetsForm');
		var method=Ext.getCmp('calDepreMethod').getValue();
		var flag=false;
		if(method!=''&&method!=null&&method!='undefind'){
			if(method == 2){
			   var intendWorkGross=Ext.getCmp('intendWorkGross').getValue();
			   var workGrossUnit=Ext.getCmp('workGrossUnit').getValue();
			   if(intendWorkGross!=null&&intendWorkGross!='undefind'&&intendWorkGross!=''&&workGrossUnit!=null&&workGrossUnit!='undefind'&&workGrossUnit!=''){
			        flag = true;
			   }						  
			}else{							   
			    var intendTerm=Ext.getCmp('intendTerm').getValue();
			    if(intendTerm!=null&&intendTerm!='undefind'&&intendTerm!=''){
			        flag=true;
			    }
			}
		
		if (fp.getForm().isValid()&&flag) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					Ext.getCmp('FixedAssetsGrid').getStore().reload();
					Ext.getCmp('FixedAssetsFormWin').close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
						title : '操作信息',
						msg : '信息保存出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					Ext.getCmp('FixedAssetsFormWin').close();
				}
			});
		}else
		   Ext.ux.Toast.msg('操作信息', '带*项为必填！！');
	  }
	}
});
