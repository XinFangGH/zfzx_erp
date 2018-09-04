Ext.ns('CarForm');
/**
 * @author
 * @createtime
 * @class CarForm
 * @extends Ext.Window
 * @description CarForm表单
 * @company 智维软件
 */
CarForm = Ext.extend(Ext.Window, {
	// 内嵌FormPanel
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CarForm.superclass.constructor.call(this, {
			layout : 'fit',
			id : 'CarFormWin',
			title : '车辆详细信息',
			iconCls : 'menu-car',
			width : 830,
			height : 450,
			minWidth : 829,
			minHeight : 449,
			maximizable : true,
			items : this.formPanel,
			border : false,
			modal : true,
			plain : true,
			buttonAlign : 'center',
			buttons : this.buttons,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.save,
				scope : this
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
		url : __ctxPath + '/admin/saveCar.do',
		layout : 'hbox',
		layoutConfig : {
            padding : '5',
            align : 'middle'
        },
        defaults : {
        	margins : '0 5 0 0'
        },
        anchor : '100%',
		id : 'CarForm',
		frame : false,
		formId : 'CarFormId',
		items : [{
			xtype : 'panel',
			title : '基本信息',
			layout : 'form',
			frame : false,
			width:300,
			height : 360,
			bodyStyle : 'padding:5px;',
			labelWidth : 100, 
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
			name : 'car.carId',
			id : 'carId',
			xtype : 'hidden',
			value : this.carId == null ? '' : this.carId
		}, {
			name : 'car.cartImage',
			id : 'cartImage',
			xtype : 'hidden'

		},{
			name : 'car.cartImageId',
			id : 'cartImageId',
			xtype : 'hidden'

		}, {
			fieldLabel : '车牌号码',
			name : 'car.carNo',
			id : 'carNo',
			allowBlank : false,
			xtype : 'textfield'
		}, {
			fieldLabel : '车辆类型',
			name : 'car.carType',
			id : 'carType',
			xtype : 'combo',
			mode : 'local',
			editable : true,
			allowBlank : false,
			triggerAction : 'all',
			store : [['1', '轿车'], ['2', '货车'], ['3', '商务车']]
		}, {
			fieldLabel : '发动机型号',
			name : 'car.engineNo',
			id : 'engineNo'
		}, {
			fieldLabel : '购买保险时间',
			name : 'car.buyInsureTime',
			id : 'buyInsureTime',
			editable : false,
			xtype : 'datefield',
			format : 'Y-m-d'
		}, {
			fieldLabel : '年审时间',
			name : 'car.auditTime',
			id : 'auditTime',
			editable : false,
			xtype : 'datefield',
			format : 'Y-m-d'
		}, {
			fieldLabel : '厂牌型号',
			name : 'car.factoryModel',
			allowBlank : false,
			id : 'factoryModel'
		}, {
			fieldLabel : '驾驶员',
			name : 'car.driver',
			allowBlank : false,
			id : 'driver'
		}, {
			fieldLabel : '购置日期',
			name : 'car.buyDate',
			id : 'buyDate',
			allowBlank : false,
			editable : false,
			xtype : 'datefield',
			format : 'Y-m-d'
		}, {
			fieldLabel : '当前状态',// 1=可用2=维修中0=报废
			hiddenName : 'car.status',
			id : 'status',
			xtype : 'combo',
			mode : 'local',
			allowBlank : false,
			editable : false,
			triggerAction : 'all',
			store : [['1', '可用'], ['2', '维修中'], ['0', '已报废']]
		},{
			fieldLabel : '备注',
			name : 'car.notes',
			xtype : 'textarea',
			anchor : '96%,96%',
			id : 'notes'
		}]
	}, {
		xtype : 'panel',
		id : 'carImageDisplay',
		frame : false,
		border : true,
		height : 360,
		html : '<img src="' + __ctxPath
				+ '/images/default_image_car.jpg" width="400" height="350"/>',
		tbar : new Ext.Toolbar({
			width : '100%',
			height : 30,
			items : [{
				text : '上传',
				iconCls : 'btn-upload',
				handler : this.upload,
				scope : this
			}, {
				text : '删除',
				iconCls : 'btn-delete',
				handler : this.detachFile,
				scope : this
			}]
		})
	}]
	});//end of the formPanel

	if (this.carId != null && this.carId != 'undefined') {
		this.formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/admin/getCar.do?carId=' + this.carId,
			method : 'post',
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var buyInsureTime = action.result.data.buyInsureTime;
				var auditTime = action.result.data.auditTime;
				var buyDate = action.result.data.buyDate;
				Ext.getCmp('buyInsureTime').setValue(new Date(getDateFromFormat(buyInsureTime,
				"yyyy-MM-dd HH:mm:ss")));
				Ext.getCmp('auditTime').setValue(new Date(getDateFromFormat(
						auditTime, "yyyy-MM-dd HH:mm:ss")));
				Ext.getCmp('buyDate').setValue(new Date(getDateFromFormat(
						buyDate, "yyyy-MM-dd HH:mm:ss")));
				var carImage = action.result.data.cartImage;
				var carPanel = Ext.getCmp('carImageDisplay');
				if (carImage != null && carImage != '' && carImage != 'undefind' && carPanel.body != null) {
					carPanel.body.update('<img src="' + __ctxPath
							+ '/attachFiles/' + carImage + '"  width="400" height="350"/>');
				}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	};//load formPanel
	
		this.buttons = 	[{
			text : '保存',
			iconCls : 'btn-save',
			handler : this.save,
			scope : this
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			handler : function() {
				Ext.getCmp('CarFormWin').close();
			}
		}];//end of the buttons 
	},//end of the initUICpomponents
	
	/**
	 * 保存
	 */
	save : function() {
		var fp = Ext.getCmp('CarForm');
		if (fp.getForm().isValid()) {
			fp.getForm().submit({
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功保存信息！');
					Ext.getCmp('CarGrid').getStore().reload();
					Ext.getCmp('CarFormWin').close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
						title : '操作信息',
						msg : '信息保存出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					Ext.getCmp('CarFormWin').close();
				}
			});
		}
	},
	
	/**
	 * 上传图片
	 */
	upload : function() {
		var photo = Ext.getCmp('cartImage');
		var photoId = Ext.getCmp('cartImageId');
		var dialog = App.createUploadDialog({
			file_cat : 'admin/carManager/car',
			callback : CarForm.uploadCarPhoto,
			permitted_extensions : ['jpg']
		});
		if (photo.getValue() != '' && photo.getValue() != null && photo.getValue() != 'undefined') {
			var msg = '再次上传需要先删除原有图片,';
			Ext.Msg.confirm('信息确认', msg + '是否删除？', function(btn) {
				if (btn == 'yes') {
					// 删除图片
					var carId = Ext.getCmp('carId').getValue();
					if (carId != null && carId != 'undefined') {
						Ext.Ajax.request({
							url : __ctxPath + '/admin/delphotoCar.do',
							method : 'post',
							params : {
								carId : carId
							},
							success : function() {
								var path = photo.value;
								photo.setValue('');
								var display = Ext.getCmp('carImageDisplay');
								display.body.update('<img src="' + __ctxPath
												+ '/images/default_image_car.jpg" width="100%" height="100%" />');
								Ext.Ajax.request({
									//url : __ctxPath + '/system/deleteFileAttach.do',
									url : __ctxPath + '/system/deleteFileIdFileAttach.do',
									method : 'post',
									params : {
										//filePath : path
										photoId : photoId.value
									},
									success : function() {
										dialog.show('queryBtn');
									}
								});
							}
						});
					}
				}
			});
		} else {
			dialog.show('queryBtn');
		}
	},
	
	
	/**
	 * 删除图片
	 */
	detachFile : function() {
		var photo = Ext.getCmp('cartImage');
		var photoId = Ext.getCmp('cartImageId');
		if (photo.value != null && photo.value != '' && photo.value != 'undefined') {
			var msg = '照片一旦删除将不可恢复,';
			Ext.Msg.confirm('确认信息', msg + '是否删除?', function(btn) {
				if (btn == 'yes') {
					Ext.ux.Toast.msg('提示信息', '请上传规格为400 X 350,或者此比例的照片.');
						var carId = Ext.getCmp('carId').getValue();
						if (carId != null && carId != 'undefined') {
							Ext.Ajax.request({
								url : __ctxPath + '/admin/delphotoCar.do',
								method : 'post',
								params : {
									carId : carId
								},
								success : function() {
									var path = photo.value;
									photo.setValue('');
									var display = Ext.getCmp('carImageDisplay');
									display.body.update('<img src="' + __ctxPath
										+ '/images/default_image_car.jpg" width="400" height="350" />');
									Ext.Ajax.request({
										//url : __ctxPath + '/system/deleteFileAttach.do',
										url : __ctxPath + '/system/deleteFileIdFileAttach.do',
										method : 'post',
										params : {
											//filePath : path
											photoId : photoId.value
										},
										success : function() {}
									});
								}
							});
						}
					}
				});
		}// end if
		else {
			Ext.ux.Toast.msg('提示信息', '您还未增加照片.');
		}
	}
});
CarForm.uploadCarPhoto = function(data) {
		var photo = Ext.getCmp('cartImage');
		var photoId = Ext.getCmp('cartImageId');
		var display = Ext.getCmp('carImageDisplay');
		photo.setValue(data[0].filePath);
		photoId.setValue(data[0].fileId);
		display.body.update('<img src="' + __ctxPath + '/attachFiles/'
				+ data[0].filePath + '"  width="100%" height="100%"/>');
	};
