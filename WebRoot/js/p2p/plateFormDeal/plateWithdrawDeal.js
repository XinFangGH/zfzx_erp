/**
 * @author
 * @class plateDeal
 * @extends Ext.Panel
 * @description [BpCustMember]管理
 * @company 智维软件
 * @createtime:
 */
plateWithdrawDeal = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				plateWithdrawDeal.superclass.constructor.call(this, {
							id : 'plateWithdrawDeal_'+this.isForBidType,
							title : '平台取现台账',
							region : 'center',
							layout : 'border',
							iconCls:"btn-tree-team39",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							autoScroll : true,
							region : 'north',
							
							border : false,
							height : 50,
							anchor : '80%',
							layoutConfig: {
				               align:'middle'
				            },
				             bodyStyle : 'padding:10px 10px 10px 10px',
							items:[{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
									fieldLabel:'订单编号',
									name : 'recordNumber',
									flex:1,
									anchor : "100%",
									xtype : 'textfield'
								},{
									fieldLabel : '交易状态',
									hiddenName : 'dealRecordStatus',
									anchor : '100%',
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'item',
									store : new Ext.data.SimpleStore({
												fields : ["item", "value"],
												data : [["成功", "2"], ["失败", "1"]]
											}),
									triggerAction : "all"
								}]
					     	},{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
									fieldLabel:'真实姓名',
									name : 'truename',
									flex:1,
									anchor : "100%",
									xtype : 'textfield'
								}]
					     	},{
								columnWidth : .2,
								layout : 'form',
								border : false,
								labelAlign : 'right',
								labelWidth : 80,
								items : [{
									fieldLabel : '交易日期',
									name : 'transferDate_GE',
									xtype :'datefield',
									anchor : '100%',
									format : 'Y-m-d'
								}] 
							},{
								columnWidth : .2,
								layout : 'form',
								border : false,
								labelAlign : 'right',
								labelWidth : 20,
								items : [{
									fieldLabel : '至',
									name : 'transferDate_LE',
									xtype :'datefield',
									anchor : '75%',
									format : 'Y-m-d'
								}] 
							},{
				     			columnWidth :.2,
								layout : 'form',
								border : false,
								labelWidth :50,
								items :[{
										text : '查询',
										xtype : 'button',
										scope : this,
										style :'padding-right:10px;',
										anchor : "45%",
										iconCls : 'btn-search',
										handler : this.search
									},{
										text : '重置',
										style :'padding-top:3px;padding-right:10px;',
										xtype : 'button',
										scope : this,
										anchor : "45%",
										iconCls : 'btn-reset',
										handler : this.reset
								}]
					     	 }]
								
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [ {
									iconCls : 'btn-xls',
									text : '导出到excel',
									xtype : 'button',
									scope:this,
									handler : this.exportExcel
								}]
				});
	
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					autoScroll : true,
					plugins : [summary],
					viewConfig:{
						   forceFit:false,
						   width:500,   //固定宽度
						   autoScroll:true
						},


					
					//使用RowActions
					//rowActions:true,
					id:'BpCustMemberGrid',
					url :__ctxPath + '/creditFlow/creditAssignment/bank/getPlateDealObAccountDealInfo.do?transferType=2',
					fields : [{
									name : 'id',
									type : 'int'
								}
								,'transferDate'
								,'recordNumber'
								,'transferType'
								,'incomMoney'
								,'payMoney'
								,'investPersonName'
								,'loginname'
								,'dealRecordStatus'
																																			],
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '交易日期',	
									dataIndex : 'transferDate'
										,width:180	,
										align : 'center',
										summaryType : 'count',
						summaryRenderer : totalMoney

								},{
									header : '订单编号',	
									dataIndex : 'recordNumber'
										,align : 'center',width:180
								},{
									header : '取现金额',	
									dataIndex : 'payMoney'
										,align : 'center',width:140,
										summaryType : 'sum',
										renderer : function(value) {
										if (value == "") {
											return "0.00元";
										} else {
											return Ext.util.Format.number(value, ',000,000,000.00')
													+ "元";
										}
									}
								},{
									header : '登录名',	
									dataIndex : 'loginname'
										,align : 'center',width:140
								},{
									header : '真实姓名',	
									dataIndex : 'investPersonName'
										,align : 'center',width:140
								},{
									header : '交易状态',	
									dataIndex : 'dealRecordStatus'
									,align : 'center',width:140,
									renderer:function(v){
										if(eval(v)==eval(2)){
											return "成功";
										}else if(eval(v)==eval(7)){
											return "冻结";
										}else{
											return "<font color='red'>失败</font>";
										}
										
								    }
								}
																																								, new Ext.ux.grid.RowActions({
								})
					]//end of columns
				});
				
				//this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			//GridPanel行点击处理事件
			/*rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BpCustMemberForm({id:rec.data.id}).show();
				});
			},*/
			//创建记录
			createRs : function() {
				new BpCustMemberForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p/multiDelBpCustMember.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p/multiDelBpCustMember.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//按ID禁用记录
			forbiddenRs : function(id) {
				$postForbi({
					url:__ctxPath+ '/p2p/multiForbiBpCustMember.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID禁用
			forbiddenSelRs : function() {
				var  flashPanel=this.gridPanel;
				var  isForBidType=this.isForBidType;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					Ext.Msg.confirm('确认信息', '您真的要将'+s[0].data.loginname+"用户禁用", function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
						url : __ctxPath + '/p2p/multiForbiBpCustMember.do',
						method : 'POST',
						scope:this,
						success :function(response, request){
							flashPanel.getStore().reload();
						},
						params : {
							ids:s[0].data.id,
							isForBidType:1
						}
			       })
						}
					})
				}
			},
			updatePassword:function(){
				var  flashPanel=this.gridPanel;
				var  isForBidType=this.isForBidType;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					new UpdatePassword({record:s[0].data}).show();
				}
			},
			//把选中ID禁用
			refrshforbidden : function() {
				var  flashPanel=this.gridPanel;
				var  isForBidType=this.isForBidType;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					Ext.Msg.confirm('确认信息', '您真的要将'+s[0].data.loginname+"用户解除禁用", function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
						url : __ctxPath + '/p2p/multiForbiBpCustMember.do',
						method : 'POST',
						scope:this,
						success :function(response, request){
							flashPanel.getStore().reload();
						},
						params : {
							ids:s[0].data.id,
							isForBidType:0
						}
			       })
						}
					})
					
				
				}
					
				
			},
			
			exportExcel:function(){
					var transferDate_GE = this.getCmpByName("transferDate_GE").getValue();
					var transferDate_LE = this.getCmpByName("transferDate_LE").getValue();
					var recordNumber = this.getCmpByName("recordNumber").getValue();
					var truename = this.getCmpByName("truename").getValue();
					var transferType = "withdraw";
					var dealRecordStatus = this.getCmpByName("dealRecordStatus").getValue();
					var time1 ="";
					var time2 ="";
					if(transferDate_GE!=""){
						 time1 = transferDate_GE.format("Y-m-d");
					}
					if(transferDate_LE!=""){
						 time2 = transferDate_LE.format("Y-m-d");
					}
					window.open( __ctxPath + "/creditFlow/creditAssignment/bank/getExcelPlateDealObAccountDealInfo.do?transferDate_GE="+time1+"" +
							"&transferDate_LE="+time2+"&recordNumber="+recordNumber+"&truename="+truename+"&transferType="+transferType+"&dealRecordStatus="+dealRecordStatus+"");
	},
	
			//编辑Rs
			editRs : function(record) {
				new BpCustMemberForm({
					id : record.data.id
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					case 'btn-forbidden' :
						this.forbiddenRs.call(this,record.data.id);
						break;
					default :
						break;
				}
			}
});
