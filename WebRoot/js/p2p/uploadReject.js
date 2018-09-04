/**
 * @author
 * @class WebFinanceApplyUploadsView
 * @extends Ext.Panel
 * @description [WebFinanceApplyUploads]管理
 * @company 智维软件
 * @createtime:
 */
uploadReject = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				uploadReject.superclass.constructor.call(this, {
							id : 'uploadReject',
							title : '已驳回的认证',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							region : 'north',
							items:[{
								columnWidth : .2,
								layout : 'form',
								labelWidth :60,
								labelAlign : 'right',
								border : false,
								items : [{
									xtype : 'textfield',
									fieldLabel : '用户名',
									anchor : '100%',
									name : 'loginname'
								}]
							},{
								columnWidth : .2,
								layout : 'form',
								labelWidth :60,
								labelAlign : 'right',
								border : false,
								items : [{
									xtype : 'textfield',
									fieldLabel : '真实姓名',
									anchor : '100%',
									name : 'truename'
								}]
							},{
								columnWidth : .07,
								layout : 'form',
								border : false,
								style : 'margin-left:20px',
								items : [{
									xtype : 'button',
									text:'查询',
									scope:this,
									iconCls:'btn-search',
									handler:this.search
								}]
							},{
								columnWidth : .07,
								layout : 'form',
								border : false,
								style : 'margin-left:20px',
								items : [{
									xtype : 'button',
									text:'重置',
									scope:this,
									iconCls:'btn-reset',
									handler:this.reset
								}]
							}]
								
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
						items : [
						         new Ext.Toolbar.Separator({
										hidden : isGranted('_create_grkh') ? false : true
									}),{
										iconCls : 'btn-readdocument',
										text : '浏览',
										xtype : 'button',
										scope : this,
										handler : this.lookFile
									}/*,{
										iconCls : 'btn-add',
										text : '通过审核',
										xtype : 'button',
										scope : this,
										handler : this.updateRs
									},{
										iconCls : 'btn-del',
										text : '拒绝',
										xtype : 'button',
										scope:this,
										handler : this.updateRs
									}*/
						        ]
				});
				 var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					plugins : [summary],
					//使用RowActions
					rowActions:true,
					id:'WebFinanceApplyUploadsGrid',
					url : __ctxPath + "/p2p/upLoadListWebFinanceApplyUploads.do?state=2",
					fields : [{
									name : 'id',
									type : 'int'
								}
								,'userID'
								,'materialstype'
								,'files'
								,'status'
								,'lastuploadtime'
								,'loginName'
								,'pictureNum'
								,'truename'
								,'rejectReason'
								,'materialCount'
							],
					columns:[
								{
									header : 'id',
									align:'center',
									dataIndex : 'id',
									hidden : true
								}
								,{
									header : '用户账号',
									align:'center',
									dataIndex : 'loginName'
								},{
									header : '真实姓名',
									align:'center',
									summaryRenderer : totalMoney,
									dataIndex : 'truename'
								},
								{
									header : '状态',	
									align:'center',
									dataIndex : 'status',
									renderer:function(v){
                                    if(v==0){
										return '未上传';
	                                    }else if(v==1){
	                                    	return '已上传、待审查或补充材料';
	                                    }else if(v==2){
	                                    	return '已驳回';
	                                    }else if(v==3){
	                                    	return '已认证';
	                                    }
									}
								},
								/*{
									header : '用户ID',	
									dataIndex : 'userID'
								}
								,*/{
									header : '材料类型',
									align:'center',
									dataIndex : 'materialstype'
								}
								,{
									header : '数量',
									align:'center',
									summaryType : 'sum',
									dataIndex : 'materialCount'
								}
								
								,{
                                    header : '上传时间',
                                    align:'center',
									dataIndex : 'lastuploadtime'
								},{
									header : '驳回原因',	
									dataIndex : 'rejectReason'
								}
															, new Ext.ux.grid.RowActions({
									header:'',
									width:0,
									actions:[/*{
											 iconCls:'btn-del',qtip:'删除',style:'margin:0 3px 0 3px'
										},{
											 iconCls:'btn-edit',qtip:'编辑',style:'margin:0 3px 0 3px'
										}*/
									],
									listeners:{
										scope:this,
										'action':this.onRowAction
									}
								})
					]//end of columns
				});
				
				//this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			/*updateSelRs：function(obj,e){
				 
				var rows = this.gridPanel.getSelectionModel().getSelections();
				var id=rows[0].data.id;
				var btest=obj.text;
				var state;
				if(btest=='通过审核'){
					state='3'
				}else if(btest=='拒绝'){
					state='2'
				}
				if (rows.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (rows.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					Ext.Ajax.request({
						url : __ctxPath + '/p2p/saveWebFinanceApplyUploads.do',
						method:'post',
						params:{'id':id,'state':state},
						async:false,
						success: function(resp,opts) {
							Ext.ux.Toast.msg('操作信息', '操作成功!');
							this.gridPanel.getStore().reload();
						}
					});
					
				}}
				
			},*/
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
				 
				if(rows.length>1){
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				}else if(rows.length==0){
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
