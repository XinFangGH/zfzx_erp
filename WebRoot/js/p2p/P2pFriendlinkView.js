/**
 * @author
 * @class P2pFriendlinkView
 * @extends Ext.Panel
 * @description [P2pFriendlink]管理
 * @company 智维软件
 * @createtime:
 */
P2pFriendlinkView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				P2pFriendlinkView.superclass.constructor.call(this, {
							id : 'P2pFriendlinkView',
							title : '友情链接管理',
							region : 'center',
							iconCls:"menu-finance",
							layout : 'border',
							items : [/* this.searchPanel, */this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'form',
							region : 'north',
							colNums : 3,
							items : [{
										fieldLabel : 'createDate',
										name : 'Q_createDate_D_EQ',
										flex : 1,
										xtype : 'datefield',
										format : 'Y-m-d'
									}, {
										fieldLabel : 'modifyDate',
										name : 'Q_modifyDate_D_EQ',
										flex : 1,
										xtype : 'datefield',
										format : 'Y-m-d'
									}, {
										fieldLabel : 'logo',
										name : 'Q_logo_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'name',
										name : 'Q_name_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'orderList',
										name : 'Q_orderList_N_EQ',
										flex : 1,
										xtype : 'numberfield'
									}, {
										fieldLabel : 'url',
										name : 'Q_url_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}],
							buttons : [{
										text : '查询',
										scope : this,
										iconCls : 'btn-search',
										handler : this.search
									}, {
										text : '重置',
										scope : this,
										iconCls : 'btn-reset',
										handler : this.reset
									}]
						});// end of searchPanel

				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-edit',
										text : '编辑',
										xtype : 'button',
										scope : this,
										handler : this.editRs1
									}, {
										iconCls : 'btn-del',
										text : '删除',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					id : 'P2pFriendlinkGrid',
					url : __ctxPath + "/p2p/listP2pFriendlink.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'createDate', 'modifyDate', 'logo', 'name','webKey','isShow',
							'orderList', 'url'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '名称',
								align:'center',
								dataIndex : 'name'
							}, {
								header : 'Logo',
								align:'center',
								dataIndex : 'logo',
								renderer:this.imageView
							}, {
								header : '链接地址',
								align:'left',
								dataIndex : 'url'
							}, {
								header : '创建日期',
								align:'center',
								dataIndex : 'createDate'
							}, {
								header : '修改日期',
								align:'center',
								dataIndex : 'modifyDate'
							}, {
								header : '网站类别',
								align:'center',
								dataIndex : 'webKey',
								renderer:function(val){
									if(val==1){
									   return 'P2P网站';
									}else if(val==2){
									   return '云购';
									}else if(val==3){
									   return '云众筹';
									}
								}
							},{
								header : '是否显示',
								align:'center',
								dataIndex : 'isShow',
								renderer:function(val){
									if(val==0){
									   return '否';
									}else {
									   return '是';
									}
								}
							},
								{
								header : '排序',
								align:'center',
								dataIndex : 'orderList'
							}
							, new Ext.ux.grid.RowActions({
								hidden:true,
										header : '管理',
										align:'center',
										width : 100,
										actions : [
//										           {
//													iconCls : 'btn-del',
//													qtip : '删除',
//													style : 'margin:0 3px 0 3px'
//												}, {
//													iconCls : 'btn-edit',
//													qtip : '编辑',
//													style : 'margin:0 3px 0 3px'
//												}
												],
										listeners : {
											scope : this,
											'action' : this.onRowAction
										}
									})
									]
						// end of columns
				});

				this.gridPanel.addListener('rowdblclick', this.rowClick);
				this.gridPanel.addListener('cellclick', this.cellclick);

			},// end of the initComponents()
			// 重置查询表单
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			cellclick : function(P2pFriendlinkGrid,rowIndex,columnIndex,e) {
				 
				var friendLinkId = P2pFriendlinkGrid.getStore().getAt(rowIndex).get("id");
//				var url = P2pFriendlinkGrid.getStore().getAt(rowIndex).get("logo");
				var fieldName = P2pFriendlinkGrid.getColumnModel().getDataIndex(columnIndex); //Get field name
				
				if("logo"==fieldName){

				    var friendLinkId = P2pFriendlinkGrid.getStore().getAt(rowIndex).get("id");
					var fieldName = P2pFriendlinkGrid.getColumnModel().getDataIndex(columnIndex); //Get field name
					var tablename  =  "p2p_friendLink";
					var mark = tablename + "." + friendLinkId;
					if("logo"==fieldName){
						P2pFriendLinkShowPic(mark)
					}
//					Ext.Ajax.request({
//						scope : this,
//						url : __ctxPath + '/p2p/getwebPathP2pFriendlink.do',
//						params : {
//							tablename : "p2p_friendLink",
//							friendLinkId : friendLinkId
//						},
//						method : 'post', 
//						success : function(response, request) {
//								var obj=Ext.util.JSON.decode(response.responseText);
////								alert(obj.webpaths);
//								var	webpaths = obj.webpaths;
//						 
//								window.open('pages/imageShow.jsp?url='+webpaths,'Logo预览','height=300,width=300,top=200,left=500,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
//							}
//					});
					

				}
			},
			
			imageView :  function(){
				return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
			},
			// 按条件搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			// GridPanel行点击处理事件
			rowClick : function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							new P2pFriendlinkForm({
										id : rec.data.id
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				
				new P2pFriendlinkForm({
					friendLinkId:""
				}).show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/p2p/multiDelP2pFriendlink.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/p2p/multiDelP2pFriendlink.do',
							grid : this.gridPanel,
							idName : 'id'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new P2pFriendlinkForm({
							id : record.data.id
						}).show();
			},
			// 编辑Rs工具栏
			editRs1 : function(record) {
			  
				var grid = Ext.getCmp('P2pFriendlinkGrid'); 
				var selections = grid.getSelectionModel().getSelections();
				var len = selections.length;
				 
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
					return;
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
					return;
				}
				var linkId = selections[0].data.id;
				new P2pFriendlinkForm({
					id : linkId,
					isAllReadOnly:false,
					friendLinkId:linkId
				}).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
