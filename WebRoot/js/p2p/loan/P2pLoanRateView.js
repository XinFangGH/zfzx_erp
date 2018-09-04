P2pLoanRateView = Ext.extend(Ext.Panel,{
	isHiddenAddBtn:false,
	isHiddenDelBtn:false,
	isHiddenEdiBtn:false,
	isHiddenSeeBtn:false,
	isFlow:false,
	isAllReadOnly:false,
	projectId:null,
	constructor:function(_cfg){
	/*	var jsCtArr = [		
			__ctxPath + '/js/creditFlow/customer/common/common.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/updateEnterprise.js'
		];*/
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
			this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
		}
		if (typeof(_cfg.isHiddenSeeBtn) != "undefined") {
			this.isHiddenSeeBtn = _cfg.isHiddenSeeBtn;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isFlow) != "undefined") {
			this.isFlow = _cfg.isFlow;
			this.isAllReadOnly=true;
		}
	    Ext.applyIf(this, _cfg);
		this.initUIComponents();
		P2pLoanRateView.superclass.constructor.call(this, {
					// layout:'fit',
					id:'P2pLoanRateView',
					layout : 'anchor',
					anchor : '100%',
					autoHeight : true,
					items : [{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',hidden:this.hiddenTitle,html : '<br/><B><font class="x-myZW-fieldset-title">【利率设置】:</font></B>'},this.panel]
				});
	},
	initUIComponents:function(){
		this.topbar = new Ext.Toolbar( {
			items : [ {
				xtype : 'button',
				text : '增加',
				tooltip : '增加利率设置',
				iconCls : 'btn-add',
				hidden:this.isHiddenAddBtn,
				scope : this,
				handler : this.createRs
			}, {
				xtype : 'button',
				text : '编辑',
				tooltip : '编辑利率设置',
				iconCls : 'btn-edit',
				hidden:this.isHiddenEdiBtn,
				scope : this,
				handler : this.editRs
			}, {
				xtype : 'button',
				text : '删除',
				tooltip : '删除选中的利率设置',
				iconCls : 'btn-delete',
				scope : this,
				hidden:this.isHiddenDelBtn,
				handler : this.removeSelRs
			}/*,{
				xtype : 'button',
				text : '查看',
				tooltip : '查看选中的个人旗下公司信息',
				iconCls : 'btn-detail',
				scope : this,
				handler : this.seeRs
			}*/]
		});
		this.jStore_thereunder = new Ext.data.JsonStore( {
				url : __ctxPath+'/p2p/listP2pLoanRate.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				autoLoad : true,
				fields : [ {
					name : 'rateId'
				}, {
					name : 'productId'
				}, {
					name : 'loanTime'
				}, {
					name : 'yearAccrualRate'
				}, {
					name : 'yearManagementConsultingOfRate'  
				}, {
					name : 'yearFinanceServiceOfRate'  
				}],
				baseParams :{
				productId : this.productId,
				start : 0,
				limit : 25}
			});
		this.panel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			autoHeight:true,
			showPaging:false,
			store : this.jStore_thereunder,
			columns : [{
				header : "贷款期限(月)",
				width : 100,
				sortable : true,
				align :'center',
				dataIndex : 'loanTime'
			}, {
				header : "贷款年化利率",
				width : 100,
				sortable : true,
				align :'center',
				dataIndex : 'yearAccrualRate',
				renderer : function(value) {
					var str = '';
					if (value =='') {
						//str=0;
					} else {
						str=value+"%";
					}
					return str;
				}
			}, {
				header : "管理咨询费年化利率",
				width : 90,
				sortable : true,
				align :'center',
				dataIndex : 'yearManagementConsultingOfRate',
				renderer : function(value) {
					var str = '';
					if (value =='') {
						str=0;
					} else {
						str=value+"%";
					}
					return str;
				}
			},{
				header : "财务服务费年化利率",
				width : 90,
				sortable : true,
				align :'center',
				dataIndex : 'yearFinanceServiceOfRate',
				renderer : function(value) {
					var str = '';
					if (value =='') {
						str=0;
					} else {
						str=value+"%";
					}
					return str;
				}
			}]
		});
	},
	createRs : function() {
		new P2pLoanRateForm({productId:this.productId,gridPanel : this.panel}).show();
	},
	
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/p2p/multiDelP2pLoanRate.do',
			grid : this.panel,
			idName : 'rateId'
		});
	},
	//编辑Rs
	editRs : function(record) {
		var s = this.panel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new P2pLoanRateForm( {
				rateId : record.data.rateId,productId : record.data.productId,gridPanel : this.panel
			}).show();
		}	
	}/*,
	seeRs : function(record){
		var s = this.panel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new P2pLoanRateForm( {
				rateId : record.data.rateId,productId : record.data.productId,gridPanel : this.panel,isReadOnly:true
			}).show();
		}	
	}*/
});
