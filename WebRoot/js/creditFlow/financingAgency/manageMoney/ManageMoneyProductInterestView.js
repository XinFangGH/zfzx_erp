/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ManageMoneyProductInterestView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ManageMoneyProductInterestView.superclass.constructor.call(this, {
			
			items : [{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【投资人放款收息表】:</font></B>',hidden:this.isFlow},this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var data=[['利息','loanInterest'],['本金','principalRepayment'],['提前赎回违约金','liquidatedDamages']];
		this.datafield = new Ext.form.DateField({
					format : 'Y-m-d',
					allowBlank : false,
					readOnly : this.isHidden
				});
			this.topbar = new Ext.Toolbar({
					items : [ {
								iconCls : 'btn-add',
								text : '增加',
								xtype : 'button',
								scope : this,
								hidden : this.isHiddenAddBtn,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
								hidden : this.isHiddenAddBtn
							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								hidden : this.isHiddenDelBtn,
								handler : this.removeSelRs
							}, new Ext.Toolbar.Separator({
								hidden : this.isHiddenDelBtn
							}),{
								iconCls : 'btn-add',
								text : '生成',
								xtype : 'button',
								scope : this,
								hidden : this.isHiddenautocreateBtn,
								handler : this.autocreate
							}]
				});
				var url=__ctxPath+ "/creditFlow/financingAgency/listByEarlyRedemptionIdPlMmOrderAssignInterest.do?orderId="+this.orderId
				if(typeof(this.earlyRedemptionId)!='undefined' && null!=this.earlyRedemptionId){
					url= __ctxPath+ "/creditFlow/financingAgency/listByEarlyRedemptionIdPlMmOrderAssignInterest.do?earlyRedemptionId="+this.earlyRedemptionId
				}
			this.gridPanel = new HT.EditorGridPanel({
			region : 'center',
			tbar : this.isHidden==true?null:this.topbar,
			showPaging : false,
			autoHeight : true,
			clicksToEdit :1,
			url :url,
			fields : [{
						name : 'assignInterestId',
						type : 'int'
					}, 'orderId', 'investPersonId', 'investPersonName',
					'mmplanId', 'mmName', 'fundType', 'incomeMoney','afterMoney','factDate','payMoney',
					'intentDate','periods','earlyRedemptionId','isValid','isCheck'],
			baseParams :{flag:1},
			columns : [{
						header : 'assignInterestId',
						dataIndex : 'assignInterestId',
						hidden : true
					}, {
						header : '期数',
						dataIndex : 'periods',
						renderer:function(v){
							if(v==null){return "";}
							else{
							return "第"+v+"期"
							}
						}
					}, {
						header : '资金类型',
						dataIndex : 'fundType',
						editor : {
							xtype : 'combo',
							mode : 'local',
							forceSelection : true, 
							displayField : 'typeValue',
							valueField : 'typeId',
							editable : false,
							triggerAction : 'all',
							readOnly : this.isHidden,
							store : new Ext.data.SimpleStore({
								data : data,
								fields:['typeValue','typeId']
							})
						},
						renderer : function(value){
							if(value=='loanInterest'){
								return '利息'
							}else if(value=='principalRepayment'){
								return '本金'
							}else if(value=='liquidatedDamages'){
								return '提前赎回违约金'
							}else{
								return '';
							}
						}
						
					}, {
						header : '计划到帐日',
						dataIndex : 'intentDate',
						editor : this.datafield,
						renderer : ZW.ux.dateRenderer(this.datafield)
					}, {
						header : '投资客户计划收入',
						dataIndex : 'incomeMoney',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							readOnly : this.isHidden
						},
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '投资客户计划支出',
						dataIndex : 'payMoney',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							readOnly : this.isHidden
						},
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}]
				// end of columns
		});
	},// end of the initComponents()
	createRs : function(){
		var newRecord = this.gridPanel.getStore().recordType;
		var newData = new newRecord({
					assignInterestId : null,
					fundType : '',
					payMoney : 0,
					incomeMoney : 0,
					intentDate : new Date()
				});
		var combox = new Ext.form.ComboBox({
			triggerAction : 'all',
			store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath
								+ '/creditFlow/financingAgency/getPayIntentPeriodPlManageMoneyPlanBuyinfo.do',
						fields : ['itemId', 'itemName'],
						baseParams : {
							id : this.orderId
						}
					}),
			valueField : 'itemId',
			displayField : 'itemName'

		})
		this.gridPanel.getColumnModel().setEditor(3, combox);
		this.gridPanel.stopEditing();
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),
				newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore()
				.getCount() - 1));
		this.gridPanel.startEditing(0, 0);
	},
	autocreate:function(){
		var earlyDate=this.object.getCmpByName('plEarlyRedemption.earlyDate').getValue();
		var earlyMoney=this.object.getCmpByName('earlyMoney1').hiddenField.value;
		var penaltyDays=this.object.getCmpByName('plEarlyRedemption.penaltyDays').getValue();
		var liquidatedDamagesRate=this.object.getCmpByName('plEarlyRedemption.liquidatedDamagesRate').getValue();
		var message="";
		if(null==earlyDate || earlyDate==''){
			message="提前赎回日期"
		}
		if(null==earlyMoney || earlyMoney==''){
			message="提前赎回金额"
		}
		if((null==penaltyDays || penaltyDays=='') && penaltyDays!='0'){
			message="罚息天数"
		}
		if((null==liquidatedDamagesRate || liquidatedDamagesRate=='') && liquidatedDamagesRate!='0'){
			message="提前赎回违约金比例"
		}
		if (message != "") {
			Ext.MessageBox.show({
						title : '操作信息',
						msg : message + '不能为空',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
			return null;
		}
		var params1={
			'plEarlyRedemption.earlyMoney':earlyMoney,
			'plEarlyRedemption.penaltyDays':penaltyDays,
			'plEarlyRedemption.earlyDate':earlyDate,
			'plEarlyRedemption.liquidatedDamagesRate':liquidatedDamagesRate,
			'orderId':this.orderId,
			'flag' : 0
		}
		
		
		var combox = new Ext.form.ComboBox({
			triggerAction : 'all',
			store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath
								+ '/creditFlow/financingAgency/getPayIntentPeriodPlManageMoneyPlanBuyinfo.do',
						fields : ['itemId', 'itemName'],
						baseParams : {
							id : this.orderId
						}
					}),
			valueField : 'itemId',
			displayField : 'itemName'

		})
		this.gridPanel.getColumnModel().setEditor(3, combox);
		
		var gridstore = this.gridPanel.getStore();
		gridstore.on('beforeload', function(gridstore, o) {
			
			Ext.apply(o.params, params1);
		});
		gridstore.reload();
	},
	removeSelRs : function(){
		var griddel = this.gridPanel;
		var storedel = griddel.getStore();
		var s = griddel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}
		Ext.Msg.confirm("提示!",'确定要删除吗？',
			function(btn) {

				if (btn == "yes") {
					griddel.stopEditing();
					 var ids = Array();
					for ( var i = 0; i < s.length; i++) {
						var row = s[i];
						if(row.data.afterMoney!=null && row.data.afterMoney!=0){
							Ext.ux.Toast.msg('操作信息', '该条款项已对账,不能删除!!');
							return;
						}
						if (row.data.assignInterestId == null || row.data.assignInterestId == '') {
							storedel.remove(row);
							griddel.getView().refresh();
						} else {
							ids.push(row.data.assignInterestId)
						}
						storedel.remove(row);
						griddel.getView().refresh();
					}
					Ext.Ajax.request( {
						url : __ctxPath + '/creditFlow/financingAgency/multiDelPlEarlyRedemption.do',
						method : 'POST',
						success : function(response) {
							Ext.ux.Toast.msg('操作信息', '删除成功!');
								
						},
						params : {ids:ids}
					});
				}
			})
		
	},
	getGridData : function(){
		var vRecords = this.gridPanel.getStore().getRange(0, this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for ( var i = 0; i < vCount; i++) {
				if(vRecords[i].data.afterMoney==null || vRecords[i].data.afterMoney==0){
					var st={
						fundType:vRecords[i].data.fundType,
						incomeMoney:vRecords[i].data.incomeMoney,
						intentDate:vRecords[i].data.intentDate,
						payMoney:vRecords[i].data.payMoney,
						periods:vRecords[i].data.periods,
						orderId:this.orderId,
						earlyRedemptionId:this.earlyRedemptionId
					}
					var str = Ext.util.JSON.encode(st);
					vDatas += str + '@';
				}
			}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
	savereload : function(){
		var params1={
			flag : 1
		}
		var gridstore = this.gridPanel.getStore();
		gridstore.on('beforeload', function(gridstore, o) {
			
			Ext.apply(o.params, params1);
		});
		gridstore.reload();
	}
});
