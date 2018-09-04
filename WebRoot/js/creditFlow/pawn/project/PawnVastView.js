/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PawnVastView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PawnVastView.superclass.constructor.call(this, {
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
			url : __ctxPath + "/creditFlow/pawn/project/listPawnVastMaragement.do?projectId="+this.projectId+"&businessType="+this.businessType,
			showPaging : false,
			hiddenCm:true,
			autoHeight : true,
			tbar : this.topbar,
			fields : [ {
				name : 'vastId',
				type : 'long'
			}, 'vastNumber', 'vastWay', 'managerId', 'managerName',
					'vastDate', 'remarks', 'projectId', 'businessType','vastWayValue'],
			columns : [{
			   	header : '赎当凭证号',
				dataIndex : 'vastNumber'
			}, {
				header : '赎当方式',
				dataIndex : 'vastWayValue'
			}, {
				header : '经办人',
				dataIndex : 'managerName'
			}, {
				header : '受理日期',
				dataIndex : 'vastDate'
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
			if(record.data.vastId<this.gridPanel.getStore().getAt(this.gridPanel.getStore().getTotalCount()-1).data.vastId){
				Ext.ux.Toast.msg('操作信息','只能编辑最后一条记录');
				return false;
			}
			new PawnVastWindow({projectId:record.data.projectId,businessType:record.data.businessType,vastId:record.data.vastId,isAllReadOnly:false,gridPanel:this.fundGridPanel,vastGridPanel : this.gridPanel}).show()
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
			new PawnVastWindow({projectId:record.data.projectId,businessType:record.data.businessType,vastId:record.data.vastId,isAllReadOnly:true,gridPanel:this.fundGridPanel,vastGridPanel : this.gridPanel}).show()
		}	
	}
	
});
