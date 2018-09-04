/**
 * @author lisl
 * @class SlFilingRecordView
 * @description 项目归档记录
 * @extends Ext.Window
 */
SlFilingRecordView = Ext.extend(Ext.Window,{
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          SlFilingRecordView.superclass.constructor.call(this,{
               title : '项目归档记录',
               width : 650,
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
          	isShowTbar:false,
          	rowActions : false,
          	border : false,
			region : 'center',
			showPaging :false,
			url : __ctxPath + "/project/getFilingRecordsSlSmallloanProject.do",
			baseParams : {
				projectId : this.projectId
			},
			fields : ['materialsName', 'isPigeonhole','isReceive','confirmTime','recieveTime','archiveConfirmRemark','recieveRemarks'],
			columns : [ {
				header : '文件名称',
				width : 430,
				dataIndex : 'materialsName'
			}, {
				header : '是否已提交',
				dataIndex : 'isPigeonhole',
				align : "center",
				width : 95,
				renderer : function(v) {
					if(v == true) {
						return "是";
					}else if(v == false) {
						return "否";
					}
				}
			},{
				header : '提交时间',
				dataIndex : 'confirmTime'
			},{
				header : '提交备注',
				dataIndex : 'archiveConfirmRemark'
			}, {
				header : '是否已提交收到',
				dataIndex : 'isReceive',
				align : "center",
				width : 95,
				renderer : function(v) {
					if(v == true) {
						return "是";
					}else if(v == false) {
						return "否";
					}
				}
			},{
				header : '收到时间',
				dataIndex : 'recieveTime'
			},{
				header : '收到备注',
				dataIndex : 'recieveRemarks'
			}
			]//end of columns
		})
	  }//初始化组件
});