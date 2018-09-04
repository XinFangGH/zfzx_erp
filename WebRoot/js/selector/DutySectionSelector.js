/**
 * 班次选择器
 */
var DutySectionSelector = {
	getView : function(callback) {
		var gridPanel = this.initGridPanel();
		var window = new Ext.Window({
			title : '班次选择器',
			width : 630,
			height : 380,
			iconCls:'btn-clock',
			layout : 'fit',
			border : false,
			items : [gridPanel],
			modal : true,
			buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						handler : function() {
							var grid = Ext.getCmp('DutySectionSelGrid');
							var rows = grid.getSelectionModel().getSelections();
							var sectionIds = '';
							var sectionNames = '';
							for (var i = 0; i < rows.length; i++) {
								if (i > 0) {
									sectionIds += ',';
									sectionNames += ',';
								}
								sectionIds += rows[i].data.sectionId;
								sectionNames += rows[i].data.sectionName;
							}

							if (callback != null) {
								callback.call(this, sectionIds, sectionNames);
							}
							window.close();
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							window.close();
						}
					},{
						text:'休息',
						iconCls:'btn-relax',
						handler: function(){
							if (callback != null) {
								callback.call(this, '-', '休息');
							}
							window.close();
						}
					}]
		});
		return window;

	},

	initGridPanel : function() {
		var sm = new Ext.grid.CheckboxSelectionModel();

		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'sectionId',
								dataIndex : 'sectionId',
								hidden : true
							}, {
								header : '班次名称',
								dataIndex : 'sectionName'
							}, {
								header : '开始签到',
								dataIndex : 'startSignin1'
							}, {
								header : '上班时间',
								dataIndex : 'dutyStartTime1'
							}, {
								header : '签到结束时间',
								dataIndex : 'endSignin1'
							}, {
								header : '早退计时',
								dataIndex : 'earlyOffTime1'
							}, {
								header : '下班时间',
								dataIndex : 'dutyEndTime1'
							}, {
								header : '签退结束',
								dataIndex : 'signOutTime1'
							}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});

		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : __ctxPath + '/personal/listDutySection.do'
					}),
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCounts',
						id : 'id',
						fields : [{
									name : 'sectionId',
									type : 'int'
								}, 'sectionName', 'startSignin1',
								'dutyStartTime1', 'endSignin1',
								'earlyOffTime1', 'dutyEndTime1', 'signOutTime1']
					})
		});
		store.load({
					params : {
						start : 0,
						limit : 25
					}
		});		
		
		var grid = new Ext.grid.GridPanel({
					id : 'DutySectionSelGrid',
					title : '班级列表',
					store : store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					height : 360,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					bbar : new HT.PagingBar({store : store})
		});
		return grid;
	}
};