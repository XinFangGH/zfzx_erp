

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.SeeCompany', {
    extend: 'Ext.Panel',
    name: 'SeeCompany',
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
							name : 'vProcreditDictionary.assuremodeidValue',
							value : this.MortgageData.vProcreditDictionary.assuremodeidValue
						}, {
							label : '所有权人与借款人的关系',
							name : 'vProcreditDictionary.relation',
							value : this.MortgageData.vProcreditDictionary.relation
						}, {
							label : '是否落实',
							name : 'vProcreditDictionary.tel',
							value : null == this.MortgageData
									? ""
									: this.MortgageData.vProcreditDictionary.tel
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
				title:`查看<${this.MortgageData.vProcreditDictionary.assuremodeidValue}-公司>详细信息`,
				defaults : {
					xtype : 'textfield',
					readOnly : this.readOnly,
					width : "100%",
					labelAlign : "left"
				},
				items : [{
							label : '营业执照号码',
							name : 'vProjMortCompany.licensenumber',
							value : this.MortgageData.vProjMortCompany.licensenumber
						}, {
							label : '法人代表',
							name : 'vProjMortCompany.corporate',
							value : this.MortgageData.vProjMortCompany.corporate
						}, {
							label : '联系电话',
							name : 'vProjMortCompany.telephone',
							value : this.MortgageData.vProjMortCompany.telephone
						}, {
							label : '法人代表电话',
							name : 'vProjMortCompany.corporatetel',
							value : this.MortgageData.vProjMortCompany.corporatetel
						}, {
							label : '公司地址',
							name : 'vProjMortCompany.registeraddress',
							value : this.MortgageData.vProjMortCompany.registeraddress
						}, {
							label : '主营业务',
							name : 'vProjMortCompany.business',
							value : this.MortgageData.vProjMortCompany.business
						}, {
							label : '资产价值',
							name : 'vProjMortCompany.netassets',
							value : this.MortgageData.vProjMortCompany.netassets
						}, {
							label : '月营业额',
							name : 'vProjMortCompany.monthlyIncome',
							value : this.MortgageData.vProjMortCompany.monthlyIncome
						}, {
							xtype : this.readOnly == true
									? 'hiddenfield'
									: 'button',
							name : 'submit',
							text : '保存',
							cls : 'buttonCls',
							scope : this,
							handler : this.formSubmit
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
            url:Ext.isEmpty(id)?( __ctxPath+'/creditFlow/customer/person/addPersonRelation.do'):
            (__ctxPath+'/creditFlow/customer/person/updatePersonRelation.do'),
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
