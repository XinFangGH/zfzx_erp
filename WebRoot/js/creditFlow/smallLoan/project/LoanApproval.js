/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
LoanApproval = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		LoanApproval.superclass.constructor.call(this, {
			id : 'LoanApproval',
			title : '贷款审批',
			region : 'center',
			layout : 'border',
			iconCls :'btn-tree-team9',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			border : false,
			region : 'north',
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [  {
				columnWidth : 0.25,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '项目编号',
					anchor:'100%',
					name : 'projectNumber'
				} ]
			},{
				columnWidth : 0.25,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '项目名称',
					anchor:'100%',
					name : 'projectName'
				}]
			}, {
				columnWidth : 0.25,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
			
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户名称',
					anchor:'95%',
					name : 'customerName'
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					width : 60,
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
					width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})
		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'menu-flowWait',
						text : '贷款审批',
						xtype : 'button',
						scope : this,
						//hidden : isGranted('_loanApproval_flow') ? false : true,
						handler : this.detailPro
					}]
		});
		this.gridPanel = new HT.GridPanel( {
			id:'LoanApprovalGrid',
			region : 'center',
			hiddenCm:true,
			tbar:this.topbar,
			notmask :true,
			url : __ctxPath + "/flow/getByProcessNameNodeKeyOfCommonTask.do?processName=smallLoanCommonFlow&flowNodeKey=slcfLoanExamine&searchType=byUserId",
			fields : [ {
				name : 'task.taskId',
				type : 'int'
			}, 'task.dueDate',
			'task.activityName',
			'vSmallloanProject.runId',
			'vSmallloanProject.taskCreateTime',
			'vSmallloanProject.projectId',
			'vSmallloanProject.subject',
			'vSmallloanProject.customerName',
			'vSmallloanProject.projectNumber',
			// 'vCommonProjectFlow.operationTypeValue',//视图没有，删除掉。
			'vSmallloanProject.activityName',
			'vSmallloanProject.businessType',
			'vSmallloanProject.projectName',
			'vSmallloanProject.oppositeTypeValue',
			'vSmallloanProject.projectMoney',
			'vSmallloanProject.businessManagerValue'
			],
			columns : [ {
				header : 'id',
				dataIndex : 'vSmallloanProject.projectId',
				hidden : true
			}, {
				header : '项目编号',
				dataIndex : 'vSmallloanProject.projectNumber'
			}, {
				header : '项目名称',
				dataIndex : 'vSmallloanProject.projectName',
				width:300
			}, {
				header : '客户类型',
				dataIndex : 'vSmallloanProject.oppositeTypeValue',
				width:100
			}, {
				header : '金额',
				align:'right',
				dataIndex : 'vSmallloanProject.projectMoney',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return Ext.util.Format.number(value,'0,000.00')+"元"	
				}
			}, {
				header : '业务经理',
				dataIndex : 'vSmallloanProject.businessManagerValue'
			}, {
				header : '提交时间',
				dataIndex : 'vSmallloanProject.taskCreateTime'
			}]
		//end of columns
				});
		this.gridPanel.addListener('rowdblclick', this.rowClick);
		this.gridPanel.addListener('afterrender', function(){
			 this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl (), {
				 msg  : '正在加载数据中······,请稍候······',
				 store:this.gridPanel.store,
				 removeMask : true// 完成后移除
			});
			this.loadMask1 .show(); //显示
			
		},this);
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
	detailPro : function() {
		
		var rs = this.gridPanel.getSelectionModel().getSelections();
		if (rs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择任务记录!');
			return;
		}
		if (rs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条任务记录!');
			return;
		}
		var record = rs[0];
		var contentPanel = App.getContentPanel();
		var formView = contentPanel.getItem('ProcessNextForm'+ record.get('task.taskId'));
		if (formView == null) {
			formView = new ProcessNextForm({
				taskId : record.get('task.taskId'),
				activityName : record.get('task.activityName'),
				agentTask : true
			});
			contentPanel.add(formView);
		}
		contentPanel.activate(formView);
	},
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(record) {
		var contentPanel = App.getContentPanel();
		var formView = contentPanel.getItem('ProcessNextForm'+ record.get('task.taskId'));
		if (formView == null) {
			formView = new ProcessNextForm({
				taskId : record.get('task.taskId'),
				activityName : record.get('task.activityName'),
				agentTask : true
			});
			contentPanel.add(formView);
		}
		contentPanel.activate(formView);
			
		});
	}
});
