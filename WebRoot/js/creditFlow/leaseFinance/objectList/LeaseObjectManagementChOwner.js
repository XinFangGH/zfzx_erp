LeaseObjectManagementChOwner = Ext.extend(Ext.Window, {
			layout : 'anchor',
			anchor : '100%',
			isManageWin : false,
			editable:false,
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(typeof(_cfg.editable)!='undefined'){
					this.editable = _cfg.editable;
				}
				this.initUIComponents();
				LeaseObjectManagementChOwner.superclass.constructor.call(this,
						{
							buttonAlign : 'center',
							title : '所有权转移记录',
							iconCls : 'btn-add',
							width : (screen.width - 180) * 0.6,
							height : 200,
							constrainHeader : true,
							collapsible : true,
							frame : true,
							border : false,
							resizable : true,
							layout : 'fit',
							constrain : true,
							closable : true,
							modal : true,
							maximizable : true,
							items : [this.LeaseObjectManageChOwnerInfo],
							buttons : [{
										text : '保存',
										iconCls : 'btn-add',
										scope : this,
										handler : this.save
									}, {
										text : '关闭',
										iconCls : 'close',
										scope : this,
										handler : function() {
											this.close();
										}
									}]

						})
			},
			initUIComponents : function() {
				this.LeaseObjectManageChOwnerInfo = new LeaseObjectManageChOwnerInfo(
						{
							isHidden:!this.editable,
							objectId : this.id
						});
				this.save = function() {
					var win = this;
					var eg = Ext.getCmp("leaseObjectChOwnerGrid");
					var vDates = getLeaseOwnerGridDate(eg);
					if (vDates != "") {
						var arrStr = vDates.split("@");
						for (var i = 0; i < arrStr.length; i++) {
							var str = arrStr[i];
							var object = Ext.util.JSON.decode(str)
							if (object.oldOwner == ""||typeof(object.oldOwner)=="undefined"||null==object.oldOwner) {
								Ext.ux.Toast.msg('操作信息', '原所有权人不能为空，请填写原所有权人');
								
								return;
							}
							if (object.newOwner == ""||typeof(object.newOwner)=="undefined"||null==object.newOwner) {
								Ext.ux.Toast.msg('操作信息', '变更后所有权人不能为空，请填写变更后所有权人');
								return;
							}
						}
					}
					var gridPanel = this.gridPanel;
					Ext.Ajax.request({
						url : __ctxPath	+ '/creditFlow/leaseFinance/project/jsonSaveFlLeaseObjectManageOwner.do',
						method : 'POST',
						success : function(response, request) {
									Ext.ux.Toast.msg('操作信息', '保存成功!');
									win.destroy();
									gridPanel.getStore().reload()},
						failure : function(response,request) {
									Ext.ux.Toast.msg('操作信息', '保存失败,请正确填写表单信息!');
								},
						params : {
									"flLeaseObjectManageOwnerInfo" : vDates
								}
					});
				}

			}
		});
