/**
 * @author 
 * @createtime 
 * @class P2pShowOnlineMaterialsForm
 * @extends Ext.Window
 * @description P2pShowOnlineMaterialsForm表单
 * @company 互融软件
 */
P2pShowOnlineMaterialsForm = Ext.extend(Ext.Window, {
			//构造函数
	        productId:null,
	        operateObj:null,
			constructor : function(_cfg) {
			    if(typeof(_cfg.productId)!="undefined")
				{
				      this.productId=_cfg.productId;
				}
				if(typeof(_cfg.operateObj)!="undefined")
				{
				      this.operateObj=_cfg.operateObj;
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				P2pShowOnlineMaterialsForm.superclass.constructor.call(this, {
							layout : 'fit',
							items : [this.gridPanel],
							modal : true,
							height : 407,
							width : 650,
							maximizable : true,
							title : '选择基础材料',
							buttonAlign : 'center',
							buttons : [
										{
											text : '确定',
											iconCls : 'btn-ok',
											scope : this,
											handler : this.save
										}, {
											text : '取消',
											iconCls : 'btn-close',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.gridPanel=new HT.GridPanel({
								region:'center',
								autoHeight : true,
								showPaging:false,
								hiddenCm:false,
								url : __ctxPath + "/p2p/upLoadOnLineListWebFinanceApplyUploads.do?userId="+this.userId+"&projectId="+this.projectId,
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
								]
							});
				
			},
			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 提交记录
			 */
			save : function() {
				var materialsIds = "";
				var rows = this.gridPanel.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					materialsIds = materialsIds+rows[i].get('id');
					if(i!=rows.length-1){
						materialsIds = materialsIds+','
					}
				}
				Ext.Ajax.request({
					url : __ctxPath + "/p2pMaterials/addOnlineMaterialsPlWebShowMaterials.do",
					method : 'POST',
					scope : this,
					success : function(response, request) {
						this.operateObj.store.reload();
						this.close();
					},
					failure : function(response) {
						Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
					},
					params : {
						'materialIds':materialsIds,
						"businessType":this.businessType,
						'operationType':this.operationType,
						'projId' : this.projectId
					}
				})
			}
		});