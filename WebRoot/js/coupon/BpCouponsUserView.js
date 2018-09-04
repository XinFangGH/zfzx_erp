/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
BpCouponsUserView =  Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.categoryId)!="undefined")
				{
				      this.categoryId=_cfg.categoryId;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCouponsUserView.superclass.constructor.call(this, {
							id : 'BpCouponsUserView',
							region : 'center',
							layout : 'border',
							modal : true,
							height :600,
							width : 900,
							maximizable : true,
							title : '优惠券批量派发查询',
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
					this.topbar = new Ext.Toolbar({
					items : [ {
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
					id : 'BpCouponsGrid',
					url : __ctxPath + "/coupon/bouponBelongListBpCoupons.do?categoryId="+this.categoryId,
					fields : [{
									name : 'couponId',
									type : 'int'
							}
							,'couponNumber','logginName','couponResourceType','resourceId','couponStatus','bindOpratorName','resourceName','activityNumber'
							,'bindOpratorId','bindOpraterDate','belongUserName','belongUserId','useProjectName'
							,'useProjectNumber','useProjectId','useProjectType','useTime','createDate'
							,'createName','createUserId','companyId','couponType','couponTypeValue','couponValue','couponStartDate','couponEndDate'],
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
								align : 'center',
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
								dataIndex : 'couponValue',
								width : 100,
								align : 'right',
								renderer:function(v){
								 	return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
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
								 	}else if(v==1){
								 		return "占用中";
								 	}else if(v==0){
								 		return "未激活";
								 	}else{
								 		return "";
								 	}

                         	    }
							},/*{
								header : '活动编号',	
								width : 100,
								align : 'center',
								dataIndex : 'activityNumber'
							},{
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
							}/*,{
								header : '投标项目名',	
								width : 120,
								align : 'center',
								dataIndex : 'useProjectName'
							},{
								header : '投标项目编号',	
								width : 120,
								align : 'center',
								dataIndex : 'useProjectNumber'
							}*/,{
								header : '使用时间',	
								width : 100,
								align : 'center',
								format : 'Y-m-d',
								dataIndex : 'useTime'
							},{
								header : '说明',	
								width : 150,
								align : 'center',
								dataIndex : 'resourceName'
							}]
					});

				 //this.gridPanel.addListener('rowdblclick',this.rowClick);

			},// end of the initComponents()
			// 重置查询表单
//			rowClick : function(grid, rowindex, e) {
//				grid.getSelectionModel().each(function(rec) {
//							new editAfterMoneyForm({
//								fundIntentId : rec.data.fundIntentId,
//								afterMoney : rec.data.afterMoney,
//								notMoney : rec.data.notMoney,
//								flatMoney : rec.data.flatMoney
//									}).show();
//						});
//				
//			},
		
				exportExcel:function(){
					var categoryId = this.categoryId;
					window.open( __ctxPath + "/coupon/excelBpcouponsBpCoupons.do?categoryId="+categoryId);
	},
			openliushuiwin : function() {
				var fundType=this.fundType;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请至少选中一条记录');
					return false;
				}else if(s.length>=1){
					var record = s[0];
					var ids = Array();
						for (var i = 0; i < s.length; i++) {
							ids.push(s[i].data.fundIntentId);
						}
				Ext.MessageBox.confirm('确认操作？', '操作后就不能恢复了', function(btn) {
					
					if (btn == 'yes') {
						var mk = new Ext.LoadMask('SlReapyMentGrid',{  
			msg: '正在提交数据，请稍候！',  
			removeMask: true //完成后移除  
     });  
	 mk.show(); //显示  
			           Ext.Ajax.request( {
									url : __ctxPath + '/creditFlow/finance/repayMentListSlFundIntent.do',
									method : 'POST',
									waitMsg : '正在发送',
									params : {
										ids : ids,
										postType:1,
										fundType:fundType
									},
									success : function(response, request) {
									   mk.hide();
										var record = Ext.util.JSON.decode(response.responseText);
										 Ext.Msg.alert('状态', record.msg);
										 Ext.getCmp('SlReapyMentGrid').getStore().reload();
									},
									checkfail:function(response, request) {
										mk.hide();
										Ext.Msg.alert('状态', "失败");
									}
								});

		
					}
			
				})
			
					
				}
			}
		});

