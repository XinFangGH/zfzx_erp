/**
 * @author
 * @class BpCustMemberView
 * @extends Ext.Panel
 * @description [BpCustMember]管理
 * @company 智维软件
 * @createtime:
 */
BpCustMemberToPersonView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpCustMemberToPersonView.superclass.constructor.call(this, {
							id : 'BpCustMemberToPersonView',
							title : '用户档案同步',
							region : 'center',
							layout : 'border',
							iconCls : 'btn-tree-team30',
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
											fieldLabel:'P2P账号',
											name : 'loginname',
											flex:1,
											anchor : "100%",
											xtype : 'textfield'
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
				     		columnWidth :.2,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							border : false,
							items : [{
											fieldLabel:'手机号码',
											name : 'telphone',
											flex:1,
											anchor : "100%",
											xtype : 'textfield'
										}]
				     	},{
			     		columnWidth :.2,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
										fieldLabel:'证件号码',
										name : 'cardcode',
										flex:1,
										anchor : "100%",
										xtype : 'textfield'
									}]
			     	},{
		     			columnWidth :.1,
						layout : 'form',
						border : false,
						labelWidth :50,
						items :[{
							text : '查询',
							xtype : 'button',
							scope : this,
							style :'margin-left:30px',
							anchor : "90%",
							iconCls : 'btn-search',
							handler : this.search
						}]
		     		},{
		     			columnWidth :.1,
						layout : 'form',
						border : false,
						labelWidth :50,
						items :[{
							text : '重置',
							style :'margin-left:30px',
							xtype : 'button',
							scope : this,
							//width : 40,
							anchor : "90%",
							iconCls : 'btn-reset',
							handler : this.reset
						}]
		     		}]
								
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [/*{
									iconCls : 'btn-add',
									text : '建立个人借款客户档案',
									xtype : 'button',
									scope:this,
									handler : this.createPerson
								},*/{
									iconCls : 'btn-sync',
									text : '同步数据',
									xtype : 'button',
									scope:this,
									handler : this.updatePerson
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					autoScroll : true,
					url : __ctxPath + "/p2p/custListBpCustMember.do?online=0",
					fields : [{
									name : 'p2pId',
									type : 'long'
								}
								,'personId'
								,'loginname'
								,'p2pCardNum'
								,'cardtype'
								,'cardtypeValue'
								,'cardNum'
								,'truename'
								,'telphone'
								,'cellphone'
								,'email'
								,'selfemail'
								,'name'																											],
					columns:[{
									header : 'P2P账号',
									align:'center',
									dataIndex : 'loginname'

								},{
									header : 'P2P姓名',	
									dataIndex : 'truename'
								},{
									header : 'ERP姓名',	
									dataIndex : 'name'
								},{
									header : '证件类型',
									align:'center',
									dataIndex : 'cardtypeValue'
								},{
									header : 'ERP证件号码',
									align:'center',
									dataIndex : 'cardNum'
								},{
									header : 'P2P证件号码',
									align:'center',
									dataIndex : 'p2pCardNum'
								},{
									header : 'ERP手机号码',
									align:'center',
									dataIndex : 'cellphone'
									
								},{
									header : 'P2P手机号码',
									align:'center',
									dataIndex : 'telphone'
								},{
									header : 'ERP邮箱',
									align:'center',
									dataIndex : 'selfemail'
								},{
									header : 'P2P邮箱',
									align:'center',
									dataIndex : 'email'
								}
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
			
			//创建记录
			createPerson : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				var gridPanel=this.gridPanel
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					if(null!=s[0].data.personId && s[0].data.personId!=''){
						Ext.ux.Toast.msg('操作信息', '该用户已建立个人客户档案，不能重复建立');
						return;
					}
					if(null==s[0].data.p2pCardNum || s[0].data.p2pCardNum==''){
						Ext.ux.Toast.msg('操作信息', '该用户没有证件号码，不能建立个人客户档案');
						return;
					}
					Ext.Ajax.request({
							url : __ctxPath + '/p2p/getPersonInfoBpCustMember.do',
							method : 'POST',
							scope:this,
							success :function(response, request){
								var obj = Ext.util.JSON.decode(response.responseText);
								if(typeof(obj.msg)!='undefined' && null!=obj.msg){
									Ext.ux.Toast.msg('操作信息', obj.msg);
									return;
								}
								Ext.Msg.confirm('确认信息', '您确认要建立个人客户档案吗?', function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											url : __ctxPath + '/p2p/savePersonInfoBpCustMember.do',
											method : 'POST',
											scope:this,
											success :function(response, request){
												var obj = Ext.util.JSON.decode(response.responseText);
												Ext.ux.Toast.msg('操作信息', '建立成功');
												gridPanel.getStore().reload();
											},
											params : {
												id:s[0].data.p2pId
											}
								       })
									}
								})
							},
							params : {
								id:s[0].data.p2pId
							}
				       })
					
				}
			},
			updatePerson : function(){
				
				var s = this.gridPanel.getSelectionModel().getSelections();
				var gridPanel=this.gridPanel
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					if(null==s[0].data.personId || s[0].data.personId==''){
						Ext.ux.Toast.msg('操作信息', '该用户尚未建立个人客户档案，请先建立个人客户档案，再同步数据');
						return;
					}
					Ext.Msg.confirm('确认信息', '您确认要同步数据吗?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath + '/p2p/updatePersonInfoBpCustMember.do',
								method : 'POST',
								scope:this,
								success :function(response, request){
									Ext.ux.Toast.msg('操作信息', '同步成功');
									gridPanel.getStore().reload();
								},
								params : {
									id:s[0].data.p2pId,
									personId:s[0].data.personId
								}
					       })
						}
					})
				}
			
			}
			
			});
