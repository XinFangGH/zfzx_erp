/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ClassTypeView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ClassTypeView.superclass.constructor.call(this, {
			id : 'ClassTypeView',
			title : '信用等级管理',
			region : 'center',
			layout : 'border',
			iconCls:"btn-tree-team36",
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			region : 'north',
			border : false,
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
            bodyStyle : 'padding-top:10px',
			items : [{
			    columnWidth:0.17,
			    layout:'form',
			    border:false,
			    labelWidth:60,
			    labelAlign:'right',
			    items:[{
			        xtype:'textfield',
			        fieldLabel:'类型名称',
			        anchor:'100%',
			        name:'classname'
			    }]
			}, {
				columnWidth : 0.17,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
						   xtype:'hidden',
						   name:'createPersonId'
						}, {
				         fieldLabel : "创建人",
							xtype : "combo",
							editable : false,
							triggerClass : 'x-form-search-trigger',
							itemVale : creditkindDicId, // xx代表分类名称
							readOnly : this.isAllReadOnly,
						    anchor:'100%',
						    onTriggerClick : function(cc) {
							     var obj = this;
							     var appuerIdObj=obj.previousSibling();
								 new UserDialog({
								 	userIds:appuerIdObj.getValue(),
								 	userName:obj.getValue(),
									single : true,
									title:"创建人",
									callback : function(uId, uname) {
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
						    }
				      } ]
			}, {
				columnWidth : 0.18,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '创建时间',
					format:'Y-m-d',
					anchor:'100%',
					name : 'startTime'
				}]
			},{
			   columnWidth:0.02,
			   layout:'form',
			   border : false,
			   items:[{
			      xtype:'label',
			      text:'~'
			   }]
			},{
			    columnWidth:0.11,
			    layout:'form',
			    border : false,
			    items:[{
			       xtype:'datefield',
			       format:'Y-m-d',
			       anchor:'100%',
			       hideLabel:true,
			       name:'endTime'
			       
			    }]
			}, {
				columnWidth : 0.07,
				labelWidth : 30,
				layout : 'form',
				border : false,
				items : [ {
					xtype : 'button',
					text : '查询',
					style:'margin-left:30px',
					//width : 60,
					anchor:'100%',
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,
				items : [ {
					xtype : 'button',
					text : '重置',
					anchor:'100%',
					style:'margin-left:30px',
					//width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '新增',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_classType_add')?false:true,
				handler : this.createRs
			}, {
				iconCls : 'btn-edit',
				text : '修改',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_classType_update')?false:true,
				handler : this.editRs
			}, {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope:this,
				hidden : isGranted('_classType_delete')?false:true,
				handler : this.removeSelRs
			}, {
				iconCls : 'btn-readdocument',
				text : '查看',
				xtype : 'button',
				scope:this,
				hidden : isGranted('_classType_see')?false:true,
				handler : this.seeRs
			}, {
				iconCls : 'btn-copys',
				text : '复制',
				xtype : 'button',
				scope:this,
				hidden : isGranted('_classType_copy')?false:true,
				handler : this.copyRs
			} ]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath+'/creditFlow/creditmanagement/classTypeListScoreGradeOfClass.do',
			fields : [ {
				name : 'classId',
				type : 'long'
			}, 'className', 'remarks', 'createPersonId', 'cretePerson',
					'createTime', 'updatePersonId', 'updatePerson', 'updateTime'],
			columns : [ {
				header : 'id',
				dataIndex : 'classId',
				hidden : true
			}, {
				header : '信用等级标准名称',
				dataIndex : 'className'
			}, {
				header : '说明',
				dataIndex : 'remarks'
			}, {
				header : '创建人',
				align:'center',
				dataIndex : 'cretePerson'
			}, {
				header : '创建时间',
				align:'center',
				dataIndex : 'createTime'
			}, {
				header : '最后更新人',
				align:'center',
				dataIndex : 'updatePerson'
			}, {
				header : '最后更新时间',
				align:'center',
				dataIndex : 'updateTime'
			}]
		//end of columns
				});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	//GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		
	},
	//创建记录
	createRs : function() {
		new ScoreGradeForm({gridPanel:this.gridPanel,isHidden:false,title:'新增信用等级标准'}).show()
	},

	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath+'/creditFlow/creditmanagement/deleteClassTypeScoreGradeOfClass.do',
			grid : this.gridPanel,
			idName : 'classId'
		});
	},
	//编辑Rs
	editRs : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
		    new ScoreGradeForm({classId:record.data.classId,gridPanel:this.gridPanel,isHidden:false,isCopy:false,title:'编辑信用等级标准'}).show()
		}	
	},
	seeRs:function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
		    new ScoreGradeForm({classId:record.data.classId,gridPanel:this.gridPanel,isHidden:true,title:'查看信用等级标准'}).show()
		}	
	},
	copyRs:function(){
	  
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
		    new ScoreGradeForm({classId:record.data.classId,gridPanel:this.gridPanel,isHidden:false,isCopy:true,title:'复制信用等级标准'}).show()
		}	
	
	}
});
