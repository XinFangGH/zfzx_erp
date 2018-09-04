/**
 * @author:
 * @class SlProcreditAssuretenetView
 * @extends Ext.Panel
 * @description []上传会议纪要
 * @company 北京智维软件有限公司
 * @createtime:
 */
DiligenceFormMeeting = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				DiligenceFormMeeting.superclass.constructor.call(this, {
							id : 'DiligenceFormMeeting',
							border:false,
							title:"上传会议纪要",
							items : []
						});
						
				this.loadData({
						url :  __ctxPath + '/project/getInfoSlSmallloanProject.do?taskId='+1,
						preName:['enterprise','person','slSmallloanProject'],
						root:'data'
					});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var jsArr = [__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
				 __ctxPath+'/js/commonFlow/EnterpriseShareequity.js',
				 __ctxPath+'/js/ourmain/SVEnterprisePersonView.js',//第三方保证
				 __ctxPath+'/js/commonFlow/MeetingSummaryForm.js'//会议纪要上传
				 
				];
				$ImportSimpleJs(jsArr, this.constructPanel,this);
			
			
			},
			constructPanel:function(){
				this.PeerMainInfoPanel=new ExtUD.Ext.PeerMainInfoPanel({isLoadShareequity:true});
				this.ProjectInfoPanel=new ExtUD.Ext.ProjectInfoPanel({});
				this.SVEnterprisePersonView=new SVEnterprisePersonView({isLoadThirdRalation:true,isEdit:true});
				this.MeetingSummaryForm=new MeetingSummaryForm({});
				this.outPanel=new Ext.Panel(
				{
						modal : true,
						labelWidth : 100,
						buttonAlign : 'center',
						layout:'form',
						border:false,
						url : __ctxPath + '/project/updateSlSmallloanProject.do',
						defaults : {
							anchor : '100%',
							xtype : 'fieldset',
							columnWidth : 1,
							collapsible : true,
							autoHeight : true
						},
						labelAlign : "right",
						frame:true,
						items:[
							{
									title : '主体信息',
									columnWidth : .8,
									id : 'mainPerson',
								
									items:[this.PeerMainInfoPanel]
							},{
									title : '项目信息',
									columnWidth : .8,
									id : 'project_more_info',
									bodyStyle : 'padding-left: 10%;',
									items:[this.ProjectInfoPanel]
							},{
									title : '第三方保证',
									items:[this.SVEnterprisePersonView]
							},{
									title : '上传会议纪要',
									width : "98%",
									//bodyStyle : 'padding-left: 10%;',
									items:[this.MeetingSummaryForm]
							},{
										columnWidth : .75,
										layout : "form",
										labelWidth : 150,
										items:[{
											xtype : "textarea",
											fieldLabel : "说明",
											name : "comments",
											anchor : '100%',
											blankText : "说明不能为空，请正确填写!",
											allowBlank : false}]
									
							}]
				
					});
				
				this.add(this.outPanel);
				this.doLayout();
	
			}
          
	
})
