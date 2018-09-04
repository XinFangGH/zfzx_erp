

Ext.define('htmobile.public.LoanCount', {
    extend: 'Ext.Panel',
    
    name: 'LoanCount',

    constructor: function (config) {
	
    	config = config || {};
    	Ext.apply(config,{
		    fullscreen: true,
		     title:'贷款计算',
		    scrollable:{
		    	direction: 'vertical'
		    },
		    items: [
    			{
	    			xtype: 'fieldset',
	    			defaults:{
	    				 labelWidth:document.body.clientWidth/3,
	    				 clearIcon : true
	    			},
	    			items:[
	    				{
	    			        xtype:'textfield',
	    			        labelWidth:'22%',
	    			        placeHolder:'请输入金额',
	    			        name: 'person.cardtype',
				            label: '贷款金额'
	    			    },
				        {
				            xtype: 'textfield',
				            labelWidth:'22%',
				            placeHolder:'请输入年化利率',
				            name: 'person.name',
				            label: '年化利率'
				        },
				        {
				            xtype: 'textfield',
				            labelWidth:'22%',
				            placeHolder:'请输入贷款期限',
				            name: 'person.cardnumber',
				            label: '贷款期限'
				        },
				        {
				            xtype: 'textfield',
				            labelWidth:'22%',
				            name: 'person.cellphone',
				            label: '还款选项'
				        },
				        {
				            html:'<div class="m-login-submit">'+
							            '<a href="#" class="w-button w-button-main" id="">计算</a>'+
							       '</div>'
				        },{
				            xtype: 'textfield',
				            labelWidth:'22%',
				            name: 'person.postaddress',
				            label: '本息合计'
				        },{
				            xtype: 'textfield',
				            labelWidth:'22%',
				            name: 'person.postaddress',
				            label: '利息合计'
				        },{
				            xtype: 'textfield',
				            labelWidth:'22%',
				            name: 'person.postaddress',
				            label: '月还本息'
				        },
				        {
				            html:'<div class="table-count-box">'+
								        '<table border="0" cellspacing="0" cellpadding="0" class="w-table w-table-loancount">'+
								            '<thead>'+
								            '<tr>'+
								                '<td><span href="" class="cptit">期数</span></td>'+
								                '<td><span href="" class="cptit">利息</span></td>'+
								                '<td><span href="" class="cptit">本金</span></td>'+
								                '<td><span href="" class="cptit">合计</span></td>'+
								                '<td><span href="" class="cptit">未还本金</span></td>'+
								            '</tr>'+
								            '</thead>'+
								            '<tbody>'+
								            '<tr>'+
								                '<td>1</td>'+
								                '<td>2%</td>'+
								                '<td>1999</td>'+
								                '<td>2999</td>'+
								                '<td>200</td>'+
								            '</tr>'+
								            '</tbody>'+
								        '</table>'+
								    '</div>'
				        }
	        ]
	        }]
    	});

    	 this.callParent([config]);
    }
});
