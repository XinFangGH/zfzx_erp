
//ownFund.js
Ext.define('htmobile.creditFlow.public.other.VisitCheck', {
    extend: 'Ext.form.Panel',
    name: 'VisitCheck',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.result;
    	this.readOnly=config.readOnly;
    	Ext.apply(config,{
    		title:'家访核实',
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
		                        label:  "家庭地址",
		                        name:'slSmallloanProject.familyAddress',
		                        value:this.data.familyAddress
		                    },
		                    {    
		                        label:  "单位地址",
		                          name:'slSmallloanProject.companyAddress',
		                        value: this.data.companyAddress
		                    },
		                    {    
		                        label:  "所留手续",
		                          name:'slSmallloanProject.familyFile',
		                        value: this.data.familyFile
		                    },
		                    {    
		                        label:  "客户家访情况",
		                          name:'slSmallloanProject.familyHow',
		                        value: this.data.familyHow
		                    },
		                    {    
		                        label:  "建议",
		                          name:'slSmallloanProject.adviceFamily',
		                        value: this.data.adviceFamily
		                    },
		                    {    
		                        label:  "家访签字",
		                          name:'slSmallloanProject.famrlyRight',
		                        value: this.data.famrlyRight
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
		  var familyAddress=loginForm.getCmpByName("slSmallloanProject.familyAddress").getValue(); 
		  if(Ext.isEmpty(familyAddress)){
		    Ext.Msg.alert('','家庭地址不能为空');
			return;
		  }
		   var companyAddress=loginForm.getCmpByName("slSmallloanProject.companyAddress").getValue(); 
		  if(Ext.isEmpty(companyAddress)){
		    Ext.Msg.alert('','单位地址不能为空');
			return;
		  }
		   var familyFile=loginForm.getCmpByName("slSmallloanProject.familyFile").getValue(); 
		  if(Ext.isEmpty(familyFile)){
		    Ext.Msg.alert('','所留手续不能为空');
			return;
		  }
		 var familyHow=loginForm.getCmpByName("slSmallloanProject.familyHow").getValue(); 
		  if(Ext.isEmpty(familyHow)){
		    Ext.Msg.alert('','客户家访情况不能为空');
			return;
		  }
		 var adviceFamily=loginForm.getCmpByName("slSmallloanProject.adviceFamily").getValue(); 
		  if(Ext.isEmpty(adviceFamily)){
		    Ext.Msg.alert('','建议不能为空');
			return;
		  }
		 var famrlyRight=loginForm.getCmpByName("slSmallloanProject.famrlyRight").getValue(); 
		  if(Ext.isEmpty(famrlyRight)){
		    Ext.Msg.alert('','家访签字不能为空');
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
