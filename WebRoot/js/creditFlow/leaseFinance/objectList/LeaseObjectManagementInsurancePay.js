LeaseObjectManagementInsurancePay = Ext.extend(Ext.Window, {
			layout : 'anchor',
			anchor : '100%',
			isManageWin : false,
			editbale:false,
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(typeof(_cfg.editable)!='undefined'){
					this.editable = _cfg.editable;
				}
				this.initUIComponents();
				LeaseObjectManagementInsurancePay.superclass.constructor.call(this,
						{
							buttonAlign : 'center',
							title : '租赁保险理赔信息',
							iconCls : 'btn-add',
							width : (screen.width - 180) * 0.6,
							height : 160,
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
							items : [this.LeaseObjectManageInsurancePayInfo],
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
				this.LeaseObjectManageInsurancePayInfo = new LeaseObjectManageInsurancePayInfo(
						{
							objectId : this.id,
							isHidden:!this.editable,
							destPlace:this.destPlace
						});
				this.save = function() {
					var win = this;
					var eg = Ext.getCmp("leaseObjectInsurancePayGrid");
					var vDates = getLeaseObjectInsurancePayGridData(eg);
					if (vDates != "") {
						var arrStr = vDates.split("@");
						for (var i = 0; i < arrStr.length; i++) {
							var str = arrStr[i];
							var object = Ext.util.JSON.decode(str)
							if (object.insuranceCode == ""||typeof(object.insuranceCode)=="undefined"||null==object.insuranceCode) {
								Ext.ux.Toast.msg('操作信息', '保单编号不能为空，请填写保单标号');
								
								return;
							}
						}
					}
					var gridPanel = this.gridPanel;
					Ext.Ajax.request({
						url :  __ctxPath	+ '/creditFlow/leaseFinance/project/jsonSaveFlLeaseFinanceInsurancePay.do',
						method : 'POST',
						success : function(response, request) {
									Ext.ux.Toast.msg('操作信息', '保存成功!');
									win.destroy();
									gridPanel.getStore().reload()
								},
						failure : function(response,request) {
									Ext.ux.Toast.msg('操作信息', '保存失败,请正确填写表单信息!');
								},
						params : {
									"flLeaseObjectManageInsuranceInfo" : vDates
								}
					});
				}

			}
		});
