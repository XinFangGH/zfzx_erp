/**
 * @author
 * @class BpCustMemberView
 * @extends Ext.Panel
 * @description [BpCustMember]管理
 * @company 智维软件
 * @createtime:
 */
BpCustMemberView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCustMemberView.superclass.constructor.call(this, {
							id : 'BpCustMemberView_'+this.isForBidType,
							title : this.isForBidType==1?'禁用用户管理':'用户账号管理',
							region : 'center',
							layout : 'border',
							iconCls:"menu-finance",
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
					     		columnWidth :.15,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'用户名',
											name : 'Q_loginname_S_LK',
											flex:1,
											anchor : "100%",
											xtype : 'textfield'
											}]
					     	},{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 70,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'真实姓名',
											name : 'Q_truename_S_LK',
											flex:1,
											anchor : "100%",
											xtype : 'textfield'
										}]
				     	},{
				     		columnWidth :.15,
							layout : 'form',
							labelWidth : 70,
							labelAlign : 'right',
							border : false,
							items : [{
											fieldLabel:'手机号码',
											name : 'Q_telphone_S_LQ',
											flex:1,
											anchor : "100%",
											xtype : 'textfield'
										}]
				     	},{
			     		columnWidth :.2,
						layout : 'form',
						labelWidth : 70,
						labelAlign : 'right',
						border : false,
						items : [{
										fieldLabel:'证件号码',
										name : 'Q_cardcode_S_LQ',
										flex:1,
										anchor : "100%",
										xtype : 'textfield'
									}]
			     	},{
					     		columnWidth :.15,
								layout : 'form',
								labelWidth : 70,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'是否为VIP',
											hiddenName : 'Q_isVip_SN_EQ',
											anchor : "100%",
											xtype : 'combo',
											displayField : 'itemName',
											valueField : 'itemValue',
											triggerAction : 'all',
											anchor : '100%',
											mode : 'local',
											store : new Ext.data.ArrayStore({
														fields : ['itemValue', 'itemName'],
														data : [['0', '否'],
																['1', '是']]
													})
											
										}]
				     	},{
		     			columnWidth :.07,
						layout : 'form',
						border : false,
						labelWidth :40,
						items :[{
							text : '查询',
							xtype : 'button',
							scope : this,
							style :'margin-left:30px',
							anchor : "100%",
							iconCls : 'btn-search',
							handler : this.search
						}]
		     		},{
		     			columnWidth :.07,
						layout : 'form',
						border : false,
						labelWidth :40,
						items :[{
							text : '重置',
							style :'margin-left:30px',
							xtype : 'button',
							scope : this,
							//width : 40,
							anchor : "100%",
							iconCls : 'btn-reset',
							handler : this.reset
						}]
		     		}]
								
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [ {
									iconCls : 'btn-resetpassd',
									text : '修改密码',
									xtype : 'button',
									scope:this,
									hidden:this.isForBidType==1?true:false,
									handler : this.updatePassword
								},{
									iconCls : 'btn-forbiduser',
									text : '禁用用户',
									xtype : 'button',
									scope:this,
									hidden:this.isForBidType==1?true:false,
									handler : this.forbiddenSelRs
								},{
									iconCls : 'btn-userable',
									text : '解除禁用',
									xtype : 'button',
									scope:this,
									hidden:this.isForBidType==1?false:true,
									handler : this.refrshforbidden
								},{
									iconCls : 'btn-p2p',
									text : '开通VIP',
									xtype : 'button',
									scope:this,
									hidden:this.isForBidType==1?true:false,
									handler : this.openVip
								},{
									iconCls : 'btn-close',
									text : '取消VIP',
									xtype : 'button',
									scope:this,
									hidden:this.isForBidType==1?true:false,
									handler : this.closeVip
								},{
									iconCls : 'btn-close',
									text : '修改内部推荐码',
									xtype : 'button',
									scope:this,
									hidden:this.isForBidType==1?true:false,
									handler : this.changeMark
								}]
				});
	           var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}

				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					plugins : [summary],
					autoScroll : true,
					viewConfig:{
					   //forceFit:false,
					   width:500,   //固定宽度
					   autoScroll:true,
					   scrollOffset: 0,
					    forceFit: true
					},
					id:'BpCustMemberGrid',
					url : __ctxPath + "/p2p/listBpCustMember.do?isForBidType="+this.isForBidType,
					fields : [{
									name : 'id',
									type : 'int'
								}
								,'loginname'
								,'truename'
								,'password'
								,'plainpassword'
								,'telphone'
								,'email'
								,'type'
								,'sex'
								,'sexname'
								,'cardtype'
								,'cardtypename'
								,'cardcode'
								,'birthday'
								,'headImage'
								,'nativePlaceProvice'
								,'nativePlaceCity'
								,'nation'
								,'homePhone'
								,'relationAddress'
								,'postCode'
								,'QQ'
								,'MSN'
								,'paymentCode'
								,'securityQuestion'
								,'securityAnswer'
								,'roleId'
								,'registrationDate'
								,'liveProvice'
								,'liveCity'
								,'marry'
								,'fax'
								,'memberOrderId'
								,'isDelete'
								,'isForbidden'
								,'memberGrade'
								,'directReferralsName'
								,'indirectReferenceName'
								,'score'
								,'categoryName'
                                ,'registrationDate'
                                ,'levelMark'
                                ,'isVip'
                                ,'departmentRecommend'
																																			],
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '登录名',	
									align:'center',
									dataIndex : 'loginname',
									width:90	
								},{
									header : '真实姓名',
									align:'center',
									dataIndex : 'truename',
									width:70
								},{
									header : '手机号码',
									align:'center',
									summaryRenderer : totalMoney,
									dataIndex : 'telphone',
									width:100
								},{
									header : '性别',
									align:'center',
									dataIndex : 'sex',
									width:40,
									renderer:function(v){
										if(typeof(v) == 'object'){
										  return '';
										}else if(typeof(v) == 'number'){
										  if(v==0){
												return '男';
											}else if(v==1){
												return '女';
											}
										}
									}
								},{
									header : '证件类型',
									align:'center',
									dataIndex : 'cardtypename',
									width:70,
									listeners:{  
 									   'change':function(){    
 									       alert('change');    
    									}  
									},    
									renderer:function(v){
										return '身份证';
									}
								},{
									header : '证件号码',
									align:'left',
									dataIndex : 'cardcode',
									width:140
									
								},{
									header : '出生日期',
									align:'center',
									dataIndex : 'birthday',
									format : 'Y-m-d',
									width:100,
									renderer : function(value, metaData, record, rowIndex,colIndex, store) {
										if(value){
											return value.substring(0,10);
										}
									}
								},{
									header : '民族',
									align:'center',
									dataIndex : 'nation',
									hidden : true,
									width:50
								},{
									header : '家庭电话',
									align:'center',
									dataIndex : 'homePhone',
									hidden : true
								},{
									header : '是否禁用',
									align:'center',
									dataIndex : 'isForbidden',
									renderer:function(v){
										if(eval(v)==eval(1)){
											return "是";
										}else{
											return "否";
										}
								    },
								    width:70
								},{
									header : '会员等级',
									align:'center',
									dataIndex : 'levelMark',
									width:80
								},{
									header : '会员积分',
									align:'right',
									dataIndex : 'score',
									summaryType : 'sum', 
									renderer : function(v) {
					                 return v+"分";
				                   },
									width:70,
									sortable : true
								},{
									header : '是否为VIP',
									align:'center',
									dataIndex : 'isVip',
									width:80,
									renderer : function(v) {
					                 if(v==1){
					                 	return '是'
					                 }else{
					                 	return '否'
					                 }
				                   }
								},{
									header : '部门推荐码',
									align:'center',
									dataIndex : 'departmentRecommend',
									width:70
								},{
									header : '一级推荐人',
									align:'center',
									dataIndex : 'directReferralsName',
									width:70
								},{
									header : '二级推荐人',
									align:'center',
									dataIndex : 'indirectReferenceName',
									width:70
								},{
									header : '注册时间',	
									align:'left',
									dataIndex : 'registrationDate',
									width:170,
									sortable : true
								}
					]//end of columns
				});
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
			//开通vip
			openVip : function() {
				var  flashPanel=this.gridPanel;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else {
					var ids = $getGdSelectedIds(flashPanel,"id");
					var state = 1;
					Ext.Msg.confirm('确认信息', '是否把所选账户设置为Vip账户', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
						url : __ctxPath + '/p2p/changeVipStateBpCustMember.do',
						method : 'POST',
						scope:this,
						success :function(response, request){
							flashPanel.getStore().reload();
						},
						params : {
							ids:ids,
							state:state
						}
			       })
						}
					})
					
				
				}
					
				
			},
			//关闭vip
			closeVip : function() {
				var  flashPanel=this.gridPanel;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else {
					var ids = $getGdSelectedIds(flashPanel,"id");
					var state = 0;
					Ext.Msg.confirm('确认信息', '是否把所选账户的vip关闭', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
						url : __ctxPath + '/p2p/changeVipStateBpCustMember.do',
						method : 'POST',
						scope:this,
						success :function(response, request){
							flashPanel.getStore().reload();
						},
						params : {
							ids:ids,
							state:state
						}
			       })
						}
					})
					
				
				}
					
				
			},
			//修改推荐码
			changeMark : function() {
				var  flashPanel=this.gridPanel;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else {
					var ids = $getGdSelectedIds(flashPanel,"id");
					var window_update = new Ext.Window({
															title : '内部推荐码修改',
															height : 150,
															constrainHeader : true,
															collapsible : true,
															frame : true,
															iconCls : 'btn-edit',
															border : false,
															bodyStyle : 'overflowX:hidden',
															buttonAlign : 'right',
															iconCls : 'newIcon',
															id:'innerPustMark_id',
															width : 300,
															resizable : true,
															layout : 'fit',
															constrain : true,
															closable : true,
															modal : true,
															items : [new Ext.FormPanel({
															layout : 'form',
															border : false,
															frame:true,
															labelAlign  :'right',
															defaults : {
																labelAlign  :'right'
															},
															defaultType : 'textfield',
															items : [{
																xtype : 'hidden',
																name : 'ids',
																value : ids
															},{
																xtype : 'textfield',
																fieldLabel : '内部推荐码',
																allowBlank:false,
																labelAlign  :'right',
																name : 'mark'
															  }]
															})
															],
															buttonAlign : 'center',
															buttons : [{
																	text : '提交',
																	iconCls : 'btn-save',
																	handler : function(v){
																		var ids = v.ownerCt.ownerCt.getCmpByName('ids').getValue();
																		var mark = v.ownerCt.ownerCt.getCmpByName('mark').getValue();
																		Ext.Ajax.request({
																			url : __ctxPath + '/p2p/changeMarkBpCustMember.do',
																			method : 'POST',
																			scope:this,
																			success :function(response, request){
																				flashPanel.getStore().reload();
																				Ext.getCmp('innerPustMark_id').close();
																			},
																			params : {
																				ids:ids,
																				mark:mark
																			}
																       })
																	}
																}]
														});
												window_update.show();
//					Ext.Msg.confirm('确认信息', '是否把所选账户的vip关闭', function(btn) {
//						if (btn == 'yes') {
//							Ext.Ajax.request({
//						url : __ctxPath + '/p2p/changeVipStateBpCustMember.do',
//						method : 'POST',
//						scope:this,
//						success :function(response, request){
//							flashPanel.getStore().reload();
//						},
//						params : {
//							ids:ids,
//							state:state
//						}
//			       })
//						}
//					})
					
				
				}
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
