/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
PlManageMoneyBuyInfoForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		var leftwidth=80;
		PlManageMoneyBuyInfoForm.superclass.constructor.call(this, {
			layout : 'column',
			items : [ {
						columnWidth : .33,
						labelWidth : leftwidth,
						layout : 'form',
						items : [{
							fieldLabel : '投资人',
							readOnly : this.isAllreadOnly,
							editable : false,
							allowBlank : false,
							// allowBlank : false,
							xtype : 'combo',
							triggerClass : 'x-form-search-trigger',
							blankText : "投资人不能为空，请正确填写!",
							name : 'plManageMoneyPlanBuyinfo.investPersonName',
					//		id : 'plManageMoneyPlanBuyinfo.investPersonName',
							maxLength : 15,
							value : this.pername,
							readOnly : true,
							onTriggerClick : function() {
								var obj=this;
								new selectInvestPersonList({
									formPanel : Ext.getCmp("PlManageMoneyProducebuy"),
										backcall:function(investId,investName){
										  obj.ownerCt.ownerCt.getCmpByName("plManageMoneyPlanBuyinfo.investPersonName").setValue(investName);
										  obj.ownerCt.ownerCt.getCmpByName("plManageMoneyPlanBuyinfo.investPersonId").setValue(investId);
										}
									}).show();
							},
							anchor : '100%'
						}, {
							xtype : "hidden",
							name : 'plManageMoneyPlanBuyinfo.investPersonId'
						//	id : 'plManageMoneyPlanBuyinfo.investPersonId'

						}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : leftwidth,
						items : [{
									fieldLabel : '购买金额',
									name : 'plManageMoneyPlanBuyinfo.buyMoney',
									allowBlank : false,
									readOnly : true,
									fieldClass : 'field-align',
									xtype : 'moneyfield',
									anchor : '100%'
								}]
					}, {
						columnWidth : .03, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 18,
						items : [{
							fieldLabel : "<span style='margin-left:1px'>元</span> ",
							labelSeparator : '',
							anchor : "100%"

						}]
					}, {
						columnWidth : .34,
						layout : 'form',
						labelWidth : leftwidth,
						items : [{
									fieldLabel : '购买时间',
									name : 'plManageMoneyPlanBuyinfo.buyDatetime',
									xtype : 'datefield',
									readOnly : true,
									format : "Y-m-d",
									allowBlank : false,
									anchor : '90%'
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : leftwidth,
						items : [{
							fieldLabel : '计息起日',
							name : 'plManageMoneyPlanBuyinfo.startinInterestTime',
							xtype : 'datefield',
							format : 'Y-m-d',
							allowBlank : false,
							readOnly : true,
							anchor : '100%',
							listeners : {
							scope : this,
							'change' : function(nf){
								var dtstr = Ext.util.Format.date(nf.getValue(), 'Y-m-d');
								var n=parseInt(investlimit);
								 var s=dtstr.split("-");
									   var yy=parseInt(s[0]);
									   var mm=parseInt(s[1])-1;
									   var dd=parseInt(s[2]);
									   var dt=new Date(yy,mm,dd);
									   dt.setMonth(dt.getMonth()+n);
									   if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + n) )
									   	{dt=new Date(dt.getYear(),dt.getMonth(),0);}
								     this.getCmpByName("plManageMoneyPlanBuyinfo.endinInterestTime").setValue(dt);
								     this.getCmpByName("plManageMoneyPlanBuyinfo.orderlimit").setValue(30*n)
							}
							}
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : leftwidth,
						items : [{
							fieldLabel : '计息止日',
							name : 'plManageMoneyPlanBuyinfo.endinInterestTime',
							readOnly:true,
							xtype : 'datefield',
							allowBlank : false,
							format : 'Y-m-d',
							anchor : '91%'
						}]
					}, {
						columnWidth : .3,
						layout : 'form',
						labelWidth : leftwidth,
						items : [{
									fieldLabel : '订单期限',
									name : 'plManageMoneyPlanBuyinfo.orderlimit',
									xtype : 'hidden',
									anchor : '100%'
									
								}]
					},{
						columnWidth : .3,
						layout : 'form',
						items : [{
							xtype : 'button',
							iconCls : 'btn-detail',
							text : '查看投资人资料',
							scope : this,
							handler : function(){
								var investId=this.getCmpByName('plManageMoneyPlanBuyinfo.investPersonId').getValue();
								Ext.Ajax.request({
							url : __ctxPath
									+ '/creditFlow/creditAssignment/customer/seePersonCsInvestmentperson.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								var obj = Ext.util.JSON
										.decode(response.responseText);
								var personData = obj.data;
								var randomId = rand(100000);
								var id = "see_person" + randomId;
								var anchor = '100%';
								var window_see = new Ext.Window({
											title : '查看个人客户详细信息',
											layout : 'fit',
											width : (screen.width - 180) * 0.7
													+ 160,
											maximizable : true,
											height : 460,
											closable : true,
											modal : true,
											plain : true,
											border : false,
											items : [new investmentObj({
														url : null,
														id : id,
														personData : personData,
														isReadOnly : true
													})],
											listeners : {
												'beforeclose' : function(panel) {
													window_see.destroy();
												}
											}
										});
								window_see.show();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : {
								investId : investId
							}
						});
							}
						}]
					}]
		});
	}
	
	
});
