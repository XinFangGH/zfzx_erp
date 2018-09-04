/**
 * @author
 * @class AttitudeExplain
 * @extends Ext.Panel
 * @description [AttitudeExplain]管理
 * @company 智维软件
 * @createtime:
 */

AttitudeExplain = Ext.extend(Ext.Panel, {
			
			// 构造函数
			constructor : function(_cfg) {
				if (typeof (_cfg.projectId) != "undefined") {
					this.projectId = _cfg.projectId;
				}
			    if (typeof (_cfg.businessType) != "undefined") {
					this.businessType = _cfg.businessType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				AttitudeExplain.superclass.constructor.call(this, {
							region : 'center',
							layout : 'anchor',
							items : [this.radioPanel,this.explainPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.radioGroup = {
				    xtype: 'radiogroup',
				    name:'result',
				    fieldLabel: "打分结果",
				    items : [{
				    	boxLabel: '0分',
				    	name: 'result',
				    	inputValue:0  //映射的值
				    },{
				    	boxLabel: '1分',
				    	name: 'result',
				    	inputValue:1,  //映射的值
				    	checked : true
				    },{
				    	boxLabel: '2分',
				    	name: 'result',
				    	inputValue:2
				    },{
				    	boxLabel: '3分',
				    	name: 'result',
				    	inputValue:3
				    },{
				    	boxLabel: '4分',
				    	name: 'result',
				    	inputValue:4
				    },{
				    	boxLabel: '5分',
				    	name: 'result',
				    	inputValue:5
				    },{
				    	boxLabel: '6分',
				    	name: 'result',
				    	inputValue:6
				    },{
				    	boxLabel: '7分',
				    	name: 'result',
				    	inputValue:7
				    },{
				    	boxLabel: '8分',
				    	name: 'result',
				    	inputValue:8
				    },{
				    	boxLabel: '9分',
				    	name: 'result',
				    	inputValue:9
				    },{
				    	boxLabel: '10分',
				    	name: 'result',
				    	inputValue:10
				    },{
				    	boxLabel: '弃权',
				    	name: 'result',
				    	inputValue:-1
				    }]
				};
	            this.radioPanel= new Ext.Panel({
					border:false,
					width :800,
					layout : {
						type : 'form',
						pack : 'left'
					},
					defaults : {
						margins : '10 10 0 0'
					},
					items:[this.radioGroup]
				});
				this.explainPanel= new Ext.Panel({
					border:false,
					layout : {
						type : 'form',
						pack : 'left'
					},
					defaults : {
						margins : '10 10 0 0'
					},
					items:[{
						xtype : "textarea",
						name : "comments",
						anchor : "100%",
						fieldLabel : ""
						
					}]
				});
			}		
});
