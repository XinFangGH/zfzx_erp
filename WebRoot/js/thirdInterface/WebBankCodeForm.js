/**
 * @author
 * @createtime
 * @class WebBankCodeForm
 * @extends Ext.Window
 * @description WebBankCode表单
 * @company 智维软件
 */
WebBankCodeForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				 if(typeof(_cfg.selectId)!="undefined") {
				      this.selectId=_cfg.selectId;
				}
				// 必须先初始化组件
				this.initUIComponents();
				WebBankCodeForm.superclass.constructor.call(this, {
							id : 'WebBankCodeFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 200,
							width : 500,
							maximizable : true,
							title : '详细信息',
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
				// 文件上传组件的回调函数
		function fn(data) {
			// 定义一个变量用来接收上传文件成功后返回的路径值
			var path;
			for (var i = 0; i < data.length; i++) {
				path = data[i].filePath;
				// 得到报表模块路径的字段，然后把上传成功后返回的路径值设到字段里面
				Ext.getCmp('logo').setValue(path);
			}
		}
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							// id : 'WebBankCodeForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							enctype : 'multipart/form-data',
							fileUpload: true,
							items : [{
										name : 'webBankCode.id',
										xtype : 'hidden',
										value : this.id == null ? '' : this.id
									}, {
										fieldLabel : '银行名称',
										name : 'webBankCode.bankName',
										maxLength : 50
									},
									
									{
										fieldLabel : '银行编码',
										name : 'webBankCode.bankCode',
										maxLength : 50
									},{
										xtype : 'textfield',
										fieldLabel : "Logo",
										columnWidth : 1,
										allowBlank : true,
										blankText : '不能为空',
										id : 'fileUpload',
										name: 'myUpload',
										fileUpload: true ,
										frame: true,
					    				inputType: 'file'
									},{
										xtype : 'numberfield',
										fieldLabel : '序号',
										name : 'webBankCode.orderNum',
										allowBlank : false,
										blankText : '序号为必填内容'
									}
									
									
//									{
//										
//									xtype : 'container',
//									height : 26,
//									layout : 'column',
//									defaultType : 'textfield',
//									
//									enctype : 'multipart/form-data',
//									fileUpload: true,
//									items : [{
//											xtype : 'label',
//											style : 'padding-left:0px;margin-left:0px;margin-bottom:2px;',
//											text : 'Logo:',
//											width : 103
//										}, {
//											columnWidth:.8,
//											name : 'webBankCode.bankLogo',
//											id : 'logo',
//											readOnly : this.isDefaultIn == 1? true  : false
//										},
//										
//										{
//											xtype : 'button',
//											id:'uploadBtn',
//											text : '上传LOGO',
//											columnWidth : .2,
//											disabled : this.isDefaultIn == 1 ? true  : false,
//											handler : function() {
//												// 点击上传附件按钮后，调用上传组件
//												var dialog = App.createUploadDialog({
//													permitted_extensions : ['jpg','png'],
//													url:__ctxPath+'/jasper-upload',
//													file_cat : 'bankLogo',
//													callback : fn
//												});
//												dialog.show('queryBtn');
//											}
//									}
//									]
//						
//								}
//									{
//									fieldLabel : '是否显示',
//									allowBlank : false,
//									xtype : 'combo',
//									hiddenName:'webBankCode.thirdPayConfig',
//									name:'webBankCode.thirdPayConfig',
//									editable : false,
//									mode : 'local',
//									triggerAction : 'all',
//									valueField:'id',
//									displayField:'name',
//									store : new Ext.data.SimpleStore({
//										fields : ["id","name"],
//										data : [['0', '显示'], ['1', '不显示']]
//									}),
//									value : 0
//								
//								}
									]
						});
				// 加载表单对应的数据
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath
										+ '/thirdInterface/getWebBankCode.do?id='
										+ this.id,
								root : 'data',
								preName : 'webBankCode'
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
				
//				alert("selectId------------"+this.selectId);
				
				var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/  
				var url = 'file://'+ Ext.get('fileUpload').dom.value;  
		        var furl=Ext.get('fileUpload').dom.value;

		        var extendname=furl.substring(furl.lastIndexOf("."),furl.length);//获取文件的后缀名
		        
//				var rootPath = this.getCmpByName('webBankCode.bankLogo').getValue();
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath
									+ '/thirdInterface/saveWebBankCode.do',
							params:{
								extendname:extendname,
								tablename:"cs_bank",
								selectId:this.selectId
							},
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('WebBankCodeGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});