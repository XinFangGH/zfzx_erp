/**
 * @author lisl
 * @class FinancingProjectInfoNavigation
 * @description 融资项目信息页面导航
 * @extends Ext.Panel
 */
FinancingProjectInfoNavigation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		FinancingProjectInfoNavigation.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			items : [this.panel]
		});
	},
	initUIComponents : function() {
		this.projectId = this.record.data.projectId;
		this.projectStatus = this.record.data.projectStatus;
		this.runId = this.record.data.runId;
		this.panel = new Ext.Panel({
			border : false,
			layout : 'column',
			defaults : {
				anchor : '100%'
			},
			items : [{
				columnWidth : 1,
				layout : 'column',
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:5px",
					columnWidth : .1
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【业务信息】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						text : '01项目基本信息',
						anchor : '100%',
						scope : this,
						handler : function() {
							location.href = "#fl_projectInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '02投资人资料',
						scope : this,
						handler : function() {
							location.href = "#fl_personInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '03收息账号信息',
						scope : this,
						handler : function() {
							location.href = "#fl_accountInfo_" + this.flag
									+ this.projectId;
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '04付款还息表',
						scope : this,
						handler : function() {
							location.href = "#fl_financeInfo_" + this.flag
									+ this.projectId;
						}
					}]
				},{
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						anchor : '100%',
						text : '05提前还款记录',
						scope : this,
						handler : function() {
							location.href = "#fl_earlyrepaymentRecord_" + this.flag
									+ this.projectId;
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				defaults : {
					anchor : '100%',
					style : "margin-left:5px;margin-top:5px",
					columnWidth : .1
				},
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .08,
					items : [{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-left:0px;margin-top:5px',
						width : 95,
						html : '<B><font class="x-myZW-fieldset-title">【流程控制】：</font></B>'
					}]
				}, {
					layout : "form", // 从上往下的布局
					disabled : isGranted('_showFlowImg_f'
								+ this.projectStatus) ? false : true,
					items : [{
						xtype : 'button',
						iconCls : 'btn-detail',
						anchor : '100%',
						text : '流程示意图',
						scope : this,
						handler : function() {
							showFlowImgWin(this.record.data.runId);
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					items : [{
						xtype : 'button',
						iconCls : 'btn-close1',
						anchor : '100%',
						text : '终止项目',
						scope : this,
						hidden : (this.flag == 'edit' && !isGranted('_stopPro_f'
								+ this.projectStatus) == false) ? false : true,
						handler : function() {
							new ProjectStop({
								record : this.record,
								idPrefix : "FinancingProjectInfo_",
								idPrefix_edit : "FinancingProjectInfo_Edit_"
							}).show();
						}
					}]
				}]
			}]
		});
	}
});