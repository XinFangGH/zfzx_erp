/**
 * @author 
 * @createtime 
 * @class BpCustRedMemberForm
 * @extends Ext.Window
 * @description BpCustRedMember表单
 * @company 智维软件
 */
BpCustRedMemberForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpCustRedMemberForm.superclass.constructor.call(this, {
							id : 'BpCustRedMemberFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BpCustRedMember]详细信息',
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
							//id : 'BpCustRedMemberForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpCustRedMember.redTopersonId',
								xtype : 'hidden',
								value : this.redTopersonId == null ? '' : this.redTopersonId
							}
																																																	,{
																fieldLabel : '',	
								 								name : 'bpCustRedMember.redMoney'
								 								 							}
																																										,{
																fieldLabel : '',	
								 								name : 'bpCustRedMember.redId'
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.redTopersonId != null && this.redTopersonId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p.redMoney/getBpCustRedMember.do?redTopersonId='+ this.redTopersonId,
								root : 'data',
								preName : 'bpCustRedMember'
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
						url:__ctxPath + '/p2p.redMoney/saveBpCustRedMember.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpCustRedMemberGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});