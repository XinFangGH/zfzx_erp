/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PawnTicketReissueView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PawnTicketReissueView.superclass.constructor.call(this, {
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
			url : __ctxPath + "/creditFlow/pawn/project/listPawnTicketReissueRecord.do?projectId="+this.projectId+"&businessType="+this.businessType,
			showPaging : false,
			hiddenCm:true,
			autoHeight : true,
			tbar : this.topbar,
			fields : [ {
				name : 'reissueRecordId',
				type : 'long'
			}, 'reissueTime', 'reissuePerson', 'operatorId', 'operatorName',
					'remarks', 'lossRecordId', 'projectId', 'businessType'],
			columns : [{
			   	header : '补发时间',
				dataIndex : 'reissueTime'
			}, {
				header : '补发人',
				dataIndex : 'reissuePerson'
			}, {
				header : '经办人',
				dataIndex : 'operatorName'
			}, {
				header : '备注',
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
			/*if(record.data.lossRecordId<this.gridPanel.getStore().getAt(this.gridPanel.getStore().getTotalCount()-1).data.lossRecordId){
				Ext.ux.Toast.msg('操作信息','只能编辑最后一条记录');
				return false;
			}*/
			new PawnTicketReissueWindow({projectId:record.data.projectId,businessType:record.data.businessType,lossRecordId:record.data.lossRecordId,reissueRecordId:record.data.reissueRecordId,isAllReadOnly:false}).show()
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
			new PawnTicketReissueWindow({projectId:record.data.projectId,businessType:record.data.businessType,lossRecordId:record.data.lossRecordId,reissueRecordId:record.data.reissueRecordId,isAllReadOnly:true}).show()
		}	
	}
	
});
