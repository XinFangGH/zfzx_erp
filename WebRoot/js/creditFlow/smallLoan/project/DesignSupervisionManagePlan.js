/**
 * @author lisl
 * @class DesignSupervisionManagePlan
 * @extends Ext.Window
 * @description 监管计划
 * @company 智维软件
 * @createtime:
 */
DesignSupervisionManagePlan = Ext.extend(Ext.Window, {
	isHidden : false,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}
		this.initUIComponents();
		DesignSupervisionManagePlan.superclass.constructor.call(this, {
			title : '贷后监管',
			layout : 'anchor',
			width : 590,
			height : 395,
			anchor : '100%',
			modal : true,
			maximizable : true,
			items : [this.gridPanel],
			buttonAlign : 'center',
			buttons : this.isHidden?[]:[{
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
	},
	initUIComponents : function() {
		var rowEditIndex = -1;
		var winObj = this;
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				hidden : this.isHidden,
				scope : this,
				handler : this.createRs
			} , new Ext.Toolbar.Separator({
				hidden : this.isHidden
			}), {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				hidden : this.isHidden,
				scope : this,
				handler : this.removeSelRs
			} , new Ext.Toolbar.Separator({
				hidden : this.isHidden
			}), {
				iconCls : 'btn-detail',
				text : '查看监管记录',
				xtype : 'button',
				scope : this,
				handler : this.detailGlobalSupervison
			}]
		});
		this.datafield=new Ext.form.DateField( {
			format : 'Y-m-d',
			allowBlank : false
		})
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			region : 'center',
			tbar : this.topbar,
			height : this.isHidden?362:331,
			autoScroll : true,
			showPaging : false,
			clicksToEdit : 1,
			isShowTbar : true,
			hiddenCm : false,
			viewConfig : {
				forceFit : true
			},
				url : __ctxPath + "/supervise/listGlobalSupervisemanage.do",
			baseParams : {
				projectId : this.projectId,
				businessType:this.businessType
			},
			fields : [{
				name : 'superviseManageId',
				type : 'int'
			}, 'designSuperviseManagers', 'designSuperviseManagersName',
					'designSuperviseManageTime', 'designSuperviseManageRemark',
					'designee','designeeId', 'superviseManageStatus','isProduceTask'],
			columns : [{
				header : 'superviseManageId',
				dataIndex : 'superviseManageId',
				hidden : true
			}, {
				header : 'designSuperviseManagers',
				dataIndex : 'designSuperviseManagers',
				hidden : true
			}, {
				header : 'isProduceTask',
				dataIndex : 'isProduceTask',
				hidden : true
			}, {
				header : '计划监管人',
				width : 80,
				dataIndex : 'designSuperviseManagersName',
				editor : {
					xtype : 'textfield',
					allowBlank : false,
					scope : this,
					listeners : {
						focus : function(obj) {
							var grid = winObj.get(0);
							new UserDialog({
								single : true,
								userIds : grid.store.getAt(rowEditIndex).data.designSuperviseManagers,
								userName : grid.store.getAt(rowEditIndex).data.designSuperviseManagersName,
								callback : function(uId, uname) {
									grid.store.getAt(rowEditIndex).data.designSuperviseManagers = uId;
									grid.store.getAt(rowEditIndex).data.designSuperviseManagersName = uname;
									grid.getView().refresh();
								}
							}).show();
						}
					}

				},
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					return value;
				}
			}, {
				header : '计划监管时间',
				width : 90,
				dataIndex : 'designSuperviseManageTime',
				renderer : ZW.ux.dateRenderer(this.datafield),
				editor :this.datafield
				
			}, {
				header : 'designeeId',
				dataIndex : 'designeeId',
				hidden : true
			}, {
				header : '指派人',
				width : 50,
				dataIndex : 'designee',
				renderer : function(v) {
					return "<font color='gray'>"+v+"</font>";
				}
			}, {
				header : '监管状态',
				width : 70,
				dataIndex : 'superviseManageStatus',
				renderer : function(v) {
					if (v == 0) {
						return "<font color='gray'>未执行</font>";
					} else if (v == 1) {
						return "<font color='gree'>已执行</font>";
					}
				}
			}, {
				header : '备注',
				width : 220,
				align : 'center',
				dataIndex : 'designSuperviseManageRemark',
				editor : {
					xtype : 'textfield'
				}
			}]
		});

		this.gridPanel.on({
			scope : this,
			beforeedit : function(e) {
				if (this.gridPanel.store.getAt(e.row).data.verify == true || this.gridPanel.store.getAt(e.row).data.superviseManageStatus ==1 || this.isHidden == true || this.gridPanel.store.getAt(e.row).data.isProduceTask == true) {
					rowEditIndex = -1;
					e.stopEvent();
				} else {
					rowEditIndex = e.row;
				}
			}
		});
	},
	createRs : function() {
		var gridadd = this.gridPanel;
		var storeadd = this.gridPanel.getStore();
		var keys = storeadd.fields.keys;
		var p = new Ext.data.Record();
		p.data = {};
		for (var i = 1; i < keys.length; i++) {
			if (keys[i] == 'designSuperviseManageTime') {
				p.data[keys[i]] = new Date();
			} else if (keys[i] == 'designee') {
				p.data[keys[i]] = currentUserFullName;
			} else if (keys[i] == 'superviseManageStatus') {
				p.data[keys[i]] = 0;
			} else if (keys[i] == 'designeeId') {
				p.data[keys[i]] = currentUserId;
			}else if (keys[i] == 'isProduceTask') {
				p.data[keys[i]] = false;
			} else {
				p.data[keys[i]] = null;
			}
		}
		var count = storeadd.getCount() + 1;
		gridadd.stopEditing();
		storeadd.addSorted(p);
	},
	getData : function() {
		var records = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var count = records.length; // 得到记录长度
		var data = '';
		if (count > 0) {
			for (var i = 0; i < count; i++) {
				var date= records[i].data.designSuperviseManageTime;
				if (typeof(date) == "string") {
					var beginDate = new Date(date.replace(/-/g, "/"));
					beginDate.format("Y-m-d");
					records[i].data.designSuperviseManageTime = beginDate;
				}
				data += Ext.util.JSON.encode(records[i].data) + '@';
			}
			data = data.substr(0, data.length - 1);
		}
		return data;
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
		removeSelRs : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		for(var i = 0;i<selRs.length;i++) {
			if(selRs[i].data.superviseManageStatus == 1) {
				Ext.ux.Toast.msg('操作信息', '监管计划已执行任务,不能删除！');
				return;
			}else if(selRs[i].data.isProduceTask == true) {
				Ext.ux.Toast.msg('操作信息', '监管计划已产生任务，不能删除！');
				return;
			}
		}
		$delGridRs({
			url : __ctxPath + '/supervise/multiDelGlobalSupervisemanage.do',
			grid : this.gridPanel,
			error : '确认删除吗,删除记录无法恢复?',
			idName : 'superviseManageId'
		});
	},
