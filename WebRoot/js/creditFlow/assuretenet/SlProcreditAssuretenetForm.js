/**
 * @author zhangyl
 * @createtime 
 * @class SlProcreditAssuretenetForm
 * @extends Ext.form.FieldSet
 * @description 业务经理审批表单
 * @company 北京智维软件有限公司
 */
SlProcreditAssuretenetForm = Ext.extend(Ext.Panel, {
	        projId:null,
	        isReadOnly:false,
	        businessType:null,
	        headTitle:"企业贷款准入原则",
	        isEditRiskmanageropinion:true,
	        isRiskReadOnly:false,
	        isEditRiskmanagercombox:true,
			constructor : function(_cfg) {
				if (_cfg == null) {
							_cfg = {};
						}
				if(typeof(_cfg.projectId)!="undefined")
				{
				      this.projId=_cfg.projectId;
				}
				if(typeof(_cfg.headTitle)!="undefined")
				{
				      this.headTitle=_cfg.headTitle;
				}
				if(typeof(_cfg.isRiskReadOnly)!="undefined")
				{
				      this.isRiskReadOnly=_cfg.isRiskReadOnly;
				}
				if(typeof(_cfg.isEditRiskmanageropinion)!="undefined")
				{
				      this.isEditRiskmanageropinion=_cfg.isEditRiskmanageropinion;
				}
				if(typeof(_cfg.isEditRiskmanagercombox)!="undefined")
				{
				      this.isEditRiskmanagercombox=_cfg.isEditRiskmanagercombox;
				}
			    this.businessType=_cfg.businessType;
				if(_cfg.isReadOnly){
					
				   this.isReadOnly=_cfg.isReadOnly;
				}
			    Ext.applyIf(this, _cfg);
				this.initUIComponents();
				SlProcreditAssuretenetForm.superclass.constructor.call(this, {
							layout:'anchor',
		  					anchor : '100%',
							id : 'SlProcreditAssuretenetFormWin',
							items : this.gridPanel
				});
			},
			initUIComponents : function() {
				var objP=this;
				this.comBox = new Ext.form.ComboBox({
					mode : 'local',
					editable : false,
					readOnly:this.isRiskReadOnly,
					store : new Ext.data.SimpleStore({
								data : [['符合', '符合'], ['不符合', '不符合'], ['无法核实', '无法核实']],
								fields : ['text', 'value']
							}),
					displayField : 'text',
					valueField : 'value',
					triggerAction : 'all'
				});
				var headTitle=this.headTitle;
				this.gridPanel = new HT.EditorGridPanel({
					clicksToEdit:1,
				    isShowTbar:false,
					showPaging:false,
					autoHeight:true,
					hiddenCm:true,
					url : __ctxPath + '/assuretenet/listSlProcreditAssuretenet.do',
					fields : ['assuretenetId','assuretenet','projid','sortvalue','logger','businessmanageropinion','riskmanageropinion'],
					baseParams:{
						projId : this.projId,
						businessType:this.businessType
					},
					columns:[
					         {
									header : headTitle,
									sortable : true,
									width : 960,
									dataIndex : 'assuretenet'								
								},{		
									header : '业务经理意见',
									sortable : true,
									hidden : this.isHiddenBusiness,
									editor : new Ext.form.ComboBox({
										mode : 'local',
										editable : false,
										readOnly:this.isReadOnly,
										store : new Ext.data.SimpleStore({
											data : [['符合', '符合'], ['不符合', '不符合'],
													['无法核实', '无法核实']],
											fields : ['text', 'value']
										}),
										displayField : 'text',
										valueField : 'value',
										triggerAction : 'all'
									}),
									dataIndex : 'businessmanageropinion',
									renderer : function(v){
										if(v==''||!v){
											if(objP.isReadOnly){
											    return '未选择意见';
											}
											return '<font color=red>请点击选择</font>' ;
										}else if(v=='符合'){
											return '<font color=green>'+v+'</font>';
										}else if(v=='不符合'){
											return '<font color=red>'+v+'</font>';
										}else if(v=='无法核实'){
											return '<font color=blue>'+v+'</font>';
										}else{
											return v;
										}
									}
								},{
									header : '风险经理意见',
									sortable : true,
									width : 94,
									readOnly:this.isRiskReadOnly,
									editor : this.isEditRiskmanagercombox
											? this.comBox
											: null,
									hidden : this.isEditRiskmanageropinion,
									dataIndex : 'riskmanageropinion',
									renderer : function(v, metaData, record, rowIndex,
											colIndex, store) {
										if (v == '' || !v) {
											if (objP.isEditRiskmanagercombox) {
												return '<font color=red>请点击选择</font>';
											} else {
												return '未选择意见';
											}
										} else if (v == '符合') {
											return '<font color=green>' + v + '</font>';
										} else if (v == '不符合') {
											return '<font color=red>' + v + '</font>';
										} else if (v == '无法核实') {
											return '<font color=blue>' + v + '</font>';
										} else {
											return v;
										}
									}
								}],
						listeners : {
							'afteredit' : function(e) {
								var args;
								var value = e.value;
								var assuretenetId = e.record.get('assuretenetId');
								var businessmanageropinion = e.record.get('businessmanageropinion');
								if(e.originalValue != e.value){
										if (e.field == 'businessmanageropinion') {
											args = {
												'slProcreditAssuretenet.businessmanageropinion' : value,
												'slProcreditAssuretenet.assuretenetId' : assuretenetId
											};
										}
										if (e.field == 'riskmanageropinion') {
											args = {
												'slProcreditAssuretenet.riskmanageropinion' : value,
												'slProcreditAssuretenet.assuretenetId' : assuretenetId
											};
										}
								}
								Ext.Ajax.request({
									url : __ctxPath + '/assuretenet/saveSlProcreditAssuretenet.do',
									method : 'POST',
									success : function(response, request) {
										    e.record.commit();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
									},
									params :args
								})
						}
					}
					
				})
			}
		});