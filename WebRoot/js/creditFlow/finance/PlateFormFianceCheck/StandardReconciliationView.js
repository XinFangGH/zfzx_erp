/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
StandardReconciliationView = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.planId)!="undefined")
				{
				      this.planId=_cfg.planId;
				      this.borrowPersonList=_cfg.borrowPersonList;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				StandardReconciliationView.superclass.constructor.call(this, {
							id : 'StandardReconciliationView',
							layout : 'fit',
							items :  this.gridPanel,
							modal : true,
							height : 440,
							width : screen.width*0.6,
							maximizable : true,
							title : '投资人列表'
							
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					// 使用RowActions
					rowActions : false,
					id : 'StandardReconciliationViewGird',
					isautoLoad:true,
					url : __ctxPath + "/creditFlow/creditAssignment/bank/getbidInfobyIdObSystemAccount.do?planId="+this.planId+"&borrowPersonList="+this.borrowPersonList,
					fields : ['buyMoney','investPersonName','buyDatetime','borrowPersonList'
							],
					columns : [{
								header : '投资人姓名',
								align : 'center',
								dataIndex : 'investPersonName',
								width : 70
							}, {
								header : "投资金额",
								dataIndex : 'buyMoney',
								align :'center',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : '投资时间',
								dataIndex : 'buyDatetime',
								width:80,
								align : 'center'
							} ,{
								header : '资金使用人',
								align : 'center',
								dataIndex : '',
								width : 70
							}
						]
					});

				this.gridPanel.addListener('cellclick', this.cellClick);

			},
			// 重置查询表单
			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ'+tabflag);
			//	obj.setEditable(false);
				var arrStore= new Ext.data.SimpleStore({});
				obj.clearValue();
                obj.store = arrStore;
			    arrStore.load({"callback":test});
			    function test(r){
			    	if (obj.view) { // 刷新视图,避免视图值与实际值不相符
			    		obj.view.setStore(arrStore);
			        }
								       
								    }
			},
			// 按条件搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			}
	
});