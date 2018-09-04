/**
 * 项目信息管理公用方法
 * @author lisl
 */
// 项目信息(流程节点使用)
var showProjectInfoTab = function(record, idPrefix) {
	var businessType = record.data.businessType;
	var tabs = Ext.getCmp('centerTabPanel');
	var gpObj = document.getElementById(idPrefix + record.data.projectId);
	var b = true;
	if (idPrefix.indexOf("Edit") < 0 ) {//判断项目信息panel的id属性值前缀是否包含"Edit"来决定弹出查看或编辑项目信息tab页签
		b = false;//不包含
	}
	if (gpObj == null) {
		switch (businessType) {
			case 'SmallLoan' :
				if (b == false) {
					gpObj = new SmallLoanProjectInfoPanel({
								record : record
							});
				} else if (b == true) {
					gpObj = new SmallLoanProjectInfoEditPanel({
								record : record
							});
				}
				break;
			case 'Financing' :
				if (b == false) {
					gpObj = new FinancingProjectInfoPanel({
								record : record
							});
				} else if (b == true) {
					gpObj = new FinancingProjectInfoEditPanel({
								record : record
							});
				}
				break;
			case 'Guarantee' :
				if (b == false) {
					gpObj = new GuaranteeProjectInfoPanel({
								record : record
							});
				} else if (b == true) {
					gpObj = new GuaranteeProjectInfoEditPanel({
								record : record
							});
				}
				break;
			case 'LeaseFinance' :
				if (b == false) {
					gpObj = new LeaseFinanceProjectInfoPanel({
								record : record
							});
				} else if (b == true) {
					gpObj = new LeaseFinanceProjectInfoEditPanel({
								record : record
							});
				}
				break;
			case 'BeNotFinancing' :
				if (b == false) {
					gpObj = new LawsuitguaranteeProjectInfo({
								record : record
							});
				} else if (b == true) {
					gpObj = new LawsuitguaranteeProjectInfoEdit({
								record : record
							});
				}
				break;
			case 'Pawn' :
				if (b == false) {
					gpObj = new PlPawnProjectInfoPanel({
								record : record
							});
				} else if (b == true) {
					gpObj = new PlPawnProjectInfoEditPanel({
								record : record
							});
				}
				break;
			default :
				break;
		}
		tabs.add(gpObj);
	}
	tabs.setActiveTab(idPrefix + record.data.projectId);
} 
// 项目信息(项目信息列表使用)
var detailPro = function(grid, idPrefix) {
	var selRs = grid.getSelectionModel().getSelections();
	if (selRs.length == 0) {
		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
		return;
	}
	if (selRs.length > 1) {
		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
		return;
	}
	var record = grid.getSelectionModel().getSelected();
	showProjectInfoTab(record, idPrefix);
}
// 项目详细(项目事项处理)
var editPro = function(grid) {
	var selRs = grid.getSelectionModel().getSelections();
	if (selRs.length == 0) {
		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
		return;
	}
	if (selRs.length > 1) {
		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
		return;
	}
	var record = grid.getSelectionModel().getSelected();
	var businessType = record.data.businessType;
	var projectId = record.data.projectId;
	var idPrefix = "";
	switch(businessType) {
		case 'SmallLoan' :
			idPrefix = "SmallLoanProjectInfo_";//这里修改为只能查看项目
			break;
		case 'Financing' :
			idPrefix = "FinancingProjectInfoEdit_";
			break;
		case 'Guarantee' :
			idPrefix = "GuaranteeProjectInfoEdit_";
			break;
		case 'ExhibitionBusiness' :
			idPrefix = "SmallLoanProjectInfo_";
			businessType = 'SmallLoan';//我参与的项目，如果是非主流程，也弹出对应的编辑项目页签。
			break;
		case 'matchingfundsBusiness':
			idPrefix = "SmallLoanProjectInfo_";
			businessType = 'SmallLoan';//我参与的项目，如果是非主流程，也弹出对应的编辑项目页签。
			break;
		default :
			break;
	}
	Ext.Ajax.request({
		url : __ctxPath
				+ '/creditFlow/getProjectViewObjectCreditProject.do',
		params : {
			businessType : businessType,
			projectId : projectId
		},
		method : 'post',
		success : function(resp, op) {
			var record = Ext.util.JSON
					.decode(resp.responseText);// JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
			showProjectInfoTab(record, idPrefix);
		},
		failure : function() {
			Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
		}
	})
}

// 终止项目
var stopPro = function(grid, idPrefix, idPrefix_edit,type) {
	var selRs = grid.getSelectionModel().getSelections();
	if (selRs.length == 0) {
		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
		return;
	}
	if (selRs.length > 1) {
		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
		return;
	}
	var record = grid.getSelectionModel().getSelected();
	new ProjectStop({record : record, idPrefix : idPrefix, idPrefix_edit : idPrefix_edit,type:type}).show();
}

