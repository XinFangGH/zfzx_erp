/**
 * @author lisl
 * @class SlExecutiveOpinionForm
 * @description 主管意见记录
 * @extends Ext.Window
 */
SlExecutiveOpinionForm = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlExecutiveOpinionForm.superclass.constructor.call(this, {
			title : '主管意见记录',
			width : 580,
			height : 400,
			modal : true,
			iconCls : '',
			autoScroll : true,
			maximizable : false,
			layout : 'fit',
			items : [this.gridPanel]
		});
	},
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-readdocument',
				text : '查看详细',
				xtype : 'button',
				scope : this,
				handler : function() {
					var selRs = this.gridPanel.getSelectionModel()
							.getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}
					var record = this.gridPanel.getSelectionModel()
							.getSelected();
					this.detailInfo(record);
				}
			}]
		});
		this.gridPanel = new HT.GridPanel({
			border : false,
			showPaging : false,
			height : 340,
			autoScroll : true,
			loadMask : true,
			tbar : this.topbar,
			url : __ctxPath
					+ '/project/getExecutiveOpinionInfoSlSmallloanProject.do',
			fields : ['voteName', 'position', 'isAgree', 'comments'],
			baseParams : {
				projectId : this.projectId,
				businessType : this.businessType
			},
			columns : [{
				header : '执行人',
				width : 50,
				dataIndex : 'voteName'
			}, {
				header : '职位',
				width : 130,
				dataIndex : 'position',
				renderer : function(val) {
					if (val == null || "" == val) {
						return '<span style="color:gray;">无</span>';
					} else {
						return val;
					}
				}
			}, {
				header : '审核意见',
				dataIndex : 'isAgree',
				width : 65,
				renderer : function(val) {
					if (val == null || "" == val) {
						return '<span style="color:gray;">尚未审核</span>';
					} else if (val == 1) {
						return '<span style="color:gree;">同意</span>';
					} else if (val == 2) {
						return '<span style="color:red;">否决</span>';
					} else {
						return '<span style="color:gray;">弃权</span>';
					}
				}
			}, {
				header : '意见与说明',
				dataIndex : 'comments',
				width : 267,
				renderer : function(val) {
					if (val == null || "" == val) {
						return '<span style="color:gray;">无意见与说明</span>';
					} else {
						return val;
					}
				}
			}],
			listeners : {
				scope : this,
				'rowdblclick' : function(grid, rowindex, e) {
					var record = grid.getSelectionModel().getSelected();
					this.detailInfo(record);
				}
			}
		})
	},// 初始化组件
	detailInfo : function(record) {
		this.formPanel = new Ext.form.FormPanel({
			buttonAlign : 'center',
			bodyStyle : 'overflowX:hidden',
			frame : true,
			height : 396,
			autoScroll : true ,
	            items : [{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 30
					},
					items : [{
							xtype : 'label',
							width : 80,
							html : '<b>投票人：</b>'
					},{
						name : 'voteName',
						html : ""
					}]
	            },{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 30
					},
					items : [{
							xtype : 'label',
							width : 80,
							html : '<b>职位：</b>'
					},{
						name : 'position',
						html : ""
					}]
	            },{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 30
					},
					items : [{
							xtype : 'label',
							width : 80,
							html : '<b>审核意见：</b>'
					},{
						name : 'isAgree',
						html : ""
					}]
	            },{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%'
					},
					items : [{
							xtype : 'label',
							width : 80,
							html : '<b>意见与说明：</b>'
					},{
						name : 'comments',
						html : ""
					}]
	            }]
		});
		this.detailWindow = new Ext.Window({
			title: '意见与说明详细信息',
			layout : 'fit',
			width : 580,
			height : 400,
			closable : true,
			resizable : true,
			plain : true,
			border : false,
			modal : true,
			buttonAlign: 'right',
			minHeight : 400,       
			minWidth: 580,
			bodyStyle:'overflowX:hidden',
			items : [this.formPanel]
		});
		this.formPanel.getCmpByName('voteName').html = record.get('voteName');
		this.formPanel.getCmpByName('position').html = record.get('position');
		var isAgree = record.get('isAgree');
		var isAgreeStr = '';
		if (isAgree == null || "" == isAgree) {
			isAgreeStr = '<span style="color:gray;">尚未审核</span>';
		} else if (isAgree == 1) {
			isAgreeStr = '<span style="color:green;">同意</span>';
		} else if (isAgree == 2) {
			isAgreeStr = '<span style="color:red;">否决</span>';
		} else {
			isAgreeStr = '<span style="color:gray;">弃权</span>';
		}
		this.formPanel.getCmpByName('isAgree').html = isAgreeStr;
		this.formPanel.getCmpByName('comments').html = record.get('comments');
		this.formPanel.doLayout();
		this.detailWindow.show();
	}
});