/**
 * @author 
 * @createtime 
 * @class SlProcreditMaterialsForm
 * @extends Ext.Window
 * @description SlProcreditMaterials表单
 * @company 智维软件
 */
PlArchivesMaterialsForm = Ext.extend(Ext.Window, {
			//构造函数
	        projId:null,
	        operateObj:null,
	        businessType:null,
			constructor : function(_cfg) {
			    if(typeof(_cfg.projectId)!="undefined")
				{
				      this.projId=_cfg.projectId;
				}
				if(typeof(_cfg.operateObj)!="undefined")
				{
				      this.operateObj=_cfg.operateObj;
				}
				if(typeof(_cfg.businessType)!="undefined"){
				      
					   this.businessType=_cfg.businessType;
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				PlArchivesMaterialsForm.superclass.constructor.call(this, {
							layout : 'fit',
							items : this.gridPanel,
							modal : true,
							height : 407,
							width : 650,
							maximizable : true,
							title : '选择归档材料',
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
		    
				/*var checkModelEnter = new Ext.grid.CheckboxSelectionModel({
					
					listeners:{
								'rowselect' : function (sm,rowIndex,r){
									alert('rowselect1111');
									alert(rowIndex);
								},
								'rowdeselect' : function(sm,rowIndex,r){
									alert('rowdeselect');
									alert(rowIndex);
								}
						}
				});*/
				this.gridPanel = new HT.EditorGridPanel({
							border : false,
							isShowTbar : false,
							showPaging : false,
							region:'center',
							border : false,
							autoScroll:true,
							id : 'PlArchivesMaterialsForm',
						    url : __ctxPath + "/creditFlow/archives/getbyoperationTypeKeyOurArchivesMaterials.do?operationTypeKey="+this.operationType,
							fields : [
										'materialsId',
									    'materialsName'
										,'isReceive'
									],
							columns:[
										{
											header : 'materialsId',
											dataIndex : 'materialsId',
											hidden : true
										},{
											header : '归档材料名称',	
											dataIndex : 'materialsName'
										}
							],
						baseParams:{
						projectId : this.projId,
						bidPlanId:this.bidPlanId,
						businessType:this.businessType
					}
						})
				
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
					materialsIds = materialsIds+rows[i].get('materialsId');
					if(i!=rows.length-1){
						materialsIds = materialsIds+','
					}
				}
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/archives/updatePlArchivesMaterials.do",
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
						'materialsIds' :materialsIds,
						'businessType' : this.businessType,
						'operationType' :this.operationType,
						'bidPlanId':this.bidPlanId,
						'projectId' : this.projId
						
					}
				})
			}
		});