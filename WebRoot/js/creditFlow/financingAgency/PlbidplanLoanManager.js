/**
 * 散标贷后查询
 */
PlbidplanLoanManager = Ext.extend(Ext.Panel, {
	titlePrefix : "",
	tabIconCls : "",
	isHiddenBd : true,
	isHiddenAn : true,
	isHiddenCd : true,
	isHiddenSd : true,
	isHiddenPs : true,
	isHiddenSuperviseIn : true,
	isHiddenBreachComment : true,
	dateLabel : "启动时间",
	startDateName : "Q_startDate_D_GE",
	endDateName : "Q_startDate_D_LE",
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.state) != "undefined") {
			this.state = parseInt(_cfg.state);
		}
		this.isGrantedShowAllProjects = isGranted('ApproveProject_seeAll_'+ this.state);// 是否授权显示所有项目记录
		switch (this.state) {
			case 7 :
				this.titlePrefix = "还款中项目";
				this.tabIconCls = "btn-team39";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				break;
			case 8 :
				this.titlePrefix = "展期中项目";
				this.tabIconCls = "btn-team39";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				this.isHiddenSuperviseIn = false;
				break;
			case 10 :
				this.titlePrefix = "已完成项目";
				this.tabIconCls = "btn-team39";
				this.isHiddenCd = false;
				this.dateLabel = "完成时间";
				this.startDateName = "Q_endDate_D_GE";
				this.endDateName = "Q_endDate_D_LE";
				break;
			case 9 :
				this.titlePrefix = "平台赎回项目";
				this.tabIconCls = "btn-team39";
				this.isHiddenCd = false;
				this.dateLabel = "完成时间";
				this.startDateName = "Q_endDate_D_GE";
				this.endDateName = "Q_endDate_D_LE";
				break;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		PlbidplanLoanManager.superclass.constructor.call(this, {
			id : 'PlbidplanLoanManager_' + this.state,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : this.tabIconCls,
			items : [{
				xtype : 'label',
				text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看您的上下级关系是否设置正确!(查询授权规律为：您可以查看自己或您下属的项目)',
				hidden : true
			}, this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {
			columnWidth : .14,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 60,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : this.dateLabel,
				name : this.startDateName,
				xtype : 'datefield',
				format : 'Y-m-d'

			}]
		};
		var endDate = {
			columnWidth : .112,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 25,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : '到',
				name : this.endDateName,
				xtype : 'datefield',
				labelSeparator : "",
				format : 'Y-m-d'
			}]
		};
		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 70,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
					name : 'state',
					xtype : 'hidden',
					value : this.state
				}, {
					name : 'isGrantedShowAllProjects',
					xtype : 'hidden',
					value : this.isGrantedShowAllProjects
				},{
		     		columnWidth :.3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '招标项目名称',
						name : 'bidProName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '招标项目编号',
						name : 'bidProNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '借款项目名称',
						name : 'proName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
	     			columnWidth :.3,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '还款日期',
						name : 'endIntentDateMin',
						anchor : "100%",
						editable : false,
						format : 'Y-m-d',
						xtype : 'datefield'
					}]
	     		},{
	     			columnWidth :.3,
					layout : 'form',
					labelWidth : 30,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '至',
						labelSeparator : "",
						style :'margin-left:10px',
						name : 'endIntentDateMax',
						anchor : "98%",
						editable : false,
						format : 'Y-m-d',
						xtype : 'datefield'
					}]
	     		},{
	     			columnWidth :.1,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		},{
	     			columnWidth :.1,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '重置',
						style :this.isHiddenBranch ?'margin-left:30px':'margin-left:245px',
						xtype : 'button',
						scope : this,
						anchor : "90%",
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		//工具栏----------------
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-detail',
				text : '查看详细',
				xtype : 'button',
				scope : this,
				hidden : isGranted('PlbidplanLoanManager_seeInfo' + this.state)? false: true,
				handler : this.seeInfo
			}, new Ext.Toolbar.Separator({
				hidden : !isGranted('PlbidplanLoanManager_seeInfo' + this.state)
			}), {
				iconCls : 'btn-detail',
				text : '查看还款计划表',
				xtype : 'button',
				hidden : isGranted('PlbidplanLoanManager_seeIntentInfo' + this.state)? false: true,
				scope : this,
				handler : this.seeCusterFundIntent
			}]
		});
		
		this.gridPanel = new HT.GridPanel({
			name : 'SmallProjectGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			// 不适用RowActions
			rowActions : false,
      	    viewConfig:{
      	    	forceFit : false
      	    },
			url : __ctxPath + "/creditFlow/financingAgency/findByStatePlBidPlan.do",
			baseParams : {
				state : this.state
			},
			fields : [{
					name : 'bidId',
					type : 'Long'
				}, 'bidProName', 'bidProNumber',
				'bidMoney', 'state', 'startInterestType',
				'bidStartTime','prepareSellTime', 'publishSingeTime', 'bidEndTime','proType',
				'bidRemark','isStart','endIntentDate','projectName','startIntentDate','projectId','id',
				'oppositeType','name','yearAccrualRate'],
			columns : [{
						header : 'bidId',
						dataIndex : 'bidId',
						hidden : true
					}, {
						header : '招标项目名称',
						width : 200, 
						dataIndex : 'bidProName'
					}, {
						header : '招标项目编号',
						width : 200, 
						dataIndex : 'bidProNumber'
					}, {
						header : '借款项目名称',
						width : 200, 
						dataIndex : 'projectName'
					},{
						header : '起息模式',
						dataIndex : 'name'
					}, {
						header : '招标金额(元)',
						dataIndex : 'bidMoney'
					}, {
						header : '年利率(%)',
						dataIndex : 'yearAccrualRate'
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
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);

	},// end of the initComponents()
	
		// 查看意见与说明记录 
	flowRecords : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		var runId =selRs[0].get('runId');
		var businessType=selRs[0].get('businessType');
		new SlProcessRunView({
						runId : runId,
						businessType : businessType
					}).show();
	},
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
	/*
	 * 查看详细
	 */
	seeInfo : function(){
	
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
	        var contentPanel = Ext.getCmp('centerTabPanel');
			var bidPlanInfoForm=new PlBidPlanInfoForm({
				bidPlanId : record.data.bidId,
				projectId:record.data.projectId,
				fundProjectId:record.data.id,
				proType:record.data.proType,
				bidProName:record.data.bidProName,
				oppositeType:record.data.oppositeType,
				businessType:'SmallLoan'
			})
			contentPanel.add(bidPlanInfoForm);
			contentPanel.activate('PlBidPlanInfoForm_'+record.data.bidId);
			/*Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do",
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					var  bidPlanInfoForm=new PlBidPlanInfoForm({
						bidPlanId : record.data.bidId,
						projectId:record.data.projectId,
						fundProjectId:record.data.id,
						proType:record.data.proType,
						bidProName:record.data.bidProName,
						oppositeType:record.data.oppositeType,
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
			})*/;
			
		}	
	},
	/*
	 * 查看还款计划
	 */
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
			var contentPanel = Ext.getCmp('centerTabPanel');
			var  bidPlanInfoForm=new PlBidPlanInfoCusterFundIntent({
				bidPlanId : record.data.bidId,
				projectId:record.data.projectId,
				fundProjectId:record.data.id,
				proType:record.data.proType,
				bidProName:record.data.bidProName,
				oppositeType:record.data.oppositeType,
				businessType:'SmallLoan'
			})
			
			var Win = new Ext.Window({
				layout : 'form',
				title : '还款计划',
				width : 1000,
				height : 400,
				buttonAlign :'center',
				maximizable : true,
				autoScroll : true,
				modal : true,
				items :[bidPlanInfoForm]
			});
			Win.show();
		/*	Ext.Ajax.request({
			url : __ctxPath + "/fund/getInfoBpFundProject.do",
			method : 'POST',
			scope:this,
			success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var contentPanel = Ext.getCmp('centerTabPanel');
					var  bidPlanInfoForm=new PlBidPlanInfoCusterFundIntent({
						bidPlanId : record.data.bidId,
						projectId:record.get('bpPersionDirPro.proId'),
						fundProjectId:record.get('bpPersionDirPro.moneyPlanId'),
						proType:record.data.proType,
						bidProName:record.data.bidProName,
						oppositeType:obj.data.oppositeType,
						businessType:'SmallLoan'
					})
					
					var Win = new Ext.Window({
						layout : 'form',
						title : '还款计划',
						width : 1000,
						height : 400,
						buttonAlign :'center',
						maximizable : true,
						autoScroll : true,
						modal : true,
						items :[bidPlanInfoForm]
					});
					Win.show();
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
