/**
 * @author
 * @class BpPersionDirProView
 * @extends Ext.Panel
 * @description [BpPersionDirPro]管理
 * @company 智维软件
 * @createtime:
 */
BpPersionDirProView = Ext.extend(Ext.Panel, {
	// 构造函数
	querysql:"?Q_loanId_NULL",
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="onlineopen") {//查询线上借款生成的直投项目
			this.querysql="?Q_loanId_NOTNULL";
		}else if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="all") {//带有预览button
			this.querysql="";
		}
		if (typeof(_cfg.type) != "undefined") {
			this.type = _cfg.type;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BpPersionDirProView.superclass.constructor.call(this, {
					id : 'BpPersionDirProView'+this.type+this.subType+this.buttonType,
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
								hidden : this.subType=="onlineopen" ? true : false,
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
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar :this.topbar,
			 plugins : [summary],
			// 使用RowActions
			//rowActions : true,
			//id : 'BpPersionDirProGrid'+this.type+this.buttonType,
			url : __ctxPath
					+ "/creditFlow/financingAgency/persion/listBpPersionDirPro.do"+this.querysql,
			fields : [{
						name : 'pdirProId',
						type : 'int'
					}, 'proId', 'businessType', 'persionId', 'persionName',
					'proName', 'proNumber', 'yearInterestRate',
					'monthInterestRate', 'dayInterestRate',
					'totalInterestRate', 'interestPeriod', 'payIntersetWay',
					'bidMoney', 'loanLife', 'bidTime', 'createTime',
					'updateTime', 'keepStat', 'schemeStat','payAcctualType','disclosureCreateDate'],
			columns : [{
						header : 'pdirProId',
						align:'center',
						dataIndex : 'pdirProId',
						hidden : true
					}, {
						header : 'proId',
						align:'center',
						dataIndex : 'proId',
						hidden : true
					}, {
						header : '业务类别',
						align:'center',
						dataIndex : 'businessType',
						width : 80,
						renderer : function(value){
							if(value=='SmallLoan'){
								return '贷款业务'
							}
						}
					}, {
						header : '姓名',
						dataIndex : 'persionName',
						width :100
					}, {
						header : '项目名称',
						summaryRenderer : totalMoney,
						dataIndex : 'proName',
						width :250
					}, {
						header : '项目编号',
						align:'center',
						dataIndex : 'proNumber',
						width :170
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
						header : '未招标金额(元)',
						dataIndex : 'bidMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(v){
							return Ext.util.Format.number(v,',000,000,000.00')
						}
					}, {
						header : '期望借款期限',
						align : 'center',
						dataIndex : 'loanLife'
					}, {
						header : '计息周期',
						dataIndex : 'payAcctualType',
						width :100,
						align : 'center',
						renderer : function(v){
							if(v=='dayPay'){
								return '日'
							}else if(v=='monthPay'){
								return  '月'
							}else if(v=='seasonPay'){
								return '季'
							}else if(v=='yearPay'){
								return '年'
							}else if(v=='owerPay'){
								return '自定义周期'
							}
						}
					}, {
						header : '维护状态',
						dataIndex : 'keepStat',
						align : 'center',
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
						align : 'center',
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
					proId : s[0].data.pdirProId,
					proType : "P_Dir"
				},
				method : 'post',
				success : function(response,request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success){
						new BpPersionDirProForm({
							pdirProId : s[0].data.pdirProId,
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
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			
			var projectId =s[0].data.proId;
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
		}	
	}
});
