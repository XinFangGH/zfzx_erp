/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京金智万维软件有限公司
 * @createtime:
 */
FundsToPromoteView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		FundsToPromoteView.superclass.constructor.call(this, {
			id : 'FundsToPromoteView',
			title : '资金推介',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			items : [ this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '资金推介',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_create_cm')?false:true,
				handler : this.editRs
			} ]
		});
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath
					+ '/project/findListSlSmallloanProject.do?projectStatus=1&isGrantedShowAllProjects='
					+ this.isGrantedShowAllProjects,
			fields : [{
						name : 'projectId',
						type : 'long'
					}, 'projectName','businessType', 'projectNumber','oppositeType','oppositeID', 'projectMoney','startDate','customerName','lastPresentDate'],
			columns : [ {
				header : 'fundIntentId',
				dataIndex : 'fundIntentId',
				hidden : true
			}, {
				header : '项目编号',
				dataIndex : 'projectNumber'
			}, {
				header : '融资客户名称',
				dataIndex : 'oppositeID',
				renderer : function(value,metaData, record,rowIndex, colIndex,store){
							var oppositeType = record.data.oppositeType;
							var oppositeID = record.data.oppositeID;
							Ext.Ajax.request({
								url : __ctxPath + '/customer/getCustNameCustomer.do',
								params : {'customerId':oppositeID,'oppositeType':oppositeType},
								async:false,
								success : function(response){
									value = response.responseText;
								}
							});
							return value;
						}
			}, {
				header : '期望资金到位日期(截止日期)',
				dataIndex : 'startDate',
				sortable : true
			}, {
				header : '拟贷款金额',
				dataIndex : 'projectMoney',
				renderer : function(value){
					var v = Ext.util.Format.number(value,'0,000.00');
					return v+('元');
				}
				
			}, {
				header : '最后推介日期',
				dataIndex : 'lastPresentDate'
			}]
		//end of columns
				});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	
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
			var contentPanel = App.getContentPanel();
			var panel = new FundsToPromoteWindow( {
				projectId: record.data.projectId,
				businessType : record.data.businessType,
				oppositeType : record.data.oppositeType,
				projectName : record.data.projectName,
				projectMoney : record.data.projectMoney
			});
			contentPanel.add(panel);
			contentPanel.activate(panel);
		}	
	}
});
