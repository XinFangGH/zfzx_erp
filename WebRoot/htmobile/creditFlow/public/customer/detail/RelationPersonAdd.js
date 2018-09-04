
//ownFund.js
Ext.define('htmobile.creditFlow.public.customer.detail.RelationPersonAdd', {
    extend: 'Ext.form.Panel',
    name: 'RelationPersonAdd',
    constructor: function (config) {
    	config = config || {};
    	Ext.apply(this,config);
    	if(Ext.isEmpty( config.addBtreadOnly)){
    		this.addBtreadOnly=false;
    	}else{
    	    this.addBtreadOnly=config.addBtreadOnly;
    	}
    	Ext.apply(config,{
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    title:this.title,
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	labelAlign:"left",
		                	readOnly:this.addBtreadOnly
		                },
		                items: [{
		                        xtype : "hiddenfield",
		                        name : 'personRelation.id'	,
		                        value:Ext.isEmpty(this.data)?null:this.relationid
		                        },{
		                        xtype : "hiddenfield",
		                        name : 'personId'	,
		                        value:Ext.isEmpty(this.data)?null:this.personId
		                        },{ 
		                        label:"姓名",
		                        name : 'personRelation.relationName',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationName
		                    },{
		                        label: "关系",
		                        xtype : "dickeycombo",
		                        nodeKey : 'gxrgx',	
		                        name : 'personRelation.relationShip',
		                        hiddenName:'personRelation.relationShip',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationShip
		                    },{ 
		                        label:"固定电话",
		                        name : 'personRelation.relationPhone',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationPhone
		                    },{ 
		                        label:"手机",
		                        name : 'personRelation.relationCellPhone',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationCellPhone
		                    },{ 
		                        label:"职业",
		                        name : 'personRelation.relationProfession',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationProfession
		                    },{ 
		                        label:"住址",
		                        name : 'personRelation.relationAddress',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationAddress
		                    },{ 
		                        label:"工作单位",
		                        name : 'personRelation.relationJobCompany',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationJobCompany
		                    },{ 
		                        label:"单位电话",
		                        name : 'personRelation.relationCompanyPhone',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationCompanyPhone
		                    },{ 
		                        label:"单位地址",
		                        name : 'personRelation.relationJobAddress',
		                        value:Ext.isEmpty(this.data)?null:this.data.relationJobAddress
		                    },{ 
		                    	xtype : "hiddenfield",
		                        label:"0 家庭联系人  1工作证明人 2紧急联系人",
		                        name : 'personRelation.flag',
		                        value:this.flag
		                    },
					        {
					            xtype: this.addBtreadOnly==true?'hiddenfield':'button',
					            name: 'submit',
					            text:'保存',
					            cls : 'submit-button',
					            scope:this,
					            handler:this.formSubmit
					        }
		          
		          ]}]
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
		 var personId= this.personId;
		 var id=this.relationid;
		 var relationName=loginForm.getCmpByName("personRelation.relationName").getValue(); 
		  if(Ext.isEmpty(relationName)){
		    Ext.Msg.alert('','姓名不能为空');
			return;
		  }
		 var relationCellPhone=loginForm.getCmpByName("personRelation.relationCellPhone").getValue(); 
		  if(Ext.isEmpty(relationCellPhone)){
		    Ext.Msg.alert('','手机不能为空');
			return;
		  }
       	 loginForm.submit({
            url:Ext.isEmpty(id)?( __ctxPath+'/creditFlow/customer/person/addPersonRelation.do'):
            (__ctxPath+'/creditFlow/customer/person/updatePersonRelation.do'),
        	method : 'POST',
        	params:{
        	 "personRelation.personId":personId
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		  Ext.Msg.alert('','保存成功');
		        	 	  var object= Ext.getCmp("RelationPersonView");
		        	 	  mobileNavi.pop();
					      object.store.loadPage(1);
		        	}else{
		        		  Ext.Msg.alert('','保存失败');
		        	}
        	}
		});}

});