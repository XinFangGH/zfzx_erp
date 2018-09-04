/**
 * @author
 * @class PlatDataPublishForm
 * @extends Ext.Panel
 * @description [PlatDataPublishForm]管理
 * @company 互融软件
 * @createtime:
 */
 
 
PlatDataPublishForm = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
   PlatDataPublishForm.superclass.constructor.call(this, {
							id : 'PlatDataPublishForm'+this.typeId,
							title : "平台数据披露",
							region : 'center',
							layout : 'fit',
							modal : true,
							height :600,
							width : 600,
							maximizable : true,
							iconCls:"menu-finance",
							tbar : this.ttbar,
							items : [this.formPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.ttbar=new Ext.Toolbar({
					items : [{
									iconCls : 'btn-save',
									text : '保存',
									xtype : 'button',
									scope:this,
									handler : this.save
								}]
				});
				var leftlabel = 150;
				var rightlabel = 160;
				this.formPanel = new Ext.FormPanel({
					region : 'center',
					layout : 'form',
					bodyStyle : 'padding:10px',
					autoScroll : true,
					frame : true,
					labelAlign : 'right',
					defaults : {
							anchor : '60%',
							columnWidth : 1,
							labelWidth : 60
						},
					items : [{
					layout : "form", // 从上往下的布局
					xtype : 'fieldset',
					title : '平台披露数据',
					  items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
										xtype : 'hidden',
										name : 'platDataPublish.publishId'
								},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '借款项目交易金额',
						       name : 'loneMoney',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%',
						       listeners : {
					    	         scope:this,
							         afterrender : function(obj) {
							         	 var value= obj.getValue();
								        obj.setValue(Ext.util.Format.number(value,'0,000.00'))
							       },
							         change  :function(nf) {
								        var value= nf.getValue();
								         {
									      nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									      this.getCmpByName("platDataPublish.loneMoney").setValue(value);
								         }
							       }
					             }
					           },{
					          xtype : "hidden",
						      name : "platDataPublish.loneMoney"
					          }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>元</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '交易笔数',
						       name : 'platDataPublish.loneCount',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '借贷余额',
						       name : 'onLoneMoney',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%',
						       listeners : {
					    	         scope:this,
							         afterrender : function(obj) {
								       obj.on("keyup")
							       },
							         change  :function(nf) {
								        var value= nf.getValue();
								         {
									      nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									      this.getCmpByName("platDataPublish.onLoneMoney").setValue(value);
								         }
							       }
					             }
					           },{
					          xtype : "hidden",
						      name : "platDataPublish.onLoneMoney"
					          }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>元</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '最大单户借款余额占比',
						       name : 'platDataPublish.oneOnloanProportion',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>%</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '最大10户借款余额占比',
						       name : 'platDataPublish.tenOnloanProportion',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>%</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '借款逾期金额',
						       name : 'overdueMoney',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%',
						       listeners : {
					    	         scope:this,
							         afterrender : function(obj) {
								       obj.on("keyup")
							       },
							         change  :function(nf) {
								        var value= nf.getValue();
								         {
									      nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									      this.getCmpByName("platDataPublish.overdueMoney").setValue(value);
								         }
							       }
					             }
					           },{
					          xtype : "hidden",
						      name : "platDataPublish.overdueMoney"
					          }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>元</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '代偿金额',
						       name : 'compensatoryMoney',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%',
						       listeners : {
					    	         scope:this,
							         afterrender : function(obj) {
								       obj.on("keyup")
							       },
							         change  :function(nf) {
								        var value= nf.getValue();
								         {
									      nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									      this.getCmpByName("platDataPublish.compensatoryMoney").setValue(value);
								         }
							       }
					             }
					           },{
					          xtype : "hidden",
						      name : "platDataPublish.compensatoryMoney"
					          }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>元</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '借贷逾期率',
						       name : 'platDataPublish.overdueProportion',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>%</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '借贷坏账率',
						       name : 'platDataPublish.badDebtProportion',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>%</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '出借人数量',
						       name : 'platDataPublish.lenderCount',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '借款人数量',
						       name : 'platDataPublish.borrowerCount',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '投资人数量',
						       name : 'platDataPublish.investorCount',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '成功投资金额',
						       name : 'investMoney',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%',
						       listeners : {
					    	         scope:this,
							         afterrender : function(obj) {
								       obj.on("keyup")
							       },
							         change  :function(nf) {
								        var value= nf.getValue();
								         {
									      nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									      this.getCmpByName("platDataPublish.investMoney").setValue(value);
								         }
							       }
					             }
					           },{
					          xtype : "hidden",
						      name : "platDataPublish.investMoney"
					          }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>元</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '已为投资人赚取金额',
						       name : 'haveEarnedMoney',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%',
						       listeners : {
					    	         scope:this,
							         afterrender : function(obj) {
							         	 var value= obj.getValue();
								        obj.setValue(Ext.util.Format.number(value,'0,000.00'))
							       },
							         change  :function(nf) {
								        var value= nf.getValue();
								         {
									      nf.setValue(Ext.util.Format.number(value,'0,000.00'))
									      this.getCmpByName("platDataPublish.haveEarnedMoney").setValue(value);
								         }
							       }
					             }
					           },{
					          xtype : "hidden",
						      name : "platDataPublish.haveEarnedMoney"
					          }]
				            },{
							    columnWidth : .1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth :20,
									//	style : 'padding-left:5px;padding-top:8px;',
								items : [{
								   fieldLabel : "<span style='margin-left:1px'>元</span> ",
								   labelSeparator : '',
								  anchor : "100%"
													
							  }]},{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
						       xtype : 'textfield',
						       fieldLabel : '投资待赚取笔数',
						       name : 'platDataPublish.toEarnCount',
						       maxLength : 100,
						       allowNegative: false, // 允许负数 
					           style: {imeMode:'disabled'},
						       blankText : "金额不能为空，请正确填写!",
						       allowBlank : false,
						       readOnly : this.isAllReadOnly,
						       anchor:'100%'
					           }]
				            },{
					         columnWidth :.9, // 该列有整行中所占百分比
					         layout : "form", // 从上往下的布局
					         labelWidth : rightlabel,
				             labelAlign :'right',
					         border : false,
					         items:[{
							fieldLabel : ' 客户投诉情况等经营管理信息',
							name : 'platDataPublish.manageInformation',
							xtype : 'textarea',
							maxLength : 500,
							height:90,
							readOnly : this.isAllReadOnly,
							anchor : "100%"
						}]
				            }]
					}]
					}]
				});

				 this.formPanel.loadData({
								url : __ctxPath + '/p2p/getPlatDataPublish.do?typeId='+this.typeId,
								root : 'data',
								preName : 'platDataPublish',
								scope:this,
			                	success : function(response, options) {
					              var result = Ext.decode(response.responseText);
				                  this.getCmpByName('loneMoney').setValue(Ext.util.Format.number(result.data.loneMoney,'0,000.00'));
				                  this.getCmpByName('onLoneMoney').setValue(Ext.util.Format.number(result.data.onLoneMoney,'0,000.00'));
				                  this.getCmpByName('overdueMoney').setValue(Ext.util.Format.number(result.data.overdueMoney,'0,000.00'));
				                  this.getCmpByName('compensatoryMoney').setValue(Ext.util.Format.number(result.data.compensatoryMoney,'0,000.00'));
				                  this.getCmpByName('investMoney').setValue(Ext.util.Format.number(result.data.investMoney,'0,000.00'));
				               },
				             failure : function() {
					         Ext.ux.Toast.msg('操作提示', '对不起，数据加载失败！');
				             }
							});

			},
			
			save : function() {
				if(this.formPanel.getForm().isValid()) {
						this.formPanel.getForm().submit({
							        url:__ctxPath + '/p2p/savePlatDataPublish.do',
									method : 'post',
									waitMsg : '正在提交数据...',
									success : function(fp, action) {
										Ext.ux.Toast.msg('操作信息', '成功信息保存！');
										
									},
									failure : function(fp, action) {
										Ext.MessageBox.show({
													title : '操作信息',
													msg : '信息保存出错，请联系管理员！',
													buttons : Ext.MessageBox.OK,
													icon : 'ext-mb-error'
												});
									}
								});

					}
				}
			}
		);

