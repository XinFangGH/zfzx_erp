/**
 * @author
 * @class SlFundintentUrgeView
 * @extends Ext.Panel
 * @description [SlFundintentUrge]管理
 * @company 智维软件
 * @createtime:
 */
MatchFundsAllView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MatchFundsAllView.superclass.constructor.call(this, {
					id : 'MatchFundsAllView' + this.tabflag,
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
									editable : false,
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
									name : 'Q_projNum_N_EQ',
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
									name : 'Q_incomemoney_S_GE',
									anchor : '96%'
								}]

					}, {
						columnWidth : 0.14,
						layout : 'form',
						border : false,
						labelWidth : 20,
						labelAlign : 'right',
						items : [

						{
									fieldLabel : '到',
									name : 'Q_incomemoney_D_LE',
									labelSeparator : '',
									xtype : 'textfield',
									anchor : '96%'

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

		});// end of searchPanel
		

		this.gridPanel = new HT.GridPanel({
			bodyStyle : "width : 100%",
			region : 'center',
			// viewConfig: {
			// forceFit:false
			// },
			// 使用RowActions
			rowActions : false,
			id : 'SlFundIntentGrid' + this.tabflag,
			url : onclickflag == 0
					? __ctxPath + '/project/findListSlSmallloanProject.do?isGrantedShowAllProjects='+this.isGrantedShowAllProjects
					: null,
			fields : [{
						name : 'projectId',
						type : 'int'
					}, 'projectName', 'projectNumber', 'projectMoney','bussinessType',
					'fundTypeName', 'startDate', 'payMoney', 'payInMoney','currency',
					'factDate', 'fundType', 'afterMoney', 'notMoney','startDate','breachRate',
					'flatMoney', 'isOverdue', 'overdueRate', 'accrualMoney','accrual',
					'status', 'remark', 'payintentPeriod',
					'lastslFundintentUrgeTime', 'oppositeName','managementConsultingOfRate',
					'opposittelephone', 'projectStartDate', 'orgName','financeServiceOfRate'],
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
	// 创建记录
	createRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var tabflag = this.tabflag
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			Ext.Ajax.request({
				url : __ctxPath
						+ '/creditFlow/finance/getUrgeCustomSlFundintentUrge.do?projectId='
						+ record.data.projectId + '&businessType='
						+ record.data.businessType,
				method : 'POST',
				success : function(response, request) {
					var tabs = Ext.getCmp('centerTabPanel');
					var gpObj = document
							.getElementById('SlFundintentUrgeFormWin'
									+ record.data.fundIntentId);
					if (gpObj == null) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var custom = obj.data;
						if (obj.oppositeType == "company_customer") {
							var custonname = custom.enterprisename;
							var email = custom.email;
							var area = custom.area;
							var telephone = custom.telephone;
							var receiveMail = custom.receiveMail;
							var postcoding = custom.postcoding;

							gpObj = new SlFundintentUrgeForm({
										tabflag : tabflag,
										fundIntentobj : record.data,
										fundIntentId : record.data.fundIntentId,
										projectId : record.data.projectId,
										businessType : record.data.businessType,
										custonname : custonname,
										email : email,
										area : area,
										telephone : telephone,
										receiveMail : receiveMail,
										postcoding : postcoding,
										oppositeType : obj.oppositeType,
										oppositeID : obj.oppositeID,
										enterpriseId : custom.id

									}).show();

						} else {
							var custonname = custom.name;
							var email = custom.selfemail;
							var area = custom.postaddress;
							var telephone = custom.telphone;
							var receiveMail = custom.name;
							var postcoding = custom.postcode;

							gpObj = new SlFundintentUrgeForm({
										tabflag : tabflag,
										fundIntentobj : record.data,
										fundIntentId : record.data.fundIntentId,
										projectId : record.data.projectId,
										businessType : record.data.businessType,
										custonname : custonname,
										email : email,
										area : area,
										telephone : telephone,
										receiveMail : receiveMail,
										postcoding : postcoding,
										oppositeType : obj.oppositeType,
										oppositeID : obj.oppositeID,
										personIdValue : custom.id

									}).show();

						}
						tabs.add(gpObj);
					}

					tabs.setActiveTab("SlFundintentUrgeFormWin"
							+ record.data.fundIntentId);
				}

			})

		}

	}
});
