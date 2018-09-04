/**
 * @author
 * @createtime
 * @class PlOrReleaseListForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
PlPersionOrBidPublish = Ext.extend(Ext.Panel, {
	isOpen:true,//发布按钮 默认不显示
	isClose:true,//关闭同时带流标  默认不显示
	isBidClose:true, //流标后关闭，默认不显示
	isStart:true,//启动起息办理流程 ，默认不显示
	isAuToBid:true,//自动投标  ， 默认不显示
	isCancelBid:true,//流标，默认不显示
	isFinish:true,//已完成，默认不显示
	isPreview:true,//预览网页，默认不显示
	isBidDetail:true,//查看投标明细， 默认不显示
	isFundDetail:true,//查看还款明细，默认不显示
	isToolBar:false,//是否出现toolBar，默认出现Toolbar
	isToExcel:true,//是否显示导出，默认不显示
	// 构造函数
	constructor : function(_cfg) {
				if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="1") {//带有预览button
			this.isPreview=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="2"){//带有发布button
			this.isOpen=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="3"){//带有流标button
			this.isCancelBid=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="4"){//带有自动投标的button
			this.isAuToBid=false;
			this.isPreview=false;
			this.isBidDetail=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="5"){//带有启动起息办理流程的button
			this.isStart=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="6"){//带有流标后关闭的button
			this.isBidClose=false;
			this.isPreview=false;
			this.isBidDetail=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="7"){//带有已完成的button
			this.isFinish=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="0"){//不出现toolbar
			this.isToolBar=false;this.isToExcel=false;
		}else if(typeof(_cfg.buttonType) != "undefined" && _cfg.buttonType=="all"){//按原来规则出现按钮
			this.isOpen=false,//发布按钮 默认不显示
			this.isClose=false;//关闭同时带流标  默认不显示
			this.isBidClose=false; //流标后关闭，默认不显示
			this.isStart=false;//启动起息办理流程 ，默认不显示
			this.isAuToBid=false;//自动投标  ， 默认不显示
			this.isCancelBid=false;//流标，默认不显示
			this.isFinish=false;//已完成，默认不显示
			this.isPreview=false;//预览网页，默认不显示
			this.isBidDetail=false;//查看投标明细， 默认不显示
			this.isFundDetail=false;//查看还款明细，默认不显示
	
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlPersionOrBidPublish.superclass.constructor.call(this, {
					id : 'PlPersionOrBidPublish_'+this.Q_proType_S_EQ+this.Q_state_N_EQ+this.buttonType,
					layout : 'border',
					iconCls : 'btn-tree-team30',
					items : [this.searchPanel,this.gridPanel],
					modal : true,
					height : 550,
					autoWidth : true,
					maximizable : true,
					buttonAlign : 'center'

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
	    var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
			        id:'UPlanBidPublishSearchPanel'+this.keystr,
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
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '招标名称',
								name : 'Q_bidProName_S_LK',
								xtype : 'textfield'

							},{
								fieldLabel : '招标编号',
								name : 'Q_bidProNumber_S_LK',
								xtype : 'textfield'

							}]
						},{
							columnWidth : .2,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 100,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '开始投标时间:从',
								name : 'publishSingeTime_GE',
								xtype : 'datefield',
								format:"Y-m-d"

							},{
								fieldLabel : '招标截止时间:从',
								name : 'bidEndTime_GE',
								xtype : 'datefield',
								format:"Y-m-d"

							}]
						},{
							columnWidth : .13,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '到',
								name : 'publishSingeTime_LE',
								xtype : 'datefield',
								format:"Y-m-d"

							},{
								fieldLabel : '到',
								name : 'bidEndTime_LE',
								xtype : 'datefield',
								format:"Y-m-d"

							}]
						},{
							columnWidth : .18,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '招标金额:从',
								name : 'Q_bidMoney_BD_GE',
								xtype : 'numberfield'

							}]
						},{
							columnWidth : .13,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
								anchor : '100%'
							},
							items :[{
								fieldLabel : '到',
								name : 'Q_bidMoney_BD_LE',
								xtype : 'numberfield'

							}]
						},{
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
						    },{
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
						}]
					}]
				});// end of searchPanel    this.Q_state_N_EQ.toString()==""
		this.topbar = new Ext.Toolbar({
					items : [ {
								iconCls : 'btn-notice',
								text : '发布',
								xtype : 'button',
								hidden:this.isOpen,
								scope : this,
								handler : this.publish
							}, {
								iconCls : 'close',
								text : '关闭',
								xtype : 'button',
								hidden:this.isClose,
								scope : this,
								handler : this.bidclose
							}, {
								iconCls : 'close',
								text : '关闭',//此关闭按钮只针对已流标的，只改变标的状态，不调用第三方接口
								xtype : 'button',
								hidden:this.isBidClose,
								scope : this,
								handler : this.close
							},{
								iconCls : 'btn-team1',
								text : '启动办理流程',
								hidden:this.isStart,
								xtype : 'button',
								scope : this,
								handler : this.startRs
							},{
								iconCls : 'btn-auto',
								text : '自动投标',
								xtype : 'button',
								hidden:this.isAuToBid,
								scope : this,
								handler : this.bidAuto
							},/* {
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
								hidden:this.isAutoTender==1?true:this.isCancelBid,
								scope : this,
								handler : this.bidFailed
							}, {
								iconCls : 'btn-finish',
								text : '完成',
								hidden:this.isFinish,
								xtype : 'button',
								scope : this,
								handler : this.fininsh
							},{
								iconCls : 'btn-xls',
								text : '导出列表',
								xtype : 'button',
								hidden:this.isToExcel,
								scope : this,
								handler : this.outToExcel
							},'->',{
								iconCls : 'btn-detail',
								text : '查看招标网页',
								xtype : 'button',
								hidden:this.isPreview,
								scope : this,
								handler : this.preview
							},{
								iconCls : 'btn-detail',
								text : '查看投标详情',
								xtype : 'button',
								scope : this,
								hidden:this.isBidDetail,
								handler : this.seeRs
							},{
								iconCls : 'btn-detail',
								text : '查看还款计划表',
								xtype : 'button',
								hidden:this.isFundDetail,
								//hidden : isGranted('PlbidplanLoanManager_seeIntentInfo' + this.state)? false: true,
								scope : this,
								handler : this.seeCusterFundIntent
							},{
								iconCls : 'btn-xls',
								text : '导出投标列表',
								xtype : 'button',
								hidden:true,
								scope : this,
								handler : this.toExcel
							}]
				});
				
		var flag=this.Q_state_N_EQ==21 || this.Q_state_N_EQ==22 ||this.Q_state_N_EQ==23;
		 var url=__ctxPath+ "/creditFlow/financingAgency/listPlBidPlan.do?Q_proType_S_EQ="+this.Q_proType_S_EQ+"&Q_state_N_EQ="+(flag?1:this.Q_state_N_EQ)+"&flag="+this.Q_state_N_EQ+"&Q_plBiddingType.biddingTypeId_L_LT=3";
		var growthColumn = new Ext.ux.grid.ProgressColumn({
					header : "招标进度",
					dataIndex : 'bidSchedule',
					hidden : (this.Q_state_N_EQ==23?false:true),
					width : 100,
					align:'center',
					textPst : '%',
					colored : true
				
				});
      
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			id : 'PlPersionOrBidPublishGrid'+this.Q_proType_S_EQ+this.Q_state_N_EQ,
			url : url,
			plugins : [growthColumn,summary],
			sm : this.projectFundsm,
			fields : [{
						name : 'bidId',
						type : 'int'
					}, {
						name : 'yearInterestRate',
						mapping : 'bpPersionOrPro.yearInterestRate'
					}, {
						name : 'proName',
						mapping : 'bpPersionOrPro.proName'
					}, {
						name : 'biddingTypeId',
						mapping : 'plBiddingType.name'
					}, 'proId', 'bidProName', 'bidProNumber',
					'bidMoney', 'bidMoneyScale', 'startMoney', 'riseMoney',
					'createtime', 'updatetime', 'state', 'startInterestType',
					'bidStartTime','prepareSellTime', 'publishSingeTime', 'bidEndTime','bpPersionOrPro.moneyPlanId','bpPersionOrPro.proId','proType',
					'bidRemark','isStart','bidSchedule'],
			columns : [{
						header : 'bidId',
						align:'center',
						dataIndex : 'bidId',
						hidden : true
					}, {
						header : '招标名称',
						width : 200,
						dataIndex : 'bidProName'
					}, {
						header : '招标编号',
						width : 170,
						dataIndex : 'bidProNumber',
						summaryType : 'count',
						summaryRenderer : totalMoney
					}, /*{
						header : '借款项目名称',
						width : 250,
						dataIndex : 'proName'
					}, {
						header : '起息模式',
						dataIndex : 'biddingTypeId'
					},*/ {
						header : '招标金额(元)',
						dataIndex : 'bidMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')
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
						dataIndex : 'bidEndTime'
					},{
						header:'招标状态',
						dataIndex:'state',
						align:'center',
						hidden:this.Q_state_N_EQ.toString()==""?false:true,
						renderer:function(value){
							if(null==value){
							
							}else if(value==0){
								return "待发标";
							}else if(value==1){
								return "招标中";
							}else if(value==2){
								return "已齐标";
							}else if(value==3){
								return "已流标";
							}else if(value==4){
								return "已关闭";
							}else if(value==5){
							
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
						projectId:record.get('bpPersionOrPro.proId'),
						fundProjectId:record.get('bpPersionOrPro.moneyPlanId'),
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
					projectId:record.get('bpPersionOrPro.moneyPlanId')
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
						projectId :record.get('bpPersionOrPro.proId'),
						bidPlanId:record.data.bidId,
						object : this.projectInfoFinance,
						bidPlanFinanceInfo:this.BidPlanFinanceInfo,
						businessType : 'SmallLoan',
						isHiddenTitle : true,
						isHiddenautocreateBtn:true,
						isFactHidden:false,
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
					projectId:record.get('bpPersionOrPro.moneyPlanId')
				}
			});
			
		}	
	
	},
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
			
		}	
	
	},
	startRs:function(){
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
				Ext.Ajax.request( {
					url : __ctxPath +"/creditFlow/financingAgency/startMatchingFundsFlowPlBidPlan.do",
					params : {bidId:record.data.bidId},//
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
										Ext.ux.Toast.msg('操作信息', '启动成功');
										var gp = this.gridPanel;
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
			var url =__ctxPath
					+ "/creditFlow/financingAgency/bidUpdateStatePlBidPlan.do?state=-1"
					Ext.Msg.confirm('信息确认', '是否关闭', function(btn) {
			 		if (btn == 'yes') {
						Ext.Ajax.request({
									url : url,
									method : "post",
									success : function(response, opts) {
											var res = Ext.util.JSON.decode(response.responseText);
											Ext.ux.Toast.msg('操作信息', '关闭成功');
											var gp = this.gridPanel;
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
	fininsh:function(){
		var gp = this.gridPanel;
		var Q_proType_S_EQ=this.Q_proType_S_EQ;
		 Ext.Msg.confirm("提示!", '确定要设置状态为完成吗？', function(btn) {
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
					var url =__ctxPath+ "/creditFlow/financingAgency/bidUpdateStatePlBidPlan.do?state=10"
					Ext.Ajax.request({
						url : url,
						method : "post",
						success : function(response, opts) {
							var res = Ext.util.JSON.decode(response.responseText);
							if(res.success || res.success=="true"){
							    gp.getStore().reload();
							    Ext.ux.Toast.msg('操作信息','设置成功!');
							}else{
								Ext.ux.Toast.msg('操作信息',res.msg);
							}
						},
						params : {
							bidId : record.data.bidId
						}
					})
				}
	 		}
		});
	},
	bidAuto :function(){   //自动投标按钮
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var gp = this.gridPanel;
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
							gp.getStore().reload();
							return;
						},
						params : {
							bidId : record.data.bidId
						}
					})
		}

	},
			//导出到Excel
	outToExcel:function(){
	    var flag=this.Q_state_N_EQ==21 || this.Q_state_N_EQ==22 ||this.Q_state_N_EQ==23;
		var bidName=this.searchPanel.getCmpByName('Q_bidProName_S_LK').getValue();
		var bidNumber=this.searchPanel.getCmpByName('Q_bidProNumber_S_LK').getValue();
		var startDate1=this.searchPanel.getCmpByName('publishSingeTime_GE').getValue();
		if(startDate1!=null&&startDate1!="undefined"&&startDate1!=""){
			startDate1=startDate1.format('Y-m-d');
		}
		var startDate2=this.searchPanel.getCmpByName('publishSingeTime_LE').getValue();
		if(startDate2!=null&&startDate2!="undefined"&&startDate2!=""){
			startDate2=startDate2.format('Y-m-d');
		}
		var endDate1=this.searchPanel.getCmpByName('bidEndTime_GE').getValue();
		if(endDate1!=null&&endDate1!=""&&endDate1!="undefined"){
			endDate1=endDate1.format('Y-m-d');
		}
		var endDate2=this.searchPanel.getCmpByName('bidEndTime_LE').getValue();
		if(endDate2!=null&&endDate2!=""&&endDate1!="undefined"){
			endDate2=endDate2.format('Y-m-d');
		}
		var bidMoney1=this.searchPanel.getCmpByName('Q_bidMoney_BD_GE').getValue();
		
		var bidMoney2=this.searchPanel.getCmpByName('Q_bidMoney_BD_LE').getValue();
		window.open(__ctxPath+ "/creditFlow/financingAgency/allExportExcelPlBidPlan.do?Q_proType_S_EQ="+this.Q_proType_S_EQ+"&Q_state_N_EQ="+(flag?1:this.Q_state_N_EQ)+"&flag="+this.Q_state_N_EQ+"&Q_plBiddingType.biddingTypeId_L_LT=3&Q_bidProName_S_LK="+bidName
		+"&Q_bidProNumber_S_LK="+bidNumber+"&publishSingeTime_GE="+startDate1+"&publishSingeTime_LE="+startDate2+"&bidEndTime_GE="+endDate1+"&bidEndTime_LE="+endDate2+"&Q_bidMoney_BD_GE="+bidMoney1+"&Q_bidMoney_BD_LE="+bidMoney2
,'_blank');
		
	}
});
