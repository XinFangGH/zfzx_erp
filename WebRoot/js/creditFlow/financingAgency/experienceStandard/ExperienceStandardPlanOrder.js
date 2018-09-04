/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
ExperienceStandardPlanOrder = Ext.extend(Ext.Panel, {
	isAutocreate:false,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof(_cfg.isAutocreate) != "undefined") {
			this.isAutocreate = _cfg.isAutocreate;
		}
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ExperienceStandardPlanOrder.superclass.constructor.call(this, {
					id : 'ExperienceStandardPlanOrder' + this.keystr,
					title : '投资人列表',
					layout : 'anchor',
					anchor : '100%',
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel

		this.topbar = new Ext.Toolbar({
		items : [{
					iconCls : 'btn-add',
					text : '生成',
					xtype : 'button',
					scope : this,
					handler : this.autocreate
				}]
		})
		/*var infostate="";
		if(this.state==1 || this.state==2){//投标中，已满标
			infostate='1'
		}else if(this.state==-2){//已流标
			infostate='-2'
		}else if(this.state==-1){//已关闭
			infostate='3'
		}else if(this.state==7){//已起息
			infostate='2,7,8,10'
		}else if(this.state==10){//已完成
		    infostate='10'
		}*/
		var infostate="1,2,3,4,7,8,10";
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.isAutocreate==true?this.topbar:null,
			id : 'ExperienceStandardPlanOrderGrid' + this.keystr,
			height : 180,
			showPaging:false,
			url : __ctxPath
					+ "/creditFlow/financingAgency/listByMmplanIdPlManageMoneyPlanBuyinfo.do?mmplanId="+this.mmplanId+"&state="+infostate,
			fields : [{
						name : 'orderId',
						type : 'int'
					},  'investPersonId','investPersonName','couponsMoney',
					'investmentProportion','buyDatetime','incomeMoney',
					'intentDate','factDate'],
			columns :[{
						header : '投资人',
						dataIndex : 'investPersonName'
					},{
						header : '优惠券金额',
						dataIndex : 'couponsMoney',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')+ "元";
						}
					},{
						header : '投资比例',
						dataIndex : 'investmentProportion',
						renderer : function(v) {
							return v+"%";
						}
					},{
						header : '购买时间',
						dataIndex : 'buyDatetime',
						renderer : function(v) {
							return v;
						}
					},{
						header : '派息金额',
						dataIndex : 'incomeMoney',
						sortable : true,
						width : 100,
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00')+ "元";
						}
					},{
						header : '计划派息日',
						dataIndex : 'intentDate',
						sortable : true,
						width : 100
					},{
						header : '实际派息日',
						dataIndex : 'factDate',
						sortable : true,
						width : 100
					}]
				// end of columns
		});
		
	},
	
			autocreate : function() {
				var startinInterestTime=null;
				var endinInterestTime=null;
				var mmplanId=null;
				var gridPanel=this.gridPanel;
				var message=""
			    startinInterestTime=this.getOriginalContainer().getCmpByName('experienceFormPanel').getCmpByName("plManageMoneyPlan.startinInterestTime").getValue();
			    endinInterestTime=this.getOriginalContainer().getCmpByName('experienceFormPanel').getCmpByName("plManageMoneyPlan.endinInterestTime").getValue();
			    mmplanId=this.mmplanId;
			    if (startinInterestTime == "" || startinInterestTime == null) {
				 	message = "起息日期";
			    }
				if(message != "") {
					Ext.MessageBox.show({
						title : '操作信息',
						msg : message + '不能为空',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					return null;
				}
				var params={
				    'startinInterestTime': startinInterestTime,
				    'endinInterestTime': endinInterestTime,
				    'mmplanId': mmplanId
				}
				var pbar = Ext.MessageBox.wait('款项生成中，可能需要较长时间，请耐心等待...','请等待',{
					interval:200,
				    increment:15
				});
				
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/createAssigninterestPlManageMoneyPlan.do",
					method : 'POST',
					scope:this,
					timeout: 600000,
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('操作信息', '生成成功!');
						pbar.getDialog().close();
						var gridstore = gridPanel.getStore();
						gridPanel.getStore().reload();
					},
					failure : function(response,request) {
						pbar.getDialog().close();
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					params : params
			    });
	        }

	
});

