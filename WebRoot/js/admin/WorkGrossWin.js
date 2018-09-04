var WorkGrossWin = function(assetsId) {
	this.assetsId = assetsId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'WorkGrossWin',
				title : '当前折算工作量',
				iconCls:'menu-assets',
				width : 300,
				border:false,
				region : 'west',
				items : [fp],
				buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('workCapacityForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									window.close();
									Ext.Msg.confirm("操作信息",
											"成功保存信息！需要再执行下一周期的折算吗？", function(
													btn) {
												if (btn == 'yes') {
													new WorkGrossWin(assetsId);
												}
											});
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									window.close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
			});
	// if (fp != null) {
	// window.show();
	// }
}

WorkGrossWin.prototype.setup = function() {
	var formPanel = new Ext.form.FormPanel({
				id : 'workCapacityForm',
				layout : 'form',
				url : __ctxPath + '/admin/depreciateDepreRecord.do',
				frame : false,
				items : [{
							xtype : 'hidden',
							name : 'ids',
							id : 'ids',
							value : this.assetsId
						}, {
							xtype : 'hidden',
							id : 'recordId',
							name : 'depreRecord.recordId'
						}, {
							fieldLabel : '开始折算时间',
							xtype : 'textfield',
							name : 'lastDepreDate',
							id : 'lastDepreDate',
							anchor : '100%',
							editable : false
						}, {
							fieldLabel : '折算时间',
							xtype : 'textfield',
							name : 'cruCalDate',
							anchor : '100%',
							id : 'cruCalDate',
							editable : false
						}, {
							layout : 'column',
							xtype : 'container',
							style : 'padding-left:0px;',
							items : [{
										xtype : 'label',
										style : 'padding-left:0px;',
										text : '该段时间工作量',
										width : 105
									}, {
										xtype : 'textfield',
										id:'workCapacity',
										name : 'depreRecord.workCapacity',
										anchor : '100%',
										allowBlank : false
									}, {
										xtype : 'label',
										id : 'unit'
									}]
						}]
			});

	if (this.assetsId != null && this.assetsId != ''
			&& this.assetsId != 'undefind') {
		this.loadData(this.assetsId);
	}
	return formPanel;

}

WorkGrossWin.prototype.loadData = function(assetsId) {
	Ext.Ajax.request({
				url : __ctxPath + '/admin/workDepreRecord.do',
				params : {
					ids : assetsId
				},
				method : 'post',
				success : function(response, options) {
					var result = Ext.util.JSON.decode(response.responseText);
					var cruCalTime = result.cruCalTime;
					var lastCalTime = result.lastCalTime;
					var defPerWorkGross = result.defPerWorkGross;
					if (!result.success) {
						var message = result.message;
						var win = Ext.getCmp('WorkGrossWin');
						if (win != null && win != 'undefind') {
							win.close();
							Ext.ux.Toast.msg('提示', message);
						}
					} else {
						var win = Ext.getCmp('WorkGrossWin');
						if (win != null && win != 'undefind') {
							win.show();
						}
						Ext.getCmp('cruCalDate').setValue(cruCalTime);
						Ext.getCmp('lastDepreDate').setValue(lastCalTime);
						if(defPerWorkGross!=null&&defPerWorkGross!='undefind'){
						   Ext.getCmp('workCapacity').setValue(defPerWorkGross);
						}
						Ext.getCmp('unit').setText(result.workGrossUnit);
					}
				},
				failure : function(response, options) {
					var result = Ext.util.JSON.decode(response.responseText);
					Ext.ux.Toast.msg('提示', result.message);
					return null;
				}
//				scope : this
			});
}