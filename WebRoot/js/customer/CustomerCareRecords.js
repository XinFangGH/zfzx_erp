
CustomerCareRecords = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof (_cfg.investPersonId) != "undefined") {
			this.investPersonId = _cfg.investPersonId;
		}
		if(typeof (_cfg.isHiddenEdit)!= "undefined"){
			this.isHiddenEdit = _cfg.isHiddenEdit;
		}else{
			this.isHiddenEdit = false;
		}
//		alert(this.isHiddenEdit);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		
		CustomerCareRecords.superclass.constructor.call(this, {
			items : [this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			
			items : [{
					iconCls : 'btn-add',
					text : '编辑',
					xtype : 'button',
					scope : this,
					handler : this.editRs
				},new Ext.Toolbar.Separator({
				    hidden:this.isHidden
			})]
		});
		
		this.gridPanel = new HT.GridPanel( {
			id : 'CustomerCareRecords',
			isShowTbar : false,
			showPaging : false,
			autoHeight : true,
			region : 'center',
			tbar : this.isHiddenEdit==true?null:this.topbar,
			url : __ctxPath + "/customer/getListInvestPersonCare.do?id="+this.peid+"&isEnterprise="+this.isEnterprise,
			fields : [ {
				name : 'id',
				type : 'Long'
			}, 'careWay', 'careTitle', 'careContent', 'careDate','careMarks','careManValue'],
			columns : [ 
				{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '关怀方式',
				dataIndex : 'careWay',
				renderer : function(value){
					if(value=='864'){
						return '短信';
					}else if(value=='865'){
						return '邮件';
					}else if(value=='866'){
						return '电话';
					}else if(value=='867'){
						return '纸质邮件';
					}else if(value=='868'){
						return '线下往来';
					}
				}
			}, {
				header : '关怀标题',
				dataIndex : 'careTitle'
			}, {
				header : '关怀内容',
				dataIndex : 'careContent'
			}, {
				header : '关怀时间',
				dataIndex : 'careDate'
			}, {
				header : '关怀人',
				dataIndex : 'careManValue'
			}, {
				header : '备注',
				dataIndex : 'careMarks'
			}]
		//end of columns
				});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	editRs : function(record) {
		        var s = this.gridPanel.getSelectionModel().getSelections();
		        var gridPanel=this.gridPanel;
		          if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var record=s[0]
					
					InvestPersonCare = record.data;
				 	new CareEditForm({//CareEditForm   InvestPersonCareForm
		            	peId : this.peid,
		            	isEnterprise:this.isEnterprise,
		            	InvestPersonCare : InvestPersonCare,
//		            	isHidden :true,
						isRead : true,
						isLook : true
					}).show();
				}
		
	}

});
