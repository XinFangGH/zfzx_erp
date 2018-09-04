/**
 * @author
 * @class IndexShowView
 * @extends Ext.Panel
 * @description [IndexShow]管理
 * @company 智维软件
 * @createtime:
 */
IndexShowView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				IndexShowView.superclass.constructor.call(this, {
							id : 'IndexShowView',
							title : '页面显示信息管理',
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
							layout : 'form',
							region : 'north',
							colNums:3,
							items:[
								{
								
									
									
								xtype : "dickeycombo",
								fieldLabel : '类型',	
 								hiddenName : 'indexShow.type',
 								name : 'Q_type_S_EQ',
 								nodeKey : 'index_show',
 								emptyText : '请选择',
 								maxLength: 255,
 								scope : this,
								editable : true,
 								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
										})
									}
								}

 							
									
								},{
									fieldLabel:'描述内容',
									name : 'Q_description_S_EQ',
									flex:1,
									xtype : 'textfield'
								},{
									fieldLabel:'插入时间',
									name : 'Q_insertTime_D_EQ',
									flex:1,
									xtype:'datefield',
									format:'Y-m-d'
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
									iconCls : 'btn-add',
									text : '添加[页面显示信息]',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								}, {
									iconCls : 'btn-del',
									text : '删除[页面显示信息]',
									xtype : 'button',
									scope:this,
									handler : this.removeSelRs
								}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					rowActions:true,
					id:'IndexShowGrid',
					url : __ctxPath + "/p2p/listIndexShow.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
									,'type'
									,'typename'
									,'description'
				                    ,'insertTime','webKey','isShow'
							],
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									
									header : 'type',	
									dataIndex : 'type',
									hidden : true
								},{
									header : '类型',
									align:'center',
									dataIndex : 'typename'
								},{
									header : '内容描述',
									align:'center',
									dataIndex : 'description'
								},{
									header : '插入时间',
									align:'center',
									dataIndex : 'insertTime'
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
							}, new Ext.ux.grid.RowActions({
									header:'管理',
									align:'center',
									width:100,
									actions:[{
											 iconCls:'btn-del',qtip:'删除',style:'margin:0 3px 0 3px'
										},{
											 iconCls:'btn-edit',qtip:'编辑',style:'margin:0 3px 0 3px'
										}
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
					new IndexShowForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new IndexShowForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p/multiDelIndexShow.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p/multiDelIndexShow.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new IndexShowForm({
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
