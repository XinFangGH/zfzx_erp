/**
 * @author
 * @class CoreParameterConfigForm
 * @extends Ext.Panel
 * @description [CoreParameterConfigForm]管理
 * @company 互融软件
 * @createtime:
 */
 
 CoreParameterConfigForm = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CoreParameterConfigForm.superclass.constructor.call(this, {
							id : 'CoreParameterConfigForm'+this.typeId,
							title : "云购核心参数配置",
							region : 'center',
							layout : 'fit',
							modal : true,
							height :600,
							width : 600,
							maximizable : true,
							iconCls:"menu-flowManager",
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
				var rightlabel = 100;
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
					title : '云购核心参数配置',
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
										name : 'coreParameterConfig.id'
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textfield',
													fieldLabel : '币值名称',
													name : 'coreParameterConfig.name',
													anchor : '90%',
													allowBlank : false,
													blankText : '描述',
													readOnly : this.isReadOnly
												}]
							
								},{
										columnWidth : 0.9, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										style : 'padding-top:8px;',
										items : [{
													xtype : 'textfield',
													fieldLabel : '1币值=',
													name : 'coreParameterConfig.amount',
													allowBlank : false,
													anchor : '100%',
													blankText : '积分数量为必填内容',
													readOnly : this.isReadOnly
												}]
							
									},{
										columnWidth : .1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 40,
										style : 'padding-left:5px;padding-top:8px;',
										items : [{
											fieldLabel : "积 分",
											labelSeparator : '',
											anchor : "100%"
										}]
									}]
					}]
					}]
				});

				 this.formPanel.loadData({
								url : __ctxPath + '/p2p/getCoreParameterConfig.do?typeId='+this.typeId,
								root : 'data',
								preName : 'coreParameterConfig'
							});

			},
			
			save : function() {
				if(this.formPanel.getForm().isValid()) {
						this.formPanel.getForm().submit({
							        url:__ctxPath + '/p2p/saveCoreParameterConfig.do',
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

