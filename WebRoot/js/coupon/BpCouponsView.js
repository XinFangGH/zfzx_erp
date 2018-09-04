/**
 * @author
 * @class BpCouponsView
 * @extends Ext.Panel
 * @description [BpCoupons]管理
 * @company 智维软件
 * @createtime:
 */
 
 BpCouponsView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCouponsView.superclass.constructor.call(this, {
							id : 'BpCouponsView',
							title : "优惠券单个派发",
							region : 'center',
							layout : 'border',
							 iconCls:"menu-finance",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
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
										columnWidth : .3,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
												fieldLabel : '优惠券类型',
												hiddenName : 'couponType',
												readOnly : this.isReadOnly,
												anchor : '100%',
												xtype : 'combo',
												mode : 'local',
												valueField : 'value',
												editable : false,
												displayField : 'item',
												store : new Ext.data.SimpleStore({
													fields : ["item","value"],
													data : [["优惠券","1"], 
															/*["体验券","2"],*/
															["加息券","3"]
														    ]
												}),
												triggerAction : "all"
												
												
										},{
											fieldLabel : '优惠券状态',
											hiddenName : 'couponstatus',
											anchor : "100%",
											xtype : 'combo',
											mode : 'local',
											valueField : 'value',
											editable : false,
											displayField : 'item',
											store : new Ext.data.SimpleStore({
												fields : ["item","value"],
												data : [["不限",""],["未派发","0"], ["已禁用","3"], ["已过期","4"]]
											}),
											triggerAction : "all"
										}]
							         },{
							         	columnWidth : .3,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '过期日',
											name : "couponEndDate_S",
											xtype : 'datefield',
											format : 'Y-m-d'
											
										},{
											fieldLabel : '优惠券编号',
											name : "couponNumber",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .3,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '至',
											name : "couponEndDate_E",
											xtype : 'datefield',
											format : 'Y-m-d',
											labelSeparator : ""
										},{
											fieldLabel : '来源',
											name : "resourceName",
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
										},{
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}]
						});

				this.topbar = new Ext.Toolbar({
					items : [{
									iconCls : 'btn-distribute',
									text : '派发',
									xtype : 'button',
									scope : this,
									handler : this.couponsDistribute
								},{
									iconCls : 'btn-forbid',
									text : '禁用',
									xtype : 'button',
									scope : this,
									handler : this.forbidcoupon
								}, {
									iconCls : 'btn-startup',
									text : '启用',
									xtype : 'button',
									scope:this,
									handler : this.enablecoupon
								}, {
									iconCls : 'btn-xls',
									text : '导出到excel',
									xtype : 'button',
									scope:this,
									hidden : true,
									handler : this.removeSelRs
								}]
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					//sm : this.					
					viewConfig: {  
		            	forceFit:false  
		            },
		            autoExpandColumn:10,
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'BpCouponsGrid',
					url : __ctxPath + "/coupon/listBpCoupons.do",
					fields : [{
									name : 'couponId',
									type : 'int'
							}
							,'couponNumber','couponResourceType','resourceId','couponStatus','bindOpratorName','resourceName'
							,'bindOpratorId','bindOpraterDate','belongUserName','belongUserId','useProjectName'
							,'useProjectNumber','useProjectId','useProjectType','useTime','createDate'
							,'createName','createUserId','couponType','companyId','couponTypeValue','couponValue','couponStartDate','couponEndDate'],
					columns : [{
								header : 'couponId',
								dataIndex : 'couponId',
								hidden : true
							},{
								header : '优惠券类型',
								width : 80,
								dataIndex : 'couponType',
								align : 'center',
								renderer : function(val){
									if(val=="1"){
										return "优惠券";
									}
									if(val=="2"){
										return "体验券";
									}
									if(val=="3"){
										return "加息券";
									}
								},
								summaryRenderer : totalMoney
							},{
								header : '优惠券编号',
								width :120,
								dataIndex : 'couponNumber',
								align : 'center'
							},{
								header : '面值',
								dataIndex : 'couponValue',
								width : 100,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v,a,b,c,d,e,f){
										if(b.data.couponType=="3"){
											return Ext.util.Format.number(v,
											',000,000,000.00')
											+ "%"
									}else{
										return Ext.util.Format.number(v,
											',000,000,000.00')
											+ "元"
									}
									
								}
							},{
								header : '有效开始时间',	
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'couponStartDate'
							},{
								header : '过期时间',	
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'couponEndDate'
							},{
								header : '状态',	
								width : 50,
								align : 'center',
								dataIndex : 'couponStatus',
								renderer:function(v){
								 	if(v==0){
								 		return "未派发";
								 	}else if(v==3){
								 		return "已禁用";
								 	}else if(v==4){
								 		return "已过期";
								 	}else{
								 		return "";
								 	}

                         	    }
							},{
								header : '生成时间',	
								width : 100,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'createDate'
							},{
								header : '说明',	
								width : 100,
								align : 'left',
								dataIndex : 'resourceName'
							}]
					});

				 //this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
			
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
			
			rowClick:function(){
				
			},
			//创建记录
			couponsDistribute : function() {
				var gridPanel =  this.gridPanel;
				var s = gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中要派发的优惠券记录');
					return false;
				}else if(s >1){
					Ext.ux.Toast.msg('操作信息', '请选中一条要派发的优惠券记录');
					return false;
				}else {
					var record=s[0];
					var couponStatus=record.data.couponStatus;
					var couponId=record.data.couponId;
					if(couponStatus==0){
						new CouponsDistributeForm({
							couponId:couponId,
							gridPanel:gridPanel
						}).show();
					}else{
						Ext.ux.Toast.msg('操作信息', '只有未派发的优惠券才能进行派发');
						return false;
					}
				
				}
			},
			//禁用优惠券
			forbidcoupon:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				var thisPanel = this.gridPanel;
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中记录');
					return false;
				}
				var couponStatus=s[0].data.couponStatus;
				if(couponStatus!=0){
					Ext.ux.Toast.msg('操作信息', '只有未派发的才能禁用');
					return false;
				}
				var ids = $getGdSelectedIds(this.gridPanel, 'couponId');
				Ext.Msg.confirm('信息确认', '你确定要禁用选中记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath + '/coupon/forbidOrEnableBpCoupons.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								//var obj = Ext.util.JSON.decode(response.responseText);	
								Ext.ux.Toast.msg('操作信息', "操作成功");
								thisPanel.getStore().reload();
							},
							params : {
								ids : ids,
								couponStatus:"3"
							}
						});
					}
				});
			
			},
			//启用优惠券
			enablecoupon:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				var thisPanel = this.gridPanel;
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中记录');
					return false;
				}
				var couponStatus=s[0].data.couponStatus;
				if(couponStatus!=3){
					Ext.ux.Toast.msg('操作信息', '只有被禁用的才能启用');
					return false;
				}
				var ids = $getGdSelectedIds(this.gridPanel, 'couponId');
				Ext.Msg.confirm('信息确认', '你确定要启用选中记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath + '/coupon/forbidOrEnableBpCoupons.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								//var obj = Ext.util.JSON.decode(response.responseText);	
								Ext.ux.Toast.msg('操作信息', "操作成功");
								thisPanel.getStore().reload();
							},
							params : {
								ids : ids,
								couponStatus:"0"
							}
						});
					}
				});
			
			}
		});

