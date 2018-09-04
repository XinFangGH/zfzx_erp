/**
 * @author zhangyl
 * @createtime
 * @class SlProcreditAssuretenetForm
 * @extends Ext.form.FieldSet
 * @description 贷款类型归类
 * @company 北京智维软件有限公司
 */
 StatisticalElementsSmallloanWin = Ext.extend(Ext.Window,{
 	constructor:function(_cfg){
 		Ext.applyIf(this,_cfg);
 		this.initUIComponent();
 		winP.superclass.constructor.call(this,{
				title:'贷款属性分类',
				layout:'anchor',
				width:'1000',
				height:'300',
				iconCls : '',
				autoScroll : true,
				maximizable : false,
				items:[this.panel],
				modal:true,
				buttonAlign :'center',
				buttons :[{
						text : '保存',
						iconCls : 'btn-save',
						scope : this,
						hidden:this.isReadOnly,
						handler : this.save
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						scope : this,
						handler : function(){
							this.close();
						}
					}]
			})
 	},
 	initUIComponent:function(){
 		var data = [['新业务',0],['旧业务',1]];
 		this.panel = new Ext.form.FormPanel({
 			items : [{
				frame:true,
				layout : 'column',
				items : [{
					columnWidth : .25,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "地区分布",
						emptyText : "请选择",
						nodeKey : 'smallloan_distributionArea',
						allowBlank : false,
						editable : false,
						hiddenName : 'projectPropertyClassification.areaId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isReadOnly,
						itemName : '地区分布',
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}
						 }
					},{
						xtype : 'combo',
						triggerClass : 'x-form-search-trigger',
						hiddenName : "projectPropertyClassification.loansAt",
						editable : false,
						fieldLabel : "贷款投向",
						blankText : "贷款投向不能为空，请正确填写!",
						allowBlank : false,
						readOnly : this.isReadOnly,
						anchor : "100%",
						scope : this,
						onTriggerClick : function(){ 
							var obj=this;
							var nextObj=obj.nextSibling()
							var selectLoansAt = function(objArray){
								nextObj.setValue(objArray[0].id) ;
								obj.setValue(objArray[0].attributes.text);
							};
						   selectDictionary('slSmallloanProject_loansAt',selectLoansAt);
						}
					},{
						xtype : 'hidden',
						name:'projectPropertyClassification.loansAtId'
					}]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 100,
					labelAlign : 'right',
					items : [{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "保证分布",
						emptyText : "请选择",
						nodeKey : 'smallloan_distributionGuarantee',
						allowBlank : false,
						editable : false,

						hiddenName : 'projectPropertyClassification.guarId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isReadOnly,
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}
						 }
					},{
						xtype : 'combo',
//						deferredrender :false, 
						triggerClass : 'x-form-search-trigger',
						hiddenName : 'projectPropertyClassification.loanIndustry',
						editable : false,
						fieldLabel : "贷款行业分类",//loanIndustry
						blankText : "贷款投向不能为空，请正确填写!",
						allowBlank : false,
						editable : false,
						readOnly : this.isReadOnly,
//						name : 'slSmallloanProject.distributionArea', 
						scope : this,
						displayField : 'itemName',
						itemName : '贷款行业分类',
						anchor : "100%",
						onTriggerClick : function(){ 
							var obj=this;
							var nextObj=obj.nextSibling()
							var selectloanIndustry = function(objArray){
								nextObj.setValue(objArray[0].id) ;
								obj.setValue(objArray[0].attributes.text);
							};
							
						   selectDictionary('hangye',selectloanIndustry);
						}
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.induId'
					}]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 120,
					labelAlign : 'right',

					items : [{
						xtype : "combo",
						labelWidth : 130,
						fieldLabel : "业务类别",
							mode : 'local',
							forceSelection : true, 
						nodeKey : 'smallloan_yeWuType',
						allowBlank : false,
						editable : false,
						triggerAction : 'all',
						hiddenName : 'projectPropertyClassification.yeWuType',
						scope : this,
						displayField : 'yeWuType',
						valueField : 'yeWuTypeValue',
						readOnly : this.isReadOnly,
						anchor : "100%",
						store : new Ext.data.SimpleStore({
							data : data,
							fields:['yeWuType','yeWuTypeValue']
						})
					},{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "企业组织产业分类",
						nodeKey : 'smallloan_enterpriseIndustryClassified',
						editable : false,
						hiddenName : 'projectPropertyClassification.enterpriseIndustryClassified',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isReadOnly,
						itemName : '企业组织产业分类',
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}
						 	
						 }
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.id'
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.projectId',
						value : this.projectId
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.businessType',
						value : this.businessType
					}]
				},{
					columnWidth:.25,
					layout:'form',
					labelWidth : 120,
					labelAlign : 'right',
					items:[{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "贷款对象规模",
						nodeKey : 'CustScale',
						editable : false,
						hiddenName : 'projectPropertyClassification.custScaleId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isReadOnly,
						itemName : '贷款对象规模',
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}	 	
						 }
					},{		
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "扣款方式",
						nodeKey : 'RepaymentWay',
						editable : false,
						hiddenName : 'projectPropertyClassification.repaymentWayId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isReadOnly,
						itemName : '扣款方式',
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}		 	
						 }
					}]
				}]
			}]
 		});
 		if(this.projectId!=null&&this.projectId!=""){
 			this.panel.loadData({
 				url: __ctxPath +"/project/getClassificationProjectPropertyClassification.do?businessType='SmallLoan'&projectId="+this.projectId,
 				method:'POST',
 				root:'data',
 				preName:'projectPropertyClassification'
 			});
 			
 		}
 	},
 	save:function(){
 		this.panel.getForm().submit({
 			url: __ctxPath +'/project/saveProjectPropertyClassification.do',
			success:function(form,action){
				Ext.ux.Toast.msg('状态','保存成功');
			},
			failure:function(form,action){
				Ext.ux.Toast.msg('状态','保存失败');
			}
 		});
 	}
 	
 	
 });
 
