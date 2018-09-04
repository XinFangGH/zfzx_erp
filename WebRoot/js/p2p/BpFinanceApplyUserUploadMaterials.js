/**
 * @author
 * @class BpFinanceApplyUserView
 * @extends Ext.Panel
 * @description [BpFinanceApplyUser]管理
 * @company 智维软件
 * @createtime:
 */
BpFinanceApplyUserUploadMaterials = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpFinanceApplyUserUploadMaterials.superclass.constructor.call(this, {
							id : 'BpFinanceApplyUserUploadMaterials',
							title : '用户已上传补充材料',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							region : 'north',
							items:[{
								columnWidth : .17,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
									fieldLabel:'借款金额',
									name : 'Q_loanMoney_S_EQ',
									anchor : '100%',
									flex:1,
									xtype : 'textfield'
								}]
							},{
								columnWidth : .17,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
									fieldLabel:'借款期限',
									name : 'Q_loanTimeLen_S_EQ',
									anchor : '100%',
									flex:1,
									xtype : 'textfield'
								}]
							},{
								columnWidth : .18,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border :false,
								items : [{
									fieldLabel:'借款申请时间',
									name : 'Q_createTime_S_EQ',
									flex:1,
									anchor : '100%',
									format : 'Y-m-d',
									xtype : 'datefield'
								}]
							},{
								columnWidth : .17,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
									fieldLabel:'用户名',
									name : 'loginname',
									flex:1,
									anchor : '100%',
									xtype : 'textfield'
								}]
							},{
								columnWidth : .17,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
									fieldLabel:'真实姓名',
									name : 'truename',
									flex:1,
									anchor : '100%',
									xtype : 'textfield'
								}]
							},{
								columnWidth : .07,
								layout : 'form',
								border :false,
								items :[{
									xtype : 'button',
									text:'查询',
									scope:this,
									iconCls:'btn-search',
									handler:this.search
								}]
							},{
								columnWidth : .07,
								layout : 'form',
								border :false,
								items :[{
									xtype : 'button',
									text:'重置',
									scope:this,
									iconCls:'btn-reset',
									handler:this.reset
								}]
							}]	
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls:'btn-readdocument',
									text:'查看',
									xtype:'button',
									scope:this,
									handler : function() {
									var rows = this.gridPanel.getSelectionModel().getSelections();
									if (rows.length == 0) {
										Ext.ux.Toast.msg('操作信息', '请选择记录!');
										return;
									} else if (rows.length > 1) {
										Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
										return;
									} else {
										Ext.Ajax.request({
											url : __ctxPath + '/p2p/getInfoBpFinanceApplyUser.do?loanId='+rows[0].data.loanId,
											method:'post',
											async:false,
											success: function(resp,opts) {
												var respText = resp.responseText;
												alarm_fields = Ext.util.JSON.decode(respText);
												appUser=alarm_fields.data;
												new BpCustMemberForm({
													memObj:alarm_fields.data.bpCustMember,
													appUser:alarm_fields.data.bpFinanceApplyUser,
													userId:alarm_fields.data.bpCustMember.id,
													userName:alarm_fields.data.bpCustMember.loginname
													
												}).show();
											}
										});
						
									}}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_create_grkh') ? false : true
								}), {
									iconCls:'uploadIcon',
									text:'重新上传',
									xtype:'button',
									scope:this,
									handler : function() {
										var flashPanel=this.gridPanel
									var rows = this.gridPanel.getSelectionModel().getSelections();
									if (rows.length == 0) {
										Ext.ux.Toast.msg('操作信息', '请选择记录!');
										return;
									} else if (rows.length > 1) {
										Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
										return;
									} else {
										Ext.Msg.confirm('信息确认', '是否重新上传', function(btn) {
					       					 if (btn == 'yes') {
												Ext.Ajax.request({
													url:__ctxPath + '/p2p/saveBpFinanceApplyUser.do',
													params:{'loanId':rows[0].data.loanId,'state':7},
													success: function(resp,opts) {
														Ext.ux.Toast.msg('操作信息', '操作成功!');
														flashPanel.getStore().reload();
													}
												});
					       					 }
										})
						
									}}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_create_grkh') ? false : true
								}), {
									iconCls:'btn-add',
									text:'通过',
									xtype:'button',
									scope:this,
									handler : function() {
										var flashPanel=this.gridPanel
									var rows = this.gridPanel.getSelectionModel().getSelections();
									if (rows.length == 0) {
										Ext.ux.Toast.msg('操作信息', '请选择记录!');
										return;
									} else if (rows.length > 1) {
										Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
										return;
									} else {
										Ext.Msg.confirm('信息确认', '是否通过', function(btn) {
					       					 if (btn == 'yes') {
												Ext.Ajax.request({
													url:__ctxPath + '/p2p/saveBpFinanceApplyUser.do',
													params:{'loanId':rows[0].data.loanId,'state':9},
													success: function(resp,opts) {
														Ext.ux.Toast.msg('操作信息', '操作成功!');
														flashPanel.getStore().reload();
													}
												});
					       					 }
										})
									}}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_create_grkh') ? false : true
								}), {
									iconCls:'btn-del',
									text:'不合格，结束项目',
									xtype:'button',
									scope:this,
									handler : function() {
										var flashPanel=this.gridPanel
									var rows = this.gridPanel.getSelectionModel().getSelections();
									if (rows.length == 0) {
										Ext.ux.Toast.msg('操作信息', '请选择记录!');
										return;
									} else if (rows.length > 1) {
										Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
										return;
									} else {
										Ext.Msg.confirm('信息确认', '是否结束项目', function(btn) {
					       					 if (btn == 'yes') {
												Ext.Ajax.request({
													url:__ctxPath + '/p2p/saveBpFinanceApplyUser.do',
													params:{'loanId':rows[0].data.loanId,'state':3},
													success: function(resp,opts) {
														Ext.ux.Toast.msg('操作信息', '操作成功!');
														flashPanel.getStore().reload();
													}
												});
					       					 }
										})
						
									}}
								}
									
						]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					rowActions:true,
					id:'BpFinanceApplyUserGrid',
					url : __ctxPath + "/p2p/personListBpFinanceApplyUser.do?state=8",
					fields : [{
									name : 'loanId',
									type : 'int'
								}
					,'productId'
					,'appName'
					,'proName'
					,'userID'
					,'loanTitle'
					,'loanMoney'
					,'loanTimeLen'
					,'remark'
					,'expectAccrual'
					,'payIntersetWay'
					,'loanUseStr'
					,'monthlyInterest'
					,'monthlyCharge'
					,'startCharge'
					,'state'
					,'createTime'
					,'truename'
					],
					columns:[
								{
									header : 'loanId',
									dataIndex : 'loanId',
									hidden : true
								}/*,{
									header : '申请状态',	
									dataIndex : 'state',
									renderer:function(v){
                                    if(v==0){
                                    	return '未提交';
                                    }else if(v==1){
                                    	return '已提交审核';
                                    }else if(v==2){
                                    	return '已受理，审核中';
                                    }else if(v==3){
                                    	return '审核失败';
                                    }else if(v==4){
                                    	return '审核通过，招标手续办理中';
                                    }else if(v==5){
                                    	return '已启动借款审批项目';
                                    }else if(v==6){
                                    	return '借款项目审批通过，请补充材料';
                                    }
									}
								}*/
								,{
									header : '用户账号',	
									dataIndex : 'appName'
								},{
									header : '真实姓名',	
									dataIndex : 'truename'
								},
								{
									header : '贷款类别',	
									dataIndex : 'proName'
								},{
									header : '借款标题',	
									dataIndex : 'loanTitle'
								},{
									header : '借款金额(万元)',	
									dataIndex : 'loanMoney',
									align:'right'
								}
								,{
									header : '借款期限',	
									dataIndex : 'loanTimeLen'
								}
								,{
									header : '借款用途',	
									dataIndex : 'loanUseStr'
								}
								/*,{
									header : '每月还本金及利息',	
									dataIndex : 'monthlyInterest'
								}
								,{
									header : '每月交借款管理费',	
									dataIndex : 'monthlyCharge'
								}
								,{
									header : '期初服务费',	
									dataIndex : 'startCharge'
								}*/
								
								/*,{
									header : '借款申请时间',	
									dataIndex : 'createTime'
								},{
									header : '借款描述',	
									dataIndex : 'remark'
								}*/
							, new Ext.ux.grid.RowActions({
									header:'',
									width:0,
									actions:[/*{
											 iconCls:'btn-del',qtip:'删除',style:'margin:0 3px 0 3px'
										},{
											 iconCls:'btn-edit',qtip:'编辑',style:'margin:0 3px 0 3px'
										}*/
									],
									listeners:{
										scope:this,
										'action':this.onRowAction
									}
								})
					]//end of columns
				});
				
				this.gridPanel.addListener('rowdblclick',this.rowClick);
					
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
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BpFinanceApplyUserForm({loanId:rec.data.loanId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new BpFinanceApplyUserForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p/multiDelBpFinanceApplyUser.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			updateSelRs : function(obj,e) {
				var  flashPanel=this.gridPanel;
				var rows = this.gridPanel.getSelectionModel().getSelections();
				if (rows.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (rows.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					var loanId=rows[0].data.loanId;
					var btest1=obj.text;
					var rowState;
					if(btest1=="受理"){
						rowState='2';
					}else if(btest1=="转正式用户"){
						rowState='5';
					}else if(btest1=="驳回"){
						rowState='3';
					}else if(btest1=="通过审核"){
						rowState='4'
					}
					Ext.Msg.confirm('信息确认', '是否要受理申请', function(btn) {
       					 if (btn == 'yes') {
							Ext.Ajax.request({
								url:__ctxPath + '/p2p/saveBpFinanceApplyUser.do',
								params:{'loanId':loanId,'state':rowState},
								success: function(resp,opts) {
									Ext.ux.Toast.msg('操作信息', '操作成功!');
									flashPanel.getStore().reload();
								}
							});
       					 }
					})
				}
			},
			//编辑Rs
			editRs : function(record) {
				new BpFinanceApplyUserForm({
					loanId : record.data.loanId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.loanId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
