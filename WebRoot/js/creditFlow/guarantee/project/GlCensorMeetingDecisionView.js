/**
 * @author lisl
 * @class GlCensorMeetingDecisionView
 * @description 线上审贷会打分情况
 * @extends Ext.Window
 */
GlCensorMeetingDecisionView = Ext.extend(Ext.Window,{
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          GlCensorMeetingDecisionView.superclass.constructor.call(this,{
        	   title : '线上审保会打分情况',
               width : 837,
			   height : 212,
			   frame : true,
               modal : true,
               iconCls :'',
               autoScroll :true,
               maximizable : false,
               layout : 'fit',
               items : [
               		this.gridPanel,
               		this.infoPanel
               ]
          });
      },
      initUIComponents : function() {
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
				scope : this,
				//store : jStore,
				autoScroll : true,
				autoWidth : true,
				layout : 'anchor',
				clicksToEdit : 1,
				viewConfig : {
					forceFit : true
				},
				bodyStyle:"padding-top:10px;",
				defaults : {
					margins : '10 10 0 0'
				},
				hiddenCm : true,
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
				url : __ctxPath + "/flow/listByRunIdTaskSign.do?runId="+this.runId+'&taskId='+this.taskId,
				fields : [{
							name : 'dataId',
							type : 'int'
						}, 'voteId','voteName','voteTime','taskId','isAgree','runId','createTime','taskLimitTime','totalScore','averageScore','position'],
				//plugins: [this.expanderFlow],
				items :[{xtype:'label',style :'padding-left:12px;',text : ''},{xtype:'label',style :'padding-left:30px;',text : ''},{xtype:'label',style :'padding-left:30px;',text : ''},{xtype:'label',style :'color:red;',text : '注'},{xtype:'label',style :'padding-bottom:12px;',text : ''}],
				columns : [
						//this.expanderFlow,
						{
							header : '职务',
							dataIndex : 'position',
							width : 107,
							scope : this,
							renderer : function(value,metadata,record,rowIndex,colIndex){
								var createTime=record.data.createTime;
								var taskLimitTime=record.data.taskLimitTime;
								var totalScore=record.data.totalScore;
								var averageScore=record.data.averageScore;
								if(averageScore==-1||totalScore==-1){
									totalScore = 0;
									averageScore = 0;
								}
								this.gridPanel.items.get(0).setText('开始时间：'+createTime);
								this.gridPanel.items.get(1).setText('截至时间：'+taskLimitTime);
								this.gridPanel.items.get(2).setText('决议方式：打分'+' (');
								this.gridPanel.items.get(4).setText('：平均分>=6,项目流转至下一个节点：确认相关费用;否则打回至：业务主管审核上报材料。)');
								this.infoPanel.items.get(0).setText('总分：'+totalScore);
								this.infoPanel.items.get(1).setText('平均分：'+averageScore);
								return value;
							}
						},{
							header : '人员',
							dataIndex : 'voteName'
						}, {
							header : '分数',
							dataIndex : 'isAgree',
							renderer : function(value){
								if(value==-1){
									return '<font color=red>弃权</font>';
								}else{
									return value;
								}
							}
						}, {
							header : '处理时间',
							dataIndex : 'voteTime',
							format : 'Y-m-d'
						}
					],
					listeners : {} 
			});
		}//初始化组件
});