/**
 * @description 会议信息选择器
 * @author YHZ
 * @company www.hzhiwei-jee.org
 * @datetime : 2010-11-2PM
 */
var ConferenceSelector = {
		/**
		 * @param callback
		 *            回调函数
		 * @param isSingle
		 *            是否单选
		 */
		getView : function(callback, isSingle) {
			// ---------------------------------start grid
			// panel--------------------------------
			var sm = null;
			if (isSingle) {
				var sm = new Ext.grid.CheckboxSelectionModel({
							singleSelect : true
						});
			} else {
				sm = new Ext.grid.CheckboxSelectionModel();
			}
			var cm = new Ext.grid.ColumnModel({
						columns : [sm, new Ext.grid.RowNumberer(), {
									header : 'confId',
									dataIndex : 'confId',
									hidden : true
								}, {
									header : "会议标题",
									dataIndex : 'confTopic',
									width : 60
								}]
					});

			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : __ctxPath + '/admin/getConfTopicConference.do'
								}),
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCounts',
									id : 'confId',
									fields : [{
												name : 'confId',
												type : 'int'
											}, 'confTopic']
								}),
						remoteSort : true
					});

			var gridPanel = new Ext.grid.GridPanel({
						id : 'CarSelectorGrid',
						width : 400,
						height : 300,
						region : 'center',
						title : '会议标题列表',
						store : store,
						trackMouseOver : true,
						disableSelection : true,
						loadMask : true,
						cm : cm,
						sm : sm,
						viewConfig : {
							forceFit : true,
							enableRowBody : false,
							showPreview : false
						},
						// paging bar on the bottom
						bbar : new Ext.PagingToolbar({
									pageSize : 10,
									store : store,
									displayInfo : true,
									displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
									emptyMsg : "当前没有记录"
								})
					});

			store.load({
				params : {
					start : 0,
					limit : 10
					}
				}
			);
			// --------------------------------end grid
			// panel-------------------------------------

			var formPanel = new Ext.FormPanel({
				width : 400,
				region : 'north',
				id : 'CarSearchForm',
				height : 40,
				frame : false,
				border : false,
				layout : 'hbox',
				layoutConfig : {
					padding : '5',
					align : 'middle'
				},
				defaults : {
					xtype : 'label',
					margins : {
						top : 0,
						right : 4,
						bottom : 4,
						left : 4
					}
				},
				items : [{
							text : '请输入查询条件:'
						}, {
							text : '会议标题：'
						}, {
							xtype : 'textfield',
							name : 'Q_confTopic_S_LK'
						}, {
							xtype : 'button',
							text : '查询',
							iconCls : 'search',
							handler : function() {
								var searchPanel = Ext.getCmp('CarSearchForm');
								var grid = Ext.getCmp('CarSelectorGrid');
								if (searchPanel.getForm().isValid()) {
									searchPanel.getForm().submit({
										waitMsg : '正在提交查询',
										url : __ctxPath + '/admin/getConfTopicConference.do',
										method : 'post',
										success : function(formPanel, action) {
											var result = Ext.util.JSON
													.decode(action.response.responseText);
											grid.getStore().loadData(result);
										}
									});
								}

							}
						}]
			});

			var window = new Ext.Window({
				title : '会议标题选择',
				iconCls : 'menu-confSummary',
				width : 630,
				height : 380,
				layout : 'border',
				border : false,
				items : [formPanel, gridPanel],
				modal : true,
				buttonAlign : 'center',
				buttons : [{
							iconCls : 'btn-ok',
							text : '确定',
							handler : function() {
								var grid = Ext.getCmp('CarSelectorGrid');
								var rows = grid.getSelectionModel().getSelections();
								var confId = rows[0].data.confId;
								var confTopic = rows[0].data.confTopic;
								if (callback != null) {
									callback.call(this, confId, confTopic);
								}
								window.close();
							}
						}, {
							text : '取消',
							iconCls : 'btn-cancel',
							handler : function() {
								window.close();
							}
						}]
			});
			return window;
		}

	};