/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
SMSTemplate = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SMSTemplate.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.formPanel,
			height : 350,
			width : 650,
			modal : true,
			resizable:false,
			title : '短信模板信息'
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var comId = this.id;
		this.formPanel = new Ext.FormPanel({
			bodyStyle : 'padding:10px',
			autoScroll : true,
			frame : true,
			defaults : {
				anchor : '95%',
				columnWidth : 1
			},
			items :[{
				columnWidth : 1,
				layout : 'column',
				name:'rootTemplate',
				items :[]
			}]
		});
		for(var i=0;i<SMS_Template.length;i++){
			var rootTemplate=this.formPanel.getCmpByName('rootTemplate');
			rootTemplate.insert(i,{
				columnWidth : 1,
				layout : 'form',
				bodyStyle:'padding-top:5px;',
				items : [{
					xtype:'label',
					html :'<font style="font-size:18px;">模板'+(i+1)+':</font>'
				},{
					xtype : 'panel',
					height : 50,
					labelAlign : 'right',
					html:'<div style="border-style:solid; border-width:1px; border-color:#CEC7C7;padding:10px;">'+SMS_Template[i]['模板'+(i+1)]+'</div>'
				}]
			});
		}
		this.formPanel.doLayout();
	}
});