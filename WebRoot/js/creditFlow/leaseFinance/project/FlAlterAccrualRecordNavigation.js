/**
 * @author lisl
 * @class FlAlterAccrualRecordNavigation
 * @description 融资租赁项目信息页面导航
 * @extends Ext.Panel
 */
FlAlterAccrualRecordNavigation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.flag) {
			this.flag = _cfg.flag;
		}
		if (_cfg.projectStatus) {
			this.projectStatus = _cfg.projectStatus;
		}
		if (_cfg.bmStatus) {
			this.bmStatus = _cfg.bmStatus;
		}
		Ext.applyIf(this, _cfg);
		FlAlterAccrualRecordNavigation.superclass.constructor.call(this, {
			items : [ {
						columnWidth : 1,
						layout : 'column',
						defaults : {
							anchor : '100%',
							style : "margin-left:5px;margin-top:5px",
							columnWidth : .1
						},
						items : [{
							layout : "form", // 从上往下的布局
							columnWidth : .08,
							items : [{
								xtype : 'panel',
								border : false,
								bodyStyle : 'margin-left:0px;margin-top:5px',
								width : 95,
								html : '<B><font class="x-myZW-fieldset-title">【流程控制】：</font></B>'
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '流程示意图',
								scope : this,
								disabled : !(isGranted('_showLilvFlowImg_fl'+this.projectStatus+this.bmStatus)||isGranted('_seeshowLilvFlowImg_fl'+this.projectStatus+this.bmStatus)),
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'FlAlterAccrualFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												showFlowImgWin(runId);
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-advice',
								anchor : '100%',
								text : '意见与说明记录',
								scope : this,
								disabled : !(isGranted('_lilvRecords_fl'+this.projectStatus+this.bmStatus)||isGranted('_seelilvRecords_fl'+this.projectStatus+this.bmStatus)),
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'FlAlterAccrualFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												new SlProcessRunView({
													runId : runId,
													projectStatus : this.projectStatus,
													businessType : this.businessType,
													isAutoHeight : false
												}).show();
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '任务换人',
								disabled : !(isGranted('_lilvTaskChange_fl'+this.projectStatus+this.bmStatus)||isGranted('_seelilvTaskChange_fl'+this.projectStatus+this.bmStatus)),
								hidden : this.flag=='see'?true:false,
								scope : this,
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'FlAlterAccrualFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												if(piId==null||piId==''||piId=='null'){
													Ext.ux.Toast.msg('提示信息', '该展期流程已经结束,不能进行展期任务换人操作!');
													return;
												}else{
													new ProjectTaskHandler({
														record : obj,
														userName : this.userName,
														idPrefix : "LeaseFinanceProjectInfo_",
														idPrefix_edit : "LeaseFinanceProjectInfoEdit_",
														isActivityComboEdit : false
													}).show();
												}
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-detail',
								anchor : '100%',
								text : '流程跳转',
								scope : this,
								disabled : !(isGranted('_lilvTaskJump_fl'+this.projectStatus+this.bmStatus)||isGranted('_seelilvTaskJump_fl'+this.projectStatus+this.bmStatus)),
								hidden : this.flag=='see'?true:false,
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'FlAlterAccrualFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												if(piId==null||piId==''||piId=='null'){
													Ext.ux.Toast.msg('提示信息', '该展期流程已经结束,不能进行展期流程跳转操作!');
													return;
												}else{
													new ProjectPathChange({
														record : obj,
														idPrefix : "LeaseFinanceProjectInfo_",
														idPrefix_edit : "LeaseFinanceProjectInfoEdit_"
													}).show();
												}
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
								}
							}]
						}, {
							layout : "form", // 从上往下的布局
							items : [{
								xtype : 'button',
								iconCls : 'btn-close1',
								anchor : '100%',
								text : '终止项目',
								scope : this,
								disabled : !(isGranted('_lilvTaskStop_fl'+this.projectStatus+this.bmStatus)||isGranted('_seelilvTaskStop_fl'+this.projectStatus+this.bmStatus)),
								hidden : this.flag=='see'?true:false,
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'FlAlterAccrualFlow'
										},
										method : 'post',
										success : function(response, request) {
											obj = Ext.util.JSON.decode(response.responseText);
											var runId = obj.data.runId;
											var piId = obj.data.piId;
											if(obj.success){
												if(piId==null||piId==''||piId=='null'){
													Ext.ux.Toast.msg('提示信息', '该展期流程已经结束,不能进行展期项目终止操作!');
													return;
												}else{
													new ProjectStop({
														record : obj,
														idPrefix : "LeaseFinanceProjectInfo_",
														idPrefix_edit : "LeaseFinanceProjectInfoEdit_"
													}).show();
												}
											}else{
												Ext.ux.Toast.msg('操作信息', '查询runId操作失败!');
											}
										},
										failure : function() {
											Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
										}
									})
								}
							}]
						}]
					}]
		});
	}
});