save : function() {
		var grid = this.gridPanel;
		var store= grid.getStore();
		if(store.getAt(store.getCount()-1).get('designSuperviseManagersName') == null) {
			Ext.ux.Toast.msg('操作信息', '计划监管人不能为空!');
			return false;
		}
		var data = this.getData();
		Ext.Ajax.request({
			url : __ctxPath
					+ "/supervise/saveGlobalSupervisemanage.do",
			params : {
				projectId : this.projectId,
				superviseMangeJsonData : data,
				businessType:this.businessType
			},
			scope : this,
			method : 'POST',
			success : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '保存监管计划成功!');
				grid.getStore().reload();
				this.close();
			},
			failure : function(fp, action) {
				Ext.ux.Toast.msg('操作信息', '操作失败，请联系管理员!');
			}
		});
	},
	detailGlobalSupervison : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		var record = this.gridPanel.getSelectionModel().getSelected();
		var superviseManageId = record.get('superviseManageId');
		var superviseManageStatus = record.get('superviseManageStatus');
		if(superviseManageId != null && superviseManageStatus == 1) {
			new GlobalSupervisonRecordView({projectId : this.projectId,superviseManageId : superviseManageId,businessType:this.businessType,sprojectId:this.sprojectId}).show();
		}else {
			Ext.ux.Toast.msg('操作信息', '监管计划尚未执行，不能查看监管记录！');
		}
	}
});