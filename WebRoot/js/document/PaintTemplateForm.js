/**
 * @author
 * @createtime
 * @class PaintTemplateForm
 * @extends Ext.Window
 * @description PaintTemplate表单
 * @company 智维软件
 */
PaintTemplateForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				PaintTemplateForm.superclass.constructor.call(this, {
							id : 'PaintTemplateFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 200,
							width : 500,
							maximizable : true,
							title : '模板详细信息',
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
							// id : 'PaintTemplateForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'paintTemplate.ptemplateId',
								xtype : 'hidden',
								value : this.ptemplateId == null
										? ''
										: this.ptemplateId
							}, {
								name : 'paintTemplate.fileId',
								xtype : 'hidden'
							}, {
								fieldLabel : '模板名称',
								name : 'paintTemplate.templateName',
								allowBlank : false,
								maxLength : 64
							},{
								fieldLabel : '是否启用',//0为启动，1为禁用
								name : 'paintTemplate.isActivate',
								allowBlank : false,
								xtype : 'hidden',
								value:0
							}, {
								xtype:'container',
								layout:'column',
								style:'padding-left:0px;margin-left:0px;',
								defaults:{border:false},
								items:[
									{
										width : 340,
										height:36,
										style : 'padding-left:0px;',
										layout:'form',
										items:{
											xtype:'textfield',
											fieldLabel : '模板文件',
											name : 'paintTemplate.path',
											readOnly:true,
											anchor:'98%,98%'
										}
									},{
										xtype:'button',
										text:'上传模板',
										iconCls:'btn-upload',
										scope:this,
										handler:function(){
											var fp=this.formPanel;
											var dialog=App.createUploadDialog({
												  permitted_extensions : ['doc','docx'],
												  file_cat:'document',
												  callback:function(data){
//												  	for(var i=0;i<data.length;i++){
												  	  if(data.length>0){
												  		fp.getCmpByName('paintTemplate.fileId').setValue(data[0].fileId);
												  		fp.getCmpByName('paintTemplate.path').setValue(data[0].filepath);
												  	  }
//												  	}
												  }
											});
											dialog.show();
										}
									}
								]
							}]
						});
				// 加载表单对应的数据
				if (this.ptemplateId != null && this.ptemplateId != 'undefined') {
					
					this.formPanel.loadData({
								url : __ctxPath
										+ '/document/getPaintTemplate.do?ptemplateId='
										+ this.ptemplateId,
								root : 'data',
								preName : 'paintTemplate'
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
							url : __ctxPath + '/document/savePaintTemplate.do',
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('PaintTemplateGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});