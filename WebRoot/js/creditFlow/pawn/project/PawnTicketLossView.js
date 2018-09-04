/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PawnTicketLossView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PawnTicketLossView.superclass.constructor.call(this, {
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
			url : __ctxPath + "/creditFlow/pawn/project/listPawnTicketLossRecord.do?projectId="+this.projectId+"&businessType="+this.businessType,
			showPaging : false,
			hiddenCm:true,
			autoHeight : true,
			tbar : this.topbar,
			fields : [ {
				name : 'lossRecordId',
				type : 'long'
			}, 'lossTime', 'lossPerson', 'lossCost', 'operatorId',
					'operatorName', 'remarks', 'projectId', 'businessType'],
			columns : [{
			   	header : '挂失时间',
				dataIndex : 'lossTime'
			}, {
				header : '挂失人',
				dataIndex : 'lossPerson'
			}, {
				header : '挂失费用',
				dataIndex : 'lossCost',
				renderer : function(value, metaData, record, rowIndex,colIndex, store){
					return Ext.util.Format.number(value,',000,000,000.00') + "元"
				}
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
			new PawnTicketLossWindow({projectId:record.data.projectId,businessType:record.data.businessType,lossRecordId:record.data.lossRecordId,isAllReadOnly:false}).show()
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
			new PawnTicketLossWindow({projectId:record.data.projectId,businessType:record.data.businessType,lossRecordId:record.data.lossRecordId,isAllReadOnly:true}).show()
		}	
	}
	
});
