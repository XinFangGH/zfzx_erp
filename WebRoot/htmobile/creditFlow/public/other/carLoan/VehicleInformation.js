
//ownFund.js
var st ;
Ext.define('htmobile.creditFlow.public.other.carLoan.VehicleInformation', {
    extend: 'Ext.form.Panel',
    name: 'carLoan',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.result;
    	this.readOnly=config.readOnly;
    	this.stately=config.stately;
    	if(this.stately==2){
    		st = false;
    	}else{
    		st = true;
    	}
    	Ext.apply(config,{
    		title:'评估核档',
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [{
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	labelAlign:"left",
		                	readOnly:this.readOnly
		                },
		                items : [{
									label : "车辆配置",
									name : 'slSmallloanProject.vehicleConfiguration',
									value : this.data.vehicleConfiguration
								}, {
									label : "车辆外观",
									name : 'slSmallloanProject.vehicleAppearance',
									value : this.data.vehicleAppearance
								}, {
									readOnly : st,
									label : "车辆违章",
									name : 'slSmallloanProject.vehicleViolation',
									value : this.data.vehicleViolation
								}, {
									readOnly : st,
									label : "核档案信息",
									name : 'slSmallloanProject.vehicleInformation',
									value : this.data.vehicleInformation
								}, {
									label : "车辆评估价(元)",
									name : 'slSmallloanProject.vehicleEvaluationPrice',
									value : this.data.vehicleEvaluationPrice
								}, {
									label : "建议贷款额(元)",
									name : 'slSmallloanProject.vehicleproLoanAmount',
									value : this.data.vehicleproLoanAmount
								}, {

									name : 'projectId',
									xtype : 'hiddenfield',
									value : this.data.projectId

								}, {
									xtype : this.readOnly == true
											? 'hiddenfield'
											: 'button',
									name : 'submit',
									text : '保存',
									cls : 'submit-button',
									handler : this.formSubmit
								}]}]
    	});

    	this.callParent([config]);
    	
    },
    formSubmit:function(){
		 var loginForm = this.up('formpanel');
		  var vehicleConfiguration=loginForm.getCmpByName("slSmallloanProject.vehicleConfiguration").getValue(); 
		  if(Ext.isEmpty(vehicleConfiguration)){
		    Ext.Msg.alert('','车辆配置不能为空');
			return;
		  }
		   var vehicleAppearance=loginForm.getCmpByName("slSmallloanProject.vehicleAppearance").getValue(); 
		  if(Ext.isEmpty(vehicleAppearance)){
		    Ext.Msg.alert('','车辆外观不能为空');
			return;
		  }
		  if(!st){
		   var vehicleViolation=loginForm.getCmpByName("slSmallloanProject.vehicleViolation").getValue(); 
		  if(Ext.isEmpty(vehicleViolation)){
		    Ext.Msg.alert('','车辆违章不能为空');
			return;
		  }
		 var vehicleInformation=loginForm.getCmpByName("slSmallloanProject.vehicleInformation").getValue(); 
		  if(Ext.isEmpty(vehicleInformation)){
		    Ext.Msg.alert('','核档案信息不能为空');
			return;
		  }
		  }
		   var vehicleEvaluationPrice=loginForm.getCmpByName("slSmallloanProject.vehicleEvaluationPrice").getValue(); 
		  if(Ext.isEmpty(vehicleEvaluationPrice)){
		    Ext.Msg.alert('','车辆评估价不能为空');
			return;
		  }
		   var vehicleproLoanAmount=loginForm.getCmpByName("slSmallloanProject.vehicleproLoanAmount").getValue(); 
		  if(Ext.isEmpty(vehicleproLoanAmount)){
		    Ext.Msg.alert('','建议贷款额不能为空');
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
