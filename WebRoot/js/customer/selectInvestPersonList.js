/**
 * @author
 * @class InvestPersonView
 * @extends Ext.Panel
 * @description [InvestPerson]管理
 * @company 智维软件
 * @createtime:
 */
selectInvestPersonList = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (_cfg.formPanel != null && _cfg.formPanel != 'underfined') {
			this.formPanel = _cfg.formPanel;
		}
		/*
		 * if(_cfg.getPerson!=null && _cfg.getPerson!='undefined'){
		 * this.getPerson = _cfg.getPerson; }
		 */
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		selectInvestPersonList.superclass.constructor.call(this, {
					id : 'InvestPersonView',
					title : '选择个人客户',
					region : 'center',
					layout : 'border',
					width : 600,
					height : 400,
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							autoScroll : true,
							region : 'north',
							
							border : false,
							height : 50,
							anchor : '80%',
							layoutConfig: {
				               align:'middle'
				            },
				             bodyStyle : 'padding:10px 10px 10px 10px',
							items:[{
					     		columnWidth :.4,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'投资人姓名',
											name : 'Q_investName_S_EQ',
											flex:1,
											anchor : "100%",
											xtype : 'textfield'
											}]
					     	},{
		     			columnWidth :.2,
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
		     			columnWidth :.2,
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
		// 初始化搜索条件Panel
		var fp = this.formPanel;
		var object = this;
		var rightValue =  isGranted('_investmentPerson_see_All');
		var isShop = isGranted('_investmentPerson_see_shop');
		var url="";
		if(this.type=='buyMmproduce'){
			//带数据分离的URL
			url=__ctxPath
					+ "/creditFlow/creditAssignment/customer/listDownCsInvestmentperson.do?isAll="+rightValue+"&isShop="+isShop
		}else{
			//原来的URL
			url=__ctxPath
					+ "/creditFlow/creditAssignment/customer/listCsInvestmentperson.do"
		}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			// 使用RowActions
			id : 'InvestPersonGrid',
			url : url,
			fields : [{
						name : 'investId',
						type : 'int'
					}, 'cardnumber', 'cardtype', 'investName', 'sex',
					'cellphone','totalMoney'],
			columns : [{
						header : 'investId',
						dataIndex : 'investId',
						hidden : true
					}, {
						header : '姓名',
						dataIndex : 'investName'
					}, {
						header : '性别',
						dataIndex : 'sex',
						renderer:function(value){
							if(value=="313"){
								return "女";
							}else if(value=="312"){
								return "男";
							}
						}
					}, {
						header : '证件类型',
						dataIndex : 'cardtype',
						renderer:function(value){
							if(value=="309"){
								return "身份证";
							}else if(value=="310"){
								return "军官证";
							}else if(value=="311"){
								return "护照";
							}else if(value=="834"){
								return "临时身份证";
							}else if(value=="835"){
								return "港澳台通行证";
							}else if(value=="836"){
								return "其他";
							}
						}
					}, {
						header : '证件号码',
						dataIndex : 'cardnumber'
					}, {
						header : '手机号码',
						dataIndex : 'cellphone'
					}, {
						header : '可用余额',
						align : 'right',
						dataIndex : 'totalMoney',
						renderer : function(v) {
						   if(!Ext.isEmpty(v)){
						      return Ext.util.Format.number(v, ',000,000,000.00')
									+ "元";
						   }else{
						   	  return v
						   }
						
						}
					}],
					
			listeners : {
				'rowdblclick' : function(grid, rowIndex, e) {
					var object = grid.ownerCt;
					var data = grid.getStore().getAt(rowIndex).data;
					grid.getSelectionModel().each(function(rec) {
						fp.getCmpByName("plManageMoneyPlanBuyinfo.investPersonName").setValue(rec.data.investName);
						fp.getCmpByName("plManageMoneyPlanBuyinfo.investPersonId").setValue(rec.data.investId);
					})
					object.close();

				}
			}
		});

	},// end of the initComponents()
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		var object = grid.ownerCt;
		grid.getSelectionModel().each(function(rec) {
			/*
			 * var perId =
			 * fp.getCmpByName('plManageMoneyPlanBuyinfo.investPersonId');
			 * perId.setValue(rec.data.investId); var perName =
			 * Ext.getCmp('plManageMoneyPlanBuyinfo.investPersonName');
			 * perName.setValue(rec.data.investName);
			 */

			object.backcall(rec.data.investId, rec.data.investName);
				//

			})
		object.close();

	},//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			} /*
		 * function(grid, rowindex, e) {
		 * grid.getSelectionModel().each(function(rec) { return rec.data;
		 * alert(rec.data.perId); }); }
		 */
});
