//列表中添加   元单位
var yuanRenderer = function(v){
	if(v&&v>0){
		return v + "元" ;
	}else if(v==null||v=='undefined'||v==0){
		return '';
	}else{
		return '<font color=red>'+v+'</font>元' ;
	}
};
//列表中添加   万元单位
var wanyuanRenderer = function(v){
	if(v&&v!=''&&v>0){
		return v + "万元" ;
	}else{
		return v ;
	}
};
//列表中添加   百分符号
var percentRenderer = function(v){
	if(v&&v>0){
		return v + "%" ;
	}else{
		return '<font color=red>'+v+'</font>%' ;
	}
};
//列表中添加   文件数量单位  份
var fenRenderer = function(v){
	if(v&&v>0){
		return v + "份" ;
	}else{
		return '<font color=red>'+v+'</font>份' ;
	}
};
//列表右键删除的时候,通用函数
var globalGridDeleteRecord = function(grid,row,event,action){
	var record = grid.getStore().getAt(row) ;
	var id = record.get('id') ;
	Ext.Msg.confirm('删除确认','确认删除选中记录?',function(btn){
		if(btn=='yes'){
			Ext.Ajax.request({
				url : action,
				method : 'POST',
				timeout : 60000,
				success : function(response, request) {
					handleAjaxRequest(response, request) ;
					grid.getStore().removeAll() ;
					grid.getStore().reload();
				},
				failure : function(response, request) {
					handleAjaxRequest(response, request) ;
					grid.getStore().removeAll() ;
					grid.getStore().reload();
				},
				params: { id: id }
			});
		}
	}) ;
};

//抵质押物-反担保：企业、个人、我方企业主体、我方个人主体store
var getStoreByBusinessType = function(businessType,customerType){
	var store;
	if(businessType=='Financing'){
		if(customerType=='enterprise'){
			store = new Ext.data.JsonStore({
				url : __ctxPath + "/creditFlow/ourmain/queryListForComboSlCompanyMain.do",
				root : 'topics',
				autoLoad : true,
				fields : [{
							name : 'companyMainId'
						}, {
							name : 'corName'
						}, {
							name : 'simpleName'
						}/*, {
							name : 'messageAddress'//通讯地址
						}, {
							name : 'businessCode'//营业执照号码
						}, {
							name : 'personMainId'//法人id
						}, {
							name : 'lawName'//法人姓名
						}*/],
				listeners : {
					'load' : function(s,r,o){
						if(s.getCount()==0){
							Ext.getCmp('enterpriseNameMortgage').markInvalid('没有查找到匹配的记录') ;
						}
					}
				}
			});
		}else{
			store = new Ext.data.JsonStore({
				url : __ctxPath + "/creditFlow/ourmain/queryListForComboSlPersonMain.do",
				root : 'topics',
				autoLoad : true,
				fields : [{
							name : 'personMainId'
						}, {
							name : 'name'
						}, {
							name : 'cardtype'
						}, {
							name : 'cardtypevalue'
						}, {
							name : 'cardnum'
						}, {
							name : 'linktel'
						}],
				listeners : {
					'load' : function(s,r,o){
						if(s.getCount()==0){
							Ext.getCmp('personNameMortgage').markInvalid('没有查找到匹配的记录') ;
						}
					}
				}
			});
		}
	}else{
		if(customerType=='enterprise'){
			store = new Ext.data.JsonStore({
				url : __ctxPath +'/creditFlow/customer/enterprise/ajaxQueryForComboEnterprise.do',
				root : 'topics',
				autoLoad : true,
				fields : [{
							name : 'id'
						}, {
							name : 'enterprisename'
						}, {
							name : 'shortname'
						}, {
							name : 'address'//地址
						}, {
							name : 'cciaa'//营业执照号码
						}, {
							name : 'legalpersonid'//法人id
						}, {
							name : 'legalperson'//法人姓名
						}],
				listeners : {
					'load' : function(s,r,o){
						if(s.getCount()==0){
							Ext.getCmp('enterpriseNameMortgage').markInvalid('没有查找到匹配的记录') ;
						}
					}
				}
			});
		}else{
			store = new Ext.data.JsonStore({
				url : __ctxPath +'/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
				root : 'topics',
				autoLoad : true,
				fields : [{
							name : 'id'
						}, {
							name : 'name'
						}, {
							name : 'cardtype'
						}, {
							name : 'cardtypevalue'
						}, {
							name : 'cardnumber'
						}, {
							name : 'cellphone'
						}],
				listeners : {
					'load' : function(s,r,o){
						if(s.getCount()==0){
							Ext.getCmp('personNameMortgage').markInvalid('没有查找到匹配的记录') ;
						}
					}
				}
			});
		}
	}
	return store;
};

//反担保、抵质押物：股权-目标公司,与所有权人参照类似。id不一致,避免冲突与在余下其他js多加参数,所以单独增加此方法。
var getStoreByBusinessTypeStock = function(businessType){
	var store;
	if(businessType=='Financing'){
		store = new Ext.data.JsonStore({
			url : __ctxPath + "/creditFlow/ourmain/queryListForComboSlCompanyMain.do",
			root : 'topics',
			autoLoad : true,
			fields : [{
						name : 'companyMainId'
					}, {
						name : 'corName'
					}, {
						name : 'simpleName'
					}, {
						name : 'messageAddress'//通讯地址
					}, {
						name : 'businessCode'//营业只好号码
					}, {
						name : 'personMainId'//法人id
					}, {
						name : 'lawName'//法人姓名
					}],
			listeners : {
				'load' : function(s,r,o){
					if(s.getCount()==0){
						Ext.getCmp('targetEnterpriseName').markInvalid('没有查找到匹配的记录') ;
					}
				}
			}
		});
	}else{
		store = new Ext.data.JsonStore({
			url : __ctxPath +'/creditFlow/customer/enterprise/ajaxQueryForComboEnterprise.do',
			root : 'topics',
			autoLoad : true,
			fields : [{
						name : 'id'
					}, {
						name : 'enterprisename'
					}, {
						name : 'shortname'
					}, {
						name : 'address'//地址
					}, {
						name : 'cciaa'//营业执照号码
					}, {
						name : 'legalpersonid'//法人id
					}, {
						name : 'legalperson'//法人姓名
					}],
			listeners : {
				'load' : function(s,r,o){
					if(s.getCount()==0){
						Ext.getCmp('targetEnterpriseName').markInvalid('没有查找到匹配的记录') ;
					}
				}
			}
		});
	}
	return store;
};