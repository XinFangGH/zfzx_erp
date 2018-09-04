/**
 * @author lisl
 * @class SlSupervisonRecordView
 * @description 监管记录
 * @extends Ext.Window
 */
SlSupervisonRecordView = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlSupervisonRecordView.superclass.constructor.call(this, {
			title : '监管记录',
			width : 695,
			height : 220,
			modal : true,
			border : false,
			iconCls : '',
			autoScroll : true,
			layout : 'fit',
			items : [this.panel],
			scope : this
		});
	},
	initUIComponents : function() {
		this.panel = new Ext.Panel({
			frame : true,
			columnWidth : 1,
			layout : 'column',
			defaults : {
				anchor : '100%',
				columnWidth : .1,
				labelAlign : "right"
			},
			items : [{
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .5,
					items : [{
						fieldLabel : '监管人',
						name : 'slSupervisemanage.superviseManagerName',
						xtype : 'textfield',
						anchor : '100%',
						readOnly : true
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .5,
					items : [{
						fieldLabel : '监管时间',
						name : 'slSupervisemanage.superviseManageTime',
						xtype : 'datefield',
						format : 'Y-m-d',
						allowBlank : false,
						anchor : '100%',
						readOnly : true
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : .5,
					items : [{
						xtype : "diccombo",
						fieldLabel : '监管方式',
						hiddenName : 'slSupervisemanage.superviseManageMode',
						itemName : '监管方式', // xx代表分类名称
						isDisplayItemName : false,
						allowBlank : false,
						editable : false,
						emptyText : "请选择",
						anchor : '100%',
						readOnly : true,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.clearInvalid();
									if (combox.getValue() > 0) {
										combox.setValue(combox.getValue());
									}
								})
							}
						}
					}]
				}, {
					layout : "form", // 从上往下的布局
					columnWidth : .5,
					items : [{
						xtype : "diccombo",
						fieldLabel : '监管意见',
						hiddenName : 'slSupervisemanage.superviseManageOpinion',
						itemName : '监管意见', // xx代表分类名称
						isDisplayItemName : false,
						allowBlank : false,
						editable : false,
						emptyText : "请选择",
						anchor : '100%',
						readOnly : true,
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.clearInvalid();
									if (combox.getValue() > 0) {
										combox.setValue(combox.getValue());
									}
								})
							}
						}
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : 1,
					items : [{
						fieldLabel : '备注',
						name : 'slSupervisemanage.superviseManageRemark',
						xtype : 'textarea',
						anchor : '100%',
						readOnly : true
					}]
				}]
			}, {
				columnWidth : 1,
				layout : 'column',
				items : [{
					layout : "form", // 从上往下的布局
					columnWidth : 1,
					style : 'padding-left:104px',
					items : [new uploads({
						title_c : '上传监管报告',
						isHidden : true,
						businessType : "SmallLoan",
						typeisfile : 'superviseRecordReport',
//						mark : 'sl_supervise_report_SmallLoan.'
//								+ this.superviseManageId,
//						projectId : this.superviseManageId,
						//☆edit by gao
						tableName : 'sl_supervise_report_SmallLoan.'
								+ this.superviseManageId,
						projectId : this.projectId,
						uploadsSize : 1
					})]	
				}]
			}]
		});
		this.autoLoadData();
	},
	autoLoadData : function(){
		this.panel.loadData({
			root:'data',
			preName:'slSupervisemanage',
			url:__ctxPath+'/supervise/getInfoSlSupervisemanage.do?superviseManageId='+this.superviseManageId, 
			scope:this,
			success:function(resp,options){
				 var result=Ext.decode(resp.responseText);
				 this.panel.getCmpByName('slSupervisemanage.superviseManagerName').setValue(result.data.superviseManagerName);
				 this.panel.getCmpByName('slSupervisemanage.superviseManageTime').setValue(new Date(getDateFromFormat(result.data.superviseManageTime, "yyyy-MM-dd HH:mm:ss")));
				 this.panel.getCmpByName('slSupervisemanage.superviseManageMode').setValue(result.data.superviseManageMode);
				 this.panel.getCmpByName('slSupervisemanage.superviseManageOpinion').setValue(result.data.superviseManageOpinion);
				 this.panel.getCmpByName('slSupervisemanage.superviseManageRemark').setValue(result.data.superviseManageRemark);
			}
		});
	}
});