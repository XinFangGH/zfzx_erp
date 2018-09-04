var CompanyView = function() {
	return this.setup();
};

CompanyView.prototype.setup = function() {
	var toolbar = this.topbar();
	var formPanel = new Ext.form.FormPanel({
		id : 'CompanyView',
		closable : true,// 这个属性可以控制关闭form
		url : __ctxPath + '/system/addCompany.do',
		title : '公司信息',
		layout : 'form',
		tbar : toolbar,
		// frame : true,
		autoScroll : true,
		iconCls : 'menu-company',
		reader : new Ext.data.JsonReader({
					root : 'result'
				}, [{
							name : 'companyId',
							mapping : 'companyId'
						}, {
							name : 'companyNo',
							mapping : 'companyNo'
						}, {
							name : 'companyName',
							mapping : 'companyName'
						}, {
							name : 'companyDesc',
							mapping : 'companyDesc'
						}, {
							name : 'legalPerson',
							mapping : 'legalPerson'
						}, {
							name : 'companySetup',
							mapping : 'setup'
						}, {
							name : 'companyPhone',
							mapping : 'phone'
						}, {
							name : 'companyFax',
							mapping : 'fax'
						}, {
							name : 'companySite',
							mapping : 'site'
						}, {
							name : 'companyLogo',
							mapping : 'logo'
						}]),
		items : [{
					xtype : 'hidden',
					name : 'company.companyId',
					id : 'companyId'
				}, {
					xtype : 'container',
					// frame:true,
					labelAlign : 'top',
					border : false,
					style : 'padding-left:10%;padding-right:10%',
					layout : 'form',
					items : [{
								xtype : 'textfield',
								fieldLabel : '公司编号',
								name : 'company.companyNo',
								id : 'companyNo',
								anchor : '78%'
							}, {
								xtype : 'textfield',
								fieldLabel : '公司名称',
								name : 'company.companyName',
								id : 'companyName',
								allowBlank : false,
								anchor : '78%'
							}, {
								layout : 'column',
								border : false,
								anchor : '78%',
								items : [{
											layout : 'form',
											columnWidth : .5,
											border : false,
											style : 'padding-left:0px;',
											items : [{
														xtype : 'textfield',
														fieldLabel : '法人',
														name : 'company.legalPerson',
														id : 'legalPerson',
														anchor : '98%'
													}, {
														xtype : 'textfield',
														fieldLabel : '电话',
														name : 'company.phone',
														id : 'companyPhone',
														anchor : '98%'
													}]
										}, {
											layout : 'form',
											border : false,
											columnWidth : .5,
											items : [{
														fieldLabel : '成立时间',
														xtype : 'datefield',
														format : 'Y-m-d',
														name : 'company.setup',
														id : 'companySetup',
														anchor : '100%'
													}, {
														xtype : 'textfield',
														fieldLabel : '传真',
														name : 'company.fax',
														id : 'companyFax',
														anchor : '100%'
													}]
										}]
							}, {
								xtype : 'hidden',
								fieldLabel : 'Logo',
								name : 'company.logo',
								id : 'companyLogo'
							}, {
								xtype : 'hidden',
								fieldLabel : 'LogoId',
								name : 'company.logoId',
								id : 'companyLogoId'
							}, {
								xtype : 'container',
								fieldLabel : '公司主页',
								style : 'padding-left:0px;padding-bottom:3px;',
								layout : 'column',
								items : [{
											xtype : 'textfield',
											name : 'company.site',
											id : 'companySite',
											width : 300
										}, {
											xtype : 'button',
											text : '访问公司主页',
											handler : function() {
												var s = Ext
														.getCmp('companySite');
												var site = s.getValue().trim();
												if (site.indexOf('http://') == 0) {
													window.open(site, '_bank');
												} else {
													Ext.ux.Toast.msg('提示信息',
															'没写完整网址.');
												}
											}
										}, {
											xtype : 'label',
											width : 80,
											text : '以http://开头'
										}]
							}, {
								xtype : 'container',
								fieldLabel : 'Logo',
								style : 'padding-left:0px;padding-bottom:3px;',
								layout : 'column',
								items : [{
								       xtype:'container',
								       border:true,
								       style:'padding-left:0px;',
									   layout:'form',
								       height : 58,
								       items:[{
										xtype : 'panel',
										height : 55,
										width : 247,
										id : 'LogoPanel',
										autoScroll : true,
										html : '<img src="' + __ctxPath
												+ '/images/default_image_car.jpg" width="100%" height="100%"/>'
								       }]
								}, {
									border : false,
									xtype:'container',
									layout:'form',
									width:93,
									style:'padding-left:3px;',
									items : [{
										xtype : 'button',
										iconCls : 'btn-add',
										text : '上传LOGO',
										handler : function() {
											var photo = Ext.getCmp('companyLogo');
											var photoId = Ext.getCmp('companyLogoId');
											var dialog = App.createUploadDialog({
												file_cat : 'system/company',
												callback : uploadLogo,
												permitted_extensions : [ 'jpg', 'png']
											});
											if (photo.getValue() != '' && photo.getValue() != null && photo.getValue() != 'undefined') {
												var msg = '再次上传需要先删除原有图片,';
												Ext.Msg.confirm('信息确认', msg + '是否删除？', function(btn) {
													if (btn == 'yes') {
														Ext.Ajax.request({
															//url : __ctxPath + '/system/deleteFileAttach.do',
															url : __ctxPath + '/system/deleteFileIdFileAttach.do',
															method : 'post',
															params : {
																//filePath : photo.value
																photoId : photoId.value
															},
															success : function(response, request) {
																var obj= Ext.util.JSON.decode(response.responseText);
																if(obj.success){
																	photo.setValue('');
																	var display = Ext.getCmp('LogoPanel');
																	display.body.update('<img src="'+ __ctxPath
																					+ '/images/default_image_car.jpg" width="100%" height="100%" />');
																	var logoImage = document.getElementById('CompanyLogo');
																	logoImage.src = __ctxPath + '/images/ht-logo.png';
																	Ext.Ajax.request( {
																		url : __ctxPath + '/system/delphotoCompany.do',
																		method : 'post',
																		success : function() {
																			dialog.show('queryBtn');
																		}
																	});
																}
																
															}
														});
													}
												});
											} else {
												dialog.show('queryBtn');
											}
										}
									}, {
										xtype : 'button',
										text : '删除LOGO',
										iconCls : 'btn-delete',
										handler : function() {
											var photo = Ext.getCmp('companyLogo');
											var photoId = Ext.getCmp('companyLogoId');
											if (photo.value != null && photo.value != '' && photo.value != 'undefined') {
												var msg = 'LOGO一旦删除将不可恢复,';
												Ext.Msg.confirm('确认信息', msg + '是否删除?', function(btn) {if (btn == 'yes') {
														Ext.Ajax.request({
															//url : __ctxPath + '/system/deleteFileAttach.do',
															url : __ctxPath + '/system/deleteFileIdFileAttach.do',
															method : 'post',
															params : {
																//filePath : photo.value
																photoId : photoId.value
															},
															success : function() {
																Ext.Ajax.request({
																	url : __ctxPath + '/system/delphotoCompany.do',
																	method : 'post',
																	success : function() {
																		photo.setValue('');
																		var display = Ext.getCmp('LogoPanel');
																		display.body.update('<img src="' + __ctxPath
																						+ '/images/default_image_car.jpg" width="100%" height="100%" />');
																		var logoImage = document.getElementById('CompanyLogo');
																		logoImage.src = __ctxPath + '/images/ht-logo.png';
																	}
																});
															}
														});
													}
												});
											}// end if
											else {
												Ext.ux.Toast.msg('提示信息', '您还未增加照片.');
											}
										}
									}]

								}, {border:false,
									xtype : 'label',
									width : 150,
									html : '<a style="color:red;">请上传比例为247*55的图片,透明底的图片更佳</a>'
								}]
							}, {
								xtype : 'htmleditor',
								fieldLabel : '公司描述',
								name : 'company.companyDesc',
								id : 'companyDesc',
								height : 200,
								anchor : '78%'
							}]
				}]

	});
	Ext.Ajax.request({
		url : __ctxPath + '/system/checkCompany.do',
		success : function(result, request) {
			var res = Ext.util.JSON.decode(result.responseText);
			if (res.success) {
				formPanel.form.load({
							url : __ctxPath + '/system/listCompany.do',
							deferredRender : true,
							layoutOnTabChange : true,
							waitMsg : '正在载入数据...',
							success : function(form, action) {
								var logo = action.result.data.companyLogo;
								var logoPanel = Ext.getCmp('LogoPanel');
								if (logo != null && logo != ''
										&& logo != 'undefind'
										&& logoPanel.body != null) {
									logoPanel.body
											.update('<img src="'
													+ __ctxPath
													+ '/attachFiles/'
													+ logo
													+ '"  width="100%" height="100%"/>');
								}
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('编辑', '载入失败');
							}
						});
			} else {
				Ext.ux.Toast.msg('提示', '还没填写公司信息');
			}
		},
		failure : function(result, request) {

		}
	});

	function uploadLogo(data) {
		var photo = Ext.getCmp('companyLogo');
		var photoId = Ext.getCmp('companyLogoId');
		var display = Ext.getCmp('LogoPanel');
		photo.setValue(data[0].filePath);
		photoId.setValue(data[0].fileId);
		display.body.update('<img src="' + __ctxPath + '/attachFiles/'
				+ data[0].filePath + '"  width="100%" height="100%"/>');
		var logoImage = document.getElementById('CompanyLogo');
		logoImage.src = __ctxPath + '/attachFiles/' + data[0].filePath;
	};
	return formPanel;
};

CompanyView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'CompanyTopBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_CompanyEdit')) {
		toolbar.add(new Ext.Button({
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var formPanel = Ext.getCmp('CompanyView');
						if (formPanel.getForm().isValid()) {
							formPanel.getForm().submit({
								waitMsg : '正在修改公司信息',
								success : function(formPanel, o) {
									Ext.ux.Toast.msg('操作信息', '公司信息保存成功！');
									var companyName = Ext.getCmp('companyName')
											.getValue();
									Ext.getCmp('toolbarCompanyName')
											.setText(companyName + '办公协同管理系统');

								}
							});
						}
					}
				}));
	}
	toolbar.add(new Ext.Button({
				text : '关闭',
				iconCls : 'close',
				handler : function() {
					var tabPanel = Ext.getCmp('centerTabPanel');
					tabPanel.remove('CompanyView');
				}
			}));
	return toolbar;
};
