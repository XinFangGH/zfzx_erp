 

//Ext.BLANK_IMAGE_URL = '/creditBusiness1.0/ext/resources/images/default/s.gif';
Ext.BLANK_IMAGE_URL = basepath()+'ext/resources/images/default/s.gif';
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'qtip';
document.oncontextmenu=function(){   
  event.returnValue=false;   
};

/*jiang*/
function basepath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return prePath+postPath+"/";
}
//////////////////////////////////////////////////////////

var VTYPES = {
	intOrDeci : {
		reg : /^\d+(\.\d+)?$/,
		msg : '只能输入正整数或小数'
	},
	int10OrDeci : {
		reg : /^\d{1,10}(\.\d+)?$/,
		msg : '请输入正整数或小数(整数位限10位)！'
	},
	email : {
		reg : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
		msg : '请输入正确的Email地址！'
	},
	idCard : {
		reg : /^\d{15}(\d{2}[A-Za-z0-9])?$/,
		msg : '请输入正确的身份证号码！'
	},
	pwd : {//校验密码：只能输入6-20个字母、数字、下划线
		reg : /^(\w){6,20}$/,
		msg : '只能输入6-20个字母、数字、下划线'
	},
	phone : {//校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”   
		reg : /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
		msg : '不符合电话，传真号码格式'
	},
	mobile : {//校验手机号码：必须以数字开头，除数字外，可含有“-”
		reg : /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/,
		msg : '不符合手机号码格式'
	},
	num : {  //校验全数字型数据
		reg : /^\d{2,2}$/,
		msg : '必须是2位数字'
	},
	//searchKey : {//效验查询条件
		//reg : /^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,19}$/,
		//msg : '不允许非法字符'
	//},
	chinese :  {
		reg : /^[\u0391-\uFFE5]+$/,
		msg : '不符合条件,只能输入中文'
	},
	noChinese : {
		reg : /^(?:[^\u4e00-\u9fa5])+$/,
		msg : '不能输入中文'
	},
	postcode : {
		reg : /^[1-9]\d{5}$/,
		msg : '不符合邮编格式'
	},
	url : {
		reg : /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
		msg : '不符合网址格式'
	},
	Number : /^\d+$/,
	qq : {
		reg : /^[1-9]\d{4,8}$/,
		msg : '不符合qq号码格式'
	},
	integer : {
		reg : /^[-\+]?\d+$/,
		msg : '必须是整数'
	},
	integerGt1 :{
		reg : /^[-\+]?\d+$/,//等待修改
		msg : '必须是大于1的整数'
	},
	doubl : {
		reg : /^[-\+]?\d+(\.\d+)?$/,
		msg : '不是浮点数'
	},
	english : {
		reg : /^[A-Za-z]+$/,
		msg : '不符合条件,只能输入英文字母'
	},
	username : {
		reg : /^[a-z]\w{3,}$/i,
		msg : '不符合条件,3个字母以上'
	}
} ;

Ext.data.Store.prototype.applySort = function(){
         //重载 applySort   
        if(this.sortInfo && !this.remoteSort){   
                var s = this.sortInfo, f = s.field;   
                var st = this.fields.get(f).sortType;   
                var fn = function(r1, r2){   
                        var v1 = st(r1.data[f]), v2 = st(r2.data[f]);   
                        // 添加:修复汉字排序异常的Bug   
                        if(typeof(v1) == "string"){ //若为字符串，   
                                return v1.localeCompare(v2);   
                                //则用 localeCompare 比较汉字字符串, Firefox 与 IE 均支持   
                        }   
                        // 添加结束   
                        return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);   
                };   
                this.data.sort(s.direction, fn);   
                if(this.snapshot && this.snapshot != this.data){   
                        this.snapshot.sort(s.direction, fn);   
                }   
        }   
}; 

//**************************************列表的可折叠插件         结束**********************************************//

