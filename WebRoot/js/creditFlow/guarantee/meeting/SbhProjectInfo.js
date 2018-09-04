/**
 * @author
 * @createtime
 * @class SbhProjectInfo
 * @extends Ext.Window
 * @description SbhProjectInfo审保会集体决议-项目信息编辑窗口
 * @company 智维软件
 */
SbhProjectInfo = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.runId) != "undefined") {
			this.runId = _cfg.runId;
		}
		if (typeof(_cfg.taskId) != "undefined") {
			this.taskId = _cfg.taskId;
		}
		if (typeof(_cfg.countersignedTaskKey) != "undefined") {
			this.countersignedTaskKey = _cfg.countersignedTaskKey;
		}
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SbhProjectInfo.superclass.constructor.call(this, {
			id : 'SbhProjectInfoWin',
			layout : 'fit',
			items : this.gridPanel,
			modal : true,
			height : 420,
			width : 840,
			maximizable : true,
			title : this.title
			//title : '审保会集体决议会签情况'
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var isShowPositionAndVoteName = isGranted('_seeSbh_gt'+ this.projectStatus + this.bmStatus) == false && isGranted('_seeSomeSbh_gt'+ this.projectStatus + this.bmStatus) == true
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-readdocument',
				text : '查看详细',
				xtype : 'button',
				scope : this,
				handler : function() {
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
					this.detailInfo(record,isShowPositionAndVoteName);
				}
			}]
		});

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
				style : 'padding-left:20px',
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
				style : 'color:red',
				text : ''
			},		/* {xtype:'label',text : ''}, */{
				html : ""
			}, {
				html : '<br>'
			}]
		});

		this.gridPanel = new HT.GridPanel({
			border : false,
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
			tbar : this.topbar,
			rowActions : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			height : 361,
			sm : this.sm,
			url : __ctxPath + "/flow/findListByRunIdTaskSign.do?runId="+this.runId+"&taskId="+this.taskId+"&countersignedTaskKey="+this.countersignedTaskKey,
			/*url : __ctxPath + "/flow/listByRunIdTaskSign.do?runId="
					+ this.runId+"&taskId="+this.taskId,*/
			fields : [{
				name : 'dataId',
				type : 'int'
			}, 'voteId', 'voteName', 'voteTime', 'taskId', 'isAgree', 'runId','sbhTimes',
					'createTime', 'taskLimitTime','position','comments','activityName'],//, 'voteCounts', 'premiumRateComments',
					//'mortgageComments', 'assureTimeLimitComments','assureTotalMoneyComments'],
			columns : [{
				header : '职务',
				dataIndex : 'position',
				width : 95,
				scope : this,
				renderer : function(value, metadata, record, rowIndex, colIndex) {
				
					var createTime = record.data.createTime;
					var taskLimitTime = record.data.taskLimitTime;
					var voteCounts = record.data.voteCounts;
					this.infoPanel.items.get(0).setText('开始时间：' + createTime);
					this.infoPanel.items.get(1).setText('截至时间：' + taskLimitTime);
					this.infoPanel.items.get(2).setText('决议方式：投票(注：系统设置只需有' + voteCounts + '个人投票通过则流程往下流转,否则打回。)');
					if(isShowPositionAndVoteName) {
						return "未知";
					}else {
						return value;
					}
				}
			}, {
				header : '人员',
				width : 46,
				dataIndex : 'voteName',
				scope : this,
				renderer : function(v) {
					if(isShowPositionAndVoteName) {
						return "未知";
					}else {
						return v;
					}
				}
			}, {
				header : '投票意见',
				width : 60,
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
				width : 80,
				dataIndex : 'voteTime'
			},/* {
				header : '担保总额调整意见',
				width : 112,
				dataIndex : 'assureTotalMoneyComments'
			}, {
				header : '保费费率调整意见',
				width : 112,
				dataIndex : 'premiumRateComments'
			}, {
				header : '抵质押物调整意见',
				width : 112,
				dataIndex : 'mortgageComments'
			}, {
				header : '担保期限调整意见',
				width : 112,
				dataIndex : 'assureTimeLimitComments'
			}, */{
				header : '会签任务',
				dataIndex : 'activityName',
				width : 40
			},{
				header : '会签情况',
				width : 60,
				dataIndex : 'sbhTimes',
				renderer : function(value) {
					if(value!=null&&value!=""){
						if(value%2==1){//奇数
							return '<span style="color:blue;">第'+value+'次会签</span>';
						}else{
							return '第'+value+'次会签';
						}
					}
					return '第'+value+'次会签';
				}
			},{
				header : '总体意见',
				dataIndex : 'comments',
				width : 52
			}],
			listeners : {
				scope : this,
				'rowdblclick' : function(grid, rowindex, e) {
					var record = grid.getSelectionModel().getSelected();
					this.detailInfo(record,isShowPositionAndVoteName);
				}
			}
		});

	},// end of the initcomponents

	// 查看详细
	// seeRs : function() {
	// var selected = this.gridPanel.getSelectionModel().getSelected();
	// if (null == selected) {
	// Ext.ux.Toast.msg('状态', '请选择一条记录!');
	// }else{
	// var runId = selected.get('runId');
	// var dataId = selected.get('dataId');
	// var voteName = selected.get('voteName');
	// var position = selected.get('position');
	// new SbhProjectMoreInfo({
	// dataId : dataId,
	// voteName : voteName,
	// position : position,
	// isReadOnly : true
	// }).show();
	// }
	// },
	detailInfo : function(record,isShowPositionAndVoteName) {
		this.formPanel = new Ext.form.FormPanel({
			buttonAlign : 'center',
			bodyStyle : 'overflowX:hidden',
			frame : true,
			height : 400,
			layout : 'column',
			defaults : {
				anchor : '100%'
			},
			autoScroll : true,
			items : [{
				columnWidth : .7,
				layout : 'column',
				items : [{
					columnWidth : .15,
					layout : 'form',
					style : "margin-top : 3px",
					defaults : {
						layout : 'form'
					},
					items : [{
						xtype : 'label',
						labelWidth : 70,
						html : '<b>人员：</b>'
					}]
				}, {
					columnWidth : .1,
					layout : 'form',
					style : "margin-top : 3px",
					defaults : {
						layout : 'form'
					},
					items : [{
						name : 'voteName',
						textAlign : "left",
						width : 70,
						html : ""
					}]
				}, {
					columnWidth : .15,
					layout : 'form',
					style : "margin-top : 3px",
					defaults : {
						layout : 'form'
					},
					items : [{
						xtype : 'label',
						html : '<b>职务：</b>'
					}]
				}, {
					columnWidth : .1,
					layout : 'form',
					style : "margin-top : 3px",
					defaults : {
						layout : 'form'
					},
					items : [{
						name : 'position',
						html : ""
					}]
				}]
			},{
				columnWidth : .8,
				layout : 'column',
				items : [{
				columnWidth : .15,
				layout : 'form',
				style : "margin-top : 3px",
				defaults : {
					layout : 'form'
				},
				items : [{
					xtype : 'label',
					labelWidth : 60,
					html : '<b>投票意见：</b>'
				}]
			},{
				columnWidth : .1,
				layout : 'form',
				style : "margin-top : 3px",
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'isAgree',
					textAlign : "left",
					width : 70,
					html : ""
				}]
			}, {
				columnWidth : .15,
				layout : 'form',
				style : "margin-top : 3px",
				defaults : {
					layout : 'form'
				},
				items : [{
					xtype : 'label',
					html : '<b>处理时间：</b>'
				}]
			}, {
				columnWidth : .2,
				layout : 'form',
				style : "margin-top : 3px",
				defaults : {
					layout : 'form'
				},
				items : [ {
					name : 'voteTime',
					html : ""
				}]
			}
			]},{
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					layout : 'form'
				},
				style : "margin-top : 3px",
				items : [{
					xtype : 'label',
					html : '<b>会签任务：</b>'
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'activityName',
					html : ""
				}]
			},{
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					layout : 'form'
				},
				style : "margin-top : 3px",
				items : [{
					xtype : 'label',
					html : '<b>会签情况：</b>'
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'sbhTimes',
					html : ""
				}]
			},/* {
				layout : 'column',
				columnWidth : 1,
				border : false,
				defaults : {
					layout : 'form'
				},
				style : "margin-top : 3px",
				items : [{
					xtype : 'label',
					html : '<b>担保总额调整意见：</b>'
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'assureTotalMoneyComments',
					html : ""
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				border : false,
				style : "margin-top : 3px",
				defaults : {
					layout : 'form'
				},
				items : [{
					xtype : 'label',
					style : "margin-top : 20px",
					html : '<b>保费费率调整意见：</b>'
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'premiumRateComments',
					html : ""
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					xtype : 'label',
					style : "margin-top : 20px",
					html : '<b>抵质押物调整意见：</b>'
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'mortgageComments',
					html : ""
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					xtype : 'label',
					style : "margin-top : 20px",
					html : '<b>担保期限调整意见：</b>'
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'assureTimeLimitComments',
					html : ""
				}]
			}, */{
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					xtype : 'label',
					style : "margin-top : 20px",
					html : '<b>总体意见：</b>'
				}]
			}, {
				layout : 'column',
				columnWidth : 1,
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					name : 'comments',
					html : ""
				}]
			}]
		});
		this.detailWindow = new Ext.Window({
			title : '审保会集体决议情况详细信息',
			// layout : 'fit',
			width : 870,
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
		if(isShowPositionAndVoteName) {
			this.formPanel.getCmpByName('voteName').html = "未知";
		}else {
			this.formPanel.getCmpByName('voteName').html = record.get('voteName');
		}
		if(isShowPositionAndVoteName) {
			this.formPanel.getCmpByName('position').html = "未知";
		}else {
			this.formPanel.getCmpByName('position').html = record.get('position');
		}
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
		this.formPanel.getCmpByName('sbhTimes').html = "第"+record.get('sbhTimes')+"次会签";
		/*this.formPanel.getCmpByName('assureTotalMoneyComments').html = record.get('assureTotalMoneyComments');
		this.formPanel.getCmpByName('premiumRateComments').html = record.get('premiumRateComments');
		this.formPanel.getCmpByName('mortgageComments').html = record.get('mortgageComments');
		this.formPanel.getCmpByName('assureTimeLimitComments').html = record.get('assureTimeLimitComments');*/

		this.formPanel.doLayout();
		this.detailWindow.show();
	}
});