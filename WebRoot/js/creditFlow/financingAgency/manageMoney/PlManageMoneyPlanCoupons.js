/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
PlManageMoneyPlanCoupons = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlManageMoneyPlanCoupons.superclass.constructor.call(this, {
					id : 'PlManageMoneyPlanCoupons' + this.keystr,
					title : '投资人奖励明细台账',
					layout : 'anchor',
					anchor : '100%',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
	this.topbar = new Ext.Toolbar({
		items : [{
					iconCls : 'btn-add',
					text : '生成',
					xtype : 'button',
					scope : this,
					hidden : this.seeIsCreateIntent,
					handler : this.autocreate
				}]
		})
		
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
	//		singleSelect : true,
			// 使用RowActions
			id : 'PlManageMoneyPlanCoupons' + this.keystr,
			height : 180,
			showPaging:false,
							url : __ctxPath
					+ "/creditFlow/financingAgency/listCouponsPlMmOrderAssignInterest.do?mmplanId="+this.mmplanId,
			fields : [{
						name : 'assignInterestId',
						type : 'int'
					}, 'orderId', 'investPersonId', 'investPersonName',
					'mmplanId', 'mmName', 'fundType', 'incomeMoney','afterMoney','factDate',
					'intentDate','periods','payMoney'],
			columns : [{
						header : 'assignInterestId',
						dataIndex : 'assignInterestId',
						hidden : true
					}, {
						header : '投资人',
						dataIndex : 'investPersonName'
					},{
						header : '期数',
						dataIndex : 'periods',
						
						renderer:function(v){
							if(v==null){return "";}
							else{
							return "第"+v+"期"
							}
						}
					}, {
						header : '资金类型',
						dataIndex : 'fundType',
					
						renderer:function(val){
							if(val=="couponInterest"){
								return "奖励利息";
							}else if(val=="subjoinInterest"){
								return "奖励利息";
							}else if(val=="principalCoupons"){
								return "奖励本金";
							}else if(val=="commoninterest"){
								return "奖励利息";
							}else if(val=="raiseinterest"){
								return "募集期奖励";
							}
						}
					}, {
						header : '奖励金额',
						dataIndex : 'incomeMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					},{
						header : '计划派息日期',
						dataIndex : 'intentDate'
					}]
				// end of columns
		});

	},
	autocreate : function() {
				var startinInterestTime=null;
				var endinInterestTime=null;
				var mmplanId=null;
				var gridPanel=this.gridPanel;
				var message=""
			    startinInterestTime=this.getOriginalContainer().getCmpByName('UPlanFormPanel').getCmpByName("plManageMoneyPlan.startinInterestTime").getValue();
			    endinInterestTime=this.getOriginalContainer().getCmpByName('UPlanFormPanel').getCmpByName("plManageMoneyPlan.endinInterestTime").getValue();
			    mmplanId=this.mmplanId;
			    if (startinInterestTime == "" || startinInterestTime == null) {
				 	message = "起息日期";
			    }
				if(message != "") {
					Ext.MessageBox.show({
						title : '操作信息',
						msg : message + '不能为空',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					return null;
				}
				var params={
				    'startinInterestTime': startinInterestTime,
				    'endinInterestTime': endinInterestTime,
				    'mmplanId': mmplanId
				}
				var pbar = Ext.MessageBox.wait('款项生成中，可能需要较长时间，请耐心等待...','请等待',{
					interval:200,
				    increment:15
				});
				
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/createCouponsIntentPlManageMoneyPlan.do",
					method : 'POST',
					scope:this,
					timeout: 600000,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('操作信息', '生成成功!');
						pbar.getDialog().close();
						var gridstore = gridPanel.getStore();
						gridPanel.getStore().reload();
					},
					failure : function(response,request) {
						pbar.getDialog().close();
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : params
			    });
	        }
	
});
