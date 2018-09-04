function seeEnterpriseBefore(eid) {
	var anchor = '100%';
	Ext.Ajax.request({
		url : 'ajaxSeeEnterprise.do',
		method : 'POST',
		success : function(response, request) {
			var obj = Ext.decode(response.responseText);
			enterpriseData = obj.data;
			var panel_seeEnterprise = new Ext.form.FormPanel({
				buttonAlign : 'center',
				layout : 'form',
				frame : true,
				border : false,
				labelAlign : 'right',
				labelWidth : 110,
				bodyStyle : 'padding:10px',
				autoScroll : true,
				monitorValid : true,
				items : [{
					layout : "column", // 定义该元素为布局为列布局方式
					defaults : {
						anchor : '100%',
						columnWidth : 1,
						isFormField : true
					},
					border : false,
					items : [{
						columnWidth : 0.83,
						layout : 'fit',
						items : [{
							layout : 'column',
							xtype : 'fieldset',
							title : '填写企业基本信息',
							autoHeight : true,
							collapsible : false,
							anchor : anchor,
							items : [{
								columnWidth : 1,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											id : 'enterNameBefore',
											xtype : 'textfield',
											fieldLabel : '企业名称',
											value : enterpriseData.enterprisename,
											readOnly : true,
											cls : 'readOnlyClass'
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '企业简称',
											value : enterpriseData.shortname,
											readOnly : true,
											cls : 'readOnlyClass'
										}, {
											xtype : 'datefield',
											fieldLabel : '企业成立日期',
											value : enterpriseData.opendate,
											readOnly : true,
											cls : 'readOnlyClass'
										}, {
											xtype : 'textfield',
											fieldLabel : '行业类别',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.hangyetypevalue
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											id : 'seeOrganizecodeValida',
											xtype : 'textfield',
											fieldLabel : '组织机构代码',
											value : enterpriseData.organizecode,
											readOnly : true,
											cls : 'readOnlyClass'
										}, {
											xtype : 'textfield',
											fieldLabel : '企业贷款卡号码',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.creditaccountbum
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											fieldLabel : '职工人数',
											xtype : 'numberfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.employeetotal
										}]
							}, {
								columnWidth : .05,
								layout : 'fit',
								defaults : {
									anchor : anchor
								},
								items : [{
									html : '<h2 style="margin-top:5px;margin-left:5px;",>以内</h2>'
								}]
							}, {
								columnWidth : .70,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '经营所在地城市',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.managecityvalue
										}]
							}, {
								columnWidth : 1,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '实际经营地址',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.factaddress
										}]
							}, {
								columnWidth : .8,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '企业通信地址',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.area
										}]
							}, {
								columnWidth : .2,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								labelWidth : 70,
								items : [{
											fieldLabel : '邮政编码',
											xtype : 'textfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.postcoding
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											fieldLabel : '收件人',
											xtype : 'textfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.receiveMail
										}, {
											fieldLabel : '传真',
											xtype : 'textfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.fax
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											fieldLabel : '联系电话',
											xtype : 'textfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.telephone
										}, {
											fieldLabel : '企业网址',
											xtype : 'textfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.website
										}]
							}]
						}, {
							layout : 'column',
							xtype : 'fieldset',
							title : '营业执照基本信息',
							autoHeight : true,
							collapsible : false,
							anchor : anchor,
							items : [{
										columnWidth : .5,
										layout : 'form',
										defaults : {
											anchor : anchor
										},
										items : [{
													xtype : 'textfield',
													fieldLabel : '法人',
													value : enterpriseData.legalperson,
													readOnly : true,
													cls : 'readOnlyClass'
												}, {
													xtype : 'textfield',
													fieldLabel : '营业执照号码',
													value : enterpriseData.cciaa,
													readOnly : true,
													cls : 'readOnlyClass'
												}, {
													xtype : 'textfield',
													fieldLabel : '注册资金币种',
													readOnly : true,
													cls : 'readOnlyClass',
													value : enterpriseData.capitalkindv
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '实际控制人',
											xtype : 'textfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.controlperson
										}, {
											xtype : 'textfield',
											fieldLabel : '所有制性质',
											readOnly : true,
											value : enterpriseData.ownershipv,
											cls : 'readOnlyClass'
										}, {
											xtype : 'textfield',
											fieldLabel : '工商注册资金(万)',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.registermoney
										}]
									}, {
										columnWidth : 1,
										layout : 'form',
										defaults : {
											anchor : anchor
										},
										items : [{
													xtype : 'textfield',
													fieldLabel : '核准经营范围',
													readOnly : true,
													cls : 'readOnlyClass',
													value : enterpriseData.managescope
												}, {
													xtype : 'textfield',
													fieldLabel : '工商注册地址',
													readOnly : true,
													cls : 'readOnlyClass',
													value : enterpriseData.address
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										defaults : {
											anchor : anchor
										},
										items : [{
											xtype : 'textfield',
											fieldLabel : '营业执照起始日期',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.registerstartdate
										}]
									}, {
										columnWidth : .5,
										layout : 'form',
										labelWidth : 120,
										defaults : {
											anchor : anchor
										},
										items : [{
											xtype : 'textfield',
											fieldLabel : '营业执照截止日期',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.registerenddate
										}]
									}, {
										columnWidth : 1,
										layout : 'form',
										defaults : {
											anchor : anchor
										},
										items : [{
													xtype : 'textfield',
													fieldLabel : '工商登记机关',
													readOnly : true,
													cls : 'readOnlyClass',
													value : enterpriseData.gslnamevalue
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										defaults : {
											anchor : anchor
										},
										items : [{
											xtype : 'textfield',
											fieldLabel : '工商是否按时年检',
											value : enterpriseData.gslexamine == true
													? '是'
													: '否',
											readOnly : true,
											cls : 'readOnlyClass'
										}]
									}, {
										columnWidth : .5,
										layout : 'form',
										labelWidth : 120,
										defaults : {
											anchor : anchor
										},
										items : [{
											xtype : 'datefield',
											format : 'Y-m-d',
											fieldLabel : '工商下一年年检时间',
											value : enterpriseData.gslexaminedate,
											readOnly : true,
											cls : 'readOnlyClass'
										}]
									}]
						}, {
							layout : 'column',
							xtype : 'fieldset',
							title : '税务登记基本信息',
							autoHeight : true,
							collapsible : false,
							anchor : anchor,
							items : [{
								columnWidth : .5,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											fieldLabel : '国税机关名称',
											xtype : 'textfield',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.taxname
										}, {
											xtype : 'textfield',
											fieldLabel : '国税是否按时年检',
											value : enterpriseData.taxexamine == true
													? '是'
													: '否',
											readOnly : true,
											cls : 'readOnlyClass'
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								labelWidth : 120,
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '国税登记证号码',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.taxnum
										}, {
											xtype : 'datefield',
											format : 'Y-m-d',
											fieldLabel : '国税下一年年检时间',
											value : enterpriseData.taxexaminedate,
											readOnly : true,
											cls : 'readOnlyClass'
										}]
							}, {
								columnWidth : 1,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '享受国税政策',
											value : enterpriseData.enjoytax,
											readOnly : true,
											cls : 'readOnlyClass'
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '地税机关名称',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.dstaxname
										}, {
											xtype : 'textfield',
											fieldLabel : '地税是否按时年检',
											value : enterpriseData.dstaxexamine == true
													? '是'
													: '否',
											readOnly : true,
											cls : 'readOnlyClass'
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								labelWidth : 120,
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '地税登记证号码',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.dstaxnum
										}, {
											xtype : 'datefield',
											format : 'Y-m-d',
											fieldLabel : '地税下一年年检时间',
											value : enterpriseData.dsexaminedate,
											readOnly : true,
											cls : 'readOnlyClass'
										}]
							}, {
								columnWidth : 1,
								layout : 'form',
								defaults : {
									anchor : anchor
								},
								items : [{
											xtype : 'textfield',
											fieldLabel : '享受地税政策',
											readOnly : true,
											cls : 'readOnlyClass',
											value : enterpriseData.enjoyds
										}]
							}, {
								id : 'seeEnterpriseId',
								xtype : 'hidden',
								name : 'enterprise.id',
								value : enterpriseData.id,
								readOnly : true,
								cls : 'readOnlyClass'
							}]
						}]

					}, {
						columnWidth : 0.17,
						anchor : '97%',
						items : [{
							xtype : 'button', // 1
							text : '业务经营情况',
							tooltip : '企业业务经营情况信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								managecaseListWin(enterpriseData.id);
							}
						}, {
							xtype : 'button', // 财务信息 2
							text : '财务信息',
							tooltip : '企业财务信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								financeInfo(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 员工结构2
							text : '员工结构',
							tooltip : '企业员工结构信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								employeeStructure(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 3
							text : '银行开户',
							tooltip : '企业银行开户信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								bankInfoListWin(enterpriseOrganizecodeValue);
							}
						}/*, {
							xtype : 'button', // 4
							text : '股权结构',
							tooltip : '企业股权结构信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								shareequityListWin(enterpriseOrganizecodeValue);
							}
						}*/, {
							xtype : 'button', // 5
							text : '管理团队',
							tooltip : '企业管理团队信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								leadteamListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 6
							text : '债务情况',
							tooltip : '企业债务情况信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								debtListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 7
							text : '债权情况',
							tooltip : '企业债权情况信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								creditorListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 8
							text : '对外担保',
							tooltip : '企业对外担保信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								outassureListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 9
							text : '对外投资',
							tooltip : '企业对外投资信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								outinvestListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 10
							text : '诉讼情况',
							tooltip : '企业诉讼情况信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								lawsuitListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 11
							text : '获奖资质',
							tooltip : '企业获奖资质信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								prizeListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 12
							text : '企业相关数据',
							tooltip : '企业相关数据信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								relatedataListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 13
							text : '关联公司情况',
							tooltip : '企业关联公司情况信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								companyListWin(enterpriseOrganizecodeValue);
							}
						}, {
							xtype : 'button', // 14
							text : '企业联系人信息',
							tooltip : '企业联系人信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								relationPersonListWin(enterpriseOrganizecodeValue);
							}
						}/*, {
							xtype : 'button', // 15
							text : '与本担保公司的业务记录',
							tooltip : '与本担保公司的业务记录信息',
							scope : this,
							style : 'margin-top:5px;margin-left:20px;',
							handler : function() {
								// var enterpriseId =
								// Ext.get('seeEnterpriseId').dom.value;
								var enterpriseOrganizecodeValue = Ext
										.get('seeOrganizecodeValida').dom.value;
								businessRecordRelationEnterprise(enterpriseOrganizecodeValue);
							}
						}*/]
					}]
				}]
			});
			var window_see = new Ext.Window({
						id : 'win_seeEnterprise',
						title : '查看企业信息',
						layout : 'fit',
						width : (screen.width - 180) * 0.7 + 160,
						constrainHeader : true,
						collapsible : true,
						autoScroll : true,
						height : 460,
						closable : true,
						resizable : false,
						modal : true,
						plain : true,
						border : false,
						items : [panel_seeEnterprise]
					}).show();
		},
		failure : function(response) {
			if (response.status == 0) {
				Ext.ux.Toast.msg('状态', '连接失败，请保证服务已开启');
			} else if (response.status == -1) {
				Ext.ux.Toast.msg('状态', '连接超时，请重试!');
			} else {
				Ext.ux.Toast.msg('状态', '查询失败!');
			}
		},
		params : {
			id : eid
		}
	})
}