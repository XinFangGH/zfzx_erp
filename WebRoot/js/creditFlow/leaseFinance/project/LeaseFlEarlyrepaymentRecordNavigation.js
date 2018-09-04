/**
 * @author lisl
 * @class LeaseFlEarlyrepaymentRecordNavigation
 * @description 融资租赁项目信息页面导航
 * @extends Ext.Panel
 */
LeaseFlEarlyrepaymentRecordNavigation = Ext.extend(Ext.Panel, {
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
		Ext.applyIf(this, _cfg);
		LeaseFlEarlyrepaymentRecordNavigation.superclass.constructor.call(this, {
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
								disabled : !(isGranted('_showTqhkFlowImg_'+this.projectStatus)||isGranted('_seeshowTqhkFlowImg_')+this.projectStatus),
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'flRepaymentAheadOfTimeFlow'
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
								disabled : !(isGranted('_txhkRecords_'+this.projectStatus)||isGranted('_seetxhkRecords_'+this.projectStatus)),
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'flRepaymentAheadOfTimeFlow'
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
								disabled : !(isGranted('_tqhkTaskChange_'+this.projectStatus)||isGranted('_seetqhkTaskChange_'+this.projectStatus)),
								hidden : this.flag=='see'?true:false,
								scope : this,
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'flRepaymentAheadOfTimeFlow'
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
														idPrefix : "FlLeaseFinanceProjectInfo_",
														idPrefix_edit : "FlLeaseFinanceProjectInfoEdit_",
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
								disabled : !(isGranted('_tqhkTaskJump_'+this.projectStatus)||isGranted('_seetqhkTaskJump_'+this.projectStatus)),
								hidden : this.flag=='see'?true:false,
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'flRepaymentAheadOfTimeFlow'
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
														idPrefix : "FlLeaseFinanceProjectInfo_",
														idPrefix_edit : "FlLeaseFinanceProjectInfoEdit_"
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
								disabled : !(isGranted('_tqhkTaskStop_'+this.projectStatus)||isGranted('_seetqhkTaskStop_'+this.projectStatus)),
								hidden : this.flag=='see'?true:false,
								handler : function() {
									Ext.Ajax.request({
										url : __ctxPath + '/flow/getByBusinessTypeProcessNameTableIdProcessRun.do',
										params : {
											slSuperviseRecordId : this.record.data.projectId,
											businessType : 'LeaseFinance',
											processName : 'flRepaymentAheadOfTimeFlow'
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
														idPrefix : "FlLeaseFinanceProjectInfo_",
														idPrefix_edit : "FlLeaseFinanceProjectInfoEdit_"
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