/**
 * @author lisl
 * @class FunctionsOperationPanel
 * @description (项目信息)功能操作
 * @extends Ext.Panel
 */
ProjectFunctionsOperation = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ProjectFunctionsOperation.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			items : [this.panel]
		});
	},
	initUIComponents : function() {
		this.taskId = this.record.data.taskId;
		this.activityName = this.record.data.activityName;
		this.projectStatus = this.record.data.projectStatus;
		if (typeof(this.record.data.bmStatus) != 'undefined') {
			this.bmStatus = this.record.data.bmStatus;
		}
		this.enabled = false;
		this.isActivityComboEdit = false;
		if (this.projectStatus != 2 && this.projectStatus != 3
				&& this.bmStatus != 2 && this.bmStatus != 3) {// 非项目完成或者是终止项目状态下
			Ext.Ajax.request({
				url : __ctxPath + '/flow/isMultiActivitysProcessActivity.do',
				params : {
					taskId : this.taskId
				},
				scope : this,
				method : 'post',
				success : function(resp, op) {
					var res = Ext.util.JSON.decode(resp.responseText);
					if (res.isMulti) {
						this.isActivityComboEdit = true;
					}
				},
				failure : function() {
					Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
				}
			});
			if (this.activityName != "主管意见" && this.activityName != "审贷会决议"
					&& this.activityName != "线上审保会集体决议") {// 内部指定处理人员，不容随意更换
				Ext.Ajax.request({
					url : __ctxPath + '/flow/usersProcessActivity.do',
					scope : this,
					params : {
						isParentFlow : false,
						isGetCurrent : true,
						taskId : this.taskId,
						activityName : this.activityName
					},
					success : function(response, options) {
						var result = Ext.decode(response.responseText);
						this.userName = [result.userNames];
					}
				});
				this.enabled = true;
			}
		}
		this.panel = new Ext.Panel({
			layout : {
				type : 'hbox',
				pack : 'left'
			},
			items : [{
				xtype : 'button',
				width : 70,
				margins : '0 3 0 0',
				iconCls : "btn-users-sel",
				text : '任务换人',
				disabled : (!this.enabled == false && !isGranted(this.granteTaskHandlerStr) == false)
						? false
						: true,
				scope : this,
				handler : function() {
					new ProjectTaskHandler({
						record : this.record,
						userName : this.userName,
						idPrefix : this.idPrefix,
						idPrefix_edit : this.idPrefix_edit,
						isActivityComboEdit : this.isActivityComboEdit
					}).show();
				}
			}, {
				xtype : 'button',
				iconCls : "btn-changeTask",
				width : 70,
				margins : '0 3 0 0',
				text : '流程跳转',
				scope : this,
				disabled : (!this.enabled == false && !isGranted(this.grantePathChangeStr) == false)
						? false
						: true,
				handler : function() {
					new ProjectPathChange({
						record : this.record,
						idPrefix : this.idPrefix,
						idPrefix_edit : this.idPrefix_edit
					}).show();
				}
			}, {
				xtype : 'button',
				iconCls : "btn-close1",
				width : 70,
				margins : '0 0 0 0',
				text : '终止项目',
				scope : this,
				disabled : (!this.enabled == false && !isGranted(this.granteStopProStr) == false)
						? false
						: true,
				handler : function() {
					new ProjectStop({
						record : this.record,
						idPrefix : this.idPrefix,
						idPrefix_edit : this.idPrefix_edit
					}).show();
				}
			}]
				// end of columns
		})
	}// 初始化组件
});