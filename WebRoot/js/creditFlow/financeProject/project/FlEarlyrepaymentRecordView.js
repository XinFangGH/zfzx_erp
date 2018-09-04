/**
 * @author lisl
 * @class SlEarlyrepaymentRecordView
 * @description 提前还款记录
 * @extends Ext.Window
 */
FlEarlyrepaymentRecordView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.flag) {
			this.flag = _cfg.flag;
		}
		if (_cfg.projectStatus) {
			this.projectStatus = _cfg.projectStatus;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		FlEarlyrepaymentRecordView.superclass.constructor.call(this, {
			/*title : '提前还款记录',
			width : 763,
			height : 415,
			modal : true,
			iconCls : '',
			autoScroll : true,
			layout : 'fit',*/
			items : [this.gridPanel]

		});
	},
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				scope : this,
				handler : function() {
					var selRs = this.gridPanel.getSelectionModel()
							.getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}
					var record = this.gridPanel.getSelectionModel()
							.getSelected();
					new FinancingEarlyRepaymentWin({
						projectId : record.data.projectId,
						isHidden:true,
						slEarlyRepaymentId : record.data.slEarlyRepaymentId
					}).show()
				}
			},{
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenRemove,
				handler : this.removeSelRs
			}]
		});
		this.gridPanel = new HT.GridPanel({
			tbar : this.topbar,
			rowActions : false,
			showPaging : false,
			border : false,
			autoHeight:true,
			region : 'center',
			url : __ctxPath
					+ "/flFinancing/earlyrepaymentRecordsFlFinancingProject.do",
			baseParams : {
				projectId : this.projectId
			},
			fields : ['slEarlyRepaymentId','earlyProjectMoney', 'earlyDate', 'payintentPeriod','prepayintentPeriod',
					'surplusProjectMoney', 'surplusEndDate', 'reason',
					'accrualtype','dateMode','payaccrualType','isPreposePayAccrual','accrual','managementConsultingOfRate','financeServiceOfRate','creator','opTime','projectId','checkStatus'],
			columns : [{
				dateIndex : 'earlyDate',
				hidden : true
			}, {
				dateIndex : 'payintentPeriod',
				hidden : true
			}, {
				dateIndex : 'prepayintentPeriod',
				hidden : true
			}, {
				dateIndex : 'slEarlyRepaymentId',
				hidden : true
			}, {
				dateIndex : 'accrualtype',
				hidden : true
			}, {
				dateIndex : 'payaccrualType',
				hidden : true
			}, {
				dateIndex : 'dateMode',
				hidden : true
			}, {
				dateIndex : 'isPreposePayAccrual',
				hidden : true
			}, {
				dateIndex : 'accrual',
				hidden : true
			}, {
				dateIndex : 'managementConsultingOfRate',
				hidden : true
			}, {
				dateIndex : 'financeServiceOfRate',
				hidden : true
			}, {
				header : '提前还款本金金额',
				dataIndex : 'earlyProjectMoney',
				width : 130,
				align : 'right',
				renderer : function(v) {
					if (v != null) {
						return Ext.util.Format.number(v, ',000,000,000.00')
								+ "元"
					} else {
						return v + "元";
					}
				}
			}, {
				header : '提前还款日期(期数)',
				dataIndex : "accrualtype",
				width : 120,
				renderer : function(v, metadata, rec) {
					if (v == 'ontTimeAccrual') {
						return Ext.util.Format.date(rec.get('earlyDate'),
								'Y-m-d');
					} else {
						if(rec.get('payintentPeriod')==0){
							return "第" + rec.get('prepayintentPeriod') + "期";
						}else{
							return "展期第"+rec.get('prepayintentPeriod') + "期";
						}
					}
				}
			}, {
				header : '剩余本金金额',
				dataIndex : 'surplusProjectMoney',
				width : 130,
				align : 'right',
				renderer : function(v) {
					if (v != null) {
						return Ext.util.Format.number(v, ',000,000,000.00')
								+ "元"
					} else {
						return v + "元";
					}
				}
			},{
				header :'申请人',
				dataIndex :'creator'
			},{
				header : '申请日期',
				dataIndex:'opTime'
			}, {
				header : '原因说明',
				width : 230,
				dataIndex : 'reason'
			}]

		})
	},// 初始化组件
	removeSelRs:function(){
		var gridPanel=this.gridPanel;
		var store=gridPanel.getStore()
		var businessType=this.businessType;
		var gridObj1 = this.ownerCt.ownerCt.ownerCt.getCmpByName('Financing_FundIntent_formulate_editGrid_Panel').get(1)
		var rs=this.gridPanel.getSelectionModel().getSelections(); 
		 if(rs.length>1){
	       		Ext.MessageBox.show({
						title : '操作信息',
						msg : '只能选择一条记录',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
				 });
				 return false;
	       }else if(rs.length==0){
	       		Ext.MessageBox.show({
						title : '操作信息',
						msg : '请选择一条记录',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
				 });
				 return false;
	       }else{
	       		if(rs[0].data.slEarlyRepaymentId<gridPanel.getStore().getAt(gridPanel.getStore().getTotalCount()-1).data.slEarlyRepaymentId){
	       			Ext.MessageBox.show({
						title : '操作信息',
						msg : '只能删除最后一次的操作记录',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					 });
					 return false;
       		}else{
       			
		        var ids = Array();
		        ids.push(eval('rs[0].data.slEarlyRepaymentId' ));
	  		    var projectId=this.projectId;
	  		    Ext.Ajax.request({
					url : __ctxPath
							+ '/smallLoan/finance/getIsCheckedSlEarlyRepaymentRecord.do',
					params : {
						slEarlyRepaymentId : rs[0].data.slEarlyRepaymentId,
						projectId : projectId,
						businessType : 'Financing'
					},
					method : 'POST',
					success : function(response, options) {
						var obj=Ext.util.JSON.decode(response.responseText);
						if(obj.isChecked==true){
							Ext.Msg.alert('操作信息','该提前还款记录的款项已对账，不能删除,请先撤销对账记录再删除');
							return;
						}else{
							Ext.Msg.confirm('信息确认', '确认删除吗,删除记录无法恢复?', function(btn) {
						        if (btn == 'yes') {
						            Ext.Ajax.request({
						                url : __ctxPath + '/smallLoan/finance/multiDelSlEarlyRepaymentRecord.do?businessType='+businessType,
						                params : {
						                    ids : ids
						                },
						                method : 'POST',
						                success : function(response, options) {
						                    Ext.ux.Toast.msg('操作信息', '成功删除该记录！');
					                        gridPanel.getStore().reload();
					                        gridObj1.getStore().reload();
						                },
						                failure : function(response, options) {
						                    Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
						                }
						            });
						        }
					        });
						}
					}
	  		    })
			    
       		}
	       }
	}
});