// 删除项目
var removePro = function(grid, idPrefix, idPrefix_edit) {
	var runId = $getGdSelectedIds(grid, 'runId');
	var activityName = $getGdSelectedIds(grid, 'activityName');
	var processName = $getGdSelectedIds(grid, 'processName');
	if (runId.length == 0) {
		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
		return;
	}

	if (runId.length > 1) {
		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
		return;
	}
	
	/*if(processName=='smallLoanFlow'){//小额常规流程
		if(activityName=='合同签署确认'||activityName=='办理抵质押物手续'||activityName=='款项计划确认'||activityName=='归档确认'||activityName=='贷中监管'||activityName=='项目完成'){
			Ext.ux.Toast.msg('操作信息', '项目已进入"合同签署确认"或之后节点,不能进行删除项目操作！');
			return;
		}
	}else if(processName=='smallLoanFast'){//小额快速流程
		if(activityName=='合同制作与签署'||activityName=='款项计划确认'||activityName=='归档确认'||activityName=='贷中监管'||activityName=='项目完成'){
			Ext.ux.Toast.msg('操作信息', '项目已进入"合同制作与签署"或之后节点,不能进行删除项目操作！');
			return;
		}
	}else if(processName=='guaranteeNormalFlow'){//金融担保成规流程
	
	}else if(processName=='zmNormalFlow'){//中铭常规流程
		
	}*/

	Ext.Msg.confirm('信息确认', '删除该项目将连同相关数据同时删除,且项目(任务)无法恢复,您确认要删除所选项目吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/removeByRunIdCreditProject.do',
				params : {
					runId : runId
				},
				method : 'post',
				success : function(resp, op) {
					var res = Ext.util.JSON.decode(resp.responseText);
					if (res.success) {
						Ext.ux.Toast.msg('信息提示', '成功删除项目！');
						closeProjectInfoTab(grid, idPrefix, idPrefix_edit);
						grid.getStore().reload();
						ZW.refreshTaskPanelView();
					} else if(!res.success&&typeof(res.deleteError)=="undefined"){
						Ext.ux.Toast.msg('信息提示', '项目的费用或者款项已对账，不能进行删除项目操作！');
					}/* else if(!res.isBeforeTask&&typeof(res.success)=="undefined"&&typeof(res.deleteError)=="undefined"){
						if(processName=='smallLoanFlow'){
							Ext.ux.Toast.msg('信息提示', '项目已进入"合同签署确认"或之后节点,不能进行删除项目操作！');
						}else if(processName=='smallLoanFast'){
							Ext.ux.Toast.msg('信息提示', '项目已进入"合同制作与签署"或之后节点,不能进行删除项目操作！');
						}
					} */else if(!res.deleteError&&typeof(res.success)=="undefined"){
						Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
					} else {
						Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
					}
				},
				failure : function() {
					Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
				}

			});
		}
	});
}

