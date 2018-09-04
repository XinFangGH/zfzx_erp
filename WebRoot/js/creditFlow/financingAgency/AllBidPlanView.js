/**
 * @author
 * @createtime
 * @class PlOrReleaseListForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
 
AllBidPlanView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		AllBidPlanView.superclass.constructor.call(this, {
					id : 'AllBidPlanView',
					layout : 'border',
					region : 'center',
					title : '招标综合查询',
					items : [this.searchPanel,this.gridPanel]

				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'column',
					border : false,
					region : 'north',
					height : 40,
					anchor : '100%',
					layoutConfig: {
		               align:'middle'
		            },
		             bodyStyle : 'padding:10px 10px 10px 10px',
					items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '招标名称',
							name : 'Q_bidProName_S_LK',
							anchor : '100%'
						},{
							xtype : 'textfield',
							fieldLabel : '招标编号',
							name : 'Q_bidProNumber_S_LK',
							anchor : '100%'
						}]
					},/*{
						columnWidth : .3,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : []
					},*/{
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 100,
				labelAlign : 'right',
				items : [{
							name : 'Q_dialMoney_BD_GE',
							labelSeparator : '',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '招标金额:从',
							anchor : '98%'

						},{
							name : 'Q_factDate_D_GE',
							labelSeparator : '',
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '开始投标日期:从',
							anchor : '99%'
						}]

			}, {
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				labelWidth : 10,
				labelAlign : 'right',
				items : [ {
							name : 'Q_dialMoney_BD_LE',
							labelSeparator : '',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '到',
							anchor : '88%'

						},{
							name : 'Q_factDate_D_LE',
							labelSeparator : '',
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '到',
							anchor : '88%'
						}]

			},{
				columnWidth : 0.18,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [{
							name : 'Q_dialMoney_BD_GE',
							labelSeparator : '',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '年利率：从',
							anchor : '98%'
						}]

			}, {
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				labelWidth : 10,
				labelAlign : 'right',
				items : [ {
							name : 'Q_dialMoney_BD_LE',
							labelSeparator : '',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '到',
							anchor : '88%'

						}]

			},{
				columnWidth : 0.07,
				layout : 'form',
				border : false,
				style : 'margin-left:20px',
				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					anchor : "100%",
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,
				style : 'margin-left:20px',
				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					anchor : "100%",
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			}]
				});// end of searchPanel
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-detail',
								text : '查看借款项目详细信息',
								xtype : 'button',
								scope : this,
								handler : this.seeProjectInfo
							},{
								iconCls : 'btn-protect',
								text : '公示信息维护',
								xtype : 'button',
								scope : this,
								handler : this.proKeep
							}, {
								iconCls : 'btn-edit',
								text : '编辑招标项目名称',
								xtype : 'button',
								scope : this,
								handler : this.editBidName
							},'->',{
								iconCls : 'btn-detail',
								text : '查看招标网页',
								xtype : 'button',
								scope : this,
								handler : this.preview
							}, {
								iconCls : 'btn-detail',
								text : '查看投标详情',
								xtype : 'button',
								scope : this,
								handler : this.seeRs
							},{
								iconCls : 'btn-detail',
								text : '查看还款计划表',
								xtype : 'button',
								scope : this,
								handler : this.seeCusterFundIntent
							},{
								iconCls : 'btn-xls',
								text : '导出投标列表',
								xtype : 'button',
								scope : this,
								handler : this.toExcel
							}]
				});
		 var url=__ctxPath+ "/creditFlow/financingAgency/allListPlBidPlan.do";
		var growthColumn = new Ext.ux.grid.ProgressColumn({
					header : "招标进度",
					dataIndex : 'bidSchedule',
					width : 100,
					textPst : '%',
					colored : true
				
				});
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			url : url,
			viewConfig: {  
            	forceFit:false  
            },
			plugins : [growthColumn],
			fields : [{
						name : 'bidId',
						type : 'int'
					}, {
						name : 'biddingTypeId',
						mapping : 'plBiddingType.name'
					}, 'proId', 'bidProName', 'bidProNumber',
					'bidMoney', 'bidMoneyScale', 'startMoney', 'riseMoney',
					'createtime', 'updatetime', 'state', 'startInterestType',
					'bidStartTime','prepareSellTime', 'publishSingeTime', 'bidEndTime','proType','bidRemark','isStart','bidSchedule',
					'bpPersionDirPro','bpPersionOrPro','bpBusinessDirPro','bpBusinessOrPro'],
			columns : [{
						header : 'bidId',
						dataIndex : 'bidId',
						hidden : true
					},{
						header : '散标类型',
						width : 80,
						dataIndex : 'proType',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
							if(value=='P_Dir' || value=='B_Dir'){
								return '直投标'
							}else if(value=='P_Or' || value=='B_Or'){
								return '债权转让标'
							}
						}
					}, {
						header : '招标名称',
						width : 200,
						dataIndex : 'bidProName'
					}, {
						header : '招标编号',
						width : 170,
						dataIndex : 'bidProNumber'
					}, {
						header : '借款项目名称',
						width : 250,
						dataIndex : 'proType',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
							if(value=='P_Dir'){
								return record.data.bpPersionDirPro.proName
							}else if(value=='P_Or'){
								return record.data.bpPersionOrPro.proName
							}else if(value=='B_Dir'){
								return record.data.bpBusinessDirPro.proName
							}else if(value=='B_Or'){
								return record.data.bpBusinessOrPro.proName
							}
						}
					}, {
						header : '起息模式',
						width : 80,
						dataIndex : 'biddingTypeId'
					}, {
						header : '招标金额',
						dataIndex : 'bidMoney',
						align : 'right',
						width : 120,
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+'元'
						}
					}, {
						header : '年利率',
						dataIndex : 'proType',
						align : 'right',
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
							if(value=='P_Dir'){
								return record.data.bpPersionDirPro.yearInterestRate+'%'
							}else if(value=='P_Or'){
								return record.data.bpPersionOrPro.yearInterestRate+'%'
							}else if(value=='B_Dir'){
								return record.data.bpBusinessDirPro.yearInterestRate+'%'
							}else if(value=='B_Or'){
								return record.data.bpBusinessOrPro.yearInterestRate+'%'
							}
						}
					},growthColumn, {
						header : '预售公告时间',
						width : 130,
						dataIndex : 'prepareSellTime'
					}, {
						header : '开始投标时间',
						width : 130,
						dataIndex : 'publishSingeTime'
					}, {
						header : '招标截至时间',
						dataIndex : 'bidEndTime',
						width : 130,
						renderer : function(value){
							var date2 = new Date().format("Y-m-d h:i:s");
							if(value<date2){
								return "<font style=\"color:red;\">"+value+"</font>";
							}else{
								return value;
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
			var proId=null;
			var moneyPlanId=null
			if(record.data.proType=='P_Dir'){
				proId=record.data.bpPersionDirPro.proId
				moneyPlanId=record.data.bpPersionDirPro.moneyPlanId
			}else if(record.data.proType=='P_Or'){
				proId=record.data.bpPersionOrPro.proId
				moneyPlanId=record.data.bpPersionOrPro.moneyPlanId
			}else if(record.data.proType=='B_Dir'){
				proId=record.data.bpBusinessDirPro.proId
				moneyPlanId=record.data.bpBusinessDirPro.moneyPlanId
			}else if(record.data.proType=='B_Or'){
				proId=record.data.bpBusinessOrPro.proId
				moneyPlanId=record.data.bpBusinessOrPro.moneyPlanId
			}
			Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do",
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					
					var  bidPlanInfoForm=new PlBidPlanInfoForm({
						bidPlanId : record.data.bidId,
						projectId:proId,
						fundProjectId:moneyPlanId,
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
					projectId:moneyPlanId
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
			var proId=null;
			var moneyPlanId=null
			if(record.data.proType=='P_Dir'){
				proId=record.data.bpPersionDirPro.proId
				moneyPlanId=record.data.bpPersionDirPro.moneyPlanId
			}else if(record.data.proType=='P_Or'){
				proId=record.data.bpPersionOrPro.proId
				moneyPlanId=record.data.bpPersionOrPro.moneyPlanId
			}else if(record.data.proType=='B_Dir'){
				proId=record.data.bpBusinessDirPro.proId
				moneyPlanId=record.data.bpBusinessDirPro.moneyPlanId
			}else if(record.data.proType=='B_Or'){
				proId=record.data.bpBusinessOrPro.proId
				moneyPlanId=record.data.bpBusinessOrPro.moneyPlanId
			}
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
						projectId :proId,
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
					projectId:moneyPlanId
				}
			});
			
		}	
	
	},
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
	
		}

	},
	seeProjectInfo : function(record) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				if(selected.data.proType=='P_Dir' || selected.data.proType=='B_Dir'){
					var projectId =null;
					if(selected.data.proType=='P_Dir'){
						projectId=selected.data.bpPersionDirPro.proId
					}else if(selected.data.proType=='B_Dir'){
						projectId=selected.data.bpBusinessDirPro.proId
					}
					var businessType = 'ParentSmallLoan';
						
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/getProjectViewObjectCreditProject.do',
						params : {
							businessType : businessType,
							projectId : projectId
						},
						method : 'post',
						success : function(resp, op) {
							var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
							
							var tabs = Ext.getCmp('centerTabPanel');
							var gpObj = document.getElementById('ApproveProjectInfoPanel' + record.data.projectId);
							if (gpObj == null) {
								gpObj = eval( "new ApproveProjectInfoPanel({record : record, flag : 'see' })");
								tabs.add(gpObj);
							}
							tabs.setActiveTab('ApproveProjectInfoPanel' +'see'+ record.data.projectId);
						},
						failure : function() {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					})
				}else{
					var projectId = null;
					if(selected.data.proType=='P_Or'){
						projectId=selected.data.bpPersionOrPro.moneyPlanId
					}else if(selected.data.proType=='B_Or'){
						projectId=selected.data.bpBusinessOrPro.moneyPlanId
					}
					var	idPrefix = "SmallLoanProjectInfo_";
				
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/getProjectViewObjectCreditProject.do',
						params : {
							businessType : "SmallLoan",
							projectId : projectId
						},
						method : 'post',
						success : function(resp, op) {
							var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
							showProjectInfoTab(record, idPrefix)
						},
						failure : function() {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					})
				}
		}
	},
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
			window.open(__ctxPath+ '/customer/toExcelInvestPersonInfo.do?Q_bidPlanId_L_EQ='+bidPlanId,'_blank');

			
			
			

			
		}	
	},
	// 项目维护
	proKeep : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			var gridPanel=this.gridPanel
			if(record.data.proType=='B_Dir'){
				if(record.data.bpBusinessDirPro.keepStat==1 && isGranted('businessDirProKeep')==false){
					Ext.ux.Toast.msg('操作信息', '该条记录为已维护项目，您没有维护已公示项目的权限，请联系管理人员开通此权限');
					return true;
				}
				if(record.data.bpBusinessDirPro.keepStat==1){
					
					Ext.Ajax.request({
						url : __ctxPath + "/creditFlow/financingAgency/business/getInfoPlBusinessDirProKeep.do",
						method : 'POST',
						scope:this,
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							new PlBusinessDirProKeepForm({
							    gp:gridPanel,
							    keep:true,
							    keepId:obj.keepId,
							    proId:record.data.bpBusinessDirPro.proId,
								bdirProId:record.data.bpBusinessDirPro.bdirProId,
								proName:record.data.bpBusinessDirPro.proName,
								proNumber:record.data.bpBusinessDirPro.proNumber,
								businessName:record.data.bpBusinessDirPro.businessName,
								proType:"B_Dir"
							}).show();
						},
						failure : function(response,request) {
							Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
						},
						params : {
							type : "B_Dir",
							id:record.data.bpBusinessDirPro.bdirProId
						}
					});
				}else{
					new PlBusinessDirProKeepForm({
					    gp:this.gridPanel,
					    keep:true,
					  	 proId:record.data.bpBusinessDirPro.proId,
						bdirProId:record.data.bpBusinessDirPro.bdirProId,
						proName:record.data.bpBusinessDirPro.proName,
						proNumber:record.data.bpBusinessDirPro.proNumber,
						businessName:record.data.bpBusinessDirPro.businessName,
						proType:"B_Dir"
					}).show();
				}
				
			}else if(record.data.proType=='B_Or'){
				if(record.data.bpBusinessOrPro.keepStat==1 && isGranted('businessOrProKeep')==false){
				Ext.ux.Toast.msg('操作信息', '该条记录为已维护项目，您没有维护已公示项目的权限，请联系管理人员开通此权限');
				return true;
			}
			if(record.data.bpPersionDirPro.keepStat==1){
				var gridPanel=this.gridPanel
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/business/getInfoPlBusinessDirProKeep.do",
					method : 'POST',
					scope:this,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						new PlBusinessDirProKeepForm({
					    gp:gridPanel,
					    keep:true,
					     keepId:obj.keepId,
					    proId:record.data.bpBusinessOrPro.proId,
						borProId:record.data.bpBusinessOrPro.borProId,
						proName:record.data.bpBusinessOrPro.proName,
						proNumber:record.data.bpBusinessOrPro.proNumber,
						businessName:record.data.bpBusinessOrPro.businessName,
						proType:"B_Or"
					}).show();
						
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : {
						type : "B_Or",
						id:record.data.bpBusinessOrPro.borProId
					}
				});
			}else{
				new PlBusinessDirProKeepForm({
					    gp:this.gridPanel,
					    keep:true,
					    proId:record.data.bpBusinessOrPro.proId,
						borProId:record.data.bpBusinessOrPro.borProId,
						proName:record.data.bpBusinessOrPro.proName,
						proNumber:record.data.bpBusinessOrPro.proNumber,
						businessName:record.data.bpBusinessOrPro.businessName,
						proType:"B_Or"
					}).show();
			}
			}else if(record.data.proType=='P_Dir'){
				if(record.data.bpPersionDirPro.keepStat==1 && isGranted('persionDirProKeep')==false){
				Ext.ux.Toast.msg('操作信息', '该条记录为已维护项目，您没有维护已公示项目的权限，请联系管理人员开通此权限');
				return true;
			}
			if(record.data.bpPersionDirPro.keepStat==1){
				var gridPanel=this.gridPanel
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/persion/getInfoPlPersionDirProKeep.do",
					method : 'POST',
					scope:this,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						Ext.Ajax.request({
							url : __ctxPath + "/creditFlow/financingAgency/persion/getBpPersionDirPro.do",
							method : 'POST',
							scope:this,
							success : function(response, request) {
								obj1 = Ext.util.JSON.decode(response.responseText);
								new PlPersionDirProKeepForm({
								 keepId:obj.keepId,
							    gp:gridPanel,
							    record:obj1,// 个人直投项目信息
							    keep:true,
							    proType:"P_Dir"
								
							}).show();
								
							},
							failure : function(response,request) {
								Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
							},
							params : {
								pdirProId:record.data.bpPersionDirPro.pdirProId
							}
						});
						
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : {
						type : "P_Dir",
						id:record.data.bpPersionDirPro.pdirProId
					}
				});
			}else{
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/persion/getBpPersionDirPro.do",
					method : 'POST',
					scope:this,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						new PlPersionDirProKeepForm({
					    gp:gridPanel,
					    record:obj,// 个人直投项目信息
					    keep:true,
					    proType:"P_Dir"
						
					}).show();
						
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : {
						pdirProId:record.data.bpPersionDirPro.pdirProId
					}
				});
				
			}
			}else if(record.data.proType=='P_Or'){
				if(record.data.bpPersionOrPro.keepStat==1 && isGranted('persionOrProKeep')==false){
				Ext.ux.Toast.msg('操作信息', '该条记录为已维护项目，您没有维护已公示项目的权限，请联系管理人员开通此权限');
				return true;
			}
			if(record.data.bpPersionOrPro.keepStat==1){
				var gridPanel=this.gridPanel
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/persion/getInfoPlPersionDirProKeep.do",
					method : 'POST',
					scope:this,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						Ext.Ajax.request({
							url : __ctxPath + "/creditFlow/financingAgency/persion/getBpPersionOrPro.do",
							method : 'POST',
							scope:this,
							success : function(response, request) {
								obj1 = Ext.util.JSON.decode(response.responseText);
									new PlPersionDirProKeepForm({
									    gp:gridPanel,
									     keepId:obj.keepId,
									    keep:true,
									    proType:"P_Or",
									    record:obj1// 个人债权项目信息
									}).show();
								
								
							},
							failure : function(response,request) {
								Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
							},
							params : {
								porProId:record.data.bpPersionOrPro.porProId
							}
						});
			
					
						
						
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : {
						type : "P_Or",
						id:record.data.bpPersionOrPro.porProId
					}
				});
			}else{
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/persion/getBpPersionOrPro.do",
					method : 'POST',
					scope:this,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
							new PlPersionDirProKeepForm({
							    gp:gridPanel,
							    keep:true,
							    proType:"P_Or",
							    record:obj// 个人债权项目信息
							}).show();
						
						
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : {
						type : "P_Or",
						porProId:record.data.bpPersionOrPro.porProId
					}
				});
			
				}
			}
		}
	},
	editBidName:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			new BidPlanInfoEditForm({
				 bidId: record.data.bidId,
				 gridPanel:this.gridPanel
			}).show()
		}
	}
	

});
