/**
 * @author 
 * @createtime 
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
editIsOverdueForm = Ext.extend(Ext.Window, {
			//构造函数11
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				editIsOverdueForm.superclass.constructor.call(this, {
							id : 'SlFundDetailFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 115,
							width : 230,
							title : '逾期状态',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
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
				var afterMoney=this.afterMoney;
				var notMoney=this.notMoney;
				var flatMoney=this.flatMoney;
				this.formPanel = new Ext.FormPanel({
					        id:'editAfterMoneyGrid',
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'SlFundDetailForm',
							defaults : {
								anchor : '96%,96%'
							},
							//defaultType : 'textfield',
							items : [{
								name : 'slFundIntent.fundIntentId',
								xtype : 'hidden',
								value : this.fundIntentId == null ? '' : this.fundIntentId
							}
													
																																										,{
																fieldLabel : '是否逾期',	
											 								id:'notMoney',
											 								xtype:'combo',
														             mode : 'local',
													               displayField : 'name',
													              valueField : 'id',
													              editable : false,
													                 width : 70,
													                 store : new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["是", "是"],
																	     	["否", "否"]
																	     	]
													              	}),
														             triggerAction : "all",
													                hiddenName:"slFundIntent.isOverdue",
														          	name : 'slFundIntent.isOverdue'
								 								
								 							}
																						
													
																																			]
						});
				//加载表单对应的数据	
				if (this.fundIntentId != null && this.fundIntentId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow/finance/getSlFundIntent.do?fundIntentId='+ this.fundIntentId,
								root : 'data',
								preName : 'slFundIntent'
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
						url:__ctxPath + '/creditFlow/finance/editIsOverdueSlFundIntent.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlFundIntentGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});