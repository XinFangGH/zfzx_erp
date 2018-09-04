/**
 * @author lisl
 * @class SlProcessFormView
 * @description 流程表单
 * @extends Ext.Window
 */
SlProcessRunView = Ext.extend(Ext.Window, {
	isAutoHeight : true,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof(_cfg.isAutoHeight) != "undefined") {
			this.isAutoHeight = _cfg.isAutoHeight;
		}
		this.initUIComponents();
		SlProcessRunView.superclass.constructor.call(this, {
					title : '意见与说明记录',
					width : 800,
					height : 425,
					modal : true,
					iconCls : 'btn-advice',
					autoScroll : true,
					maximizable : true,
					layout : 'fit',
					items : [this.gridPanel]

				});
	},
	initUIComponents : function() {
		var safeLevel = 0;
		if(this.businessType == 'SmallLoan') {
			if(isGranted('seeAll_' + this.projectStatus)) {
				safeLevel = 3;
			}else if (isGranted('seeSome_' + this.projectStatus)) {
				safeLevel = 1;
			}
		} else if (this.businessType == 'Guarantee') {
			if(isGranted('seeAll_gt' + this.projectStatus + this.bmStatus)) {
				safeLevel = 3;
			}else if (isGranted('seeSome_gt' + this.projectStatus + this.bmStatus)) {
				safeLevel = 1;
			}
		}
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				scope:this,
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
			region : 'center',
			rowActions : false,
			tbar:this.topbar,
//			loadMask : true,
			store : this.store,
			defaults : {
				anchor : '96%'
			},
			url : __ctxPath + "/creditFlow/runListCreditProject.do",
			baseParams : {
				runId : this.runId,
				safeLevel : safeLevel,
				filterableNodeKeys : filterableNodeKeys
			},
			fields : ['activityName', 'creatorName','status', 'createtime', 'endtime','durTimes', 'comments', 'safeLevel'],
			border : false,
			columns : [{
					id : 'activityName',
					header : '节点名称',
					width : 113,
					sortable : true,
					dataIndex : 'activityName'
				}, {
					header : '执行人',
					width : 50,
					dataIndex : 'creatorName',
					renderer : function(val) {
						if (val == null || val == 'null') {
							return '<span style="color:red;">暂无执行人</span>';
						}else{
							return val;
						}
					}
				}, {
					header : '开始时间',
					width : 130,
					dataIndex : 'createtime'
				}, {
					width : 130,
					header : '结束时间',
					dataIndex : 'endtime'
				}, {
					header : '耗时',
					width : 145,
					dataIndex : 'durTimes',
					renderer : function(val) {
						// alert(val);
						var days = parseInt(val / 86400000);
						var hours = parseInt((val - days * 86400000)
								/ 3600000);
						var minute = parseInt((val - days * 86400000 - hours
								* 3600000)
								/ 60000);
						var second = (val - days * 86400000 - hours
								* 3600000 - minute * 60000)
								/ 1000;

						return '<span style="color:gray;">' + days
								+ '天' + hours + '小时' + minute + '分'
								+ second + '秒' + '</span>';

					}
				}, {
					header : '审批状态',
					width : 60,
					dataIndex : 'status',
					renderer : function(val) {
						if (val == 1) {
							return '<span style="color:green;">审批通过</span>';
						} else if (val == -1) {
							return '<span style="color:red;">驳回</span>';
						} else if (val == 2) {
							return '<span style="color:orange;">流程跳转</span>';
						}else if (val == 3) {
							return '<span style="color:red;">打回重做</span>';
						}else if (val == 4) {
							return '<span style="color:red;">任务追回</span>';
						}else if (val == 5) {
							return '<span style="color:orange;">任务换人</span>';
						} else if (val == 6) {
							return '<span style="color:orange;">项目换人</span>';
						} else if (val == 7) {
							return '<span style="color:orange;">项目终止</span>';
						}
						return "未审批";
					}
				} ,{
					header : '意见与说明',
					width : 70,
					dataIndex : 'comments',
					renderer : function(value,metadata,record,rowIndex,colIndex) {
						if(value==null||value==''||value=='null'){
							return '';
						}else{
							re = /u000a/g; // 创建正则表达式模式。
							r = value.replace(re, "<br>"); // 用 "<br>"替换 "\n"。
							return r;// 返回替换后的字符串。
						}
						/*var safeLevel=record.data.safeLevel;
						var activityName=record.data.activityName;
						if(value != null) {
							if(safeLevel==3&&"总裁审批"!=activityName&&"审保会决议确认"!=activityName){
								return '您无权查看该意见与说明!';
							}else{
								re = /u000a/g; // 创建正则表达式模式。
								r = value.replace(re, "<br>"); // 用 "<br>"替换 "\n"。
								return r;// 返回替换后的字符串。
								//return value;
							}
						}else {
							return '';
						}*/
					}
				}
				],
				listeners : {
					scope : this,
					'rowdblclick' : function(grid, rowindex, e) {
						var record = grid.getSelectionModel().getSelected();
						this.detailInfo(record);
					}
				} 
		});
		
		
	},//初始化组件
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
							html : '<b>节点名称：</b>'
					},{
						name : 'activityName',
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
							html : '<b>执行人：</b>'
					},{
						name : 'creatorName',
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
							html : '<b>开始时间：</b>'
					},{
						name : 'createtime',
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
							html : '<b>结束时间：</b>'
					},{
						name : 'endtime',
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
							html : '<b>耗时：</b>'
					},{
						name : 'durTimes',
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
							html : '<b>审批状态：</b>'
					},{
						name : 'status',
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
			width : 800,
			height : 425,
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
		this.formPanel.getCmpByName('activityName').html = record.get('activityName');
		var creatorName = record.get('creatorName');
		if(creatorName != null && creatorName != 'null') {
			this.formPanel.getCmpByName('creatorName').html = creatorName;
		}else {
			this.formPanel.getCmpByName('creatorName').html = "暂无执行人";
		}
		this.formPanel.getCmpByName('createtime').html = record.get('createtime');
		this.formPanel.getCmpByName('endtime').html = record.get('endtime');
		var durTimes = record.get('durTimes');
		var days = parseInt(durTimes / 86400000);
		var hours = parseInt((durTimes - days * 86400000) / 3600000);
		var minute = parseInt((durTimes - days * 86400000 - hours * 3600000) / 60000);
		var second = (durTimes - days * 86400000 - hours * 3600000 - minute * 60000) / 1000;
		this.formPanel.getCmpByName('durTimes').html = '<span style="color:gray;">' + days + '天' + hours + '小时' + minute + '分' + second + '秒' + '</span>';
		var status = record.get('status');
		var statusStr = '<span style="color:gray;">未审批</span>';
		if (status == 1) {
			statusStr = '<span style="color:green;">审批通过</span>';
		} else if (status == -1) {
			statusStr = '<span style="color:red;">驳回</span>';
		} else if (status == 2) {
			statusStr = '<span style="color:orange;">流程跳转</span>';
		}else if (status == 3) {
			statusStr = '<span style="color:red;">打回重做</span>';
		}else if (status == 4) {
			statusStr = '<span style="color:red;">任务追回</span>';
		}else if (status == 5) {
			statusStr = '<span style="color:orange;">任务换人</span>';
		}else if (status == 6) {
			statusStr = '<span style="color:orange;">项目换人</span>';
		}else if (status == 7) {
			statusStr = '<span style="color:orange;">项目终止</span>';
		}
		this.formPanel.getCmpByName('status').html = statusStr;
		var safeLevel = record.data.safeLevel;
		var activityName = record.data.activityName;
		var comments = record.data.comments;
		var commentsStr = "";
		if(comments != null) {
			re = /u000a/g; // 创建正则表达式模式。
			r = comments.replace(re, "<br>"); // 用 "<br>"替换 "\n"。
			commentsStr = r;// 返回替换后的字符串。
			/*if(safeLevel==3&&"总裁审批"!=activityName&&"审保会决议确认"!=activityName){
				commentsStr =  '您无权查看该意见与说明!';
			}else{
				re = /u000a/g; // 创建正则表达式模式。
				r = comments.replace(re, "<br>"); // 用 "<br>"替换 "\n"。
				commentsStr = r;// 返回替换后的字符串。
			}*/
		}
		this.formPanel.getCmpByName('comments').html = commentsStr;
		this.formPanel.doLayout();
		this.detailWindow.show();
	}
});