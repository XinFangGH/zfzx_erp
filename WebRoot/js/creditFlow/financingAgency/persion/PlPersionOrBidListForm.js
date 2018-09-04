/**
 * @author
 * @createtime
 * @class PlOrReleaseListForm
 * @extends Ext.Window
 * @description PlOrRelease表单
 * @company 智维软件
 */
PlPersionOrBidListForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlPersionOrBidListForm.superclass.constructor.call(this, {
					id : 'PlPersionOrBidListForm',
					layout : 'border',
					items : [this.gridPanel],
					modal : true,
					height : 500,
					width : 900,
					maximizable : true,
					title : '详细信息',
					buttonAlign : 'center'
					
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'form',
					region : 'north',
					colNums : 3,
					items : [{
								fieldLabel : '包装id',
								name : 'Q_packId_L_EQ',
								flex : 1,
								xtype : 'numberfield'
							}],
					buttons : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});// end of searchPanel
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加方案',
								xtype : 'button',
								scope : this,
								hidden:this.isUnFinish,
								handler : this.createRs
							}, {
								iconCls : 'btn-del',
								text : '删除方案',
								xtype : 'button',
								scope : this,
								hidden:this.isUnFinish,
								handler : this.removeSelRs
							}, {
								iconCls : 'btn-edit',
								text : '编辑方案',
								xtype : 'button',
								scope : this,
							//	hidden:this.isUnFinish,
								handler : this.editRs
							}, {
								iconCls : 'btn-detail',
								text : '查看招标计划详细',
								xtype : 'button',
								scope : this,
								handler : this.seeRs
							}, {
								iconCls : 'btn-edit',
								text : '编辑招标项目名称',
								xtype : 'button',
								scope : this,
								hidden:!this.isUnFinish,
								handler : this.editBidName
							}/*, {
								iconCls : 'btn-add',
								text : '添加自动债权匹配',
								xtype : 'button',
								scope : this,
								hidden:this.rate==100?true:false,
								handler : this.addautomacthRs
							}*/]
				});
				var bidMoneyScale = new Ext.ux.grid.ProgressColumn({
					header : "招标项目占比",
					dataIndex : 'bidMoneyScale',
					width : 100,
					textPst : '%',
					colored : true
				
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			
			id : 'PlPersionOrBidListFormGrid',
			url : __ctxPath
						+ "/creditFlow/financingAgency/listPlBidPlan.do?Q_bpPersionOrPro.porProId_L_EQ=" +this.porProId+"&Q_proType_S_EQ="+this.proType,
			fields : [{
						name : 'bidId',
						type : 'int'
					},{
						name : 'yearInterestRate',
						mapping : 'bpPersionOrPro.yearInterestRate'
					},{
						name : 'biddingTypeId',
						mapping : 'plBiddingType.name'
					},  'bidProName', 'bidProNumber',
					'bidMoney', 'bidMoneyScale',
					'startMoney', 'riseMoney', 'createtime', 'updatetime',
					'state', 'startInterestType', 'bidStartTime',
					'publishSingeTime', 'bidEndTime', 'bidRemark','prepareSellTime','investMoney'],
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
						header : '起息模式',
						dataIndex : 'biddingTypeId'
					}, {
						header : '招标金额(元)',
						dataIndex : 'bidMoney'
					},{
						header : '已投金额(元)',
						dataIndex : 'investMoney'
					}, bidMoneyScale, {
						header : '年利率(%)',
						dataIndex : 'yearInterestRate'
					}, {
						header : '起息日类型',
						dataIndex : 'startInterestType',
						renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                    	if(data==0) return 'T(投标日+1天)';
	                    	if(data==1) return 'T(招标截止日+1天)';
	                    	if(data==2) return 'T(满标日+1天)';
	            }
					}, {
						header : '开始投标时间',
						dataIndex : 'publishSingeTime'
					}, {
						header : '招标截至时间',
						dataIndex : 'bidEndTime'
					}, {
						header : '招标状态',
						dataIndex : 'state',
						renderer : function(value, metaData, record, rowIndex,colIndex, store){
							if(value=='-1'){
								return '已关闭'
							}else if(value=='3'){
								return '已流标'
							}else if(value=='0'){
								return '待发标'
							}else if(value=='1'){
								if(new Date(record.data.prepareSellTime.replace(/-/g,"/")).getTime()>(new Date()).getTime()){
									return '待预售'
								}else if(new Date(record.data.prepareSellTime.replace(/-/g,"/")).getTime()<=(new Date()).getTime() && new Date(record.data.publishSingeTime.replace(/-/g,"/")).getTime()>(new Date()).getTime()){
									return '预售中'
								}else if( new Date(record.data.publishSingeTime.replace(/-/g,"/")).getTime()<=(new Date()).getTime()){
									return '招标中'
								}
							}else if(value=='2'){
								return '已齐标'
							}else if(value=='4'){
								return '已过期'
							}else if(value=='6'){
								return '起息办理中'
							}else if(value=='7'){
								return '还款中'
							}else if(value=='10'){
								return '已完成'
							}else{
								return ''
							}
						}
					}]
				// end of columns
		});
