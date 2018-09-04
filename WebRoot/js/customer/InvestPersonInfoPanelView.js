var getGridDate1 = function(grid) {
	if (typeof (grid) == "undefined" || null == grid) {
		return "";
		return false;
	}
	var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）

	var vCount = vRecords.length; // 得到记录长度

	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for ( var i = 0; i < vCount; i++) {
			var str = Ext.util.JSON.encode(vRecords[i].data);
			/*var index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";
			index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";
			index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";*/
			vDatas += str + '@';
		}

		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	return vDatas;
}
var seeIntent = function(orderNo,investPersonName,bidPlanId){


	
					new InverstPersonBpFundIntent({
					investPersonName:investPersonName,
					orderNo:orderNo,
					bidPlanId:bidPlanId
					}).show();
};

var downContracts=function(investId){

	

	window.open(__ctxPath + "/contract/downContractsProduceHelper.do?investId="
					+ investId, '_blank');

}
var deleteFun = function(url, prame, sucessFun,i,j) {
	Ext.Ajax.request( {
		url : url,
		method : 'POST',
		success : function(response) {
			if (response.responseText.trim() == '{success:true}') {
				if(i==(j-1)){
				    Ext.ux.Toast.msg('操作信息', '删除成功!');
				}
				sucessFun();
			} else if (response.responseText.trim() == '{success:false}') {
				
				Ext.ux.Toast.msg('操作信息', '删除失败!');
			}
		},
		params : prame
	});
};
InvestPersonInfoPanelView = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"Invest_info",
		sharteequitysm : null,
		sharteequitybar : null,
		bussinessType:null,
		grid_sharteequity : null,
		projectId : null,
		//enId : null,
		isHidden : false,
		isHiddenHT : false,
		unAdvanceMoney:0,
		object:null,
		flag:true,
		//isTitle : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.htType) != "undefined") {
				this.htType = _cfg.htType;
			}
			if (typeof (_cfg.projectId) != "undefined") {
				this.projectId = _cfg.projectId;
			}
			/*if (typeof (_cfg.enId) != "undefined") {
				this.enId = _cfg.enId;
			}*/
			if (typeof (_cfg.bussinessType) != "undefined") {
				this.bussinessType = _cfg.bussinessType;
			}
			if (_cfg.isHidden) {
				this.isHidden = _cfg.isHidden;
			}
			if (_cfg.isHiddenHT) {
				this.isHiddenHT = _cfg.isHiddenHT;
			}
			if (_cfg.object) {
				this.object = _cfg.object;
			}
			/*if (typeof (_cfg.isTitle) != "undefined") {
				this.isTitle = _cfg.isTitle;
			}*/
			Ext.applyIf(this, _cfg);
			this.initUIComponents();
			InvestPersonInfoPanelView.superclass.constructor.call(this,{
						items : [
							{xtype:'panel',
							border:false,
							hidden:this.isFlow,
							//hidden : this.isTitle==true?false:true,
							bodyStyle:'margin-bottom:5px',
							html : '<B><font class="x-myZW-fieldset-title">【投资人信息】：</font></B>'
							},
							this.grid_sharteequity 
						]
					})
		},
		initUIComponents : function() {
			var me = this;
			var summary = new Ext.ux.grid.GridSummary();
			function totalMoney(v, params, data) {
				return '总计';
			}
			this.sharteequitybar = new Ext.Toolbar({
				items : [/*{
					html:'<font style="color:black;font-weight:bold;">【投资人信息】</font>',
					hidden:!this.isFlow
				},*/{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.createShareequity
							
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					}),{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.deleteShareequity
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					})/*,{
						iconCls : 'btn-readdocument',
						text : '查看',
						xtype : 'button',
						//hidden:this.isHidden,
						scope : this,
						handler:this.seeShareequity
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					})*/,{
						iconCls : 'btn-save',
						text : '保存',
						hidden:this.isHidden,
						xtype : 'button',			
						scope : this,
						handler:this.save
					} 
					,{
						iconCls : 'btn-save',
						text : '合同生成',
						hidden:this.isHiddenHT,
						xtype : 'button',			
						scope : this,
						handler:this.createContract
					}
                    ,{
                        iconCls : 'btn-save',
                        text : '众签签章合同生成',
                        hidden:this.isHiddenHT,
                        xtype : 'button',
                        scope : this,
                        handler:this.createContractByZQ
                    }]
				})
	var url="";
	if(this.object.getCmpByName('platFormBpFundProject.id')){
	
	}
	//alert(this.object.getCmpByName('platFormBpFundProject.id').getValue());
	if(this.bidPlanId!=null&&""!=this.bidPlanId&&typeof(this.bidPlanId)!="undefined"){
		url =  __ctxPath + "/customer/listByMoneyPlanIdInvestPersonInfo.do?Q_bidPlanId_L_EQ="+this.bidPlanId;
	}else if(this.object.getCmpByName('platFormBpFundProject.id')!=null &&null!=this.object.getCmpByName('platFormBpFundProject.id').getValue()&&""!=this.object.getCmpByName('platFormBpFundProject.id').getValue()){
	       //url=__ctxPath+ '/creditFlow/common/getShareequity.do?enterpriseId='+ this.enId;
		url =  __ctxPath + "/customer/listByMoneyPlanIdInvestPersonInfo.do?Q_moneyPlanId_L_EQ = "+this.object.getCmpByName('platFormBpFundProject.id').getValue();
	}else if(this.projectId==null && this.bussinessType==null && this.enId!=null){
	       //url=__ctxPath+ '/creditFlow/common/getShareequity.do?enterpriseId='+ this.enId;
		url =  __ctxPath + "/customer/listByProjectIdInvestPersonInfo.do?projectId="+this.projectId+"&businessType="+this.bussinessType;
	}		
	else{
		url =  __ctxPath + "/customer/listByProjectIdInvestPersonInfo.do?projectId="+this.projectId+"&businessType="+this.bussinessType;
	       //url=__ctxPath+ '/creditFlow/common/getShareequity.do?projectId='+ this.projectId+ "&enterpriseId="+ this.enId+"&bussinessType="+this.bussinessType;
	}
	
	
	this.grid_sharteequity = new HT.EditorGridPanel(
			{
				border : false,
				id : 'grid_sharteequity',
				region:'center',
				showPaging:false,
				tbar :(this.isHiddenHT==true?null:this.sharteequitybar) ,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				isautoLoad:false,
				plugins : [summary],
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store(
						{
							//autoLoad:false,
							proxy : new Ext.data.HttpProxy(
									{
										url : url,// __ctxPath + "/customer/listInvestPersonInfo.do?projectId="+this.projectId+"&businessType="+this.businessType,
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [
									{
										name : 'investId'
									},
									{
										name: 'investTime'
									},
									{
										name : 'investPersonId'
									},
									{
										name : 'investPersonName'
									},
									{
										name : 'investMoney'
									},
									{
										name : 'investPercent'
									},{
										name : 'remark'
									},{
										name : 'fundResource'
									},{
										name : 'orderNo'
									},{
										name : 'contractUrls'
									},{
										name : 'bidPlanId'
									},{
										name : 'couponsValue'
									},{
										name : 'validMoney'
									},{
										name : 'couponsType'
									},{
										name : 'investTime'
									}
							]),
							root : 'result'
						})
			}),
				columns : [{
						header : 'investId',
						dataIndex : 'investId',
						hidden : true
					},{
						header:'资金来源',
						dataIndex:'fundResource',
						sortable : true,
						width : 100,
						scope : this,
						editor:{
							xtype:'combo',
							id : 'type',
							mode : 'local',
						    displayField : 'name',
						    valueField : 'value',
						    width : 70,
						    readOnly:this.isHidden,
						    store : new Ext.data.SimpleStore({
									fields : ["name", "value"],
									data : [["个人", 0]]
							}),
							triggerAction : "all",
							listeners : {
								scope:this,
								'select' : function(combox,record,index){
									var grid_sharteequity=this.grid_sharteequity;
									var r=combox.getValue();
									var personCom = new Ext.form.ComboBox({
										triggerClass : 'x-form-search-trigger',
										resizable : true,
										onTriggerClick : function() {
											/*if(r=='0'){
												selectInvestPerson(selectInvestEnterpriseObj,r);
											}else{
												selectInvestEnterPrise(selectInvestEnterpriseObj,r,true);
											}*/
											selectInvestmentPerson(selectInvestEnterpriseObj,r);
										},
										mode : 'remote',
										editable : false,
										lazyInit : false,
										allowBlank : false,
										typeAhead : true,
										minChars : 1,
										width : 100,
										listWidth : 150,
										store : new Ext.data.JsonStore({}),
										triggerAction : 'all',
										listeners : {
											'select' : function(combo,record,index) {
												grid_sharteequity.getView().refresh();
											}
										}
									})
									var ComboBox = new Ext.grid.GridEditor(personCom);
									grid_sharteequity.getColumnModel().setEditor(4,ComboBox);
								}
							}
						},
						renderer : function(value) {
							return "个人";
						}
					},{
						header : '投资日期',
						dataIndex : 'investTime',
						sortable : true,
						width : 100,
						scope : this
					},{
						header : '投资方',
						dataIndex : 'investPersonName',
						readOnly:this.isHidden,
						sortable : true,
						width : 100,
						scope : this,
						summaryType : 'count',
						summaryRenderer : totalMoney
					},/*{
						header : '投资人',
						dataIndex : 'investPersonName',
						sortable : true,
						width : 100,
						scope : this,
						summaryType : 'count',
						summaryRenderer : totalMoney,
						editor : new Ext.form.ComboBox({
							triggerClass : 'x-form-search-trigger',
							resizable : true,
							onTriggerClick : function() {
								selectInvestPerson(selectPersonObj);
							},
							mode : 'remote',
							editable : false,
							lazyInit : false,
							allowBlank : false,
							typeAhead : true,
							minChars : 1,
							width : 100,
							listWidth : 150,
							readOnly:this.isHidden,
							store : new Ext.data.JsonStore({}),
							triggerAction : 'all',
							listeners : {
								'select' : function(combo,record,index) {
									grid.getView().refresh();
								},
								'blur' : function(f) {
								}
							}
						})
					},{
						header : '投资份额（份）',
						dataIndex : 'investQuotient',
						align : 'right',
						width : 127,
						summaryType: 'sum',
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						}
					},*/{
						header : '投资金额',
						dataIndex : 'investMoney',						
						sortable : true,
						align : 'right',
						width : 100,
						summaryType : 'sum',
						editor : {
							xtype : 'numberfield',
							readOnly:this.isHidden,
							allowBlank:false,
							style: {imeMode:'disabled'}
						},
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
							if(rowIndex && value>me.unAdvanceMoney && 0!=me.unAdvanceMoney && record.data.fundResource==0){
								Ext.ux.Toast.msg('操作信息','已超出未投资金额，请重新填写!');
								return Ext.util.Format.number(0,'0,000.00')+"元"	;
							}else{
								return Ext.util.Format.number(value,'0,000.00')+"元"	;
							}
						}
					},{
						header : '投资时间',
						dataIndex : 'remark',
						readOnly:this.isHidden,
						sortable : true,
						width : 100,
						scope : this
					},{
						header : '优惠券面值',
						dataIndex : 'couponsValue',
						readOnly:this.isHidden,
						align : 'right',
						sortable : true,
						width : 100,
						scope : this/*,
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
							if(value>0){
								return Ext.util.Format.number(value,'0,000.00')+"元"	;
							}
						}*/
					},{
						header : '有效面值',
						dataIndex : 'validMoney',
						readOnly:this.isHidden,
						align : 'right',
						sortable : true,
						width : 100,
						scope : this
					},{
						header : '优惠券类型',
						dataIndex : 'couponsType',
						readOnly:this.isHidden,
						align : 'right',
						sortable : true,
						width : 100,
						scope : this
					},{
						header : '投资比例',
						fixed : true,
						width : 100,
						align : 'right',
						summaryType : 'sum',
						dataIndex : 'investPercent',
						sortable : true,
						scope:this,
						editor : {
							xtype : 'numberfield',
							allowBlank:false,
							readOnly:this.isHidden,
							style: {imeMode:'disabled'}
						},
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
							//处理已过期的标
							var bidMoneyText=me.getOriginalContainer().getCmpByName('bidMoney1');
							if(bidMoneyText){
								var projectMoney=Number(bidMoneyText.hiddenField.value);
								if(projectMoney!=null && projectMoney!=''&& investMoney!=null && investMoney!=''){
									value = 100*investMoney/projectMoney;
								}
								return Ext.util.Format.number(value,'0,000.00')+"%";
							}

							//--------change by zcb 2013-09-18
							//资金比例
							var win=me.object;
							var investMoney = record.data['investMoney'];
							
							if(typeof(win)!="undefined"){
								if(win.getCmpByName("ownBpFundProjectMoney")){
									var projectMoney = Ext.util.Format.number(win.getCmpByName('ownBpFundProjectMoney').value,'0,000.00');
									if(projectMoney!=null && projectMoney!=''&& investMoney!=null && investMoney!=''){
										value = 100*investMoney/projectMoney;
									}
									return Ext.util.Format.number(value,'0,000.00')+"%";
								}else if(win.getCmpByName("platFormBpFundProjectMoney")){
									var projectMoney = Ext.util.Format.number(win.getCmpByName('platFormBpFundProjectMoney').value,'0,000.00');
									if(projectMoney!=null && projectMoney!=''&& investMoney!=null && investMoney!=''){
										value = 100*investMoney/projectMoney;
									}
									return Ext.util.Format.number(value,'0,000.00')+"%";
								}
							}else{
								return Ext.util.Format.number(value,'0,000.00')+"%";
							}	
						}
					}/*, {
						header : '备注',
						dataIndex : 'remark',
						sortable : true,
						align : "center",
						width : 100,
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					}*/,{
						header : '款项计划表',
						dataIndex : 'remark',
						sortable : true,
						align : "center",
						width : 100,
					
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			           		return '<a title="款项计划表" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="seeIntent(\''
										+ record.get('orderNo') +'\',\''+record.get('investPersonName')+ '\',\''+record.get('bidPlanId')+'\')" >款项计划表</a>';// 个人款项台账
			                    }
					},{
						header : '合同下载',
						dataIndex : 'remark',
						sortable : true,
						align : "center",
						width : 100,
					
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                   	if (record.get('contractUrls')==null || record.get('contractUrls')=='') {
			                    	return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer">0</a>';// 个人贷款合同
			                    		
			                    } else {
			           		           return '<a title="合同下载" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downContracts(\''
										+ record.get('investId') + '\')" >合同下载</a>';// 个人贷款合同
			                    }
			                 }

					// }

					}]
              });
              	var grid=this.grid_sharteequity;
              	var selectPersonObj=function(obj){
					  grid.getSelectionModel().getSelected().data['investPersonId'] = obj.perId;
					  grid.getSelectionModel().getSelected().data['investPersonName'] = obj.perName;
					  grid.getView().refresh();
				}
				var selectFundObj=function(obj){
					grid.getSelectionModel().getSelected().data['investPersonId'] = obj.id;
					grid.getSelectionModel().getSelected().data['investPersonName'] = obj.fundName;
					me.unAdvanceMoney= obj.unmappingMoney;
					grid.getView().refresh();
				}
				var selectInvestEnterpriseObj=function(obj){
					grid.getSelectionModel().getSelected().data['investPersonId'] = obj.investId;
					grid.getSelectionModel().getSelected().data['investPersonName'] = obj.investName;
					grid.getView().refresh();
				}
				if (null != this.projectId) {
					this.grid_sharteequity.getStore().load();
				}
				/*else if(null!=this.enId){
				    this.grid_sharteequity.getStore().load();
				}*/
     },
     createShareequity : function(){
    	 var gridadd = this.grid_sharteequity;
			var storeadd = this.grid_sharteequity.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = null;
				p.data[keys[2]] = '';
				p.data[keys[3]] = 0.00;
				p.data[keys[4]] = 0;
				p.data[keys[5]] = '';
				//p.data[keys[8]]='';
			}
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     },
     deleteShareequity : function(){
			var griddel = this.grid_sharteequity;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s.length <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {
					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.investId == null || row.data.investId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/customer/multiDelInvestPersonInfo.do',
										{
											ids :row.data.investId
										},
										function() {
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
						}
					}
				})
     },
     seeShareequity : function(){
			var s = this.grid_sharteequity.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{
					var record=s[0];
					if(record.data.fundResource==1){//企业
						new InvestEnterpriseForm( {
							id : record.data.investPersonId,
							isAllReadOnly : true,
							gridpanel : this.grid_sharteequity
						}).show();
					}else{
						new InvestPersonForm({
							perId : record.data.investPersonId,
							isLook:true
						}).show();
					}
				}
     },
     save : function() {
		 //投资人信息
        var investDatas = "";
        investDatas = getGridDate1(this.grid_sharteequity);
        var id= null;
        if(this.object.getCmpByName('ownBpFundProject.id')){
        	id = this.object.getCmpByName('ownBpFundProject.id').value;
        }else if(this.object.getCmpByName('platFormBpFundProject.id')){
        	id = this.object.getCmpByName('platFormBpFundProject.id').value;
        }else{
        	return;
        }
        
		Ext.Ajax.request({
			scope : this,
//			url : __ctxPath + '/project/saveSlSmallloanProject.do',
			url : __ctxPath + '/fund/saveBpFundProject.do',
			params : {'investInfo':investDatas,'projectId' : this.projectId,'id' :id,bidPlanId:this.bidPlanId},
			method : 'post',
			callback : function(fp, action) {
				this.grid_sharteequity.getStore().reload();
			}
		});
				
	},
	createContract:function(){
		new OperateKxcsContractWindow({
			htType:this.htType,
			fundProjectId:this.fundProjectId,
			businessType:this.businessType,
			projectId:this.projectId,
			bidPlanId:this.bidPlanId,
			cpanel:this.grid_sharteequity,
			isHidden:true,
			isWDLoan:false
		}).show();
	},
    createContractByZQ:function () {
        new OperateKxcsContractWindow({
            htType:this.htType,
            fundProjectId:this.fundProjectId,
            businessType:this.businessType,
            projectId:this.projectId,
            bidPlanId:this.bidPlanId,
            cpanel:this.grid_sharteequity,
            ZqType:'ZQ',
            isHidden:true,
            isWDLoan:false
        }).show();

    }
			
});
