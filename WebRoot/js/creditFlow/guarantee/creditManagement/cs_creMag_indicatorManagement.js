/**jiang -------资信评估指标管理------*/

var g_evaluateName = '';//评价名称
var g_scoreValue = 0;//评价分值

Ext.onReady(function() {
	
	/**###########################          数据源                  ###########################*/
	var jStore_allElement = new Ext.data.JsonStore( {
		url : 'allElement.action',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
			name : 'id'
		}, {
			name : 'indicatorId'
		}, {
			name : 'name'
		}, {
			name : 'createTime'
		}, {
			name : 'updateTime'
		},{
			name : 'opinion'
		},{
			name : 'optionNum'
		},{
			name : 'scoreValue'
		}]
	});
	
	/**#########################         加载数据       ##################################*/
	jStore_allElement.load({
		params : {
			start : 0,
			limit : 20
		}
	});
	
	/*刷新面板*/
	var refrashPanel = function(){
		jStore_allElement.load({
			params : {
				start : 0,
				limit : 20
			}
		});
	}
	
	/**#################            列表显示面板           ####################*/
	var cModel_project = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer( {
						header : '序号',
						width : 30
					}),
					{
						header : "要素名称",
						width : 100,
						sortable : true,
						dataIndex : 'name'
					}, {
						header : "所属指标",
						width : 100,
						sortable : true,
						dataIndex : 'indicatorId'
					},{
						header : "分数",
						width : 100,
						sortable : true,
						dataIndex : 'scoreValue'
					},{
						header : "评价数量",
						width : 100,
						sortable : true,
						dataIndex : 'optionNum'
					},{
						id : "extendId_updateTime",
						header : "修改时间",
						width : 100,
						sortable : true,
						dataIndex : 'updateTime'
					}]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 15,
		store : jStore_allElement,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
			//jStore_allElement.setDefaultSort('sendTime', 'ASC');
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	
	/** ##############################        增加要素                     ######################*/
	/*增加要素*/
	var addElement = new Ext.Button({
		
		text : '新增要素',
		iconCls : 'addIcon',
		handler : function() {

		var fPanel_add = new Ext.FormPanel({
			url:'addElement.action',
			labelAlign : 'left',
			buttonAlign : 'center',
			bodyStyle : 'padding:5px;',
			height : 100,
			frame : true,
			labelWidth : 100,
			monitorValid : true,
//			tbar : [],
			items : [{
				xtype : 'textfield',
				id : 'indicatorName',
				fieldLabel : '指标类型',
				emptyText : '请选择指标',
				width : 100,
				name : 'indicatorId',
				listeners : {
					'focus' : function(){
						pacDicType('creditManagement','indicatorName','indicatorId');
					}
				}
			},{
				xtype : 'hidden',
				id : 'indicatorId',
				name : 'indicatorId'
			},{
				xtype : 'textfield',
				id : 'elementName',
				fieldLabel : '要素名称',
				emptyText : '请填写要素名称',
				width : 100,
				name : 'elementName'
			},{
				xtype : 'button',
				text : '新增评价',
				handler : function(){
					var panel_addEvaluate = new Ext.Panel({
						layout : 'form',
						labelAlign : 'left',
						buttonAlign : 'center',
						bodyStyle : 'padding:5px;',
						height : 100,
						frame : true,
						labelWidth : 100,
						monitorValid : true,
						items : [{
							xtype : 'textfield',
							fieldLabel : '评价名称',
							emptyText : '请填写评价名称',
							width : 100,
							name : 'evaluateName'
						},{
							xtype : 'textfield',
							fieldLabel : '分值',
							emptyText : '请填写分值',
							width : 100,
							name : 'scoreValue'
						}],//items
						buttons : [ {
							text : '增加',
							formBind : true,
							handler : function() {
								//here codes
								////////////////////////////////////////////////////
							}
						},{
							text : '取消',
							handler : function(){
								window_addEvaluate.destroy();
							}
						} ]//buttons
					});//Panel_addEvaluate
					
					var window_addEvaluate = new Ext.Window({
						id : 'w_addEvaluate',
						title: '新增评价',
						layout : 'fit',
						width : 300,
						height :200,
						closable : true,
						resizable : true,
						plain : true,
						border : false,
						modal : true,
						buttonAlign: 'right',
						//minHeight: 250,	//resizable为true有效	        
						//minWidth: 530,//resizable为true有效
						bodyStyle : 'padding: 5',
						items : [panel_addEvaluate],
				         listeners: {
				             'show': function(){
								//alert();
				             }
				         },
				         manager:new Ext.WindowGroup()
			});//window_addEvaluate
					
					window_addEvaluate.show();
				}//handler
			},{
				xtype : 'textarea',
				hideLabel : true,
				readOnly : true,
		            cls : 'readOnlyClass',
				width : 200,
				height : 100,
				emptyText : '此处显示添加信息',
				name : 'showEvaluate'
			}],
			buttons : [ {
				text : '增加',
				formBind : true,
				handler : function() {
					fPanel_add.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function() {
							Ext.Msg.alert('状态', '发送消息成功!',
									function(btn, text) {
										if (btn == 'ok') {
											//jStore_allElement.removeAll();
											//jStore_allElement.reload();
											window_add.destroy();
										}
									});
						},
						failure : function(form, action) {
							Ext.Msg.alert('状态','发送消息失败!');								
//							top.getForm().reset();
						}
					});
				}
			},{
				text : '取消',
				handler : function(){
					window_add.destroy();
				}
			} ]
		});//fPanel_add
		
		var window_add = new Ext.Window({
				id : 'w_add',
				title: '新增要素',
				layout : 'fit',
				width : 400,
				height :300,
				closable : true,
				resizable : true,
				plain : true,
				border : false,
				modal : true,
				buttonAlign: 'right',
				minHeight: 250,	//resizable为true有效	        
				minWidth: 530,//resizable为true有效
				bodyStyle : 'padding: 5',
//				tbar : [{}],
				items : [fPanel_add],
//				 buttons: [{}],
		         listeners: {
		             'show': function(){
						//alert();
		             }
		         },
		         manager:new Ext.WindowGroup()
			});
		window_add.show();
		}
		
	});
	
	/** ##############################        删除消息                     ######################*/
	
	var button_delete = new Ext.Button({
		text : '删除要素',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_allMessage.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
		
				Ext.Ajax.request({
					url : 'delElement.action?id=' + id,
					method : 'POST',
					success : function() {
						Ext.Msg.alert('状态', '删除成功!');
						refrashPanel();
					},
					failure : function(result, action) {
						Ext.Msg.alert('状态','删除失败!');
					}
				});
			}
		//
		}
	});
	
	/** ##############################         主表格模板                               ######################*/
	
	var gPanel_allElement = new Ext.grid.GridPanel( {
		trackMouseOver : true,
		title : '资信评估指标管理',
		id : 'gPanel_allElement',//id
		store : jStore_allElement,//数据源， 这里是  json模式
		width : (((Ext.getBody().getWidth()-6)<500) ? 500 : (Ext.getBody().getWidth()-6)),
		height : (((Ext.getBody().getHeight()-6)<485) ? 485 : (Ext.getBody().getWidth()-6)),
		colModel : cModel_project,//列模板    即要显示一个grid列表 最少要有 一个 ColumnModel 一个 store（JsonStore） 一个 GridPanel
		autoExpandColumn : 'extendId_updateTime',
		selModel : new Ext.grid.RowSelectionModel(),//行选择模式
		stripeRows : true,
		loadMask : myMask,//当grid加载过程中，会有一个Ext.LoadMask的遮罩效果
		bbar : pagingBar,//配置一组工具栏对像(底端显示 bottomBar)
		tbar : ['->',addElement,button_delete],//显示工具栏（顶端显示 topBar）
		
		/*#####################     双击监听       ###################  */
//		listener是一个事件名 + 处理函数的组合，如：
//		"click" : function(){...}; "mouseOver" : function(){....}
		listeners : {
			'rowdblclick' : function( grid, rowIndex,  e ){//双击事件
		var selected = grid.getSelectionModel().getSelected();//得到选中的行
		var isRead = 0;
		if (null == selected) {
			Ext.MessageBox.alert('状态', '请选择一条记录!');//
		}else{
			var id = selected.get('id');//数据 id（此为数据库 主键）
			
			/*ajax*/
			Ext.Ajax.request({
				url : 'seeElement.action',//数据提交
				method : 'POST',//form method
				success : function(response,request) {//成功
					obj = Ext.util.JSON.decode(response.responseText);//从后台得到数据
					
					/*form panel*/
					var panel_showMsg = new Ext.Panel({
						frame : true,
						html : '<b>日期：</b>'+obj.data.sendTime+'<br /><br />'+'<b>发件人：</b>'+
						obj.data.sendFrom+'<br /><br />'+'<b>项目：</b>'+obj.data.projName+'<br /><br />'+'<b>主题：</b>'+obj.data.title+
						'<br /><br />'+'<b>内容：</b>'+obj.data.content+'<br /><br />'+'<b>附件：</b>'+'<a href="#" onclick="return showDownload_judge('+obj.data.id+','+obj.data.hasAppendix+');">'+'下载'+'</a>'
						,buttons:[
							{
							text:'删除',
							tooltip : '删除此条信息',
							handler:function(){
								Ext.Ajax.request({
									url : 'delMessage.action?id=' + id,
									method : 'POST',
									success : function() {
										refrashPanel();
									},
									failure : function(result, action) {
									}
								})
									window_show.destroy();
						  	}
						  
						},
							{
							text:'继续提醒',
							handler:function(){
//								isRead = 0;
//								Ext.Ajax.request({
//									url : 'readMessage.action?id=' + id+'&isRead='+isRead,
//									method : 'POST',
//									success : function() {
//										refrashPanel();
//									},
//									failure : function(result, action) {
//									}
//								})
								
								window_show.destroy();
							}
						},{
							text:'关闭',
							handler:function(){
								isRead = 1;
								
								Ext.Ajax.request({
									url : 'readMessage.action?id=' + id+'&isRead='+isRead,
									method : 'POST',
									success : function() {
										refrashPanel();
									},
									failure : function(result, action) {
									}
								})
								
								window_show.destroy();
							}
						}]
					
					});
					
					/*############    弹出窗口显示         ##########*/
					var window_show = new Ext.Window({
							title: '消息详细信息',
							layout : 'fit',
							width : 300,
							height : 300,
							closable : false,
							x : (Ext.getBody().getWidth()-600)/2,
							y : 20,
							resizable : true,//可定义
							plain : true,//背景透明
							border : false,//边框
							modal : true,//最前显示
							buttonAlign: 'right',//按钮位置
							minHeight: 250,	//resizable为true有效
							minWidth: 200,//resizable为true有效
							bodyStyle : 'padding: 5',//css
							
							items : [panel_showMsg]//**********************把修改面板 panel 加入进来
							
						});
					window_show.show();//显示 窗口			
				},
				failure : function(response) {
						Ext.Msg.alert('状态','操作失败，请重试');	
				},
				params: { id: id }
			});	
		}
	}
		}
	});
	
	var vPort_enterprise = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [{
			region : "center",
			layout : 'fit',
			items : [gPanel_allElement]
		}]
	});
	
	/**###############################          页面显示                     ############################*/
	window.onresize = function(){
		bodyWidth = Ext.getBody().getWidth();
		bodyHeight = Ext.getBody().getHeight();
		w = widthFun(bodyWidth);
		gPanel_allElement.setWidth(w);
	}
	var widthFun = function(bodyWidth){
		return ((bodyWidth-6)<500) ? 500 : (bodyWidth-6);
	}
});

