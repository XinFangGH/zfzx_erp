
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.CensorMeetingCollectivityDecisionConfirm', {
    extend: 'mobile.List',
    
    name: 'CensorMeetingCollectivityDecisionConfirm',

    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	var	url = __ctxPath + "/flow/findListByRunIdTaskSign.do?runId="+this.runId+'&countersignedTaskKey='+this.countersignedTaskKey
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tablehead'> 人员</span>",
    	                                                      "<span class='tablehead' >投票意见</span>",
    	                                                           "<span class='tablehead' >处理时间</span>")});
    	 var infoPanel= Ext.create("Ext.Panel",{
					border:false,
					docked:'top',
					defaults : {
						margins : '10 10 10 10'
					},
					items:[{xtype:'label',style :'font-size:13px;height:23px;margin-top:10px;margin-left:10px;"',text : ''},
						   {xtype:'label',style :'font-size:13px;height:23px;margin-top:10px;margin-left:10px;',text : ''},
						   {xtype:'label',style :'font-size:13px;height:23px;margin-top:10px;margin-left:10px;',text : ''},
					       {html : '<hr />'}]
				}) ;
	    var loadfunction=function(this1,records){
	      var record=records[0];
	      var createTime=record.data.createTime;
		var taskLimitTime=record.data.taskLimitTime;
		var voteCounts=record.data.voteCounts;
		var taskSignType = record.data.taskSignType;
		infoPanel.items.get(0).setHtml('开始时间：'+createTime);
		infoPanel.items.get(1).setHtml('截至时间：'+taskLimitTime);
		if(taskSignType==1){
			infoPanel.items.get(2).setHtml('决议方式：投票');
			//this.infoPanel.items.get(2).setText('决议方式：投票(注：系统设置需有'+voteCounts+'个人投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
		}else{
			infoPanel.items.get(2).setHtml('决议方式：百分比');
			//this.infoPanel.items.get(2).setText('决议方式：百分比(注：系统设置需有'+voteCounts+'%投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
		}
	    }			
    	Ext.apply(_cfg,{
    		modeltype:"CensorMeetingCollectivityDecisionConfirm",
    		flex:1,
    		width:"100%",
		//    height:"100%",
    		title:"<div style='font-size:15px'>终审意见审核会签情况</div>",
    		items:[infoPanel,panel],
    		fields:[{
								name : 'dataId',
								type : 'int'
							}, 'voteId','voteName','voteTime','taskId','isAgree','runId','createTime','taskLimitTime',
								'premiumRateComments','position','comments','voteCounts','activityName','sbhTimes'/*'mortgageComments','assureTimeLimitComments','assureTotalMoneyComments','feedbackComments'*/],
    	        url :url,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
		        itemTpl: new Ext.XTemplate(  "<span  class='tablelist'>{voteName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >" +
		    		"<tpl if='isAgree==-1'>尚未投票</tpl>" +
		    		"<tpl if='isAgree==1'>同意</tpl>" +
		    		"<tpl if='isAgree==2'>否决</tpl>" +
		    		"<tpl if='isAgree==3'>打回</tpl>" +
		    		"<tpl if='isAgree==4'>有条件通过</tpl>" +
		    		"</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{voteTime}</span>" +
		    		"<span class='tableDetail'>></span>"
		    			
		    		),
		    isload:true,
		    loadfunction:loadfunction,
 	        listeners : {
 	        	scope:this,
				itemsingletap : this.itemsingletap,
			    load: function() {
						Ext.each(this.store.data.items,//对MyStore的
						function(arrayItem, index) {
							var record=arrayItem;
							var createTime=record.data.createTime;
									var taskLimitTime=record.data.taskLimitTime;
									var voteCounts=record.data.voteCounts;
									var taskSignType = record.data.taskSignType;
									this.infoPanel.items.get(0).setText('开始时间：'+createTime);
									this.infoPanel.items.get(1).setText('截至时间：'+taskLimitTime);
									if(taskSignType==1){
										this.infoPanel.items.get(2).setText('决议方式：投票');
										//this.infoPanel.items.get(2).setText('决议方式：投票(注：系统设置需有'+voteCounts+'个人投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
									}else{
										this.infoPanel.items.get(2).setText('决议方式：百分比');
										//this.infoPanel.items.get(2).setText('决议方式：百分比(注：系统设置需有'+voteCounts+'%投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
									}
									
				          	
				          	
					});
            }
			}
    	});
    //	this.store.addListeners('load',function( this1, records, successful, operation, eOpts ){
    //	var data =this1.data.items[0];
    		/*	var createTime=record.data.createTime;
									var taskLimitTime=record.data.taskLimitTime;
									var voteCounts=record.data.voteCounts;
									var taskSignType = record.data.taskSignType;
									this.infoPanel.items.get(0).setText('开始时间：'+createTime);
									this.infoPanel.items.get(1).setText('截至时间：'+taskLimitTime);
									if(taskSignType==1){
										this.infoPanel.items.get(2).setText('决议方式：投票');
										//this.infoPanel.items.get(2).setText('决议方式：投票(注：系统设置需有'+voteCounts+'个人投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
									}else{
										this.infoPanel.items.get(2).setText('决议方式：百分比');
										//this.infoPanel.items.get(2).setText('决议方式：百分比(注：系统设置需有'+voteCounts+'%投票通过则流程往下流转至<审保会决议确认>,否则打回至<业务主管审核上报材料>。)');
									}*/
    		
    	//})
    	
    	
    	
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("职务","人员","投票意见","处理时间","会签任务","会签情况","总体意见"
    	 ); 
    	  var value = new Array(data.position,data.voteName,data.isAgree,data.voteTime,data.activityName,'第'+data.sbhTimes+'次会签',data.comments
    	);  
    	 var xtype = new Array(null,null,null,null,null,null,"textareafield");
          getListDetail(label,value,xtype);
		    

}
});
