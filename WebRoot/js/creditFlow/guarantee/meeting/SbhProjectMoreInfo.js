/**
 * @author 
 * @createtime 
 * @class SbhProjectMoreInfo
 * @extends Ext.Window
 * @description SbhProjectMoreInfo表单
 * @company 北京互融时代软件有限公司
 */
SbhProjectMoreInfo = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.isReadOnly)!="undefined"){
           this.isReadOnly=_cfg.isReadOnly;
        }
        if(typeof(_cfg.dataId)!="undefined"){
           this.dataId=_cfg.dataId;
        }
        var anchor = '96%';
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		SbhProjectMoreInfo.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 460,
			width : 700,
			maximizable : true,
			title : '意见详细信息'
		});
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel( {
			layout:'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			monitorValid : true,
			frame : true,
		    plain : true,
		    labelAlign:'right',
			defaults : {
				anchor : anchor,
				labelWidth : 100,
				columnWidth : 1,
			    layout : 'column'
			},
			//defaultType : 'textfield',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype:'textfield',
					fieldLabel : '人员',
					anchor : anchor,
					readOnly : this.isReadOnly,
					name : 'taskSignData.voteName',
					value : this.voteName
				}, {
					id : 'isAgreeValue',
					xtype:'textfield',
					fieldLabel : '投票意见',
					anchor : anchor,
					readOnly : this.isReadOnly,
					name : 'taskSignData.isAgree'
				}]
			}
			, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype:'textfield',
					fieldLabel : '职务',
					anchor : anchor,
					readOnly : this.isReadOnly,
					name : 'taskSignData.position',
					value : this.position
				},{
					xtype:'textfield',
					fieldLabel : '处理时间',
					anchor : anchor,
					readOnly : this.isReadOnly,
					name : 'taskSignData.voteTime'
				}]
			},{
				layout : 'form',
				items : [ {
					xtype:'textarea',
					fieldLabel : '担保总额调整意见',
					anchor : '98.5%',
					readOnly : this.isReadOnly,
					name : 'taskSignData.assureTotalMoneyComments'
				},{
					xtype:'textarea',
					fieldLabel : '保费费率调整意见',
					anchor : '98.5%',
					readOnly : this.isReadOnly,
					name : 'taskSignData.premiumRateComments'
				},{
					xtype:'textarea',
					fieldLabel : '抵质押物调整意见',
					anchor : '98.5%',
					readOnly : this.isReadOnly,
					name : 'taskSignData.mortgageComments'
				},{
					xtype:'textarea',
					fieldLabel : '担保期限调整意见',
					anchor : '98.5%',
					readOnly : this.isReadOnly,
					name : 'taskSignData.assureTimeLimitComments'
				},{
					xtype:'textarea',
					fieldLabel : '总体意见',
					anchor : '98.5%',
					readOnly : this.isReadOnly,
					name : 'taskSignData.comments'
				}]
			}]
		});
		//加载表单对应的数据	
		//if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/flow/getByRunIdTaskSign.do?dataId=' + this.dataId,
				root : 'data',
				preName : 'taskSignData',
				scope : this,
					success : function(resp, options) {
						var result = Ext.decode(resp.responseText);
						var res = result.data.isAgree;
						var isAgreeValue;
						if (res == -1) {
							isAgreeValue = '尚未投票';
						} else if (res == 1) {
							isAgreeValue = '同意';
						} else if (res == 2) {
							isAgreeValue = '否决';
						} else {
							isAgreeValue = '尚未投票';
							//isAgreeValue = '弃权';
						}
						Ext.getCmp('isAgreeValue').setValue(isAgreeValue);
					}
			});
		//}

	}//end of the initcomponents
});