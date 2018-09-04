/**
 * @author:
 * @class FormDefView
 * @extends Ext.Panel
 * @description [FormDef]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
FormDefView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {

				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FormDefView.superclass.constructor.call(this, {
							id : 'FormDefView',
							title : '流程表单定义管理',
							region : 'center',
							iconCls:'menu-form',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.FormPanel({
					layout : 'form',
					region : 'north',
					height : 40,
					width : '100%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						xtype : 'container',
						layout : 'column',
						border : false,
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						defaults : {
							xtype : 'textfield'
						},
						layoutConfig : {
							align : 'middle',
							padding : '5'
						},
						items : [{
							columnWidth : .25,
							xtype : 'container',
							layout : 'form',
							items : [{
								anchor : '99%',
								fieldLabel : '表单标题',
								name : 'Q_formTitle_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}]
						}, {
							columnWidth : .25,
							xtype : 'container',
							layout : 'form',
							items : [{
								anchor : '99%',
								fieldLabel : '表单描述',
								name : 'Q_formDesp_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}]
						}, {
							columnWidth : .25,
							xtype : 'container',
							layout : 'form',
							items : [{
								anchor : '99%',
								fieldLabel : '生成实体状态',
								hiddenName : 'Q_isGen_SN_EQ',
								xtype : 'combo',
								mode : 'local',
								triggerAction : 'all',
								editable : false,
								forceSelection : true,
								store : [['', '全部'],['0', '未生成'],['1','已生成']]
							}]
						}, {
							columnWidth : .25,
							xtype : 'container',
							layout : 'column',
							defaults : {
								xtype : 'button'
							},
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'reset',
								handler : this.reset
							}]
						}]
					}]
				});

				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-add',
						text : '添加表单',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_addbd')?false:true,
						handler : this.createRs
					}, '-', {
						iconCls : 'btn-del',
						text : '删除表单',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_removebd')?false:true,
						handler : this.removeSelRs
					}, '-', {
						iconCls : 'btn-check',
						text : '创建全部实体',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_cjqbst')?false:true,
						handler : this.genAllEnt
					}]
				});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					id : 'FormDefGrid',
					url : __ctxPath + "/flow/listFormDef.do",
					fields : [{
								name : 'formDefId',
								type : 'int'
							}, 'formTitle', 'formDesp', 'defHtml', 'status','isGen','isDefault'],
					columns : [{
								header : 'formDefId',
								dataIndex : 'formDefId',
								hidden : true
							}, {
								header : '表单名称',
								dataIndex : 'formTitle'
							}, {
								header : '表单描述',
								dataIndex : 'formDesp'
							}, {
								header : '状态',
								dataIndex : 'status',
								renderer:function(value){
							       if(value==1){
							          return '<font color="green">正式发布</font>';
							       }
							       return '<font color="red">草稿</font>';
							    }
							},{
							    header:'是否已生成实体',
							    dataIndex:'isGen',
							    renderer:function(value){
							       if(value==1){
							          return '<font color="green">已生成</font>';
							       }
							       return '<font color="red">未生成</font>';
							    }
							}, new Ext.ux.grid.RowActions({
										header : '管理',
										width : 100,
										actions : [{
													iconCls : 'btn-del',
													qtip : '删除',
													style : 'margin:0 3px 0 3px',
													fn:function(rs){
														if(rs.data.isDefault==1){
															return false;
														}
														return true;
													}
												}, {
													iconCls : 'btn-edit',
													qtip : '编辑',
													style : 'margin:0 3px 0 3px'
												}, {
													iconCls : 'btn-check',
													qtip : '创建实体',
													style : 'margin:0 3px 0 3px',
													fn:function(rs){
														if(rs.data.status==1){
														   return true;
														}
														return false;
													}
												},{
												    iconCls : 'btn-detail',
												    qtip : '查看',
												    style : 'margin: 0 3px 0 3px'
												}],
										listeners : {
											scope : this,
											'action' : this.onRowAction
										}
									})]
						// end of columns
				});

//				this.gridPanel.addListener('rowdblclick', this.rowClick);

			},// end of the initComponents()
			// 重置查询表单
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
			// GridPanel行点击处理事件
			rowClick : function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							new FormDefForm({
										formDefId : rec.data.formDefId
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
//				new FormDefForm().show();
				var gp=this.gridPanel;
				new FormDesignPanelForm({
					callback:function(){
				       gp.getStore().reload();
				    }
				}).show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/flow/multiDelFormDef.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/flow/multiDelFormDef.do',
							grid : this.gridPanel,
							idName : 'formDefId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				var gp=this.gridPanel;
				 new FormDesignPanelForm({
					    formDefId : record.data.formDefId,
					    callback:function(){
					       gp.getStore().reload();
					    }
					}).show();
			},
			//生成全部实体
			genAllEnt:function(){
				
				var totalCount=this.gridPanel.getStore().getTotalCount();
				if(totalCount==0){
					Ext.ux.Toast.msg('操作信息','没有要生成的实体！');
					return;
				}
				var gp=this.gridPanel;
				Ext.Msg.confirm('信息确认', '您确认要创建全部实体吗？', function(btn) {
			        if(btn=='yes'){
			        	 Ext.MessageBox.show({  
			                    msg : '实体创建中，请稍后...',  
			                    width : 300,  
			                    wait : true,  
			                    progress : true,  
			                    closable : true,  
			                    waitConfig : {  
			                        interval : 200  
			                    },  
			                    icon : Ext.Msg.INFO  
			                }); 
			        	 
						   Ext.Ajax.request({
						       url:__ctxPath+'/flow/genAllFormDef.do',
						       timeout:120000,
						       method:'post',
						       success:function(response, opts){
							   		Ext.MessageBox.hide();
							   		
							   		var responseText=Ext.decode(response.responseText);
						    		if(responseText.success){
						    			Ext.ux.Toast.msg('操作信息','成功创建实体！');
							    		gp.getStore().reload();
						    		}else{
						    			Ext.ux.Toast.msg('操作信息','创建实体出错，请联系管理员！');
						    		}
						       },
						       failure:function(){
						    	   	Ext.MessageBox.hide();
						       		Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
						       }
						   });
			        }
			    });
			},
			//生成实体类
			genEnt:function(record){
				var gp=this.gridPanel;
				Ext.Msg.confirm('信息确认', '您确认要创建所选实体吗？', function(btn) {
			        if(btn=='yes'){
			        	Ext.MessageBox.show({  
		                    msg : '实体创建中，请稍后...',  
		                    width : 300,  
		                    wait : true,  
		                    progress : true,  
		                    closable : true,  
		                    waitConfig : {  
		                        interval : 200  
		                    },  
		                    icon : Ext.Msg.INFO  
		                }); 
						   Ext.Ajax.request({
						       url:__ctxPath+'/flow/genFormDef.do',
						       params:{formDefId:record.data.formDefId},
						       method:'post',
						       timeout:120000,
						       success:function(response, opts){
						    		Ext.MessageBox.hide();
						    		
						    		var responseText=Ext.decode(response.responseText);
						    		if(responseText.success){
						    			Ext.ux.Toast.msg('操作信息','成功创建实体！');
							    		gp.getStore().reload();
						    		}else{
						    			Ext.ux.Toast.msg('操作信息','创建实体出错，请联系管理员！');
						    		}
						    		
						    		
						    		
						    		
						       },
						       failure:function(){
						    		Ext.MessageBox.hide();
						    		Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
						       }
						   });
			        }
			    });
			},
			detailEnt:function(record){
			    new FormDefDetailWin({
			       formDefId:record.data.formDefId
			    }).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.formDefId);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					case 'btn-check' :
						this.genEnt.call(this, record);
						break;
					case 'btn-detail' :
						this.detailEnt.call(this, record);
						break;
					default :
						break;
				}
			}
		});
