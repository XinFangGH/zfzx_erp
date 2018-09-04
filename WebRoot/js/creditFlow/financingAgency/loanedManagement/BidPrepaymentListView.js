/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
BidPrepaymentListView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BidPrepaymentListView.superclass.constructor.call(this, {
			id : 'BidPrepaymentListView'+this.proType,
			title : '提前还款查询',
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team30',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计';
		}
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			border : false,
			region : 'north',
			height : 70,
			anchor : '100%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [{
				columnWidth :1,
				layout : 'column',
				border : false,
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
						fieldLabel : '招标项目编号',
						name : 'bidProNumber',
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
						fieldLabel : '提前还款日期',
						format : 'Y-m-d',
						name : 'startDate',
						anchor : '100%'
					},{
						xtype : 'textfield',
						fieldLabel : '借款项目名称',
						name : 'proName',
						anchor : '100%'
					}]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 30,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : 'datefield',
						fieldLabel : '至',
						name:'endDate',
						format : 'Y-m-d',
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
						anchor : "100%",
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					},{
						xtype : 'button',
						text : '重置',
						anchor : "100%",
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
				}]
			}]

		})

		this.topbar = new Ext.Toolbar( {
			items : [{
				iconCls : 'btn-xls',
				text : '导出到Excle',
				xtype : 'button',
				scope : this,
				handler : this.toExcel
			}, {
				iconCls : 'btn-detail',
				text : '查看详细',
				xtype : 'button',
				scope : this,
				handler : this.seeRs
			}, {
				iconCls : 'btn-detail',
				text : '查看款项计划表',
				xtype : 'button',
				scope : this,
				handler : this.seeFundIntentRs
			}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath + "/smallLoan/finance/allListSlEarlyRepaymentRecord.do?proType="+this.proType,
			plugins : [summary],
			fields : [ {
				name : 'slEarlyRepaymentId',
				type : 'long'
			}, 'earlyDate', 'earlyProjectMoney', 'bidPlanId', 'bidProName',
					'bidProNumber', 'penaltyDays', 'proName', 'loanInterestMoney', 'interestPenaltyMoney',
					'consultationMoney','serviceMoney','proType','allMoney','projectId','fundProjectId'],
			columns : [ {
				header : '招标项目名称',
				dataIndex : 'bidProName'
			}, {
				header : '招标项目编号',
				dataIndex : 'bidProNumber'
			}, {
				header : '借款项目名称',
				summaryRenderer : totalMoney,
				dataIndex : 'proName'
			}, {
				header : '提前还款日期',
				align:'center',
				dataIndex : 'earlyDate'
			}, {
				header : '提前还款本金',
				dataIndex : 'earlyProjectMoney',
				align : 'right',
				summaryType : 'sum',
				renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
					if(null!=value && value!='' && value!='0') {
						return  Ext.util.Format.number(value,',000,000,000.00')+ "元"
					}else{
						return '';
					}
				}
			}, {
				header : '截止利息金额',
				dataIndex : 'loanInterestMoney',
				align : 'right',
				summaryType : 'sum',
				renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
					if(null!=value && value!='' && value!='0') {
						return  Ext.util.Format.number(value,',000,000,000.00')+ "元"
					}else{
						return '';
					}
				}
			}, {
				header : '截止咨询费金额',
				dataIndex : 'consultationMoney',
				align : 'right',
				summaryType : 'sum',
				renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
					if(null!=value && value!='' && value!='0') {
						return  Ext.util.Format.number(value,',000,000,000.00')+ "元"
					}else{
						return '';
					}
				}
			}, {
				header : '截止服务费金额',
				dataIndex : 'serviceMoney',
				align : 'right',
				summaryType : 'sum',
				renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
					if(null!=value && value!='' && value!='0') {
						return  Ext.util.Format.number(value,',000,000,000.00')+ "元"
					}else{
						return '';
					}
				}
			}, {
				header : '补偿息天数',
				dataIndex : 'penaltyDays',
				renderer : function(v) {
					                 return v+"天";
				                   }

			}, {
				header : '补偿息金额',
				dataIndex : 'interestPenaltyMoney',
				align : 'right',
				summaryType : 'sum',
				renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
					if(null!=value && value!='' && value!='0') {
						return  Ext.util.Format.number(value,',000,000,000.00')+ "元"
					}else{
						return '';
					}
				}
			}, {
				header : '合计',
				dataIndex : 'allMoney',
				align : 'right',
				summaryType : 'sum',
				renderer : function(value, metaData, record, rowIndex,
								colIndex, store){
					if(null!=value && value!='' && value!='0') {
						return  Ext.util.Format.number(value,',000,000,000.00')+ "元"
					}else{
						return '';
					}
				}
			}]
		//end of columns
				});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
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
						bidPlanId : record.data.bidPlanId,
						projectId:record.data.projectId,
						fundProjectId:record.data.fundProjectId,
						proType:record.data.proType,
						bidProName:record.data.bidProName,
						oppositeType:obj.data.oppositeType,
						businessType:'SmallLoan'
					})
					
					 contentPanel.add(bidPlanInfoForm);
					contentPanel.activate('PlBidPlanInfoForm_'+record.data.bidPlanId);
				},
				failure : function(response,request) {
					Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
				},
				params : {
					projectId:record.data.fundProjectId
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
				bidPlanId : record.data.bidPlanId,
				projectId:record.data.projectId,
				fundProjectId:record.data.fundProjectId,
				bidProName:record.data.bidProName,
				businessType:'SmallLoan'
			}).show()
		}
	},
  		//导出到Excel
	toExcel:function(){
		var bidProName=this.searchPanel.getCmpByName('bidProName').getValue();
		var bidProNumber=this.searchPanel.getCmpByName('bidProNumber').getValue();
		var proName=this.searchPanel.getCmpByName('proName').getValue();
		var startDate=this.searchPanel.getCmpByName('startDate').getValue();
		var endDate=this.searchPanel.getCmpByName('endDate').getValue();
	   if(startDate!=null&&startDate!="undefined"&&startDate!=""){
			startDate=startDate.format('Y-m-d');
		}
	   if(endDate!=null&&endDate!=""&&endDate!="undefined"){
			endDate=endDate.format('Y-m-d');
		}
		window.open(__ctxPath + '/smallLoan/finance/toExcelSlEarlyRepaymentRecord.do?proType='+this.proType+'&bidProName='+bidProName+'&bidProNumber='+bidProNumber+"&proName="+proName+"&startDate="+startDate+"&endDate="+endDate,'_blank');
	}
});
