makePanle = function() {
	return {
		create : function(Str, pson) {
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
			var c = {};
			var gg = "0";// 是否为上传审贷会xx的
			for (var i = 0, len = Str.length; i < len; i++) {
				if (Str[i] == "ah_m")// 审贷会意见汇总
					gg = "1";
				// 通过判断获取filest
				c = _getFactory.create(Str[i], pson);
				if (c != {}) { // 判断--没写
					p.add(c);
				}
			}
			// 添加执行下一步方法
			if (gg == "0") {
				p.add({
							xtype : 'hidden',
							name : 'preHandler',
							value : 'slSmallloanProjectService.ahGoToNext'
						});
			} else {
				p.add({
					xtype : 'hidden',
					name : 'preHandler',
					value : 'slSmallloanProjectService.uploadMeetingSummaryNextStep'
				});
			}
			
			p.add({
					xtype : 'hidden',
					name : 'taskId',
					value : pson.taskId
				});
			return p;
		}

	}
}()
