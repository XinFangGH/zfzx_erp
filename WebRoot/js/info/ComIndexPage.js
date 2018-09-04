/**
 * @author:
 * @class SectionView
 * @extends Ext.Panel
 * @description 公司主面
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
				}];
	}

});

ComIndexPage = Ext.extend(Ext.Panel, {
	//portalPanel : null,
	toolbar : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ComIndexPage.superclass.constructor.call(this, {
					title : '公司主页',
					closable : false,
					id : 'ComIndexPage',
					iconCls : 'menu-company',
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
								iconCls : 'btn-refresh',
								text : '刷新视图',
								xtype : 'button',
								scope : this,
								handler : this.refreshRs.createCallback(this)
							}]

				});

		var tools = [{
					id : 'gear',
					handler : function() {
						Ext.Msg.alert('Message',
								'The Settings tool was clicked.');
					}
				}];
				
		this.refreshRs(this);
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
							id : 'IndexPortal',
							margins : '35 5 5 0',
							items : _items
						});
				self.doLayout();
			},
			failure : function() {

			}
		})
	}

});
