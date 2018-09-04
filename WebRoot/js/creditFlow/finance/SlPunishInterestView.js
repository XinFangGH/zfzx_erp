/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
SlPunishInterestView =  Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.businessType)!="undefined"){
		      this.businessType=_cfg.businessType;
		}
		if(typeof(_cfg.FundType)!="undefined"){
		      this.FundType=_cfg.FundType; //principal ,interest
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlPunishInterestView.superclass.constructor.call(this, {
			 id : 'SlPunishInterestView',
			 region : 'center',
			 layout : 'border',
			 modal : true,
			 height :300,
			 width : screen.width*0.5,
			 maximizable : true,
			 title : '罚息',
			 items : [this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
        var labelsize=70;
		var labelsize1=115;

		this.topbar = new Ext.Toolbar({
			items : [{
	        	iconCls : 'btn-user-sel',
	        	text : '流水对账',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_liushui_f_interestIncome_'+this.businessType)?false:true,
				handler : this.openliushuiwin
			},new Ext.Toolbar.Separator({
				hidden : isGranted('_liushui_f_interestIncome_'+this.businessType)?false:true
			}),{
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_liushuisee_f_interestIncome_'+this.businessType)?false:true,
				handler : this.openliushuiwin1
			},{
				iconCls : 'btn-ok',
				text :'核销',
				xtype : 'button',
				scope : this,
				hidden : false,
				handler : this.pingAccount
			}]
		});

		var type=this.type;
		
		this.gridPanel = new HT.GridPanel({
			bodyStyle : "width : 100%",
			region : 'center',
			tbar : this.topbar,
			viewConfig: {  
            	forceFit:false  
            },
			// 使用RowActions
			rowActions : false,
			id : 'SlPunishInterestGrid',
			url : __ctxPath + "/creditFlow/finance/listSlPunishInterest.do?fundIntentId="+this.fundIntentId,
			fields : [{
					name : 'punishInterestId',
					type : 'int'
				}, 'incomeMoney','intentDate','payMoney', 'factDate','afterMoney', 'notMoney',
				'businessType','companyId','fundType','flatMoney','penaltyTax','overdureTax'],
			columns : [{
					header : 'punishInterestId',
					dataIndex : 'punishInterestId',
					hidden : true
				}, {
					header : '资金类型',
					dataIndex : '',
					width : 100,
					renderer:function(){
						return "罚息"
					}
				}, {
					header : '罚息总额',
					dataIndex : 'incomeMoney',
					align : 'right',
					width : 100,
					renderer:function(v){
						return Ext.util.Format.number(v,',000,000,000.00')+"元"
             	    }
				}, {
					header : '罚息截至日期',
					width : 100,
					dataIndex : 'intentDate',
					align : 'center'
				}, {
					header : '实际到账日',
					width : 100,
					dataIndex : 'factDate'
				}, {
					header : '核销金额',
					dataIndex : 'flatMoney',
					align : 'right',
					width : 100,
					renderer:function(v){
						if(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元"
						}else{
							return Ext.util.Format.number(0,',000,000,000.00')+"元"
						}
             		}
				}, {
					header : '已对账金额',
					dataIndex : 'afterMoney',
					width : 100,
					align : 'right',
					renderer:function(v){
						return Ext.util.Format.number(v,',000,000,000.00')+"元"
             		}
				}, {
					header : '未对账金额',
					dataIndex : 'notMoney',
					align : 'right',
					width : 100,
					sortable:true,
					renderer : function(v) {
						switch (v) {
							case 0:
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
								break;
							default:
								return '<font color="red">'+Ext.util.Format.number(v,',000,000,000.00')+'元</font>';
						}
					}
				}]
			});
			this.gridPanel.addListener('cellclick', this.cellClick);
		},// end of the initComponents()
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
		//流水对账
		openliushuiwin : function() {
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中记录');
				return false;
			}
			var flag=true;
			for(var i=0;i<s.length;i++){
				var notMoney=s[i].data.notMoney;
				if(notMoney==null){
					notMoney=0;
				}
				if(notMoney==0){
					Ext.ux.Toast.msg('操作信息', '该罚息已全部对账!');
					flag=false;
					break;
				}
			}
			if(flag){
				if(s.length>1){
					this.manyInntentopenliushuiwin();
				}else if(s.length==1){
				   this.oneopenliushuiwin();
				}
			}
		},
		//单笔对账
		oneopenliushuiwin : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			var	record=s[0];
		    var flag=0;            //incomeMoney
		    if(record.data.payMoney !=0){   //payMoney
		    	flag=1;
		    }
			new SlPunishInterestForm({
				punishInterestId : record.data.punishInterestId,
				fundType : record.data.fundType,
				notMoney : record.data.notMoney,
				flag:flag,
				businessType :record.data.businessType,
				companyId:record.data.companyId
			}).show();
		},
		//批量对账
		manyInntentopenliushuiwin : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中记录');
				return false;
			}else{
		   		var company=s[0].data.companyId;
		     	for(var i=1;i<s.length;i++){
		    		if(s[i].data.companyId !=company){
		    			Ext.ux.Toast.msg('操作信息','请选中的记录分公司保持一致');
						return false;
		    		}
		    	}
			    var a=0;
			    var b=0;
			    var sumnotMoney=0;
			    for(var i=0;i<s.length;i++){
			    	if(s[i].data.payMoney >0)
			    	a++;
			    	if(s[i].data.incomeMoney >0)
				    b++;
			    	sumnotMoney=sumnotMoney+s[i].data.notMoney;
			    }
			    if(a>0 && b>0){
			    	Ext.ux.Toast.msg('操作信息','请选中的记录支出保持一致');
					return false;
			    }
				var ids = $getGdSelectedIds(this.gridPanel,'punishInterestId');
				var	record=s[0];
				var flag=0;            //incomeMoney
			    if(record.data.payMoney !=0){   //payMoney
			     	flag=1;
			    }
				new SlPunishInterestForm1({
					ids : ids,
					flag:flag,
					fundType : record.data.fundType,
					sumnotMoney :sumnotMoney,
					companyId:record.data.companyId
				}).show();
			}	
		},
		//核销
		pingAccount:function(record){
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				var	record=s[0];
				if(s[0].data.notMoney==0){
					Ext.ux.Toast.msg('操作信息','该罚息已全部对账!');
					return false;
				}else{
					new editPunishAfterMoneyForm({
						punishInterestId : record.data.punishInterestId,
						afterMoney : record.data.afterMoney,
						notMoney : record.data.notMoney,
						flatMoney : record.data.flatMoney
					}).show();
				}
			}
		},
		//查看
		openliushuiwin1 : function(record,flag) {
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				var hidden=false;
				var flag=1;
				var	record=s[0];
				new punishDetailView({
					punishInterestId : record.data.punishInterestId,
					fundType : record.data.fundType,
					flag : flag,
					hidden1 : false,
					hidden2 :false,
					businessType: record.data.businessType
				}).show();
			}	
		}
	});