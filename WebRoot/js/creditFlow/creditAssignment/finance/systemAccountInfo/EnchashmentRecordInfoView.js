//enchashmentRecordInfoView
/**
 * 金智万维软件
 * 取现流程审批查看
 * @class enchashmentRecordInfoView
 * @extends Ext.Panel
 */
EnchashmentRecordInfoView = Ext.extend(Ext.Panel, {
	rechargeConfirmStatus : "",
	recordStatus:2,
	titlePrefix : "",
	seniorHidden:false,
	Confirmhidden:false,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.rechargeConfirmStatus) != "undefined") {
			this.rechargeConfirmStatus =_cfg.rechargeConfirmStatus;
		}
		if (typeof(_cfg.recordStatus) != "undefined") {
			this.recordStatus =parseInt(_cfg.recordStatus);
		}
		if(this.rechargeConfirmStatus==1){
			this.titlePrefix="取现一次审核";
		}else if(this.rechargeConfirmStatus==2){
			this.titlePrefix="投资客户取现记录";
		}else  if(this.rechargeConfirmStatus==4){
			this.titlePrefix="取现二次复审";
		}else  if(this.rechargeConfirmStatus==5){
			this.titlePrefix="取现手续办理";
		}else{
			this.titlePrefix="取现记录查询";
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		EnchashmentRecordInfoView.superclass.constructor.call(this, {
			id : 'EnchashmentRecordInfoView_'+this.rechargeConfirmStatus+this.recordStatus,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team23',
			items : [ this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};
		
		this.searchPanel = new Ext.FormPanel({
//			rechargeLevel:this.rechargeLevel,
			layout : 'column',
			region : 'north',
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
						
						name : 'rechargeLevel',
						anchor : "100%",
						xtype : 'hidden',
						value: this.rechargeLevel
					},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '投资人',
						name : 'investPersonName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					labelWidth :50,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		},{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					labelWidth :50,
					items :[{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						//width : 40,
						anchor : "90%",
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit2',
					text : '取现办理',
					hidden : isGranted('_see_investPDetail_' + this.rechargeConfirmStatus+this.recordStatus)? false: true,
					xtype : 'button',
					scope : this,
					handler :this.enchashmentOperate
				}]
			});
			
			
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			name : 'confirmRechargeGrid',
			region : 'center',
			plugins : [summary],
//			tbar : eval(this.rechargeConfirmStatus)==eval(5)?this.topbar:null,
			tbar : eval(this.recordStatus)==eval(2)?null:this.topbar,
			notmask : true,
			// 不适用RowActions
			rowActions : false,
			url : __ctxPath + "/creditFlow/creditAssignment/bank/getreChargeListObAccountDealInfo.do?transferType=2&dealRecordStatus="+this.recordStatus,
			fields : [{
				name : 'id',
				type : 'int'
			}, 'accountId','incomMoney', 'payMoney', 'transferType', 'shopId', 'shopName','currentMoney',
					'createId', 'dealType', 'createDate','investPersonId',
					'transferDate', 'rechargeLevel', 'investPersonName', 'createName','bankName', 'bankNum', 'areaName', 'createName','rechargeConfirmStatus','seniorValidationRechargeStatus','investPersonType'],
			/*listeners : {
				scope : this,
				afteredit : function(e) {
					if (e.originalValue != e.value){
						var args ;
						if(e.field =='rechargeConfirmStatus'){
								args = {
										'rechargeConfirmStatus' : e.value,
										'rechargeLevel':e.record.data['rechargeLevel'],
										'id' : e.record.data['id']
							}
						}
						if(e.field =='seniorValidationRechargeStatus'){
								args = {
										'seniorValidationRechargeStatus' : e.value,
										'rechargeLevel':this.rechargeLevel,
										'id' : e.record.data['id']
							}
						}
						if(e.field =='rechargeConfirmStatus' ||e.field =='seniorValidationRechargeStatus'){
							var panel =this;
							Ext.Ajax.request({
										url : __ctxPath + "/creditFlow/creditAssignment/bank/ConfirmStatusObAccountDealInfo.do",
										method : 'POST',
										scope :this,
										success : function(response, request) {
											e.record.commit();
											panel.getCmpByName("confirmRechargeGrid").getStore().reload();
										},
										failure : function(response) {
											Ext.ux.Toast.msg('状态', '操作失败，请重试！');
										},
										params: args
									})
						}
					}
				}
			},*/

			columns : [{
				header : 'id',
				dataIndex : 'id',
				align:'center',
				hidden : true
			}, {
				header : 'investmentPersonId',
				dataIndex : 'investPersonId',
				hidden : true
			}, {
				header : '投资人姓名',
				width : 100,
				summaryRenderer : totalMoney,
				dataIndex : 'investPersonName'
			}, {
				header : '取现金额(元)',
				width : 130,
				align:'right',
				summaryType : 'sum',
				dataIndex : 'payMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00');
					}
				}
			}, {
				header : '剩余金额(元)',
				width : 130,
				align:'right',
				summaryType : 'sum',
				dataIndex : 'currentMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00');
					}
				}
			}, {
				header : '交易时间',
				width : 310,
				align:'center',
				dataIndex : 'transferDate'
			}, {
				header : '门店名称',
				width : 70,
				dataIndex : 'shopName'
			}, {
				header : '操作人',
				width : 70,
				dataIndex : 'createName'
			}]
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);
	
	},// end of the initComponents()
	
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	enchashmentOperate:function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
				if (selectRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
							var record = selectRs[0];
							var enchashMentOperateForm =new EnchashMentOperateForm({
								customerType:record.data.investPersonType,
								investPersonId:record.data.investPersonId,
								obAccountDealInfoId:record.data.id,
								flashTargat:this.gridPanel
							})
							enchashMentOperateForm.show();
				}
	}
});
