/**
 * @author lisl
 * @class SlVoteSignForm
 * @description 会签情况
 * @extends Ext.Window
 */
SlVoteSignPanel = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlVoteSignPanel.superclass.constructor.call(this, {
	//		title : '线上审贷会投票情况',
	
			modal : true,
			iconCls : '',
			autoScroll : true,
			maximizable : false,
			layout : 'fit',
			items : [this.gridPanel]
		});
	},
	initUIComponents : function() {
		

		// 显示会签情况
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			border : false,
			hiddenCm : true,
			showPaging : false,
			loadMask : true,
			autoHeight : true,
		    isShowTbar:false,
			showPaging:false,
			autoScroll : true,
			url : __ctxPath + "/creditFlow/signListCreditProject.do",
			baseParams : {
				'runId' : this.runId
			},
			fields : ['voteName', 'voteTime', 'isAgree', 'comments'],
			columns : [{
				header : '投票人',
				width : 50,
				dataIndex : 'voteName'
			}, {
				header : '投票时间',
				width : 130,
				dataIndex : 'voteTime'
			}, {
				header : '投票意见',
				align : "center",
				width : 65,
				dataIndex : 'isAgree',
				renderer : function(val) {
					if (val == null) {
						return '<span style="color:gray;">尚未投票</span>';
					} else if (val == 1) {
						return '<span style="color:green;">同意</span>';
					} else if (val == 2) {
						return '<span style="color:red;">否决</span>';
					} else {
						return '<span style="color:gray;">弃权</span>';
					}
				}
			}, {
				header : '审批意见',
				width : 267,
				dataIndex : 'comments',
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
		});
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
							html : '<b>投票时间：</b>'
					},{
						name : 'voteTime',
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
							html : '<b>投票意见：</b>'
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
							html : '<b>审批意见：</b>'
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
		this.formPanel.getCmpByName('voteTime').html = record.get('voteTime');
		var isAgree = record.get('isAgree');
		var isAgreeStr = '';
		if (isAgree == null) {
			isAgreeStr = '<span style="color:gray;">尚未投票</span>';
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