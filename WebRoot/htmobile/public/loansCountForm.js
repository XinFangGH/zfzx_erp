
/**
 * 贷款计算 by shang
 */
Ext.define('htmobile.public.loansCountForm', {
    extend: 'Ext.form.Panel',
    name: 'loansCountForm',

    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	config = config || {};
    	Ext.apply(this,config);
    	Ext.apply(config,{
		    fullscreen: true,
		    title:'贷款计算',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [bottomBar,
		            {
		                xtype: 'fieldset',
		                defaults:{
		                	xtype: 'textfield',
		                	labelAlign:"left"
		                },
		                items: [{
		                    	label : "贷款金额(元)",
		                    	id:'aaa',
								placeHolder : '请输入金额',
								xtype : 'numberfield',
								name : 'projectMoney',
								maxValue:10000000,
								minValue:0,
								scope:this,
								listeners:{
									blur:function(e, eOpts ){
										//this.focus();
									}
								}
							},{
		                        xtype : "numberfield",
		                        label:"年化利率(%)",
		                        placeHolder : '请输入年化利率',
		                        name : 'Yearrate',
		                        maxValue:15,
		                        minValue:0
		                    },{
		                        xtype : "numberfield",
		                        label:"贷款期限(月)",
		                        placeHolder : '请输入贷款期限',
		                        name : 'loanTime',
		                        maxValue:150,
		                        minValue:0
		                    },{
		                        xtype : "textfield",
		                        label:"还款选项 ",
		                        name : 'csCooperationBank.linkPhone',
		                        readOnly:true,
		                        value:'等额本息'
		                    },{
		                    	xtype:'panel',
		                    	layout: {
							        type: 'hbox',
					        		align: 'middle'
							    },
		                    	items:[
		                    	{
									xtype : 'spacer'
								},{
						            xtype:'button',
						            name: 'submit',
						            style:'border-radius: 0.2em;color:#ffffff;font-family: 微软雅黑;width:49%;margin-top:5px;margin-bottom:5px;',
						            text:'计算',
						            scope:this,
						            handler:this.formSubmit
		                    	},{
									xtype : 'spacer'
							    },{
						            xtype:'button',
						            name: 'submit',
						            style:'border-radius: 0.2em;color:#ffffff;font-family: 微软雅黑;width:49%;margin-top:5px;margin-bottom:5px;',
						            text:'清空',
						            scope:this,
						            handler:this.clearcontent
		                    	},{
									xtype : 'spacer'
								}]
		                    },{
		                        xtype : "textfield",
		                        label:"本息合计(元)",
		                        name : 'TotalMoney',
		                        readOnly:true
		                    },{
		                        xtype : "textfield",
		                        label:"利息合计(元)",
		                        name : 'InterestTotal',
		                        readOnly:true
		                    },{
		                        xtype : "textfield",
		                        label:"月还本息(元)",
		                        name : 'MoneyTotalMoney',
		                        readOnly:true
		                    },{
		                    	xtype:'panel',
		                    	items:[{
		                    		html:'<div id="_result"></div>'
		                    	}]
		                    }
		          ]}]
    	});
    	this.callParent([config]);
    },formSubmit:function(){
    	//贷款金额
    	var projectMoney =  this.getCmpByName("projectMoney").getValue();
    	//年利率
    	var Yearrate =  this.getCmpByName("Yearrate").getValue();
    	//还款月数
    	var loanTime =  this.getCmpByName("loanTime").getValue();
    	
    	
    	var flag=true;
    	var Msg = '填写不正确，请检查！';
    	if(projectMoney==null||projectMoney==""||projectMoney==0){
			    flag=false;
			    Msg="贷款金额、"+Msg;
    	}
    	if(Yearrate==null||Yearrate==""||Yearrate==0){
			    flag=false;
			    Msg="年利率、"+Msg;
    	}
    	if(loanTime==null||loanTime==""||loanTime==0){
			    flag=false;
			    Msg="还款月数、"+Msg;
    	}
    	
    	if(flag){
    		
    		//类型转化
	    	//月利率
	    	var Monthrate = Yearrate/12/100;
	    	//每月还款额
	    	var RepaymentDate = getTodayForm();
	    	//贷款金额
	    	projectMoney = parseFloat(projectMoney)
	    	//年利率
	    	Yearrate = parseFloat(Yearrate)
	    	//贷款期限
	    	loanTime = parseFloat(loanTime)
	    	//月利率
	    	Monthrate = parseFloat(Monthrate)
	    	
	    	//根据公式计算（每月还款额）
	    	var MoneyTotalMoney = (projectMoney*Monthrate*Math.pow(1+Monthrate,loanTime))/((Math.pow(1+Monthrate,loanTime))-1);
	    	//利息合计
	    	var InterestTotal = 0;
	    	//已还款本金
	    	var Rprincipal = 0;
	    	
	    	var htmlValue='<div class="count_label">' +
	            			'<span style="width:10.0%;border-left:none">期数</span>' +
	            			'<span style="width:30.0%">还款日期</span>' +
	            			'<span style="width:30.0%">本金</span>' +
	            			'<span style="width:30.0%">利息</span>' +
	            		  '</div>';
	    	
	    	for(var i=1;i<=loanTime;i++){
	    		//还款日期
	    		RepaymentDate = getNextMonth(RepaymentDate);
	    		//每月应还利息
	    		var Interest = (projectMoney-Rprincipal)*Monthrate;
	    		//每月应还本金
	    		var Principal = MoneyTotalMoney-Interest;
	    		//累计已还本金
	    		Rprincipal=Rprincipal+Principal;
	    		InterestTotal+=Interest;
	    		
				htmlValue+='<div id="countResult">'+
	                			'<span style="width:10.0%;border-left:none">'+i+'</span>' +
	                			'<span style="width:30.0%">'+RepaymentDate+'</span>' +
	                			'<span style="width:30.0%">'+formatCurrency(Principal.toFixed(2))+'</span>' +
	                			'<span style="width:30.0%">'+formatCurrency(Interest.toFixed(2))+'</span>' +
	                		'</div>'
	    	}
	    	
	    	//设置值
	    	//本息合计
	    	TotalMoney = projectMoney+InterestTotal;//本金+利息合计=本息合计
	    	this.getCmpByName("TotalMoney").setValue(formatCurrency(TotalMoney.toFixed(2)));
	    	//利息合计
	    	this.getCmpByName("InterestTotal").setValue(formatCurrency(InterestTotal.toFixed(2)));
	    	//月还本息
	    	this.getCmpByName("MoneyTotalMoney").setValue(formatCurrency(MoneyTotalMoney.toFixed(2)));
	    	
	    	$("#_result").html(htmlValue);
    	}else{//验证不通过
    		Ext.Msg.alert('警告', Msg, Ext.emptyFn);
    	}
    	
    },clearcontent:function(){
    	this.reset();
    	$("#_result").html("");
    }
});