StatisticalElementsSmallloan = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	bodyStyle : 'padding-right:18px;',
	constructor : function(_cfg) {
		var data = [['新业务',0],['旧业务',1]];
		Ext.applyIf(this, _cfg);
		this.initComponents();
		StatisticalElementsSmallloan.superclass.constructor.call(this, {
			id:'statisticalElementsSmallloan',
			items : [{
				layout : 'column',
				items : [{
					columnWidth : .25,
					layout : 'form',
					labelWidth :100,
					labelAlign : 'right',
					items : [{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "地区分布",
						emptyText : "请选择",
						nodeKey : 'smallloan_distributionArea',
						allowBlank : false,
						editable : false,
						hiddenName : 'projectPropertyClassification.areaId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						itemName : '地区分布',
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}
						 }
					},{
						xtype : 'combo',
						triggerClass : 'x-form-search-trigger',
						hiddenName : "projectPropertyClassification.loansAt",
						editable : false,
						fieldLabel : "贷款投向",
						blankText : "贷款投向不能为空，请正确填写!",
						allowBlank : false,
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						scope : this,
						onTriggerClick : function(){ 
							var obj=this;
							var nextObj=obj.nextSibling()
							var selectLoansAt = function(objArray){
								nextObj.setValue(objArray[0].id) ;
								obj.setValue(objArray[0].attributes.text);
							};
						   selectDictionary('slSmallloanProject_loansAt',selectLoansAt);
						}
					},{
						xtype : 'hidden',
						name:'projectPropertyClassification.loansAtId'
					}]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 100,
					labelAlign : 'right',
					items : [{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "保证分布",
						emptyText : "请选择",
						nodeKey : 'smallloan_distributionGuarantee',
						allowBlank : false,
						editable : false,

						hiddenName : 'projectPropertyClassification.guarId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}
						 }
					},{
						xtype : 'combo',
//						deferredrender :false, 
						triggerClass : 'x-form-search-trigger',
						hiddenName : 'projectPropertyClassification.loanIndustry',
						editable : false,
						fieldLabel : "贷款行业分类",//loanIndustry
						blankText : "贷款投向不能为空，请正确填写!",
						allowBlank : false,
						editable : false,
						readOnly : this.isAllReadOnly,
//						name : 'slSmallloanProject.distributionArea', 
						scope : this,
						displayField : 'itemName',
						itemName : '贷款行业分类',
						anchor : "100%",
						onTriggerClick : function(){ 
							var obj=this;
							var nextObj=obj.nextSibling()
							var selectloanIndustry = function(objArray){
								nextObj.setValue(objArray[0].id) ;
								obj.setValue(objArray[0].attributes.text);
							};
							
						   selectDictionary('hangye',selectloanIndustry);
						}
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.induId'
					}]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 120,
					labelAlign : 'right',

					items : [{
						xtype : "combo",
						labelWidth : 130,
						fieldLabel : "业务类别",
							mode : 'local',
							forceSelection : true, 
						nodeKey : 'smallloan_yeWuType',
						allowBlank : false,
						editable : false,
						triggerAction : 'all',
						hiddenName : 'projectPropertyClassification.yeWuType',
						scope : this,
						displayField : 'yeWuType',
						valueField : 'yeWuTypeValue',
						readOnly : this.isAllReadOnly,
						anchor : "100%",
						store : new Ext.data.SimpleStore({
							data : data,
							fields:['yeWuType','yeWuTypeValue']
						})
					},{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "企业组织产业分类",
						nodeKey : 'smallloan_enterpriseIndustryClassified',
						editable : false,
						hiddenName : 'projectPropertyClassification.enterpriseIndustryClassified',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						itemName : '企业组织产业分类',
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}
						 	
						 }
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.id'
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.projectId',
						value : this.projectId
					},{
						xtype : 'hidden',
						name : 'projectPropertyClassification.businessType',
						value : this.businessType
					}]
				},{
					columnWidth:.25,
					layout:'form',
					labelWidth : 120,
					labelAlign : 'right',
					items:[{
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "贷款对象规模",
						nodeKey : 'CustScale',
						editable : false,
						hiddenName : 'projectPropertyClassification.custScaleId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						itemName : '贷款对象规模',
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}	 	
						 }
					},{		
						xtype : "dickeycombo",
						labelWidth : 130,
						fieldLabel : "扣款方式",
						nodeKey : 'RepaymentWay',
						editable : false,
						hiddenName : 'projectPropertyClassification.repaymentWayId',
						scope : this,
						displayField : 'itemName',
						readOnly : this.isAllReadOnly,
						itemName : '扣款方式',
						anchor : "100%",
						listeners : { 
							afterrender : function(combox) {
							var st =combox.getStore();
							st.on("load", function() {
						 		combox.setValue(combox.getValue());
						 		combox.clearInvalid(); 
						 		})
						 	}		 	
						 }
					}]
				}]
			}]
		});
		if(typeof(this.projectId)!="undefined" && this.projectId!=null){
 			this.loadData({
 				url: __ctxPath +"/project/getClassificationProjectPropertyClassification.do?businessType="+this.businessType+"&projectId="+this.projectId,
 				method:'POST',
 				root:'data',
 				preName:'projectPropertyClassification'
 			});
 			
 		}
	},
	initComponents : function() {
	
	}
})