/********************************列表的可编辑checkbox插件  开始****************************************************/
Ext.grid.CheckColumn = function(config) {
		Ext.apply(this, config);
		if (!this.id) {
			this.id = Ext.id();
		}
		this.renderer = this.renderer.createDelegate(this);
	};

	Ext.grid.CheckColumn.prototype = {
		init : function(grid) {
			this.grid = grid;
			this.grid.on('render', function() {
				var view = this.grid.getView();
				view.mainBody.on('mousedown', this.onMouseDown, this);
			}, this);
		},
	
		onMouseDown : function(e, t) {
			if (t.className && t.className.indexOf('x-grid3-cc-' + this.id) != -1 && this.editable!==false) {
				e.stopEvent();
				var index = this.grid.getView().findRowIndex(t);
				var cindex = this.grid.getView().findCellIndex(t);
				var record = this.grid.store.getAt(index);
				var field = this.grid.colModel.getDataIndex(cindex);
				var e = {
					grid : this.grid,
					record : record,
					field : field,
					originalValue : record.data[this.dataIndex],
					value : !record.data[this.dataIndex],
					row : index,
					column : cindex,
					cancel : false
				};
				if (this.grid.fireEvent("validateedit", e) !== false && !e.cancel) {
					delete e.cancel;
					record.set(this.dataIndex, !record.data[this.dataIndex]);
					this.grid.fireEvent("afteredit", e);
				}
			}
		},
	
		renderer : function(v, p, record) {
			p.css += ' x-grid3-check-col-td';
			return '<div class="x-grid3-check-col' + (v ? '-on' : '') + ' x-grid3-cc-' + this.id + '">&#160;</div>';
		}
	};
/********************************************列表的可编辑checkbox插件 结束********************************************/
//**************************************可折叠插件   列表         开始**********************************************//
Ext.namespace('CS.grid') ;
CS.grid.CheckHistoryGridPanel = function(config){
	var config = config || {};
	var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.XTemplate(
            '<p><b>审批部门:</b> {apActivityName}</p><br>',
            '<tpl if="apUserId == -1">',
            	'<p><b>审批人:</b> 审保会会议</p><br>',
            '</tpl>',
            '<tpl if="apUserId != -1">',
            	'<p><b>审批人:</b> {name}</p><br>',
            '</tpl>',
            '<p><b>审批人职务:</b> {positionname}</p><br>',
//            '<p><b>审批人:</b> {name}</p><br>',
            '<p><b>审批意见:</b> {apOpinion}</p><br>',
            '<p><b>审批时间:</b> {apDateTime}</p>'
        )
    });
    var jStore_checkHistory = new Ext.data.JsonStore({
		url : 'ajaxGetProcreditApprovalList.do',//----
//		totalProperty : 'totalProperty',
		root : 'topics',
		autoLoad : true ,
		fields : [{
					name : 'id'
				}, {
					name : 'apProjId'
				}, {
					name : 'apTaskId'
				}, {
					name : 'apActivityName'
				}, {
					name : 'apUserId'
				}, {
					name : 'apResult'
				}, {
					name : 'apOpinion'
				}, {
					name : 'apDateTime'
				}, {
					name : 'apRemarks'
				}, {
					name : 'name'
				},{
					name : 'resultName'
				},{
					name : 'positionname'
				}],
		baseParams : {
			projId : config.projId
		}
	});
	var cModel_checkHistory = new Ext.grid.ColumnModel([expander,
		{
			header : '职务',
			sortable : true,
			dataIndex : 'positionname'
		},{
			header : '节点名称',
			sortable : true,
			dataIndex : 'apActivityName'
		},{
			header : '审批人',
			sortable : true,
			dataIndex : 'name',
			renderer : function(v,m,r){
				if(r.get('apUserId')==-1){
					return '审保会会议' ;
				}
				return v ;
			}
		},{
			header:'审批结果',
			sorable:true,
			dataIndex : 'resultName'
		}, {
			id : 'autoExpand',
			header : "审批意见",
			width : 260,
			sortable : true,
			dataIndex : 'apOpinion'
		},{
			header : '审批日期',
			sortable : true,
			width : 160,
			dataIndex : 'apDateTime'
		}]);
	Ext.applyIf(config, {
//		autoHeight : true,
		store : jStore_checkHistory,
		colModel : cModel_checkHistory,
		autoExpandColumn : 'autoExpand',
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		plugins: expander
	});
	CS.grid.CheckHistoryGridPanel.superclass.constructor.call(this, config);
};
Ext.extend(CS.grid.CheckHistoryGridPanel,Ext.grid.GridPanel);
Ext.reg('checkHistoryGridPanel',CS.grid.CheckHistoryGridPanel) ;
//**************************************可折叠插件   列表         结束**********************************************//

/**
 * js比较两个日期
 * @param {} DateOne
 * @param {} DateTwo
 * @return {}
 */
function daysBetween(DateOne,DateTwo)  
{   
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  
  
    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  
  
    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
    return cha;  
}

