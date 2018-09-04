//DiscountUseCouponQuery.js
/**
 * @author
 * @class BpCouponsView
 * @extends Ext.Panel
 * @description [BpCoupons]管理
 * @company 智维软件
 * @createtime:
 */
 
 DiscountUseCouponQuery = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				DiscountUseCouponQuery.superclass.constructor.call(this, {
							id : 'DiscountUseCouponQuery',
							title : "优惠券使用明细",
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
										columnWidth : .2,
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
															fields : ["item", "value"],
															data : [["优惠券", "1"], /*["体验券", "2"],*/
																	["加息券", "3"]]
														}),
												triggerAction : "all"
												
												
										},{
											fieldLabel : '投资编号',
											name : "useProjectNumber",
											xtype : 'textfield',
											labelSeparator : ""
										
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 90,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '投资人账号',
											name : "bindOpratorName",
											xtype : 'textfield',
											labelSeparator : ""
											
										},{
											fieldLabel : '优惠券编号',
											name : "couponNumber",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 90,
										defaults : {
											anchor : anchor
										},
										items : [{fieldLabel : '使用日期',
											name : "useTime",
											format : 'Y-m-d',
											xtype : 'datefield',
											labelSeparator : ""
										},{
											fieldLabel : '活动编号',
											name : "activityNumber",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 90,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '止',
											name : "useTime2",
											format : 'Y-m-d',
											xtype : 'datefield',
											labelSeparator : ""
										},{
											fieldLabel : '说明',
											name : "resourceName",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },/*{
							         	columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '来源',
											name : "resourceName",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },*/{
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
					items : [/*{
									iconCls : 'btn-forbid',
									text : '禁用',
									xtype : 'button',
									scope : this,
									handler : this.forbidcoupon
								}, {
									iconCls : 'btn-ok',
									text : '启用',
									xtype : 'button',
									scope:this,
									handler : this.enablecoupon
								}, */{
									iconCls : 'btn-xls',
									text : '导出到excel',
									xtype : 'button',
									scope:this,
									handler : this.exportExcel
								}],
								viewConfig:{
					 forceFit: true // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
					 //scrollOffset: 0 //不加这个的话，会在grid的最右边有个空白，留作滚动条的位置
					  }
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
					viewConfig: {  
		            	forceFit:false  
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'BpCouponsuseGrid',
					url : __ctxPath + "/coupon/bouponBelongListBpCoupons.do?couponstatus=10",
					fields : [{
									name : 'couponId',
									type : 'int'
							}
							,'couponNumber','logginName','couponResourceType','resourceId','couponStatus','bindOpratorName','resourceName','activityNumber'
							,'bindOpratorId','bindOpraterDate','belongUserName','belongUserId','useProjectName'
							,'useProjectNumber','useProjectId','useProjectType','useTime','createDate'
							,'createName','createUserId','companyId','couponType','couponTypeValue','couponValue','couponStartDate','couponEndDate','couponMoney','infoMoney','returnRatio'],
					columns : [{
								header : 'couponId',
								dataIndex : 'couponId',
								hidden : true
							},{
								header : '用户名',
								width : 100,
								align : 'center',
								dataIndex : 'logginName'
							},{
								header : '优惠券来源',
								width : 100,
								align : 'left',
								summaryRenderer : totalMoney,
								dataIndex : 'couponResourceType',
								renderer : function(val){
									if(val=="couponResourceType_active"){
										return "活动优惠券"
									}
									return "普通派发优惠券"
								}
								
							},{
								header : '优惠券类型',
								width : 80,
								dataIndex :'couponType',
								align : 'center',
								renderer : function(val) {
									if (val == "1") {
										return "优惠券";
									}
									if (val == "2") {
										return "体验券";
									}
									if (val == "3") {
										return "加息券";
									}
								}
							},{
								header : '优惠券编号',
								width : 120,
								dataIndex : 'couponNumber',
								align : 'center'
							},{
								header : '面值',
								summaryType : 'sum',
								dataIndex : 'couponValue',
								width : 100,
								align : 'right',
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
							},/*{
								header : '有效开始时间',	
								width : 100,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'couponStartDate'
							},{
								header : '过期时间',	
								width : 100,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'couponEndDate'
							},{
								header : '状态',	
								width : 100,
								align : 'center',
								dataIndex : 'couponStatus',
								renderer:function(v){
								 	if(v==5){
								 		return "未使用";
								 	}else if(v==3){
								 		return "已禁用";
								 	}else if(v==4){
								 		return "已过期";
								 	}else if(v==10){
								 		return "已使用";
								 	}else{
								 		return "";
								 	}

                         	    }
							},*/{
								header : '活动编号',	
								width : 100,
								align : 'center',
								dataIndex : 'activityNumber'
							},/*{
								header : '绑定投资人',	
								width : 100,
								align : 'center',
								dataIndex : 'belongUserName'
							},*/{
								header : '绑定时间',	
								width : 100,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'bindOpraterDate'
							},{
								header : '使用类型',	
								width : 100,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'useProjectType',
								renderer : function(val) {
									if (val == "mmplan") {
										return "理财计划";
									}else{
										return "散标";
									}
									
								}
							},{
								header : '投标名称',	
								width : 120,
								align : 'left',
								dataIndex : 'useProjectName'
							},{
								header : '投标编号',	
								width : 120,
								align : 'center',
								dataIndex : 'useProjectNumber'
							},{
								header : '投资金额(元)',	
								width : 120,
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"元";
				                   },
								align : 'center',
								dataIndex : 'infoMoney'
							},{
								header : '折现比例(%)',	
								width : 120,
								align : 'center',
								dataIndex : 'returnRatio'
							},{
								header : '奖励金额(元)',	
								width : 120,
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"元";
				                   },
								align : 'center',
								dataIndex : 'couponMoney'
							},{
								header : '使用时间',	
								width : 100,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'useTime'
							},{
								header : '说明',	
								width : 150,
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
				var ids = $getGdSelectedIds(this.gridPanel, 'couponId');
				Ext.Msg.confirm('信息确认', '你确定要禁用选中记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath + '/coupon/forbidOrEnableBpCoupons.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								var obj = Ext.util.JSON.decode(response.responseText);	
								Ext.ux.Toast.msg('操作信息', obj.msg);
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
				var ids = $getGdSelectedIds(this.gridPanel, 'couponId');
				Ext.Msg.confirm('信息确认', '你确定要启用选中记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath + '/coupon/forbidOrEnableBpCoupons.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								var obj = Ext.util.JSON.decode(response.responseText);	
								Ext.ux.Toast.msg('操作信息', obj.msg);
								thisPanel.getStore().reload();
							},
							params : {
								ids : ids,
								couponStatus:"3"
							}
						});
					}
				});
			
			},exportExcel:function(){
				
					var couponType = this.getCmpByName("couponType").getValue();
					var useTime = this.getCmpByName("useTime").getValue();
					var bindOpratorName = this.getCmpByName("bindOpratorName").getValue();
					var couponNumber = this.getCmpByName("couponNumber").getValue();
					var useProjectNumber = this.getCmpByName("useProjectNumber").getValue();
					var useTime2 = this.getCmpByName("useTime2").getValue();
					var resourceName = this.getCmpByName("resourceName").getValue();
					var activityNumber = this.getCmpByName("activityNumber").getValue();
					var time1="";
					var time2="";
					if(useTime!=""){
						 time1 = useTime.format("Y-m-d");
					}
					if(useTime2!=""){
						 time2 = useTime2.format("Y-m-d");
					}
					window.open( __ctxPath + "/coupon/excelUseBpcouponsBpCoupons.do?couponType="+couponType+"&useTime="+time1+"" +
							"&bindOpratorName="+bindOpratorName+"&couponNumber="+couponNumber+"&useProjectNumber="+useProjectNumber+"&activityNumber="+activityNumber+"&useTime2="+time2+"&resourceName="+resourceName+"");
	}
		});

