/**
 * @author
 * @class CsProspectivePersonDistributionView
 * @extends Ext.Panel
 * @description [CsProspectivePersonDistributionView]管理
 * @company 智维软件
 * @createtime:
 */
CsProspectivePersonDistributionView= Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsProspectivePersonDistributionView.superclass.constructor.call(this, {
							id : 'CsProspectivePersonDistributionView'+this.personType,
							title : 0==this.personType?"借款客户意向资源分配":"投资客户意向资源分配",
							region : 'center',
							layout : 'border',
							iconCls : 'btn-tree-team23',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var tabflag=this.tabflag;
				var labelsize=70;
				var labelsize1=115;
				
				var isShow = false;
				var rightValue =  isGranted('_Prospective_distribution_All_'+this.personType);
				var isShop =  isGranted('_Prospective_distribution_shop_'+this.personType);
				if (RoleType == "control") {
					isShow = true;
				}
				
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
								      
							},{ columnWidth : isShow ? 0.15 :0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '联系电话',
										name : 'telephoneNumber',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								} ]
							}/*,{
								columnWidth : .2,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [{
										fieldLabel : '客户类型',
										hiddenName : 'customerType',
										editable : false,
										width:105,
										displayField : 'name',
									    valueField : 'id',
									    triggerAction : 'all',
										xtype : 'combo',
										mode : 'local',
										autoload : true,
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["企业", "1"],["个人", "2"]]
													}),
										anchor : '96%'
								}]
							}*/,{
								columnWidth : isShow ? 0.15 :.2,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [{
									fieldLabel : "跟进状态",
									xtype : "dickeycombo",
									hiddenName : 'followUpType',
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
				
						this.topbar = new Ext.Toolbar({
							items : [{
					        	iconCls : 'btn-add',
					        	text : '添加',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_distribution_add_'+this.personType)?false:true,
								handler : this.addCsProspectivePerson
									
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_distribution_add_'+this.personType)?false:true
							}),{
					        	iconCls : 'btn-details',
					        	text : '查看',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_distribution_see_'+this.personType)?false:true,
								handler : this.seeCsProspectivePerson
									
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_distribution_see_'+this.personType)?false:true
							}),{
								iconCls : 'btn-edit',
								text : '修改',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_distribution_edit_'+this.personType)?false:true,
								handler : this.editCsProspectivePerson
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_distribution_edit_'+this.personType)?false:true
							}),{
								iconCls : 'btn-idea',
								text : '意向分配',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_distribution_grant_'+this.personType)?false:true,
								handler : this.prospectivepDistribution
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_distribution_grant_'+this.personType)?false:true
							})/*,{
								iconCls : 'btn-detail',
								text : '高级意向分配',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_distribution_seniorGrant_'+this.personType)?false:true,
								handler : this.seniorgrant
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_distribution_seniorGrant_'+this.personType)?false:true
							}),{
								iconCls : 'btn-setting',
								text :'导出Excel',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_distribution_outToExcel_'+this.personType)?false:true,
								handler : this.preview
					        	
					
							}*/]
						});
					
				this.gridPanel = new HT.EditorGridPanel({
					bodyStyle : "width : 100%",
					id:'BpCustProsperctive'+this.isAll+this.ProsperctiveType,
					region : 'center',
					tbar : this.topbar,
					viewConfig: {  
		            	forceFit:false  
		            },
					rowActions : false,
					url : __ctxPath + "/creditFlow/customer/customerProsperctive/listBpCustProsperctive.do?isAll="+rightValue+"&prosperctiveType=2&personType="+this.personType
					+"&isShop="+isShop,
					fields : [{
								name : 'perId',
								type : 'int'
							}, 'orgName','customerType','customerName', 'telephoneNumber','postaddress', 'customerChannel','customerChannelValue',
							'email', 'hangyeType','hangyeName', 'area','loanMoney',
							'loanPeriod', 'loanDate','loanType', 'isMortgage','orgName',
							'mortgageRemark', 'remark', 'creatorId','creatorName','createDate','belongId','belongName','companyId','followUpType','nextFollowDate','followUpRemark','followUpcount'],
					columns : [{
								header : 'perId',
								align:'center',
								dataIndex : 'perId',
								hidden : true
							},{
								header : "所属分公司",
								sortable : true,
								width : 120,
								hidden:RoleType=="control"?false:true,
								dataIndex : 'orgName'
							}, /*{
								header : '客户类型',
								dataIndex : 'customerType',
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
								dataIndex : 'customerName',
								width : 100
							}, {
								header : '客户来源',
								dataIndex : 'customerChannel',
								xtype:'combocolumn',
                				gridId:'BpCustProsperctive'+this.isAll+this.ProsperctiveType,
								editor:{
									xtype : "dickeycombo",
									displayField : 'itemName',
									readOnly : true,
									nodeKey : 'customer_channel',
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
								},
								width : 100
							}, {
								header : '地区',
								dataIndex : 'area',
								align:'center',
								width : 120
							
							}, {
								header : '联系电话',
								dataIndex : 'telephoneNumber',
								align:'center',
								width : 80
							
							}, /*{
								header : '主营业务',
								width : 100,
								dataIndex : 'hangyeName',
								align : 'center'
							}, */{
								header :'创建人',
								width : 100,
								align:'center',
								dataIndex : 'creatorName'
							}, {
								header :'共享人',
								width : 100,
								align:'center',
								dataIndex : 'belongName'
							}, {
								header : '跟进状态',
								align:'center',
								dataIndex : 'followUpType',
								width : 100,
								xtype:'combocolumn',
                				gridId:'BpCustProsperctive'+this.isAll+this.ProsperctiveType,
                				editor:{fieldLabel : "跟进状态",
									xtype : "dickeycombo",
									hiddenName : 'bpCustProsperctive.followUpType',
									displayField : 'itemName',
									readOnly : this.isRead,
									nodeKey : 'customer_followUpStatus',
									emptyText : "请选择",
									editable :false,
									anchor : "100%"
								},
								align : 'right'
								
							}, {
								header : '录入时间',
								dataIndex : 'createDate',
								align:'center',
								width : 100,
								sortable:true
							}
							, {
								header : '最后跟进时间',
								dataIndex : 'lastFollowUpDate',
								align:'center',
								width : 100
							}, {
								header : '跟进次数(次)',
								dataIndex : 'followUpcount',
								width : 100
							
							}, {
								header : '下次跟进时间',
								width : 100,
								dataIndex : 'nextFollowDate',
								align : 'center',
								hidden : false
							}, {
								header : '提醒内容',
								width : 200,
								dataIndex : 'followUpRemark',
								align : 'left',
								hidden : false
							}
						]
					});

				 this.gridPanel.addListener('rowdblclick',this.rowClick);

			},

			reset : function() {
				this.searchPanel.getForm().reset();
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
			
			addCsProspectivePerson : function() {
				new CsProspectivePersonForm({
					titleChange:"添加意向客户",
					isHiddenCommRecord:true,
					flashTargat:this.gridPanel.getStore(),
					isReadOnly:false,
					personType : this.personType
				}).show()
			},
			seeCsProspectivePerson : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new CsProspectivePersonForm({
						perId:record.data.perId,
						titleChange:"查看意向客户",
						isHiddenCommRecord:false,
						isReadOnly:true,
						isLook:true,
						personType : this.personType
					}).show()
				}
				
			},
			editCsProspectivePerson : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new CsProspectivePersonForm({
						perId:record.data.perId,
						titleChange:"编辑意向客户",
						flashTargat:this.gridPanel.getStore(),
						isHiddenCommRecord:false,
						isReadOnly:false,
						isLook:false,
						personType : this.personType
					}).show()
				}
			},
			prospectivepDistribution:function(){
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var grid =this.gridPanel;
					var record = this.gridPanel.getSelectionModel().getSelected();
					var perId =record.data.perId;
					var belongedIdStr =record.data.belongId;
					var belongerStr=record.data.belongName;
					new UserDialog({
						single : false,
						title : "授权共享人",
						userIds : belongedIdStr,
						userName : belongerStr,
						callback : function(uId, uname) {
							Ext.Msg.confirm('信息确认', '确认为选中的客户授权共享人吗？', function(btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : __ctxPath+ "/creditFlow/customer/customerProsperctive/getGrantBpCustProsperctive.do",
										method : "post",
										params : {
											perId:perId,
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
			seniorgrant:function(){
				new seniorgrant({}).show()
			}
			
		});
