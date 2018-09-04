

Ext.define('htmobile.creditFlow.public.mortgage.house.HouseStorage', {
    extend: 'Ext.form.Panel',
    
    name: 'HouseStorage',

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
		                        label:  '<div class="fieldlabel">家庭情况:</div>',
		                         name:"slSmallloanProject.familySituation",
		                         value: Ext.isEmpty(this.data)?null:this.data.familySituation
		                    },
		                    {
		                        label:  '<div class="fieldlabel">房产及周边:</div>',
		                         name:"slSmallloanProject.houseProperty",
		                         value: Ext.isEmpty(this.data)?null:this.data.houseProperty
		                    },
		                    {
		                        label:  '<div class="fieldlabel">还款来源:</div>',
		                         name:"slSmallloanProject.repaymentSource",
		                         value: Ext.isEmpty(this.data)?null:this.data.repaymentSource
		                    },
		                    {
		                        label:  '<div class="fieldlabel">其他情况:</div>',
		                         name:"slSmallloanProject.otherInstructions",
		                         value: Ext.isEmpty(this.data)?null:this.data.otherInstructions
		                    },
		                    {
		                        label:  '<div class="fieldlabel">建议贷款额(元):</div>',
		                         name:"slSmallloanProject.houseproLoanAmount",
		                         value: Ext.isEmpty(this.data)?null:this.data.houseproLoanAmount
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
        
		var familySituation=loginForm.getCmpByName('slSmallloanProject.familySituation').getValue();
         if(Ext.isEmpty(familySituation)){
		    Ext.Msg.alert('','家庭情况不能为空');
			return;
		 }
		var houseProperty=loginForm.getCmpByName('slSmallloanProject.houseProperty').getValue();
         if(Ext.isEmpty(houseProperty)){
		    Ext.Msg.alert('','房产及周边不能为空');
			return;
		 }
		var repaymentSource=loginForm.getCmpByName('slSmallloanProject.repaymentSource').getValue();
         if(Ext.isEmpty(repaymentSource)){
		    Ext.Msg.alert('','还款来源不能为空');
			return;
		 }
		var otherInstructions=loginForm.getCmpByName('slSmallloanProject.otherInstructions').getValue();
         if(Ext.isEmpty(otherInstructions)){
		    Ext.Msg.alert('','其他情况不能为空');
			return;
		 }
		var houseproLoanAmount=loginForm.getCmpByName('slSmallloanProject.houseproLoanAmount').getValue();
         if(Ext.isEmpty(houseproLoanAmount)){
		    Ext.Msg.alert('','建议贷款金额不能为空');
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
