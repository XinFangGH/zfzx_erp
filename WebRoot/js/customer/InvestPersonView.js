/**
 * @author
 * @class InvestPersonView
 * @extends Ext.Panel
 * @description [InvestPerson]管理
 * @company 智维软件
 * @createtime:
 */
InvestPersonView = Ext.extend(Ext.Panel, {
	// 构造函数
	isHiddenSearchPanel:false,
	constructor : function(_cfg) {
		if (_cfg && typeof(_cfg.isHiddenSearchPanel) != "undefined") {
			this.isHiddenSearchPanel = _cfg.isHiddenSearchPanel;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		InvestPersonView.superclass.constructor.call(this, {
					id : 'InvestPersonView',
					title : this.isHiddenSearchPanel?'':'投资个人客户管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team23',
					items : this.isHiddenSearchPanel?[this.gridPanel]:[this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		var itemwidth = 0.2;
		var isShow=false;
		if(RoleType=="control"){
			isShow=true;
		}	
		this.searchPanel = new HT.SearchPanel({
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
					items : [isShow?{
								columnWidth : itemwidth,
								labelWidth : 65,
								labelAlign : 'right',
								bodyStyle : 'padding:5px 0px 0px 0px',
								items : [
								{
										xtype : "combo",
										anchor : "100%",
										fieldLabel : '所属分公司',
										hiddenName : "companyId",
										displayField : 'companyName',
										valueField : 'companyId',
										triggerAction : 'all',
										store : new Ext.data.SimpleStore({
											autoLoad : true,
											url : __ctxPath+'/system/getControlNameOrganization.do',
											fields : ['companyId', 'companyName']
										})
								}
							]}:{columnWidth : 0.01},{
								columnWidth : itemwidth,
								bodyStyle : 'padding:5px 0px 0px 0px',
								items : [{
											fieldLabel : '姓名',
											name : 'Q_perName_S_LK',
											flex : 1,
											xtype : 'textfield',
											anchor : "100%"
										}]
							},{
								columnWidth : itemwidth,
								items : [{
											fieldLabel : '证件号码',
											name : 'Q_cardNumber_S_LK',
											flex : 1,
											xtype : 'textfield',
											anchor : "100%"
										}]
							},{
								columnWidth : itemwidth,
								items : [{
									xtype : "dickeycombo",
									editable:false,
									hiddenName : "customerLevel",
									nodeKey : 'finaing_customerLevel', // xx代表分类名称
									fieldLabel : "客户级别",
									anchor : '100%',
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
											})
								       }
									}
								}]
							},{
								columnWidth : itemwidth,
								items :[{
									name : "areaId",
									xtype:'hidden'
								},{
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "areaName",
									editable : false,
									fieldLabel : "所在区域",
									anchor : "99%",
									onTriggerClick : function() {
												var com = this
												var selectBankLinkMan = function(array) {
													var str = "";
													var idStr = ""
													for (var i = array.length - 1; i >= 0; i--) {
														str = str + array[i].text + "-"
														idStr = idStr + array[i].id + ","
													}
													if (str != "") {
														str = str.substring(0, str.length - 1);
													}
													if (idStr != "") {
														idStr = idStr.substring(0, idStr.length - 1)
													}
													com.previousSibling().setValue(idStr)
													com.setValue(str);
												};
												selectDictionary('area', selectBankLinkMan);
										}
								}]
							},{
								columnWidth : 0.07,
								items : [{
											id : 'searchButton',
											xtype : 'button',
											text : '查询',
											iconCls : 'btn-tree-team25',
											width : 60,
											formBind : true,
											labelWidth : 20,
											bodyStyle : 'padding:5px 0px 0px 0px',
											scope : this,
											handler : this.searchByCondition
										}]
							}, {

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
								text : '添加',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_create_grtzkh') ? false : true,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_create_grtzkh') ? false : true)||(isGranted('_See_grtzkh') ? false : true)
							}), {
								iconCls : 'btn-customer',
								text : '查看',
								xtype : 'button',
								hidden : isGranted('_see_grtzkh') ? false : true,
								scope : this,
								handler : this.checkfindRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_see_grtzkh') ? false : true)||(isGranted('_edit_grtzkh') ? false : true)
							}), {
								iconCls : 'btn-edit',
								text : '编辑',
								xtype : 'button',
								hidden : isGranted('_edit_grtzkh') ? false : true,
								scope : this,
								handler : this.checkeditRs
							}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_edit_grtzkh') ? false : true)||(isGranted('_remove_grtzkh') ? false : true)
							}),	{
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								hidden : isGranted('_remove_grtzkh') ? false : true,
								scope : this,
								handler : this.removeSelRs
							},	new Ext.Toolbar.Separator({
								hidden : (isGranted('_remove_grtzkh') ? false : true)||(isGranted('_export_grtzkh') ? false : true)
							}), {
								text : '导出到Excel',
								iconCls : 'btn-xls',
								scope : this,
								hidden : isGranted('_export_grtzkh') ? false : true,
								handler : function() {
									var areaId=this.getCmpByName("areaId").getValue();
									var customerLevel = this.getCmpByName("customerLevel").getValue();
									var companyId = (isShow==true?this.getCmpByName("companyId").getValue():1);
									var perName = this.getCmpByName("Q_perName_S_LK").getValue();
									var cardNumber = this.getCmpByName("Q_cardNumber_S_LK").getValue();
									window.open(__ctxPath+ '/customer/exportExcelInvestPerson.do?isAll='
										+ isGranted('_detail_sygrtzkh')+'&areaId='+areaId
										+'&companyId='+companyId+'&Q_perName_S_LK='+encodeURIComponent(encodeURIComponent(perName))+'&Q_cardNumber_S_LK='+encodeURIComponent(encodeURIComponent(cardNumber)),'_blank');
								}
// handler:this.getCheckedID
							 }, new Ext.Toolbar.Separator({}),{
								iconCls : 'menu-customer-manager',
								text : '授权共享人',
								xtype : 'button',
								scope : this,
								handler : this.grant
							 },{
				iconCls : 'btn-p2p',
				text : '开通p2p账户',
				xtype : 'button',
				hidden : isGranted('_add_p_invest') ? false : true,
				scope:this,
				handler: this.addP2pUser
			}]
				});
		var isShow=false;
		if(RoleType=="control"){
		  isShow=true;
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			rowActions : false,
			id : 'InvestPersonGrid',
			url : __ctxPath + "/customer/listInvestPerson.do?isAll="+ isGranted('_detail_sygrtzkh'),
			fields : [{
						name : 'perId',
						type : 'int'
					}, 'cardNumber', 'cardType', 'isdelete', 'perName',
					'perSex', 'phoneNumber', 'postAddress', 'postCode',
					'perBirthday','orgName','customerLevel','belonger','belongedId','areaText'],
					columns : [isShow?{
						header : '所属分公司',
						dataIndex : 'orgName'
					}:{
						columnWidth:0.001,
						hidden : true,
				     	border:false	
				    },{
						header : 'perId',
						dataIndex : 'perId',
						hidden : true
					}, {
						header : 'belongedId',
						dataIndex : 'belongedId',
						hidden : true
					},{
						header : '姓名',
						dataIndex : 'perName'
					}, {
						header : '性别',
						dataIndex : 'perSex',
						renderer : function(value){
							if(value=='312' || value==312){
								return '男';
							} else if(value=='313' || value==313){
								return '女';
							} else{
								return '';
							}
						}
					},{
						header : '手机号码',
						dataIndex : 'phoneNumber'
					},{
						header : '客户级别',
						dataIndex :'customerLevel',
						renderer : function(value){
							if(value == "861"){
								return '潜在客户';
							}else if(value == "862"){
								return '正式客户';
							}else if(value == "863"){
								return '大客户';
							}else{
								return '';
							}
						}
					},{
						header : '证件类型',
						dataIndex : 'cardType',
						renderer : function(value){
							if(value=='309' || value==309){
								return '身份证';
							} else if(value=='310' || value==310){
								return '军官证';
							} else if(value=='311' || value==311){
								return '护照';
							} else if(value=='834' || value==834){
								return '临时身份证';
							} else if(value=='835' || value==835){
								return '港澳台通行证';
							}  else{
								return '其他';
							}
						}
					}, {
						header : '证件号码',
						dataIndex : 'cardNumber'
					}, {
						header:'共享人',
						dataIndex:'belonger'
					
					},{
						header : '出生日期',
						dataIndex : 'perBirthday'
						
					},{
						header : '所属地区',
						dataIndex :'areaText'
					},{
						header : '通信地址',
						dataIndex : 'postAddress'
					}],
				listeners : {
						scope:this,
						'rowdblclick' : function(grid, index, e) {
							if(this.isHiddenSearchPanel){
								var selected = grid.getStore().getAt(index) ;
								this.callbackFun(selected);
								Ext.getCmp('selectInvestPersonWin').destroy();
							}
						}
					}
				// end of columns
			});

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	searchByCondition : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new InvestPersonForm({
								perId : rec.data.perId
							}).show();
				});
	},
	// 获取到所有备选则项的ID
	
	
	// 创建记录
	createRs : function() {
		new InvestPersonForm({
			isRead : false,
			investPerson : null
		}).show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath + '/customer/multiDelInvestPerson.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath + '/customer/multiDelInvestPerson.do',
			grid : this.gridPanel,
			idName : 'perId'
		});
	},
	// 添加p2p账户
	addP2pUser : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s.length <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		}
		var personId = record.data.perId;
		new BpCustRelationForm({
			type:"p_invest",
			userId:personId
		}).show();
	},
	// 单选编辑
	checkeditRs : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s.length <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else{
			record = s[0]
			Ext.Ajax.request({
				url : __ctxPath + '/customer/getInvestPerson.do?perId=' + record.data.perId,
				method : 'POST',
				scope : this,
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var investPerson = obj.data;
		            new InvestPersonForm({
			            investPerson : investPerson,
						isRead : false,
						isEdit : true,
						isCareHidden : true
				 }).show();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				}
			});
		}
	},
	/*
	 * getCheckedID:function(){ var s =
	 * this.gridPanel.getSelectionModel().getSelections();
	 * 
	 * var ids=''; if(s.length<=0){ records = this.gridPanel.getStore();
	 * records.each(function(record){ ids+=record.get('perId')+','; }); }else{
	 * for(var i=0; i<s.length;i++){ ids+=s[i].data.perId+','; } }
	 * 
	 * window.open(__ctxPath+ '/customer/exportExcelInvestPerson.do?isAll='+
	 * isGranted('_detail_sygrtzkh')+"&useIds="+ids,'_blank'); //alert(ids);
	 *  },
	 */
	// 授权共享人
	grant : function() {
			var idCustomerTypeStr = "";
			var belongedIdStr = "";
			var belongerStr = "";
			var grid = this.gridPanel;
			var selRs = grid.getSelectionModel().getSelections();
		if (selRs<= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			for (var i = 0; i < selRs.length; i++) {
				idCustomerTypeStr += selRs[i].data.perId + ";InvestPerson,";
				belongedIdStr += selRs[i].data.belongedId + ",";
				belongerStr += selRs[i].data.belonger + ",";
			}
			idCustomerTypeStr = idCustomerTypeStr.substring(0,idCustomerTypeStr.length - 1);
			belongedIdStr = belongedIdStr.substring(0, belongedIdStr.length - 1);
			belongerStr = belongerStr.substring(0, belongerStr.length - 1);
			
			new UserDialog({
				single : false,
				title : "授权共享人",
				userIds : selRs.length > 1?"":belongedIdStr,
				userName : selRs.length > 1?"":belongerStr,
				callback : function(uId, uname) {
					Ext.Msg.confirm('信息确认', '确认为选中的'+selRs.length+'位客户授权共享人吗？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath + "/customer/grantBelongerCustomer.do",
								method : "post",
								params : {
									idCustomerTypeStr : idCustomerTypeStr,
									userIdStr : uId
								},
								scope : this,
								success : function(response) {
									grid.getStore().reload();
									Ext.ux.Toast.msg('操作信息', '授权成功！');
								},
								failure : function() {
									Ext.ux.Toast.msg('操作信息', '授权失败！');
								}
							})
						}
					})
				}
			}).show();
		}

	},
	// 查看
	checkfindRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			record = s[0]
			Ext.Ajax.request({
				url : __ctxPath + '/customer/getInvestPerson.do?perId=' + record.data.perId,
				method : 'POST',
				scope : this,
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					var investPerson = obj.data;
		            new InvestPersonForm({
		            	investPerson : investPerson,
						isRead : true,
						isLook : true,
						isHiddenEdit : true,
						isCareHidden : true
					}).show();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				}
			});
		}
	}
});
