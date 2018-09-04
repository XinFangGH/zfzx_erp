/**
 * @author 
 * @createtime 
 * @class P2POnlineUploadView
 * @extends Ext.Window
 * @description P2POnlineUploadView表单
 * @company 互融软件
 */
P2POnlineUploadView = Ext.extend(Ext.Panel, {
	
		//构造函数
		constructor : function(_cfg) {
			if (_cfg == null) {
				_cfg = {};
			}
			Ext.applyIf(this, _cfg);
			//必须先初始化组件
			this.initUIComponents();
			P2POnlineUploadView.superclass.constructor.call(this, {
			    region : 'center',
			    layout : 'anchor',
			  //  height:100,
				items : [this.gridPanel]
			});
		},
		// 初始化组件
		initUIComponents : function() {
			this.gridPanel=new HT.GridPanel({
								region:'center',
								rowActions:true,
								autoHeight : true,
								showPaging:false,
								hiddenCm:true,
								url : __ctxPath + "/p2p/upLoadListWebFinanceApplyUploads.do?userId="+this.userId,
								fields : [{
												name : 'id',
												type : 'int'
											}
											,'userID'
											,'materialstype'
											,'files'
											,'status'
											,'pictureNum'
											,'lastuploadtime'
											,'materialCount'
										],
								columns:[
											{
												header : 'id',
												dataIndex : 'id',
												hidden : true
											}
											,{
												header : '用户ID',	
												dataIndex : 'userID',
												hidden:true
											}
											,{
												header : '材料类型',	
												dataIndex : 'materialstype'
											}
											,{
												header : '数量',	
												dataIndex :'materialCount'
											}
											,{
												header : '状态',	
												dataIndex : 'status',
												renderer:function(v){
			                                    if(v==0){
												return '未上传';
			                                    }else if(v==1){
			                                    	return '已上传、待审查或补充材料';
			                                    }else if(v==2){
			                                    	return '已驳回';
			                                    }else if(v==3){
			                                    	return '已认证';
			                                    }
											}
											}
											,{
					                            header : '上传时间',	
												dataIndex : 'lastuploadtime'
											}
											, new Ext.ux.grid.RowActions({
												header:'管理',
												width:100,
												actions:[	{
														         iconCls:'btn-readdocument',qtip:'浏览',style:'margin:0 3px 0 3px'
															}/*,{
																 iconCls:'btn-add',qtip:'通过',style:'margin:0 3px 0 3px'
															},{
																 iconCls:'btn-del',qtip:'驳回',style:'margin:0 3px 0 3px'
															}*/
												],
												listeners:{
													scope:this,
													'action':this.onRowAction
												}
											})
								]
							});
		},
			onRowAction : function(grid, record, action, row, col) {
			switch (action) {
				/*case 'btn-del' :
					this.rejectRs.call(this,record.data.id,"a",grid);
					break;
				case 'btn-add':
					this.updateRs.call(this,record.data.id,"b");
					break;*/
				case 'btn-readdocument':
				 var webId=grid.getStore().getAt(row).get("userID");
				 
				 
				 var materialstype=grid.getStore().getAt(row).get("materialstype");
				 var mark=webId+"."+materialstype;
			     BpCustMemberPicView(mark,"readOnly","typeisfile",/*grid.ownerCt.projId*/this.projId,/*grid.ownerCt.businessType*/this.businessType);
				 break;
				 default :
				break;
			}
		}
});