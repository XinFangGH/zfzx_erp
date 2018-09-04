

Ext.define('htmobile.creditFlow.public.processFomlist', {
    extend: 'mobile.List',
    
    name: 'processFomlist',

    constructor: function (config) {
		this.taskId=config.taskId;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead' >节点名称</span>",
    	                                                      "<span class='tablehead' >执行人</span>","<span class='tablehead' >审批状态</span>")});
    	Ext.apply(config,{
    		flex:1,
    		title:"<span style='font-size:16px'>意见与说明记录</span>",
    		items:[panel],
    		fields:['activityName', 'creatorName','status', 'createtime', 'endtime','durTimes', 'comments', 'safeLevel'],
    	    url : __ctxPath+ '/flow/getByTaskIdProcessRun.do?taskId='
								+ this.taskId ,
    		root:'result',
    	    totalProperty: 'totalCounts',
		    itemTpl: "<span  class='tablelist'>{activityName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{creatorName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' ><tpl if='status==1'><font color='green'>审批通过</tpl>" +
		    		"<tpl if='status==-1'><font color='red'>驳回</font></tpl>" +
		    		"<tpl if='status==2'><font color='orange'>流程跳转</font></tpl>" +
		    		"<tpl if='status==3'><font color='red'>打回重做</font></tpl>" +
		    		"<tpl if='status==4'><font color='red'>任务追回</font></tpl>" +
		    		"<tpl if='status==5'><font color='orange'>任务换人</font></tpl>" +
		    		"<tpl if='status==6'><font color='orange'>项目换人</font></tpl>" +
		    		"<tpl if='status==7'><font color='orange'>项目终止</font></tpl>" +
		    		"</span>&nbsp;&nbsp;" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("节点名称","执行人","开始时间","结束时间","耗时",
    	"审批状态" ,"意见与说明"); 
    	  var value = new Array(data.activityName,data.creatorName,data.createtime,data.endtime,
    	data.durTimes ,data.status==1?"审批通过":( data.status==-1?"驳回": data.status==2?"流程跳转":(data.status==3?"打回重做":(data.status==4?"任务追回":(data.status==5?"任务换人":(data.status==6?"项目换人":"项目终止"))))),data.comments);  
        var a = new Array(null,null,null,null,null,null,"textareafield"); 
    	getListDetail(label,value,a);
		    

}
});
