/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
investValueInformation = Ext.extend(Ext.Window, {
	name : "investValueInformation",
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg.id != "undefined") {
			this.id = _cfg.id;
		}
		if (_cfg.date != "undefined") {
			this.date = _cfg.date;
		}
		if (_cfg.startDate != "undefined") {
			this.startDate = _cfg.startDate;
		}
		if (_cfg.endDate != "undefined") {
			this.endDate = _cfg.endDate;
		}
		if (_cfg.settlementReviewerPayId != "undefined") {
			this.settlementReviewerPayId = _cfg.settlementReviewerPayId;
		}
		Ext.applyIf(this, _cfg);
		var leftwidth=80;
		// 初始化组件
		this.initUIComponents()
		investValueInformation.superclass.constructor.call(this, {
			columnWidth : .8,
			height : 300,
			width : 1200,
			layout : 'form',
			labelWidth : leftwidth,
			items : [{
				xtype : 'panel',
				border : false,
				bodyStyle : 'margin-top:-5px',
				html : '<br/><B><font class="x-myZW-fieldset-title">【详情】</font></B>'
			},this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		this.topbar = new Ext.Toolbar({
		items : [/*{
								iconCls : 'btn-add',
								text : '生成',
								xtype : 'button',
								scope : this,
								handler : this.autocreate
							}*/]
		
		})
		var field = Ext.data.Record.create([{
					name : 'intentDate'
				}, {
					name : 'incomeMoney'
				},{
					name : 'truename'
				},{
					name : 'loginname'
				},{
					name : 'bidProName'
				}]);
		var url = __ctxPath
				+ "/web/listSettleInformationSettlementReviewerPay.do?date="+this.date;
		var jStore = new Ext.data.JsonStore({
					url : url,
					root : 'result',
					fields : field
				});
		jStore.load({
					params : {
		// bidPlanId : this.bidPlanId
					}
				});
		this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
				});
				/*jStore.load({
					params : {
					}
				});*/
		this.gridPanel = new HT.GridPanel({
			border : false,
			name : 'gridPanel',
			scope : this,
			store : jStore,
			tbar :   this.topbar ,
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			rowActions : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			sm : this.projectFundsm,
			plugins : [summary],
			columns : [{
						header : '日期',
						align : 'center',
						dataIndex : 'intentDate'
					},{
						header : '当日保有量(元)',
						align : 'center',
						dataIndex : 'incomeMoney'
					},{
						header : '投资人姓名',
						align : 'center',
						dataIndex : 'truename'
					},{
						header : '投资人账号',
						align : 'center',
						dataIndex : 'loginname'
					},{
						header : '标的名字',
						align : 'center',
						dataIndex: 'bidProName'
					}],
			listeners : {
				scope : this,
			}
		});

	},
	autocreate : function() {
		var startDate = this.ownerCt.getCmpByName('startDate').getValue();
		var endDate = this.ownerCt.getCmpByName('settlementReviewerPay.settlementEndDate').getValue();
		var investValue = this.ownerCt.getCmpByName("investValue");
//		var pbar = Ext.MessageBox.wait('款项生成中，可能需要较长时间，请耐心等待...','请等待',{
//				interval:200,
//			    increment:15
//			});
		var params1 = {
				id : this.organizationId,
				startDate : startDate,
				endDate : endDate,
				settlementReviewerPayId : this.settlementReviewerPayId
		};
		var gridPanel1=this.gridPanel;
		var gridstore = gridPanel1.getStore();
		gridstore.on('beforeload', function(gridstore, o) {
					Ext.apply(o.params, params1);
				});
		gridPanel1.getStore().reload();
		/*Ext.Ajax.request({
			url : __ctxPath + "/web/listSettleSettlementReviewerPay.do",
			method : 'POST',
			scope:this,
			timeout: 600000,
			success : function(response, request) {debugger
			pbar.getDialog().close();
				obj = Ext.util.JSON.decode(response.responseText);
			},
			failure : function(response,request) {
				pbar.getDialog().close();
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			},
			params : {
				id : this.organizationId,
				startDate : startDate,
				endDate : endDate,
				settlementReviewerPayId : this.settlementReviewerPayId
			}
		});*/
	}
	
	
});
