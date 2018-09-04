/**
*CsProspectivePersonFollowUpManager.js
*/

CsProspectivePersonFollowUpManager = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	name:"CsProspectivePersonFollowUpManager_info",
	titlePrefix : "",
	tabIconCls : "",
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		CsProspectivePersonFollowUpManager.superclass.constructor.call(this, {
			id : 'CsProspectivePersonFollowUpManager'+this.personType,
			title : 0==this.personType?"借款客户跟进记录管理":"投资客户跟进记录管理",
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team23',
			items : [this.searchPanel, this.gridPanel]
			
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow = false;
		var rightValue =  isGranted('_Prospective_followUp_All_'+this.personType);
		var isShop =  isGranted('_Prospective_followUp_shop_'+this.personType);
		if (RoleType == "control") {
			isShow = true;
		}
		var anchor = '100%';
		var tabflag=this.tabflag;
		var labelsize=70;
		var labelsize1=115;
		this.topbar = new Ext.Toolbar({
				items : [{
						iconCls : 'btn-detail1',
						text : '跟进详情',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_Prospective_followUp_info_'+this.personType)?false:true,
						handler : this.seeFollowInfo
					},{
						iconCls : 'btn-comment',
						text : '跟进点评',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_Prospective_followUp_commonent__'+this.personType)?false:true,
						handler : this.doRecomment
					}]
				})
	
		this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
							anchor : '96%',
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
				 //            bodyStyle : 'padding:10px 10px 10px 10px',
							items : [isShow ? {
								columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
									xtype : "combo",
									anchor : "100%",
									fieldLabel : '所属分公司',
									hiddenName : "companyId",
									displayField : 'companyName',
									valueField : 'companyId',
									triggerAction : 'all',
									store : new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath+ '/system/getControlNameOrganization.do',
										fields : ['companyId', 'companyName']
									})
								}]
							}: {
							},  {  
								columnWidth : isShow ? 0.15 :0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '客户名称',
										name : 'customerName',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								}] 
								      
							},{
								columnWidth : isShow ? 0.15 :.2,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [{
											xtype : "combo",
											triggerClass : 'x-form-search-trigger',
											hiddenName : "followPersonId17",
											editable : false,
											fieldLabel : "跟进人",
											anchor : "100%",
											onTriggerClick : function(cc) {
												var obj = this;
												var appuerIdObj = obj.nextSibling();
												var userIds = appuerIdObj.getValue();
												if ("" == obj.getValue()) {
													userIds = "";
												}
												new UserDialog({
													userIds : userIds,
													userName : obj.getValue(),
													single : false,
													title : "选择跟进人",
													callback : function(uId, uname) {
														obj.setValue(uId);
														obj.setRawValue(uname);
														appuerIdObj.setValue(uId);
													}
												}).show();
											}
										},{
				                       	 	xtype : 'hidden',
				                        	name : 'followPersonId'
										}]
							},{
								columnWidth : isShow ? 0.15 :.2,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [{
									fieldLabel : "跟进状态",
									xtype : "dickeycombo",
									hiddenName : 'followUpStatus',
									displayField : 'itemName',
									readOnly : this.isRead,
									nodeKey : 'customer_followUpStatus',
									editable :false,
									anchor : "100%",
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
							}, {
								columnWidth : .07,
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
							}, {
								columnWidth : .07,
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

						});// end of searchPanel
		this.gridPanel = new HT.EditorGridPanel({
			bodyStyle : "width : 100%",
			name : 'CsProspectivePersonFollowUpManagerGrid',
			region : 'center',
			id:"CsProspectivePersonFollowUpManager",
			showPaging:true,
			tbar : this.topbar,
			rowActions : false,
			url : __ctxPath + "/creditFlow/customer/customerProsperctiveFollowup/listBpCustProspectiveFollowup.do?isAll="+rightValue+"&personType="+this.personType
			 +"&isShop="+isShop,
			fields : [{
				name : 'followId',
				type : 'int'
			}, 'followPersonId','name','followDate', 'followType', 'followTitle', 'followInfo', 'successRate',
					'followUpStatus', 'commentorrId','commentorName', 'commentRemark','bpCustProsperctive.customerName',
					'bpCustProsperctive.customerType','bpCustProsperctive.telephoneNumber','bpCustProsperctive.orgName'],

			columns : [{
				header : 'followId',
				align:'center',
				dataIndex : 'commId',
				hidden : true
			},{
				header : "所属分公司",
				sortable : true,
				width : 120,
				hidden:RoleType=="control"?false:true,
				dataIndex : 'bpCustProsperctive.orgName'
			},/*{
				header : '客户类型',
				dataIndex : 'bpCustProsperctive.customerType',
				width : 100,
				renderer:function(v){
					if(eval(v)==eval(1)){
						return "企业";
					}else if(eval(v)==eval(2)){
						return "个人";
					}else{
						return "";
					}
                }
			},*/ {
				header : '客户名称',
				dataIndex : 'bpCustProsperctive.customerName',
				width : 100
			}, {
				header : '跟进人',
				width : 100,
				dataIndex : 'name'
			}, {
				header : '跟进时间',
				width : 100,
				align:'center',
				dataIndex : 'followDate'
			}, {
				header : '跟进方式',
				width : 80,
				sortable : true,
				xtype:'combocolumn',
                gridId:'CsProspectivePersonFollowUpManager',
				dataIndex : 'followType',
				editor:{
						xtype : "dickeycombo",
						nodeKey : 'comm_type',
						anchor : '100%',
						editable : true,
						readOnly : true,
						listeners : {
							afterrender : function(combox) {
							var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == 0|| combox.getValue() == 1|| combox.getValue() == ""|| combox.getValue() == null) {
									combox.setValue("");
									} else {
											combox.setValue(combox.getValue());
									}
									combox.clearInvalid();
								})
							},
							select : function(combo, record,index) {}
					}
									
				}
			}, {
				header : '标题',
				width : 100,
				dataIndex : 'followTitle'
			},{
				header : '跟进内容',
				width : 80,
				dataIndex : 'followInfo'
			}, {
				header : '点评人',
				width : 130,
				dataIndex : 'commentorName'
			},{
				header : '点评备注',
				width : 80,
				dataIndex : 'commentRemark'
			}]
		});
	},
	search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//编辑跟进记录
	seeFollowInfo:function(){
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (rows.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
				var followId=rows[0].data.followId
				var editForm=new CommRecorForm({
					followId:followId,
					flashTargat:this.gridPanel,
					titleChange:"查看跟进记录",
					isRead:true,
					isLook:true,
					isReadOnly:true
				})
				editForm.show();	
		}
	
		
	}
	,
	//设置点评人信息
	doRecomment:function(){
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (rows.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
				var followId=rows[0].data.followId
				var editForm=new updateCommontInfo({
					followId:followId,
					flashTargat:this.gridPanel,
					titleChange:"点评跟进记录",
					isRead:true,
					isReadOnly:true
				})
				editForm.show();	
		}
	
		
	}
	
});