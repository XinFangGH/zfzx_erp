//BpCouponsEdView.js
/**
 * @author
 * @class BpCouponSettingView
 * @extends Ext.Panel
 * @description [BpCouponSetting]管理
 * @company 智维软件
 * @createtime:
 */
 
 //PlateFormFianceBidIncomeDetailView.js
BpCouponsEdView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCouponsEdView.superclass.constructor.call(this, {
							id : 'BpCouponsEdView',
							title : "已全部派发",
							region : 'center',
							layout : 'border',
							iconCls :'btn-team29',
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
										columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '优惠券类型',
											hiddenName : 'Q_couponType_S_EQ',
											anchor : "100%",
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
										}]
							         },{
							         	columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '说明',
											name : "Q_couponDescribe_S_LK",
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
											iconCls : 'reset',
											handler : this.reset
										}]
									}]
						});

				this.topbar = new Ext.Toolbar({
					items : [{
									iconCls : 'btn-detail',
									text : '派发查询',
									xtype : 'button',
									scope : this,
									handler : this.detailBpCoupons
								}]
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
//					sm : this.projectFundsm,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'BpCouponSettingGrid1',
					url : __ctxPath + "/coupon/listBpCouponSetting.do?Q_couponState_SN_EQ=2&Q_setType_L_EQ=2",
					fields : [ {
									name : 'categoryId',
									type : 'int'
								}
							,'couponType','couponTypeValue','couponDescribe','couponValue','counponCount','totalCouponValue'
							,'couponStartDate','couponEndDate','couponCheckDate','couponCheckUserName'
							,'couponCheckUserId','couponState','createDate','createName','createUserId','companyId','setType','counponSumCount','totalSumCouponValue'],
					columns : [{
								header : 'categoryId',
								dataIndex : 'categoryId',
								hidden : true
							},{
								header : '优惠券类型',
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
								header : '面值',
								dataIndex : 'couponValue',
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
							},{
								header : '总张数',	
								summaryType : 'sum',
								align : 'center',
								dataIndex : 'counponSumCount'
							},{
								header : '总面值',
								dataIndex : 'totalSumCouponValue',
								summaryType : 'sum',
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
							},{
								header : '剩余张数',	
								summaryType : 'sum',
								align : 'center',
								dataIndex : 'counponCount'
							},{
								header : '剩余面值',
								dataIndex : 'totalCouponValue',
								summaryType : 'sum',
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
								header : '说明',	
								width : 200,
								align : 'center',
								dataIndex : 'couponDescribe'
							}
						]
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
			detailBpCoupons :function (){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			var categoryId=record.data.categoryId;
				new BpCouponsUserView({
							categoryId : categoryId
						}).show();
			
		}
	
		
			},
			rowClick:function(){
				
			},
			//通过审核
			checkPass : function() {
				var gridPanel =  this.gridPanel;
				var s = gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中要派发的记录');
					return false;
				}else if(s >1){
					Ext.ux.Toast.msg('操作信息', '只能选择一条派发的记录');
					return false;
				}else {
						var record=s[0];
							var categoryId = record.data.categoryId
							var gridPanel = this.gridPanel;
							new BpCouponSettingAllForm({
							categoryId : categoryId,
							gridPanel : gridPanel
						}).show();
	
				}
				
			},
			//不通过审核
			checkFaild:function(){
				var gridPanel =  this.gridPanel;
				var s = gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中要审核的记录');
					return false;
				}else if(s >1){
					Ext.ux.Toast.msg('操作信息', '请选中一条要审核的记录');
					return false;
				}else {
						var record=s[0];
						var couponDescribe=record.data.couponDescribe;
						var counponCount =record.data.counponCount;
						var couponValue=record.data.couponValue;
						var couponTypeValue=record.data.couponTypeValue;
						var msg="<font color=red >确定审核否决【"+couponDescribe+"】批量生成面值为"+couponValue+"元，共"+counponCount+"张"+couponTypeValue+"?此操作不可逆</font>";
						Ext.Msg.confirm('信息确认', msg, function(btn) {
							if (btn == 'yes') {
								var _gridPanel = this.gridPanel;
								Ext.Ajax.request({
									url :__ctxPath + '/coupon/checkBpCouponSetting.do',
									method : 'POST',
									scope:this,
									params : {
										categoryId : record.data.categoryId,
										checkType:"refuse"
									},
									success :function(response, request){
										var obj = Ext.util.JSON.decode(response.responseText);	
										Ext.ux.Toast.msg('操作信息', obj.msg);
										gridPanel.getStore().reload();
									},
									failure : function(fp, action) {
										
									}
		        				});
		        				
							}
						},this)
				}
			}
		});