// 挂起、恢复项目
var suspendPro = function(grid, idPrefix, idPrefix_edit,isSuspendedProject) {
	var runId = $getGdSelectedIds(grid, 'runId');
	var taskId = $getGdSelectedIds(grid, 'taskId');
	if (runId.length == 0) {
		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
		return;
	}

	if (runId.length > 1) {
		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
		return;
	}
	var messageStr = "";
	if(isSuspendedProject == 1) {
		messageStr = '挂起该项目将暂停相关任务信息,确认要挂起所选项目吗？';
	}else if (isSuspendedProject == 0) {
		messageStr = '恢复该项目将会使项目重新生效,确认要恢复所选项目吗？';
	}
	Ext.Msg.confirm('信息确认',messageStr, function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/flow/suspendByRunIdTask.do',
				params : {
					runId : runId,
					taskId : taskId,
					isSuspendedProject : isSuspendedProject//0：恢复项目；1：挂起项目。
				},
				method : 'post',
				success : function(resp, op) {
					var res = Ext.util.JSON.decode(resp.responseText);
					if (res.success) {
						Ext.ux.Toast.msg('信息提示', '成功挂起项目！');
						closeProjectInfoTab(grid, idPrefix, idPrefix_edit);
						grid.getStore().reload();
						ZW.refreshTaskPanelView();
					} else {
						Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
					}
				},
				failure : function() {
					Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
				}

			});
		}
	});
}
var showFlowImgWin = function(runId) {
	var flowImagePanel = new Ext.Panel({
				autoHeight : true,
				border : false,
				html : '<img src="' + __ctxPath + '/jbpmImage?runId='
						+ runId + '&rand=' + Math.random()
						+ '"/>'
			});

	var panel = new Ext.Panel({
				autoHeight : true,
				layout : 'form',
				border : false,
				items : [flowImagePanel]
			});

	// 若当前为子流程，则显示子流程
	if (this.isSubFlow) {
		panel.add({
					xtype : 'panel',
					autoHeight : true,
					border : false,
					html : '<img src="' + __ctxPath + '/jbpmImage?runId='
							+ selRs[0].data.runId + '&isSubFlow=true&rand='
							+ Math.random() + '"/>'
				});
		panel.doLayout();
	}

	new Ext.Window({
				autoScroll : true,
				iconCls : 'btn-flow-chart',
				bodyStyle : 'background-color:white',
				maximizable : true,
				title : '流程示意图',
				width : 800,
				height : 600,
				modal : true,
				layout : 'fit',
				items : panel
			}).show();
}
// 查看流程图
var showFlowImg = function(grid) {
	var selRs = grid.getSelectionModel().getSelections();
	if (selRs.length == 0) {
		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
		return;
	}
	if (selRs.length > 1) {
		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
		return;
	}
	var runId = selRs[0].data.runId;
	showFlowImgWin(runId);
}
// 查看客户信息
var seeCustomer = function(oppositeType, oppositeId) {
	if (oppositeType == 'person_customer') {
		seePersonCustomer(oppositeId);
	} else if (oppositeType == 'company_customer') {
		seeEnterpriseCustomer(oppositeId);
	}
}
// 编辑客户信息
var editCustomer = function(oppositeType, oppositeId,formObj) {
	if (oppositeType == 'person_customer') {
		editPersonInfo(oppositeId,formObj);
	} else if (oppositeType == 'company_customer') {
		editEnterpriseInfo(oppositeId,null,formObj);
	}
}
// 关闭项目详细页签
var closeProjectInfoTab = function(projectId, idPrefix, idPrefix_edit) {
	var gpId = idPrefix + projectId;
	var gpId_edit = idPrefix_edit + projectId;
	var gpObj = document.getElementById(gpId);
	var gpObj_edit = document.getElementById(gpId_edit);
	if (gpObj != null) {
		Ext.getCmp('centerTabPanel').remove(gpId);
	}
	if (gpObj_edit != null) {
		Ext.getCmp('centerTabPanel').remove(gpId_edit);
	}
}
//桌面搜索项目信息
var searchProject = function(businessType) {
	var text = "";
	var gpObj = null;
	switch (businessType) {
	  case "SmallLoan": 
	   	if(document.getElementById("searchKeyword"))
	   	text=document.getElementById("searchKeyword").value;
		App.clickTopTabReplace('SmallLoanProjectManager_7',{projectStatus:7,keyWord:text});
	    break;
	  case "Financing": 
	  	if(document.getElementById("searchKeywordFinancing"))
	  	text=document.getElementById("searchKeywordFinancing").value;
	  	
		App.clickTopTabReplace('FinancingProjectManager_6',{projectStatus:6,keyWord:text});
	    break;
	  default: 
	  if(document.getElementById("searchKeyword"))
	   	text=document.getElementById("searchKeyword").value;
	  	App.clickTopTabReplace('SmallLoanProjectManager_7',{projectStatus:7,keyWord:text});

		
	 }
	

	
	
}

//桌面搜索文本框触发得失焦点事件
var changeText = function(tag,div) {
	var searchText = div;
	if(tag == 0) {//文本框失去焦点
		if(searchText.value == "") {
			searchText.value = "请输入项目关键字，例如项目编号、项目名称";
			searchText.setAttribute("style","color:gray");
		}
	}else if(tag == 1) {//文本框得到焦点
		if(searchText.value == "请输入项目关键字，例如项目编号、项目名称") {
			searchText.value = "";
			searchText.setAttribute("style","color:black");
		}
	}
}

/**
 * 根据文件名查询项目信息
 * @param {} grid
 * @param {} fileName 文件名即显示的对象名称
 * @param {} flag  标记是查看还是编辑   查看see 编辑edit
 * 例：project的查询页面为SmallLoanProjectInfoPanel
 * 　　　seeProjectInfoByFileName(grid,"SmallLoanProjectInfoPanel");
 */
var seeProjectInfoByFileName = function(grid, fileName,flag) {
	var selRs = grid.getSelectionModel().getSelections();
	if (selRs.length == 0) {
		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
		return;
	}
	if (selRs.length > 1) {
		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
		return;
	}
	var record = grid.getSelectionModel().getSelected();
	
	var businessType = record.data.businessType;
	var tabs = Ext.getCmp('centerTabPanel');
	var gpObj = document.getElementById(fileName + record.data.projectId);
	if (gpObj == null) {
		gpObj = eval( "new "+fileName+"({record : record, flag : '"+flag+"' })");
		tabs.add(gpObj);
	}
	tabs.setActiveTab(fileName +flag+ record.data.projectId);

}
