/**
 * @author
 * @createtime
 * @class BpProductParameterForm
 * @extends Ext.Window
 * @description BpProductParameter表单
 * @company 智维软件
 */
BpProductParameterForm2 = Ext.extend(Ext.Window, {
	// 构造函数
	productId:null,
	object:null,
	constructor : function(_cfg) {
		if(typeof(_cfg.productId)!="undefined"){
			this.productId=_cfg.productId;
		}
		if(typeof(_cfg.object)!="undefined"){
			this.object=_cfg.object;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BpProductParameterForm2.superclass.constructor.call(this, {
			layout : 'form',
			items : this.outPanel,
			modal : true,
			autoScroll:true,
			height : 600,
			width : 1000,
			maximizable : true,
			frame:true,
			title : '产品信息',
			buttonAlign : 'center'
		});
	},
	// 初始化组件
	initUIComponents : function() {
		//贷款材料清单
		Ext.Ajax.request({
			url : __ctxPath + "/materials/listByProductIdOurProcreditMaterialsEnterprise.do?productId="+this.productId,
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.result){
					var str="";
					for(var i=0;i<obj.result.length;i++){
						str+='<li style="margin-left:100px;list-style:decimal inside" id="ml'+i+'">'+obj.result[i].materialsName+'</li>';
					}
					Ext.get("cl").dom.innerHTML=str;
				}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询失败!');
			}
		});
		//贷款必备材料
		Ext.Ajax.request({
			url : __ctxPath + "/assuretenet/listByProjectIdOurProcreditAssuretenet.do?productId="+this.productId,
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.result){
					var str="";
					for(var i=0;i<obj.result.length;i++){
						str+='<li style="margin-left:100px;list-style:decimal inside" id="ml'+i+'">'+obj.result[i].assuretenet+'</li>';
					}
					Ext.get("zryz").dom.innerHTML=str;
				}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询失败!');
			}
		});
		//保证和抵质押物详情
		Ext.Ajax.request({
			url : __ctxPath +'/system/getByProductIdBpProductParameter.do?productId='+this.productId,
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.result){
					var str1="";
					var str2="";
					var str3="";
					for(var i=0;i<obj.result.length;i++){
						if(obj.result[i].assuretypeid==604){//抵押
							str1+='<li  style="margin-left:100px;list-style:decimal inside" id="ml'+i+'">类别名称：'+obj.result[i].guaranteeType+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述：'+obj.result[i].remarks+'</td>';
						}else if(obj.result[i].assuretypeid==605){
							str2+='<li  style="margin-left:100px;list-style:decimal inside" id="ml'+i+'">类别名称：'+obj.result[i].guaranteeType+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述：'+obj.result[i].remarks+'</td>';
						}else if(obj.result[i].assuretypeid==606){
							str3+='<li  style="margin-left:100px;list-style:decimal inside" id="ml'+i+'">类别名称：'+obj.result[i].guaranteeType+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述：'+obj.result[i].remarks+'</td>';
						}
						
					}
					Ext.get("dyw").dom.innerHTML=str1;
					Ext.get("zyw").dom.innerHTML=str2;
					Ext.get("bz").dom.innerHTML=str3;
				}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询失败!');
			}
		});
		//手续费用收取清单
		Ext.Ajax.request({
			url : __ctxPath +'/creditFlow/finance/getByProductIdSlActualToCharge.do?productId='+this.productId,
			method : 'POST',
			scope:this,
			success : function(response, request) {
				var obj = Ext.util.JSON.decode(response.responseText);
				if(obj.result){
					var str="";
					for(var i=0;i<obj.result.length;i++){
						str+='<li  style="margin-left:100px;list-style:decimal inside" id="ml'+i+'">费用类型：'+obj.result[i].costType+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费用标准：'+obj.result[i].chargeStandard+'</td>';
					}
					Ext.get("sxqd").dom.innerHTML=str;
				}
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询失败!');
			}
		});
		this.outPanel = new Ext.Panel({
			modal : true,
			labelWidth : 100,
			frame:true,
			layout : 'fit',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				labelAlign : 'right',
				autoHeight : true
		    },
		    items:[{
				xtype : 'fieldset',
				border : false,
				title : '产品简介:',
				collapsible : false,
				autoHeight : true,
				labelAlign : 'right',
				items : [{
					layout : "form", // 从上往下的布局
					items : [{
						html:'<b><label  style="font-size:13;margin-left:25px;">产品名称:</label ></b> &nbsp;&nbsp;&nbsp;&nbsp;'+this.object.productName+'<p style="height:15px;"></p>' +
							'<b><label style="font-size:13;margin-left:25px;">借款人类型:</label></b> &nbsp;&nbsp;&nbsp;&nbsp;'+this.object.borrowerType+'<p style="height:15px;"></p>' +
							'<b><font style="font-size:13;margin-left:25px;">产品描述:</font></b> &nbsp;&nbsp;&nbsp;&nbsp;'+this.object.productDescribe
					}]
				}]
		    },{
				xtype : 'fieldset',
				title : '款项配置信息:',
				collapsible : false,
				border : false,
				autoHeight : true,
				labelAlign : 'right',
				items : [{
						html:'<b><label  style="font-size:13;margin-left:25px;">还款方式:</label ></b> &nbsp;&nbsp;&nbsp;&nbsp;'+this.object.accrualtype+'<p style="height:15px;"></p>' +
							'<b><label style="font-size:13;margin-left:25px;">还款周期:</label></b> &nbsp;&nbsp;&nbsp;&nbsp;'+this.object.payaccrualType+'<p style="height:15px;"></p>' +
							'<b><font style="font-size:13;margin-left:25px;">贷款期限:</font></b> &nbsp;&nbsp;&nbsp;&nbsp;'+this.object.payintentPeriod+'期<p style="height:15px;"></p>'+
							'<b><font style="font-size:13;margin-left:25px;">还款选项:</font></b> &nbsp;&nbsp;&nbsp;&nbsp;'+this.object.select+'<p style="height:15px;"></p>'+
							'<b><font style="font-size:13;margin-left:25px;">贷款利率:</font></b> &nbsp;&nbsp;&nbsp;&nbsp;月利率:'+this.object.accrual.toFixed(3)+'%'+'<p style="height:15px;"></p>'
				}]
		    },{
				xtype : 'fieldset',
				title : '所需材料清单:',
				collapsible : false,
				border : false,
				labelAlign : 'right',
				items : [{html:'<b><label style="font-size:13;margin-left:25px;">贷款材料:</label></b>&nbsp;&nbsp;&nbsp;&nbsp;<ol id="cl"></ol>'}]
		    },{
				xtype : 'fieldset',
				title : '贷款必备条件:',
				collapsible : false,
				border : false,
				labelAlign : 'right',
				items : [{html:'<b><label style="font-size:13;margin-left:25px;">准入原则:</label></b>&nbsp;&nbsp;&nbsp;&nbsp;<ol id="zryz"></ol>'}]
		    },{
				xtype : 'fieldset',
				title : '抵押物详情:',
				collapsible : false,
				border : false,
				labelAlign : 'right',
				items : [{html:'<b><label style="font-size:13;margin-left:25px;">抵押物:</label></b>&nbsp;&nbsp;&nbsp;&nbsp;<ol id="dyw"></ol>' +
							   '<b><label style="font-size:13;margin-left:25px;">质押物:</label></b>&nbsp;&nbsp;&nbsp;&nbsp;<ol id="zyw"></ol>' +
							   '<b><label style="font-size:13;margin-left:25px;">保证担保:</label></b>&nbsp;&nbsp;&nbsp;&nbsp;<ol id="bz"></ol>'}]
		    },{
				xtype : 'fieldset',
				title : '手续费用收取清单:',
				collapsible : false,
				border : false,
				labelAlign : 'right',
				items : [{html:'<b><label style="font-size:13;margin-left:25px;">费用清单:</label></b>&nbsp;&nbsp;&nbsp;&nbsp;<ol id="sxqd"></ol>'}]
		    }]
		});
	}
});