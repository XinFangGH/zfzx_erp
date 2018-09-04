/**
 * @author
 * @createtime
 * @class NewsForm
 * @extends Ext.Window
 * @description News表单
 * @company 智维软件
 */
NewsForm = Ext.extend(Ext.Window, {
	imagePanlbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		NewsForm.superclass.constructor.call(this, {
			id : 'NewsFormWin',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 550,
			width : 1030,
			maximizable : true,
			iconCls : 'menu-news',
			title : '新闻详细信息',
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
		this.imagePanlbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-upload',
				text : '上传',
				scope : this,
				handler : this.uploadImage.createCallback(this)
			}, '-', {
				iconCls : 'btn-del',
				text : '删除',
				scope : this,
				handler : this.deleteImage.createCallback(this)
			}]
		});
		this.formPanel = new Ext.FormPanel({
			layout : 'hbox',
			frame : false,
			layoutConfig : {
				padding : '5',
				pack : 'start',
				align : 'middle'
			},
			defaults : {
				margins : '0 5 0 0'
			}, 
			keys : [{
				key : Ext.EventObject.ENTER,
				fn :  this.save,
				scope : this
			}],
			border : false,
			items : [{
				xtype : 'fieldset',
				title : '新闻内容',
				layout : 'form',
				flex : 2,
				labelWidth : 60,
				width : '60%',
				defaultType : 'textfield',
				// autoWidth : true,
				autoHeight : true,
				defaults : {
					width : '97%'
				},
				items : [{
					name : 'news.newsId',
					xtype : 'hidden',
					value : this.newsId == null ? '' : this.newsId
				}, {
					name : 'news.sectionId',
					xtype : 'hidden'
				}, {
					fieldLabel : '是否新闻',
					name : 'news.isNotice',
					xtype : 'hidden',
					value : 0
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
					fieldLabel : '新闻标题',
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
					height : 360,
					xtype : 'fckeditor',
					maxLength : 65535
				}]
			}, {
				xtype : 'fieldset',
				title : '其他新闻',
				flex : 1,
				layout : 'form',
				labelWidth : 60,
				defaultType : 'textfield',
				width : '34%',
				// autoWidth : true,
				autoHeight : true,
				defaults : {
					width : '97%'
				},
				items : [{
					fieldLabel : '新闻图片',
					name : 'news.subjectIcon',
					maxLength : 128,
					xtype : 'hidden'
				},{
					fieldLabel : '新闻图片Id',
					name : 'news.subjectIconId',
					maxLength : 128,
					xtype : 'hidden'
				} ,{
					fieldLabel : '创建时间',
					name : 'news.createtime',
					allowBlank : false,
					xtype : 'datefield',
					format : 'Y-m-d',
					value : new Date()
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
					height : 337,
					width : 345,
					tbar : this.imagePanlbar,
					html : '<img style="border:0;" src="' + __ctxPath + '/images/default_newsIcon.jpg" border="0"/>'
				}]
			}]
		});
		// 加载表单对应的数据
		if (this.newsId != null && this.newsId != 'undefined') {
			var fPanel = this.formPanel;
			fPanel.loadData({
				url : __ctxPath + '/info/getNews.do?newsId=' + this.newsId,
				root : 'data',
				preName : 'news',
				success : function(response, options) {
					var news = Ext.util.JSON.decode(response.responseText).data;
					fPanel.getCmpByName('sectionName').setValue(news.section.sectionName);
					fPanel.getCmpByName('news.createtime').setValue(new Date(getDateFromFormat(news.createtime, 'yyyy-MM-dd HH:mm:ss')));
					var displayPanel = fPanel.getCmpByName('NewsImageScanPanel');
					if (news.subjectIcon && news.subjectIcon != '') {
						displayPanel.body.update('<img style="border:0;" src="' + __ctxPath + '/attachFiles/' + news.subjectIcon + '" border="0"/>');
					}
				},
				failure : function(response, options) {
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
	uploadImage : function(self) {
		var displayPanel = self.getCmpByName('NewsImageScanPanel');
		var subjectIcon = self.getCmpByName('news.subjectIcon');
		var subjectIconId = self.getCmpByName('news.subjectIconId');
		var newsId = self.getCmpByName('news.newsId').getValue();
		var dialog = App.createUploadDialog({
					file_cat : 'info/news',
					permitted_extensions : ['jpg','gif','bmp','png'],
					callback : function(data) {
						subjectIcon.setValue(data[0].filePath);
						subjectIconId.setValue(data[0].fileId);
						displayPanel.body.update('<img style="border:0;"  src="' + __ctxPath + '/attachFiles/' + data[0].filePath + '" border="0"/>');
					}
				});
		if (subjectIcon.value != '' && subjectIcon.value != null && subjectIcon.value != 'undefined') {
			var msg = '再次上传需要先删除原有图片,';
			Ext.Msg.confirm('新闻确认', msg + '是否删除？', function(btn) {
				if (btn == 'yes') {
					// 删除图片
					Ext.Ajax.request({
						//url : __ctxPath + '/system/deleteFileAttach.do',
						url : __ctxPath + '/system/deleteFileIdFileAttach.do',
						method : 'post',
						params : {
							//filePath : subjectIcon.value
							photoId : subjectIconId.value
						},
						success : function() {
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
										displayPanel.body.update('<img style="border:0;"src="' + __ctxPath + '/images/default_newsIcon.jpg" border="0"/>');
										// Ext.getCmp('NewsGrid').getStore().reload();
										dialog.show('queryBtn');
									}
								});
							} else {
								subjectIcon.setValue('');
								// 改为默认图标
								displayPanel.body.update('<img style="border:0;" src="' + __ctxPath + '/images/default_newsIcon.jpg" border="0"/>');
								dialog.show('queryBtn');
							}
						}
					});
				}
			});
		} else {
			dialog.show('queryBtn');
		}
	},
	deleteImage : function(self) {
		var displayPanel = self.getCmpByName('NewsImageScanPanel');
		var subjectIcon = self.getCmpByName('news.subjectIcon');
		var subjectIconId = self.getCmpByName('news.subjectIconId');
		var newsId = self.getCmpByName('news.newsId').getValue();
		if (subjectIcon.getValue() != null && subjectIcon.getValue() != ''
				&& subjectIcon.getValue() != 'undefined') {
			var msg = '图片一旦删除将不可恢复,';
			Ext.Msg.confirm('确认信息', msg + '是否删除?', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
						//url : __ctxPath + '/system/deleteFileAttach.do',
						url : __ctxPath + '/system/deleteFileIdFileAttach.do',
						method : 'post',
						params : {
							//filePath : subjectIcon.getValue()
							photoId : subjectIconId.value
						},
						success : function() {
							if (newsId != '' && newsId != null && newsId != 'undefined') {
								Ext.Ajax.request({
									url : __ctxPath + '/info/iconNews.do',
									method : 'post',
									params : {
										newsId : newsId
									},
									success : function() {
										subjectIcon.setValue('');
										// 这里改为默认图标
										displayPanel.body.update('<img style="border:0;" width="48" height="48" src="'
											+ __ctxPath + '/images/default_newsIcon.jpg" border="0"/>');
										// Ext.getCmp('NewsGrid').getStore().reload();
									}
								});
							} else {
								subjectIcon.setValue('');
								// 这里改为默认图标
								displayPanel.body.update('<img style="border:0;" width="48" height="48" src="'
								+ __ctxPath + '/images/default_newsIcon.jpg" border="0"/>');
							}
						}
					});
				}
			});
		}// end if
		else {
			Ext.ux.Toast.msg('提示信息', '您还未增加图标.');
		}
	},
	section : function(self) {
		new SectionSelector({
			callback : function(sectionId, sectionName) {
				this.close();
				self.getCmpByName('news.sectionId').setValue(sectionId);
				self.getCmpByName('sectionName').setValue(sectionName);
			},
			isSingle : true
		}).show();
	}

});