Ext.namespace('CS') ;
CS.PagingToolbar = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		pageSize : 15,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : '没有符合条件的记录······'
	});
	CS.PagingToolbar.superclass.constructor.call(this, config);
};
Ext.extend(CS.PagingToolbar,Ext.PagingToolbar);

CS.grid.GridPanel = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		}),//加载时候提示信息
		bbar : new CS.PagingToolbar({
			pageSize : config.pageSize,
			store : config.store
		})//底部分页条
	});
	CS.grid.GridPanel.superclass.constructor.call(this, config);
};
Ext.extend(CS.grid.GridPanel,Ext.grid.GridPanel);

CS.grid.EditorGridPanel = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		}),//加载时候提示信息
		bbar : new CS.PagingToolbar({
			pageSize : config.pageSize,
			store : config.store
		})//底部分页条
	});
	CS.grid.EditorGridPanel.superclass.constructor.call(this, config);
};
Ext.extend(CS.grid.EditorGridPanel,Ext.grid.EditorGridPanel);


Ext.namespace('CS.form') ;
/**************************远程加载数据字典的下拉选项****开始*******************************************/
CS.form.RemoteCombo = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		triggerAction : 'all',
		editable : false,
		lazyInit : false ,
		mode : 'romote',
		displayField : 'value',
		valueField : 'id',
		resizable : true,
		store : new Ext.data.JsonStore({
			url : __ctxPath+'/credit/customer/enterprise/queryDic.do',
			root : 'topics',
			autoLoad : true ,
			fields : [{
						name : 'id'
					}, {
						name : 'typeId'
					}, {
						name : 'sortorder'
					}, {
						name : 'value'
					}],
			baseParams :{id : config.dicId},
			listeners : {
				'load' : function(store,records,options){
					if(config.id&&config.selectFirst&&!Ext.getCmp(config.id).getRawValue()){
						if(records&&records[0]){
							Ext.getCmp(config.id).setValue(records[0].get('id')) ;
							Ext.getCmp(config.id).originalValue = records[0].get('id') ;
							Ext.getCmp(config.id).lastQuery = '' ;
						}
					}
				}
			}
		})
	});
	CS.form.RemoteCombo.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.RemoteCombo,Ext.form.ComboBox);
Ext.reg('csRemoteCombo', CS.form.RemoteCombo);
/**************************远程加载数据字典的下拉选项****结束*******************************************/

/**************************远程加载角色对应用户的下拉选项****开始*******************************************/
CS.form.RemoteRoleCombo = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		triggerAction : 'all',
		editable : false,
		lazyInit : false ,
		mode : 'romote',
		displayField : 'name',
		valueField : 'id',
		store : new Ext.data.JsonStore({
			url : 'queryRoleUser.do',
			root : 'topics',
//			autoLoad : true ,
			fields : [{
						name : 'id'
					}, {
						name : 'name'
					}],
			baseParams :{
				roleName : config.roleName,
				sameGroup : config.sameGroup//是否只选择本组的相关人员（暂时只在业务部存在组别）
			}
		})
	});
	CS.form.RemoteRoleCombo.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.RemoteRoleCombo,Ext.form.ComboBox);
Ext.reg('csRemoteRoleCombo', CS.form.RemoteRoleCombo);
/**************************远程加载角色对应用户的下拉选项****结束*******************************************/

