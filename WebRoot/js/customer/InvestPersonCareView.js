/**
 * @author
 * @class InvestPersonCareView
 * @extends Ext.Panel
 * @description [InvestPersonCare]管理
 * @company 智维软件
 * @createtime:
 */
var me;
InvestPersonCareView = Ext.extend(Ext.Panel, {
			// 构造函数
	        onclickflag:0,
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if (typeof (this.onclickflag) == "undefined") {
					this.onclickflag =0;
				}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				InvestPersonCareView.superclass.constructor.call(this, {
							id : 'InvestPersonCareView',
							title : '投资客户关系维护',
							region : 'center',
							layout : 'border',
							iconCls : 'btn-tree-team23',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				me = this;
				var tabflag ="investPersonCare";
				var onclickflag=this.onclickflag;
				var onclickisInterent=this.onclickisInterent;
				var itemwidth = 0.25;
				
				var isShow = false;
				var rightValue =  isGranted('_careView_see_All');
				var isShop = isGranted('_careView_see_shop');
				if (RoleType == "control") {
					isShow = true;
		
				}
				
				this.store = new Ext.data.JsonStore({
				url : __ctxPath
						+ '/creditFlow/creditAssignment/customer/listCareAndGrantCsInvestmentperson.do?isAll='+rightValue+"&isShop="+isShop
						,
				totalProperty : 'totalProperty',
				root : 'topics',
				remoteSort : true,
				fields : [{
							name : 'investId'
						}, {
							name : 'investName'
						}, {
							name : 'sexvalue'
						}, {
							name : 'cardtypevalue'
						}, {
							name : 'cardnumber'
						}, {
							name : 'cellphone'
						}, {
							name : 'shopId'
						}, {
							name : 'companyId'
						}, {
							name : 'shopName'
						},  {
							name : 'orgName'
						}, {
							name : 'accountNumber'
						},  {
							name : 'contractStatus'
						}, {
							name : 'changeCardStatus'
						}, {
							name : 'birthDay'
						}]
			});
			var person_store = this.store;
			// 加载数据
		this.store.load({
					scope : this,
					params : {
						start : 0,
						limit : this.pageSize,
						isAll : isGranted('_detail_sygrkh')
					}
				});
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							frame : false,
							region : 'north',
							height : 35,
							monitorValid : true,
							layout : 'column',
							bodyStyle : 'padding:0px 0px 0px 0px',
							border : false,
							defaults : {
								layout : 'form',
								border : false,
								labelWidth : 60,
								bodyStyle : 'padding:5px 0px 0px 5px'
							},
							labelAlign : "right",
							keys : [{
										key : Ext.EventObject.ENTER,
										fn : this.searchByCondition,
										scope : this
									}, {
										key : Ext.EventObject.ESC,
										fn : this.reset,
										scope : this
									}],
							items:[{
									columnWidth : itemwidth,
									bodyStyle : 'padding:5px 0px 0px 0px',
									items : [{
												fieldLabel : '姓名',
												name : 'investName',
												flex : 1,
												xtype : 'textfield',
												anchor : "100%"
											}]
								},{
									columnWidth : itemwidth,
									items : [{
												fieldLabel : '证件号码',
												name : 'cardnumber',
												flex : 1,
												xtype : 'textfield',
												anchor : "100%"
											}]
							},{
								columnWidth : .14,
								labelAlign : 'right',
								xtype : 'container',
								layout : 'form',
								labelWidth : 60,
								defaults : {
									anchor : anchor
								},
								items : [{
										fieldLabel:'生日',
										name : 'Q_intentDate_D_GE',
										id :'Q_intentDate_D_GE'+tabflag,
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '96%',
										value :(onclickflag ==0 || onclickflag ==4)?null:new Date()
									}]
							}, {
								columnWidth : .12,
								labelAlign : 'right',
								xtype : 'container',
								layout : 'form',
								labelWidth : 20,
								defaults : {
									anchor :anchor
								},
								items : [{
										fieldLabel:'到',
										name : 'Q_intentDate_D_LE',
										id :'Q_intentDate_D_LE'+tabflag,
										xtype : 'datefield',
										labelSeparator : '',
										format : 'Y-m-d',
										anchor : '96%',
										value :onclickflag ==0?null:(onclickflag ==1?new Date():(onclickflag ==2?date5:(onclickflag ==3?datemonth:new Date)))
									}]
							},{
								columnWidth : 0.07,
								items : [{
											id : 'searchButton',
											xtype : 'button',
											text : '查询',
											iconCls : 'btn-search',
											width : 60,
											formBind : true,
											labelWidth : 20,
											bodyStyle : 'padding:5px 0px 0px 0px',
											scope : this,
											handler : this.searchByCondition
										}]
							},{
								columnWidth : 0.07,
								items : [{
											xtype : 'button',
											text : '重置',
											width : 60,
											scope : this,
											iconCls : 'btn-reset',
											handler : this.reset
										}]

							}]
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [{
								iconCls : 'btn-add',
								text : '新增客户关怀记录',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_create_tzghkh') ? false : true,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_create_tzghkh') ? false : true)||(isGranted('_see_tzghkh') ? false : true)
							}), {
								iconCls : 'btn-detail',
								text : '查看客户关怀记录',
								xtype : 'button',
								hidden : isGranted('_see_tzghkh') ? false : true,
								scope : this,
								handler : this.checkfindRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_see_tzghkh') ? false : true)//||(isGranted('_edit_tzghkh') ? false : true)
							}),{
								iconCls : 'btn-edit',
								text : '编辑客户关怀记录',
								xtype : 'button',
								hidden : isGranted('_edit_tzghkh') ? false : true,
								scope : this,
								handler : this.checkeditRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_edit_tzghkh') ? false : true)||(isGranted('_remove_grtzkh') ? false : true)
							}),'->',{
								xtype:'radio',
								scope : this,
								boxLabel : '今日生日',
								id:"10"+tabflag,
								name : '1',
								checked:onclickflag==1?true:false,
								listeners:{
									scope :this,
								    check :function(){
								    var flag=Ext.getCmp("10"+tabflag).getValue();
									    if(flag==true){
											Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue(new Date);
											var now=new Date();
											var time=now.getTime();
//											time +=1000*60*60*24*3
											now.setTime(time);
											Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue(now);
											this.searchByCondition();
									    }
								    }
								}
							},' ',
								{
									xtype:'radio',
									scope : this,
									boxLabel : '三日内生日',
									id:"11"+tabflag,
									name : '1',
									checked:onclickflag==1?true:false,
									listeners:{
										scope :this,
									    check :function(){
									    var flag=Ext.getCmp("11"+tabflag).getValue();
										    if(flag==true){
												Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue(new Date);
												var now=new Date();
												var time=now.getTime();
												time +=1000*60*60*24*3
												now.setTime(time);
												Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue(now);
												this.searchByCondition();
										    }
									    }
									}
								},' ',' ', {
									xtype:'radio',
									boxLabel : '五天内生日',
									name : '1',
									id:"13"+tabflag,
									checked :onclickflag==2?true:false,
									 listeners:{
									 	scope :this,
									    check :function(){
									     var flag=Ext.getCmp("13"+tabflag).getValue();
									     if(flag==true){
									     	  Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue(new Date);
											    var now=new Date();
											   var time=now.getTime();
												time+=1000*60*60*24*5;
												now.setTime(time);
												 Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue(now);
									      this.searchByCondition();
									    }
									    }
									}
								},' ',' ',{
									xtype:'radio',
									boxLabel : '一个月内生日',
									id:"12"+tabflag,
									name : '1',
									checked :onclickflag==3?true:false,
									listeners:{
										scope :this,
									    check :function(){
									   var flag=Ext.getCmp("12"+tabflag).getValue();
									     if(flag==true){
									     	 Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue(new Date);
											 var now=new Date();
											 var time=now.getTime();
											 time+=1000*60*60*24*30;//加上3天
												now.setTime(time);
												 Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue(now);
											      this.searchByCondition();
									    }
									    }
									}
								},' ',' ',' ',' ']
				});
	
				this.gridPanel=new HT.GridPanel({
						region : 'center',
						tbar : this.topbar,
						clicksToEdit : 1,
						store : this.store,
						loadMask : this.myMask,
								columns : [{
									header : 'investId',
									align:'center',
									dataIndex : 'investId',
									hidden : true
								}, {
									header : '姓名',
									align:'center',
									dataIndex : 'investName'
								}, {
									header : '性别',
									align:'center',
									dataIndex : 'sexvalue'
								},{
									header : '手机号码',
									align:'center',
									dataIndex : 'cellphone'
								},{
									header : '证件类型',
									align:'center',
									dataIndex : 'cardtypevalue'
								}, {
									header : '证件号码',
									align:'center',
									dataIndex : 'cardnumber'
								},{
									header : '出生日期',
									align:'center',
									dataIndex : 'birthDay',
									sortable : true
								},{
									header : '登记门店',
									dataIndex :'shopName'
								}/*,{
									header : '通信地址',
									dataIndex : 'postAddress'
								}*//*,{
									header : '最后关怀时间',
									dataIndex : 'lostCareDate',
//								 	renderer: Ext.util.Format.dateRenderer('Y-m-d')
									editor : new Ext.form.DateField({})
    								
								}*/]
							// end of columns
			});
				
				this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
