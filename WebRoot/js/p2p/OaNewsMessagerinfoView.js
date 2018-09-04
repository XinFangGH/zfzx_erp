/**
 * @author
 * @class OaNewsMessagerinfoView
 * @extends Ext.Panel
 * @description [OaNewsMessagerinfo]管理
 * @company 智维软件
 * @createtime:
 */


OaNewsMessagerinfoView = Ext.extend(Ext.Panel, {
	       	 title:"系统收件箱管理",
	       	 iconCls:"menu-finance",
	         querySql:"",//不同状态列表查询SQL
	         isSeeRs:true,//查询按钮，默认不显示
	       	 isEditRs:true,//编辑站内信，默认不显示
	         isDeleteRs:true,//删除站内信，默认不显示
	         isRead:true,//标记已读，默认不显示
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="1") {//系统收件箱管理
					this.isEditRs=false;//出现编辑button
					this.isDeleteRs=false;//出现删除button
					this.isSendRs=false;//出现发送button
					this.querySql="?status=2&useType=0";//useType=1  表示系统收件
					this.messageInfoType=_cfg.messageInfoType;
				}else if(typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="2"){//用户收件箱管理
					this.isSeeRs=false;//出现查看button
					this.isDeleteRs=false;//出现删除button
					this.querySql="?status=2&useType=1";//useType=0   表示用户收件
					this.title="用户收件箱管理";
					this.messageInfoType=_cfg.messageInfoType;
				}else if(typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="3"){//已删除系统收件
					this.isSeeRs=false;//出现查看button
					this.querySql="?status=1&useType=0";
					this.title="已删除系统收件";
					this.messageInfoType=_cfg.messageInfoType;
				}else if(typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="4"){//已删除用户信件
					this.isSeeRs=false;//出现查看button
					this.querySql="?status=1&useType=1";
					this.title="已删除用户信件";
					this.messageInfoType=_cfg.messageInfoType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				OaNewsMessagerinfoView.superclass.constructor.call(this, {
							id : 'OaNewsMessagerinfoView'+this.messageInfoType,
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
								}, {
									iconCls : 'btn-del',
									text : '删除站内信',
									xtype : 'button',
									scope:this,
									hidden:this.isDeleteRs,
									handler : this.removeRs
								}, {
									iconCls : 'btn-del',
									text : '标记已读',
									xtype : 'button',
									scope:this,
									hidden:this.isRead,
									handler : this.markRead
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//id:'OaNewsMessageGrid',
					url : __ctxPath + "/p2p/listOaNewsMessagerinfo.do"+this.querySql,
					fields : [{
									name : 'id',
									type : 'int'
								},'userId','userType','userName','messageId','status','readStatus'
								 ,'readTime','istop','isTopTime','title','content','sendTime','operator',
								 'addresser','typename'],
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
									format:'Y-m-d '
								},{
									header : '收件人',
									align:'center',
									dataIndex : 'userName'
								},{
									header : '发件人',
									align:'center',
									dataIndex : 'addresser'
								},{
									header : '状态',	
									align:'center',
									dataIndex : 'status',
									renderer:function(val){
										if(val==1){
											return "已删除";
										}else if(val==1){
											return "已发送";
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
									url : __ctxPath + '/p2p/isDeleteOaNewsMessagerinfo.do',
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
								url : __ctxPath + '/p2p/sendAllUserOaNewsMessage.do',
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
							id : record.data.messageId,
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





/*OaNewsMessagerinfoView = Ext.extend(Ext.Panel, {
	       title:"系统收件箱管理",
	         querySql:"",//不同状态列表查询SQL
	         isSeeRs:true,//查询按钮，默认不显示
	       	 isEditRs:true,//编辑站内信，默认不显示
	         isDeleteRs:true,//删除站内信，默认不显示
	         isRead:true,//标记已读，默认不显示
	         
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="1") {//系统收件箱管理
					this.isEditRs=false;//出现编辑button
					this.isDeleteRs=false;//出现删除button
					this.isSendRs=false;//出现发送button
					this.querySql="?status=1&useType=0";//useType=1  表示系统收件
					this.messageInfoType=_cfg.messageInfoType;
				}else if(typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="2"){//用户收件箱管理
					this.isSeeRs=false;//出现查看button
					this.isDeleteRs=false;//出现删除button
					this.querySql="?status=1&useType=1";//useType=0   表示用户收件
					this.title="用户收件箱管理";
					this.messageInfoType=_cfg.messageInfoType;
				}else if(typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="3"){//已删除系统收件
					this.isSeeRs=false;//出现查看button
					this.querySql="?status=2&useType=0";
					this.title="已删除系统收件";
					this.messageInfoType=_cfg.messageInfoType;
				}else if(typeof(_cfg.messageInfoType) != "undefined" && _cfg.messageInfoType=="4"){//已删除用户信件
					this.isSeeRs=false;//出现查看button
					this.querySql="?status=2&useType=1";
					this.title="已删除用户信件";
					this.messageInfoType=_cfg.messageInfoType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				OaNewsMessagerinfoView.superclass.constructor.call(this, {
							id : 'OaNewsMessagerinfoView'+this.messageInfoType,
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
					autoWhith : true,
					layout : 'column',
					region : 'north',
					border : false,
					height : 70,
					anchor : '100%',
					layoutConfig : {
						align : 'middle'
					},
					bodyStyle : 'padding:15px 0px 0px 0px',
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '收件人',
											name : 'Q_customerName_S_LK',
											anchor : "100%",
											xtype : 'textfield'
										}]
							},{
								columnWidth : .125,
								layout : 'form',
								border : false,
								labelWidth : 60,
								items : [{
											text : '查询',
											xtype : 'button',
											scope : this,
											style : 'margin-left:30px',
											anchor : "90%",
											iconCls : 'btn-search',
											handler : this.search
										}]
							},{
								columnWidth : .125,
								layout : 'form',
								border : false,
								labelWidth : 60,
								items : [{
										
											text : '重置',
											style : 'margin-left:30px',
											xtype : 'button',
											scope : this,
											anchor : "90%",
											iconCls : 'btn-reset',
											handler : this.reset
										}]
							}]
				});

				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-see',
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
								},{
									iconCls : 'btn-see',
									text : '查看收件人站内信',
									xtype : 'button',
									scope : this,
									handler : this.see
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					rowActions:true,
					url : __ctxPath + "/p2p/listOaNewsMessagerinfo.do"+this.querySql,
					fields : [{
									name : 'id',
									type : 'int'
								},'userId','userType','userName','messageId','status','readStatus'
								 ,'readTime','istop','isTopTime','title','content','sendTime','operator',
								 'addresser','typename'
							],
							columns:[{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '消息类型',	
									dataIndex : 'typename'
								},{
									header : '标题 ',	
									dataIndex : 'title'
								},{
									header : '内容',	
									dataIndex : 'content'
								},{
									header : '发送时间',	
									dataIndex : 'sendTime',
									format:'Y-m-d'
								},{
									header : '接收人',	
									dataIndex : 'comment1'
								},{
									header : '操作人',	
									dataIndex : 'operator'
								},{
									header : '发送人',	
									dataIndex : 'addresser'
								},{
									header : '状态',	
									dataIndex : 'status',
									renderer:function(val){
										if(val==1){
											return "已发送";
										}else{
											return "未发送";
										}
									}
								}]
					columns:[{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '类型名称',	
									dataIndex : 'typename'
								},{
									header : '标题 ',	
									dataIndex : 'title'
								},{
									header : '发件人',	
									dataIndex : 'userName'
								},{
									header : '收件人',	
									dataIndex : 'userName'
								},{
									header : '状态',	
									dataIndex : 'status',
									renderer:function(val){
										if(val==2){
											return "已发送";
										}else if(val==1){
											return "已删除";
										}else{
											return "未发送";
										}
									}
								},{
									header : '阅读状态',	
									dataIndex : 'readStatus',
									renderer:function(val){
										if(val==1){
											return "已阅读";
										}else{
											return "未阅读";
										}
									}
								},{
									header : '阅读时间',	
									dataIndex : 'readTime',
									format:'Y-m-d'
								},{
									header : '是否置顶',	
									dataIndex : 'istop',
									renderer:function(val){
										if(val==1){
											return "已置顶";
										}else{
											return "未置顶";
										}
									}
								},{
									header : '置顶时间',	
									dataIndex : 'isTopTime',
									format:'Y-m-d'
								}]//end of columns
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
								url : __ctxPath + '/p2p/sendAllUserOaNewsMessage.do',
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
							isSendHide:true
							
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
});*/