//**************************************企业贷款   审批意见  fieldset  开始***************************************************/
CS.form.checkOperateFieldSet = function(config){
	var config = config || {};
	
//----------提示错误,开发时使用	
//	config.checkPerson = config.checkPerson || '<font color=red>请传入审核人姓名,如->checkPerson:"审核人"</font>' ;
	config.activityName = config.activityName || '<font color=red>请传入审核节点,如->activityName:"业务主管审核"</font>' ;
	config.title = config.fieldSetTitle || '<font color=red>请传入标题,如->fieldSetTitle:"title"</font>' ;
		//	config.checkSuggest = config.checkSuggest || ;
	dicId = config.dicId || 278 ;
	config.layout = 'form' ;
	Ext.applyIf(config, {
		collapsible: true,
		anchor: '97%',
		autoHeight:true,
		border : true,
		title : config.fieldSetTitle,
    	layout : 'form',
    	items : [{
				xtype : 'csRemoteCombo',
				fieldLabel:'审批结论',
				hiddenName : config.checkResult,
				anchor : '50% 100%',
				allowBlank : false,
	            blankText : '必填项',
				dicId : dicId,
				listeners : {
					'select' : function(combo,record,index){
						 if(record.get('id')=='1266'){
						 	if(Ext.getCmp('advicemeeting')){
						 		Ext.getCmp('advicemeeting').setVisible(true) ;
						 	}
						 }else{
						 	if(Ext.getCmp('advicemeeting')){
						 		Ext.getCmp('advicemeeting').setVisible(false) ;
						 	}
						 }
						/* if(record.get('id')=='1269'){//快速流程-总裁审批节点：根据选择的选项显示或隐藏指定风险经理以及传递下拉列表的id
						 	if(Ext.getCmp('riskManageFiledSet')){
						 		Ext.getCmp('riskManageINode9Ceo').setValue(record.get('id'));
						 		Ext.getCmp('riskManageFiledSet').setVisible(true);
						 	}
						 }else{
						 	if(Ext.getCmp('riskManageFiledSet')){
						 		Ext.getCmp('riskManageFiledSet').setVisible(false) ;
						 	}
						 }*/
					}
				}
    		},{
    			xtype : 'textarea',
    			fieldLabel : '审批意见',
    			name : config.checkSuggest,
    			allowBlank : false,
	            blankText : '必填项',
    			anchor : '99%'
    		}]
	});
	CS.form.checkOperateFieldSet.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.checkOperateFieldSet,Ext.form.FieldSet);
Ext.reg('checkOperateFieldSet',CS.form.checkOperateFieldSet);
//**************************************企业贷款   审批意见  fieldset  结束**************************************************/
/********************************************审核历史 fieldset**开始**************************************************/
CS.form.CheckHistoryFieldSet = function(config){
	var config = config || {};
	var grid = new CS.grid.CheckHistoryGridPanel({projId:config.projId,autoHeight:true}) ;
	Ext.applyIf(config, {
		title : '审批信息',
        layout : 'fit',
        items:[grid]
	});
	CS.form.CheckHistoryFieldSet.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.CheckHistoryFieldSet,Ext.form.FieldSet);
Ext.reg('checkHistoryFieldSet',CS.form.CheckHistoryFieldSet);
/*****************************************审核历史 fieldset 结束************************************************/

/**#########################企业贷款   项目基本信息  fieldset  开始######################################*/
CS.form.EnterpriseBaseFieldSet = function(config){
	var config = config || {};
	var textfieldAnchor = '100%' ;
	
	Ext.applyIf(config, {
		layout : 'column',
		collapsible: true,
		anchor: config.anchor || '97%',
		autoHeight:true,
		border : true,
        title : '项目基本信息',
        style : 'padding-top:5px;',
        labelWidth:config.labelWidth||100,
        defaults : {
        	columnWidth : 0.5,
        	layout : 'form'
        },
        items:[{
        	columnWidth:0.65,
        	items : [{
        		xtype : 'textfield',
        		fieldLabel : '企业名称',
        		readOnly : true,
        		anchor : textfieldAnchor,
        		value : config.enterpriseName
        	}]
        },{
        	columnWidth:0.35,
        	items : [{
        		xtype:'textfield',
        		readOnly : true,
	        	anchor:textfieldAnchor,
	            fieldLabel:'企业简称',
	        	value:config.enterpriseShortName
          	}]
        },{
        	columnWidth:1,
        	items : [{
          		xtype : 'textfield',
          		readOnly : true,
	        	anchor:textfieldAnchor,
	            fieldLabel:'行业类别',
	            value:config.hangyetypevalue
	          }]
        },{
        	defaults : {
        		xtype:'textfield',
        		readOnly : true,
	        	anchor:textfieldAnchor
	        },
        	items:[{
				fieldLabel:'项目编号',
				value : config.projNum
			  },{
				fieldLabel:'项目类别',
				value : config.projectTypeName			  
			  }]
         },{
        	defaults : {
	        	anchor:textfieldAnchor,
        		readOnly : true,
	        	xtype:'textfield'
	        },
        	items:[{
				fieldLabel:'业务品种',
				value : config.servicetype
     		},{
     			fieldLabel:'项目阶段',
				value : config.projphase
     		}]
         },{
         	columnWidth : 1,
         	items : [{
		      	xtype : 'textarea',
	            fieldLabel:'项目说明',
	            name:'remark',
	            readOnly : true,
	            anchor : textfieldAnchor,
	            value : config.remark
      		}]
         }]
      });
      CS.form.EnterpriseBaseFieldSet.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.EnterpriseBaseFieldSet,Ext.form.FieldSet);
