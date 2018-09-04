// 1可编辑2不可编辑
// ah_a1 :基本项目信息 12
// ah_b1 :企业/个人信息12
// ah_c1 :款项12
// ah_d1:第一还款来源12
// ah_e1:共同借款人12
// ah_f1 :担保物品12 3
// ah_g1 :准入原则12
// ah_h1 :资料清单12
// ah_i1 :调研报告12
// ah_j:风险专员
// ah_k1:风险报告
// ah_l:审贷会
// ah_m:审贷会意见总会 写
// ah_n:审贷会意见
// ah_o:合同列表
// ah_p:归档材料
// 生产总控制,对js没太大用，给人看的
// 全局单例工厂
_getFactory = function() {
	return {
		create : function(fac, pson) {
			var f = {};
			if (fac == "ah_1") { // 精致调查
				return makeF.mack(1,pson);
			}
			if (fac == "ah_2") {// 审贷会
				return makeF.mack(2,pson);
			}
			if (fac == "ah_3") {// 普通青年
				return makeF.mack(3,pson);
			}
			if (fac == "ah_a1") {

				return makeF.mackA(false);
			}
			if (fac == "ah_a2") {
				return makeF.mackA(true);
			}

			if (fac == "ah_b1") {
				if (pson.oppositeType == "company_customer")
					return makeF.mackB1(pson, false);
				if (pson.oppositeType == "person_customer")
					return makeF.mackB2(pson, false);
			}

			if (fac == "ah_b2") {
				if (pson.oppositeType == "company_customer")
					return makeF.mackB1(pson, true);
				if (pson.oppositeType == "person_customer")
					return makeF.mackB2(pson, true);
			}
			
			

			if (fac == "ah_c1") {
				return makeF.makeC(pson, false,null,10);
			}
			if (fac == "ah_c2") {
				return makeF.makeC(pson, true);// 只读
			}

			if (fac == "ah_c3") {
				return makeF.makeC(pson, true, true, 1);// 只读    
			}

			if (fac == "ah_c4") {
				return makeF.makeC(pson, true, true, 0);// 只读+修改启示
			}
			if (fac == "ah_c5") {//add by gao 需求要求  贷款发放确认节点，放款收息表部分没有“增加”、“删除”按钮，此节点有此操作权限；
				return makeF.makeC(pson, true, true, 2);// 只读+修改启示
			}
			if (fac == "ah_c6") {//add by gao 需求要求  贷款发放确认节点，放款收息表部分没有“增加”、“删除”按钮，此节点有此操作权限；
				return makeF.makeC(pson, true, true, 3);// 只读+修改启示
			}
			if (fac == "ah_d1") {
				return makeF.makeD(pson, false);
			}

			if (fac == "ah_d2") {
				return makeF.makeD(pson, true);
			}

			if (fac == "ah_e1") {
				return makeF.makeE(pson, false);
			}
			if (fac == "ah_e2") {
				return makeF.makeE(pson, true);
			}

			if (fac == "ah_f1") { // 添加
				// 1.相关按钮2.相关文件
				// 按钮可用，文件不可落实
				return makeF.makeF(pson, false, true, "");
			}

			if (fac == "ah_f2") {// 查看
				return makeF.makeF(pson, true, true, "");
			}
			if (fac == "ah_f3") { // 办理
				return makeF.makeF(pson, true, true, "bl");
			}

			if (fac == "ah_f4") { // 归档
				return {
					xtype : 'fieldset',
					title : '担保措施',
					collapsible : true,
					autoHeight : true,
					items : [new DZYMortgageView({
										projectId : pson.projectId,
										titleText : '抵质押担保',
										businessType : pson.businessType,
										isHiddenAddContractBtn : true,
										isHiddenDelContractBtn : true,
										isHiddenEdiContractBtn : true,
										isHiddenAddBtn : true,
										isHiddenDelBtn : true,
										isHiddenEdiBtn : true,
										isSeeContractHidden : false,
										isHiddenRelieve : true,
										isblHidden : false,
										isblEdit : false,
										isgdHidden : true,
										isgdEdit : false,
										isRecieveHidden : false,
										isRecieveEdit : true
									}), new BaozMortgageView({
										projectId : pson.projectId,
										titleText : '保证担保',
										businessType : pson.businessType,
										isHiddenAddContractBtn : true,
										isHiddenDelContractBtn : true,
										isHiddenEdiContractBtn : true,
										isHiddenAddBtn : true,
										isHiddenDelBtn : true,
										isHiddenEdiBtn : true,
										isSeeContractHidden : false,
										isHiddenRelieve : true,
										isblHidden : true,
										isgdHidden : true,
										isgdEdit : false,
										isRecieveHidden : false,
										isRecieveEdit : true
									})]
				}
			}

			if (fac == "ah_g1") {// 查看
				return makeF.makeG(pson, false, false);
			}
			if(fac == "ah_g11"){//add by gao 不显示风险经理意见
				return makeF.makeG1(pson, false, false);
			}

			if (fac == "ah_g2") {// 调查
				return makeF.makeG(pson, true, false);
			}
			if(fac == "ah_g21"){//add by gao  不显示风险经理意见
				return makeF.makeG1(pson, true, false);
			}
			if (fac == "ah_g3") {// 风险
				return makeF.makeG(pson, false, true);
			}
			if (fac == "ah_h1") {
				return makeF.makeH(pson, false);
			}
			if (fac == "ah_h2") {
				return makeF.makeH(pson, true);
			}
			if (fac == "ah_i1") {
				return makeF.makeI(pson, false);
			}
			if (fac == "ah_i2") {
				return makeF.makeI(pson, true);
			}
			if (fac == "ah_j") {
				return  makeF.makeJ(pson);
			}
			if (fac == "ah_k1") {
				return makeF.makeK(pson, false);
			}
			if (fac == "ah_k2") {
				return makeF.makeK(pson, true);
			}
			if (fac == "ah_l") {
				return  makeF.makeL(pson);
			}
			if (fac == "ah_m") {
				return makeF.makeM(pson);
			}
			if (fac == "ah_n") {
				return makeF.makeN(pson);
			}
			if (fac == "ah_o") {
				return makeF.makeO(pson);
			}
			if (fac == "ah_p") {
				return makeF.makeP(pson);
			}
			if(fac=='ah_pc1'){
				return makeF.makePC(pson,false);
			}
			if(fac=='ah_pc2'){
				return makeF.makePC(pson,true);
			}
			if (fac == "ah_p1") {
				return {
					xtype : 'fieldset',
					title : '归档材料',
					items : new PlArchivesMaterialsView({
								projectId : pson.projectId,
								businessType : pson.businessType,
								isHidden_materials : true,
								isHidden_materialsxs : true,
								isHidden_materialsyl : true,
								isHiddenAffrim : false,
								isEditAffrim : true,
								isHiddenRecive : true,// 提交时间
								isHiddenRecive : true,// 提交备注
								isHiddenAffrim : false,// 受到时间
								isHiddenAffrim : false,// 备注
								isHiddenType : false
							})
				}
			}
			if (fac == "ah_p2") {
				return {
					xtype : 'fieldset',
					title : '归档材料',
					items : new PlArchivesMaterialsView({
								projectId : pson.projectId,
								businessType : pson.businessType,
								isHidden_materials : false,
								isHidden_materialsxs : true,
								isHidden_materialsyl : true,
								isHiddenAffrim : false,
								isEditAffrim : true,
								isHiddenRecive : true,// 提交时间
								isHiddenRecive : true,// 提交备注
								isHiddenAffrim : false,// 受到时间
								isHiddenAffrim : false,// 备注
								isHiddenType : false
							})
				}
			}

			if (fac == "1") {

				return new Ext.OfferView({
							projectId : pson.projectId,
							titleText : 'offer商的维护',
							businessType : pson.businessType,
							isHiddenAddBtn : true,
							isHiddenDelBtn : true,
							isHiddenEdiBtn : true,
							isSeeContractHidden : false,
							isblEdit : true,
							isHiddenRelieve : true,
							isblHidden : false,
							isgdHidden : true,
							isRecieveHidden : true,
							isHandleHidden : true,
							isSignHidden : false
						})
			}
			// 全都不等于返回f={}，后面判断
			return f;
		}

	}
}()

