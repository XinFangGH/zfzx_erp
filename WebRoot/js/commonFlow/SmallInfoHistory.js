/****
 * 信贷流程项目基本信息
 * @author zcb
 * @class ExtUD.Ext.PerCreditLoanProjectInfoPanel
 * @extends Ext.Panel
 */
SmallInfoHistory = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.applyIf(this, _cfg);
		var leftlabel = 85;
		SmallInfoHistory.superclass.constructor.call(this, {
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					//columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
						xtype : 'hidden',
						name : 'slSmallloanProject.projectId'
					},{
						columnWidth : .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						items : [{
							fieldLabel : "项目名称",
							xtype : "textfield",
							readOnly : true,
							name : "slSmallloanProject.projectName",
							blankText : "项目名称不能为空，请正确填写!",
							anchor : "100%"// 控制文本框的长度
						}]
					},{
						columnWidth :  .3, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 80,
						labelAlign : 'right',
						items : [{
							fieldLabel : "项目编号",
							xtype : "textfield",
							name : "slSmallloanProject.projectNumber",
							readOnly : true,
							blankText : "项目编号不能为空，请正确填写!",
							anchor : "100%"
						}]
					},{
						columnWidth:.4,
						layout:'form',
						labelWidth:100,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						items:[{
							labelWidth : 85,
							xtype : "combo",
							mode : 'local',
							anchor:'100%',
							disabled:this.product==null?false:!this.product,
							//allowBlank:false,
							readOnly:this.readOnly,
							triggerAction : 'all',
							hiddenName : "slSmallloanProject.productId",
							displayField:'productName',
							valueField:'id',
							fieldLabel : "产品类型",
							editable : false,
							store:new Ext.data.JsonStore( {
								url : __ctxPath+ "/system/listBpProductParameter.do",
								totalProperty : 'totalCounts',
								autoLoad:true,
								root : 'result',
								fields : [{
									name : 'id'
								},{
									name : 'productName'
								}]
							}),
							listeners:{
								scope:this,
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										combox.setValue(combox.getValue());
										combox.clearInvalid();
									})
								}/*,
								select:function(combox,recode,index){
									 
								}*/
							}
						}]
					},{
						columnWidth:.3,
						layout:'form',
						labelWidth:100,
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						disabled:this.product==null?false:!this.product,
						items:[{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							hiddenName : "slSmallloanProject.appUserName",
							editable : false,
							fieldLabel : "项目经理",
							labelWidth : 85,
							blankText : "项目经理不能为空，请正确填写!",
							allowBlank : true,
							anchor : "100%",
							readOnly:this.readOnly,
							onTriggerClick : function(cc) {
								var obj = this;
								var appuerIdObj = obj.nextSibling();
								var userIds = appuerIdObj.getValue();
								if ("" == obj.getValue()) {
									userIds = "";
								}
								new UserDialog({
									userIds : userIds,
									userName : obj.getValue(),
									single : false,
									title : "选择项目经理",
									callback : function(uId, uname) {
										obj.setValue(uname);
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
							}
						}, {
							xtype : "hidden",
							disable:true,
							name : 'slSmallloanProject.appUserId',
							value : ""
						}]
					},{
						columnWidth:.3,
						layout:'form',
						labelWidth:100,
						
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						items:[{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							hiddenName : "slSmallloanProject.teamManagerName",
							editable : false,
							fieldLabel : "团队经理",
							labelWidth : 85,
							blankText : "团队经理不能为空，请正确填写!",
							allowBlank : true,
							readOnly:this.readOnly,
							anchor : "100%",
							onTriggerClick : function(cc) {
								var obj = this;
								var appuerIdObj = obj.nextSibling();
								var userIds = appuerIdObj.getValue();
								if ("" == obj.getValue()) {
									userIds = "";
								}
								new UserDialog({
									userIds : userIds,
									userName : obj.getValue(),
									single : false,
									title : "选择项目经理",
									callback : function(uId, uname) {
										obj.setValue(uname);
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
							}
						}, {
							xtype : "hidden",
							disable:true,
							name : 'slSmallloanProject.teamManagerId',
							value : ""
						}]
					},{
						columnWidth:.4,
						layout:'form',
						labelWidth:100,
						
						hidden:this.product==null?false:!this.product,
						labelAlign : 'right',
						items:[{
							name:'testRadioGroup', 
							xtype: 'radiogroup',
							allowBlank :false,
							fieldLabel: '项目状态',
							labelWidth: 5,   
						    items:[  
						        {boxLabel: '放款后项目', name: 'slSmallloanProject.projectStatus', inputValue: "1"},  
						        {boxLabel: '已完成项目', name: 'slSmallloanProject.projectStatus', inputValue: "2"}   
								]
						}]
					}]
			}]
		});
	}
});