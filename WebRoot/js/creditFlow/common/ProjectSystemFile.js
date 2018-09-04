/**
 * @author lisl
 * @class ProjectSystemFilePanel
 * @description (项目信息)项目系统文件
 * @extends Ext.Panel
 */
ProjectSystemFile = Ext.extend(Ext.Panel, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				ProjectSystemFile.superclass.constructor.call(this, {
							region : 'center',
							layout : 'anchor',
							items : [this.panel]
						});
			},
			initUIComponents : function() {
				this.panel = new Ext.Panel({
					layout : {
						type : 'hbox',
						pack : 'left'
					},
					items : [{
								xtype : 'button',
								iconCls : "btn-advice",
								width : 109,
								margins : '0 1 0 0',
								text : '意见与说明记录',
								scope : this,
								disabled : !this.isSeeAgreements,
								handler : function() {
									new SlProcessRunView({runId : this.runId,projectStatus : this.projectStatus,bmStatus : this.bmStatus,businessType : this.businessType,isAutoHeight : false}).show();
								}
							}, {
								xtype : 'button',
								iconCls : "btn-users",
								width : 109,
								margins : '0 1 0 0',
								text : '项目参与人员',
								scope : this,
								disabled : !this.isSeePersons,
								handler : function() {
									new SlProcessFormView({runId : this.runId}).show();
								}
							}, {
								xtype : 'button',
								iconCls : "selectIcon",
								width : 109,
								margins : '0 0 0 0',
								text : '项目归档记录',
								scope : this,
								disabled : !this.isSeeFilingRecord,
								handler : function() {
									new SlFilingRecordView({projectId : this.projectId}).show();
								}
							}]
						//end of columns
				})
			}//初始化组件
		});