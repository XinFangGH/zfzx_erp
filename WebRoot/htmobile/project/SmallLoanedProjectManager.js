 //项目管理managerType=1 贷后监管
 
Ext.define('htmobile.project.SmallLoanedProjectManager', {
    extend: 'mobile.List',
    name: 'SlSmallloanProjectList',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
		var search = Ext.create('Ext.Container', {
    		docked:'top',
    			
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:white;">'+
				    '<header class="search-title dflex">'+
				       ' <input type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    	});
    	config = config || {};
    	
    	Ext.apply(config,{
    		style:'background-color:white;',
    		title:config.title,
    		items:[bottomBar,search],
    		itemsTpl:[search].join(""),
    		modeltype:'SlSmallloanProjectList',
    		fields:[{
				name : 'runId',
				type : 'int'
			}, 'projectId','appUserName','createDate','orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney',
					'payProjectMoney', 'OppositeType', 'oppositeTypeValue',
					'customerName', 'projectStatus', 'operationType',
					'operationTypeValue', 'taskId', 'activityName',
					'oppositeID', 'businessType', 'startDate', 'endDate',
					'superviseOpinionName', 'supervisionPersonName',
					'processName', 'businessManagerValue', 'breachComment',
					'accrualtype', 'expectedRepaymentDate', 'payintentPeriod',
					'accrual','managementConsultingOfRate','executor','processName','repaymentDate','loanStartDate','businessType',
					'orgUserName','orgUserId'],
    		url : __ctxPath + '/project/approveProjectListSlSmallloanProject.do?isAll='+isGranted('ApproveProject_seeAll_8')+'&projectStatus=7&marker=8&isGrantedShowAllProjects='+isGranted('isGrantedShowAllProjects'),
		    itemTpl:  new Ext.XTemplate("<span style='font-size:14px;color:#412f1f;'>" +
					"{projectName}</span><br/>" +
					"<span style='font-size:12px;color:#a7573b;float:right'>" +
					"{projectNumber}</span>" ),
		   	root:'result',
	    	totalProperty: 'totalCounts',
//	    	searchCol:'projectName',
//		    searchTip:'请输入项目名称',
	        grouped: false,
//		    pullRefresh: true,
		    listPaging: true,
		    plugins:[ {
	            type:"listopt",
	            itemFilter:function(list,action, record){
	            	if(action=="See"){
	            		
	            	   return true;
	            	}
	               if(action=="Edit"){
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
	                text:"贷款详情"
	            },  {
	                action:"Edit",
	                cls:"write",
	                color:"yellow",
	                text:"监管计划"
	            }]
	        } ],
		    params:{
		     projectName:""  //搜索的名字
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
       var id=  record.data.id;
       var data=record.data;
        if (action == "Edit") {
        
        	 mobileNavi.push(
			            Ext.create('htmobile.project.supervision.SlSupervisionManageList',{
				        projectId:data.projectId,
				        businessType:data.businessType
			        	}));
        	
        } else if (action == "See") {
		    mobileNavi.push(
			            Ext.create('htmobile.project.SlSmallloanProjectDetail',{
				        data:data
			        	}));
    
    }
    }

});