Ext.reg('enterpriseBaseFieldSet',CS.form.EnterpriseBaseFieldSet) ;
// add by chencc
CS.form.EnterpriseBaseFieldSetPerson = function(config){
	var config = config || {};
	var textfieldAnchor = '100%' ;
	
	Ext.applyIf(config, {
		layout : 'column',
		collapsible: true,
		anchor: config.anchor || '97%',
		autoHeight:true,
		border : true,
        title : '项目基本信息',
        style : 'padding-top:5px;',
        labelWidth:config.labelWidth||100,
        defaults : {
        	columnWidth : 0.5,
        	layout : 'form'
        },
        items:[{
        	columnWidth:0.65,
        	items : [{
        		xtype : 'textfield',
        		fieldLabel : '项目名称',
        		readOnly : true,
        		anchor : textfieldAnchor,
        		value : config.projName
        	}]
        },{
        	columnWidth:0.35,
        	items : [{
        		xtype:'textfield',
        		readOnly : true,
	        	anchor:textfieldAnchor,
	            fieldLabel:'项目编号',
	        	value:config.projNum
          	}]
        },{
        	columnWidth:1,
        	items : [{
          		xtype : 'textfield',
          		readOnly : true,
	        	anchor:textfieldAnchor,
	            fieldLabel:'项目来源',
	            value:config.projfrom
	          }]
        },{
        	defaults : {
        		xtype:'textfield',
        		readOnly : true,
	        	anchor:textfieldAnchor
	        },
        	items:[{
				fieldLabel:'项目类别',
				value : config.projectTypeName			  
			  },{
     			fieldLabel:'借款期限(月)',
				value : config.creditterm
     		}]
         },{
        	defaults : {
	        	anchor:textfieldAnchor,
        		readOnly : true,
	        	xtype:'textfield'
	        },
        	items:[{
				fieldLabel:'借款金额(万)',
				value : config.creditmoney
     		}]
         }]
      });
      CS.form.EnterpriseBaseFieldSetPerson.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.EnterpriseBaseFieldSetPerson,Ext.form.FieldSet);
Ext.reg('enterpriseBaseFieldSetPerson',CS.form.EnterpriseBaseFieldSetPerson) ;

/**#########################企业贷款   项目基本信息  fieldset  结束######################################*/
//############################################################################################################//
CS.form.EnterpriseBaseFieldSetForCheck = function(config){
	var config = config || {};
	var fullWidthAnchor = '100%' ;
	
	Ext.applyIf(config, {
        title : '项目基本信息',
        style : 'padding-top:5px;',
        labelWidth:80,
        defaults : {
        	columnWidth : 0.5,
        	layout : 'form',
        	defaults : {
	        	anchor:fullWidthAnchor,
        		readOnly : true,
	        	xtype:'textfield'
	        }
        },
        items:[{
        	columnWidth : 1,
        	items : [{
	            fieldLabel:'项目名称',
	            value : config.projName
	          },{
				fieldLabel:'客户名称',
				value : config.enterpriseName
			  }]
        },{
        	items:[{
				fieldLabel:'业务品种',
				value : config.servicetype
	          },{
				fieldLabel:'贷款金额(万)',
				value : config.creditmoney
     		},{
				fieldLabel:'项目类别',
				value : config.projectTypeName
     		}]
         },{
        	items:[{
	            fieldLabel:'项目编号',
	            value : config.projNum
			  },{
	            fieldLabel:'贷款期限(月)',
	            value : config.creditterm
     		},{
     			fieldLabel:'项目阶段',
				value : config.projphase
     		}]
         },{
         	columnWidth : 1,
         	items : [{
		      	xtype : 'textarea',
	            fieldLabel:'项目说明',
	            name:'remark',
	            value : config.remark
      		}]
         }]
      });
      CS.form.EnterpriseBaseFieldSetForCheck.superclass.constructor.call(this, config);
};

Ext.extend(CS.form.EnterpriseBaseFieldSetForCheck,Ext.form.FieldSet);
Ext.reg('enterpriseBaseFieldSetForCheck',CS.form.EnterpriseBaseFieldSetForCheck) ;

