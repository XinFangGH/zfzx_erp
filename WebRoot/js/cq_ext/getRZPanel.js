MakeRZPanle = function() {
}
// 静态属性
// 初始化地址,保存地址,下一步地址
MakePanle.loadUrl = "/flFinancing/getInfoFlFinancingProject.do";
MakePanle.saveUrl = "/flFinancing/saveNoApprovalFlFinancingProject.do";
MakePanle.nextUrl = "flFinancingProjectService.financingNoApprovalBusinessDataEntry";
// 属性
MakePanle.prototype.jsons = {
	"融资资料录入" : ["ah_1", "ah_a1"]
}
// 创建panel
MakePanle.prototype.create = function(s, pson) {
	var fac=_getRZFactory;
	var p = new Ext.Panel({
				modal : true,
				labelWidth : 100,
				buttonAlign : 'center',
				layout : 'form',
				border : false,
				defaults : {
					anchor : '100%',
					xtype : 'fieldset',
					labelAlign : 'left',
					collapsible : true,
					autoHeight : true
				}
			});
	// 循环Str
	var Str = this.jsons[s];
	// LiuCy
	MakePanle.getUrl(s);
	var c = {};
	for (var i = 0, len = Str.length; i < len; i++) {
		c = fac.create(Str[i], pson);
		if (c != {}) { // 判断--没写
			p.add(c);
		}
	}
	// 添加执行下一步方法
	p.add({
				xtype : 'hidden',
				name : 'preHandler',
				value : MakePanle.nextUrl
			});
	return p;
}


MakePanle.getUrl = function(s) {
	var Str = MakePanle.prototype.jsons[s];
	return Str;
}
