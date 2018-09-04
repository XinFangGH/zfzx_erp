 //项目管理managerType=1 贷后监管
 
Ext.define('htmobile.project.supervision.SlSupervisionManageList', {
    extend: 'mobile.List',
    id:"SlSupervisionManageList",
    name: 'SlSupervisionManageList',

    constructor: function (config) {
		
    	config = config || {};
    	this.projectId=config.projectId;
		this.businessType=config.businessType;
		var button=this.readOnly?{}:Ext.create('Ext.Panel',{
			docked:'top',
		      items:[
		      {
		       style:"margin:15px 100px  ",
		       xtype:'button',
		       handler:this.addbtn,
		       scope:this,
		       text:'添加'
		       		
		      }
		      ]
			
		});
    	Ext.apply(config,{
    		title:"贷后监管",
    		modeltype:'SlSmallloanProjectList',
    		items:[button],
    		fields:[{
				name : 'superviseManageId',
				type : 'int'
			}, 'designSuperviseManagers', 'designSuperviseManagersName',
					'designSuperviseManageTime','superviseManageRemark', 'designSuperviseManageRemark',
					'designee','designeeId', 'superviseManageStatus'],
    	     url : __ctxPath + "/supervise/listGlobalSupervisemanage.do",
		    itemTpl:  new Ext.XTemplate("<span style='font-size:14px;color:#412f1f;'>" +
					"{designSuperviseManagersName}--{designSuperviseManageTime}--{designee}---<tpl if='superviseManageStatus==0'>未执行</tpl><tpl if='superviseManageStatus==1'>已执行</tpl></span><br/>" 
					/*"<span style='font-size:12px;color:#a7573b;float:right'>" +
					"{designSuperviseManageTime}</span>"*/ ),
		   	root:'result',
	    	totalProperty: 'totalCounts',
		    pullRefresh: true,
		    listPaging: true,
		    plugins:[ {
	            type:"listopt",
	            itemFilter:function(list,action, record){
	            	if(action=="See"){
	            		
	            	   return true;
	            	}
	            	if(action=="Remove"){
	               	  //加权限
		               	if(list.readOnly){
		               	   return false;
		               	}else{
		               		return true;
		               	}
	            	   
	            	}
	            },
	            items:[{
	                action:"See",
	                cls:"write",
	                color:"grey",
	                text:"查看监管记录"
	            },  {
	                action:"Remove",
	                cls:"write",
	                color:"red",
	                text:"删除"
	            }]
	        } ],
		    params:{
		    projectId : this.projectId,
		    businessType:this.businessType
		    }
    	});

    	this.callParent([config]);

    }
,
    initialize:function() {
        this.callParent(arguments);
        this.on({
            listoptiontap:"optTap",
            scope:this
        });
    },
    optTap:function(action, list, record) {
    	var obj=this;
       var data=record.data;
       	var superviseManageId =data.superviseManageId;
       	var businessType = obj.businessType;
       	var projectId = obj.projectId;
         if (action == "See") {
        	Ext.Ajax.request({
					url : __ctxPath + '/htmobile/getGlobalSupervisemanageobjVmInfo.do',
					async:false,
					params:{
					    superviseManageId:superviseManageId,
					    businessType:businessType
					},
				    success : function(response) {
				    var obj = Ext.util.JSON.decode(response.responseText);
						var data = obj.data;
                            mobileNavi.push(
				    		  Ext.create('htmobile.project.supervision.GlobalSupervisionRecord',{
											readOnly:true,
											superviseManageId:superviseManageId,
											data:data,
					   						businessType:businessType,
					   						projectId:projectId
							        	})
				    		);
    
         }
			});
					  
    
	    }else if (action == "Remove") {
        
	    	
        	Ext.Ajax.request({
				url : __ctxPath + '/supervise/multiDelGlobalSupervisemanage.do',
				params:{
					ids:superviseManageId
				},
			   	success : function(response) {
			        var responseText = Ext.util.JSON.decode(response.responseText);
			        
				   if (responseText.success==true) {
				   	  Ext.Msg.alert("", "删除成功 ");
				      obj.store.loadPage(1);
				   	
				   }else{
			
						Ext.Msg.alert("", "删除失败");
						return;
					
				}
			}
		});  	
        }
    },
    addbtn:function(){
    
      
        	  mobileNavi.push(
			            Ext.create('htmobile.project.supervision.GlobalSupervisionRecordAdd',{
				        projectId:this.projectId,
				        businessType:this.businessType
			        	}));
        	
        
    
    
    }

});