//			//按条件搜索
//			search : function() {
//				$search({
//					searchPanel:this.searchPanel,
//					gridPanel:this.gridPanel
//				});
//			},
			// 按条件搜索
			searchByCondition : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				me.createRs();
//				grid.getSelectionModel().each(function(rec) {
//					new InvestPersonCareForm({id:rec.data.id}).show();
//					new InvestPersonForm({perId : rec.data.perId}).show();
//				});
			},
			//新增客户关怀记录
			createRs : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					var record = s[0]
					new InvestPersonCareForm({
				            	investPersonId :record.data.investId,
								isRead : true,
								isLook : true,
								isHiddenEdit : true,
								isLookCare : false
							}).show();
				}
			
			},
			//查看客户关怀记录
			checkfindRs : function(record){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					record = s[0]
					new InvestPersonCareForm({
				            	investPersonId :record.data.investId,
								isRead : true,
								isLook : true,
								isHiddenEdit : true,
								isLookCare : false,
								careHidden : true
							}).show();
				}
			},
			//编辑客户关怀记录
			checkeditRs : function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					record = s[0]
					new InvestPersonCareForm({
				            	investPersonId :record.data.investId,
								isRead : true,
								isLook : true,
								isHiddenEdit : false,
								isLookCare : false,
								careHidden : true
							}).show();
				}
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/customer/multiDelInvestPersonCare.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/customer/multiDelInvestPersonCare.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new InvestPersonCareForm({
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
					default :
						break;
				}
			}
});
