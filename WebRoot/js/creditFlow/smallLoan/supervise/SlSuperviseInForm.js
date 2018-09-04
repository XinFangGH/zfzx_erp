/**
 * @author 
 * @createtime 
 * @class SlSuperviseInForm
 * @extends Ext.Window
 * @description SlSuperviseIn表单
 * @company 智维软件
 */
SlSuperviseInForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SlSuperviseInForm.superclass.constructor.call(this, {
							id : 'SlSuperviseInFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[SlSuperviseIn]详细信息',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
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
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'SlSuperviseInForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'slSuperviseIn.superviseId',
								xtype : 'hidden',
								value : this.superviseId == null ? '' : this.superviseId
							}
																																																	,{
																fieldLabel : '',	
								 								name : 'slSuperviseIn.superviseUsers'
								 																 								,maxLength: 200
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slSuperviseIn.superviseDateTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slSuperviseIn.superviseOpinion'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'slSuperviseIn.remark'
								 																 								,maxLength: 250
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.superviseId != null && this.superviseId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/supervise/getSlSuperviseIn.do?superviseId='+ this.superviseId,
								root : 'data',
								preName : 'slSuperviseIn'
							});
				}
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/supervise/saveSlSuperviseIn.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlSuperviseInGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});