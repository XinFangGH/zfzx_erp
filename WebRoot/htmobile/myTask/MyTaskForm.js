
/**
 * 我的待办表单
 * by cjj
 */

Ext.define('htmobile.myTask.MyTaskForm', {
    extend: 'htmobile.myTask.TableForm',
    name: 'MyTaskForm',
    constructor: function (config) {
		config = config || {};
    	this.activityName=config.activityName;
    	
        this.callParent([config]);
	},

	formSubmit:function(){
		var tabpanel = this.up('tabpanel');
		var tabpanelconfig = tabpanel.config;
		var mainTalbeItems = tabpanel.mainTalbeItems;
		var approveSetting = tabpanel.approveSetting;
		
		var comments=approveSetting.getCmpByName('comments').getValue();
		if(Ext.isEmpty(comments)){
			   	Ext.Msg.alert('',"意见与说明不能为空");
			   	return false;
			}
		
		
		
		// 会签
		var signVoteType = null;
		if(tabpanelconfig.isSignTask){
			var signField = approveSetting.getCmpByName('signField');
			var signItems = signField.getInnerItems();
			for(var idx=0;idx<signItems.length;idx++){
				var vote = signItems[idx];
				if(vote.getChecked()){
					var voteVal = vote.getValue();
					signVoteType = voteVal!=null?voteVal:0;
					break;
				}
			}
		}
	//	var preHandler= formpanel.getCmpByName("preHandler");
		// 短讯提醒
		var sendMsg = approveSetting.getCmpByName('sms');
		// 邮件提醒
		var sendMail = approveSetting.getCmpByName('mail');
		// 是否驳回
		var back = this.config.active==2?'true':'false';
		// 选择流程路径
		var   dest=Ext.getCmp("dest"); 
		var destvalue=dest.getValue();
		
		
		var businessType=tabpanel.businessType;
		var projectId=tabpanel.projectId;
		var activityName=tabpanel.activityName
		//！--君和源项目
		 Ext.Ajax.request({
					url : __ctxPath+ '/htmobile/getFlowToInfoVmInfo.do',
					params:{
					    businessType:businessType,
					    projectId:projectId
					},
					asyn:false,
				    success : function(response) {
						var obj = Ext.util.JSON.decode(response.responseText);
						var projectMoney = obj.projectMoney;
					    var systemMoney = obj.controlMoney;
					    var  vehicleproLoanAmount=obj.vehicleproLoanAmount;
				/*		var  systemMoney=50000;
						var projectMoney=400000;*/
					    
						if(systemMoney=="0" ||systemMoney==0){
						
						}else{
							
						//	  车贷
							 
									//客户经理受理
								if(activityName=="客户经理受理" &&(destvalue=="合同签署" || destvalue=="业务副总复核")){
									if(Ext.isEmpty(projectMoney)){
									   	Ext.Msg.alert('',"贷款金额不能为空");
									   	return false;
									}
									if(projectMoney<=vehicleproLoanAmount && projectMoney<=systemMoney){
									    destvalue="合同签署";
									}
									if(projectMoney>vehicleproLoanAmount || projectMoney>systemMoney){
									    destvalue="业务副总复核";
									}
								}
								
								//业务副总复核
								if(activityName=="业务副总复核" &&(destvalue=="风控委风险审核" || destvalue=="合同签署")){
									if(Ext.isEmpty(projectMoney)){
									   	Ext.Msg.alert('',"贷款金额不能为空");
									   	return false;
									}
									if(projectMoney<=systemMoney){
									    destvalue="合同签署";
									}
									if(projectMoney>systemMoney){
									    destvalue="风控委风险审核";
									}
						}
						}
					
					        
						//	 车贷
							 
						
						
						
						
								
						   //系统代码不许改动
							Ext.Msg.confirm('确认',"确认提交到\""+destvalue+"\"节点",function(buttonId){
							if (buttonId === 'yes') {
								
								  if(tabpanelconfig.extForm){
									mainTalbeItems.formSubmit({
										voteAgree: voteAgree,
										mainTalbeItems: mainTalbeItems
									});
								}else{
								  if(tabpanel.validate(tabpanel,signVoteType)){
									mainTalbeItems.submit({
										url : __ctxPath + "/flow/nextProcessActivity.do",
								//		url: __ctxPath+'/htmobile/doNextTask.do',
								        params: {
								        	'comments': approveSetting.getCmpByName('comments').getValue(),
								        	'taskId':tabpanelconfig.taskId,
											'defId':tabpanelconfig.defId,
										//	'activityName':tabpanelconfig.activityName,
											'destName':destType=="task"?destvalue:null,            //普通节点为目标节点的名称，决策，合并啥的都是传空
											'signalName':destvalue, //目标的节点名称
								        	'signVoteType':signVoteType,
								        	'back':back,
								        	'sendMsg': sendMsg.getChecked()?'true':'false',
								        	'sendMail': sendMail.getChecked()?'true':'false',
								     //   	'preHandler':preHandler,
								        	'afterHandler':tabpanelconfig.afterHandler
								        	
								        	/*	useTemplate : this.useTemplate,
								                flowAssignId : flowAssignId,
								                signalName : signalName,*/
										},
								        method: 'POST',
								        success: function(form,action,response) {
								       	tabpanelconfig.callback.call();
								        	Ext.Msg.alert('提示','提交成功!');
								        	mobileNavi.pop();
								        },
								        failure: function(form,action,response){
											var obj = Ext.util.JSON.decode(response);
											Ext.Msg.alert('提示', obj.msg);
								        }
									});
								}
							}
								
								}
								
								
							});
						
							
							//系统代码不许改动
							
						
				}
			});
		//--！君和源项目
			
			
			
/*	如果不许要判断就把--！君和源项目里面内容全删掉，放出下面这个代码					
						   //系统代码不许改动
							Ext.Msg.confirm('确认',"确认提交到\""+destvalue+"\"节点",function(buttonId){
							if (buttonId === 'yes') {
								
								  if(tabpanelconfig.extForm){
									formpanel.formSubmit({
										voteAgree: voteAgree,
										formpanel: formpanel
									});
								}else{
								  if(tabpanel.validate(tabpanel,signVoteType)){
									mainTalbeItems.submit({
										url : __ctxPath + "/flow/nextProcessActivity.do",
								//		url: __ctxPath+'/htmobile/doNextTask.do',
								        params: {
								        	'comments': approveSetting.getCmpByName('comments').getValue(),
								        	'taskId':tabpanelconfig.taskId,
											'defId':tabpanelconfig.defId,
										//	'activityName':tabpanelconfig.activityName,
											'destName':destType=="task"?destvalue:null,            //普通节点为目标节点的名称，决策，合并啥的都是传空
											'signalName':destvalue, //目标的节点名称
								        	'signVoteType':signVoteType,
								        	'back':back,
								        	'sendMsg': sendMsg.getChecked()?'true':'false',
								        	'sendMail': sendMail.getChecked()?'true':'false',
								        //	'preHandler':preHandler,
								        	'afterHandler':tabpanelconfig.afterHandler
								        	
								        		useTemplate : this.useTemplate,
								                flowAssignId : flowAssignId,
								                signalName : signalName,
										},
								        method: 'POST',
								        success: function(form,action,response) {
								       	tabpanelconfig.callback.call();
								        	Ext.Msg.alert('提交成功');
								        	mobileNavi.pop();
								        },
								        failure: function(form,action,response){
											var obj = Ext.util.JSON.decode(response);
											Ext.Msg.alert('', obj.msg);
								        }
									});
								}
							}
								
								}
								
								
							});
						
							
							//系统代码不许改动	
*/	
	},
	
	validate:function(tabpanel,signVoteType){
		var tabpanelconfig = tabpanel.config;
		if(tabpanelconfig.isSignTask){
			if(signVoteType==null){
				Ext.Msg.alert('', '请进行会签设置');
				return false;
			}
		}
		return true;
	},
	progressRunGridPanel:function(){
		var taskId=this.taskId;
	    mobileNavi.push(
		Ext.create('htmobile.creditFlow.public.processFomlist',{
			taskId:taskId/*,
			filterableNodeKeys:filterableNodeKeys*/
		        	})
		    	);
	
	}

});
