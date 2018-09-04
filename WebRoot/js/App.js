Ext.ns("App");

/**
 * processNameFlowKey流程的key：根据流程key查询对应的任务等信息。(全局变量)
 * 待办事项(桌面)：App.home.js
 * 待办事项(more)：MyTaskView.js
 * 项目待办事项(左侧菜单): ActivityTaskView.js
 * 我参与的项目	：MyProcessRunView.js
 * 项目事项追回  ：MyRunningTaskView.js
 * 我发起的项目  : ProcessRunView.js
 * 已办结事项	：CompleteTaskView.js
 * 
 * processNameFlowKey包含全部的流程的key
 * myProcessNameFlowKey不包含小贷展期流程、小贷主管意见流程、小贷监管流程
 */
var processNameFlowKey="ALL";
var myProcessNameFlowKey="ALL";
//var processNameFlowKey="repaymentAheadOfTimeFlow,alterAccrualFlow,microNormalFlow,microFastFlow,smallLoanNormalFlow,smallLoanFastFlow,mcroHistoryRecordsFlow,smallHistoryRecordsFlow,smallLoanPostponedFlow,microPostponedFlow";
//var myProcessNameFlowKey="repaymentAheadOfTimeFlow,alterAccrualFlow,microNormalFlow,microFastFlow,smallLoanNormalFlow,smallLoanFastFlow，mcroHistoryRecordsFlow,smallHistoryRecordsFlow,smallLoanPostponedFlow,microPostponedFlow";

/**
 * beJuxtaposedFlowNodeKeys同步流程节点key说明：
 * 所有流程中有并列(同步)任务的情况下,如中铭常规流程中的同步任务："财务收费确认"和"银行贷款信息登记",
 * 需要在产生同步任务的vm文件中显示格式为：财务收费确认,银行贷款信息登记 的单选项,
 * 则需要把其中一个节点的key(非主干的节点key)放置到beJuxtaposedFlowNodeKeys变量中,
 * 如果都有往下流转的连线,则需把其中一个key放置到beJuxtaposedFlowNodeKeys变量中,
 * 否则会显示两个同为：财务收费确认,银行贷款信息登记 的单选项;
 * 
 * 如果有并列任务中有的节点没有连线,如中铭常规流程中的同步任务："反担保措施登记"(无往下流转连线：非主干),
 * "项目保前归档"(无往下流转连线：非主干),"保中监管及还款凭证获取"(有往下流转连线：主干),
 * 则需要把"反担保措施登记"和"项目保前归档"的节点的key放置到beJuxtaposedFlowNodeKeys变量中。
 * 
 * 注：该变量beJuxtaposedFlowNodeKeys完全只针对有并列(同步)任务的情况,
 * 如果流程中同步任务的增加或者删除等情况需要同时修改该变量。
 * 
 */
//var beJuxtaposedFlowNodeKeys="slpfPracticable,slnWorkableMortgageMeasures,slfWorkableMortgageMeasures,gnCollectCost";

//var currentNodeKeyByNextTaskBeJuxtaposed="slnCheckPassResult,slfExaminationArrangement,slpfCheckOpinionOfMeetting,gnMakeContract";

/**
 * 在流程vm文件中不需要显示某个节点的的意见与说明，
 * 则把该节点的key放到以下变量中。
 */
//var filterableNodeKeys="filterableNodeKeys,ExaminationArrangement";
var filterableNodeKeys="onlineJudgement,filterableNodeKeys,ExaminationArrangement,xsps,resolutionOnlineReviewMeeting";
//首页PORTAL元素
var PortalItem=function(panelId,column,row){
   this.panelId=panelId;
   this.column=column;
   this.row=row;
};
//栏目PROTAL元素
var SectionItem = function(sectionId,colNumber,rowNumber){
	this.sectionId=sectionId;
	this.colNumber=colNumber;
	this.rowNumber=rowNumber;
};
//用户信息
var UserInfo=function(user){
	this.userId=user.userId;
	this.username=user.username;
	this.fullname=user.fullname;
	this.depId=user.depId;
	this.depName=user.depName;
	this.rights=user.rights;
	this.portalConfig=user.items;
	this.userDesk=user.userDesk;//用户配置了属于自己的个人桌面   @desk
	this.deskRights=user.deskRights;//用户拥有的角色配置了个人桌面 @desk
	this.topModules=user.topModules;
};
//系统配置
var SysConfig=function(sysConfigs){
	this.dynamicPwd=sysConfigs.dynamicPwd;
};

