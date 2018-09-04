
/**
 * 手机表单
 * by cjj
 */

Ext.define('htmobile.myTask.TableForm', {
	extend: 'Ext.TabPanel',
    name: 'TableForm',
    constructor: function (config) {
		config = config || {};
		this.taskId = config.taskId;
		this.defId = config.defId;
		this.activityName = config.activityName;
		this.newjs=config.newjs;
    	var mainHidden = true;
    	var reg=new RegExp("<br/>","g");
      	var  vm=eval(this.newjs.replace(reg,"\r\n"));
	    var mainTalbeItems = Ext.create(vm,{
			taskId:config.taskId,
    		defId:config.defId,
    		preTaskName:config.preTaskName,
    		isSignTask:config.isSignTask,
    		trans:config.trans,
    		taskName:config.taskName,
    		vars:config.vars,
    		activityName:config.activityName
		});
		this.projectId=mainTalbeItems.projectId;
		this.businessType=mainTalbeItems.businessType;
    	var toolbar = Ext.create('Ext.Toolbar',{
			docked: 'top'
		});
    	
		//<font style='color:#a7573b;'>--</font>
    	toolbar.add({
			xtype:'label',
			html:"<div style='text-align:left;'><font style='color:white;font-size:16px'>"+config.taskName+"</font><br></div><div style='text-align:right;'><font style='color:white;font-size:16px'>---"+config.activityName+"</font></div>"
		});
		
    	/*// 普通节点
    	this.preTaskName = config.preTaskName;
    	if(this.preTaskName){
    		toolbar.add({
                xtype: 'button',
	            iconCls: 'mytaskBack',
	            iconMask: true,
	            text: '驳回',
	            active:2,
                handler: this.formSubmit
            });
    	}*/
    	
    	// 审批设置
    	var approveSetting = Ext.create('Ext.form.Panel');
    	
        var toolbar1 = Ext.create('Ext.Toolbar', {
        			style:'background-color:white;',
					docked : 'bottom',
					defaults:{
						style:'border-radius:3px;',
						xtype: 'button',
						scope : this
					},
					items : [{
								xtype : 'spacer'
							},{
					            text: '执行下一步',
					            width:150,
					            handler: this.formSubmit
					        },{
								xtype : 'spacer'
							},{
								text : '意见说明',
								width:150,
								handler : this.progressRunGridPanel
							}, {
								xtype : 'spacer'
							}]
				});
		// 下一任务路径
		var trans = config.trans;
		Ext.define('transModel', {
		    extend: 'Ext.data.Model',
		    config: {
		        fields: [
		            {name: 'destType', type: 'string'},
		            {name: 'destination', type: 'string'},
		            {name: 'name', type: 'string'},
		            {name: 'source', type: 'string'}
		        ]
		    }
		});
		
		for (var i = 0; i <trans.length; i++) {
			
			destName = trans[i].destination;
			destType = trans[i].destType;
			// 下一节点为分支及fork节点
			if ('decision' == destType || 'fork' == destType) {
				Ext.Ajax.request({
					url : __ctxPath + '/flow/outerTransProcessActivity.do?taskId='+ this.taskId,
					params : {
						nodeName : destName,
						isParentFlow : false
					},
					scope : this,
			//		async : true, 
					success : function(resp, options) {
						var outers = Ext.decode(resp.responseText);
						if (outers.length > 0) {
							var simpleStoreData = new Array();　
							var defaultValue = "";//默认值
							for (var i = 0; i < outers.length; i++) {
								if (!outers[i][6]&& !(outers[i][1].indexOf("终止") != -1)&& !(outers[i][1].indexOf("结束") != -1)) {
									boxValue = "退回至<"+ outers[i][1].replace(/[0-9]/g, '')+ ">";
								} else {
									boxValue = outers[i][1].replace(/[0-9]/g,'');
								}
								if(boxValue.indexOf("退回至")==-1&&boxValue.indexOf("终止")==-1&&boxValue.indexOf("结束")==-1){
									defaultValue=boxValue;
								}
								var temp = new Array();
								var temp={"text":boxValue+"["+outers[i][4]+"]","value":outers[i][1],"destName":outers[i][1],"signalName":outers[i][1]}
								simpleStoreData.push(temp);
								
							}
							
							var jumpCombobox = Ext.create("Ext.field.Select",{  
								    name: 'dest',
								    id:'dest',
								//	name : 'taskSubmit',
								    labelWidth:"31%",
									label: '任务提交至',
								    displayField: 'text',
           	                        valueField: 'value',
									options:simpleStoreData,
									value:defaultValue,
									destName:destType
								//	hiddenName : 'signalName',
									
							});
							approveSetting.doItemLayoutAdd(jumpCombobox);
						} 
					}
				});
				
			} else if ('join' == destType) {
				Ext.Ajax.request({
					url : __ctxPath + '/flow/outerTransProcessActivity.do?taskId=' + this.taskId,
					params : {
						nodeName : destName,
						isParentFlow : false
					},
					scope : this,
	//				async:false,
					success : function(resp, options) {
						var outers = Ext.decode(resp.responseText);
						if (outers.length > 0) {
							var simpleStoreData = new Array();　
							var defaultValue = "";//默认值
							for (var i = 0; i < outers.length; i++) {
								if (!outers[i][6]&& !(outers[i][1].indexOf("终止") != -1)&& !(outers[i][1].indexOf("结束") != -1)) {
									boxValue = "退回至<"+ outers[i][1].replace(/[0-9]/g, '')+ ">";
								} else {
									boxValue = outers[i][1].replace(/[0-9]/g,'');
								}
								if(boxValue.indexOf("退回至")==-1&&boxValue.indexOf("终止")==-1&&boxValue.indexOf("结束")==-1){
									defaultValue=boxValue;
								}
								var temp = new Array();
								var temp={"text":boxValue,"value":outers[i][1]}
								simpleStoreData.push(temp);
							}
							
									var jumpCombobox = Ext.create("Ext.field.Select",{  
								    name: 'dest',
								    id:'dest',
								//	name : 'taskSubmit',
									label: '任务提交至',
									labelWidth:"31%",
								    displayField: 'text',
           	                        valueField: 'value',
									options:simpleStoreData,
									value:defaultValue,
									destName:destType
								//	hiddenName : 'signalName',
									
							});
							approveSetting.doItemLayoutAdd(jumpCombobox);
						} 
					}
				});

			} else {// 下一节点为普通任务节点
							var jumpCombobox = Ext.create("Ext.field.Select",{  
								    name: 'dest',
								    id:'dest',
								//	name : 'taskSubmit',
								    labelWidth:"31%",
									label: '任务提交至',
								    displayField: 'text',
           	                        valueField: 'value',
									options:[{"text":destName,"value":destName}],
									value:destName,
									destName:destType
								//	hiddenName : 'signalName',
									
							});
							approveSetting.doItemLayoutAdd(jumpCombobox);
			
			}
		}
		
		/*
		var outerTrans=config.outerTrans;
		var transStore = Ext.create("Ext.data.Store", {
		    model: "transModel",
		    data: trans
		});
		approveSetting.add({
			xtype: 'selectfield',
			name: 'dest',
			required: true,
			label: '执行路径',
			store: transStore,
			displayField: 'destination',
			valueField: 'destination'
		});*/
		
		/*approveSetting.add({
			xtype: 'selectfield',
			name: 'dest',
			required: true,
			label: '执行路径',
			options: transOptions,
			displayField: 'destination',
			valueField: 'destination'
		});*/
    	// 会签节点
    	this.isSignTask = config.isSignTask;
    	if(this.isSignTask){
    		var signPanel = Ext.create('Ext.form.FieldSet',{
    			itemId:'signField',
    			title:'会签设置',
    			items:[{
                    xtype: 'radiofield',
                    name : 'signVoteType',
                    value: 1,
                    labelWidth:"31%",
                    label: '同意'
                },{
                    xtype: 'radiofield',
                    name : 'signVoteType',
                    value: 4,
                    labelWidth:"31%",
                    label: '有条件通过'
                },{
                    xtype: 'radiofield',
                    name : 'signVoteType',
                    value: 3,
                    labelWidth:"31%",
                    label: '打回'
                },{
                    xtype: 'radiofield',
                    name : 'signVoteType',
                    value: 2,
                    labelWidth:"31%",
                    label: '否决'
                }]
    		});
    		approveSetting.add(signPanel);
    	}
    	
    	approveSetting.add({
    		xtype:'fieldset',
    		items:[{
    			// 是否短讯提醒
                xtype: 'checkboxfield',
                name : 'sms',
                label: '短讯提醒'
            },{
            	// 是否邮件提醒
                xtype: 'checkboxfield',
                name : 'mail',
                label: '邮件提醒'
            }]
    	});
    	
    	// 审批意见
    	approveSetting.add({
            xtype: 'textareafield',
            label: '审批意见',
            maxRows: 4,
            name: 'comments',
            value:config.comments,
            labelAlign:"top"
    	});
    	this.approveSetting=approveSetting;
    	this.mainTalbeItems=mainTalbeItems;
        Ext.apply(config,{
        	title:'项目详情',
            layoutOnTabChange: true,
            items : [toolbar1, {
								title : '流程表单',
								items : [{
											items : this.mainTalbeItems
										}],
								scrollable : {
									direction : 'vertical',
									directionLock : true
								}
							}, {
								title : '流程图',
								items : Ext.create('htmobile.myTask.JbpmImage',
										{
											taskId : this.taskId
										}),
								scrollable : {
									direction : 'both',
									directionLock : true
								}
							}, {
								title : '审批设置',
								layout : 'fit',
								items : approveSetting,
								scrollable : {
									direction : 'vertical',
									directionLock : true
								}
							}]
        });
        this.callParent([config]);
	}
});

