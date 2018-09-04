/**
 * @author
 * @class CensorMeetingCollectivityDecision
 * @extends Ext.Panel
 * @description [CensorMeetingCollectivityDecision]管理
 * @company 智维软件
 * @createtime:
 */
CensorMeetingCollectivityDecision = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		/*if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}*/
		/*if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}*/
		if (typeof(_cfg.runId) != "undefined") {
			this.runId = _cfg.runId;
		}
		if (typeof(_cfg.taskId) != "undefined") {
			this.taskId = _cfg.taskId;
		}
		/*if (typeof(_cfg.countersignedTaskKey) != "undefined") {
			this.countersignedTaskKey = _cfg.countersignedTaskKey;
		}*/
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CensorMeetingCollectivityDecision.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			items : [this.infoPanel, this.infoPanel2, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

		/*this.topbar = new Ext.Toolbar({
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
		});*/

		this.sm = new Ext.grid.CheckboxSelectionModel({
			header : '序号'
		});

		this.infoPanel = new Ext.Panel({
			border : false,
			layout : {
				type : 'form',
				pack : 'left'
			},
			defaults : {
				margins : '10 10 0 100'
			},
			items : [{
				xtype : 'label',
				style : 'padding-left:102px',
				text : ''
			}, {
				xtype : 'label',
				style : 'padding-left:30px',
				text : ''
			}, {
				xtype : 'label',
				style : 'padding-left:30px',
				text : ''
			}, {
				xtype : 'label',
				text : '决议方式：投票'
			},		/* {xtype:'label',text : ''}, */{
				html : ""
			}, {
				html : '<br>'
			}]
		});
		this.infoPanel2 = new Ext.Panel({
			border : false,
			layout : {
				type : 'form',
				pack : 'left'
			},
			defaults : {
				margins : '10 10 10 0'
			},
			labelAlign:'right',
			items : [{
				xtype : 'radiogroup',
				fieldLabel : '会签投票',
				name : 'signValue',
				columns : [80, 80, 100 ,80],
				items : [{
					boxLabel : '同意',
					name : 'signVoteTypeDefining',
					inputValue : 1,
					checked : true
				}, {
					boxLabel : '否决',
					name : 'signVoteTypeDefining',
					inputValue : 2
				}, {
					boxLabel : '有条件通过',
					name : 'signVoteTypeDefining',
					inputValue : 4
				}, {
					boxLabel : '打回',
					name : 'signVoteTypeDefining',
					inputValue : 3
				}/*, {
					boxLabel : '弃权',
					name : 'signVoteTypeDefining',
					inputValue : 0
				}*/]
			}, {
				xtype : "textarea",
				fieldLabel : "意见与说明",
				allowBlank : false,
				name : "comments",
				anchor : "100%"
			}]
		});

		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			//tbar : this.topbar,
			hidden : true,
			scope : this,
			// store : jStore,
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			isShowTbar : false,
			rowActions : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			sm : this.sm,
			url : __ctxPath + "/flow/getByRunIdTaskIdTaskSign.do?runId="+this.runId+'&taskId='+this.taskId+'&isNode=true',//+'&countersignedTaskKey='+this.countersignedTaskKey,
			fields : [{
				name : 'dataId',
				type : 'int'
			}, 'voteId', 'voteName', 'voteTime', 'taskId', 'isAgree', 'runId',
					'createTime', 'taskLimitTime','position',
					'comments', 'voteCounts','activityName'],
			columns : [{
				header : '人员',
				width : 60,
				dataIndex : 'voteName'
			}, {
				header : '职务',
				dataIndex : 'position',
				width : 100,
				scope : this,
				renderer : function(value, metadata, record, rowIndex, colIndex) {

					var createTime = record.data.createTime;
					var taskLimitTime = record.data.taskLimitTime;

					this.infoPanel.items.get(0).setText('开始时间：' + createTime);
					this.infoPanel.items.get(1).setText('截至时间：' + taskLimitTime);
					return value;
				}
			}, {
				header : '投票意见',
				width : 90,
				dataIndex : 'isAgree',
				renderer : function(val) {
					if (val == -1) {
						return '<span style="color:gray;">尚未投票</span>';
					} else if (val == 1) {
						return '<span style="color:green;">同意</span>';
					} else if (val == 2) {
						return '<span style="color:red;">否决</span>';
					} else if (val == 3) {
						return '<span style="color:red;">打回</span>';
					} else if (val == 4) {
						return '<span style="color:blue;">有条件通过</span>';
					} else {
						return '<span style="color:gray;">尚未投票</span>';
						//return '<span style="color:gray;">弃权</span>';
					}
				}
			}, {
				header : '处理时间',
				width : 100,
				dataIndex : 'voteTime'
			},{
				header : '会签任务',
				width : 50,
				dataIndex : 'activityName'
			},{
				header : '意见与说明',
				width : 300,
				dataIndex : 'comments'
			}],
			listeners : {
				scope : this,
				'rowdblclick' : function(grid, rowindex, e) {
					var record = grid.getSelectionModel().getSelected();
					this.detailInfo(record);
				}
			}
		});
	},

	detailInfo : function(record) {
		this.formPanel = new Ext.form.FormPanel({
			buttonAlign : 'center',
			bodyStyle : 'overflowX:hidden',
			frame : true,
			height : 400,
			layout : "anchor",
			anchor : '98%',
			defaults : {
				anchor : '98%'
				// 初始化组件
			},
			autoScroll : true,
			items : [{
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					layout : 'form',
					height : 30
				},
				items : [{
					xtype : 'label',
					width : 80,
					html : '<b>人员：</b>'
				}, {
					name : 'voteName',
					html : ""
				}, {
					xtype : 'label',
					width : 80,
					style : "margin-left : 20px",
					html : '<b>职务：</b>'
				}, {
					name : 'position',
					html : ""
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					layout : 'form',
					height : 30
				},
				items : [{
					xtype : 'label',
					width : 80,
					html : '<b>投票意见：</b>'
				}, {
					name : 'isAgree',
					html : ""
				}, {
					xtype : 'label',
					width : 80,
					style : "margin-left : 20px",
					html : '<b>处理时间：</b>'
				}, {
					name : 'voteTime',
					html : ""
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					layout : 'form',
					anchor : '98%'
				},
				items : [{
					xtype : 'label',
					width : 120,
					height : 17,
					style : "margin-top : 20px",
					html : '<b>会签任务：</b>'
				}, {
					name : 'activityName',
					html : ""
				}]
			},{
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					layout : 'form',
					anchor : '98%'
				},
				items : [{
					xtype : 'label',
					width : 120,
					height : 17,
					style : "margin-top : 20px",
					html : '<b>意见与说明：</b>'
				}, {
					name : 'comments',
					html : ""
				}]
			}]
		});
		this.detailWindow = new Ext.Window({
			title : '审保会集体决议情况详细信息',
			//layout : 'fit',
			width : 840,
			height : 430,
			closable : true,
			resizable : true,
			plain : true,
			border : false,
			modal : true,
			buttonAlign : 'right',
			minHeight : 400,
			minWidth : 580,
			defaults : {
				anchor : '95%'
			},
			bodyStyle : 'overflowX:hidden',
			items : [this.formPanel]
		});
		this.formPanel.getCmpByName('voteName').html = record.get('voteName');
		this.formPanel.getCmpByName('position').html = record.get('position');
		var isAgree = record.get('isAgree');
		var isAgreeStr = "";
		if (isAgree == -1) {
			isAgreeStr = '<span style="color:gray;">尚未投票</span>';
		} else if (isAgree == 1) {
			isAgreeStr = '<span style="color:green;">同意</span>';
		} else if (isAgree == 2) {
			isAgreeStr = '<span style="color:red;">否决</span>';
		} else if (isAgree == 3) {
			isAgreeStr = '<span style="color:red;">打回</span>';
		} else if (isAgree == 4) {
			isAgreeStr = '<span style="color:blue;">有条件通过</span>';
		} else {
			isAgreeStr = '<span style="color:gray;">尚未投票</span>';
			//isAgreeStr = '<span style="color:gray;">弃权</span>';
		}
		this.formPanel.getCmpByName('isAgree').html = isAgreeStr;
		this.formPanel.getCmpByName('voteTime').html = record.get('voteTime');
		this.formPanel.getCmpByName('comments').html = record.get('comments');
		this.formPanel.getCmpByName('activityName').html = record.get('activityName');

		this.formPanel.doLayout();
		this.detailWindow.show();
	}
});
