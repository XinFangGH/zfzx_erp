var getDisable = function(button, functionCode) {
	Ext.Ajax.request({
		url : 'findFunctionAble.do',
		method : 'POST',
		success : function(response,request) {
			if (response.responseText == '{success:true}') {
				button.enable();
			} else if(response.responseText == '{success:false}') {
				button.disable();
			}
		},
		failure : function(response) {
			
		},
		params: {
			functionCode : functionCode
		}
	});
};

//只传一个参数的方法...projObj
var getPermissionA = function(funName, functionCode, projObj) {
	Ext.Ajax.request({
		url : 'findFunctionAble.do',
		method : 'POST',
		success : function(response,request) {
			if (response.responseText == '{success:true}') {
				funName(false, projObj);
			} else if(response.responseText == '{success:false}') {
				funName(true, projObj);
			}
		},
		failure : function(response) {
			
		},
		params: {
			functionCode : functionCode
		}
	});
}
//传三个参数的方法...projObj
var getPermission3 = function(funName, functionCode, projId, enterpriseId, obj) {
	Ext.Ajax.request({
		url : 'findFunctionAble.do',
		method : 'POST',
		success : function(response,request) {
			if (response.responseText == '{success:true}') {
				funName(false, projId, enterpriseId, obj);
			} else if(response.responseText == '{success:false}') {
				funName(true, projId, enterpriseId, obj);
			}
		},
		failure : function(response) {
			
		},
		params: {
			functionCode : functionCode
		}
	});
}

var getPermission = function(funName, functionCode, id, projObjData) {
	Ext.Ajax.request({
		url : 'findFunctionAble.do',
		method : 'POST',
		success : function(response,request) {
			if (response.responseText == '{success:true}') {
				funName(false, id, projObjData);
			} else if(response.responseText == '{success:false}') {
				funName(true, id, projObjData);
			}
		},
		failure : function(response) {
			
		},
		params: {
			functionCode : functionCode
		}
	});
}

var getPermission2 = function(funName, functionCode1, functionCode2, id, projObjData) {
	Ext.Ajax.request({
		url : 'findFunctionAble.do',
		method : 'POST',
		success : function(response,request) {
			var s1;
			if (response.responseText == '{success:true}') {
				s1 = true;
			} else if(response.responseText == '{success:false}') {
				s1 = false;
			}
			Ext.Ajax.request({
				url : 'findFunctionAble.do',
				method : 'POST',
				success : function(response,request) {
					var s2;
					var right;
					if (response.responseText == '{success:true}') {
						s2 = true;
					} else if(response.responseText == '{success:false}') {
						s2 = false;
					}
					if (s1 == false && s2 == false) {
						right = 0;
					}
					if (s1 == true && s2 == false) {
						right = 1;
					}
					if (s1 == false && s2 == true) {
						right = 2;
					}
					if (s1 == true && s2 == true) {
						right = 3;
					}
					
					funName(right, id, projObjData);
				},
				failure : function(response) {
					
				},
				params: {
					functionCode : functionCode2
				}
			});
		},
		failure : function(response) {
			
		},
		params: {
			functionCode : functionCode1
		}
	});
}