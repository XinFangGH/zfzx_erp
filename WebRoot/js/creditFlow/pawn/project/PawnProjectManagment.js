/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷项目管理
 * @company 智维软件
 * @createtime:
 */
PawnProjectManagment = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.type) != "undefined") {
			this.type = parseInt(_cfg.type);
		}
		this.isGrantedShowAllProjects = isGranted('_seeAllPawnPro_p1');// 是否授权显示所有项目记录
		switch (this.type) {
			case 1 :
				this.titlePrefix = "续当办理";
				this.tabIconCls = "btn-tree-team18";
				break;
			case 2 :
				this.titlePrefix = "赎当办理";
				this.tabIconCls = "btn-tree-team19";
				break;
			case 3 :
				this.titlePrefix = "绝当处置";
				this.tabIconCls = "btn-tree-team20";
				break;
			case 4 :
				this.titlePrefix = "当票挂失";
				this.tabIconCls = "btn-tree-team16";
				break;
			case 5 :
				this.titlePrefix = "当票转当";
				this.tabIconCls = "btn-tree-team16";
				break;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PawnProjectManagment.superclass.constructor.call(this, {
			id : 'PawnProjectManagment_' + this.type,
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

		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
					name : 'projectStatus',
					xtype : 'hidden',
					value : 1
				}, {
					name : 'isGrantedShowAllProjects',
					xtype : 'hidden',
					value : this.isGrantedShowAllProjects
				},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '当票号',
						name : 'phnumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '客户名称',
						name : 'customerName',
						anchor : "98%",
						xtype : 'textfield'
					}]
		     	}, this.isHiddenBranch == false ?{
					columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属分公司',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "companyId",
						displayField : 'companyName',
						valueField : 'companyId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/system/getControlNameOrganization.do',
							fields : ['companyId', 'companyName']
						})
					}]
				}:{ 
					columnWidth:0.001,
			     	border:false
		     	},{
	     			columnWidth :.07,
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
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						xtype : 'button',
						scope : this,
						width : 40,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		if (this.type == 1) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '续当办理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_pawn_continued_managment')?false:true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
							return false;
						}else if(s.length>1){
							Ext.ux.Toast.msg('操作信息','只能选中一条记录');
							return false;
						}else{	
							record=s[0]
							if(record.data.lockStatus==0){
								new PawnContinuedWindow({projectId:record.data.projectId,businessType:record.data.businessType,continueId:null,idDefinition:'continueEdit',isAllReadOnly:false}).show()
							}else{
								Ext.ux.Toast.msg('操作信息','该项目目前处于挂失状态，已被锁定，不能进行续当办理');
								return;
							}
						}
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('_pawn_continued_managment')?false:true
				}),{
					iconCls : 'btn-detail',
					text : '查看典当详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_pawnInfo'+this.type)?false:true,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfo_')
					}
				}]
			});
		} 
		else if(this.type == 2){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '赎当办理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_pawn_redeem_managment')?false:true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
							return false;
						}else if(s.length>1){
							Ext.ux.Toast.msg('操作信息','只能选中一条记录');
							return false;
						}else{	
							record=s[0]
							if(record.data.lockStatus==0){
								new PawnRedeemWindow({projectId:record.data.projectId,businessType:record.data.businessType,redeemId:null,projectPanel:this.gridPanel}).show()
							}else{
								Ext.ux.Toast.msg('操作信息','该项目目前处于挂失状态，已被锁定，不能进行赎当办理');
								return;
							}
						}
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('_pawn_redeem_managment')?false:true
				}),{
					iconCls : 'btn-detail',
					text : '查看典当详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_pawnInfo'+this.type)?false:true,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfo_')
					}
				}]
			})
			}
		else if (this.type == 3) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '绝当办理',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_pawn_vast_managment')?false:true,
					handler : function() {
						var s = this.gridPanel.getSelectionModel().getSelections();
						if (s <= 0) {
							Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
							return false;
						}else if(s.length>1){
							Ext.ux.Toast.msg('操作信息','只能选中一条记录');
							return false;
						}else{	
							record=s[0]
							if(record.data.lockStatus==0){
								new PawnVastWindow({projectId:record.data.projectId,businessType:record.data.businessType,projectPanel:this.gridPanel}).show()
							}else{
								Ext.ux.Toast.msg('操作信息','该项目目前处于挂失状态，已被锁定，不能进行绝当办理');
								return;
							}
						}
					}
				}, new Ext.Toolbar.Separator({
					hidden : isGranted('_pawn_vast_managment')?false:true
				}),{
					iconCls : 'btn-detail',
					text : '查看典当详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_see_pawnInfo'+this.type)?false:true,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfo_')
					}
				}]
			})
			} else if (this.type == 4) {
				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-edit',
						text : '当票挂失',
						xtype : 'button',
						hidden : isGranted('_pawn_ticket_loss')?false:true,
						scope : this,
						handler : function() {
							var s = this.gridPanel.getSelectionModel().getSelections();
							if (s <= 0) {
								Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
								return false;
							}else if(s.length>1){
								Ext.ux.Toast.msg('操作信息','只能选中一条记录');
								return false;
							}else{	
								record=s[0]
								if(record.data.lockStatus==0){
									new PawnTicketLossWindow({projectId:record.data.projectId,businessType:record.data.businessType,projectPanel:this.gridPanel,isAllReadOnly:false}).show()
								}else{
									Ext.ux.Toast.msg('操作信息','该项目目前已经处于挂失状态，已被锁定，不能再次进行挂失');
									return;
								}
							}
						}
					}, new Ext.Toolbar.Separator({
					}),{
						iconCls : 'btn-edit',
						text : '当票补发',
						xtype : 'button',
						hidden : isGranted('_pawn_ticket_reissue')?false:true,
						scope : this,
						handler : function() {
							var s = this.gridPanel.getSelectionModel().getSelections();
							if (s <= 0) {
								Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
								return false;
							}else if(s.length>1){
								Ext.ux.Toast.msg('操作信息','只能选中一条记录');
								return false;
							}else{	
								record=s[0]
								if(record.data.lockStatus!=0){
									new PawnTicketReissueWindow({projectId:record.data.projectId,businessType:record.data.businessType,lossRecordId:record.data.lockStatus,projectPanel:this.gridPanel}).show()
								}else{
									Ext.ux.Toast.msg('操作信息','该项目目前已经不处于挂失状态，不能进行当票补发操作');
									return;
								}
							}
						}
					},{
						iconCls : 'btn-detail',
						text : '查看典当详情',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_see_pawnInfo'+this.type)?false:true,
						handler : function() {
							detailPro(this.gridPanel, 'PlPawnProjectInfo_')
						}
					}]
				})
			} else if (this.type == 5) {
				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-edit',
						text : '当票转当',
						xtype : 'button',
						scope : this,
						handler : function() {
							detailPro(this.gridPanel, 'SmallLoanProjectInfoEdit_')
						}
					}, new Ext.Toolbar.Separator({
					}),{
						iconCls : 'btn-detail',
						text : '查看典当详情',
						xtype : 'button',
						scope : this,
						handler : function() {
							detailPro(this.gridPanel, 'PlPawnProjectInfo_')
						}
					}]
				})
			} 
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			// 不适用RowActions
			rowActions : false,
			url : __ctxPath + "/creditFlow/pawn/project/projectListPlPawnProject.do",
			baseParams : {
				'projectStatus' : 1,
				'isGrantedShowAllProjects' : this.isGrantedShowAllProjects,
				'keyWord' : this.keyWord
			},
			fields : [{
				name : 'runId',
				type : 'long'
			}, 'projectId','subject', 'creator', 'userId', 'defId', 'piId',
					'createtime', 'runStatus', 'processName', 'businessType',
					 'projectNumber', 'accrual',
					'customerName', 'projectStatus', 'operationType',
					'appUserId', 'companyId', 'comprehensiveCost',
					'createDate', 'dayOfEveryPeriod', 'flowType', 'intentDate',
					'isInterestByOneTime', 'isPreposePayAccrual',
					'isStartDatePay', 'mineId', 'mineType',
					'monthFeeRate', 'operationType', 'oppositeID',
					'oppositeType','overdueRate','overdueRateLoan','pawnApplication',
					'pawnType','payaccrualType','payintentPeriod','payintentPerioDate',
					'phnumber','projectMoney','projectStatus','remarks','startDate','taskId',
					'activityName','taskCreateTime','endTime','taskLimitTime','piDbid','projectName','appUserName','executor','orgName','continueCount','lockStatus'],

			columns : [{
				header : '所属分公司',
				hidden : this.isHiddenBranch?true : false,
				width : 100,
				dataIndex : 'orgName'
			}, {
				header : '当票号',
				width : 130,
				dataIndex : 'phnumber'
			}, {
				header : '客户名称',
				dataIndex : 'customerName'
			}, {
				header : '典当开始日期',
				dataIndex : 'startDate'
			}, {
				header : '计划到期日期',
				dataIndex : 'intentDate'
			}, {
				header : '典当金额',
				align : 'right',
				width : 110,
				sortable : true,
				dataIndex : 'projectMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00元";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}
				}
			}, {
				header : '项目经理',
				width : 70,
				dataIndex : 'appUserName'
			},{
				header : '续当次数',
				dataIndex : 'continueCount'
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
		/*if (!this.isGrantedShowAllProjects) {
			this.gridPanel.getStore().on("load", function(store) {
				if (store.getCount() < 1) {
					this.get(0).setVisible(true);
					this.searchPanel.setVisible(false);
					this.gridPanel.setVisible(false);
					this.doLayout();
				}
			}, this)
		}*/
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
	}
});
