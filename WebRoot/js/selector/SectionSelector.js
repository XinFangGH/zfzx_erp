/**
 * 
 * @class SectionSelector
 * @extends Ext.Window
 */
SectionSelector=Ext.extend(Ext.Window,{
    searchFormPanel:null,
    gridPanel:null,
	constructor:function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   SectionSelector.superclass.constructor.call(this,{
	        layout:'border',
	        width : 630,
			height : 380,
			iconCls:'menu-seal',
			title:'栏目选择器',
	        border : false,
	        modal : true,
	        buttonAlign : 'center',
			buttons : [{
						iconCls : 'btn-ok',
						text : '确定',
						scope:this,
						handler : function() {
							var grid = this.gridPanel;
							var rows = grid.getSelectionModel().getSelections();
							var sectionId = '';
							var sectionName = '';
							var sectionType = '';
							for(var i=0;i<rows.length;i++){
								if (i > 0) {
									sectionId += ',';
									sectionName += ',';
									sectionType += ',';
								}
								sectionId += rows[i].data.sectionId;
							    sectionName += rows[i].data.sectionName;
							    sectionType += rows[i].data.sectionType;
							}
							if (this.callback != null) {
								this.callback.call(this,sectionId,sectionName,sectionType);
							}
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						scope:this,
						handler : function() {
							this.close();
						}
					}],
	        items:[this.searchFormPanel,this.gridPanel]
	   });
	},
	initUI:function(){
		this.searchFormPanel=new Ext.FormPanel({
			region : 'north',
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
						text : '栏目名称'
					}, {
						xtype : 'textfield',
						name : 'Q_sectionName_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						scope:this,
						handler : function() {
							$search({
								searchPanel : this.searchFormPanel,
								gridPanel : this.gridPanel
							});

						}
					}]
		});
	    this.gridPanel = new HT.GridPanel({
					region : 'center',
					id : 'SectionGrid',
					singleSelect:this.isSingle?true:false,
					baseParams : {
						'Q_status_SN_EQ' : this.status
					},
					url : __ctxPath + "/info/listSection.do",
					fields : [{
								name : 'sectionId',
								type : 'int'
							}, 'sectionName', 'sectionDesc', 'createtime',
							'sectionType', 'username', 'userId', 'colNumber',
							'rowNumber', 'status'],
					columns : [{
								header : 'sectionId',
								dataIndex : 'sectionId',
								hidden : true
							}, {
								header : '栏目名称',
								dataIndex : 'sectionName'
							}, {
								header : '栏目类型',
								dataIndex : 'sectionType',
								renderer : function(value){
									if(value !=null){
										if(value ==1){
											return '<font color="green">一般栏目</font>';
										}else if(value == 2){
											return '<font color="green">桌面新闻</font>';
										}else if(value == 3){
											return '<font color="green">滚动公告</font>';
										}
									}else{
										return '';
									}
								}
							}, {
								header : '状态',
								dataIndex : 'status',
								renderer : function(value){
									if(value !=null && value ==1){
										return '<font color="green">激活</font>';
									}else{
										return '<font color="red">禁用</font>';
									}
								}
							}]
						// end of columns
				});
	}
	
});