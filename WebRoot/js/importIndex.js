
		document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/core/ux/HTExt.js"></script>');
		document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/core/ScriptMgr.js"></script>');
		document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/core/AppUtil.js"></script>');
		document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/core/ux/TreePanelEditor.js"></script>');
		document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/core/ux/TreeXmlLoader.js"></script>');
		document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/core/ux/WebOffice.js"></script>');
		document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/core/ux/TreeCombo.js"></script>');
		function importJs(){
			
   var storeTheme=getCookie('theme');
			   	  if(storeTheme==null || storeTheme==''){
				   	  storeTheme='ext-all';
			   	  }
			      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
			   
			
		JSLoader.loadStyleSheet(__ctxPath+"/ext3/resources/css/ext-patch.css");
		JSLoader.loadStyleSheet(__ctxPath+"/ext3/ux/css/Portal.css");
		JSLoader.loadStyleSheet(__ctxPath+"/ext3/ux/css/Ext.ux.UploadDialog.css");
        JSLoader.loadStyleSheet(__ctxPath+"/css/admin.css");

		
		JSLoader.loadStyleSheet(__ctxPath+"/ext3/ux/css/data-view.css");
		JSLoader.loadStyleSheet(__ctxPath+"/ext3/ux/caltask/calendar.css");
		JSLoader.loadStyleSheet(__ctxPath+"/publicmodel/uploads/css/UploadPanel.css");
		JSLoader.loadStyleSheet(__ctxPath+"/css/icons.css");
		JSLoader.loadStyleSheet(__ctxPath+"/ext3/ux/css/Ext.ux.form.LovCombo.css");
		/* ExtJS总计组件 css 
		JSLoader.loadStyleSheet(__ctxPath+"/ext3/ux/css/Ext.ux.grid.GridSummary.css");
			<!--使用iframe加载的依赖JS  -->
	  JSLoader.loadJavaScript(__ctxPath+"/ext3/miframe.js");
		
		
		<!-- ExtJS总计组件 js-->
		 JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/Ext.ux.grid.GridSummary.js"); */
			
		 //JSLoader.loadJavaScript(__ctxPath+"/js/fckeditor/fckeditor.js");
		 JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/Fckeditor.js");
         //JSLoader.loadJavaScript(__ctxPath+"/js/fckeditor/custom/plugins/flvPlayer/swfobject.js");
    	
		 JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/UploadDialog.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/system/FileAttachDetail.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/system/FileUploadManager.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/system/FileUploadImageDetailForm.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/flow/fileupload/swfobject.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/flow/fileupload/FlexUploadDialog.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/system/GlobalTypeForm.js");
        
		 JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/PageComboResizer.js");
		
		 JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/Toast.js");
		
		 JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/Ext.ux.grid.RowActions.js");

		 JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/js/DataView-more.js");

		 JSLoader.loadJavaScript(__ctxPath+"/js/selector/UserSelector.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/selector/UserLevelSelector.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/selector/DepSelector.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/selector/OnlineUserSelector.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/core/TreeSelector.js");
			
			
		 JSLoader.loadJavaScript(__ctxPath+"/js/core/date.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/p2p/AddressAndSex.js");

		 JSLoader.loadJavaScript(__ctxPath+"/js/info/MessageWin.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/info/MessageReplyWin.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/info/MessageDetail.js");
		
		 JSLoader.loadJavaScript(__ctxPath+"/js/flow/ProcessRunStart.js");
		
		 JSLoader.loadJavaScript(__ctxPath+"/js/archive/ArchivesNode.js");
		
         JSLoader.loadJavaScript(__ctxPath+"/js/search/SearchForm.js");
        
         JSLoader.loadJavaScript(__ctxPath+"/js/global.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/globalDicProperty.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/globalFunction.js");
		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/dictionary/dictionaryTree.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/dictionary/dictionaryNotLastNodeTree.js" );
		  JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/common/AreaTree.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/carListWin.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/ourmain/selectSlCompanyMain.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/ourmain/selectSlPersonMain.js" ); 
		 JSLoader.loadJavaScript(__ctxPath+"/js/system/TradeCategorySelecter.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/vehicle/cs_tn_seeVehicle.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/vehicle/cs_tn_vehicle.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/vehicle/cs_tn_vehicle_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/stockownership/cs_tn_seeStockownership.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/stockownership/cs_tn_stockownership.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/stockownership/cs_tn_stockownership_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/product/cs_tn_seeProduct.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/product/cs_tn_product_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/product/cs_tn_product.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/officebuilding/cs_tn_seeOfficebuilding.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/officebuilding/cs_tn_officebuilding_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/officebuilding/cs_tn_officebuilding.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/machineinfo/cs_tn_seeMachineinfo.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/machineinfo/cs_tn_machineinfo_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/machineinfo/cs_tn_machineinfo.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/industry/cs_tn_seeIndustry.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/industry/cs_tn_industry_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/industry/cs_tn_industry.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/houseground/cs_tn_seeHouseground.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/houseground/cs_tn_houseground_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/houseground/cs_tn_houseground.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/house/cs_tn_seeHouse.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/house/cs_tn_house.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/house/cs_tn_house_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/education/cs_tn_seeEducation.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/education/cs_tn_education_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/education/cs_tn_education.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/dutyperson/cs_tn_seeDutyperson.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/dutyperson/cs_tn_dutyperson_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/dutyperson/cs_tn_dutyperson.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/droit/cs_tn_seeDroit.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/droit/cs_tn_droit_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/droit/cs_tn_droit.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/company/cs_tn_seeCompany.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/company/cs_tn_company_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/company/cs_tn_company.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/businessandlive/cs_tn_seeBusinessandlive.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/businessandlive/cs_tn_businessandlive_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/businessandlive/cs_tn_businessandlive.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/business/cs_tn_seeBusiness.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/business/cs_tn_business_update.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/business/cs_tn_business.js" );
		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/vehicle/cs_tn_vehicle_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/stockownership/cs_tn_stockownership_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/product/cs_tn_product_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/officebuilding/cs_tn_officebuilding_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/machineinfo/cs_tn_machineinfo_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/industry/cs_tn_industry_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/houseground/cs_tn_houseground_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/house/cs_tn_house_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/education/cs_tn_education_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/dutyperson/cs_tn_dutyperson_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/droit/cs_tn_droit_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/company/cs_tn_company_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/businessandlive/cs_tn_businessandlive_see.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/business/cs_tn_business_see.js" );
		
		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/addMortgageWindow.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/SmallLoanMortgageView.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/GuaranteeMortgageView.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/relieveMortgageWindow.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/banliMortgageWindow.js" );
		 JSLoader.loadJavaScript(__ctxPath+"js/p2p/OaNewsMessageManual.js" );

	
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/DZYMortgageView.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/BaozMortgageView.js" );	
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/AddDzyMortgageWin.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/business/BusinessForm.js" );	
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/businessandlive/BusinessandliveForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/company/CompanyForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/droit/DroitForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/dutyperson/DutypersonForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/education/EducationForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/house/HouseForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/houseground/HousegroundForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/industry/IndustryForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/machineinfo/MachineinfoForm.js" );		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/officebuilding/OfficeBuildingForm.js" );	
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/product/ProductForm.js" );			
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/stockownership/StockownershipForm.js" );			
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/vehicle/VehicleForm.js" );	
		  JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/vehicle/VehicleCarForm.js" );
		  JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/receivables/ReceivablesForm.js" );
		  JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/receivables/cs_tn_seeReceivables.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/UpdateDzyMortgageWin.js" );	
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/AddBaozMortgageWin.js" );	
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/UpdateBaozMortgageWin.js" );
	
	     JSLoader.loadJavaScript(__ctxPath+"/publicmodel/uploads/js/cs_picViewer.js");
	     JSLoader.loadJavaScript(__ctxPath+"/publicmodel/uploads/js/cs_showDownload.js");
	
	   	 JSLoader.loadJavaScript(__ctxPath+"/publicmodel/uploads/js/UploadPanel.js");
	     JSLoader.loadJavaScript(__ctxPath+"/publicmodel/uploads/js/upload.js");
	     JSLoader.loadJavaScript(__ctxPath+"/publicmodel/uploads/js/swfupload.js");
	    
	     JSLoader.loadJavaScript(__ctxPath+"/publicmodel/uploads/js/uploads.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/core/ntkoffice/NtkOfficePanel.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/archive/OfficeTemplateView.js");

         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/public.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/selectEnterGrid.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/selectEnterprise.js");
         JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/js/Ext.ux.util.js");
        
         JSLoader.loadJavaScript(__ctxPath+"/ext3/ux/js/Ext.ux.form.LovCombo.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/addEnterprise.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/addFastEnterprise.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/updateEnterprise.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/seeEnterprise.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/ajaxValidation.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/bankInfoList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/managecaseList.js");
         /*JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/shareequityList.js");*/
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/leadteamList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/debtList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/creditorList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/outassureList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/outinvestList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/relatedataList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/prizeList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/employeeStructure.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/financeInfo.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/seeEnterpriseCanzhao.js");
         /*JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/businessRecordRelationEnterprise.js");*/
       	 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationWin.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationGua.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/guarantee/enterpriseBusiness/creditRating.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/common/EnterpriseShareequity.js");
	      JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/EnterpriseShareequityView.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/upAnddownsStreamCustList.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/upAndDownStream.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/cashflowandSaleIncomeList.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/cashflowandSaleIncome.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/bplawsuit.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/bplawsuitList.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/upAndDownContract.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/upAnddownContractList.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/bppaytax.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/bppaytaxList.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/HeaderWin.js");
	    
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/common/bankInfoWin.js");
	    /*added by luowenyan*/
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/NegativeInfoWin.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/EducationInfoWin.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/WorkExperienceInfoWin.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/PublicActivityInfoWin.js");
	    JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/linkManEnterprise.js");

	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/PersonBusiness.js");
	     	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/CreditLoanHistoryPanel.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/CreditLoanHistoryFormPanel.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/PersonMortgage.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/Shareequity.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/LegalPerson.js");
	      JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/personAll.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/businessDealings/EnterpriseAll.js");
	    
	    
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/ajaxValidation.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/selectPersonWin.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/seePersonCanzhao.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/enterprise/relationPersonList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/personWindowObjList.js");
         JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/SpousePanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/addFamilyPerson.js");
		 /*JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/businessRecordList.js");*/
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/listReditregistries.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/thereunderList.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/public.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/bankInfoPersonList.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/common/common.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/relationPerson/relationPersonWin.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/SpouseWin.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/PersonView.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/ThereunderPanelView.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/ThereunderView.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/ThereunderForm.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/PersonFinanceInfo.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/addPersonFinanceInfo.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/addPersonWorkInfo.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/person/addFamilyInfo.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/dictionary/treegrid/TreeGridSorter.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/dictionary/treegrid/TreeGridColumnResizer.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/dictionary/treegrid/TreeGridNodeUI.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/dictionary/treegrid/TreeGridLoader.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/dictionary/treegrid/TreeGridColumns.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/dictionary/treegrid/TreeGrid.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/dictionary/TreeNodeChecked.js" );
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/bankrelationperson/BankRelationPersonView.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/bankrelationperson/BankRelationPersonWindow.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/bankrelationperson/bankDaRelationPersonList.js");
		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/contract/SlContractView.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/contract/SeeThirdContractWindow.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/contract/OperateContractWindow.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/contract/OperateThirdContractWindow.js");
