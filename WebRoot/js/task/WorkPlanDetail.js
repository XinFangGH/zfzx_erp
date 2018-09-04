/**
 * 计划详细信息
 * 
 */
WorkPlanDetail = Ext.extend(Ext.Window,{
     constructor:function(_cfg){
        Ext.applyIf(this,_cfg);
        WorkPlanDetail.superclass.constructor.call(this,{
            title : this.planName+'--详细信息',
				iconCls:'menu-planmanage',
				autoHeight : true,
				x:280,
				y:100,
				modal:true,
				width : 660,
				layout:'form',
				buttonAlign : 'center',
				autoLoad : {
					url : __ctxPath + '/task/showWorkPlan.do?planId='+ this.planId
				},
				buttons : [{
							text : '关闭',
							iconCls:'close',
							scope:this,
							handler :this.closeWin
						}]
        });
     },
     closeWin:function(){
        this.close();
     }
});
