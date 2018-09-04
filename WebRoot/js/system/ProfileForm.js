var ProfileForm = function(_userId) {
	return this.setup(_userId);
};

ProfileForm.prototype.setup = function(userId) {
	var footToolbar = this.initFooterToolbar(userId);
	var profileform = new Ext.form.FormPanel({
		id : 'ProfileForm',
		title : '修改个人资料',
		closable : true,
		iconCls:"menu-flowManager",
		border : false, // 不要边框
		autoScroll : true,
		labelAlign : "right",
		layout : 'fit',
		tbar : footToolbar,
		defaultType : "textfield",
		url : __ctxPath + '/system/profileAppUser.do',
		reader : new Ext.data.JsonReader({
					root : 'data'
				}, [{
							name : 'appUser.userId',
							mapping : 'userId'
						}, {
							name : 'appUser.username',
							mapping : 'username'
						}, {
							name : 'appUser.fullname',
							mapping : 'fullname'
						}, {
							name : 'appUser.email',
							mapping : 'email'
						},

						{
							name : 'profileTitle',
							mapping : 'title'
						}, {
							name : 'appUser.position',
							mapping : 'position'
						}, {
							name : 'appUser.phone',
							mapping : 'phone'
						}, {
							name : 'appUser.mobile',
							mapping : 'mobile'
						}, {
							name : 'appUser.fax',
							mapping : 'fax'
						}, {
							name : 'appUser.address',
							mapping : 'address'
						}, {
							name : 'appUser.zip',
							mapping : 'zip'
						}, {
							name : 'appUser.photo',
							mapping : 'photo'
						}, {
							name : 'appUser.photoId',
							mapping : 'photoId'
						}]),
		items : [{
			xtype : 'panel',
			frame : false,
			autoWidth : true,
			autoHeight : true,
			border : false,
			layout : 'table',
			bodyStyle : "margin-top:5px;margin-left: 17%; background-color: transparent;",
			layoutConfig : {
				columns : 2
			},
		items :[{
			id : 'displayProfilePhoto',
			xtype : "panel",
			width : 230,
			rowspan : 2,
			style:'padding:3px 4px 25px 0px;',
			height : 320,
			title : "个人照片",
			html : '<img src="' + __ctxPath
					+ '/images/default_image_male.jpg"/>',
			tbar : new Ext.Toolbar({
				height : 30,
				items : [{
					text : '上传',
					iconCls : 'btn-upload',
					handler : function() {
						ProfileForm.uploadPhotoBtn(userId);
					}
				}, '-', {
					text : '删除',
					iconCls : 'btn-delete',
					handler : function() {
						ProfileForm.deleteUserPhoto(userId);
					}
				}]
			})
		}, {
			xtype : "panel",
			id : 'ProfileMustInfo',
			width : 305,
			height : 320,
			title : "个人资料",
			layout : 'form',
			style:'padding:3px 4px 25px 0px;',
			defaultType : "textfield",
			defaults : {
				width : 203
			},
			labelWidth : 60,
			labelAlign : "right",
			hideLabels : false,
			items : [{
						xtype : 'hidden',
						fieldLabel : '员工ID',
						name : 'appUser.userId',
						id : 'profile.userId'
					}, {
						fieldLabel : '登录账号',
						name : 'appUser.username',
						id : 'profile.username',
						readOnly : true
					}, {
						fieldLabel : '员工姓名',
						name : 'appUser.fullname',
						id : 'profile.fullname',
						allowBlank : false,
						blankText : '员工姓名不能为空!'
					}, {
						fieldLabel : 'E-mail',
						name : 'appUser.email',
						id : 'profile.email',
						vtype : 'email',
						allowBlank : false,
						blankText : '邮箱不能为空!',
						vtypeText : '邮箱格式不正确!'
					}, {
						fieldLabel : '性别',
						xtype : 'combo',
						hiddenName : 'appUser.title',
						id : 'profileTitle',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['1', '先生'], ['0', '女士']],
						value : '1',
						listeners : {
							select : function(combo, record, index) {
								var photo = Ext.getCmp('profile.photo');
								if (photo.value == ''
										|| photo.value == 'undefined'
										|| photo.value == null) {
									var display = Ext
											.getCmp('displayProfilePhoto');
									if (combo.value == '0') {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_female.jpg" />');
									} else {
										display.body
												.update('<img src="'
														+ __ctxPath
														+ '/images/default_image_male.jpg" />');
									}
								}
							}
						}
					},{
						fieldLabel : '家庭电话',
						name : 'appUser.phone',
						id : 'profile.phone',
						maxLength : 12,
						regex : /(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})/,
						regexText : '电话号码输入有误！'
					}, {
						fieldLabel : '移动电话',
						name : 'appUser.mobile',
						id : 'profile.mobile',
						maxLength : 12,
						regex : /(86)*0*1\d{10}/,
						regexText : '移动电话输入有误！'
					}, {
						fieldLabel : '传真',
						// xtype : 'numberfield',
						name : 'appUser.fax',
						id : 'profile.fax',
						maxLength : 12,
						regex : /[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+/,
						regexText : '传真号码输入有误！'
					}, {
						fieldLabel : '家庭住址',
						name : 'appUser.address',
						id : 'profile.address',
						maxLength : 64
					}, {
						fieldLabel : '邮编',
						xtype : 'numberfield',
						name : 'appUser.zip',
						id : 'profile.zip',
						maxLength : 6,
						regex : /[1-9]{1}(\d+){5}/,
						regexText : '邮政编号输入有误！'
					}, {
						filedLabel : '照片',
						xtype : 'hidden',
						id : 'profile.photo',
						name : 'appUser.photo'
					}, {
						filedLabel : '照片ID',
						xtype : 'hidden',
						id : 'profile.photoId',
						name : 'appUser.photoId'
					}]
		}]}]
	});

	profileform.getForm().load({
		deferredRender : false,
		url : __ctxPath + '/system/getAppUser.do',
		waitMsg : '正在载入数据...',
		success : function(form, action) {
			var photo = Ext.getCmp('profile.photo');
			var display = Ext.getCmp('displayProfilePhoto');
			var appUserTitle = Ext.getCmp('profileTitle');
			if (photo.value != '' && photo.value != null
					&& photo.value != 'undefined') {
				display.body.update('<img src="' + __ctxPath + '/attachFiles/'
						+ photo.value + '" width="100%" height="100%"/>');
			} else if (appUserTitle.value == '0') {
				display.body.update('<img src="' + __ctxPath
						+ '/images/default_image_female.jpg" />');
			}

		},
		failure : function(form, action) {
			Ext.ux.Toast.msg('编辑', '载入失败');
		}
	});

	return profileform;
};
// 初始化操作菜单
ProfileForm.prototype.initFooterToolbar = function(userId) {

	var toolbar = new Ext.Toolbar({
				id : 'ProfileFormToolbar',
				height : 30,
				items : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var userform = Ext.getCmp('ProfileForm');
						userform.getForm().submit({
							waitMsg : '正在提交用户信息',
							success : function(userform, o) {
								Ext.ux.Toast.msg('操作信息', '保存成功！');
								var userview = Ext.getCmp('AppUserGrid');
								if (userview != null) {// 假如员工列表不为空,则重载数据
									userview.getStore().reload({
										start : 0,
										limit : 25
									});
								}
							}
						});
					}

				}, '-', {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						var tabs = Ext.getCmp('centerTabPanel');
						tabs.remove('ProfileForm');
					}
				}, '-', {
					text : '修改密码',
					iconCls : 'btn-resetpassd',
					handler : function() {
						new ResetPasswordForm(userId);
					}
				}]
			});
	return toolbar;
};

