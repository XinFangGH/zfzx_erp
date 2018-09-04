/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SlCompanyMainView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlCompanyMainView.superclass.constructor.call(this, {
			id : 'SlCompanyMainView',
			title : '企业主体管理',
			region : 'center',
			layout : 'border',
			iconCls:"menu-flowManager",
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}


		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			border : false,
			region : 'north',
			height : 40,
			anchor : '100%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [ /*{
				name : 'isPledge',
				xtype : 'hidden',
				value : 0
			},*/{
				columnWidth : 0.19,
				layout : 'form',
				border : false,
				//labelWidth : 60,
				labelAlign : 'right',
			
				items : [ {
					anchor:'100%',
					xtype : 'textfield',
					fieldLabel : '企业名称',
					name : 'Q_corName_S_LK'
				} ]
			}, {
				columnWidth : 0.19,
				layout : 'form',
				border : false,
				//labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					anchor:'100%',
					xtype : 'textfield',
					fieldLabel : '法人姓名',
					name : 'Q_lawName_S_LK'
				} ]
			}, {
				columnWidth : 0.19,
				layout : 'form',
				border : false,
				//labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					anchor:'100%',
					xtype : 'textfield',
					fieldLabel : '组织机构代码',
					name : 'Q_organizeCode_S_LK'
				}]
			},isShow?{
			    columnWidth : 0.2,
			    layout : 'form',
			    border:false,
			   //	labelWidth : 80,
				labelAlign : 'right',
			    items : [
			    {
			      xtype : "combo",
			      anchor : "100%",
			      fieldLabel : '所属分公司',
			      hiddenName : "companyId",
			      displayField : 'companyName',
			      valueField : 'companyId',
			      editable:false,
			      triggerAction : 'all',
			      store : new Ext.data.SimpleStore({
			       autoLoad : true,
			       url : __ctxPath+'/system/getControlNameOrganization.do',
			       fields : ['companyId', 'companyName']
			      })
			    }
			   ]}:{
			    columnWidth:0.01,
			    border:false
			    }, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					anchor : "100%",
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					anchor : "100%",
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '添加企业主体',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_create_cm')?false:true,
				handler : this.createRs
			}, {
				iconCls : 'btn-del',
				text : '删除企业主体',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_remove_cm')?false:true,
				handler : this.removeSelRs
			}, {
				iconCls : 'btn-edit',
				text : '编辑企业主体',
				xtype : 'button',
				scope:this,
				hidden : isGranted('_edit_cm')?false:true,
				handler : this.editRs
			} ]
		});

		this.gridPanel = new HT.GridPanel( {
			id:'SlCompanyMainGrid',
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath + "/creditFlow/ourmain/listSlCompanyMain.do",
			fields : [ {
				name : 'companyMainId',
				type : 'int'
			}, 'corName', 'simpleName', 'lawName', 'organizeCode',
					'businessCode', 'haveCharcter', 'tax', 'tel', 'mail',
					'messageAddress','sjjyAddress','postalCode','isPledge',
					'registerMoney','hangyeType','hangyeTypeValue','registerStartDate','companyName','companyId'],
			columns : [ {
				header : 'id',
				align:'center',
				dataIndex : 'companyMainId',
				hidden : true
			},{
			   	header : '分公司名称',
			   	hidden : !isShow,
				dataIndex : 'companyName'
			}, {
				header : '企业名称',
				dataIndex : 'corName'
			}, {
				header : '证件号码',
				align:'center',
				dataIndex : 'organizeCode'
			}, {
				header : '营业执照号码',
				align:'center',
				dataIndex : 'businessCode'
			}, {
				header : '联系电话',
				align:'center',
				dataIndex : 'tel'
			}, {
				header : '所有制性质',
				dataIndex : 'haveCharcter'
			}, {
				header : '法人姓名',
				align:'center',
				dataIndex : 'lawName'
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
		grid.getSelectionModel().each(function(rec) {
			new SlCompanyMainForm( {
				id : rec.data.companyMainId,isReadOnly : false,gridpanel : this.gridPanel
			}).show();
		});
	},
	//创建记录
	createRs : function() {
		new SlCompanyMainForm({isPledge:0,isReadOnly : false,gridpanel : this.gridPanel}).show();
	},

	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/creditFlow/ourmain/multiDelSlCompanyMain.do',
			grid : this.gridPanel,
			idName : 'companyMainId'
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
			new SlCompanyMainForm( {
				id : record.data.companyMainId,isReadOnly : false,gridpanel : this.gridPanel
			}).show();
		}	
	}
});
