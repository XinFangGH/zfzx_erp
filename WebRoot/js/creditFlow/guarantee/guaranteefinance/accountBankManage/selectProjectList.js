/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
selectProjectList = function(funname){
	var searchPanel = new HT.SearchPanel({
			border : false,
			layout : 'column',
			region : 'north',
			height : 40,
			anchor : '70%',
			keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
			layoutConfig : {
				align : 'middle'
			},
			items : [
					{
				columnWidth : 0.3,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',

				items : [{
							fieldLabel:'项目编号',
									name : 'Q_projectNumber_S_LK',
									xtype : 'textfield'
						}
						]

			}, {
				columnWidth : 0.3,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
							fieldLabel:'项目名称',
									name : 'Q_projectName_S_LK',
									xtype : 'textfield'
						}]

			}

			, {
				columnWidth : .1,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				items : [{
							text : '查询',
							iconCls : 'btn-search',
							handler : function(){
								$search({
									searchPanel : searchPanel,
									gridPanel : gridPanel
								});
							}
						}]
			}, {
				columnWidth : .1,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				items : [ {
							text : '重置',
							iconCls : 'reset',
							handler : function(){
								searchPanel.getForm().reset();
							}
						}]
			}

			]
			});// end of searchPanel
	var gridPanel = new HT.GridPanel({
					isShowTbar : false,
					region : 'center',
					// 使用RowActions
					rowActions : false,
					id : 'selectProjectListGrid',
					url:__ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/listGLGuaranteeloanProject.do',
					fields : [{
								name : 'projectId',
								type : 'Long'
							}, 'projectName','projectNumber','projectMoney','businessType','createDate','earnestmoney'],
					columns : [{
								header : 'projectId',
								dataIndex : 'projectId',
								hidden : true
							}, {
								header : '项目编号',
								dataIndex : 'projectNumber',
								width :100
								
							}, {
								header : '项目名称',
								dataIndex : 'projectName',
								width :250
								
							}, {
								header : '项目金额(万元)',
								dataIndex : 'projectMoney',
								renderer :function(v){
									if(v!=null){
									   return v+"万元"
									}else{
									
									return v;
									
									}
								
								}
								
								
							}, {
								header : '保证金金额(万元)',
								dataIndex : 'earnestmoney',
								renderer :function(v){
									if(v!=null){
									   return v+"万元"
									}else{
									
									return v;
									
									}
								
								}
								
								
							}, {
								header : '创建时间',
								dataIndex : 'createDate',
								width :100
								
							}
							
							],
									listeners:{
									     'rowdblclick' : function(grid,rowIndex,e) {
		
		                                                grid.getSelectionModel().each(function(rec) {
				                                       	funname(rec.data.projectId,rec.data.projectName,rec.data.businessType,rec.data.earnestmoney);
			                      	                    });
		                                         selectAccountlWindow.destroy();
			}
		}
						// end of columns
				});
	var selectAccountlWindow = new Ext.Window({
		width: (screen.width-180)*0.7,
		title : '项目列表',
		height : 415 ,//高度自by chencc
		collapsible : true,
		region : 'center',
		layout : 'border',
		modal : true,
		items : [searchPanel,gridPanel]
	});
selectAccountlWindow.show();
}