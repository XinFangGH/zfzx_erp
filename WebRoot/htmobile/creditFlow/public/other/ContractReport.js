
//ownFund.js
Ext.define('htmobile.creditFlow.public.other.ContractReport', {
    extend: 'Ext.form.Panel',
    name: 'ContractReport',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.result;
    	this.readOnly=config.readOnly;
    	Ext.apply(config,{
    		title:'面前报告',
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textareafield',
		                	labelAlign:"left",
		                	readOnly:this.readOnly
		                },
		                items: [
		                    {    
		                        label:  "基本资料",
		                        name:'slSmallloanProject.informationCar',
		                        value:this.data.informationCar
		                    },
		                    {    
		                        label:  "软信息类",
		                          name:'slSmallloanProject.softInformation',
		                        value: this.data.softInformation
		                    },
		                    {    
		                        label:  "其它情况",
		                          name:'slSmallloanProject.otherInformation',
		                        value: this.data.otherInformation
		                    },
		                    {    
		                        label:  "确认签字",
		                          name:'slSmallloanProject.rightSign',
		                        value: this.data.rightSign
		                    },{
				            
				           name:'projectId',
				           xtype:'hiddenfield',
				           value:this.data.projectId
				        },
				         {
				            xtype: this.readOnly==true?'hiddenfield':'button',
				            name: 'submit',
				            text:'保存',
				            cls : 'submit-button',
				            handler:this.formSubmit
				        }
		          ]}]
    	});

  

    	this.callParent([config]);
    	
    },
    formSubmit:function(){	
		 var loginForm = this.up('formpanel');
		  var informationCar=loginForm.getCmpByName("slSmallloanProject.informationCar").getValue(); 
		  if(Ext.isEmpty(informationCar)){
		    Ext.Msg.alert('','基本资料不能为空');
			return;
		  }
		   var softInformation=loginForm.getCmpByName("slSmallloanProject.softInformation").getValue(); 
		  if(Ext.isEmpty(softInformation)){
		    Ext.Msg.alert('','软信息类不能为空');
			return;
		  }
		   var otherInformation=loginForm.getCmpByName("slSmallloanProject.otherInformation").getValue(); 
		  if(Ext.isEmpty(otherInformation)){
		    Ext.Msg.alert('','其它情况不能为空');
			return;
		  }
		 var rightSign=loginForm.getCmpByName("slSmallloanProject.rightSign").getValue(); 
		  if(Ext.isEmpty(rightSign)){
		    Ext.Msg.alert('','确认签字不能为空');
			return;
		  }
       	loginForm.submit({
            url: __ctxPath+'/htmobile/creditLoanProjectVmInfo.do',
        	method : 'POST',
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','保存成功');
		        		   mobileNavi.pop();
		        	}else{
		        		  Ext.Msg.alert('','保存失败');
		        		
		        	}
        	}
		});}

});
