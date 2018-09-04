/**
 * @author
 * @class SlFundintentUrgeView
 * @extends Ext.Panel
 * @description [SlFundintentUrge]管理
 * @company 智维软件
 * @createtime:
 */
MatchFundsView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MatchFundsView.superclass.constructor.call(this, {
					id : 'MatchFundsView' + this.tabflag,
					title : this.tabflagtitle,
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		// this.businessType="all";
		var businessType = this.businessType;
		var tabflag = this.tabflag;
		var onclickflag = this.onclickflag;
		var onclickisInterent = this.onclickisInterent;
		var date5 = new Date();
		var time = date5.getTime();
		time += 1000 * 60 * 60 * 24 * 5;
		date5.setTime(time);
		var datemonth = new Date();
		var timemonth = datemonth.getTime();
		timemonth += 1000 * 60 * 60 * 24 * 30;
		datemonth.setTime(timemonth);
		this.isGrantedShowAllProjects = isGranted('_seeAllPro_p1');
		var isShow = false;
		if (RoleType == "control") {
			isShow = true;
		}
		this.searchPanel = new HT.SearchPanel({
			layout : 'column',
			style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
			region : 'north',
			height : 20,
			anchor : '96%',
			keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
			layoutConfig : {
				align : 'middle',
				padding : '5px'

			},
			// bodyStyle : 'padding:10px 10px 10px 10px',
			items : [{
						columnWidth : 0.2,
						layout : 'form',
						border : false,
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
									labelWidth : 70,
									fieldLabel : '项目名称',
									name : 'projectName',
									flex : 1,
									//editable : false,
									width : 105,
									xtype : 'textfield',
									anchor : '96%'

								}]

					}, {
						columnWidth : 0.2,
						layout : 'form',
						border : false,
						labelWidth : 60,
						labelAlign : 'right',
						items : [{
									labelWidth : 70,
									fieldLabel : '项目编号',
									name : 'Q_projNum_S_LK',
									flex : 1,
									editable : false,
									width : 105,
									xtype : 'textfield',
									anchor : '96%'

								}]

					}, {
						columnWidth : 0.21,
						layout : 'form',
						border : false,
						labelWidth : 100,
						labelAlign : 'right',
						items : [{
									labelWidth : 70,
									fieldLabel : '金额范围:从',
									labelSeparator : '',
									width : 100,
									xtype : 'textfield',
									name : 'Q_incomemoney_D_GE',
									anchor : '96%'
								}]

					}, {
						columnWidth : 0.14,
						layout : 'form',
						border : false,
						labelWidth : 20,
						labelAlign : 'right',
						items : [{
									fieldLabel : '到',
									name : 'Q_incomemoney_D_LE',
									labelSeparator : '',
									xtype : 'textfield',
									anchor : '96%'

								}, {
									xtype : 'hidden',
									name : 'Q_projectStatus_N_EQ',
									value : '10'
								}]

					}, {
						columnWidth : .08,
						xtype : 'container',
						layout : 'form',
						defaults : {
							xtype : 'button'
						},
						style : 'padding-left:10px;',
						items : [{
									text : '查询',
									scope : this,
									iconCls : 'btn-search',
									handler : this.search
								}]
					}, {
						columnWidth : .08,
						xtype : 'container',
						layout : 'form',
						defaults : {
							xtype : 'button'
						},
						style : 'padding-left:10px;',
						items : [{
									text : '重置',
									scope : this,
									iconCls : 'reset',
									handler : this.reset
								}]
					}

			]

		});
		
			this.topbar = new Ext.Toolbar({
						items : [{
							iconCls : 'btn-detail',
							text : '查看项目详情',
							xtype : 'button',
							scope : this,
							handler : /*this.findPro*/function(){
								detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
							}
						}, new Ext.Toolbar.Separator({}),{
							iconCls : 'menu-zmexport',
							text : '登记资金意向',
							xtype : 'button',
							scope : this,
							handler : this.gradePro
						}, new Ext.Toolbar.Separator({}),{
							text : '转为匹配失败项目',
							iconCls : 'btn-del',
							xtype : 'button',
							scope : this,
							handler : this.tofailPro
						}]
					})
		

		this.gridPanel = new HT.GridPanel({
			bodyStyle : "width : 100%",
			region : 'center',
			tbar : this.topbar,
			rowActions : false,
			id : 'SlFundIntentGrid' + this.tabflag,
			url : __ctxPath + '/project/projectListSlSmallloanProject.do?projectStatus=10&isGrantedShowAllProjects='+this.isGrantedShowAllProjects,
			fields : [{
						name : 'runId',
						type : 'int'
					},{
						name : 'projectId',
						type : 'int'
					},
					'orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney',
					'payProjectMoney', 'oppositeType', 'oppositeTypeValue',
					'customerName', 'projectStatus', 'operationType',
					'operationTypeValue', 'taskId', 'activityName',
					'oppositeId', 'businessType', 'startDate', 'endDate',
					'superviseOpinionName', 'supervisionPersonName',
					'processName', 'businessManagerValue', 'breachComment',
					'accrualtype', 'expectedRepaymentDate', 'payintentPeriod'],
			columns : [{
						header : 'fundIntentId',
						dataIndex : 'fundIntentId',
						hidden : true
					}, {
						header : '项目名称',
						dataIndex : 'projectName',
						width : 250
					}, {
						header : '项目编号',
						dataIndex : 'projectNumber',
						width : 250
					}, {
						header : '融资客户名称',
						dataIndex : 'projectName',
						width : 250
					}, {
						header : '拟贷款金额',
						dataIndex : 'projectMoney',
						align : 'right',
						width : 150,
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元"
						}

					}, {
						header : '期望资金到位日期',
						width : 100,
						dataIndex : 'startDate',
						align : 'center'
					}

			]
		});
	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		/*this.gridPanel.getStore().proxy = new Ext.data.HttpProxy({
			url : __ctxPath
					+ "/creditFlow/finance/listbyurgeSlFundIntent.do?businessType="
					+ this.businessType + "&tabflag=" + this.tabflag
		});*/
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new SlFundintentUrgeForm({
								slFundintentUrgeId : rec.data.slFundintentUrgeId
							}).show();
				});
	},
	//登记资金意向
	gradePro : function(grid, rowindex, e){
		var record = this.gridPanel.getSelectionModel().getSelected();
		if(record==null || record=='undefined'){
			Ext.ux.Toast.msg('操作信息','请选中一条记录');
			return;
		}
		new RegistrationIntentForm({
			projectId : record.data.projectId,
			runId : record.data.runId,
			projectMoney : record.data.projectMoney,
			bussinessType : 'SmallLoan',
			isFundMatch:true,
			gridPanel : this.gridPanel
		}).show();
	},
	//转为匹配失败项目
	tofailPro : function(){
		var record = this.gridPanel.getSelectionModel().getSelected();
		if(record==null || record=='undefined'){
			Ext.ux.Toast.msg('操作信息','请选中一条记录');
			return;
		}
		Ext.Msg.confirm('注意','确定要转为匹配失败项目吗?',tofail,this)
		function tofail(fn){
			if(fn=='yes'){
				Ext.Ajax.request({
					url : __ctxPath + '/project/tofailSlSmallloanProject.do?projectId='+record.data.projectId,
//					async:false,
					scope : this,
					success : function(response){
						Ext.ux.Toast.msg('操作信息', '转换成功');
						this.gridPanel.getStore().reload();
					}
				});
			}
		}
	},
	// 查看记录
	findPro : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var tabflag = this.tabflag
		if (s.length <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			
		}

	}
});
