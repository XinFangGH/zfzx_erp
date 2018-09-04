/**
 * @author
 * @createtime
 * @class PlPersionDirBidPlanView
 * @extends Ext.Window
 * @description PlPersionDirBidPlanView表单
 * @company 互融软件
 */
 
PlPersionDirBidPlanView = Ext.extend(Ext.Panel, {
	// 构造函数
	querysql:"&loanId=underline",
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="onlineopen") {//查询线上借款生成的直投项目
			this.querysql="&loanId=online";
		}else if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="all") {//查询全部的直投项目
			this.querysql="";
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlPersionDirBidPlanView.superclass.constructor.call(this, {
					id : 'PlPersionDirBidPlanView'+this.state+this.proType,
					layout : 'border',
					items : [this.searchPanel,this.gridPanel],
					modal : true,
					height : 550,
					autoWidth : true,
					maximizable : true,
					iconCls : 'btn-tree-team30',
				//	title : this.titlePrefix,
					buttonAlign : 'center'

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var data=[['招标中',1],['已齐标',2],['已流标',3],['已到期',4],['起息办理中',6],['还款中',7],['展期中',9],['已完成',10],['已关闭',-1]];
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
			     //   id:'UPlanBidPublishSearchPanel'+this.keystr,
					layout : 'form',
					region : 'north',
					border : false,
					height : 65,
					anchor : '70%',
						items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						items : [{
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '招标编号',
								name : 'bidProNumber',
								xtype : 'textfield'

							}]
						},{
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '招标名称',
								name : 'bidProName',
								xtype : 'textfield'

							}]
						},{
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							hidden:this.state==11?false:true,
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
							xtype:'combo',
							hiddenName : 'bstate',
							anchor : '94%',
							fieldLabel:'招标状态',
							mode : 'local',
							forceSelection : true, 
							displayField : 'typeValue',
							valueField : 'typeId',
							editable : false,
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({
								data : data,
								fields:['typeValue','typeId']
							})
						}]
						},/*{
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							hidden:true,
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								name : 'proType',
								xtype : 'textfield',
								value:this.proType

							}]
						},*/{
							columnWidth : .1,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:50px;',
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
						}]},{
							columnWidth : .1,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
						}]
					}]
				});// end of searchPanel
		this.topbar = new Ext.Toolbar({
					items : [/*{
								iconCls : 'btn-add',
								text : '发布',
								xtype : 'button',
								hidden:(this.Q_state_N_EQ.toString()!=""&&this.Q_state_N_EQ==0)?false:true,
								scope : this,
								handler : this.publish
							}, {
								iconCls : 'tipsClose',
								text : '关闭',
								xtype : 'button',
								hidden:(this.Q_state_N_EQ==-1||this.Q_state_N_EQ==6||this.Q_state_N_EQ==2||this.Q_state_N_EQ==3 ||this.Q_state_N_EQ==7 ||this.Q_state_N_EQ==10||this.Q_state_N_EQ=='' || this.isAutoTender==1)?true:false,
								scope : this,
								handler : this.bidclose
							}, {
								iconCls : 'tipsClose',
								text : '关闭',//此关闭按钮只针对已流标的，只改变标的状态，不调用第三方接口
								xtype : 'button',
								hidden:(this.Q_state_N_EQ==3?false:true),
								scope : this,
								handler : this.close
							},*/ {
								iconCls : 'btn-team1',
								text : '启动办理流程',
								hidden:this.state==13?false:true,
								xtype : 'button',
								scope : this,
								handler : this.startRs
							},/*{
								iconCls : 'btn-add',
								text : '自动投标',
								xtype : 'button',
								hidden:this.Q_state_N_EQ==1||this.Q_state_N_EQ==23?false:true,
								scope : this,
								handler : this.bidAuto
							}, {
								iconCls : 'btn-add',
								text : '开启',
								xtype : 'button',
								hidden:this.Q_state_N_EQ==-1?false:true,
								scope : this,
								handler : this.bidopen
							},*/ {
								iconCls : 'btn-flow',
								text : '流标',
								xtype : 'button',
								hidden:this.state==12?false:true,
								scope : this,
								handler : this.bidFailed
							}, /*{
								iconCls : 'btn-add',
								text : '完成',
								hidden:(this.Q_state_N_EQ==7)?false:true,
								xtype : 'button',
								scope : this,
								handler : this.fininsh
							},*/'->',{
								iconCls : 'btn-detail',
								text : '查看招标网页',
								xtype : 'button',
								//hidden:(this.Q_state_N_EQ.toString()!=""&&this.Q_state_N_EQ==0)?false:true,
								scope : this,
								handler : this.preview
							}, {
								iconCls : 'btn-detail',
								text : '查看投标详情',
								xtype : 'button',
								scope : this,
								handler : this.seeRs
							}/*,{
									iconCls : 'btn-detail',
									text : '查看还款计划表',
									xtype : 'button',
									hidden:(this.Q_state_N_EQ==7 ||this.Q_state_N_EQ==10)?false:true,
									//hidden : isGranted('PlbidplanLoanManager_seeIntentInfo' + this.state)? false: true,
									scope : this,
									handler : this.seeCusterFundIntent
								},{
								iconCls : 'btn-xls',
								text : '导出投标列表',
								xtype : 'button',
								hidden:(this.Q_state_N_EQ==-1||this.Q_state_N_EQ==2||this.Q_state_N_EQ==3||this.Q_state_N_EQ==4 ||this.Q_state_N_EQ==6 ||this.Q_state_N_EQ==7 ||this.Q_state_N_EQ==10)?false:true,
								scope : this,
								handler : this.toExcel
							}*/]
				});
		 var url=__ctxPath+ "/creditFlow/financingAgency/bidListPlBidPlan.do?state="+this.state+"&proType="+this.proType+this.querysql;
		var growthColumn = new Ext.ux.grid.ProgressColumn({
					header : "招标进度",
					dataIndex : 'bidSchedule',
					align:'center',
					hidden : (this.Q_state_N_EQ==23?false:true),
					width : 100,
					textPst : '%',
					colored : true
				
				});
				 var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			//id : 'PlPersionDirBidPlanViewGrid'+this.state,
			url : url,
			plugins : [growthColumn,summary],
			fields : [{
						name : 'bidId',
						type : 'int'
					}, 'proId', 'bidProName', 'bidProNumber',
					'bidMoney', 'bidMoneyScale', 'startMoney', 'riseMoney',
					'createtime', 'updatetime', 'state', 'startInterestType',
					'bidStartTime','prepareSellTime', 'publishSingeTime', 'bidEndTime','proId','moneyPlanId','proType',
					'bidSchedule','proName',"typeName","isStart",'yearInterestRate'],
			columns : [{
						header : 'bidId',
						align:'center',
						dataIndex : 'bidId',
						hidden : true
					}, {
						header : '招标名称',
						width : 200,
						summaryRenderer : totalMoney,
						dataIndex : 'bidProName'
					}, {
						header : '招标编号',
						width : 170,
						summaryType : 'count',
						dataIndex : 'bidProNumber'
					}, {
						header : '借款项目名称',
						width : 250,
						dataIndex : 'proName'
					}, {
						header : '起息模式',
						dataIndex : 'typeName'
					}, {
						header : '招标金额',
						dataIndex : 'bidMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元"
						}
					}, {
						header : '年利率',
						dataIndex : 'yearInterestRate',
						align : 'right',
						renderer : function(v){
							return v+'%'
						}
					},growthColumn/*, {
						header : '起息日类型',
						dataIndex : 'startInterestType',
						renderer : function(data, metadata, record, rowIndex,
								columnIndex, store) {
							if (data == 0)
								return 'T(投标日+1天)';
							if (data == 1)
								return 'T(招标截止日+1天)';
							if (data == 2)
								return 'T(满标日+1天)';
						}
					}*/, {
						header : '预售公告时间',
						align:'center',
						dataIndex : 'prepareSellTime'
					}, {
						header : '开始投标时间',
						align:'center',
						dataIndex : 'publishSingeTime'
					}, {
						header : '招标截至时间',
						align:'center',
						dataIndex : 'bidEndTime',
						renderer : function(value){
							var date2 = new Date().format("Y-m-d h:i:s");
							if(value<date2){
								return "<font style=\"color:red;\">"+value+"</font>";
							}else{
								return value;
							}
						}
					},{ 
						header:'招标状态',
						dataIndex:'state',
						align:'center',
					//	hidden:this.Q_state_N_EQ.toString()==""?false:true,
						renderer:function(value){
							if(null==value){
							
							}else if(value==-1){
								return "已关闭";
							}else if(value==1){
								return "招标中";
							}else if(value==2){
								return "已齐标";
							}else if(value==3){
								return "已流标";
							}else if(value==4){
								return "已到期";
							}else if(value==5){
							    return "起息办理中";
							}else if(value==6){
							    return "起息办理中";
							}else if(value==7){
							    return "还款中";
							}else if(value==9){
							    return "展期中";
							}else if(value==10){
							    return "已完成";
							}
						}
					}]
				// end of columns
		});

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	seeRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do",
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					var  bidPlanInfoForm=new PlBidPlanInfoForm({
						bidPlanId : record.data.bidId,
						projectId:record.data.proId,
						fundProjectId:record.data.moneyPlanId,
						proType:record.data.proType,
						bidProName:record.data.bidProName,
						oppositeType:obj.data.oppositeType,
						businessType:'SmallLoan',
						subType : this.subType
					})
					
					 contentPanel.add(bidPlanInfoForm);
					contentPanel.activate('PlBidPlanInfoForm_'+record.data.bidId);
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				},
				params : {
					projectId:record.data.moneyPlanId
				}
			});
			
		}	
	},
	seeCusterFundIntent: function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do",
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					/*var  bidPlanInfoForm=new PlBidPlanInfoCusterFundIntent({
						bidPlanId : record.data.bidId,
						projectId:record.get('bpPersionDirPro.proId'),
						fundProjectId:record.get('bpPersionDirPro.moneyPlanId'),
						proType:record.data.proType,
						bidProName:record.data.bidProName,
						oppositeType:obj.data.oppositeType,
						businessType:'SmallLoan'
					})*/
					var SlFundIntentViewVM = new CusterFundIntentView({
						projectId :record.data.proId,
						bidPlanId:record.data.bidId,
						object : this.projectInfoFinance,
						bidPlanFinanceInfo:this.BidPlanFinanceInfo,
						isFactHidden:false,
						businessType : 'SmallLoan',
						isHiddenTitle : true,
						isHiddenautocreateBtn:true,
						isHiddenExcel:true,
						isHaveLending:'yes',
						isHiddenAddBtn : true,// 生成
						isHiddenDelBtn : true,// 删除
						isHiddenCanBtn : true,// 取消
						isHiddenResBtn : true,// 还原
						isHiddenResBtn1 : true,// 手动对账
						isHiddenseeqlideBtn : true,// 查看流水单项订单
						isHiddenseesumqlideBtn : true,
						isHidden1:true
					});
					var Win = new Ext.Window({
						title : '还款计划',
						width : 1000,
						height : 400,
						buttonAlign :'center',
						maximizable : true,
						autoScroll : true,
						modal : true,
						items :[SlFundIntentViewVM]
					});
					Win.show();
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				},
				params : {
					projectId:record.data.moneyPlanId
				}
			});
			
		}	
	
	},
	startRs:function(){
		var subType=this.subType;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
		}
		if("1"==record.data.isStart){
			Ext.ux.Toast.msg('操作信息', '已启动任务!');
			return;
		}
		Ext.Msg.confirm('信息确认', '是否启动起息办理流程', function(btn) {
			 if (btn == 'yes') {
				Ext.Ajax.request( {
					url : __ctxPath +"/creditFlow/financingAgency/startMatchingFundsFlowPlBidPlan.do",
					params : {bidId:record.data.bidId,subType:subType},//
					method : 'POST',
					success : function(response) {
						var obj=Ext.util.JSON.decode(response.responseText)
						if(typeof(obj.msg)!='undefined' && null!=obj.msg){
							Ext.Msg.alert('操作信息',obj.msg);
							return;
						}
						Ext.ux.Toast.msg('操作信息','启动成功');
						
							var contentPanel = App.getContentPanel();
						var formView = contentPanel.getItem('ProcessNextForm' + obj.taskId);
						if (formView == null) {
							formView = new ProcessNextForm({
								taskId : obj.taskId,
								activityName : obj.activityName,
								projectName : obj.projectName,
								agentTask : true
							});
							contentPanel.add(formView);
						}
						contentPanel.activate(formView);
						
					},
					failure : function(fp, action) {
											Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									}
					});
				 }
		})
	},
	// 开启
	bidopen : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var gp = this.gridPanel;
			var url = __ctxPath
					+ "/creditFlow/financingAgency/bidUpdateStatePlBidPlan.do?state=0"
					Ext.Msg.confirm('信息确认', '是否开启', function(btn) {
       					 if (btn == 'yes') {
							Ext.Ajax.request({
										url : url,
										method : "post",
										scope:this,
										success : function(response, opts) {
											var res = Ext.util.JSON
													.decode(response.responseText);
											Ext.ux.Toast.msg('操作信息','启动成功');
											
											gp.getStore().reload();
										},
										params : {
											bidId : record.data.bidId
										}
									})
       					 }
				 })
		}

	},
	
	// 关闭
	bidclose : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
				var gp = this.gridPanel;
			var url =__ctxPath
					+ "/creditFlow/financingAgency/bidUpdateStatePlBidPlan.do?state=-1"
					Ext.Msg.confirm('信息确认', '是否关闭', function(btn) {
       					 if (btn == 'yes') {
							Ext.Ajax.request({
								url : url,
								method : "post",
								success : function(response, opts) {
										var res = Ext.util.JSON.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息','关闭成功');
									
									gp.getStore().reload();
								},
								params : {
									bidId : record.data.bidId
								}
							})
       					 }
					})
		}

	},
	close : function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var gp = this.gridPanel;
			var url =__ctxPath
					+ "/creditFlow/financingAgency/closeBidInfoPlBidPlan.do?state=-1"
					Ext.Msg.confirm('信息确认', '是否关闭', function(btn) {
       					 if (btn == 'yes') {
							Ext.Ajax.request({
								url : url,
								method : "post",
								success : function(response, opts) {
										var res = Ext.util.JSON.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息','关闭成功');
									
									gp.getStore().reload();
								},
								params : {
									bidId : record.data.bidId
								}
							})
       					 }
					})
		}

	},
	// 预览
	preview : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			window.open(__p2pPath + "/creditFlow/financingAgency/bidPlanDetailPlBidPlan.do?bidId="+record.data.bidId,'_blank');
			/*var url = __ctxPath
					+ "/creditFlow/financingAgency/previewPublishPlBidPlan.do"
			Ext.Ajax.request({
						url : url,
						method : "post",
						success : function(response, opts) {
							var res = Ext.util.JSON
									.decode(response.responseText);
							//window.open(__p2pPath + res.htmlPath, '_blank');
						},
						params : {
							bidId : record.data.bidId,
							isPublish : false
						}
					})*/
		}

	},
	// 发布
	publish : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			if((new Date(record.data.bidEndTime)).getTime()<(new Date()).getTime()){
				Ext.Msg.alert('操作信息','该项目招标截止日已过，不能发布！')
				return;
			}
			var gp = this.gridPanel;
			var url = __ctxPath
					+ "/creditFlow/financingAgency/previewPublishPlBidPlan.do"
					Ext.Msg.confirm('信息确认', '是否发布', function(btn) {
       					 if (btn == 'yes') {
							Ext.Ajax.request({
								url : url,
								method : "post",
								scope:this,
								success : function(response, opts) {
									var res = Ext.util.JSON
											.decode(response.responseText);
									Ext.ux.Toast.msg('操作信息', res.msg);
									
									gp.getStore().reload();
									if(res.success==true){
									//window.open(__p2pPath + res.htmlPath, '_blank');
									}
								},
								params : {
									bidId : record.data.bidId,
									isPublish : true
								}
							})
       					 }
					})
		}

	},
	bidFailed :function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var url = __ctxPath+ "/creditFlow/financingAgency/bidFailedPlBidPlan.do"
			Ext.Msg.confirm('信息确认', '是否流标', function(btn) {
				 if (btn == 'yes') {
					Ext.Ajax.request({
								url : url,
								method : "post",
								scope:this,
								success : function(response, opts) {
									var res = Ext.util.JSON
											.decode(response.responseText);
									Ext.ux.Toast.msg('操作信息', res.msg);
									return;
								},
								params : {
									bidId : record.data.bidId
								}
							})
				 }
			})
		}

	},
	bidAuto :function(){   //自动投标按钮
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			Ext.MessageBox.wait('正在操作','请稍后...');//锁屏
			var record = selectRs[0];
			var url = __ctxPath+ "/creditFlow/financingAgency/bidAutoPlBidPlan.do"
			Ext.Ajax.request({
						url : url,
						method : "post",
						scope:this,
						success : function(response, opts) {
							var res = Ext.util.JSON.decode(response.responseText);
							Ext.ux.Toast.msg('操作信息', res.msg);
							Ext.MessageBox.hide();//解除锁屏
							return;
						},
						params : {
							bidId : record.data.bidId
						}
					})
		}

	},
	fininsh:function(){
	var gp = this.gridPanel;
	var Q_proType_S_EQ=this.Q_proType_S_EQ;
	 Ext.Msg.confirm("提示!", '确定要设置状态为 成完吗？', function(btn) {
	 	if (btn == "yes") {
			var selectRs = gp.getSelectionModel().getSelections();
			if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			} else if (selectRs.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
			} else {
				var record = selectRs[0];
				var url =__ctxPath
						+ "/creditFlow/financingAgency/bidUpdateStatePlBidPlan.do?state=10"
				Ext.Ajax.request({
							url : url,
							method : "post",
							success : function(response, opts) {
									var res = Ext.util.JSON.decode(response.responseText);
									
									Ext.getCmp('PlPersionDirBidPublishGrid'+Q_proType_S_EQ+"10").getStore().reload();
								
								    gp.getStore().reload();
								    Ext.ux.Toast.msg('操作信息', '设置成功!');
							},
							params : {
								bidId : record.data.bidId
							}
						})
			}
	 	}
	});
	
	}
	,
	/*
	 * 导出投资人列表
	 */
	toExcel : function(){
		
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			var bidPlanId  =  record.data.bidId;
			
	//		var parameter = encodeURI(encodeURI(this.getSearchParameter()));
			window.open(__ctxPath+ '/customer/toExcelInvestPersonInfo.do?Q_bidPlanId_L_EQ='+bidPlanId,'_blank');

			
			
			
	/*		Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do",
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					var  bidPlanInfoForm=new PlBidPlanInfoForm({
						bidPlanId : record.data.bidId,
						projectId:record.get('bpPersionDirPro.proId'),
						fundProjectId:record.get('bpPersionDirPro.moneyPlanId'),
						proType:record.data.proType,
						bidProName:record.data.bidProName,
						oppositeType:obj.data.oppositeType,
						businessType:'SmallLoan'
					})
					
					 contentPanel.add(bidPlanInfoForm);
					contentPanel.activate('PlBidPlanInfoForm_'+record.data.bidId);
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				},
				params : {
					projectId:record.get('bpPersionDirPro.moneyPlanId')
				}
			});*/
			
		}	
	
	}
	

});
