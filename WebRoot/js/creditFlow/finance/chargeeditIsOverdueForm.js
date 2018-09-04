/**
 * @author 
 * @createtime 
 * @class SlFundDetailForm
 * @extends Ext.Window
 * @description SlFundDetail表单
 * @company 智维软件
 */
chargeeditIsOverdueForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				chargeeditIsOverdueForm.superclass.constructor.call(this, {
							id : 'chargeeditIsOverdueForm',
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
					        id:'chargeeditIsOverdueGrid',
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
								name : 'slActualToCharge.actualChargeId',
								xtype : 'hidden',
								value : this.actualChargeId == null ? '' : this.actualChargeId
							}
													
																																										,{
																fieldLabel : '是否逾期',	
						//					 								id:'notMoney',
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
													                hiddenName:"slActualToCharge.isOverdue",
														          	name : 'slActualToCharge.isOverdue'
								 								
								 							}
																						
													
																																			]
						});
				//加载表单对应的数据	
				if (this.actualChargeId != null && this.actualChargeId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow/finance/getSlActualToCharge.do?actualChargeId='+ this.actualChargeId,
								root : 'data',
								preName : 'slActualToCharge'
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
						url:__ctxPath + '/creditFlow/finance/editIsOverdueSlActualToCharge.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SlActualToChargeGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});