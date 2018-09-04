/**
 * @author lisl
 * @description 小额贷款项目信息
 * @extends Ext.Panel
 */

PlPawnProjectInfoEdit = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PlPawnProjectInfoEdit.superclass.constructor.call(this, {
			autoScroll : true,
			height : document.body.clientHeight - 280,
			tbar : this.toolbar,
			border : false,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/pawn/project/PawnProjectInfoPanel.js',
				__ctxPath + '/js/creditFlow/pawn/project/loanPawnData.js',
				__ctxPath + '/js/commonFlow/ExtUD.Ext.js',//客户信息 项目基本信息
				__ctxPath + '/js/selector/UserDialog.js',
				__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',//股东信息
				__ctxPath + '/js/creditFlow/mortgage/business/BusinessForm.js',
				__ctxPath + '/js/creditFlow/customer/dictionary/dictionaryNotLastNodeTree.js',
				__ctxPath + '/js/commonFlow/NewProjectForm.js',
				__ctxPath + '/js/creditFlow/pawn/finance/PawnProjectFinanceInfoPanel.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/PawnItemsListView.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/AddPawnItemsWin.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/UpdatePawnItemsWin.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeVehicleInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeStockownershipInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeMachineInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeProductInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeHouseInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeOfficeBuildingInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeHouseGroundInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeBusinessInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeBusinessAndLiveInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeEducationInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeIndustryInfo.js',
				__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeDroitUpdateInfo.js',
				__ctxPath + '/js/creditFlow/pawn/materials/PawnMaterialsView.js',
				__ctxPath + '/js/creditFlow/pawn/materials/PawnMaterialsForm.js',
				__ctxPath + '/js/creditFlow/smallLoan/meeting/MeetingSummaryForm.js',
				__ctxPath + '/js/creditFlow/smallLoan/meeting/MeetingSummaryUpload.js',
				__ctxPath+'/js/creditFlow/guarantee/meeting/CensorMeetingCollectivityDecisionConfirm.js',
				__ctxPath + '/js/creditFlow/pawn/contract/PawnContractView.js',
				__ctxPath + '/js/creditFlow/pawn/contract/PawnOperateContractWindow.js',
				__ctxPath + '/js/creditFlow/pawn/finance/PlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/report/SlReportView.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
				__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnContinuedWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnContinuedForm.js',
				__ctxPath+ '/js/creditFlow/pawn/finance/PawnContinuedFundIntentView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnRedeemWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnRedeemForm.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnVastWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnVastForm.js',
				__ctxPath+ '/js/creditFlow/pawn/finance/PlFundIntentViewVM.js',
				__ctxPath + '/js/creditFlow/pawn/project/loadPawnContinuedData.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnContinuedView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnRedeemView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnVastView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnTicketLossView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnTicketLossWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnTicketLossForm.js',
				__ctxPath+ '/js/creditFlow/pawn/project/PawnTicketReissueView.js',
				__ctxPath+ '/js/creditFlow/pawn/project/pawnTicketReissueWindow.js',
				__ctxPath+ '/js/creditFlow/pawn/project/pawnTicketReissueForm.js',
				__ctxPath + '/js/creditFlow/pawn/materials/PawnUploads.js',
				__ctxPath + '/js/creditFlow/archives/PlArchivesMaterialsView.js'
				
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);
		this.toolbar = new Ext.Toolbar({
			items : ['->', {
				iconCls : 'btn-save',
				text : '保存',
				scope : this,
				handler : this.saveAllDatas
			}]
		})
	},// 初始化组件
	constructPanel : function() {
		
		this.projectId = this.record.data.projectId;
		this.oppositeType = this.record.data.oppositeType;
		this.projectStatus = this.record.data.projectStatus;
		this.activityName = this.record.data.activityName;
		this.runId = this.record.data.runId;
		this.operationType = this.record.data.operationType;
		this.businessType=this.record.data.businessType;
		this.perMain = "";
		this.projectInfo = new PawnProjectInfoPanel({
			isDiligenceReadOnly : false,
			isAllReadOnly : isGranted('editPawnProjectInfo_'+ this.projectStatus)?false:true
		});
		this.projectInfoFinance= new PawnProjectFinanceInfoPanel({
			isDiligenceReadOnly : true,
			projectId : this.projectId,
			isStartDateReadOnly:isGranted('editPawnFinanceInfo_'+ this.projectStatus)?false:true,
			idDefinition : 'pawnedit' ,
			isHiddencalculateBtn : false,
			isHiddenbackBtn : false ,
			isHiddenParams : true,
			isAllReadOnly : isGranted('editPawnFinanceInfo_'+ this.projectStatus)?false:true
		});
		var title="企业客户信息";
		if (this.oppositeType =="person_customer") {
			this.perMain = new ExtUD.Ext.PeerPersonMainInfoPanel({
				 projectId : this.projectId,
				 isAllReadOnly :isGranted('editPawnCustomerInfo_'+ this.projectStatus)?false:true,
				 isNameReadOnly:false,
				 isSpouseReadOnly: true,
				 isHidden : true,
				isEditPerson : isGranted('editPawnCustomerInfo_'+ this.projectStatus)
			});
			title="个人客户信息";
		} else if(this.oppositeType =="company_customer"){
			     this.perMain = new ExtUD.Ext.PeerMainInfoPanel({
				 projectId : this.projectId,
				 bussinessType:this.businessType,
				 isAllReadOnly : isGranted('editPawnCustomerInfo_'+ this.projectStatus)?false:true,
				 isNameReadOnly:false,
				 isHidden : true,
				 isEditEnterprise : isGranted('editPawnCustomerInfo_'+ this.projectStatus)
			});
		}

		this.pawnItemsListView=new PawnItemsListView({
			titleText : '当物清单',
			isHiddenTitle : true,
			 projectId : this.projectId,
			 businessType:this.businessType,
			 isHiddenAddBtn : !isGranted('addPawnItemsListInfo_'+ this.projectStatus),
			 isHiddenEdiBtn : !isGranted('editPawnItemsListInfo_'+ this.projectStatus),
			 isHiddenDelBtn : !isGranted('removePawnItemsListInfo_'+ this.projectStatus)
		})
		this.gridPanel = new PlFundIntentViewVM({
			projectId : this.projectId,
			object : this.projectInfoFinance,
			projectInfoObject : this.projectInfo,
			businessType : this.businessType,
			isHiddenAddBtn:!isGranted('addPawnFundInent_'+ this.projectStatus),
			isHiddenDelBtn:!isGranted('delPawnFundInent_'+ this.projectStatus),
			isHiddenCanBtn :!isGranted('canPawnFundInent_'+ this.projectStatus),
			isHiddenResBtn :!isGranted('resPawnFundInent_'+ this.projectStatus),
			isHiddenResBtn1 :!isGranted('reconPawnFundInent_'+ this.projectStatus),
			isHiddenseeqlideBtn : !isGranted('seeqlidePawmFundInent_'+ this.projectStatus),
			isHiddenseesumqlideBtn : !isGranted('seesumqlidePawnFundInent_'+ this.projectStatus),
			isHiddenExcel : !isGranted('ecportExcel_'+ this.projectStatus),
			isHiddenOverdue:true,
			isHiddenTitle:true,
			isHiddenautocreateBtn : true
		});
		this.PlArchivesMaterialsView = new PlArchivesMaterialsView({
			projectId:this.projectId,
			businessType : this.businessType,
			operationType : this.operationType,
			isHiddenAffrim:true,
			isHiddenRecive:false,
			isHidden_materials : !isGranted('addPawnMaterials_'+ this.projectStatus),
			isHiddenType :false,
			isHiddenAffrim:false,
			isAffrimEdit:true
		});
		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			frame : true,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				collapsible : true,
				autoHeight : true
			},
			labelAlign : "right",
			items : [{
				xtype : 'fieldset',
				name:'projectInfo',
				title : '<a name="pl_projectInfo_edit' + this.projectId+ '"><font color="red">01</font>项目基本信息</a>',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				hidden :!(isGranted('seePawnProjectInfo_' + this.projectStatus)||isGranted('editPawnProjectInfo_'+ this.projectStatus)),
				items : [this.projectInfo]
			}, {
				xtype : 'fieldset',
				title :'<a name ="pl_customerInfo_edit' + this.projectId+ '"><font color="red">02</font>' +title+ '</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnCustomerInfo_' + this.projectStatus)||isGranted('editPawnCustomerInfo_'+ this.projectStatus)),
				items : [this.perMain]
			}, {       
			    xtype : 'fieldset',
				title : '<a name ="pl_financeInfo_edit' + this.projectId+ '"><font color="red">03</font>典当信息</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				name:'financeInfoFieldset',
				autoHeight : true,
				hidden :!(isGranted('seePawnFinanceInfo_' + this.projectStatus)||isGranted('editPawnFinanceInfo_'+ this.projectStatus) || isGranted('seePawnItemsList_' + this.projectStatus)||isGranted('editPawnItemsList_'+ this.projectStatus) || isGranted('seePawnFundIntent_' + this.projectStatus)||isGranted('editPawnFundIntent_'+ this.projectStatus)),
				items : [{
						xtype : 'panel',
						hidden : !(isGranted('seePawnFinanceInfo_' + this.projectStatus)||isGranted('editPawnFinanceInfo_'+ this.projectStatus)),
						items : [this.projectInfoFinance]
					},
					{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-bottom:5px',
						hidden :!(isGranted('seePawnItemsList_' + this.projectStatus)||isGranted('editPawnItemsList_'+ this.projectStatus)),
						html : '<br/><B><font class="x-myZW-fieldset-title"><a name="pl_pawnItemsListInfo_edit'
								+ this.projectId
								+ '">【<font color="red">04</font>当物清单】</a></font></B>'
					},{
						xtype : 'panel',
						hidden : !(isGranted('seePawnItemsList_' + this.projectStatus)||isGranted('editPawnItemsList_'+ this.projectStatus)),
						items : [this.pawnItemsListView]
					},
					{
						xtype : 'panel',
						border : false,
						bodyStyle : 'margin-bottom:5px',
						hidden :!(isGranted('seePawnFundIntent_' + this.projectStatus)||isGranted('editPawnFundIntent_'+ this.projectStatus)),
						html : '<br/><B><font class="x-myZW-fieldset-title"><a name="pl_fundIntentInfo_edit'
								+ this.projectId
								+ '">【<font color="red">04</font>放款收息表】</a></font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
					},{
						xtype : 'panel',
						hidden :!(isGranted('seePawnFundIntent_' + this.projectStatus)||isGranted('editPawnFundIntent_'+ this.projectStatus)),
						items : [this.gridPanel]
					}
					
				]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_contractInfo_edit' + this.projectId+ '"><font color="red">06</font>合同列表</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnContractInfo_' + this.projectStatus)||isGranted('editPawnContractInfo_'+ this.projectStatus)),
				items : [
					new PawnContractView({
						projectId : this.projectId,
				    	businessType : this.businessType,
				    	htType : 'pawnContract',
				    	HTLX : 'pawnContract',
				    	isHiddenAddBtn : !isGranted('addPawnContractInfo_' + this.projectStatus),
						isHiddenEdiBtn : !isGranted('editPawnContract_' + this.projectStatus),
						isHiddenDelBtn : !isGranted('delPawnContractInfo_' + this.projectStatus),
						isHidden:true
					})
				]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_uploads_edit' + this.projectId+ '"><font color="red">07</font>办理文件</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnUploads_' + this.projectStatus)||isGranted('editPawnUploads_'+ this.projectStatus)),
				items : [
					new PawnUploads({
						projectId : this.projectId,
				    	businessType : this.businessType,
				    	tableName : 'plPawnProjectFile',
				    	typeisfile: 'plPawnProjectFile',
				    	issubmitHidden : !isGranted('addPawnUploads_' + this.projectStatus),
				    	isgdHidden :!isGranted('addPawnUploads_' + this.projectStatus),
				    	isgdEdit :isGranted('addPawnUploads_' + this.projectStatus),
				    	isHidden : !isGranted('addPawnUploads_' + this.projectStatus),
				    	titleName : '办理文件'
					})
				]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_preAssessmentReportInfo_edit' + this.projectId+ '"><font color="red">08</font>预评估文件</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnPreAssessmentReportInfo_' + this.projectStatus)||isGranted('editPawnPreAssessmentReportInfo_'+ this.projectStatus)),
				items : [new SlReportView({
					projectId : this.projectId,
					businessType : this.businessType,
					isHidden_report : !isGranted('addPawnPreAssessmentReportInfo_' + this.projectStatus)
				})]
			},{
				xtype :'fieldset',
				title : '<a name ="pl_materials_edit' + this.projectId+ '"><font color="red">09</font>归档材料清单</a>',
				collapsible : true,
				autoHeight : true,
				hidden :!(isGranted('seePawnMaterials_' + this.projectStatus)||isGranted('editPawnMaterials_'+ this.projectStatus)),
				items : [this.PlArchivesMaterialsView]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_pawnContinued_edit' + this.projectId+ '"><font color="red">10</font>续当记录</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnContinuedInfo_' + this.projectStatus)||isGranted('editPawnContinuedInfo_'+ this.projectStatus)),
				items : [new PawnContinuedView({projectId:this.projectId,businessType:this.businessType,fundGridPanel:this.gridPanel,isHiddenEdit:!isGranted('editPawnContinued_'+ this.projectStatus)})]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_pawnRedeem_edit' + this.projectId+ '"><font color="red">11</font>赎当记录</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden : !(isGranted('seePawnRedeemInfo_' + this.projectStatus)||isGranted('editPawnRedeemInfo_'+ this.projectStatus)),
				items : [new PawnRedeemView({projectId:this.projectId,businessType:this.businessType,fundGridPanel:this.gridPanel,isHiddenEdit:!isGranted('editPawnRedeem_'+ this.projectStatus)})]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_pawnVast_edit' + this.projectId+ '"><font color="red">12</font>绝当记录</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnVastInfo_' + this.projectStatus)||isGranted('editPawnVastInfo_'+ this.projectStatus)),
				items : [new PawnVastView({projectId:this.projectId,businessType:this.businessType,fundGridPanel:this.gridPanel,isHiddenEdit:!isGranted('editPawnVast_'+ this.projectStatus)})]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_pawnTicketLoss_edit' + this.projectId+ '"><font color="red">13</font>挂失记录</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnTicketLoss_' + this.projectStatus)||isGranted('editPawnTicketLoss_'+ this.projectStatus)),
				items : [new PawnTicketLossView({projectId:this.projectId,businessType:this.businessType,isHiddenEdit:!isGranted('editPawnTicketLossRecord_'+ this.projectStatus)})]
			},{
				 xtype : 'fieldset',
				title : '<a name ="pl_pawnTicketReissue_edit' + this.projectId+ '"><font color="red">14</font>补发记录</a>',
				bodyStyle : 'padding-left:0px',
				collapsible : true,
				labelAlign : 'right',
				autoHeight : true,
				hidden :!(isGranted('seePawnTicketReissue_' + this.projectStatus)||isGranted('editPawnTicketReissue_'+ this.projectStatus)),
				items : [new PawnTicketReissueView({projectId:this.projectId,businessType:this.businessType,isHiddenEdit:!isGranted('editPawnTicketReissueRecord_'+ this.projectStatus)})]
			}]
		});
		this.loadData({
			url : __ctxPath + '/creditFlow/pawn/project/getInfoPlPawnProject.do?slProjectId='+this.projectId,
			method : "POST",
			preName : ['enterprise', 'person', 'plPawnProject'],
			root : 'data',
			success : function(response, options) {
				var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				this.getCmpByName('projectMoney1').setValue(Ext.util.Format.number(alarm_fields.data.plPawnProject.projectMoney,'0,000.00'));
				var appUsers= alarm_fields.data.appuerName;
				var appUserId= alarm_fields.data.plPawnProject.appUserId;
				if(""!=appUserId &&  null!=appUserId){
				   this.getCmpByName('degree').setValue(appUserId);
				   this.getCmpByName('degree').setRawValue(appUsers);
				   this.getCmpByName('degree').nextSibling().setValue(appUserId);
				}
				
				fillPawnData(this,alarm_fields,'pawnedit');
			
			}
		});
		this.formPanel = new Ext.FormPanel();
		this.formPanel.add(this.outPanel);
		this.add(this.formPanel);
		this.doLayout();
	},// 初始化UI结束
	saveAllDatas : function() {
		if(this.getCmpByName('person.cardtype').getValue()==309){
			if(validateIdCard(this.getCmpByName('person.cardnumber').getValue())==1){
				Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
				return;
			}else if(validateIdCard(this.getCmpByName('person.cardnumber').getValue())==2){
				Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
				return;
			}else if(validateIdCard(this.getCmpByName('person.cardnumber').getValue())==3){
				Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
				return;
			}
		}
		var vDates="";
      
        var oppType=this.oppositeType;
        var op=this.getCmpByName('person.id')
        if(this.oppositeType=="company_customer")
        {
           var eg=this.perMain.getCmpByName('gudong_store').get(0).get(1);
           vDates=getGridDate(eg);
           if(vDates!=""){
              var arrStr=vDates.split("@");
			  for(var i=0;i<arrStr.length;i++){
				  var str=arrStr[i];
				  var object = Ext.util.JSON.decode(str)
				 if(object.personid==""){
					 Ext.ux.Toast.msg('操作信息','股东名称不能为空，请选择股东名称');
					 return;
				 }
				  if(object.shareholdertype==""){
						 Ext.ux.Toast.msg('操作信息','股东类别不能为空，请选择股东类别');
						 return;
					 }
			  }
		  }
        }
     	var fundIntentJsonData=this.gridPanel.getGridData();
     	var gridPanel=this.gridPanel
		this.formPanel.getForm().submit({
		    clientValidation: false, 
			url : __ctxPath + '/creditFlow/pawn/project/updateAllPlPawnProject.do',
			params : {
				"gudongInfo" : vDates,
				fundIntentJsonData : fundIntentJsonData
			},
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			scope: this,
			success : function(fp, action) {
				var object = Ext.util.JSON.decode(action.response.responseText)
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
				if(oppType=="company_customer"){
				   eg.getStore().reload();
				   op.setValue(object.legalpersonid)
				}
				gridPanel.save()
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
			}
		});
	}
});