/**
 * @author
 * @class CensorMeetingCollectivityDecisionConfirm
 * @extends Ext.Panel
 * @description [CensorMeetingCollectivityDecisionConfirm]管理
 * @company 智维软件
 * @createtime:
 */
CensorMeetingCollectivityDecisionConfirm = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				/*if (typeof (_cfg.projectId) != "undefined") {
					this.projectId = _cfg.projectId;
				}*/
			   /* if (typeof (_cfg.businessType) != "undefined") {
					this.businessType = _cfg.businessType;
				}*/
				if (typeof (_cfg.runId) != "undefined") {
					this.runId = _cfg.runId;
				}
				if (typeof (_cfg.taskId) != "undefined") {
					this.taskId = _cfg.taskId;
				}
				if (typeof (_cfg.isEdit) != "undefined") {
					this.isEdit = _cfg.isEdit;
				}
				if (typeof (_cfg.countersignedTaskKey) != "undefined") {
					this.countersignedTaskKey = _cfg.countersignedTaskKey;
				}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CensorMeetingCollectivityDecisionConfirm.superclass.constructor.call(this, {
							region : 'center',
							layout : 'anchor',
							items : [
								this.infoPanel,
								this.gridPanel
							]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				
				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-readdocument',
						text : '查看详细',
						xtype : 'button',
						scope : this,
						//handler : this.seeShbMoreInfo
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
				
				/* this.expanderColumn = new Ext.ux.grid.RowExpander({
					enableCaching : false ,
				    tpl : new Ext.XTemplate(
					'<p><b>担保总额调整意见:</b> {assureTotalMoneyComments} &nbsp;&nbsp;&nbsp;&nbsp;</p>',
					'<p><b>保费费率调整意见:</b> {premiumRateComments} &nbsp;&nbsp;&nbsp;&nbsp;</p>',
					'<p><b>抵质押物调整意见:</b> {mortgageComments}</p>',
					'<p><b>担保期限调整意见:</b> {assureTimeLimitComments} &nbsp;&nbsp;&nbsp;&nbsp;</p>',
					'<p><b>总体意见:</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{comments}</p>',
					'<p><b>反馈意见:</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{feedbackComments}</p>'
				    )
				});*/
				
				this.sm = new Ext.grid.CheckboxSelectionModel({header:'序号'});
				
				this.infoPanel= new Ext.Panel({
					border:false,
					layout : {
						type : 'form',
						pack : 'left'
					},
					defaults : {
						margins : '10 10 0 100'
					},
					items:[{xtype:'label',style :'padding-left:15px',text : ''},{xtype:'label',style :'padding-left:20px',text : ''},{xtype:'label',style :'padding-left:30px',text : ''},{xtype:'label',style :'color:red',text : ''},/*{xtype:'label',text : ''},*/{html:""},{html : '<br>'}]
				});
				
				this.gridPanel = new HT.EditorGridPanel({
					border : false,
					hiddenCm : true,
					scope : this,
					tbar : this.topbar,
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
					//plugins : [this.expanderColumn],
					sm : this.sm,
					url : __ctxPath + "/flow/findListByRunIdTaskSign.do?runId="+this.runId+'&countersignedTaskKey='+this.countersignedTaskKey,
					fields : [{
								name : 'dataId',
								type : 'int'
							}, 'voteId','voteName','voteTime','taskId','isAgree','runId','createTime','taskLimitTime',
								'premiumRateComments','position','comments','voteCounts','activityName','sbhTimes'/*'mortgageComments','assureTimeLimitComments','assureTotalMoneyComments','feedbackComments'*/],
					columns : [//this.expanderColumn,
							/*{
								header : '职务',
								dataIndex : 'position',
								width : 100,
								scope : this,
								renderer : function(value,metadata,record,rowIndex,colIndex){
									
									var createTime=record.data.createTime;
									var taskLimitTime=record.data.taskLimitTime;
									var voteCounts=record.data.voteCounts;
									var taskSignType = record.data.taskSignType;
									this.infoPanel.items.get(0).setText('开始时间：'+createTime);
									this.infoPanel.items.get(1).setText('截至时间：'+taskLimitTime);
									if(taskSignType==1){
										this.infoPanel.items.get(2).setText('决议方式：投票');
										//this.infoPanel.items.get(2).setText('决议方式：投票(注：系统设置需有'+voteCounts+'个人投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
									}else{
										this.infoPanel.items.get(2).setText('决议方式：百分比');
										//this.infoPanel.items.get(2).setText('决议方式：百分比(注：系统设置需有'+voteCounts+'%投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
									}
									
									return value;
								}
							},*/{
								header : '人员',
								width : 60,
								dataIndex : 'voteName'
							},{
								header : '投票意见',
								width : 90,
								dataIndex : 'isAgree',
								renderer : function(val){
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
							},{
								header : '处理时间',
								width : 150,
								dataIndex : 'voteTime'
							}/*,{
								header : '反馈意见',
								width : 160,
								dataIndex : 'feedbackComments',
								editor :{
									xtype :'textfield'
								},
								editor :this.isEdit,
								renderer : this.doRenderer
							}*//*,{
								header : '担保总额调整意见',
								width : 160,
								dataIndex : 'assureTotalMoneyComments'
							},{
								header : '保费费率调整意见',
								width : 160,
								dataIndex : 'premiumRateComments'
							},{
								header : '抵质押物调整意见',
								width : 160,
								dataIndex : 'mortgageComments'
							},{
								header : '担保期限调整意见',
								width : 160,
								dataIndex : 'assureTimeLimitComments'
							},*/,{
								header : '会签任务',
								width : 50,
								dataIndex : 'activityName'
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
								width : 300,
								dataIndex : 'comments'
							}
						],
						listeners : {
							scope : this,
							afteredit : function(e) {
								var args;
								var value = e.value;
								var dataId = e.record.data['dataId'];
								if (e.originalValue != e.value) {//编辑行数据发生改变
									if(e.field == 'feedbackComments') {
										args = {'feedbackComments': value,'dataId': dataId}
									}
									Ext.Ajax.request({
										url : __ctxPath+'/creditFlow/guarantee/glFlownodeComments/updateGlFlownodeComments.do',
										method : 'POST',
										scope : this,
										success : function(response, request) {
											e.record.commit();
											//this.editorGridPanel.getStore().reload();
										},
										failure : function(response) {
											Ext.ux.Toast.msg('状态', '操作失败，请重试');
										},
										params : {
											dataId : dataId,
											feedbackComments : value
										}
									});
								}
							},
							'rowdblclick' : function(grid, rowindex, e) {
								var record = grid.getSelectionModel().getSelected();
								this.detailInfo(record);
							}/*,
							viewready:function(g){
				              for(var i in g.view.getRows()){
				                        //expander为插件
				                  this.expanderColumn.toggleRow(g.view.getRow(i));
				              }
				          }*/
						} 
				});
			},
			
	/*seeShbMoreInfo : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var runId = selected.get('runId');
			var dataId = selected.get('dataId');
			var voteName = selected.get('voteName');
			var position = selected.get('position');
			new SbhProjectMoreInfo({
				dataId : dataId,
				voteName : voteName,
				position : position,
				isReadOnly : true
			}).show();
		}
	}*/
			
	doRenderer : function(v){
		return (v==''||v==null)?'<font color=gray>点击编辑</font>':v;
	},
	
	detailInfo : function(record) {
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
			]}, /*{
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
				style : "margin-top : 3px",
				border : false,
				defaults : {
					layout : 'form'
				},
				items : [{
					xtype : 'label',
					style : "margin-top : 20px",
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
			},{
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
			}/*,{
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
					html : '<b>反馈意见：</b>'
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
					name : 'feedbackComments',
					html : ""
				}]
			}*/]
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
		/*this.formPanel.getCmpByName('assureTotalMoneyComments').html = record.get('assureTotalMoneyComments');
		this.formPanel.getCmpByName('premiumRateComments').html = record.get('premiumRateComments');
		this.formPanel.getCmpByName('mortgageComments').html = record.get('mortgageComments');
		this.formPanel.getCmpByName('assureTimeLimitComments').html = record.get('assureTimeLimitComments');
		this.formPanel.getCmpByName('feedbackComments').html = record.get('feedbackComments');*/
		this.formPanel.getCmpByName('comments').html = record.get('comments');
		this.formPanel.getCmpByName('activityName').html = record.get('activityName');
		this.formPanel.getCmpByName('sbhTimes').html = "第"+record.get('sbhTimes')+"次会签";
		
		this.formPanel.doLayout();
		this.detailWindow.show();
	}
});
