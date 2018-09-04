/**
 * @author lisl
 * @class SlAlterAccrualRecordView
 * @description 利率变更记录
 * @extends Ext.Window
 */
SlAlterAccrualRecordView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg.projectId) {
			this.projectId = _cfg.projectId;
		}
		if (_cfg.projectStatus) {
			this.projectStatus = _cfg.projectStatus;
		}
		if (_cfg.oppositeType) {
			this.oppositeType = _cfg.oppositeType;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlAlterAccrualRecordView.superclass.constructor.call(this, {
			/*title : '利率变更记录',
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
					
					var record = this.gridPanel.getSelectionModel().getSelected();
					var contentPanel = App.getContentPanel();
					var formView = contentPanel.getItem('SlAlterAccrualRecordEditInfoView_see_'+record.data.slAlteraccrualRecordId);
					if (formView == null){
						 formView=new SlAlterAccrualRecordEditInfoView({record:record,oppositeType:this.oppositeType,flag:'see',projectId:this.projectId})
						 contentPanel.add(formView);
					}
					contentPanel.activate(formView);
			/*		new SlAlterAccrualDetailView({
						surplusnotMoney : (this.projectMoney - ((this.payProjectMoney == null)
								? 0
								: this.payProjectMoney)),
						intentDate : record.data.surplusEndDate,
						payintentPeriod : record.data.payintentPeriod,
						projectId : this.projectId,
						record : record
					}).setVisible(true);*/
				}
			},{
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden:this.isEditHidden,
				handler : function(){
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
					var record = this.gridPanel.getSelectionModel().getSelected();
					if(record.get('checkStatus')==1){
						Ext.ux.Toast.msg('操作信息', '该利率变更记录已经通过审核，不能编辑！');
						return;
					}
					var contentPanel = App.getContentPanel();
					var formView = contentPanel.getItem('SlAlterAccrualRecordEditInfoView_edit_'+record.data.slAlteraccrualRecordId);
					if (formView == null){
						 formView=new SlAlterAccrualRecordEditInfoView({record:record,oppositeType:this.oppositeType,flag:'edit',projectStatus:this.projectStatus,projectId:this.projectId})
						 contentPanel.add(formView);
					}
					contentPanel.activate(formView);

				}
			}/*,{
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			}*/]
		});
		this.gridPanel = new HT.GridPanel({
			tbar : this.topbar,
			rowActions : false,
			showPaging : false,
			autoHeight : true,
			border : false,
			region : 'center',
			url : __ctxPath
					+ "/project/alterAccrualRecordsSlSmallloanProject.do",
			baseParams : {
				projectId : this.fundProjectId
			},
			fields : ['slAlteraccrualRecordId','reason','alelrtDate','alterProjectMoney',
					'dateMode', 'accrualtype', 'accrual',
					'payaccrualType','isPreposePayAccrual','managementConsultingOfRate',
					'financeServiceOfRate','surplusEndDate','alterpayintentPeriod','payintentPeriod','creator','opTime','projectId','checkStatus'],
			columns : [{
				dateIndex : 'slAlteraccrualRecordId',
				hidden : true
			},{
				dateIndex : 'alelrtDate',
				hidden : true
			}, {
				dateIndex : 'dateMode',
				hidden : true
			}, {
				dateIndex : 'payaccrualType',
				hidden : true
			}, {
				dateIndex : 'isPreposePayAccrual',
				hidden : true
			}, {
				dateIndex : 'surplusEndDate',
				hidden : true
			}, {
				dateIndex : 'alterpayintentPeriod',
				hidden : true
			}/*,{
				header : '剩余本金金额',
				dataIndex: 'alterProjectMoney',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
					return Ext.util.Format.number(value,'0,000.00')+"元"	
				}
			}*/, {
				header : '变更后贷款利率',
				dataIndex : 'accrual',
				width : 92,
				renderer : function(v) {
					return v + "%";
				}
			}/*, {
				header : '变更后咨询管理费率',
				dataIndex : "managementConsultingOfRate",
				width : 115,
				renderer : function(v) {
					return v + "%";
				}
			}, {
				header : '变更后财务服务费率',
				dataIndex : 'financeServiceOfRate',
				width : 115,
				renderer : function(v) {
					return v + "%";
				}
			}*/, {
				header : '变更开始日期',
				dataIndex : 'alelrtDate',
				width : 135
			},{
				header :'状态',
				dataIndex : 'checkStatus',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
					if(value=='0'){
						return '未审核'
					}else if(value=='1'){
						return '审核通过'
					}else if(value=='3'){
						return '审核失败'
					}
				}
			},{
				header : '申请人',
				dataIndex : 'creator'
			},{
				header : '申请日期',
				dataIndex : 'opTime'
			}, {
				header : '申请原因',
				width : 210,
				dataIndex : 'reason'
			}],// end of columns
			listeners : {
				scope : this,
				'rowdblclick' : function(grid, rowindex, e) {}
			}

		})
	},// 初始化组件
	removeSelRs:function(){
		var businessType=this.businessType
		var gridPanel=this.gridPanel;
		var store=gridPanel.getStore()
		var gridObj1 = this.ownerCt.ownerCt.ownerCt.getCmpByName('SlFundIntentViewVM_panel').get(1)
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
	       		if(rs[0].data.slAlteraccrualRecordId<gridPanel.getStore().getAt(gridPanel.getStore().getTotalCount()-1).data.slAlteraccrualRecordId){
	       			Ext.MessageBox.show({
						title : '操作信息',
						msg : '只能删除最后一次的操作记录',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					 });
					 return false;
       		}else{
       			
		        var ids = Array();
		        ids.push(eval('rs[0].data.slAlteraccrualRecordId' ));
	
	  		    var projectId=this.projectId;
		
	  		    Ext.Ajax.request({
					url : __ctxPath
							+ '/smallLoan/finance/getIsCheckedSlAlterAccrualRecord.do',
					params : {
						slAlteraccrualRecordId : rs[0].data.slAlteraccrualRecordId,
						projectId : projectId
					},
					method : 'POST',
					success : function(response, options) {
						var obj=Ext.util.JSON.decode(response.responseText);
						if(obj.isChecked==true){
							Ext.Msg.alert('操作信息','该利率变更记录的款项已对账，不能删除，请先撤销对账记录再删除');
							return;
						}else{
							Ext.Msg.confirm('信息确认', '确认删除吗,删除记录无法恢复?', function(btn) {
						        if (btn == 'yes') {
						            Ext.Ajax.request({
						                url : __ctxPath + '/smallLoan/finance/multiDelSlAlterAccrualRecord.do?businessType='+businessType,
						                params : {
						                    ids : ids
						                },
						                method : 'POST',
						                success : function(response, options) {
						                    Ext.ux.Toast.msg('操作信息', '成功删除该记录！');
						                       gridPanel.getStore().reload();
						                       gridObj1.getStore().reload()
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