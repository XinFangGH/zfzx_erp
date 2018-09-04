/**
 * @author lisl
 * @class SlMeetingSummary
 * @description 上传审贷会纪要
 * @extends Ext.Window
 */
SlMeetingSummary = Ext.extend(Ext.Window,{
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          SlMeetingSummary.superclass.constructor.call(this,{
        	   title : '上传审贷会纪要',
               width : 785,
			   height: this.isHidden?226:253,
			   autoScroll : true,
               modal : true,
               iconCls:'',
               border : false,
               layout : 'fit',
               buttonAlign : 'center',
			   buttons : [
					  {
						  text:'保存',
						  scope:this,
						  iconCls:'btn-save',
						  disabled : this.isReadOnly, 
						  handler:this.submit
					  },{
						  text:'取消',
						  iconCls:'btn-cancel',
						  scope:this,
					      handler:function(){
					    	  this.close();
					      }
					  } 							
				  ],
			   items:[]
          });
      },
      initUIComponents : function() {
      	  var jsArr = [
    		  __ctxPath + '/js/creditFlow/smallLoan/meeting/MeetingSummaryForm.js',
    		  __ctxPath + '/js/creditFlow/smallLoan/meeting/MeetingSummaryUpload.js'
    		  ];
    	  $ImportSimpleJs(jsArr, this.constructPanel,this);		
	  },//初始化组件
	  constructPanel : function() {
		  this.meetingSummaryForm = new MeetingSummaryForm({projectId : this.projectId, businessType : this.businessType,isReadOnly : this.isReadOnly,isHidden : this.isHidden});
		  this.formPanel = new Ext.FormPanel({frame : true,labelWidth :60,labelAlign :'right'});
	  	  this.formPanel.add(this.meetingSummaryForm);
		  this.add(this.formPanel);
		  this.doLayout();
	  },
	  submit : function() {
	  	  this.formPanel.getForm().submit({
			url : __ctxPath + '/project/saveSlConferenceRecord.do?projId='+this.projectId+'&businessType='+this.businessType,
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			scope : this,
			success : function(fp, action) {
				this.meetingSummaryForm.autoLoadData();
				Ext.ux.Toast.msg('操作信息', '保存信息成功!');
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '信息保存出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
			}
		});
	  }
});
