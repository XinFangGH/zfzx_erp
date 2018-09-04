

Ext.define('htmobile.creditFlow.public.mortgage.house.HouseInformation', {
    extend: 'Ext.form.Panel',
    
    name: 'HouseInformation',

    constructor: function (config) {
    	config = config || {};
    	 this.data = config.result;
    	 this.readOnly=config.readOnly;
    	 this.projectId=config.projectId;
    	Ext.apply(config,{
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    title:"<span style='font-size:16px;'>房产信息</span>",
		    items: [{
		                xtype: 'fieldset',
		                 title :'<span style="color:#000000;">查看<住宅>详细信息</span>',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly:this.readOnly,
		                	labelWidth:document.body.clientWidth/3,
	    				    clearIcon : true
		                	
		                },
		                items: [{
				        		xtype : 'hiddenfield',
				        		name : 'slSmallloanProject.projectId',
				        		value:this.projectId
				        	},
		                    {
		                        label:  '<div class="fieldlabel">房产评估价:</div>',
		                         name:"slSmallloanProject.houseEvaluationPrice",
		                         value: Ext.isEmpty(this.data)?null:this.data.houseEvaluationPrice
		                    }
		               
		                
		                ]
		            },
					        {
					            xtype: this.readOnly==true?'hiddenfield':'button',
					            name: 'submit',
					            style:"margin:20px 20px 200px 20px;",
					            text:'保存',
					            cls : 'buttonCls',
					            scope:this,
					            handler:this.submitBtn
					        }]
		            
    	});

    	this.callParent([config]);
    	
    },
    submitBtn:function(){
    
    
        var loginForm = this;
        
		var houseEvaluationPrice=loginForm.getCmpByName('slSmallloanProject.houseEvaluationPrice').getValue();
         if(Ext.isEmpty(houseEvaluationPrice)){
		    Ext.Msg.alert('','房产评估价不能为空');
			return;
		 }
		 var projectId=loginForm.getCmpByName('slSmallloanProject.projectId').getValue();
       	loginForm.submit({
       		
            url: __ctxPath+'/htmobile/creditLoanProjectVmInfo.do',
        	method : 'POST',
        	params:{
				
				"projectId":projectId
			},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','提交成功');
		        		  mobileNavi.pop(1);
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});	
    }

});
