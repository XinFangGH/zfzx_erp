/**
 * @author
 * @class PlBidPlanView
 * @extends Ext.Panel
 * @description [PlBidPlan]管理
 * @company 智维软件
 * @createtime:
 */
BidPlanLoanedView = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
			if (typeof(_cfg.managerType) != "undefined") {
			this.managerType = parseInt(_cfg.managerType);
		}
		switch (this.managerType) {
			case 1 :
				this.titlePrefix = "贷后监管";
				this.tabIconCls = "btn-tree-team12";
				break;
			
			case 2 :
				this.titlePrefix = "贷款展期";
				this.tabIconCls = "btn-tree-team14";
				break;
			case 3 :
				this.titlePrefix = "提前还款";
				this.tabIconCls = "btn-tree-team15";
				break;
			case 4 :
				this.titlePrefix = "违约处理";
				this.tabIconCls = "btn-tree-team59";
				break;
			case 5 :
				this.titlePrefix = "贷款结项";
				this.tabIconCls = "";
				break;
			
		}
		Ext.applyIf(this, _cfg);
		
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BidPlanLoanedView.superclass.constructor.call(this, {
					id : 'BidPlanLoanedView'+this.managerType,
					title : this.titlePrefix,
					region : 'center',
					layout : 'border',
					items : [this.searchPanel,this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					layout : 'column',
					region : 'north',
					height :70,
					 bodyStyle : 'padding:10px 10px 10px 10px',
					items : [{
						columnWidth : .3,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '招标项目名称',
							name : 'bidProName',
							anchor : '100%'
						},{
							xtype : 'textfield',
							fieldLabel : '借款项目名称',
							name : 'proName',
							anchor : '100%'
						}]
					},{
						columnWidth : .3,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'datefield',
							fieldLabel : '还款日期',
							name : 'startDate',
							format : 'Y-m-d',
							anchor : '100%'
						},{
							xtype : 'textfield',
							fieldLabel : '招标项目编号',
							name : 'bidProNumber',
							anchor : '100%'
						}]
					},{
						columnWidth : .3,
						layout : 'form',
						labelWidth : 40,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'datefield',
							fieldLabel : '至',
							name : 'endDate',
							format : 'Y-m-d',
							anchor : '100%'
						}]
					},{
						columnWidth : .1,
						layout : 'form',
						border : false,
						style : 'margin-left:20px',
						items : [{
							xtype : 'button',
							text : '查询',
							scope : this,
							iconCls : 'btn-search',
							handler : this.search
						},{
							xtype : 'button',
							text : '重置',
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]
					}]
				});// end of searchPanel
		if(this.managerType==3){
			this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-add',
									text : '提前还款',
									xtype : 'button',
									scope : this,
									handler : this.startPrepaymentFlow
								}, {
									iconCls : 'btn-detail',
									text : '查看详细',
									xtype : 'button',
									scope : this,
									handler : this.seeRs
								}, {
									iconCls : 'btn-detail',
									text : '查看还款计划表',
									xtype : 'button',
									scope : this,
									handler : this.seeFundIntentRs
								}]
					});
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath + "/creditFlow/financingAgency/allLoanedListPlBidPlan.do",
			fields : [{
						name : 'bidId',
						type : 'long'
					}, 'bidProName', 'bidProNumber', 'biddingTypeId',
					'proType', 'proId', 'bidMoney', 'bidMoneyScale',
					'startMoney', 'riseMoney', 'createtime', 'updatetime',
					'state', 'startInterestType', 'bidStartTime',
					'publishSingeTime', 'bidEndTime', 'bidRemark', 'htmlPath',
					'startIntentDate','endIntentDate','bpPersionOrPro','bpBusinessDirPro','bpPersionDirPro','bpBusinessOrPro',"isOtherFlow"],
			columns : [{
						header : 'bidId',
						dataIndex : 'bidId',
						hidden : true
					}, {
						header : '招标项目名称',
						dataIndex : 'bidProName'
					}, {
						header : '招标项目编号',
						dataIndex : 'bidProNumber'
					}, {
						header : '借款项目名称',
						dataIndex : 'proName',
						renderer : function(data, metadata, record, rowIndex,
								columnIndex, store) {
							if(null!=record.get('bpPersionOrPro') && record.get('bpPersionOrPro')!=''){
								return record.data.bpPersionOrPro.proName
							}else if(null!=record.get('bpBusinessDirPro') && record.get('bpBusinessDirPro')!=''){
								return record.data.bpBusinessDirPro.proName
							}else if(null!=record.get('bpPersionDirPro') && record.get('bpPersionDirPro')!=''){
								return record.data.bpPersionDirPro.proName
							}else{
								return record.data.bpBusinessOrPro.proName
							}
						}
					},{
						header : '招标类型',
						dataIndex : 'proType',
						renderer : function(data, metadata, record, rowIndex,
								columnIndex, store) {
							if(data=='B_Dir'){
								return '企业直投标'
							}else if(data=='B_Or'){
								return '企业转让标'
							}else if(data=='P_Dir'){
								return '个人直投标'
							}else{
								return '个人转让标'
							}
						}
					}, {
						header : '招标金额',
						dataIndex : 'bidMoney',
						renderer : function(data, metadata, record, rowIndex,
								columnIndex, store) {
									return Ext.util.Format.number(data,',000,000,000.00')+"元"
								}
					}, {
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
					}, {
						header : '起息日期',
						dataIndex : 'startIntentDate'
					}, {
						header : '还款日期',
						dataIndex : 'endIntentDate'
					}]
				//end of columns
			});


	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	//GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlBidPlanForm({
								bidId : rec.data.bidId
							}).show();
				});
	},
	//启动提前还款流程
	startPrepaymentFlow : function() {
		var gridPanel = this.gridPanel;
		var s = this.gridPanel.getSelectionModel()
				.getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			var isOtherFlow =record.data.isOtherFlow;
			if(isOtherFlow==1){
				Ext.ux.Toast.msg('操作信息', '正在进行提前还款流程，不能启动新的提前还款流程！');
				return false;
			}else if(isOtherFlow==2){
				Ext.ux.Toast.msg('操作信息', '正在进行展期流程，不能启动新的提前还款流程！');
				return false;
			}else{//isOtherFlow==0表示当前没有办理任何贷后流程

				var gridPanel=this.gridPanel
				Ext.Ajax.request( {
					url : __ctxPath +"/smallLoan/finance/startBidPrePaymentProcessSlEarlyRepaymentRecord.do",
					params : {bidId:record.data.bidId},
					method : 'POST',
					success : function(response) {
						gridPanel.getStore().reload()
						var obj=Ext.util.JSON.decode(response.responseText)
						var contentPanel = App.getContentPanel();
						if(obj.taskId==1){
							Ext.ux.Toast.msg('操作信息','您不是提前还款流程中任务<提前还款申请>的处理人!');
							return;
						}else{
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
						}
						gridPanel.getStore().reload()
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
			
			
			}
		

	},

	//编辑Rs
	seeRs : function(record) {
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
						projectId:((null!=record.get('bpPersionOrPro') && record.get('bpPersionOrPro')!='')?record.data.bpPersionOrPro.proId:(null!=record.get('bpBusinessDirPro') && record.get('bpBusinessDirPro')!=''?record.data.bpBusinessDirPro.proId:(null!=record.get('bpPersionDirPro') && record.get('bpPersionDirPro')!=''?record.data.bpPersionDirPro.proId:record.data.bpBusinessOrPro.proId))),
						fundProjectId:((null!=record.get('bpPersionOrPro') && record.get('bpPersionOrPro')!='')?record.data.bpPersionOrPro.moneyPlanId:(null!=record.get('bpBusinessDirPro') && record.get('bpBusinessDirPro')!=''?record.data.bpBusinessDirPro.moneyPlanId:(null!=record.get('bpPersionDirPro') && record.get('bpPersionDirPro')!=''?record.data.bpPersionDirPro.moneyPlanId:record.data.bpBusinessOrPro.moneyPlanId))),
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
					projectId:((null!=record.get('bpPersionOrPro') && record.get('bpPersionOrPro')!='')?record.data.bpPersionOrPro.moneyPlanId:(null!=record.get('bpBusinessDirPro') && record.get('bpBusinessDirPro')!=''?record.data.bpBusinessDirPro.moneyPlanId:(null!=record.get('bpPersionDirPro') && record.get('bpPersionDirPro')!=''?record.data.bpPersionDirPro.moneyPlanId:record.data.bpBusinessOrPro.moneyPlanId)))
				}
			});
			
		}	
	},
	seeFundIntentRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new SeeFundIntentView({
				bidPlanId : record.data.bidId,
				projectId:((null!=record.get('bpPersionOrPro') && record.get('bpPersionOrPro')!='')?record.data.bpPersionOrPro.proId:(null!=record.get('bpBusinessDirPro') && record.get('bpBusinessDirPro')!=''?record.data.bpBusinessDirPro.proId:(null!=record.get('bpPersionDirPro') && record.get('bpPersionDirPro')!=''?record.data.bpPersionDirPro.proId:record.data.bpBusinessOrPro.proId))),
				fundProjectId:((null!=record.get('bpPersionOrPro') && record.get('bpPersionOrPro')!='')?record.data.bpPersionOrPro.moneyPlanId:(null!=record.get('bpBusinessDirPro') && record.get('bpBusinessDirPro')!=''?record.data.bpBusinessDirPro.moneyPlanId:(null!=record.get('bpPersionDirPro') && record.get('bpPersionDirPro')!=''?record.data.bpPersionDirPro.moneyPlanId:record.data.bpBusinessOrPro.moneyPlanId))),
				bidProName:record.data.bidProName,
				businessType:'SmallLoan'
			}).show()
		}
	}
});