//当前登录用户
var curUserInfo=null;
//取得当前系统配置
var sysConfigInfo=null;

//检查当前用户有权访问funKey对应的功能
function isGranted(funKey){
	if(curUserInfo.rights.indexOf('__ALL')!=-1){
		return true;
	}
	var rights = curUserInfo.rights;
	/*var myPattern = new RegExp("^[a-zA-Z]"); // 以英文字母开头
	if(curUserInfo.rights.indexOf(funKey)!=-1 ){
		if(curUserInfo.rights.indexOf(+funKey)==0){
			return true;
		}else{
			if(!myPattern.exec(rights.substr(rights.indexOf(funKey)-1,1))){
				return true;
			}
		}
	}*/
	var arr=rights.split(",");
	for(var i=0;i<arr.length;i++){
		if(funKey.trim()==arr[i].trim()){
			return true;
			
		}	
	
	} 
	
	return false;
}
function isSystemType(funKey){
	var arr = curUserInfo.topModules;
	for(var i=0;i<arr.length;i++){ 
		//@DHZCB
		//注释掉是因为导航是大的分类，每个导航下面又会区分很多个小的左侧导航
		//例如menu-fa.xml下面包括menu-fa1.xml、menu-fa2.xml...
		/*if(funKey==arr[i].id){
			return true;
		}*/
		if(arr[i] && -1!=arr[i].id.indexOf(funKey)){
			return true;
		}
	} 
	return false;
}