//		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/mortgageInfoWinExt.js");//封装抵质押物查看信息  未完成 add by gao
		
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/common/ProjectAppUtil.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/common/ProjectStop.js");
	     JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/guarantee/project/GuaranteeProjectInfoEditPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/guarantee/project/GuaranteeProjectInfoPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/guarantee/project/ProjectInfoNavigation.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/project/SmallLoanProjectInfoEditPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/project/SmallLoanProjectInfoPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/project/SmallLoanProjectInfoNavigation.js");
		 
		  JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/project/ApproveProjectInfoPanel.js");
	 	 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/project/ApproveProjectInfoNavigation.js");
	 	 
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/financeProject/project/FinancingProjectInfoPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/financeProject/project/FinancingProjectInfoEditPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/financeProject/project/FinancingProjectInfoNavigation.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/project/SmallLoanProjectManager.js");
		 //流程中查看信息用到 add by gao
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/pawn/project/PlPawnProjectInfoEditPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/pawn/project/PlPawnProjectNavigation.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/pawn/project/PlPawnProjectInfoEdit.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/pawn/project/PlPawnProjectInfoPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/pawn/project/PlPawnProjectInfo.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/system/Imge_window.js");
		 
		 //融资租赁start
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/leaseFinance/project/LeaseFinanceProjectInfoNavigation.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/smallLoan/project/SlProcessRunView.js");//★★★ add by gaoqingrui  不想全局加载  关联文件LeaseFinanceProjectManager.js
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/leaseFinance/project/LeaseFinanceProjectInfoEditPanel.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/leaseFinance/project/LeaseFinanceProjectInfoPanel.js");
		  //融资租赁end
		 JSLoader.loadJavaScript(__ctxPath+"/js/selector/UserDialog.js");
		 
		 JSLoader.loadJavaScript(__ctxPath+"/js/customer/InvestPersonWindowObjList.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/customer/InvestPersonForm.js");
		 JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/common/monthPick.js");
		 /* 身份证验证JS */
		  JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/common/ValidateIdCard.js");
		  
		JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/mortgage/luoshiMortgageWindow.js" );

		JSLoader.loadJavaScript(__ctxPath+'/js/p2p/materials/BpCustMemberPicView.js' );
		JSLoader.loadJavaScript(__ctxPath+'/js/p2p/ChangeRecommand.js' );
		JSLoader.loadJavaScript(__ctxPath+'/js/creditFlow/customer/cooperation/selectCsCooperationPerson.js');
		JSLoader.loadJavaScript(__ctxPath+'/js/creditFlow/customer/cooperation/selectCsCooperationEnterprise.js');

/*招标详情*/
		JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/financingAgency/PlBidPlanInfoForm.js");
		JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/financingAgency/PlBidPlanInfoCusterFundIntent.js");
		
		JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/cooperation/selectCsCooperationEnterprise.js");
		JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/customer/cooperation/selectCsCooperationPerson.js");


		JSLoader.loadJavaScript(__ctxPath+"/js/creditFlow/StaffOnlyAdministration/InvestmentDetails.js");

		}
		