//this.gridPanel.addListener('rowdblclick', this.rowClick);

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
					new PlPersionOrPlanForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		if(this.keepStat==1){//维护后才能添加方案
			new PlPersionOrPlanForm({ 
				        porProId : this.porProId,
				        proType:this.proType,
				        gp:this.gridPanel
					/*	proName:this.proName,
						proNumber:this.proNumber,
						yearInterestRate:this.yearInterestRate,
						monthInterestRate:this.monthInterestRate,
						dayInterestRate:this.dayInterestRate,
						totalInterestRate:this.totalInterestRate,
						interestPeriod:this.interestPeriod,
						payIntersetWay:this.payIntersetWay,
						bidMoney:this.bidMoney,
						loanStarTime:this.loanStarTime,
						loanEndTime:this.loanEndTime,
						residueMoney:this.residueMoney,
						obligatoryPersion:this.obligatoryPersion,
						payAcctualType:this.payAcctualType,
						receiverName:this.receiverName,
						receiverP2PAccount:this.receiverP2PAccount*/
						
			}).show();
		}else{
			Ext.ux.Toast.msg('操作信息', '请先维护项目,并刷新列表页！');
		}
	},
	/*// 创建记录
	addautomacthRs : function() {
		var datas = this;
		Ext.MessageBox.confirm('确认发布？', '发布后将不能修改 ', function(btn) {
		if (btn == 'yes') {
		new PlPersionOrPlanAutomatchForm(
				{ 
			        porProId : this.porProId,
					proName:this.proName,
					proNumber:this.proNumber,
					yearInterestRate:this.yearInterestRate,
					monthInterestRate:this.monthInterestRate,
					dayInterestRate:this.dayInterestRate,
					totalInterestRate:this.totalInterestRate,
					interestPeriod:this.interestPeriod,
					payIntersetWay:this.payIntersetWay,
					bidMoney:this.bidMoney,
					loanStarTime:this.loanStarTime,
					loanEndTime:this.loanEndTime,
					residueMoney:this.residueMoney,
					obligatoryPersion:this.obligatoryPersion,
					proType:this.proType,
					gp:this.gridPanel
				}).show();
			}
		},this);
		
	},*/
		// 编辑
	editRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
	//	if(record.data.state=='0'||record.data.state=='4'){	
		if(record.data.state=='0'){	
			new PlPersionOrPlanForm(
			{           bidId: record.data.bidId,
				        porProId : this.porProId,
				        gp:this.gridPanel,
				        proType:this.proType
					/*	proName:this.proName,
						proNumber:this.proNumber,
						yearInterestRate:this.yearInterestRate,
						monthInterestRate:this.monthInterestRate,
						dayInterestRate:this.dayInterestRate,
						totalInterestRate:this.totalInterestRate,
						interestPeriod:this.interestPeriod,
						payIntersetWay:this.payIntersetWay,
						bidMoney:this.bidMoney,
						loanStarTime:this.loanStarTime,
						loanEndTime:this.loanEndTime,
						residueMoney:this.residueMoney,
						obligatoryPersion:this.obligatoryPersion,
						receiverName:this.receiverName,
						receiverP2PAccount:this.receiverP2PAccount*/
			}
			).show();
		}else{
			Ext.ux.Toast.msg('操作信息', '该方案已非待发标，不能进行编辑!');
		}		
		}

	
	},
	seeRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record = selectRs[0];
			
		new PlPersionOrPlanForm(
		{           bidId: record.data.bidId,
			        porProId : this.porProId,
			        gp:this.gridPanel,
			        proType:this.proType,
			        isReadOnly:true
				  /*proName:this.proName,
					proNumber:this.proNumber,
					yearInterestRate:this.yearInterestRate,
					monthInterestRate:this.monthInterestRate,
					dayInterestRate:this.dayInterestRate,
					totalInterestRate:this.totalInterestRate,
					interestPeriod:this.interestPeriod,
					payIntersetWay:this.payIntersetWay,
					bidMoney:this.bidMoney,
					loanStarTime:this.loanStarTime,
					loanEndTime:this.loanEndTime,
					residueMoney:this.residueMoney,
					obligatoryPersion:this.obligatoryPersion*/
					
		}
		).show();
			
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
	},
	// 把选中ID删除
	removeSelRs : function() {
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择要删除的记录!');
			return;
		}
		for(var i=0; i<selectRs.length; i++){
			if(selectRs[i].data.state != 0){
				Ext.ux.Toast.msg('操作信息', '该方案已发布!');
				return;
		    }
		}
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/financingAgency/multiDelPlBidPlan.do',
			grid : this.gridPanel,
			idName : 'bidId'
		});
	}
});
