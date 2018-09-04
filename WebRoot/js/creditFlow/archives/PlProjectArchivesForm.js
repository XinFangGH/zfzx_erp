/**
 * @author 
 * @createtime 
 * @class PlProjectArchivesForm
 * @extends Ext.Window
 * @description PlProjectArchives表单
 * @company 智维软件
 */
PlProjectArchivesForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				
				if(typeof(_cfg.readonly)!="undefined")
						{
						      this.readonly=_cfg.readonly;
						}
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				PlProjectArchivesForm.superclass.constructor.call(this, {
							id : 'PlProjectArchivesFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 250,
							width : 500,
							maximizable : true,
							title : '办理归档',
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
						    layout:'column',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							monitorValid : true,
							frame : true,
						plain : true,
						labelAlign : "right",
							//id : 'PlProjectArchivesForm',
							defaults : {
								anchor : '96%',
								labelWidth : 80,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [{
								name : 'plProjectArchives.projtoarchiveId',
								xtype : 'hidden',
								value : this.projtoarchiveId == null ? '' : this.projtoarchiveId
							}
																																																	,{
																fieldLabel : '',
																xtype : 'hidden',
																id :'archivescherkerid',
								 								name : 'plProjectArchives.plarchivesId'
								 							}
																																										,{
																
																fieldLabel : '',	
																xtype : 'hidden',
								 								name : 'plProjectArchives.projectId'
								 							}
																
								 							,{  columnWidth : .26,
																 labelWidth : 90,
																 layout : 'form',
																 items :[{
																		fieldLabel : '项目相关文件',	
										 								name : 'isArchives',
										 								id :'isArchives',
										 								readOnly :this.readonly,
										 								xtype : 'checkbox',
										 								 listeners:{
																           'check':function(box,checked){
																               if(checked==true){
																                  Ext.getCmp("plProjectArchives.isArchives").setValue(1);
																               }else{
																               
																               Ext.getCmp("plProjectArchives.isArchives").setValue(0);
																               }
																               
																           }
																        }
								 								},
								 								{
										 								name : 'plProjectArchives.isArchives',
										 								id :'plProjectArchives.isArchives',
										 								xtype : 'hidden',
										 								value :0
								 								}]
								 							},{  columnWidth : .24,
																 labelWidth : 90,
																 labelAlign : 'left',
																 layout : 'form',
																 items :[{
										 								fieldLabel : "<span style='margin-left:3px'>是否归档</span> ",
										                                labelSeparator : '',
										                                anchor : "100%"
								 								}]
								 							},{  columnWidth : .5,
																 labelWidth : 90,
																 layout : 'form',
																 items :[{
																		fieldLabel : '归档时间',	
										 								name : 'plProjectArchives.archivesTime',
										 								format :'Y-m-d',
										 								readOnly :this.readonly,
										 								xtype : 'datefield'
								 								}]
								 							},{  columnWidth : 1,
																 labelWidth : 90,
																 layout : 'form',
																 items :[{
																		
										 									id:'archivescherkername',	 
																			fieldLabel : '档案柜',	
																			//value : '中国银行',
																			submitValue:false,
																			readOnly :this.readonly,
																		 	name : 'plProjectArchives.plarchivesname',
														                    xtype:'trigger',
																			triggerClass :'x-form-search-trigger',
																			emptyText : '点击选择----------------',
																			editable : false,
																			allowBlank:false,
																			onTriggerClick : function(){
																				
																				var selectavrchives=function(obj){
																				Ext.getCmp("archivescherkername").setValue(obj.pathname);
																				Ext.getCmp("archivescherkerid").setValue(obj.id);
																					
																				}
																				selectArchiveschecker(selectavrchives);
																			},
																			anchor : '93%'
								 								},{
																		
										 								fieldLabel : '归档备注',	
										 								name : 'plProjectArchives.remark',
										 								readOnly :this.readonly,
										 								xtype : 'textarea'
								 								}]
								 							}
															
																																			]
						});
				//加载表单对应的数据	
				if (this.projtoarchiveId != null && this.projtoarchiveId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow/archives/getPlProjectArchives.do?projtoarchiveId='+ this.projtoarchiveId,
								root : 'data',
								preName : 'plProjectArchives',
								scope:this,
								success:function(resp,options){
             						var result=Ext.decode(resp.responseText);
             						Ext.getCmp('archivescherkername').setValue(result.data.checkername);
             						if(result.data.isArchives==0){
             						   Ext.getCmp('isArchives').setValue(false);
             						}else{
             						   Ext.getCmp('isArchives').setValue(true);
             						}
             						
             						
             						}
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
						url:__ctxPath + '/creditFlow/archives/savePlProjectArchives.do',
						params : {
							businessType : this.businessType,
							projectId : this.projectId
						},
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('PlProjectArchivesGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});