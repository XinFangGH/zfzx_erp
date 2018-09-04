/**
 * @author
 * @class WebFinanceApplyUploadsView
 * @extends Ext.Panel
 * @description [WebFinanceApplyUploads]管理
 * @company 智维软件
 * @createtime:
 */
WebFinanceApplyUploadsView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				WebFinanceApplyUploadsView.superclass.constructor.call(this, {
							id : 'WebFinanceApplyUploadsView',
							title : '客户资料审核',
							region : 'center',
							layout : 'border',
							iconCls : 'btn-tree-team30',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
					var itemschange=[
								 	new alerdyUpload({tabflag:'alerdyUpload',type:this.type}),
								 	new uploadCertification({tabflag:'uploadCertification',type:this.type}),
								 	new uploadReject({tabflag:'uploadReject',type:this.type})
								];
								this.tabpanel = new Ext.TabPanel({
									resizeTabs : true, // turn on tab resizing
									minTabWidth : 115,
									tabWidth : 135,
									enableTabScroll : true,
									Active : 0,
									width : 600,
									defaults : {
										autoScroll : true
									},
									region : 'center', 
									//layout:'fit'
									deferredRender : true,
									activeTab :0, // first tab initially active
									xtype : 'tabpanel',
										items : itemschange
									});
									
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
					new WebFinanceApplyUploadsForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				new WebFinanceApplyUploadsForm().show();
			},
			//浏览图片
			lookFile:function(){
				
				var rows = this.gridPanel.getSelectionModel().getSelections();
				 
				if(rows>1){
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				}else if(rows==0){
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				}else{
					 var webId=rows[0].data.userID;
					 var materialstype=rows[0].data.materialstype;
					 var mark=webId+"."+materialstype;
					 BpCustMemberPicView(mark,this.isHiddenEdit,"typeisfile",null,null);
				    // BpCustMemberPicView(mark,this.isHiddenEdit,"typeisfile",gridPanel.ownerCt.projId,gridPanel.ownerCt.businessType);
				}
			},
			//操作
			updateRs : function(obj,e) {
				var rows = this.gridPanel.getSelectionModel().getSelections();
				var btest=obj.text;
				var state;
				if(btest=='通过审核'){
					state='3';
				}else if(btest=='拒绝'){
					state='2';
				}
				if(rows>1){
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				}else if(rows==0){
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				}else{
					var id=rows[0].data.id;
					Ext.Ajax.request({
						url : __ctxPath + '/p2p/saveWebFinanceApplyUploads.do',
						method:'post',
						params:{'id':id,'state':state},
						//async:false,
						success: function(resp,opts) {
							/*this.gridPanel.getStore().reload();*/
							Ext.ux.Toast.msg('操作信息', '操作成功!');
						}
					});
					
				}
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p/multiDelWebFinanceApplyUploads.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new WebFinanceApplyUploadsForm({
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