makeF = function() {
	return {
		mack : function(v,p) {
			if (v == 1) {
				return [{
							xtype : 'hidden',
							name : 'needSave',
							value : '1'
						}, {
							xtype : 'hidden',
							name : 'gudongInfo'
						}, {
							xtype : 'hidden',
							name : 'borrowerInfo'
						}, {
							xtype : 'hidden',
							name : 'repaymentSource'
						}, {
							xtype : 'hidden',
							name : 'slActualToChargeJson'
						},{
							xtype : 'hidden',
							name : 'fundIntentJsonData'
						}, {
							xtype : 'hidden',
							name : 'task_id',
							value : p.taskId
						}]
			}
			if (v == 2) {
				return [{
							xtype : 'hidden',
							name : 'needSave',
							value : '2'
						}, {
							xtype : 'hidden',
							name : 'repaymentSource'
						}, {
							xtype : 'hidden',
							name : 'slActualToChargeJson'
						},{
							xtype : 'hidden',
							name : 'fundIntentJsonData'
						}, {
							xtype : 'hidden',
							name : 'safeLevel',
							value : 1
						}, {
							xtype : 'hidden',
							name : 'task_id',
							value : p.taskId
						}, {
							xtype : 'hidden',
							name : 'projectId',
							value : p.projectId
						}]
			}

			if (v == 3) {
				return [{
							xtype : 'hidden',
							name : 'needSave',
							value : '3'
						}, {
							xtype : 'hidden',
							name : 'task_id',
							value : p.taskId
						},{
							xtype : 'hidden',
							name : 'fundIntentJsonData'
						}]
			}
		},
		mackA : function(v) {
			return {
				xtype : 'fieldset',
				name : 'projectInfo',
				title : '项目基本信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : {
					xtype : "whbasinfo",
					isDiligenceReadOnly : v,
					isAllReadOnly : v
				}
			}
		},
		mackB1 : function(pson, v) {
			return {
				xtype : 'fieldset',
				title : '企业客户信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : new ExtUD.Ext.PeerMainInfoPanel({
					id : "ext_perMain",
					projectId : pson.projectId,
					bussinessType : pson.businessType,
					isAllReadOnly : v, // 编辑a 这些看
					isNameReadOnly : v, // 编辑b
					isReadOnly : v,// 贷款账户编辑
					isHidden : false,
//					isEditEnterprise : false
					isEditEnterprise : !v,//
					bankAreaRootControl:true
						// 可以编辑人
					})
			}
		},
		mackB2 : function(pson, v) {
			return {

				xtype : 'fieldset',
				title : '个人客户信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : new ExtUD.Ext.PeerPersonMainInfoPanel({
							isEditPerson : !v,
							projectId : pson.projectId,
							isReadOnly : v,
							isAllReadOnly:v,
							isSpouseReadOnly : true,
							isNameReadOnly : true
						})
			}
		},
		makeC : function(pson, v, f, e) {//e  改为数字判断 by gao
			var a = new ExtUD.Ext.newProjectInfoFinancePanel({
				id : "ext_projectInfoFinance",
				isDiligenceReadOnly : v, // ？
				projectId : pson.projectId,
				isStartDateReadOnly : (e == 2 || e==10)? false : true, // ？
				idDefinition : 'Smallliucheng' + pson.taskId,
				isHiddencalculateBtn : false,
				isHiddenbackBtn : false,
				isHiddenParams : true,
				isAllReadOnly : v, // 大部分只读
				isProjectMoneyReadOnly : true
					// 判断放款金额是否只读
				});
			if (f == true) {
				var b = new SlActualToChargeVM({
							id : "ext_slActualToCharge",
							projId : pson.projectId,
							businessType : 'SmallLoan',// 小贷
							isHiddenCheckBtn : false,
							isHidden : v
						});
				if(e==1){
					b = new SlActualToChargeVM({
							id : "ext_slActualToCharge",
							projId : pson.projectId,
							businessType : 'SmallLoan',// 小贷
							isHiddenCheckBtn : false,
							isHidden : v,
							isHiddenCheckBtn:true
							});
				}
			} else {
				var b = new SlActualToCharge({
							id : "ext_slActualToCharge",
							projId : pson.projectId,
							businessType : 'SmallLoan',// 小贷
							isHidden : v
						});
			}
			var d = [a,b];
			if (e == 1) {
				var c = new SlFundIntentViewVM({
					id:"ext_slFundIntentViewVm"+pson.projectId,
					projectId : pson.projectId,
					object : a,
					projectInfoObject : pson.projectInfo,
					businessType : 'SmallLoan',
					isHiddenAddBtn : false,// 生成
					isHiddenAddBtn : false,// 增加
					isHiddenDelBtn : false,// 删除
					isHiddenCanBtn : true,// 取消
					isHiddenResBtn : true,// 还原
					isHiddenResBtn1 : true,// 手动对账
					isHiddenseeqlideBtn : false,// 查看流水单项订单
					isHiddenseesumqlideBtn : true
						// 项目流水记录

					});
				d = [a,b, c];
			}
			if (e == 0) {
				var c = new SlFundIntentViewVM({
							object : a,
							projectId : pson.projectId,
							object : pson.projectInfoFinance,
							projectInfoObject : pson.projectInfo,
							businessType : 'SmallLoan',
							isHiddenAddBtn : true,
							isHiddenDelBtn : true,
							isHiddenResBtn1 : false,// 手动对账
							isHiddenseeqlideBtn : false,// 查看单项流水记录
							isHiddenExcel : false,// 导出ext
							isHiddenOverdue : true,
							isHiddenseesumqlideBtn : true,
							isHiddenautocreateBtn : true,
							isFinbtn : true

						});
				d = [a,b, c];
			}
			if (e == 2) {
				var c = new SlFundIntentViewVM({
					projectId : pson.projectId,
					object : a,
					projectInfoObject : pson.projectInfo,
					businessType : 'SmallLoan',
					isHiddenAddBtn : false,// 生成
					isHiddenDelBtn : false,// 删除
					isHiddenCanBtn : true,// 取消
					isHiddenResBtn : true,// 还原
					isHiddenResBtn1 : false,// 手动对账
					isHiddenseeqlideBtn : false,// 查看流水单项订单
					isHiddenseesumqlideBtn : true,
					isHiddenautocreateBtn : true
						// 项目流水记录
					});
				d = [a,b, c];
			}
			if (e == 3) {
				var c = new SlFundIntentViewVM({
					projectId : pson.projectId,
					object : a,
					projectInfoObject : pson.projectInfo,
					businessType : 'SmallLoan',
					isHiddenAddBtn : false,// 生成
					isHiddenDelBtn : false,// 删除
					isHiddenCanBtn : true,// 取消
					isHiddenResBtn : true,// 还原
					isHiddenResBtn1 : false,// 手动对账
					isHiddenseeqlideBtn : false,// 查看流水单项订单
					isHiddenseesumqlideBtn : true,
					isHiddenautocreateBtn : false
						// 项目流水记录
					});
				d = [a,b, c];
			}
			return {
				xtype : 'fieldset',
				title : '贷款基本信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				name : 'financeInfoFieldset',
				autoHeight : true,
				items : d
			}
		},
		makeD : function(pson, v) {
			return {

				xtype : 'fieldset',
				title : '第一还款来源',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : [new RepaymentSource({
							id : 'ext_repaymentSource',
							isHiddenAddBtn : v,
							isHiddenDelBtn : v,
							projectId : pson.projectId
						})]
			}
		},
		makeE : function(pson, v) {
			return {
				xtype : 'fieldset',
				title : '共同借款人信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : new BorrowerInfo({
							id : "ext_borrowerInfo",
							projectId : pson.projectId,
							isHidden : v,
							isHiddenAddBtn : v,
							isHiddenDelBtn : v,
							isReadOnly : v
						})
			}
		},
		makeF : function(pson, v, b, g) {
			if (g == "bl") {
				return {
					xtype : 'fieldset',
					title : '担保措施',
					collapsible : true,
					autoHeight : true,
					items : [new DZYMortgageView({
								projectId : pson.projectId,
								titleText : '抵质押担保',
								businessType : pson.businessType,
								isHiddenAddBtn : true,
								isHiddenDelBtn : true,
								isHiddenEdiBtn : true,
								isSeeContractHidden : false,
								isblEdit : true,
								isHiddenRelieve : true,
								isblHidden : false,
								isgdHidden : true,
								isRecieveHidden : true,
								isHandleHidden : true,
								isSignHidden : false,
								formPanel : Ext
										.getCmp("ext_projectInfoFinance")
							}), new BaozMortgageView({
								projectId : pson.projectId,
								titleText : '保证担保',
								businessType : pson.businessType,
								isHiddenAddBtn : true,
								isHiddenDelBtn : true,
								isHiddenEdiBtn : true,
								isSeeContractHidden : false,
								isblEdit : true,
								isHiddenRelieve : true,
								isblHidden : false,
								isgdHidden : true,
								isRecieveHidden : true,
								isSignHidden : false
							})]
				}

			}

			return {
				xtype : 'fieldset',
				title : '担保措施',
				collapsible : true,
				autoHeight : true,
				items : [new DZYMortgageView({
							projectId : pson.projectId,
							titleText : '抵质押担保',
							isAllReadOnly : g,// 。。。
							businessType : pson.businessType,
							isHiddenAddBtn : v, // 增加抵质押物
							isHiddenDelBtn : v,
							isHiddenEdiBtn : v,

							isfwEdit : b, // 是否法务确认
							isqsEdit : b, // 是否签署并检验合格
							isHiddenRelieve : b, // 是否解除
							isblHidden : b,// 是否办理/落实
							isgdHidden : b,// 提交时间
							isRecieveHidden : b,
							hidden : b,
							formPanel : Ext.getCmp("ext_projectInfoFinance")
								// 是否已收到
								// 收到时间
							}), new BaozMortgageView({
									projectId : pson.projectId,
									titleText : '保证担保',
									isAllReadOnly : g,
									businessType : pson.businessType,
									isHiddenAddBtn : v,// 增加保证担保
									isHiddenDelBtn : v,
									isHiddenEdiBtn : v,
									isfwEdit : b,
									isqsEdit : b,
									isHiddenRelieve : b,
									isblHidden : b,
									isgdHidden : b,
									isblEdit : !b,
									isRecieveHidden : b

								})]
			}
		},
		makeG : function(pson, d, f) {
			return {
				xtype : 'fieldset',
				title : '贷款准入原则',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : new SlProcreditAssuretenetedForm({
					businessType : 'SmallLoan',
					projectId : pson.projectId,
					isEditBbusinessmanageropinion : d,
					isEditRiskmanageropinion : false,// 有风险经理
					isEditRiskmanagercombox : f
						// 风险编辑

					})

					
			}
		},
		makeG1 : function(pson, d, f) {//by gao
			return {
				xtype : 'fieldset',
				title : '贷款准入原则',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				items : new SlProcreditAssuretenetedForm({
					businessType : 'SmallLoan',
					projectId : pson.projectId,
					isEditBbusinessmanageropinion : d,
					isEditRiskmanageropinion : true,// 有风险经理
					isEditRiskmanagercombox : f
						// 风险编辑

					})
			}
		},
		makeH : function(pson, v) {
			return {
				xtype : 'fieldset',
				title : '贷款资料清单',
				collapsible : true,
				autoHeight : true,
				bodyStyle : 'padding-left: 0px',
				items : new SlEnterPriseProcreditMaterialsView({
			    projectId : pson.projectId,
				businessType : 'SmallLoan',
			    isHidden_materials:v,
			    isHiddenArchive : !v,
			    operationType:'SmallLoan_SmallLoanBusiness'
		    })

			}
		},
		makeI : function(pson, v) {
			return {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '调查报告',
				items : [new SlReportView({
							projectId : pson.projectId,
							businessType : 'SmallLoan',
							isHidden_report : v
						})]
			}
		},
		makeJ : function(pson) {
			return {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '风险专员设置',
				labelWidth :80,
				labelAlign : 'right',
				items : [{
							xtype : "hidden",
							name : "riskAttacheId"
						},{
					fieldLabel : "风险专员",
					xtype : "combo",
					allowBlank : false,
					editable : false,
					triggerClass : 'x-form-search-trigger',
					itemVale : creditkindDicId, // xx代表分类名称
					hiddenName : "appUsersOfA",
					readOnly : false,
					anchor : "70%",
					onTriggerClick : function(cc) {
						var obj = this;
						var appuerIdObj = obj.previousSibling();
						new UserDialog({
									// single : false,//风险专员只能选择一个
									userIds : appuerIdObj.getValue(),
									userName : obj.getValue(),
									title : "风险专员",
									callback : function(uId, uname) {
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
					}
				}]
				
			}
		},
		makeK : function(pson, v) {
			return {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '风险分析报告',
				items : [new SlRiskReportView({
							projectId : pson.projectId,
							businessType : 'SmallLoan',
							isHidden_riskReport : v
						})]
			}
		},
		makeL : function(pson) {
			return {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				title : '线上评审会成员设置',
				labelWidth : 120,
				labelAlign : 'right',
				items : [{
							xtype : "hidden",
							name : "onlineJudgementIds"
						},{
					fieldLabel : "线上评审会成员",
					xtype : "combo",
					allowBlank : true,
					editable : false,
					triggerClass : 'x-form-search-trigger',
					itemVale : creditkindDicId, // xx代表分类名称
					hiddenName : "appUsersOfA",
					readOnly : this.isAllReadOnly,
					anchor : "70%",
					onTriggerClick : function(cc) {
						var obj = this;
						var appuerIdObj = obj.previousSibling();
						new UserDialog({
									single : false,// 线上评审会成员，多个
									userIds : appuerIdObj.getValue(),
									userName : obj.getValue(),
									title : "风险专员",
									callback : function(uId, uname) {
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
					}
				}]
				
			}
		},
		makeM : function(pson) {
			return {
				title : '上传会议纪要',
				labelAlign : "right",
				bodyStyle : 'padding-left:5px',
				items : [new MeetingSummaryForm({
							projectId : pson.projectId,
							businessType : pson.businessType,
							isReadOnly : false,
							isHidden : false
						})]
			}
		},
		makeN : function(pson) {
			return {
				xtype : 'fieldset',
				collapsible : true,
				autoHeight : true,
				name : 'censorMeeting',
				title : '审贷会意见',
				items : [new CensorMeetingCollectivityDecisionConfirm({
							runId : pson.runId,
							taskId : pson.taskId,
							countersignedTaskKey : pson.node
						})]
			}
		},
		makeO : function(pson) {
			return {
				xtype : 'fieldset',
				title : '贷款合同',
				collapsible : true,
				autoHeight : true,
				items : [new SlContractView({
							projectId : pson.projectId,
							businessType : pson.businessType,
							htType : 'loanContract',
							HTLX : 'loanContract',
							isHiddenAddBtn : false,
							isHiddenEdiBtn : false,
							isHiddenDelBtn : false,
							isHidden : true,
							isSignHidden:false,
							isqsEdit:true,
							isHiddenRZZLHT:true
						})]
			}
		},
		makeP : function(pson) {
			return {
				xtype : 'fieldset',
				title : '归档材料',
				items : new PlArchivesMaterialsView({
							projectId : pson.projectId,
							businessType : pson.businessType,
							isHiddenAffrim : false,
							isEditAffrim : true,
							isHidden_materials : false
						})
			}
		},
		makePC : function(pson,v){
		return {
				xtype : 'fieldset',
				title : '项目属性分类',
				collapsible : true,
				autoHeight : true,
				items : [new StatisticalElementsSmallloan({
							projectId : pson.projectId,
							businessType : pson.businessType,
							isReadOnly : v
						})]
			}
	}

	}
	

}()