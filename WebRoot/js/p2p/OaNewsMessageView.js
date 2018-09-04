/**
 * @author
 * @class OaNewsMessageView
 * @extends Ext.Panel
 * @description [OaNewsMessage]管理
 * @company 智维软件
 * @createtime:
 */
OaNewsMessageView = Ext.extend(Ext.Panel, {
	       querySql:"",//不同状态列表查询SQL
	       isSeeRs:true,//查询按钮，默认不显示
	       isEditRs:true,//编辑站内信，默认不显示
	       isDeleteRs:true,//删除站内信，默认不显示
	       isSendRs:true,//发送站内信，默认不显示
	       title:"站内信草稿管理",
	       iconCls:"menu-finance",
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (typeof(_cfg.messageType) != "undefined" && _cfg.messageType=="1") {//草稿箱未发送，同时未删除
					this.isEditRs=false;//出现编辑button
					this.isDeleteRs=false;//出现删除button
					this.isSendRs=false;//出现发送button
					this.querySql="?status=0&isDelete=0";
				}else if(typeof(_cfg.messageType) != "undefined" && _cfg.messageType=="2"){//已发送站内信（成功发送并且未删除）
					this.isSeeRs=false;//出现查看button
					this.isDeleteRs=false;//出现删除button
					this.querySql="?status=1&isDelete=0";
					this.title="已发送信件管理";
				}else if(typeof(_cfg.messageType) != "undefined" && _cfg.messageType=="3"){//已删除站内信（不管是否已发送）
					this.isSeeRs=false;//出现查看button
					this.querySql="?isDelete=1";
					this.title="已删除信件管理";
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				OaNewsMessageView.superclass.constructor.call(this, {
							id : 'OaNewsMessageView'+this.messageType,
							title : this.title,
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'form',
							region : 'north',
							colNums:3,
							items:[{
									fieldLabel:'标题',
									name : 'title',
									flex:1,
									xtype : 'textfield'
									},{
									fieldLabel:'内容',
									name : 'content',
									flex:1,
									xtype : 'textfield'
								},{
									fieldLabel:'发送时间',
									name : 'sendTime',
									flex:1,
									xtype:'datefield',
									format:'Y-m-d'
								},{
									fieldLabel:'操作人',
									name : 'operator',
									flex:1,
									xtype : 'textfield'
								},{
									fieldLabel:'发送人',
									name : 'addresser',
									flex:1,
									xtype : 'textfield'
								}],
								buttons:[
									{
										text:'查询',
										scope:this,
										iconCls:'btn-search',
										handler:this.search
									},{
										text:'重置',
										scope:this,
										iconCls:'btn-reset',
										handler:this.reset
									}							
								]	
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-detail',
									text : '查看站内信',
									xtype : 'button',
									scope : this,
									hidden:this.isSeeRs,
									handler : this.seeRs
								},{
									iconCls : 'btn-edit',
									text : '编辑站内信',
									xtype : 'button',
									scope : this,
									hidden:this.isEditRs,
									handler : this.editRs
								}, {
									iconCls : 'btn-del',
									text : '删除站内信',
									xtype : 'button',
									scope:this,
									hidden:this.isDeleteRs,
									handler : this.removeRs
								},{
									iconCls : 'btn-add',
									text : '发送站内信',
									xtype : 'button',
									scope : this,
									hidden:this.isSendRs,
									handler : this.send
								}/*,{
									iconCls : 'btn-see',
									text : '查看收件人站内信',
									xtype : 'button',
									scope : this,
									handler : this.see
								}*/]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//id:'OaNewsMessageGrid',
					url : __ctxPath + "/p2p/listOaNewsMessage.do"+this.querySql,
					fields : [{
									name : 'id',
									type : 'int'
								}
									,'title'
									,'content'
									,'sendTime'
									,'recipient'
									,'operator'
									,'addresser'
									,'status'
									,'readTime'
									,'isDelete'
									,'comment1'
									,'comment2'
									,'comment3'
									,'type'
									,'typename'
									
							],
					columns:[{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '消息类型',
									align:'center',
									dataIndex : 'typename'
								},{
									header : '标题 ',
									align:'left',
									dataIndex : 'title'
								},{
									header : '内容',	
									align:'left',
									dataIndex : 'content'
								},{
									header : '发送时间',
									align:'center',
									dataIndex : 'sendTime',
									format:'Y-m-d'
								},{
									header : '接收人',
									align:'center',
									dataIndex : 'comment1'
								},{
									header : '操作人',
									align:'center',
									dataIndex : 'operator'
								},{
									header : '发送人',
									align:'center',
									dataIndex : 'addresser'
								},{
									header : '状态',
									align:'center',
									dataIndex : 'status',
									renderer:function(val){
										if(val==1){
											return "已发送";
										}else{
											return "未发送";
										}
									}
								}]
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
					new OaNewsMessageForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new OaNewsMessageForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
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
					var record=s[0];
					var status=record.data.status;
					Ext.Msg.confirm('确认信息', '您真的要将【'+s[0].data.title+"】删除", function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/p2p/isDeleteOaNewsMessage.do',
									method : 'POST',
									scope:this,
									success :function(response, request){
										flashPanel.getStore().reload();
										var object=Ext.util.JSON.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息', object.msg);
									},
									params : {
										id:s[0].data.id
									}
						       })
							}
						})
					
				}
			},
			//把选中ID删除
			removeSelRs : function() {
				
				$delGridRs({
					url:__ctxPath + '/p2p/isDeleteOaNewsMessage.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			
			//把选中ID的站内信发送给用户
			send : function() {
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
					var status=s[0].data.status;
					var msgrecord="发送";
					if(status==1){
						msgrecord="重新发送";
					}
					Ext.Msg.confirm('确认信息', '您真的要将'+s[0].data.title+msgrecord, function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath + '/p2p/newsendOaNewsMessage.do',
								method : 'POST',
								scope:this,
								success :function(response, request){
									flashPanel.getStore().reload();
									var object=Ext.util.JSON.decode(response.responseText);
									Ext.ux.Toast.msg('操作信息', object.msg);
								},
								params : {
									id:s[0].data.id
								}
					       })
						}
					})
					
				}
			},
			
			seeRs:function(){
				var grid = this.gridPanel;
				var store = grid.getStore();
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.Msg.alert('状态', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.Msg.alert('状态', '只能选中一条记录');
					return false;
				} else {
					var record=s[0];
					var status=record.data.status;
					new OaNewsMessageForm({
							id : record.data.id,
							isSendHide:true,
							isAllReadOnly:true,
							isAllHide:true
							
						}).show();
				}
			},
			editRs:function(){
				var grid = this.gridPanel;
				var store = grid.getStore();
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.Msg.alert('状态', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.Msg.alert('状态', '只能选中一条记录');
					return false;
				} else {
					var record=s[0];
					var status=record.data.status;
					new OaNewsMessageForm({
							id : record.data.id
						}).show();
				}
			},
			//查看本站内信收件人的站内信信息
			see:function(){
				var grid = this.gridPanel;
				var store = grid.getStore();
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.Msg.alert('状态', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.Msg.alert('状态', '只能选中一条记录');
					return false;
				} else {
					var record=s[0];
					var status=record.data.status;
					if(status==1){//已经发送的站内信
						new OaNewsMessageShowView({
							id : record.data.id
						}).show();
					}else{//没有发送的站内信
						Ext.Msg.alert('状态', '未发送站内信无法使用查看功能');
						return false;
					}
				}
			}
});
