/**
 * @author
 * @createtime
 * @class NoticeForm
 * @extends Ext.Window
 * @description News表单
 * @company 智维软件
 */
NoticeForm = Ext.extend(Ext.Window, {
			imagePanlbar : null,
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				NoticeForm.superclass.constructor.call(this, {
					id : 'NoticeFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 550,
					width : 1030,
					iconCls : 'menu-notice',
					maximizable : true,
					title : '公告详细公告',
					buttonAlign : 'center',
					buttons : [{
						text : '保存',
						iconCls : 'btn-save',
						scope : this,
						handler : this.save
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
				this.imagePanlbar = new Ext.Toolbar({
					items : [{	
						iconCls : 'btn-upload',
						text : '上传',
						scope : this,
						handler : this.uploadImage.createCallback(this)
					}, {
						iconCls : 'btn-del',
						text  : '删除',
						scope : this,
						handler : function(){
						
						}
					}]
				});
				this.formPanel = new Ext.FormPanel({
							layout : 'hbox',
							frame : false,
							// baseCls:'x-plain',
							layoutConfig : {
								padding : '5',
								pack : 'start',
								align : 'middle'
							},
							defaults : {
								margins : '0 5 0 0'
							},
							border : false,
							items : [{
								xtype : 'fieldset',
								title : '公告内容',
								layout : 'form',
								labelWidth : 60,
								defaultType : 'textfield',
								autoWidth : true,
								autoHeight : true,
								defaults : {
									width : 550
								},
								items : [{
									name : 'news.newsId',
									xtype : 'hidden',
									value : this.newsId == null ? '' : this.newsId
								}, {
									name : 'news.sectionId',
									xtype : 'hidden'
								}, {
									fieldLabel : '是否公告',
									name : 'news.isNotice',
									xtype : 'hidden',
									value : 1
										// 是公告
									}, {
									xtype : 'compositefield',
									fieldLabel : '所属栏目',
									items : [{
												xtype : 'textfield',
												name : 'sectionName',
												allowBlank : false,
												readOnly : true
											}, {
												xtype : 'button',
												text : '选择栏目',
												iconCls : 'btn-select',
												scope : this,
												handler : this.section.createCallback(this)
											}]
								}, {
									fieldLabel : '公告标题',
									name : 'news.subject',
									allowBlank : false,
									maxLength : 128
								}, {
									fieldLabel : '作者',
									name : 'news.author',
									allowBlank : false,
									maxLength : 32
								}, {
									fieldLabel : '内容',
									name : 'news.content',
									allowBlank : false,
									height : 450,
									xtype : 'fckeditor',
									maxLength : 65535
								}]
							}, {
								xtype : 'fieldset',
								title : '其他信息',
								layout : 'form',
								labelWidth : 60,
								defaultType : 'textfield',
								autoWidth : true,
								autoHeight : true,
								defaults : {
									width : 280
								},
								items : [{
											fieldLabel : '公告图片',
											name : 'news.subjectIcon',
											maxLength : 128,
											xtype : 'hidden'
										},{
											fieldLabel : '公告图片Id',
											name : 'news.subjectIconId',
											maxLength : 128,
											xtype : 'hidden'
										}, {
											fieldLabel : '创建时间',
											name : 'news.createtime',
											allowBlank : false,
											xtype : 'datefield',
											format : 'Y-m-d',
											value : new Date()
										}, {
											fieldLabel : '失效时间',
											name : 'news.expTime',
											xtype : 'datefield',
											format : 'Y-m-d'
										}, {
											fieldLabel : '发布人',
											name : 'news.issuer',
											allowBlank : false,
											maxLength : 32,
											value : curUserInfo.fullname
										}, {
											fieldLabel : '状态',
											hiddenName : 'news.status',
											allowBlank : false,
											xtype : 'combo',
											editable : false,
											mode : 'local',
											triggerAction : 'all',
											store : [['0', '禁用'], ['1', '激活']],
											value : 1
										}, {
											fieldLabel : '顺序',
											name : 'news.sn',
											xtype : 'numberfield'
										}, {
											xtype : 'panel',
											title : '图片',
											name : 'NewsImageScanPanel',
											height : 311,
											width : 345,
											tbar : this.imagePanlbar,
											html : '<img style="border:0;" src="' + __ctxPath+ '/images/default_newsIcon.jpg" border="0"/>'
										}]
							}]
						});
				// 加载表单对应的数据
				if (this.newsId != null && this.newsId != 'undefined') {
					var fPanel = this.formPanel;
					fPanel.loadData({
						url : __ctxPath + '/info/getNews.do?newsId='+ this.newsId,
						root : 'data',
						preName : 'news',
						success : function(response,options) {
							var news=Ext.util.JSON.decode(response.responseText).data;
							fPanel.getCmpByName('sectionName').setValue(news.section.sectionName);
							fPanel.getCmpByName('news.createtime').setValue(new Date(getDateFromFormat(news.createtime,'yyyy-MM-dd HH:mm:ss')));
							if(news.expTime != null && news.expTime != ""){								
								fPanel.getCmpByName('news.expTime').setValue(new Date(getDateFromFormat(news.expTime,'yyyy-MM-dd HH:mm:ss')));
							}
							var displayPanel  = fPanel.getCmpByName('NewsImageScanPanel');
							if (news.subjectIcon != null && news.subjectIcon != '') {
								displayPanel.body.update('<img style="border:0;" src="' + __ctxPath+ '/attachFiles/'+ news.subjectIcon+ '" border="0"/>');
							}
						},
						failure : function(response,options) {
							Ext.ux.Toast.msg('编辑', '载入失败');
						}
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
					url : __ctxPath + '/info/saveNews.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('NewsGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
			},// end of save
			uploadImage : function(self){
				var displayPanel = self.getCmpByName('NewsImageScanPanel');
				var subjectIcon = self.getCmpByName('news.subjectIcon');
				var subjectIconId = self.getCmpByName('news.subjectIconId');
				var newsId = self.getCmpByName('news.newsId').getValue();
				var dialog = App.createUploadDialog({
					file_cat : 'info/notice',
					permitted_extensions : ['gif','jpg','png','bmp'],
					callback : function(data){
						subjectIcon.setValue(data[0].filePath);
						subjectIconId.setValue(data[0].fileId);
						displayPanel.body.update('<img style="border:0;"  src="'
							+ __ctxPath + '/attachFiles/' + data[0].filePath
							+ '" border="0"/>');
					}
				});
				if (subjectIcon.value != '' && subjectIcon.value != null && subjectIcon.value != 'undefined') {
					var msg = '再次上传需要先删除原有图片,';
					Ext.Msg.confirm('公告确认', msg + '是否删除？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								//url : __ctxPath + '/system/deleteFileAttach.do',
								url : __ctxPath + '/system/deleteFileIdFileAttach.do',
								method : 'post',
								params : {
									//filePath : subjectIcon.value
									photoId : subjectIconId.value
								},
								success : function(response, request) {
									var obj= Ext.util.JSON.decode(response.responseText);
									if(obj.success){
										//var newsId = Ext.getCmp('newsId').value;
										if (newsId != '' && newsId != null && newsId != 'undefined') {
											Ext.Ajax.request({
												url : __ctxPath + '/info/iconNews.do',
												method : 'post',
												params : {
													newsId : newsId
												},
												success : function() {
													subjectIcon.setValue('');
													// 改为默认图标
													displayPanel.body.update('<img style="border:0;"src="' + __ctxPath
																	+ '/images/default_newsIcon.jpg" border="0"/>');
													//Ext.getCmp('NewsGrid').getStore().reload();
													dialog.show('queryBtn');
												}
											});
										} else {
											subjectIcon.setValue('');
											// 改为默认图标
											displayPanel.body.update('<img style="border:0;" src="' + __ctxPath
															+ '/images/default_newsIcon.jpg" border="0"/>');
											dialog.show('queryBtn');
										}
									}else{
										Ext.ux.Toast.msg('提示信息', obj.msg);
									}
								}
							});
						}
					});
				} else {
					dialog.show('queryBtn');
				}
			},
			
			section : function(self){
				new SectionSelector({callback:function(sectionId,sectionName){
					this.close();
					self.getCmpByName('news.sectionId').setValue(sectionId);
					self.getCmpByName('sectionName').setValue(sectionName);
				}}).show();
			}

		});