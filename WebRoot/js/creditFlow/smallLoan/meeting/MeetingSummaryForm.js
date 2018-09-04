MeetingSummaryForm = Ext.extend(Ext.Panel, {
	projId : 0,
	isReadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined")
			{
			      this.projId=_cfg.projectId;
			      this.businessType=_cfg.businessType;
			}
		if (typeof(_cfg.isReadOnly) != 'undefined') {
			this.isReadOnly = _cfg.isReadOnly;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		MeetingSummaryForm.superclass.constructor.call(this, {
			border : false,
			baseCls:'my-panel-no-border',
//			layout:'form',
			items:[
				this.MeetingSummaryForm
			]
		});
		
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		
		this.MeetingSummaryForm = new Ext.Panel({
		//	frame : true,
			autoHeight : true,
			border : false,
//			layout : "form",
			items :[{
				autoHeight : true,
				layout : "column",
				// bodyStyle : 'margin-left: 5%;',
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					labelWidth : 60
				},
				items : [/*{
				xtype : 'hidden',
				name : 'preHandler',
				value : 'slConferenceRecordService.saveAfterFlow'
			},*/{
				name : 'slConferenceRecord.projectId',
				xtype : 'hidden',
				value : this.projId
			},{
				name : 'slConferenceRecord.businessType',
				xtype : 'hidden',
				value : this.businessType
			},{
//				id : 'conforenceId',
				name : 'slConferenceRecord.conforenceId',
				xtype : 'hidden'
			}, {
				columnWidth : .5,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items : [{
					fieldLabel : '会议时间',
					readOnly : this.isReadOnly,
					name : 'slConferenceRecord.conforenceTime',
					//xtype : 'datefield',
					allowBlank : false,
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s'
					//value : new Date()
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items : [{
					//id : 'recordPersonName',
					name : 'slConferenceRecord.recordPersonName',
					readOnly : this.isReadOnly,
					fieldLabel : '记录人员',
					allowBlank : false,
					xtype : 'combo',
					triggerClass : 'x-form-search-trigger',
					// emptyText : '点击选择人员----------------',
					editable : false,
					scope : this,
					onTriggerClick : function() {
						var obj = this;
						var appuerIdObj=obj.nextSibling();
						new UserDialog({
							userIds:appuerIdObj.getValue(),
							userName:obj.getValue(),
							single : false,
							callback : function(uId, uname) {
								
								// alert(uId);
								// aert(uname);
//								Ext.getCmp('recordPersonId').setValue(uId);
//								Ext.getCmp('recordPersonName').setValue(uname);
								obj.setValue(uId);
								obj.ownerCt.ownerCt.getCmpByName('slConferenceRecord.recordPersonId').setValue(uId);
								obj.setRawValue(uname);
								appuerIdObj.setValue(uId);
//								this.previousSibling().getCmpByName('slConferenceRecord.recordPersonId').setValue(uId);
//								this.getCmpByName('slConferenceRecord.recordPersonName').setValue(uname);
							}
						}).show();

					}
				},{
				//id : 'recordPersonId',
				name : 'slConferenceRecord.recordPersonId',
				xtype : 'hidden'
			}]
			}, {
				columnWidth : 1,
				defaults : {
					anchor : '100%'
				},
				layout : 'form',
				items : [{
					fieldLabel : '会议地点',
					name : 'slConferenceRecord.conforencePlace',
					readOnly : this.isReadOnly,
					xtype : 'textfield',
					allowBlank : false,
					maxLength : 200
				}]
			}, {
				columnWidth : 1,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items : [{
					//id : 'jionPersonName',
					name : 'slConferenceRecord.jionPersonName',
					readOnly : this.isReadOnly,
					fieldLabel : '参与人员',
					allowBlank : false,
					xtype : 'combo',
					triggerClass : 'x-form-search-trigger',
					// emptyText : '点击选择人员----------------',
					editable : false,
					scope : this,
					onTriggerClick : function() {
						var obj = this;
						var appuerIdObj=obj.nextSibling();
						new UserDialog({
							single : false,
							userIds:appuerIdObj.getValue(),
							userName:obj.getValue(),
							callback : function(uId, uname) {
								
								// alert(uId);
								// aert(uname);
//								Ext.getCmp('jionPersonId').setValue(uId);
//								Ext.getCmp('jionPersonName').setValue(uname);
								obj.setValue(uId);
								obj.ownerCt.ownerCt.getCmpByName('slConferenceRecord.jionPersonId').setValue(uId);
								obj.setRawValue(uname);
								appuerIdObj.setValue(uId);
//								this.previousSibling().getCmpByName('slConferenceRecord.jionPersonId').setValue(uId);
//								this.getCmpByName('slConferenceRecord.jionPersonName').setValue(uname);
							}
						}).show();

					}

				},{
				//id : 'jionPersonId',
				name : 'slConferenceRecord.jionPersonId',
				xtype : 'hidden'
			}]
			}, {
				columnWidth : .5,
				defaults : {
					anchor : '100%'
				},
				layout : 'form',
				items : [{
					fieldLabel : '决议方式',
					allowBlank : false,
					editable : false,
					lazyInit : false,
					forceSelection : false,
					xtype : 'dicIndepCombo',
					//itemVale : 178,
					nodeKey : 'resolutionMethods',
					isDisplayItemName : false,
					hiddenName : 'slConferenceRecord.decisionType',
					readOnly : this.isReadOnly,
					listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
							}
						}
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				defaults : {
					anchor : '100%'
				},
				items : [{
					//id :'slConferenceRecord.conferenceResult',
					fieldLabel : '会议结果',
					xtype : 'dicIndepCombo',
					allowBlank : false,
					editable : false,
					lazyInit : false,
					forceSelection : false,
					//itemVale : 1151,
					nodeKey :'meetingResult',
					isDisplayItemName : false,
					hiddenName : 'slConferenceRecord.conferenceResult',
					readOnly : this.isReadOnly,
					listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								var thisBusType = this.businessType;
								st.on("load", function() {
									//remove掉有条件通过项
									if(thisBusType == 'Guarantee'){
										st.remove(st.getAt(1));
									}
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
							}
						}
					
									}]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [{
					fieldLabel : '会议纪要',
					scope :this,
					items : [new MeetingSummaryUpload({
						projectId : this.projId,
						businessType :this.businessType,
						isHidden : this.isHidden						
					})]
				}]
			}]
			}]
			
		});
		this.autoLoadData();
		
		
	},
	autoLoadData : function(){
		this.MeetingSummaryForm.loadData({
			root:'data',
			preName:'slConferenceRecord',
			url:__ctxPath+'/project/getByProjectIdSlConferenceRecord.do?projId='+this.projId+"&businessType="+this.businessType, //${projectId}
			scope:this,
			success:function(resp,options){
	              var result=Ext.decode(resp.responseText);
	              if(result.data == null){
	              	return false;
	              }
	              this.MeetingSummaryForm.getCmpByName('slConferenceRecord.conferenceResult').setValue(result.data.conferenceResult);
	              this.MeetingSummaryForm.getCmpByName('slConferenceRecord.decisionType').setValue(result.data.decisionType);
	              this.MeetingSummaryForm.getCmpByName('slConferenceRecord.recordPersonId').setValue(result.data.recordPersonId);
	              this.MeetingSummaryForm.getCmpByName('slConferenceRecord.jionPersonId').setValue(result.data.jionPersonId);
	              this.MeetingSummaryForm.getCmpByName('slConferenceRecord.conforenceTime').setValue(new Date(getDateFromFormat(result.data.conforenceTime, "yyyy-MM-dd HH:mm:ss")));
//	              this.getCmpByName('slConferenceRecord.conferenceResult').setValue(result.data.conferenceResultStr);
//	              this.getCmpByName('slConferenceRecord.decisionType').setValue(result.data.decisionTypeStr);
//	              this.getCmpByName('slConferenceRecord.recordPersonName').setValue(result.data.recordPersonName);
//	              this.getCmpByName('slConferenceRecord.jionPersonName').setValue(result.data.jionPersonName);
	              //Ext.getCmp('slConferenceRecord.conferenceResult').setValue(result.data.conferenceResult);
	              //Ext.getCmp('slConferenceRecord.decisionType').setValue(result.data.decisionType);
//	              Ext.getCmp('slConferenceRecord.recordPersonName').setValue(result.data.recordPersonName);
//	              Ext.getCmp('slConferenceRecord.jionPersonName').setValue(result.data.jionPersonName);
			}

		});
	}
	
})
