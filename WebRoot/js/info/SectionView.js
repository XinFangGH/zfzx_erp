/**
 * @author:
 * @class SectionView
 * @extends Ext.Panel
 * @description 栏目管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */

// 一般栏目
Portlet = Ext.extend(Ext.ux.Portlet, {
	tools : null,
	url : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initTool(_cfg);
		
		if(_cfg.sectionType == 2){
			this.url = __ctxPath+ '/pages/indexpages/deskNewsListPage.jsp?sectionId='+ _cfg.sectionId
		}else if(_cfg.sectionType == 3){
			this.url = __ctxPath+ '/pages/indexpages/noticeScrollPage.jsp?sectionId='+ _cfg.sectionId
		}else {
			this.url = __ctxPath+ '/pages/indexpages/newsListPage.jsp?sectionId='+ _cfg.sectionId
		}
		
		Portlet.superclass.constructor.call(this, {
					// id : 'DepPlanPanelView',
					title : _cfg.title,
					iconCls : _cfg.iconCls,
					tools : this.tools,
					autoLoad : {
						url : this.url,
						scripts : true
					}
				});
	},
	initTool : function(_cfg) {
		this.tools = [{
					id : 'refresh',
					scope : this,
					handler : function() {
						this.getUpdater().update(this.url);
					}
				}, {
					id : 'close',
					scope : this,
					handler : function(e, target, panel) {
						Ext.Msg.confirm('提示信息', '确认删除此模块吗？', function(btn) {
							if (btn == 'yes') {
								panel.ownerCt.remove(panel, true);
								Ext.Ajax.request({
									url : __ctxPath + '/info/disableSection.do',
									method : 'POST',
									params : {
										sectionId : _cfg.sectionId
									},
									success : function() {

									},
									failure : function() {

									}
								});
							}
						});
					}
				}];
	}

});

