//SystemAccountSettingInfoView
SystemAccountSettingInfoView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SystemAccountSettingInfoView.superclass.constructor.call(this, {
							id : 'SystemAccountSettingInfoView',
							title : "系统账户配置信息",
							region : 'center',
							layout : 'border',
							iconCls:"menu-flowManager",
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.topbar = new Ext.Toolbar({
					items : [{
			        	iconCls : 'btn-edit',
			        	text : '编辑',
						xtype : 'button',
						scope : this,
						handler : this.edit
							
					}]
				});
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					viewConfig: {  
		            	forceFit:false  
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'SystemAccountSettingInfoGrid',
					url : __ctxPath + "/creditFlow/creditAssignment/accountSetting/listObSystemaccountSetting.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'typeName','typeKey','mark', 'usedRemark'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '名称',
								dataIndex : 'typeName',
								width : '20%'
							},{
								header : '名称说明',
								dataIndex : 'mark',
								width : "35%"
							},  {
								header : '使用说明',
								dataIndex : 'usedRemark',
								width : "40%"
							}]
					});


			},
			edit : function(distinguish) {
				var selectRs = this.gridPanel.getSelectionModel().getSelections();
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					var record = selectRs[0];
					var newwindow=new EditSystemAccountSettingItem({
					     itemId:record.data.id,
					     refreshPanel:this.gridPanel
					});
					newwindow.show();
                    				
				}
		
	}
		});