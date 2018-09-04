/**
 * 流程执行下一步总入口,需要传入任务id，当前节点名称
 * 
 * @class ProcessNextForm
 * @extends Ext.Panel
 */
ProcessNextForm = Ext.extend(Ext.Panel, {
	flagInFlowFlag:true,//add by gao
	constructor : function(config) {
		// 若当前任务尚未分配该用户，则该用户进入该任务时，
		// 需要弹出一个对话框架告诉他正在锁定该任务执行
		Ext.applyIf(this, config);
		var flag = false;
		Ext.Ajax.request({
					params : {
						taskId : this.taskId
					},
					async : false,
					scope : this,
					url : __ctxPath + "/flow/checkTask.do",
					success : function(response, options) {
						// 若当前任务已经被其他人员执行或已经执行完成等
						var result = Ext.util.JSON
								.decode(response.responseText);
						if (!this.agentTask) {// 若该任务没有被代理
							if (result.assigned != undefined) {
								if (!result.assigned) {
									Ext.ux.Toast.msg('操作信息', '该任务已经被其他用户锁定执行！');
									flag = true;
								} else if (result.assigned) {
									Ext.ux.Toast.msg('操作信息', '该任务已经成功锁定!');
								}
							}
						}
						this.isSubFlow = result.isSubFlow
								? result.isSubFlow
								: false;
					}
				});
		if (flag) {
			var taskPanel = Ext.getCmp('TaskPanelView');
			if (taskPanel != null && taskPanel != undefined) {
				taskPanel.getUpdater().update(__ctxPath
						+ '/flow/displayTask.do');
			}
			// ProcessNextForm.superclass.constructor.call(null);
			return;
		}

		// 下一授予任务名
		this.assignTasks = new Array();
		// 下一任务用户名
		this.assignUserIds = new Array();

		this.formPanel = new Ext.FormPanel({
					// layout:'table',
					border : false,
					autoHeight : true,
					autoLoad : {
						url : __ctxPath + "/flow/getProcessActivity.do?taskId="
								+ this.taskId,
						nocache : true,
						params : {
							activityName : this.activityName
						},
						scope : this,
						callback : this.getFormHtmlCallback
					}
				});
		// 显示流程审批的表单
		this.xg = Ext.grid;

		// 用户选择的Panel，为下一节点进行人员选择
		this.userJumpPanel = new Ext.Panel({
					// title:'选择下一任务执行人',
					// collapsed: true,
					// autoHeight:true,
					// collapsed: false,
					// collapsible : true,
					border : false
				});

		// 加载跳转的按钮
		this.jumpPanel = new Ext.Panel({
					border : false,
					autoHeight : true,
					// bodyStyle:'padding:8px',
					layout : 'form',
					labelAlign : "right",
					labelWidth : 100,
					defaults : {
						layout : 'form',
						labelAlign : "right",
						labelWidth : 100
					},
					items : [this.userJumpPanel]
				});

		this.toolbar = new Ext.Toolbar({
			items : [/*
						 * '&nbsp;&nbsp; ', { xtype : 'checkbox', boxLabel :
						 * '自由跳转', scope : this, hidden : false, handler :
						 * this.freeJump }, '-',
						 */
					{
						xtype : 'button',
						text : '快速保存信息',
						scope : this,
						iconCls : 'btn-save',
						handler : this.saveStep
					},
					/*
					 * '-', { xtype : 'button', text : '提交任务', scope : this,
					 * iconCls : 'btn-transition', handler : this.nextStep },
					 * '-','向下一任务处理人发送:&nbsp;',{ xtype : 'checkbox', boxLabel :
					 * '邮件提醒', scope : this, handler : function(ck, checked) {
					 * if (checked) { this.sendMail = true; } else {
					 * this.sendMail = false; } } },'&nbsp;', { xtype :
					 * 'checkbox', boxLabel : '短信提醒', scope : this, handler :
					 * function(ck, checked) { if (checked) { this.sendMsg =
					 * true; } else { this.sendMsg = false; } } },
					 */'->', {
						text : '流程示意图',
						iconCls : 'btn-flow-chart',
						scope : this,
						handler : this.showFlowImage
					}
					/**
					 * 客户详细信息移到页面客户信息中
					 */
					/*
					 * , '-', { text : '客户信息', iconCls : 'btn-customer', scope :
					 * this, handler : function() { var oppositeId = null; var
					 * oppositeType = this.formPanel.get(0).oppositeType; if
					 * (oppositeType == "person_customer") { oppositeId =
					 * this.formPanel .getCmpByName('person.id').getValue(); }
					 * else if (oppositeType == "company_customer") { oppositeId =
					 * this.formPanel .getCmpByName('enterprise.id')
					 * .getValue(); } seeCustomer(oppositeType, oppositeId); } }
					 */
					, '-', {
						text : '项目信息',
						iconCls : 'btn-projectInfo',
						scope : this,
						hidden:this.projectName.split("-")[0]=='费用报销' || this.projectName.split("-")[0]=='借款' || this.projectName.split("-")[0]=='付款'?true:false,
						handler : function() {
							var businessType = this.formPanel.get(0).businessType;
							var projectId = this.formPanel.get(0).projectId;
							var idPrefix = "";
							if (businessType == 'SmallLoan') {
								idPrefix = "SmallLoanProjectInfo_";
							} else if (businessType == 'Financing') {
								idPrefix = "FinancingProjectInfoEdit_";
							} else if (businessType == 'Guarantee') {
								idPrefix = "GuaranteeProjectInfo_";
							}else if(businessType == 'LeaseFinance'){
								idPrefix = "LeaseFinanceProjectInfoEdit_";
							}else if(businessType == "Pawn"){
								idPrefix ="PawnProjectInfo_"
							}else if(businessType == "GeneralCredit"){
								idPrefix = "PlGeneralCreditProjectInfo_";
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
									showProjectInfoTab(record, idPrefix)
								},
								failure : function() {
									Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
								}
							})
						}
					}, '-', {
						text : '打印',
						iconCls : 'btn-print',
						scope : this,
						hidden : true,
						handler : function() {
							gpObj = document.getElementById('ProcessNextForm_'
									+ this.taskId);
							printFlag = true;

							fckeditorIframe = null;
							var iframes = gpObj.getElementsByTagName("iframe");
							for (var i = 0; i < iframes.length; i++) {
								if (iframes[i].id.indexOf("fckeditor") != -1) {
									fckeditorIframe = iframes[i];
								}
							}

							window.open(__ctxPath + '/js/printer/Print.jsp');
						}
					}]
		});
		this.loadJumpTrans.call(this);
		this.bigprogressRunGridPanel = new Ext.form.FieldSet({
					xtype : 'fieldset',
					layout : 'form',
					autoHeight : true,
					collapsed : false,
					collapsible : true,
					name : 'commentsRecords',
					title : "意见与说明记录",
					items : []
				})
		// 调用构造函数 主框架
		ProcessNextForm.superclass.constructor.call(this, {
					tbar : this.toolbar,
					id : 'ProcessNextForm_' + this.taskId,
					iconCls : 'btn-approvalTask',
					// title : this.activityName + '--待办事项',
					title : this.activityName + '--' + this.projectName,
					layout : 'form',
					frame : true,
					// bodyStyle:'padding:5px',
					layoutConfig : {
						padding : '5',
						pack : 'center',
						align : 'middle'
					},
					defaults : {
						margins : '0 5 10 0',
						anchor : '98%'
					},
					items : [this.formPanel, {
								xtype : 'fieldset',
								layout : 'form',
								autoHeight : true,
								collapsed : false,
								collapsible : true,
								hidden : false,
								name : 'zxcz',
								title : "执行操作",
								items : [this.jumpPanel]
							}, this.bigprogressRunGridPanel]
				});
		this.addListener({
					'beforeclose' : this.closetab
				});
	},
	// 加载当前任务节点的跳转路径
	loadJumpTrans : function() {
		// 加载审批表单的功能按钮
		Ext.Ajax.request({
			url : __ctxPath + "/flow/transProcessActivity.do",
			params : {
				taskId : this.taskId
			},
			scope : this,
			success : function(response, options) {
				var object = Ext.decode(response.responseText);
				if (object.preTaskName != undefined && object.preTaskName != '') {
					// 加上驳回按钮
					if (!this.jumpBackButton) {
						this.jumpBackButton = new Ext.Button({
									text : '驳回',
									iconCls : 'btn-back',
									hidden : true,
									scope : this,
									handler : this.backFlow
								});
						// this.toolbar.insert(3, new Ext.Toolbar.Separator());
						this.toolbar.insert(3, this.jumpBackButton);
						this.unAddBack = true;
						this.preTaskName = object.preTaskName;
					}
				}
				var radioItems = [];

				// update bu lu 2011.12.26 start
				// 处理任务节点没有下一节点指向的问题。(如企业常规流程：出具担保意向书)
				if (object.data.length == 0) {
					this.getNoNextTaskRadioCheck.call(this);// gengjj 增加
					this.userJumpPanel.hide();
					return;
				}
				// update bu lu 2011.12.26 end
				// -----------------子流程
				for (var i = 0; i < object.data.length; i++) {
					radioItems.push({
						boxLabel : object.data[i].destination,
						name : 'jumpPath_' + this.taskId,
						inputValue : object.data[i].name,
						destType : object.data[i].destType,
						destName : object.data[i].destination,
						checked : i == 0 ? true : false
							// 缺省第一个选中
						});
				}
				// -----------------子流程
//				this.jumpRadioGroup = new Ext.form.RadioGroup({
//							listeners : {
//								scope : this,
//								'change' : this.jumpRadioCheck
//							},
//							fieldLabel : '执行路径',
//							items : radioItems
//						});

				//this.jumpPanel.insert(0, this.jumpRadioGroup);
				this.jumpPanel.doLayout();

				// 以加载相应的人员

				// 加上会签的投票
				this.isZYHTFlow = false;
				if (object.isSignTask) {
					var _isZYHTFlow = false;
					if (object.isZYHTFlow != null) {
						_isZYHTFlow = object.isZYHTFlow
					}
					this.isZYHTFlow = _isZYHTFlow
					if (this.isZYHTFlow == true) {

					} else {
						this.addSignVoteOpinion.call(this);
					}
					this.getSignTaskRadioCheck.call(this);

				} else {
					this.getTaskRadioCheck.call(this, object);
				}
			}
		});
	},
	/**
	 * 加上会签意见
	 */
	addSignVoteOpinion : function() {
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : __ctxPath
								+ "/flow/signListProcessActivity.do?taskId="
								+ this.taskId
					}),
			baseParams : {
				activityType : 1
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCounts',
						fields : ['voteName', 'voteTime', 'isAgree', 'comments']
					}),
			remoteSort : true
		});
		this.store.load();
		this.voteListPanel = new Ext.grid.GridPanel({
			region : 'center',
			// hidden : isZYHTFlow,
			store : this.store,
			defaults : {
				anchor : '96%'
			},
			trackMouseOver : true,
			disableSelection : false,
			stripeRows : true,
			border : true,
			stateful : true,
			showPaging : false,
			autoHeight : true,
			hidden : true,
			loadMask : true,
			columns : [new Ext.grid.RowNumberer({}), {
						header : '投票人',
						width : 100,
						// fixed : true,
						dataIndex : 'voteName'
					}, {
						header : '投票时间',
						width : 100,
						// fixed : true,
						dataIndex : 'voteTime'
					}, {
						header : '投票意见',
						width : 60,
						// fixed : true,
						dataIndex : 'isAgree',
						renderer : function(val) {
							if (val == -1) {
								return '<span style="color:gray;">尚未投票</span>';
							} else if (val == 1) {
								return '<span style="color:green;">同意</span>';
							} else if (val == 2) {
								return '<span style="color:red;">否决</span>';
							} else if (val == 3) {
								return '<span style="color:red;">打回</span>';
							} else if (val == 4) {
								return '<span style="color:blue;">有条件通过</span>';
							} else if (val == 0) {
								return '<span style="color:gray;">弃权</span>';
							} else {
								return '<span style="color:gray;">尚未投票</span>';
							}
							/*
							 * if (val == null) { return '<span
							 * style="color:gray;">尚未投票</span>'; } else if (val ==
							 * 1) { return '<span style="color:green;">同意</span>'; }
							 * else if (val == 2) { return '<span
							 * style="color:red;">否决</span>'; } else { return '<span
							 * style="color:gray;">弃权</span>'; }
							 */
						}
					}, {
						header : '意见与说明',
						width : 300,
						dataIndex : 'comments',
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							metadata.attr = 'style="white-space:normal;"';
							return value;
						}
					}],
			viewConfig : {
				forceFit : true,
				autoFill : true
			}
		});

		this.voteRadioGroup = new Ext.form.RadioGroup({
					columns : [100, 100, 100, 100, 100],
					vertical : true,
					items : [{
								xtype : 'radio',
								name : 'signVoteType',
								boxLabel : '同意',
								inputValue : 1,
								checked : true
							}, {
								xtype : 'radio',
								boxLabel : '有条件通过',
								name : 'signVoteType',
								inputValue : 4
							}, {
								xtype : 'radio',
								boxLabel : '打回',
								name : 'signVoteType',
								inputValue : 3
							}, {
								name : 'signVoteType',
								xtype : 'radio',
								boxLabel : '否决',
								inputValue : 2
							}/*, {
								xtype : 'radio',
								name : 'signVoteType',
								boxLabel : '弃权',
								inputValue : 0
							}*/]
				});

		this.voteCmpField = new Ext.form.CompositeField({
					width : 540,
					bodyStyle : 'padding-top :10px',
					autoHeight : true,
					fieldLabel : '会签投票意见',
					name : 'countersignOpinions',
					items : [this.voteRadioGroup]
				});
		// 显示会签情况
		// this.voteListPanel = new Ext.Panel({
		// border : false,
		// autoHeight : true,
		// autoLoad : {
		// url : __ctxPath + "/flow/signListProcessActivity.do?taskId="
		// + this.taskId,
		// nocache : true
		// }
		// });
		this.voteSignFieldSet = new Ext.form.FieldSet({
					title : '会签情况',
					autoHeight : true,
					collapsed : false,
					collapsible : true,
					// hidden : isZYHTFlow,
					layout : 'form',
					items : [this.voteListPanel, this.voteCmpField, {
								xtype : "textarea",
								style : "margin-top:12px",
								name : "comments",
								anchor : "100%",
								labelWidth : 100,
								fieldLabel : "审批说明"
							}]
				});
	},

	/**
	 * 单选项按钮点击
	 * 
	 * @param {}
	 *            rd
	 * @param {}
	 *            ckradio
	 */
	jumpRadioCheck : function(radioGp, radio) {

		if (!radioGp) {
			radioGp = this.jumpRadioGroup;
		}
		if (!radio) {
			radio = radioGp.getValue();
		}
		// 加上下一任务执行人
		this.getTaskUsers.call(this, radio.destName, radio.destType);
		// 加上下一任务限定时间 update by ly 2011.12.14(隐藏任务完成时限panel)
		this.getDueDatePanel.call(this, 'hideDueDatePanel');
	},
	// 到期设置日期
	getDueDatePanel : function(destType) {
		// 设置默认设置为4h
		if (!this.dueDatePanel) {
			var curDate = new Date();
			curDate.setHours(curDate.getHours() + 4);
			this.dueDatePanel = new Ext.Panel({
						layout : 'form',
						border : false,
						labelWidth : 100,
						items : [{
									xtype : 'datetimefield',
									fieldLabel : '完成限定时间',
									name : 'dueDate',
									width : 240,
									format : 'Y-m-d H:i:s',
									value : curDate
								}]
					});
			this.jumpPanel.add(this.dueDatePanel);
			this.jumpPanel.doLayout();
		}

		if (destType == 'task') {
			this.dueDatePanel.show();
		} else {
			this.dueDatePanel.hide();
		}

	},
	/**
	 * 取得下一节点对应的处理人员
	 * 
	 * @param {}
	 *            destName 目标节点的名称
	 * @param {}
	 *            destType 目标节点的类型
	 */
	getTaskUsers : function(destName, destType) {

		// 下一节点为分支及fork节点
		if ('decision' == destType || 'fork' == destType || 'join' == destType) {
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.genForkDecUserAssign.call(this, destName);
		} else if (destType.indexOf('end') != -1) {// 下一节点为结束节点，需要隐藏下一步的执行人

			// 若为结束节点，需要考虑父流程的处理方式，所以在这里，需要判断一下，当前流程是否为子流程，若是的话，则需要加载其跳回父流程的路径
			if (this.isSubFlow) {
				this.getOutToParentFlow.call(this);
			}
			this.userJumpPanel.removeAll();
			this.userJumpPanel.hide();
		} else {// 下一节点为普通任务节点
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.userJumpPanel
					.add(this.getSingleUserPanel.call(this, destName));
		}
		this.jumpPanel.doLayout();

	},

	getParentFlowTaskUsers : function(destName, destType) {
		// 下一节点为分支及fork节点
		if ('decision' == destType || 'fork' == destType || 'join' == destType) {
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.genForkDecUserAssign.call(this, destName, true);
		} else if (destType.indexOf('end') != -1) {// 下一节点为结束节点，需要隐藏下一步的执行人

			this.userJumpPanel.removeAll();
			this.userJumpPanel.hide();

		} else {// 下一节点为普通任务节点
			this.userJumpPanel.removeAll();
			this.userJumpPanel.show();
			this.userJumpPanel.add(this.getSingleUserPanel.call(this, destName,
					true));

		}
	},

	/**
	 * 
	 */
	parentJumpPathComboSelect : function(combo, record, index) {
		var destName = record.data.destination;
		var destType = record.data.destType;

		this.getParentFlowTaskUsers.call(this, destName, destType);
		this.userJumpPanel.doLayout();
	},

	/**
	 * 
	 */
	getOutToParentFlow : function() {
		this.parentJumpPathCombo = new Ext.form.ComboBox({
					allowBlank : true,
					store : new Ext.data.JsonStore({
								autoLoad : true,
								url : __ctxPath
										+ '/flow/parentTransProcessActivity.do?taskId='
										+ this.taskId,
								fields : ['destType', 'destination', 'name'],
								root : 'data'
							}),
					fieldLabel : '跳至父流程路径',
					mode : 'local',
					editable : false,
					triggerAction : 'all',
					displayField : 'destination',
					valueField : 'name',
					listeners : {
						scope : this,
						'select' : this.parentJumpPathComboSelect
					}
				});
		this.jumpPanel.insert(1, this.parentJumpPathCombo);
		this.jumpPanel.doLayout();
	},
	/**
	 * 下一步仅有一个任务，即仅需要为一个任务赋予人员
	 * 
	 * @param {}
	 *            isParentFlow 是否取得该任务对应的外围父流程的跳转分支的人员指派
	 * @return {}
	 */
	getSingleUserPanel : function(destName, isParentFlow) {
		this.flowAssignName = new Ext.form.TextArea({
					width : 400,
					height : 26,
					name : 'flowAssignName'
				});

		var singleUserField = new Ext.form.CompositeField({
					xtype : 'compositefield',
					fieldLabel : '执行人',
					labelWidth : 100,
					anchor : '92%,92%',
					items : [this.flowAssignName/*
												 * , { xtype:'button',
												 * scope:this, text:'...',
												 * iconCls : 'btn-users',
												 * handler:function(){ var
												 * formPanel=this.flowUserFieldPanel;
												 * UserSelector.getView( {
												 * callback:function(uId,uname){
												 * this.flowAssignName.setValue(uname);
												 * this.assignTasks=[destName];
												 * this.assignUserIds=[uId]; },
												 * scope:this }).show(); } }
												 */
					]
				});
		Ext.Ajax.request({
					url : __ctxPath + '/flow/usersProcessActivity.do',
					scope : this,
					params : {
						taskId : this.taskId,
						activityName : destName,
						isParentFlow : isParentFlow
					},
					success : function(response, options) {
						var result = Ext.decode(response.responseText);
						this.flowAssignName.setValue(result.userNames);
						this.assignTasks = [destName];
						this.assignUserIds = [result.userIds];
					}
				});

		return singleUserField;
	},

	// 为汇集或分支节点产生自由跳转的人员选择
	/**
	 * 
	 * @param {}
	 *            destName
	 * @param {}
	 *            isParentFlow 是否取得该任务对应的外围父流程的跳转分支的人员指派
	 */
	genForkDecUserAssign : function(destName, isParentFlow) {
		Ext.Ajax.request({
					url : __ctxPath
							+ '/flow/outerTransProcessActivity.do?taskId='
							+ this.taskId,
					params : {
						nodeName : destName,
						isParentFlow : isParentFlow
						/*
						 * , beJuxtaposedFlowNodeKeys :
						 * beJuxtaposedFlowNodeKeys,
						 * currentNodeKeyByNextTaskBeJuxtaposed :
						 * currentNodeKeyByNextTaskBeJuxtaposed
						 */
					},
					scope : this,
					success : function(resp, options) {
						// outers数据格式为[{singalName,activityName,destType},...]
						// 如：[["to 总经理审阅","总经理审阅","task","1,2","张三,李四"],["to
						// 财务审核","财务审核","task","1,2","张三,李五"]]
						var outers = Ext.decode(resp.responseText);
						if (outers.length > 0) {
							this.userJumpPanel.show();
							for (var i = 0; i < outers.length; i++) {
								this.userJumpPanel.add(this.genUserFieldSel
										.call(this, outers[i], i));
							}
							this.userJumpPanel.doLayout();
						} else {
							this.userJumpPanel.hide();
						}
					}
				});
	},
	// 产生用户选择
	genUserFieldSel : function(outers, idx) {
		// 目标节点名称
		var destName = outers[1];
		this.assignTasks[idx] = destName;
		this.assignUserIds[idx] = outers[3];

		var flowAssignUserName = new Ext.form.TextArea({
					allowBlank : false,
					width : 400,
					height : 40,
					value : outers[4]
				});
		var cmpField = new Ext.form.CompositeField({
					anchor : '92%,92%',
					bodyStyle : "background-color:white;padding:0 0 0 0",
					fieldLabel : destName,
					items : [flowAssignUserName/*
												 * ,{ xtype:'button',
												 * text:'...', iconCls :
												 * 'btn-users', scope:this,
												 * handler:function(){
												 * UserSelector.getView({
												 * scope:this,
												 * callback:function(uIds,
												 * uNames) {
												 * flowAssignUserName.setValue(uNames);
												 * //查找该数组中是否已经存在这个目标节点，若存在，则找到其坐标
												 * var
												 * index=this.assignTasks.length;
												 * for(var i=index-1;i>=0;i--){
												 * if(this.assignTasks[i]==destName){
												 * index=i; break; } }
												 * this.assignTasks[index]=destName;
												 * this.assignUserIds[index]=uIds; }
												 * }).show(); }//end of handler }
												 */
					]
				});

		return cmpField;

	},
	/**
	 * 取得下一任务及其对应的人员
	 */
	getFlowAssignId : function() {
		// 返回其格式如下：领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
		var flowAssignId = '';
		var destTasks = '';
		var destUserIds = '';
		for (var i = 0; i < this.assignTasks.length; i++) {
			if (i > 0) {
				destTasks += ':';
				destUserIds += ':';
			}
			destTasks += this.assignTasks[i];
			destUserIds += this.assignUserIds[i];
		}
		if (destTasks != '') {
			flowAssignId = destTasks + '|' + destUserIds;
		}
		return flowAssignId;
	},

	/**
	 * 保存业务数据
	 */
	saveStep : function() {
		if (this.formExtPanel != null && this.formExtPanel.saveBusDatas) {
			this.comments = this.getCmpByName('comments').getValue();
			this.formPanel.comments = this.comments;
			this.formExtPanel.saveBusDatas.call(this.formExtPanel,
					this.formPanel);
		}
	},

	/**
	 * 执行下一步
	 */
	nextStep : function() {
		var isValid = true;
		// 加上对于表单的前置验证，允许在模板中加上自己的验证
		if (this.formExtPanel != null && this.formExtPanel.validate) {
			isValid = this.formExtPanel.validate.call(this.formExtPanel, this);
		} else {
			isValid = $validForm.call(this);
		}

		if (!isValid)
			return;

		var signalName = '';
		var destName = '';
		var signVoteType = null;
		
		signalName = this.jumpCombobox.getValue();
		
		
		// 是为会签
		if (this.isZYHTFlow == true) {
			signVoteType = 1;
		} else {
			if (this.voteRadioGroup) {
				signVoteType = this.voteRadioGroup.getValue().getGroupValue();
			}
		}

		var form = this.formPanel.getForm();
		// modify by lyy start
		/**
		 * @author lyy
		 * @description 取得表单里面的OFFICE控件面板，保存文档，再把文档ID返回给表单字段
		 */
		var officePanel = this.officePanel;
		if (officePanel) {
			var obj = null;
			if (this.fileId != '' && this.fileId != undefined) {
				obj = officePanel.saveDoc({
							docName : 'ProcessDocument',
							fileId : this.fileId,
							doctype : 'doc'
						});
			} else {
				obj = officePanel.saveDoc({
							docName : 'ProcessDocument',
							doctype : 'doc'
						});
			}
			if (obj && obj.success) {
				var fileId = obj.fileId;
				this.hiddenF.setValue(fileId);
			}
		}

		// 设置flowAssignId,用于指定下一任务的执行人
		var flowAssignId = '';
		if (this.formExtPanel != null && this.formExtPanel.getFlowAssignId) {// 若在模板中指定了下一步的执行人员
			flowAssignId = this.formExtPanel.getFlowAssignId.call(
					this.formExtPanel, this);
		} else {
			flowAssignId = this.getFlowAssignId.call(this);
		}

		this.comments = this.getCmpByName('comments').getValue();
		var baseParams = {
			useTemplate : this.useTemplate,
			signVoteType : signVoteType,
			flowAssignId : flowAssignId,
			taskId : this.taskId,
			signalName : signalName,
			destName : destName,
			sendMsg : this.sendMsg,
			sendMail : this.sendMail,
			comments : this.comments,
			sendCallBefore : this.sendCallBefore
		};
		// 设置下一任务执行的限定完成时间
		// if(this.dueDatePanel.show()){//update bu lu 2011.12.26
		// 处理任务节点没有下一节点指向的问题。(如企业常规流程：出具担保意向书)
		if (this.dueDatePanel && this.dueDatePanel.show()) {
			var dueDate = this.dueDatePanel.getCmpByName('dueDate').getValue();
			baseParams.dueDate = $formatDate(dueDate);
		}
		// 设置跳回父流程的路径
		if (this.parentJumpPathCombo) {
			if (this.parentJumpPathCombo.getValue() == '') {
				Ext.ux.Toast.msg('操作信息', '请选择回跳至父流程的节点!');
				return;
			}
			baseParams.toParentPath = this.parentJumpPathCombo.getValue();
		}

		if (this.detailGrids) {// 适用于多个GRID的
			var grids = this.detailGrids.keys;
			for (var j = 0; j < grids.length; j++) {
				var details = [];
				var detailPanel = this.detailGrids.get(grids[j]);
				var store = detailPanel.getStore();
				for (var i = 0; i < store.getCount(); i++) {
					var record = store.getAt(i);
					var d = HT.encode(record.data);
					details.push(d);
				}
				baseParams[grids[j] + 'details'] = Ext.encode(details);
			}
		}
		var dom = form.getEl().dom;
		// 取得表单里面的子表单
		var forms = dom.getElementsByTagName('form');
		var dv = [];
		var detailsMap = new Ext.util.MixedCollection();
		for (var i = 0; i < forms.length; i++) {
			var belongName = forms[i].getAttribute('belongName');
			var pkName = forms[i].getAttribute('pkName');
			var pkValue = forms[i].getAttribute('pkValue');
			var baseParam2 = Ext.Ajax.serializeForm(forms[i]);
			var deParams = Ext.urlDecode(baseParam2);// 取得了从表里面的数据
			// 进行数据组装
			if (pkName && pkValue) {
				deParams[pkName] = pkValue;
			}
			var dd = HT.encode(deParams);

			var tt = detailsMap.get(belongName);
			if (!tt) {
				var details = [];
				details.push(dd);
				detailsMap.add(belongName, details);
			} else {
				tt.push(dd);
			}
		}

		for (var i = 0; i < detailsMap.keys.length; i++) {
			var keyName = detailsMap.keys[i];
			baseParams[keyName + 'details'] = Ext.encode(detailsMap
					.get(keyName));
		}
		
/*		if(this.sendMail){  //对企业内部邮箱做效验
			var sendMailFlag = true;
			Ext.Ajax.request({
				url : __ctxPath + "/system/checkUserEmailAppUser.do",
				method : 'POST',
			//	params : {} ,
				async : false,//设为同步请求，这个执行完了再继续执行下一步的form表单验证
				success : function(response){
					if(response.responseText.trim() == "{success:formatIsError}"){
						Ext.ux.Toast.msg('状态', '请更换为企业邮箱,再使用邮件提醒功能!');
						sendMailFlag = false;
					}else if(response.responseText.trim()=="{success:noSetEmail}"){
						Ext.ux.Toast.msg('状态', '请设置你的邮箱后，再使用邮件提醒功能!');
						sendMailFlag = false;
					}
				},
				failure : function(response){
					Ext.ux.Toast.msg("提示","效验发送人邮箱出错,请联系管理员!");
					sendMailFlag = false;
				}
			});
			
			if(!sendMailFlag){
				return sendMailFlag;			
			}
		
		}*/
		
		//if (this.getCmpByName('comments').getValue() != '') {
			if (form.isValid()) {// 是合法有效
				var taskSubmit = this.jumpPanel.getCmpByName("taskSubmit");
				var taskSubmit1 = this.jumpPanel.getCmpByName("taskSubmit1");
				var taskSubmit2 = this.jumpPanel.getCmpByName("taskSubmit2");
				if (taskSubmit != null || taskSubmit2 != null) {
					
			//		if (this.jumpRadioGroup.items.length > 1) {
					if (true) {
						var info = "";
						var jumpComboboxSelectValue = this.jumpCombobox.lastSelectionText
						if(jumpComboboxSelectValue.indexOf("退回")!=-1){
						    info = '确定要退回到<span style="color:red;">"'+ signalName.replace(/[0-9]/g, '')+ '"</span>吗？';
						}else if (signalName.indexOf("终止") != -1) {
							info = '任务提交到<span style="color:red;">"'+ signalName.replace(/[0-9]/g, '')+ '"</span>后,项目(任务)将无法恢复,您确认提交吗？';
						}else{
						    info = '确定要提交到<span style="color:red;">"'+ signalName.replace(/[0-9]/g, '')+ '"</span>吗？';
						}
						Ext.Msg.confirm("信息确认!", info, function(btn) {
							if (btn == "yes") {
								form.submit({
											url : __ctxPath
													+ "/flow/nextProcessActivity.do",
											method : 'post',
											waitMsg : '正在提交处理，请稍等',
											scope : this,
											params : baseParams,
											success : function(fp, action) {
												Ext.ux.Toast.msg('操作信息',
														'成功保存！');
												AppUtil
														.removeTab('ProcessNextForm_'
																+ this.taskId);
												ZW.refreshTaskPanelView();
												var obj = document
														.getElementById("taskCount");
												ZW.refreshTaskCount(obj);
												if (officePanel) {
													officePanel.closeDoc();
												}
											},
											failure : function(fp, action) {
												Ext.ux.Toast.msg('操作信息',
														'操作出错，请联系管理员！');
											}
										});
							}
						}, this)
					} else {
						form.submit({
									url : __ctxPath
											+ "/flow/nextProcessActivity.do",
									method : 'post',
									waitMsg : '正在提交处理，请稍等',
									scope : this,
									params : baseParams,
									success : function(fp, action) {
										Ext.ux.Toast.msg('操作信息', '成功保存！');
										AppUtil.removeTab('ProcessNextForm_'
												+ this.taskId);
										ZW.refreshTaskPanelView();
										var obj = document
												.getElementById("taskCount");
										ZW.refreshTaskCount(obj);
										if (officePanel) {
											officePanel.closeDoc();
										}
									},
									failure : function(fp, action) {
										Ext.ux.Toast
												.msg('操作信息', '操作出错，请联系管理员！');
									}
								});
					}
				} else {
					form.submit({
						url : __ctxPath + "/flow/nextProcessActivity.do",
						method : 'post',
						waitMsg : '正在提交处理，请稍等',
						scope : this,
						params : baseParams,
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存！');
							AppUtil.removeTab('ProcessNextForm_' + this.taskId);
							ZW.refreshTaskPanelView();
							var obj = document.getElementById("taskCount");
							ZW.refreshTaskCount(obj);
							if (officePanel) {
								officePanel.closeDoc();
							}
						},
						failure : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
						}
					});
				}
			}
		/*} else {
			Ext.Msg.alert("提示信息", "意见与说明不能为空，请正确填写！")
		}*/
	},// end of next step

	/**
	 * 选中或未选中自由跳转选项
	 * 
	 * @param {}
	 *            ck
	 * @param {}
	 *            checked
	 */
	freeJump : function(ck, checked) {
		if (this.formExtPanel != null && this.formExtPanel.noJumping) {
			this.formExtPanel.noJumping.call(this.formExtPanel, this.formPanel);
			this.isFreeJump = false;
			// ck.checked=false;
			ck.setValue(false);
		} else {
			if (checked) {
				// 自由跳转
				this.isFreeJump = true;
				this.jumpPanel.remove(this.jumpRadioGroup);
				this.freeTransCombo = new Ext.form.ComboBox({
					fieldLabel : '跳转任务',
					xtype : 'combo',
					allowBlank : false,
					editable : false,
					lazyInit : false,
					// anchor:'96%,96%',
					triggerAction : 'all',
					listeners : {
						scope : this,
						select : function(combo, record, index) {
							var destName = record.data.destName;
							var destType = record.data.destType;
							var isJumpToTargetTask = record.data.isJumpToTargetTask;
							if (isJumpToTargetTask == 0) {
								this.jumpPrompt.call(this, destName, ck);
								return;
							}
							this.getTaskUsers.call(this, destName, destType);
						}
					},
					store : new Ext.data.ArrayStore({
								autoLoad : true,
								url : __ctxPath
										+ '/flow/freeTransProcessActivity.do?taskId='
										+ this.taskId,
								fields : ['signalName', 'destName', 'destType',
										'isJumpToTargetTask']
							}),
					displayField : 'destName',
					valueField : 'signalName'
				});
				this.jumpPanel.insert(0, this.freeTransCombo);
				this.jumpPanel.doLayout();
			} else {
				// 非自由跳转
				this.isFreeJump = false;
				this.jumpPanel.remove(this.freeTransCombo);
				this.loadJumpTrans.call(this);
			}
		}
	},

	// 任务跳转提示
	jumpPrompt : function(destName, ck) {
		this.isFreeJump = false;
		ck.setValue(false);
		Ext.ux.Toast.msg('提示信息', '不能跳转到' + destName + '节点!');
	},

	// 流程退回
	backFlow : function() {
		Ext.Msg.confirm('信息确认', '您确认要回退所选记录吗？', function(btn) {
					if (btn == 'yes') {
						this.formPanel.getForm().submit({
							clientValidation : false,
							url : __ctxPath + "/flow/nextProcessActivity.do",
							scope : this,
							params : {
								useTemplate : this.useTemplate,
								taskId : this.taskId,
								destName : this.preTaskName,
								// 回退标志
								back : 'true'
							},
							success : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '成功回退！');
								AppUtil.removeTab('ProcessNextForm_'
										+ this.taskId);
								ZW.refreshTaskPanelView();
								if (officePanel) {
									officePanel.closeDoc();
								}
							},
							failture : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '回退失败！');
							}
						});
					}// end if
				}, // end of function
				this);
	},
	// 加载流程业务表单回调函数，用于业务数据回调，加上验证，业务字段的权限控制
	getFormHtmlCallback : function() {
		// 加上可以审批的意见
		// this.formPanel.add(new Ext.form.TextArea({
		// name:'comments',
		// anchor:'70%,70%',
		// fieldLabel:'审批意见'
		// }));
		// 使用自定义Ext模板表单
		var formExt = document.getElementById('formTaskExt' + this.taskId);
		
		if (formExt != null) {
			// 加上标识，表示是使用EXT模板进行
			this.useTemplate = true;

			var valExt = formExt.value;

			valExt = valExt.replace('Ext.form.FormPanel', 'Ext.Panel');

			var vmParams = '{taskId:' + this.taskId + '}';
			this.formExtPanel = eval('new (' + valExt + ')(' + vmParams + ');');
             
			if (this.formExtPanel.afterLoad) {
				this.formExtPanel.afterLoad.call(this.formExtPanel, this);
			}

			this.formExtPanel.outerFormPanel = this.formPanel;
			this.formPanel.add(this.formExtPanel);
			// 加上投票的意见
			if (this.voteSignFieldSet) {

				this.formPanel.add(this.voteSignFieldSet);
			}
			this.formPanel.doLayout();
			this.getprogressRunGridPanel.call(this);

			return;
		}
		// 加上投票的意见
		if (this.voteSignFieldSet) {

			this.formPanel.add(this.voteSignFieldSet);
		}
		this.formPanel.doLayout();

		try {
			var json = document.getElementById('entity_' + this.taskId);
			var rights = document.getElementById('rightstask_' + this.taskId);
			var name, type, value, xtype;
			// 加载JS回调函数
			var callback = function() {
				var entityJson = null;
				if (json != null && json.value) {
					entityJson = Ext.decode(json.value);
				}
				var rightJson = null;
				if (rights != null) {
					rightJson = Ext.decode(rights.value);
				}
				$converDetail.call(this, entityJson, rightJson);
			};
			// 后加载文档的JS
			$ImportSimpleJs(
					[__ctxPath + '/js/core/ntkoffice/NtkOfficePanel.js',
							__ctxPath + '/js/selector/SealSelector.js',
							__ctxPath + '/js/selector/PaintTemplateSelector.js'],
					callback, this);

		} catch (e) {
		}
	},

	/**
	 * 显示流程图
	 */
	showFlowImage : function() {
		var flowImagePanel = new Ext.Panel({
					autoHeight : true,
					border : false,
					html : '<img src="' + __ctxPath + '/jbpmImage?taskId='
							+ this.taskId + '&rand=' + Math.random() + '"/>'
				});

		var panel = new Ext.Panel({
					autoHeight : true,
					layout : 'form',
					border : false,
					items : [flowImagePanel]
				});

		if (this.isSubFlow) {// 若当前为子流程，则显示子流程
			panel.add({
						xtype : 'panel',
						autoHeight : true,
						border : false,
						html : '<img src="' + __ctxPath + '/jbpmImage?taskId='
								+ this.taskId + '&isSubFlow=true&rand='
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
	},
	getprogressRunGridPanel : function() {
		var level;
		this.expanderFlow = new Ext.ux.grid.RowExpander({/*
															 * tpl : new
															 * Ext.Template( '', '<p><b>意见与说明:</b>
															 * {comments:this.formatDisplacementValue}', {
															 * formatDisplacementValue :
															 * function(obj) {
															 * re = /u000a/g; //
															 * 创建正则表达式模式。 r =
															 * obj.replace(re, "<br>"); //
															 * 用 "<br>" // 替换
															 * "\n"。 return (r); //
															 * 返回替换后的字符串。 } }), //
															 * tpl : new
															 * Ext.Template( // '<tpl
															 * if="{iafeLevel} //
															 * >2">','<p><b>意见与说明:</b>','12321','<l>' // )
															 * getRowClass :
															 * function(record,
															 * rowIndex, p, ds) {
															 * p.cols = p.cols -
															 * 1; var content =
															 * this.bodyContent[record.id];
															 * if (!content) {// &&
															 * !this.lazyRender){
															 * content =
															 * this.getBodyContent(record,
															 * rowIndex); } if
															 * (content) {
															 * p.body = content; }
															 * return
															 * this.state[record.id] ?
															 * 'x-grid3-row-expanded' :
															 * 'x-grid3-row-expanded'; }
															 * 
															 */});

		this.safelevel = this.formExtPanel.safeLevel;
		level = this.safelevel;
		if (typeof(this.safelevel) == "undefined") {
			this.safelevel = 0;
		}
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
						url : __ctxPath
								+ '/creditFlow/runListCreditProject.do?taskId='
								+ this.taskId + '&filterableNodeKeys='
								+ filterableNodeKeys// + this.safelevel// ,
						}),
					baseParams : {
						RunType : 1
					},
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
								fields : ['activityName', 'creatorName',
										'status', 'createtime', 'endtime',
										'durTimes', 'comments', 'safeLevel']
							}),
					remoteSort : true
				});
		this.store.load();
		this.progressRunGridPanel = new Ext.grid.GridPanel({
			store : this.store,
			defaults : {
				anchor : '96%'
			},
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			stripeRows : true,
			autoExpandColumn : 'comments',
			border : true,
			name : "progressRunGridPanel",
			stateful : true,
			showPaging : false,
			autoHeight : true,
			plugins : this.expanderFlow,
			// collapsible: true,
			animCollapse : false,
			cm : new Ext.grid.ColumnModel({
				defaults : {
					width : 130,
					sortable : false
				},
				// columns : [this.expanderFlow, new Ext.grid.RowNumberer({
				columns : [new Ext.grid.RowNumberer({
									width : 30
								}), {
							id : 'activityName',
							header : '节点名称',
							width : 140,
							sortable : true,
							dataIndex : 'activityName'
						}, {
							header : '执行人',
							width : 70,
							dataIndex : 'creatorName',
							renderer : function(val) {
								if (val == null || val == 'null') {
									return '<span style="color:red;">暂无执行人</span>';
								} else {
									return val;
								}
							}
						}, {
							header : '开始时间',
							width : 136,
							// xtype : 'datecolumn',
							// format : 'M d, Y',
							dataIndex : 'createtime'
						}, {
							// xtype : 'datecolumn',
							// format : 'M d, Y',
							width : 136,
							header : '结束时间',
							dataIndex : 'endtime',
							renderer : function(val) {
								if (val == null || val == 'null') {
									return '';
								} else {
									return val;
								}
							}
						}, {
							header : '耗时',
							width : 145,
							dataIndex : 'durTimes',
							renderer : function(val) {
								var days = parseInt(val / 86400000);
								var hours = parseInt((val - days * 86400000)
										/ 3600000);
								var minute = parseInt((val - days * 86400000 - hours
										* 3600000)
										/ 60000);
								var second = (val - days * 86400000 - hours
										* 3600000 - minute * 60000)
										/ 1000;

								return '<span style="color:gray;">' + days
										+ '天' + hours + '小时' + minute + '分'
										+ second + '秒' + '</span>';;

							}
						}, {
							header : '意见与说明',
							width : 300,
							dataIndex : 'comments',
							renderer : function(value, metadata, record,
									rowIndex, colIndex) {
								// var safeLevel=record.data.safeLevel;
								// var activityName=record.data.activityName;

								/*
								 * if(safeLevel==3&&level!=3&&"总裁审批"!=activityName&&"审保会决议确认"!=activityName){
								 * return '您无权查看该意见与说明!'; }else
								 */
								if (value == null || value == ''
										|| value == 'null') {
									return '';
								} else {
									metadata.attr = 'style="white-space:normal;"';
									re = /u000a/g; // 创建正则表达式模式。
									r = value.replace(re, "<br>"); // 用"<br>"替换"\n"。
									return (r);// 返回替换后的字符串。
								}
							}
						}, {
							header : '审批状态',
							align : "center",
							width : 70,
							dataIndex : 'status',
							renderer : function(val) {
								if (val == 1) {
									return '<span style="color:green;">审批通过</span>';
								} else if (val == -1) {
									return '<span style="color:red;">驳回</span>';
								} else if (val == 2) {
									return '<span style="color:orange;">流程跳转</span>';
								} else if (val == 3) {
									return '<span style="color:red;">打回重做</span>';
								} else if (val == 4) {
									return '<span style="color:red;">任务追回</span>';
								} else if (val == 5) {
									return '<span style="color:orange;">任务换人</span>';
								} else if (val == 6) {
									return '<span style="color:orange;">项目换人</span>';
								} else if (val == 7) {
									return '<span style="color:orange;">项目终止</span>';
								}
								return "未审批";
							}
						}]
			})
		});
		this.bigprogressRunGridPanel.add(this.progressRunGridPanel)
		this.doLayout();

	},
	/**
	 * 没有下一节点时 create by gengjj
	 * 
	 * @param {}
	 *            destName 目标节点的名称
	 * @param {}
	 *            destType 目标节点的类型
	 */
	getNoNextTaskRadioCheck : function() {
		this.buttonPanel = new Ext.Panel({
					layout : 'form',
					border : false,
					labelWidth : 100,
					items : [{
								layout : 'form',
								border : false,
								labeborderlWidth : 100,
								items : [{
											xtype : /*'fckeditor'*/'textarea',
											width : 1000,
											height : 200,
											style : "margin-top:4px",
											name : "comments",
											anchor : "100%",
											labelWidth : 100,
											//allowBlank : false,
											fieldLabel : "意见与说明"
										}, {
											border : false,
											layout : {
												type : 'hbox',
												pack : 'center',
												align : 'middle' // top、middle、bottom：顶对齐、居中、底对齐；stretch：延伸；stretchmax：以最大的元素为标准延伸
											},
											defaults : {
												xtype : 'button',
												width : 120,
												margins : '5 5 0 0'
											},
											items : [/*
														 * { //xtype :
														 * 'checkbox',
														 * //boxLabel : '自由跳转',
														 * text : '自由跳转', scope :
														 * this, hidden : false,
														 * handler :
														 * this.freeJump },
														 */{
														xtype : 'button',
														text : '快速保存信息',
														scope : this,
														iconCls : 'btn-save',
														handler : this.saveStep
													}, {
														xtype : 'button',
														text : '提交任务',
														scope : this,
														iconCls : 'btn-transition',
														handler : this.nextStep
													}]
										}]
							}]
				});

		this.jumpPanel.add(this.buttonPanel);
		this.jumpPanel.doLayout();
		this.jumpPanel.addClass("borderfalse");
	},
	/**
	 * 审贷会时 create by gengjj
	 * 
	 * @param {}
	 *            destName 目标节点的名称
	 * @param {}
	 *            destType 目标节点的类型
	 */
	getSignTaskRadioCheck : function() {
		this.buttonPanel = new Ext.Panel({
					layout : 'form',
					border : false,
					labelWidth : 100,
					items : [{
								layout : 'form',
								border : false,
								labeborderlWidth : 100,
								items : [{
											border : false,
											layout : {
												type : 'hbox',
												pack : 'center',
												align : 'middle' // top、middle、bottom：顶对齐、居中、底对齐；stretch：延伸；stretchmax：以最大的元素为标准延伸
											},
											defaults : {
												xtype : 'button',
												width : 120,
												margins : '5 5 0 0'
											},
											items : [/*
														 * { //xtype :
														 * 'checkbox',
														 * //boxLabel : '自由跳转',
														 * text : '自由跳转', scope :
														 * this, hidden : false,
														 * handler :
														 * this.freeJump },
														 */{
														xtype : 'button',
														text : '快速保存信息',
														scope : this,
														iconCls : 'btn-save',
														handler : this.saveStep
													}, {
														xtype : 'button',
														text : '提交任务',
														scope : this,
														iconCls : 'btn-transition',
														handler : this.nextStep
													}]
										}]
							}]
				});

		this.jumpPanel.add(this.buttonPanel);
		this.jumpPanel.doLayout();
		this.jumpPanel.addClass("borderfalse");
	},
	getTaskRadioCheck : function(object) {
		var radioItems = [];
		for (var i = 0; i < object.data.length; i++) {
			destName = object.data[i].destination;
			destType = object.data[i].destType;
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
								temp.push(outers[i][1]);   //设置提交的值
								temp.push(boxValue);       //设置下拉选项的名字
								simpleStoreData.push(temp);//push到storeNode大数组中
							}
							
							storeNode =  new Ext.data.SimpleStore({
								data :   simpleStoreData,
								fields : ["value", "name"]
							})
							
							this.jumpCombobox = new Ext.form.ComboBox({  
									width:600,
									name : 'taskSubmit',
									fieldLabel : '任务提交至',
									hiddenName : 'signalName',
									allowBlank: false,
									readOnly:false,
									store : storeNode,
									editable : false,
									valueField: 'value',
									displayField:'name',
									mode: 'local',
									triggerAction: 'all',
									emptyText:'请选择'
							});
							this.jumpCombobox.setValue(defaultValue);

							this.jumpPanel.insert(0,this.jumpCombobox); 
							this.jumpPanel.doLayout();
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
								temp.push(outers[i][1]);   //设置提交的值
								temp.push(boxValue);       //设置下拉选项的名字
								simpleStoreData.push(temp);//push到storeNode大数组中
							}
							
							storeNode =  new Ext.data.SimpleStore({
								data :   simpleStoreData,
								fields : ["value", "name"]
							})
							
							this.jumpCombobox = new Ext.form.ComboBox({  
									width:600,
									name : 'taskSubmit',
									fieldLabel : '任务提交至',
									hiddenName : 'signalName',
									allowBlank: false,
									readOnly:false,
									editable : false,
									store : storeNode,
									valueField: 'value',
									displayField:'name',
									mode: 'local',
									triggerAction: 'all',
									emptyText:'请选择'
							});
							this.jumpCombobox.setValue(defaultValue); //设置默认选项

							this.jumpPanel.insert(0,this.jumpCombobox); 
							this.jumpPanel.doLayout();
						} 
					}
				});

			} else if (destType.indexOf('end') != -1) {
				// 下一节点为结束节点，需要隐藏下一步的执行人
				this.getNoNextTaskRadioCheck.call(this)
				// 若为结束节点，需要考虑父流程的处理方式，所以在这里，需要判断一下，当前流程是否为子流程，若是的话，则需要加载其跳回父流程的路径
				if (this.isSubFlow) {
					// ----------------------子流程
					this.getOutToParentFlow.call(this);
				}
				
				this.jumpCombobox = new Ext.form.ComboBox({ 
									width:600,
									name : 'taskSubmit',
									fieldLabel : '任务提交至',
									hiddenName : 'signalName',
									allowBlank: false,
									readOnly:false,
									store : new Ext.data.SimpleStore({
										fields : ["value", "name"],
										data : [[object.data[i].destination, object.data[i].destination]]
									}),
									valueField: 'value',
									editable : false,
									displayField:'value',
									mode: 'local',
									triggerAction: 'all',
									emptyText:'请选择'
							});		
				this.jumpCombobox.setValue(object.data[i].destination);
						
				this.jumpPanel.insert(0,this.jumpCombobox); 
				this.jumpPanel.doLayout();
				return;

			} else {// 下一节点为普通任务节点
				this.jumpCombobox = new Ext.form.ComboBox({  
									width:600,
									name : 'taskSubmit',
									fieldLabel : '任务提交至',
									hiddenName : 'signalName',
									allowBlank: false,
									readOnly:false,
									editable : false,
									store : new Ext.data.SimpleStore({
										fields : ["value", "name"],
										data : [[object.data[i].destination, object.data[i].destination]]
									}),
									valueField: 'value',
									displayField:'value',
									mode: 'local',
									triggerAction: 'all',
									emptyText:'请选择'
							});		
				this.jumpCombobox.setValue(object.data[i].destination);
				this.jumpPanel.insert(0,this.jumpCombobox); 
				this.jumpPanel.doLayout();
			}
		}
		
		this.addmsgPanel.call(this);
	},
	addmsgPanel : function() {
		this.commentsPanel = new Ext.Panel({
			layout : 'form',
			border : false,
			items : [{
						layout : 'form',
						border : false,
						fieldLabel : '任务提醒方式',
						items : [{
									layout : "column",
									items : [{
												xtype : 'checkbox',
												style : "margin-left:10px",
												boxLabel : '任务提醒',
												scope : this,
												hidden : true,
												disabled : true,
												checked : true

											}, {
												xtype : 'checkbox',
												boxLabel : '邮件提醒',
												style : "margin-left:10px",
												name : "emailcheck",
												scope : this,
												handler : function(ck, checked) {
													if (checked) {
														this.sendMail = true;
													} else {
														this.sendMail = false;
													}
												}
											}, {
												xtype : 'checkbox',
												style : "margin-left:10px",
												boxLabel : '短信提醒',
												name : "messagecheck",
												scope : this,
												handler : function(ck, checked) {
													if (checked) {
														this.sendMsg = true;
													} else {
														this.sendMsg = false;
													}
												}
											},
											/* 
											 * 发消息给之前的所有人 start
											 */
											{
												xtype : 'checkbox',
												boxLabel : '消息提醒',
												style : "margin-left:10px",
												name : "messageremind",
												scope : this,
												handler : function(ck, checked) {
													if (checked) {
														this.sendCallBefore = true;
													} else {
														this.sendCallBefore = false;
													}
												}
											}
											/* 
											 * 发消息给之前的所有人 end
											 */
											 ]
								}]
					}, {
						xtype : /*'fckeditor'*/'textarea',
						width : 1000,
						height : 200,
						style : "margin-top:4px",
						name : "comments",
						anchor : "100%",
						//allowBlank : false,
						fieldLabel : "意见与说明"
					}, {
						border : false,
						layout : {
							type : 'hbox',
							pack : 'center',
							align : 'middle' // top、middle、bottom：顶对齐、居中、底对齐；stretch：延伸；stretchmax：以最大的元素为标准延伸
						},
						defaults : {
							xtype : 'button',
							width : 120,
							margins : '5 5 0 0'
						},
						items : [/*
									 * { //xtype : 'checkbox', //boxLabel :
									 * '自由跳转', text : '自由跳转', scope : this,
									 * hidden : false, handler : this.freeJump },
									 */{
									xtype : 'button',
									text : '快速保存信息',
									scope : this,
									iconCls : 'btn-save',
									handler : this.saveStep
								}, {
									xtype : 'button',
									text : '提交任务',
									scope : this,
									iconCls : 'btn-transition',
									handler : this.nextStep
								}]
					}]
		});
		this.jumpPanel.add(this.commentsPanel);
		this.jumpPanel.doLayout();

	},
	closetab : function() {
	}

});
