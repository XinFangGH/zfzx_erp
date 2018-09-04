// define xtype will be used in *.vm
Ext.ns('Ext.flow.form');
/**
 * 选择下一节点执行人
 * flowRunInfo 自动保存改字段
 */
Ext.flow.form.submitPerson = Ext.extend(Ext.Panel,{
			layout : "form",
			labelWidth : 85,
			submitLabel:'提交至',
			submitAllowBlank:false,
			constructor:function(config) {
					config = config || {};
					config.listeners = config.listeners || {};
					if(config.layout){
						this.layout = config.layout
					}
					if(config.labelWidth){
						this.labelWidth = config.labelWidth;
					}
					if(config.submitLabel){
						this.submitLabel = config.submitLabel;
					}
					if(config.submitAllowBlank){
						this.submitAllowBlank = config.submitAllowBlank;
					}
					Ext.applyIf(this,config);
					var single = typeof(this.single)!="undefined"?this.single:true;
					var jsArr = [
							__ctxPath + '/js/selector/UserDialog.js'
					];
					$ImportSimpleJs(jsArr, function(){},this);
					Ext.flow.form.submitPerson.superclass.constructor.call(this, {
					items : [{
						columnWidth : .85, 
						xtype:"hidden",
						name:"flowAssignId"
					},{
						fieldLabel :this.submitLabel,
						xtype : "combo",
						columnWidth : .85, 
						//allowBlank:false,
					//	allowBlank : this.submitAllowBlank,
						bodyStyle : 'padding-left:0px;',
						labelAlign : 'right',
						editable : false,
						//emptyText:'请选择执行人',
						labelWidth : this.labelWidth,
						//blankText : "提交至执行人不能为空，请正确填写!",
						triggerClass : 'x-form-search-trigger',
						name : "assignNames",
						anchor : this.anchor==null?"85%":this.anchor,
						onTriggerClick : function(cc) {
						     var obj = this;
						     var appuerIdObj=obj.previousSibling();
							 new UserDialog({
								single : single,//风险专员只能选择一个 
								userIds:appuerIdObj.getValue(),
								userName:obj.getValue(),
								title:"执行人",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();
						}
					}]
				});
				},
				initUIComponents : function(){
					
				}
		})
	//打回 或终止  下一节点的提交人  不必填，提交则必填     
	Ext.flow.form.validateSubmitPerson = function(outpanel){
		if(outpanel.ownerCt&&outpanel.ownerCt.ownerCt){
			 var submitGroup = outpanel.ownerCt.ownerCt.getCmpByName('taskSubmit')||outpanel.ownerCt.ownerCt.getCmpByName('taskSubmit2')
			 var checkedGroupLabel = "";
			 if(submitGroup){
				  checkedGroupLabel = submitGroup.getValue().boxLabel
			 }
			 if(checkedGroupLabel.indexOf("退回至")==0||checkedGroupLabel.indexOf("结束")!=-1 ){
	        	 	if(outpanel.getCmpByName("flowAssignId")){
	            	 	outpanel.getCmpByName("flowAssignId").setDisabled(true);
	            	 	outpanel.getCmpByName("assignNames").setDisabled(true);
	        	 	}
			 }
		}
	}
		//包含某些字段验证   才验证  比如 线上，则线上成员必填 ，线下，线上成员不必填
	Ext.flow.form.validateSubmitByName = function(outpanel,keyWords){
		 var submitGroup = outpanel.ownerCt.ownerCt.getCmpByName('taskSubmit')||outpanel.ownerCt.ownerCt.getCmpByName('taskSubmit2')
		 var checkedGroupLabel = "";
		 if(submitGroup){
			  checkedGroupLabel = submitGroup.getValue().boxLabel
		 }
		 if(checkedGroupLabel.indexOf(keyWords)==-1 ){
        	 	if(outpanel.getCmpByName("flowAssignId")){
            	 	outpanel.getCmpByName("flowAssignId").setDisabled(true);
            	 	outpanel.getCmpByName("assignNames").setDisabled(true);
        	 	}
		 }
	}
	Ext.reg('submitPerson', Ext.flow.form.submitPerson); 
