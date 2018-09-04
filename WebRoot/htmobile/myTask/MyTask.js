/**
 * 手机待办
 * by cjj
 * 
 * var data = {
 *    "tasks" : [
 *    	{
            "taskId": "1",
            "taskName": "task1"
        },
        {
            "taskId": "2",
            "taskName": "task2"
        },
        {
            "taskId": "3",
            "taskName": "task3"
        }
 *    ] 
 * }
 * 
 */

Ext.define('htmobile.myTask.MyTask', {
    extend: 'mobile.List',
    name: 'MyTask',
    constructor: function (config) {
    	//底部导航
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
		var search = Ext.create('Ext.Container', {
    		docked:'top',
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:white;">'+
				    '<header class="search-title dflex">'+
				       ' <input id="daiban" name="daiban" type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button id="find1" type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    	});
    	config = config || {};
    	Ext.apply(config,{
    		style:'background-color:white;',
    		allowDeselect:false,
    		refreshHeightOnUpdate:false,
    		title:config.title,
    		items:[search,bottomBar],
    		itemsTpl:[search].join(""),
    		fields:['taskName',
    				'activityName', 
    				'assignee',
					'createTime', 
					'dueDate', 
					'executionId', 
					'pdId',
					'taskId', 
					'isMultipleTask'
					],
    		url : __ctxPath+ "/flow/userActivityAllTask.do?processName="+ processNameFlowKey,
    		root:'result',
//		    searchCol:'projectName',
		    itemTpl: new Ext.XTemplate(
		     		'<div class="taskListMain">',
						'<div class="taskListOne">',
							'<span class="taskName">{taskName:this.getName}',
							'<span class="icon"></span></span>',
							'<span class="taskNumber">{taskName:this.getNumber}</span>',
						'</div>',
						'<div class="taskListTwo">',
							'<span class="text">当前节点：</span>' ,
							'<span class="activityName">{activityName}</span>',
						'</div>',
					'</div>',{
						getName: function(taskName){
							taskName=taskName.substr(0,taskName.indexOf("-"));
							return taskName;
				        },
				        getNumber:function(taskName){
				        	taskName=taskName.substr(taskName.indexOf("-")+1,taskName.length);
				        	return taskName;
				        }
					}
					),
		    grouped: false,
		    totalProperty: 'totalCounts',
		    pullRefresh: true,
		    listPaging: true,
		    params:{
		     	projectName:param  //搜索的名字
		    },
		    listeners: {
    			itemsingletap:this.itemsingletap
    		}
    	});

    	this.callParent([config]);
    	//条件查询
    	_list =this;
    	$("#find1").on('click', function() {
					var projectName = $("#daiban")[0].value;
					_list.getStore().setParams({
								projectName : projectName
							});
					_list.getStore().loadPage(1);
				});

    },itemsingletap:function(obj, index, target, record){
		var taskId = record.get('taskId');
		var activityName = record.get('activityName');
		var defId = record.get('defId');
		var formpanel = Ext.create('Ext.form.Panel');
		var taskName = record.get('taskName');
	    var objtasklist=this;
	    var flag = false; 
	    formpanel.submit({
		    url: __ctxPath+'/htmobile/getTask.do',
		    params: {
				taskId:taskId,
				activityName:activityName,
				defId:defId
		    },
		    method: 'POST',
		    async : false,
		    success: function(form,action,response){
		        var result = Ext.util.JSON.decode(response);
		    	mobileNavi.push(
		        	Ext.create('htmobile.myTask.MyTaskForm',{
		        		taskId:taskId,
		        		defId:defId,
		        		preTaskName:result.preTaskName,
		        		isSignTask:result.isSignTask,
		        		trans:result.trans,
		        		taskName:taskName,
		        		vars:result.vars,
		        		newjs:result.newjs,
		        		activityName:activityName,
		        		comments:result.comments,
		        		callback:function(){
		        			obj.store.load();
		        		}
		        	})
		    	);
		    },
		    failure: function(form,action,response){
				var obj = Ext.util.JSON.decode(response);
				Ext.Msg.alert('', obj.msg);
	        }
		});
    }
});
