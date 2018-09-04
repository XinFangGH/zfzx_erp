BpPersionDirProAllView = Ext.extend(Ext.Panel, {
	/*
	 * rechargeLevel : 0, titlePrefix : "",
	 */
	hiddenInfo : false,

	// 构造函数
	 querysql:"?Q_loanId_NULL",
	constructor : function(_cfg) {
		if (_cfg == null) {
				_cfg = {};
		}
		if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="onlineopen") {//带有预览button
			this.querysql="?Q_loanId_NOTNULL";
			this.subType= _cfg.subType;
		}else if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="all") {//带有预览button
			this.querysql="";
		    this.subType= _cfg.subType;
		}
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		BpPersionDirProAllView.superclass.constructor.call(this, {
					id : 'BpPersionDirProAllView'+this.subType,
					title : '全部项目',
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
		if (this.managerType == "bulkProduct") {
			this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-protect',
									text : '公示信息维护',
									xtype : 'button',
									scope : this,
									hidden : isGranted('persionDirProKeep')?false:true,
									handler : this.projectKeep
								}]
					});
		} else if (this.managerType == "successProduct") {
			this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-detail',
									text : '项目编辑',
									xtype : 'button',
									scope : this,
									
									handler : this.seeMappingObligationInfo
								}]
					});
		}
      var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计（元）';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			//id : 'BpPersionDirProGrid',
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
					'updateTime', 'keepStat', 'schemeStat','sex', 'age',
					'education', 'marriage', 'userName', 'monthIncome',
					'address', 'companyIndustry', 'companyScale', 'position',
					'workTime', 'workCity', 'houseProperty', 'houseLoan','loanId',
					'vehicleProperty', 'vehicleLoan','payAcctualType','keepStat'],
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
						width : 100
					}, {
						header : '项目名称',
						summaryRenderer : totalMoney,
						dataIndex : 'proName',
						width : 250
					}, {
						header : '项目编号',
						dataIndex : 'proNumber',
						width : 170
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
						align : 'center',
						dataIndex : 'payAcctualType',
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
					},{
						header : '维护状态',
						align : 'center',
						dataIndex : 'keepStat',
						renderer : function(v){
							if(v==0){
								return '未维护'
							}else if(v==1){
								return '已维护'
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

	// 项目维护
	projectKeep : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var stype =this.subType;
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			if(record.data.keepStat==1 && isGranted('persionDirProKeep')==false){
				Ext.ux.Toast.msg('操作信息', '该条记录为已维护项目，您没有维护已公示项目的权限，请联系管理人员开通此权限');
				return true;
			}
			//判断是否为线上借款
			if(typeof(stype) != undefined && 'onlineopen' == stype){
					var loanId=record.data.loanId
			    	if(record.data.keepStat==1){
				   var gridPanel=this.gridPanel
				   var userId=0;
					Ext.Ajax.request({
									url : __ctxPath + '/p2p/getBpFinanceApplyUser.do?loanId='+loanId,
									method:'post',
									async:false,
									success: function(resp,opts) {
										var respText = resp.responseText;
										var alarm_fields = Ext.util.JSON.decode(respText);
									    userId=alarm_fields.data.userID;
									}
								});
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/persion/getInfoPlPersionDirProKeep.do",
					method : 'POST',
					scope:this,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						new PlPersionDirProKeepOnlineForm({
					    gp:gridPanel,
					    keepId:obj.keepId,
					    record:record,// 个人直投项目信息
					    keep:false,
					    proType:"P_Dir",
					    proIdupload:record.data.pdirProId,
					    userId:userId,
				        tablename:"bp_persion_dir_pro"
					}).show();
						
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : {
						type : "P_Dir",
						id:record.data.pdirProId
					}
				});
			}else{
					Ext.Ajax.request({
									url : __ctxPath + '/p2p/getBpFinanceApplyUser.do?loanId='+loanId,
									method:'post',
									async:false,
									success: function(resp,opts) {
										var respText = resp.responseText;
										var alarm_fields = Ext.util.JSON.decode(respText);
										var data=alarm_fields.data;
										 new PlPersionDirProKeepOnlineForm({
					                             gp:this.gridPanel,
					                             record:record,// 个人直投项目信息
					                             keep:true,
					                             proType:"P_Dir",
					                             userId:data.userID,
					                             productId:data.productId,
					                             proIdupload:record.data.pdirProId,
					                             tablename:"bp_persion_dir_pro"
					                           }).show();
									}
								});
			}
			}else{
			  	if(record.data.keepStat==1){
				var gridPanel=this.gridPanel
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/persion/getInfoPlPersionDirProKeep.do",
					method : 'POST',
					scope:this,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						new PlPersionDirProKeepForm({
					    gp:gridPanel,
					    keepId:obj.keepId,
					    record:record,// 个人直投项目信息
					    keep:false,
					    proType:"P_Dir",
					    proIdupload:record.data.pdirProId,
				        tablename:"bp_persion_dir_pro"
					}).show();
						
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : {
						type : "P_Dir",
						id:record.data.pdirProId
					}
				});
			}else{
				new PlPersionDirProKeepForm({
					    gp:this.gridPanel,
					    record:record,// 个人直投项目信息
					    keep:true,
					    proType:"P_Dir",
						proIdupload:record.data.pdirProId,
						tablename:"bp_persion_dir_pro"
					}).show();
			}
	   	 }
		
		}

	}
});
