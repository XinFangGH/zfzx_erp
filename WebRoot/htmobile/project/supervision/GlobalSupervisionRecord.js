

Ext.define('htmobile.project.supervision.GlobalSupervisionRecord', {
    extend: 'Ext.form.Panel',
    
    name: 'GlobalSupervisionRecord',

    constructor: function (config) {
    	config = config || {};
    	 this.projectId=config.projectId;
    	 this.businessType=config.businessType;
    	 this.superviseManageId=config.superviseManageId;
    	 this.data=config.data;
    	 this.readOnly=false;
    	 if(!Ext.isEmpty(config.readOnly)){
    	 	 this.readOnly=config.readOnly;
    	 }
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
		                	width:"100%",
		                	readOnly: this.readOnly
		                //	labelAlign:"top"
		                	
		                },
		                items: [
		                    {
		                        label: '<div class="fieldlabel">监管人:</div>',
		                        readOnly: true,
		                        value:curUserInfo.fullname
		                    }  ,
		                      {
		                        label: '<div class="fieldlabel">监管时间:</div>',
					              xtype: 'datepickerfield',
					         //     style:"width:98%; margin:1%;",
					              minWidth:20,
					              name: 'globalSupervisemanage.designSuperviseManageTime',
					              id:"globalSupervisemanagedesignSuperviseManageTime",
				                  dateFormat:'Y-m-d',
				                  picker: {
				                     xtype:'todaypickerux'
				                  },
				                  value:Ext.isEmpty(this.data)?null:new Date(this.data.designSuperviseManageTime)
		                        
		                        
		                    }  ,
		                      {
		                           label: '<div class="fieldlabel">监管方式:</div>',
		                        	xtype : "diccombo",
									hiddenName : 'globalSupervisemanage.superviseManageMode',
									name:'globalSupervisemanage.superviseManageMode',
									itemName : '监管方式', // xx代表分类名称
									isDisplayItemName : false,
									 value:Ext.isEmpty(this.data)?null:this.data.superviseManageMode
		                        
		                    } ,
		                      {
		                        label: '<div class="fieldlabel">监管意见:</div>',
		                        xtype : "diccombo",
								hiddenName : 'globalSupervisemanage.superviseManageOpinion',
								name:'globalSupervisemanage.superviseManageOpinion',
								itemName : '监管意见', // xx代表分类名称
								isDisplayItemName : false,
							    value:Ext.isEmpty(this.data)?null:this.data.superviseManageOpinion
		                    },
		                      {
		                        label: '<div class="fieldlabel">指派人备注:</div>',
		                        name : 'globalSupervisemanage.superviseManageRemark',
		                        xtype:"textareafield",
		                        readOnly:Ext.isEmpty(this.data)?false:true,
							    value:Ext.isEmpty(this.data)?null:this.data.superviseManageRemark
		                    }  ,
		                      {
		                        xtype:Ext.isEmpty(this.data)?"hiddenfield":"textareafield",
		                        label: '<div class="fieldlabel">监管人备注:</div>',
		                        name : 'globalSupervisemanage.designSuperviseManageRemark',
							    value:Ext.isEmpty(this.data)?null:this.data.designSuperviseManageRemark
		                    } ,{
		                    	style:"margin:20px 20px 20px 20px;",
		                    	xtype: 'button',
		                        text:"上传/下载文件",
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
		 var designSuperviseManageTime= Ext.getCmp("globalSupervisemanagedesignSuperviseManageTime").getValue();
		 var strsuperviseManageTime=  formatTime(designSuperviseManageTime,"yyyy-MM-dd");
		
       	 loginForm.submit({
            url: __ctxPath+ "/htmobile/saveGlobalSupervisemanageVmInfo.do",
        	method : 'POST',
        	params:{
        	    "globalSupervisemanage.superviseManageId" : loginForm.superviseManageId,
        	    projectId:loginForm.projectId,
				businessType:loginForm.businessType,
				"globalSupervisemanage.designSuperviseManageTime":strsuperviseManageTime
        	},
        	success : function(form,action,response) {
		        	var obj = Ext.util.JSON.decode(response);
		        	if(obj.success==true){
		        		  Ext.Msg.alert('','提交成功');
		        		  mobileNavi.pop();
		        	}else{
		        		  Ext.Msg.alert('','提交失败');
		        		
		        	}
        	}
		});
    
    
    },
   filelist: function(){
   	var typeisfile = 'superviseRecordReport';
   var tableName = 'global_supervise_report_'+this.businessType+'.'+ this.superviseManageId+".";
       var	mark='cs_procredit_mortgage.';
       mobileNavi.push(
         Ext.create('htmobile.creditFlow.public.file.FileList',
         	{ 
         	  mark:tableName,
         	 readOnly: this.readOnly,
	         tableid: this.projectId,
	    //     typeisfile:typeisfile,
			 title:"上传/下载监管文件"}
         	));
    }

});

