//upAndDownContract.js


Ext.define('htmobile.creditFlow.public.main.MeetingSummaryForm', {
    extend: 'Ext.Panel',
    name: 'MeetingSummaryForm',
    constructor: function (config) {
    	config = config || {};
    	this.data=config.result;
    	Ext.apply(config,{
		    fullscreen: true,
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	labelAlign:"top"
		                },
		                items: [{
		                        label: "<div class='fieldlabel'>会议时间:</div>",
		                        value:data==null?"": data.conforenceTime
		                    },
		                    {  
		                        label: "<div class='fieldlabel'>记录人员:</div>",
		                        value:data==null?"": data.recordPersonName
		                    },{
		                        label:"<div class='fieldlabel'>会议地点:</div>",
		                        xtype: 'togglefield',
		                        value:data==null?"": data.conforencePlace
		                    },
		                    {
		                        label:  "<div class='fieldlabel'>参与人员 </div>",
		                         value:data==null?"": data.jionPersonName
		                    },
		                    {
		                        label: "<div class='fieldlabel'>决议方式</div>",
		                        value:data==null?"":  data.decisionType,
		                       xtype : 'dicIndepCombo',
					          nodeKey : 'resolutionMethods'
		                   
		                    },
		                    {
		                        label: "<div class='fieldlabel'>会议结果:</div>",
		                        value:data==null?"":  data.conferenceResult,
		                        xtype : 'dicIndepCombo',
		                        nodeKey :'meetingResult'
		                    }
		          
		          ]}]
    	});

  

    	this.callParent([config]);
    	
    }

});
Ext.define('htmobile.customer.enterprise.menudetail.MeetingSummaryList', {
    extend: 'mobile.List',
    
    name: 'MeetingSummaryList',

    constructor: function (config) {
		this.projId=config.projId;
		this.businessType=config.businessType;
    	config = config || {};
    var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tableheadtwo' >文件名称(已上传)</span>"
    	                                                    )});
    	Ext.apply(config,{
    		modeltype:"MeetingSummaryList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		items:[panel],
    		fields:[{
					name : 'fileid'
				}, {
					name : 'filename'
				}, {
					name : 'filepath'
				}, {
					name : 'extendname'
				}, {
					name : 'filesize'
				}, {
					name : 'createtime'
				}, {
					name : 'contentType'
				}, {
					name : 'webPath'
				}, {
					name : 'projId'
				}],
    	    	url : __ctxPath + '/creditFlow/fileUploads/ajaxGetFilesListFileForm.do',
	    		root:'topics',
	    	    totalProperty: 'totalProperty',
	    	    params : {
						mark : "sl_conference_record." + this.projId+"."+this.businessType,
						typeisfile : "typeismeeting"
			},
		    itemTpl: new Ext.XTemplate(
		    		"<span  class='tablelistone' style='width:70%;'>{filename}</span>&nbsp;&nbsp;" +
 		        "<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline' onclick=\"javascript:downLoadFile('{filepath:this.filepathFormat}')\">下载</span>" +
 		        "<span  class='tablelist'  style='width:95%;'>大小{filesize/1024}KB&nbsp;&nbsp;扩展名{extendname}&nbsp;&nbsp;上传时间{createtime}</span>&nbsp;&nbsp;" 
		    		,{
		    		filepathFormat: function(filepath) {
		    			 	var reg = /\\/g;
                            var  filepath=filepath.replace(reg,"/");
                            return filepath;
						}  
		    		}),
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	   var label = new Array("日期模型 ","件数","合同金额","销售收入实现金额","无合同销售额"); 
    	  var value = new Array(data.dateMode,data.count,data.contractMoney,data.saleIncomeyMoney,
    	  data.noContractSaleMoney);  
          getListDetail(label,value);
		    

}
});

Ext.define('htmobile.creditFlow.public.main.MeetingSummaryTab', {
	extend: 'Ext.TabPanel',
    
    name: 'MeetingSummaryTab',

    constructor: function (config) {
    	var data=config.data;
    	this.projId=config.projId;
    	this.businessType=config.businessType;
	//    var MeetingSummaryForm=Ext.create('htmobile.creditFlow.public.main.MeetingSummaryForm',{data:data}); 
	    var MeetingSummaryList=Ext.create('htmobile.customer.enterprise.menudetail.MeetingSummaryList',{businessType:this.businessType,projId:this.projId}); 
		config = config || {};
	    Ext.apply(config,{
        	title:'上传会议纪要',
            layoutOnTabChange: true,
            items: [
	            
	            {
	            	title: '<div style="font-size:15px;">会议纪要</div>',
	            	fullscreen: true,
					 scrollable:{
						    	direction: 'vertical'
					 },
	                items:[{
	    	            xtype: 'fieldset',
	    	             defaults:{
		                	xtype: 'textfield',
		                	readOnly: true,
		                	labelAlign:"top"
		                },
	    	            items:[{
		                        label: "<div class='fieldlabel'>会议时间:</div>",
		                        value:data==null?"": data.conforenceTime
		                    },
		                    {  
		                        label: "<div class='fieldlabel'>记录人员:</div>",
		                        value:data==null?"": data.recordPersonName
		                    },{
		                        label:"<div class='fieldlabel'>会议地点:</div>",
		                        value:data==null?"": data.conforencePlace
		                    },
		                    {
		                        label:  "<div class='fieldlabel'>参与人员 </div>",
		                         value:data==null?"": data.jionPersonName
		                    },
		                    {
		                        label: "<div class='fieldlabel'>决议方式</div>",
		                        value:(data!=null&&data.decisionType!=null)?data.decisionType:"",
		                        xtype : 'dicIndepCombo',
					            nodeKey : 'resolutionMethods'
		                   
		                    },
		                    {
		                        label: "<div class='fieldlabel'>会议结果:</div>",
		                        value:(data!=null&&data.conferenceResult!=null)?data.conferenceResult:"",
		                        xtype : 'dicIndepCombo',
		                        nodeKey :'meetingResult'
		                    }
		          
		          ]
	    	        }]
	            },
	            {
	            	title: '<div style="font-size:15px;">上传会议纪要</div>',
	                items:[{
	    	            xtype: 'fieldset',
	    	            items:MeetingSummaryList
	    	        }]
	            }
            ]
        });
    
        this.callParent([config]);
	}

});

