
Ext.define('htmobile.project.bpFundProjectList', {
    extend: 'mobile.List',
    
    name: 'bpFundProjectList',

    constructor: function (config) {
		
    	config = config || {};
    	
    	Ext.apply(config,{
    		title:config.title,
    		modeltype:'bpFundProjectList',
    		fields:[{
								name : 'projectId'
							}, {
								name : 'projectName'
							}, {
								name : 'projectNumber'
							}, {
								name : 'runId'
							}],
    		url : __ctxPath + '/fund/projectListBpFundProject.do?projectStatus=7&isGrantedShowAllProjects='+isGranted('isGrantedShowAllProjects'),
		    itemTpl:  new Ext.XTemplate("<span style='font-size:14px;color:#412f1f;'>" +
					"{projectName}</span><br/>" +
					"<span style='font-size:12px;color:#a7573b;float:right'>" +
					"{projectNumber}</span>" ),
		   	root:'result',
	    	totalProperty: 'totalCounts',
	    	searchCol:'projectName',
		    searchTip:'请输入项目名称',
	    	 grouped: false,
		    pullRefresh: true,
		    listPaging: true,
		    params:{
		     projectName:""  //搜索的名字
		    },
		    listeners: {
    			itemsingletap:this.itemsingletap
    		}
    	});

    	this.callParent([config]);

    }
,
  itemsingletap:function(obj, index, target, record){
					    mobileNavi.push(
			            Ext.create('htmobile.project.bpFundProjectMenu',{
				        data:record.data
			        	}));
    
    }

});