//个人经营性贷款 项目基本信息 add by chencc
CS.form.EnterpriseBaseFieldSetForCheckPerson = function(config){
	var config = config || {};
	var fullWidthAnchor = '100%' ;
	
	Ext.applyIf(config, {
        title : '项目基本信息',
        style : 'padding-top:5px;',
        labelWidth:100,
        defaults : {
        	columnWidth : 0.5,
        	layout : 'form',
        	defaults : {
	        	anchor:fullWidthAnchor,
        		readOnly : true,
	        	xtype:'textfield'
	        }
        },
        items:[{
        	columnWidth : 0.5,
        	items : [{
	            fieldLabel:'项目名称',
	            value : config.projName
	          },{
				fieldLabel:'项目来源',
				value : config.projfrom
	          },{
				fieldLabel:'借款金额(万元)',
				value : config.creditmoney
     		}]
        },{
        	items:[{
	            fieldLabel:'项目编号',
	            value : config.projNum
			  },{
	            fieldLabel:'项目类别',
	            value : config.projectTypeName
			  },{
	            fieldLabel:'借款期限(月)',
	            value : config.creditterm
     		}]
         }]
      });
      CS.form.EnterpriseBaseFieldSetForCheckPerson.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.EnterpriseBaseFieldSetForCheckPerson,Ext.form.FieldSet);
Ext.reg('enterpriseBaseFieldSetForCheckPerson',CS.form.EnterpriseBaseFieldSetForCheckPerson) ;

//############################################################################################################//
/**##################保中项目  基本信息   开始 ############################################################*/
CS.form.MiddleBaseInfoFieldSet = function(config){
	var config = config || {};
	var textfieldAnchor = '98%' ;
	var fullWidthAnchor = '99%' ;
	
	Ext.applyIf(config, {
		xtype : 'fieldset',
        title : '项目基本信息',
        style : 'padding-top:5px;',
        layout : 'column',
        defaults : {
        	columnWidth : 0.5,
        	layout : 'form'
        },
        items:[{
        	columnWidth : 1,
        	items : [{
        		xtype:'textfield',
        		readOnly : true,
        		anchor:fullWidthAnchor,
	            fieldLabel:'客户名称',
	            value : config.customerName
	          }]
        },{
        	items:[{
        		xtype:'textfield',
        		readOnly : true,
	        	anchor:textfieldAnchor,
				fieldLabel:'客户简称',
				value : config.customerShortName
	          }]
         },{
         	items:[{
         		xtype:'textfield',
        		readOnly : true,
	        	anchor:textfieldAnchor,
				fieldLabel:'项目编号',
				value : config.projNum
     		}]
         },{
        	columnWidth : 1,
        	items : [{
        		xtype:'textfield',
        		readOnly : true,
        		anchor:fullWidthAnchor,
	            fieldLabel:'贷款银行',
	            value : config.bankName
	          }]
         },{
        	columnWidth : 1,
        	items : [{
        		xtype:'textfield',
        		readOnly : true,
        		anchor:fullWidthAnchor,
	            fieldLabel:'贷款银行支行',
	            value : config.childBankName
	          }]
         },{
        	items:[{
        		xtype:'textfield',
        		readOnly : true,
	        	anchor:textfieldAnchor,
				fieldLabel:'保前项目经理',
				value : config.beforeItemManager
	          },{
	          	xtype: 'textfield',
	          	fieldLabel:'担保期限*月',
	          	readOnly : true,
        		anchor:textfieldAnchor,
				value : config.guaranteeTermMonth
	          }]
         },{
         	defaults : {
         		readOnly : true,
	        	anchor:textfieldAnchor
         	},
         	items:[{
         		xtype:'textfield',
				fieldLabel:'保前金额*万元',
	          	value : config.guaranteeMoney
     		},{
     			xtype : 'textfield',
     			fieldLabel : '合同截止日期',
     			value : config.guaranteeEndDate
     		}]
         }]
      });
      CS.form.MiddleBaseInfoFieldSet.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.MiddleBaseInfoFieldSet,Ext.form.FieldSet);
Ext.reg('middleBaseInfoFieldSet',CS.form.MiddleBaseInfoFieldSet) ;
/**############################################################################################################*/

/**CS.form.FormPanel*/
CS.form.FormPanel = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		labelAlign : 'right',
		buttonAlign : 'center',
		frame : true,
		border : true,
		autoScroll : true,
		monitorValid : true
	});
	CS.form.FormPanel.superclass.constructor.call(this, config);
};
Ext.extend(CS.form.FormPanel,Ext.form.FormPanel);



