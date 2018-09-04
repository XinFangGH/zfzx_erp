

Ext.define('htmobile.creditFlow.public.mortgage.ZbaozMortgage.LuoshiMortgageFrom', {
    extend: 'Ext.form.Panel',
    
    name: 'LuoshiMortgageFrom',

    constructor: function (config) {
    	config = config || {};
    	 this.businessType=config.businessType;
    	 this.mortgageId=config.mortgageId;
    	 this.mortgagepersontypeforvalue=config.mortgagepersontypeforvalue;
    	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>抵质押物</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                flex:1,
		                title :'<span style="color:#000000;">落实<'+this.mortgagepersontypeforvalue+'>手续</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	width:"100%"
		                //	labelAlign:"top"
		                	
		                },
		                items: [
		                  {
							   	  xtype:'hiddenfield',
							   	  name:'procreditMortgage.isTransact',
							   	  value:true
							   	  
							   },{
							   	  xtype:'hiddenfield',
							   	  value:this.mortgageId,
							   	  name:'mortgageid'
							   	  
							   },{
							   	  xtype:'hiddenfield',
							   	  value:this.businessType,
							   	  name:'procreditMortgage.businessType'
							   	  
							   },{
							   	  xtype:'hiddenfield',
							   	  value:this.businessType,
							   	  name:'procreditMortgage.businessType'
							   	  
							   } , {
		                         label: '<div class="fieldlabel">时间:</div>',
					              xtype: 'datepickerfield',
					              minWidth:20,
					              id:"procreditMortgagetransactDate",
				                  dateFormat:'Y-m-d',
				                  picker: {
				                     xtype:'todaypickerux'
				                  },
				                  value:Ext.isEmpty(this.data)?null:new Date(this.data.transactDate)
		                        
		                        
		                    
		                    }  ,
		                      {
		                        xtype : 'textfield',
		                        label:  "<div class='fieldlabel'>经办人:</div>",
		                        name:'procreditMortgage.transactPerson',
		                        id:"procreditMortgagetransactPerson",
		                        value:Ext.isEmpty(this.data)?null:this.data.transactPerson,
		                        readOnly:this.readOnly,
		                        listeners : {
								scope:this,
								'focus' : function(f) {
									if(!this.readOnly){
									   mobileNavi.push(Ext.create('htmobile.public.AppuserList',{
									   callback:function(data){
									   	  var transactPerson= Ext.getCmp("procreditMortgagetransactPerson");
									   	   var transactPersonId= Ext.getCmp("procreditMortgagetransactPersonId");
									        transactPerson.setValue(data.fullname); 
									         transactPersonId.setValue(data.userId); 
									   }}));
									}
									}
								}
								
		                    }  ,{
		                      xtype:"hiddenfield",
		                      id:"procreditMortgagetransactPersonId",
		                      name:"procreditMortgage.transactPersonId",
		                       value:Ext.isEmpty(this.data)?null:this.data.transactPersonId
		                    },
		                      {
		                        label: '<div class="fieldlabel">备注:</div>',
		                         value:Ext.isEmpty(this.data)?null:this.data.transactRemarks,
		                        name:'procreditMortgage.transactRemarks',
		                        xtype:"textareafield"
		                    }  ,{
		                    	style:"margin:20px 20px 20px 20px;",
		                    	xtype: 'button',
		                        text:"上传/下载落实文件",
		                        scope:this,
		                        handler:this.filelist
		                    } 
		                ]
		            },
		                    {
		                    	style:"margin:20px 20px 200px 20px;",
		                    	xtype: 'button',
		                        text:"保存",
		                        handler:this.addsubmit
		                    } ]
		            
    	});

    	this.callParent([config]);
    	
    },
    addsubmit:function(){
    		
		 var loginForm = this.up('formpanel');
		 var transactDate= Ext.getCmp("procreditMortgagetransactDate").getValue();
		 
		  if(Ext.isEmpty(transactDate)){
		    Ext.Msg.alert('','时间不能为空');
			return;
		  }
		  var strtransactDate = formatTime(transactDate,"yyyy-MM-dd");
		     
		   
		     var transactPerson= loginForm.getCmpByName("procreditMortgage.transactPerson").getValue();
		  if(Ext.isEmpty(transactPerson)){
		    Ext.Msg.alert('','经办人不能为空');
			return;
		  }
		  
		     var transactRemarks= loginForm.getCmpByName("procreditMortgage.transactRemarks").getValue();
		  if(Ext.isEmpty(transactRemarks)){
		    Ext.Msg.alert('','备注不能为空');
			return;
		  }
		  
       	 loginForm.submit({
           	url: __ctxPath +'/credit/mortgage/updateMortgage1.do',
        	method : 'POST',
        	params:{
        		"procreditMortgage.transactDate":strtransactDate
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		  Ext.Msg.alert('','提交成功');
		        		  
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});
    
    
    },
   filelist: function(){
       var	mark='cs_procredit_mortgage.';
       mobileNavi.push(
         Ext.create('htmobile.creditFlow.public.file.FileList',
         	{ 
         	  mark:mark,
         	 readOnly: false,
	         tableid:this.id,
			 title:"上传/下载抵质押物文件"}
         	));
    }

});

