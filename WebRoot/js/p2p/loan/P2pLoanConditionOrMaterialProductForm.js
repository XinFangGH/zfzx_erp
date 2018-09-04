/**
 * @author 
 * @createtime 
 * @class P2pLoanConditionOrMaterialProductForm
 * @extends Ext.Window
 * @description P2pLoanConditionOrMaterialProductForm表单
 * @company 互融软件
 */
P2pLoanConditionOrMaterialProductForm = Ext.extend(Ext.Window, {
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
				P2pLoanConditionOrMaterialProductForm.superclass.constructor.call(this, {
							layout : 'fit',
							items : this.gridPanel,
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
				this.gridPanel = new HT.EditorGridPanel({
							border : false,
							isShowTbar : false,
							showPaging : false,
							region:'center',
							border : false,
							autoScroll:true,
							id : 'P2pLoanConditionOrMaterialProductForm',
						    url :  __ctxPath + "/p2p/listByProductP2pLoanBasisMaterial.do",
							fields : [
										'materialId',
									    'materialName'
										,'materialState','operationType','remark'
									],
							columns:[
										{
											header : 'materialId',
											dataIndex : 'materialId',
											hidden : true
										},{
											header : '客户类型',	
											dataIndex : 'operationType',
											renderer : function(val){
								             if(val=="person"){
								 	              return "个人";
								              }else if(val=="enterprise"){
									              return "企业";
								              }
							            }
										},{
											header : '贷款材料',	
											dataIndex : 'materialName'
										},{
											header : '材料类型',	
											dataIndex : 'materialState',
											renderer : function(val){
								             if(val==1){
								 	              return "必备";
								              }else if(val==2){
									              return "可选";
								              }
							            }
											
										}
							],
						baseParams:{
						productId : this.productId,
						operationType:this.operationType
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
				var materialId = "";
				var rows = this.gridPanel.getSelectionModel().getSelections();
				for(var i=0;i<rows.length;i++){
					materialId = materialId+rows[i].get('materialId');
					if(i!=rows.length-1){
						materialId = materialId+','
					}
				}
				Ext.Ajax.request({
					url : __ctxPath + "/p2p/addInfoP2pLoanConditionOrMaterial.do",
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
						'materialId':materialId,
						'productId' : this.productId
					}
				})
			}
		});