ProfileForm.uploadPhotoBtn = function(userId) {
	var photo = Ext.getCmp('profile.photo');
	var photoId = Ext.getCmp('profile.photoId');
	var dialog = App.createUploadDialog({
		file_cat : 'myDesktop/profile',
		callback : uploadUserPhoto,
		permitted_extensions : ['jpg','png','bmp','gif']
	});
	if (photo.value != '' && photo.value != null && photo.value != 'undefined') {
		var msg = '再次上传需要先删除原有图片,';
		Ext.Msg.confirm('信息确认', msg + '是否删除？', function(btn) {
			if (btn == 'yes') {
				// 删除图片
				Ext.Ajax.request({
					//url : __ctxPath + '/system/deleteFileAttach.do',
					url : __ctxPath + '/system/deleteFileIdFileAttach.do',
					method : 'post',
					params : {
						//filePath : photo.values
						photoId : photoId.value
					},
					success : function(response, request) {
						var obj= Ext.util.JSON.decode(response.responseText);
						if(obj.success){
								if (userId != '' && userId != null && userId != 'undefined') {
								Ext.Ajax.request({
									url : __ctxPath + '/system/photoAppUser.do',
									method : 'post',
									params : {
										userId : userId
									},
									success : function() {
										photo.setValue('');
										var appUserTitle = Ext.getCmp('profileTitle');
										var display = Ext.getCmp('displayProfilePhoto');
										if (appUserTitle.value == 1) {
											display.body.update('<img src="' + __ctxPath + '/images/default_image_male.jpg" />');
										} else {
											display.body.update('<img src="' + __ctxPath + '/images/default_image_female.jpg" />');
										}
										dialog.show('queryBtn');
									}
								});
							} else {
								photo.setValue('');
								var profileTitle = Ext.getCmp('profileTitle');
								var display = Ext.getCmp('displayProfilePhoto');
								if (profileTitle.value == 1) {
									display.body.update('<img src="' + __ctxPath + '/images/default_image_male.jpg" />');
								} else {
									display.body.update('<img src="' + __ctxPath + '/images/default_image_female.jpg" />');
								}
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
};

ProfileForm.deleteUserPhoto = function(userId) {
	var photo = Ext.getCmp('profile.photo');
	var photoId = Ext.getCmp('profile.photoId');
	if (photo.value != null && photo.value != '' && photo.value != 'undefined') {
		var msg = '照片一旦删除将不可恢复,';
		Ext.Msg.confirm('确认信息', msg + '是否删除?', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/deleteFileIdFileAttach.do',
					method : 'post',
					params : {
						//filePath : photo.value
						photoId : photoId.value
					},
					success : function(response, request) {
						var obj= Ext.util.JSON.decode(response.responseText);
						if(obj.success){
								if (userId != '' && userId != null&& userId != 'undefined') {
								Ext.Ajax.request({
									url : __ctxPath + '/system/photoAppUser.do',
									method : 'post',
									params : {
										userId : userId
									},
									success : function() {
										photo.setValue('');
										var profileTitle = Ext.getCmp('profileTitle');
										var display = Ext.getCmp('displayProfilePhoto');
										if (profileTitle.value == 1) {
											display.body.update('<img src="' + __ctxPath + '/images/default_image_male.jpg" />');
										} else {
											display.body.update('<img src="' + __ctxPath + '/images/default_image_female.jpg" />');
										}
									}
								});
							} else {
								photo.setValue('');
								var profileTitle = Ext.getCmp('profileTitle');
								var display = Ext.getCmp('displayProfilePhoto');
								if (profileTitle.value == 1) {
									display.body.update('<img src="' + __ctxPath + '/images/default_image_male.jpg" />');
								} else {
									display.body.update('<img src="' + __ctxPath + '/images/default_image_female.jpg" />');
								}
							}
						}else{
							Ext.ux.Toast.msg('提示信息', obj.msg);
						}
					}
				});
			}
		});
	}// end if
	else {
		Ext.ux.Toast.msg('提示信息', '您还未增加照片.');
	}

};
/**
 * 上传照片
 * 
 * @param {}
 *            data
 */
function uploadUserPhoto(data) {
	if(data != null && data.length>0){		
		var photo = Ext.getCmp('profile.photo');
		var photoId = Ext.getCmp('profile.photoId');
		var display = Ext.getCmp('displayProfilePhoto');
		photo.setValue(data[0].filePath);
		photoId.setValue(data[0].fileId);
		display.body.update('<img src="' + __ctxPath + '/attachFiles/' + data[0].filePath + '"  width="100%" height="100%"/>');
	}
};
