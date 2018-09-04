/**
 * @author lisl
 * @class SlProcessFormView
 * @description 流程表单
 * @extends Ext.Window
 */
SlProcessFormView=Ext.extend(Ext.Window,{
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          SlProcessFormView.superclass.constructor.call(this,{
               title : '项目参与人员',
               width : 580,
			   height : 400,
               modal : true,
               iconCls:'',
               autoScroll :true,
               layout : 'fit',
               items:[
                  this.gridPanel
               ]
          
          });
      },
      initUIComponents : function() {
          this.gridPanel = new HT.GridPanel( {
          	hiddenCm : true,
          	isShowTbar : false,
          	border : false,
			region : 'center',
			rowActions : false,
			url : __ctxPath + "/creditFlow/getProcessFormCreditProject.do",
			baseParams : {
				runId : this.runId
			},
			fields : ['activityName','creatorName','createtime','endtime'],
			columns : [ {
				header : '节点名称',
				dataIndex : 'activityName'
			}, {
				header : '执行人',
				dataIndex : 'creatorName',
				renderer : function(val) {
					if (val == null || val == 'null') {
						return '<span style="color:red;">暂无执行人</span>';
					}else{
						return val;
					}
				}
			}, {
				header : '开始时间',
				dataIndex : 'createtime'
			}, {
				header : '结束时间',
				dataIndex : 'endtime'
			}]//end of columns
		})
	  }//初始化组件
});