//CS.data命名空间
Ext.namespace('CS.data') ;
/**CS.data.JsonStore*/
CS.data.JsonStore = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		totalProperty : 'totalProperty',
		root : 'topics'
	});
	CS.data.JsonStore.superclass.constructor.call(this, config);
};
Ext.extend(CS.data.JsonStore,Ext.data.JsonStore);

CS.Window = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		collapsible : true ,
//		titleCollapse : true ,
		constrainHeader : true,
		layout : 'fit',
		width : 600,
		height : 450,
		pageY : 5,
		closable : true,
		maximizable : true,
		resizable : true,
		border : false,
		modal : true,
		buttonAlign : 'center'//,
//		minWidth : 600,// resizable为true有效
//		minHeight : 450 // resizable为true有效
	});
	CS.Window.superclass.constructor.call(this, config);
};
Ext.extend(CS.Window,Ext.Window);

//CS.button命名空间
Ext.namespace('CS.button') ;
/**添加按钮*/
CS.button.AButton = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		text : '增加',
		iconCls : 'addIcon',
		tooltip : '增加一条新的记录',
		scope : this
	});
	CS.button.AButton.superclass.constructor.call(this, config);
};
Ext.extend(CS.button.AButton,Ext.Button);
Ext.reg('addbutton',CS.button.AButton);
/**更新按钮*/
CS.button.UButton = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		text : '编辑',
		tooltip : '编辑选中的记录信息',
		iconCls : 'updateIcon',
		scope : this
	});
	CS.button.UButton.superclass.constructor.call(this, config);
};
Ext.extend(CS.button.UButton,Ext.Button);
Ext.reg('updatebutton',CS.button.UButton);
/**删除按钮*/
CS.button.DButton = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		text : '删除',
		tooltip : '删除选中的记录',
		iconCls : 'deleteIcon',
		scope : this
	});
	CS.button.DButton.superclass.constructor.call(this, config);
};
Ext.extend(CS.button.DButton,Ext.Button);
Ext.reg('deletebutton',CS.button.DButton);
/**查看按钮*/
CS.button.SButton = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		text : '查看',
		tooltip : '查看选中记录的详细信息',
		iconCls : 'seeIcon',
		scope : this
	});
	CS.button.SButton.superclass.constructor.call(this, config);
};
Ext.extend(CS.button.SButton,Ext.Button);
Ext.reg('seebutton',CS.button.SButton);
/**查询按钮*/
CS.button.FButton = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		text : '查询',
		tooltip : '根据查询条件过滤',
		iconCls : 'searchIcon',
		scope : this
	});
	CS.button.FButton.superclass.constructor.call(this, config);
};
Ext.extend(CS.button.FButton,Ext.Button);
Ext.reg('findbutton',CS.button.FButton);
/**重置按钮*/
CS.button.RButton = function(config){
	var config = config || {};
	Ext.applyIf(config, {
		text : '重置',
		tooltip : '重置',
		scope : this 
	});
	CS.button.RButton.superclass.constructor.call(this, config);
};
Ext.extend(CS.button.RButton,Ext.Button);
Ext.reg('resetbutton',CS.button.RButton);

new Ext.KeyMap(document, [{   
    key: Ext.EventObject.BACKSPACE,   
    fn: function (key, e) {   
        var t = e.target.tagName;   
        if (t !== "INPUT" && t !== "TEXTAREA") {   
            e.stopEvent();   
        }else if(t=="INPUT"&&e.target.readOnly==true){
        	e.stopEvent();
        }else if(t=="TEXTAREA"&&e.target.readOnly==true){
        	e.stopEvent();
        }
    }   
}]); 
    
var csAlert = function(title,msg){
	Ext.MessageBox.alert(title, msg);
};
/*
 * 该方法准备废除,不要再使用
 */
var handleResponse = function(response,handleType){
	if(response.status==0){
		Ext.Msg.alert('状态','连接失败，请保证服务已开启');
	}else if(response.status==-1){
		Ext.Msg.alert('状态','连接超时，请重试!');
	}else{
		Ext.Msg.alert('状态',handleType+'失败!');
	}
};
/**
 * 处理 Ext.Ajax.request 成功的方法.
 * @param {} response
 * @param {} request 
 * @param {} successCallback 成功的回调方法  方法应该接受一个参数,即请求返回的数据
 * @param {} failureCallback 失败的回调方法  方法应该接受一个参数,即请求返回的数据
 */
