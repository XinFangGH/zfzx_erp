/**
 * @author
 * @class PlFinanceProductRate
 * @extends Ext.Panel
 * @description [PlFinanceProductRate]管理
 * @company 智维软件
 * @createtime:
 */
PlFinanceProductRate =  Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlFinanceProductRate.superclass.constructor.call(this, {
							id : 'PlFinanceProductRate',
							region : 'center',
							layout : 'border',
							modal : true,
							height :600,
							width : 900,
							maximizable : true,
							title : '产品利率设置',
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
										items : [{
										    fieldLabel : '执行日期',
											name : "intentDateStart",
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
											name : "intentDateEnd",
											format : 'Y-m-d',
											xtype : 'datefield',
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
									iconCls : 'btn-save',
									text : '增加',
									xtype : 'button',
									scope:this,
									handler : this.addform
								}, {
									iconCls : 'btn-update',
									text : '修改',
									xtype : 'button',
									scope:this,
									handler : this.updateform
								},/* {
									iconCls : 'btn-del',
									text : '删除',
									xtype : 'button',
									scope:this,
									handler : this.exportExcel
								},*/{
									iconCls : 'btn-xls',
									text : '导出到excel',
									xtype : 'button',
									scope:this,
									handler : this.exportExcel
								}]
				});
				// 初始化搜索条件Panel
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					viewConfig: {  
		            	forceFit:false  
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'PlFinanceProductRateGrid',
					url : __ctxPath + "/financeProduct/listPlFinanceProductRate.do",
					fields : [{
									name : 'id',
									type : 'int'
							}
							,'intentDate','yearRate','sevYearRate','dayRate','sevDayRate','productId','createDate','modifyDate'
							,'createUserId','createUserName','productName'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							},{
								header : '产品名称',
								width : 180,
								align : 'center',
								dataIndex : 'productName'
							},{
								header : '执行日期',	
								width : 150,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'intentDate'
							},{
								header : '年化利率',
								dataIndex : 'yearRate',
								width : 150,
								align : 'right',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00')+"%"
                         	    }
							},{
								header : '七日平均年化利率',
								dataIndex : 'sevYearRate',
								width : 150,
								align : 'right',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00')+"%"
                         	    }
							},{
								header : '设置日期',	
								width : 150,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'createDate'
							},{
								header : '修改日期',	
								width : 150,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'modifyDate'
							},{
								header : '修改人',
								width : 180,
								align : 'center',
								dataIndex : 'createUserName'
							}/*,{
								header : '状态',	
								width : 100,
								align : 'center',
								dataIndex : 'couponStatus',
								renderer:function(v){
								 	if(v==1){
								 		return "锁定";
								 	}else{
								 		return "开启";
								 	}

                         	    }
							}*/]
					});


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
			//设置年化利率
			addform:function(){
				var gridPanel = this.gridPanel;
			  new PlFinanceProductRateForm({
			   	gridPanel : gridPanel
			   }).show();
			   
			},
			//更新理财专户产品
			updateform:function(){ 
				var gridPanel = this.gridPanel;
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if(selRs.length==0){
				   Ext.ux.Toast.msg('操作信息','请选择记录！');
				   return;
				}else if(selRs.length>1){
				   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
				   return;
				}else{
					var record=selRs[0];
					var days=record.data.intentDate;//计息日期
					var nowDays=new Date().format("Y-m-d");//今天日期
					var date1 = new Date(Date.parse(days.replace(/-/g,"/")));
                    var date2 = new Date(Date.parse(nowDays.replace(/-/g,"/")));
                    
					if(date1>=date2){
						new PlFinanceProductRateUpdateForm({
							gridPanel : gridPanel,
							id:record.data.id
						}).show();
					}else{
						 Ext.ux.Toast.msg('操作信息','今天之前的年化利率不允许修改！');
				   			return;
					}
				}
				
			},
			exportExcel:function(){
					var intentDateStart=this.searchPanel.getCmpByName('intentDateStart').getValue();
					if(intentDateStart!=null&&intentDateStart!=""&&intentDateStart!="undefined"){
						intentDateStart=intentDateStart.format('Y-m-d');
					}
					var intentDateEnd=this.searchPanel.getCmpByName('intentDateEnd').getValue();
					if(intentDateEnd!=null&&intentDateEnd!=""&&intentDateEnd!="undefined"){
						intentDateEnd=intentDateEnd.format('Y-m-d');
					}
					window.open( __ctxPath + "/financeProduct/exportAllProductRatePlFinanceProductRate.do?intentDateStart="+intentDateStart+"&intentDateEnd="+intentDateEnd);
			},
			openliushuiwin : function() {}
		});

