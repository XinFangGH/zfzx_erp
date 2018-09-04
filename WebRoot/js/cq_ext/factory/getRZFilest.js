// 1可编辑2不可编辑
// ah_a1 :基本项目信息 12
// ah_b1 :企业/个人信息12
// ah_c1 :款项12
// ah_d1:第一还款来源12
// ah_e1:共同借款人12
// ah_f1 :担保物品12 3
// ah_g1 :准入原则1 主 2风
// ah_h1 :资料清单12
// ah_i1 :调研报告12
// ah_j:风险专员
// ah_k1:风险报告
// ah_l:审贷会
// ah_m 1,2:审贷会
// ah_n:审贷会意见
// ah_o :合同列表 1.add 2.edit 3.query
// ah_p:归档材料
// ah_q:放宽信息确认
// 生产总控制,对js没太大用，给人看的
// 全局单例工厂
_getRZFactory = function() {
	return {
		create : function(fac, pson) {
			var f = {};
			if (fac == "ah_1") { // 一级保存调查
				return makeF.mack(1, pson);
			}
			if (fac == "ah_a1") {
				return makeRZ.mackA();
			}
			return f;
		}

	}
}()

makeRZ = function() {
	return {
		mackA : function(v, m) {
			return {
				xtype : 'fieldset',
				title : '项目基本信息 ',
				name : 'projectInfo',
				collapsible : true,
				autoHeight : true,
				labelAlign : 'right',
				items : [
						new ExtUD.Ext.TypeSelectInfoThreeGradesReadOnlyPanel(),
						new ExtUD.Ext.TypeSelectInfoMineType()]
			}
		}
	}

}()