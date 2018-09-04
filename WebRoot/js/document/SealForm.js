/**
 * @author
 * @createtime
 * @class SealForm
 * @extends Ext.Window
 * @description Seal表单
 * @company 智维软件
 */
SealForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SealForm.superclass.constructor.call(this, {
							id : 'SealFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 200,
							width : 440,
							maximizable : true,
							title : '印章详细信息',
							buttonAlign : 'center',
							buttons : [{
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
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							// id : 'SealForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
										name : 'seal.sealId',
										xtype : 'hidden',
										value : this.sealId == null
												? ''
												: this.sealId
									}, {
										fieldLabel : '',
										name : 'seal.fileId',
										xtype : 'hidden'
									}, {
										fieldLabel : '印章名称',
										name : 'seal.sealName',
										allowBlank : false,
										maxLength : 64
									},{
									    xtype:'hidden',
									    name:'seal.belongId',
									    value:curUserInfo.userId
									},{
									    xtype:'compositefield',
									    fieldLabel : '所属人员',
										items:[
										{
										    xtype:'textfield',
										    allowBlank : false,
										    readOnly:true,
										    name : 'seal.belongName',
										    value:curUserInfo.fullname
										},{
										    xtype:'button',
										    text:'选择人员',
										    scope:this,
										    handler:function(){
										       var fp=this.formPanel;
										       UserSelector.getView(function(id,name){
										          fp.getCmpByName('seal.belongId').setValue(id);
												  fp.getCmpByName('seal.belongName').setValue(name);
										       },true).show();
										    }
										}
										]
									    
									}, {
										xtype:'container',
										layout:'column',
										style:'padding-left:0px;margin-left:0px;',
										defaults:{border:false},
										items:[
											{
												width : 280,
												height:36,
												style : 'padding-left:0px;',
												layout:'form',
												items:{
													fieldLabel : '印章路途',
													name : 'seal.sealPath',
													maxLength : 128,
													xtype:'textfield',
													readOnly:true,
													anchor:'98%,98%'
												}
											},{
												xtype:'button',
												text:'上传印章文件',
												iconCls:'btn-upload',
												scope:this,
												handler:function(){
													var fp=this.formPanel;
													var dialog=App.createUploadDialog({
														  file_cat:'document',
														  callback:function(data){
														  	  if(data.length>0){
														  		fp.getCmpByName('seal.fileId').setValue(data[0].fileId);
														  		fp.getCmpByName('seal.sealPath').setValue(data[0].filePath);
														  	  }
														  }
													});
													dialog.show();
												}
											}
										]
									}
//									, {
//										fieldLabel : '',
//										name : 'seal.belongId',
//										allowBlank : false,
//										xtype : 'numberfield'
//									}, {
//										fieldLabel : '',
//										name : 'seal.belongName',
//										allowBlank : false,
//										maxLength : 64
//									}
									]
						});
				// 加载表单对应的数据
				if (this.sealId != null && this.sealId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/document/getSeal.do?sealId='
										+ this.sealId,
								root : 'data',
								preName : 'seal'
							});
				}

			},// end of the initcomponents

			/**
			 * 重置
			 * 
			 * @param {}
			 *            formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/document/saveSeal.do',
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('SealGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});