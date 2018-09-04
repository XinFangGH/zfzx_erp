/**
 * @author lisl
 * @class SlSupervisionManage
 * @extends Ext.Window
 * @description 贷后监管
 * @company 智维软件
 * @createtime:
 */
SlSupervisionManage = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlSupervisionManage.superclass.constructor.call(this, {
			title : '制定监管计划',
			layout : 'anchor',
			width : 590,
			height : 395,
			anchor : '100%',
			modal : true,
			maximizable : true,
			items : [this.gridPanel],
			buttonAlign : 'center',
			buttons : [{
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
				scope : this,
				handler : this.createRs
			}, '-', {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			}]
		});
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			region : 'center',
			tbar : this.topbar,
			height : 331,
			autoScroll : true,
			showPaging : false,
			clicksToEdit : 1,
			isShowTbar : true,
			hiddenCm : false,
			viewConfig : {
				forceFit : true
			},
			url : __ctxPath + "/supervise/listSlSupervisemanage.do",
			baseParams : {
				projectId : this.projectId
			},
			fields : [{
				name : 'superviseManageId',
				type : 'int'
			}, 'designSuperviseManagers', 'designSuperviseManagersName',
					'designSuperviseManageTime', 'designSuperviseManageRemark',
					'designee','designeeId', 'superviseManageStatus'],
			columns : [{
				header : 'superviseManageId',
				dataIndex : 'superviseManageId',
				hidden : true
			}, {
				header : 'designSuperviseManagers',
				dataIndex : 'designSuperviseManagers',
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
				renderer : function(value, metaData, record, rowIndex,
						colIndex, store) {
					if (typeof(value) == "string") {
						//var beginDate = new Date(value.replace(/-/g, "/"));
						return value;
					}
					var dateTime = Ext.util.Format.date(value, 'Y-m-d');
					return dateTime
				},
				editor : {
					xtype : 'datefield',
					format : 'Y-m-d'
				}
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
				if (this.gridPanel.store.getAt(e.row).data.verify == true) {
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
			$delGridRs({
			url : __ctxPath + '/supervise/multiDelSlSupervisemanage.do',
			grid : this.gridPanel,
			error : '确认删除吗,删除记录无法恢复?',
			idName : 'superviseManageId'
		});
	},
	save : function() {
		var grid = this.gridPanel;
		var data = this.getData();
		Ext.Ajax.request({
			url : __ctxPath
					+ "/supervise/saveSlSupervisemanage.do",
			params : {
				projectId : this.projectId,
				superviseMangeJsonData : data
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
	}
});