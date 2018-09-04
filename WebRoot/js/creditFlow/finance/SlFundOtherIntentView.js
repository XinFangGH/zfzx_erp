SlFundOtherIntentView = Ext.extend(Ex.Panel,{
	
	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		SlFundOtherIntentView.superclass.constructor.call(this,{
			layout:'fit',
			items:[]
		});
	},
	initUIComponents:function(){
		this.topBar = new Ext.Toolbar({
			items:[{
				iconCls : 'btn-add',
			    text : '增加',
				xtype : 'button',
				scope : this,
				handler : this.addRs
			},{
				iconCls : 'btn-del',
			    text : '删除',
				xtype : 'button',
				scope : this,
				handler : this.delRs
			}]
		});
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,new Ext.grid.RowNumberer(),{
				header : '资金类型',
				dataIndex : 'fundType',
				renderer : function(value) {
					return "支出金额";
				}
		}]); 
		this.gridPanel = new Ext.grid.EditorGridPanel({
			
		});
	},
	//增加
	addRs:function(){
		
	},
	//删除
	delRs:function(){
	
	}
});