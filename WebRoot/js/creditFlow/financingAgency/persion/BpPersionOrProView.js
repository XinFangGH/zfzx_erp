/**
 * @author
 * @class BpPersionOrProView
 * @extends Ext.Panel
 * @description [BpPersionOrPro]管理
 * @company 智维软件
 * @createtime:
 */
BpPersionOrProView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BpPersionOrProView.superclass.constructor.call(this, {
					id : 'BpPersionOrProView'+this.buttonType,
					title : this.buttonType == '2' ? '贷后信息披露' : '借款项目管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'column',
					region : 'north',
					height :40,
					items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'Q_persionName_S_LK',
							anchor : '100%'
						}]
						
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '项目名称',
							name : 'Q_proName_S_LK',
							anchor : '100%'
						}]
						
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
							xtype : 'textfield',
							fieldLabel : '项目编码',
							name : 'Q_proNumber_S_LK',
							anchor : '100%'
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						style : 'margin-left:20px',
						items : [{
							xtype : 'button',
							text : '查询',
							scope : this,
							iconCls : 'btn-search',
							handler : this.search
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border : false,
						items : [{
							xtype : 'button',
							text : '重置',
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-detail',
								text : '查看借款项目详细信息',
								xtype : 'button',
								scope : this,
								handler : this.editRs
							},{
								iconCls : 'btn-message',
								text : '贷后信息披露',
								xtype : 'button',
								scope : this,
								hidden : this.buttonType == '2' ? false : true,
								handler : this.createRs
							}]
				});
        var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计（元）';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			 plugins : [summary],
			// 使用RowActions
			//rowActions : true,
			//id : 'BpPersionOrProGrid',
			url : __ctxPath
					+ "/creditFlow/financingAgency/persion/listBpPersionOrPro.do?Q_orginalType_SN_EQ=1",
			fields : [{
						name : 'porProId',
						type : 'int'
					}, 'proId', 'businessType', 'businessId', 'persionName',
					'proName', 'proNumber', 'yearInterestRate',
					'monthInterestRate', 'dayInterestRate',
					'totalInterestRate', 'interestPeriod', 'payIntersetWay',
					'bidMoney', 'bidTime', 'createTime', 'updateTime',
					'loanStarTime', 'loanEndTime', 'obligatoryPersion','moneyPlanId',
					'payAcctualType','loanLife','keepStat','receiverName','receiverP2PAccountNumber','disclosureCreateDate'],
			columns : [{
						header : 'porProId',
						align:'center',
						dataIndex : 'porProId',
						hidden : true
					}, {
						header : 'proId',
						align:'center',
						dataIndex : 'proId',
						hidden : true
					}, {
						header : '业务类别',
						dataIndex : 'businessType',
						width : 70,
						renderer : function(value){
							if(value=='SmallLoan'){
								return '贷款业务'
							}
						}
					}, {
						header : '姓名',
						dataIndex : 'persionName',
						width : 70
					}, {
						header : '项目名称',
						summaryRenderer : totalMoney,
						dataIndex : 'proName',
						width : 250
					}, {
						header : '项目编号',
						dataIndex : 'proNumber',
						width : 150
					}, {
						header : '年化利率',
						dataIndex : 'yearInterestRate',
						align : 'right',
						renderer : function(v){
							return v+'%'
						}
					}, {
						header : '付息方式',
						align:'center',
						dataIndex : 'payIntersetWay',
						renderer : function(value){
							if(value==1){
								return '等额本息'
							}else if(value==2){
								return '等额本金'
							}else if(value==3){
								return '等本等息'
							}else if(value==4){
								return '按期收息,到期还本'
							}else if(value==5){
								return '一次性支付全部利息'
							}
						}
					}, {
						header : '招标金额(元)',
						dataIndex : 'bidMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')
						}
					},  {
						header : '债权持有人名称',
						dataIndex : 'receiverName'
					}, {
						header : '债权持有人P2P账号',
						align:'center',
						dataIndex : 'receiverP2PAccountNumber'
					}, {
						header : '维护状态',
						align:'center',
						dataIndex : 'keepStat',
						hidden : this.buttonType == 2 ? true : false,
						renderer : function(v){
							if(v==0){
								return '未维护'
							}else if(v==1){
								return '已维护'
							}
						}
					}, {
						header : '披露状态',
						align:'center',
						dataIndex : 'disclosureCreateDate',
						renderer : function(v){
							if(v != null){
								return '已披露'
							}else{
								return '未披露'
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
	// 创建记录
	createRs : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var grid = this.gridPanel;
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/financingAgency/isAfterLoanPlBidPlan.do',
				params : {
					proId : s[0].data.porProId,
					proType : "P_Or"
				},
				method : 'post',
				success : function(response,request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success){
						new BpPersionOrProForm({
							porProId : s[0].data.porProId,
							isEdit : false,
							grid : grid
						}).show();
					}else{
						Ext.ux.Toast.msg('信息提示', obj.msg);
					}
				},
				failure : function() {
					Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
				}
			})
			
		}
	
	},
	// 编辑Rs
	editRs : function(record) {
			var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{

				
					var projectId = selected.get('moneyPlanId');
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
});
