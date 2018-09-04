/**
 * 分类管理
 * @class ProModalManager
 * @extends Ext.Panel
 */
IndicatorManagement =Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		IndicatorManagement .superclass.constructor.call(this,{
		    id:'IndicatorManagement',
			layout:'border',
			title:'指标体系管理',
			iconCls:"btn-team34",
			items:[
				this.leftPanel,
				this.centerPanel
			]
		});
	},
	initUIComponents:function(){
					  var start = 0 ;
			var pageSize = 30;
			var bodyWidth = Ext.getBody().getWidth();
			var bodyHeight = Ext.getBody().getHeight();
			var innerPanelWidth = bodyWidth-6 ;/** 暂时未用到，调整窗体大小使用*/
			var defaultLabelWidth = 120 ;//默认标签的宽度
			var defaultTextfieldWidth = 150 ;//默认文本输入域宽度
			var fieldWidth = 150;
			var root = 'topics';
			var totalProperty = 'totalProperty';
		
		
		

		/*	Ext.BLANK_IMAGE_URL = '/creditBusiness1.0/ext/resources/images/default/s.gif';
			Ext.QuickTips.init();*/
			Ext.form.Field.prototype.msgTarget = 'side';
			var windowGroup = new Ext.WindowGroup();
			
			var widthFun = function(bodyWidth){
				return ((bodyWidth-6)<500) ? 500 : (bodyWidth-6);
			}
			var heightFun = function(bodyHeight){
				return ((bodyHeight<400) ? 400 : (bodyHeight));	
			}
		/*-------选项列表start---------------------------------------------------------------------------------*/
			var indicatorId;
			var storeId;
	
		/*-------选项列表end---------------------------------------------------------------------------------*/
			var indicatorTypeId = 0;
			var indicatorType = '';
			var leaf=false;
			 this.reader = new Ext.data.JsonReader({
			    totalProperty : 'totalCounts',
				root : 'result'
			}, [ {
					name : 'id'
				},{
					name : 'indicatorId'
				}, {
					name : 'indicatorName'
				}, {
					name : 'optionName'
				},{
					name : 'score'
				},{
				    name:'maxScore'
				},{
				    name:'minScore'
				}]
	       );

       this.jStore_indicator = new Ext.data.GroupingStore({
			proxy : new Ext.data.HttpProxy({url :  __ctxPath+'/creditFlow/creditmanagement/list1Indicator.do?type=dx'}),
			reader : this.reader,
			groupField : 'indicatorId'
		});


			
			var jStore_indicator=this.jStore_indicator;
			this.button_add = new Ext.Button({
				text : '增加',
				tooltip : '增加一条新的指标',
				iconCls : 'addIcon',
				scope : this,
				hidden : isGranted('_ic_add')?false:true,
				handler : function() {

					if(indicatorTypeId == 0 || leaf==false) {
						Ext.ux.Toast.msg('状态', '请选择一条要素名称!');
						return;
					}
					var fPanel_add = new Ext.FormPanel({
						url: __ctxPath+'/creditFlow/creditmanagement/addIndicator.do',
						labelAlign : 'right',
						buttonAlign : 'center',
						width:600,
						height : 400,
						frame : true,
						labelWidth : 80,
						monitorValid : true,
						autoScroll:true,
						items : [ {
							layout : 'column',
							border : false,
							labelSeparator : ':',
							defaults : {
								layout : 'form',
								border : false,
								columnWidth : 1
							},
							items : [ {
								items : [{
										id : 'indicator.indicatorTypeId',
										xtype :'hidden',
										name : 'indicator.indicatorTypeId',
										value : indicatorTypeId
								},{
										xtype :'hidden',
										name : 'indicator.indicatorType',
										value : indicatorType
								},{
										xtype : 'textfield',
										fieldLabel : '要素名称',
										name : 'indicatorType',
										width : defaultTextfieldWidth,
										value : indicatorType,
										readOnly : true,
				                        cls : 'readOnlyClass'
										
								},{
									xtype : 'textfield',
									fieldLabel : '指标名称',
									name : 'indicator.indicatorName',
									width : 300,
									allowBlank : false,
									blankText : '必填信息'
								},{
									xtype : 'textarea',
									fieldLabel : '指标说明',
									name : 'indicator.indicatorDesc',
									width : 300
								},{
								   xtype : 'fieldset',
								   title : '指标选项',
								   items : [new OptionList({})]
								    
								} ]
							}]// items
						} ],
						buttons : [ {
							text : '提交',
							iconCls : 'submitIcon',
							formBind : true,
							handler : function() {

								var vDates=getOptionGridDate(fPanel_add.getCmpByName("gPanel_option"))
								fPanel_add.getForm().submit({
									url: __ctxPath+'/creditFlow/creditmanagement/addIndicator.do',
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									params:{optionStr:vDates},
									success : function() {
										Ext.ux.Toast.msg('状态', '添加成功!');
										jStore_indicator.removeAll();
										jStore_indicator.reload();
										window_add.destroy();
										
									},
									failure : function(form, action) {
										Ext.ux.Toast.msg('状态','添加失败!');
									}
								});
							}
						}/*,{
							text : '取消',
							handler : function(){
								window_add.destroy();
							}
						}*/ ]
				});

				var window_add = new Ext.Window({
					//id : 'w_add',
					title: '新增指标',
					layout : 'fit',
					autoHeight:true,
					//autoWidth:true,
					width:600,
					x : (Ext.getBody().getWidth()-600)/2,
					y : 20,
					closable : true,
					resizable : true,
					plain : true,
					border : false,
					modal : true,
					buttonAlign: 'right',
					minHeight: 250,	//resizable为true有效	        
					minWidth: 500,//resizable为true有效
					bodyStyle : 'padding: 5',
					items : [fPanel_add],
			        manager:this.windowGroup
				});
				window_add.show();
				}
			});
			
			
			this.button_update = new Ext.Button({
				text : '修改',
				tooltip : '修改选中的指标信息',
				iconCls : 'updateIcon',
				scope : this,
				hidden : isGranted('_ic_update')?false:true,
				handler : function() {
					var selected = this.centerPanel.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('indicatorId');
						var gPanel_option=new OptionList({})
						gPanel_option.getCmpByName("gPanel_option").getStore().baseParams['indicatorId']=id
						gPanel_option.getCmpByName("gPanel_option").getStore().load()
						Ext.Ajax.request({
							url :  __ctxPath+'/creditFlow/creditmanagement/getInfoIndicator.do',
							method : 'POST',
							success : function(response,request) {
								obj = Ext.util.JSON.decode(response.responseText);
		/**修改企业信息弹出开始*/
								var fPanel_update = new Ext.FormPanel({
									url: __ctxPath+'/creditFlow/creditmanagement/updateIndicator.do',
									labelAlign : 'right',
									buttonAlign : 'center',
									//bodyStyle : 'padding:5px;',
									height : 100,
									frame : true,
									autoScroll:true,
									labelWidth : 80,
									monitorValid : true,
									items : [ {
										layout : 'column',
										border : false,
										labelSeparator : ':',
										defaults : {
											layout : 'form',
											border : false,
											columnWidth : 1
										},
										items : [ {
												items : [{
														id : 'indicator.id',
														xtype : 'hidden',
														name : 'indicator.id',
														value : obj.data.id
												},{
														id : 'indicator.indicatorTypeId',
														xtype :'hidden',
														name : 'indicator.indicatorTypeId',
														value : obj.data.indicatorTypeId
												},{
														xtype :'hidden',
														name : 'indicator.indicatorType',
														value : indicatorType
												},{
														xtype : 'textfield',
														fieldLabel : '要素名称',
														name : 'indicatorType',
														width : defaultTextfieldWidth,
														value : obj.data.indicatorType,
														readOnly : true,
				                                         cls : 'readOnlyClass'
														
												},{
													xtype : 'textfield',
													fieldLabel : '指标名称',
													name : 'indicator.indicatorName',
													width : 300,
													allowBlank : false,
													blankText : '必填信息',
														value : obj.data.indicatorName
												},{
													xtype : 'textarea',
													fieldLabel : '指标说明',
													name : 'indicator.indicatorDesc',
													width : 300,
													value : obj.data.indicatorDesc
												},{
												   xtype : 'fieldset',
												   title : '指标选项',
												   items : [gPanel_option]
												    
												} ]
											}]// items
									} ],
									buttons : [ {
										text : '修改',
										iconCls : 'submitIcon',
										formBind : true,
										handler : function() {
											fPanel_update.getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												params:{optionStr:getOptionGridDate(fPanel_update.getCmpByName("gPanel_option"))},
												success : function() {
													Ext.ux.Toast.msg('状态', '修改成功!');
													jStore_indicator.removeAll();
													jStore_indicator.reload();
													window_update.destroy();
													
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态','修改失败!');
		//											top.getForm().reset();
												}
											});
										}
									}/*,{
										text : '取消',
										handler : function(){
											window_update.destroy();
										}
									}*/ ]
								});
								
									var window_update = new Ext.Window({
										//id : 'window_update',
										title: '修改指标',
										layout : 'fit',
										width : 600,
										height : 400,
										x : (Ext.getBody().getWidth()-600)/2,
										y : 20,
										closable : true,
										resizable : true,
										plain : true,
										border : false,
										modal : true,
										buttonAlign: 'right',
										minHeight: 250,	//resizable为true有效	        
										minWidth: 500,//resizable为true有效
										bodyStyle : 'padding: 5',
										items : [fPanel_update],
		//								 buttons: [{}],
								         listeners: {
								             'show': function(){
												//alert();
								             }
								         },
								         manager:this.windowGroup
									});
								window_update.show();			
		/**修改企业信息弹出结束*/
							},
							failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
							},
							params: { id: id }
						});
					}
				}
			});
			this.button_seeIndicator = new Ext.Button({
				text : '查看',
				tooltip : '查看选中的指标信息',
				iconCls : 'updateIcon',
				scope : this,
				hidden : isGranted('_ic_see')?false:true,
				handler : function() {
					var selected = this.centerPanel.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('indicatorId');
						var gPanel_option=new OptionList({isHidden:true})
						gPanel_option.getCmpByName("gPanel_option").getStore().baseParams['indicatorId']=id
						gPanel_option.getCmpByName("gPanel_option").getStore().load()
						//gPanel_option.getTopToolbar.hide()
						Ext.Ajax.request({
							url :  __ctxPath+'/creditFlow/creditmanagement/getInfoIndicator.do',
							method : 'POST',
							success : function(response,request) {
								obj = Ext.util.JSON.decode(response.responseText);
		/**修改企业信息弹出开始*/
								var fPanel_update = new Ext.FormPanel({
									
									labelAlign : 'right',
									buttonAlign : 'center',
									//bodyStyle : 'padding:5px;',
									height : 100,
									frame : true,
									autoScroll:true,
									labelWidth : 80,
									monitorValid : true,
									items : [ {
										layout : 'column',
										border : false,
										labelSeparator : ':',
										defaults : {
											layout : 'form',
											border : false,
											columnWidth : 1
										},
										items : [ {
												items : [{
														id : 'indicator.id',
														xtype : 'hidden',
														name : 'indicator.id',
														value : obj.data.id
												},{
														id : 'indicator.indicatorTypeId',
														xtype :'hidden',
														name : 'indicator.indicatorTypeId',
														value : obj.data.indicatorTypeId
												},{
														xtype :'hidden',
														name : 'indicator.indicatorType',
														value : indicatorType
												},{
														xtype : 'textfield',
														fieldLabel : '要素名称',
														name : 'indicatorType',
														width : defaultTextfieldWidth,
														value : obj.data.indicatorType,
														readOnly : true,
				                                         cls : 'readOnlyClass'
														
												},{
													xtype : 'textfield',
													fieldLabel : '指标名称',
													name : 'indicator.indicatorName',
													width : 300,
													allowBlank : false,
													blankText : '必填信息',
													readOnly : true,
													value : obj.data.indicatorName
												},{
													xtype : 'textarea',
													fieldLabel : '指标说明',
													name : 'indicator.indicatorDesc',
													width : 300,
													readOnly : true,
													value : obj.data.indicatorDesc
												},{
												   xtype : 'fieldset',
												   title : '指标选项',
												   items : [gPanel_option]
												    
												} ]
											}]// items
									} ]
								});
								
									var window_update = new Ext.Window({
										//id : 'window_update',
										title: '查看指标',
										layout : 'fit',
										width : 600,
										height : 400,
										x : (Ext.getBody().getWidth()-600)/2,
										y : 20,
										closable : true,
										resizable : true,
										plain : true,
										border : false,
										modal : true,
										buttonAlign: 'right',
										minHeight: 250,	//resizable为true有效	        
										minWidth: 500,//resizable为true有效
										bodyStyle : 'padding: 5',
										items : [fPanel_update],
		//								 buttons: [{}],
								         listeners: {
								             'show': function(){
												//alert();
								             }
								         },
								         manager:this.windowGroup
									});
								window_update.show();			
		/**修改企业信息弹出结束*/
							},
							failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
							},
							params: { id: id }
						});
					}
				}
			});
			this.button_delete = new Ext.Button({
				text : '删除',
				tooltip : '删除选中的指标信息',
				iconCls : 'deleteIcon',
				scope : this,
				disabled : false,
				hidden : isGranted('_ic_delete')?false:true,
				handler : function() {
					var selected = this.centerPanel.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('indicatorId');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url :  __ctxPath+'/creditFlow/creditmanagement/deleteRsIndicator.do',
									method : 'POST',
									success : function(response) {
										if (response.responseText == '{success:true}') {
											Ext.ux.Toast.msg('状态', '删除成功!');
											jStore_indicator.removeAll();
											jStore_indicator.reload();
										} else if(response.responseText == '{success:false}') {
											Ext.ux.Toast.msg('状态','删除失败!');
										} else {
											Ext.ux.Toast.msg('状态','删除失败!');
										}
									},
									failure : function(result, action) {
										Ext.ux.Toast.msg('状态','服务器未响应，删除失败!');
									},
									params: { id: id }
								});
							}
						});
					}
				}
			});
			
			//编辑指标选项
			this.button_option = new Ext.Button({
				text : '编辑选项',
				tooltip : '编辑选中的指标选项',
				iconCls : 'updateIcon',
				scope : this,
				handler : function() {
					var selected =this.centerPanel.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						indicatorId = id;
						jStore_option.removeAll();
						jStore_option.reload({
							params : {
								indicatorId : id
							}
						});
						win_option.show();
					}
				}
			
			});
			
			var button_see = new Ext.Button({
				text : '查看',
				tooltip : '查看选中的指标企业信息',
				iconCls : 'seeIcon',
				scope : this,
				handler : function() {
					var selected = gPanel_enterprise.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						seeEnterprise(id);
					}
				}
			});
			var indicatorName=function(data, cellmeta, record){

                  return record.get("indicatorName");

            }
		/*	this.cModel_indicator = new Ext.grid.ColumnModel(
					[{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : "",
						width : 300,
						sortable : true,
						hidden:true,
						dataIndex : 'indicatorId',
						renderer:indicatorName
					}, {
						header : "指标选项",
						width : 280,
						sortable : true,
						dataIndex : 'optionName'
					}, {
						header : "分值",
						width : 50,
						sortable : true,
						dataIndex : 'score'
					} ]);*/
		
			this.pagingBar = new Ext.PagingToolbar( {
				pageSize : 30,
				store : this.jStore_indicator,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});

			this.jStore_indicator.load({
			   params : {
					start : start,
					limit : 25,
					indicatorTypeId:0,
					leaf:false
			   }
		   });
			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});

			this.centerPanel = new HT.GridPanel( {
				title : '要素名称：',
				region : 'center',
				//id : 'gPanel_enterprise',
				store : this.jStore_indicator,
				view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text}'
			
			    }),
			    hiddenCmTwo:true,
				height : Ext.getBody().getViewSize().height,
				stripeRows : true,
				loadMask : myMask,
				tbar : [this.button_add, this.button_update, this.button_delete, this.button_seeIndicator],
				columns:[
				{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : "",
						width : 300,
						sortable : true,
						hidden:true,
						dataIndex : 'indicatorId',
						renderer:indicatorName
					}, {
						header : "指标选项",
						width : 280,
						sortable : true,
						dataIndex : 'optionName'
					}, {
						header : "分值",
						width : 50,
						sortable : true,
						dataIndex : 'score'
					} ]
			});


			
			window.onresize = function(){
				bodyWidth = Ext.getBody().getWidth();
				bodyHeight = Ext.getBody().getHeight() ;
				w = widthFun(bodyWidth);
				//gPanel_enterprise.setWidth(w);
				//panel_enterprise.doLayout();
			//vPort_enterprise.doLayout();
			}
			var widthFun = function(bodyWidth){
				return ((bodyWidth-6)<800) ? 800 : (bodyWidth-6);
			}
			
		this.dic_TreeLoader = new Ext.tree.TreeLoader({
				dataUrl :  __ctxPath+'/creditFlow/creditmanagement/list1Indicator.do?type=dx'
			})
		this.dic_Root = new Ext.tree.AsyncTreeNode({
			id : '0',
			text : "指标库"
		});
        var centerPanel=this.centerPanel;
		this.dic_TreePanel = new Ext.Panel({
			id : 'dic_TreePanel',
			frame : false,
			autoWidth:true,
			collapsible : false,
			titleCollapse : false,
			autoScroll:true,
			height : Ext.getBody().getViewSize().height-115,
			items : [{
					id :'tree_panel',
					xtype : 'treepanel',
					border : false,
					iconCls : 'icon-nav',
					rootVisible : true,
					loader : this.dic_TreeLoader,
					root : this.dic_Root,
					listeners : {
						'contextmenu' : function(node,e) {
							nodeSelected = node;
							nodeSelected.select();
							setCatalogMenuFun(node,e,centerPanel);
						},
						'click' : function(node) {

							
								indicatorTypeId = node.id;
								indicatorType = node.text;
								leaf=node.leaf;
								centerPanel.getStore().removeAll();
								centerPanel.getStore().baseParams.indicatorTypeId = indicatorTypeId;
								centerPanel.getStore().baseParams.leaf = node.leaf;
								centerPanel.getStore().load({
									params : {
										start : start,
										limit : pageSize
									}
								});
								centerPanel.getStore().on('load',function(){
								if(node.leaf==true){
								    centerPanel.setTitle("要素名称：" + node.text+"       总计最高分："+centerPanel.getStore().getAt(0).data.maxScore+"        总计最低分："+centerPanel.getStore().getAt(0).data.minScore);
								}else if(node.id==0){
								    centerPanel.setTitle("所有指标");
								}else if(node.id!=0 && node.leaf==false){
								    centerPanel.setTitle("目录类型为"+node.text+"的指标")
								}
								})
							
							}
						//}
					}
			}]
						
		});
		this.leftPanel = new Ext.Panel({
			title:'指标体系管理',
			region : 'west',
			layout : 'anchor',
			collapsible : true,
			split : true,
			width : 200,
			
			autoHeight:true,
			items : [this.dic_TreePanel]
		}

		);
		

		
		
	}
	
});