var handleAjaxRequest = function(response, request,successCallback,failureCallback){
	var MSG_TITLE = '消息提示' ;
	var MSG_ABORT = '连接超时,客户端自动退出,请求可能被接收,请刷新页面更新数据！' ;
	var MSG_FAILURE = '出错了,请刷新后重试！' ;
	var status = response.status ;
//**/Ext.Msg.alert('请求状态',status) ;//####################################测试阶段方便查看状态###########################################
	switch(status){
		case -1 : //请求超过时间,被主动中断,服务器可能接收到请求,未处理完毕,最好刷新页面.
			Ext.Msg.alert(MSG_TITLE,MSG_ABORT) ;
			if(failureCallback){
				failureCallback() ;
			}
			break ;
		case 0 : //请求出现异常...
			if(response.responseText){
				try{
					var obj = Ext.decode(response.responseText) ;
					Ext.Msg.alert(MSG_TITLE,obj?(obj.msg?obj.msg:MSG_FAILURE):MSG_FAILURE);
					if(failureCallback){
						failureCallback(obj) ;
					}
				}catch(e){
					Ext.Msg.alert(MSG_TITLE,MSG_FAILURE) ;
					if(failureCallback){
						failureCallback() ;
					}
				}
			}else{
				Ext.Msg.alert(MSG_TITLE,MSG_FAILURE) ;
				if(failureCallback){
					failureCallback() ;
				}
			}
			break ;
		case 200 : 
			try{
				var obj = Ext.decode(response.responseText) ;
				if(obj.success){
					if(successCallback){
						successCallback(obj) ;
					}
				}else{
					if(failureCallback){
						failureCallback(obj) ;
					}else{
						Ext.Msg.alert(MSG_TITLE,MSG_FAILURE) ;
					}
				}
			}catch(e){
			}
			break ;
		default	:
			Ext.Msg.alert(MSG_TITLE,MSG_FAILURE) ;
			if(failureCallback){
				failureCallback() ;
			}
	}
};

var handleFormRequestSuccess = function(form,action,successCallback){
	var obj = null ;
	try{
		obj = Ext.decode(action.response.responseText) ;
	}catch(e){}
	if(successCallback&&obj){
		successCallback(obj);
	}else if(successCallback){
		successCallback() ;
	}
};
var handleFormRequestFailure = function(form,action,failureCallback){
	var MSG_TITLE = '消息提示' ;
	var MSG_ABORT = '连接超时,客户端自动退出,请求可能被接收,请刷新页面更新数据！' ;
	var MSG_FAILURE = '出错了,请刷新后重试！' ;
	var failureType = action.failureType ;
	if(failureType==Ext.form.Action.CONNECT_FAILURE){
		Ext.Msg.alert(MSG_TITLE,MSG_ABORT) ;
		if(failureCallback){
			failureCallback() ;
		}
	}else if(failureType==Ext.form.Action.LOAD_FAILURE){
		if(action.response.responseText){
			try{
				var obj = Ext.decode(action.response.responseText) ;
				Ext.Msg.alert(MSG_TITLE,obj?(obj.msg?obj.msg:MSG_FAILURE):MSG_FAILURE);
				if(failureCallback){
					failureCallback(obj) ;
				}
			}catch(e){
				Ext.Msg.alert(MSG_TITLE,MSG_FAILURE) ;
				if(failureCallback){
					failureCallback() ;
				}
			}
		}else{
			Ext.Msg.alert(MSG_TITLE,MSG_FAILURE) ;
			if(failureCallback){
				failureCallback() ;
			}
		}
	}else if(failureType==Ext.form.Action.SERVER_INVALID){
		Ext.Msg.alert(MSG_TITLE,MSG_FAILURE) ;
		if(failureCallback){
			failureCallback() ;
		}
	}
};


function hideField(field)     
{     
   field.disable();// for validation     
   field.hide();     
   field.getEl().up('.x-form-item').setDisplayed(false); // hide label     
}     
     
function showField(field)     
{     
   field.enable();     
   field.show();     
   field.getEl().up('.x-form-item').setDisplayed(true);// show label     
}   
var READONLYCLS = 'readOnlyClass' ;
var CAOZUOTISHI = "操作提示" ;
var BAOCUNCHENGGONG = "保存成功" ;
var BAOCUNSHIBAI = "保存失败" ;
var SUCCESSSAVEALERT = function(){
	Ext.Msg.alert(CAOZUOTISHI,BAOCUNCHENGGONG) ;
};
var FAILURESAVEALERT = function(){
	Ext.Msg.alert(CAOZUOTISHI,BAOCUNSHIBAI) ;
};
