// 1可编辑2不可编辑
// ah_a1 :基本项目信息 12
// ah_b1 :企业/个人信息12
// ah_c1 :款项12
// ah_d1:第一还款来源12
// ah_e1:共同借款人12
// ah_f1 :担保物品12 3
// ah_g1 :准入原则1 主 2风
// ah_h1 :资料清单12
// ah_i1 :调研报告12
// ah_j:风险专员
// ah_k1:风险报告
// ah_l:审贷会
// ah_m 1,2:审贷会
// ah_n:审贷会意见
// ah_o :合同列表 1.add 2.edit 3.query
// ah_p:归档材料
// ah_q:放宽信息确认
// ah_r:多次放宽
// ah_s:监管计划
// ah_t:属性分类
// ah_u:奖金提成
// ah_v:打印放款审批
// 生产总控制,对js没太大用，给人看的
// 全局单例工厂
_getFactory = function() {
	return {
		create : function(fac, pson) {
			var f = {};
			if (fac == "ah_1") { // 一级保存调查
				return makeF.mack(1, pson);
			}
			if (fac == "ah") { // 一级保存调查
				// // var a = new Ext.L.TextField({
				// // unitText : "xxxx",
				// // width : 130,
				// // allowBlank : false,
				// // maxLength : 4,
				// // name : 'username',
				// // fieldLabel : '姓名',
				// // blankText : '请输入姓名',
				// // maxLengthText : '姓名不能超过4个字符'
				// // // ,
				// // // listeners : {
				// // // 'render' : function() {
				// // //  
				// // // }
				// // // }
				// // });
				// // var b = new Ext.form.TextField({
				// // unitText : "xxxx",
				// // width : 130,
				// // allowBlank : false,
				// // maxLength : 4,
				// // name : 'username',
				// // fieldLabel : '姓名',
				// // blankText : '请输入姓名',
				// // maxLengthText : '姓名不能超过4个字符'
				// // });
				//
				// var d = new Ext.Panel({
				// items : new Ext.L.GridPanel({
				// pageSize : 10, // 默认5
				// _class : "Person",
				// _mapping : [{
				// header : "姓名",
				// name : "name"
				// }, {
				// header : "姓名",
				// name : "name",
				// type : "sting"
				// }, {
				// header : "id",
				// name : "id"
				// }]
				// })
				// });
				var g = new Ext.L.Panel({
							layout : "form",
							_mapping : [[{
												col : 3,
												// xtype : 'textfield',
												name : 'baseInfoSave',
												value : '11',
												fieldLabel : "项目名称",
												readOnly : true
											}, {
												xtype : 'textfield',
												name : 'baseInfoSave',
												value : '12'
											}, {
												xtype : 'textfield',
												name : 'baseInfoSave',
												value : '13'
											}], [{
												xtype : 'textfield',
												name : 'baseInfoSave',
												value : '21'
											}]]
						});

				return {
					xtype : 'fieldset',
					name : 'projectInfo',
					title : '项目基本信息x ',
					collapsible : true,
					autoHeight : true,
					labelAlign : 'right',
					items : g
				};
			}
			if (fac == "ah_a1") {
				var a = {
					xtype : 'hidden',
					name : 'projectSave',
					value : '1'
				}
				var b = {
					xtype : 'hidden',
					name : 'baseInfoSave',
					value : '1'
				}
				var c = makeF.mackA(false, false, pson);

				return [a, b, c];

			}
			if (fac == "ah_a2") {
				return makeF.mackA(true, true, pson);
			}
			if (fac == "ah_a3") {
				var a = {
					xtype : 'hidden',
					name : 'baseInfoSave',
					value : '1'
				}
				var b = makeF.mackA(false, true, pson);
				return [a, b]
			}

			if (fac == "ah_b1") {
				if (pson.oppositeType == "company_customer") {
					var a = {
						xtype : 'hidden',
						name : 'entSave',
						value : '1'
					}
					var b = makeF.mackB1(pson, false);
				}
				if (pson.oppositeType == "person_customer") {
					var a = {
						xtype : 'hidden',
						name : 'perSave',
						value : '1'
					}
					var b = makeF.mackB2(pson, false);
				}

				return [a, b];
			}
			if (fac == "ah_b2") {
				if (pson.oppositeType == "company_customer")
					return makeF.mackB1(pson, true);
				if (pson.oppositeType == "person_customer")
					return makeF.mackB2(pson, false);
			}

			if (fac == "ah_c1") {
				var a = {
					SmallProjectFinanceInfo : true,
					SlActualToCharge : true,
					SlActualToChargeVM : false,
					SlFundIntentViewVM : false
				}
				var b = {
					p1_isStartDateReadOnly : false,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : false,
					p1_isHiddencalculateBtn : false,
					p2_isHidden : false
					// 试算
				}
				return makeF.makeC(pson, a, b);
			}
			if (fac == "ah_c2") {
				var a = {
					SmallProjectFinanceInfo : true,
					SlActualToCharge : true,
					SlActualToChargeVM : false,
					SlFundIntentViewVM : false
				}
				var b = {
					p1_isStartDateReadOnly : false,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : true,
					p1_isHiddencalculateBtn : false,
					p2_isHidden : true
					// 试算

				}

				return makeF.makeC(pson, a, b);// 只读
			}

			if (fac == "ah_c3") {
				var a = {
					SmallProjectFinanceInfo : true,
					SlActualToCharge : true,
					SlActualToChargeVM : true,
					SlFundIntentViewVM : false
				}
				var b = {
					p1_isStartDateReadOnly : true,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : true,
					p1_isHiddencalculateBtn : false,
					p4_isHiddenCheckBtn : true
				}

				return makeF.makeC(pson, a, b);// 只读
			}

			if (fac == "ah_c4") {
				var a = {
					SmallProjectFinanceInfo : true,
					SlActualToCharge : false,
					SlActualToChargeVM : false,
					SlFundIntentViewVM : true
				}
				var b = {
					p1_isStartDateReadOnly : false,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : true,
					p1_isHiddencalculateBtn : false

				}

				return makeF.makeC(pson, a, b);// 只读
			}

			if (fac == "ah_c5") {
				var a = {
					SmallProjectFinanceInfo : true,
					SlActualToCharge : false,
					SlActualToChargeVM : false,
					SlFundIntentViewVM : true
				}
				var b = {
					p1_isStartDateReadOnly : true,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : true,
					p1_isHiddencalculateBtn : false,
					p2_isHiddenautocreateBtn : true, // 生成
					p2_isHiddenAddBtn : true,// 增加
					p2_isHiddenResBtn1 : true
					// 手动
				}

				return makeF.makeC(pson, a, b);// 只读
			}

			if (fac == "ah_d1") {
				var a = {
					xtype : 'hidden',
					name : 'firstSave',
					value : '1'
				}
				var b = makeF.makeD(pson, false);
				return [a, b]
			}

			if (fac == "ah_d2") {
				return makeF.makeD(pson, true);
			}

			if (fac == "ah_e1") {
				var a = {
					xtype : 'hidden',
					name : 'borrowerSave',
					value : '1'
				}
				var b = makeF.makeE(pson, false)
				return [a, b];
			}
			if (fac == "ah_e2") {
				return makeF.makeE(pson, true);
			}

			if (fac == "ah_f1") { // 添加

				// 1.相关按钮2.相关文件
				// 添加按钮//d办理按钮//z办理按钮
				return makeF.makeF(pson, false, true, true);
			}

			if (fac == "ah_f2") { // 查看
				return makeF.makeF(pson, true, true, true, false);
			}
			if (fac == "ah_f3") {
				return makeF.makeF(pson, true, false, true);
			}
			if (fac == "ah_f4") {
				return makeF.makeF(pson, true, true, false);
			}

			if (fac == "ah_f5") { // 归档
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
										isSeeContractHidden : true,
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
										isSeeContractHidden : true,
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

			if (fac == "ah_g2") {// 调查
				return makeF.makeG(pson, true, false);
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

			if (fac == "ah_h3") {
				return makeF.makeH(pson, true, false);
			}
			if (fac == "ah_i1") {
				return makeF.makeI(pson, false);
			}
			if (fac == "ah_i2") {
				return makeF.makeI(pson, true);
			}
			if (fac == "ah_j") {
				return [{
							xtype : "hidden",
							name : "riskAttacheId"
						}, makeF.makeJ(pson)];
			}
			if (fac == "ah_k1") {
				return makeF.makeK(pson, false);
			}
			if (fac == "ah_k2") {
				return makeF.makeK(pson, true);
			}
			if (fac == "ah_l") {
				return [{
							xtype : "hidden",
							name : "onlineJudgementIds"
						}, makeF.makeL(pson)];
			}
			if (fac == "ah_m1") {
				var a = {
					xtype : "hidden",
					name : "offlineSave",
					value : 1
				}
				var b = makeF.makeM(false, pson);;
				return [a, b];
			}
			if (fac == "ah_m2") {
				return makeF.makeM(true, pson);
			}
			if (fac == "ah_n") {
				return makeF.makeN(pson);
			}
			if (fac == "ah_o1") {
				var v = {
					isHiddenAddBtn : false,
					isHiddenEdiBtn : false,
					isHiddenDelBtn : false,
					isHidden : true,
					isqsEdit : false
				}
				return makeF.makeO(v, pson);
			}
			if (fac == "ah_o2") {
				var v = {
					isHiddenAddBtn : true,
					isHiddenEdiBtn : true,
					isHiddenDelBtn : true,
					isHidden : true,
					isqsEdit : true,
					isSignHidden : false

				}
				return makeF.makeO(v, pson);
			}

			if (fac == "ah_o3") {
				var v = {
					isHiddenAddBtn : true,
					isHiddenEdiBtn : true,
					isHiddenDelBtn : true,
					isHidden : true,
					isqsEdit : false,
					isSignHidden : false, // 签署
					isLawCheckHidden : false
				}
				return makeF.makeO(v, pson);
			}

			if (fac == "ah_o4") {
				var v = {
					isHiddenAddBtn : true,
					isHiddenEdiBtn : true,
					isHiddenDelBtn : true,
					isHidden : false,
					isqsEdit : false,
					isSignHidden : true,
					isLawCheckHidden : false
				}
				return makeF.makeO(v, pson);
			}
			if (fac == "ah_p") {
				return makeF.makeP(pson);
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
								isHidden_materials : true,
								isHiddenRecive : true,// 提交时间
								isHiddenRecive : true,// 提交备注
								isHiddenAffrim : false,// 受到时间
								isHiddenAffrim : false,// 备注
								isHiddenType : false
							})
				}
			}

			if (fac == "ah_q") {
				return makeF.makeQ();
			}

			if (fac == "ah_r1") {
				var a = {
					xtype : 'hidden',
					name : 'manyChargeSave',
					value : '1'
				}

				var b = {
					xtype : 'hidden',
					name : 'kxSave',
					value : '1'
				}
				var v = {
					SmallProjectFinanceInfo : true,
					SlFundIntentViewVM : false,
					SlActualToCharge : true,
					SlActualToChargeVM : false
				}
				var p = {
					p1_isStartDateReadOnly : false,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : false,
					p1_isHiddencalculateBtn : false,
					p3_isHidden : false
					// 手动对账
				}
				var c = makeF.makeR(pson, v, p, false);
				return [a, b, c]
			}

			if (fac == "ah_r2") {
				var v = {
					SmallProjectFinanceInfo : true,
					SlFundIntentViewVM : false,
					SlActualToCharge : true,
					SlActualToChargeVM : false
				}
				var p = {
					p1_isStartDateReadOnly : true,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : true,
					p1_isHiddencalculateBtn : false,
					p3_isHidden : true
				}
				return makeF.makeR(pson, v, p, true);
			}

			if (fac == "ah_r3") {
				var a = {
					xtype : 'hidden',
					name : 'kxSave',
					value : '1'
				}
				var v = {
					SmallProjectFinanceInfo : true,
					SlFundIntentViewVM : true,
					SlActualToCharge : false,
					SlActualToChargeVM : true
				}
				var p = {
					p1_isStartDateReadOnly : false,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : true,
					p1_isHiddencalculateBtn : false,
					// p3_isHidden : true,
					p2_isHiddenAddBtn : false, // 生成
					p2_isHiddenAddBtn : false,// 增加
					p2_isHiddenDelBtn : false,// 删除
					p2_isHiddenCanBtn : true,// 取消
					p2_isHiddenResBtn : true,// 还原
					p2_isHiddenResBtn1 : false,// 手动
					p2_isHiddenseesumqlideBtn : false
					// 项目总流水
				}
				var b = makeF.makeR(pson, v, p, true);
				return [a, b];
			}
			if (fac == "ah_r4") {
				var v = {
					SmallProjectFinanceInfo : true,
					SlFundIntentViewVM : true,
					SlActualToCharge : false,
					SlActualToChargeVM : false
				}
				var p = {
					p1_isStartDateReadOnly : true,// 开始日期
					p1_isHiddenbackBtn : false,// ??
					p1_isAllReadOnly : true,
					p1_isHiddencalculateBtn : false,
					p2_isHiddenAddBtn : true, // 生成
					p2_isHiddenAddBtn : true,// 增加
					p2_isHiddenDelBtn : true,// 删除
					p2_isHiddenCanBtn : true,// 取消
					p2_isHiddenResBtn : true,// 还原
					p2_isHiddenResBtn1 : true,// 手动
					p2_isHiddenseesumqlideBtn : false
				}
				return makeF.makeR(pson, v, p, true);
			}

			if (fac == "ah_s") {
				var a = {
					name : "SupervisemanSave",
					xtype : "hidden",
					value : '1'
				}
				var b = makeF.makeS(pson)
				return [a, b];
			}

			if (fac == "ah_t") {
				var a = makeF.makeT(pson)
				return a;
			}

			if (fac == "ah_u") {
				var a = {
					name : "TCFundIntentSave",
					xtype : "hidden",
					value : '1'
				}
				var b = makeF.makeU(pson)
				return [a, b];
			}

			if (fac == "ah_v") {
				return makeF.makeV(pson)
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
		mack : function(v, p) {
			if (v == 1) {
				return [{
							xtype : 'hidden',
							name : 'projectId',
							value : p.projectId
						}, {
							xtype : 'hidden',
							name : 'preHandler',
							value : 'slAsmallloanProjectService.goToNext'
						}]
			}
		},
		mackA : function(v, m, pson) {
			var form = new CQProject({
						isDiligenceReadOnly : v,
						isAllReadOnly : m,
						projectId : pson.projectId
					});

			var field = {
				xtype : 'fieldset',
				name : 'projectInfo',
				title : '项目基本信息 ',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : form
			};

			return field;
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
					isEditEnterprise : false
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
							isEditPerson : false,
							projectId : pson.projectId,
							isReadOnly : true,
							isSpouseReadOnly : true,
							isNameReadOnly : true
						})
			}
		},
		makeC : function(pson, v, p) {
			var a, b, c, d, e;
			if (v.SmallProjectFinanceInfo) {
				a = new SmallProjectFinanceInfo({
					id : "ext_projectInfoFinance",
					projectId : pson.projectId,
					isStartDateReadOnly : p.p1_isStartDateReadOnly,
					idDefinition : 'Smallliucheng' + pson.taskId,
					isHiddencalculateBtn : p.p1_isHiddencalculateBtn,
					isHiddenbackBtn : p.p1_isHiddenbackBtn,
					isAllReadOnly : p.p1_isAllReadOnly, // 大部分只读
					isHiddenRemainingMoney : true
						// 剩余金额

					});
			}

			if (v.SlFundIntentViewVM) {
				b = new SlFundIntentViewVM({
					id : "ext_makeCharge",
					projectId : pson.projectId,
					object : a,
					projectInfoObject : pson.projectInfo,
					businessType : 'SmallLoan',
					isHiddenautocreateBtn : p.p2_isHiddenautocreateBtn,// 生成
					isHiddenAddBtn : p.p2_isHiddenAddBtn,// 增加
					isHiddenDelBtn : p.p2_isHiddenDelBtn,// 删除
					isHiddenCanBtn : p.p2_isHiddenCanBtn,// 取消
					isHiddenResBtn : p.p2_isHiddenResBtn,// 还原
					isHiddenResBtn1 : p.p2_isHiddenResBtn1,// 手动对账
					isHiddenseeqlideBtn : false,// 查看流水单项订单
					isHiddenseesumqlideBtn : true,
					isHiddenseesumqlideBtn : p.p2_isHiddenseesumqlideBtn
						// 项目流水记录

					});
				if (p.p2_isHiddenAddBtn == false) {
					e = {
						xtype : "hidden",
						name : "makeChargeSave",
						value : '1'
					}
				}
			}
			if (v.SlActualToCharge) {
				c = new SlActualToCharge({
							id : "ext_slActualToCharge",
							projId : pson.projectId,
							businessType : 'SmallLoan',// 小贷
							isHidden : p.p3_isHidden
						});
			};

			if (v.SlActualToChargeVM) {
				d = new SlActualToChargeVM({
							id : "ext_slActualToCharge",
							projId : pson.projectId,
							businessType : 'SmallLoan',// 小贷
							isHiddenCheckBtn : false,
							isHiddenCheckBtn : p.p4_isHiddenCheckBtn
						});
			}

			var f = [a, b, c, d, e];
			var g = [];
			var n = 0;
			for (var i in f) {
				if (f[i]) {
					g[n] = f[i];
					n++;
				}
			}
			return {
				xtype : 'fieldset',
				title : '贷款基本信息',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				name : 'financeInfoFieldset',
				autoHeight : true,
				items : g
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
		makeF : function(pson, add, doD, doB, e) {
			return {
				xtype : 'fieldset',
				title : '担保措施',
				collapsible : true,
				autoHeight : true,
				items : [new DZYMortgageView({
									projectId : pson.projectId,
									titleText : '抵质押担保',
									// isAllReadOnly : g,// 。。。
									businessType : pson.businessType,
									isHiddenAddBtn : add, // 增加抵质押物
									isHiddenDelBtn : add,
									isHiddenEdiBtn : add,
									isContractHidden : (e == false)
											? false
											: true,

									isblEdit : !doD,
									isblHidden : doD,// 是否办理/落实
									// isfwEdit : doD,
									// isHandleHidden : doD,// 是否处理

									isgdHidden : true,// 是否已提交
									isRecieveHidden : true,// 是否收到
									isHiddenRelieve : true, // 是否解除
									isSignHidden : true,// 是否签署并检验合格
									formPanel : Ext
											.getCmp("ext_projectInfoFinance")

								}), new BaozMortgageView({
									projectId : pson.projectId,
									titleText : '保证担保',

									isblEdit : !doB,
									isblHidden : doB,// 是否办理/落实
									businessType : pson.businessType,
									isHiddenAddBtn : add,// 增加保证担保
									isHiddenDelBtn : add,
									isHiddenEdiBtn : add,

									isContractHidden : (e == false)
											? false
											: true,
									isgdHidden : true,// 是否已提交
									isRecieveHidden : true,// 是否收到
									isHiddenRelieve : true, // 是否解除
									isSignHidden : true
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
		makeH : function(pson, v, m) {

			return {
				xtype : 'fieldset',
				title : '贷款资料清单',
				collapsible : true,
				autoHeight : true,
				bodyStyle : 'padding-left: 0px',
				items : new SlProcreditMaterialsView({
							projectId : pson.projectId,
							businessType : pson.businessType,
							isHiddenEdit : v,
							isHiddenRecieve : (m == true),
							isHidden_materials : v,
							operationType : pson.operationType
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
				fieldLabel : "线上评审会成员",
				anchor : "70%",
				xtype : "combo",
				allowBlank : true,
				editable : false,
				triggerClass : 'x-form-search-trigger',
				itemVale : creditkindDicId, // xx代表分类名称
				hiddenName : "appUsersOfA",
				readOnly : this.isAllReadOnly,

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
			}
		},
		makeM : function(v, pson) {
			return {
				title : '上传会议纪要',
				labelAlign : "right",
				bodyStyle : 'padding-left:5px',
				items : [new MeetingSummaryForm({
							projectId : pson.projectId,
							businessType : pson.businessType,
							isReadOnly : v,
							isHidden : v
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
		makeO : function(v, pson) {
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
					isHiddenAddBtn : v.isHiddenAddBtn,
					isHiddenEdiBtn : v.isHiddenEdiBtn,
					isHiddenDelBtn : v.isHiddenDelBtn,
					isHidden : v.isHidden,
					isqsEdit : v.isqsEdit,
					isSignHidden : v.isSignHidden, // 签署
					isLawCheckHidden : v.isLawCheckHidden
						// 法务确认
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
		makeQ : function(pson) {
			var data = [["本次放款", "true"], ["本次不放款", "false"]];
			var proxy = new Ext.data.MemoryProxy(data);
			var _data = Ext.data.Record.create([{
						name : "cname",
						type : "string",
						mapping : 0
					}, {
						name : "chidden",
						type : "string",
						mapping : 1
					}]);
			var reader = new Ext.data.ArrayReader({}, _data);
			var store = new Ext.data.Store({
						proxy : proxy,
						reader : reader,
						autoLoad : true
					});

			var combobox = new Ext.form.ComboBox({
						fieldLabel : "是否放款",
						// anchor : "70%",
						width : 100,
						triggerAction : "all",
						store : store,
						displayField : "cname",
						valueField : "chidden",
						mode : "local",
						emptyText : "请选择",
						allowBlank : false,
						editable : false,// 是否允许输入
						blankText : '请选择是否放款'
					});
			return combobox;
		},
		makeR : function(pson, v, p, tag) {
			var a = new SXProject({
						onlySee : tag
					});
			var b = this.makeC(pson, v, p);
			return [{
						xtype : 'fieldset',
						title : '贷款额度',
						items : [a]
					}, b];
		},
		makeS : function(pson) {
			var a = new DesignSupervisionManagePlanView({
						id : 'ext_supervise',
						projectId : pson.projectId
					});
			return [{
						xtype : 'fieldset',
						title : '确定贷款监管计划',
						items : a
					}];
		},
		makeT : function(pson) {
			var a = new StatisticalElementsSmallloan({});
			return [{
						xtype : 'fieldset',
						title : '贷款属性分类',
						items : a
					}];
		},
		makeU : function(pson) {
			var bonusSchemeForm = new BonusSchemeForm({
						projectId : pson.projectId,
						businessType : pson.businessType,
						operationType : pson.operationType,
						idDefinition : 'Smallliucheng' + pson.taskId
					})
			var bonusFundIntentView = new BonusFundIntentView({
						id : "ext_tcsave",
						projectId : pson.projectId,
						businessType : pson.businessType,
						operationType : pson.operationType,
						object : bonusSchemeForm
					})
			return [{
						xtype : 'fieldset',
						title : '奖金方案',
						bodyStyle : 'padding-left:0px',
						collapsible : true,
						labelAlign : 'right',
						autoHeight : true,
						items : [bonusSchemeForm, {
									xtype : 'panel',
									name : 'bonusFundIntentView',
									items : [bonusFundIntentView]
								}

						]
					}];
		},
		makeV : function(pson) {
			return {
				xtype : 'fieldset',
				title : '放款审批单',
				collapsible : true,
				autoHeight : true,
				items : [new SXProject({
									onlySee : true
								}), new LetterAndBookView({
									projectId : pson.projectId,
									businessType : pson.businessType,
									LBTemplate : 'LoanNotice',
									isHidden : false
								})]
			}
		}
	}

}()
