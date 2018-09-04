
Ext.define('htmobile.project.SlSmallloanProjectList', {
    extend: 'mobile.List',
    name: 'SlSmallloanProjectList',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	config = config || {};
    	var search = Ext.create('Ext.Container', {
    		docked:'top',
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:white;">'+
				    '<header class="search-title dflex">'+
				       ' <input id="search" type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button id="findbtn" type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    	});
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
    		url : __ctxPath + '/project/approveProjectListSlSmallloanProject.do?isAll='+isGranted('ApproveProject_seeAll_3')+'&marker=3&projectStatus=2&isGrantedShowAllProjects='+isGranted('isGrantedShowAllProjects'),
		    itemTpl:  new Ext.XTemplate(
		     		'<div class="taskListMain">',
						'<div class="taskListOne" style="border-bottom:none;">',
							'<span class="taskName">{projectName:this.projectNameSub}',
							'<span class="icon"></span></span>',
							'<span class="projectNumber">{projectNumber}</span>' +
							'<span class="projectStatus">状态:' +
							'<a style="color:#eeae1a;">{projectStatus:this.transProjectStatus}</a></span>',
						'</div>',
						'<div class="taskListTwo" style="width:100%">',
							'<span class="_projectMoney">金额：{projectMoney}元</span>' ,
							'<span class="_time">期限：{payintentPeriod}个月</span>',
						'</div>',
					'</div>',{
						projectNameSub:function(projectName){
							return projectName.substring(0,projectName.indexOf('2'))+projectName.substring(projectName.indexOf('('),projectName.length);
						},						
						transProjectStatus:function(projectStatus){
							if(projectStatus==0){
								return '办理中贷款';
							}else if(projectStatus==1){
								return '放款后贷款';
							}else if(projectStatus==2){
								return '已完成贷款';
							}else if(projectStatus==3){
								return '提前终止贷款';
							}else if(projectStatus==4){
								return '展期申请中';
							}else if(projectStatus==5){
								return '通过展期申请';
							}else if(projectStatus==6){
								return '未通过展期申请';
							}else if(projectStatus==7){
								return '贷后监管中';
							}else if(projectStatus==8){
								return '违约贷款';
							}else if(projectStatus==9){
								return '完成贷后监管';
							}else if(projectStatus==10){
								return '已挂起项目';
							}
				        }
					}
					),
		   	root:'result',
	    	totalProperty: 'totalCounts',
//	    	searchCol:'projectName',
//		    searchTip:'请输入项目名称',
	        grouped: false,
		//    pullRefresh: true,
		    listPaging: true,
//		    params:{
//		     projectName:""  //搜索的名字
//		    },
		    listeners: {
    			itemsingletap:this.itemsingletap
    		}
    	});

    	this.callParent([config]);
    	//条件查询
    	_list =this;
    	$("#findbtn").on('click', function() {
					var projectName = $("#search")[0].value;
					_list.getStore().setParams({
								projectName : projectName
							});
					_list.getStore().loadPage(1);
				});
    },itemsingletap:function(obj, index, target, record){
  			mobileNavi.push(Ext.create('htmobile.project.SlSmallloanProjectDetail',{
							data : record.data
						}));
    }
});
