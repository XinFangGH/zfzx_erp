/**
 * @class PostponedProjectInfoEditPanel
 * @description 小贷展期项目信息编辑页面
 * @extends Ext.Panel
 */
PostponedProjectInfoEditPanel = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.projectRecord) {
			this.projectRecord = _cfg.projectRecord;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PostponedProjectInfoEditPanel.superclass.constructor.call(this, {
			id : 'PostponedProjectInfoEditPanel_' + this.projectRecord.data.projectId,// id属性值前缀,通过是否包含"Edit"来区分查看和编辑页面
			title : "编辑<" + "<font color='red'>"+this.record.data.AppUserName+"</font>"+">申请的展期项目",
			iconCls : 'btn-edit',
			border : false,
			autoHeight : true,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/smallLoan/project/PostponedProjectInfoEdit.js'// 项目信息
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);

	},// 初始化组件
	constructPanel : function() {
		this.projectStatus = this.projectRecord.data.projectStatus;
		this.taskId = this.projectRecord.data.taskId;
		this.activityName = this.projectRecord.data.activityName;
		this.isActivityComboEdit = false;
		this.enabled = false;
		this.taskNodeKey = "";
		this.taskSequence = 0;
		Ext.Ajax.request({
			url : __ctxPath + '/flow/freeTransProcessActivity.do?taskId='
					+ this.taskId,
			scope : this,
			success : function(response, options) {
				var res = Ext.util.JSON.decode(response.responseText);
				for (var i = 0; i < res.length; i++) {
					if (res[i][1] == this.activityName) {
						this.taskSequence = res[i][4];
						this.taskNodeKey = res[i][5];
					}
				}
			}
		});
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
			if (this.taskNodeKey != "slnExaminationArrangement") {// 内部指定处理人员，不容随意更换
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
		// 项目信息导航
		this.postponedProjectInfoNavigation = new PostponedProjectInfoNavigation({
			slSuperviseRecordId : this.record.data.id,
			record : this.projectRecord,
			isActivityComboEdit : this.isActivityComboEdit,
			enabled : this.enabled,
			flag : "edit" // edit表示编辑页面,see表示查看页面
		});
		// 项目信息
		this.postponedProjectInfo = new PostponedProjectInfoEdit({
			record : this.record,
			projectRecord : this.projectRecord,
			projectStatus : this.projectStatus
		});
		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			frame : true,
			autoHeight : true,
			defaults : {
				anchor : '100%',
				columnWidth : 1,
				collapsible : false
			},
			labelAlign : "right",
			items : [{
				xtype : 'panel',
				height : 115,
				title : '<font class="x-myZW-fieldset-title">展期项目总览(</font><font color="red">注：带图标按钮为弹窗式操作按钮，其余均为项目信息部分快速定位操作按钮</font><font class="x-myZW-fieldset-title">)</font>',
				collapsible : true,
				items : [this.postponedProjectInfoNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.postponedProjectInfo.setHeight(document.body.clientHeight - 280 + 160);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						var nowHeight = this.postponedProjectInfo.getHeight();
						this.postponedProjectInfo.setHeight(nowHeight - 160);//项目信息部分面板当前的高度减去收缩起来的面板的高度
						this.doLayout();
					}
				}
			}, {
				xtype : 'panel',
				layout : 'form',
				layoutConfig : {
					padding : '5',
					pack : 'center',
					align : 'middle'
				},
				items : [this.postponedProjectInfo]
			}]
		});
		this.add(this.outPanel);
		this.doLayout();
	}// 初始化UI结束
});