//根据SystemType设定左侧菜单顶级标题
function changeWestTitle(){
	var westTitle="导航";
	
	 if(SystemType=="team"){
	    westTitle="导航-借贷中心";
	 }
	 else if(SystemType=="slDeal"){
	    westTitle="导航-贷款办理";
	 }
	 else if(SystemType=="org"){
	    westTitle="导航-组织机构";
	 }
	 else if(SystemType=="investCstManger"){
	    westTitle="导航-财富中心";
	 }
	 else if(SystemType=="fa"){
	    westTitle="导航-业务中心";
	 }
	 else if(SystemType=="erp"){
	    westTitle="导航-系统管理";
	 }
	 else if(SystemType=="p2p"){
	    westTitle="导航-运营中心";
	 }
	 else if(SystemType=="pteam"){
	 	westTitle="导航-个贷办理";
	 }
	 else if(SystemType=="eteam"){
		westTitle="导航-企贷办理";
	 }
	  else if(SystemType=="control"){
	 	westTitle="导航-风控中心";
	 }
	 else if(SystemType=="report"){
		westTitle="导航-台账报表";
	 }
	  else if(SystemType=="customer"){
		westTitle="导航-客户中心";
	 }else if(SystemType=="fa_online"){
		westTitle="导航-信用借款业务";
	 }else if(SystemType=="fa_p2p"){
		westTitle="导航-P2P中介业务";
	 }else if(SystemType=="fa_p2c"){
		westTitle="导航-C2P中介业务";
	 }else if(SystemType=="fa_pa2p"){
		westTitle="导航-PA2P中介业务";
	 }else if(SystemType=="fa_ca2p"){
		westTitle="导航-CA2P中介业务";
	 }else if(SystemType=="fa_secondtransaction"){
		westTitle="导航-债权交易业务";
	 }else if(SystemType=="fa_dplan"){
		westTitle="导航-D债权转让业务";
	 }else if(SystemType=="fa_uplan"){
		westTitle="导航-U债权转让业务";
	 }else if(SystemType=="fa_bao"){
		westTitle="导航-活期理财业务";
	 }else if(SystemType=="marketing"){
		westTitle="导航-营销中心";
	 }
	 Ext.getCmp("west-panel").setTitle(westTitle);
}

   //初始化系统类型   
	function changeSystem(type){
		//按照权限 修改头部导航 
		//p2p 管理
		if(!isSystemType('Mod_p2p')){
			try{
    			Ext.get("p2pManger").setStyle("display", "none");
    		}catch (e) { }
    	}
    	//贷款办理
    	if(!isSystemType('Mod_sldeal')){
    		try{
    			Ext.get("slDeal").setStyle("display", "none");
    		}catch (e) { }
    	}
    	//机构管理
    	if(!isSystemType('Mod_org')){
    		try{
    			Ext.get("orgManger").setStyle("display", "none");
    		}catch (e) { }
    	}//投资客户管理
    	if(!isSystemType('Mod_investcstmanger')){
    		try{
    			Ext.get("investCstManger").setStyle("display", "none");
    		}catch (e) { }
    	}//金融中介管理
    	if(!isSystemType('Mod_fa')){
    		try{
    			Ext.get("faManger").setStyle("display", "none");
    		}catch (e) { }
    	}
    	//ERP 管理
    	if(!isSystemType('Mod_erp')){
    		try{
    			Ext.get("ERPManger").setStyle("display", "none");
    		}catch (e) { }
    	}
    	//贷款管理
    	if(!isSystemType('Mod_team')&& !isSystemType('Mod_investcstmanger')&&!isSystemType('Mod_yun')){
    		try{
    			Ext.get("smallHandler").setStyle("display", "none");
    		}catch (e) { }
    	}
    	
    	//风控中心
		if(!isSystemType('Mod_control')){
			try{
    			Ext.get("controlManger").setStyle("display", "none");
    		}catch (e) { }
    	}
    	//报表中心
		if(!isSystemType('Mod_report')){
			try{
    			Ext.get("reportManger").setStyle("display", "none");
    		}catch (e) { }
    	}
    	//客户中心
		if(!isSystemType('Mod_customer')){
			try{
    			Ext.get("customerManger").setStyle("display", "none");
    		}catch (e) { }
    	}
    	//营销中心
		if(!isSystemType('Mod_Marketing')){
			try{
    			Ext.get("marketingManger").setStyle("display", "none");
    		}catch (e) { }
    	}
	    SystemType=type;
	    loadWestMenu(false);
	    changeWestTitle();
	    changeDesk()
	}
	
	/**
	 * 刷新整个项目时初始化个人桌面方法
	 * 以及每次登陆后刷新系统读取工作桌面的方法
	 * 
	 */
	function changeDesk(){
		var tabs = Ext.getCmp('centerTabPanel');
		var tabItem = tabs.getItem("PersonalDesktop");
		if (tabItem) {
			tabs.remove(tabItem);
		}
		tabItem = tabs.getItem("PersonalDeskNew");
		if (tabItem) {
			tabs.remove(tabItem);
		}
        //获取用户自己定义的特性桌面
		var personDesk=curUserInfo.userDesk;
		//默认展示的桌面类型
		var loadItems="PersonalDesktop";
		if(curUserInfo.rights.indexOf('__ALL')!=-1){//判断超级管理员的桌面展示情况
			if(initDeskMenus){//获取系统中支持的全部桌面
				//对现有的桌面参数进行拆分
				var initDesks=initDeskMenus.replace("{","").replace("}","").split(',');
				//判断超级管理员配置的 桌面是否包含在需要显示的桌面内
				//判断是否配置了偏好桌面
				if(personDesk){
					var deafault=null;
					for(var i=0;i<initDesks.length;i++){
						loadItem=initDesks[i].split("=");
						if(loadItem[0].indexOf(personDesk)>0){
							deafault=personDesk;
						    break;
						}else{
							continue;
						}
					}
					if(deafault!=null){
						loadItems=deafault
					}else{
						loadItems=initDesks[0].split("=")[0];
					}
				
				}else{
					loadItems=initDesks[0].split("=")[0];
				}
			}
		}else{
			//获得非超级管理员的角色配置的桌面种类
			var roleDesk=curUserInfo.deskRights;
			if(roleDesk){//角色配置了桌面
				//判断当前用户配置的 桌面是否包含在可显示的桌面内
				if(personDesk&&roleDesk.includes(personDesk)){
					loadItems=personDesk;
				}else{//如果不包含在内，选择可展示桌面中的的第一个桌面
					loadItems=roleDesk.split(",")[0];
				}
			}
		}
		
		loadAppHome(loadItems);//@desk html
	}
	
	/**
	 * 导航条上关于可选择的工作桌面类型判断
	 * 如果用户角色有多种可以展示的个人桌面，则出现列表
	 * add by linyan 2016-05-20
	 */
	function changeShowDesk(el){
		var loadItems="PersonalDesktop";
		var deskType="默认桌面";
		var menu= new Ext.menu.Menu();
		if(initDeskMenus){
			var initDesks=initDeskMenus.replace("{","").replace("}","").split(',');
			for(var i=0;i<initDesks.length;i++){
				loadItem=initDesks[i].split("=");
				if(curUserInfo.rights.indexOf('__ALL')!=-1){
					menu.add({id: loadItem[0],text:loadItem[1],handler:clickAppHomeMethod,iconCls:'btn-deskSet'});
				}else {
					if(curUserInfo.deskRights){//表示当前角色有配置这个桌面
						var roleDesk=curUserInfo.deskRights.trim().split(',')
						if(roleDesk.includes(loadItem[0].trim())){
							menu.add({id: loadItem[0],text:loadItem[1],handler:clickAppHomeMethod,iconCls:'btn-deskSet'});
						}
						
					}
				}
			}
			
			if(menu.items.length<=0){//展示默认页面
				menu.add({id: loadItems,text:deskType,handler:clickAppHomeMethod,iconCls:'btn-deskSet'});
			}
		}else{//展示默认提示页面
			 menu.add({id: loadItems,text:deskType,handler:clickAppHomeMethod,iconCls:'btn-deskSet'});
		}
		menu.show(el);
	}

	/**
	 * 工作桌面可选择的桌面类型中handlar点击方法
	 * 点击menu的处理方法
	 * add by linyan 2016-05-20
	 * 
	 */
	function clickAppHomeMethod(){
	 	var loadItem=this.id;
	 	loadAppHome(loadItem)
	}
	
    //下拉选框 
    var menu = new Ext.menu.Menu();
    menu.add({id: 'menu1',text:'个贷办理',handler:smallPHandler,iconCls:'btn-tree-team56'});
    menu.add({id: 'menu3',text:'企贷办理',handler:smallEHandler,iconCls:'btn-tree-team56'});
   //  menu.add({id: 'menu4',text:'融资租赁系统',handler:leasefinanceHandler,iconCls:'menu-guarantee-info'});
   //  menu.add({id: 'menu5',text:'典当系统',handler:pawnHandler,iconCls:'menu-guarantee-info'});
       
     //显示贷款业务下拉选项
    function showMenus(el){
         menu.show(el);
    }
    //各贷款业务操作
    function smallHandler(){
    	if(!isSystemType('Mod_team')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	//loadAppHome("AppHomeTeam");
     	SystemType='team';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);	
    }
    function smallPHandler(){
    	//loadAppHome("AppHomeTeam");
     	SystemType='pteam';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);	
    }
    function smallEHandler(){
    	//loadAppHome("AppHomeTeam");
     	SystemType='eteam';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);	
    }
    
    
    
     //贷款办理
    function slDeal(){
    	if(!isSystemType('Mod_sldeal')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	//loadAppHome("AppHomeTeam");
     	SystemType='slDeal';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
	 	
    }
      //机构管理
    function orgManger(){
    	if(!isSystemType('Mod_org')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	//loadAppHome("AppHomeTeam");
     	SystemType='org';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
	 	
    }
      //投资客户管理
    function investCstManger(){
    	if(!isSystemType('Mod_investcstmanger')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	//loadAppHome("AppHomeTeam");
     	SystemType='investCstManger';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
       //金融中介管理
    function faManger(){
    	if(!isSystemType('Mod_fa')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	//loadAppHome("AppHomeTeam");
     	SystemType='fa';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
       //ERP管理
    function ERPManger(el){
    	if(!isSystemType('Mod_erp')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	//loadAppHome("AppHomeTeam");
     	SystemType='erp';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
      //p2p管理
    function p2pManger(el){
    	if(!isSystemType('Mod_p2p')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	changeHeaderClass(el);
     	SystemType='p2p';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
    
    
      //风控中心
    function controlManger(el){
    	if(!isSystemType('Mod_control')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	
    	changeHeaderClass(el);
     	SystemType='control';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
    
    
      //报表中心
    function reportManger(el){
    	if(!isSystemType('Mod_report')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	changeHeaderClass(el);
     	SystemType='report';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
    //客户中心
    function customerManger(el){
    	if(!isSystemType('Mod_customer')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	changeHeaderClass(el);
     	SystemType='customer';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
    
    //营销中心
    function MarketingManger(el){
    	if(!isSystemType('Mod_Marketing')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	changeHeaderClass(el);
     	SystemType='marketing';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
    }
    
    //融资系统
    function financingHandler(){
 		if(!isSystemType('Mod_financing')){
 			Ext.Msg.alert('提示', "抱歉，您没有此权限。");
 			return;
 		}
      	loadAppHome("AppHomeTeamFinancing");
      	SystemType='financing';
      	changeSysType(SystemType);
      	changeWestTitle();
	  	loadWestMenu(false);
    }
        //各贷款业务操作
    function creditHandler(){
    	if(!isSystemType('Mod_credit')){
    		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
    		return;
    	}
    	//loadAppHome("AppHomeTeam");
     	SystemType='credit';
     	changeSysType(SystemType);
     	changeWestTitle();
	 	loadWestMenu(false);
	 	
    }
    //互联网金融
    function interentFinanceHandler(){
    
 		if(!isSystemType('Mod_internetfinance')){
 			Ext.Msg.alert('提示', "抱歉，您没有此权限。");
 			return;
 		}
      	SystemType='internetfinance';
      	changeSysType(SystemType);
      	changeWestTitle();
	  	loadWestMenu(false);
    }
    
     function guaranteeHandler(){
     	if(!isSystemType('Mod_guarantee')){
     		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
     		return;
     	}
	    loadAppHome("AppHomeTeamGuarantee");
	    SystemType='guarantee';
	    changeSysType(SystemType);
	    changeWestTitle();
		loadWestMenu(false);
    }
 	
    function pawnHandler(){
 		if(!isSystemType('Mod_pawn')){
 			Ext.Msg.alert('提示', "抱歉，您没有此权限。");
 			return;
 		}
      	loadAppHome("AppHomeTeamPawn");
      	SystemType='pawn';
      	changeSysType(SystemType);
      	changeWestTitle();
	  	loadWestMenu(false);
    }
     function leasefinanceHandler(){
 		if(!isSystemType('Mod_leasefinance')){
 			Ext.Msg.alert('提示', "抱歉，您没有此权限。");
 			return;
 		}
      	loadAppHome("AppHomeTeamLeaseFinance");
      	SystemType='leasefinance';
      	changeSysType(SystemType);
      	changeWestTitle();
	  	loadWestMenu(false);
    }
   //债权转让系统
     function creditAssignmentHandler(){
 		if(!isSystemType('Mod_assignment')){
 			Ext.Msg.alert('提示', "抱歉，您没有此权限。");
 			return;
 		}
      	//loadAppHome("AppHomeTeamLeaseFinance");
      	SystemType='assignment';
      	changeSysType(SystemType);
      	changeWestTitle();
	  	loadWestMenu(false);
    }
     
    //修改选择系统的session
     function changeSysType(sysType){
        var url=__ctxPath+"/system/changeSystypeAppRole.do";
	    Ext.Ajax.request({
			url:url,
			method:"post",
			success:function(response,opts){},
			params:{sysType:sysType} 
		});
	       
	 }
	 //改变头部导航的字体选中的颜色
   function changeHeaderClass(el){
	   	Array.prototype.slice.call($(".header_rit a")).forEach(function(item){
			item.className = "";
		});
		el.className = "curr";
   }

/**
 * 
 * @param {} id
 * @param {} callback 回调函数
 */
App.clickTopTab=function(id,params,precall,callback,activeid){
	if(precall!=null){
		precall.call(this);
	}
	var importId=id;
	if(id.indexOf('_')!=-1){//当传入的id为ABC_1;则会加载名为ABC的类，但其id却为ABC_1;
		importId=id.split('_')[0];
	}
	if("NewProjectFormSSZZ"==id){
		$ImportJs('NewProjectFormSSZZ', function() {
			new NewProjectFormSSZZ().show();
		}, null);
	}else if("NewProjectFormHistory"==id){
		$ImportJs('NewProjectFormHistory', function() {
			new NewProjectFormHistory().show();
		}, null);
	}else if("NewProjectFormEnterPrise"==id){
		$ImportJs('NewProjectFormEnterPrise', function() {
			new NewProjectFormEnterPrise().show();
		}, null);
	}else if("CreateNewProjectFrom"==id){
		$ImportJs('CreateNewProjectFrom', function() {
			new CreateNewProjectFrom({operationType:params.operationType,history:params.history}).show();
		}, params);
	}else if("P2pAddProduct"==id){
		$ImportJs('P2pAddProduct', function() {
			new P2pAddProduct().show();
		}, null);
	}else if("DeskTopForm"==id){
		$ImportJs('DeskTopForm', function() {
			new DeskTopForm().show();
		},null);
	}else{
		//方便桌面切换效果渲染，关闭已经打开的界面
		var tabs = Ext.getCmp('centerTabPanel');
		
	    tabs.items.each(function(item){
         if(id.trim().indexOf("Desk") > 0&&item.getId().indexOf("Desk") > 0){
	         tabs.remove(item,true);
	  	 }
	   });
		
		var tabItem = tabs.getItem(id.trim());
		if (tabItem == null) {
			$ImportJs(importId.trim(), function(view) {
				tabItem = tabs.add(view);
				if(tabItem.getId().indexOf("Desk") > 0){
						    tabs.items.each(function(item){
                                if( item.getId()!=tabItem.getId() && item.getId().indexOf("Desk") > 0){
                                  tabs.remove(item,true);
                                 }
                        });
				}
				tabs.activate(tabItem);
			},params);
		}else {
			if(callback!=null){
				callback.call(this);
			}
			tabs.activate(tabItem);
		}
		if(activeid!=null){
			var west = Ext.getCmp('west-panel');
			west.getLayout().setActiveItem(activeid);
	    }
	}
};
/**
 * 
 * @param {} id
 * @param {} callback 回调函数
 */
App.clickTopTabRemoveAll=function(id,params,precall,callback,activeid){
	if(precall!=null){
		precall.call(this);
	}
	var importId=id;
	if(id.indexOf('_')!=-1){//当传入的id为ABC_1;则会加载名为ABC的类，但其id却为ABC_1;
		importId=id.split('_')[0];
	}
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem(id);
	if (tabItem != null) {
		tabs.removeAll ();
	}
	$ImportJs(importId, function(view) {
	
		tabItem = tabs.add(view);
		tabs.activate(tabItem);
	},params);
	
	if(activeid!=null){
			var west = Ext.getCmp('west-panel');
			west.getLayout().setActiveItem(activeid);
    }
};
/**
 * 
 * @param {} id
 * @param {} callback 回调函数
 */
App.clickTopTabReplace=function(id,params,precall,callback,activeid){
	if(precall!=null){
		precall.call(this);
	}
	var importId=id;
	if(id.indexOf('_')!=-1){//当传入的id为ABC_1;则会加载名为ABC的类，但其id却为ABC_1;
		importId=id.split('_')[0];
	}
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem(id);
	if (tabItem != null) {
		tabs.remove (id);
	}
	$ImportJs(importId, function(view) {
	
		tabItem = tabs.add(view);
		tabs.activate(tabItem);
	},params);
	
	if(activeid!=null){
			var west = Ext.getCmp('west-panel');
			west.getLayout().setActiveItem(activeid);
    }
};
App.clickTopTabIframe = function(node){
	if (node.id == null || node.id == '' || node.id.indexOf('xnode') != -1) {
		return;
	}
	// alert(node.id);
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem(node.id);
	if (tabItem == null) {
		tabItem = tabs.add( {
			xtype : 'iframepanel',
			title : node.text,
			id : node.id,
			loadMask : {
				msg : '正在加载...,请稍等...'
			},
			iconCls : node.attributes.iconCls,
			defaultSrc : __ctxPath + '/pages/iframe/'+node.attributes.model+'/' + node.id + '.jsp?id='
					+ Math.random(),
			listeners : {
				domready : function(iframe) {
				}
			}
		});
	}
	tabs.activate(tabItem);
};
App.clickTopTabUrl = function(node){
	if (node.id == null || node.id == '' || node.id.indexOf('xnode') != -1) {
		return;
	}
	// alert(node.id);
	var tabs = Ext.getCmp('centerTabPanel');
	var _url = node.attributes.url;
	if(!(_url.substring(0,5)=="http:")){
		_url = __ctxPath + _url;
	}
	var tabItem = tabs.getItem(node.id);
	if (tabItem == null) {
		tabItem = tabs.add( {
			xtype : 'iframepanel',
			title : node.text,
			id : node.id,
			loadMask : {
				msg : '正在加载...,请稍等...'
			},
			iconCls : node.attributes.iconCls,
			defaultSrc : _url,
			listeners : {
				domready : function(iframe) {
				}
			}
		});
	}
	tabs.activate(tabItem);
};
/**
 * 菜单启动流程
 * @param {} node
 */
App.clickStartFlow = function(node){
	var contentPanel = App.getContentPanel();
	var startForm = contentPanel.getItem('ProcessRunStart' + node.attributes.defId);

	if (startForm == null) {
		startForm = new ProcessRunStart({
					id : 'ProcessRunStart' + node.attributes.defId,
					defId : node.attributes.defId,
					flowName : node.attributes.flowName
				});
		contentPanel.add(startForm);
	}
	contentPanel.activate(startForm);
};
/**
 * 点击了公文流程的结点
 */
App.clickFLowNode = function(node){
	
	var jsArr=[
	           __ctxPath+'/js/archive/ArchivesDetailWin.js',
	           __ctxPath+'/js/archive/ArchHastenForm.js',
	           __ctxPath+'/js/flow/ProcessNextForm.js',
	           __ctxPath+'/js/flow/ProcessRunDetail.js'];
	$ImportSimpleJs(jsArr,null);
	
	var contentPanel = App.getContentPanel();
	var nodePanel = contentPanel.getItem(node.id);

	if (nodePanel == null) {
		nodePanel = new ArchivesNode({
					id : 'ProcessRunStart' + node.id,
					title : node.attributes.text
				});
		contentPanel.add(nodePanel);
	}
	contentPanel.activate(nodePanel);
};

App.ClickFlowByKey = function(node){
	var contentPanel = App.getContentPanel();
	Ext.Ajax.request({
			url:__ctxPath+'/setting/getFlowCommonFlowConf.do?flowType='+node.attributes.flowKey,
			method:'POST',
			success:function(response,options){
				var object=Ext.util.JSON.decode(response.responseText);
				if(object.defId==null){
					//Ext.Msg.alert("提示","系统没有配置本流程，请联系管理员！");
					Ext.ux.Toast.msg('提示信息', '系统没有配置本流程，请联系管理员！');
					return;
				};
				var startForm = contentPanel.getItem('ProcessRunStart' + object.defId);

				if (startForm == null) {
					startForm = new ProcessRunStart({
								id : 'ProcessRunStart' + object.defId,
								defId : object.defId,
								flowName : object.flowName
							});
					contentPanel.add(startForm);
				};
				contentPanel.activate(startForm);
			},
			failure : function(response,options){
			
			}
	});
	
};

App.clickNode = function(node) {
	//if(node.id==null || node.id=='' || node.id.indexOf('xnode')!=-1){
		//return ;
	//}
	var nodeid = node.id;
	if(nodeid==null || nodeid=='' || nodeid.indexOf('xnode')!=-1){
		return ;
	}
	//判断如果id中最后一个字符为"_"时，则截取掉。目的是防止menu-mian.xml等菜单中访问id一样时。(汪贵州 2011-2-28修改)
	if(nodeid.substring(nodeid.length-1,nodeid.length)=="_"){
		nodeid=nodeid.substring(0,nodeid.length-1);
	}
	//报表,id带参的情况解析
	var id = nodeid;
	var title=node.text;
	if(id.indexOf('?')>0){
		var str=id.split('?');
		var paramsString="";
		if(str.length>0){
			id=str[0];
			var paramsStr=str[1];
			var paramArray=paramsStr.split('&');
			for(i=0;i<paramArray.length;i++){
				var pstr=paramArray[i];
				var parr=pstr.split('=');
				var p=parr[0];
				var v=parr[1];
				paramsString+=p+':\''+v+'\',';
			}
			paramsString+='title:\''+title+'\'';
			paramsString="{"+paramsString+"}";
		}
		if(node.attributes.url){
			App.clickTopTabUrl(node);
		}else if(node.attributes.iframe){
			App.clickTopTabIframe(node);
		}else if(node.attributes.defId){//启动工作流
			App.clickStartFlow(node);
		}else if (node.attributes.flowNode){//启动工作流
			App.clickFLowNode(node);
		}else if (node.attributes.flowKey){//根据流程KEY启动流程
			App.ClickFlowByKey(node);
		}else{
			App.clickTopTab(id,Ext.decode(paramsString));
		}
		
	}else{
		if(node.attributes.url){
			App.clickTopTabUrl(node);
		}else if(node.attributes.iframe){
			App.clickTopTabIframe(node);
		}else if(node.attributes.defId){
			App.clickStartFlow(node);
		}else if (node.attributes.flowNode){
			App.clickFLowNode(node);
		}else if (node.attributes.flowKey){//根据流程KEY启动流程
			App.ClickFlowByKey(node);
		}else{
			App.clickTopTab(nodeid,Ext.decode(node.attributes.params));
		}
	}
};
/**
 * 桌面点击
 */
App.MyDesktopClick=function(){
	var desktopPanel=Ext.getCmp("MyDesktop");
	if(desktopPanel!=null){
		desktopPanel.expand(true);
	}
	App.clickTopTab('ComIndexPage');
};
/**
 * 点击个人主页
 */
App.MyDesktopClickTopTab=function(id,params,precall,callback){
	if(precall!=null){
		precall.call(this);
	}
	var tabs = Ext.getCmp('centerTabPanel');
	var tabItem = tabs.getItem(id);
	
	if (tabItem == null) {
		$ImportJs(id, function(view) {
			tabItem = tabs.add(view);
			tabs.activate(tabItem);
		},params);
	}else {
		tabs.remove(tabItem);
		var str='new ' + id ;
		if(params!=null){
			str+='(params);';
		}else{
			str+='();';
		}
		var view= eval(str);
		tabItem = tabs.add(view);
		tabs.activate(tabItem);
	}
};
/**
 * 退出系统
 */
App.Logout = function() {
	try{
		window.location.href=__ctxPath+"/j_logout.do";		
	}catch(e){}
//	Ext.Ajax.request({
//				url : __ctxPath + '/j_logout.do',
//				success : function() {
//					deleteCookie("jforumSSOCookieNameUser","/",0);
//					window.location.href = __ctxPath + '/login.jsp';
//				}
//	});
};

/**
 * 
 */
App.Topcollapse = function(a) {
	try{
		var panel=Ext.getCmp("__nortPanel");
		
		if(panel.collapsed){
			panel.expand(true);
			a.className="index-btn-expand";
			a.title="收起头部";
			
		}else{
			a.className="index-btn-collapse";
			a.title="展开头部";
			panel.collapse(true);
		}		
	}catch(e){}
//	Ext.Ajax.request({
//				url : __ctxPath + '/j_logout.do',
//				success : function() {
//					deleteCookie("jforumSSOCookieNameUser","/",0);
//					window.location.href = __ctxPath + '/login.jsp';
//				}
//	});
};

App.isCustomized4DLTC = function(){
	return hurong_flowType="dltcCreditFlow";
};

//----------------------------报表相关--开始----------------------------------

/**
 * 公用方法
 * @param {} panel 
 * @param {} url 			请求URL
 * @param {} reportKey		报表key
 * @param {} reportType		报表类型(pdf、html、xls)
 */
function commomClick(panel,url,reportKey,reportType){
	var parentObj=panel.form.getFieldValues();
	parentObj.reportKey=reportKey;
	parentObj.reportType=reportType;
	transform(parentObj);
	if(reportType=="xls" || reportType=="pdf"){
		commonPost(url,parentObj);
	}else if(reportType=="html"){
		openPostWindow(url,parentObj,"_blank");
	}else{
		Ext.Ajax.request({
			method:"post",
			url : url,
			params : parentObj,
		    success : function(ret) {
		    	var totalHeight=$('#centerTabPanel').height();
		    	var doc=document.getElementById(reportKey);
		    	
		    	doc.setAttribute('style',"overflow:scroll;height:"+(totalHeight-100)+"px;");
		    	
		    	doc.innerHTML = ret.responseText;//动态的将页面加载
		    }
		});
	}
}

/**
 * 格式化日期参数
 * @param {} obj
 */
function transform(obj){
    for(var item in obj){
        if(obj[item] instanceof Date){
        	//obj[item]=obj[item].toLocaleDateString()
        	//obj[item]=(obj[item]).format('Y-m-d');
        	obj[item]=(obj[item]).format('Y-m-d');
        }
    }
}

/**
 * 模拟post提交
 * @param {} path
 * @param {} params
 * @param {} method
 */
function commonPost(path, params) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);

    concatParams(params,form);//拼接参数

    form.submit();
}

/**
 * 模拟post提交,打开新窗口
 * @param {} url
 * @param {} params
 * @param {} name
 */
function openPostWindow(url, params, name) {
	var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", url);
    form.setAttribute("target", name);
    
	concatParams(params,form);//拼接参数
	
	form.addEventListener("onsubmit",function(){
		window.open(name); 
	}); 
	form.submit(); 
}

/**
 * 拼接参数
 * @param {} params		参数
 * @param {} tempForm	form表单
 */
function concatParams(params,tempForm){
	for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            tempForm.appendChild(hiddenField);
         }
    }
}

//----------------------------报表相关--结束----------------------------------

	
//应用程序总入口
Ext.onReady(App.init);
