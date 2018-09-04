function check(score,id_score) {
	//Ext.getDom(id_score).innerText = score;
	document.getElementById(id_score).innerHTML = score;
}

var autoHight = Ext.getBody().getHeight();
var autoWidth = Ext.getBody().getWidth();
var dic_TreePanel;
var nodeSelected;
var dic_Root;
var defaultLabelWidth = 120 ;//默认标签的宽度
var defaultTextfieldWidth = 150 ;//默认文本输入域宽度
var start = 0 ;
var pageSize = 15 ;
var bodyWidth = Ext.getBody().getWidth();
var bodyHeight = Ext.getBody().getHeight();
var innerPanelWidth = bodyWidth-6 ;/** 暂时未用到，调整窗体大小使用*/
var fieldWidth = 150 ;
var root = 'topics' ;
var totalProperty = 'totalProperty' ;

Ext.onReady(function(){
	
	var windowGroup = new Ext.WindowGroup();
	
	var widthFun = function(bodyWidth){
		return ((bodyWidth-6)<500) ? 500 : (bodyWidth-6);
	}
	var heightFun = function(bodyHeight){
		return ((bodyHeight<400) ? 400 : (bodyHeight));	
	}
	
	var fPanel_search = new Ext.form.FormPanel( {
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px;',
		height : 110,
//		width : (((Ext.getBody().getWidth()-6)<800) ? 800 : (Ext.getBody().getWidth()-6)),
		width : widthFun(bodyWidth),
		frame : true,
		labelWidth : 80,
		monitorValid : true,
		items : [ {
			layout : 'column',
			border : false,
			labelSeparator : ':',
			defaults : {
				layout : 'form',
				border : false,
				columnWidth : .25
			},
			items : [ {
				items : [{
					id : 'arr_id',
					xtype : 'hidden',
					name : 'arr_id'
				},{
					id : 'arr_score',
					xtype : 'hidden',
					name : 'arr_score'
				}, {
					xtype : 'textfield',
					fieldLabel : '客户类型',
					name : 'customerType',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : customerType
				},{
					xtype : 'textfield',
					fieldLabel : '财务报表文件',
					name : 'financeFile',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : financeFile
				}]
			}, {
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户名称',
					name : 'customerName',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : customerName
				},{	
					id : 'ratingScore',
					xtype : 'textfield',
					fieldLabel : '得分情况',
					name : 'ratingScore',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			},{
				items : [ {
					xtype : 'textfield',
					fieldLabel : '资信评估模板',
					name : 'name',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : creditTemplate
				},{
					id : 'creditRegister',
					xtype : 'textfield',
					fieldLabel : '资信等级',
					name : 'creditRegister',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			},{
				items : [ {
					id : 'advise_sb',
					xtype : 'textfield',
					fieldLabel : '建议内容',
					name : 'advise_sb',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				},{
					xtype : 'textfield',
					fieldLabel : '贷款上限',
					name : 'username',
//					width : defaultTextfieldWidth,
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			}]// items
		} ],
		buttons : [ {
			text : '计算得分',
			tooltip : '根据选项选择情况计算得分',
			iconCls : 'searchIcon',
			scope : this,
			handler : function() {
				countResult(0);
			}
		} ,{
			text : '提交评估',
			tooltip : '重置查询条件',
			iconCls : 'select',
			scope : this,
			handler : function() {
				countResult(1);
			}
		}]
	});
	
	var jStore_enterprise = new Ext.data.JsonStore( {
		url :__ctxPath+'/creditFlow/creditmanagement/addJsonCreditRating.do',
		totalProperty : totalProperty,
		root : 'topics',
		fields : [ {
			name : 'id'
		},{
			name : 'indicatorName'
		}, {
			name : 'indicatorDesc'//选项字符串。暂用
		}, {
			name : 'creater'//分值串。暂用
		},{
			name : 'lastModifier'//得分分值。暂用
		}/*,{
			name : 'positionName'
		}*/],
		baseParams : {
			creditTemplateId : creditTemplateId
		}
	});
	
	jStore_enterprise.load({
		params : {
			start : start,
			limit : pageSize
		}
	});
	
	var getScore = function() {
		return 'a';
	}
	
	var cModel_enterprise = new Ext.grid.ColumnModel(
			[
					new Ext.grid.RowNumberer( {
						header : '序号',
						width : 50
					}),
					{
						header : "指标",
						width : 200,
						dataIndex : 'indicatorName'
					},
					{
						header : "选项",
						width : 300,
						dataIndex : 'indicatorDesc'
					},
					{
						header : "分值",
						width : 100,
						dataIndex : 'creater'
					},
					{
						header : "得分",
						width : 100,
						/*sortable : true,*/
						dataIndex : 'lastModifier'
						/*renderer : getScore*/
					}]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : pageSize,
		store : jStore_enterprise,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
			//jStore_enterprise.setDefaultSort('name', 'DESC');
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});

	var gPanel_enterprise = new Ext.grid.GridPanel( {
		id : 'gPanel_enterprise',
		store : jStore_enterprise,
		width : widthFun(bodyWidth),
		height : Ext.getBody().getHeight()-140,
//		height : heightFun(bodyHeight)-110,
		autoScroll : true,
		colModel : cModel_enterprise,
		//autoExpandColumn : 6,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		bbar : pagingBar,
		//tbar : [button_add,button_see,button_update,button_delete],
		listeners : {
			'rowdblclick' : function(grid,index,e){
				var id = grid.getSelectionModel().getSelected().get('id');
				seeEnterprise(id);
			}
		}
	});
	
	var panel_enterprise = new Ext.Panel( {
		title : '资信评估测试',
		autoHeight : true,
		autoScroll : true ,
		items : [fPanel_search,gPanel_enterprise]
	});
	
	var vPort_enterprise = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [{
			region : "center",
			layout : 'fit',
			items : [panel_enterprise]
		}]
	});
})