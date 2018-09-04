/**
 * @author
 * @class SettlementReviewerPayView
 * @extends Ext.Panel
 * @description [SettlementReviewerPay]管理
 * @company 智维软件
 * @createtime:
 */
SettlementInfoView1 = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SettlementInfoView1.superclass.constructor.call(this, {
					id : 'SettlementInfoView1',
					title : '结算详情',
					layout : 'fit',
					modal : true,
					height : 400,
					width : 650,
					maximizable : true,
					buttonAlign : 'center',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		
		var field = Ext.data.Record.create([{
					name : 'infoId'
				}, {
					name : 'settleMoney'
				},{
					name : 'royaltyRatio'
				},{
					name : 'royaltyMoney'
				},{
					name : 'createDate'
				},{
					name : 'orgId'
				}]);
		var url = __ctxPath + "/web/listSettlementInfo.do";
		var jStore = new Ext.data.JsonStore({
					url : url,
					root : 'result',
					fields : field
				});
//		jStore.load({
//					params : {
//						// bidPlanId : this.bidPlanId
//					}
//				});
		this.projectFundsm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
				});
		this.topbar = new Ext.Toolbar({
		items : [{
				iconCls : 'btn-xls',
				text : '导出excel',
				xtype : 'button',
				scope : this,
				handler :function(){
						window.open(__ctxPath+ '/web/shipsExcelSettlementReviewerPay.do?infoId='+this.infoId,'_blank');
					}
			}]
		
		})
		this.gridPanel = new HT.GridPanel({
			border : false,
			name : 'gridPanel',
			scope : this,
			store : jStore,
//			tbar :   this.topbar ,
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
						header : 'id',
						align : 'center',
						hidden : true,
						dataIndex : 'infoId'
					},{
						header : '日期',
						align : 'center',
						dataIndex : 'createDate'
					},{
						header : '当日保有量(元)',
						align : 'center',
						dataIndex : 'settleMoney'
					},{
						header : '提成比例(%)',
						align : 'center',
						dataIndex : 'royaltyRatio'
					},{
						header : '提成金额(元)',
						align : 'center',
						dataIndex : 'royaltyMoney'
					},{
						header : '操作',
						align : 'center',
						dataIndex: 'operation',
						renderer:function(value, metadata, record, rowIndex,colIndex){
							return '查看详情';
						}
					}],
			listeners : {
				scope : this,
				'cellclick' : function(grid,rowIndex,columnIndex,e){
					var fieldName = grid.getColumnModel().getDataIndex(columnIndex); //Get field name
					var id = grid.getStore().getAt(rowIndex).data.infoId;
					if(fieldName=="operation"){
						new SettlementInfoView({
						infoId : id,
						type : this.type
						}).show();
					}
				}
			}
		});

	}// end of the initComponents()
	
});
