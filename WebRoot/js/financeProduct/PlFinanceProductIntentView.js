//PlFinanceProductUserAccountInfo.js
/**
 * @author
 * @class PlFinanceProductIntentView
 * @extends Ext.Panel
 * @description [PlFinanceProductIntentView]管理
 * @company 智维软件
 * @createtime:
 */
PlFinanceProductIntentView =  Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlFinanceProductIntentView.superclass.constructor.call(this, {
							id : 'PlFinanceProductIntentView',
							region : 'center',
							layout : 'border',
							modal : true,
							height :600,
							width : 900,
							maximizable : true,
							title : '派息记录台账',
							iconCls :'btn-team30',
							items : [this.searchPanel,this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
					this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
							anchor : '96%',
							keys : [{
								key : Ext.EventObject.ENTER,
								fn : this.search,
								scope : this
							}, {
								key : Ext.EventObject.ESC,
								fn : this.reset,
								scope : this
							}],
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
							items : [{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{fieldLabel : '派息日期',
											name : "dealDateS",
											format : 'Y-m-d',
											xtype : 'datefield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{fieldLabel : '至',
											name : "dealDateE",
											format : 'Y-m-d',
											xtype : 'datefield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '会员名称',
											name : "loginName",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '开户名',
											name : "userName",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '查询',
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										}]
									},{
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '重置',
											scope : this,
											iconCls : 'btn-reset',
											handler : this.reset
										}]
									}]
						});
				this.topbar = new Ext.Toolbar({
					items : [ {
									iconCls : 'btn-xls',
									text : '导出到excel',
									xtype : 'button',
									scope:this,
									handler : this.exportExcel
								},{
									iconCls : 'btn-nowmoney',
									text : '今日派息',
									xtype : 'button',
									scope:this,
									handler : this.nowDateIntentRecord
								},{
									iconCls : 'btn-repair',
									text : '补派息',
									xtype : 'button',
									scope:this,
									handler : this.beforeIntentRecord
								}]
				});
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计（元）';
				}
				// 初始化搜索条件Panel
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'PlFinanceProductIntentGrid',
					url : __ctxPath + "/financeProduct/listPlFinanceProductUserAccountInfo.do?dealtype=3&dealStatus=2",
					fields : [{
									name : 'id',
									type : 'int'
							}
							,'userId','userName','userloginName','productId','productName','currentMoney',
							'amont','dealtype','dealtypeName','dealStatus','dealStatusName','remark',
							'requestNo','dealDate'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								align:'center',
								hidden : true
							},{
								header : '派息日期',	
								width : 150,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'dealDate'
							},{
								header : '会员账号',
								width : 180,
								summaryRenderer : totalMoney,
								align : 'center',
								dataIndex : 'userloginName'
							},{
								header : '开户名',
								width : 180,
								dataIndex : 'userName'
							},{
								header : '产品名称',
								width : 180,
								dataIndex : 'productName'
							},{
								header : '派息金额(元)',
								dataIndex : 'amont',
								width : 150,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00');
                         	    }
							},{
								header : '账户金额（元）',
								dataIndex : 'currentMoney',
								width : 150,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00');
                         	    }
							},{
								header : '交易流水号',
								width : 180,
								align : 'center',
								dataIndex : 'requestNo'
							},{
								header : '备注',
								width : 180,
								align : 'center',
								dataIndex : 'remark'
							}]
					});

				 //this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
		// 重置查询表单

		reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ');
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
			},
			exportExcel:function(){
					var userName=this.searchPanel.getCmpByName('userName').getValue();
					var loginName=this.searchPanel.getCmpByName('loginName').getValue();
					
					var dealDateS=this.searchPanel.getCmpByName('dealDateS').getValue();
					if(dealDateS!=null&&dealDateS!=""&&dealDateS!="undefined"){
						dealDateS=dealDateS.format('Y-m-d');
					}
					var dealDateE=this.searchPanel.getCmpByName('dealDateE').getValue();
					if(dealDateE!=null&&dealDateE!=""&&dealDateE!="undefined"){
						dealDateE=dealDateE.format('Y-m-d');
					}
					window.open( __ctxPath + "/financeProduct/exportIntentToExcelPlFinanceProductUserAccountInfo.do?dealtype=3&dealStatus=2&userName"+userName+"&loginName="+loginName+"&dealDateS="+dealDateS+"&dealDateE="+dealDateE);
			},
			
			nowDateIntentRecord:function(){
				var grid =this.gridPanel;
				Ext.Msg.confirm('信息确认', '确认派息么？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath+ '/financeProduct/createLateDayIntentRecordPlFinanceProductUserAccountInfo.do',
									method : 'post',
									success : function() {
										Ext.ux.Toast.msg("信息提示", "成功派息！");
										if (grid != null) {
											grid.getStore().reload({});
										}
									}
								});
					}
				});
			},
			beforeIntentRecord:function(){
				var grid =this.gridPanel;
				new PlFianceProductIntentDay({
					gridPanel:grid
				}).show();
			},
			test:function(){
				var grid =this.gridPanel;
				Ext.Msg.confirm('信息确认', '确认补派息么？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath+ '/financeProduct/creteIntentRecordPlFinanceProductUserAccountInfo.do?days=2015-08-10',
									method : 'post',
									success : function() {
										Ext.ux.Toast.msg("信息提示", "成功派息！");
										if (grid != null) {
											grid.getStore().reload({});
										}
									}
								});
					}
				});
			
			},
			
			openliushuiwin : function() {}
		});


