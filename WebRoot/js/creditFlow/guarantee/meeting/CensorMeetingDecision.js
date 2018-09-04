/**
 * @author
 * @class CensorMeetingDecision
 * @extends Ext.Panel
 * @description [CensorMeetingDecision]管理
 * @company 智维软件
 * @createtime:
 */
CensorMeetingDecision = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if (typeof (_cfg.projectId) != "undefined") {
					this.projectId = _cfg.projectId;
				}
			    if (typeof (_cfg.businessType) != "undefined") {
					this.businessType = _cfg.businessType;
				}
				if (typeof (_cfg.runId) != "undefined") {
					this.runId = _cfg.runId;
				}
				if (typeof (_cfg.taskId) != "undefined") {
					this.taskId = _cfg.taskId;
				}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CensorMeetingDecision.superclass.constructor.call(this, {
							region : 'center',
							layout : 'anchor',
							items : [
								//{xtype:'panel',border:false,bodyStyle:'margin-bottom:8px',html : '<br/>开始时间：'+this.abc+'&nbsp;&nbsp;&nbsp;&nbsp;截至时间：&nbsp;&nbsp;&nbsp;&nbsp;决议方式：打分'},
								this.gridPanel,
								this.infoPanel
							]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				//var startTime ='';//开始时间
				/*this.expanderFlow = new Ext.ux.grid.RowExpander({
					tpl : new Ext.Template('<table>{contractContent}</table>'),
					lazyRender : false, 
					enableCaching : false
				});*/
				this.sm = new Ext.grid.CheckboxSelectionModel({header:'序号'});
				
				this.infoPanel= new Ext.Panel({
					border:false,
					layout : {
						type : 'form',
						pack : 'left'
					},
					defaults : {
						margins : '10 10 0 0'
					},
					items:[{xtype:'label',style :'padding-left:30px',text : ''},{xtype:'label',style :'padding-left:30px',text : ' '}]
				});
				this.gridPanel = new HT.EditorGridPanel({
					border : false,
					hiddenCm : true,
					scope : this,
					//store : jStore,
					autoScroll : true,
					autoWidth : true,
					layout : 'anchor',
					clicksToEdit : 1,
					viewConfig : {
						forceFit : true
					},
					bbar : false,
					tbar : null,
					isShowTbar : false,
					rowActions : false,
					showPaging : false,
					stripeRows : true,
					plain : true,
					loadMask : true,
					autoHeight : true,
					sm : this.sm,
					url : __ctxPath + "/flow/listByRunIdTaskSign.do?runId="+this.runId+'&taskId='+this.taskId+'&isNode=true',
					fields : [{
								name : 'dataId',
								type : 'int'
							}, 'voteId','voteName','voteTime','taskId','isAgree','runId','createTime','taskLimitTime','totalScore','averageScore','position','comments'],
					//plugins: [this.expanderFlow],
					items :[{xtype:'label',style :'padding-left:30px',text : ''},{xtype:'label',style :'padding-left:30px',text : ''},{xtype:'label',style :'padding-left:30px',text : ''},{xtype:'label',style :'color:red',text : '注'},{xtype:'label',text : ''},{html:""},{html : '<br>'}],
					columns : [
							//this.expanderFlow,
							{
								header : '职务',
								dataIndex : 'position',
								width : 60,
								scope : this,
								renderer : function(value,metadata,record,rowIndex,colIndex){
									
									var totalScore;
									var averageScore;
									
									var createTime=record.data.createTime;
									var taskLimitTime=record.data.taskLimitTime;
									totalScore=record.data.totalScore;
									averageScore=record.data.averageScore;
									
									if(averageScore==-1||totalScore==-1){
										totalScore = 0;
										averageScore = 0;
									}
									
									this.gridPanel.items.get(0).setText('开始时间：'+createTime);
									this.gridPanel.items.get(1).setText('截至时间：'+taskLimitTime);
									this.gridPanel.items.get(2).setText('决议方式：打分'+' (');
									this.gridPanel.items.get(4).setText('：平均分>=6,项目流转至下一个节点：审保会决议确认;否则打回至：业务主管审核上报材料。)');
									this.infoPanel.items.get(0).setText('总分：'+totalScore);
									this.infoPanel.items.get(1).setText('平均分：'+averageScore);
									return value;
								}
							},{
								header : '人员',
								width : 60,
								dataIndex : 'voteName'
							}, {
								header : '分数',
								dataIndex : 'isAgree',
								width : 60,
								renderer : function(value){
									if(value==-1){
										return '<font color=red>弃权</font>';
									}else{
										return value;
									}
								}
							}, {
								header : '处理时间',
								width : 80,
								dataIndex : 'voteTime',
								format : 'Y-m-d'
							}, {
								header : '意见与说明',
								width : 300,
								dataIndex : 'comments'
							}
						],
						listeners : {} 
				});
			}		
});
