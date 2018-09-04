/**
 * @author 
 * @createtime 
 * @class IndexShowForm
 * @extends Ext.Window
 * @description IndexShow表单
 * @company 智维软件
 */
IndexShowForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				IndexShowForm.superclass.constructor.call(this, {
							id : 'IndexShowFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[页面显示]详细信息',
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
							//id : 'IndexShowForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'indexShow.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							},{
								xtype : "dickeycombo",
								fieldLabel : '类型',	
 								hiddenName : 'indexShow.type',
 								nodeKey : 'index_show',
 								emptyText : '请选择',
 								maxLength: 255,
 								scope : this,
								editable : true,
 								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
										})
									}
								}

 							},{
								xtype : 'textarea',
								width : 1000,
								height : 400,
								style : "margin-top:4px",
								name : "indexShow.description",
								anchor : "100%",
								labelWidth : 100,
								allowBlank : false,
								fieldLabel : "内容"
							},{
								fieldLabel : '添加时间',	
 								name : 'indexShow.insertTime',
 								xtype:'datefield',
								format:'Y-m-d',
								allowBlank : false,
								value:new Date()
							},{
										xtype : 'combo',
										fieldLabel : '网站类别',
										allowBlank : false,
										mode : 'local',
										displayField : 'typeValue',
						                valueField : 'typeId',
						                triggerAction : 'all',
										hiddenName : 'indexShow.webKey',
										store : new Ext.data.SimpleStore({
								        data : [['P2P网站',1],['云购',2],['云众筹',3]],
								        fields:['typeValue','typeId']
							            })
									},{
								fieldLabel : '是否显示',
								hiddenName : 'indexShow.isShow',
								allowBlank : false,
								xtype : 'combo',
								store : [['1','是'],['0','否']],
								mode : 'local',
								editable : false,
								triggerAction : 'all'
								//emptyText : '--请选择表单状态--'
							}]
						});
				//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p/getIndexShow.do?id='+ this.id,
								root : 'data',
								preName : 'indexShow'
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
						url:__ctxPath + '/p2p/saveIndexShow.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('IndexShowGrid');
							if (gridPanel != null) {
								
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});