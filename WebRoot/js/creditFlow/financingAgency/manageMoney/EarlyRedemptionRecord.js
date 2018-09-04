/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
EarlyRedemptionRecord = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		EarlyRedemptionRecord.superclass.constructor.call(this, {
			items : [this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-detail',
				text : '查看赎回详细信息',
				xtype : 'button',
				scope : this,
				handler : this.seeRs
			}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			showPaging : false,
			autoHeight : true,
			url : __ctxPath + "/creditFlow/financingAgency/listPlEarlyRedemption.do?orderId="+this.orderId,
			fields : [ {
				name : 'earlyRedemptionId',
				type : 'long'
			}, 'orderId', 'createDate', 'creator', 'earlyMoney',
					'penaltyDays', 'liquidatedDamagesRate', 'liquidatedDamages', 'checkStatus','earlyDate'],
			columns : [{
			   	header : '提前赎回日期',
			   	format : 'Y-m-d',
				dataIndex : 'earlyDate'
			}, {
				header : '提前赎回金额',
				dataIndex : 'earlyMoney',
				align : 'right',
				renderer : function(value){
					if(null!=value){
						return Ext.util.Format.number(value,',000,000,000.00')+"元"
					}
				}
			}, {
				header : '罚息天数',
				dataIndex : 'penaltyDays',
				align : 'right',
				renderer : function(value){
					if(null!=value){
						return value+"天"
					}
				}
				
			}, {
				header : '提起赎回违约金比例',
				dataIndex : 'liquidatedDamagesRate',
				align : 'right',
				renderer : function(value){
					if(null!=value){
						return value+"%"
					}
				}
			}, {
				header : '违约金',
				dataIndex : 'liquidatedDamages',
				align : 'right',
				renderer : function(value){
					if(null!=value){
						return Ext.util.Format.number(value,',000,000,000.00')+"元"
					}
				}
			}, {
				header : '状态',
				dataIndex : 'checkStatus',
				renderer : function(value){
					if(value=='0'){
						return '审批中'
					}else if(value=='1'){
						return '已通过'
					}else if(value=='3'){
						return '已终止'
					}else{
						return ''
					}
				}
			}]
		//end of columns
				});


	},// end of the initComponents()
	
	//编辑Rs
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
			new EarlyRedemptionInfo({
				orderId : record.data.orderId,
				earlyRedemptionId : record.data.earlyRedemptionId
			}).show();
		}	
	}
});