SectionView = Ext.extend(Ext.Panel, {
	//portalPanel : null,
	toolbar : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SectionView.superclass.constructor.call(this, {
					title : '栏目管理',
					closable : false,
					id : 'SectionView',
					iconCls : 'menu-section-view',
					layout : 'fit',
					defaults : {
						padding : '0 5 0 0'
					},
					tbar : this.toolbar,
					items : []
				});
	},
	initUIComponents : function() {
		this.toolbar = new Ext.Toolbar({
					height : 30,
					items : [{
								iconCls : 'btn-add',
								text : '添加栏目',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, '-',{
								iconCls : 'btn-refresh',
								text : '刷新视图',
								xtype : 'button',
								scope : this,
								handler : this.refreshRs.createCallback(this)
							}, '-', {
								xtype : 'button',
								text : '保存视图',
								scope : this,
								iconCls : 'btn-save',
								handler : this.positionRs.createCallback(this)
								
							}, '-', {
								xtype : 'button',
								text : '添加模块',
								scope : this,
								iconCls : 'btn-add',
								handler : function() {
									var firstColumn = this.getCmpByName('FirstColumn');
									
									new SectionSelector({
										isSingle : false,
										status : 0,//取出被禁用的栏目
										callback:function(sectionId,sectionName,sectionType){
											this.close();
											var secIds = sectionId.split(',');
											var secNames = sectionName.split(',');
											var secTypes = sectionType.split(',');
											for(var i=0;i<secIds.length;i++){
												if(secIds[i]!=null && secIds[i]!='' && secIds[i]!='undefined'){
													firstColumn.add(new Portlet({
														title : secNames[i],
														sectionId : secIds[i],
														sectionType : secTypes[i]
													}));
												}
											}
											
											firstColumn.ownerCt.doLayout();
											//选中的栏目标为激活
											Ext.Ajax.request({
												url: __ctxPath + '/info/enableSection.do',
												method : 'POST',
												params : {
													secIds : sectionId
												},
												success : function(){
													
												},
												failure : function(){
													
												}
											});
										}}
									).show();
								}
							}, '-',{
								xtype : 'button',
								text : '视图设置',
								iconCls : 'btn-system-setting',
								scope : this,
								handler : this.viewSettingRs.createCallback(this)
							}]

				});

		var tools = [{
					id : 'gear',
					handler : function() {
						Ext.Msg.alert('Message',
								'The Settings tool was clicked.');
					}
				}, {
					id : 'close',
					handler : function(e, target, panel) {
						panel.ownerCt.remove(panel, true);
					}
				}];
				
		this.refreshRs(this);
	},
	// 创建记录
	createRs : function() {
		new SectionForm().show();
	},
	/**
	 * 
	 * @param {} self
	 * @param {} columnType 视图显示的列出
	 */
	refreshRs : function(self,columnType) {
		//self.items[0].destory();
		self.removeAll(true);
		self.doLayout();
		
		var column0 = [];
		var column1 = [];
		var column2 = [];
		Ext.Ajax.request({
			url : __ctxPath + '/info/listSection.do',
			method : 'POST',
			params : {
				'Q_status_SN_EQ' : 1
				// 状态为激活的栏目
			},
			success : function(response) {
				var res = Ext.util.JSON.decode(response.responseText);
				var data = res.result;
				if(columnType == null || columnType == undefined || columnType ==''){
					columnType = res.columnType;
				}
				for (var i = 0; i < res.totalCounts; i++) {
					var section = data[i];
					if(columnType == 1){
						column0.push(new Portlet({
										title : section.sectionName,
										sectionId : section.sectionId,
										sectionType : section.sectionType
							}));
					}else if(columnType == 2){//两列显示
						if (section.colNumber == 1) {
							column0.push(new Portlet({
										title : section.sectionName,
										sectionId : section.sectionId,
										sectionType : section.sectionType
							}));
						} else {
							column1.push(new Portlet({
										title : section.sectionName,
										sectionId : section.sectionId,
										sectionType : section.sectionType
									}));
						}
					}else {
						if (section.colNumber == 1) {
							column0.push(new Portlet({
										title : section.sectionName,
										sectionId : section.sectionId,
										sectionType : section.sectionType
							}));
						} else if (section.colNumber == 2) {
							column1.push(new Portlet({
										title : section.sectionName,
										sectionId : section.sectionId,
										sectionType : section.sectionType
									}));
						} else{
							column2.push(new Portlet({
										title : section.sectionName,
										sectionId : section.sectionId,
										sectionType : section.sectionType
									}));
						}
					}
					
				}
				
				var _items = null;
				if(columnType == 1){
					_items = [{
										columnWidth : .98,
										style : 'padding:10px 0 10px 10px',
										name : 'FirstColumn',
										items : column0
									}]
				}else if (columnType == 2){
					_items = [{
										columnWidth : .64,
										style : 'padding:10px 0 10px 10px',
										name : 'FirstColumn',
										items : column0
									}, {
										columnWidth : .35,
										style : 'padding:10px 10px 10px 10px',
										items : column1
									}]
				}else {
					_items = [{
										columnWidth : .33,
										style : 'padding:10px 0 10px 10px',
										name : 'FirstColumn',
										items : column0
									}, {
										columnWidth : .33,
										style : 'padding:10px 10px 10px 10px',
										items : column1
									},{
										columnWidth : .33,
										style : 'padding:10px 0px 10px 0px',
										items : column2
									}]
				}
				
				self.add({
							xtype : 'portal',
							region : 'center',
							border : false,
							id : 'SectionPortal',
							margins : '35 5 5 0',
							items : _items
						});
				self.doLayout();
			},
			failure : function() {

			}
		})
	},
	positionRs : function() {
			var Portal = Ext.getCmp('SectionPortal');
			var items = Portal.items;
			var sections = new Array();
			 for (var i = 0; i < items.length; i++) {
				 var v = items.itemAt(i);
				 for (var j = 0; j < v.items.getCount(); j++) {
					 var m = v.items.itemAt(j);
					 var sectionItem = new SectionItem(m.sectionId, i+1,j+1);
					 sections.push(sectionItem);
				 }
			 }
			 Ext.Ajax.request({
				 method : 'post',
				 url : __ctxPath + '/info/positionSection.do',
				 params : {
				 	sections :Ext.encode(sections)
				 },
				 success : function(request) {
				 	Ext.ux.Toast.msg('操作信息', '保存成功');
				 },
				 failure : function(request) {
					 Ext.MessageBox.show({
						 title : '操作信息',
						 msg : '信息保存出错，请联系管理员！',
						 buttons : Ext.MessageBox.OK,
						 icon : 'ext-mb-error'
				 	});
				 }
			 });
			//
		},
		/**
		 * 视图设置
		 */
	viewSettingRs : function(self){
		var form = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					defaults : {
						anchor : '98%,98%'
					},
					items : [{
										xtype : 'radiogroup',
										id : 'columnLayout',
										autoHeight : true,
										columns : 1,
										items : [{
													boxLabel : '一列布局',
													name : 'columnType',
													inputValue : '1'
												}, {
													boxLabel : '两列布局',
													name : 'columnType',
													inputValue : '2',
													checked : true
												}, {
													boxLabel : '三列布局',
													name : 'columnType',
													inputValue : '3'
												}]
									}]
				})
		var win = new Ext.Window({
					layout : 'fit',
					iconCls : 'btn-system-setting',
					items : form,
					modal : true,
					minHeight : 149,
					minWidth : 499,
					height : 150,
					width : 500,
					maximizable : true,
					title : '视图设置',
					buttonAlign : 'center',
					buttons : [{
						text : '确定',
						iconCls : 'btn-save',
						handler : function() {
							var ids = '';
							var columnLayout = Ext.getCmp('columnLayout');
							var columnType = columnLayout.getValue().getGroupValue();
							Ext.Ajax.request({
								url : __ctxPath + '/info/columnSection.do',
								method : 'POST',
								params : {columnType:columnType},
								success : function(){
									Ext.ux.Toast.msg('操作信息','视图设置成功.');
								},
								failure : function(){
									
								}
							})
							self.refreshRs(self,columnType);
							win.close();
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							win.close();
						}
					}]
				});
		win.show();
	}

});
