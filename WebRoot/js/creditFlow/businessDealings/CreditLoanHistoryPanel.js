CreditLoanHistoryPanel = Ext.extend(Ext.Panel,{
	isReadOnly:false,
	constructor:function(_cfg){
		var _cfg = _cfg||{};
		Ext.applyIf(this,_cfg);
		if(typeof(_cfg.isReadOnly)!="undefined"){
			this.isReadOnly = _cfg.isReadOnly;
		}
		if(typeof(this.personId)=="undefined")throw "CreditLoanHistoryPanel 未传入personId,personId为必填项";
		this.initUIComponents();
		CreditLoanHistoryPanel.superclass.constructor.call(this,{
			id : 'CreditLoanHistory',
			title : '信贷历史录入',
			layout : 'form',
			autoHeight:true,
			autoWidth:true,
			iconCls :'menu-company',
			items : [this.gridPanel]
		});
	},
	initUIComponents:function(){
		this.topbar = new Ext.Toolbar({
			items : [ {
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						hidden:this.isReadOnly,
						scope : this,
						handler : this.addCreditLoanHistory
					},{
						iconCls : 'btn-edit',
						text : '编辑',
						xtype : 'button',
						hidden:this.isReadOnly,
						scope : this,
						handler : this.editCreditLoanHistory
					},{
						iconCls : 'btn-mode',
						text : '查看',
						xtype : 'button',
						scope : this,
						handler : this.seeCreditLoanHistory
					},{
						iconCls:'btn-delete',
						text:'删除',
						xtype:'button',
						hidden:this.isReadOnly,
						scope:this,
						handler:this.deleteCreditLoanHistory
					}]
		});
		var store = new Ext.data.JsonStore({
			url : __ctxPath+'/creditFlow/customer/person/ajaxQueryPersonCreditLoanHistory.do',
			totalProperty :'totalProperty',
			root:'topics',
			autoLoad:true,
			baseParams:{
				personId:this.personId
			},
			fields:[{
				name:'id'
			},{
				name:'bankName'
			},{
				name:'loanMoney'
			},{
				name:'loanPeriod'
			},{
				name:'isHaveOverDue'
			},{
				name:'loanStartDate'
			},{
				name:'loanEndDate'
			},{
				name:'loanState'
			},{
				name:'personId'
			}]
		});
		
		var dicCombo = new DicKeyCombo({
				nodeKey:'xdlsdkzt',
				editable : false,
				readOnly : true,
				listeners : {
					afterrender : function(combox) {
						var st = combox.getStore();
						st.on("load", function() {
							if (combox.getValue() == 0
									|| combox.getValue() == 1
									|| combox.getValue() == ""
									|| combox.getValue() == null) {
								combox.setValue("");
							} else {
								combox.setValue(combox
										.getValue());
							}
							combox.clearInvalid();
						})
					}
				}
		});
		this.gridPanel = new Ext.grid.EditorGridPanel({
			id:'CreditLoanHistoryPanelGrid',
			tbar:this.topbar,
			autoHeight : true,
			disableSelection:false,
			store:store,
			sm:new Ext.grid.CheckboxSelectionModel({
					singleSelect:false
				}),
			viewConfig:{
				forceFit:true,
				autoFill : true
			},
			columns:[new Ext.grid.CheckboxSelectionModel({
					singleSelect:false
				}),{
						header:'id',
						dataIndex:'id',
						hidden:true
					},{
						header:'客户Id',
						dataIndex:"personId",
						hidden:true
					},{
						header:"借款机构",//'借款银行',
						dataIndex:'bankName'
					},{
						header:'借款金额',
						dataIndex:'loanMoney'
					},{
						header:'是否有逾期',
						dataIndex:'isHaveOverDue',
						renderer:function(value){
							if(value)return "是"
							return "否";
						}
					},{
						header:'开始日期',
						dataIndex:'loanStartDate'
					},{
						header:'结束日期',
						dataIndex:'loanEndDate'
					},{
						header:'状态',
						dataIndex:'loanState',
						xtype:'combocolumn',
						editor:dicCombo,
						gridId:"CreditLoanHistoryPanelGrid"
					}]
		});
	},
	addCreditLoanHistory:function(){
		new CreditLoanHistoryFormPanel({personId:this.personId,gridPanel:this.gridPanel}).show()
	},
	editCreditLoanHistory:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new CreditLoanHistoryFormPanel({personId:this.personId,gridPanel:this.gridPanel,id:record.data.id,isReadOnly:false}).show()
		}
	},
	seeCreditLoanHistory:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new CreditLoanHistoryFormPanel({personId:this.personId,gridPanel:this.gridPanel,id:record.data.id,isReadOnly:true}).show()
		}
	},
	deleteCreditLoanHistory:function(){
		$delGridRs( {
			url : __ctxPath+'/creditFlow/customer/person/deleteRsPersonCreditLoanHistory.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	}
})