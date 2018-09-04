/**
 * @author
 * @class PlFinanceProductUserAccount
 * @extends Ext.Panel
 * @description [PlFinanceProductUserAccount]管理
 * @company 智维软件
 * @createtime:
 */
PlFinanceProductUserAccount =  Ext.extend(Ext.Panel, {
			querySql:"",
			title:"活期业务账户查询",
			iconCls :'btn-team30',
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (typeof(_cfg.accountType) != "undefined" && _cfg.accountType=="1") {//带有转入
					this.title="活期账户锁定";
					this.querySql="?accountStatus=1";
					this.accountType=1;
				}else if(typeof(_cfg.accountType) != "undefined" && _cfg.accountType=="-1") {//带有转出
					this.title="活期账户解锁";
					this.querySql="?accountStatus=-1";
					this.accountType=2;
				}
				
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlFinanceProductUserAccount.superclass.constructor.call(this, {
							id : 'PlFinanceProductUserAccount'+this.accountType+this.buttonType,
							region : 'center',
							layout : 'border',
							modal : true,
							height :600,
							width : 900,
							maximizable : true,
							title : this.title,
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
										items : [{fieldLabel : '开户日期',
											name : "openDateS",
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
											name : "openDateE",
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
											fieldLabel : '开户名',
											name : "userName",
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
											fieldLabel : '会员账号',
											name : "loginName",
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
								}, {
									iconCls : 'btn-bind',
									text : '锁定',
									xtype : 'button',
									scope:this,
									hidden:this.buttonType==1?false:true,
									handler : this.bindAccount
								}, {
									iconCls : 'btn-ok',
									text : '解锁',
									xtype : 'button',
									scope:this,
									hidden:this.buttonType==2?false:true,
									handler : this.openAccount
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
		            	forceFit:true 
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					url : __ctxPath + "/financeProduct/listPlFinanceProductUseraccount.do"+this.querySql,
					fields : [{
									name : 'id',
									type : 'int'
							}
							,'userId','userName','userloginName','productId','productName','openDate','accountStatus','modifyDate'
							,'currentMoney','totalIntestMoney','yesterdayEarn','accountStatus'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								align:'center',
								hidden : true
							},{
								header : '会员账号',
								width : 180,
								 summaryRenderer : totalMoney,
								align : 'center',
								dataIndex : 'userloginName'
							},{
								header : '开户名',
								width : 180,
								align : 'center',
								dataIndex : 'userName'
							},{
								header : '账户金额(元)',
								dataIndex : 'currentMoney',
								width : 150,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00')
                         	    }
							},{
								header : '累计收益(元)',
								dataIndex : 'totalIntestMoney',
								width : 150,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00')
                         	    }
							},{
								header : '昨日收益(元)',
								dataIndex : 'yesterdayEarn',
								width : 150,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00')
                         	    }
							},{
								header : '开户日期',	
								width : 150,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'openDate'
							},{
								header : '状态',	
								width : 100,
								align : 'center',
								dataIndex : 'accountStatus',
								renderer:function(v){
								 	if(v==-1){
								 		return "锁定";
								 	}else{
								 		return "开启";
								 	}

                         	    }
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
					
					var openDateS=this.searchPanel.getCmpByName('openDateS').getValue();
					if(openDateS!=null&&openDateS!=""&&openDateS!="undefined"){
						openDateS=openDateS.format('Y-m-d');
					}
					var openDateE=this.searchPanel.getCmpByName('openDateE').getValue();
					if(openDateE!=null&&openDateE!=""&&openDateE!="undefined"){
						openDateE=openDateE.format('Y-m-d');
					}
					if(this.querySql!=""){
						window.open( __ctxPath + "/financeProduct/exportProductUseraccountToExcelPlFinanceProductUseraccount.do"+this.querySql+"userName"+userName+"&loginName="+loginName+"&openDateS="+openDateS+"&openDateE="+openDateE);
					}else{
						window.open( __ctxPath + "/financeProduct/exportProductUseraccountToExcelPlFinanceProductUseraccount.do?userName"+userName+"&loginName="+loginName+"&openDateS="+openDateS+"&openDateE="+openDateE);

					}
		},
			openliushuiwin : function() {},
			
			//锁定账户 
			bindAccount:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					var id=s[0].data.id
					Ext.Msg.confirm('信息确认', '确认锁定账户？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : __ctxPath+ '/financeProduct/updateAccountStatusPlFinanceProductUseraccount.do?id='+id+"&accountStatus=-1",
										method : 'post',
										success : function() {
											Ext.ux.Toast.msg("信息提示", "成功锁定！");
											if (grid != null) {
												grid.getStore().reload({});
											}
										}
									});
						}
					});
					
				}
			
			},
			//解锁账户 
			openAccount:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					var id=s[0].data.id
					Ext.Msg.confirm('信息确认', '确认解锁账户？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : __ctxPath+ '/financeProduct/updateAccountStatusPlFinanceProductUseraccount.do?id='+id+"&accountStatus=1",
										method : 'post',
										success : function() {
											Ext.ux.Toast.msg("信息提示", "成功解锁！");
											if (grid != null) {
												grid.getStore().reload({});
											}
										}
									});
						}
					});
					
				}
			
			}
		});

