/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PawnRedeemView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PawnRedeemView.superclass.constructor.call(this, {
			items : [ this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
	this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenEdit,
				handler : this.editRs
			}, {
				iconCls : 'btn-readdocument',
				text : '查看',
				xtype : 'button',
				scope : this,
				handler : this.seeRs
			}]
		});
		this.gridPanel = new HT.GridPanel( {
			url : __ctxPath + "/creditFlow/pawn/project/listPawnRedeemManagement.do?projectId="+this.projectId+"&businessType="+this.businessType,
			showPaging : false,
			hiddenCm:true,
			autoHeight : true,
			tbar : this.topbar,
			fields : [ {
				name : 'redeemId',
				type : 'long'
			}, 'redeemNum', 'redeemWay', 'managerId', 'managerName',
					'redeemDate', 'remarks', 'projectId', 'businessType','redeemWayValue'],
			columns : [{
			   	header : '赎当凭证号',
				dataIndex : 'redeemNum'
			}, {
				header : '赎当方式',
				dataIndex : 'redeemWayValue'
			}, {
				header : '经办人',
				dataIndex : 'managerName'
			}, {
				header : '受理日期',
				dataIndex : 'redeemDate'
			}, {
				header : '赎当备注',
				dataIndex : 'remarks'
			}]
		//end of columns
		});


	},
	editRs:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			if(record.data.redeemId<this.gridPanel.getStore().getAt(this.gridPanel.getStore().getTotalCount()-1).data.redeemId){
				Ext.ux.Toast.msg('操作信息','只能编辑最后一条记录');
				return false;
			}
			new PawnRedeemWindow({projectId:record.data.projectId,businessType:record.data.businessType,redeemId:record.data.redeemId,isAllReadOnly:false,gridPanel:this.fundGridPanel,redeemGridPanel:this.gridPanel}).show()
		}	
	},
	seeRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new PawnRedeemWindow({projectId:record.data.projectId,businessType:record.data.businessType,redeemId:record.data.redeemId,isAllReadOnly:true,gridPanel:this.fundGridPanel,redeemGridPanel:this.gridPanel}).show()
		}	
	}
	
});
