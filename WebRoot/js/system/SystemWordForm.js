/**
 * @author 
 * @createtime 
 * @class SystemWordForm
 * @extends Ext.Window
 * @description SystemWord表单
 * @company 智维软件
 */
SystemWordForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SystemWordForm.superclass.constructor.call(this, {
							id : 'SystemWordFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 530,
							width : 600,
							maximizable : true,
							title : '词条详细信息',
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
							//id : 'SystemWordForm',
							defaults : {
								anchor : '100%,100%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'systemWord.wordId',
								xtype : 'hidden',
								value : this.wordId == null ? '' : this.wordId
							}, {
								fieldLabel : '是否是词条分类',
								value : false,
								xtype : 'hidden',
								name : 'systemWord.isWordType'
							}, {
								fieldLabel : '词条名称',	
 								name : 'systemWord.wordName',
 								allowBlank : false,
 								labelWidth:60,
 								maxLength: 100
							}, {
								fieldLabel : '词条Key',	
 								name : 'systemWord.wordKey',
 								allowBlank : false,
 								labelWidth:60,
 								maxLength: 100,
 								readOnly : this.isReadOnly
							},{
								fieldLabel : '词条描述',	
 								name : 'systemWord.wordDescription',
 								xtype:'fckeditor',
 								labelWidth:60,
 								allowBlank : false,
 								height : 350,
 								maxLength: 65535
							}
																																			]
						});
				//加载表单对应的数据	
				if (this.wordId != null && this.wordId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getSystemWord.do?wordId='+this.wordId,
								root : 'data',
								preName : 'systemWord'
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
				var callback=this.callback;
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/system/saveSystemWord.do?wordKey='+this.wordKey,
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('SystemWordGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							if(callback!=null && callback!=undefined){
								callback.call(this);
							}
							this.close();
						}
					}
				);
			}//end of save

		});