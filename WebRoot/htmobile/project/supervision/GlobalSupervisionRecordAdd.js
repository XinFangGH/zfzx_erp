

Ext.define('htmobile.project.supervision.GlobalSupervisionRecordAdd', {
    extend: 'Ext.form.Panel',
    
    name: 'GlobalSupervisionRecordAdd',

    constructor: function (config) {
    	config = config || {};
    	 this.projectId=config.projectId;
    	 this.businessType=config.businessType;
    	 this.superviseManageId=config.superviseManageId;
    	 this.data=config.data;
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"<span style='font-size:16px;'>监管信息录入</span>",
		    flex:1,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                flex:1,
		                title :'<span style="color:#000000;"></span>',
		                defaults:{
		                	xtype: 'textfield',
		                	width:"100%"
		                //	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">计划监管人:</div>',
		                        id:"globalSupervisemanagedesignSuperviseManagersName",
		                        name:"globalSupervisemanage.designSuperviseManagersName",
		                        readOnly: true,
		                        listeners : {
								scope:this,
								'focus' : function(f) {
									if(!this.readOnly){
									   mobileNavi.push(Ext.create('htmobile.public.AppuserList',{
									   callback:function(data){
									   	  var teamManagerId= Ext.getCmp("globalSupervisemanagedesignSuperviseManagers");
									   	   var teamManagerName= Ext.getCmp("globalSupervisemanagedesignSuperviseManagersName");
									        teamManagerName.setValue(data.fullname); 
									         teamManagerId.setValue(data.userId); 
									   }}));
									}
									}
								}
		                    }  ,{
		                       xtype:"hiddenfield",
		                       name:"globalSupervisemanage.designSuperviseManagers",
		                       id:"globalSupervisemanagedesignSuperviseManagers"
		                    
		                    },
		                      {
		                        label: '<div class="fieldlabel">计划监管时间:</div>',
					              xtype: 'datepickerfield',
					         //     style:"width:98%; margin:1%;",
					              minWidth:20,
					              name: 'globalSupervisemanage.designSuperviseManageTime',
					              id:"globalSupervisemanagedesignSuperviseManageTime",
				                  dateFormat:'Y-m-d',
				                  picker: {
				                     xtype:'todaypickerux'
				                  }
		                        
		                        
		                    }  ,
		                      {
		                           label: '<div class="fieldlabel">指派人:</div>',
		                           value:curUserInfo.fullname
		                        
		                    } ,
		                      {
		                        label: '<div class="fieldlabel">监管状态:</div>',
		                        value:"未执行"
		                    },
		                      {
		                        label: '<div class="fieldlabel">指派人备注:</div>',
		                        name : 'globalSupervisemanage.superviseManageRemark',
		                        xtype:"textareafield",
							    value:Ext.isEmpty(this.data)?null:this.data.superviseManageRemark
		                    } ,
		                      { 
		                      	readOnly:Ext.isEmpty(this.data)?true:false,
		                        xtype:Ext.isEmpty(this.data)?"hiddenfield":"textareafield",
		                        label: '<div class="fieldlabel">监管人备注:</div>',
		                        name : 'globalSupervisemanage.designSuperviseManageRemark',
							    value:Ext.isEmpty(this.data)?null:this.data.designSuperviseManageRemark
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
		var designSuperviseManagersName=loginForm.getCmpByName("globalSupervisemanage.designSuperviseManagersName").getValue(); 
		  if(Ext.isEmpty(designSuperviseManagersName)){
		    Ext.Msg.alert('','计划监管人不能为空');
			return;
		  }
		   var designSuperviseManageTime=loginForm.getCmpByName("globalSupervisemanage.designSuperviseManageTime").getValue(); 
		  if(Ext.isEmpty(designSuperviseManageTime)){
		    Ext.Msg.alert('','计划监管时间不能为空');
			return;
		  }
		  var superviseManageRemark=loginForm.getCmpByName("globalSupervisemanage.superviseManageRemark").getValue(); 
		  if(Ext.isEmpty(superviseManageRemark)){
		    Ext.Msg.alert('','指派人备注不能为空');
			return;
		  }
		 var strdesignSuperviseManageTime=  formatTime(designSuperviseManageTime,"yyyy-MM-dd");
       	 loginForm.submit({
            url: __ctxPath+ "/htmobile/saveGlobalSupervisemanageVmInfo.do",
        	method : 'POST',
        	params:{
        	    projectId:loginForm.projectId,
				businessType:loginForm.businessType,
				"globalSupervisemanage.designSuperviseManageTime":strdesignSuperviseManageTime
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        	      Ext.getCmp("SlSupervisionManageList").store.loadPage(1);
		        	      mobileNavi.pop();
		        		  Ext.Msg.alert('','提交成功');
		        		  
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});
    
    
    }

});

