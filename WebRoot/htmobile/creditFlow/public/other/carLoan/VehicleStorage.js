Ext.define('htmobile.creditFlow.public.other.carLoan.VehicleStorage', {
    extend: 'Ext.form.Panel',
    name: 'VehicleStorage',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.result;
    	this.readOnly=config.readOnly;
    	Ext.apply(config,{
    		title:'车辆入库信息',
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
		                items : [{		label : "车主姓名",
											name : 'slSmallloanProject.ownerName',
											value : this.data.ownerName
										},{
											label : "车牌号",
											name : 'slSmallloanProject.licensePlate',
											value : this.data.licensePlate
										},{
											label : "送车司机",
											name : 'slSmallloanProject.carDriver',
											value : this.data.carDriver
										},{
											label : "司机电话",
											name : 'slSmallloanProject.driverPhone',
											value : this.data.driverPhone
										},{
											label : "停车场",
											name : 'slSmallloanProject.parkingLot',
											value : this.data.parkingLot
										},{
											label : "停车位",
											name : 'slSmallloanProject.parkingSpaces',
											value : this.data.parkingSpaces
										},{

											name : 'projectId',
											xtype : 'hiddenfield',
											value : this.data.projectId

										},{
											xtype : this.readOnly == true
													? 'hiddenfield'
													: 'button',
											name : 'submit',
											text : '提交',
											cls : 'submit-button',
											handler : this.formSubmit
										}
								]}]
    	});

  

    	this.callParent([config]);
    	
    },
    formSubmit:function(){	
		 var loginForm = this.up('formpanel');
		 var ownerName=loginForm.getCmpByName("slSmallloanProject.ownerName").getValue(); 
		  if(Ext.isEmpty(ownerName)){
		    Ext.Msg.alert('','车主姓名不能为空');
			return;
		  }
		   var licensePlate=loginForm.getCmpByName("slSmallloanProject.licensePlate").getValue(); 
		  if(Ext.isEmpty(licensePlate)){
		    Ext.Msg.alert('','车牌号不能为空');
			return;
		  }
		   var carDriver=loginForm.getCmpByName("slSmallloanProject.carDriver").getValue(); 
		  if(Ext.isEmpty(carDriver)){
		    Ext.Msg.alert('','送车司机不能为空');
			return;
		  }
		 var driverPhone=loginForm.getCmpByName("slSmallloanProject.driverPhone").getValue(); 
		  if(Ext.isEmpty(driverPhone)){
		    Ext.Msg.alert('','司机电话不能为空');
			return;
		  }
		   var parkingLot=loginForm.getCmpByName("slSmallloanProject.parkingLot").getValue(); 
		  if(Ext.isEmpty(parkingLot)){
		    Ext.Msg.alert('','停车场不能为空');
			return;
		  }
		   var parkingSpaces=loginForm.getCmpByName("slSmallloanProject.parkingSpaces").getValue(); 
		  if(Ext.isEmpty(parkingSpaces)){
		    Ext.Msg.alert('','停车位不能为空');
			return;
		  }
       	loginForm.submit({
            url: __ctxPath+'/htmobile/creditLoanProjectVmInfo.do',
        	method : 'POST',
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	
		        		  Ext.Msg.alert('','提交成功');
		        		   mobileNavi.pop();
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});}

});
