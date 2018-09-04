

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.SeeDutyperson', {
    extend: 'Ext.Panel',
    
    name: 'SeeDutyperson',

    constructor: function (config) {
    	config = config || {};
    	this.MortgageData=config.MortgageData;
    	this.type=config.type;
    	this.readOnly=config.readOnly;
    //	 this.data=config.data;
    	Ext.apply(config,{
    		style:'background-color:white;',
		    fullscreen: true,
		    title:"保证担保",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items : [{
				xtype : 'fieldset',
				flex : 1,
				title : '保证担保基础信息',
				defaults : {
					xtype : 'textfield',
					readOnly : this.readOnly,
					width : "100%",
					labelAlign : "left"
				},
				items : [{
							xtype : "hiddenfield",
							name : 'vProcreditDictionary.id',
							value : this.MortgageData.vProcreditDictionary.id
						}, {
							label : '项目编号',
							name : 'vProcreditDictionary.projnum',
							value : this.MortgageData.vProcreditDictionary.projnum
						}, {
							label : '项目名称',
							name : 'vProcreditDictionary.projname',
							value : this.MortgageData.vProcreditDictionary.projname
						}, {
							label : '客户名称',
							name : 'vProcreditDictionary.enterprisename',
							value : this.MortgageData.vProcreditDictionary.enterprisename
						}, {
							label : '保证人',
							name : 'vProcreditDictionary.assureofnameEnterOrPerson',
							value : this.MortgageData.vProcreditDictionary.assureofnameEnterOrPerson
						}, {
							label : '备注',
							xtype : 'textareafield',
							name : 'vProcreditDictionary.mortgageRemarks',
							value : this.MortgageData.vProcreditDictionary.mortgageRemarks
						}, {
							label : '保证人类型',
							readOnly : true,
							name : 'vProcreditDictionary.personTypeValue',
							value : this.MortgageData.vProcreditDictionary.personTypeValue
						}, {
							label : '保证方式',
							xtype : "dickeycombo",
							nodeKey : 'bzfs',
							name : 'procreditMortgage.assuremodeid',
							value : this.MortgageData.vProcreditDictionary.assuremodeidValue
						}, {
							label : '所有权人与借款人的关系',
							name : 'vProcreditDictionary.relation',
							value : this.MortgageData.vProcreditDictionary.relation
						}, {
							label : '是否落实',
							name : 'vProcreditDictionary.tel',
							value : null == this.MortgageData ? "" : this.tel
						}, {
							xtype : 'datepickerfield',
							style : "width:98%; margin:1%;",
							minWidth : 20,
							label : '落实时间',
							name : 'vProcreditDictionary.transactDate',
							id : "vProcreditDictionarytransactDate",
							dateFormat : 'Y-m-d',
							value : new Date(this.MortgageData.vProcreditDictionary.transactDate),
							picker : {
								xtype : 'todaypickerux'
							}
						}, {
							label : '经办人',
							name : 'vProcreditDictionary.transactPerson',
							value : this.MortgageData.vProcreditDictionary.transactPerson
						}, {
							label : '落实文件',
							name : 'vProcreditDictionary.business'
							//		                           value: MortgageData.procreditMortgageEnterprise.business
					}	, {
							label : '落实备注',
							name : 'vProcreditDictionary.transactRemarks',
							value : this.MortgageData.vProcreditDictionary.transactRemarks
						}, {
							label : '是否归档',
							name : 'vProcreditDictionary.isArchiveConfirm',
							value : this.MortgageData.vProcreditDictionary.isArchiveConfirm == false
									? '否'
									: '是'
						}, {
							label : '归档备注',
							name : 'vProcreditDictionary.remarks',
							value : this.MortgageData.vProcreditDictionary.remarks
						}, {
							label : '要求材料清单',
							name : 'vProcreditDictionary.needDatumList',
							value : this.MortgageData.vProcreditDictionary.needDatumList
						}]
			}, {
				xtype : 'fieldset',
				flex : 1,
				title:`查看<${this.MortgageData.vProcreditDictionary.assuremodeidValue}-个人>详细信息`,
				defaults : {
					xtype : 'textfield',
					readOnly : this.readOnly,
					width : "100%",
					labelAlign : "left"
				},
				items : [{
							label : '证件号码',
							name : 'vProjMortDutyPerson.idcard',
							value : this.MortgageData.vProjMortDutyPerson.idcard
						}, {
							label : '家庭住址',
							name : 'vProjMortDutyPerson.familyaddress',
							value : this.MortgageData.vProjMortDutyPerson.zhusuo
						}, {
							label : '家庭电话',
							name : 'vProjMortDutyPerson.phone',
							xtype : "textfield",
							value : this.MortgageData.vProjMortDutyPerson.phone
						}, {
							label : '是否为公务员',
							name : 'vProjMortDutyPerson.isCivilServant',
							xtype : 'togglefield',
							value : this.MortgageData.vProjMortDutyPerson.isCivilServant == true
									? "1"
									: "0"
						}, {
							label : '主营业务或职务',
							name : 'vProjMortDutyPerson.business',
							value : this.MortgageData.vProjMortDutyPerson.business
						}, {
							label : '主要资产',
							name : 'vProjMortDutyPerson.assets',
							value : this.MortgageData.vProjMortDutyPerson.assets
						}, {
							label : '资产价值',
							name : 'vProjMortDutyPerson.assetvalue',
							value : this.MortgageData.vProjMortDutyPerson.assetvalue
						}, {
							label : '月收入',
							name : 'vProjMortDutyPerson.monthlyIncome',
							value : this.MortgageData.vProjMortDutyPerson.monthlyIncome
						}, {
							xtype : this.readOnly == true
									? 'hiddenfield'
									: 'button',
							name : 'submit',
							text : '保存',
							cls : 'buttonCls',
							scope : this,
							handler : this.MortgageData.vProjMortDutyPerson.formSubmit
						}]
			}]
    	});
    	this.callParent([config]);
    },
    detail:function(){
       mobileNavi.push(
		Ext.create('htmobile.customer.person.PersonMenu',{
			        data:this.data
		        	})
		    	);
    },
    formSubmit:function(){
		 var loginForm = this;
		 var birthday= Ext.getCmp("personRelationbirthday").getValue();
		 var strDate=  formatTime(birthday,"yyyy-MM-dd");
		 var personId= this.personId;
		 var id=this.id;
       	 loginForm.submit({
            url:__ctxPath+'/credit/mortgage/updateDutyperson.do',
        	method : 'POST',
        	params:{
        	 "personRelation.birthday":strDate,
        	 "personRelation.personId":personId
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','保存成功');
		        	}else{
		        		  Ext.Msg.alert('','保存失败');
		        	}
        	}
		});}
});
