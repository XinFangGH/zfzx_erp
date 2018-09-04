LeaseObjectManagementChPlace = Ext.extend(Ext.Window, {
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
				LeaseObjectManagementChPlace.superclass.constructor.call(this,
						{
							buttonAlign : 'center',
							title : '租赁标的信息',
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
							items : [this.LeaseObjectManageChPlaceInfo],
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
				this.LeaseObjectManageChPlaceInfo = new LeaseObjectManageChPlaceInfo(
						{
							objectId : this.id,
							isHidden:!this.editable,
							destPlace:this.destPlace
						});
				this.save = function() {
					var win = this;
					var eg = Ext.getCmp("leaseObjectChPlaceGrid");
					var vDates = getLeaseInsuranceGridDate(eg);
					if (vDates != "") {
						var arrStr = vDates.split("@");
						for (var i = 0; i < arrStr.length; i++) {
							var str = arrStr[i];
							var object = Ext.util.JSON.decode(str)
							if (object.destPlace == ""||typeof(object.destPlace)=="undefined"||null==object.destPlace) {
								Ext.ux.Toast.msg('操作信息', '转移地点不能为空，请填写转移地点');
								
								return;
							}
							if (object.moveDate == ""||typeof(object.moveDate)=="undefined"||null==object.moveDate) {
								Ext.ux.Toast.msg('操作信息', '转移时间不能为空，请填写转移时间');
								return;
							}else{
								object.moveDate = Ext.util.Format.date(new Date(object.moveDate),'Y-m-d');
								object.operationDate = Ext.util.Format.date(new Date(object.operationDate),'Y-m-d');
							}
						}
					}
					var gridPanel = this.gridPanel;
					Ext.Ajax.request({
						url : __ctxPath	+ '/creditFlow/leaseFinance/project/jsonSaveFlLeaseObjectManagePlace.do',
						method : 'POST',
						success : function(response, request) {
									Ext.ux.Toast.msg('操作信息', '保存成功!');
									win.destroy();
									gridPanel.getStore().reload()},
						failure : function(response,request) {
							Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
						},
						params : {
									"flLeaseObjectManagePlaceInfo" : vDates
								}
					});
				}

			}
		});
