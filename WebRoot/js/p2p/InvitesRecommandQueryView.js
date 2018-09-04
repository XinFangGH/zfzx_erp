/**
 * @author
 * @class BpCustMemberView
 * @extends Ext.Panel
 * @description [BpCustMember]管理
 * @company 智维软件
 * @createtime:
 */
InvitesRecommandQueryView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				InvitesRecommandQueryView.superclass.constructor.call(this, {
							id : 'InvitesRecommandQueryView',
							title : "邀请推荐查询",
							region : 'center',
							iconCls:"menu-finance",
							layout : 'border',
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
											fieldLabel:'推荐人用户名',
											name : 'Q_loginname_S_LK',
											flex:1,
											anchor : "90%",
											xtype : 'textfield'
											}]
					     	},{
					     		columnWidth :.2,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'推荐人邀请码',
											name : 'Q_recommandPerson_S_LK',
											flex:1,
											anchor : "90%",
											xtype : 'textfield'
										}]
				     	},{
				     		columnWidth :.2,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							border : false,
							items : [{
											fieldLabel:'推荐人姓名',
											name : 'Q_truename_S_LK',
											flex:1,
											anchor : "90%",
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
		     		}]
								
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [ {
									iconCls : 'btn-del',
									text : '查询推荐详情',
									xtype : 'button',
									scope:this,
									iconCls : 'btn-search',
									hidden:false,
									handler : this.updatePassword
								},{
									 
									text : '刷新推荐用户数',
									xtype : 'button',
									scope:this,
									hidden:false,
									iconCls : 'btn-reset1',
									handler : this.forbiddenSelRs
								},{
									iconCls : 'btn-userable',
									text : '解除禁用',
									xtype : 'button',
									scope:this,
									hidden:true,
									handler : this.refrshforbidden
								},{
									iconCls : 'btn-userable',
									text : '更换邀请码',
									xtype : 'button',
									scope:this,
									hidden:false,
									handler : this.changeRecommand
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					//rowActions:true,
					id:'BpCustMemberGrid',
					url : __ctxPath + "/p2p/listCommandBpCustMember.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
								,'loginname'
								,'truename'
								,'plainpassword'
								,'telphone'
								,'recommandPerson'
								,'cardcode'
                                ,'recommandNum'
                                ,'secondRecommandNum'
							],
					columns:[{
								header : '推荐人用户名',
								dataIndex : 'loginname',
								anchor : "100%"
							},{
								header : '推荐人邀请码',	
								dataIndex : 'plainpassword' ,
								anchor : "100%"
									 
							},{
								header : '推荐人姓名',
								align:'center',
								dataIndex : 'truename',
								anchor : "100%"
									 
							},{
								header : '推荐人身份证号',	
								dataIndex : 'cardcode',
								anchor : "100%"
									 
							},{
								header : '推荐人手机',
								dataIndex : 'telphone',
								anchor : "100%"
									 
							},{
								header : '上级邀请码',
								dataIndex : 'recommandPerson',
								anchor : "100%"
									 
							},{
								header : '直接推荐用户个数',
								align:'center',
								dataIndex : 'recommandNum',
								anchor : "100%",   
								region:'center',
								renderer : function(val, m, r) {	
									
									return "<a title='查看详情' style ='TEXT-DECORATION:underline;cursor:pointer' onclick=openTw('"+r.get('plainpassword')+"','"+r.get('cardcode')+"','"+r.get('truename')+"'" +
											",'"+r.get('telphone')+"','"+r.get('telphone')+"','"+r.get('email')+"','"+r.get('id')+"')>"+val+"</a>";// 个人贷款合同
								}
							},{
								header : '二级推荐用户个数',
								align:'center',
								dataIndex : 'secondRecommandNum',
								anchor : "100%",   
								region:'center'
							}]
				});
				
				//this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			updatePasswordGrid:function(){   
				new RefreshUserNumTW({
					plainpassword:r.get('plainpassword'),
					cardcode:r.get('cardcode'),
					truename:r.get('truename'),
					loginname:r.get('loginname'),
					telphone:r.get('telphone'),
					email:r.get('email'),
					id:r.get('id')  
				}).show(); 
				
			},
			
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
			//刷新推荐用户数据
			forbiddenSelRs : function() {
				var  flashPanel=this.gridPanel;
					Ext.Msg.confirm('刷新推荐用户数', '本操作运行时间较长，在未返回‘刷新成功’提示前，请勿关闭本窗口和浏览器', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath + '/p2p/refreshUserNumBpCustMember.do',
							method : 'POST',
							scope:this,
							success :function(response, request){
								flashPanel.getStore().reload();
							}
						})
						}
					})
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
					var plainpassword  =s[0].data.plainpassword ;
					var cardcode =s[0].data.cardcode;
					var truename =s[0].data.truename;
					var loginname =s[0].data.loginname;
					var telphone =s[0].data.telphone;
					var email =s[0].data.email;
					var id =s[0].data.id;
					
					new RefreshUserNumTW({
						plainpassword:plainpassword,
						cardcode:cardcode,
						truename:truename,
						loginname:loginname,
						telphone:telphone,
						email:email,
						id:id  
					}).show();   
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
			// 修改邀请码 XF
   			 changeRecommand : function() {
				var  flashPanel=this.gridPanel;
				var  isForBidType=this.isForBidType;
				var s = this.gridPanel.getSelectionModel().getSelections();
                 if (s <= 0) {
                     Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
                     return false;
                 }
                var ids = '';
                 for(var i = 0;i<s.length;i++){
					ids += s[i].data.id+",";
				 }

                 new ChangeRecommand({
					 ids:ids
                 }).show();
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

function openTw(plainpassword,cardcode,truename,loginname,telphone,email,id)
{
	new RefreshUserNumTW({
		plainpassword:plainpassword,
		cardcode:cardcode,
		truename:truename,
		loginname:loginname,
		telphone:telphone,
		email:email,
		id:id  
	}).show();   
	
}
